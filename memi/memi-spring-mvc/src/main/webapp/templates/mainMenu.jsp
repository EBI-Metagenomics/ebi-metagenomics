<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="top-menu">
    <ul>
    <li class="grey"><a href="<c:url value="${baseURL}/index"/>" title="Home">Home</a></li>
    <li class="pink"><c:choose>
    <c:when test="${empty mgModel.submitter}">
    <a href="<c:url value="${baseURL}/login"/>" class="more_desc" title="Submit data">Submit data</a>
    </c:when>
    <c:otherwise>
    <a href="<c:url value="${baseURL}/submissionForm"/>" class="more_desc" title="Submit data">Submit data</a>
    </c:otherwise>
    </c:choose></li>
    <li class="blue">
        <c:choose>
            <c:when test="${empty mgModel.submitter}">
                <a href="<c:url value="${baseURL}/viewStudies/doSearch?searchTerm=&studyVisibility=ALL_PUBLISHED_STUDIES&search=Search"/>" title="View studies">Studies</a>
            </c:when>
            <c:otherwise>
                <a href="<c:url value="${baseURL}/viewStudies/doSearch?searchTerm=&studyVisibility=ALL_STUDIES&search=Search"/>" title="View studies">Studies</a>
            </c:otherwise>
        </c:choose>
    </li>
    <li class="greyblue">
        <c:choose>
            <c:when test="${empty mgModel.submitter}">
                <a href="<c:url value="${baseURL}/viewSamples/doSearch?searchTerm=&sampleVisibility=ALL_PUBLISHED_SAMPLES&search=Search"/>" title="View samples" >Samples</a>
            </c:when>
            <c:otherwise>
                <a href="<c:url value="${baseURL}/viewSamples/doSearch?searchTerm=&sampleVisibility=ALL_SAMPLES&search=Search"/>" title="View samples" >Samples</a>
            </c:otherwise>
        </c:choose>
    </li>
    <li class="green"><a href="<c:url value="${baseURL}/info"/>" title="About us">About</a></li>
    <li class="yellow"><a href="mailto:chrish@ebi.ac.uk?subject=Request from the MG portal" title="Contact us" >Contact us</a></li>
    <li class="orange"><a href="<c:url value="${baseURL}/help"/>" title="Help">Help</a></li>
    <li class="black">  <c:choose>
        <c:when test="${empty mgModel.submitter}">
            <a href="<c:url value="${baseURL}/index"/>" title="Login">Login</a>
        </c:when>
        <c:otherwise>
            <a href="<c:url value="${baseURL}/logout"/>">Logout</a>
        </c:otherwise>
    </c:choose>
    <%--<button id="create-user">Login</button>--%></li>
    </ul>



</div>
