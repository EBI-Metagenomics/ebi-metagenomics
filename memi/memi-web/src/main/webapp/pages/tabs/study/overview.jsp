<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%--Page variable which is used several time within this page. Used for not specified study attributes.--%>
<c:set var="notGivenId" value="(not given)"/>
<c:set var="study" value="${model.study}"/>

<div id="project_ov">

    <%-- Show icon only for people are are logged in--%>
    <c:if test="${not empty model.submitter}">
        <!-- Private icon-->
        <c:if test="${!study['public']}">
            <p class="show_tooltip icon icon-functional" data-icon="L" title="Private data">Private data
                <c:choose>
                    <c:when test="${not empty study.publicReleaseDate}">
                        <c:set var="publicReleaseDate" value="${study.publicReleaseDate}"/>
                 <span class="list_warn">&nbsp;(will be published on the <fmt:formatDate value="${publicReleaseDate}"
                                                                                         pattern="dd-MMM-yyyy"/>)</span>
                    </c:when>
                    <c:otherwise>
                        <c:set var="publicReleaseDate" value="${notGivenId}"/>
                    </c:otherwise>
                </c:choose>
            </p>
        </c:if>
        <c:if test="${study['public']}">
            <p class="show_tooltip icon icon-functional" data-icon="U" title="Public data">Public data </p>
        </c:if>
    </c:if>


    <p class="project_upd_date">
        Last updated: ${study.formattedLastReceived}</p>

    <!--Google map with sample locations - add the Google map if the JSON data file is available-->
    <c:if test="${model.googleMapDataAvailable}">
        <div id="map-container">
            <div id="map_project"></div>
        </div>
    </c:if>

    <tags:publications publications="${study.publications}" relatedPublications="${model.relatedPublications}"
                       relatedLinks="${model.relatedLinks}"/>
    <!-- Related publication, resources, links -->
    <c:if test="${study['public']}">
        <div class="sidebar-allrel">
            <div id="sidebar-related">
                <h2>Related links</h2>
                    <%--<span class="separator"></span>--%>
                <ul>
                    <!--ENA links -->
                    <li>
                        <a title="Click to view entry on European Nucleotide Archive"
                           href="https://www.ebi.ac.uk/ena/data/view/${study.studyId}"
                           class="list_more">ENA website (${study.studyId})</a>
                    </li>
                </ul>
            </div>
        </div>
    </c:if>
    <!--/ Related publication, resources, links -->


    <h3 class="study_desc">Description</h3>

    <div class="output_form">
        <c:choose>
            <c:when test="${not empty study.studyAbstract}">
                <c:set var="studyAbstract" value="${study.studyAbstract}"/>
            </c:when>
            <c:otherwise>
                <c:set var="studyAbstract" value="${notGivenId}"/>
            </c:otherwise>
        </c:choose>

        <p class="study_desc_text"><c:out value="${studyAbstract}"/></p>


        <c:choose>
            <c:when test="${not empty study.experimentalFactor}">
                <c:choose>
                    <c:when test="${study.experimentalFactor=='none'}"></c:when>
                    <c:otherwise>
                        <c:set var="experimentalFactor" value="${study.experimentalFactor}"/>
                        <h4>Experimental factor: <c:out value="${experimentalFactor}"/></h4>
                    </c:otherwise></c:choose>
            </c:when>
            <c:otherwise>
                <c:set var="experimentalFactor" value="${notGivenId}"/>
            </c:otherwise>
        </c:choose>

        <c:if test="${not empty study.biome}">
            <h4>Classification: <c:out
                    value="${fn:replace(fn:replace(study.biome.lineage,'root:',''),':',' > ')}"/></h4>
        </c:if>
    </div>

    <h3>Submitter details</h3>

    <div class="output_form">
        <c:set var="centreName" value="${study.centreName}"/>
        <c:choose>
            <c:when test="${not empty study.centreName}">
                <div class="result_row">
                    <div class="result_row_label">Institute:</div>
                    <div class="result_row_data"><c:out value="${centreName}"/></div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="result_row">
                    <div class="result_row_label">Institute:</div>
                    <c:set var="centreName" value="${notGivenId}"/></div>
            </c:otherwise>
        </c:choose>

    </div>

    <%--<h3>Other information</h3>--%>


    <h3 id="samples_id">Associated runs</h3>
    <%-- <c:if test="${isDialogOpen==false}">
        <p><span style="color:red">No export data available for that(these) sample(s)!</span></p>
    </c:if>
    <div>
        <a href="<c:url value="${baseURL}/project/${study.studyId}/doExport/"/>">Export more detailed sample info to CSV</a>
    </div>--%>

    <div class="export">
        <a href="<c:url value="${baseURL}/projects/${study.studyId}/overview/doExport"/>" id="csv_plus"
           title="<spring:message code="studyView.download.anchor.title"/>">
            <spring:message code="download.anchor.label.detailed"/></a>
    </div>

    <table class="table-heading result" id="associated-run">
        <thead>
        <tr>
            <th>Sample Name</th>
            <th>Sample ID</th>
            <th>Run ID</th>
            <th>Experiment type</th>
            <th>Instrument model</th>
            <th>Version</th>
            <th width="170px">Analysis results</th>
        </tr>
        </thead>
        <c:choose>
            <c:when test="${run.analysisStatus == 'completed'}">
                <a title="Taxonomic analysis" class="list_sample"
                   href="<c:url value="${baseURL}/projects/${study.studyId}/samples/${run.externalSampleId}/runs/${run.externalRunIds}/results/versions/${run.releaseVersion}#ui-id-6"/>">Taxonomy </a>|
                <c:if test="${run.experimentType != 'amplicon'}">
                    <a title="Function analysis" class="list_sample"
                       href="<c:url value="${baseURL}/projects/${study.studyId}/samples/${run.externalSampleId}/runs/${run.externalRunIds}/results/versions/${run.releaseVersion}#ui-id-8"/>">Function </a>|
                </c:if>
                <a title="Download results"
                   class="icon icon-functional list_sample" data-icon="="
                   href="<c:url value="${baseURL}/projects/${study.studyId}/samples/${run.externalSampleId}/runs/${run.externalRunIds}/results/versions/${run.releaseVersion}#ui-id-12"/>"></a>
            </c:when>
            <c:otherwise>
                ${run.analysisStatus}
            </c:otherwise>
        </c:choose>
        <%--</td>--%>
        <%--</tr>--%>
        <%--</c:forEach>--%>
        <%--</tbody>--%>
    </table>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $('#associated-run').DataTable({
            "autoWidth": false,// remove the auto width calculation as it doesn't resize properly with ajax call
            "columnDefs": [ //add style to the different columns as direct css doesn't work
                {className: "table_xs_text", "targets": [0]},
                {className: "table-align-center table_xs_text", "targets": [1, 2]},
                {className: "table-align-center fl_capitalize", "targets": [3]},
                {className: "table-align-center xs_hide", "targets": [4, 5]},
                {className: "xs_hide", "targets": [6]}
            ],
            "oLanguage": {
                "sSearch": "Filter:"
            },
            "lengthMenu": [[25, 50, 100, -1], [25, 50, 100, "All"]],
            //fnDrawCallBack doesn't work, doesn't wait for data to be loaded with Ajax
            /*           "fnDrawCallback": function () {
             if (this.fnSettings().fnRecordsDisplay() > 10) {
             $('.dataTables_length').css("display", "block");
             } else {
             $('.dataTables_length').css("display", "none");//Remove show all dropdown when one single result page
             $('.dataTables_paginate ').css("display", "none");//Remove pagination
             }
             },*/
            "processing": true,
            "ajax": {
                "type": "GET",
                "url": "<c:url value="${baseURL}/projects/${study.studyId}/overview/runs"/>",
                "contentType": 'application/json; charset=utf-8',
                "data": function (data) {
                    return JSON.stringify(data);
                }
            },
            "columns": [
                { // render sample name with hyperlink
                    "data": function (data, type, row, meta) {
                        return '<a class="fl_uppercase_title" title="Sample ' + data.externalSampleId + '" href="' + '<c:url value="${baseURL}/projects/${study.studyId}/samples/"/>' + data.externalSampleId + '">' + data.sampleName + '</a>';
                    }
                },
                {"data": "externalSampleId"},// render external sample id
//                {"data": "externalRunIds"},
                { // render external run id with hyperlink
                    "data": function (data, type, row, meta) {
                        var secondaryAcc = '';
                        if (data.externalRunIds.startsWith("GCA")) {
                            secondaryAcc = ' / ' + data.secondaryAccession;
                        }
                        return '<a title="Run ' + data.externalSampleId + '" href="' + '<c:url value="${baseURL}/projects/${study.studyId}/samples/"/>' + data.externalSampleId + '/runs/' + data.externalRunIds + '/results/versions/' + data.releaseVersion + '">' + data.externalRunIds + secondaryAcc + '</a>';
                    }
                },
                {"data": "experimentType"}, // render experiment type
                {"data": "instrumentModel"}, // render instrument model
                { // render pipeline version
                    "data": function (data, type, row, meta) {
                        return '<a title="Pipeline version ' + data.releaseVersion + '" href="' + '<c:url value="${baseURL}/pipelines/"/>' + data.releaseVersion + '">' + data.releaseVersion + '</a>';
                    }
                },
                // render analysis results
                { // render hyperlinks to analysis results
                    "data": function (data, type, row, meta) {
                        if (data.analysisStatus == 'completed') {
                            var outputStr = '<a title="Taxonomic analysis" class="list_sample" href="' + '<c:url value="${baseURL}/projects/${study.studyId}/samples/"/>' + data.externalSampleId + '/runs/' + data.externalRunIds + '/results/versions/' + data.releaseVersion + '#ui-id-6">Taxonomy </a>|';
                            if (data.experimentType != 'amplicon') {
                                outputStr += '<a title="Function analysis" class="list_sample" href="' + '<c:url value="${baseURL}/projects/${study.studyId}/samples/"/>' + data.externalSampleId + '/runs/' + data.externalRunIds + '/results/versions/' + data.releaseVersion + '#ui-id-8"> Function </a>|';
                            }
                            outputStr += ' <a title="Download results" class="icon icon-functional list_sample" data-icon="=" href="' + '<c:url value="${baseURL}/projects/${study.studyId}/samples/"/>' + data.externalSampleId + '/runs/' + data.externalRunIds + '/results/versions/' + data.releaseVersion + '#ui-id-12"></a>';
                            return outputStr;
                        } else {
                            return data.analysisStatus;
                        }
                    }
                }
            ]
        });
        $("#associated-run_filter input").addClass("filter_sp");

        // Highlight the search term in the table using the filter input, using jQuery Highlight plugin
        $('.filter_sp').keyup(function () {
            $("#associated-run tr td").highlight($(this).val());
            // console.log($(this).val());
            $('#associated-run tr td').unhighlight();// highlight more than just first character entered in the text box and reiterate the span to highlight
            $('#associated-run tr td').highlight($(this).val());

        });
        // remove highlight when click on X (clear button)
        $('input[type=search]').on('search', function () {
            $('#associated-run tr td').unhighlight();
        });

    });

</script>

<c:if test="${model.googleMapDataAvailable}">
    <script>
        //jquery solution for full screen map - https://gist.github.com/lou/1550454
        $(function () {
            var map = new google.maps.Map(document.getElementById("map_project"), {});

            var googleMapWidth = $("#map_project").css('width');
            var googleMapHeight = $("#map_project").css('height');

            $('#btn-enter-full-screen').click(function () {
                $("#map-container").css("position", 'fixed').
                css('top', 0).
                css('left', 0).
                css('z-index', 1000).
                css("width", '100%').
                css("background-color", 'rgba(0, 0, 0, 0.6)').
                css("height", '100%');

                $("#map_project").css("position", 'relative').
                css("height", '94%');//not 100% to avoid cropping map at the bottom

                google.maps.event.trigger(map, 'resize');

                // Gui
                $('#btn-enter-full-screen').toggle();
                $('#btn-exit-full-screen').toggle();
                return false;
            });

            $('#btn-exit-full-screen').click(function () {
                $("#map-container").css("position", 'relative').
                css('top', 0).
                css('z-index', 1).
                css("background-color", 'transparent').
                css("height", 394);

                $("#map_project").css("position", 'relative').
                css("height", '360');

                google.maps.event.trigger(map, 'resize');

                // Gui
                $('#btn-enter-full-screen').toggle();
                $('#btn-exit-full-screen').toggle();
                return false;
            });
        });
    </script>
</c:if>