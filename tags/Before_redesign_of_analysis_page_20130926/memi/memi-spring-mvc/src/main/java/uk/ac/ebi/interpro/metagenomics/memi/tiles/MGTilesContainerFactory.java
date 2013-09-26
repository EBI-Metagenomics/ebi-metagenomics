package uk.ac.ebi.interpro.metagenomics.memi.tiles;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.context.TilesRequestContextFactory;
import org.apache.tiles.definition.DefinitionsFactoryException;
import org.apache.tiles.factory.BasicTilesContainerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the configuration class for the Tiles framework.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class MGTilesContainerFactory extends BasicTilesContainerFactory {

    private final static Log log = LogFactory.getLog(MGTilesContainerFactory.class);

    @Override
    protected List<URL> getSourceURLs(TilesApplicationContext applicationContext,
                                      TilesRequestContextFactory contextFactory) {
        log.info("Getting Tiles definition sources...");
        List<URL> urls = new ArrayList<URL>();
        try {
            // TODO - Works OK, but seems to be the wrong path?
            urls.add(applicationContext.getResource("/WEB-INF/tiles-defs.xml"));
        } catch (IOException e) {
            log.warn("Cannot load definition URLs!", new DefinitionsFactoryException(
                    "Cannot load definition URLs", e));
        }
        return urls;
    }
}
