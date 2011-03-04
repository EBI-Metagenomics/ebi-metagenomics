package uk.ac.ebi.interpro.metagenomics.memi.feed;

import com.sun.syndication.feed.synd.SyndEntry;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * Wrapper for the <a href="http://java.net/projects/rome/">Rome RSS client</a>
 *
 * For further details see http://www.ebi.ac.uk/panda/jira/browse/IBU-1300
 *
 * @author  Antony Quinn
 * @version $Id$
 */
public interface RomeClient {

    /**
     * Returns the maximum number of feed entries.
     *
     * @return Maximum number of feed entries.
     */
    int getLimit();

    /**
     * Returns the feed resource.
     *
     * @return Feed resource
     */
    Resource getFeed();

    /**
     * Returns list of feed entries.
     *
     * @return list of feed entries.
     */
    List<SyndEntry> getEntries();

    /**
     * Returns list of feed entries.
     *
     * @param  feed Feed source
     * @return list of feed entries.
     */
    List<SyndEntry> getEntries(Resource feed);

}
