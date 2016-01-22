package tutorial.core.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tutorial.core.entities.Blog;
import tutorial.core.entities.BlogEntry;
import tutorial.core.repositories.BlogEntryRepo;
import tutorial.core.repositories.BlogRepo;
import tutorial.core.services.BlogService;
import tutorial.core.services.exceptions.BlogDoesNotExistException;
import tutorial.core.services.util.BlogEntryList;
import tutorial.core.services.util.BlogList;

/**
 * Created by sletras on 22/01/2016.
 */
@Service
@Transactional
public class BlogServiceImpl implements BlogService {
    @Autowired
    BlogRepo blogRepo;
    @Autowired
    BlogEntryRepo blogEntryRepo;

    @Override
    public BlogEntry createBlogEntry(Long blogId, BlogEntry data) {
        Blog blog = blogRepo.findBlog(blogId);
        if(blog == null){
            throw new BlogDoesNotExistException();
        }
        BlogEntry entry = blogEntryRepo.createBlogEntry(data);
        entry.setBlog(blog);
        return entry;
    }

    @Override
    public Blog findBlog(Long id) {
        return blogRepo.findBlog(id);
    }

    @Override
    public BlogList findAllBlogs() {
        return new BlogList(blogRepo.findAllBlogs());
    }

    @Override
    public BlogEntryList findAllBlogEntries(Long blogId) {
        Blog blog = blogRepo.findBlog(blogId);
        if(blog == null){
            throw new BlogDoesNotExistException();
        }
        return new BlogEntryList(blogId, blogEntryRepo.findByBlogId(blogId));
    }
}
