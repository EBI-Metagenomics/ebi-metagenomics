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
            <c:set var="addComma" value="false"/>
            <c:forEach var="taxonomyData" items="${model.taxonomyAnalysisResult.taxonomyDataSet}" varStatus="status"><c:choose><c:when test="${addComma}">,
            </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>
            ['${row.index}','<div title="${taxonomyData.phylum}" class="puce-square-legend" style="background-color: #${taxonomyData.colorCode};"></div> ${taxonomyData.phylum}', '${taxonomyData.superKingdom}', ${taxonomyData.numberOfHits}, ${taxonomyData.percentage}]
            </c:forEach>
        ];

        var t = $('#tax_table_bar').DataTable( {
            order: [[ 3, "desc" ]],
            columnDefs: [ //add responsive style as direct css doesn't work
                {className: "xs_hide", "targets": [2]},//domain
                {className: "table-align-right", "targets": [3,4]}//numbers easier to compare
            ],
            oLanguage: {
                "sSearch": "Filter table: "
            },
            lengthMenu: [[25, 50, 100, -1], [25, 50, 100, "All"]],
            data: rowData,
            columns: [
                { title: "" },
                { title: "Phylum" },
                { title: "Domain" },
                <c:choose>
                <c:when test="${model.run.releaseVersion == '1.0'}">
                { title: "Unique OTUs" },
                </c:when>
                    <c:otherwise>{title: "Reads"},
                </c:otherwise>
                </c:choose>
                { title: "%" },
            ]
        } );
        // insert number for lines as  first column and make it not sortable nor searchable
        t.on( 'order.dt search.dt', function () {
            t.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
                cell.innerHTML = i+1;
            } );
        } ).draw();

        // ADD INTERACTION BETWEEN TABLE ROW AND BAR CHART
        var hideToggle = true;
        $("#tax_table_bar tbody tr").click(function() {
            var no = $(this).index();
//            console.log(no)
            var chart = $('#tax_chart_bar_phy').highcharts()
            var point = chart.series[0].data[no];
            if (hideToggle) {
                point.graphic.hide();
               // $('#button').html('Show data');
            } else {
                point.graphic.show();
            }
            hideToggle = !hideToggle;
        });


//        $("#tax_table_bar tbody tr").click(function(){
//            var no = $(this).index();
//            var point = $('#tax_chart_bar_phy').highcharts().series[0].data[no];
//            point.setVisible(!point.visible);
//        })
        //HIGHLIGHT TERMS IN DATATABLE

        $("#tax_table_bar_filter input").addClass("filter_sp");

        // Highlight the search term in the table using the filter input, using jQuery Highlight plugin
        $('.filter_sp').keyup(function () {
            $("#tax_table_bar tr td").highlight($(this).val());
            // console.log($(this).val());
            $('#tax_table_bar tr td').unhighlight();// highlight more than just first character entered in the text box and reiterate the span to highlight
            $('#tax_table_bar tr td').highlight($(this).val());
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

            //BAR CHART - DOMAIN COMPOSITION
            $('#tax_chart_bar_dom').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'column',
                    style: {
                        fontFamily: 'Helvetica'
                    }
                },
                navigation: {
                    buttonOptions: {
                        height: 40,
                        width: 48,
                        symbolSize: 24,
                        symbolX: 23,
                        symbolY: 21,
                        y: -10
                    }
                },
                exporting: {
                    buttons: {
                        contextButton: {
                            symbol: 'url(${pageContext.request.contextPath}/img/ico_download.png)',//TEMP download img icon
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
                    data:   [
                        <c:set var="addComma" value="false"/>
                        <c:forEach var="domainEntry" items="${model.taxonomyAnalysisResult.domainComposition.domainMap}"><c:choose><c:when test="${addComma}">,</c:when>
                        <c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>
                        ['${domainEntry.key}', ${domainEntry.value}]
                        </c:forEach>
                    ]

                }]
            });

            //PIE CHART - PHYLUM COMPOSITION

            // Phylum data
            var data = [
                <c:set var="addComma" value="false"/>
                <c:forEach var="taxonomyData" items="${model.taxonomyAnalysisResult.taxonomyDataSet}" varStatus="status"><c:choose><c:when test="${addComma}">,</c:when>
                <c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>
                ['${taxonomyData.phylum}', ${taxonomyData.numberOfHits}, ${taxonomyData.percentage}]
                </c:forEach>
            ]
            //IMPORTANT - regroup small values <0.1 into "Others" to improve bar chart readability
            var newData=[];
            var other=0.0;
            for (var slice in data) {
            //thresold 0.1
                if (data[slice][2] < 10/100) {
                    other += data[slice][1];
                } else {
                    newData.push(data[slice]);
                }
            }

            //IMPORTANT remove "other" empty bar
            if (other == 0.0) {
                newData.push();}
            else {
                newData.push({name:'Other', y:other, color:'#ccc'});
            }

            //BAR CHART PHYLUM
            $('#tax_chart_bar_phy').highcharts({
                chart: {
                    zoomType: 'x',// add zoom options -useful when very small values
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'column',
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
                            symbol: 'url(${pageContext.request.contextPath}/img/ico_download.png)',//temp download img icon
                        }
                    }
                },
                credits: {text: null },//remove credit line bottom
                colors: [${model.taxonomyAnalysisResult.colorCodeForPieChart} ],//color palette
                legend: {enabled: false},//remove legend
                title: {
                    <c:choose>
                    <c:when test="${model.run.releaseVersion == '1.0'}">
                    text: '${phylumCompositionTitle} <span style="font-size:80%;">(Total: ${model.taxonomyAnalysisResult.sliceVisibilityThresholdDenominator} OTUs)</span>',
                    </c:when>
                    <c:otherwise>
                    text: '${phylumCompositionTitle} <span style="font-size:80%;">(Total: ${model.taxonomyAnalysisResult.sliceVisibilityThresholdDenominator} reads)</span>',
                    </c:otherwise>
                    </c:choose>
                    style: {
                        fontSize:16,
                        fontWeight: "bold"
                    }
                },
                subtitle: {
                    text: 'Drag to zoom in/out',
                    style: {
                        fontSize:11,
                        fontWeight: "italic"
                }},
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
//                  gridLineColor: '#e0e0e0', // now default
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
                        }
                    }
                },
                series: [{
                    name: 'Phylumn',
                    colorByPoint: true,
//                    borderWidth: 1,
//                    borderColor: 'rgba(0,0,0,0.3)',
                    pointPadding: 0,
                    groupPadding: 0.1,
                    data: newData
                }]
            });
        });
    });
</script>

<script type="text/javascript">
     // script to make the tab download link work
    $('.open-tab').click(function (event) {
        $('#navtabs').tabs("option", "active", $(this).data("tab-index"));
    });
</script>
