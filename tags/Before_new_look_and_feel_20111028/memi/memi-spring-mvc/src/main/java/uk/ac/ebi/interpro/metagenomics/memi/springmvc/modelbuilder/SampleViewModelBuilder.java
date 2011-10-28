package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.basic.comparators.PublicationComparator;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSampleAnnotation;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.HostSample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Publication;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.SampleViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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

    private List<String> archivedSeqs;

    private List<EmgSampleAnnotation> sampleAnnotations;


    public SampleViewModelBuilder(SessionManager sessionMgr, String pageTitle, List<Breadcrumb> breadcrumbs,
                                  MemiPropertyContainer propertyContainer,
                                  Sample sample, List<String> archivedSeqs, List<EmgSampleAnnotation> sampleAnnotations) {
        super(sessionMgr);
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.propertyContainer = propertyContainer;
        this.sample = sample;
        this.archivedSeqs = archivedSeqs;
        this.sampleAnnotations=sampleAnnotations;
    }

    @Override
    public SampleViewModel getModel() {
        log.info("Building instance of " + SampleViewModel.class + "...");
        return new SampleViewModel(getSessionSubmitter(sessionMgr), sample, archivedSeqs, pageTitle,
                breadcrumbs, propertyContainer, isHostAssociated(), getSamplePublications(),sampleAnnotations);
    }

    private boolean isHostAssociated() {
        if (sample != null) {
            if (sample instanceof HostSample) {
                return true;
            }
        }
        return false;
    }

    private List<Publication> getSamplePublications() {
        List<Publication> publications = new ArrayList<Publication>();
        if (sample != null) {
            Set<Publication> pubs = sample.getPublications();
            if (pubs != null) {
                publications.addAll(pubs);
            }
        }
        Collections.sort(publications, new PublicationComparator());
        return publications;
    }
}