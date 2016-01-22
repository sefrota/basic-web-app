package tutorial.rest.resources.asm;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import tutorial.core.entities.BlogEntry;
import tutorial.rest.mvc.BlogController;
import tutorial.rest.mvc.BlogEntryController;
import tutorial.rest.resources.BlogEntryResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 * Created by sletras on 20/01/2016.
 * É a classe que faz a tradução entre a entidade interna BlogEntry e a entidade externa
 * BlogEntryResource
 * 1º Parametro construtor é o controller associado à entidade
 * 2º Parametro é o resource externo
 */
public class BlogEntryResourceAsm extends ResourceAssemblerSupport<BlogEntry, BlogEntryResource> {

    public BlogEntryResourceAsm(){
        super(BlogEntryController.class, BlogEntryResource.class);
    }

    @Override
    public BlogEntryResource toResource(BlogEntry blogEntry) {
        BlogEntryResource resource = new BlogEntryResource();
        resource.setTitle(blogEntry.getTitle());
        resource.setContent(blogEntry.getContent());
        Link self = linkTo(BlogEntryController.class)
                .slash(blogEntry.getId()).withSelfRel();
        resource.add(self);
        if(blogEntry.getBlog() != null){
            resource.add((linkTo(BlogController.class)
                .slash(blogEntry.getBlog().getId()).withRel("blog")));
        }
        return resource;
    }
}
