package uk.ac.ebi.interpro.metagenomics.memi.feed;

import com.googlecode.ehcache.annotations.Cacheable;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;

import org.springframework.core.io.Resource;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                String title = ((SyndEntry) o).getTitle();
                title = transformTwitterText(title);
                ((SyndEntry)o).setTitle(title);
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

    private final static String transformTwitterText(String text) {
        // Strip username off the front of the message
        String twitterPrefix = "EBImetagenomics: ";
        if (text.startsWith(twitterPrefix)) {
            text = text.substring(twitterPrefix.length());
        }

        // Find links contained within RSS feed text
        String regex = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        Set<String> urlSet = new HashSet<String>();
        while (matcher.find()) {
            urlSet.add(matcher.group());
        }

        // Now make the contained links linkable
        int maxUrlTextLength = 35;
        for (String url : urlSet) {
            String urlText;
            if (url.length() > maxUrlTextLength) {
                urlText = url.substring(0, maxUrlTextLength - 1) + "...";
            }
            else {
                urlText = url;
            }
            String replacedUrl = "<a href=\"" + url + "\">" + urlText + "</a>";
            text = text.replaceAll(url, replacedUrl);
        }

        return text;
    }

}
