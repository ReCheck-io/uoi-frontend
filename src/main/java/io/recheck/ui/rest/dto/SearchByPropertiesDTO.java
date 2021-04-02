package io.recheck.ui.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchByPropertiesDTO {

    private String key;
    private String value;
    private Boolean withMetaData;

}
