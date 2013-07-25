<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%--<script type="text/javascript">--%>
    <%--$(document).ready(function(){--%>
        <%--$('#expandercontent').css('display','none');--%>
        <%--$("#expanderhead").click(function(){--%>
            <%--$("#expandercontent").slideToggle();--%>
            <%--if ($("#expandersign").text() == "+"){--%>
                <%--$("#expandersign").text("-")--%>
            <%--}--%>
            <%--else {--%>
                <%--$("#expandersign").text("+")--%>
            <%--}--%>
        <%--});--%>
    <%--});--%>
<%--</script>--%>

<script type="text/javascript">

// Load the Visualization API and the piechart package.
google.load('visualization', '1.1', {'packages':['corechart','table','controls'] });


// Set a callback to run when the Google Visualization API is loaded.
google.setOnLoadCallback(drawChart);

// Callback that creates and populates a data table,
// instantiates the pie chart, passes in the data and
// draws it.
function drawChart() {

     // DATA taxonomy Pie+Bar chart Domain
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'kingdom');
    data.addColumn('number', 'Match');
    data.addRows([
        ['Bacteria', 201],
        ['Archaea', 28],
        ['Unassigned', 1]
    ]);

    // DATA taxonomy Pie+Bar chart Phylum
    var data2 = new google.visualization.DataTable();
    data2.addColumn('string', 'Phylum');
    data2.addColumn('number', 'Match');
    data2.addRows([
        ['Proteobacteria', 146],
        ['Crenarchaeota', 17],
        ['Euryarchaeota', 11],
        ['Bacteroidetes', 11],
        ['SAR406', 11],
        ['Actinobacteria', 10],
        ['Verrucomicrobia', 7],
        ['Chloroflexi', 3],
        ['NC10', 3],
        ['PAUC34f', 2],
        ['Planctomycetes', 2],
        ['Caldiserica', 1],
        ['Cyanobacteria', 1],
        ['Elusimicrobia', 1],
        ['Firmicutes', 1],
        ['OP11', 1],
        ['Unassigned bacteria', 1]
    ]);


    // DATA taxonomy Stacked column
    var data3 = google.visualization.arrayToDataTable([
        [ '','Proteobacteria', 'Crenarchaeota', 'Euryarchaeota', 'Bacteroidetes', 'SAR406', 'Actinobacteria', 'Verrucomicrobia', 'Chloroflexi', 'NC10', 'PAUC34f', 'Planctomycetes', 'Caldiserica', 'Cyanobacteria', 'Elusimicrobia', 'Firmicutes', 'OP11', 'Unassigned bacteria'],
        ['', 146/229, 17/229, 11/229, 11/229, 11/229, 10/229, 7/229, 3/229, 3/229, 2/229, 2/229, 1/229, 1/229, 1/229, 1/229, 1/229 , 1/229 ]
    ]);

    // DATA function Bar GO terms bp
    var data4 = new google.visualization.DataTable();
    data4.addColumn('string', 'GO term');
    data4.addColumn('number', 'Match');
    data4.addRows([
        ['cell projection assembly', 37],
        ['cell wall organization or biogenesis', 340],
        ['cellular component organization  ', 1068],
                ['cell division', 105],
                ['cell growth', 0],
                ['cell motility ', 292],
                ['transposition', 169],
                ['ion transport', 2062],
                ['protein secretion', 258],
                ['transport ',8058 ],
                ['ATP metabolic process', 443],
                ['biosynthetic process', 21253],
                ['carbohydrate metabolic process', 4158],
                ['catabolic process', 2251],
                ['cellular amino acid metabolic process', 6824],
                ['cellular respiration', 1045],
                ['cofactor metabolic process ', 2389],
                ['DNA metabolic process', 5063],
                ['electron transport chain', 595],
                ['generation of precursor metabolites and energy', 1625],
                ['GTP metabolic process', 115],
                ['lipid metabolic process', 2039],
                ['nitrogen compound metabolic process', 23093],
                ['nitrogen cycle metabolic process', 27],
                ['nucleotide metabolic process', 2356],
                ['oxidation-reduction process', 14516],
                ['phosphorylation', 1219],
                ['photosynthesis', 44],
                ['protein metabolic process', 8764],
                ['RNA metabolic process', 7554],
                ['small molecule metabolic process', 12914],
                ['sulfur compound metabolic process', 560],
                ['toxin metabolic process ', 19],
                ['transcription, DNA-dependent', 3479],
                ['translation', 4501],
                ['cell communication', 1149],
                ['chemotaxis ', 136],
                ['response to stress', 1772],
                ['signal transduction', 1071],
                ['two-component signal transduction system (phosphorelay)', 477],
                ['cell morphogenesis', 150],
                ['conjugation', 11],
                ['homeostatic process', 393],
                ['immune system process', 4],
                ['pathogenesis', 20],
                ['sporulation', 0],
                ['symbiosis, encompassing mutualism through parasitism', 4],
                ['viral reproduction  ', 35]
    ]);

    // DATA function Bar GO terms mf
        var data5 = new google.visualization.DataTable();
        data5.addColumn('string', 'GO term');
        data5.addColumn('number', 'Match');
        data5.addRows([
            ['cofactor binding', 7883],
            ['cytoskeletal protein binding', 4],
            ['metal ion binding', 5540],
            ['nucleic acid binding', 10224 ],
            ['nucleotide binding', 15793],
            ['receptor binding', 8],
            [' transcription factor binding', 150],
            ['unfolded protein binding', 246],
            ['aminoacyl-tRNA ligase activity', 2382],
            ['isomerase activity ',2425 ],
            ['ligase activity', 5727],
            ['lyase activity', 2867],
            ['oxidoreductase activity', 15758],
            ['ATPase activity', 2882],
            ['GTPase activity', 553],
            ['helicase activity', 1028],
            ['hydrolase activity', 13262],
            ['hydrolase activity, acting on carbon-nitrogen (but not peptide) bonds', 1010],
            ['hydrolase activity, acting on ester bonds', 2911],
            ['hydrolase activity, acting on glycosyl bonds', 521],
            ['lipase activity', 36],
            ['nuclease activity', 1431],
            ['peptidase activity', 2493],
            ['phosphatase activity', 200],
            ['acetyltransferase activity', 189],
            ['DNA polymerase activity', 752],
            ['kinase activity', 1492],
            ['protein kinase activity', 406],
            ['RNA polymerase activity', 1176],
            ['transferase activity', 11979 ],
            ['transferase activity, transferring acyl groups', 1148],
            ['transferase activity, transferring alkyl or aryl (other than methyl) groups', 865],
            ['transferase activity, transferring glycosyl groups', 718],
            ['enzyme regulator activity', 143],
            ['motor activity', 158],
            ['nucleic acid binding transcription factor activity', 999],
            ['protein binding transcription factor activity', 323],
            ['receptor activity', 950],
            ['translation regulator activity', 0],
            ['transporter activity', 6672],
            ['two-component response regulator activity', 423]
        ]);

    // DATA function Bar GO terms cc
            var data6 = new google.visualization.DataTable();
            data6.addColumn('string', 'GO term');
            data6.addColumn('number', 'Match');
            data6.addRows([
                ['DNA polymerase complex', 117],
                ['proton-transporting two-sector ATPase complex', 538],
                ['ribosome', 1865],
                [' transcription factor complex', 0 ],
                ['chromosome', 883],
                ['cytoplasm', 7170],
                ['cytoskeleton', 33],
                ['endoplasmic reticulum', 24],
                ['extrachromosomal circular DNA', 30],
                ['Golgi apparatus ',2],
                ['mitochondrion', 25],
                ['nucleoid', 98],
                ['nucleus', 31],
                ['periplasmic space', 584],
                ['thylakoid', 4],
                ['vacuole', 3],
                ['vesicle', 5],
                ['ATP-binding cassette (ABC) transporter complex', 28],
                ['cell wall', 162],
                ['flagellum', 387],
                ['membrane', 10552],
                ['outer membrane', 317],
                ['pilus', 8],
                ['plasma membrane', 312],
                ['respiratory chain', 15],
                ['extracellular matrix', 18],
                ['virion', 103]
            ]);

   // DATA function PIE GO terms bp SORTED BY DECREASED NUMBER
        var data7 = new google.visualization.DataTable();
        data7.addColumn('string', 'GO term');
        data7.addColumn('number', 'Match');
        data7.addRows([
            ['nitrogen compound metabolic process', 23093],
            ['biosynthetic process', 21253],
            ['oxidation-reduction process', 14516],
            ['small molecule metabolic process', 12914],
            ['protein metabolic process', 8764],
            ['transport ', 8058],
            ['RNA metabolic process', 7554],
            ['cellular amino acid metabolic process', 6824],
            ['DNA metabolic process', 5063],
            ['translation', 4501],
            ['carbohydrate metabolic process', 4158],
            ['transcription, DNA-dependent', 3479],
            ['cofactor metabolic process ', 2389],
            ['nucleotide metabolic process', 2356],
            ['catabolic process', 2251],
            ['ion transport', 2062],
            ['lipid metabolic process', 2039],
            ['response to stress', 1772],
            ['generation of precursor metabolites and energy', 1625],
            ['phosphorylation', 1219],
            ['cell communication', 1149],
            ['signal transduction', 1071],
            ['cellular component organization  ', 1068],
            ['cellular respiration', 1045],
            ['electron transport chain', 595],
            ['sulfur compound metabolic process', 560],
            ['two-component signal transduction system (phosphorelay)', 477],
            ['ATP metabolic process', 443],
            ['homeostatic process', 393],
            ['cell wall organization or biogenesis', 340],
            ['cell motility ', 292],
            ['protein secretion', 258],
            ['transposition', 169],
            ['cell morphogenesis', 150],
            ['chemotaxis ', 136],
            ['GTP metabolic process', 115],
            ['cell division', 105],
            ['photosynthesis', 44],
            ['cell projection assembly', 37],
            ['viral reproduction  ', 35],
            ['nitrogen cycle metabolic process', 27],
            ['pathogenesis', 20],
            ['toxin metabolic process ', 19],
            ['conjugation', 11],
            ['immune system process', 4],
            ['symbiosis, encompassing mutualism through parasitism', 4],
            ['cell growth', 0],
            ['sporulation', 0]
        ]);

    // DATA function PIE GO terms mf SORTED BY DECREASED NUMBER
    var data8 = new google.visualization.DataTable();
            data8.addColumn('string', 'GO term');
            data8.addColumn('number', 'Match');
            data8.addRows([
                ['nucleotide binding', 15793],
                ['oxidoreductase activity', 15758],
                ['hydrolase activity', 13262],
                ['transferase activity', 11979 ],
                ['nucleic acid binding', 10224 ],
                ['cofactor binding', 7883],
                ['transporter activity', 6672],
                ['ligase activity', 5727],
                ['metal ion binding', 5540],
                ['hydrolase activity, acting on ester bonds', 2911],
                ['ATPase activity', 2882],
                ['lyase activity', 2867],
                ['peptidase activity', 2493],
                ['isomerase activity ',2425 ],
                ['aminoacyl-tRNA ligase activity', 2382],
                ['kinase activity', 1492],
                ['nuclease activity', 1431],
                ['RNA polymerase activity', 1176],
                ['transferase activity, transferring acyl groups', 1148],
                ['helicase activity', 1028],
                ['hydrolase activity, acting on carbon-nitrogen (but not peptide) bonds', 1010],
                ['nucleic acid binding transcription factor activity', 999],
                ['receptor activity', 950],
                ['transferase activity, transferring alkyl or aryl (other than methyl) groups', 865],
                ['DNA polymerase activity', 752],
                ['transferase activity, transferring glycosyl groups', 718],
                ['protein kinase activity', 406],
                ['GTPase activity', 553],
                ['hydrolase activity, acting on glycosyl bonds', 521],
                ['two-component response regulator activity', 423],
                ['protein binding transcription factor activity', 323],
                ['unfolded protein binding', 246],
                ['phosphatase activity', 200],
                ['acetyltransferase activity', 189],
                ['motor activity', 158],
                [' transcription factor binding', 150],
                ['enzyme regulator activity', 143],
                ['lipase activity', 36],
                ['receptor binding', 8],
                ['cytoskeletal protein binding', 4],
                ['translation regulator activity', 0]
            ]);

    // DATA function PIE GO terms cc SORTED BY DECREASED NUMBER
    var data9 = new google.visualization.DataTable();
    data9.addColumn('string', 'GO term');
    data9.addColumn('number', 'Match');
    data9.addRows([
        ['membrane', 10552],
        ['cytoplasm', 7170],
        ['ribosome', 1865],
        ['proton-transporting two-sector ATPase complex', 538],
        ['chromosome', 883],
        ['periplasmic space', 584],
        ['flagellum', 387],
        ['outer membrane', 317],
        ['plasma membrane', 312],
        ['cell wall', 162],
        ['DNA polymerase complex', 117],
        ['virion', 103],
        ['nucleoid', 98],
        ['cytoskeleton', 33],
        ['nucleus', 31],
        ['extrachromosomal circular DNA', 30],
        ['ATP-binding cassette (ABC) transporter complex', 28],
        ['mitochondrion', 25],
        ['endoplasmic reticulum', 24],
        ['extracellular matrix', 18],
        ['respiratory chain', 15],
        ['pilus', 8],
        ['vesicle', 5],
        ['thylakoid', 4],
        ['vacuole', 3],
        ['Golgi apparatus', 2],
        [' transcription factor complex', 0]
    ]);

    // taxonomy Pie chart domain
    var options = {'title':'Domain composition', 'titleTextStyle':{fontSize:12}, 'colors':['#5f8694','#91d450', '#535353' ],'width':200, 'height':220, 'chartArea':{left:10, top:26, width:"80%", height:"55%"}, 'pieSliceBorderColor':'none', 'legend':{fontSize:10}, 'pieSliceTextStyle':{color:'white'}};

    // taxonomy Pie chart Phylum
    var options2 = {'title':'Phylum composition (Total: 229)',
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

     // Taxonomy Bar - domain
    var options3 = {'title':'Domain composition',
        'titleTextStyle':{fontSize:12},
        'colors':['#5f8694'],
        'width':240,
        'height':180,
        'chartArea':{left:70, top:40, width:"66%", height:"70%"},
        'vAxis':{textStyle:{fontSize:11}},
        'pieSliceBorderColor':'none',
        'bar':{groupWidth:10},
        'legend':'none'
    };

    // Taxonomy Bar - phylum
    var options4 = {'title':'Phylum composition (Total: 229)',
        'titleTextStyle':{fontSize:12},
        'colors':['#5f8694'],
        'width':400,
        'height':320,
        'chartArea':{left:120, top:40, width:"66%", height:"70%"},
        'pieSliceBorderColor':'none',
        'legend':'none'
    };

    // Stacked column graph
    var options5 = {'title':'Phylum composition (Total: 229)',
        'titleTextStyle':{fontSize:12},
        'colors':['#058dc7', '#50b432', '#ed561b', '#edef00', '#24cbe5', '#64e572', '#ff9655', '#fff263', '#6af9c4', '#b2deff', '#ccc', '#ccc', '#ccc', '#ccc', '#ccc', '#ccc', '#ccc'],
        'width':320,
        'height':400,
        'legend':{position:'right', fontSize:10},
        'chartArea':{left:80, top:40, width:"20%", height:"86%"},
        'pieSliceBorderColor':'none',
        'sliceVisibilityThreshold':1 / 50,
        'vAxis': { viewWindowMode:'maximized'},//        important to keep viewWindowMode separated from the rest to keep the display of the value 100% on vaxis
        'vAxis': {title:'Relative abundance', format:'#%', baselineColor: '#ccc'},
        'isStacked':true
    };

    // GO TERM bar Biological Process
    var options6 = {'title':'Biological process', 'titleTextStyle':{fontSize:12}, 'colors':['#058dc7'], 'width':340, 'height':600, 'chartArea':{left:220, top:40, width:"100%", height:"100%"}, 'vAxis':{textStyle:{fontSize:10}}, 'hAxis':{textPosition: 'none', gridlines:{color:'white'}}, 'bar':{groupWidth:8}, 'legend':'none'
//        'colors':['#5f8694'],
//        'vAxis':{'textPosition':'in'},
    };

    // GO TERM bar Molecular Function
    var options7 = {'title':'Molecular function', 'titleTextStyle':{fontSize:12}, 'colors':['#50b432'], 'width':230, 'height':600, 'chartArea':{left:110, top:40, width:"100%", height:"100%"}, 'vAxis':{textStyle:{fontSize:10}},'hAxis':{textPosition: 'none', gridlines:{color:'white'}}, 'bar':{groupWidth:8}, 'legend':'none'
//        'colors':['#91d450'],
//        'width':340,
//        'vAxis':{'textPosition':'in'},
    };

    // GO TERM bar Cellular component
    var options8 = {'title':'Cellular component', 'titleTextStyle':{fontSize:12},'colors':['#ed561b'], 'width':320, 'height':600, 'chartArea':{left:220, top:40, width:"100%", height:"100%"}, 'vAxis':{textStyle:{fontSize:10}}, 'hAxis':{textPosition: 'none', gridlines:{color:'white', count:15}}, 'bar':{groupWidth:8}, 'legend':'none'
//        'colors':['#535353'],
//        'vAxis':{'textPosition':'in'},
    };

    // GO TERM Pie Biological process
    var options9 = {'title':'Biological process',
        'titleTextStyle':{fontSize:12},
        'colors':['#058dc7', '#50b432', '#ed561b', '#edef00', '#24cbe5', '#64e572', '#ff9655', '#fff263', '#6af9c4', '#b2deff'],
        'width':340,
        'height':220,
        'legend':{position:'right', fontSize:10},
        'chartArea':{left:0, top:30, width:"70%", height:"100%"},
        'pieSliceBorderColor':'none',
        'sliceVisibilityThreshold':1 / 30
    };

    // GO TERM Pie Molecular function
    var options10 = {'title':'Molecular function',
        'titleTextStyle':{fontSize:12},
        'colors':['#058dc7', '#50b432', '#ed561b', '#edef00', '#24cbe5', '#64e572', '#ff9655', '#fff263', '#6af9c4', '#b2deff'],
        'width':340,
        'height':220,
        'legend':{position:'right', fontSize:10},
        'chartArea':{left:0, top:30, width:"70%", height:"100%"},
        'pieSliceBorderColor':'none',
        'sliceVisibilityThreshold':1 / 30
    };

    // GO TERM Pie Cellular component
    var options11 = {'title':'Cellular component',
        'titleTextStyle':{fontSize:12},
        'colors':['#058dc7', '#50b432', '#ed561b', '#edef00', '#24cbe5', '#64e572', '#ff9655', '#fff263', '#6af9c4', '#b2deff'],
        'width':340,
        'height':220,
        'legend':{position:'right', fontSize:10},
        'chartArea':{left:0, top:30, width:"70%", height:"100%"},
        'pieSliceBorderColor':'none',
        'sliceVisibilityThreshold':1 / 100
    };

    // Instantiate and draw our chart, passing in some options.
    var chart = new google.visualization.PieChart(document.getElementById('tax_chart_pie_dom'));
    chart.draw(data, options);
    var chart2 = new google.visualization.PieChart(document.getElementById('tax_chart_pie_phy'));
    chart2.draw(data2, options2);
    var chart3 = new google.visualization.BarChart(document.getElementById('tax_chart_bar_dom'));
    chart3.draw(data, options3);
    var chart4 = new google.visualization.BarChart(document.getElementById('tax_chart_bar_phy'));
    chart4.draw(data2, options4);
    var chart5 = new google.visualization.ColumnChart(document.getElementById('tax_chart_col'));
    chart5.draw(data3, options5);
    var chart6 = new google.visualization.BarChart(document.getElementById('func_chart_bar_go_bp'));
    chart6.draw(data4, options6);
    var chart7 = new google.visualization.BarChart(document.getElementById('func_chart_bar_go_mf'));
    chart7.draw(data5, options7);
    var chart8 = new google.visualization.BarChart(document.getElementById('func_chart_bar_go_cc'));
    chart8.draw(data6, options8);
    var chart9 = new google.visualization.PieChart(document.getElementById('func_chart_pie_go_bp'));
    chart9.draw(data7, options9);
    var chart10 = new google.visualization.PieChart(document.getElementById('func_chart_pie_go_mf'));
    chart10.draw(data8, options10);
    var chart11 = new google.visualization.PieChart(document.getElementById('func_chart_pie_go_cc'));
    chart11.draw(data9, options11);
}

</script>

<script type='text/javascript'>

 //BEGIN code used to showroom the row number selection - TODO apply on the new table

    google.setOnLoadCallback(init);

    var dataSourceUrl = 'https://docs.google.com/spreadsheet/ccc?key=0AgWotcbTSSjYdGF6NjE0WGxGRmV5djJDWEZ6RzZhT2c&usp=sharing';
    var query, options, container;

       function init() {
         query = new google.visualization.Query(dataSourceUrl);
         container = document.getElementById("func_table_div1");
         options = {width:600, allowHtml:true, showRowNumber:true, page:'enable', pageSize:10, pagingSymbols:{prev:'prev', next:'next'}, sortColumn:2, sortAscending:false};
         sendAndDraw();
       }

       function sendAndDraw() {
         query.abort();
         var tableQueryWrapper = new TableQueryWrapper(query, container, options);
         tableQueryWrapper.sendAndDraw();
       }


       function setOption(prop, value) {
         options[prop] = value;
         sendAndDraw();
       }


    google.setOnLoadCallback(drawTable);// Set a callback to run when the Google Visualization API is loaded.

 //END code used to showroom the row number selection - TODO apply on the new table


function drawTable() {

        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Phylum');
        data.addColumn('string', 'Kingdom');
        data.addColumn('number', 'Unique OTUs');
        data.addColumn('number', 'Count of reads assigned');
        data.addColumn('number', '% reads assigned');
        data.addRows([
            <c:set var="addComma" value="false"/>
            <c:set var="colourCode" value="#058dc7" scope="page"/>
            <c:forEach var="taxonomyData" items="${model.taxonomyAnalysisResult.taxonomyDataSet}" varStatus="status">
            <c:choose>
            <c:when test="${addComma}">,
            </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise>
            </c:choose>
            ['<a href="#">${taxonomyData.phylum}</a>', '${taxonomyData.superKingdom}', ${taxonomyData.numberOfHits}, ,  ${taxonomyData.percentage}]
            </c:forEach>
        ]);

    // Table used for bar chart where we don't need to get colour for each row
        var data2 = new google.visualization.DataTable();
            data2.addColumn('string', 'Phylum');
            data2.addColumn('string', 'Domain');
            data2.addColumn('number', 'Unique OTUs');
            data2.addColumn('number', 'Count of reads assigned');
            data2.addColumn('number', '% reads assigned');
            data2.addRows([
            <c:set var="addComma" value="false"/>
            <c:set var="colourCode" value="#058dc7" scope="page"/>
            <c:forEach var="taxonomyData" items="${model.taxonomyAnalysisResult.taxonomyDataSet}" varStatus="status">
            <c:choose>
            <c:when test="${addComma}">,
            </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise>
            </c:choose>
            ['${taxonomyData.phylum}', '${taxonomyData.superKingdom}', ${taxonomyData.numberOfHits},,  ${taxonomyData.percentage}]
            </c:forEach>
        ]);

        var data3 = new google.visualization.DataTable();
        data3.addColumn('string', 'Phylum');
        data3.addColumn('string', 'Kingdom');
        data3.addColumn('number', 'Hits');
        data3.addColumn('number', '%');
        data3.addRows([
            <c:set var="addComma" value="false"/>
            <c:set var="colourCode" value="#058dc7" scope="page"/>
            <c:forEach var="taxonomyData" items="${model.taxonomyAnalysisResult.taxonomyDataSet}" varStatus="status">
            <c:choose>
            <c:when test="${addComma}">,
            </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise>
            </c:choose>
            ['<a href="#">${taxonomyData.phylum}</a>', '${taxonomyData.superKingdom}', ${taxonomyData.numberOfHits}, ${taxonomyData.percentage}]
            </c:forEach>
        ]);

        //Pie top hits table
        var table2 = new google.visualization.Table(document.getElementById('tax_table_pie'));
        table2.draw(data2, { allowHtml:true, showRowNumber:true, page:'enable', pageSize:10, pagingSymbols:{prev:'prev', next:'next'}, sortColumn:2, sortAscending:false});

        //TABLE top hits stacked column
        var table5 = new google.visualization.Table(document.getElementById('tax_table_col'));
        table5.draw(data2, { allowHtml:true, showRowNumber:true, page:'enable', pageSize:10, pagingSymbols:{prev:'prev', next:'next'}, sortColumn:3, sortAscending:false});

        //TABLE top hits Bar
        var table6 = new google.visualization.Table(document.getElementById('tax_table_bar'));
        table6.draw(data2, { allowHtml:true, showRowNumber:true, page:'enable', pageSize:10, pagingSymbols:{prev:'prev', next:'next'}, sortColumn:2, sortAscending:false});
    }

</script>






    <script type="text/javascript">
        // This script produces the chart and the table for the 'InterPro match summary' section (functional analysis tab)

        //  Load the Visualization API and the chart package.
        //  Line moved to rootTemplate.jsp

        //  Set a callback to run when the Google Visualization API is loaded.
        google.setOnLoadCallback(drawInterProMatchesTable);
        google.setOnLoadCallback(drawInterProMatchesPieChart);

        function drawInterProMatchesTable() {
          drawVisualization();
          drawToolbar();
        }

        function drawVisualization() {

            // Functional analysis table - List of InterPro matches
              var interProMatchesData = new google.visualization.DataTable();
                interProMatchesData.addColumn('string', 'Entry name');
                interProMatchesData.addColumn('string', 'ID');
                interProMatchesData.addColumn('number', 'Hits');
                interProMatchesData.addRows([
                    <c:set var="addComma" value="false"/>
                    <c:forEach var="entry" items="${model.interProEntries}" varStatus="status">
                    <c:choose>
                    <c:when test="${addComma}">,
                    </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise>
                    </c:choose>
                    //  !important TEMP solution - sorting order doesn't work properly for entry name when using HTML tags
                    ['<a title="${entry.entryDescription}" target="_blank" href="http://www.ebi.ac.uk/interpro/entry/${entry.entryID}">${entry.entryDescription}</a>', '${entry.entryID}', ${entry.numOfEntryHits}]
                    </c:forEach>
                ]);

            // Define a StringFilter control for the 'Name'and 'ID' column
            var stringFilter = new google.visualization.ControlWrapper({
              'controlType': 'StringFilter',
              'containerId': 'func_table_filter',
              'options': {'matchType':'any','filterColumnIndex': '0,1', 'ui': {'label': 'Filter', 'labelSeparator': ':', 'ui.labelStacking':'vertical', 'ui.cssClass':'custom_col_search'}
              }
            });

            // Table visualization option
            var interProMatchesTableOptions = new google.visualization.ChartWrapper({
              'chartType': 'Table',
              'containerId': 'func_table_pie_interpro',
              'options': {width:'600', allowHtml:true, showRowNumber:true, page:'enable', pageSize:10, pagingSymbols:{prev:'prev', next:'next'}, sortColumn:2, sortAscending:false }
            });

            // Create the dashboard.
            var dashboard = new google.visualization.Dashboard(document.getElementById('func_dashboard')).
              // Configure the string filter to affect the table contents
              bind(stringFilter, interProMatchesTableOptions).
              // Draw the dashboard
              draw(interProMatchesData);



                    // Taxonomy analysis table
                var taxMatchesData = new google.visualization.DataTable();
                taxMatchesData.addColumn('string', '');
                taxMatchesData.addColumn('string', 'Phylum');
                taxMatchesData.addColumn('string', 'Domain');
                taxMatchesData.addColumn('number', 'Unique OTUs');
                taxMatchesData.addColumn('number', 'Count of reads assigned');
                taxMatchesData.addColumn('number', '% reads assigned');
                taxMatchesData.addRows([
                            <c:set var="addComma" value="false"/>
                            <c:set var="colourCode" value="#058dc7" scope="page"/>
                            <c:forEach var="taxonomyData" items="${model.taxonomyAnalysisResult.taxonomyDataSet}" varStatus="status">
                            <c:choose>
                            <c:when test="${addComma}">,
                            </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise>
                            </c:choose>
                            ['<ul class="color_legend"><li  style="color: #${taxonomyData.colourCode};"></li></ul>','${taxonomyData.phylum}', '${taxonomyData.superKingdom}', ${taxonomyData.numberOfHits}, ,  ${taxonomyData.percentage}]
                            </c:forEach>
                        ]);


                    // Define a StringFilter control for the 'Name'and 'ID' column
                    var taxstringFilter = new google.visualization.ControlWrapper({
                      'controlType': 'StringFilter',
                      'containerId': 'tax_table_filter',
                      'options': { 'matchType':'any',
                      'filterColumnIndex': '1',
                      'ui': {'label': 'Filter', 'labelSeparator': ':', 'ui.labelStacking':'vertical', 'ui.cssClass':'custom_col_search'}
                      }
                    });

                    // Table visualization option
                    var taxTableOptions = new google.visualization.ChartWrapper({
                      'chartType': 'Table',
                      'containerId': 'tax_table_pie',
                      'options': { allowHtml:true, showRowNumber:true, page:'enable', pageSize:10, pagingSymbols:{prev:'prev', next:'next'}, sortColumn:2, sortAscending:false }
                    });

                    // Create the dashboard.
                    var tax_dashboard = new google.visualization.Dashboard(document.getElementById('tax_dashboard')).
                      // Configure the string filter to affect the table contents
                      bind(taxstringFilter, taxTableOptions).
                      // Draw the dashboard
                      draw(taxMatchesData);

        // Pie chart produced from Google spreadsheet
//          var query = new google.visualization.Query('https://docs.google.com/spreadsheet/ccc?key=0AgWotcbTSSjYdGF6NjE0WGxGRmV5djJDWEZ6RzZhT2c&usp=sharing');
           // Optional request to return only column A and C

            //TODO: Do we need that?
//           query.setQuery('select A, B');
          // Send the query with a callback function.
//          query.send(handleQueryResponse);
        }  //END function drawVisualization()

        function drawToolbar() {
            var components = [
                {type: 'html', datasource: 'https://spreadsheets.google.com/tq?key=0AgWotcbTSSjYdGF6NjE0WGxGRmV5djJDWEZ6RzZhT2c'},
                {type: 'csv', datasource: 'https://spreadsheets.google.com/tq?key=0AgWotcbTSSjYdGF6NjE0WGxGRmV5djJDWEZ6RzZhT2c'},
                {type: 'htmlcode', datasource: 'https://spreadsheets.google.com/tq?key=0AgWotcbTSSjYdGF6NjE0WGxGRmV5djJDWEZ6RzZhT2c',
                    gadget: 'https://www.google.com/ig/modules/pie-chart.xml',

                    style: 'width: 800px; height: 700px; border: 1px solid black;'}
            ];

            var container1 = document.getElementById('toolbar_div');
            google.visualization.drawToolbar(container1, components);
        };


        // Set a callback to run when the Google Visualization API is loaded.
        google.setOnLoadCallback(drawInterProMatchesPieChart);

        // Callback that creates and populates a data table,
        // instantiates the pie chart, passes in the data and
        // draws it.
        function drawInterProMatchesPieChart() {

            // Create the data table.
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Entry name');
            data.addColumn('number', 'Hits');
            data.addRows([
                <c:set var="addComma" value="false"/>
                <c:forEach var="entry" items="${model.interProEntries}" varStatus="status">
                <c:choose>
                <c:when test="${addComma}">,
                </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise>
                </c:choose>
                ['${entry.entryDescription}', ${entry.numOfEntryHits}]
                </c:forEach>
            ]);

            // Set chart options
            var options = {title:'InterPro matches summary (Total: ${fn:length(model.interProEntries)})',width:500, titleTextStyle:{fontSize:12},
                colors:['#058dc7', '#50b432', '#ed561b', '#edef00', '#24cbe5', '#64e572', '#ff9655', '#fff263', '#6af9c4', '#b2deff', '#ccc'],
                height:240, legend:{position:'right', fontSize:10}, chartArea:{left:0, top:30, width:"42%", height:"100%"},
                pieSliceBorderColor:'none',  sliceVisibilityThreshold:1 /160};

            // Instantiate and draw our chart, passing in some options.
            var chart = new google.visualization.PieChart(document.getElementById('func_chart_pie'));
            chart.draw(data, options);

          //TODO: Think we can delete that part
            // // BEGIN pie chart that works with toolbar element
//        var container = document.getElementById('func_chart_pie');
//        var visualization = new google.visualization.PieChart(container);
//
//        // END PIe chart that works with toolbar element

//        //TODO: Not used anywhere
//        var query2 = new google.visualization.Query('https://docs.google.com/spreadsheet/ccc?key=0AgWotcbTSSjYdGF6NjE0WGxGRmV5djJDWEZ6RzZhT2c&usp=sharing');
//        query2.setQuery('select A, C');
//        query2.send(queryCallback);
//
//        function queryCallback(response) {
//          visualization.draw(response.getDataTable(), {is3D: false, title:'InterPro matches summary (Total: 51119)', titleTextStyle:{fontSize:12}, colors:['#058dc7', '#50b432', '#ed561b', '#edef00', '#24cbe5', '#64e572', '#ff9655', '#fff263', '#6af9c4', '#b2deff', '#ccc'], width:500, height:240, legend:{position:'right', fontSize:10}, chartArea:{left:0, top:30, width:"42%", height:"100%"}, pieSliceBorderColor:'none',  sliceVisibilityThreshold:1 /160});
//        }

        }

        //TODO: Do we need that?
//        function handleQueryResponse(response) {
//          if (response.isError()) {
//            alert('Error in query: ' + response.getMessage() + ' ' + response.getDetailedMessage());
//            return;
//          }
//
//          var data20 = response.getDataTable();
//        }
    </script>


<c:choose>
<c:when test="${not empty model.sample}">

<div class="title_tab">
    <span class="subtitle">Sample <a href="<c:url value="${baseURL}/sample/${model.sample.sampleId}"/>">
        (${model.sample.sampleId})</a></span>

    <h2>${model.sample.sampleName}</h2>
</div>

<div class="sample_ana">
<div id="navtabs">

    <%--Main Tabs--%>
<ul>
    <li><a href="#fragment-overview"><span>Overview</span></a></li>
    <li><a href="#fragment-quality"><span>Quality control</span></a></li>
    <li><a href="#fragment-taxonomy"><span>Taxonomy analysis</span></a></li>
    <li><a href="#fragment-functional"><span>Functional analysis</span></a></li>
    <li><a href="#fragment-download"><span>Download</span></a></li>
        <%--<li><a href="#fragment-experimental"><span>Experimental factor</span></a></li>--%>
</ul>


<div id="fragment-overview">


    <tags:publications publications="${model.sample.publications}" relatedPublications="${model.relatedPublications}" relatedLinks="${model.relatedLinks}" />

<div class="main_tab_content">
    <%--BEGIN DESCRIPTION--%>
    <h3 id="sample_desc">Description</h3>

    <div class="output_form">
        <c:choose>
            <c:when test="${not empty model.sample.sampleDescription}">
                <c:set var="sampleDescription" value="${model.sample.sampleDescription}"/>
                <p style="font-size:110%;"><c:out value="${sampleDescription}"/></p>
            </c:when>
        </c:choose>

        <c:choose>
            <c:when test="${not empty model.sample.sampleClassification}">
                <c:set var="sampleClassification" value="${model.sample.sampleClassification}"/>
            </c:when>
            <c:otherwise>
                <c:set var="sampleClassification" value="${notGivenId}"/>
            </c:otherwise>
        </c:choose>

        <div class="result_row"><label>Classification:</label><span> <c:out value="${sampleClassification}"/></span>
        </div>

    </div>
    <%--END DESCRIPTION--%>

    <%--BEGIN ENVIRONMENTAL/HOST ASSOCIATED    --%>
    <c:choose>

        <c:when test="${model.hostAssociated}">
            <h3>Host associated</h3>

            <div class="output_form" id="large">

               <div class="result_row"><label>Species:</label>
                    <c:choose>
                    <c:when test="${not empty model.sample.hostTaxonomyId && model.sample.hostTaxonomyId>0}">
        <span><c:out value="${model.sample.species}"/> <a class="ext"
                                                          href="<c:url value="http://www.uniprot.org/taxonomy/${model.sample.hostTaxonomyId}"/>">Tax
            ID <c:out value="${model.sample.hostTaxonomyId}"/></a> </span></div>
                </c:when>
                <c:otherwise>
                    <c:out value="${notGivenId}"/>
                </c:otherwise>
                </c:choose>


                    <c:if test="${not empty model.sample.hostSex}">
                        <c:set var="hostSex" value="${model.sample.hostSex}"/>
                        <div class="result_row"><label>Sex:</label> <span style="text-transform:lowercase;"><c:out
                                                value="${hostSex}"/></span></div>
                    </c:if>






                        <c:if test="${not empty model.sample.phenotype}">
                            <c:set var="phenotype" value="${model.sample.phenotype}"/>
                <div class="result_row"><label>Phenotype:</label> <span><c:out value="${phenotype}"/></span> </div>
                        </c:if>


            </div>  <%--end div output_form--%>

        </c:when>

        <c:otherwise>
            <h3>Environmental conditions</h3>

            <div class="output_form" id="large">
                <c:choose>
                    <c:when test="${not empty model.sample.environmentalBiome}">
                        <c:set var="environmentalBiome" value="${model.sample.environmentalBiome}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="environmentalBiome" value="${notGivenId}"/>
                    </c:otherwise>
                </c:choose>                                  <div class="result_row"><label>Biome:</label> <span><c:out value="${environmentalBiome}"/></span>
                </div>

                <c:choose>
                    <c:when test="${not empty model.sample.environmentalFeature}">
                        <c:set var="environmentalFeature" value="${model.sample.environmentalFeature}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="environmentalFeature" value="${notGivenId}"/>
                    </c:otherwise>
                </c:choose>
                <div class="result_row"><label>Experimental feature:</label> <span><c:out
                        value="${environmentalFeature}"/></span></div>
                <c:choose>
                    <c:when test="${not empty model.sample.environmentalMaterial}">
                        <c:set var="environmentalMaterial" value="${model.sample.environmentalMaterial}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="environmentalMaterial" value="${notGivenId}"/>
                    </c:otherwise>
                </c:choose>
                <div class="result_row"><label>Material:</label> <span><c:out
                        value="${environmentalMaterial}"/></span>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
    <%--END ENVIRONMENTAL/HOST ASSOCIATED--%>

    <%--BEGIN LOCALISATION   --%>
    <h3>Localisation</h3>

    <div class="output_form" id="large" style="overflow:auto;">

        <c:if test="${!model.hostAssociated}">
            <c:choose>
                <c:when test="${not empty model.sample.latitude}">
                    <c:set var="latLon" value="${model.sample.latitude} , ${model.sample.longitude}"/>
                    <div id="map_canvas"></div>
                    <script language="javascript"> initialize(${model.sample.latitude}, ${model.sample.longitude})</script>
                </c:when>
                <c:otherwise>
                    <c:set var="latLon" value="${notGivenId}"/>
                </c:otherwise>
            </c:choose>
            <div class="result_row"><label>Latitude/Longitude:</label> <span><c:out value="${latLon}"/></span></div>
        </c:if>

        <c:choose>
            <c:when test="${not empty model.sample.geoLocName}">
                <c:set var="geoLocName" value="${model.sample.geoLocName}"/>
            </c:when>
            <c:otherwise>
                <c:set var="geoLocName" value="${notGivenId}"/>
            </c:otherwise>
        </c:choose>
        <div class="result_row"><label>Geographic location:</label> <span><c:out value="${geoLocName}"/></span></div>
    </div>
    <%--END LOCALISATION   --%>

    <%--BEGIN OTHER INFO   --%>
    <c:if test="${not empty model.sampleAnnotations}">
        <h3 id="expanderhead" style="">Other information
            <%--<span id="expandersign">+</span>--%>
        </h3>
        <div id="expandercontent">
        <table border="1" class="result">
            <thead>
            <tr>
                <c:set var="headerWidth" value="" scope="page"/>
                <c:set var="headerId" value="" scope="page"/>
                <th id="${headerId}" abbr="${headerName}" width="${headerWidth}" scope="col">Annotation</th>
                <th id="${headerId}" abbr="${headerName}" width="${headerWidth}" scope="col">Value</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="annotation" items="${model.sampleAnnotations}" varStatus="status">
                <tr>
                    <td style="text-align:left;" id="ordered">${annotation.annotationName}</td>
                    <td style="text-align:left;">${annotation.annotationValue} ${annotation.unit}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </div>
    </c:if>
    <%--END OTHER INFO   --%>


</div>
</div>


<div id="fragment-taxonomy">

<div class="main_tab_full_content">
      <%--<h3>Taxonomy analysis</h3>--%>
      <h3>Top taxonomy Hits</h3>
        <div id="tabs-taxchart">

            <%--Tabs--%>
            <ul> <li class="selector_tab">Switch view:</li>
                <%--<li><a href="#tax-table" title="Table view"><span class="ico-table"></span></a></li>--%>
                <li><a href="#tax-pie" title="Pie chart view"><span class="ico-pie"></span></a></li>
                <li><a href="#tax-bar" title="Bar chart view"><span class="ico-barh"></span></a></li>
                <li><a href="#tax-col" title="Stacked column chart view"><span class="ico-col"></span></a></li>
                <li><a href="#tax-Krona" title="Krona interactive chart view"><span class="ico-krona"></span></a></li>
                <%--<li class="ico-downl"><a class="icon icon-functional" data-icon="=" href="#download" title="Download image/table"></a></li>--%>
            </ul>



                <%--Taxonomy google chart--%>
                <div id="tax-pie">
                    <div class="chart_container">
                        <div class="chart_container">
                            <div id="tax_chart_pie_dom"></div><div id="tax_chart_pie_phy"></div>

                            <div id="tax_dashboard">
                             <div id="tax_table_filter"></div>
                             <div id="tax_table_pie"></div>
                             </div>

                          </div>
                    </div>
                </div>

                <div id="tax-bar">
                    <div class="chart_container"><div id="tax_chart_bar_dom"></div><div id="tax_chart_bar_phy"></div><div id="tax_table_bar"></div></div>
                </div>

                <div id="tax-col">
                  <div class="chart_container"><div id="tax_chart_col"></div><div id="tax_table_col"></div>
                </div>

                 </div>
                  <div id="tax-Krona">
                  <object class="krona_chart" data="<c:url value="${baseURL}/sample/${model.sample.sampleId}/krona?taxonomy=true&collapse=false"/>" type="text/html"></object>
                  </div>


       </div>
  </div>
</div>

<div id="fragment-functional">

      <div class="main_tab_full_content">

              <h3>InterPro match summary</h3>

              <p>Most frequently found InterPro matches to this sample:</p>
              <%--<div>
                <div>
                  <button id="rerun" ><span class="icon icon-functional" data-icon="=" ></span></button>
                  <button id="select">Select what to download</button>
                </div>
                <ul>
                  <li><a href="#">Save chart image</a></li>
                  <li><a href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/doExportIPRFile"/>"   title="<spring:message code="analysisStatsView.label.download.i5.table.view"/>">Save table</a></li>
                  <li><a href="#">Save raw data</a></li>
                </ul>
              </div>--%>
              <c:choose>
                  <c:when test="${not empty model.interProEntries}">

          <div id="interpro-chart">

             <%--Tabs--%>
             <ul>
                 <%--<li><a href="#interpro-match-table" title="Table view"><span class="ico-table"></span></a></li>--%>
                     <li class="selector_tab">Switch view:</li>
                     <li><a href="#interpro-match-pie" title="Pie chart view"><span class="ico-pie"></span></a></li>
                 <%--<li><a href="#interpro-match-bar" title="Bar chart view"><span class="ico-barh"></span></a></li>--%>
                 <%--<li><a href="#interpro-match-col" title="Stacked column chart view"><span class="ico-col"></span></a></li>--%>
                 <%--<li><a href="#interpro-match-Krona" title="Krona interactive chart view"><span class="ico-krona"></span></a></li>--%>
                 <div class="ico-download" id="toolbar_div" style="display:none;"><a class="icon icon-functional" data-icon="=" id="csv" href="#"   title=""></a></div>
             </ul>



                 <div id="interpro-match-pie">

                <div class="chart_container">

                     <%--<div id="func_chart_div1"></div>--%>

                     <div id="func_chart_pie"></div>

                     <div id="func_dashboard">
                     <div id="func_table_filter"></div>
                     <div id="func_table_pie_interpro"></div>
                     </div>

                    <%--  BEGIN code used if we want to use the row number select option

                    <div id="func_table_div1" style="display:none;"></div>
                    <form action="" class="expandertable" >
                    Show rows:
                    <select onChange="setOption('pageSize', parseInt(this.value, 10))">
                    <option selected=selected value="10">10</option>
                    <option value="25">25</option>
                    <option value="50">50</option>
                    <option value="100">100</option>
                    <option value="1000">1000</option>
                    <option value="10000">All</option>
                    </select></form>

                    END code used if we want to use the row number select option  --%>

                 </div>


             </div>

          </div>



      </c:when>
      <c:otherwise>
          <b><c:out value="${noDisplayID}"/></b>
      </c:otherwise>
    </c:choose>

    <h3>GO Terms annotation</h3>
    <p>A summary of Gene Ontology (GO) terms derived from InterPro matches to your sample is provided in the charts below.</p>

    <div id="tabs-chart">

        <%--Tabs--%>
    <ul> <li class="selector_tab">Switch view:</li>
        <%--<li><a href="#go-terms-table" title="Table view"><span class="ico-table"></span></a></li>--%>
        <li><a href="#go-terms-pie" title="Pie chart view"><span class="ico-pie"></span></a></li>
        <li><a href="#go-terms-bar" title="Bar chart view"><span class="ico-barh"></span></a></li>
        <%--<li><a href="#go-terms-col" title="Stacked column chart view"><span class="ico-col"></span></a></li>--%>
        <%--<li><a href="#go-terms-Krona" title="Krona interactive chart view"><span class="ico-krona"></span></a></li>--%>
        <%--<li class="ico-downl"><a class="icon icon-functional" data-icon="=" href="#download" title="Download image/table"></a></li>--%>
    </ul>


    <div id="go-terms-bar">
    <div class="go-chart"><div id="func_chart_bar_go_bp"></div> <div id="func_chart_bar_go_mf"></div> <div id="func_chart_bar_go_cc"></div> </div>
    </div>

    <div id="go-terms-pie">
    <div class="go-chart"><div id="func_chart_pie_go_bp"></div> <div id="func_chart_pie_go_mf"></div> <div id="func_chart_pie_go_cc"></div> </div>
    </div>

     </div>

     </div>

</div> <%--end div fragment functional--%>

<div id="fragment-quality">
     <%--BEGIN READS SECTION   --%>

    <%--<h3>Submitted nucleotide data</h3>--%>
    <c:choose>
    <c:when test="${not empty model.sample.analysisCompleted}">

       <%--<h3>Quality control</h3>--%>

       <div style="display:block; overflow: auto;">
       <c:url var="statsImage" value="/getImage" scope="request">
           <c:param name="imageName" value="_summary.png"/>
           <c:param name="imageType" value="PNG"/>
           <c:param name="dir" value="${model.emgFile.fileID}"/>
       </c:url>
       <div style="float:left; margin-left: 9px;"><img src="<c:out value="${statsImage}"/>"/></div>

    </c:when>
    <c:otherwise>
        <h3>Data processed</h3> <p>Analysis in progress!</p>
     </c:otherwise>
    </c:choose>

         <ul style="list-style-type: none; padding-top:0; margin-top:0; line-height: 2;">
            <c:forEach var="downloadLink" items="${model.downloadSection.seqDataDownloadLinks}" varStatus="loop">
                <li>
                    <c:choose>
                        <c:when test="${downloadLink.externalLink}">
                            <a href="${downloadLink.linkURL}"
                               title="${downloadLink.linkTitle}">${downloadLink.linkText}</a>
                        </c:when>
                        <c:otherwise>
                            <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                               title="${downloadLink.linkTitle}">
                                    ${downloadLink.linkText}</a><span
                                class="list_date"> - ${downloadLink.fileSize}</span>
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:forEach>
        </ul>

       </div>

</div>

<div id="fragment-download">


    <%--<h3>Download results</h3>--%>

    <div class="box-export">
        <h4>Sequence data</h4>
        <ul>
            <c:forEach var="downloadLink" items="${model.downloadSection.seqDataDownloadLinks}" varStatus="loop">
                <li>
                    <c:choose>
                        <c:when test="${downloadLink.externalLink}">
                            <a href="${downloadLink.linkURL}"
                               title="${downloadLink.linkTitle}">${downloadLink.linkText}</a>
                        </c:when>
                        <c:otherwise>
                            <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                               title="${downloadLink.linkTitle}">
                                    ${downloadLink.linkText}</a><span
                                class="list_date"> - ${downloadLink.fileSize}</span>
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:forEach>
        </ul>
        <c:if test="${not empty model.downloadSection.funcAnalysisDownloadLinks}">
            <h4>Functional Analysis</h4>
            <ul>
                <c:forEach var="downloadLink" items="${model.downloadSection.funcAnalysisDownloadLinks}"
                           varStatus="loop">
                    <li>
                        <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                           title="${downloadLink.linkTitle}">
                                ${downloadLink.linkText}</a><span
                            class="list_date"> - ${downloadLink.fileSize}</span>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${not empty model.downloadSection.taxaAnalysisDownloadLinks}">
            <h4>Taxonomic Analysis</h4>
            <ul>
                <c:forEach var="downloadLink" items="${model.downloadSection.taxaAnalysisDownloadLinks}"
                           varStatus="loop">
                    <li>
                        <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                           title="${downloadLink.linkTitle}">
                                ${downloadLink.linkText}</a><span
                            class="list_date"> - ${downloadLink.fileSize}</span>
                    </li>
                </c:forEach>
            </ul>
        </c:if>

    </div>
</div>

</div>
<%--end navtabs--%>


<%--script for tabs--%>
<script>
    $( "#navtabs").tabs({ ${model.analysisStatus.disabledAttribute} });
    $( "#interpro-chart" ).tabs();
//    $( "#interpro-chart" ).tabs({ disabled: [1,2,3,4], selected: 0 });
    $( "#tabs-chart" ).tabs({ selected: 1  });
    $( "#tabs-taxchart" ).tabs({ disabled: [5], selected: 0 });

// fix the auto-scrolling issue when linking from homepage using htag
setTimeout(function() {
  if (location.hash) {
    window.scrollTo(0, 0);
  }
}, 1);
</script>

</c:when>
<c:otherwise>
    <h3>Sample ID Not Recognised</h3>
</c:otherwise>
</c:choose>

</div>  <%--end sample_ana--%>
<div class="but_top"><a href="#top" title="back to the top page">Top</a></div>
