<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html" %>
<%@ page import="java.util.*" %>

<div id="fragment-taxonomy">

    <div class="main_tab_full_content">
        <p>These are the results from the taxonomic analysis steps of our pipeline. You can switch between
            different views of the data using the menu of icons below (pie, bar, stacked and interactive krona
            charts).  If you wish to download the full set of results, all files are listed under the
            "Download" tab.</p>
        <%--<h3>Taxonomy analysis</h3>--%>


        <c:choose>
            <c:when test="${empty model.sample.analysisCompleted}"><div class="msg_error">Analysis in progress.</div></c:when>
            <c:when test="${not empty model.sample.analysisCompleted && !model.analysisStatus.taxonomicAnalysisTabDisabled}">
                <h3>Top taxonomy Hits</h3>
                <div id="tabs-taxchart">
                    <ul>
                        <li class="selector_tab">Switch view:</li>
                            <%--<li><a href="#tax-table" title="Table view"><span class="ico-table"></span></a></li>--%>
                        <li><a href="#tax-pie" title="Pie chart view"><span class="ico-pie"></span></a></li>
                        <li><a href="#tax-bar" title="Bar chart view"><span class="ico-barh"></span></a></li>
                        <li><a href="#tax-col" title="Stacked column chart view"><span class="ico-col"></span></a></li>
                        <li class="but_krona"><a href="#tax-Krona" title="Krona interactive chart view"><span class="ico-krona"></span></a></li>
                            <%--<li class="ico-downl"><a class="icon icon-functional" data-icon="=" href="#download" title="Download image/table"></a></li>--%>
                    </ul>


                        <%--Taxonomy google chart--%>
                    <div id="tax-pie">

                            <div class="chart_container">
                                <div id="tax_chart_pie_dom"></div>
                                <div id="tax_chart_pie_phy"></div>

                                <div id="tax_dashboard">
                                    <div id="tax_table_filter"></div>
                                    <div id="tax_table_pie"></div>
                                        <%--<div id="table_div"></div>--%>
                                </div>

                            </div>

                    </div>

                    <div id="tax-bar">
                        <div class="chart_container">
                            <div id="tax_chart_bar_dom"></div>
                            <div id="tax_chart_bar_phy"></div>

                            <div id="tax_dashboard_bar">
                                <div id="tax_table_bar_filter"></div>
                                <div id="tax_table_bar"></div>
                            </div>

                        </div>
                    </div>

                    <div id="tax-col">
                        <div class="chart_container">
                            <div id="tax_chart_col"></div>

                            <div id="tax_dashboard_col">
                                <div id="tax_table_col_filter"></div>
                                <div id="tax_table_col"></div>
                            </div>

                        </div>

                    </div>
                    <div id="tax-Krona"><object class="krona_chart" data="<c:url value="${baseURL}/sample/${model.sample.sampleId}/krona?taxonomy=true&collapse=false"/>" type="text/html"></object>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="msg_error">No taxonomy result files have been associated with this sample.</div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        // Taxonomy charts navigation
        $("#tabs-taxchart").tabs({${model.analysisStatus.taxonomicAnalysisTab.tabsOptions}});
    });
</script>
<%@ include file="googleCharts/taxonomicAnalysisTab.jsp" %>