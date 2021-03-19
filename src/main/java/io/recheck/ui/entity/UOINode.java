package io.recheck.ui.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UOINode {

    private String uoi;
    private Map<String, String> properties = new HashMap<>();
    private String parentUOI = null;
    private String countryCode;
    private LEVEL level;
    private String timestamp;
    private String owner;
    private String uoiClass;

    public UOINode(String uoi) {
        this.uoi = uoi;
    }

    public void addProperties(String key, String value) {
        if (this.properties != null){
            this.properties.put(key, value);
        }else {
            this.properties = new HashMap();
            this.properties.put(key, value);
        }
    }

    public String getPropertiesAsJson() {
        if (properties != null && !properties.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            try {
                return mapper.writeValueAsString(this.properties);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return "";
    }


}