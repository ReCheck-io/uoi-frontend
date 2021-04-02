package io.recheck.ui.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.recheck.ui.entity.UOINode;
import io.recheck.ui.rest.dto.SearchByPropertiesDTO;
import io.recheck.ui.rest.dto.SearchByUoiDTO;
import io.recheck.ui.rest.dto.NewUoiDTO;
import io.recheck.ui.rest.dto.UpdatePropertiesDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;
import java.util.*;

@Service
@Slf4j
public class RestClientService implements Serializable {

    @Value("${uoi.backend.server.host}")
    private String uoiBackendServerHost;

    private RestTemplate restTemplate;

    public RestClientService() {
        restTemplate = new RestTemplate();
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

    public ResponseEntity<String> searchByUoi(SearchByUoiDTO searchByUoiDTO) {
        HttpEntity entity = buildHttpEntityWithHeaders();
        UriComponentsBuilder builder = buildEndpointWithParams(searchByUoiDTO, "/search/uoi");
        log.debug("Send GET {}", builder.toUriString());
        ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
        log.debug("Receive {}", responseEntity);
        return responseEntity;
    }

    public ResponseEntity<String> searchByProperties(SearchByPropertiesDTO searchByPropertiesDTO) {
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
