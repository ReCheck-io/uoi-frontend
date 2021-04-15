package io.recheck.ui.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UOINode {

    private String uoi;
    private Map<String, String> properties = new HashMap<>();
    private String parentUOI;
    private String countryCode;
    private LEVEL level;
    private String timestamp;
    private String uoiClass;
    private String owner;
    private ArrayList<String> children = new ArrayList();

    public UOINode(String uoi) {
        this.uoi = uoi;
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

    public boolean equals(Object o){
        if(o == null)
            return false;
        if(!(o instanceof UOINode))
            return false;

        UOINode other = (UOINode) o;
        return this.uoi.equals(other.uoi);
    }


}
