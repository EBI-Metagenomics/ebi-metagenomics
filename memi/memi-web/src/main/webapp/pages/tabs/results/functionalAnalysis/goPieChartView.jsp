<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="go-terms-pie">
    <div class="go-chart">
        <div class="grid_8">
            <div id="func_chart_pie_go_bp"></div>
        </div>

        <div class="grid_8">
            <div id="func_chart_pie_go_mf"></div>
        </div>

        <div class="grid_8">
            <div id="func_chart_pie_go_cc"></div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {

        $(document).ready(function () {


            //PIE CHART - Biological process

            // BP data
            var data = [

                <c:set var="addComma" value="false"/>
                <c:forEach var="goTerm" items="${model.sortedBiologicalProcessGOTerms}" varStatus="status"><c:choose><c:when test="${addComma}">,</c:when>
                <c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>
                ['${goTerm.synonym}', ${goTerm.numberOfMatches}, <fmt:formatNumber type="number" maxFractionDigits="2" value="${goTerm.numberOfMatches*100 / model.functionalAnalysisResult.goTermSection.biologicalProcessGoTerms.totalHitsCount}" />]
                </c:forEach>
            ]

            //IMPORTANT - regroup small values under thresold into "Others" to improve pie chart readability
            var newData=[];
            //calculating the threshold: changing for each chart (OR use 20/100 fix treshold) + use round value to be the same as value rounded for slices
            var thresOld=((${model.functionalAnalysisResult.goTermSection.biologicalProcessGoTerms.sliceVisibilityThresholdValue}*100).toFixed(2));

            var other=0.0
            for (var slice in data) {
                //thresold variable
                if (data[slice][2] < thresOld) {

                    other += data[slice][1];
                } else {
                    newData.push(data[slice]);
                }
            }

            //IMPORTANT remove "other" empty slice
            if (other == 0.0) {
                newData.push();}
            else {
                newData.push({name:'Other', y:other, color:'#ccc'});
            }

            //BP PIE CHART
            $('#func_chart_pie_go_bp').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'pie',
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
                                            filename:'<spring:message code="file.name.func.go.pie.chart.bp"/>',// externalRunId need to be added to the model
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadJPEG',
                                    onclick: function () {
                                        this.exportChart({
                                            width: 1200,
                                            filename:'<spring:message code="file.name.func.go.pie.chart.bp"/>',// externalRunId need to be added to the model
                                            type: 'image/jpeg'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadPDF',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'<spring:message code="file.name.func.go.pie.chart.bp"/>',// externalRunId need to be added to the model
                                            type: 'application/pdf'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadSVG',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'<spring:message code="file.name.func.go.pie.chart.bp"/>',// externalRunId need to be added to the model
                                            type: 'image/svg+xml'
                                        });
                                    }
                                },
                            ],

                        }
                    }
                },
                credits: {enabled: false},//remove credit line
                colors: [<c:set var="addComma" value="false"/><c:forEach begin="0" end="10" varStatus="status"><c:choose><c:when test="${addComma}">, </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose><c:choose><c:when test="${status.index>9}">'#ccc'</c:when><c:otherwise>'#<c:out value="${model.colorCodeList[status.index]}"/>'</c:otherwise></c:choose></c:forEach>],//color palette
                legend: {
                    title: {
                        text: '<span style="font-size: 12px; color: #666; font-weight: normal">Click to hide</span>',
                        style: {
                            fontStyle: 'italic'
                        }
                    },
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle ',
                    itemWidth: 120,
                    x: 0,
                    y: 60,
                    itemStyle: {fontWeight: "regular"}
                },
                title: {
                    text: 'Biological process ',
                    style: {
                        fontSize:16,
                        fontWeight: "bold"
                    }
                },
                subtitle: {
                    text: 'Total: ${model.functionalAnalysisResult.goTermSection.biologicalProcessGoTerms.totalHitsCount} matches',
                    style: {
                        fontSize:11,
                    }},
                tooltip: {
                    backgroundColor: 'white',
                    headerFormat: '',
                    pointFormat: '<span style=\'color:{point.color}\'>&#9632;</span> {point.name}: <br/><strong>{point.y}</strong> matches ({point.percentage:.2f}%)',
                    useHTML: true
                },

                plotOptions: {
                    pie: {
                        allowPointSelect: true,//let the slice be selected with animation
                        cursor: 'pointer',//show hand cursor on mouse-over
                        showInLegend: true,// legend but also need legend
                    },
                    series: {
                        dataLabels: {
                            enabled: false//hide labels next to slices
                        }
                    }
                },
                series: [{
                    name: 'func_pie_bp',
                    borderColor: false,
                    data: newData
                }]
            });


            //PIE CHART - Molecular Function

            // MF data
            var data = [
                <c:set var="addComma" value="false"/>
                <c:forEach var="goTerm" items="${model.sortedMolecularFunctionGOTerms}" varStatus="status"><c:choose><c:when test="${addComma}">,</c:when>
                <c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>
                ['${goTerm.synonym}', ${goTerm.numberOfMatches}, <fmt:formatNumber type="number" maxFractionDigits="2" value="${goTerm.numberOfMatches*100 / model.functionalAnalysisResult.goTermSection.molecularFunctionGoTerms.totalHitsCount}" />]
                </c:forEach>
            ]

            //IMPORTANT - regroup small values under thresold into "Others" to improve pie chart readability
            var newData=[];
            //calculating the threshold: changing for each chart (OR use 20/100 fix treshold) + use round value to be the same as value rounded for slices
            var thresOld=((${model.functionalAnalysisResult.goTermSection.molecularFunctionGoTerms.sliceVisibilityThresholdValue}*100).toFixed(2));

            var other=0.0
            for (var slice in data) {
                //thresold variable
                if (data[slice][2] < thresOld) {

                    other += data[slice][1];
                } else {
                    newData.push(data[slice]);
                }
            }

            //IMPORTANT remove "other" empty slice
            if (other == 0.0) {
                newData.push();}
            else {
                newData.push({name:'Other', y:other, color:'#ccc'});
            }

            //MF PIE CHART
            $('#func_chart_pie_go_mf').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'pie',
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
                                            filename:'<spring:message code="file.name.func.go.pie.chart.mf"/>',// externalRunId need to be added to the model
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadJPEG',
                                    onclick: function () {
                                        this.exportChart({
                                            width: 1200,
                                            filename:'<spring:message code="file.name.func.go.pie.chart.mf"/>',// externalRunId need to be added to the model
                                            type: 'image/jpeg'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadPDF',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'<spring:message code="file.name.func.go.pie.chart.mf"/>',// externalRunId need to be added to the model
                                            type: 'application/pdf'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadSVG',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'<spring:message code="file.name.func.go.pie.chart.mf"/>',// externalRunId need to be added to the model
                                            type: 'image/svg+xml'
                                        });
                                    }
                                },
                            ],

                        }
                    }
                },
                credits: {enabled: false},//remove credit line
                colors: [<c:set var="addComma" value="false"/><c:forEach begin="0" end="10" varStatus="status"><c:choose><c:when test="${addComma}">, </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose><c:choose><c:when test="${status.index>9}">'#ccc'</c:when><c:otherwise>'#<c:out value="${model.colorCodeList[status.index]}"/>'</c:otherwise></c:choose></c:forEach>],//color palette
                legend: {
                    title: {
                        text: '<span style="font-size: 12px; color: #666; font-weight: normal">Click to hide</span>',
                        style: {
                            fontStyle: 'italic'
                        }
                    },
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle ',
                    itemWidth: 120,
                    x: 0,
                    y: 60,
                    itemStyle: {fontWeight: "regular"}
                },
                title: {
                    text: 'Molecular function ',
                    style: {
                        fontSize:16,
                        fontWeight: "bold"
                    }
                },
                subtitle: {
                    text: 'Total: ${model.functionalAnalysisResult.goTermSection.molecularFunctionGoTerms.totalHitsCount} matches',
                    style: {
                        fontSize:11,
                    }},
                tooltip: {
                    backgroundColor: 'white',
                    headerFormat: '',
                    pointFormat: '<span style=\'color:{point.color}\'>&#9632;</span> {point.name}: <br/><strong>{point.y}</strong> matches ({point.percentage:.2f}%)',
                    useHTML: true
                },

                plotOptions: {
                    pie: {
                        allowPointSelect: true,//let the slice be selected with animation
                        cursor: 'pointer',//show hand cursor on mouse-over
                        showInLegend: true,// legend but also need legend: enable
                    },
                    series: {
                        dataLabels: {
                            enabled: false //hide labels next to slices
                        }
                    }
                },
                series: [{
                    name: 'func_pie_mf',
                    borderColor: false,
                    data: newData
                }]
            });

            //PIE CHART - Cellular component

            // CC data
            var data = [
                <c:set var="addComma" value="false"/>
                <c:forEach var="goTerm" items="${model.sortedCellularComponentGOTerms}" varStatus="status"><c:choose><c:when test="${addComma}">,</c:when>
                <c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>
                ['${goTerm.synonym}', ${goTerm.numberOfMatches}, <fmt:formatNumber type="number" maxFractionDigits="2" value="${goTerm.numberOfMatches*100 / model.functionalAnalysisResult.goTermSection.cellularComponentGoTerms.totalHitsCount}" />]
                </c:forEach>
            ]

            //IMPORTANT - regroup small values under thresold into "Others" to improve pie chart readability
            var newData=[];
            //calculating the threshold
            var thresOld=((${model.functionalAnalysisResult.goTermSection.cellularComponentGoTerms.sliceVisibilityThresholdValue}*100).toFixed(2));

            var other=0.0
            for (var slice in data) {
                //thresold variable
                if (data[slice][2] < thresOld) {
                    other += data[slice][1];
                } else {
                    newData.push(data[slice]);
                }
            }

            //IMPORTANT remove "other" empty slice
            if (other == 0.0) {
                newData.push();}
            else {
                newData.push({name:'Other', y:other, color:'#ccc'});
            }

            //CC PIE CHART
            $('#func_chart_pie_go_cc').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'pie',
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
                                            filename:'<spring:message code="file.name.func.go.pie.chart.cc"/>',// externalRunId need to be added to the model
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadJPEG',
                                    onclick: function () {
                                        this.exportChart({
                                            width: 1200,
                                            filename:'<spring:message code="file.name.func.go.pie.chart.cc"/>',// externalRunId need to be added to the model
                                            type: 'image/jpeg'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadPDF',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'<spring:message code="file.name.func.go.pie.chart.cc"/>',// externalRunId need to be added to the model
                                            type: 'application/pdf'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadSVG',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'<spring:message code="file.name.func.go.pie.chart.cc"/>',// externalRunId need to be added to the model
                                            type: 'image/svg+xml'
                                        });
                                    }
                                },
                            ],

                        }
                    }
                },
                credits: {enabled: false},//remove credit line
                colors: [<c:set var="addComma" value="false"/><c:forEach begin="0" end="10" varStatus="status"><c:choose><c:when test="${addComma}">, </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose><c:choose><c:when test="${status.index>9}">'#ccc'</c:when><c:otherwise>'#<c:out value="${model.colorCodeList[status.index]}"/>'</c:otherwise></c:choose></c:forEach>],//color palette
                legend: {
                    title: {
                        text: '<span style="font-size: 12px; color: #666; font-weight: normal">Click to hide</span>',
                        style: {
                            fontStyle: 'italic'
                        }
                    },
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle ',
                    itemWidth: 120,
                    x: 0,
                    y: 60,
                    itemStyle: {fontWeight: "regular"}
                },
                title: {
                    text: 'Cellular component',
                    style: {
                        fontSize:16,
                        fontWeight: "bold"
                    }
                },
                subtitle: {
                    text: 'Total: ${model.functionalAnalysisResult.goTermSection.cellularComponentGoTerms.totalHitsCount} matches',
                    style: {
                        fontSize:11,
                    }},
                tooltip: {
                    backgroundColor: 'white',
                    headerFormat: '',
                    pointFormat: '<span style=\'color:{point.color}\'>&#9632;</span> {point.name}: <br/><strong>{point.y}</strong> matches ({point.percentage:.2f}%)',
                    useHTML: true
                },

                plotOptions: {
                    pie: {
                        allowPointSelect: true,//let the slice be selected with animation
                        cursor: 'pointer',//show hand cursor on mouse-over
                        showInLegend: true,// legend but also need legend: enable
                    },
                    series: {
                        dataLabels: {
                            enabled: false //hide labels next to slices
                        }
                    }
                },
                series: [{
                    name: 'func_pie_cc',
                    borderColor: false,
                    data: newData
                }]
            });

        });
    });
</script>
<%--<div id="go-terms-pie">--%>
    <%--<div class="go-chart">--%>
        <%--<div class="chart-block col-1-3">--%>

         <%--<div class="but_chart_export ui-buttonset">--%>
         <%--<button id="select" class="ui-button ui-widget ui-state-default ui-button-text-icon-secondary ui-corner-right"><span class="ui-button-text">Export</span><span class="ui-button-icon-secondary ui-icon ui-icon-triangle-1-s"></span></button>--%>
         <%--</div>--%>

         <%--<ul class="export_list">--%>
         <%--<li><strong>Biological process</strong></li>--%>
         <%--<li class="chart_exp png" id="pie_bp_png"><a onclick="saveAsImg(document.getElementById('func_chart_pie_go_bp'),'<spring:message code="file.name.func.go.pie.chart.bp.png"/>',1);">PNG</a></li>--%>
         <%--<li class="chart_exp png" id="pie_bp_png_h"><a onclick="saveAsImg(document.getElementById('func_chart_pie_go_bp'),'<spring:message code="file.name.func.go.pie.chart.bp.high.png"/>',300/72);">PNG (Higher quality)</a></li>--%>
         <%--<li class="chart_exp" id="pie_bp_svg"><a onclick="saveAsSVG(document.getElementById('func_chart_pie_go_bp'),'<spring:message code="file.name.func.go.pie.chart.bp.svg"/>');">SVG</a></li>--%>
         <%--<li><strong>Molecular function</strong></li>--%>
         <%--<li class="chart_exp png" id="pie_mf_png"><a onclick="saveAsImg(document.getElementById('func_chart_pie_go_mf'),'<spring:message code="file.name.func.go.pie.chart.mf.png"/>',1);">PNG</a></li>--%>
         <%--<li class="chart_exp png" id="pie_mf_png_h"><a onclick="saveAsImg(document.getElementById('func_chart_pie_go_mf'),'<spring:message code="file.name.func.go.pie.chart.mf.high.png"/>',300/72);">PNG (Higher quality)</a></li>--%>
         <%--<li class="chart_exp" id="pie_mf_svg"><a onclick="saveAsSVG(document.getElementById('func_chart_pie_go_mf'),'<spring:message code="file.name.func.go.pie.chart.mf.svg"/>');">SVG</a></li>--%>
         <%--<li><strong>Cellular component</strong></li>--%>
         <%--<li class="chart_exp png" id="pie_cc_png"><a onclick="saveAsImg(document.getElementById('func_chart_pie_go_cc'),'<spring:message code="file.name.func.go.pie.chart.cc.png"/>',1);">PNG</a></li>--%>
         <%--<li class="chart_exp png" id="pie_cc_png_h"><a onclick="saveAsImg(document.getElementById('func_chart_pie_go_cc'),'<spring:message code="file.name.func.go.pie.chart.cc.high.png"/>',300/72);">PNG (Higher quality)</a></li>--%>
         <%--<li class="chart_exp" id="pie_cc_svg"><a onclick="saveAsSVG(document.getElementById('func_chart_pie_go_cc'),'<spring:message code="file.name.func.go.pie.chart.cc.svg"/>');">SVG</a></li>--%>
         <%--</ul>--%>

        <%--<div id="func_chart_pie_go_bp"></div>--%>
        <%--</div>--%>

        <%--<div class="chart-block col-1-3">--%>
        <%--<div id="func_chart_pie_go_mf"></div>--%>
        <%--</div>--%>

        <%--<div class="chart-block col-1-3">--%>
        <%--<div id="func_chart_pie_go_cc"></div>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>
<%--<script type="text/javascript">--%>
    <%--drawBiologicalProcessPieChart();--%>
    <%--drawMolecularFunctionPieChart();--%>
    <%--drawCellularComponentPieChart();--%>


    <%--//Draws GO term pie chart for Biological process--%>
    <%--function drawBiologicalProcessPieChart() {--%>

        <%--var sortedBiologicalProcessGOTerms = new google.visualization.DataTable();--%>
        <%--sortedBiologicalProcessGOTerms.addColumn('string', 'GO term');--%>
        <%--sortedBiologicalProcessGOTerms.addColumn('number', 'Match');--%>
        <%--sortedBiologicalProcessGOTerms.addRows([--%>
            <%--<c:set var="addComma" value="false"/><c:forEach var="goTerm" items="${model.sortedBiologicalProcessGOTerms}" varStatus="status"><c:choose><c:when test="${addComma}">,--%>
            <%--</c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>--%>
            <%--['${goTerm.synonym}', ${goTerm.numberOfMatches}]</c:forEach>--%>
        <%--]);--%>

        <%--var options = {'title':'Biological process', 'titleTextStyle':{fontSize:12}, 'fontName':'"Arial"',--%>
            <%--'colors':[<c:set var="addComma" value="false"/><c:forEach begin="0" end="10" varStatus="status"><c:choose><c:when test="${addComma}">, </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose><c:choose><c:when test="${status.index>9}">'#ccc'</c:when><c:otherwise>'#<c:out value="${model.colorCodeList[status.index]}"/>'</c:otherwise></c:choose></c:forEach>],--%>
            <%--'height':240, 'legend':{position:'right', 'textStyle':{'fontSize':10}}, 'chartArea':{left:9, top:30, width:"100%", height:"80%"}, 'pieSliceBorderColor':'none',--%>
<%--//      'backgroundColor':'green',--%>
            <%--'sliceVisibilityThreshold':${model.functionalAnalysisResult.goTermSection.biologicalProcessGoTerms.sliceVisibilityThresholdValue}--%>
        <%--};--%>

        <%--var pieChart = new google.visualization.PieChart(document.getElementById('func_chart_pie_go_bp'));--%>
        <%--pieChart.draw(sortedBiologicalProcessGOTerms, options);--%>
    <%--}--%>

    <%--//Draws GO term pie chart for Molecular Function--%>
    <%--function drawMolecularFunctionPieChart() {--%>

        <%--var sortedMolecularFunctionGOTerms = new google.visualization.DataTable();--%>
        <%--sortedMolecularFunctionGOTerms.addColumn('string', 'GO term');--%>
        <%--sortedMolecularFunctionGOTerms.addColumn('number', 'Match');--%>
        <%--sortedMolecularFunctionGOTerms.addRows([--%>
            <%--<c:set var="addComma" value="false"/><c:forEach var="goTerm" items="${model.sortedMolecularFunctionGOTerms}" varStatus="status"><c:choose><c:when test="${addComma}">,--%>
            <%--</c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>--%>
            <%--['${goTerm.synonym}', ${goTerm.numberOfMatches}]</c:forEach>--%>
        <%--]);--%>

        <%--// GO TERM Pie Molecular function--%>
        <%--var options = {'title':'Molecular function', 'titleTextStyle':{fontSize:12}, 'fontName':'"Arial"',--%>
           <%--'colors':[<c:set var="addComma" value="false"/><c:forEach begin="0" end="10" varStatus="status"><c:choose><c:when test="${addComma}">, </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose><c:choose><c:when test="${status.index>9}">'#ccc'</c:when><c:otherwise>'#<c:out value="${model.colorCodeList[status.index]}"/>'</c:otherwise></c:choose></c:forEach>],--%>
           <%--'height':240, 'legend':{position:'right', 'textStyle':{'fontSize':10}}, 'chartArea':{left:9, top:30, width:"100%", height:"80%"}, 'pieSliceBorderColor':'none',--%>
<%--//      'backgroundColor':'blue',--%>
           <%--'sliceVisibilityThreshold':${model.functionalAnalysisResult.goTermSection.molecularFunctionGoTerms.sliceVisibilityThresholdValue}--%>
        <%--};--%>

        <%--var pieChart = new google.visualization.PieChart(document.getElementById('func_chart_pie_go_mf'));--%>
        <%--pieChart.draw(sortedMolecularFunctionGOTerms, options);--%>
    <%--}--%>


    <%--//Draws GO term pie chart for Cellular component--%>
    <%--function drawCellularComponentPieChart() {--%>
        <%--var sortedCellularComponentGOTerms = new google.visualization.DataTable();--%>
        <%--sortedCellularComponentGOTerms.addColumn('string', 'GO term');--%>
        <%--sortedCellularComponentGOTerms.addColumn('number', 'Match');--%>
        <%--sortedCellularComponentGOTerms.addRows([--%>
            <%--<c:set var="addComma" value="false"/><c:forEach var="goTerm" items="${model.sortedCellularComponentGOTerms}" varStatus="status"><c:choose> <c:when test="${addComma}">,--%>
            <%--</c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>--%>
            <%--['${goTerm.synonym}', ${goTerm.numberOfMatches}]</c:forEach>--%>
        <%--]);--%>

        <%--// GO TERM Pie Cellular component--%>
        <%--var options = {'title':'Cellular component', 'titleTextStyle':{fontSize:12}, 'fontName':'"Arial"',--%>
            <%--'colors':[<c:set var="addComma" value="false"/><c:forEach begin="0" end="10" varStatus="status"><c:choose><c:when test="${addComma}">, </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose><c:choose><c:when test="${status.index>9}">'#ccc'</c:when><c:otherwise>'#<c:out value="${model.colorCodeList[status.index]}"/>'</c:otherwise></c:choose></c:forEach>],--%>
            <%--'height':240, 'legend':{position:'right', 'textStyle':{'fontSize':10}}, 'chartArea':{left:9, top:30, width:"100%", height:"80%"}, 'pieSliceBorderColor':'none',--%>
<%--//      'backgroundColor':'yellow',--%>
            <%--'sliceVisibilityThreshold':${model.functionalAnalysisResult.goTermSection.cellularComponentGoTerms.sliceVisibilityThresholdValue}--%>
        <%--};--%>

        <%--var pieChart = new google.visualization.PieChart(document.getElementById('func_chart_pie_go_cc'));--%>
        <%--pieChart.draw(sortedCellularComponentGOTerms, options);--%>
    <%--}--%>
    <%--//make the charts responsive--%>
           <%--$(window).resize(function(){--%>
               <%--drawBiologicalProcessPieChart();--%>
               <%--drawMolecularFunctionPieChart();--%>
               <%--drawCellularComponentPieChart();--%>
           <%--});--%>
<%--</script>--%>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/export-button-menu.js"></script>--%>