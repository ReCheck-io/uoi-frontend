package io.recheck.accounts;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import io.recheck.accounts.entity.Account;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@VaadinSessionScope
public class AccountService {

    private Account loggedAccount;

    public Optional<Account> findAccountByUserName(String userName) {
        loggedAccount = new Account(1L, userName, "test");
        return Optional.of(loggedAccount);
    }

    public Optional<Account> getLoggedAccount() {
        return Optional.ofNullable(loggedAccount);
    }

    public void logout() {
        loggedAccount = null;
    }

}
