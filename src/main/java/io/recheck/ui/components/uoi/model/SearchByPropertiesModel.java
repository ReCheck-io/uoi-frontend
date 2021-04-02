package io.recheck.ui.components.uoi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchByPropertiesModel {

    private String key;
    private String value;
    private Boolean withMetaData;

}
