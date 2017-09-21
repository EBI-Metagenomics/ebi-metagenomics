<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="fragment-quality">
    <%--BEGIN READS SECTION   --%>

    <%--<h3>Submitted nucleotide data</h3>--%>
    <c:choose>
        <c:when test="${empty model.sample.analysisCompleted}">
            <div class="msg_error">Analysis in progress.</div>
        </c:when>

        <%--<a target="_blank"--%>
        <%--href="${pageContext.request.contextPath}/projects/${model.study.studyId}/download/${downloadSection.key}/pca"--%>
        <%--title="Click to view principal component analysis (PCA) for all study runs">--%>

        <c:when test="${not empty model.sample.analysisCompleted && !model.analysisStatus.qualityControlTabDisabled}">
            <p>This page gives you information regarding metagenomic community diversity estimation and information
                which allow comparisons between all study runs. The following plots illustrate the taxa abundance
                distribution, and use OTU counts to compute diversity indices, including several estimates of the total
                diversity of the community sampled.
                Additionally, estimates are provided for the number of individuals that would need to be sequenced in
                order to see a given fraction of the total population diversity (based on the assumption of an
                underlying Poisson-log-normal taxa abundance distribution). These provide guidance for the sequencing
                effort likely to be required for a more complete characterisation of the microbial community of
                interest.</p>
            <div style="display:block; overflow: auto;">
                <c:url var="tadplotsFile"
                       value="${baseURL}/projects/${projectId}/samples/${sampleId}/runs/${runId}/results/versions/${versionId}/stats/tad-plots"
                       scope="request">
                </c:url>
                <p><img src="<c:out value="${tadplotsFile}"/>"/></p>
                <c:url var="heatmapFile"
                       value="${baseURL}/projects/${projectId}/samples/${sampleId}/runs/${runId}/results/versions/${versionId}/stats/heatmap"
                       scope="request">
                </c:url>
                <p><img src="<c:out value="${heatmapFile}"/>"/></p>
            </div>
        </c:when>
        <c:otherwise>
            <div class="msg_error">No result files have been associated with this sample.</div>
        </c:otherwise>
    </c:choose>

</div>