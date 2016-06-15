package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maq on 03/06/2016.
 */
public class EBISearchResults {

    String[] dataTypes = {"projects", "samples", "runs"};
    EBISampleSearchResults samples;
    EBIProjectSearchResults projects;
    EBIRunSearchResults runs;

    public EBISearchResults() {
        samples = new EBISampleSearchResults();
        projects = new EBIProjectSearchResults();
        runs = new EBIRunSearchResults();
    }

    public String[] getDataTypes() {
        return dataTypes;
    }

    public EBISampleSearchResults getSamples() {
        return samples;
    }

    public void setSamples(EBISampleSearchResults samples) {
        this.samples = samples;
    }

    public EBIProjectSearchResults getProjects() {
        return projects;
    }

    public void setProjects(EBIProjectSearchResults projects) {
        this.projects = projects;
    }

    public EBIRunSearchResults getRuns() {
        return runs;
    }

    public void setRuns(EBIRunSearchResults runs) {
        this.runs = runs;
    }
}
