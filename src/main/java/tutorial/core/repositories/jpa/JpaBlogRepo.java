package tutorial.core.repositories.jpa;

import org.springframework.stereotype.Repository;
import tutorial.core.entities.Blog;
import tutorial.core.repositories.BlogRepo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by sletras on 22/01/2016.
 */
@Repository
public class JpaBlogRepo implements BlogRepo {

    @PersistenceContext
    EntityManager em;

    @Override
    public Blog createBlog(Blog data) {
        em.persist(data);
        return data;
    }

    @Override
    public Blog findBlogByTitle(String title) {
        Query query = em.createQuery("Select b from Blog b where b.title = :title");
        query.setParameter("title", title);
        List<Blog> blogs = query.getResultList();
        if(blogs.isEmpty())
            return null;
        else
            return blogs.get(0);
    }

    @Override
    public Blog findBlog(Long id) {
        return em.find(Blog.class, id);
    }

    @Override
    public List<Blog> findAllBlogs() {
        Query query = em.createQuery("Select b from Blog b");
        return query.getResultList();
    }

    @Override
    public List<Blog> findBlogsByAccount(Long accountId) {
        Query query = em.createQuery("Select b from Blog b where b.owner.id = :accountId");
        query.setParameter("accountId", accountId);
        return query.getResultList();
    }
}
