package io.recheck.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.recheck.exceptionhandler.ApiError;
import io.recheck.rest.dto.*;
import io.recheck.uoi.entity.UOINode;
import io.recheck.uoi.ui.components.model.SearchByPropertiesModel;
import io.recheck.uoi.ui.components.model.SearchByUoiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RestClientService implements Serializable {

    @Value("${uoi.backend.server.host}")
    private String uoiBackendServerHost;

    private RestTemplate restTemplate;

    public RestClientService() {
        restTemplate = new RestTemplate();
    }

    public ResponseEntity<CirdaxResponseWrapperDTO> requestAccess(CirdaxAccessRequestDTO requestAccessDTO) {
        HttpEntity entity = buildHttpEntityWithHeaders(requestAccessDTO);
        UriComponentsBuilder builder = buildEndpoint("/documents/cirdax");
        log.debug("Send POST {} \n Body: {}", builder.toUriString(), entity);

        try {
            ResponseEntity<CirdaxResponseWrapperDTO> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, CirdaxResponseWrapperDTO.class);
            log.debug("Receive {}", responseEntity);
            return responseEntity;
        } catch (HttpClientErrorException ex) {
            return httpClientErrorToResponse(ex);
        }
    }

    public ResponseEntity newUoi(NewUoiDTO newUoiDTO) {
        HttpEntity entity = buildHttpEntityWithHeaders();
        UriComponentsBuilder builder = buildEndpointWithParams(newUoiDTO, "/new");
        log.debug("Send GET {}", builder.toUriString());

        try {
            ResponseEntity<UOINode> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, UOINode.class);
            log.debug("Receive {}", responseEntity);
            return responseEntity;
        } catch (HttpClientErrorException ex) {
            return httpClientErrorToResponse(ex);
        }
    }

    public ResponseEntity<UOINode> updateProperties(UpdatePropertiesDTO updatePropertiesDTO) {
        HttpEntity entity = buildHttpEntityWithHeaders(updatePropertiesDTO);
        UriComponentsBuilder builder = buildEndpoint("/node/properties");
        log.debug("Send PUT {} \n Body: {}", builder.toUriString(), entity);

        try {
            ResponseEntity<UOINode> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, entity, UOINode.class);
            log.debug("Receive {}", responseEntity);
            return responseEntity;
        } catch (HttpClientErrorException ex) {
            return httpClientErrorToResponse(ex);
        }
    }

    public ResponseEntity makeRelationship(UpdateRelationshipDTO updateRelationshipDTO) {
        HttpEntity entity = buildHttpEntityWithHeaders(updateRelationshipDTO);
        UriComponentsBuilder builder = buildEndpoint("/node/relationship");
        log.debug("Send PUT {} \n Body: {}", builder.toUriString(), entity);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, entity, String.class);
            log.debug("Receive {}", responseEntity);
            return responseEntity;
        } catch (HttpClientErrorException ex) {
            return httpClientErrorToResponse(ex);
        }
    }

    public List<UOINode> searchByUoi(SearchByUoiModel searchByUoiDTO) {
        HttpEntity entity = buildHttpEntityWithHeaders();
        UriComponentsBuilder builder = buildEndpointWithParams(searchByUoiDTO, "/search/uoi");
        log.debug("Send GET {}", builder.toUriString());

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
            log.debug("Receive {}", responseEntity);
            return getSearchByUOIResult(responseEntity);
        } catch (HttpClientErrorException ex) {
            httpClientErrorToResponse(ex);
            return Collections.emptyList();
        }
    }

    public List<UOINode> searchByProperties(SearchByPropertiesModel searchByPropertiesDTO) {
        HttpEntity entity = buildHttpEntityWithHeaders();
        UriComponentsBuilder builder = buildEndpointWithParams(searchByPropertiesDTO, "/search/properties");
        log.debug("Send GET {}", builder.toUriString());

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
            log.debug("Receive {}", responseEntity);
            return getSearchByPropertiesResult(responseEntity);
        } catch (HttpClientErrorException ex) {
            httpClientErrorToResponse(ex);
            return Collections.emptyList();
        }
    }

    public ResponseEntity<String> getChildren(SearchByPropertiesModel searchByPropertiesDTO) {
        HttpEntity entity = buildHttpEntityWithHeaders();
        UriComponentsBuilder builder = buildEndpointWithParams(searchByPropertiesDTO, "/search/properties");
        log.debug("Send GET {}", builder.toUriString());

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
            log.debug("Receive {}", responseEntity);
            return responseEntity;
        } catch (HttpClientErrorException ex) {
            return httpClientErrorToResponse(ex);
        }
    }


    //==========================================================
    //             UTIL METHODS
    //==========================================================

    private List<UOINode> getSearchByUOIResult(ResponseEntity<String> responseEntity) {
        String responseBody = responseEntity.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            UOINode uoiNode = objectMapper.readValue(responseBody, UOINode.class);
            return Arrays.asList(uoiNode);
        } catch (JsonProcessingException e) {
            log.error("", e);
        }
        return Collections.emptyList();
    }

    private List<UOINode> getSearchByPropertiesResult(ResponseEntity<String> responseEntity) {
        String responseBody = responseEntity.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<UOINode> uoiNodes = objectMapper.readValue(responseBody, new TypeReference<List<UOINode>>(){});
            return uoiNodes;
        } catch (JsonProcessingException e) {
            log.error("", e);
        }
        return Collections.emptyList();
    }

    private ResponseEntity httpClientErrorToResponse(HttpClientErrorException ex) {
        log.info("{}", ex.getMessage());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ApiError apiError = objectMapper.readValue(ex.getResponseBodyAsString(), ApiError.class);
            return (ResponseEntity<ApiError>) new ResponseEntity(apiError, ex.getStatusCode());
        } catch (JsonProcessingException e) {
            log.error("", e);
            return (ResponseEntity<String>) new ResponseEntity(ex.getResponseBodyAsString(), ex.getStatusCode());
        }
    }

    private UriComponentsBuilder buildEndpoint(String endpoint) {
        return UriComponentsBuilder.fromHttpUrl(uoiBackendServerHost + endpoint);
    }

    private UriComponentsBuilder buildEndpointWithParams(Object dto, String endpoint) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> dtoAsMap =
                objectMapper.convertValue(dto, new TypeReference<Map<String, String>>() {});

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(uoiBackendServerHost + endpoint);

        for (Map.Entry<String, String> entry : dtoAsMap.entrySet()) {
            builder.queryParam(entry.getKey(), encodeValue(entry.getValue()));
        }

        return builder;
    }

    private HttpEntity buildHttpEntityWithHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return new HttpEntity<String>(headers);
    }

    private HttpEntity buildHttpEntityWithHeaders(Object dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(dto, headers);
    }

    private String encodeValue(String value) {
        if (StringUtils.hasText(value)) {
            try {
                return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException e) {
                log.error("", e);
            }
        }
        return "";
    }

}
