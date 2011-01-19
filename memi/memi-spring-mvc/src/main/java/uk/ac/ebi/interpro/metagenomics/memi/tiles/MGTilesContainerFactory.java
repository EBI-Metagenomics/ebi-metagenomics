package uk.ac.ebi.interpro.metagenomics.memi.tiles;

import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.context.TilesRequestContextFactory;
import org.apache.tiles.definition.DefinitionsFactoryException;
import org.apache.tiles.factory.BasicTilesContainerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Represents the configuration class for the Tiles framework.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class MGTilesContainerFactory extends BasicTilesContainerFactory {

    @Override
    protected List<URL> getSourceURLs(TilesApplicationContext applicationContext,
                                      TilesRequestContextFactory contextFactory) {
        List<URL> urls = new ArrayList<URL>();
        try {
            urls.add(applicationContext.getResource("/WEB-INF/tiles-defs.xml"));
        } catch (IOException e) {
            throw new DefinitionsFactoryException(
                    "Cannot load definition URLs", e);
        }
        return urls;
    }
}
