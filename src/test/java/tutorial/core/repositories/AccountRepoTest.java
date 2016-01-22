package tutorial.core.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import tutorial.core.entities.Account;

import static org.junit.Assert.assertNotNull;

/**
 * Created by sletras on 22/01/2016.
 * This uses the @RunWith annotation which allows us to provide a custom junit test runner.
 * In this case we're going to inject a Spring specific testrunner that makes us able to inject and configure
 * spring beans.
 * It also uses the @ContextConfiguration annotation which provides a specific configuration xml file.
 * By default maven will look for the folder resources for the classpath to look for configuration files.
 * It is important to use the @Transactional annotation when accessing or modifying data to avoid problems.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/business-config.xml")
public class AccountRepoTest {

    @Autowired
    private AccountRepo repo;
    private Account account;

    @Before
    @Transactional
    @Rollback(false)
    public void setup(){
        account = new Account();
        account.setName("test");
        account.setPassword("test");
        repo.createAccount(account);
    }

    @Test
    @Transactional //It should be transactional to make us able to inject the EntityManager
    public void find(){
        assertNotNull(repo.findAccount(account.getId()));
    }
}
