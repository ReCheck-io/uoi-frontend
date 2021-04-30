package io.recheck.uoi.ui.components.model;

import io.recheck.uoi.entity.LEVEL;
import io.recheck.uoi.entity.UOINode;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class UOIFormModel {

    private String uoi;
    private String countryCode;
    private LEVEL level;
    private String parentUOI;

    public UOIFormModel(UOINode uoiNode) {
        this.uoi = uoiNode.getUoi();
        this.countryCode = uoiNode.getUoi().substring(0, uoiNode.getUoi().indexOf('.'));
        this.level = uoiNode.getLevel();
        this.parentUOI = Optional.ofNullable(uoiNode.getParentUOI()).orElse("");
    }

}
