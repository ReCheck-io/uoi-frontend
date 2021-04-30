package io.recheck.uoi.rest.dto;

import io.recheck.uoi.entity.RELATIONSHIP;
import lombok.Data;

@Data
public class UpdateRelationshipDTO {

    RELATIONSHIP relationship = RELATIONSHIP.PARTOF;
    String childNode;
    String parentNode;

    public UpdateRelationshipDTO(String uoi, String parentUoi) {
        this.childNode = uoi.trim();
        this.parentNode = parentUoi.trim();
    }
}
