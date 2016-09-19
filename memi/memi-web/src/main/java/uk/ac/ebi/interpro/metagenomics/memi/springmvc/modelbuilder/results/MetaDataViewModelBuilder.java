package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.results;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.core.comparators.PublicationComparator;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Publication;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.MetaDataViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.UserManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model builder class for {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.QualityCheckViewModel}.
 * <p>
 * See {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder} for more information of how to use.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
public class MetaDataViewModelBuilder extends AbstractResultViewModelBuilder<MetaDataViewModel> {

    private final static Log log = LogFactory.getLog(MetaDataViewModelBuilder.class);

    private Sample sample;

    private List<Publication> relatedLinks;

    private List<Publication> relatedPublications;

    public MetaDataViewModelBuilder(UserManager sessionMgr,
                                    EBISearchForm ebiSearchForm,
                                    String pageTitle,
                                    List<Breadcrumb> breadcrumbs,
                                    MemiPropertyContainer propertyContainer,
                                    AnalysisJob analysisJob) {
        super(sessionMgr, ebiSearchForm, pageTitle, breadcrumbs, propertyContainer, null, null, null, analysisJob);
        this.sample = analysisJob.getSample();
        this.relatedLinks = new ArrayList<Publication>();
        this.relatedPublications = new ArrayList<Publication>();
    }

    public MetaDataViewModel getModel() {
        log.debug("Building instance of " + MetaDataViewModel.class + "...");
        //Get the sample object from the analysis job
        Sample sample = analysisJob.getSample();
        buildPublicationLists();

        return new MetaDataViewModel(getSessionSubmitter(sessionMgr), getEbiSearchForm(), pageTitle, breadcrumbs, propertyContainer, sample, analysisJob, relatedLinks, relatedPublications);
    }

    /**
     * Divides the set of publications into 2 different types of publication sets.
     */
    private void buildPublicationLists() {
        for (Publication pub : sample.getPublications()) {
            if (pub.getPubType().equals("WEBSITE_LINK")) {
                relatedLinks.add(pub);
            } else {
                relatedPublications.add(pub);
            }
        }
        //Sorting lists
        Collections.sort(relatedPublications, new PublicationComparator());
        Collections.sort(relatedLinks, new PublicationComparator());
    }
}