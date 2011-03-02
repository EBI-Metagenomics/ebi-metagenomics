package uk.ac.ebi.interpro.metagenomics.memi.tools;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.controller.CommonController;

import java.util.HashMap;

/**
 * Represents a Metagenomics {@link ModelAndView} factory. Use this factory if you want to create
 * more specific {@link ModelAndView}s.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class MemiModelAndViewFactory {

    private MemiModelAndViewFactory() {
    }

    public static ModelAndView getAccessDeniedMAV(String objectId) {
        ModelMap model = new ModelMap();
        model.addAttribute("objectId", objectId);
        return new ModelAndView(CommonController.ACCESS_DENIED_VIEW_NAME, model);
    }
}
