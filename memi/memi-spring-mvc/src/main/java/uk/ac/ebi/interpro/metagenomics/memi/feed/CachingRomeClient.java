package uk.ac.ebi.interpro.metagenomics.memi.feed;

import com.googlecode.ehcache.annotations.Cacheable;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This implementation caches entries.
 *
 * @author  Antony Quinn
 * @version $Id$
 */
public final class CachingRomeClient implements RomeClient {

    // See ehcache.xml for details of caching parameters
    private final int limit;
    private final Resource feed;

    public CachingRomeClient(Resource feed, int limit) {
        this.feed  = feed;
        this.limit = limit;
    }

    @Override public int getLimit() {
        return limit;
    }

    @Override public Resource getFeed() {
        return feed;
    }

    @Override public List<SyndEntry> getEntries() {
        return getEntries(feed);
    }

    @Cacheable(cacheName="rssEntriesCache")
    @Override public List<SyndEntry> getEntries(Resource feed) {
        List<SyndEntry> entries = new ArrayList<SyndEntry>();
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed syndFeed;
        try {
            syndFeed = input.build(new InputSource(feed.getInputStream()));
        }
        catch (IOException e) {
            throw new IllegalStateException("Could not access feed: " + feed.getFilename(), e);
        }
        catch (FeedException e) {
            throw new IllegalStateException("Could not parse feed" + feed.getFilename(), e);
        }
        int count = 0;
        for (Object o : syndFeed.getEntries()) {
            if (count < limit) {
                entries.add((SyndEntry) o);                 
            }
            else {
                break;
            }
            count++;
        }
        // TODO: Create our own model for news, including entries and URL
        return entries;
    }  
    
}
