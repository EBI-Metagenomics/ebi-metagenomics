<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
Created by Maxim Scheremetjew, EMBL-EBI, InterPro
Date: 04-Jan-2011
MG portal main menu template
--%>
<%@include file="loginJQueryTemplate.jsp" %>
<div style="margin-top:80px"/>
<div align="center">
    <a href="<c:url value="${baseURL}/index"/>" title="Home">Home</a>
    <a href="<c:url value="${baseURL}/viewStudies/doSearch?searchTerm=&studyVisibility=ALL_PUBLISHED_STUDIES&search=Search"/>"
       title="View studies">View studies</a>
    <a href="<c:url value="${baseURL}/viewSamples/doSearch?searchTerm=&sampleVisibility=ALL_PUBLISHED_SAMPLES&search=Search"/>"
       title="View samples">View samples</a>
    <a href="<c:url value="${baseURL}/info"/>" title="About">About</a>
    <a href="mailto:chrish@ebi.ac.uk?subject=Request from the MG portal">Contact
        us</a>
    <c:choose>
        <c:when test="${empty mgModel.submitter}">
            <a href="<c:url value="${baseURL}/index"/>" title="Login">Login</a>
        </c:when>
        <c:otherwise>
            <a href="<c:url value="${baseURL}/logout"/>">Logout</a>
        </c:otherwise>
    </c:choose>
    <%--<button id="create-user">Login</button>--%>
</div>
<div style="margin-top:60px"/>