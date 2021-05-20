package io.recheck.uoi.ui.components.model;

import lombok.Data;

@Data
public class SearchByUoiModel {

    private String uoi;

    public SearchByUoiModel(String uoi) {
        this.uoi = uoi.trim();
    }
}
