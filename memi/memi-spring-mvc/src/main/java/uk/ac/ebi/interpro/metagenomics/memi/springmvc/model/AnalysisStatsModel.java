package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.googlechart.GoogleChartFactory;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Model to hold statistics related to the analysis of a sample.
 * <p/>
 * TODO - revisit before beta release.
 *
 * @author Maxim Scheremetjew, Phil Jones
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class AnalysisStatsModel extends MGModel {

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

    private List<AbstractGOTerm> bioGOTerms;

    private EmgFile emgFile;

    AnalysisStatsModel(Submitter submitter, String pageTitle, List<Breadcrumb> breadcrumbs, Sample sample,
                       String barChartURL, String pieChartBiologicalProcessURL,
                       String pieChartCellularComponentURL, String pieChartMolecularFunctionURL,
                       String pieChartInterProMatchURL, List<AbstractGOTerm> bioGOTerms, EmgFile emgFile) {
        super(submitter, pageTitle, breadcrumbs);
        this.sample = sample;
        this.barChartURL = barChartURL;
        this.pieChartBiologicalProcessURL = pieChartBiologicalProcessURL;
        this.pieChartMolecularFunctionURL = pieChartMolecularFunctionURL;
        this.pieChartCellularComponentURL = pieChartCellularComponentURL;
        this.pieChartInterProMatchURL = pieChartInterProMatchURL;
        this.bioGOTerms = bioGOTerms;
        this.emgFile = emgFile;
    }

    private String getTempChart() {
        List<Float> chartData = new ArrayList<Float>();
        chartData.add(4.8235593f);
        chartData.add(4.747398f);
        chartData.add(4.468139f);
        chartData.add(4.391978f);

        List<String> chartLabels = new ArrayList<String>();
        chartLabels.add("GO:0003824 catalytic activity (190)");
        chartLabels.add("GO:0006412 translation (187)");
        chartLabels.add("GO:0008152 metabolic process (176)");
        chartLabels.add("GO:0003735 structural constituent of (173)");

        Properties props = new Properties();
        props.put(GoogleChartFactory.CHART_COLOUR, "FFCC33|7637A2");
        props.put(GoogleChartFactory.CHART_MARGIN, "270,270");
        props.put(GoogleChartFactory.CHART_SIZE, "740x180");

        return GoogleChartFactory.buildPieChartURL(props, chartData, chartLabels);
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

    public String getPieChartInterProMatchURL() {
//        return pieChartInterProMatchURL;
        return getTempChart();
    }

    public List<AbstractGOTerm> getBioGOTerms() {
        return bioGOTerms;
    }

    public EmgFile getEmgFile() {
        return emgFile;
    }
}