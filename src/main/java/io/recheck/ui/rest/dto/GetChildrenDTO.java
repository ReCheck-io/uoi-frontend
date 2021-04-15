package io.recheck.ui.rest.dto;

import io.recheck.ui.entity.LEVEL;
import lombok.Data;

@Data
public class GetChildrenDTO {

    private LEVEL level;
    private String uoi;

    public GetChildrenDTO(String uoi, LEVEL level){
        this.uoi = uoi;
        this.level = level;
    }

}

