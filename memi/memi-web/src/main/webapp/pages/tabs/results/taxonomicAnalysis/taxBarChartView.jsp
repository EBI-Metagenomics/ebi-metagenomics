<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="tax-bar">

    <div  class="chart_container" >
        <div class="grid_8"><div id="tax_chart_bar_dom" style="height: 360px;"></div></div>
        <div class="grid_16">  <div id="tax_chart_bar_phy" style="height: 360px;"></div></div>
        <div class="grid_24"> <table id="tax_table_bar" class="table-glight"></table></div>
    </div>

    <div class="msg_help blue_h phylum_help">
        <p><span class="icon icon-generic" data-icon="i"></span>This view aggregates the taxonomy
            information at the domain and phylum level. To download the full detailed taxonomy distribution
            (TSV format),
            <a href="#ui-id-6" class="open-tab" data-tab-index="4"> please follow this link</a>.</p>
    </div>

</div>

<c:set var="phylumCompositionTitle" scope="request" value="Phylum composition"/>

<script type="text/javascript">

    // Create the Datatable
    $(document).ready(function() {

        //table data
        var rowData = [
            <%--<c:set var="addComma" value="false"/>--%>
            <c:forEach var="taxonomyData" items="${model.taxonomyAnalysisResult.taxonomyDataSet}" varStatus="status">
            <%--<c:choose><c:when test="${addComma}">,</c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>--%>
            <c:choose>
            <c:when test="${taxonomyData.phylum=='Unassigned'}">
            //remove unassigned data
            </c:when>
            <c:otherwise>
            ['${status.index}','${taxonomyData.phylum}', '${taxonomyData.superKingdom}', ${taxonomyData.numberOfHits}, ${taxonomyData.percentage}],
            <%--['${row.index}','<div title="${taxonomyData.phylum}" class="puce-square-legend" style="background-color: #${taxonomyData.colorCode};"></div> ${taxonomyData.phylum}', '${taxonomyData.superKingdom}', ${taxonomyData.numberOfHits}, ${taxonomyData.percentage},'${taxonomyData.phylum}'],--%>
            </c:otherwise>
            </c:choose>
            </c:forEach>
        ];

        var t = $('#tax_table_bar').DataTable( {
            order: [[ 3, "desc" ]],
            columnDefs: [ //add responsive style as direct css doesn't work
                {className: "xs_hide", "targets": [0,2]},//hide number + domain columns
                {className: "table-align-right", "targets": [3,4]}//numbers easier to compare
            ],
            //adding ID numbers for each row - used for interaction with chart
            createdRow: function (row, rowData, dataIndex) {
                $(row).addClass(""+dataIndex);
            },
            oLanguage: {
                "sSearch": "Filter table: "
            },
            lengthMenu: [[25, 50, 100, -1], [25, 50, 100, "All"]],
            data: rowData,
            columns: [
                {title: "" },
                {title: "Phylum" },
                {title: "Domain" },
                <c:choose>
                <c:when test="${model.run.releaseVersion == '1.0'}">
                {title: "Unique OTUs" },
                </c:when>
                    <c:otherwise>{title: "Reads"},
                </c:otherwise>
                </c:choose>
                {title: "%" }
            ]
        } );
        // insert number for lines as  first column and make it not sortable nor searchable
        t.on( 'order.dt search.dt', function () {
            t.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
                cell.innerHTML = i+1;
            } );
        } ).draw();

        // ADD INTERACTION BETWEEN TABLE ROW AND BAR CHART
        $("#tax_table_bar tbody").on('click', 'tr', function(){
            //important - use row Id for interaction otherwise table sorting was messsing the use of $(this).index()
            var legInd = (this).className.split(' ')[0];
            var chart = $('#tax_chart_bar_phy').highcharts();
            var point = chart.series[0].data[legInd];
                // if value for point like for results between 1-10
                if (point) {
                  point.select(null, true);// toggled and multi-selection - "fake" but working show/hide effect
//                alert(point.category)
//                alert(point.visible)//true or not
//                point.remove(); //kills the index but resize well
//                point.graphic.hide();//can't find a way to do working if statement working like: if (point.graphic).
                }
                else
                //show/hide whole "other" slice
                {
                  var point = chart.series[0].data[9];
                  point.select(null, true);
                }
                $(this).toggleClass("disabled");

        });

        $("#tax_table_bar tbody").on('mouseenter', 'tr', function(){
            var legInd = (this).className.split(' ')[0];
            var chart = $('#tax_chart_bar_phy').highcharts();
            var point = chart.series[0].data[legInd];
            if (point) {
                point.setState('hover');
                //show tooltip
                chart.tooltip.refresh(point);
            } else
            //highlight other
            {var point = $('#tax_chart_bar_phy').highcharts().series[0].points[9];
                point.setState('hover');
                chart.tooltip.refresh(point);}
        });

        $("#tax_table_bar tbody").on('mouseleave', 'tr', function(){
            var legInd = (this).className.split(' ')[0];
            var chart = $('#tax_chart_bar_phy').highcharts();
            var point = chart.series[0].data[legInd];
            if (point) {
                point.setState('');}
            else
            //unselect other slice
            {var point = $('#tax_chart_bar_phy').highcharts().series[0].points[9];
                point.setState('');}
        });

        //HIGHLIGHT TERMS IN DATATABLE

        $("#tax_table_bar_filter input").addClass("filter_sp");

        // Highlight the search term in the table (all except first number column) using the filter input, using jQuery Highlight plugin
        $('.filter_sp').keyup(function () {
            $("#tax_table_bar tr td:nth-child(n+2)").highlight($(this).val());
            $('#tax_table_bar tr td:nth-child(n+2)').unhighlight();// highlight more than just first character entered in the text box and reiterate the span to highlight
            $('#tax_table_bar tr td:nth-child(n+2)').highlight($(this).val());
        });
        // remove highlight when click on X (clear button)
        $('input[type=search]').on('search', function () {
            $('#tax_table_bar tr td').unhighlight();
        });
    } );

</script>

<script type="text/javascript">
    $(function () {

        $(document).ready(function () {
            var data = [
                <c:forEach var="domainEntry" items="${model.taxonomyAnalysisResult.domainComposition.domainMap}">
                ['${domainEntry.key}', ${domainEntry.value}],
                </c:forEach>
            ]
            // Remove the unassigned from displaying on the chart
            var iData = data.filter(function(item){ return item[0] != "Unassigned" })

            // Get a value for unassigned reads/OTUs
            var totalUnassigned=[];
            for (var slice in data) {
                if (data[slice][0] == "Unassigned") {
                    totalUnassigned += data[slice][1];
                }
            }

            //BAR CHART - DOMAIN COMPOSITION
            $('#tax_chart_bar_dom').highcharts({
                chart: {
                    type: 'column',
                    style: {
                        fontFamily: 'Helvetica'
                    }
                },
                navigation: {
                    buttonOptions: {
                        height: 32,
                        width: 32,
                        symbolX: 16,
                        symbolY: 16,
                        y: -10
                    }
                },
                exporting: {
                    buttons: {
                        contextButton: {
                            symbol: 'url(${pageContext.request.contextPath}/img/ico_download.png)',// img icon export
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
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.bar.chart.domain"/>',
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadJPEG',
                                    onclick: function () {
                                        this.exportChart({
                                            width: 1200,
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.bar.chart.domain"/>',
                                            type: 'image/jpeg'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadPDF',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.bar.chart.domain"/>',
                                            type: 'application/pdf'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadSVG',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.bar.chart.domain"/>',
                                            type: 'image/svg+xml'
                                        });
                                    }
                                },
                            ],


                        }
                    }
                },
                credits: {text: null},//remove credit line
                colors: [${model.taxonomyAnalysisResult.domainComposition.colorCode}],//color palette
                title: {
                    text: 'Domain composition',
                    style: {
                        fontSize:16,
                        fontWeight: "bold"
                    }
                },
                tooltip: {
                    backgroundColor: 'white',
                    headerFormat: '',
                    <c:choose>
                    <c:when test="${model.run.releaseVersion == '1.0'}">
                    pointFormat: '<span style=\'color:{point.color}\'>&#9632;</span> <span style=\'font-size:88%;\'>{point.name}: </span><br/><strong><small>{point.y}</small></strong> OTUs',
                    </c:when>
                    <c:otherwise>
                    pointFormat: '<span style=\'color:{point.color}\'>&#9632;</span> <span style=\'font-size:88%;\'>{point.name}: </span><br/><strong><small>{point.y}</small></strong> reads',
                    </c:otherwise>
                    </c:choose>
                    useHTML: true
                },
                xAxis: {
                    categories: [],//auto add {point.name}
                },
                yAxis: {
                    maxPadding: 0, // get last value on chart closer to edge of chart
                    endOnTick: false,//  not end on a tick
                    labels: {
                        style:{
                            color: '#bbb'
                        }
                    },
                    title:{
                        <c:choose>
                        <c:when test="${model.run.releaseVersion == '1.0'}">
                        text: 'Unique OTUs',
                        </c:when>
                        <c:otherwise>
                        text: 'Reads',
                        </c:otherwise>
                        </c:choose>
                        enabled: true,
                        style:{
                            color: '#bbb'
                        }}
                },
//                plotOptions: {
//                    series: {
//                        dataLabels: {
//                            enabled: true,
//                            format: '{point.name}'
//
//                        }
//                    }
//                },
                legend: {enabled: false},//remove legend
                series: [{
                    name: 'Domain',
                    colorByPoint: true,//one color for each category
//                    borderColor: '#686868',
                    data:iData
                }]
            });

            //BAR CHART - PHYLUM COMPOSITION

            // Phylum data
            var data = [
                <c:forEach var="taxonomyData" items="${model.taxonomyAnalysisResult.taxonomyDataSet}" varStatus="status">
                ['${taxonomyData.phylum}', ${taxonomyData.numberOfHits}, ${taxonomyData.percentage}],
                </c:forEach>
            ]

            // Remove the unassigned from displaying on the chart
            var iData = data.filter(function(item){ return item[0] != "Unassigned" })

            //IMPORTANT - regroup small values <0.1 into "Others" to improve bar chart readability
            var newData=[];
            //calculating the threshold: changing for each chart
            var thresOld=((${model.taxonomyAnalysisResult.sliceVisibilityThresholdNumerator / model.taxonomyAnalysisResult.sliceVisibilityThresholdDenominator}*100).toFixed(2));

            var other=0.0;
            for (var slice in iData) {
            //thresold 0.1
                if (iData[slice][2] < thresOld) {
                    other += iData[slice][1];
                } else {
                    newData.push(iData[slice]);
                }
            }

            //IMPORTANT create "other"  bar
            if (other == 0.0) {
                newData.push();}
            else {
                newData.push({name:'Other', y:other, color:'#ccc'});
            }

            //BAR CHART PHYLUM
            $('#tax_chart_bar_phy').highcharts({
                chart: {
                    zoomType: 'x',// add zoom options -useful when very small values
                    type: 'column',
                    style: {
                        fontFamily: 'Helvetica'
                    }
                },
                navigation: {
                    buttonOptions: {
                        height: 32,
                        width: 32,
                        symbolX: 16,
                        symbolY: 16,
                        y: -10
                    }
                },
                exporting: {
                    buttons: {
                        contextButton: {
                            symbol: 'url(${pageContext.request.contextPath}/img/ico_download.png)',// img icon export
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
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.bar.chart.phylum"/>',
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadJPEG',
                                    onclick: function () {
                                        this.exportChart({
                                            width: 1200,
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.bar.chart.phylum"/>',
                                            type: 'image/jpeg'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadPDF',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.bar.chart.phylum"/>',
                                            type: 'application/pdf'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadSVG',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.bar.chart.phylum"/>',
                                            type: 'image/svg+xml'
                                        });
                                    }
                                },
                            ],


                        }
                    }
                },
                credits: {enabled: false},//remove credit line
                colors: [${model.taxonomyAnalysisResult.colorCodeForChart} ],//color palette
                legend: {enabled: false},//remove legend
                title: {
                    text: '${phylumCompositionTitle}',
                    style: {
                        fontSize:16,
                        fontWeight: "bold"
                    }
                },
                subtitle: {
                    <c:choose>
                    <c:when test="${model.run.releaseVersion == '1.0'}">
                    text: 'Total: ${model.taxonomyAnalysisResult.sliceVisibilityThresholdDenominator} OTUs including '+totalUnassigned+' unassigned - Drag to zoom in/out',
                    </c:when>
                    <c:otherwise>
                    text: 'Total: ${model.taxonomyAnalysisResult.sliceVisibilityThresholdDenominator} reads including '+totalUnassigned+' unassigned - Drag to zoom in/out',
                    </c:otherwise>
                    </c:choose>
                    style: {
                        fontSize:11,
                    }},
                tooltip: {
                    backgroundColor: 'white',
                    headerFormat: '',
                    <c:choose>
                    <c:when test="${model.run.releaseVersion == '1.0'}">
                    pointFormat: '<span style=\'color:{point.color}\'>&#9632;</span> {point.name}: <br/><strong>{point.y}</strong> OTUs',
                    </c:when>
                    <c:otherwise>
                    pointFormat: '<span style=\'color:{point.color}\'>&#9632;</span> {point.name}: <br/><strong>{point.y}</strong> reads',
                    </c:otherwise>
                    </c:choose>
                    useHTML: true
                },
                xAxis: {
                    categories: [],//auto add {point.name}
                },
                yAxis: {
                    maxPadding: 0, // get last value on chart closer to edge of chart
                    endOnTick: false,//  no end on a tick
                    labels: {
                        style:{
                            color: '#bbb'
                        }
                    },
                    title:{
                        <c:choose>
                        <c:when test="${model.run.releaseVersion == '1.0'}">
                        text: 'Unique OTUs',
                        </c:when>
                        <c:otherwise>
                        text: 'Reads',
                        </c:otherwise>
                        </c:choose>
                        enabled: true,
                        style:{
                            color: '#bbb'
                        }}
                },
                plotOptions: {
                    series: {
                        dataLabels: {
                            enabled: false,
                            format: '{point.name}',
                        },
                        states: {//used for interaction with table
                            select: {
                                borderWidth:0,
                                borderColor:'white',
                                color: 'transparent'
                            }
                        }
                    }
                },
                series: [{
                    name: 'Phylumn',
                    colorByPoint: true,
//                    borderColor: 'rgba(0, 0, 0, 0.1)',
                    pointPadding: 0,
                    groupPadding: 0.1,
                    data: newData
                }]
            });
        });
    });
    //      TO GET THE LEGEND OUT OF CHART - TEMP
    <%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>--%>
//    <div id="legend"></div>
    // Retrieving number of reads - removing 1 for hidden unassigned
    <%--var readNum = "${fn:length(model.taxonomyAnalysisResult.taxonomyDataSet)-1}";--%>
    <%--console.log(readNum);--%>

    <%--// Creation of the legend items for each phylum--%>
    <%--for (var i = 0; i < readNum; i++)--%>

    <%--{      // Retrieving charts and charts series as JS vars--%>
        <%--var chart = $('#tax_chart_bar_phy').highcharts();--%>
        <%--var currentColor = chart.series[0].data[i].color;--%>
        <%--var currentName =  chart.series[0].data[i].name;--%>
        <%--$('<div/>', {--%>
            <%--'id': 'legend_' + i,--%>
            <%--'class': 'legend-item',--%>
            <%--'html': '<div class="legend-rectangle" style="background-color:'+ currentColor + ';"></div><span> '+currentName+'</span>',--%>
            <%--'click': function () {--%>
                <%--var legInd = this.id.split('_')[1]; // Gives the series index to hide / show when clicking on the legend item--%>
                <%--//legend off -> On--%>

                <%--if ($(".legend-item-off")[legInd]) {--%>
                    <%--$(this).children('.legend-rectangle').css('background-color', chart.series[0].data[legInd].color);//bring color--%>
                <%--}--%>
                 <%--else--%>
                <%--//legend on--%>
                 <%--{--%>
                     <%--$(this).children('.legend-rectangle').css('background-color', '#D1D1D1');//have to keep that to force the color as style is defined inline--%>
                 <%--}--%>
                <%--$(this).toggleClass('legend-item-off');--%>
            <%--}, // Adding legend item to legend div--%>
            <%--'mouseover': function () {--%>
                <%--var legInd = this.id.split('_')[1];--%>
                <%--chart.series[0].data[legInd].setState('hover');--%>
                <%--chart.tooltip.refresh(chart.series[0].data[legInd]);--%>
                <%--// }--%>
            <%--},--%>
            <%--'mouseleave': function () {--%>
                <%--var legInd = this.id.split('_')[1];--%>
                <%--chart.series[0].data[legInd].setState();--%>
                <%--// }--%>
            <%--},--%>
        <%--}).appendTo('#legend'); //where the color puce are--%>
   <%--}--%>
</script>

<script type="text/javascript">
     // script to make the tab download link work
    $('.open-tab').click(function (event) {
        $('#navtabs').tabs("option", "active", $(this).data("tab-index"));
    });
</script>
