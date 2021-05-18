package io.recheck.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class CirdaxAccessRequestDTO {

    private String uoi;
    private String requestorCode;
    private String requestorName;

}
