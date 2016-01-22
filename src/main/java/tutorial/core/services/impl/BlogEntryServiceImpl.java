package tutorial.core.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tutorial.core.entities.BlogEntry;
import tutorial.core.repositories.BlogEntryRepo;
import tutorial.core.repositories.BlogRepo;
import tutorial.core.services.BlogEntryService;

/**
 * Created by sletras on 22/01/2016.
 */
@Service
@Transactional
public class BlogEntryServiceImpl implements BlogEntryService{
    @Autowired
    BlogEntryRepo blogEntryRepo;

    @Override
    public BlogEntry findBlogEntry(Long id) {
        return blogEntryRepo.findBlogEntry(id);
    }

    @Override
    public BlogEntry updateBlogEntry(Long id, BlogEntry data) {
        return blogEntryRepo.updateBlogEntry(id, data);
    }

    @Override
    public BlogEntry deleteBlogEntry(Long id) {
        return blogEntryRepo.deleteBlogEntry(id);
    }
}
