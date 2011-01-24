package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.model.News;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.List;

/**
 * Represents the model for the home page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class HomePageModel extends MGModel {

    private List<News> newsList;

    private List<Study> studies;

    public final static String MODEL_ATTR_NAME = "homePageModel";

    public HomePageModel(Submitter submitter, List<Study> studies, List<News> newsList) {
        super(submitter);
        this.studies = studies;
        this.newsList = newsList;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public List<Study> getStudies() {
        return studies;
    }

    public void setStudies(List<Study> studies) {
        this.studies = studies;
    }
}