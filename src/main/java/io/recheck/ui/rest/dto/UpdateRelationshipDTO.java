package io.recheck.ui.rest.dto;

import io.recheck.ui.components.uoi.model.UOIFormModel;
import io.recheck.ui.entity.RELATIONSHIP;
import io.recheck.ui.entity.UOINode;
import lombok.Data;

@Data
public class UpdateRelationshipDTO {

    RELATIONSHIP relationship = RELATIONSHIP.PARTOF;
    String childNode;
    String parentNode;

    public UpdateRelationshipDTO(UOINode uoiNode, UOIFormModel uoiFormModel) {
        this.childNode = uoiNode.getUoi();
        this.parentNode = uoiFormModel.getParentUOI();
    }
}
