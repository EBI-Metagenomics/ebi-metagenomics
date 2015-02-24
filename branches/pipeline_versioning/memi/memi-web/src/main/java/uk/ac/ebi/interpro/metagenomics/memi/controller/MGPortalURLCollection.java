package uk.ac.ebi.interpro.metagenomics.memi.controller;

/**
 * Collection of available URLs.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
public final class MGPortalURLCollection {

    //Set of URLs for the analysis result page

    //Entry page
    public final static String PROJECT_SAMPLE_RUN_RESULTS = "/projects/{projectId}/samples/{sampleId}/runs/{runId}/results";

    //Version handling
    private final static String PROJECT_SAMPLE_RUN_RESULTS_VERSION = "/versions/{releaseVersion:\\d\\.\\d}";

    //Overview tab
    public final static String PROJECT_SAMPLE_RUN_RESULTS_OVERVIEW = PROJECT_SAMPLE_RUN_RESULTS + "/overview" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    //Download tab
    public final static String PROJECT_SAMPLE_RUN_RESULTS_DOWNLOADS = PROJECT_SAMPLE_RUN_RESULTS + "/download" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    //QC tab
    public final static String PROJECT_SAMPLE_RUN_RESULTS_QUALITY_CONTROL = PROJECT_SAMPLE_RUN_RESULTS + "/qualityControl" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    //Taxonomy tab
    public final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC = PROJECT_SAMPLE_RUN_RESULTS + "/taxonomic" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_PIE_CHART = PROJECT_SAMPLE_RUN_RESULTS + "/taxPieChartView" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_BAR_CHART = PROJECT_SAMPLE_RUN_RESULTS + "/taxBarChartView" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_COLUMN_CHART = PROJECT_SAMPLE_RUN_RESULTS + "/taxColumnChartView" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_KRONA_VIEW = PROJECT_SAMPLE_RUN_RESULTS + "/kronaChartView" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_KRONA_CHART = PROJECT_SAMPLE_RUN_RESULTS + "/krona" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    //Functional tab
    public final static String PROJECT_SAMPLE_RUN_RESULTS_FUNCTIONAL = PROJECT_SAMPLE_RUN_RESULTS + "/functional" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_FUNCTIONAL_GO_BAR_CHART = PROJECT_SAMPLE_RUN_RESULTS + "/goBarChartView" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_FUNCTIONAL_GO_PIE_CHART = PROJECT_SAMPLE_RUN_RESULTS + "/goPieChartView" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    //File exports - functional results
    public final static String PROJECT_SAMPLE_RUN_RESULTS_FILE_EXPORT_GO_SLIM = PROJECT_SAMPLE_RUN_RESULTS + "/GOSlimFile" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_FILE_EXPORT_GO = PROJECT_SAMPLE_RUN_RESULTS + "/GOFile" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_FILE_EXPORT_I5_TSV = PROJECT_SAMPLE_RUN_RESULTS + "/I5TSVFile" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    //File exports - sequence files
    private final static String PROJECT_SAMPLE_RUN_RESULTS_SEQUENCES = PROJECT_SAMPLE_RUN_RESULTS + "/sequences" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_SEQUENCES_EXPORT = PROJECT_SAMPLE_RUN_RESULTS_SEQUENCES + "/export";

    //File export - taxonomy files
    private final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMY = PROJECT_SAMPLE_RUN_RESULTS + "/taxonomy" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMY_EXPORT = PROJECT_SAMPLE_RUN_RESULTS_TAXONOMY + "/export";

    //Sample page
    public final static String PROJECT_SAMPLE = "/projects/{projectId}/samples/{sampleId}";

}
