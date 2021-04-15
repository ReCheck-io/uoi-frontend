package io.recheck.ui.components.uoi.model;

import lombok.Data;

@Data
public class SearchByUoiModel {

    private String uoi;

    public SearchByUoiModel(String uoi) {
        this.uoi = uoi.trim();
    }
}
