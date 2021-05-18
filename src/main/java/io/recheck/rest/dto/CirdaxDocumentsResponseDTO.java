package io.recheck.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CirdaxDocumentsResponseDTO {

    private List<CirdaxDocumentsDTO> cirdaxDocumentsDTOList;
    private String rawResponse;

}
