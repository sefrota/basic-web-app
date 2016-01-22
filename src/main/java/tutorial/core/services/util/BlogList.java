package tutorial.core.services.util;

import tutorial.core.entities.Blog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sletras on 21/01/2016.
 */
public class BlogList {
    private List<Blog> blogs = new ArrayList<Blog>();

    public BlogList(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }
}
