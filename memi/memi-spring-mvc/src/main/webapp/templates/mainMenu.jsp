<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="top-nav">
    <div class="top-menu">
        <ul>
            <li class="grey"><a href="<c:url value="${baseURL}/"/>" title="Home">Home</a></li>
            <li class="pink"><c:choose>
                <c:when test="${empty model.submitter}">
                    <a href="<c:url value="${baseURL}/login"/>" class="more_desc" title="Submit data">Submit data</a>
                </c:when>
                <c:otherwise>
                    <a href="<c:url value="${baseURL}/submit"/>" class="more_desc" title="Submit data">Submit data</a>
                </c:otherwise>
            </c:choose></li>
            <li class="blue">
                <c:choose>
                    <c:when test="${empty model.submitter}">
                        <a href="<c:url value="${baseURL}/studies/doSearch?search=Search&studyVisibility=ALL_PUBLISHED_PROJECTS"/>"
                           title="View studies">Projects</a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value="${baseURL}/studies/doSearch?search=Search&studyVisibility=ALL_PROJECTS"/>"
                           title="View studies">Projects</a>
                    </c:otherwise>
                </c:choose>
            </li>
            <li class="greyblue">
                <c:choose>
                    <c:when test="${empty model.submitter}">
                        <a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=&sampleVisibility=ALL_PUBLISHED_SAMPLES&search=Search"/>"
                           title="View samples">Samples</a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=&sampleVisibility=ALL_SAMPLES&search=Search"/>"
                           title="View samples">Samples</a>
                    </c:otherwise>
                </c:choose>
            </li>


            <li class="green"><a href="<c:url value="${baseURL}/info"/>" title="About us">About</a></li>
            <li class="yellow"><a href="<c:url value="${baseURL}/contact"/>" title="Contact us">Contact</a></li>

            </li>
        </ul>
    </div>
    <div class="top-login">
        <c:url var="editPrefsUrl"
               value="http://jweb-1a.ebi.ac.uk:21040/embl/genomes/submission/external-update-link.jsf">
            <c:param name="url" value="http://localhost:8082/metagenomics/"/>
        </c:url>
        <c:choose>
            <c:when test="${empty model.submitter}">
                <span id="login"> Not logged in | <a href="<c:url value="${baseURL}/"/>" title="Login">login</a></span>
            </c:when>
            <c:otherwise>
                <c:out value="${model.submitter.firstName} ${model.submitter.surname}"/>
                (<a href="<c:out value="${editPrefsUrl}"/>" title="Edit preferences">edit</a>)
                <span id="logout">
                    <a href="<c:url value="${baseURL}/logout"/>" title="logout"> | logout</a>
                </span>
            </c:otherwise>
        </c:choose>
    </div>
</div>