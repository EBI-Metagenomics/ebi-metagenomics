package uk.ac.ebi.interpro.metagenomics.memi.feed;

import static org.junit.Assert.assertEquals;

import com.sun.syndication.feed.synd.SyndEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.UrlResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for {@link CachingRomeClient}.
 *
 * @author  Antony Quinn
 * @version $Id$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CachingRomeClientTest {

    @Resource
    RomeClient client;

    @Resource
    List<String> expectedTitles;

    @Test
    public void testGetEntries() {
        int limit = client.getLimit();
        List<SyndEntry> entries = client.getEntries();
        assertEquals("Expected number of entries not found", limit, entries.size());
        List<String> titles = new ArrayList<String>();
        for (SyndEntry entry : entries) {
            titles.add(entry.getTitle());
        }
        assertEquals("Expected title not found", expectedTitles, titles);
        // TODO: How can we test if caching works?
    }

    public static void main(String[] args) throws Exception {
        String url = "http://news.google.com/?output=rss";
        if (args.length > 0) {
            url = args[0];
        }
        int limit = 5;
        if (args.length > 1) {
            limit = Integer.valueOf(args[1]);
        }
        RomeClient client = new CachingRomeClient(new UrlResource(url), limit);
        List<SyndEntry> entries = client.getEntries();
        for (SyndEntry entry : entries) {
            System.out.println(entry.getTitle());
        }

    }      

}
