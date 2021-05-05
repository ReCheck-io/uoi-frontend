package io.recheck.uoi.ui.components;

import io.recheck.accounts.entity.Account;

public interface DocumentsRequestAccessListeners {

    void doIfAccountPresent(String uoi, Account account);

}
