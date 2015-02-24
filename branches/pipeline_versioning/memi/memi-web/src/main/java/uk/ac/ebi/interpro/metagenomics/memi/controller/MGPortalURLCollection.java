package uk.ac.ebi.interpro.metagenomics.memi.controller;

/**
 * Collection of available URLs.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
public final class MGPortalURLCollection {

    //Set of URLs for the analysis results
    public final static String PROJECT_SAMPLE_RUN_RESULTS = "/projects/{projectId}/samples/{sampleId}/runs/{runId}/results";

    private final static String PROJECT_SAMPLE_RUN_RESULTS_VERSION = "/versions/{releaseVersion:\\d\\.\\d}";

    public final static String PROJECT_SAMPLE_RUN_RESULTS_OVERVIEW = PROJECT_SAMPLE_RUN_RESULTS + "/overview" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_DOWNLOADS = PROJECT_SAMPLE_RUN_RESULTS + "/download" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_QUALITY_CONTROL = PROJECT_SAMPLE_RUN_RESULTS + "/qualityControl" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

    public final static String PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC = PROJECT_SAMPLE_RUN_RESULTS + "/taxonomic" + PROJECT_SAMPLE_RUN_RESULTS_VERSION;

}
