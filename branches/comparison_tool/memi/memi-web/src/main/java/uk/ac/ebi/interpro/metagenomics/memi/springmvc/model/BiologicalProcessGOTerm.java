package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

/**
 * TODO: Description
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class BiologicalProcessGOTerm extends AbstractGOTerm {
    public BiologicalProcessGOTerm(String accessionID, String synonym, int numberOfMatches) {
        super(accessionID, synonym, numberOfMatches,"Biological Process");
    }
}
