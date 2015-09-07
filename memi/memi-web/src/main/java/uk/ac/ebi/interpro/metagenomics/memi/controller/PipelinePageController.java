package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.PipelineReleaseDAO;
import uk.ac.ebi.interpro.metagenomics.memi.exceptionHandling.EntryNotFoundException;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.PipelineRelease;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.PipelineReleaseTool;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.DefaultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Represents the controller for the MG portal home page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller(value = "pipelinePageController")
@RequestMapping('/' + PipelinePageController.VIEW_NAME)
public class PipelinePageController extends AbstractController {

    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "pipelines";

    @Resource
    private PipelineReleaseDAO pipelineReleaseDAO;

    @RequestMapping(value = "/{releaseVersion:\\d\\.\\d}")
    public ModelAndView doGet(@PathVariable final String releaseVersion, ModelMap model) {
        return buildModelAndView(
                "pipeline",
                model,
                new ModelPopulator() {
                    @Override
                    public void populateModel(ModelMap model) {
                        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(sessionManager, "Pipeline Version", getSubPageBreadcrumbs(releaseVersion), propertyContainer);
                        final ViewModel defaultViewModel = builder.getModel();
                        defaultViewModel.changeToHighlightedClass(ViewModel.TAB_CLASS_ABOUT_VIEW);
                        model.addAttribute(ViewModel.MODEL_ATTR_NAME, defaultViewModel);
                        //
                        PipelineRelease pipelineRelease = pipelineReleaseDAO.readByReleaseVersion(releaseVersion);
                        if (pipelineRelease == null) {
                            throw new EntryNotFoundException();
                        }

                        Set<PipelineReleaseTool> pipelineReleaseTools = pipelineRelease.getPipelineReleaseTools();
                        model.addAttribute("releaseVersion", releaseVersion);
                        model.addAttribute("releaseDate", pipelineRelease.getReleaseDate());
                        model.addAttribute("pipelineReleaseTools", pipelineReleaseTools);
                    }
                });
    }

    @RequestMapping
    public ModelAndView doGet(ModelMap model) {
        return buildModelAndView(
                getModelViewName(),
                model,
                new ModelPopulator() {
                    @Override
                    public void populateModel(ModelMap model) {
                        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(sessionManager, "Pipelines", getBreadcrumbs(null), propertyContainer);
                        final ViewModel defaultViewModel = builder.getModel();
                        defaultViewModel.changeToHighlightedClass(ViewModel.TAB_CLASS_ABOUT_VIEW);
                        model.addAttribute(ViewModel.MODEL_ATTR_NAME, defaultViewModel);
                        //
                        List<PipelineRelease> pipelineReleases = pipelineReleaseDAO.retrieveAllSortedByReleaseVersion();
                        model.addAttribute("pipelineReleases", pipelineReleases);
                    }
                });
    }

    protected String getModelViewName() {
        return VIEW_NAME;
    }

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        result.add(new Breadcrumb("About", "About the Metagenomics portal", InfoController.VIEW_NAME));
        result.add(new Breadcrumb("Pipeline release archive", "List of pipeline releases", VIEW_NAME));

        return result;
    }

    private List<Breadcrumb> getSubPageBreadcrumbs(final String releaseVersion) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>(getBreadcrumbs(null));
        result.add(new Breadcrumb("Release version " + releaseVersion, "Pipeline release version " + releaseVersion, "pipelines/" + releaseVersion));
        return result;
    }
}