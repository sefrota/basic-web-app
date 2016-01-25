package tutorial.core.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tutorial.core.entities.Account;
import tutorial.core.entities.Blog;
import tutorial.core.repositories.AccountRepo;
import tutorial.core.repositories.BlogRepo;
import tutorial.core.services.AccountService;
import tutorial.core.services.exceptions.AccountDoesNotExistException;
import tutorial.core.services.exceptions.AccountExistsException;
import tutorial.core.services.exceptions.BlogExistsException;
import tutorial.core.services.util.AccountList;
import tutorial.core.services.util.BlogList;

import java.util.List;

/**
 * Created by sletras on 22/01/2016.
 * The use of @Service Annotation allows Spring to detect these classes as services, basically
 * setting these classes as Singleton Beans just as Components. On the other hand we also need to
 * add the @Transactional Annotation to the Class to make all the methods in it occur under one sole transaction
 * which means all the data will persist at the end of the method to the database.
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private BlogRepo blogRepo;

    @Override
    public Account findAccount(Long id) {
        return accountRepo.findAccount(id);
    }

    @Override
    public Account createAccount(Account data) {
        Account account = accountRepo.findAccountByName(data.getName());
        if(account != null){
            throw new AccountExistsException();
        }
        return accountRepo.createAccount(data);
    }

    @Override
    public Blog createBlog(Long accountId, Blog data) {
        Blog sameTitle = blogRepo.findBlogByTitle(data.getTitle());
        if(sameTitle != null){
            throw new BlogExistsException();
        }

        Account account = accountRepo.findAccount(accountId);
        if(account == null){
            throw new AccountDoesNotExistException();
        }

        Blog createdBlog = blogRepo.createBlog(data);
        createdBlog.setOwner(account);

        return createdBlog;
    }

    @Override
    public BlogList findBlogsByAccount(Long accountId) {
        return new BlogList(blogRepo.findBlogsByAccount(accountId));
    }

    @Override
    public AccountList findAllAccounts() {
        return new AccountList(accountRepo.findAllAccounts());
    }

    @Override
    public Account findByAccountName(String name) {
        return accountRepo.findAccountByName(name);
    }
}
