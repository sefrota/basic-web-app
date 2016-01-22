package tutorial.mvc;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tutorial.core.entities.Account;
import tutorial.core.entities.Blog;
import tutorial.core.entities.BlogEntry;
import tutorial.core.services.AccountService;
import tutorial.core.services.BlogService;
import tutorial.core.services.exceptions.AccountDoesNotExistException;
import tutorial.core.services.exceptions.AccountExistsException;
import tutorial.core.services.exceptions.BlogDoesNotExistException;
import tutorial.core.services.exceptions.BlogExistsException;
import tutorial.core.services.util.BlogEntryList;
import tutorial.core.services.util.BlogList;
import tutorial.rest.mvc.AccountController;
import tutorial.rest.mvc.BlogController;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created by sletras on 20/01/2016.
 * Muito cuidado com os imports
 * endswith tem que ser do hamcrest.Matchers e não do mockito!!!!
 *
 */
public class AccountControllerTest {
    @InjectMocks
    private AccountController controller;

    @Mock
    private AccountService service;

    private MockMvc mockMvc;

    private ArgumentCaptor<Account> accountArgumentCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);
    }

    @Test
    public void createBlogExistingAccount() throws Exception {
        Blog createdBlog = new Blog();
        createdBlog.setId(1L);
        createdBlog.setTitle("Test Title");

        when(service.createBlog(eq(1L), any(Blog.class)))
                .thenReturn(createdBlog);

        mockMvc.perform(post("/rest/accounts/1/blogs")
                .content("{\"title\":\"Test Title\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", is("Test Title")))
                .andExpect(jsonPath("$.links[*].href",
                        hasItem(endsWith("/rest/blogs/1"))))
                .andExpect(header().string("Location", endsWith("rest/blogs/1")))
                .andExpect(status().isCreated());
    }

    @Test
    public void createBlogNonExistingAccount() throws Exception {

        when(service.createBlog(eq(1L), any(Blog.class)))
                .thenThrow(new AccountDoesNotExistException());

        mockMvc.perform(post("/rest/accounts/1/blogs")
                .content("{\"title\":\"Test Title\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createBlogExistingName() throws Exception {

        when(service.createBlog(eq(1L), any(Blog.class)))
                .thenThrow(new BlogExistsException());

        mockMvc.perform(post("/rest/accounts/1/blogs")
                .content("{\"title\":\"Test Title\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void createAccountNonExistingUsername() throws Exception{
        Account createdAccount = new Account();
        createdAccount.setId(1L);
        createdAccount.setName("test");
        createdAccount.setPassword("test");

        when(service.createAccount(any(Account.class)))
                .thenReturn(createdAccount);

        mockMvc.perform(post("/rest/accounts")
                .content("{\"name\":\"test\", \"password\":\"test\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location",
                        endsWith("/rest/accounts/1")))
                .andExpect(jsonPath("$.name", is(createdAccount.getName())))
                .andExpect(status().isCreated());

        //Permite saber quais os argumentos que foram passados no Mock
        verify(service).createAccount(accountArgumentCaptor.capture());
        String password = accountArgumentCaptor.getValue().getPassword();

        //Aqui vamos comparar se efectivamente foi esse o valor utilizado.
        Assert.assertEquals("test", password);
    }

    @Test
    public void createAccountExistingUsername() throws Exception{
        Account createdAccount = new Account();
        createdAccount.setId(1L);
        createdAccount.setName("test");
        createdAccount.setPassword("test");

        when(service.createAccount(any(Account.class)))
                .thenThrow(new AccountExistsException());

        mockMvc.perform(post("/rest/accounts")
                .content("{\"name\":\"test\", \"password\":\"test\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void getExistingAccount() throws Exception{
        Account foundAccount = new Account();
        foundAccount.setId(1L);
        foundAccount.setName("test");
        foundAccount.setPassword("test");

        when(service.findAccount(1L)).thenReturn(foundAccount);

        mockMvc.perform(get("/rest/accounts/1"))
                .andDo(print())
                .andExpect(jsonPath("$.password", isEmptyOrNullString()))
                .andExpect(jsonPath("$.name", is(foundAccount.getName())))
                .andExpect(status().isOk());
    }



    @Test
    public void getNonExistingAccount() throws Exception{
        when(service.findAccount(1L)).thenThrow(new AccountDoesNotExistException());

        mockMvc.perform(get("/rest/accounts/1"))
                .andExpect(status().isNotFound());
    }
}
