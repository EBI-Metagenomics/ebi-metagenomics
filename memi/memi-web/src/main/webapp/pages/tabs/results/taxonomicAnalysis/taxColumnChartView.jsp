<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="tax-col">

    <div  class="chart_container" >
        <div class="grid_24"><div id="tax_chart_col" style="height: 360px;"></div></div>
        <div class="grid_24"> <table id="tax_table_col" class="table-glight"></table></div>
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
            <c:forEach var="taxonomyData" items="${model.taxonomyAnalysisResult.taxonomyDataSet}" varStatus="status">
            ['${status.index}','<div title="${taxonomyData.phylum}" class="puce-square-legend" style="background-color: #${taxonomyData.colorCode};"></div> ${taxonomyData.phylum}', '${taxonomyData.superKingdom}', ${taxonomyData.numberOfHits}, ${taxonomyData.percentage}],
            </c:forEach>
        ];

        var t = $('#tax_table_col').DataTable( {
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

        // insert number for lines as  first column and make it not sortable nor searchable
        t.on( 'order.dt search.dt', function () {
            t.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
                cell.innerHTML = i+1;
            } );
        } ).draw();

        // ADD INTERACTION BETWEEN TABLE ROW AND STACKED COLUMN CHART

        $("#tax_table_col tbody").on('click', 'tr', function(){
            //important - use row Id for interaction otherwise table sorting was messsing the use of $(this).index()
            var legInd = (this).className.split(' ')[0];
//            var index = $(this).index();
            var chart = $('#tax_chart_col').highcharts();
            if(chart.series[legInd].visible) {
                chart.series[legInd].hide();
            } else {
                chart.series[legInd].show();
            }
            $(this).toggleClass("disabled");
        });

        $("#tax_table_col tbody").on('mouseenter', 'tr', function(){
            var legInd = (this).className.split(' ')[0];
            var chart = $('#tax_chart_col').highcharts();
            var point = chart.series[legInd].points[0];
            point.setState('hover');
            chart.tooltip.refresh(point);
        });

        $("#tax_table_col tbody").on('mouseleave', 'tr', function(){
            var legInd = (this).className.split(' ')[0];
            var chart = $('#tax_chart_col').highcharts();
            var point = chart.series[legInd].points[0];
            point.setState('');
        });

        //HIGHLIGHT TERMS IN DATATABLE

        $("#tax_table_col_filter input").addClass("filter_sp");

        // Highlight the search term in the table (all except first number column) using the filter input, using jQuery Highlight plugin
        $('.filter_sp').keyup(function () {
            $("#tax_table_col tr td:nth-child(n+2)").highlight($(this).val());
            $('#tax_table_col tr td:nth-child(n+2)').unhighlight();// highlight more than just first character entered in the text box and reiterate the span to highlight
            $('#tax_table_col tr td:nth-child(n+2)').highlight($(this).val());
        });
        // remove highlight when click on X (clear button)
        $('input[type=search]').on('search', function () {
            $('#tax_table_col tr td').unhighlight();
        });
    } );

</script>

<script type="text/javascript">
    $(function () {

        $(document).ready(function () {

            //STACKED COLUMN CHART - PHYLUM COMPOSITION
            var data = [
                <c:forEach var="taxonomyData" items="${model.taxonomyAnalysisResult.taxonomyDataSet}" varStatus="status">
                { name: '${taxonomyData.phylum}',
                  data: [<fmt:formatNumber type="number" maxFractionDigits="3" value="${taxonomyData.numberOfHits *100/ model.taxonomyAnalysisResult.sliceVisibilityThresholdDenominator}" />],
                  testnb: '${taxonomyData.numberOfHits}'
                },
                </c:forEach>
            ]

            // Remove the unassigned from displaying on the chart
            //var iData = data.filter(function(item){ return item.name != "Unassigned" })

            // Get a value for unassigned reads/OTUs
            var UnData = []
            for(var i=0, l = data.length; i<l; i++) {
                if(data[i].name == "Unassigned") UnData.push(data[i]);
            }
            var totalUnassigned = UnData[0].testnb;

            //BAR CHART PHYLUM
            $('#tax_chart_col').highcharts({
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
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.col.chart.phylum"/>',
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadJPEG',
                                    onclick: function () {
                                        this.exportChart({
                                            width: 1200,
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.col.chart.phylum"/>',
                                            type: 'image/jpeg'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadPDF',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.col.chart.phylum"/>',
                                            type: 'application/pdf'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadSVG',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.col.chart.phylum"/>',
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
                    text: 'Total: ${model.taxonomyAnalysisResult.sliceVisibilityThresholdDenominator} OTUs',
                 </c:when>
                    <c:otherwise>
                    text: 'Total: ${model.taxonomyAnalysisResult.sliceVisibilityThresholdDenominator} reads',
                    </c:otherwise>
                    </c:choose>
                    style: {
                        fontSize:11,
                    }},
                tooltip: {
                    backgroundColor: 'white',
                    headerFormat: '',
                    // use formatter instead of pointFormat for customization tooltip
                    // <c:choose>
                    <c:when test="${model.run.releaseVersion == '1.0'}">
                    formatter:function(){
                        return '<span style="color:'+this.series.color+'">&#9632;</span> '+this.series.name+':<br/><strong>'+Highcharts.numberFormat((this.y/100*${model.taxonomyAnalysisResult.sliceVisibilityThresholdDenominator}),0,'.')+'</strong> OTUs ('+Highcharts.numberFormat((this.percentage),2,'.')+'%)';
                    },
                    </c:when>
                    <c:otherwise>
                    formatter:function(){
                        return '<span style="color:'+this.series.color+'">&#9632;</span> '+this.series.name+':<br/><strong>'+Highcharts.numberFormat((this.y/100*${model.taxonomyAnalysisResult.sliceVisibilityThresholdDenominator}),0,'.')+'</strong> reads ('+Highcharts.numberFormat((this.percentage),2,'.')+'%)';
                    },
                    </c:otherwise>
                    </c:choose>

                   // pointFormat: '<span style=\'color:{point.color}\'>&#9632;</span> <span style=\'font-size:88%;\'>{series.name}: </span><br/>{point.y}(<strong>{point.percentage:.2f}</strong>%)',//can't get the read number out of that

                    useHTML: true
                },

                xAxis: {
                    visible:false,//hide tick and label
                    },
                yAxis: {
                    reversedStacks: false,//reverse data order - high value to bottom
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
                        text: 'Relative abundance (%)',
                        </c:otherwise>
                        </c:choose>
                        enabled: true,
                        style:{
                            color: '#bbb'
                        }}
                },
                plotOptions: {
                    column: {
                        stacking: 'percent'
                    },
                    series: {
                        borderWidth:0,// remove the white border between slices or small values don't appear properly
                        borderColor: '#303030'
//                        dataLabels: {
//                            enabled: false,
//                            format: '{point.name}',
//                        }
                    }
                },
                series: data
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
