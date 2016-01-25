package tutorial.rest.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tutorial.core.entities.Account;
import tutorial.core.entities.Blog;
import tutorial.core.entities.BlogEntry;
import tutorial.core.services.AccountService;
import tutorial.core.services.BlogEntryService;
import tutorial.core.services.exceptions.AccountDoesNotExistException;
import tutorial.core.services.exceptions.AccountExistsException;
import tutorial.core.services.exceptions.BlogExistsException;
import tutorial.core.services.util.AccountList;
import tutorial.core.services.util.BlogList;
import tutorial.rest.exceptions.BadRequestException;
import tutorial.rest.exceptions.ConflictException;
import tutorial.rest.exceptions.NotFoundException;
import tutorial.rest.resources.*;
import tutorial.rest.resources.asm.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
//import tutorial.entities.BlogEntry;

/**
 * Created by sletras on 20/01/2016.
 */
@Controller
@RequestMapping(value="/rest/accounts")
public class AccountController {
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

    private AccountService accountService;
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
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
    public ResponseEntity<AccountListResource> findAllAccounts(@RequestParam(required = false) String name){
        AccountList accountList = null;
        if(name == null){
            accountList = accountService.findAllAccounts();
        }else{
            Account account = accountService.findByAccountName(name);
            if(account == null){
                accountList = new AccountList(new ArrayList<Account>());
            }else{
                accountList = new AccountList(Arrays.asList(account));
            }
        }
        return new ResponseEntity<AccountListResource>
                (new AccountListResourceAsm().toResource(accountList),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AccountResource> create(@RequestBody AccountResource sentAccount) {
        try {
            Account createdAccount = accountService.createAccount(sentAccount.toAccount());
            AccountResource accountResource = new AccountResourceAsm().toResource(createdAccount);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(accountResource.getLink("self").getHref()));
            return new ResponseEntity<AccountResource>(accountResource, headers, HttpStatus.CREATED);
        } catch (AccountExistsException e) {
            throw new ConflictException(e);
        }
    }

    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<AccountResource> getAccount(@PathVariable Long accountId) {
        try {
            Account account = accountService.findAccount(accountId);
            if (null != account) {
                AccountResource resource = new AccountResourceAsm().toResource(account);
                return new ResponseEntity<AccountResource>(resource, HttpStatus.OK);
            } else {
                return new ResponseEntity<AccountResource>(HttpStatus.NOT_FOUND);
            }
        } catch (AccountDoesNotExistException e) {
            throw new NotFoundException(e);
        }
    }

    @RequestMapping(value = "/{accountId}/blogs", method = RequestMethod.POST)
    public ResponseEntity<BlogResource> createBlog(@PathVariable Long accountId,
                                                        @RequestBody BlogResource blogResource) {
        try {
            System.out.print("Estou aqui!");
            //Esta parte lida com a gravação do novo blog na BD.
            Blog createdBlog = accountService.createBlog(accountId, blogResource.toBlog());
            System.out.print("Tou aqui");
            //Posteriormente vai ser gerado o objecto a passar para o exterior
            BlogResource createdBlogResource = new BlogResourceAsm().toResource(createdBlog);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(createdBlogResource.getLink("self").getHref()));
            return new ResponseEntity<BlogResource>(createdBlogResource, headers, HttpStatus.CREATED);
        } catch (AccountDoesNotExistException e) {
            throw new BadRequestException(e);
        } catch (BlogExistsException e) {
            throw new ConflictException(e);
        }
    }

    @RequestMapping(value ="/{accountId}/blogs", method = RequestMethod.GET)
    public ResponseEntity<BlogListResource> findAllBlogs(@PathVariable Long accountId){
        try {
            BlogList blogs = accountService.findBlogsByAccount(accountId);
            BlogListResource blogsRes = new BlogListResourceAsm().toResource(blogs);
            return new ResponseEntity<BlogListResource>(blogsRes, HttpStatus.OK);
        } catch (AccountDoesNotExistException e) {
            throw new NotFoundException(e);
        }
    }
}
