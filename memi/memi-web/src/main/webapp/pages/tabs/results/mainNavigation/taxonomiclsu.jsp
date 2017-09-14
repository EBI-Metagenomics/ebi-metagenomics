<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="fragment-taxonomy">

    <div class="main_tab_full_content">
        <p>These are the results from the taxonomic analysis steps of our pipeline, based on the analysis of the large subunit rRNA - LSU. You can switch between different
            views of the data using the menu of icons below (interactive krona, static pie, bar and stacked charts). The data
            used to build these charts can be found under the "Download" tab.</p>

        <c:choose>
            <c:when test="${empty model.sample.analysisCompleted}">
                <div class="msg_error">Analysis in progress.</div>
            </c:when>
            <c:when test="${not empty model.sample.analysisCompleted && !model.analysisStatus.taxonomicAnalysisTabDisabled}">
                <h3>Top taxonomy Hits</h3>

                <div class="tabs-chart" id="tabs-taxchart-lsu">
                    <ul>
                        <%--<li class="selector_tab xs_hide">Switch view:</li>--%>
                        <li class="but_krona"><a class="show_tooltip" title="Krona chart view"
                                                 href="<c:url value="${baseURL}/projects/${model.run.externalProjectId}/samples/${model.run.externalSampleId}/runs/${model.run.externalRunId}/results/kronaChartView/lsu/versions/${model.analysisJob.pipelineRelease.releaseVersion}"/>"><span
                                class="ico-krona"></span></a></li>
                        <li><a class="show_tooltip" title="Pie chart view"
                               href="<c:url value="${baseURL}/projects/${model.run.externalProjectId}/samples/${model.run.externalSampleId}/runs/${model.run.externalRunId}/results/taxPieChartView/lsu/versions/${model.analysisJob.pipelineRelease.releaseVersion}"/>"><span
                                class="ico-pie"></span></a></li>
                        <li><a class="show_tooltip" title="Bar chart view"
                               href="<c:url value="${baseURL}/projects/${model.run.externalProjectId}/samples/${model.run.externalSampleId}/runs/${model.run.externalRunId}/results/taxBarChartView/lsu/versions/${model.analysisJob.pipelineRelease.releaseVersion}"/>"><span
                                class="ico-barh"></span></a></li>
                        <li><a class="show_tooltip" title="Stacked Column chart view"
                               href="<c:url value="${baseURL}/projects/${model.run.externalProjectId}/samples/${model.run.externalSampleId}/runs/${model.run.externalRunId}/results/taxColumnChartView/lsu/versions/${model.analysisJob.pipelineRelease.releaseVersion}"/>"><span
                                class="ico-col"></span></a></li>
                    </ul>
                </div>

            </c:when>
            <c:otherwise>
                <div class="msg_error">No taxonomy result files have been associated with this sample.</div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<script type="text/javascript">

    //Ajax load approach as described here: http://jqueryui.com/tabs/#ajax
    $("#tabs-taxchart-lsu").tabs({
        cache: true,
        ajaxOptions: {
            error: function (xhr, status, index, anchor) {
                $(anchor.hash).html("<div class='msg_error'>Couldn't load this tab. We'll try to fix this as soon as possible.</div>");
            }
        },
        spinner: false

    });

    //Default functionality
    $("#tabs-taxchart-lsu").tabs({${model.analysisStatus.taxonomicAnalysisTab.tabsOptions}});
</script>

<%--Remove the filter field for taxonomy table when the total number of phylum is less than 10 - note can't one single table id + ID needed or it affects the interpro match datatable--%>
<c:if test="${fn:length(model.taxonomyAnalysisResultLSU.taxonomyDataSet)<10}">
    <style>#tax_table_wrapper .dataTables_filter, #tax_table_bar_wrapper .dataTables_filter, #tax_table_col_wrapper .dataTables_filter {display: none;}</style>
</c:if>
<%--Remove the sort number field + navigation for taxonomy table when the total number of phylum is less than 25--%>
<c:if test="${fn:length(model.taxonomyAnalysisResultLSU.taxonomyDataSet)<25}">
    <style> #tax_table_wrapper .dataTables_length, #tax_table_wrapper .dataTables_paginate,  #tax_table_bar_wrapper .dataTables_length, #tax_table_bar_wrapper .dataTables_paginate,   #tax_table_col_wrapper .dataTables_length, #tax_table_col_wrapper .dataTables_paginate  {display: none;}</style>
</c:if>