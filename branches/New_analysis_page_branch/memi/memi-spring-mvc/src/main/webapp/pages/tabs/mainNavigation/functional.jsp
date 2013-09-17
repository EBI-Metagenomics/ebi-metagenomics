<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="fragment-functional">

    <div class="main_tab_full_content">
        <p>Functional analysis has 3 main outputs: a sequence features summary, showing the number of reads with
            predicted coding sequences (pCDS), the number of pCDS with InterPro matches, and so on; the matches of pCDS
            to the <a href="http://www.ebi.ac.uk/interpro" title="InterPro website" class="ext">InterPro database</a>
            and a chart of the GO terms that summarise the functional content of the sample's sequences. If you wish to
            download the full set of results, all files are listed under the "Download" tab.</p>

        <c:choose>
            <c:when test="${empty model.sample.analysisCompleted}">
                <div class="msg_error">Analysis in progress.</div>
            </c:when>
            <c:when test="${not empty model.sample.analysisCompleted && !model.analysisStatus.functionalAnalysisTab.interProMatchSectionDisabled && !model.analysisStatus.functionalAnalysisTab.goSectionDisabled}">

                <c:choose>

                    <c:when test="${not empty model.sample.analysisCompleted && !model.analysisStatus.functionalAnalysisTab.sequenceFeatureSectionDisabled}">

                        <h3>Sequence feature summary</h3>

                        <div style="display:block; overflow: auto;">
                            <c:url var="sequenceFeatureImage" value="/getImage" scope="request">
                                <c:param name="imageName" value="/charts/seq-feat.png"/>
                                <c:param name="imageType" value="PNG"/>
                                <c:param name="dir" value="${model.emgFile.fileID}"/>
                            </c:url>
                            <div style="float:left; margin-left: 9px;"><img
                                    src="<c:out value="${sequenceFeatureImage}"/>"/></div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <%-- remove the section if empty
             <div class="msg_error">No sequence feature result files have been associated with this sample.</div>--%>
                    </c:otherwise>
                </c:choose>


                <h3>InterPro match summary</h3>

                <p>Most frequently found InterPro matches to this sample:</p>
                <%--<div>
                  <div>
                    <button id="rerun" ><span class="icon icon-functional" data-icon="=" ></span></button>
                    <button id="select">Select what to download</button>
                  </div>
                  <ul>
                    <li><a href="#">Save chart image</a></li>
                    <li><a href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/doExportIPRFile"/>"   title="<spring:message code="analysisStatsView.label.download.i5.table.view"/>">Save table</a></li>
                    <li><a href="#">Save raw data</a></li>
                  </ul>
                </div>--%>

                <c:choose>
                    <c:when test="${empty model.sample.analysisCompleted}">
                        <div class="msg_error">Analysis in progress.</div>
                    </c:when>
                    <c:when test="${not empty model.sample.analysisCompleted && !model.analysisStatus.functionalAnalysisTab.interProMatchSectionDisabled}">
                        <div id="interpro-chart">

                                <%--Tabs--%>
                            <ul>
                                    <%--<li><a href="#interpro-match-table" title="Table view"><span class="ico-table"></span></a></li>--%>
                                <li class="selector_tab"></li>
                                <%--<li><a href="#interpro-match-pie" title="Pie chart view"><span class="ico-pie"></span></a></li>--%>
                                    <%--<li><a href="#interpro-match-bar" title="Bar chart view"><span class="ico-barh"></span></a></li>--%>
                                    <%--<li><a href="#interpro-match-col" title="Stacked column chart view"><span class="ico-col"></span></a></li>--%>
                                    <%--<li><a href="#interpro-match-Krona" title="Krona interactive chart view"><span class="ico-krona"></span></a></li>--%>
                                <div class="ico-download" id="toolbar_div" style="display:none;"><a
                                        class="icon icon-functional"
                                        data-icon="=" id="csv"
                                        href="#" title=""></a></div>
                            </ul>


                            <div id="interpro-match-pie">

                                <div class="chart_container">

                                        <%--<div id="func_chart_div1"></div>--%>

                                    <div id="func_chart_pie_ipro"></div>

                                    <div id="func_dashboard">
                                        <div id="func_table_filter"></div>
                                        <div id="func_table_pie_ipro"></div>
                                    </div>

                                        <%--  BEGIN code used if we want to use the row number select option
                          <div id="func_table_div1" style="display:none;"></div>
                          <form action="" class="expandertable" >
                          Show rows:
                          <select onChange="setOption('pageSize', parseInt(this.value, 10))">
                          <option selected=selected value="10">10</option>
                          <option value="25">25</option>
                          <option value="50">50</option>
                          <option value="100">100</option>
                          <option value="1000">1000</option>
                          <option value="10000">All</option>
                          </select></form>
                          END code used if we want to use the row number select option  --%>

                                </div>


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
                    <c:when test="${empty model.sample.analysisCompleted}">
                        <div class="msg_error">Analysis in progress.</div>
                    </c:when>
                    <c:when test="${not empty model.sample.analysisCompleted && !model.analysisStatus.functionalAnalysisTab.goSectionDisabled}">
                        <div id="tabs-chart">

                                <%--Tabs--%>
                            <ul>
                                <li class="selector_tab">Switch view:</li>
                                    <%--<li><a href="#go-terms-table" title="Table view"><span class="ico-table"></span></a></li>--%>
                                <li><a href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/goBarChartView"/>"
                                       title="Bar-Chart-View"><span class="ico-barh"></span></a></li>
                                <li><a href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/goPieChartView"/>"
                                       title="Pie-Chart-View"><span class="ico-pie"></span></a></li>

                                    <%--<li><a href="#go-terms-col" title="Stacked column chart view"><span class="ico-col"></span></a></li>--%>
                                    <%--<li><a href="#go-terms-Krona" title="Krona interactive chart view"><span class="ico-krona"></span></a></li>--%>
                                    <%--<li class="ico-downl"><a class="icon icon-functional" data-icon="=" href="#download" title="Download image/table"></a></li>--%>
                            </ul>


                                <%--<div id="go-terms-bar">--%>
                                <%--<div class="go-chart">--%>
                                <%--<div id="func_chart_bar_go_bp"></div>--%>
                                <%--<div id="func_chart_bar_go_mf"></div>--%>
                                <%--<div id="func_chart_bar_go_cc"></div>--%>
                                <%--</div>--%>
                                <%--</div>--%>

                                <%--<div id="go-terms-pie">--%>
                                <%--<div class="go-chart">--%>
                                <%--<div id="func_chart_pie_go_bp"></div>--%>
                                <%--<div id="func_chart_pie_go_mf"></div>--%>
                                <%--<div id="func_chart_pie_go_cc"></div>--%>
                                <%--</div>--%>
                                <%--</div>--%>

                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="msg_error">No GO Terms annotation have been associated with this sample.</div>
                    </c:otherwise>
                </c:choose>

            </c:when>
            <c:otherwise>

                <div class="msg_error">No functional result files have been associated with this sample.</div>
            </c:otherwise>

        </c:choose>

    </div>

</div>
<%--end div fragment functional--%>
<script type="text/javascript">
    $(document).ready(function () {
        // Functional analysis tab navigation
        $("#interpro-chart").tabs();
        //Ajax load approach as described here: http://jqueryui.com/tabs/#ajax
        $("#tabs-chart").tabs({
            ajaxOptions:{
                error:function (xhr, status, index, anchor) {
                    $(anchor.hash).html("Couldn't load this tab. We'll try to fix this as soon as possible.");
                }
            },
            spinner:false,
            select:function (event, ui) {
                var tabID = "#ui-tabs-" + (ui.index + 1);
                $(tabID).html("<b>Loading Data.... Please wait....</b>");
            }
        });
        //Default functionality
        $("#tabs-chart").tabs({ selected:0  });
    });
</script>
<%@ include file="../functionalAnalysis/interproMatchesView.jsp" %>