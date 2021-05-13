package io.recheck.uoi.ui.components.model;

import lombok.Data;

@Data
public class SearchByPropertiesModel {

    private String key;
    private String value;
    private Boolean withMetaData;

    public SearchByPropertiesModel(String key, String value, Boolean withMetaData) {
        this.key = key.trim();
        this.value = value.trim();
        this.withMetaData = withMetaData;
    }
}
