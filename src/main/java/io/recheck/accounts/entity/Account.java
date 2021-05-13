package io.recheck.accounts.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Account {

    private Long id;
    private String userName;
    private String password;

}
