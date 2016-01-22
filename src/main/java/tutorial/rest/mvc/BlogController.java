package tutorial.rest.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tutorial.core.entities.Account;
import tutorial.core.entities.Blog;
import tutorial.core.entities.BlogEntry;
import tutorial.core.services.AccountService;
import tutorial.core.services.BlogService;
import tutorial.core.services.exceptions.AccountDoesNotExistException;
import tutorial.core.services.exceptions.AccountExistsException;
import tutorial.core.services.exceptions.BlogDoesNotExistException;
import tutorial.core.services.util.BlogEntryList;
import tutorial.core.services.util.BlogList;
import tutorial.rest.exceptions.ConflictException;
import tutorial.rest.exceptions.NotFoundException;
import tutorial.rest.resources.*;
import tutorial.rest.resources.asm.*;

import java.net.URI;
import java.util.List;
//import tutorial.entities.BlogEntry;

/**
 * Created by sletras on 20/01/2016.
 */
@Controller
@RequestMapping(value="/rest/blogs")
public class BlogController {
    /*@RequestMapping("/test")
    public ResponseEntity<Object> test(){
        BlogEntry entry = new BlogEntry();
        entry.setTitle("Hello there, this is a new entry");
        return new ResponseEntity<Object>(entry, HttpStatus.OK);
    }*/

    /*@RequestMapping(value="/test", method = RequestMethod.POST)
    public @ResponseBody BlogEntry test(@RequestBody BlogEntry entry){

        return entry;
    }*/

    private BlogService blogService;
    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    /*
    *Este método é um ponto de entrada da aplicação que permite encontrar entradas de blog
    * por id. O mesmo id é passado no endereço e é feito o bind para o construtor do metodo
    * através da anotação PathVariable. É depois invocado o serviço para encontrar a BlogEntry.
    * A mesma terá que ser traduzida para um recurso externo tal como foi definido na classe
    * BlogEntryResourceAsm que devolverá um BlogEntryResource e o mesmo é devolvido em conjunto
    * com o status HTTP.
    * O nome da variavel no RequestMapping deverá ser igual ao nome da variavel com a anotação
    * @PathVariable que está no construtor.
    */

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<BlogListResource> findAllBlogs()
    {
        BlogList blogList = blogService.findAllBlogs();
        BlogListResource blogListResource = new BlogListResourceAsm().toResource(blogList);
        return new ResponseEntity<BlogListResource>(blogListResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{blogId}", method = RequestMethod.GET)
    public ResponseEntity<BlogResource> getBlog(@PathVariable Long blogId)
    {
        Blog blog = blogService.findBlog(blogId);
        BlogResource blogResource = new BlogResourceAsm().toResource(blog);
        return new ResponseEntity<BlogResource>(blogResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{blogId}/blog-entries", method = RequestMethod.POST)
    public ResponseEntity<BlogEntryResource> createBlogEntry(@RequestBody BlogEntryResource blogEntrySent,
                                                             @PathVariable Long blogId)
    {
        try {
            BlogEntry blogEntry = blogEntrySent.toBlogEntry();
            blogService.createBlogEntry(blogId, blogEntry);
            BlogEntryResource blogEntryResource = new BlogEntryResourceAsm().toResource(blogEntry);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(blogEntryResource.getLink("self").getHref()));
            return new ResponseEntity<BlogEntryResource>(blogEntryResource, headers, HttpStatus.CREATED);
        } catch (BlogDoesNotExistException e) {
            throw new NotFoundException(e);
        }
    }

    @RequestMapping(value = "/{blogId}/blog-entries", method = RequestMethod.GET)
    public ResponseEntity<BlogEntryListResource> findAllBlogEntries(@PathVariable Long blogId)
    {
        try {
            BlogEntryList blogEntryList = blogService.findAllBlogEntries(blogId);
            BlogEntryListResource blogEntryListResource = new BlogEntryListResourceAsm().toResource(blogEntryList);
            return new ResponseEntity<BlogEntryListResource>(blogEntryListResource, HttpStatus.OK);
        } catch (BlogDoesNotExistException e) {
            throw new NotFoundException(e);
        }
    }
}
