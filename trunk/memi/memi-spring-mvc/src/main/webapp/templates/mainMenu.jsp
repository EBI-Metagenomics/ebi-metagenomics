<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="loginDialog.jsp" %>
<div class="top-nav">
    <div class="top-menu">
        <ul>
            <li class="grey"><a href="<c:url value="${baseURL}/"/>" title="Home">Home</a></li>
            <li class="ebigrey"><a href="<c:url value="${baseURL}/submit"/>" class="more_desc" title="Submit data">Submit
                data</a></li>
            <%--changed to make it EBI compliant --%>
            <li class="blue">
                <c:choose>
                    <c:when test="${empty model.submitter}">
                        <a href="<c:url value="${baseURL}/projects/doSearch?search=Search&studyVisibility=ALL_PUBLISHED_PROJECTS"/>"
                           title="View projects">Projects</a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value="${baseURL}/projects/doSearch?search=Search&studyVisibility=ALL_PROJECTS"/>"
                           title="View projects">Projects</a>
                    </c:otherwise>
                </c:choose>
            </li>
            <li class="greyblue">
                <c:choose>
                    <c:when test="${empty model.submitter}">
                        <a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=&sampleVisibility=ALL_PUBLISHED_SAMPLES&search=Search&startPosition=0"/>"
                           title="View samples">Samples</a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=&sampleVisibility=ALL_SAMPLES&search=Search&startPosition=0"/>"
                           title="View samples">Samples</a>
                    </c:otherwise>
                </c:choose>
            </li>


            <li class="green"><a href="<c:url value="${baseURL}/info"/>" title="About us">About</a></li>
            <li class="ebigreen"><a href="<c:url value="${baseURL}/contact"/>" title="Contact us">Contact</a></li>
            <%--changed to make it EBI compliant --%>
            </li>
        </ul>
    </div>
    <div class="top-login">
        <c:choose>
            <c:when test="${empty model.submitter}">
                <span id="login"> Not logged in | <a id="loginLinkId" href="<c:url value="${baseURL}/"/>" title="Login">login</a>
                <a id="jqueryLogin" href="" title="Login">JQuery Login</a></span>
            </c:when>
            <c:otherwise>
                <c:out value="${model.submitter.firstName} ${model.submitter.surname}"/>
                <c:set var="enaUrlParam" value="<%=requestRoot%>"/>
                <c:url var="editPrefsUrl"
                       value="${model.propertyContainer.enaSubmissionURL.editPrefsLink}">
                    <c:param name="url" value="${enaUrlParam}"/>
                </c:url>
                (<a href="<c:out value="${editPrefsUrl}"/>" title="Edit preferences">edit</a>)
                <span id="logout">
                   |  <a href="<c:url value="${baseURL}/logout"/>" title="logout">logout</a>
                </span>
            </c:otherwise>
        </c:choose>
    </div>
</div>