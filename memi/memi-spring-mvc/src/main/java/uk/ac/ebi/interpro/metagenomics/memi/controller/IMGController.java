package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

/**
 * Represents a interface that defines which methods have to be implemented
 * for each Metagenomics controller.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public interface IMGController {

    public ModelAndView doGet(ModelMap model);
}
