package tutorial.core.services.util;

import tutorial.core.entities.Account;
import tutorial.core.entities.Blog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sletras on 22/01/2016.
 */
public class AccountList {
    private List<Account> accounts = new ArrayList<Account>();

    public List<Account> getAccounts() {
        return accounts;
    }

    public AccountList(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
