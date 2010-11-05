package uk.ac.ebi.interpro.metagenomics.memi.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Represents the controller for demo Hello world test. Used the tutorial of the following link:<br>
 * http://maestric.com/doc/java/spring/hello_world
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class HelloWorldController implements Controller {
    public ModelAndView handleRequest(HttpServletRequest request,
                                      HttpServletResponse response) throws ServletException, IOException {

        String aMessage = "Hello World MVC!";

        ModelAndView modelAndView = new ModelAndView("HelloWorldPage");
        modelAndView.addObject("message", aMessage);

        return modelAndView;
    }

}
