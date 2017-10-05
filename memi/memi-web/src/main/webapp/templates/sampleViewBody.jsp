<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAivAQNT-w_7wzIJ9nf7E9GTOzn1mkDGzE"
        type="text/javascript"></script>

<script defer type="text/javascript">
    function initialize(lat, long) {
        var latlng = new google.maps.LatLng(lat, long);
        google.maps.MarkerOptions
        var myOptions = {
            zoom: 4,
            center: latlng,
            mapTypeId: google.maps.MapTypeId.ROADMAP,
            streetViewControl: false
        };
        var map = new google.maps.Map(document.getElementById("map_canvas"),
                myOptions);
        var marker = new google.maps.Marker({
            position: latlng,
            map: map
        });
    }
</script>

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
        <c:if test="${!sample['public']}">
            <p class="show_tooltip icon icon-functional" data-icon="L" title="Private data">Private data

            </p>
        </c:if>
        <c:if test="${sample['public']}">
            <p class="show_tooltip icon icon-functional" data-icon="U" title="Public data">Public data </p>
        </c:if>
    </c:if>

    <!-- Description -->
    <h3 class="study_desc">Description</h3>

    <div class="output_form">

        <c:if test="${not empty sample.sampleDescription}">
            <c:set var="sampleDescription" value="${sample.sampleDescription}"/>
            <p class="fl_capitalize sample_desc"><c:out value="${sampleDescription}"/></p>
        </c:if>

        <c:choose>
            <c:when test="${not empty sample.biome}">
                <c:set var="sampleClassification"
                       value="${fn:replace(fn:replace(sample.biome.lineage,'root:',''),':',' > ')}"/>
            </c:when>
            <c:otherwise>
                <c:set var="sampleClassification" value="(not given)"/>
            </c:otherwise>
        </c:choose>

        <div class="result_row">
            <div class="result_row_label">Classification:</div>
            <div class="result_row_data"><c:out value="${sampleClassification}"/></div>
        </div>

        <div class="result_row">
            <div class="result_row_label">Project name:</div>
            <div class="result_row_data"><a title="${study.studyName}"
                                            href="<c:url value="${baseURL}/projects/${study.studyId}"/>">${study.studyName}
                (${study.studyId})</a></div>
        </div>

    </div>
    <!--/ Description -->
    <!-- Host associated/environmental -->
    <c:choose>
        <c:when test="${hostAssociated}">

            <h3>Host associated</h3>
            <div class="output_form">

                <div class="result_row">
                    <div class="result_row_label">Species:</div>
                    <c:choose>
                        <c:when test="${not empty sample.hostTaxonomyId && sample.hostTaxonomyId>0}">
                            <div class="result_row_data"><a class="ext"
                                                            title="UniProt website - Tax ID <c:out value="${sample.hostTaxonomyId}"/>"
                                                            href="<c:url value="http://www.uniprot.org/taxonomy/${sample.hostTaxonomyId}"/>"><em><c:out
                                    value="${sample.species}"/></em></a></div>
                        </c:when>
                        <c:otherwise>
                            <c:out value="(not given)"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:when>


        <c:otherwise>

            <c:if test="${not empty sample.environmentalBiome or not empty sample.environmentalFeature or not empty sample.environmentalMaterial}">
                <h3>Environmental conditions</h3>
                <div class="output_form">

                    <c:choose>
                        <c:when test="${not empty sample.environmentalBiome}">
                            <c:set var="environmentalBiome" value="${sample.environmentalBiome}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="environmentalBiome" value="(not given)"/>
                        </c:otherwise>
                    </c:choose>

                    <div class="result_row">
                        <div class="result_row_label">Biome:</div>
                        <div class="result_row_data"><%--${fn:toLowerCase(environmentalBiome)}--%>
                                ${environmentalBiome}
                        </div>
                    </div>

                    <c:choose>
                        <c:when test="${not empty sample.environmentalFeature}">
                            <c:set var="environmentalFeature" value="${sample.environmentalFeature}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="environmentalFeature" value="(not given)"/>
                        </c:otherwise>
                    </c:choose>

                    <div class="result_row">
                        <div class="result_row_label">Experimental feature:</div>
                        <div class="result_row_data">
                                <%--${fn:toLowerCase(environmentalFeature)}--%>
                                ${environmentalFeature}
                        </div>
                    </div>

                    <c:choose>
                        <c:when test="${not empty sample.environmentalMaterial}">
                            <c:set var="environmentalMaterial" value="${sample.environmentalMaterial}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="environmentalMaterial" value="(not given)"/>
                        </c:otherwise>
                    </c:choose>

                    <div class="result_row">
                        <div class="result_row_label">Material:</div>
                        <div class="result_row_data">
                                <%--${fn:toLowerCase(environmentalMaterial)}--%>
                                ${environmentalMaterial}
                        </div>
                    </div>

                </div>
            </c:if>
        </c:otherwise>
    </c:choose>
    <!--/ Host associated/environmental -->

    <!-- Localisation -->
    <c:choose>
        <c:when test="${empty sample.geoLocName && empty sample.longitude && empty sample.latitude}">
            <%--don't show the localisation box if empty--%>
        </c:when>
        <c:otherwise>
            <h3>Localisation</h3>

            <div class="output_form" style="overflow:auto;">

                <c:if test="${!hostAssociated}">
                    <c:choose>
                        <c:when test="${empty sample.latitude}">
                            <%--remove label when emtpy otherwise alignment problem--%>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${not empty sample.latitude}">
                                    <c:set var="latLon" value="${sample.latitude} , ${sample.longitude}"/>
                                    <div id="map_canvas"></div>
                                    <script defer
                                            type="text/javascript"> initialize(${sample.latitude}, ${sample.longitude})</script>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="latLon" value="(not given)"/>
                                </c:otherwise>
                            </c:choose>

                            <div class="result_row">
                                <div class="result_row_label">Latitude/Longitude:</div>
                                <div class="result_row_data"><c:out value="${latLon}"/></div>
                            </div>

                        </c:otherwise>
                    </c:choose>
                </c:if>
                    <%--host associated - what showing country means ... to redefine more precisely--%>
                <c:choose>
                    <c:when test="${empty sample.geoLocName}">
                        <%--remove label when emtpy otherwise alignment problem--%>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${not empty sample.geoLocName}">
                                <c:set var="geoLocName" value="${sample.geoLocName}"/>
                                <div class="result_row">
                                    <div class="result_row_label">Geographic location:</div>
                                    <div class="result_row_data"><c:out value="${geoLocName}"/></div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <c:set var="geoLocName" value="(not given)"/>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>


            </div>
        </c:otherwise>
    </c:choose>
    <!--/ Localisation -->

    <!-- Other info box -->
    <c:if test="${not empty sampleAnnotations}">
        <h3 id="redux_button">Sample metadata</h3>
        <div class="output_form more_sample_meta_data">
            <table class="simple_table">
                <tbody>
                <c:forEach var="annotation" items="${sampleAnnotations}" varStatus="status">
                    <tr>
                        <td id="ordered" width="250px">
                            <div class="fl_capitalize">${annotation.annotationName}</div>
                        </td>
                        <td class="fl_capitalize">${annotation.annotationValue} ${annotation.unit}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
    <!--/ Other info box -->

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
                    <th>Run id</th>
                    <th>Pipeline version</th>
                    <th>Experiment type</th>
                    <th>Analysis date</th>
                    <th width="170px">Analysis results</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="analysisJob" items="${analysisJobs}" varStatus="status">
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${analysisJob.analysisStatus.analysisStatus == 'completed'}">
                                    <a title="Overview"
                                       href="<c:url value="${baseURL}/projects/${study.studyId}/samples/${sample.sampleId}/runs/${analysisJob.externalRunIDs}/results/versions/${analysisJob.pipelineRelease.releaseVersion}"/>">${analysisJob.externalRunIDs}</a>
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
                                       href="<c:url value="${baseURL}/projects/${study.studyId}/samples/${sample.sampleId}/runs/${analysisJob.externalRunIDs}/results/versions/${analysisJob.pipelineRelease.releaseVersion}#ui-id-6"/>">Taxonomy </a>|
                                    <c:if test="${analysisJob.experimentType.experimentType != 'amplicon'}">
                                        <a title="Function analysis" class="list_sample"
                                           href="<c:url value="${baseURL}/projects/${study.studyId}/samples/${sample.sampleId}/runs/${analysisJob.externalRunIDs}/results/versions/${analysisJob.pipelineRelease.releaseVersion}#ui-id-8"/>">Function </a>|
                                    </c:if>
                                    <a title="download results"
                                       class="icon icon-functional list_sample" data-icon="="
                                       href="<c:url value="${baseURL}/projects/${study.studyId}/samples/${sample.sampleId}/runs/${analysisJob.externalRunIDs}/results/versions/${analysisJob.pipelineRelease.releaseVersion}#ui-id-10"/>"></a>
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
                {className: "table-align-center table_xs_text", "targets": [0, 3]},
                {className: "table-align-center", "targets": [1]},
                {className: "table-align-center fl_capitalize", "targets": [2]},
                {className: "xs_hide", "targets": [4]}
            ],
            "bDeferRender": true,
            "bRetrieve": true,
            "oLanguage": {
                "sSearch": "Filter:"
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
        $('.filter_sp').keyup(function () {
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
<script type="text/javascript">
    $("#expand_button").click(function () {
        $(".more_sample_meta_data").slideToggle();
        $("#expand_button").toggleClass("min");
    });
    $("#redux_button").click(function () {
        $(".more_sample_meta_data").slideToggle();
        $("#redux_button").toggleClass("max");
    });


</script>
