package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

/**
 * TODO: Description
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public abstract class AbstractGOTerm {
    private String accessionID;

    private String synonym;

    private int numberOfMatches;

    private String ontology;

    AbstractGOTerm(String accessionID, String synonym, int numberOfMatches, String ontology) {
        this.accessionID = accessionID;
        this.synonym = synonym;
        this.numberOfMatches = numberOfMatches;
        this.ontology = ontology;
    }

    public String getAccessionID() {
        return accessionID;
    }

    public String getSynonym() {
        return synonym;
    }

    public int getNumberOfMatches() {
        return numberOfMatches;
    }

    public String getOntology() {
        return ontology;
    }

    @Override
    public String toString() {
//        GO:0003824 catalytic activity (190)
        return synonym + " (" + numberOfMatches + ")";
    }
}
