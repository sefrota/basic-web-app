package tutorial.core.repositories.jpa;

import org.springframework.stereotype.Repository;
import tutorial.core.entities.Account;
import tutorial.core.entities.Blog;
import tutorial.core.repositories.AccountRepo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by sletras on 22/01/2016.
 * This class has a @Repository Annotation to facilitate the translation of JPA exceptions since it normalizes them
 * in a way Spring can find a common exception to deal with. It has this advantage over the @Component annotation.
 */
@Repository
public class JpaAccountRepo implements AccountRepo {

    @PersistenceContext //Manages whether or not this EntityManager is transaction scoped or not. By default it is.
    private EntityManager em;

    @Override
    public Account findAccount(Long id) {
        return em.find(Account.class, id);
    }

    @Override
    public Account createAccount(Account data) {
        em.persist(data);
        return data;
    }

    @Override
    public List<Account> findAllAccounts() {
        Query query = em.createQuery("Select a from Account a");
        return query.getResultList();
    }

    @Override
    public Account findAccountByName(String name) {
        Query query = em.createQuery("Select a from Account a where a.name = :name");
        query.setParameter("name", name);
        List<Account> result = query.getResultList();
        if(result.isEmpty())
            return null;
        else
            return result.get(0);
    }

}
