package io.recheck.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class CirdaxDocumentsResponseDTO {

    private List<CirdaxDocumentsDTO> cirdaxDocumentsDTOList;
    private String rawResponse;

}
