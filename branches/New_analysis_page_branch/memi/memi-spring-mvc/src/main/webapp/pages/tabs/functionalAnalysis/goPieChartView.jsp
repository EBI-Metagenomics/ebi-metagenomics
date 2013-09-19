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

        var options = {'title':'Biological process', 'titleTextStyle':{fontSize:12}, 'fontName':'"Arial"',
            'colors':[<c:set var="addComma" value="false"/><c:forEach begin="0" end="10" varStatus="status"><c:choose><c:when test="${addComma}">, </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose><c:choose><c:when test="${status.index>9}">'#ccc'</c:when><c:otherwise>'#<c:out value="${model.analysisResult.colorCodeList[status.index]}"/>'</c:otherwise></c:choose></c:forEach>],
            'width':420, 'height':240, 'legend':{position:'right', 'textStyle':{'fontSize':10}}, 'chartArea':{left:9, top:30, width:"100%", height:"80%"}, 'pieSliceBorderColor':'none',
//      'backgroundColor':'green',
            'sliceVisibilityThreshold':${model.functionalAnalysisResult.goTermSection.biologicalProcessGoTerms.sliceVisibilityThresholdValue}
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
        var options = {'title':'Molecular function', 'titleTextStyle':{fontSize:12}, 'fontName':'"Arial"',
            'colors':[<c:set var="addComma" value="false"/><c:forEach begin="0" end="10" varStatus="status"><c:choose><c:when test="${addComma}">, </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose><c:choose><c:when test="${status.index>9}">'#ccc'</c:when><c:otherwise>'#<c:out value="${model.analysisResult.colorCodeList[status.index]}"/>'</c:otherwise></c:choose></c:forEach>],
            'width':360, 'height':240, 'legend':{position:'right', 'textStyle':{'fontSize':10}}, 'chartArea':{left:9, top:30, width:"100%", height:"80%"}, 'pieSliceBorderColor':'none',
//      'backgroundColor':'blue',
            'sliceVisibilityThreshold':${model.functionalAnalysisResult.goTermSection.molecularFunctionGoTerms.sliceVisibilityThresholdValue}
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
        var options = {'title':'Cellular component', 'titleTextStyle':{fontSize:12}, 'fontName':'"Arial"',
            'colors':[<c:set var="addComma" value="false"/><c:forEach begin="0" end="10" varStatus="status"><c:choose><c:when test="${addComma}">, </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose><c:choose><c:when test="${status.index>9}">'#ccc'</c:when><c:otherwise>'#<c:out value="${model.analysisResult.colorCodeList[status.index]}"/>'</c:otherwise></c:choose></c:forEach>],
            'width':360, 'height':240, 'legend':{position:'right', 'textStyle':{'fontSize':10}}, 'chartArea':{left:9, top:30, width:"100%", height:"80%"}, 'pieSliceBorderColor':'none',
//      'backgroundColor':'yellow',
            'sliceVisibilityThreshold':${model.functionalAnalysisResult.goTermSection.cellularComponentGoTerms.sliceVisibilityThresholdValue}
        };

        var pieChart = new google.visualization.PieChart(document.getElementById('func_chart_pie_go_cc'));
        pieChart.draw(sortedCellularComponentGOTerms, options);
    }
</script>