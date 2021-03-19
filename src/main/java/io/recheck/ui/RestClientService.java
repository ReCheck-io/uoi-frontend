package io.recheck.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.recheck.ui.entity.UOINode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RestClientService implements Serializable {

    @Value("${uoi.backend.server.host}")
    private String uoiBackendServerHost;

    private RestTemplate restTemplate;

    public RestClientService() {
        restTemplate = new RestTemplate();
    }

    public UOINode searchByUOI(String UOI) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        final String url = String.format(uoiBackendServerHost + "/search/uoi?uoi=%s", UOI);

        String searchResponse = restTemplate.getForObject(url, String.class);
        if (searchResponse.equals("The node does not exist.")) {
            return null;
        }
        else {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                UOINode uoiNode = objectMapper.readValue(searchResponse, UOINode.class);
                return uoiNode;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public List<UOINode> searchByProperties(String key, String value, Boolean returnWithProperties) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        final String url = String.format(uoiBackendServerHost + "/search/properties?key=%s&value=%s&withMetaData=%s", key, value, returnWithProperties);

        String searchResponse = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<UOINode> uoiNodes = objectMapper.readValue(searchResponse, new TypeReference<List<UOINode>>(){});
            return uoiNodes;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();

    }

    public UOINode newUOI(String countryCode, String level, String parentUOI) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        final String url = String.format(uoiBackendServerHost + "/new?countryCode=%s&level=%s&parentUOI=%s", countryCode, level, parentUOI);

        return restTemplate.exchange(url, HttpMethod.GET, entity, UOINode.class).getBody();
    }

    public void updateProperties(String uoi, Map<String, String> properties) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        properties.forEach((key, value) -> {
            String url = String.format(uoiBackendServerHost + "/node/properties?uoi=%s&key=%s&value=%s", uoi, key, value);
            ResponseEntity<UOINode> exchangeResponse = restTemplate.exchange(url, HttpMethod.PUT, entity, UOINode.class);
        });

    }

}
