<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<%--Globale page properties--%>
<c:set var="phylumCompositionTitle" scope="request" value="Phylum composition (Total: ${model.taxonomyAnalysisResult.sliceVisibilityThresholdDenominator} OTUs)"/>

<script type="text/javascript">
    drawDomainCompositionBarChart();
    drawPhylumBarChart();
    drawPhylumTable();

    function drawPhylumTable() {
// Taxonomy top phylum table - Bar chart
        var taxMatchesDataBarChart = new google.visualization.DataTable();
        taxMatchesDataBarChart.addColumn('string', 'Phylum');
        taxMatchesDataBarChart.addColumn('string', 'Domain');
        taxMatchesDataBarChart.addColumn('number', 'Unique OTUs');
        taxMatchesDataBarChart.addColumn('number', '%');
//        taxMatchesData.addColumn('number', 'Count of reads assigned');
//        taxMatchesData.addColumn('number', '% reads assigned');
        taxMatchesDataBarChart.addRows([
            <c:set var="addComma" value="false"/><c:forEach var="taxonomyData" items="${model.taxonomyAnalysisResult.taxonomyDataSet}" varStatus="status"><c:choose><c:when test="${addComma}">,
            </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>
            ['${taxonomyData.phylum}', '${taxonomyData.superKingdom}', ${taxonomyData.numberOfHits}, ${taxonomyData.percentage}]</c:forEach>
        ]);

        // Define a StringFilter control for the 'Phylum' column - bar chart table
        var taxbarStringFilter = new google.visualization.ControlWrapper({
            'controlType':'StringFilter',
            'containerId':'tax_table_bar_filter',
            'options':{'matchType':'any', 'filterColumnIndex':'0', 'ui':{'label':'Filter table', 'labelSeparator':':', 'ui.labelStacking':'vertical', 'ui.cssClass':'custom_col_search'}
            }
        });

        // Table visualization option - Bar chart table
        var taxbarTableOptions = new google.visualization.ChartWrapper({
            'chartType':'Table',
            'containerId':'tax_table_bar',
            'options':{ allowHtml:true, showRowNumber:true, page:'enable', pageSize:10, pagingSymbols:{prev:'prev', next:'next'}, sortColumn:2, sortAscending:false }
        });

        // Draw the Dashboard for the Bar chart
        new google.visualization.Dashboard(document.getElementById('tax_dashboard_bar')).
            // Configure the string filter to affect the table contents
                bind(taxbarStringFilter, taxbarTableOptions).
            // Draw the dashboard
                draw(taxMatchesDataBarChart);

    }  //END function drawPhylumTable()

    function drawPhylumBarChart() {
        // DATA taxonomy Pie+Bar chart Phylum
        var phylumBarChartPieChartData = new google.visualization.DataTable();
        phylumBarChartPieChartData.addColumn('string', 'Phylum');
        phylumBarChartPieChartData.addColumn('number', 'Match');
        phylumBarChartPieChartData.addRows([
            <c:set var="addComma" value="false"/><c:forEach var="taxonomyData" items="${model.taxonomyAnalysisResult.taxonomyDataSet}" varStatus="status"><c:choose><c:when test="${addComma}">,
            </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>
            ['${taxonomyData.phylum}', ${taxonomyData.numberOfHits}]</c:forEach>
        ]);
// Taxonomy Bar - phylum
        var options = {'title':'${phylumCompositionTitle}', 'titleTextStyle':{fontSize:12}, 'colors':['#5f8694'], 'width':346, 'height':380, 'chartArea':{left:120, top:30, width:"60%", height:"70%"}, 'pieSliceBorderColor':'none', 'legend':'none' };
        var phylumBarChart = new google.visualization.BarChart(document.getElementById('tax_chart_bar_phy'));
        phylumBarChart.draw(phylumBarChartPieChartData, options);
    }

    function drawDomainCompositionBarChart() {
        // DATA taxonomy Pie+Bar chart Domain
        var domainBarChartPieChartData = new google.visualization.DataTable();
        domainBarChartPieChartData.addColumn('string', 'kingdom');
        domainBarChartPieChartData.addColumn('number', 'Match');
        domainBarChartPieChartData.addRows([
            <c:set var="addComma" value="false"/><c:forEach var="domainEntry" items="${model.taxonomyAnalysisResult.domainComposition.domainMap}"><c:choose><c:when test="${addComma}">,
            </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>
            ['${domainEntry.key}', ${domainEntry.value}]</c:forEach>
        ]);

// Taxonomy Bar - domain
        var options = {'title':'Domain composition', 'titleTextStyle':{fontSize:12}, 'colors':['#5f8694'], 'width':190, 'height':100, 'chartArea':{left:70, top:30, width:"56%", height:"70%"}, 'vAxis':{textStyle:{fontSize:11}}, 'pieSliceBorderColor':'none', 'bar':{groupWidth:10}, 'legend':'none'};

        var domainBarChart = new google.visualization.BarChart(document.getElementById('tax_chart_bar_dom'));
        domainBarChart.draw(domainBarChartPieChartData, options);
    }
</script>