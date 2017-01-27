<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="go-terms-bar">
    <div class="go-chart">
        <div class="grid_9">
       <div id="func_chart_bar_go_bp"></div>
        </div>

        <div class="grid_9">
        <div id="func_chart_bar_go_mf"></div>
        </div>

        <div class="grid_6">
        <div id="func_chart_bar_go_cc"></div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {

        $(document).ready(function () {
            // GO TERM bar Biological Process

            // BP data
            var data = [
                <c:set var="addComma" value="false"/>
                <c:forEach var="goTerm" items="${model.biologicalProcessGOTerms}" varStatus="status"><c:choose><c:when test="${addComma}">,
                </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>
                ['${goTerm.synonym}', ${goTerm.numberOfMatches}, <fmt:formatNumber type="number" maxFractionDigits="2" value="${goTerm.numberOfMatches*100 / model.functionalAnalysisResult.goTermSection.biologicalProcessGoTerms.totalHitsCount}" />]
                </c:forEach>
            ]

            //BP PIE
            $('#func_chart_bar_go_bp').highcharts({
                chart: {
                    height: 940,
                    zoomType: 'x',// add zoom options - useful when very small values
                    type: 'bar',
                    style: {
                        fontFamily: 'Helvetica'
                    }
                },
                navigation: {
                    buttonOptions: {
                        height: 40,
                        width: 40,
                        symbolX: 20,
                        symbolY: 20,
                        y: -10
                    }
                },
                exporting: {
                    buttons: {
                        contextButton: {
                            symbol: 'url(<c:url value="${baseURL}/img/ico_download.png"/>)',
                            menuItems: [
                                {
                                    textKey: 'printChart',
                                    onclick: function () {
                                        this.print();
                                    }
                                }, {
                                    separator: true
                                },
                                {
                                    //text: 'Export to PNG',
                                    textKey: 'downloadPNG',
                                    onclick: function () {
                                        this.exportChart({
                                            width: 1200,
                                            filename:'<spring:message code="file.name.func.go.bar.chart.bp"/>',// externalRunId need to be added to the model
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadJPEG',
                                    onclick: function () {
                                        this.exportChart({
                                            width: 1200,
                                            filename:'<spring:message code="file.name.func.go.bar.chart.bp"/>',// externalRunId need to be added to the model
                                            type: 'image/jpeg'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadPDF',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'<spring:message code="file.name.func.go.bar.chart.bp"/>',// externalRunId need to be added to the model
                                            type: 'application/pdf'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadSVG',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'<spring:message code="file.name.func.go.bar.chart.bp"/>',// externalRunId need to be added to the model
                                            type: 'image/svg+xml'
                                        });
                                    }
                                },
                            ],

                        }
                    }
                },
                credits: {enabled: false},//remove credit line
                colors: [ '#058dc7' ],// only one color for all PB
                legend: {
                    enabled:false,
                },
                title: {
                    text: 'Biological process ',
                    style: {
                        fontSize:16,
                        fontWeight: "bold"
                    }
                },
                subtitle: {
                    text: 'Total: ${model.functionalAnalysisResult.goTermSection.biologicalProcessGoTerms.totalHitsCount} matches - Drag to zoom in/out',
                    style: {
                        fontSize:11,
                    }},
                tooltip: {
                    backgroundColor: 'white',
                    headerFormat: '',
                    pointFormat: '<span style=\'color:{point.color}\'>&#9632;</span> {point.name}:<br/><strong>{point.y}</strong> matches ',
                    useHTML: true
                },
                xAxis: {
                    categories: [],//auto add {point.name}
                    labels: {
                        step:1,
                    },
                    lineColor: "#595959",
                    tickColor: ""
                    },
                yAxis: {
                    title: {
                        text: "Matches",
                        style: {
                            color: "#a0a0a0"
                        }
                    },

                    opposite: true,
                    endOnTick: false,//  no end on a tick
                    maxPadding: 0, // get last value on chart closer to edge of chart
                    labels: {
                        y:-6,
                        style:{
                            color: '#bbb'
                        }
                    },
                },
                plotOptions: {
                    bar: {
                        allowPointSelect: true,//let the slice be selected with animation
                        cursor: 'pointer',//show hand cursor on mouse-over
                        showInLegend: false,//no legend
                    },
                    series: {
                        dataLabels: {
                            enabled: false//hide labels next to slices
                        }
                    }
                },
                series: [{
                    name: 'bp_data',
                    borderColor: false,//remove border slices
                    data: data
                }]
            });
            // GO TERM bar Molecular Function

            // MF data
            var data = [
                <c:set var="addComma" value="false"/>
                <c:forEach var="goTerm" items="${model.molecularFunctionGOTerms}" varStatus="status"><c:choose><c:when test="${addComma}">,
                </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>
                ['${goTerm.synonym}', ${goTerm.numberOfMatches}, <fmt:formatNumber type="number" maxFractionDigits="2" value="${goTerm.numberOfMatches*100 / model.functionalAnalysisResult.goTermSection.molecularFunctionGoTerms.totalHitsCount}" />]
                </c:forEach>
            ]

            //MF PIE
            $('#func_chart_bar_go_mf').highcharts({
                chart: {
                    height: 940,
                    zoomType: 'x',// add zoom options - useful when very small values
                    type: 'bar',
                    style: {
                        fontFamily: 'Helvetica'
                    }
                },
                navigation: {
                    buttonOptions: {
                        height: 40,
                        width: 40,
                        symbolX: 20,
                        symbolY: 20,
                        y: -10
                    }
                },
                exporting: {
                    buttons: {
                        contextButton: {
                            symbol: 'url(<c:url value="${baseURL}/img/ico_download.png"/>)',
                            menuItems: [
                                {
                                    textKey: 'printChart',
                                    onclick: function () {
                                        this.print();
                                    }
                                }, {
                                    separator: true
                                },
                                {
                                    //text: 'Export to PNG',
                                    textKey: 'downloadPNG',
                                    onclick: function () {
                                        this.exportChart({
                                            width: 1200,
                                            filename:'<spring:message code="file.name.func.go.bar.chart.mf"/>',// externalRunId need to be added to the model
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadJPEG',
                                    onclick: function () {
                                        this.exportChart({
                                            width: 1200,
                                            filename:'<spring:message code="file.name.func.go.bar.chart.mf"/>',// externalRunId need to be added to the model
                                            type: 'image/jpeg'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadPDF',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'<spring:message code="file.name.func.go.bar.chart.mf"/>',// externalRunId need to be added to the model
                                            type: 'application/pdf'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadSVG',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'<spring:message code="file.name.func.go.bar.chart.mf"/>',// externalRunId need to be added to the model
                                            type: 'image/svg+xml'
                                        });
                                    }
                                },
                            ],

                        }
                    }
                },
                credits: {enabled: false},//remove credit line
                colors: [ '#50b432' ],// only one color for all MF #ed561b
                legend: {
                    enabled:false,
                },
                title: {
                    text: 'Molecular function',
                    style: {
                        fontSize:16,
                        fontWeight: "bold"
                    }
                },
                subtitle: {
                    text: 'Total: ${model.functionalAnalysisResult.goTermSection.molecularFunctionGoTerms.totalHitsCount} matches - Drag to zoom in/out',
                    style: {
                    }},
                tooltip: {
                    backgroundColor: 'white',
                    headerFormat: '',
                    pointFormat: '<span style=\'color:{point.color}\'>&#9632;</span> {point.name}:<br/><strong>{point.y}</strong> matches ',
                    useHTML: true
                },
                xAxis: {
                    categories: [],//auto add {point.name}
                    labels: {
                        step:1,
                    },
                    lineColor: "#595959",
                    tickColor: ""
                },
                yAxis: {
                    title: {
                        text: "Matches",
                        style: {
                            color: "#a0a0a0"
                        }
                    },

                    opposite: true,
                    endOnTick: false,//  no end on a tick
                    maxPadding: 0, // get last value on chart closer to edge of chart
                    labels: {
                        y:-6,
                        style:{
                            color: '#bbb'
                        }
                    },
                },
                plotOptions: {
                    bar: {
                        allowPointSelect: true,//let the slice be selected with animation
                        cursor: 'pointer',//show hand cursor on mouse-over
                        showInLegend: false,//no legend
                    },
                    series: {
                        dataLabels: {
                            enabled: false//hide labels next to slices
                        }
                    }
                },
                series: [{
                    name: 'mf_data',
                    borderColor: false,//remove border slices
                    data: data
                }]
            });

            // GO TERM bar Cellular component

            // CC data
            var data = [
                <c:set var="addComma" value="false"/>
                <c:forEach var="goTerm" items="${model.cellularComponentGOTerms}" varStatus="status"><c:choose><c:when test="${addComma}">,
                </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>
                ['${goTerm.synonym}', ${goTerm.numberOfMatches}, <fmt:formatNumber type="number" maxFractionDigits="2" value="${goTerm.numberOfMatches*100 / model.functionalAnalysisResult.goTermSection.cellularComponentGoTerms.totalHitsCount}" />]
                </c:forEach>
            ]

            //CC PIE
            $('#func_chart_bar_go_cc').highcharts({
                chart: {
                    height: 940,
                    zoomType: 'x',// add zoom options - useful when very small values
                    type: 'bar',
                    style: {
                        fontFamily: 'Helvetica'
                    }
                },
                navigation: {
                    buttonOptions: {
                        height: 40,
                        width: 40,
                        symbolX: 20,
                        symbolY: 20,
                        y: -10
                    }
                },
                exporting: {
                    buttons: {
                        contextButton: {
                            symbol: 'url(<c:url value="${baseURL}/img/ico_download.png"/>)',
                            menuItems: [
                                {
                                    textKey: 'printChart',
                                    onclick: function () {
                                        this.print();
                                    }
                                }, {
                                    separator: true
                                },
                                {
                                    //text: 'Export to PNG',
                                    textKey: 'downloadPNG',
                                    onclick: function () {
                                        this.exportChart({
                                            width: 1200,
                                            filename:'<spring:message code="file.name.func.go.bar.chart.cc"/>',// externalRunId need to be added to the model
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadJPEG',
                                    onclick: function () {
                                        this.exportChart({
                                            width: 1200,
                                            filename:'<spring:message code="file.name.func.go.bar.chart.cc"/>',// externalRunId need to be added to the model
                                            type: 'image/jpeg'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadPDF',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'<spring:message code="file.name.func.go.bar.chart.cc"/>',// externalRunId need to be added to the model
                                            type: 'application/pdf'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadSVG',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'<spring:message code="file.name.func.go.bar.chart.cc"/>',// externalRunId need to be added to the model
                                            type: 'image/svg+xml'
                                        });
                                    }
                                },
                            ],

                        }
                    }
                },
                credits: {enabled: false},//remove credit line
                colors: [ '#ed561b' ],// only one color for all MF
                legend: {
                    enabled:false,
                },
                title: {
                    text: 'Cellular component',
                    style: {
                        fontSize:16,
                        fontWeight: "bold"
                    }
                },
                subtitle: {
                    text: 'Total: ${model.functionalAnalysisResult.goTermSection.cellularComponentGoTerms.totalHitsCount} matches - Drag to zoom in/out',
                    style: {
                        fontSize:11,
                        fontWeight: "italic"
                    }},
                tooltip: {
                    backgroundColor: 'white',
                    headerFormat: '',
                    pointFormat: '<span style=\'color:{point.color}\'>&#9632;</span> {point.name}:<br/><strong>{point.y}</strong> matches ',
                    useHTML: true
                },
                xAxis: {
                    categories: [],//auto add {point.name}
                    labels: {
                        step:1,
                    },
                    lineColor: "#595959",
                    tickColor: ""
                },
                yAxis: {
                    title: {
                        text: "Matches",
                        style: {
                            color: "#a0a0a0"
                        }
                    },
                    opposite: true,
                    endOnTick: false,//  no end on a tick
                    maxPadding: 0, // get last value on chart closer to edge of chart
                    labels: {
                        y:-6,
                        style:{
                            color: '#bbb'
                        }
                    },
                },
                plotOptions: {
                    bar: {
                        allowPointSelect: true,//let the slice be selected with animation
                        cursor: 'pointer',//show hand cursor on mouse-over
                        showInLegend: false,//no legend
                    },
                    series: {
                        dataLabels: {
                            enabled: false//hide labels next to slices
                        }
                    }
                },
                series: [{
                    name: 'mf_data',
                    borderColor: false,//remove border slices
                    data: data
                }]
            });
        });
    });
</script>
<%--<script type="text/javascript">--%>
<%--drawBiologicalProcessBarChart();--%>
<%--drawMolecularFunctionBarChart();--%>
<%--drawCellularComponentBarChart();--%>

<%--function drawBiologicalProcessBarChart() {--%>

    <%--var biologicalProcessGOTerms = new google.visualization.DataTable();--%>
    <%--biologicalProcessGOTerms.addColumn('string', 'GO term');--%>
    <%--biologicalProcessGOTerms.addColumn('number', 'Match');--%>
    <%--biologicalProcessGOTerms.addRows([--%>
        <%--<c:set var="addComma" value="false"/><c:forEach var="goTerm" items="${model.biologicalProcessGOTerms}" varStatus="status"><c:choose><c:when test="${addComma}">,--%>
        <%--</c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>--%>
        <%--['${goTerm.synonym}', ${goTerm.numberOfMatches}]</c:forEach>--%>
    <%--]);--%>

    <%--// GO TERM bar Biological Process--%>
    <%--var options = {'title':'Biological process', 'titleTextStyle':{fontSize:12}, 'fontName': '"Arial"', 'colors':['#058dc7'], 'height':640, 'chartArea':{left:250, top:40, width:"100%", height:"100%"}, 'vAxis':{textStyle:{fontSize:10}}, 'hAxis':{textPosition:'none', gridlines:{color:'white'}}, 'bar':{groupWidth:8}, 'legend':'none'--%>
<%--//  'vAxis':{'textPosition':'in'},--%>
    <%--};--%>

    <%--var barChart = new google.visualization.BarChart(document.getElementById('func_chart_bar_go_bp'));--%>
    <%--barChart.draw(biologicalProcessGOTerms, options);--%>
<%--}--%>

<%--function drawMolecularFunctionBarChart() {--%>
    <%--var molecularFunctionGOTerms = new google.visualization.DataTable();--%>
    <%--molecularFunctionGOTerms.addColumn('string', 'GO term');--%>
    <%--molecularFunctionGOTerms.addColumn('number', 'Match');--%>
    <%--molecularFunctionGOTerms.addRows([--%>
        <%--<c:set var="addComma" value="false"/><c:forEach var="goTerm" items="${model.molecularFunctionGOTerms}" varStatus="status"><c:choose><c:when test="${addComma}">,--%>
        <%--</c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>--%>
        <%--['${goTerm.synonym}', ${goTerm.numberOfMatches}]</c:forEach>--%>
    <%--]);--%>

    <%--// GO TERM bar Molecular Function--%>
    <%--var options = {'title':'Molecular function', 'titleTextStyle':{fontSize:12}, 'fontName': '"Arial"', 'colors':['#50b432'], 'height':640, 'chartArea':{left:230, top:40, width:"100%", height:"100%"}, 'vAxis':{textStyle:{fontSize:10}}, 'hAxis':{textPosition:'none', gridlines:{color:'white'}}, 'bar':{groupWidth:8}, 'legend':'none' };--%>

    <%--var barChart = new google.visualization.BarChart(document.getElementById('func_chart_bar_go_mf'));--%>
    <%--barChart.draw(molecularFunctionGOTerms, options);--%>
<%--}--%>

<%--function drawCellularComponentBarChart() {--%>
    <%--var cellularComponentGOTerms = new google.visualization.DataTable();--%>
    <%--cellularComponentGOTerms.addColumn('string', 'GO term');--%>
    <%--cellularComponentGOTerms.addColumn('number', 'Match');--%>
    <%--cellularComponentGOTerms.addRows([--%>
        <%--<c:set var="addComma" value="false"/><c:forEach var="goTerm" items="${model.cellularComponentGOTerms}" varStatus="status"><c:choose><c:when test="${addComma}">,--%>
        <%--</c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>--%>
        <%--['${goTerm.synonym}', ${goTerm.numberOfMatches}]</c:forEach>--%>
    <%--]);--%>

    <%--// GO TERM bar Cellular component--%>
    <%--var options = {'title':'Cellular component', 'titleTextStyle':{fontSize:12}, 'fontName': '"Arial"', 'colors':['#ed561b'], 'height':640, 'chartArea':{left:160, top:40, width:"100%", height:"100%"}, 'vAxis':{textStyle:{fontSize:10}}, 'hAxis':{textPosition:'none', gridlines:{color:'white', count:15}}, 'bar':{groupWidth:8}, 'legend':'none'};--%>

    <%--var barChart = new google.visualization.BarChart(document.getElementById('func_chart_bar_go_cc'));--%>
    <%--barChart.draw(cellularComponentGOTerms, options);--%>
<%--}--%>

<%--</script>--%>