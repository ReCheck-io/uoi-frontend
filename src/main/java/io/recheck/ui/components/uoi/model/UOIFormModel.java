package io.recheck.ui.components.uoi.model;

import io.recheck.ui.entity.LEVEL;
import io.recheck.ui.entity.UOINode;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class UOIFormModel {

    private String countryCode;
    private LEVEL level;
    private String parentUOI;

    public UOIFormModel(UOINode uoiNode) {
        this.countryCode = uoiNode.getUoi().substring(0, uoiNode.getUoi().indexOf('.'));
        this.level = uoiNode.getLevel();
        this.parentUOI = Optional.ofNullable(uoiNode.getParentUOI()).orElse("");
    }

}
