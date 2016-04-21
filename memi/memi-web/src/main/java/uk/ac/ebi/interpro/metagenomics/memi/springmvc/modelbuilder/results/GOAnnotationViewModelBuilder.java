package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.results;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileExistenceChecker;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.BiologicalProcessGOTerm;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.CellularComponentGOTerm;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MolecularFunctionGOTerm;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.GOAnnotationViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.UserManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Model builder class for {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.ResultViewModel}.
 * <p>
 * See {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder} for more information of how to use.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
public class GOAnnotationViewModelBuilder extends AbstractResultViewModelBuilder<GOAnnotationViewModel> {

    private final static Log log = LogFactory.getLog(GOAnnotationViewModelBuilder.class);

    public GOAnnotationViewModelBuilder(UserManager sessionMgr,
                                        EBISearchForm ebiSearchForm,
                                        String pageTitle,
                                        List<Breadcrumb> breadcrumbs,
                                        MemiPropertyContainer propertyContainer,
                                        AnalysisJob analysisJob) {
        super(sessionMgr, ebiSearchForm, pageTitle, breadcrumbs, propertyContainer, null, null, null, analysisJob);
    }

    public GOAnnotationViewModel getModel() {
        log.info("Building instance of " + GOAnnotationViewModel.class + "...");
        //Add GO results
        FunctionalAnalysisResult functionalAnalysisResult = loadGODataFromCSV(new FunctionalAnalysisResult());
        //
        return new GOAnnotationViewModel(getSessionSubmitter(sessionMgr), getEbiSearchForm(), pageTitle, breadcrumbs, propertyContainer, functionalAnalysisResult);
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
}