package tutorial.rest.resources.asm;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import tutorial.core.entities.Account;
import tutorial.core.entities.BlogEntry;
import tutorial.rest.mvc.AccountController;
import tutorial.rest.mvc.BlogEntryController;
import tutorial.rest.resources.AccountResource;
import tutorial.rest.resources.BlogEntryResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by sletras on 20/01/2016.
 * É a classe que faz a tradução entre a entidade interna Account e a entidade externa
 * AccountResource
 * 1º Parametro construtor é o controller associado à entidade
 * 2º Parametro é o resource externo
 */
public class AccountResourceAsm extends ResourceAssemblerSupport<Account, AccountResource> {

    public AccountResourceAsm(){
        super(AccountController.class, AccountResource.class);
    }

    @Override
    public AccountResource toResource(Account account) {
        AccountResource resource = new AccountResource();
        resource.setName(account.getName());
        resource.setPassword(account.getPassword());
        Link self = linkTo(AccountController.class)
                .slash(account.getId()).withSelfRel();
        resource.add(self);
        return resource;
    }
}
