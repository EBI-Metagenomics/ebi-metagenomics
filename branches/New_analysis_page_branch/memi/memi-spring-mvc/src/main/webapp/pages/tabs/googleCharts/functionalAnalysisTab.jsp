<%-- This script produces the chart and the table for the 'InterPro match summary' section (functional analysis tab)--%>
<%@ page language="java" contentType="text/html" %>
<%@ page import="java.util.*" %>
<%
    //Use in functionalAnalysisTab.jsp to set the color code of the first 10 entries in the InterPro matches table
    ArrayList colorCodeList = new ArrayList();
    colorCodeList.add("#058dc7");
    colorCodeList.add("#50b432");
    colorCodeList.add("#ed561b");
    colorCodeList.add("#edef00");
    colorCodeList.add("#24cbe5");
    colorCodeList.add("#64e572");
    colorCodeList.add("#ff9655");
    colorCodeList.add("#fff263");
    colorCodeList.add("#6af9c4");
    colorCodeList.add("#dabe88");
    pageContext.setAttribute("colorCodeList", colorCodeList);
%>
<script type="text/javascript">
drawInterProMatchesTable();
drawInterProMatchesPieChart();

function drawInterProMatchesTable() {
    drawVisualization();
    drawToolbar();
}

function drawVisualization() {
    // Functional analysis table - List of InterPro matches
    var interProMatchesData = new google.visualization.DataTable();
    interProMatchesData.addColumn('string', 'Entry name');
    interProMatchesData.addColumn('string', 'ID');
    interProMatchesData.addColumn('number', 'Hits');
    interProMatchesData.addRows([
        <c:set var="addComma" value="false"/><c:forEach var="entry" items="${model.interProEntries}" varStatus="status"><c:choose><c:when test="${addComma}">,
        </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>
        ['<div title="${entry.entryDescription}" class="_cc" style="background-color:<c:choose><c:when test="${status.index>9}">#b9b9b9</c:when><c:otherwise><c:out value="${colorCodeList[status.index]}"/></c:otherwise></c:choose>;"></div> <a title="${entry.entryDescription}" target="_blank" href="http://www.ebi.ac.uk/interpro/entry/${entry.entryID}">${entry.entryDescription}</a>', '${entry.entryID}', ${entry.numOfEntryHits}]</c:forEach>
    ]);

    // Define a StringFilter control for the 'Name'and 'ID' column
    var stringFilter = new google.visualization.ControlWrapper({
        'controlType':'StringFilter',
        'containerId':'func_table_filter',
        'options':{'matchType':'any', 'filterColumnIndex':'0,1', 'ui':{'label':'Filter table', 'labelSeparator':':', 'ui.labelStacking':'vertical', 'ui.cssClass':'custom_col_search'}
        }
    });

    // Table visualization option
    var interProMatchesTableOptions = new google.visualization.ChartWrapper({
        'chartType':'Table',
        'containerId':'func_table_pie_ipro',
        'options':{width:'520', allowHtml:true, showRowNumber:true, page:'enable', pageSize:10, pagingSymbols:{prev:'prev', next:'next'}, sortColumn:2, sortAscending:false }
    });

    // Create the dashboard.
    var dashboard = new google.visualization.Dashboard(document.getElementById('func_dashboard')).
        //Configure the string filter to affect the table contents
            bind(stringFilter, interProMatchesTableOptions).
        //Draw the dashboard
            draw(interProMatchesData);
}  //END function drawVisualization()

function drawToolbar() {
    var components = [
        {type:'html', datasource:'https://spreadsheets.google.com/tq?key=0AgWotcbTSSjYdGF6NjE0WGxGRmV5djJDWEZ6RzZhT2c'},
        {type:'csv', datasource:'https://spreadsheets.google.com/tq?key=0AgWotcbTSSjYdGF6NjE0WGxGRmV5djJDWEZ6RzZhT2c'},
        {type:'htmlcode', datasource:'https://spreadsheets.google.com/tq?key=0AgWotcbTSSjYdGF6NjE0WGxGRmV5djJDWEZ6RzZhT2c',
            gadget:'https://www.google.com/ig/modules/pie-chart.xml',
            style:'width: 800px; height: 700px; border: 1px solid black;'}
    ];

    var container1 = document.getElementById('toolbar_div');
    google.visualization.drawToolbar(container1, components);
}


// Callback that creates and populates a data table,
// instantiates the pie chart, passes in the data and
// draws it.
function drawInterProMatchesPieChart() {

    // Create the data table.
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Entry name');
    data.addColumn('number', 'Hits');
    data.addRows([
        <c:set var="addComma" value="false"/><c:forEach var="entry" items="${model.interProEntries}" varStatus="status"><c:choose><c:when test="${addComma}">,
        </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>
        ['${entry.entryDescription}', ${entry.numOfEntryHits}]</c:forEach>
    ]);

    // Set chart options
    var options = {title:'InterPro matches summary (Total: ${fn:length(model.interProEntries)})',
        titleTextStyle:{fontSize:12}, width:340, height:290,
        colors:[ <c:set var="addComma" value="false"/><c:forEach var="entry" items="${model.interProEntries}" varStatus="status"><c:choose><c:when test="${addComma}">, </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose><c:choose><c:when test="${status.index>9}">'#b9b9b9'</c:when><c:otherwise>'<c:out value="${colorCodeList[status.index]}"/>'</c:otherwise></c:choose></c:forEach>],
        pieSliceText:'none',
        legend:'none',
//        'backgroundColor':'red',
//       WITH CAPTION - legend:{position:'right', fontSize:10}, chartArea:{left:0, top:30, width:"42%", height:"100%"},
        chartArea:{left:20, top:30, width:"60%", height:"100%"},
        pieSliceBorderColor:'none',
        // 'sliceVisibilityThreshold':8.1/1251
        'sliceVisibilityThreshold':0.0066
    };

    // Instantiate and draw our chart, passing in some options.
    var func_chart = new google.visualization.PieChart(document.getElementById('func_chart_pie_ipro'));
    func_chart.draw(data, options);
}
</script>