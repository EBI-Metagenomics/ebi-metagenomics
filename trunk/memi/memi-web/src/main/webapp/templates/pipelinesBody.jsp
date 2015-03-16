<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Pipeline release archive</h2>
<p>You will find here the list of pipelines that were used to analyse our data, classified by public release date.</p>


<c:forEach var="pipelineRelease" items="${pipelineReleases}" varStatus="status">

<a href="<c:url value="${baseURL}/pipelines/${pipelineRelease.releaseVersion}"/>" class="pi_arch_title"><h3>Pipeline v.${pipelineRelease.releaseVersion} <span>(${pipelineRelease.releaseDate})</span></h3></a>
    <c:choose>
        <c:when test="${pipelineRelease.releaseVersion == '2.0'}">
            <ul><li>remove clustering step during the Quality control (QC)</li>
             <li>added step to mask rRNA on reads (instead of removing reads with rRNA)</li>
             <li>improve performance and tools update</li></ul>
        </c:when>
    </c:choose>
</c:forEach>
