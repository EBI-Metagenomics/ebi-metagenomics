package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.results;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.core.comparators.PublicationComparator;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSampleAnnotation;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.MetaDataViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model builder class for {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.QualityCheckViewModel}.
 * <p/>
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

    private List<EmgSampleAnnotation> sampleAnnotations;

    public MetaDataViewModelBuilder(SessionManager sessionMgr,
                                    String pageTitle,
                                    List<Breadcrumb> breadcrumbs,
                                    MemiPropertyContainer propertyContainer,
                                    AnalysisJob analysisJob,
                                    List<EmgSampleAnnotation> sampleAnnotations) {
        super(sessionMgr, pageTitle, breadcrumbs, propertyContainer, null, null, null, analysisJob);
        this.sample = analysisJob.getSample();
        this.sampleAnnotations = sampleAnnotations;
        this.relatedLinks = new ArrayList<Publication>();
        this.relatedPublications = new ArrayList<Publication>();
    }

    public MetaDataViewModel getModel() {
        log.debug("Building instance of " + MetaDataViewModel.class + "...");
        //Get the sample object from the analysis job
        Sample sample = analysisJob.getSample();
        final boolean isHostAssociated = isHostAssociated(sample);

        buildPublicationLists();

        return new MetaDataViewModel(getSessionSubmitter(sessionMgr), pageTitle, breadcrumbs, propertyContainer, sample, analysisJob, isHostAssociated, sampleAnnotations, relatedLinks, relatedPublications);
    }

    private boolean isHostAssociated(Sample sample) {
        if (sample != null) {
            Biome biome = sample.getBiome();
            if (biome != null) {
                String lineage = biome.getLineage();
                if (lineage != null) {
                    if (lineage.startsWith("root:Host-associated")) {
                        return true;
                    }
                }
            }
        }
        return false;
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