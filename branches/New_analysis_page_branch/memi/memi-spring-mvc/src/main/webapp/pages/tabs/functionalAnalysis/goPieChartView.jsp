<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="go-terms-pie">
    <div class="go-chart">
        <div id="func_chart_pie_go_bp"></div>
        <div id="func_chart_pie_go_mf"></div>
        <div id="func_chart_pie_go_cc"></div>
    </div>
</div>
<script type="text/javascript">
drawBiologicalProcessPieChart();
drawMolecularFunctionPieChart();
drawCellularComponentPieChart();


//Draws GO term pie chart for Biological process
function drawBiologicalProcessPieChart() {

    var sortedBiologicalProcessGOTerms = new google.visualization.DataTable();
    sortedBiologicalProcessGOTerms.addColumn('string', 'GO term');
    sortedBiologicalProcessGOTerms.addColumn('number', 'Match');
    sortedBiologicalProcessGOTerms.addRows([
        <c:set var="addComma" value="false"/><c:forEach var="goTerm" items="${model.sortedBiologicalProcessGOTerms}" varStatus="status"><c:choose><c:when test="${addComma}">,
        </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>
        ['${goTerm.synonym}', ${goTerm.numberOfMatches}]</c:forEach>
    ]);

    var options = {'title':'Biological process',
        'titleTextStyle':{fontSize:12},
        'colors':['#058dc7', '#50b432', '#ed561b', '#edef00', '#24cbe5', '#64e572', '#ff9655', '#fff263', '#6af9c4', '#dabe88'],
        'width':420,
        'height':240,
        'legend':{position:'right', 'textStyle':{'fontSize':10}},
//        'legend':{position:'right', fontSize:10},
        'chartArea':{left:9, top:30, width:"100%", height:"80%"},
        'pieSliceBorderColor':'none',
//         'backgroundColor':'green',
        'sliceVisibilityThreshold':1 / 30
    };

    var pieChart = new google.visualization.PieChart(document.getElementById('func_chart_pie_go_bp'));
    pieChart.draw(sortedBiologicalProcessGOTerms, options);
}

//Draws GO term pie chart for Molecular Function
function drawMolecularFunctionPieChart() {

    var sortedMolecularFunctionGOTerms = new google.visualization.DataTable();
    sortedMolecularFunctionGOTerms.addColumn('string', 'GO term');
    sortedMolecularFunctionGOTerms.addColumn('number', 'Match');
    sortedMolecularFunctionGOTerms.addRows([
        <c:set var="addComma" value="false"/><c:forEach var="goTerm" items="${model.sortedMolecularFunctionGOTerms}" varStatus="status"><c:choose><c:when test="${addComma}">,
        </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>
        ['${goTerm.synonym}', ${goTerm.numberOfMatches}]</c:forEach>
    ]);

    // GO TERM Pie Molecular function
    var options = {'title':'Molecular function',
        'titleTextStyle':{fontSize:12},
        'colors':['#058dc7', '#50b432', '#ed561b', '#edef00', '#24cbe5', '#64e572', '#ff9655', '#fff263', '#6af9c4', '#dabe88'],
        'width':360,
        'height':240,
        'legend':{position:'right', 'textStyle':{'fontSize':10}},
        'chartArea':{left:9, top:30, width:"100%", height:"80%"},
        'pieSliceBorderColor':'none',
//                       'backgroundColor':'blue',
        'sliceVisibilityThreshold':1 / 30
    };

    var pieChart = new google.visualization.PieChart(document.getElementById('func_chart_pie_go_mf'));
    pieChart.draw(sortedMolecularFunctionGOTerms, options);
}



//Draws GO term pie chart for Cellular component
function drawCellularComponentPieChart() {
    var sortedCellularComponentGOTerms = new google.visualization.DataTable();
    sortedCellularComponentGOTerms.addColumn('string', 'GO term');
    sortedCellularComponentGOTerms.addColumn('number', 'Match');
    sortedCellularComponentGOTerms.addRows([
        <c:set var="addComma" value="false"/><c:forEach var="goTerm" items="${model.sortedCellularComponentGOTerms}" varStatus="status"><c:choose> <c:when test="${addComma}">,
        </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>
        ['${goTerm.synonym}', ${goTerm.numberOfMatches}]</c:forEach>
    ]);

    // GO TERM Pie Cellular component
    var options = {'title':'Cellular component',
        'titleTextStyle':{fontSize:12},
        'colors':['#058dc7', '#50b432', '#ed561b', '#edef00', '#24cbe5', '#64e572', '#ff9655', '#fff263', '#6af9c4', '#dabe88'],
        'width':360,
        'height':240,
        'legend':{position:'right', 'textStyle':{'fontSize':10}},
        'chartArea':{left:9, top:30, width:"100%", height:"80%"},
        'pieSliceBorderColor':'none',
//         'backgroundColor':'yellow',
        'sliceVisibilityThreshold':1 / 100
    };

    var pieChart = new google.visualization.PieChart(document.getElementById('func_chart_pie_go_cc'));
    pieChart.draw(sortedCellularComponentGOTerms, options);
}
</script>