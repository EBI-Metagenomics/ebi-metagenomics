<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
                    <a href="<c:url value="${baseURL}/studies/doSearch?search=Search&studyVisibility=ALL_PUBLISHED_STUDIES"/>"
                       title="View studies">Studies</a>
                </c:when>
                <c:otherwise>
                    <a href="<c:url value="${baseURL}/studies/doSearch?search=Search&studyVisibility=ALL_STUDIES"/>"
                       title="View studies">Studies</a>
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
        <li class="yellow"><a href="mailto:chrish@ebi.ac.uk?subject=Request from the MG portal" title="Contact us">Contact
            us</a></li>
        <li class="white"></li>
        <%-- <li class="orange"><a href="<c:url value="${baseURL}/help"/>" title="Help">Help</a></li>--%>
        <li class="black"><c:choose>
            <c:when test="${empty model.submitter}">
                <a href="<c:url value="${baseURL}/"/>" title="Login">Login</a>
            </c:when>
            <c:otherwise>
                <a href="<c:url value="${baseURL}/logout"/>" title="logout">Logout</a>
            </c:otherwise>
        </c:choose>
            <%--<button id="create-user">Login</button>--%></li>
    </ul>


</div>
