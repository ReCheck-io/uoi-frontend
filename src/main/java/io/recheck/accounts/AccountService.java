package io.recheck.accounts;

import io.recheck.accounts.entity.Account;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    private List<Account> accounts = new ArrayList<>();

    public AccountService() {
        accounts.add(new Account(1L, "TestAccount", "testPassword"));
    }

    public Account findAccountByUserName(String userName) {
        return accounts.stream().filter(a -> a.getUserName().equalsIgnoreCase(userName)).findFirst().get();
    }

}
