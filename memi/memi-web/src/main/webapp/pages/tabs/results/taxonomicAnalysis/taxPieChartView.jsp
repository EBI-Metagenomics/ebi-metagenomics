<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="tax-pie">

    <div  class="chart_container" >
        <div class="grid_8"><div id="tax_chart_pie_domain"></div></div>
        <div class="grid_16">  <div id="tax_chart_pie_phylum"></div></div>
        <div class="grid_24"> <table id="tax_table" class="table-glight"></table></div>
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

        var t = $('#tax_table').DataTable( {
            order: [[ 3, "desc" ]],
            columnDefs: [ //add responsive style as direct css doesn't work
                {className: "xs_hide", "targets": [0,2]},//hide number + domain columns
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

        // ADD INTERACTION BETWEEN TABLE ROW AND CHART
        $("#tax_table tbody tr").click(function(){
            var index = $(this).index();
            var point = $('#tax_chart_pie_phylum').highcharts().series[0].points[index];
            point.setVisible(!point.visible);
      })
        //HIGHLIGHT TERMS IN DATATABLE
        $("#tax_table_filter input").addClass("filter_sp");
        // Highlight the search term in the table using the filter input, using jQuery Highlight plugin
        $('.filter_sp').keyup(function () {
            $("#tax_table tr td").highlight($(this).val());
            $('#tax_table tr td').unhighlight();// highlight more than just first character entered in the text box and reiterate the span to highlight
            $('#tax_table tr td').highlight($(this).val());

        });
        // remove highlight when click on X (clear button)
        $('input[type=search]').on('search', function () {
            $('#tax_table tr td').unhighlight();
        });
    } );

</script>

<script type="text/javascript">
    $(function () {

        $(document).ready(function () {

            //PIE CHART DOMAIN
            $('#tax_chart_pie_domain').highcharts({
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
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.pie.chart.domain.png"/>',
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadJPEG',
                                    onclick: function () {
                                        this.exportChart({
                                            width: 1200,
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.pie.chart.domain.jpeg"/>',
                                            type: 'image/jpeg'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadPDF',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.pie.chart.domain.pdf"/>',
                                            type: 'application/pdf'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadSVG',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.pie.chart.domain.svg"/>',
                                            type: 'image/svg+xml'
                                        });
                                    }
                                },
                            ],


                        }
                    }
                },
                credits: {enabled: false},//remove credit line
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
                    name: 'Phylumn',
                    //colorByPoint: true,
                    borderColor: false,
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

            //IMPORTANT - regroup small values under thresold into "Others" to improve pie chart readability
            var newData=[];
            //calculating the threshold: changing for each chart (OR use 20/100 fix treshold) + use round value to be the same as value rounded for slices
            var thresOld=((${model.taxonomyAnalysisResult.sliceVisibilityThresholdNumerator / model.taxonomyAnalysisResult.sliceVisibilityThresholdDenominator}*100).toFixed(2));

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
            $('#tax_chart_pie_phylum').highcharts({
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
                                        filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.pie.chart.phylum.png"/>',
                                    });
                                }
                            },
                                {
                                    textKey: 'downloadJPEG',
                                    onclick: function () {
                                        this.exportChart({
                                            width: 1200,
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.pie.chart.phylum.jpeg"/>',
                                            type: 'image/jpeg'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadPDF',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.pie.chart.phylum.pdf"/>',
                                            type: 'application/pdf'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadSVG',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.pie.chart.phylum.svg"/>',
                                            type: 'image/svg+xml'
                                        });
                                    }
                                },
                            ],

                        }
                    }
                },
                credits: {text: null },//remove credit line bottom
                colors: [ ${model.taxonomyAnalysisResult.colorCodeForChart} ],//color palette
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
                    //     itemWidth: 140 //align items in column same size for legend
                    x: 0,
                    y: 60,
                    itemStyle: {fontWeight: "regular"}
                },
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
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
//                            distance:-30, //inside labels as it used to be in Google chart
                            enabled: true,
                        },
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

                        }
                    }
                },
                series: [{
                    name: 'Phylumn',
                    //colorByPoint: true,
                    borderColor: false,
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

