package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.results;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.TaxonomyData;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.FunctionalViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.TaxonomicViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model builder class for {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.ResultViewModel}.
 * <p/>
 * See {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder} for more information of how to use.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
public class TaxonomicViewModelBuilder extends AbstractResultViewModelBuilder<TaxonomicViewModel> {

    private final static Log log = LogFactory.getLog(TaxonomicViewModelBuilder.class);

    private Run run;

    public TaxonomicViewModelBuilder(SessionManager sessionMgr,
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

    public TaxonomicViewModel getModel() {
        log.info("Building instance of " + FunctionalViewModel.class + "...");
        //Get the sample object from the analysis job
        final Sample sample = analysisJob.getSample();
        //Get analysis status
        final AnalysisStatus analysisStatus = getAnalysisStatus((sample.getAnalysisCompleted() == null ? false : true));
        final TaxonomyAnalysisResult taxonomyAnalysisResult = loadTaxonomyDataFromCSV();

        return new TaxonomicViewModel(getSessionSubmitter(sessionMgr), pageTitle, breadcrumbs, propertyContainer, analysisJob.getSample(), run, analysisJob, analysisStatus, taxonomyAnalysisResult);
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
}