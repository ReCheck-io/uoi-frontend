package io.recheck.uoi.ui.components.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestAccessModel {

    private String username;
    private String password;
    private String uoi;

}
