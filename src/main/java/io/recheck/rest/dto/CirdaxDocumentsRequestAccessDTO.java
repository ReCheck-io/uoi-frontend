package io.recheck.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class CirdaxDocumentsRequestAccessDTO {

    private String uoi;
    private String requestorCode;
    private String requestorName;

}
