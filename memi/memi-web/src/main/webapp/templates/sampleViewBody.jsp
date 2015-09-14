<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<div class="title_tab_p">
    <span class="subtitle">Sample overview <span>(<a title="Click to view entry on European Nucleotide Archive"
                                                     href="https://www.ebi.ac.uk/ena/data/view/${sample.sampleId}"
                                                     class="ext">${sample.sampleId}</a>)</span></span>

    <h2 class="fl_uppercase_title">${sample.sampleName}</h2>
</div>

<h3 class="study_desc">Description</h3>

<div class="output_form">

    <div class="result_row">
        <div class="result_row_label">Project name:</div>
        <div class="result_row_data"><a title="${sample.study.studyName}" href="<c:url value="${baseURL}/projects/${sample.study.studyId}"/>">${sample.study.studyName} (${sample.study.studyId})</a></div>
    </div>

</div>

<h3 id="samples_id">Associated runs</h3>

<c:choose>
    <c:when test="${empty analysisJobs}">
        <p>
            No runs/results available yet.
        </p>
    </c:when>
    <c:otherwise>
        <table border="1" class="result">
            <thead>
            <tr>
                <th scope="col" class="h_left">Run id</th>
                <th scope="col">Pipeline version</th>
                <th scope="col">Experiment type</th>
                <th scope="col">Analysis date</th>
                <th scope="col" width="170px">Analysis results</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="analysisJob" items="${analysisJobs}" varStatus="status">
                <tr>
                    <td class="h_left"><a title="Overview"
                           href="<c:url value="${baseURL}/projects/${sample.study.studyId}/samples/${sample.sampleId}/runs/${analysisJob.externalRunIDs}/results/versions/${analysisJob.pipelineRelease.releaseVersion}"/>">${analysisJob.externalRunIDs}</a>
                    </td>
                    <td>${analysisJob.pipelineRelease.releaseVersion}</td>
                    <td><span class="capitalize">${analysisJob.experimentType}</span>
                    </td>
                    <td>${analysisJob.completeTime}</td>
                    <td>
                        <a title="Taxonomic analysis" class="list_sample"
                           href="<c:url value="${baseURL}/projects/${sample.study.studyId}/samples/${sample.sampleId}/runs/${analysisJob.externalRunIDs}/results/versions/${analysisJob.pipelineRelease.releaseVersion}#ui-id-6"/>">Taxonomy </a>|
                        <a title="Function analysis" class="list_sample"
                           href="<c:url value="${baseURL}/projects/${sample.study.studyId}/samples/${sample.sampleId}/runs/${analysisJob.externalRunIDs}/results/versions/${analysisJob.pipelineRelease.releaseVersion}#ui-id-8"/>">Function </a>|
                        <a title="download results"
                           class="icon icon-functional list_sample" data-icon="="
                           href="<c:url value="${baseURL}/projects/${sample.study.studyId}/samples/${sample.sampleId}/runs/${analysisJob.externalRunIDs}/results/versions/${analysisJob.pipelineRelease.releaseVersion}#ui-id-10"/>"></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>
