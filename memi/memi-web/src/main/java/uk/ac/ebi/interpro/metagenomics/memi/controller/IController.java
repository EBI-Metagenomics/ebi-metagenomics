package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Represents an interface that defines which methods have to be implemented
 * for each controller implementation class.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public interface IController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGet(ModelMap model);
}
