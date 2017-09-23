package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

/**
 * Holds a list of parameters, which are used in the ViewModel class, but also globally.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public interface IViewModelParams {

    public static final String DEFAULT_CLASS = "ebigrey";

    public static final String HIGHLIGHTED_CLASS = "active-trail";

    public static final String TAB_CLASS_HOME_VIEW = "tabClassHomeView";

    public static final String TAB_CLASS_SEARCH_VIEW = "tabClassSearchView";

    public static final String TAB_CLASS_SEQUENCE_SEARCH_VIEW = "tabClassSequenceSearchView";

    public static final String TAB_CLASS_SUBMIT_VIEW = "tabClassSubmitView";

    public static final String TAB_CLASS_PROJECTS_VIEW = "tabClassProjectsView";

    public static final String TAB_CLASS_SAMPLES_VIEW = "tabClassSamplesView";

    public static final String TAB_CLASS_ABOUT_VIEW = "tabClassAboutView";

    public static final String TAB_CLASS_COMPARE_VIEW = "tabClassCompareView";

    public static final String TAB_CLASS_CONTACT_VIEW = "tabClassContactView";

    public static final String TAB_CLASS_FEEDBACK_VIEW = "tabClassFeedbackView";
}