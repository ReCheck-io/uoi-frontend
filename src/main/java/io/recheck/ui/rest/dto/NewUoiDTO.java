package io.recheck.ui.rest.dto;

import io.recheck.ui.components.uoi.model.UOIFormModel;
import io.recheck.ui.entity.LEVEL;
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
