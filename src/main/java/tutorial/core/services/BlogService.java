package tutorial.core.services;

import tutorial.core.entities.Blog;
import tutorial.core.entities.BlogEntry;
import tutorial.core.services.util.BlogEntryList;
import tutorial.core.services.util.BlogList;

/**
 * Created by sletras on 20/01/2016.
 * Contem os serviços que vao estar disponíveis para ser acedidos pelo exterior efectuando a ligação entre a camada de dados
 * e o controller
 */
public interface BlogService {

    public BlogEntry createBlogEntry(Long blogId, BlogEntry data);

    public Blog findBlog(Long id);

    public BlogList findAllBlogs();

    public BlogEntryList findAllBlogEntries(Long blogId);
}
