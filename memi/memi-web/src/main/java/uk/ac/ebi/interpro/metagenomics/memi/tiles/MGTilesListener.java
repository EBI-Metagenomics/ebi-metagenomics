package uk.ac.ebi.interpro.metagenomics.memi.tiles;

import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.factory.AbstractTilesContainerFactory;
import org.apache.tiles.startup.AbstractTilesInitializer;
import org.apache.tiles.startup.TilesInitializer;
import org.apache.tiles.web.startup.AbstractTilesListener;

/**
 * Represents the default listener for the Tiles framework.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class MGTilesListener extends AbstractTilesListener {

    @Override
    protected TilesInitializer createTilesInitializer() {
        return new TestTilesListenerInitializer();
    }

    private static class TestTilesListenerInitializer extends AbstractTilesInitializer {

        @Override
        protected AbstractTilesContainerFactory createContainerFactory(
                TilesApplicationContext context) {
            return new MGTilesContainerFactory();
        }
    }
}
