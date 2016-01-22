package tutorial.core.services;

import tutorial.core.entities.BlogEntry;

/**
 * Created by sletras on 20/01/2016.
 * Contem os serviços que vao estar disponíveis para ser acedidos pelo exterior efectuando a ligação entre a camada de dados
 * e o controller
 */
public interface BlogEntryService {
    public BlogEntry findBlogEntry(Long id);//Returns the blog entry with the id param

    public BlogEntry updateBlogEntry(Long id, BlogEntry data);//Updates the blog entry with the id param with the data param

    public BlogEntry deleteBlogEntry(Long id);//Delete the blog entry with the id param
}
