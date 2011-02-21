package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the model for the home page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class HomePageModel extends MGModel {

    private List<Study> publicStudies;

    private List<Sample> publicSamples;

    private List<Study> myStudies;

    private List<Sample> mySamples;

    public final static String MODEL_ATTR_NAME = "homePageModel";


    public HomePageModel(Submitter submitter, List<Study> publicStudies, List<Sample> publicSamples) {
        super(submitter);
        this.publicStudies = publicStudies;
        this.publicSamples = publicSamples;
        this.myStudies = new ArrayList<Study>();
        this.mySamples = new ArrayList<Sample>();
    }

    public HomePageModel(Submitter submitter, List<Study> publicStudies, List<Sample> publicSamples, List<Study> myStudies, List<Sample> mySamples) {
        this(submitter, publicStudies, publicSamples);
        this.myStudies = myStudies;
        this.mySamples = mySamples;
    }

    public List<Study> getPublicStudies() {
        return publicStudies;
    }

    public void setPublicStudies(List<Study> publicStudies) {
        this.publicStudies = publicStudies;
    }

    public List<Sample> getPublicSamples() {
        return publicSamples;
    }

    public void setPublicSamples(List<Sample> publicSamples) {
        this.publicSamples = publicSamples;
    }

    public List<Study> getMyStudies() {
        return myStudies;
    }

    public void setMyStudies(List<Study> myStudies) {
        this.myStudies = myStudies;
    }

    public List<Sample> getMySamples() {
        return mySamples;
    }

    public void setMySamples(List<Sample> mySamples) {
        this.mySamples = mySamples;
    }
}