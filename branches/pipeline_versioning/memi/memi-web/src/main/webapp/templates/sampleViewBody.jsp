<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h3 id="samples_id">Associated runs</h3>
<table border="1" class="result">
    <thead>
    <tr>
        <th scope="col" abbr="Pname">External run id</th>
        <th scope="col" abbr="Pname">Pipeline version</th>
        <th scope="col" abbr="Pname">Analysed on</th>
        <th scope="col" abbr="Analysis" width="170px">Analysis results</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="analysisJob" items="${analysisJobs}" varStatus="status">
        <tr>
            <td>${analysisJob.externalRunIDs}</td>
            <td>${analysisJob.pipelineRelease.releaseVersion}</td>
            <td>${analysisJob.completeTime}</td>
            <td>
                <a href="<c:url value="${baseURL}/sample/${sample.sampleId}?runId=${sample.id}#ui-id-6"/>"
                   class="list_sample" title="Taxonomy analysis">Taxonomy </a>|
                <a href="<c:url value="${baseURL}/sample/${sample.sampleId}?runId=${sample.id}#ui-id-8"/>"
                   class="list_sample" title="Function analysis">Function </a>| <a
                    class="icon icon-functional list_sample" data-icon="="
                    href="<c:url value="${baseURL}/sample/${sample.sampleId}?runId=${sample.id}#ui-id-10"/>"
                    class="list_sample" title="download results"></a>
            </td>
        </tr>

    </c:forEach>
    </tbody>
</table>
