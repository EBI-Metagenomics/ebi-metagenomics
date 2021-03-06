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

    //Analysis result page - Stats tab
    public final static String PROJECT_SAMPLE_RUN_RESULTS_STATS = PROJECT_SAMPLE_RUN_RESULTS + "/stats" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    //Analysis result page - Taxonomy tab
    public final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC = PROJECT_SAMPLE_RUN_RESULTS + "/taxonomic" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    //Analysis result page - Taxonomy tab ssu
    public final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_SSU = PROJECT_SAMPLE_RUN_RESULTS + "/taxonomic/ssu/" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    //Analysis result page - Taxonomy tab lsu
    public final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_LSU = PROJECT_SAMPLE_RUN_RESULTS + "/taxonomic/lsu/" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    //Set of URLs to allow Ajax request to render the different charts of the taxonomy results
    //START
    public final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_PIE_CHART = PROJECT_SAMPLE_RUN_RESULTS + "/taxPieChartView/{rRNAType}" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_BAR_CHART = PROJECT_SAMPLE_RUN_RESULTS + "/taxBarChartView/{rRNAType}" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_COLUMN_CHART = PROJECT_SAMPLE_RUN_RESULTS + "/taxColumnChartView/{rRNAType}" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_KRONA_VIEW = PROJECT_SAMPLE_RUN_RESULTS + "/kronaChartView/{rRNAType}" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_KRONA_CHART = PROJECT_SAMPLE_RUN_RESULTS + "/krona" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;
    //END taxonomy results

    //Analysis result page - Functional tab
    public final static String PROJECT_SAMPLE_RUN_RESULTS_FUNCTIONAL = PROJECT_SAMPLE_RUN_RESULTS + "/functional" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    //Set of URLs to allow Ajax request to render the different charts of the functional results
    //START
    public final static String PROJECT_SAMPLE_RUN_RESULTS_FUNCTIONAL_GO_BAR_CHART = PROJECT_SAMPLE_RUN_RESULTS + "/goBarChartView" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_FUNCTIONAL_GO_PIE_CHART = PROJECT_SAMPLE_RUN_RESULTS + "/goPieChartView" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;
    //END functional results

    //Analysis result page - stats tab
    //START
    public final static String PROJECT_SAMPLE_RUN_RESULTS_STATS_FILES = PROJECT_SAMPLE_RUN_RESULTS + PROJECT_SAMPLE_RUN_RESULTS_VERSION + "/stats/{svgFile}";
    //END stats tab

    //Set of URLs for download/export functionality
    //START
    //Functional results files (InterProScan and GO annotation results)
    public final static String PROJECT_SAMPLE_RUN_RESULTS_FILE_EXPORT_GO_SLIM = PROJECT_SAMPLE_RUN_RESULTS + PROJECT_SAMPLE_RUN_RESULTS_VERSION + "/function/GOSlimAnnotations";

    public final static String PROJECT_SAMPLE_RUN_RESULTS_FILE_EXPORT_GO = PROJECT_SAMPLE_RUN_RESULTS + PROJECT_SAMPLE_RUN_RESULTS_VERSION + "/function/GOAnnotations";

    public final static String PROJECT_SAMPLE_RUN_RESULTS_FILE_EXPORT_I5_TSV = PROJECT_SAMPLE_RUN_RESULTS + "/I5TSVFile" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_FUNCTION_INTERPROSCAN_CHUNKS = PROJECT_SAMPLE_RUN_RESULTS + PROJECT_SAMPLE_RUN_RESULTS_VERSION + "/function/InterProScan/chunks";

    public final static String PROJECT_SAMPLE_RUN_RESULTS_FUNCTION_INTERPROSCAN_CHUNKS_VALUE = PROJECT_SAMPLE_RUN_RESULTS + PROJECT_SAMPLE_RUN_RESULTS_VERSION + "/function/InterProScan/chunks/{chunkValue}";

    //Unchunked sequence files
    public final static String PROJECT_SAMPLE_RUN_RESULTS_SEQUENCES_EXPORT = PROJECT_SAMPLE_RUN_RESULTS + PROJECT_SAMPLE_RUN_RESULTS_VERSION + "/sequences/export";


    //Chunked sequence files (seq input files, pre-processed/filtered files)
    public final static String PROJECT_SAMPLE_RUN_RESULTS_SEQUENCES_SEQ_TYPE_CHUNKS = PROJECT_SAMPLE_RUN_RESULTS + PROJECT_SAMPLE_RUN_RESULTS_VERSION + "/sequences/{sequenceType}/chunks";
    public final static String PROJECT_SAMPLE_RUN_RESULTS_SEQUENCES_SEQ_TYPE_CHUNKS_VALUE = PROJECT_SAMPLE_RUN_RESULTS + PROJECT_SAMPLE_RUN_RESULTS_VERSION + "/sequences/{sequenceType}/chunks/{chunkValue}";

    //Taxonomy result files
    public final static String PROJECT_SAMPLE_RUN_RESULTS_SEQUENCES_SEQ_TYPE = PROJECT_SAMPLE_RUN_RESULTS + PROJECT_SAMPLE_RUN_RESULTS_VERSION + "/sequences/{resultType}";

    //Taxonomy result files
    public final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMY_TYPE = PROJECT_SAMPLE_RUN_RESULTS + PROJECT_SAMPLE_RUN_RESULTS_VERSION + "/taxonomy/{resultType}";

    //END export URLs

    //Sample page
    public final static String PROJECT_SAMPLE = "/projects/{projectId}/samples/{sampleId}";

    //Project page
    public final static String PROJECT = "/projects/{studyId}";

    //Project page, overview tab
    public final static String PROJECT_OVERVIEW = PROJECT + "/overview";

    //Project page, download tab
    public final static String PROJECT_DOWNLOAD = PROJECT + "/download";

    //Project page, download tab
    public final static String PROJECT_DOWNLOAD_PCA = PROJECT_DOWNLOAD + "/{releaseVersion}/pca";

    //Google map data
    public final static String PROJECT_MAP_DATA = PROJECT + "/map-data";

    //List of all project runs
    public final static String PROJECT_RUNS = PROJECT + "/runs";

    //Project page - export functionality
    public final static String PROJECT_SUMMARY_EXPORT = PROJECT_DOWNLOAD + "/{releaseVersion}/export";

    //Google image export function (for PNG and SVG file exports) on the taxonomy and functional tab
    //POST request
    public final static String PROJECT_SAMPLE_RUN_RESULTS_IMAGE_EXPORT = PROJECT_SAMPLE_RUN_RESULTS_ENTRY + "/export";


    //Quality control stats files
    public final static String PROJECT_SAMPLE_RUN_RESULTS_QC_TYPE = PROJECT_SAMPLE_RUN_RESULTS + PROJECT_SAMPLE_RUN_RESULTS_VERSION + "/qc-stats/{resultType}";
}