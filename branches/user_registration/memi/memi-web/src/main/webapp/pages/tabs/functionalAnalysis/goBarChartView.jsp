<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="go-terms-bar">
    <div class="go-chart">
        <div class="chart-block">
            <%--move on the left side to avoid overlap with snapshot--%>
            <div class="but_chart_export ui-buttonset">
             <button id="func-go-bar" style="display: none;"></button>
             <button id="select" class="ui-button ui-widget ui-state-default ui-button-text-icon-secondary ui-corner-right"><span class="ui-button-text">Export</span><span class="ui-button-icon-secondary ui-icon ui-icon-triangle-1-s"></span></button>
             </div>

             <ul class="export_list">
             <li><strong>Biological process</strong></li>
             <%--<li class="chart_exp_snap"> <a onclick="toImg(document.getElementById('func_chart_bar_go_bp'), document.getElementById('img_div'));">Snapshot</a></li>--%>
             <li class="chart_exp png" id="bar_bp_png"><a onclick="saveAsImg(document.getElementById('func_chart_bar_go_bp'),'<spring:message code="file.name.func.go.bar.chart.bp.png"/>',1);">PNG</a></li>
             <li class="chart_exp png" id="bar_bp_png_h"><a onclick="saveAsImg(document.getElementById('func_chart_bar_go_bp'),'<spring:message code="file.name.func.go.bar.chart.bp.high.png"/>',300/72);">PNG (Higher quality)</a></li>
             <li class="chart_exp" id="bar_bp_svg"><a onclick="saveAsSVG(document.getElementById('func_chart_bar_go_bp'),'<spring:message code="file.name.func.go.bar.chart.bp.svg"/>');">SVG</a></li>
             <li><strong>Molecular function</strong></li>
             <%--<li class="chart_exp_snap"> <a onclick="toImg(document.getElementById('func_chart_bar_go_mf'), document.getElementById('img_div'));">Snapshot</a></li>--%>
             <li class="chart_exp png" id="bar_mf_png"><a onclick="saveAsImg(document.getElementById('func_chart_bar_go_mf'),'<spring:message code="file.name.func.go.bar.chart.mf.png"/>',1);">PNG</a></li>
             <li class="chart_exp png" id="bar_mf_png_f"><a onclick="saveAsImg(document.getElementById('func_chart_bar_go_mf'),'<spring:message code="file.name.func.go.bar.chart.mf.high.png"/>',300/72);">PNG (Higher quality)</a></li>
             <li class="chart_exp" id="bar_mf_svg"><a onclick="saveAsSVG(document.getElementById('func_chart_bar_go_mf'),'<spring:message code="file.name.func.go.bar.chart.mf.svg"/>');">SVG</a></li>
             <li><strong>Cellular component</strong></li>
             <%--<li class="chart_exp_snap"> <a onclick="toImg(document.getElementById('func_chart_bar_go_cc'), document.getElementById('img_div'));">Snapshot</a></li>--%>
             <li class="chart_exp png" id="bar_cc_png"><a onclick="saveAsImg(document.getElementById('func_chart_bar_go_cc'),'<spring:message code="file.name.func.go.bar.chart.cc.png"/>',1);">PNG</a></li>
             <li class="chart_exp png" id="bar_cc_png_h"><a onclick="saveAsImg(document.getElementById('func_chart_bar_go_cc'),'<spring:message code="file.name.func.go.bar.chart.cc.high.png"/>',300/72);">PNG (Higher quality)</a></li>
             <li class="chart_exp" id="bar_cc_svg"><a onclick="saveAsSVG(document.getElementById('func_chart_bar_go_cc'),'<spring:message code="file.name.func.go.bar.chart.cc.svg"/>');">SVG</a></li>
             </ul>

        <div id="func_chart_bar_go_bp"></div>
        </div>

        <div class="chart-block">
        <div id="func_chart_bar_go_mf"></div>
        </div>

        <div class="chart-block">

        <div id="func_chart_bar_go_cc"></div>
        </div>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/export-button-menu.js"></script>