package io.recheck.ui.components.uoi.model;

import io.recheck.ui.components.map.MapModel;
import io.recheck.ui.entity.UOINode;
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
