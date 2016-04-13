<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Pipeline release archive</h2>
<p>You will find here the list of pipelines that were used to run the analyses.</p>


<c:forEach var="pipelineRelease" items="${pipelineReleases}" varStatus="status">

<p class="pi_arch_title"><span><a href="<c:url value="${baseURL}/pipelines/${pipelineRelease.releaseVersion}"/>">Pipeline v.${pipelineRelease.releaseVersion}</a></span> (${pipelineRelease.releaseDate})</p>
    <c:choose>
        <c:when test="${pipelineRelease.releaseVersion == '3.0'}">
                <ul><li>Updated tools: InterProScan, FraGeneScan, QIIME and Trimmomatic</li>
                    <li>Updated GO slim based on the analysis of X billion functional annotations</li>
                    <li>Integrated transfer RNA selection step</li>
                    <li>Improved quality control statistics (sequence length summary, GC and nucleotide distribution)</li></ul>
            </c:when>
        <c:when test="${pipelineRelease.releaseVersion == '2.0'}">
            <ul><li>Removed clustering step during the Quality control (QC)</li>
             <li>Added step to mask rRNA on reads (instead of removing reads with rRNA)</li>
             <li>Improved performance and tools update</li></ul>
        </c:when>
    </c:choose>
</c:forEach>
