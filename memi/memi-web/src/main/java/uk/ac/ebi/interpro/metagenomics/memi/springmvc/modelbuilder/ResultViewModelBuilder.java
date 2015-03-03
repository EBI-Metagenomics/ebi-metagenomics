package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.core.comparators.PublicationComparator;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSampleAnnotation;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.*;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileExistenceChecker;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.AnalysisStatus;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.tabActivation.FunctionalAnalysisTab;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.tabActivation.TaxonomicAnalysisTab;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Model builder class for {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ResultViewModel}.
 * <p/>
 * See {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder} for more information of how to use.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class ResultViewModelBuilder extends AbstractViewModelBuilder<ResultViewModel> {

    private final static Log log = LogFactory.getLog(ResultViewModelBuilder.class);

    private String pageTitle;

    private List<Breadcrumb> breadcrumbs;

    private MemiPropertyContainer propertyContainer;

    private Sample sample;

    private Run run;

    private List<EmgSampleAnnotation> sampleAnnotations;

    private AnalysisJob analysisJob;

    private ResultViewModel.ExperimentType experimentType;

    private DownloadSection downloadSection;

    private final List<String> archivedSequences;

    private List<Publication> relatedLinks;

    private List<Publication> relatedPublications;

    private List<ResultFileDefinitionImpl> qualityControlFileDefinitions;

    private List<FunctionalAnalysisFileDefinition> functionalAnalysisFileDefinitions;

    private List<ResultFileDefinitionImpl> taxonomicAnalysisFileDefinitions;


    public ResultViewModelBuilder(SessionManager sessionMgr,
                                  Sample sample,
                                  Run run,
                                  String pageTitle,
                                  List<Breadcrumb> breadcrumbs,
                                  AnalysisJob analysisJob,
                                  List<String> archivedSequences,
                                  MemiPropertyContainer propertyContainer,
                                  ResultViewModel.ExperimentType experimentType,
                                  final DownloadSection downloadSection,
                                  List<EmgSampleAnnotation> sampleAnnotations,
                                  List<ResultFileDefinitionImpl> qualityControlFileDefinitions,
                                  List<FunctionalAnalysisFileDefinition> functionalAnalysisFileDefinitions,
                                  List<ResultFileDefinitionImpl> taxonomicAnalysisFileDefinitions) {
        super(sessionMgr);
        this.sample = sample;
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.analysisJob = analysisJob;
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
        this.run = run;
    }

    @Override
    public ResultViewModel getModel() {
        log.info("Building instance of " + ResultViewModel.class + "...");
        FunctionalAnalysisResult functionalAnalysisResult = getListOfInterProEntries();
        final boolean isHostAssociated = isHostAssociated();
        final Submitter submitter = getSessionSubmitter(sessionMgr);

        buildPublicationLists();

        //Get analysis status
        AnalysisStatus analysisStatus = getAnalysisStatus((sample.getAnalysisCompleted() == null ? false : true));


        if (analysisJob != null) {
            //Add GO results
            functionalAnalysisResult = loadGODataFromCSV(functionalAnalysisResult);
            final ResultViewModel resultViewModel = new ResultViewModel(submitter,
                    pageTitle,
                    breadcrumbs,
                    sample,
                    run,
                    analysisJob,
                    archivedSequences,
                    propertyContainer,
                    functionalAnalysisResult,
                    experimentType,
                    downloadSection,
                    relatedLinks,
                    relatedPublications,
                    isHostAssociated,
                    sampleAnnotations,
                    analysisStatus);
            //Load and set taxonomy result data
            resultViewModel.setTaxonomyAnalysisResult(loadTaxonomyDataFromCSV());
            return resultViewModel;
        } else {
            return new ResultViewModel(submitter,
                    pageTitle,
                    breadcrumbs,
                    sample,
                    run,
                    archivedSequences,
                    propertyContainer,
                    functionalAnalysisResult,
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
                    new FunctionalAnalysisTab(true, true, true));
        }
        //Set qualityControlTab value
        //If one of the quality control files does exist the tab gets activated
        boolean qualityControlTabDisabled = true;
        for (IResultFileDefinition fileDefinition : qualityControlFileDefinitions) {
            File fileObject = FileObjectBuilder.createFileObject(analysisJob, propertyContainer, fileDefinition);
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
        boolean isSequenceFeatureSectionDisabled = true;
        for (FunctionalAnalysisFileDefinition fileDefinition : functionalAnalysisFileDefinitions) {
            File fileObject = FileObjectBuilder.createFileObject(analysisJob, propertyContainer, fileDefinition);
            boolean doesExist = FileExistenceChecker.checkFileExistence(fileObject);
            if (doesExist) {
                if (fileDefinition.getIdentifier().equalsIgnoreCase(FileDefinitionId.INTERPRO_MATCHES_SUMMARY_FILE.toString())) {
                    isInterProMatchSectionDisabled = false;
                } else if (fileDefinition.getIdentifier().equalsIgnoreCase(FileDefinitionId.GO_COMPLETE_FILE.toString())) {
                    isGoSectionDisabled = false;
                } else if (fileDefinition.getIdentifier().equalsIgnoreCase(FileDefinitionId.SEQUENCE_FEATURE_SUMMARY_FILE.toString())) {
                    isSequenceFeatureSectionDisabled = false;
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
            File fileObject = FileObjectBuilder.createFileObject(analysisJob, propertyContainer, fileDefinition);
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
                new FunctionalAnalysisTab(isInterProMatchSectionDisabled, isGoSectionDisabled, isSequenceFeatureSectionDisabled));
    }

    /**
     * Loads taxonomy result data from a CSV file, which should be located
     *
     * @return TaxonomyAnalysisResult.
     */

    private TaxonomyAnalysisResult loadTaxonomyDataFromCSV() {
        final List<TaxonomyData> taxonomyDataSet = new ArrayList<TaxonomyData>();

        File phylumFile = FileObjectBuilder.createFileObject(analysisJob, propertyContainer, propertyContainer.getResultFileDefinition(FileDefinitionId.KINGDOM_COUNTS_FILE));
        if (!phylumFile.exists()) {
            log.warn("Deactivating taxonomy result tab, because file " + phylumFile.getAbsolutePath() + " doesn't exist!");
        } else {
            //Get the data
            List<String[]> data = getRawData(phylumFile, '\t');
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
            return new TaxonomyAnalysisResult(taxonomyDataSet);
        }
        return new TaxonomyAnalysisResult();
    }

    private boolean isHostAssociated() {
        if (sample != null) {
            if (sample instanceof HostSample) {
                return true;
            }
        }
        return false;
    }

    private FunctionalAnalysisResult getListOfInterProEntries() {
        File interProMatchesSummaryFile = FileObjectBuilder.createFileObject(analysisJob, propertyContainer, propertyContainer.getResultFileDefinition(FileDefinitionId.INTERPRO_MATCHES_SUMMARY_FILE));
        if (FileExistenceChecker.checkFileExistence(interProMatchesSummaryFile)) {
            List<String[]> rows = getRawData(interProMatchesSummaryFile, ',');
            return loadInterProMatchesFromCSV(rows);
        } else {
            return new FunctionalAnalysisResult();
        }
    }


    /**
     * Loads InterPro matches from the Python pipeline result file with file extension '_summary.ipr'.
     *
     * @param rows Parsed list of InterPro entries.
     * @return
     */
    protected static FunctionalAnalysisResult loadInterProMatchesFromCSV(List<String[]> rows) {
        List<InterProEntry> result = new ArrayList<InterProEntry>();
        log.info("Processing interpro result summary file...");

        int totalReadsCount = 0;
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
                    totalReadsCount += numOfEntryHits;
                } else {
                    log.warn("Row size is not the expected one.");
                }
            }
        } else {
            log.warn("Didn't get any data from InterPro result summary file. There might be some fundamental change to this file" +
                    "(maybe in the near past), which affects this parsing process!");
        }
        return new FunctionalAnalysisResult(new InterProMatchesSection(result, totalReadsCount));
    }

    protected static String encodeSingleQuoteMarks(String entryDesc) {
        return entryDesc.replaceAll("\'", "\\\\'");
    }

    private FunctionalAnalysisResult loadGODataFromCSV(final FunctionalAnalysisResult functionalAnalysisResult) {
        log.info("Processing GO slim file...");
        File goSlimFile = FileObjectBuilder.createFileObject(analysisJob, propertyContainer, propertyContainer.getResultFileDefinition(FileDefinitionId.GO_SLIM_FILE));
        if (FileExistenceChecker.checkFileExistence(goSlimFile)) {
            List<String[]> rows = getRawData(goSlimFile, ',');
            if (rows != null) {
                final List<BiologicalProcessGOTerm> biologicalProcessGOTermList = new ArrayList<BiologicalProcessGOTerm>();
                final List<CellularComponentGOTerm> cellularComponentGOTermList = new ArrayList<CellularComponentGOTerm>();
                final List<MolecularFunctionGOTerm> molecularFunctionGOTermList = new ArrayList<MolecularFunctionGOTerm>();
                int totalHitsCountBioProcess = 0;
                int totalHitsCountCellComponent = 0;
                int totalHitsCountMolFunction = 0;
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
                                totalHitsCountBioProcess += numberOfMatches;
                                biologicalProcessGOTermList.add(instance);
                            } else if (ontology.equals("cellular_component")) {
                                CellularComponentGOTerm instance = new CellularComponentGOTerm(accession,
                                        synonym, numberOfMatches);
                                totalHitsCountCellComponent += numberOfMatches;
                                cellularComponentGOTermList.add(instance);
                            } else {
                                MolecularFunctionGOTerm instance = new MolecularFunctionGOTerm(accession,
                                        synonym, numberOfMatches);
                                totalHitsCountMolFunction += numberOfMatches;
                                molecularFunctionGOTermList.add(instance);
                            }
                        }
                    } else {
                        log.warn("Row size is not the expected one.");
                    }
                }
                GoTermSection goTermSection = new GoTermSection(new BiologicalProcessGoTerms(totalHitsCountBioProcess, biologicalProcessGOTermList),
                        new CellularComponentGoTerms(totalHitsCountCellComponent, cellularComponentGOTermList),
                        new MolecularFunctionGoTerms(totalHitsCountMolFunction, molecularFunctionGOTermList));
                functionalAnalysisResult.setGoTermSection(goTermSection);
            } else {
                log.warn("Didn't get any data from GO term file. There might be some fundamental change to this file" +
                        "(maybe in the near past), which affects this parsing process!");
            }
        }
        return functionalAnalysisResult;
    }

    /**
     * Reads raw data from the specified file by using a CSV reader. Possible to specify different delimiters.
     */
    private List<String[]> getRawData(final File file, final char delimiter) {
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