package tutorial.core.repositories;

import tutorial.core.entities.Account;
import tutorial.core.entities.Blog;

import java.util.List;

/**
 * Created by sletras on 22/01/2016.
 */
public interface AccountRepo {
    public Account findAccount(Long id);

    public Account createAccount(Account data);

    public List<Account> findAllAccounts();

    public Account findAccountByName(String name);
}
