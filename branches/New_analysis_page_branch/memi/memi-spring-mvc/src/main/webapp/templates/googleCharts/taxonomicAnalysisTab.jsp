<%-- This script produces the charts (pie, bar and stack chart) and the table for the taxonomic analysis tab--%>
<script type="text/javascript">

    //  Load the Visualization API and the chart package.
    //  Line moved to rootTemplate.jsp

    //  Set a callback to run when the Google Visualization API is loaded.
    google.setOnLoadCallback(drawPhylumTable);
    google.setOnLoadCallback(drawPhylumPieChart);
    google.setOnLoadCallback(drawDomainComposition);

    function drawPhylumTable() {
        // Taxonomy top phylum table
        var taxMatchesData = new google.visualization.DataTable();
        taxMatchesData.addColumn('string', '');
        taxMatchesData.addColumn('string', 'Phylum');
        taxMatchesData.addColumn('string', 'Domain');
        taxMatchesData.addColumn('number', 'Unique OTUs');
        taxMatchesData.addColumn('number', 'Count of reads assigned');
        taxMatchesData.addColumn('number', '% reads assigned');
        taxMatchesData.addRows([
            <c:set var="addComma" value="false"/>
            <c:forEach var="taxonomyData" items="${model.taxonomyAnalysisResult.taxonomyDataSet}" varStatus="status">
            <c:choose>
            <c:when test="${addComma}">,
            </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise>
            </c:choose>
            ['<ul class="color_legend"><li  style="color: #${taxonomyData.colourCode};"></li></ul>', '${taxonomyData.phylum}', '${taxonomyData.superKingdom}', ${taxonomyData.numberOfHits}, , ${taxonomyData.percentage}]
            </c:forEach>
        ]);


        // Define a StringFilter control for the 'Phylum' column
        var taxstringFilter = new google.visualization.ControlWrapper({
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
            'options':{ allowHtml:true, showRowNumber:true, page:'enable', pageSize:10, pagingSymbols:{prev:'prev', next:'next'}, sortColumn:3, sortAscending:false }
        });

        // Create the dashboard.
        var tax_dashboard = new google.visualization.Dashboard(document.getElementById('tax_dashboard')).
            // Configure the string filter to affect the table contents
                bind(taxstringFilter, taxTableOptions).
            // Draw the dashboard
                draw(taxMatchesData);
    }  //END function drawPhylumTable()

    function drawPhylumPieChart() {
        // DATA taxonomy Pie+Bar chart Phylum
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Phylum');
        data.addColumn('number', 'Match');
        data.addRows([
            <c:set var="addComma" value="false"/>
            <c:forEach var="taxonomyData" items="${model.taxonomyAnalysisResult.taxonomyDataSet}" varStatus="status">
            <c:choose>
            <c:when test="${addComma}">,
            </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise>
            </c:choose>
            ['${taxonomyData.phylum}', ${taxonomyData.numberOfHits}]
                    </c:forEach>

//            ['Proteobacteria', 146],
//            ['Crenarchaeota', 17],
//            ['Euryarchaeota', 11],
//            ['Bacteroidetes', 11],
//            ['SAR406', 11],
//            ['Actinobacteria', 10],
//            ['Verrucomicrobia', 7],
//            ['Chloroflexi', 3],
//            ['NC10', 3],
//            ['PAUC34f', 2],
//            ['Planctomycetes', 2],
//            ['Caldiserica', 1],
//            ['Cyanobacteria', 1],
//            ['Elusimicrobia', 1],
//            ['Firmicutes', 1],
//            ['OP11', 1],
//            ['Unassigned bacteria', 1]
        ]);

        // taxonomy Pie chart Phylum
        var options = {'title':'Phylum composition (Total: 229)',
            'titleTextStyle':{fontSize:12},
            'colors':['#058dc7', '#50b432', '#ed561b', '#edef00', '#24cbe5', '#64e572', '#ff9655', '#fff263', '#6af9c4', '#b2deff', '#ccc'],
//Krona style      'colors':['#d47f7f','#d1a575','#d4c97f','#99d47f','#7fd4a7','#7fc3d4','#7f8ad4','#a77fd4','#d47fd3','#d47faf','#ccc','#ccc','#ccc'],
//      'width':220,
            'width':290,
            'height':220,
            'legend':{position:'right', fontSize:10},
            'chartArea':{left:10, top:30, width:"100%", height:"100%"},
            'pieSliceBorderColor':'none',
            'sliceVisibilityThreshold':1 / 115
        };

        var pieChart = new google.visualization.PieChart(document.getElementById('tax_chart_pie_phy'));
        pieChart.draw(data, options);
    }

    function drawDomainComposition() {
        // DATA taxonomy Pie+Bar chart Domain
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'kingdom');
        data.addColumn('number', 'Match');
        data.addRows([
            <c:set var="addComma" value="false"/>
            <c:forEach var="domainEntry" items="${model.taxonomyAnalysisResult.domainMap}">
            <c:choose>
            <c:when test="${addComma}">,
            </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise>
            </c:choose>
            ['${domainEntry.key}', ${domainEntry.value}]
            </c:forEach>
//            ['Bacteria', 201],
//            ['Archaea', 28],
//            ['Unassigned', 1]
        ]);

        // taxonomy Pie chart domain
        var options = {'title':'Domain composition', 'titleTextStyle':{fontSize:12}, 'colors':['#5f8694','#91d450', '#535353' ],'width':200, 'height':220, 'chartArea':{left:10, top:26, width:"80%", height:"55%"}, 'pieSliceBorderColor':'none', 'legend':{fontSize:10}, 'pieSliceTextStyle':{color:'white'}};

        var chart = new google.visualization.PieChart(document.getElementById('tax_chart_pie_dom'));
        chart.draw(data, options);
    }
</script>