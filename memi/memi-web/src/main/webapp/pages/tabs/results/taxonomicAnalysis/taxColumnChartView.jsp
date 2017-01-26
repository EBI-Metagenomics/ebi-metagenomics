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
            <c:set var="addComma" value="false"/>
            <c:forEach var="taxonomyData" items="${model.taxonomyAnalysisResult.taxonomyDataSet}" varStatus="status"><c:choose><c:when test="${addComma}">,
            </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose>
            ['${row.index}','<div title="${taxonomyData.phylum}" class="puce-square-legend" style="background-color: #${taxonomyData.colorCode};"></div> ${taxonomyData.phylum}', '${taxonomyData.superKingdom}', ${taxonomyData.numberOfHits}, ${taxonomyData.percentage}]
            </c:forEach>
        ];

        var t = $('#tax_table_col').DataTable( {
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

        // ADD INTERACTION BETWEEN TABLE ROW AND STACKED COLUMN CHART
        $("#tax_table_col tr").click(function() {
            var no = $(this).index();
            var chart = $('#tax_chart_col').highcharts();
            if(chart.series[no].visible) {
                chart.series[no].hide();
            } else {
                chart.series[no].show();
            }
        });

        //HIGHLIGHT TERMS IN DATATABLE

        $("#tax_table_col_filter input").addClass("filter_sp");

        // Highlight the search term in the table using the filter input, using jQuery Highlight plugin
        $('.filter_sp').keyup(function () {
            $("#tax_table_col tr td").highlight($(this).val());
            $('#tax_table_col tr td').unhighlight();// highlight more than just first character entered in the text box and reiterate the span to highlight
            $('#tax_table_col tr td').highlight($(this).val());
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
           // var result = data.map(function(a) {return a.testnb;});
            //console.log(result);


            //BAR CHART PHYLUM
            $('#tax_chart_col').highcharts({
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
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.col.chart.phylum.png"/>',
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadJPEG',
                                    onclick: function () {
                                        this.exportChart({
                                            width: 1200,
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.col.chart.phylum.jpeg"/>',
                                            type: 'image/jpeg'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadPDF',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.col.chart.phylum.pdf"/>',
                                            type: 'application/pdf'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadSVG',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.tax.col.chart.phylum.svg"/>',
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
