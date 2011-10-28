package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Represents a interface that defines which methods have to be implemented
 * for each Metagenomics controller. MG stands for Metagenomics.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public interface IMGController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGet(ModelMap model);
}
