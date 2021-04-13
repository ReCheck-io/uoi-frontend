package io.recheck.ui.components.uoi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestAccessModel {

    private String username;
    private String password;
    private String uoi;
    private String url;

}
