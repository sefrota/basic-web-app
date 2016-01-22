package tutorial.rest.resources;

import org.springframework.hateoas.ResourceSupport;
import tutorial.core.entities.Blog;
import tutorial.core.services.util.BlogList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sletras on 21/01/2016.
 */
public class BlogListResource  extends ResourceSupport {
    private List<BlogResource> blogs = new ArrayList<BlogResource>();

    public List<BlogResource> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<BlogResource> blogs) {
        this.blogs = blogs;
    }
}
