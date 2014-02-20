package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

/**
 * TODO: Description
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class CellularComponentGOTerm extends AbstractGOTerm {
    public CellularComponentGOTerm(String accessionID, String synonym, int numberOfMatches) {
        super(accessionID, synonym, numberOfMatches,"Cellular Component");
    }
}
