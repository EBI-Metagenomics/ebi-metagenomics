package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Represents a common controller, which currently holds request mappings
 * for simple pages2 that need no own controller class.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Controller
public class CommonController {
    @RequestMapping("/installationSitePage")
    public void installationSiteHandler() {
    }

    @RequestMapping("/info")
    public void infoHandler() {
    }

    @RequestMapping("/about")
    public void aboutHandler() {
    }
}
