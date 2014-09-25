package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

/**
 * Created with IntelliJ IDEA.
 * User: maxim
 * Date: 31/10/12
 * Time: 16:26
 * To change this template use File | Settings | File Templates.
 */
public enum ExternalResourceType {
    SRA("Sequence Read Archive");

    private String name;

    private ExternalResourceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
