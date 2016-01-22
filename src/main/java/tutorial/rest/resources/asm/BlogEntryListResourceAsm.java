package tutorial.rest.resources.asm;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import tutorial.core.entities.BlogEntry;
import tutorial.core.services.util.BlogEntryList;
import tutorial.rest.mvc.BlogController;
import tutorial.rest.mvc.BlogEntryController;
import tutorial.rest.resources.BlogEntryListResource;
import tutorial.rest.resources.BlogEntryResource;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by sletras on 20/01/2016.
 * É a classe que faz a tradução entre a entidade interna BlogEntry e a entidade externa
 * BlogEntryResource
 * 1º Parametro construtor é o controller associado à entidade
 * 2º Parametro é o resource externo
 */
public class BlogEntryListResourceAsm extends ResourceAssemblerSupport<BlogEntryList, BlogEntryListResource> {

    public BlogEntryListResourceAsm(){
        super(BlogController.class, BlogEntryListResource.class);
    }

    @Override
    public BlogEntryListResource toResource(BlogEntryList blogEntryList) {
        List<BlogEntryResource> resources = new BlogEntryResourceAsm().toResources(blogEntryList.getEntries());
        BlogEntryListResource listResources = new BlogEntryListResource();
        listResources.setEntries(resources);
        listResources.add(linkTo(methodOn(BlogController.class)
                .findAllBlogEntries(blogEntryList.getBlogId()))
                .withSelfRel());
        return listResources;
    }
}