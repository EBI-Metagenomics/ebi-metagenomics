<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="notGivenId" value="(not given)" scope="session"/>

<div id="fragment-overview">


    <tags:publications publications="${model.sample.publications}" relatedPublications="${model.relatedPublications}"
                       relatedLinks="${model.relatedLinks}"/>


    <!-- Related publication, resources, links -->
    <div class="sidebar-allrel">
    <div id="sidebar-related">
    <h2>Related links</h2>
    <%--<span class="separator"></span>--%>
    <ul>
    <li>
        <a title="Click to view entry on European Nucleotide Archive" href="https://www.ebi.ac.uk/ena/data/view/${model.analysisJob.secondaryAccession}"
                                                      class="list_more">ENA website (${model.analysisJob.secondaryAccession})</a>
    </li>
    </ul>
    </div>
    </div>
    <!--/ Related publication, resources, links -->

    <div class="main_tab_content">
        <!-- Description -->
        <h3 id="sample_desc">Description</h3>


        <div class="output_form">
            <div class="result_row">
                <div class="result_row_label">Sample name:</div>
                <div class="result_row_data"><a title="${model.sample.sampleName}" href="<c:url value="${baseURL}/projects/${study.studyId}/samples/${model.sample.sampleId}"/>">${model.sample.sampleName} (${model.sample.sampleId})</a></div>
            </div>

            <div class="result_row">
                <div class="result_row_label">Project name:</div>
                <div class="result_row_data"><a title="${study.studyName}" href="<c:url value="${baseURL}/projects/${study.studyId}"/>">${study.studyName} (${study.studyId})</a></div>
            </div>
        </div>
        <!--/ Description -->

        <!-- Data analysis -->
        <h3>Data analysis</h3>

        <div class="output_form" >
            <div class="result_row">
                <div class="result_row_label">Experiment type:</div>
                <div class="result_row_data"> ${model.analysisJob.experimentType.experimentType}</div>
            </div>
            <div class="result_row">
                <div class="result_row_label">Pipeline version:</div>
                <div class="result_row_data"><a title="Pipeline release" href="<c:url value="${baseURL}/pipelines/${model.analysisJob.pipelineRelease.releaseVersion}"/>">${model.analysisJob.pipelineRelease.releaseVersion}</a></div>
            </div>
            <div class="result_row">
                <div class="result_row_label">Analysis date:</div>
                <div class="result_row_data">${model.analysisJob.completeTime}</div>
            </div>
        </div>
        <!--/Data analysis -->

    </div>
</div>