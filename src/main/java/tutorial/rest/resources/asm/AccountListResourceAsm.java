package tutorial.rest.resources.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import tutorial.core.services.util.AccountList;
import tutorial.core.services.util.BlogList;
import tutorial.rest.mvc.AccountController;
import tutorial.rest.mvc.BlogController;
import tutorial.rest.resources.AccountListResource;
import tutorial.rest.resources.BlogListResource;

/**
 * Created by sletras on 20/01/2016.
 * É a classe que faz a tradução entre a entidade interna BlogList e a entidade externa
 * BlogListResource
 * 1º Parametro construtor é o controller associado à entidade
 * 2º Parametro é o resource externo
 */
public class AccountListResourceAsm extends ResourceAssemblerSupport<AccountList, AccountListResource> {

    public AccountListResourceAsm(){
        super(AccountController.class, AccountListResource.class);
    }

    @Override
    public AccountListResource toResource(AccountList accountList) {
        AccountListResource accountListResource = new AccountListResource();
        accountListResource.setAccounts(new AccountResourceAsm().toResources(accountList.getAccounts()));
        return accountListResource;
    }
}
