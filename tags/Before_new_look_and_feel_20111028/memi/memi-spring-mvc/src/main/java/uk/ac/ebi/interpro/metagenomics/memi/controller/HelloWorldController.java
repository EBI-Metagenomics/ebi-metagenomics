package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Represents the controller for demo Hello world test. Used the tutorial of the following link:<br>
 * http://maestric.com/doc/java/spring/hello_world
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping("/helloworld")
public class HelloWorldController {
    private final static Log log = LogFactory.getLog(HelloWorldController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView helloWorld() {
        log.info("Building the hello world spring_model...");
        ModelAndView mav = new ModelAndView("HelloWorldPage");
        String msg = "Hello World MVC!";
        mav.addObject("message", msg);
        log.info("Finished buildup of spring_model.");
        return mav;
    }
}
