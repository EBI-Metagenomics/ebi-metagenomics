<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Pipeline Release Archive</h2>

<ul>
    <c:forEach var="pipelineRelease" items="${pipelineReleases}" varStatus="status">
        <li><a title="" href="<c:url value="${baseURL}/pipelines/${pipelineRelease.releaseVersion}"/>">${pipelineRelease.releaseVersion} (${pipelineRelease.releaseDate})</a></li>
    </c:forEach>
</ul>