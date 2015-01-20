<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type='text/javascript'>
    //BEGIN code used to showroom the row number selection - TODO apply on the new table

    //    google.setOnLoadCallback(init);
    //
    //    var dataSourceUrl = 'https://docs.google.com/spreadsheet/ccc?key=0AgWotcbTSSjYdGF6NjE0WGxGRmV5djJDWEZ6RzZhT2c&usp=sharing';
    //    var query, options, container;
    //
    //       function init() {
    //         query = new google.visualization.Query(dataSourceUrl);
    //         container = document.getElementById("func_table_div1");
    //         options = {width:600, allowHtml:true, showRowNumber:true, page:'enable', pageSize:10, pagingSymbols:{prev:'prev', next:'next'}, sortColumn:2, sortAscending:false};
    //         sendAndDraw();
    //       }
    //
    //       function sendAndDraw() {
    //         query.abort();
    //         var tableQueryWrapper = new TableQueryWrapper(query, container, options);
    //         tableQueryWrapper.sendAndDraw();
    //       }
    //
    //
    //       function setOption(prop, value) {
    //         options[prop] = value;
    //         sendAndDraw();
    //       }


    //    google.setOnLoadCallback(drawTable);// Set a callback to run when the Google Visualization API is loaded.

    //END code used to showroom the row number selection - TODO apply on the new table

</script>
<div id="fragment-functional">

<div class="main_tab_full_content">
    <p>Functional analysis has 3 main outputs: a sequence features summary, showing the number of reads with
        predicted coding sequences (pCDS), the number of pCDS with InterPro matches, and so on; the matches of pCDS
        to the <a href="http://www.ebi.ac.uk/interpro" target="_blank" title="InterPro website" class="ext">InterPro
            database</a>
        and a chart of the GO terms that summarise the functional content of the sample's sequences. If you wish to
        download the full set of results, all files are listed under the "Download" tab.</p>

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
            <h3>Sequence feature summary</h3>
            <c:choose>
                <c:when test="${!model.analysisStatus.functionalAnalysisTab.sequenceFeatureSectionDisabled}">

                    <div style="display:block; overflow: auto;">
                        <c:url var="sequenceFeatureImage" value="/getImage" scope="request">
                            <c:param name="imageName" value="/charts/seq-feat.png"/>
                            <c:param name="imageType" value="PNG"/>
                            <c:param name="dir" value="${model.emgFile.fileID}"/>
                        </c:url>
                        <p><img
                                src="<c:out value="${sequenceFeatureImage}"/>"/></p>
                    </div>
                </c:when>
                <c:otherwise>
                    <%-- message when section if empty - shouldn't happen --%>
                    <div class="msg_error">No sequence feature result files have been associated with this sample.
                    </div>
                </c:otherwise>
            </c:choose>
            <h3>InterPro match summary</h3>
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
                <c:when test="${!model.analysisStatus.functionalAnalysisTab.interProMatchSectionDisabled}">


                    <%--JQuery tabs DIV and UL definition for the InterPro match summary section--%>
                    <%--<div id="interpro-chart">--%>

                    <%--Tabs--%>
                    <%--<ul>--%>
                    <%--<li><a href="#interpro-match-table" title="Table view"><span class="ico-table"></span></a></li>--%>
                    <%--<li class="selector_tab"></li>--%>
                    <%--<li><a href="#interpro-match-pie" title="Pie chart view"><span class="ico-pie"></span></a></li>--%>
                    <%--<li><a href="#interpro-match-bar" title="Bar chart view"><span class="ico-barh"></span></a></li>--%>
                    <%--<li><a href="#interpro-match-col" title="Stacked column chart view"><span class="ico-col"></span></a></li>--%>
                    <%--<li><a href="#interpro-match-Krona" title="Krona interactive chart view"><span class="ico-krona"></span></a></li>--%>

                    <%--</ul>--%>


                    <div id="interpro-match-pie">


                            <%--<div class="chart_container">--%>

                            <%--<div class="chart-block">--%>
                        <div class="chart_container">
                            <div class="chart-block">
                                <div class="but_chart_export ui-buttonset">
                                    <button id="func-ip-pie" style="display: none;"></button>
                                    <button id="select" class="ui-button ui-widget ui-state-default ui-button-text-icon-secondary ui-corner-right"><span class="ui-button-text">Export</span><span class="ui-button-icon-secondary ui-icon ui-icon-triangle-1-s"></span></button>
                                </div>

                                <ul class="export_list">
                                    <li class="chart_exp_snap"><a
                                            onclick="toImg(document.getElementById('func_chart_pie_ipro'), document.getElementById('img_div'));">Snapshot</a>
                                    </li>
                                    <li class="chart_exp_png"><a
                                            onclick="saveAsImg(document.getElementById('func_chart_pie_ipro'),'<spring:message code="file.name.func.ip.pie.chart.png"/>',1);">PNG</a>
                                    </li>
                                    <li class="chart_exp_png"><a
                                            onclick="saveAsImg(document.getElementById('func_chart_pie_ipro'),'<spring:message code="file.name.func.ip.pie.chart.high.png"/>',300/72);">PNG
                                        (Higher quality)</a></li>
                                    <li class="chart_exp_png"><a
                                            onclick="saveAsSVG(document.getElementById('func_chart_pie_ipro'),'<spring:message code="file.name.func.ip.pie.chart.svg"/>');">SVG</a>
                                    </li>
                                </ul>

                                <div id="func_chart_pie_ipro"></div>
                                <div class="func_chart_caption">
                                    <div class="puce_chart"></div>
                                    Other matches
                                </div>
                            </div>

                                <%--BEGIN http://jsfiddle.net/SCjm8/1/--%>
                                <%--<div style="float:left; width:312px; border:0px red solid;" class="chart-export-but">--%>
                                <%--<button onclick="saveAsImg(document.getElementById('func_chart_pie_ipro'));">Save as PNG Image</button>--%>
                                <%--<button onclick="toImg(document.getElementById('func_chart_pie_ipro'), document.getElementById('img_div'));">Convert to image</button>--%>
                                <%--<div id="func_chart_pie_ipro"></div></div>                       &lt;%&ndash; END http://jsfiddle.net/SCjm8/1/&ndash;%&gt;--%>


                                <%--<div id="func_chart_div1"></div>--%>


                                <%-- BEGIN Toolbar - to export chart--%>
                            <div class="ico-download" id="toolbar_div" style="display:none;"><a
                                    class="icon icon-functional" data-icon="=" id="csv" href="#" title=""></a></div>
                                <%-- END Toolbar--%>


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
                            <li><a href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/goBarChartView"/>"
                                   title="Bar-Chart-View"><span class="ico-barh"></span></a></li>
                            <li><a href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/goPieChartView"/>"
                                   title="Pie-Chart-View"><span class="ico-pie"></span></a></li>

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
        ajaxOptions:{
            error:function (xhr, status, index, anchor) {
                $(anchor.hash).html("<div class='msg_error'>Couldn't load this tab. We'll try to fix this as soon as possible.</div>");
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

</script>

<%@ include file="../functionalAnalysis/interproMatchesView.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/export-button-menu.js"></script>