package tutorial.rest.resources;

import org.springframework.hateoas.ResourceSupport;
import tutorial.core.entities.BlogEntry;
import tutorial.core.services.util.BlogEntryList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sletras on 21/01/2016.
 */
public class BlogEntryListResource extends ResourceSupport {
    private String title;
    private List<BlogEntryResource> entries = new ArrayList<BlogEntryResource>();

    public List<BlogEntryResource> getEntries() {
        return entries;
    }

    public void setEntries(List<BlogEntryResource> entries) {
        this.entries = entries;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
