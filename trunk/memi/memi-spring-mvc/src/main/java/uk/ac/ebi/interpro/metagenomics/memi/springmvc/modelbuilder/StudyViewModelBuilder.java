package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.StudyViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.util.List;

public class StudyViewModelBuilder extends AbstractViewModelBuilder<StudyViewModel> {

    private final static Log log = LogFactory.getLog(StudyViewModelBuilder.class);

    private String pageTitle;

    private List<Breadcrumb> breadcrumbs;

    private MemiPropertyContainer propertyContainer;

    private Study study;

    List<Sample> samples;

    public StudyViewModelBuilder(SessionManager sessionMgr, String pageTitle, List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer,
                                 Study study, List<Sample> samples) {
        super(sessionMgr);
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.propertyContainer = propertyContainer;
        this.study = study;
        this.samples = samples;
    }

    @Override
    public StudyViewModel getModel() {
        log.info("Building instance of " + StudyViewModel.class + "...");
        return new StudyViewModel(getSessionSubmitter(sessionMgr), study, samples, pageTitle,
                breadcrumbs, propertyContainer);
    }
}