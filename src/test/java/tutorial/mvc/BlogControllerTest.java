package tutorial.mvc;

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
import tutorial.core.services.exceptions.BlogDoesNotExistException;
import tutorial.core.services.util.BlogEntryList;
import tutorial.core.entities.Blog;
import tutorial.core.entities.BlogEntry;
import tutorial.core.services.BlogService;
import tutorial.core.services.util.BlogList;
import tutorial.rest.mvc.BlogController;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



/**
 * Created by sletras on 20/01/2016.
 * Muito cuidado com os imports
 * endswith tem que ser do hamcrest.Matchers e não do mockito!!!!
 *
 */
public class BlogControllerTest {
    @InjectMocks
    private BlogController controller;

    @Mock
    private BlogService service;

    private MockMvc mockMvc;

    private ArgumentCaptor<BlogEntry> blogEntryArgumentCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        blogEntryArgumentCaptor = ArgumentCaptor.forClass(BlogEntry.class);
    }

    @Test
    public void findAllBlogs() throws Exception{
        List<Blog> list = new ArrayList<Blog>();

        Blog blogA = new Blog();
        blogA.setId(1L);
        blogA.setTitle("Title A");
        list.add(blogA);

        Blog blogB = new Blog();
        blogB.setId(2L);
        blogB.setTitle("Title B");
        list.add(blogB);

        BlogList allBlogs = new BlogList();
        allBlogs.setBlogs(list);

        when(service.findAllBlogs()).thenReturn(allBlogs);

        mockMvc.perform(get("/rest/blogs"))
                .andExpect(jsonPath("$.blogs[*].title",
                        hasItems(endsWith("Title A"), endsWith("Title B"))))
                .andExpect(status().isOk());
    }

    @Test
    public void getBlog() throws Exception{
        Blog blog = new Blog();
        blog.setId(1L);
        blog.setTitle("Test title");

        Account account = new Account();
        account.setId(1L);
        blog.setOwner(account);

        when(service.findBlog(1L)).thenReturn(blog);

        mockMvc.perform(get("/rest/blogs/1"))
                .andDo(print())
                .andExpect(jsonPath("$.title", is(blog.getTitle())))
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/blogs/1"))))
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/accounts/1"))))
                //.andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/blogs/1/entries"))))
                .andExpect(jsonPath("$.links[*].rel",
                        hasItems(
                                is("self"),
                                is("owner")
                                //is("entries")
                        )))
                .andExpect(status().isOk());
    }

    @Test
    public void createBlogEntryExistingBlog() throws Exception {
        Blog blog = new Blog();
        blog.setId(1L);

        BlogEntry entry = new BlogEntry();
        entry.setTitle("Test Title");
        entry.setId(1L);

        when(service.createBlogEntry(eq(1L), any(BlogEntry.class))).thenReturn(entry);


        mockMvc.perform(post("/rest/blogs/1/blog-entries")
                .content("{\"title\":\"Test Title\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", is(entry.getTitle())))
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("rest/blog-entries/1"))))
                .andExpect(header().string("Location", endsWith("rest/blog-entries/1")))
                .andExpect(status().isCreated());

        //Permite saber quais os argumentos que foram passados no Mock
        verify(service).createBlogEntry(any(Long.class), blogEntryArgumentCaptor.capture());
        Long id = blogEntryArgumentCaptor.getValue().getId();

    }


    @Test
    public void createBlogEntryNonExistingBlog() throws Exception {

        when(service.createBlogEntry(eq(1L), any(BlogEntry.class)))
                .thenThrow(new BlogDoesNotExistException());

        mockMvc.perform(post("/rest/blogs/1/blog-entries")
                .content("{\"title\":\"Generic Title\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void listBlogEntriesForExistingBlog() throws Exception{

        BlogEntry entryA = new BlogEntry();
        entryA.setId(1L);
        entryA.setTitle("Entry A");

        BlogEntry entryB = new BlogEntry();
        entryB.setId(2L);
        entryB.setTitle("Entry B");

        List<BlogEntry> blogListings = new ArrayList<BlogEntry>();
        blogListings.add(entryA);
        blogListings.add(entryB);

        BlogEntryList list = new BlogEntryList();
        list.setEntries(blogListings);
        list.setBlogId(1L);

        when(service.findAllBlogEntries(1L)).thenReturn(list);

        mockMvc.perform(get("/rest/blogs/1/blog-entries"))
                .andDo(print())
                .andExpect(jsonPath("$.links[*].href",
                        hasItem(endsWith("/blogs/1/blog-entries"))))
                .andExpect(jsonPath("$.entries[*].title",
                        hasItems(is("Entry A"), is("Entry B"))))
                .andExpect(status().isOk());
    }

    @Test
    public void listBlogEntriesForNonExistingBlog() throws Exception{

        when(service.findAllBlogEntries(1L)).thenThrow(new BlogDoesNotExistException());

        mockMvc.perform(get("/rest/blogs/1/blog-entries"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
