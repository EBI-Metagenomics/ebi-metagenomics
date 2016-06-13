package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.core.tools.MemiTools;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Biome;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.BiomeViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.BiomeDAO;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.BiomesViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents the controller for the MG portal home page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller(value = "biomesPageController")
public class BiomesPageController extends AbstractController implements IController {

    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "biomes";

    @Resource
    private BiomeDAO biomeDAO;

    @RequestMapping("/biomes")
    @Override
    public ModelAndView doGet(ModelMap model) {
        return buildModelAndView(
                getModelViewName(),
                model,
                new ModelPopulator() {
                    @Override
                    public void populateModel(ModelMap model) {
                        final ViewModelBuilder<BiomeViewModel> builder = new BiomesViewModelBuilder(userManager,
                                getEbiSearchForm(), "Biomes", getBreadcrumbs(null), propertyContainer, biomeDAO);
                        final BiomeViewModel defaultViewModel = builder.getModel();
//                        defaultViewModel.changeToHighlightedClass(ViewModel.TAB_CLASS_CONTACT_VIEW);
                        model.addAttribute(BiomeViewModel.MODEL_ATTR_NAME, defaultViewModel);
                        List<Object[]> raw_result = defaultViewModel.getBiomeDAO().countProjects();
                        List<HashMap> result = new ArrayList<HashMap>();
                        if (raw_result != null && !raw_result.isEmpty()) {
                            for (Object[] row : raw_result) {
                                HashMap b = new HashMap();
                                b.put("biome", row[1]);
                                b.put("numProjects", row[0]);
                                b.put("numProjectsIncludingChildren", defaultViewModel.getBiomeDAO().countProjectsIncludingChildren((Biome)row[1])); //defaultViewModel.getBiomeDAO().countProjects(row[0])
                                b.put("cssClass", MemiTools.getBiomeIconCSSClass((Biome)row[1], biomeDAO));
                                b.put("iconTitle", MemiTools.getBiomeIconTitle((Biome)row[1], biomeDAO));
                                b.put("formattedLineage", MemiTools.formatLineage(((Biome)row[1]).getLineage()));
                                result.add(b);
                            }
                        }
                        model.addAttribute(BiomeViewModel.MODEL_COUNTER, result);
                    }
                });
    }

    protected String getModelViewName() {
        return VIEW_NAME;
    }

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        result.add(new Breadcrumb("Biomes", "List of all biomes", VIEW_NAME));
        return result;
    }
}