package tutorial.core.services;

import tutorial.core.entities.Account;
import tutorial.core.entities.Blog;
import tutorial.core.entities.BlogEntry;
import tutorial.core.services.util.AccountList;

/**
 * Created by sletras on 20/01/2016.
 * Contem os serviços que vao estar disponíveis para ser acedidos pelo exterior efectuando a ligação entre a camada de dados
 * e o controller
 */
public interface AccountService {
    public Account findAccount(Long id);

    public Account createAccount(Account data);

    public Blog createBlog(Long accountId, Blog data);

    public AccountList findAllAccounts();

    public Account findByAccountName(String name);
}
