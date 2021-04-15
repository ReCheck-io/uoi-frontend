package io.recheck.ui.rest.dto;

import lombok.Data;

@Data
public class UpdatePropertiesDTO {

    private String uoi;
    private String key;
    private String value;

    public UpdatePropertiesDTO(String uoi, String key, String value) {
        this.uoi = uoi.trim();
        this.key = key.trim();
        this.value = value.trim();
    }
}
