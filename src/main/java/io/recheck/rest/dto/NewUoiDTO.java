package io.recheck.rest.dto;

import io.recheck.uoi.entity.LEVEL;
import io.recheck.uoi.ui.components.model.UOIFormModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewUoiDTO {

    private String countryCode;
    private LEVEL level;
    private String parentUOI;

    public NewUoiDTO(UOIFormModel uoiFormModel) {
        this.countryCode = uoiFormModel.getCountryCode().trim();
        this.level = uoiFormModel.getLevel();
        this.parentUOI = uoiFormModel.getParentUOI().trim();
    }

}
