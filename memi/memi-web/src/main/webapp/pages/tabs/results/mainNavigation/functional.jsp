<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="fragment-functional">

    <div class="main_tab_full_content">
        <p>These charts present the functional analysis of our pipeline, which focuses on matches to the <a
                href="http://www.ebi.ac.uk/interpro" target="_blank" title="InterPro website" class="ext">InterPro
            database</a> and GO terms, which summarise the functional content of the sequences in the sample. The full
            set of results files may be found under the "Download" tab.</p>

        <c:choose>
            <%--If analysis is NOT completed--%>
            <c:when test="${empty model.sample.analysisCompleted}">
                <div class="msg_error">Analysis in progress.</div>
            </c:when>
            <%--If Analysis completed completed and NO functional results have been associated--%>
            <c:when test="${model.analysisStatus.functionalAnalysisTab.sequenceFeatureSectionDisabled && model.analysisStatus.functionalAnalysisTab.interProMatchSectionDisabled
    && model.analysisStatus.functionalAnalysisTab.goSectionDisabled}">
                <div class="msg_error">No functional result files have been associated with this sample.</div>
            </c:when>
            <%--If Analysis completed completed and functional results have been associated--%>
            <c:otherwise>
                <%--<h3>Sequence feature summary</h3>--%>
                <c:choose>
                    <c:when test="${!model.analysisStatus.functionalAnalysisTab.sequenceFeatureSectionDisabled}">

                        <div id="sq_sum"></div>
                        <%--<div style="display:block; overflow: auto;" >--%>
                            <%--<c:url var="sequenceFeatureImage" value="/getImage" scope="request">--%>
                                <%--<c:param name="imageName" value="/charts/seq-feat.png"/>--%>
                                <%--<c:param name="imageType" value="PNG"/>--%>
                                <%--<c:param name="dir" value="${model.analysisJob.resultDirectory}"/>--%>
                            <%--</c:url>--%>
                            <%--<p><img--%>
                                    <%--src="<c:out value="${sequenceFeatureImage}"/>"/></p>--%>
                        <%--</div>--%>


                        <script type="text/javascript">
                            $(function () {
                                var common_path = "/metagenomics/projects/${projectId}/samples/${sampleId}/runs/${runId}/results/versions/${releaseVersion}/qc-stats/",
                                        file_summary = common_path + 'summary',
                                        numberOfLines = 5;
                              //  debugger;
                                var stats_data =null;
                                    $.ajax(file_summary).done(function(d){
                                        sumNumberOfReadsChart(d,numberOfLines,stats_data==null?null:stats_data["sequence_count"],file_summary);
                                    });
                            });
                        </script>

                    </c:when>
                    <c:otherwise>
                        <%-- message when section if empty - shouldn't happen --%>
                        <div class="msg_error">No sequence feature result files have been associated with this sample.
                        </div>
                    </c:otherwise>
                </c:choose>

                <h3>InterPro match summary</h3>

                <c:choose>
                    <c:when test="${!model.analysisStatus.functionalAnalysisTab.interProMatchSectionDisabled}">
                        <div id="interpro-match-pie">
                            <div  class="chart_container" >
                                <div class="grid_8"><div id="func_chart_pie_ipro"></div></div>
                                <div class="grid_16"> <table id="func_table_ipro" class="table-glight"></table></div>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="msg_error">No InterPro match result files have been associated with this sample.
                        </div>
                    </c:otherwise>
                </c:choose>

                <h3>GO Terms annotation</h3>

                <p>A summary of Gene Ontology (GO) terms derived from InterPro matches to your sample is provided in the
                    charts
                    below.</p>
                <c:choose>
                    <c:when test="${!model.analysisStatus.functionalAnalysisTab.goSectionDisabled}">
                        <div id="tabs-chart">

                                <%--Tabs--%>
                            <ul>
                                <li class="selector_tab">Switch view:</li>
                                    <%--<li><a href="#go-terms-table" title="Table view"><span class="ico-table"></span></a></li>--%>
                                <li><a class="show_tooltip"
                                       href="<c:url value="${baseURL}/projects/${model.run.externalProjectId}/samples/${model.run.externalSampleId}/runs/${model.run.externalRunId}/results/goBarChartView/versions/${model.analysisJob.pipelineRelease.releaseVersion}"/>"
                                       title="Bar chart view"><span class="ico-barh"></span></a></li>
                                <li><a class="show_tooltip"
                                       href="<c:url value="${baseURL}/projects/${model.run.externalProjectId}/samples/${model.run.externalSampleId}/runs/${model.run.externalRunId}/results/goPieChartView/versions/${model.analysisJob.pipelineRelease.releaseVersion}"/>"
                                       title="Pie chart view"><span class="ico-pie"></span></a></li>

                            </ul>


                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="msg_error">No GO Terms annotation have been associated with this sample.</div>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>

    </div>

</div>
<%--end div fragment functional--%>
<script type="text/javascript">
    //Ajax function for InterPro matches JQuery tabs
    //$("#interpro-chart").tabs();
    //Ajax function for GO annotation JQuery tabs (switch from pie to bar chart)
    //Ajax load approach as described here: http://jqueryui.com/tabs/#ajax
    $("#tabs-chart").tabs({
        ajaxOptions: {
            error: function (xhr, status, index, anchor) {
                $(anchor.hash).html("<div class='msg_error'>Couldn't load this tab. We'll try to fix this as soon as possible.</div>");
            }
        },
        spinner: false,
        select: function (event, ui) {
            var tabID = "#ui-tabs-" + (ui.index + 1);
            $(tabID).html("<b>Loading Data.... Please wait....</b>");
        }
    });
    //Default functionality
    $("#tabs-chart").tabs({selected: 0});

    //Temp - Duplicate the tooltip call - otherwise not showing it for Go term annotation switch view button
    $('.show_tooltip').qtip({
        position: {
            my: 'top center',
            at: 'bottom center'
        }
    });

</script>

<%@ include file="../functionalAnalysis/interproMatchesView.jsp" %>