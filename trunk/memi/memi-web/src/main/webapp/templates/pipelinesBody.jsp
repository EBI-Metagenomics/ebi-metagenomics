<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Pipeline release archive</h2>
<p>You will find here the list of pipelines that were used to analyse our data, classified by public release date.</p>
<ul>
    <c:forEach var="pipelineRelease" items="${pipelineReleases}" varStatus="status">
        <li><a title="" href="<c:url value="${baseURL}/pipelines/${pipelineRelease.releaseVersion}"/>">Pipeline v.${pipelineRelease.releaseVersion}</a>  (${pipelineRelease.releaseDate})</li>
    </c:forEach>
</ul>