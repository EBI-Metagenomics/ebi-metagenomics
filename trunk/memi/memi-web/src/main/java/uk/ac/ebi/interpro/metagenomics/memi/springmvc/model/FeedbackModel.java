package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.forms.FeedbackForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;

import java.util.List;

/**
 * Represents the model for the feedback form and page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class FeedbackModel extends ViewModel {

    private FeedbackForm feedbackForm;

    public FeedbackModel(Submitter submitter, String pageTitle, List<Breadcrumb> breadcrumbs,
                         MemiPropertyContainer propertyContainer) {
        super(submitter, pageTitle, breadcrumbs, propertyContainer);
        this.feedbackForm = new FeedbackForm();
    }

    public FeedbackForm getFeedbackForm() {
        return feedbackForm;
    }

    public void setFeedbackForm(FeedbackForm feedbackForm) {
        this.feedbackForm = feedbackForm;
    }
}