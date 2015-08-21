package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.results;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileExistenceChecker;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.FunctionalViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Model builder class for {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.ResultViewModel}.
 * <p/>
 * See {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder} for more information of how to use.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
public class FunctionalViewModelBuilder extends AbstractResultViewModelBuilder<FunctionalViewModel> {

    private final static Log log = LogFactory.getLog(FunctionalViewModelBuilder.class);

    private Run run;

    public FunctionalViewModelBuilder(SessionManager sessionMgr,
                                      String pageTitle,
                                      List<Breadcrumb> breadcrumbs,
                                      MemiPropertyContainer propertyContainer,
                                      Run run,
                                      List<ResultFileDefinitionImpl> qualityControlFileDefinitions,
                                      List<FunctionalAnalysisFileDefinition> functionalAnalysisFileDefinitions,
                                      List<ResultFileDefinitionImpl> taxonomicAnalysisFileDefinitions,
                                      AnalysisJob analysisJob) {
        super(sessionMgr, pageTitle, breadcrumbs, propertyContainer, qualityControlFileDefinitions, functionalAnalysisFileDefinitions, taxonomicAnalysisFileDefinitions, analysisJob);
        this.run = run;
    }

    public FunctionalViewModel getModel() {
        log.info("Building instance of " + FunctionalViewModel.class + "...");
        //Get the sample object from the analysis job
        final Sample sample = analysisJob.getSample();
        //Get analysis status
        final AnalysisStatus analysisStatus = getAnalysisStatus((sample.getAnalysisCompleted() == null ? false : true));
        FunctionalAnalysisResult functionalAnalysisResult = null;
        if (!isAmpliconData()) {
            functionalAnalysisResult = getListOfInterProEntries();
        }
        return new FunctionalViewModel(getSessionSubmitter(sessionMgr), pageTitle, breadcrumbs, propertyContainer, analysisJob.getSample(), run, analysisJob, analysisStatus, functionalAnalysisResult);
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
        List<InterProEntry> fullResults = new ArrayList<InterProEntry>();
        List<InterProEntry> condensedResults = new ArrayList<InterProEntry>();
        log.info("Processing interpro result summary file...");

        int totalReadsCount = 0;
        int otherMatchesReadsCount = 0;
        final int maxNumCondensedResults = 10;
        if (rows != null) {
            for (int i = 0; i < rows.size(); i++) {
                String[] row = rows.get(i);
                if (row.length == 3) {
                    String entryID = row[0];
                    String entryDesc = row[1];
                    //Remove single quote marks
                    entryDesc = encodeSingleQuoteMarks(entryDesc);
                    entryDesc = entryDesc.replaceAll("\'", "");
                    int numOfEntryHits = Integer.parseInt(row[2]);
                    if (entryID != null && entryID.trim().length() > 0) {
                        fullResults.add(new InterProEntry(entryID, entryDesc, numOfEntryHits));
                        if (i < maxNumCondensedResults) {
                            // Only store the top results in the condensed list
                            condensedResults.add(new InterProEntry(entryID, entryDesc, numOfEntryHits));
                        } else {
                            otherMatchesReadsCount += numOfEntryHits;
                        }
                    }
                    totalReadsCount += numOfEntryHits;
                } else {
                    log.warn("Row size is not the expected one.");
                }
            }
            if (otherMatchesReadsCount > 0) {
                // Add on the "other matches" entry at the end of the condensed list
                condensedResults.add(new InterProEntry("Other matches", "Other matches", otherMatchesReadsCount));
            }
        } else {
            log.warn("Didn't get any data from InterPro result summary file. There might be some fundamental change to this file" +
                    "(maybe in the near past), which affects this parsing process!");
        }
        return new FunctionalAnalysisResult(new InterProMatchesSection(fullResults, condensedResults, totalReadsCount));
    }

    protected static String encodeSingleQuoteMarks(String entryDesc) {
        return entryDesc.replaceAll("\'", "\\\\'");
    }
}