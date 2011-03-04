package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Represents the model for the home page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class HomePageModel extends MGModel {

//    private List<Study> publicStudies;

    /**
     * Maps studies and their number of samples.
     */
    private SortedMap<Study, Long> publicStudiesMap;

    private List<Sample> publicSamples;

//    private List<Study> myStudies;

    /**
     * Maps studies and their number of samples.
     */
    private SortedMap<Study, Long> myStudiesMap;

    private List<Sample> mySamples;

    HomePageModel(Submitter submitter, SortedMap<Study, Long> publicStudiesMap, List<Sample> publicSamples) {
        super(submitter);
        this.publicStudiesMap = publicStudiesMap;
        this.publicSamples = publicSamples;
        this.myStudiesMap = new TreeMap<Study, Long>();
        this.mySamples = new ArrayList<Sample>();
    }

    HomePageModel(Submitter submitter, SortedMap<Study, Long> publicStudiesMap, List<Sample> publicSamples, SortedMap<Study, Long> myStudiesMap, List<Sample> mySamples) {
        this(submitter, publicStudiesMap, publicSamples);
        this.myStudiesMap = myStudiesMap;
        this.mySamples = mySamples;
    }

    public SortedMap<Study, Long> getPublicStudiesMap() {
        return publicStudiesMap;
    }

    public void setPublicStudiesMap(SortedMap<Study, Long> publicStudiesMap) {
        this.publicStudiesMap = publicStudiesMap;
    }

    public SortedMap<Study, Long> getMyStudiesMap() {
        return myStudiesMap;
    }

    public void setMyStudiesMap(SortedMap<Study, Long> myStudiesMap) {
        this.myStudiesMap = myStudiesMap;
    }

    public List<Sample> getPublicSamples() {
        return publicSamples;
    }

    public void setPublicSamples(List<Sample> publicSamples) {
        this.publicSamples = publicSamples;
    }


    public List<Sample> getMySamples() {
        return mySamples;
    }

    public void setMySamples(List<Sample> mySamples) {
        this.mySamples = mySamples;
    }
}
