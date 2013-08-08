<%-- This script produces the charts (pie, bar and stack chart) and the table for the taxonomic analysis tab--%>
<script type="text/javascript">

    //  Load the Visualization API and the chart package.
    //  Line moved to rootTemplate.jsp

    //  Set a callback to run when the Google Visualization API is loaded.
    google.setOnLoadCallback(drawPhylumTable);
    google.setOnLoadCallback(drawPhylumPieChart);
    google.setOnLoadCallback(drawPhylumBarChart);
    google.setOnLoadCallback(drawPhylumStackChart);
    google.setOnLoadCallback(drawDomainCompositionPieChart);
    google.setOnLoadCallback(drawDomainCompositionBarChart);

    function drawPhylumTable() {
        // Taxonomy top phylum table
        var taxMatchesData = new google.visualization.DataTable();
        taxMatchesData.addColumn('string', 'Phylum');
        taxMatchesData.addColumn('string', 'Domain');
        taxMatchesData.addColumn('number', 'Unique OTUs');
        taxMatchesData.addColumn('number', '%');
//        taxMatchesData.addColumn('number', 'Count of reads assigned');
//        taxMatchesData.addColumn('number', '% reads assigned');
        taxMatchesData.addRows([
            <c:set var="addComma" value="false"/>
            <c:forEach var="taxonomyData" items="${model.taxonomyAnalysisResult.taxonomyDataSet}" varStatus="status">
            <c:choose>
            <c:when test="${addComma}">,
            </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise>
            </c:choose>
            ['<div title="${taxonomyData.phylum} color code" class="chart_color_leg" style="background-color: #${taxonomyData.colorCode}; margin: 4px 6px 0 2px;"></div> ${taxonomyData.phylum}', '${taxonomyData.superKingdom}', ${taxonomyData.numberOfHits}, ${taxonomyData.percentage}
//                , 0 , 0
]
            </c:forEach>
        ]);

          // Taxonomy top phylum table
        var taxMatchesDatabar = new google.visualization.DataTable();
        taxMatchesDatabar.addColumn('string', 'Phylum');
        taxMatchesDatabar.addColumn('string', 'Domain');
        taxMatchesDatabar.addColumn('number', 'Unique OTUs');
        taxMatchesDatabar.addColumn('number', '%');
//        taxMatchesData.addColumn('number', 'Count of reads assigned');
//        taxMatchesData.addColumn('number', '% reads assigned');
        taxMatchesDatabar.addRows([
            <c:set var="addComma" value="false"/>
            <c:forEach var="taxonomyData" items="${model.taxonomyAnalysisResult.taxonomyDataSet}" varStatus="status">
            <c:choose>
            <c:when test="${addComma}">,
            </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise>
            </c:choose>
            ['${taxonomyData.phylum}', '${taxonomyData.superKingdom}', ${taxonomyData.numberOfHits}, ${taxonomyData.percentage}
//                , 0 , 0
]
            </c:forEach>
        ]);

        // Define a StringFilter control for the 'Phylum' column
        var taxStringFilter = new google.visualization.ControlWrapper({
            'controlType':'StringFilter',
            'containerId':'tax_table_filter',
            'options':{ 'matchType':'any',
                'filterColumnIndex':'1, 2',
                'ui':{'label':'Filter', 'labelSeparator':':', 'ui.labelStacking':'vertical', 'ui.cssClass':'custom_col_search'}
            }
        });

        // Table visualization option
        var taxTableOptions = new google.visualization.ChartWrapper({
            'chartType':'Table',
            'containerId':'tax_table_pie',
            'options':{ allowHtml:true, showRowNumber:true, page:'enable', pageSize:10, pagingSymbols:{prev:'prev', next:'next'}, sortColumn:2, sortAscending:false }
        });

        // Draw the phylum table on the pie chart tab
        new google.visualization.Dashboard(document.getElementById('tax_dashboard')).
            // Configure the string filter to affect the table contents
                bind(taxStringFilter, taxTableOptions).
            // Draw the dashboard
                draw(taxMatchesData);

        // Draw the phylum table on the stack chart tab
        new google.visualization.Table(document.getElementById('tax_table_col')).draw(taxMatchesData, { allowHtml:true, showRowNumber:true, page:'enable', pageSize:10, pagingSymbols:{prev:'prev', next:'next'}, sortColumn:2, sortAscending:false});

        // Draw the phylum table on the bar chart tab
        new google.visualization.Table(document.getElementById('tax_table_bar')).draw(taxMatchesDatabar, { allowHtml:true, showRowNumber:true, page:'enable', pageSize:10, pagingSymbols:{prev:'prev', next:'next'}, sortColumn:2, sortAscending:false});
    }  //END function drawPhylumTable()

    // DATA taxonomy Pie+Bar chart Phylum
    var phylumBarChartPieChartData = new google.visualization.DataTable();
    phylumBarChartPieChartData.addColumn('string', 'Phylum');
    phylumBarChartPieChartData.addColumn('number', 'Match');
    phylumBarChartPieChartData.addRows([
        <c:set var="addComma" value="false"/>
        <c:forEach var="taxonomyData" items="${model.taxonomyAnalysisResult.taxonomyDataSet}" varStatus="status">
        <c:choose>
        <c:when test="${addComma}">,
        </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise>
        </c:choose>
        ['${taxonomyData.phylum}', ${taxonomyData.numberOfHits}]
        </c:forEach>
    ]);

    function drawPhylumPieChart() {

        // taxonomy Pie chart Phylum
        var options = {'title':'Phylum composition (Total: ${model.taxonomyAnalysisResult.uniqueUTUsTotalCount})',
            'titleTextStyle':{fontSize:12},
            'colors':[${model.taxonomyAnalysisResult.colorCodeForPieChart}],
//Krona style      'colors':['#d47f7f','#d1a575','#d4c97f','#99d47f','#7fd4a7','#7fc3d4','#7f8ad4','#a77fd4','#d47fd3','#d47faf','#ccc','#ccc','#ccc'],
//      'width':220,
            'width':290,
            'height':220,
            'legend':{position:'right', fontSize:10},
            'chartArea':{left:10, top:30, width:"100%", height:"100%"},
            'pieSliceBorderColor':'none'
        };

        var phylumPieChart = new google.visualization.PieChart(document.getElementById('tax_chart_pie_phy'));
        phylumPieChart.draw(phylumBarChartPieChartData, options);
    }

    function drawPhylumBarChart() {
        // Taxonomy Bar - phylum
        var options = {'title':'Phylum composition (Total: ${model.taxonomyAnalysisResult.uniqueUTUsTotalCount})', 'titleTextStyle':{fontSize:12}, 'colors':['#5f8694'], 'width':360, 'height':320, 'chartArea':{left:120, top:40, width:"60%", height:"70%"}, 'pieSliceBorderColor':'none', 'legend':'none' };
        var phylumBarChart = new google.visualization.BarChart(document.getElementById('tax_chart_bar_phy'));
        phylumBarChart.draw(phylumBarChartPieChartData, options);
    }

    function drawPhylumStackChart() {
        // DATA taxonomy Stacked column
        var data = google.visualization.arrayToDataTable([
            [''<c:forEach var="taxonomyData" items="${model.taxonomyAnalysisResult.taxonomyDataSet}" varStatus="status">
                , '${taxonomyData.phylum}'
                </c:forEach>],
            [''<c:forEach var="taxonomyData" items="${model.taxonomyAnalysisResult.taxonomyDataSet}" varStatus="status">
                , ${taxonomyData.numberOfHits / model.taxonomyAnalysisResult.uniqueUTUsTotalCount}
                </c:forEach>]
        ]);

        // Stacked column graph
        var options = {'title':'Phylum composition (Total: ${model.taxonomyAnalysisResult.uniqueUTUsTotalCount})',
            'titleTextStyle':{fontSize:12},
            'colors':[${model.taxonomyAnalysisResult.colorCodeForStackChart}],
//            'colors':['#058dc7', '#50b432', '#ed561b', '#edef00', '#24cbe5', '#64e572', '#ff9655', '#fff263', '#6af9c4', '#b2deff', '#ccc', '#ccc', '#ccc', '#ccc', '#ccc', '#ccc', '#ccc'],
            'width':320,
            'height':400,
            'legend':{position:'right', fontSize:10},
            'chartArea':{left:80, top:40, width:"20%", height:"86%"},
            'pieSliceBorderColor':'none',
            'sliceVisibilityThreshold':1 / 50,
            'vAxis':{ viewWindowMode:'maximized'}, //        important to keep viewWindowMode separated from the rest to keep the display of the value 100% on vaxis
            'vAxis':{title:'Relative abundance', format:'#%', baselineColor:'#ccc'},
            'isStacked':true
        };

        var phylumStackChart = new google.visualization.ColumnChart(document.getElementById('tax_chart_col'));
        phylumStackChart.draw(data, options);
    }

    // DATA taxonomy Pie+Bar chart Domain
    var domainBarChartPieChartData = new google.visualization.DataTable();
    domainBarChartPieChartData.addColumn('string', 'kingdom');
    domainBarChartPieChartData.addColumn('number', 'Match');
    domainBarChartPieChartData.addRows([
        <c:set var="addComma" value="false"/>
        <c:forEach var="domainEntry" items="${model.taxonomyAnalysisResult.domainComposition.domainMap}">
        <c:choose>
        <c:when test="${addComma}">,
        </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise>
        </c:choose>
        ['${domainEntry.key}', ${domainEntry.value}]
        </c:forEach>
    ]);

    function drawDomainCompositionPieChart() {
        // taxonomy Pie chart domain
        var options = {'title':'Domain composition', 'titleTextStyle':{fontSize:12}, 'colors':[${model.taxonomyAnalysisResult.domainComposition.colorCode}], 'width':200, 'height':220, 'chartArea':{left:10, top:26, width:"80%", height:"55%"}, 'pieSliceBorderColor':'none', 'legend':{fontSize:10}, 'pieSliceTextStyle':{color:'white'}};

        var domainPieChart = new google.visualization.PieChart(document.getElementById('tax_chart_pie_dom'));
        domainPieChart.draw(domainBarChartPieChartData, options);
    }

    function drawDomainCompositionBarChart() {
        // Taxonomy Bar - domain
        var options = {'title':'Domain composition', 'titleTextStyle':{fontSize:12}, 'colors':['#5f8694'], 'width':190, 'height':180, 'chartArea':{left:70, top:40, width:"56%", height:"70%"}, 'vAxis':{textStyle:{fontSize:11}}, 'pieSliceBorderColor':'none', 'bar':{groupWidth:10}, 'legend':'none'};

        var domainBarChart = new google.visualization.BarChart(document.getElementById('tax_chart_bar_dom'));
        domainBarChart.draw(domainBarChartPieChartData, options);
    }
</script>