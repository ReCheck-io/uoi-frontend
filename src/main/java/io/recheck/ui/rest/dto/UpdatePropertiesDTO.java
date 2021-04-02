package io.recheck.ui.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatePropertiesDTO {

    private String uoi;
    private String key;
    private String value;

}
