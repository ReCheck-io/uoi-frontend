package io.recheck.ui.rest.dto;

import io.recheck.ui.entity.RELATIONSHIP;
import lombok.Data;

@Data
public class UpdateRelationshipDTO {

    RELATIONSHIP relationship = RELATIONSHIP.PARTOF;
    String childNode;
    String parentNode;

    public UpdateRelationshipDTO(String uoi, String parentUoi) {
        this.childNode = uoi;
        this.parentNode = parentUoi;
    }
}
