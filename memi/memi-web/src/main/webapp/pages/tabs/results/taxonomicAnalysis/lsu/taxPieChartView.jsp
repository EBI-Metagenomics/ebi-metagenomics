<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div id="tax-pie">

    <div  class="chart_container" >
        <div class="grid_8"><div id="tax_chart_pie_domain"></div></div>
        <div class="grid_16">  <div id="tax_lsu_chart_pie_phylum"></div></div>
        <div class="grid_24"> <table id="tax_table_lsu" class="table-glight"></table></div>
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
    $(function () {

        $(document).ready(function () {

            //PIE CHART DOMAIN
            // Domain data
            var data =  [
                <%--<c:set var="addComma" value="false"/>--%>
                <c:forEach var="domainEntry" items="${model.taxonomyAnalysisResultLSU.domainComposition.domainMap}">
                <%--<c:choose><c:when test="${addComma}">,</c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>--%>
                ['${domainEntry.key}', ${domainEntry.value}],
                </c:forEach>
            ]

            // Remove the unassigned from displaying on the chart
            //var iData = data.filter(function(item){ return item[0] != "Unassigned" })

            // Get a value for unassigned reads/OTUs
            var totalUnassigned=[];
            for (var slice in data) {
                if (data[slice][0] == "Unassigned") {
                    totalUnassigned += data[slice][1];
                }
            }

            $('#tax_chart_pie_domain').highcharts({
                chart: {
                    type: 'pie',
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
                            symbol: 'url(${pageContext.request.contextPath}/img/ico_download_custom.svg)',// img icon export
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
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.pie.chart.domain"/>',
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadJPEG',
                                    onclick: function () {
                                        this.exportChart({
                                            width: 1200,
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.pie.chart.domain"/>',
                                            type: 'image/jpeg'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadPDF',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.pie.chart.domain"/>',
                                            type: 'application/pdf'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadSVG',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.pie.chart.domain"/>',
                                            type: 'image/svg+xml'
                                        });
                                    }
                                },
                            ],

                        }
                    }
                },
                credits: {enabled: false},//remove credit line
                colors: [${model.taxonomyAnalysisResultLSU.domainComposition.colorCode}],//color palette
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
                    pointFormat: '<span style=\'color:{point.color}\'>&#9632;</span> <span style=\'font-size:88%;\'>{point.name}: </span><br/><strong><small>{point.y}</small></strong> OTUs (<strong>{point.percentage:.2f}</strong>%)',
                    </c:when>
                    <c:otherwise>
                    pointFormat: '<span style=\'color:{point.color}\'>&#9632;</span> <span style=\'font-size:88%;\'>{point.name}: </span><br/><strong><small>{point.y}</small></strong> reads (<strong>{point.percentage:.2f}</strong>%)',
                    </c:otherwise>
                    </c:choose>
                    useHTML: true
                },

                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
//                        borderColor: 'rgba(0, 0, 0, 0.1)',
                        dataLabels: {
//                            distance:-30, //inside labels as it used to be in Google chart
                            enabled: true
                        },
                        showInLegend: false
                    },
                    series: {
                        dataLabels: {
                            enabled: true,
                        //    format: '{point.name}: {point.percentage:.1f}%'
                        }
                    }
                },

                series: [{
                    name: 'Domain composition',
                    //colorByPoint: true,
                    borderColor: false,// to hide white default border on slice
                    data:  data
                }]
            });

            //PIE CHART - PHYLUM COMPOSITION

            // Phylum data
            var data = [
                <%--<c:set var="addComma" value="false"/>--%>
                <c:forEach var="taxonomyData" items="${model.taxonomyAnalysisResultLSU.taxonomyDataSet}" varStatus="status">
                <%--<c:choose><c:when test="${addComma}">,</c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>--%>
                ['${taxonomyData.phylum}', ${taxonomyData.numberOfHits}, ${taxonomyData.percentage}],
                </c:forEach>
            ]

            // Remove the unassigned from displaying on the chart
            //var iData = data.filter(function(item){ return item[0] != "Unassigned" })

            //IMPORTANT - regroup small values under thresold into "Others" to improve pie chart readability
            var newData=[];
            //calculating the threshold: changing for each chart (OR use 20/100 fix treshold) + use round value to be the same as value rounded for slices
            var thresOld=((${model.taxonomyAnalysisResultLSU.sliceVisibilityThresholdNumerator / model.taxonomyAnalysisResultLSU.sliceVisibilityThresholdDenominator}*100).toFixed(2));
            //console.log (thresOld)
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

            //PIE CHART PHYLUM
            $('#tax_lsu_chart_pie_phylum').highcharts({
                chart: {
                    type: 'pie',
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
                            symbol: 'url(${pageContext.request.contextPath}/img/ico_download_custom.svg)',// img icon export
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
                                        filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.pie.chart.phylum"/>',
                                    });
                                }
                            },
                                {
                                    textKey: 'downloadJPEG',
                                    onclick: function () {
                                        this.exportChart({
                                            width: 1200,
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.pie.chart.phylum"/>',
                                            type: 'image/jpeg'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadPDF',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.pie.chart.phylum"/>',
                                            type: 'application/pdf'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadSVG',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.pie.chart.phylum"/>',
                                            type: 'image/svg+xml'
                                        });
                                    }
                                },
                            ],

                        }
                    }
                },
                credits: {text: null },//remove credit line bottom
                colors: [ ${model.taxonomyAnalysisResultLSU.colorCodeForChart} ],//color palette
                legend: {
                    title: {
                        text: '<span style="font-size: 12px; color: #666; font-weight: normal">Click to hide</span>',
                        style: {
                            fontStyle: 'italic'
                        }
                    },
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'top',
                    x: 0,
                    y: 60,
                    itemStyle: {fontWeight: "regular"},
                    onclick: function () {
                        this.exportChart({
                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.pie.chart.phylum"/>',
                            type: 'image/svg+xml'
                        });
                    }
                },
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
                    text: 'Total: ${model.taxonomyAnalysisResultLSU.sliceVisibilityThresholdDenominator} OTUs',
                    </c:when>
                    <c:otherwise>
                    text: 'Total: ${model.taxonomyAnalysisResultLSU.sliceVisibilityThresholdDenominator} reads',
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
                    pointFormat: '<span style=\'color:{point.color}\'>&#9632;</span> {point.name}:<br/><strong>{point.y}</strong> OTUs ({point.percentage:.2f}%)',
                    </c:when>
                    <c:otherwise>
                    pointFormat: '<span style=\'color:{point.color}\'>&#9632;</span> {point.name}:<br/><strong>{point.y}</strong> reads ({point.percentage:.2f}%)',
                    </c:otherwise>
                    </c:choose>
                    useHTML: true
                },

                plotOptions: {
                    pie: {
//                        borderColor: 'rgba(0, 0, 0, 0.18)',
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
//                            distance:-30, //inside labels as it used to be in Google chart
                            enabled: true,
                        },
                        point:{
                            events: {
                                legendItemClick: function () {
                                    //interaction between chart legend -> table
                                    var visibility = this.visible ? 'visible' : 'hidden';
                                    var index = this.index +1;
                                    var l = $('#tax_lsu_chart_pie_phylum').highcharts().series[0].points.length;
                                    if (index<l) {$("#tax_table_lsu tbody tr."+this.index+"").toggleClass("disabled");}//"invisible" effect as datatable is replaced
                                    if (index==l) {
                                        //hide all together other rows
                                        var n=l;
                                        var readNum = data.length;//total number of rows
                                        for (n = l; n <= readNum ; n++) {
                                          $("#tax_table_lsu tbody tr."+n+"").toggleClass("disabled");
                                        }
                                    }
//                                    if (!confirm('This '+ index +' series on '+l+' is currently ' +
//                                                    visibility + '. Do you want to change that?')) {
//                                        return false;
//                                    }

                                }
                            }//end events
                        },//end point
                        showInLegend: true
                    },
                    series: {
                        dataLabels: {
                            enabled: true,//bit messay if labels are shown next to slices
                                format: '{point.name}: {point.percentage:.2f}%',
//                            format: '{point.percentage:.1f}%',//inside labels
                            style: {
                                textShadow: false,
//                                color:'white' //inside labels as it used to be in Google chart
                            }
                        },
                    }
                    },
                series: [{
                    name: 'Phylumn',
                    //colorByPoint: true,
                    borderColor: false,// to hide white default border on slice
                    data: newData
                }]
            });

        });
    });
</script>
<script type="text/javascript">

    // Create the Datatable
    $(document).ready(function() {

        //table data
        var rowData = [
            <c:forEach var="taxonomyData" items="${model.taxonomyAnalysisResultLSU.taxonomyDataSet}" varStatus="status">
            <%--//remove unassigned data
            <c:choose>
            <c:when test="${taxonomyData.phylum=='Unassigned'}">
                //leave empty
            </c:when>
            <c:otherwise>--%>
            <%--['${row.index}','<div title="${taxonomyData.phylum}" class="puce-square-legend" style="background-color: #${taxonomyData.colorCode}; "></div> ${taxonomyData.phylum}', '${taxonomyData.superKingdom}', ${taxonomyData.numberOfHits}, ${taxonomyData.percentage},'${taxonomyData.phylum}'],--%>
            ['${status.index}','<div title="${taxonomyData.phylum}" class="puce-square-legend" style="background-color: #${taxonomyData.colorCode}; "></div> ${taxonomyData.phylum}', '${taxonomyData.superKingdom}', ${taxonomyData.numberOfHits}, ${taxonomyData.percentage}],
                <%-- </c:otherwise>
                 </c:choose>--%>
                 </c:forEach>
                     ];
             // TEMP - OTHER POSSIBILITY TO REMOVE UNASSIGNED
             //        // Remove the unassigned from displaying on the chart
             //        var irowData = []
             //        // Remove the unassigned from displaying on the chart
             //        var irowData = rowData.filter(function(item){ return item[5] != "Unassigned" })

             var t = $('#tax_table_lsu').DataTable( {
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
              //insert a "fixed" number for lines as first column and make it not sortable nor searchable
             t.on( 'order.dt search.dt', function () {
                 t.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
                     cell.innerHTML = i+1;
                 } );
             } ).draw();


             // ADD INTERACTION BETWEEN TABLE ROW AND PIE CHART
             // Note - some interaction improvement to do across pages (e.g. row select Other not maintained)
             $("#tax_table_lsu tbody").on('click', 'tr', function(){
                 //important - use row Id for interaction otherwise table sorting was messsing the use of $(this).index()
                 var legInd = (this).className.split(' ')[0];
     //            console.log(legInd)
     //          var index = $(this).index();
                 var point = $('#tax_lsu_chart_pie_phylum').highcharts().series[0].points[legInd];
     //            console.log(point)

                 if (point && point.name!="Other") {
                         point.setVisible(!point.visible);
                         $(this).toggleClass("disabled");
                 }
                 else
                 // point undefined - show/hide whole "other" slice
                 { var l = $('#tax_lsu_chart_pie_phylum').highcharts().series[0].points.length;
                    var point = $('#tax_lsu_chart_pie_phylum').highcharts().series[0].points [l - 1];
                   point.setVisible(!point.visible);

                     var readNum = ${fn:length(model.taxonomyAnalysisResultLSU.taxonomyDataSet)};//total number of records
//                     console.log(readNum)
                     for (n = l-1; n <= readNum ; n++) {
                         $("#tax_table_lsu tbody tr."+n+"").toggleClass("disabled");
                     }
                  }

             });

             $("#tax_table_lsu tbody").on('mouseenter', 'tr', function(){
                 var legInd = (this).className.split(' ')[0];
                 var chart = $('#tax_lsu_chart_pie_phylum').highcharts();
                 var point = chart.series[0].points[legInd];
                 if (point) {
                 point.setState('hover');
                 chart.tooltip.refresh(point);
                 } else
                  //highlight other
                 { var l = $('#tax_lsu_chart_pie_phylum').highcharts().series[0].points.length;
                   var point = $('#tax_lsu_chart_pie_phylum').highcharts().series[0].points [l - 1];
                  point.setState('hover');
                  chart.tooltip.refresh(point);}
             });

             $("#tax_table_lsu tbody").on('mouseleave', 'tr', function(){
                 var legInd = (this).className.split(' ')[0];
                 var chart = $('#tax_lsu_chart_pie_phylum').highcharts();
                 var point = chart.series[0].points[legInd];

                 if (point) {
                 point.setState('');}
                 else
                 //unselect other slice
                 {var l = $('#tax_lsu_chart_pie_phylum').highcharts().series[0].points.length;
                     var point = $('#tax_lsu_chart_pie_phylum').highcharts().series[0].points [l - 1];
                     point.setState('');}
             });

             //HIGHLIGHT TERMS IN DATATABLE
             $("#tax_table_lsu_filter input").addClass("filter_sp");
             // Highlight the search term in the table (all except first number column) using the filter input, using jQuery Highlight plugin
             $('.filter_sp').keyup(function () {
                 $("#tax_table_lsu tr td:nth-child(n+2)").highlight($(this).val());
                 $('#tax_table_lsu tr td:nth-child(n+2)').unhighlight();// highlight more than just first character entered in the text box and reiterate the span to highlight
                 $('#tax_table_lsu tr td:nth-child(n+2)').highlight($(this).val());
             });
             // remove highlight when click on X (clear button)
             $('input[type=search]').on('search', function () {
                 $('#tax_table_lsu tr td').unhighlight();
             });
         } );

     </script>
     <script type="text/javascript">
         // script to make the tab download link work
         $('.open-tab').click(function (event) {
             $('#navtabs').tabs("option", "active", $(this).data("tab-index"));
         });
     </script>

