package tutorial.rest.resources;

import org.springframework.hateoas.ResourceSupport;
import tutorial.core.entities.Account;
import tutorial.core.entities.Blog;

/**
 * Created by sletras on 21/01/2016.
 */
public class BlogResource  extends ResourceSupport {

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Blog toBlog(){
        Blog blog = new Blog();
        blog.setTitle(title);

        return blog;
    }
}
