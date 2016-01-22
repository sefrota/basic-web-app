package tutorial.core.repositories;

import tutorial.core.entities.Account;
import tutorial.core.entities.Blog;
import tutorial.core.entities.BlogEntry;

import java.util.List;

/**
 * Created by sletras on 22/01/2016.
 */
public interface BlogEntryRepo {
    public BlogEntry findBlogEntry(Long id);//Returns the blog entry with the id param

    public BlogEntry updateBlogEntry(Long id, BlogEntry data);//Updates the blog entry with the id param with the data param

    public BlogEntry deleteBlogEntry(Long id);//Delete the blog entry with the id param

    public BlogEntry createBlogEntry(BlogEntry data);

    public List<BlogEntry> findByBlogId(Long blogId);
}
