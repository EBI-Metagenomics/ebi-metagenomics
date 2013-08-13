package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.core.comparators.PublicationComparator;
import uk.ac.ebi.interpro.metagenomics.memi.googlechart.GoogleChartFactory;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSampleAnnotation;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.HostSample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Publication;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.PublicationType;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileExistenceChecker;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.tabActivation.FunctionalAnalysisTab;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.tabActivation.TaxonomicAnalysisTab;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Model builder class for {@link SampleViewModel}.
 * <p/>
 * See {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder} for more information of how to use.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class SampleViewModelBuilder extends AbstractViewModelBuilder<SampleViewModel> {

    private final static Log log = LogFactory.getLog(SampleViewModelBuilder.class);

    private String pageTitle;

    private List<Breadcrumb> breadcrumbs;

    private MemiPropertyContainer propertyContainer;

    private Sample sample;

    private List<EmgSampleAnnotation> sampleAnnotations;

    /* An EmgFile object holds two attributes of the */
    private EmgFile emgFile;

    private SampleViewModel.ExperimentType experimentType;

    private DownloadSection downloadSection;

    private final List<String> archivedSequences;

    private final String resultFilesDirectoryPath;

    private List<Publication> relatedLinks;

    private List<Publication> relatedPublications;

    private List<ResultFileDefinitionImpl> qualityControlFileDefinitions;

    private List<FunctionalAnalysisFileDefinition> functionalAnalysisFileDefinitions;

    private List<ResultFileDefinitionImpl> taxonomicAnalysisFileDefinitions;


    public SampleViewModelBuilder(SessionManager sessionMgr,
                                  Sample sample,
                                  String pageTitle,
                                  List<Breadcrumb> breadcrumbs,
                                  EmgFile emgFile,
                                  List<String> archivedSequences,
                                  MemiPropertyContainer propertyContainer,
                                  SampleViewModel.ExperimentType experimentType,
                                  final DownloadSection downloadSection,
                                  List<EmgSampleAnnotation> sampleAnnotations,
                                  List<ResultFileDefinitionImpl> qualityControlFileDefinitions,
                                  List<FunctionalAnalysisFileDefinition> functionalAnalysisFileDefinitions,
                                  List<ResultFileDefinitionImpl> taxonomicAnalysisFileDefinitions) {
        super(sessionMgr);
        this.sample = sample;
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.emgFile = emgFile;
        this.archivedSequences = archivedSequences;
        this.propertyContainer = propertyContainer;
        this.experimentType = experimentType;
        this.downloadSection = downloadSection;
        this.sampleAnnotations = sampleAnnotations;
        this.qualityControlFileDefinitions = qualityControlFileDefinitions;
        this.functionalAnalysisFileDefinitions = functionalAnalysisFileDefinitions;
        this.taxonomicAnalysisFileDefinitions = taxonomicAnalysisFileDefinitions;
        //
        this.relatedLinks = new ArrayList<Publication>();
        this.relatedPublications = new ArrayList<Publication>();
        //
        final String directoryName = emgFile.getFileIDInUpperCase().replace('.', '_');
        this.resultFilesDirectoryPath = propertyContainer.getPathToAnalysisDirectory() + directoryName;
    }

    @Override
    public SampleViewModel getModel() {
        log.info("Building instance of " + SampleViewModel.class + "...");
        final List<InterProEntry> interProEntries = getListOfInterProEntries(emgFile);
        final boolean isHostAssociated = isHostAssociated();
        final Submitter submitter = getSessionSubmitter(sessionMgr);

        buildPublicationLists();

        //Get analysis status
        AnalysisStatus analysisStatus = getAnalysisStatus((sample.getAnalysisCompleted() == null ? false : true));


        if (emgFile != null) {
            //Get GO results
            final Map<Class, List<AbstractGOTerm>> goData = loadGODataFromCSV(propertyContainer.getPathToAnalysisDirectory(),
                    emgFile);
            final SampleViewModel sampleViewModel = new SampleViewModel(submitter,
                    pageTitle,
                    breadcrumbs,
                    sample,
                    goData,
                    emgFile,
                    archivedSequences,
                    propertyContainer,
                    interProEntries,
                    experimentType,
                    downloadSection,
                    relatedLinks,
                    relatedPublications,
                    isHostAssociated,
                    sampleAnnotations,
                    analysisStatus);
            //Load and set taxonomy result data
            sampleViewModel.setTaxonomyAnalysisResult(loadTaxonomyDataFromCSV(propertyContainer.getPathToAnalysisDirectory()));
            return sampleViewModel;
        } else {
            return new SampleViewModel(submitter,
                    pageTitle,
                    breadcrumbs,
                    sample,
                    archivedSequences,
                    propertyContainer,
                    interProEntries,
                    experimentType,
                    downloadSection,
                    relatedLinks,
                    relatedPublications,
                    isHostAssociated,
                    sampleAnnotations,
                    analysisStatus);
        }
    }

    private AnalysisStatus getAnalysisStatus(boolean isAnalysisCompleted) {
        if (!isAnalysisCompleted) {
            return new AnalysisStatus(
                    new TaxonomicAnalysisTab(true, true, true, true),
                    true,
                    new FunctionalAnalysisTab(true, true));
        }
        //Set qualityControlTab value
        //If one of the quality control files does exist the tab gets activated
        boolean qualityControlTabDisabled = true;
        for (IResultFileDefinition fileDefinition : qualityControlFileDefinitions) {
            File fileObject = FileObjectBuilder.createFileObject(emgFile, propertyContainer, fileDefinition);
            boolean doesExist = FileExistenceChecker.checkFileExistence(fileObject);
            if (doesExist) {
                qualityControlTabDisabled = false;
                break;
            }
        }
        //
        //Set functional analysis tab object
        boolean isInterProMatchSectionDisabled = true;
        boolean isGoSectionDisabled = true;
        for (FunctionalAnalysisFileDefinition fileDefinition : functionalAnalysisFileDefinitions) {
            File fileObject = FileObjectBuilder.createFileObject(emgFile, propertyContainer, fileDefinition);
            boolean doesExist = FileExistenceChecker.checkFileExistence(fileObject);
            if (doesExist) {
                if (fileDefinition.getIdentifier().equalsIgnoreCase(FileDefinitionId.INTERPROSCAN_MATCHES_FILE.toString())) {
                    isInterProMatchSectionDisabled = false;
                } else if (fileDefinition.getIdentifier().equalsIgnoreCase(FileDefinitionId.GO_SLIM_FILE.toString())) {
                    isGoSectionDisabled = false;
                }
            }
        }
        //
        //Set taxonomic analysis tab object
        boolean isPieChartTabDisabled = true;
        boolean isBarChartTabDisabled = true;
        boolean isStackChartTabDisabled = true;
        boolean isKronaTabDisabled = true;
        for (ResultFileDefinitionImpl fileDefinition : taxonomicAnalysisFileDefinitions) {
            File fileObject = FileObjectBuilder.createFileObject(emgFile, propertyContainer, fileDefinition);
            boolean doesExist = FileExistenceChecker.checkFileExistence(fileObject);
            if (doesExist) {
                if (fileDefinition.getIdentifier().equalsIgnoreCase(FileDefinitionId.KRONA_HTML_FILE.toString())) {
                    isKronaTabDisabled = false;
                } else if (fileDefinition.getIdentifier().equalsIgnoreCase(FileDefinitionId.KINGDOM_COUNTS_FILE.toString())) {
                    isPieChartTabDisabled = false;
                    isBarChartTabDisabled = false;
                    isStackChartTabDisabled = false;
                }
            }
        }
        return new AnalysisStatus(
                new TaxonomicAnalysisTab(isPieChartTabDisabled, isBarChartTabDisabled, isStackChartTabDisabled, isKronaTabDisabled),
                qualityControlTabDisabled,
                new FunctionalAnalysisTab(isInterProMatchSectionDisabled, isGoSectionDisabled));
    }

    /**
     * Loads taxonomy result data from a CSV file, which should be located
     *
     * @param pathToAnalysisDirectory
     * @return
     */

    private TaxonomyAnalysisResult loadTaxonomyDataFromCSV(final String pathToAnalysisDirectory) {
        TaxonomyAnalysisResult taxonomyAnalysisResult = new TaxonomyAnalysisResult();
        final List<TaxonomyData> taxonomyDataSet = new ArrayList<TaxonomyData>();

        File phylumFile = new File(resultFilesDirectoryPath + propertyContainer.getResultFileName(MemiPropertyContainer.FileNameIdentifier.PHYLUM_COUNTS));
        if (!phylumFile.exists()) {
            log.warn("Deactivating taxonomy result tab, because file " + phylumFile.getAbsolutePath() + " doesn't exist!");
        } else {
            //Get the data
            List<String[]> data = getRows(phylumFile, '\t');
            for (String[] row : data) {
                try {
                    String superKingdom = row[0];
                    String phylum = row[1];
                    if (phylum.equalsIgnoreCase("Unassigned")) {
                        if (superKingdom.equalsIgnoreCase("Bacteria")) {
                            phylum = "Unassigned Bacteria";
                        } else if (superKingdom.equalsIgnoreCase("Archaea")) {
                            phylum = "Unassigned Archaea";
                        }
                    }
                    TaxonomyData taxonomyData = new TaxonomyData(superKingdom, phylum, Integer.parseInt(row[2]), row[3]);
                    if (taxonomyData != null && taxonomyData.getPhylum() != null) {
                        taxonomyDataSet.add(taxonomyData);
                    }
                } catch (NumberFormatException e) {
                    log.warn("Cannot parse string '" + row[2] + "' into an integer!");
                }
            }

            Collections.sort(taxonomyDataSet, TaxonomyAnalysisResult.TaxonomyDataComparator);
            taxonomyAnalysisResult = new TaxonomyAnalysisResult(taxonomyDataSet);

            return taxonomyAnalysisResult;
        }
        return taxonomyAnalysisResult;
    }

    private boolean isHostAssociated() {
        if (sample != null) {
            if (sample instanceof HostSample) {
                return true;
            }
        }
        return false;
    }

    private List<InterProEntry> getListOfInterProEntries(EmgFile emgFile) {
        List<String[]> rows = getRawData(emgFile, "_summary.ipr", ',');
        return loadInterProMatchesFromCSV(rows);
    }


    /**
     * Loads InterPro matches from the Python pipeline result file with file extension '_summary.ipr'.
     *
     * @param rows Parsed list of InterPro entries.
     * @return
     */
    protected static List<InterProEntry> loadInterProMatchesFromCSV(List<String[]> rows) {
        List<InterProEntry> result = new ArrayList<InterProEntry>();
        log.info("Processing interpro result summary file...");

        if (rows != null) {
            for (String[] row : rows) {
                if (row.length == 3) {
                    String entryID = row[0];
                    String entryDesc = row[1];
                    //Remove single quote marks
                    entryDesc = encodeSingleQuoteMarks(entryDesc);
                    entryDesc = entryDesc.replaceAll("\'", "");
                    int numOfEntryHits = Integer.parseInt(row[2]);
                    if (entryID != null && entryID.trim().length() > 0) {
                        result.add(new InterProEntry(entryID, entryDesc, numOfEntryHits));
                    }
                } else {
                    log.warn("Row size is not the expected one.");
                }
            }
        } else {
            log.warn("Didn't get any data from InterPro result summary file. There might be some fundamental change to this file" +
                    "(maybe in the near past), which affects this parsing process!");
        }
        return result;
    }

    protected static String encodeSingleQuoteMarks(String entryDesc) {
        return entryDesc.replaceAll("\'", "\\\\'");
    }

    private static String getHBarChartURL(Class clazz, Map<Class, List<AbstractGOTerm>> goData) {

        List<Integer> data = getGOData(clazz, goData);
        List<String> labels = getGOLabels(clazz, goData);
        Properties props = new Properties();

        props.put(GoogleChartFactory.CHART_MARGIN, "0,40,0,0");
        props.put(GoogleChartFactory.CHART_SIZE, "300x440");
        props.put(GoogleChartFactory.CHART_COLOUR, "ff0a00,ff4700,ff4700,ffb444,ffb444,ffd088,ffd088," +
                "ffebcc,b8b082,b8b082,b8b082,b8b082,b8b082,b8b082,b8b082,b8b082");
        props.put("chxt", "x");
        props.put("chxs", "0,ffffff,0,0,_");

        props.put("chds", "0,600");
        props.put("chxr", "0,0,600");
        props.put("chxs", "0,ffffff,0,0,_");

        return GoogleChartFactory.buildHorizontalBarChartURL(props, data, labels);
    }

    private static List<Integer> getGOData(Class clazz, Map<Class, List<AbstractGOTerm>> goData) {
        List<Integer> result = new ArrayList<Integer>();
        List<AbstractGOTerm> goTerms = null;
        if (clazz.equals(BiologicalProcessGOTerm.class)) {
            goTerms = goData.get(BiologicalProcessGOTerm.class);
        } else if (clazz.equals(CellularComponentGOTerm.class)) {
            goTerms = goData.get(CellularComponentGOTerm.class);
        } else {
            goTerms = goData.get(MolecularFunctionGOTerm.class);
        }
        if (goTerms != null) {
            //the second iteration is to calculate the pie chart data
            for (AbstractGOTerm term : goTerms) {
                result.add(term.getNumberOfMatches());
            }
        }
        return result;
    }

    private static List<String> getGOLabels(Class clazz, Map<Class, List<AbstractGOTerm>> goData) {
        List<String> result = new ArrayList<String>();
        List<AbstractGOTerm> goTerms = null;
        if (clazz.equals(BiologicalProcessGOTerm.class)) {
            goTerms = goData.get(BiologicalProcessGOTerm.class);
        } else if (clazz.equals(CellularComponentGOTerm.class)) {
            goTerms = goData.get(CellularComponentGOTerm.class);
        } else {
            goTerms = goData.get(MolecularFunctionGOTerm.class);
        }
        if (goTerms != null) {
            //the second iteration is to calculate the pie chart data
            for (AbstractGOTerm term : goTerms) {
                result.add(term.toString());
            }
        }
        return result;
    }

    private String getBarChartURL(String classPathToStatsFile, EmgFile emgFile) {
        List<Integer> chartData = loadStatsFromCSV(classPathToStatsFile, emgFile);
        if (chartData != null && chartData.size() > 0) {
            Properties props = new Properties();
            props.put(GoogleChartFactory.CHART_TYPE, "bhs");
            props.put(GoogleChartFactory.CHART_SIZE, "450x200");
            props.put(GoogleChartFactory.CHART_AXES, "y");
            props.put(GoogleChartFactory.CHART_LEGEND_TEXT, "total number of submitted reads|total number of processed reads|reads with at least 1 pCDS|reads with at least 1 InterPro match");
            props.put(GoogleChartFactory.CHART_COLOUR, "FF0000|00FF00|0000FF|FFFF10");
            props.put(GoogleChartFactory.CHART_MARKER, "N,000000,0,-1,11");
            props.put(GoogleChartFactory.CHART_DATA_SCALE, "0," + chartData.get(0));
            return GoogleChartFactory.buildChartURL(props, chartData);
        }
        return null;
    }

    private List<Integer> loadStatsFromCSV(String classPathToStatsFile, EmgFile emgFile) {
        List<Integer> result = new ArrayList<Integer>();

        log.info("Processing summary file...");
        List<String[]> rows = getRawData(emgFile, "_summary", ',');

        if (rows != null && rows.size() >= 6) {
            int numSubmittedSeqs = getValueOfRow(rows, 0);
            int numSeqsOfProcessedSeqs = getValueOfRow(rows, 3);
            int numSeqsWithPredicatedCDS = getValueOfRow(rows, 4);
            int numSeqsWithInterProScanMatch = getValueOfRow(rows, 5);
            result.add(numSubmittedSeqs);
            result.add(numSeqsOfProcessedSeqs);
            result.add(numSeqsWithPredicatedCDS);
            result.add(numSeqsWithInterProScanMatch);
        } else {
            log.warn("Didn't get any data from summary file. There might be some fundamental change to this file" +
                    "(maybe in the near past), which affects this parsing process!");
        }
        return result;
    }

    private static int getValueOfRow(List<String[]> rows, int i) {
        String[] row = rows.get(i);
        if (row.length > 1) {
            return Integer.parseInt(row[1]);
        }
        return -1;
    }

    private Map<Class, List<AbstractGOTerm>> loadGODataFromCSV(String classPathToStatsFile, EmgFile emgFile) {
        Map<Class, List<AbstractGOTerm>> result = new Hashtable<Class, List<AbstractGOTerm>>();
        result.put(BiologicalProcessGOTerm.class, new ArrayList<AbstractGOTerm>());
        result.put(CellularComponentGOTerm.class, new ArrayList<AbstractGOTerm>());
        result.put(MolecularFunctionGOTerm.class, new ArrayList<AbstractGOTerm>());

        log.info("Processing GO slim file...");
        List<String[]> rows = getRawData(emgFile, "_summary.go_slim", ',');

        if (rows != null) {
            for (String[] row : rows) {
                if (row.length == 4) {
                    String ontology = row[2];
                    if (ontology != null && ontology.trim().length() > 0) {
                        String accession = row[0];
                        String synonym = row[1];
                        int numberOfMatches = Integer.parseInt(row[3]);
                        if (ontology.equals("biological_process")) {
                            BiologicalProcessGOTerm instance = new BiologicalProcessGOTerm(accession,
                                    synonym, numberOfMatches);
                            result.get(BiologicalProcessGOTerm.class).add(instance);
                        } else if (ontology.equals("cellular_component")) {
                            CellularComponentGOTerm instance = new CellularComponentGOTerm(accession,
                                    synonym, numberOfMatches);
                            result.get(CellularComponentGOTerm.class).add(instance);
                        } else {
                            MolecularFunctionGOTerm instance = new MolecularFunctionGOTerm(accession,
                                    synonym, numberOfMatches);
                            result.get(MolecularFunctionGOTerm.class).add(instance);
                        }
                    }
                } else {
                    log.warn("Row size is not the expected one.");
                }
            }
        } else {
            log.warn("Didn't get any data from GO term file. There might be some fundamental change to this file" +
                    "(maybe in the near past), which affects this parsing process!");
        }
        return result;
    }

    /**
     * Reads raw data from the specified file by using a CSV reader. Possible to specify different delimiters.
     *
     * @param emgFile
     * @return
     */
    private List<String[]> getRawData(EmgFile emgFile, String fileExtension, char delimiter) {
        String directoryName = emgFile.getFileIDInUpperCase().replace('.', '_');
        File file = new File(resultFilesDirectoryPath + '/' + directoryName + fileExtension);
        return getRows(file, delimiter);
    }

    private List<String[]> getRows(final File file, final char delimiter) {
        List<String[]> rows = null;
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(file), delimiter);
        } catch (FileNotFoundException e) {
            log.warn("Could not find the following file " + file.getAbsolutePath(), e);
        }
        if (reader != null) {
            try {
                rows = reader.readAll();
            } catch (IOException e) {
                log.warn("Could not get rows from CSV file!", e);
            }
        }
        return rows;
    }

    /**
     * Divides the set of publications into 2 different types of publication sets.
     */
    private void buildPublicationLists() {
        for (Publication pub : sample.getPublications()) {
            if (pub.getPubType().equals(PublicationType.PUBLICATION)) {
                relatedPublications.add(pub);
            } else if (pub.getPubType().equals(PublicationType.WEBSITE_LINK)) {
                relatedLinks.add(pub);
            }
        }
        //Sorting lists
        Collections.sort(relatedPublications, new PublicationComparator());
        Collections.sort(relatedLinks, new PublicationComparator());
    }
}