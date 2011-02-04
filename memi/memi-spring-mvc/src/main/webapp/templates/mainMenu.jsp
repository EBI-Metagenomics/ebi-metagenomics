<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
Created by Maxim Scheremetjew, EMBL-EBI, InterPro
Date: 04-Jan-2011
MG portal main menu template
--%>
<div style="margin-top:80px"/>
<div align="center">
    <a href="<c:url value="${baseURL}/index"/>" title="Home">Home</a>
    <a href="<c:url value="${baseURL}/viewStudies/doSearch?searchTerm=&studyVisibility=PUBLIC&search=Search"/>" title="View studies">View studies</a>
    <a href="<c:url value="${baseURL}/viewSamples/doSearch?searchTerm=&sampleVisibility=PUBLIC&search=Search"/>" title="View samples">View samples</a>

    <a href="<c:url value="${baseURL}/info"/>" title="About">About</a>
    <a href="mailto:maxim@ebi.ac.uk?cc=maxim.scheremetjew@googlemail.com&subject=Request from the MG portal">Contact
        us</a>
</div>
<div style="margin-top:60px"/>