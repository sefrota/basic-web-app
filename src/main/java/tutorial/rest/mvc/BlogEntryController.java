package tutorial.rest.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tutorial.core.entities.BlogEntry;
import tutorial.core.services.BlogEntryService;
import tutorial.rest.resources.BlogEntryResource;
import tutorial.rest.resources.asm.BlogEntryResourceAsm;
//import tutorial.entities.BlogEntry;

/**
 * Created by sletras on 20/01/2016.
 */
@Controller
@RequestMapping(value="/rest/blog-entries")
public class BlogEntryController {
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

    private BlogEntryService blogEntryService;
    @Autowired
    public BlogEntryController(BlogEntryService blogEntryService) {
        this.blogEntryService = blogEntryService;
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
    @RequestMapping(value = "/{blogEntryId}", method = RequestMethod.GET)
    public ResponseEntity<BlogEntryResource> getBlogEntry(@PathVariable Long blogEntryId) {
        BlogEntry entry = blogEntryService.findBlogEntry(blogEntryId);
        if (null != entry) {
            BlogEntryResource resource = new BlogEntryResourceAsm().toResource(entry);
            return new ResponseEntity<BlogEntryResource>(resource, HttpStatus.OK);
        } else {
            return new ResponseEntity<BlogEntryResource>(HttpStatus.NOT_FOUND);
        }
    }
    /*
    *Neste caso trata-se de um update logo é passado como argumento de entrada o próprio objecto
    * externo, blogEntryResource e o id do próprio para que possa ser feito o update. Após o objecto
    * ter sido actualizado o mesmo é pssado novamente para objecto externo e devolvido na resposta com
    * o respectivo link associado ao Resource.
     */
    @RequestMapping(value = "/{blogEntryId}", method = RequestMethod.PUT)
    public ResponseEntity<BlogEntryResource> updateBlogEntry(@PathVariable Long blogEntryId,
                                                             @RequestBody BlogEntryResource blogEntrySent) {
        BlogEntry entry = blogEntryService.updateBlogEntry(blogEntryId, blogEntrySent.toBlogEntry());
        if (null != entry) {
            BlogEntryResource resource = new BlogEntryResourceAsm().toResource(entry);
            return new ResponseEntity<BlogEntryResource>(resource, HttpStatus.OK);
        } else {
            return new ResponseEntity<BlogEntryResource>(HttpStatus.NOT_FOUND);
        }
    }

    /*
    *Neste caso é apenas passado o id visto que se trata de um delete. O recurso é apagado
    * e depois devolvido como resposta.
     */
    @RequestMapping(value = "/{blogEntryId}", method = RequestMethod.DELETE)
    public ResponseEntity<BlogEntryResource> deleteBlogEntry(@PathVariable Long blogEntryId) {
        BlogEntry entry = blogEntryService.deleteBlogEntry(blogEntryId);
        if (null != entry) {
            BlogEntryResource resource = new BlogEntryResourceAsm().toResource(entry);
            return new ResponseEntity<BlogEntryResource>(resource, HttpStatus.OK);
        } else {
            return new ResponseEntity<BlogEntryResource>(HttpStatus.NOT_FOUND);
        }
    }
}
