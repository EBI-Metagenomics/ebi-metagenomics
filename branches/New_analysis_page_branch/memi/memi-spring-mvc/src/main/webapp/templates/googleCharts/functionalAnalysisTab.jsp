<%-- This script produces the chart and the table for the 'InterPro match summary' section (functional analysis tab)--%>
<script type="text/javascript">

//  Load the Visualization API and the chart package.
//  Line moved to rootTemplate.jsp

//  Set a callback to run when the Google Visualization API is loaded.
google.setOnLoadCallback(drawInterProMatchesTable);
google.setOnLoadCallback(drawInterProMatchesPieChart);
google.setOnLoadCallback(drawBiologicalProcessPieChart);
google.setOnLoadCallback(drawMolecularFunctionPieChart);
google.setOnLoadCallback(drawCellularComponentPieChart);
google.setOnLoadCallback(drawBiologicalProcessBarChart);
google.setOnLoadCallback(drawMolecularFunctionBarChart);
google.setOnLoadCallback(drawCellularComponentBarChart);

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
        <c:set var="addComma" value="false"/>
        <c:forEach var="entry" items="${model.interProEntries}" varStatus="status">
        <c:choose><c:when test="${addComma}">,</c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>['<div title="${entry.entryDescription}" class="_cc" style="background-color:<c:choose><c:when test="${status.index>9}">#b9b9b9</c:when><c:otherwise><c:out value="${colorCodeList[status.index]}"/></c:otherwise></c:choose>; margin: 4px 6px 0 2px;"></div> <a title="${entry.entryDescription}" target="_blank" href="http://www.ebi.ac.uk/interpro/entry/${entry.entryID}">${entry.entryDescription}</a>', '${entry.entryID}', ${entry.numOfEntryHits}]</c:forEach>
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
        'options':{width:'500', allowHtml:true, showRowNumber:true, page:'enable', pageSize:10, pagingSymbols:{prev:'prev', next:'next'}, sortColumn:2, sortAscending:false }
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
        <c:set var="addComma" value="false"/>
        <c:forEach var="entry" items="${model.interProEntries}" varStatus="status"><c:choose><c:when test="${addComma}">,</c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>['${entry.entryDescription}', ${entry.numOfEntryHits}]</c:forEach>]);

    // Set chart options
    var options = {title:'InterPro matches summary (Total: ${fn:length(model.interProEntries)})',
        titleTextStyle:{fontSize:12}, width:340, height:290,
        colors:[
            <c:set var="addComma" value="false"/>
            <c:forEach var="entry" items="${model.interProEntries}" varStatus="status"><c:choose><c:when test="${addComma}">,</c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose><c:choose><c:when test="${status.index>9}">'#b9b9b9'</c:when><c:otherwise>'<c:out value="${colorCodeList[status.index]}"/>'</c:otherwise></c:choose></c:forEach>
        ],
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

//Draws GO term pie chart for Biological process
function drawBiologicalProcessPieChart() {
    var sortedBiologicalProcessGOTerms = new google.visualization.DataTable();
    sortedBiologicalProcessGOTerms.addColumn('string', 'GO term');
    sortedBiologicalProcessGOTerms.addColumn('number', 'Match');
    sortedBiologicalProcessGOTerms.addRows([
        <c:set var="addComma" value="false"/>
        <c:forEach var="goTerm" items="${model.sortedBiologicalProcessGOTerms}" varStatus="status">
        <c:choose>
        <c:when test="${addComma}">,
        </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise>
        </c:choose>
        ['${goTerm.synonym}', ${goTerm.numberOfMatches}]
        </c:forEach>
    ]);
    var options = {'title':'Biological process',
        'titleTextStyle':{fontSize:12},
        'colors':['#058dc7', '#50b432', '#ed561b', '#edef00', '#24cbe5', '#64e572', '#ff9655', '#fff263', '#6af9c4', '#dabe88'],
        'width':340,
        'height':220,
        'legend':{position:'right', fontSize:10},
        'chartArea':{left:0, top:30, width:"70%", height:"100%"},
        'pieSliceBorderColor':'none',
        'sliceVisibilityThreshold':1 / 30
    };

    var pieChart = new google.visualization.PieChart(document.getElementById('func_chart_pie_go_bp'));
    pieChart.draw(sortedBiologicalProcessGOTerms, options);
}

function drawBiologicalProcessBarChart() {
    var biologicalProcessGOTerms = new google.visualization.DataTable();
    biologicalProcessGOTerms.addColumn('string', 'GO term');
    biologicalProcessGOTerms.addColumn('number', 'Match');
    biologicalProcessGOTerms.addRows([
        <c:set var="addComma" value="false"/>
        <c:forEach var="goTerm" items="${model.biologicalProcessGOTerms}" varStatus="status">
        <c:choose>
        <c:when test="${addComma}">,
        </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise>
        </c:choose>
        ['${goTerm.synonym}', ${goTerm.numberOfMatches}]
        </c:forEach>
    ]);
    // GO TERM bar Biological Process
    var options = {'title':'Biological process', 'titleTextStyle':{fontSize:12}, 'colors':['#058dc7'], 'width':360, 'height':600, 'chartArea':{left:250, top:40, width:"100%", height:"100%"}, 'vAxis':{textStyle:{fontSize:10}}, 'hAxis':{textPosition:'none', gridlines:{color:'white'}}, 'bar':{groupWidth:8}, 'legend':'none'
//        'colors':['#5f8694'],
//        'vAxis':{'textPosition':'in'},
    };

    var barChart = new google.visualization.BarChart(document.getElementById('func_chart_bar_go_bp'));
    barChart.draw(biologicalProcessGOTerms, options);
}


//Draws GO term pie chart for Molecular Function
function drawMolecularFunctionPieChart() {

    var sortedMolecularFunctionGOTerms = new google.visualization.DataTable();
    sortedMolecularFunctionGOTerms.addColumn('string', 'GO term');
    sortedMolecularFunctionGOTerms.addColumn('number', 'Match');
    sortedMolecularFunctionGOTerms.addRows([
        <c:set var="addComma" value="false"/>
        <c:forEach var="goTerm" items="${model.sortedMolecularFunctionGOTerms}" varStatus="status">
        <c:choose>
        <c:when test="${addComma}">,
        </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise>
        </c:choose>
        ['${goTerm.synonym}', ${goTerm.numberOfMatches}]
        </c:forEach>
    ]);
    // GO TERM Pie Molecular function
    var options = {'title':'Molecular function',
        'titleTextStyle':{fontSize:12},
        'colors':['#058dc7', '#50b432', '#ed561b', '#edef00', '#24cbe5', '#64e572', '#ff9655', '#fff263', '#6af9c4', '#dabe88'],
        'width':340,
        'height':220,
        'legend':{position:'right', fontSize:10},
        'chartArea':{left:0, top:30, width:"70%", height:"100%"},
        'pieSliceBorderColor':'none',
        'sliceVisibilityThreshold':1 / 30
    };

    var pieChart = new google.visualization.PieChart(document.getElementById('func_chart_pie_go_mf'));
    pieChart.draw(sortedMolecularFunctionGOTerms, options);
}

function drawMolecularFunctionBarChart() {
    var molecularFunctionGOTerms = new google.visualization.DataTable();
    molecularFunctionGOTerms.addColumn('string', 'GO term');
    molecularFunctionGOTerms.addColumn('number', 'Match');
    molecularFunctionGOTerms.addRows([
        <c:set var="addComma" value="false"/>
        <c:forEach var="goTerm" items="${model.molecularFunctionGOTerms}" varStatus="status">
        <c:choose>
        <c:when test="${addComma}">,
        </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise>
        </c:choose>
        ['${goTerm.synonym}', ${goTerm.numberOfMatches}]
        </c:forEach>
    ]);

    // GO TERM bar Molecular Function
    var options = {'title':'Molecular function', 'titleTextStyle':{fontSize:12}, 'colors':['#50b432'], 'width':360, 'height':600, 'chartArea':{left:230, top:40, width:"100%", height:"100%"}, 'vAxis':{textStyle:{fontSize:10}}, 'hAxis':{textPosition:'none', gridlines:{color:'white'}}, 'bar':{groupWidth:8}, 'legend':'none'
//        'colors':['#91d450'], 'width':300, 'vAxis':{'textPosition':'in'},
    };

    var barChart = new google.visualization.BarChart(document.getElementById('func_chart_bar_go_mf'));
    barChart.draw(molecularFunctionGOTerms, options);
}

//Draws GO term pie chart for Cellular component
function drawCellularComponentPieChart() {
    var sortedCellularComponentGOTerms = new google.visualization.DataTable();
    sortedCellularComponentGOTerms.addColumn('string', 'GO term');
    sortedCellularComponentGOTerms.addColumn('number', 'Match');
    sortedCellularComponentGOTerms.addRows([
        <c:set var="addComma" value="false"/>
        <c:forEach var="goTerm" items="${model.sortedCellularComponentGOTerms}" varStatus="status">
        <c:choose>
        <c:when test="${addComma}">,
        </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise>
        </c:choose>
        ['${goTerm.synonym}', ${goTerm.numberOfMatches}]
        </c:forEach>
    ]);

    // GO TERM Pie Cellular component
    var options = {'title':'Cellular component',
        'titleTextStyle':{fontSize:12},
        'colors':['#058dc7', '#50b432', '#ed561b', '#edef00', '#24cbe5', '#64e572', '#ff9655', '#fff263', '#6af9c4', '#dabe88'],
        'width':340,
        'height':220,
        'legend':{position:'right', fontSize:10},
        'chartArea':{left:0, top:30, width:"70%", height:"100%"},
        'pieSliceBorderColor':'none',
        'sliceVisibilityThreshold':1 / 100
    };

    var pieChart = new google.visualization.PieChart(document.getElementById('func_chart_pie_go_cc'));
    pieChart.draw(sortedCellularComponentGOTerms, options);
}

function drawCellularComponentBarChart() {
    var cellularComponentGOTerms = new google.visualization.DataTable();
    cellularComponentGOTerms.addColumn('string', 'GO term');
    cellularComponentGOTerms.addColumn('number', 'Match');
    cellularComponentGOTerms.addRows([
        <c:set var="addComma" value="false"/>
        <c:forEach var="goTerm" items="${model.cellularComponentGOTerms}" varStatus="status">
        <c:choose>
        <c:when test="${addComma}">,
        </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise>
        </c:choose>
        ['${goTerm.synonym}', ${goTerm.numberOfMatches}]
        </c:forEach>
    ]);

    // GO TERM bar Cellular component
    var options = {'title':'Cellular component', 'titleTextStyle':{fontSize:12}, 'colors':['#ed561b'], 'width':270, 'height':600, 'chartArea':{left:160, top:40, width:"100%", height:"100%"}, 'vAxis':{textStyle:{fontSize:10}}, 'hAxis':{textPosition:'none', gridlines:{color:'white', count:15}}, 'bar':{groupWidth:8}, 'legend':'none'
//        'colors':['#535353'], 'vAxis':{'textPosition':'in'},
    };

    var barChart = new google.visualization.BarChart(document.getElementById('func_chart_bar_go_cc'));
    barChart.draw(cellularComponentGOTerms, options);
}
</script>