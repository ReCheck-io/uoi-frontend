package io.recheck.uoi.ui.components.model;

import io.recheck.uoi.entity.UOINode;
import io.recheck.uoi.ui.components.map.MapModel;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Map;


@Data
public class PropertiesModel implements MapModel {

    private String uoi;
    private Map<String, String> properties;

    public PropertiesModel(UOINode uoiNode) {
        BeanUtils.copyProperties(uoiNode, this);
    }

    public Map getMap() {
        return properties;
    }

    public void setMap(Map map) {
        properties.putAll(map);
    }
}
