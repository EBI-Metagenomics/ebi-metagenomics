package uk.ac.ebi.interpro.metagenomics.memi.controller;

/**
 * Collection of available URLs.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
public final class MGPortalURLCollection {

    //Set of URLs for the analysis result page

    //Root URL
    public final static String PROJECT_SAMPLE_RUN_RESULTS = "/projects/{projectId}/samples/{sampleId}/runs/{runId}/results";

    //The MGPipeline releases new versions form time to time. Different pipeline versions can produce different result files.
    //The web application can handle and render the different result versions
    private final static String PROJECT_SAMPLE_RUN_RESULTS_VERSION = "/versions/{releaseVersion:\\d\\.\\d}";

    //URL to access the result entry page
    public final static String PROJECT_SAMPLE_RUN_RESULTS_ENTRY = PROJECT_SAMPLE_RUN_RESULTS + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    //Analysis result page - Overview tab
    public final static String PROJECT_SAMPLE_RUN_RESULTS_OVERVIEW = PROJECT_SAMPLE_RUN_RESULTS + "/overview" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    //Analysis result page - Download tab
    public final static String PROJECT_SAMPLE_RUN_RESULTS_DOWNLOADS = PROJECT_SAMPLE_RUN_RESULTS + "/download" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    //Analysis result page - Quality control tab
    public final static String PROJECT_SAMPLE_RUN_RESULTS_QUALITY_CONTROL = PROJECT_SAMPLE_RUN_RESULTS + "/qualityControl" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    //Analysis result page - Taxonomy tab
    public final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC = PROJECT_SAMPLE_RUN_RESULTS + "/taxonomic" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    //Set of URLs to allow Ajax request to render the different charts of the taxonomy results
    //START
    public final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_PIE_CHART = PROJECT_SAMPLE_RUN_RESULTS + "/taxPieChartView" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_BAR_CHART = PROJECT_SAMPLE_RUN_RESULTS + "/taxBarChartView" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_COLUMN_CHART = PROJECT_SAMPLE_RUN_RESULTS + "/taxColumnChartView" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_KRONA_VIEW = PROJECT_SAMPLE_RUN_RESULTS + "/kronaChartView" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_KRONA_CHART = PROJECT_SAMPLE_RUN_RESULTS + "/krona" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;
    //END taxonomy results

    //Analysis result page - Functional tab
    public final static String PROJECT_SAMPLE_RUN_RESULTS_FUNCTIONAL = PROJECT_SAMPLE_RUN_RESULTS + "/functional" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    //Set of URLs to allow Ajax request to render the different charts of the functional results
    //START
    public final static String PROJECT_SAMPLE_RUN_RESULTS_FUNCTIONAL_GO_BAR_CHART = PROJECT_SAMPLE_RUN_RESULTS + "/goBarChartView" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_FUNCTIONAL_GO_PIE_CHART = PROJECT_SAMPLE_RUN_RESULTS + "/goPieChartView" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;
    //END functional results

    //Set of URLs for download/export functionality
    //START
    //Functional results files (InterProScan and GO annotation results)
    public final static String PROJECT_SAMPLE_RUN_RESULTS_FILE_EXPORT_GO_SLIM = PROJECT_SAMPLE_RUN_RESULTS + "/GOSlimFile" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_FILE_EXPORT_GO = PROJECT_SAMPLE_RUN_RESULTS + "/GOFile" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_FILE_EXPORT_I5_TSV = PROJECT_SAMPLE_RUN_RESULTS + "/I5TSVFile" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_FUNCTION_INTERPROSCAN_CHUNKS = PROJECT_SAMPLE_RUN_RESULTS + PROJECT_SAMPLE_RUN_RESULTS_VERSION + "/function/InterProScan/chunks";

    public final static String PROJECT_SAMPLE_RUN_RESULTS_FUNCTION_INTERPROSCAN_CHUNKS_VALUE = PROJECT_SAMPLE_RUN_RESULTS + PROJECT_SAMPLE_RUN_RESULTS_VERSION + "/function/InterProScan/chunks/{chunkValue}";


    //Sequence files (seq input files, pre-processed/filtered files)
    private final static String PROJECT_SAMPLE_RUN_RESULTS_SEQUENCES = PROJECT_SAMPLE_RUN_RESULTS + "/sequences" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_SEQUENCES_EXPORT = PROJECT_SAMPLE_RUN_RESULTS_SEQUENCES + "/export";

    //Taxonomy result files
    private final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMY = PROJECT_SAMPLE_RUN_RESULTS + "/taxonomy" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMY_EXPORT = PROJECT_SAMPLE_RUN_RESULTS_TAXONOMY + "/export";
    //END export URLs

    //Sample page
    public final static String PROJECT_SAMPLE = "/projects/{projectId}/samples/{sampleId}";

    //Project page
    public final static String PROJECT = "/projects/{studyId}";

    //Project page - export functionality
    public final static String PROJECT_EXPORT = "/projects/{studyId}/export";

    //Google image export function (for PNG and SVG file exports) on the taxonomy and functional tab
    //POST request
    public final static String PROJECT_SAMPLE_RUN_RESULTS_IMAGE_EXPORT = PROJECT_SAMPLE_RUN_RESULTS_ENTRY + "/export";

}