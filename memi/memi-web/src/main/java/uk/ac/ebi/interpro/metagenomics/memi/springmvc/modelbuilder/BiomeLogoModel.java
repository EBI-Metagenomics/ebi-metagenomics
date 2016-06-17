package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

/**
 * Created by gsalazar on 14/06/2016.
 */
public class BiomeLogoModel {
    private String label;
    private String lineage;
    private String css;
    private long numberOfProjects;

    public BiomeLogoModel(String label, String lineage, String css, long numberOfProjects) {
        this.label = label;
        this.lineage = lineage;
        this.css = css;
        this.numberOfProjects = numberOfProjects;
    }

    public long getNumberOfProjects() {
        return numberOfProjects;
    }

    public void setNumberOfProjects(long numberOfProjects) {
        this.numberOfProjects = numberOfProjects;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLineage() {
        return lineage;
    }

    public void setLineage(String lineage) {
        this.lineage = lineage;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

}
