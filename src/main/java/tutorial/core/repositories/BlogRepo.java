package tutorial.core.repositories;

import tutorial.core.entities.Blog;
import tutorial.core.entities.BlogEntry;
import tutorial.core.services.util.BlogEntryList;
import tutorial.core.services.util.BlogList;

import java.util.List;

/**
 * Created by sletras on 22/01/2016.
 */
public interface BlogRepo {

    public Blog createBlog(Blog data);

    public Blog findBlogByTitle(String title);

    public Blog findBlog(Long id);

    public List<Blog> findAllBlogs();

    public List<Blog> findBlogsByAccount(Long accountId);
}
