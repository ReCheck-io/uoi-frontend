package io.recheck.ui.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.recheck.ui.components.uoi.model.SearchByPropertiesModel;
import io.recheck.ui.components.uoi.model.SearchByUoiModel;
import io.recheck.ui.entity.UOINode;
import io.recheck.ui.rest.dto.NewUoiDTO;
import io.recheck.ui.rest.dto.RequestAccessDTO;
import io.recheck.ui.rest.dto.UpdatePropertiesDTO;
import io.recheck.ui.rest.dto.UpdateRelationshipDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RestClientService implements Serializable {

    @Value("${uoi.backend.server.host}")
    private String uoiBackendServerHost;

    private RestTemplate restTemplate;

    public RestClientService() {
        restTemplate = new RestTemplate();
    }

    public ResponseEntity<String> requestAccess(RequestAccessDTO requestAccessDTO) {
        HttpEntity entity = buildHttpEntityWithHeaders(requestAccessDTO);
        UriComponentsBuilder builder = buildEndpoint("/uoi");
        log.debug("Send PUT {} \n Body: {}", builder.toUriString(), entity);
        ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
        log.debug("Receive {}", responseEntity);
        return responseEntity;
    }

    public ResponseEntity<UOINode> newUoi(NewUoiDTO newUoiDTO) {
        HttpEntity entity = buildHttpEntityWithHeaders();
        UriComponentsBuilder builder = buildEndpointWithParams(newUoiDTO, "/new");
        log.debug("Send GET {}", builder.toUriString());
        ResponseEntity<UOINode> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, UOINode.class);
        log.debug("Receive {}", responseEntity);
        return responseEntity;
    }

    public ResponseEntity<UOINode> updateProperties(UpdatePropertiesDTO updatePropertiesDTO) {
        HttpEntity entity = buildHttpEntityWithHeaders(updatePropertiesDTO);
        UriComponentsBuilder builder = buildEndpoint("/node/properties");
        log.debug("Send PUT {} \n Body: {}", builder.toUriString(), entity);
        ResponseEntity<UOINode> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, entity, UOINode.class);
        log.debug("Receive {}", responseEntity);
        return responseEntity;
    }

    public ResponseEntity<String> makeRelationship(UpdateRelationshipDTO updateRelationshipDTO) {
        HttpEntity entity = buildHttpEntityWithHeaders(updateRelationshipDTO);
        UriComponentsBuilder builder = buildEndpoint("/node/relationship");
        log.debug("Send PUT {} \n Body: {}", builder.toUriString(), entity);
        ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, entity, String.class);
        log.debug("Receive {}", responseEntity);
        return responseEntity;
    }

    public ResponseEntity<String> searchByUoi(SearchByUoiModel searchByUoiDTO) {
        HttpEntity entity = buildHttpEntityWithHeaders();
        UriComponentsBuilder builder = buildEndpointWithParams(searchByUoiDTO, "/search/uoi");
        log.debug("Send GET {}", builder.toUriString());
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
            log.debug("Receive {}", responseEntity);
            return responseEntity;
        } catch (HttpClientErrorException ex) {
            ResponseEntity<String> responseEntity = new ResponseEntity(ex.getStatusCode());
            return responseEntity;
        }
    }

    public ResponseEntity<String> searchByProperties(SearchByPropertiesModel searchByPropertiesDTO) {
        HttpEntity entity = buildHttpEntityWithHeaders();
        UriComponentsBuilder builder = buildEndpointWithParams(searchByPropertiesDTO, "/search/properties");
        log.debug("Send GET {}", builder.toUriString());
        ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
        log.debug("Receive {}", responseEntity);
        return responseEntity;
    }

    public ResponseEntity<String> getChildren(SearchByPropertiesModel searchByPropertiesDTO) {
        HttpEntity entity = buildHttpEntityWithHeaders();
        UriComponentsBuilder builder = buildEndpointWithParams(searchByPropertiesDTO, "/search/properties");
        log.debug("Send GET {}", builder.toUriString());
        ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
        log.debug("Receive {}", responseEntity);
        return responseEntity;
    }


    //==========================================================
    //             UTIL METHODS
    //==========================================================

    public List<UOINode> getSearchByUOIResult(ResponseEntity<String> responseEntity) {
        String responseBody = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode.value() == 404) {
            return Collections.emptyList();
        }
        else {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                UOINode uoiNode = objectMapper.readValue(responseBody, UOINode.class);
                return Arrays.asList(uoiNode);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return Collections.emptyList();
        }
    }

    public List<UOINode> getSearchByPropertiesResult(ResponseEntity<String> responseEntity) {
        String responseBody = responseEntity.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<UOINode> uoiNodes = objectMapper.readValue(responseBody, new TypeReference<List<UOINode>>(){});
            return uoiNodes;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
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
            builder.queryParam(entry.getKey(), entry.getValue());
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

}
