<%
    final String url = request.getRequestURL().toString();
    final String context = request.getContextPath();
    final String requestRoot = url.substring(0, url.indexOf(context));
    // using getAttribute allows us to get the orginal url out of the page when a forward has taken place.
    String queryString = "?" + request.getAttribute("javax.servlet.forward.query_string");
    String requestURI = "" + request.getAttribute("javax.servlet.forward.request_uri");
    if (requestURI.equals("null")) {
        // using getAttribute allows us to get the orginal url out of the page when a include has taken place.
        queryString = "?" + request.getAttribute("javax.servlet.include.query_string");
        requestURI = "" + request.getAttribute("javax.servlet.include.request_uri");
        if (requestURI.equals("null")) {
            queryString = "?" + request.getQueryString();
            requestURI = request.getRequestURI();
        }
    }
    if (queryString.equals("?null")) queryString = "";
    String activePage = requestRoot + requestURI + queryString;
%>