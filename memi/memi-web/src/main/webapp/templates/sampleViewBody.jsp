<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="sample_ov">

    <div class="title_tab_p">
        <span class="subtitle">Sample overview <span>(${sample.sampleId})</span></span>

        <h2 class="fl_uppercase_title">${sample.sampleName}</h2>
    </div>

    <!-- Related publication, resources, links -->
    <div class="sidebar-allrel">
        <div id="sidebar-related">
            <h2>Related links</h2>
            <%--<span class="separator"></span>--%>
            <ul>
                <li>
                    <a title="Click to view entry on European Nucleotide Archive"
                       href="https://www.ebi.ac.uk/ena/data/view/${sample.sampleId}"
                       class="list_more">ENA website (${sample.sampleId})</a>
                </li>
            </ul>
        </div>
    </div>

    <%-- Show icon only for people are are logged in--%>
    <c:if test="${not empty model.submitter}">
        <!-- Private icon-->
        <c:if test="${!sample.public}">
            <p class="show_tooltip icon icon-functional" data-icon="L" title="Private data">Private data

            </p>
        </c:if>
        <c:if test="${sample.public}">
            <p class="show_tooltip icon icon-functional" data-icon="U" title="Public data">Public data </p>
        </c:if>
    </c:if>

    <h3 class="study_desc">Description</h3>

    <div class="output_form">

        <div class="result_row">
            <div class="result_row_label">Project name:</div>
            <div class="result_row_data"><a title="${sample.study.studyName}"
                                            href="<c:url value="${baseURL}/projects/${sample.study.studyId}"/>">${sample.study.studyName}
                (${sample.study.studyId})</a></div>
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
            <table class="table-heading result" id="associated-run-sample">
                <thead>
                <tr>
                    <th class="h_left">Run id</th>
                    <th>Pipeline version</th>
                    <th>Experiment type</th>
                    <th>Analysis date</th>
                    <th width="170px" class="xs_hide">Analysis results</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="analysisJob" items="${analysisJobs}" varStatus="status">
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${analysisJob.analysisStatus.analysisStatus == 'completed'}">
                                    <a title="Overview"
                                       href="<c:url value="${baseURL}/projects/${sample.study.studyId}/samples/${sample.sampleId}/runs/${analysisJob.externalRunIDs}/results/versions/${analysisJob.pipelineRelease.releaseVersion}"/>">${analysisJob.externalRunIDs}</a>
                                </c:when>
                                <c:otherwise>
                                    ${analysisJob.externalRunIDs}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${analysisJob.pipelineRelease.releaseVersion}</td>
                        <td>${analysisJob.experimentType.experimentType}</td>
                        <td>${analysisJob.completeTime}</td>
                        <td>
                            <c:set var="analysisStatus" value="${analysisJob.analysisStatus.analysisStatus}"
                                   scope="page"/>
                            <c:choose>
                                <c:when test="${analysisStatus == 'completed'}">
                                    <a title="Taxonomic analysis" class="list_sample"
                                       href="<c:url value="${baseURL}/projects/${sample.study.studyId}/samples/${sample.sampleId}/runs/${analysisJob.externalRunIDs}/results/versions/${analysisJob.pipelineRelease.releaseVersion}#ui-id-6"/>">Taxonomy </a>|
                                    <c:if test="${analysisJob.experimentType.experimentType != 'amplicon'}">
                                        <a title="Function analysis" class="list_sample"
                                           href="<c:url value="${baseURL}/projects/${sample.study.studyId}/samples/${sample.sampleId}/runs/${analysisJob.externalRunIDs}/results/versions/${analysisJob.pipelineRelease.releaseVersion}#ui-id-8"/>">Function </a>|
                                    </c:if>
                                    <a title="download results"
                                       class="icon icon-functional list_sample" data-icon="="
                                       href="<c:url value="${baseURL}/projects/${sample.study.studyId}/samples/${sample.sampleId}/runs/${analysisJob.externalRunIDs}/results/versions/${analysisJob.pipelineRelease.releaseVersion}#ui-id-10"/>"></a>
                                </c:when>
                                <c:otherwise>
                                    ${analysisStatus}
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $('#associated-run-sample').DataTable({
            "columnDefs": [ //add style to the different columns as direct css doesn't work
                            {className:"table-align-center", "targets": [0,1,3]},
                            {className:"table-align-center fl_capitalize", "targets": [2]},
                            {className:"xs_hide", "targets": [4]}
                        ],
            "bDeferRender":true,
            "bRetrieve":true,
            "oLanguage": {
                "sSearch":"Filter:"
            },
            "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
            "fnDrawCallback": function () {

                if (this.fnSettings().fnRecordsDisplay() > 10) {
                    $('.dataTables_length').css("display", "block");
                } else {
                    $('.dataTables_length').css("display", "none");//Remove show all dropdown when one single result page
                    $('.dataTables_paginate ').css("display", "none");//Remove pagination
                }
            }
        });

         //add class to filter to use for highlighting terms
        $("#associated-run-sample_filter input").addClass("filter_sp");
          // Highlight the search term in the table using the filter input, using jQuery Highlight plugin
        $('.filter_sp').keyup (function () {
             $("#associated-run-sample tr td").highlight($(this).val());
               // console.log($(this).val());
                $('#associated-run-sample tr td').unhighlight();// highlight more than just first character entered in the text box and reiterate the span to highlight
                $('#associated-run-sample tr td').highlight($(this).val());

        });
        // remove highlight when click on X (clear button)
        $('input[type=search]').on('search', function () {
                        $('#associated-run-sample tr td').unhighlight();
                });

    });

</script>
