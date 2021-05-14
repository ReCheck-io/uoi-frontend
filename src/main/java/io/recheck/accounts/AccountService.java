package io.recheck.accounts;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import io.recheck.accounts.entity.Account;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@VaadinSessionScope
public class AccountService {

    private List<Account> accounts = new ArrayList<>();
    private Account loggedAccount;

    public AccountService() {
        accounts.add(new Account(1L, "Ivaylo-Rusev-Recheck", "test"));
    }

    public Optional<Account> findAccountByUserName(String userName) {
        return accounts.stream().filter(a -> a.getUserName().equalsIgnoreCase(userName)).findFirst();
    }

    public Optional<Account> getLoggedAccount() {
        return Optional.ofNullable(loggedAccount);
    }

    public void setLoggedAccount(Account loggedAccount) {
        this.loggedAccount = loggedAccount;
    }
}
