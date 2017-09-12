<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="fragment-taxonomy">

    <div class="sub-tabs" id="subtabs">
        <ul>
            <li>
                <a class="show_tooltip" title="SSU rRNA"
                   href="<c:url value="${baseURL}/projects/${model.run.externalProjectId}/samples/${model.run.externalSampleId}/runs/${model.run.externalRunId}/results/taxonomic/ssu/versions/${model.analysisJob.pipelineRelease.releaseVersion}"/>">
                    <span style="font-size:125%;"><small>Small subunit rRNA </small>16S - SSU</span>
                </a>
            </li>
            <li>
                <a class="show_tooltip" title="LSU rRNA"
                   href="<c:url value="${baseURL}/projects/${model.run.externalProjectId}/samples/${model.run.externalSampleId}/runs/${model.run.externalRunId}/results/taxonomic/lsu/versions/${model.analysisJob.pipelineRelease.releaseVersion}"/>">
                    <span style="font-size:125%;"><small>Large subunit rRNA </small> 18S - LSU</span>
                </a>
            </li>

        </ul>
    </div>

</div>

<script type="text/javascript">


    $("#subtabs").tabs({
        cache: true,
        ajaxOptions: {
            error: function (xhr, status, index, anchor) {
                $(anchor.hash).html("<div class='msg_error'>Couldn't load this tab. We'll try to fix this as soon as possible.</div>");
            }
        },
        spinner: false

    });

    $("#subtabs").tabs();
</script>

<%--Remove the filter field for taxonomy table when the total number of phylum is less than 10 - note can't one single table id + ID needed or it affects the interpro match datatable--%>
<%--<c:if test="${fn:length(model.taxonomyAnalysisResultSSU.taxonomyDataSet)<10}">--%>
    <%--<style>#tax_table_wrapper .dataTables_filter, #tax_table_bar_wrapper .dataTables_filter, #tax_table_col_wrapper .dataTables_filter {display: none;}</style>--%>
<%--</c:if>--%>
<%--&lt;%&ndash;Remove the sort number field + navigation for taxonomy table when the total number of phylum is less than 25&ndash;%&gt;--%>
<%--<c:if test="${fn:length(model.taxonomyAnalysisResultSSU.taxonomyDataSet)<25}">--%>
    <%--<style> #tax_table_wrapper .dataTables_length, #tax_table_wrapper .dataTables_paginate,  #tax_table_bar_wrapper .dataTables_length, #tax_table_bar_wrapper .dataTables_paginate,   #tax_table_col_wrapper .dataTables_length, #tax_table_col_wrapper .dataTables_paginate  {display: none;}</style>--%>
<%--</c:if>--%>