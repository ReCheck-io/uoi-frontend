package io.recheck.rest.dto;

import lombok.Data;

@Data
public class CirdaxResponseWrapperDTO {

    private CirdaxAccessResponseDTO accessResponse;
    private CirdaxDocumentsResponseDTO documentsResponse;

}
