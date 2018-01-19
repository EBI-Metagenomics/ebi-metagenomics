package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.results;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.TaxonomyData;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.FunctionalViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.TaxonomicViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.UserManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model builder class for {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.ResultViewModel}.
 * <p>
 * See {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder} for more information of how to use.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
public class TaxonomicViewModelBuilder extends AbstractResultViewModelBuilder<TaxonomicViewModel> {

    private final static Log log = LogFactory.getLog(TaxonomicViewModelBuilder.class);

    private Run run;

    public TaxonomicViewModelBuilder(UserManager sessionMgr,
                                     EBISearchForm ebiSearchForm,
                                     String pageTitle,
                                     List<Breadcrumb> breadcrumbs,
                                     MemiPropertyContainer propertyContainer,
                                     Run run,
                                     List<ResultFileDefinitionImpl> qualityControlFileDefinitions,
                                     List<FunctionalAnalysisFileDefinition> functionalAnalysisFileDefinitions,
                                     List<ResultFileDefinitionImpl> taxonomicAnalysisFileDefinitions,
                                     AnalysisJob analysisJob) {
        super(sessionMgr, ebiSearchForm, pageTitle, breadcrumbs, propertyContainer, qualityControlFileDefinitions,
                functionalAnalysisFileDefinitions, taxonomicAnalysisFileDefinitions, analysisJob);
        this.run = run;
    }

    public TaxonomicViewModel getModel() {
        log.info("Building instance of " + FunctionalViewModel.class + "...");
        //Get the sample object from the analysis job
        final Sample sample = analysisJob.getSample();
        //Get analysis status
        final AnalysisStatus analysisStatus = getAnalysisStatus((sample.getAnalysisCompleted() == null ? false : true));
        final TaxonomyAnalysisResult taxonomyAnalysisResultSSU = loadTaxonomyDataSSUFromCSV();
        final TaxonomyAnalysisResult taxonomyAnalysisResultLSU = loadTaxonomyDataLSUFromCSV();

        return new TaxonomicViewModel(getSessionSubmitter(sessionMgr), getEbiSearchForm(), pageTitle, breadcrumbs, propertyContainer, analysisJob.getSample(), run, analysisJob, analysisStatus, taxonomyAnalysisResultSSU, taxonomyAnalysisResultLSU);
    }

    private TaxonomyAnalysisResult loadTaxonomyDataLSUFromCSV() {
        // Get pipeline release version
        String releaseVersion = analysisJob.getPipelineRelease().getReleaseVersion();
        // Parsing 18S/LSU results
        if (releaseVersion.equalsIgnoreCase("4.0") || releaseVersion.equalsIgnoreCase("4.1")) {
            final List<TaxonomyData> taxonomyDataSetLSU = parsingResults(FileDefinitionId.KINGDOM_COUNTS_FILE_LSU);
            if (taxonomyDataSetLSU != null) {
                Collections.sort(taxonomyDataSetLSU, TaxonomyAnalysisResult.TaxonomyDataComparator);
                return new TaxonomyAnalysisResult(taxonomyDataSetLSU);
            }
        }
        return new TaxonomyAnalysisResult();
    }

    /**
     * Loads taxonomy result data from a CSV file, which should be located
     *
     * @return TaxonomyAnalysisResult.
     */
    private TaxonomyAnalysisResult loadTaxonomyDataSSUFromCSV() {
        // Get pipeline release version
        String releaseVersion = analysisJob.getPipelineRelease().getReleaseVersion();
        // Parsing 18S/LSU results
        FileDefinitionId fileDefinitionId = FileDefinitionId.KINGDOM_COUNTS_FILE;
        if (releaseVersion.equalsIgnoreCase("4.0") || releaseVersion.equalsIgnoreCase("4.1")) {
            fileDefinitionId = FileDefinitionId.KINGDOM_COUNTS_FILE_SSU;
        }
        final List<TaxonomyData> taxonomyDataSetLSU = parsingResults(fileDefinitionId);
        if (taxonomyDataSetLSU != null) {
            Collections.sort(taxonomyDataSetLSU, TaxonomyAnalysisResult.TaxonomyDataComparator);
            return new TaxonomyAnalysisResult(taxonomyDataSetLSU);
        }
        return new TaxonomyAnalysisResult();
    }


    private List<TaxonomyData> parsingResults(FileDefinitionId fileDefinitionId) {
        File phylumFileLSU = FileObjectBuilder.createFileObject(analysisJob, propertyContainer, propertyContainer.getResultFileDefinition(fileDefinitionId));
        if (phylumFileLSU != null && phylumFileLSU.exists()) {
            //Get the data
            return parseTaxonomyData(phylumFileLSU);
        }
        return null;
    }

    private List<TaxonomyData> parseTaxonomyData(final File phylumFile) {
        final List<TaxonomyData> taxonomyDataSet = new ArrayList<TaxonomyData>();
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
                if (taxonomyData.getPhylum() != null) {
                    taxonomyDataSet.add(taxonomyData);
                }
            } catch (NumberFormatException e) {
                log.warn("Cannot parse string '" + row[2] + "' into an integer!");
            }
        }
        return taxonomyDataSet;
    }
}