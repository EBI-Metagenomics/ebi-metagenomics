package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Model to hold statistics related to the analysis of a sample.
 * <p/>
 * TODO - revisit before beta release.
 *
 * @author Maxim Scheremetjew, Phil Jones
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class AnalysisStatsModel extends ViewModel {

    public static final NumberFormat NUMBER_FORMAT = DecimalFormat.getInstance();

    private Sample sample;

    // URLs to use the Google chart Tool

    /* URL should display a bar chart about a statistical overview */
    private String barChartURL;

    /* URL should display a pie chart which includes GO annotations for biological process */
    private String pieChartBiologicalProcessURL;

    /* URL should display a pie chart which includes GO annotations for molecular function */
    private String pieChartMolecularFunctionURL;

    /* URL should display a pie chart which includes GO annotations for cellular component */
    private String pieChartCellularComponentURL;

    private String pieChartInterProMatchURL;
    /* List of InterPro entries. Loaded from the MG pipeline produced file with the IPR extension
     (summary of InterPro matches).
     */
    private List<InterProEntry> interProEntries;

    private List<AbstractGOTerm> bioGOTerms;

    /* An EmgFile object holds two attributes of the */
    private EmgFile emgFile;

    private List<String> archivedSequences;

    AnalysisStatsModel(Submitter submitter, String pageTitle, List<Breadcrumb> breadcrumbs, Sample sample,
                       String barChartURL, String pieChartBiologicalProcessURL,
                       String pieChartCellularComponentURL, String pieChartMolecularFunctionURL,
                       String pieChartInterProMatchURL, List<AbstractGOTerm> bioGOTerms, EmgFile emgFile,
                       List<String> archivedSequences, MemiPropertyContainer propertyContainer,
                       List<InterProEntry> interProEntries) {
        super(submitter, pageTitle, breadcrumbs, propertyContainer);
        this.sample = sample;
        this.barChartURL = barChartURL;
        this.pieChartBiologicalProcessURL = pieChartBiologicalProcessURL;
        this.pieChartMolecularFunctionURL = pieChartMolecularFunctionURL;
        this.pieChartCellularComponentURL = pieChartCellularComponentURL;
        this.pieChartInterProMatchURL = pieChartInterProMatchURL;
        this.interProEntries = interProEntries;
        this.bioGOTerms = bioGOTerms;
        this.emgFile = emgFile;
        this.archivedSequences = archivedSequences;
    }

    AnalysisStatsModel(Submitter submitter, String pageTitle, List<Breadcrumb> breadcrumbs, Sample sample,
                       EmgFile emgFile, List<String> archivedSequences, MemiPropertyContainer propertyContainer) {
        this(submitter, pageTitle, breadcrumbs, sample, null, null, null, null, null, null, emgFile,
                archivedSequences, propertyContainer, null);
    }

    public Sample getSample() {
        return sample;
    }

    public String getBarChartURL() {
        return barChartURL;
    }

    public String getPieChartBiologicalProcessURL() {
        return pieChartBiologicalProcessURL;
    }

    public String getPieChartMolecularFunctionURL() {
        return pieChartMolecularFunctionURL;
    }

    public String getPieChartCellularComponentURL() {
        return pieChartCellularComponentURL;
    }

    public List<String> getArchivedSequences() {
        return archivedSequences;
    }

    public String getPieChartInterProMatchURL() {
        return pieChartInterProMatchURL;
    }

    public List<AbstractGOTerm> getBioGOTerms() {
        return bioGOTerms;
    }

    public EmgFile getEmgFile() {
        return emgFile;
    }

    public List<InterProEntry> getInterProEntries() {
        return interProEntries;
    }
}