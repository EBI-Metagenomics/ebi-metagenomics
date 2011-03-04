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
    <li class="yellow"><a href="mailto:chrish@ebi.ac.uk?subject=Request from the MG portal" title="Contact us" >Contact</a></li>
    <li class="white"></li>
    <%-- <li class="orange"><a href="<c:url value="${baseURL}/help"/>" title="Help">Help</a></li>--%>
     <%--<li class="black">  <c:choose>
        <c:when test="${empty model.submitter}">
            <a href="<c:url value="${baseURL}/index"/>" title="Login">Login</a>
        </c:when>
        <c:otherwise>
            <a href="<c:url value="${baseURL}/logout"/>" title="logout">Logout</a>
        </c:otherwise>
    </c:choose>--%>
    </li>
    </ul>
</div>
<div class="top-login">
<c:choose>
        <c:when test="${empty model.submitter}">
           <span id="login"> Not logged in | <a href="<c:url value="${baseURL}/"/>" title="Login">login</a></span>
        </c:when>
        <c:otherwise>
          <span id="logout"> <c:out value="${model.submitter.firstName} ${model.submitter.surname}"/> | <a href="<c:url value="${baseURL}/logout"/>" title="logout">logout</a></span>
        </c:otherwise>
</c:choose></div>
</div>