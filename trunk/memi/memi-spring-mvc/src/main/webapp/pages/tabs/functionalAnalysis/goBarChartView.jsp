<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="go-terms-bar">
    <div class="go-chart">
        <div id="func_chart_bar_go_bp"></div>
        <div id="func_chart_bar_go_mf"></div>
        <div id="func_chart_bar_go_cc"></div>
    </div>
</div>

<script type="text/javascript">
drawBiologicalProcessBarChart();
drawMolecularFunctionBarChart();
drawCellularComponentBarChart();

function drawBiologicalProcessBarChart() {

    var biologicalProcessGOTerms = new google.visualization.DataTable();
    biologicalProcessGOTerms.addColumn('string', 'GO term');
    biologicalProcessGOTerms.addColumn('number', 'Match');
    biologicalProcessGOTerms.addRows([
        <c:set var="addComma" value="false"/><c:forEach var="goTerm" items="${model.biologicalProcessGOTerms}" varStatus="status"><c:choose><c:when test="${addComma}">,
        </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>
        ['${goTerm.synonym}', ${goTerm.numberOfMatches}]</c:forEach>
    ]);

    // GO TERM bar Biological Process
    var options = {'title':'Biological process', 'titleTextStyle':{fontSize:12}, 'fontName': '"Arial"', 'colors':['#058dc7'], 'width':360, 'height':600, 'chartArea':{left:250, top:40, width:"100%", height:"100%"}, 'vAxis':{textStyle:{fontSize:10}}, 'hAxis':{textPosition:'none', gridlines:{color:'white'}}, 'bar':{groupWidth:8}, 'legend':'none'
//  'vAxis':{'textPosition':'in'},
    };

    var barChart = new google.visualization.BarChart(document.getElementById('func_chart_bar_go_bp'));
    barChart.draw(biologicalProcessGOTerms, options);
}

function drawMolecularFunctionBarChart() {
    var molecularFunctionGOTerms = new google.visualization.DataTable();
    molecularFunctionGOTerms.addColumn('string', 'GO term');
    molecularFunctionGOTerms.addColumn('number', 'Match');
    molecularFunctionGOTerms.addRows([
        <c:set var="addComma" value="false"/><c:forEach var="goTerm" items="${model.molecularFunctionGOTerms}" varStatus="status"><c:choose><c:when test="${addComma}">,
        </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>
        ['${goTerm.synonym}', ${goTerm.numberOfMatches}]</c:forEach>
    ]);

    // GO TERM bar Molecular Function
    var options = {'title':'Molecular function', 'titleTextStyle':{fontSize:12}, 'fontName': '"Arial"', 'colors':['#50b432'], 'width':360, 'height':600, 'chartArea':{left:230, top:40, width:"100%", height:"100%"}, 'vAxis':{textStyle:{fontSize:10}}, 'hAxis':{textPosition:'none', gridlines:{color:'white'}}, 'bar':{groupWidth:8}, 'legend':'none' };

    var barChart = new google.visualization.BarChart(document.getElementById('func_chart_bar_go_mf'));
    barChart.draw(molecularFunctionGOTerms, options);
}

function drawCellularComponentBarChart() {
    var cellularComponentGOTerms = new google.visualization.DataTable();
    cellularComponentGOTerms.addColumn('string', 'GO term');
    cellularComponentGOTerms.addColumn('number', 'Match');
    cellularComponentGOTerms.addRows([
        <c:set var="addComma" value="false"/><c:forEach var="goTerm" items="${model.cellularComponentGOTerms}" varStatus="status"><c:choose><c:when test="${addComma}">,
        </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>
        ['${goTerm.synonym}', ${goTerm.numberOfMatches}]</c:forEach>
    ]);

    // GO TERM bar Cellular component
    var options = {'title':'Cellular component', 'titleTextStyle':{fontSize:12}, 'fontName': '"Arial"', 'colors':['#ed561b'], 'width':270, 'height':600, 'chartArea':{left:160, top:40, width:"100%", height:"100%"}, 'vAxis':{textStyle:{fontSize:10}}, 'hAxis':{textPosition:'none', gridlines:{color:'white', count:15}}, 'bar':{groupWidth:8}, 'legend':'none'};

    var barChart = new google.visualization.BarChart(document.getElementById('func_chart_bar_go_cc'));
    barChart.draw(cellularComponentGOTerms, options);
}
</script>