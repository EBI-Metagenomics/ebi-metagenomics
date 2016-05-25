/*
 * Source code from blog post: Transparently supporting HTTP HEAD requests in Java and Spring MVC
 * http://blog.axelfontaine.eu/2009/09/transparently-supporting-http-head.html
 *
 * Copyright 2009 Axel Fontaine
 *
 * Parts of this code have been inspired by code found in the Servlet API 2.5.
 *
 * This code is provided free of charge. You agree to use and modify this code at your own risk.
 *
 * Feedback is always welcome !
 *
 */
package uk.ac.ebi.interpro.metagenomics.memi.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.HttpSessionRequiredException;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Servlet filter that presents a HEAD request as a GET. The application doesn't need to know the difference, as this
 * filter handles all the details.
 */
public class HttpHeadFilter implements Filter {

    private static final Log log = LogFactory.getLog(HttpHeadFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        //TODO: First try to fix the catch exceptions (IllegalStateException, HttpSessionRequiredException) down below
        // Get http session
//        HttpSession httpSession = httpServletRequest.getSession();
//        if (httpSession == null) {
//            // TODO: Not sure how to handle this at the moment
//
//        } else {
//            // Get search form session attribute
//            if (httpSession.getAttribute("ebiSearchForm") != null) {
//                // don't do anything
//            } else {
//                // attach a new instance of the session attribute
//                httpServletRequest.setAttribute("ebiSearchForm", new EBISearchForm());
//            }
//
//        }

        if (isHttpHead(httpServletRequest)) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            NoBodyResponseWrapper noBodyResponseWrapper = new NoBodyResponseWrapper(httpServletResponse);

            chain.doFilter(new ForceGetRequestWrapper(httpServletRequest), noBodyResponseWrapper);
            noBodyResponseWrapper.setContentLength();
        } else {
            try {
                // TODO: We need to investigate why and when those exceptions are thrown and fix them
                chain.doFilter(request, response);
            } catch (IllegalStateException e1) {
                log.error("Caught IllegalStateException: ", e1);
            } catch (HttpSessionRequiredException e2) {
                log.error("Caught HttpSessionRequiredException: ", e2);
            }
        }
    }

    @Override
    public void destroy() {
        //Do nothing
    }

    /**
     * Checks whether the HTTP method of this request is HEAD.
     *
     * @param request The request to check.
     * @return {@code true} if it is HEAD, {@code false} if it isn't.
     */
    private boolean isHttpHead(HttpServletRequest request) {
        return "HEAD".equals(request.getMethod());
    }

    /**
     * Request wrapper that lies about the Http method and always returns GET.
     */
    private class ForceGetRequestWrapper extends HttpServletRequestWrapper {
        /**
         * Initializes the wrapper with this request.
         *
         * @param request The request to initialize the wrapper with.
         */
        public ForceGetRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        /**
         * Lies about the HTTP method. Always returns GET.
         *
         * @return Always returns GET.
         */
        @Override
        public String getMethod() {
            return "GET";
        }
    }

    /**
     * Response wrapper that swallows the response body, leaving only the headers.
     */
    private class NoBodyResponseWrapper extends HttpServletResponseWrapper {
        /**
         * Outputstream that discards the data written to it.
         */
        private final NoBodyOutputStream noBodyOutputStream = new NoBodyOutputStream();

        private PrintWriter writer;

        /**
         * Constructs a response adaptor wrapping the given response.
         *
         * @param response The response to wrap.
         */
        public NoBodyResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return noBodyOutputStream;
        }

        @Override
        public PrintWriter getWriter() throws UnsupportedEncodingException {
            if (writer == null) {
                writer = new PrintWriter(new OutputStreamWriter(noBodyOutputStream, getCharacterEncoding()));
            }

            return writer;
        }

        /**
         * Sets the content length, based on what has been written to the outputstream so far.
         */
        void setContentLength() {
            super.setContentLength(noBodyOutputStream.getContentLength());
        }
    }

    /**
     * Outputstream that only counts the length of what is being written to it while discarding the actual data.
     */
    private class NoBodyOutputStream extends ServletOutputStream {
        /**
         * The number of bytes written to this stream so far.
         */
        private int contentLength = 0;

        /**
         * @return The number of bytes written to this stream so far.
         */
        int getContentLength() {
            return contentLength;
        }

        @Override
        public void write(int b) {
            contentLength++;
        }

        @Override
        public void write(byte buf[], int offset, int len) throws IOException {
            contentLength += len;
        }
    }
}