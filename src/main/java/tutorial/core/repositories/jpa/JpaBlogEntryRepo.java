package tutorial.core.repositories.jpa;

import org.springframework.stereotype.Repository;
import tutorial.core.entities.BlogEntry;
import tutorial.core.repositories.BlogEntryRepo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by sletras on 22/01/2016.
 */
@Repository
public class JpaBlogEntryRepo implements BlogEntryRepo {

    @PersistenceContext
    EntityManager em;

    @Override
    public BlogEntry findBlogEntry(Long id) {
        return em.find(BlogEntry.class, id);
    }

    @Override
    public BlogEntry updateBlogEntry(Long id, BlogEntry data) {
        BlogEntry blogEntry = em.find(BlogEntry.class, id);
        blogEntry.setTitle(data.getTitle());
        blogEntry.setContent(data.getContent());
        em.persist(blogEntry);
        return blogEntry;
    }

    @Override
    public BlogEntry deleteBlogEntry(Long id) {
        BlogEntry blogEntry = em.find(BlogEntry.class, id);
        em.remove(blogEntry);
        return blogEntry;
    }

    @Override
    public BlogEntry createBlogEntry(BlogEntry data) {
        em.persist(data);
        return data;
    }

    @Override
    public List<BlogEntry> findByBlogId(Long blogId) {
        Query query = em.createQuery("Select b from BlogEntry b where b.blog.id = :blogId");
        query.setParameter("blogId", blogId);
        return query.getResultList();
    }
}
