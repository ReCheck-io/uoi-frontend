package io.recheck.ui.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestAccessDTO {

    private String userId;
    private String systemId;
    private String uoi;

}
