<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
    $(function () {

        $(document).ready(function () {

            // InterPro match data
            var data = [
                <c:forEach var="entry" items="${model.functionalAnalysisResult.interProMatchesSection.interProEntryList}" varStatus="status">
                ['${entry.entryDescription}', ${entry.numOfEntryHits}, <fmt:formatNumber type="number" maxFractionDigits="2" value="${entry.numOfEntryHits*100 / model.functionalAnalysisResult.interProMatchesSection.totalReadsCount}" />],
                </c:forEach>
            ]

            //IMPORTANT - regroup small values under thresold into "Others" to improve pie chart readability
            var newData=[];
            //calculating the threshold: changing for each chart (OR use 20/100 fix treshold) + use round value to be the same as value rounded for slices
            var thresOld=((${model.functionalAnalysisResult.interProMatchesSection.sliceVisibilityThresholdValue}*100).toFixed(2));
            //console.log (thresOld)
            var other=0.0
            for (var slice in data) {
                // still need a condition to remove case when 10=11=...=thresOld where data color shoul be greyed and included in Other
                //thresold variable
                if (data[slice][2] < thresOld) {
                    other += data[slice][1];
                } else {
                    newData.push(data[slice]);
                }
            }

            //IMPORTANT remove "other" empty slice - should never happen for InterPro matches chart
            if (other == 0.0) {
                newData.push();}
            else {
                newData.push({name:'Other', y:other, color:'#ccc'});
            }

            //PIE CHART INTERPRO MATCHES
            $('#func_chart_pie_ipro').highcharts({
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
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.func.ip.pie.chart"/>',
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadJPEG',
                                    onclick: function () {
                                        this.exportChart({
                                            width: 1200,
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.func.ip.pie.chart"/>',
                                            type: 'image/jpeg'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadPDF',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.func.ip.pie.chart"/>',
                                            type: 'application/pdf'
                                        });
                                    }
                                },
                                {
                                    textKey: 'downloadSVG',
                                    onclick: function () {
                                        this.exportChart({
                                            filename:'${model.run.externalRunId}_<spring:message code="file.name.func.ip.pie.chart"/>',
                                            type: 'image/svg+xml'
                                        });
                                    }
                                },
                            ],


                        }
                    }
                },
                credits: {enabled: false},//remove credit line
                <%--colors: [<c:set var="addComma" value="false"/><c:forEach begin="0" end="10" varStatus="status"><c:choose><c:when test="${addComma}">, </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise></c:choose><c:choose><c:when test="${status.index>9}">'#ccc'</c:when><c:otherwise>'#<c:out value="${model.colorCodeList[status.index]}"/>'</c:otherwise></c:choose></c:forEach>],// color palette - just first 10 slices colored - work better for IPro matches summary - otherwise exception when % is the same between 10 and 11--%>
                colors: [ ${model.functionalAnalysisResult.colorCodeForChart} ],
                legend: {
                    enabled:false,
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'top',
                    //     itemWidth: 140 //align items in column same size for legend
                    x: 0,
                    y: 60
                },
                title: {
                    text: 'InterPro matches summary',
                    style: {
                        fontSize:16,
                        fontWeight: "bold"
                    }
                },
                subtitle: {
                    text: 'Total: ${fn:length(model.functionalAnalysisResult.interProMatchesSection.interProEntryList)} InterPro entries',
                    style: {
                        fontSize:11,
                    }},
                tooltip: {
                    backgroundColor: 'white',
                    headerFormat: '',
                    pointFormat: '<span style=\'color:{point.color}\'>&#9632;</span> {point.name}: <br/><strong>{point.y}</strong> pCDS matched ({point.percentage:.2f}%)',
                    useHTML: true
                },

                plotOptions: {
                    pie: {
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
                    name: 'Ipromatches',
                    borderColor: false,//remove border slices
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
            <c:forEach var="entry" items="${model.functionalAnalysisResult.interProMatchesSection.interProEntryList}" varStatus="status">
            ['${status.index}','<div title="${entry.entryDescription}" class="_cc puce-square-legend" style="background-color: <c:choose><c:when test="${status.index>9}">#b9b9b9</c:when><c:otherwise>#<c:out value="${model.functionalAnalysisResult.interProMatchesSection.colorCodeList[status.index]}"/></c:otherwise></c:choose>;"></div> <a title="${entry.entryDescription}" href="http://www.ebi.ac.uk/interpro/entry/${entry.entryID}" >${entry.entryDescription}</a>', '${entry.entryID}', ${entry.numOfEntryHits}, <fmt:formatNumber type="number" maxFractionDigits="2" value="${entry.numOfEntryHits*100 / model.functionalAnalysisResult.interProMatchesSection.totalReadsCount}" />],
            </c:forEach>
        ];

        var t = $('#func_table_ipro').DataTable( {
            order: [[ 3, "desc" ]],
            columnDefs: [ //add responsive style as direct css doesn't work
                {className: "xs_hide table-align-center col_xs", "targets": [0,2]},//number + id  centered + reduce width col
                {className: "table-align-right col_xs", "targets": [3,4]}//numbers easier to compare align on right + reduce width col
            ],
            //adding ID numbers for each row - used for interaction with chart
            createdRow: function (row, rowData, dataIndex) {
                $(row).addClass(""+dataIndex);
            },
            oLanguage: {
                "sSearch": "Filter table: "
            },
            lengthMenu: [[10, 50, 100, -1], [10, 50, 100, "All"]],
            data: rowData,
            columns: [
                {title: "" },
                {title: "Entry name"},//use all table space to display entry name
                {title: "ID" },
                {title: "pCDS matched"},
                {title: "%" },
            ]
        } );

        // insert number for lines as  first column and make it not sortable nor searchable
        t.on( 'order.dt search.dt', function () {
            t.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
                cell.innerHTML = i+1;
            } );
        } ).draw();

        //ADD INTERACTION BETWEEN TABLE ROW AND PIE CHART

        $("#func_table_ipro tbody").on('click', 'tr', function(){
            //important - use row Id for interaction otherwise table sorting was messing the use of $(this).index()
            var legInd = (this).className.split(' ')[0];
            console.log (legInd)
//            var index = $(this).index();
            var point = $('#func_chart_pie_ipro').highcharts().series[0].points[legInd];
            if (point) {
                point.setVisible(!point.visible);
            }
            else

            //show/hide whole "other" slice
            {
                var point = $('#func_chart_pie_ipro').highcharts().series[0].points[10];
                point.setVisible(!point.visible);
            }
            $(this).toggleClass("disabled");
            //          switch color doesn't work
            // if ($(".disabled")[index]) {
            //                $(this).find('div').css('background-color', 'black');//bring color
            //            }
        })

        $("#func_table_ipro tbody").on('mouseenter', 'tr', function(){
            var legInd = (this).className.split(' ')[0];
            var chart = $('#func_chart_pie_ipro').highcharts();
            var point = chart.series[0].points[legInd];
            if (point) {
                point.setState('hover');
                chart.tooltip.refresh(point);
            } else
            //highlight other
            {var point = $('#func_chart_pie_ipro').highcharts().series[0].points[10];
                point.setState('hover');
                chart.tooltip.refresh(point);}
        });

        $("#func_table_ipro tbody").on('mouseleave', 'tr', function(){
            var legInd = (this).className.split(' ')[0];
            var chart = $('#func_chart_pie_ipro').highcharts();
            var point = chart.series[0].points[legInd];
            if (point) {
                point.setState('');}
            else
            //unselect other slice
            {var point = $('#func_chart_pie_ipro').highcharts().series[0].points[10];
                point.setState('');}
        });


        //HIGHLIGHT TERMS IN DATATABLE
        $("#func_table_ipro_filter input").addClass("filter_sp");
        // Highlight the search term in the table (all except first number column) using the filter input, using jQuery Highlight plugin
        $('.filter_sp').keyup(function () {
            $("#func_table_ipro tr td:nth-child(n+2)").highlight($(this).val());
            $('#func_table_ipro tr td:nth-child(n+2)').unhighlight();// highlight more than just first character entered in the text box and reiterate the span to highlight
            $('#func_table_ipro tr td:nth-child(n+2)').highlight($(this).val());

        });
        // remove highlight when click on X (clear button)
        $('input[type=search]').on('search', function () {
            $('#func_table_ipro tr td').unhighlight();
        });
    } );

</script>
