<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
google.load('visualization', '1.0', {'packages':['corechart']});

// Set a callback to run when the Google Visualization API is loaded.
google.setOnLoadCallback(drawChart);

// Callback that creates and populates a data table,
// instantiates the pie chart, passes in the data and
// draws it.
function drawChart() {

    // Create the data table.
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'kingdom');
    data.addColumn('number', 'Match');
    data.addRows([
        ['Bacteria', 201],
        ['Archaea', 28],
        ['Unassigned', 1]
    ]);

    var data2 = new google.visualization.DataTable();
    data2.addColumn('string', 'Phylum');
    data2.addColumn('number', 'Match');
    data2.addRows([
        ['Proteobacteria', 146],
        ['Bacteroidetes', 11],
        ['SAR406', 11],
        ['Actinobacteria', 10],
        ['Verrucomicrobia', 7],
        ['Chloroflexi', 3],
        ['PAUC34f', 2],
        ['Planctomycetes', 2],
        ['Firmicutes', 1],
        ['Cyanobacteria', 1],
        ['Unassigned bacteria', 1]
    ]);

    var data3 = new google.visualization.DataTable();
    data3.addColumn('string', 'Phylum');
    data3.addColumn('number', 'Match');
    data3.addRows([
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

    var data4 = new google.visualization.DataTable();
    data4.addColumn('string', 'Phylum');
    data4.addColumn('number', 'Match');
    data4.addRows([
        ['Crenarchaeota', 17],
        ['Euryarchaeota', 11]
    ]);
    var data5 = google.visualization.arrayToDataTable([
//        [ '','Proteobacteria', 'Crenarchaeota', 'Euryarchaeota', 'Bacteroidetes', 'SAR406', 'Actinobacteria', 'Verrucomicrobia', 'Chloroflexi', 'NC10', 'PAUC34f', 'Planctomycetes', 'Caldiserica', 'Cyanobacteria', 'Elusimicrobia', 'Firmicutes', 'OP11', 'Unassigned bacteria'],
//        ['', 146/229, 17/229, 11/229, 11/229, 11/229, 10/229, 7/229, 3/229, 3/229, 2/229, 2/229, 1/229, 1/229, 1/229, 1/229, 1/229, 1/229]
        [ '','Proteobacteria', 'Crenarchaeota', 'Euryarchaeota', 'Bacteroidetes', 'SAR406', 'Actinobacteria', 'Verrucomicrobia', 'Chloroflexi', 'NC10', 'PAUC34f', 'Planctomycetes', 'Caldiserica', 'Cyanobacteria', 'Elusimicrobia', 'Firmicutes', 'OP11', 'Unassigned bacteria'],
        ['', 146/229, 17/229, 11/229, 11/229, 11/229, 10/229, 7/229, 3/229, 3/229, 2/229, 2/229, 1/229, 1/229, 1/229, 1/229, 1/229 , 1/229 ]
    ]);

    // Kingdom level
    var options = {'title':'Domain composition',
        'titleTextStyle':{fontSize:12},
// Krona colors       'colors':['#cf6f6f', '#cfc26f', '#8dcf6f'],
         'colors':['#5f8694','#91d450', '#535353' ],
//         'colors':['#5f8694','#91d450', '#d1dadd' ],
//        'colors':['#058dc7', '#50b432', '#ed561b'],
//                           'width':120,
        'width':200,
        'height':220,
        'chartArea':{left:10, top:26, width:"80%", height:"55%"},
//                        'chartArea.left':0,
//                          'chartArea.top':0,
        'pieSliceBorderColor':'none',
//                           'pieSliceText': 'label',
//                            'legend':{position:'none',fontSize:10},
        'legend':{fontSize:10},
        'pieSliceTextStyle':{color:'white'}
//                           'backgroundColor':'black'
    };

    // Bacteria level
    var options2 = {'title':'Bacteria level (Total: 201)',
        'titleTextStyle':{fontSize:12},
        'colors':['#058dc7', '#50b432', '#ed561b', '#edef00', '#24cbe5', '#64e572', '#ff9655', '#fff263', '#6af9c4', '#b2deff', '#ccc', '#ccc', '#ccc'],
//Krona style               'colors':['#d47f7f','#d1a575','#d4c97f','#99d47f','#7fd4a7','#7fc3d4','#7f8ad4','#a77fd4','#d47fd3','#d47faf','#ccc','#ccc','#ccc'],
        'width':290,
        'height':220,
        'legend':{position:'right', fontSize:10},
        'chartArea':{left:10, top:30, width:"100%", height:"100%"},
        'pieSliceBorderColor':'none'
//Krona style               'pieSliceBorderColor':'#9c8989'
    };
    // Archea level
    var options3 = {'title':'Archea level (Total: 28)',
        'titleTextStyle':{fontSize:12},
        'colors':['#058dc7', '#50b432', '#ed561b', '#edef00', '#24cbe5', '#64e572', '#ff9655', '#fff263', '#6af9c4', '#b2deff', '#ccc', '#ccc', '#ccc'],
//Krona style               'colors':['#d47f7f','#d1a575','#d4c97f','#99d47f','#7fd4a7','#7fc3d4','#7f8ad4','#a77fd4','#d47fd3','#d47faf','#ccc','#ccc','#ccc'],
        'width':290,
        'height':220,
        'legend':{position:'right', fontSize:10},
        'chartArea':{left:10, top:30, width:"100%", height:"100%"},
        'pieSliceBorderColor':'none'
    };

    // Top taxonomy Pie
    var options4 = {'title':'Phylum composition (Total: 229)',
        'titleTextStyle':{fontSize:12},
        'colors':['#058dc7', '#50b432', '#ed561b', '#edef00', '#24cbe5', '#64e572', '#ff9655', '#fff263', '#6af9c4', '#b2deff', '#ccc'],
//Krona style               'colors':['#d47f7f','#d1a575','#d4c97f','#99d47f','#7fd4a7','#7fc3d4','#7f8ad4','#a77fd4','#d47fd3','#d47faf','#ccc','#ccc','#ccc'],
//                            'width':220,
        'width':290,
        'height':220,
        'legend':{position:'right', fontSize:10},
//                            'legend':{position:'none',fontSize:10},
        'chartArea':{left:10, top:30, width:"100%", height:"100%"},
        'pieSliceBorderColor':'none',
        'sliceVisibilityThreshold':1 / 115
    };

    // Top taxonomy Bar
    var options5 = {'title':'Phylum composition (Total: 229)',
        'titleTextStyle':{fontSize:12},
        'colors':['#5f8694'],
        //Krona style               'colors':['#d47f7f','#d1a575','#d4c97f','#99d47f','#7fd4a7','#7fc3d4','#7f8ad4','#a77fd4','#d47fd3','#d47faf','#ccc','#ccc','#ccc'],
        'width':400,
        'height':320,
        'chartArea':{left:120, top:40, width:"66%", height:"70%"},
        'pieSliceBorderColor':'none',
        'legend':'none'
    };
    var options6 = {'title':'Domain composition',
        'titleTextStyle':{fontSize:12},
        'colors':['#5f8694'],
        'width':200,
        'height':320,
        'chartArea':{left:60, top:40, width:"66%", height:"70%"},
        'pieSliceBorderColor':'none',
        'legend':'none'
    };
    var options7 = {'title':'Bacteria level (Total: 201)',
        'titleTextStyle':{fontSize:12},
        'colors':['#058dc7', '#50b432', '#ed561b', '#edef00', '#24cbe5', '#64e572', '#ff9655', '#fff263', '#6af9c4', '#b2deff'],
        'width':300,
        'height':220,
        'legend':{position:'right', fontSize:10},
        'chartArea':{left:120, top:20, width:"100%", height:"100%"},
        'pieSliceBorderColor':'none',
        'sliceVisibilityThreshold':1 / 110,
        'legend':'none'
    };
    var options8 = {'title':'Archea level (Total: 28)',
        'titleTextStyle':{fontSize:12},
        'colors':['#058dc7', '#50b432', '#ed561b', '#edef00', '#24cbe5', '#64e572', '#ff9655', '#fff263', '#6af9c4', '#b2deff'],
        'width':300,
        'height':220,
        'legend':{position:'right', fontSize:10},
        'chartArea':{left:120, top:20, width:"100%", height:"100%"},
        'pieSliceBorderColor':'none',
        'sliceVisibilityThreshold':1 / 110,
        'legend':'none'
    };
    // Stacked column graph
    var options9 = {'title':'Phylum composition (Total: 229)',
        'titleTextStyle':{fontSize:12},
        'colors':['#058dc7', '#50b432', '#ed561b', '#edef00', '#24cbe5', '#64e572', '#ff9655', '#fff263', '#6af9c4', '#b2deff', '#ccc', '#ccc', '#ccc', '#ccc', '#ccc', '#ccc', '#ccc'],
        //Krona style               'colors':['#d47f7f','#d1a575','#d4c97f','#99d47f','#7fd4a7','#7fc3d4','#7f8ad4','#a77fd4','#d47fd3','#d47faf','#ccc','#ccc','#ccc'],
        'width':300,
        'height':400,
        'legend':{position:'right', fontSize:10},
        'chartArea':{left:60, top:40, width:"20%", height:"86%"},
        'pieSliceBorderColor':'none',
        'sliceVisibilityThreshold':1 / 50,
        'vAxis': {viewWindowMode:'maximized'},//        important to keep viewWindowMode separated from the rest to keep the display of the value 100% on vaxis
        'vAxis': {format:'#%', baselineColor: '#ccc'},
        'isStacked':true
    };

    // Instantiate and draw our chart, passing in some options.
    var chart = new google.visualization.PieChart(document.getElementById('tax_chart_div'));
    chart.draw(data, options);
    var chart2 = new google.visualization.PieChart(document.getElementById('tax_chart_div2'));
    chart2.draw(data2, options2);
    var chart5 = new google.visualization.PieChart(document.getElementById('tax_chart_div5'));
    chart5.draw(data4, options3);
    var chart3 = new google.visualization.PieChart(document.getElementById('tax_chart_div3'));
    chart3.draw(data3, options4);
    var chart4 = new google.visualization.BarChart(document.getElementById('tax_chart_div4'));
    chart4.draw(data3, options5);
    var chart6 = new google.visualization.BarChart(document.getElementById('tax_chart_div6'));
    chart6.draw(data, options6);
    var chart7 = new google.visualization.BarChart(document.getElementById('tax_chart_div7'));
    chart7.draw(data3, options7);
    var chart8 = new google.visualization.BarChart(document.getElementById('tax_chart_div8'));
    chart8.draw(data4, options8);
    var chart9 = new google.visualization.ColumnChart(document.getElementById('tax_chart_div9'));
    chart9.draw(data5, options9);
}

</script>
<script type='text/javascript'>
    google.load('visualization', '1', {packages:['table']});
    google.setOnLoadCallback(drawTable);

    function drawTable() {

        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Phylum');
        data.addColumn('string', 'Kingdom');
        data.addColumn('number', 'Hits');
        data.addColumn('number', '%');
        data.addRows([
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

        var data2 = new google.visualization.DataTable();
//        data2.addColumn('string', '');
        data2.addColumn('string', 'Phylum');
        data2.addColumn('string', 'Domain');
        data2.addColumn('number', 'Hits');
        data2.addColumn('number', '%');
        data2.addRows([
            <c:set var="addComma" value="false"/>
            <c:set var="colourCode" value="#058dc7" scope="page"/>
            <c:forEach var="taxonomyData" items="${model.taxonomyAnalysisResult.taxonomyDataSet}" varStatus="status">
            <c:choose>
            <c:when test="${addComma}">,
            </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise>
           </c:choose>
            [<%--' <ul class="color_legend"><li  style="color: #${taxonomyData.colourCode};"></li></ul>',--%> '${taxonomyData.phylum}', '${taxonomyData.superKingdom}', ${taxonomyData.numberOfHits}, ${taxonomyData.percentage}]
            </c:forEach>
        ]);

        var data2b = new google.visualization.DataTable();
        data2b.addColumn('string', 'Phylum');
        data2b.addColumn('string', 'Kingdom');
        data2b.addColumn('number', 'Hits');
        data2b.addColumn('number', '%');
        data2b.addRows([
            <c:set var="addComma" value="false"/>
            <c:set var="colourCode" value="#058dc7" scope="page"/>
            <c:forEach var="taxonomyData" items="${model.taxonomyAnalysisResult.taxonomyDataSet}" varStatus="status">
            <c:choose>
            <c:when test="${addComma}">,
            </c:when><c:otherwise><c:set var="addComma" value="true"/></c:otherwise>
            </c:choose>
            ['${taxonomyData.phylum}', '${taxonomyData.superKingdom}', ${taxonomyData.numberOfHits}, ${taxonomyData.percentage}]
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

        //Bacteria level
        var table = new google.visualization.Table(document.getElementById('tax_table_div'));
        table.draw(data, { allowHtml:true, showRowNumber:true, page:'enable', pageSize:10, pagingSymbols:{prev:'<', next:'>'}, sortColumn:2, sortAscending:false });

        //Archea level
        var table4 = new google.visualization.Table(document.getElementById('tax_table_div5'));
        table4.draw(data3, { allowHtml:true, showRowNumber:true, page:'enable', pageSize:10, pagingSymbols:{prev:'prev', next:'next'}, sortColumn:2, sortAscending:false });

        //Pie top hits table
        var table2 = new google.visualization.Table(document.getElementById('tax_table_div2'));
        table2.draw(data2, { allowHtml:true, showRowNumber:true, page:'enable', pageSize:10, pagingSymbols:{prev:'prev', next:'next'}, sortColumn:3, sortAscending:false});

        //Stacked column top hits table
        var table5 = new google.visualization.Table(document.getElementById('tax_table_div6'));
        table5.draw(data2, { allowHtml:true, showRowNumber:true, page:'enable', pageSize:10, pagingSymbols:{prev:'prev', next:'next'}, sortColumn:3, sortAscending:false});

//        var table3 = new google.visualization.Table(document.getElementById('tax_table_div3'));
//        table3.draw(data2b, { allowHtml:true, showRowNumber:true, page:'enable', pageSize:100, pagingSymbols:{prev:'prev', next:'next'}, sortColumn:2, sortAscending:false});

        //Bar top hits table
        var table6 = new google.visualization.Table(document.getElementById('tax_table_div4'));
        table6.draw(data2b, { allowHtml:true, showRowNumber:true, page:'enable', pageSize:10, pagingSymbols:{prev:'prev', next:'next'}, sortColumn:2, sortAscending:false});


    }
</script>
<%-- <script>
$(function() {
  $( "#rerun" )
    .button()
    .click(function() {
      alert( "Running the last action" );
    })
    .next()
      .button({
        text: false,
        icons: {
          primary: "ui-icon-triangle-1-s"
        }
      })
      .click(function() {
        var menu = $( this ).parent().next().show().position({
          my: "left top",
          at: "left bottom",
          of: this
        });
        $( document ).one( "click", function() {
          menu.hide();
        });
        return false;
      })
      .parent()
        .buttonset()
        .next()
          .hide()
          .menu();
});
</script>--%>
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

<div class="sidebar-allrel">

    <c:if test="${not empty model.publications}">

    <div id="sidebar-related">

        <h2>Related resources</h2>

                <span class="separator"></span>
                <ul>
                    <c:forEach var="pub" items="${model.publications}" varStatus="status">
                        <li>
                            <c:if test="${pub.pubType == 'PUBLICATION'}">
                                <a class="list_more" href="<c:url value="http://dx.doi.org/${pub.doi}"/>"><c:out
                                        value="${pub.pubTitle}"/></a><br/>
                                <i><c:out value="${pub.shortAuthors}"/></i><br/>
                                <c:out value="${pub.year}"/> <c:out value="${pub.volume}"/><br/>
                            </c:if>
                            <c:if test="${pub.pubType == 'WEBSITE_LINK'}">
                                <a class="list_more" href="<c:url value="${pub.url}"/>"><c:out
                                        value="${pub.pubTitle}"/></a>
                            </c:if>
                        </li>
                    </c:forEach>
                </ul>

        </div>
    </c:if>
</div>

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
<%--<div class="sidebar-allrel">--%>

            <%--<h3>Download results</h3>--%>

            <%--<div class="box-export">--%>
                <%--<c:if test="${not empty model.downloadSection.taxaAnalysisDownloadLinks}">--%>
                    <%--<h4>Taxonomic Analysis</h4>--%>
                    <%--<ul>--%>
                        <%--<c:forEach var="downloadLink" items="${model.downloadSection.taxaAnalysisDownloadLinks}"--%>
                                   <%--varStatus="loop">--%>
                            <%--<li>--%>
                                <%--<a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"--%>
                                   <%--title="${downloadLink.linkTitle}">--%>
                                        <%--${downloadLink.linkText}</a><span--%>
                                    <%--class="list_date"> - ${downloadLink.fileSize}</span>--%>
                            <%--</li>--%>
                        <%--</c:forEach>--%>
                    <%--</ul>--%>
                <%--</c:if>--%>
            <%--</div>--%>

<%--</div>--%>
<div class="main_tab_full_content">
      <%--<h3>Taxonomy analysis</h3>--%>
      <h4>Top taxonomy Hits (Total: 229)</h4>
        <div id="tabs-taxchart">

            <%--Tabs--%>
            <ul>
                <%--<li><a href="#tax-table" title="Table view"><span class="ico-table"></span></a></li>--%>
                <li><a href="#tax-pie" title="Pie chart view"><span class="ico-pie"></span></a></li>
                <li><a href="#tax-bar" title="Bar chart view"><span class="ico-barh"></span></a></li>
                <li><a href="#tax-col" title="Stacked column chart view"><span class="ico-col"></span></a></li>
                <li><a href="#tax-Krona" title="Krona interactive chart view"><span class="ico-krona"></span></a></li>
                <li class="ico-downl"><a class="icon icon-functional" data-icon="=" href="#download" title="Download image/table"></a></li>
            </ul>

                <%--<div id="tax-table">--%>
                <%--<div id="tax_table_div3"></div>--%>
                <%--</div>--%>


                <%--Taxonomy google chart--%>
                <div id="tax-pie">
                        <div class="chart_container">
                            <div class="chart_container"><div id="tax_chart_div"></div><div id="tax_chart_div3"></div><div id="tax_table_div2"></div>
                                <div style="float:right;margin-top: -31px;margin-right: 20px;"><form action="">
                                                                 Show rows:
                                                                 <select onChange="setOption('pageSize', parseInt(this.value, 10))">
                                                                   <option selected=selected value="10">10</option>
                                                                   <option value="50">50</option>
                                                                   <option value="100">100</option>
                                                                   <option value="1000">1000</option>
                                                                   <option value="all">all</option>
                                                                 </select>
                                                               </form>
                                                             </div>
                              </div>
                            <p><br/></p>
                            <%--<h4>Top taxonomy levels</h4>--%>
                            <%--Bacteria level--%>
                            <div id="tax_chart_div2" style="display:none;"></div><div id="tax_table_div" style="display:none;"></div>
                           <%--Archea level--%>
                           <div id="tax_chart_div5" style="display:none;"></div> <div id="tax_table_div5" style="display:none;"></div>
                        </div>

                </div>

                <div id="tax-bar">
                    <div class="chart_container"><div id="tax_chart_div6"></div><div id="tax_chart_div4"></div> <div id="tax_table_div4"></div></div>
                    <%--<h4>Top taxonomy levels</h4>--%>
                    <div class="chart_container"><div id="tax_chart_div7" style="display:none;"></div><div id="tax_chart_div8" style="display:none;"></div></div>
                </div>

                <div id="tax-col">
                  <div class="chart_container"><div id="tax_chart_div9"></div><div id="tax_table_div6"></div>
                  </div>




                 </div>
                  <div id="tax-Krona">
                      <table class="result">
                          <tr>

                              <td >
                                  <object class="krona_chart" data="<c:url value="${baseURL}/sample/${model.sample.sampleId}/krona?taxonomy=true&font=10"/>" type="text/html"/>
                              </td>
                          </tr>
                          <%--<table class="result">--%>
                          <%--<tr>--%>
                            <%--<td width="10%" style="background-color:white;padding:0;">--%>
                                <%--&lt;%&ndash;Taxonomy google chart&ndash;%&gt;--%>

                                <%--<object class="krona_chart_small" data="<c:url value="${baseURL}/sample/${model.sample.sampleId}/krona?taxonomy=true&slim=true&depth=1&font=11"/>" type="text/html"/>--%>
                            <%--</td>--%>
                            <%--<td rowspan="2" style="background-color:white;padding:0;">--%>
                                <%--<object class="krona_chart" data="<c:url value="${baseURL}/sample/${model.sample.sampleId}/krona?taxonomy=true&font=10"/>" type="text/html"/>--%>
                            <%--</td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<td style="background-color:white;padding:0;"><object class="krona_chart_small" data="<c:url value="${baseURL}/sample/${model.sample.sampleId}/krona?taxonomy=true&slim=true&node=1&depth=2&font=11"/>" type="text/html"/></td>--%>
                        <%--</tr>--%>
                      </table>
                  </div>


       </div>
  </div>
</div>

<div id="fragment-functional">

        <%--<div class="sidebar-allrel">--%>

            <%--<h3>Download results</h3>--%>

            <%--<div class="box-export">--%>

                <%--<c:if test="${not empty model.downloadSection.funcAnalysisDownloadLinks}">--%>
                    <%--<h4>Functional Analysis</h4>--%>
                    <%--<ul>--%>
                        <%--<c:forEach var="downloadLink" items="${model.downloadSection.funcAnalysisDownloadLinks}"--%>
                                   <%--varStatus="loop">--%>
                            <%--<li>--%>
                                <%--<a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"--%>
                                   <%--title="${downloadLink.linkTitle}">--%>
                                        <%--${downloadLink.linkText}</a><span--%>
                                    <%--class="list_date"> - ${downloadLink.fileSize}</span>--%>
                         <%--&lt;%&ndash;to rename Complete Go Annotatio&ndash;%&gt;--%>
                      <%--</li>--%>
                  <%--</c:forEach>--%>
                  <%--<c:if test="${not empty model.pieChartBiologicalProcessURL}">--%>
                  <%--<li>--%>

                    <%--<a id="csv"--%>
                       <%--title="<spring:message code="analysisStatsView.label.download.go.slim.anchor.title"/>"--%>
                       <%--href="<c:url--%>
                        <%--value="${baseURL}/sample/${model.sample.sampleId}/doExportGOSlimFile"/>">--%>
                        <%--<spring:message--%>
                                <%--code="analysisStatsView.label.download.go.slim.anchor.href.message"/></a>--%>

                  <%--</li></c:if>--%>

              <%--</ul>--%>
          <%--</c:if>--%>

      <%--</div>--%>
      <%--</div>--%>

      <div class="main_tab_full_content">
          <%--<div id="small"> <div class="export">--%>
                                <%--<a id="csv"--%>
                                   <%--href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/doExportIPRFile"/>"--%>
                                   <%--title="<spring:message code="analysisStatsView.label.download.i5.table.view"/>">--%>
                                    <%--<spring:message code="analysisStatsView.label.download.i5.table.view"/>&lt;%&ndash; <c:out--%>
                                <%--value="${model.emgFile.fileSizeMap['_summary.ipr']}"/>&ndash;%&gt;--%>
                                <%--</a>--%>
                            <%--</div>--%>
                            <%--</div>--%>

      <%--<h3>Function analysis</h3>--%>

              <%--<p>The entire InterProScan results file (<a title="Click to download full InterPro matches table (TSV)"--%>
                                                          <%--href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/doExportI5TSVFile"/>">download--%>
                  <%--here</a>) has been used to produce the following summaries.</p>--%>

              <h4>InterPro match summary</h4>

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
                 <%--<li><a href="#interpro-match-pie" title="Pie chart view"><span class="ico-pie"></span></a></li>--%>
                 <%--<li><a href="#interpro-match-bar" title="Bar chart view"><span class="ico-barh"></span></a></li>--%>
                 <%--<li><a href="#interpro-match-col" title="Stacked column chart view"><span class="ico-col"></span></a></li>--%>
                 <%--<li><a href="#interpro-match-Krona" title="Krona interactive chart view"><span class="ico-krona"></span></a></li>--%>

             </ul>
             <%--<div class="ico-download"><a class="icon icon-functional" data-icon="=" id="csv" href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/doExportIPRFile"/>"   title="<spring:message code="analysisStatsView.label.download.i5.table.view"/>"></a></div>--%>
             <div id="interpro-match-table">
                 <table border="1" class="result">
                 <thead>
                 <tr>
                     <th scope="col" abbr="IEname" id="h_left">Entry name</th>
                     <th scope="col" abbr="IEid" width="90px">ID</th>
                     <th scope="col" abbr="IEnum" width="130px">Proteins matched</th>
                 </tr>
                 </thead>
                 <tbody>
                 <c:forEach var="entry" items="${model.interProEntries}" varStatus="status">
                     <tr>

                         <td style="text-align:left;">
                            <a href="http://www.ebi.ac.uk/interpro/entry/${entry.entryID}"
                                                       title="<c:out value="Link to ${entry.entryID}"/>" class="ext">${entry.entryDescription}</a></td>
                         <td>
                            <%--<c:url var="linkToInterProSearch" value="http://www.ebi.ac.uk/interpro/search">--%>
                                <%--<c:param name="q" value="${entry.entryID}"/>--%>
                            <%--</c:url>--%>

                                <c:out value="${entry.entryID}"/>

                        </td>
                         <td id="ordered">${entry.numOfEntryHits}</td>
                     </tr>
                 </c:forEach>
                           <tr><td colspan="3" class="showHideRelated" ><c:set var="showFullTableID" value="View full table"/>
                           <a title="<c:out value="${showFullTableID}"/>"
                                                       href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/showProteinMatches"/>">
                                                     <c:out value="${showFullTableID}"/></a></td></tr>
                           </tbody>
                       </table>
             </div>
          </div>



      </c:when>
      <c:otherwise>
          <b><c:out value="${noDisplayID}"/></b>
      </c:otherwise>
    </c:choose>

    <h4>GO Terms annotation</h4>
    <p>A summary of Gene Ontology (GO) terms derived from InterPro matches to your sample is provided in the charts below.</p>

    <div id="tabs-chart">

        <%--Tabs--%>
    <ul>
        <%--<li><a href="#go-terms-table" title="Table view"><span class="ico-table"></span></a></li>--%>
        <%--<li><a href="#go-terms-pie" title="Pie chart view"><span class="ico-pie"></span></a></li>--%>
        <%--<li><a href="#go-terms-bar" title="Bar chart view"><span class="ico-barh"></span></a></li>--%>
        <%--<li><a href="#go-terms-col" title="Stacked column chart view"><span class="ico-col"></span></a></li>--%>
        <%--<li><a href="#go-terms-Krona" title="Krona interactive chart view"><span class="ico-krona"></span></a></li>--%>
        <%--<li class="ico-downl"><a class="icon icon-functional" data-icon="=" href="#download" title="Download image/table"></a></li>--%>
    </ul>




    <div id="go-terms-bar">


        <div class="go-chart">
          <div class="go_rotate">
              <h2>Biological process</h2>
              <c:url var="bioImage" value="/getImage" scope="request">
                  <c:param name="imageName" value="_summary_biological_process.png"/>
                  <c:param name="imageType" value="PNG"/>
                  <c:param name="dir" value="${model.emgFile.fileID}"/>
              </c:url>
              <img src="<c:out value="${bioImage}"/>"/>  <br/>
                  <%--<b><c:out value="${noDisplayID}"/></b>--%>
          </div>

          <div class="go_rotate">
              <h2>Molecular function</h2>
              <c:url var="molecularImage" value="/getImage" scope="request">
                  <c:param name="imageName" value="_summary_molecular_function.png"/>
                  <c:param name="imageType" value="PNG"/>
                  <c:param name="dir" value="${model.emgFile.fileID}"/>
              </c:url>
             <img src="<c:out value="${molecularImage}"/>"/>  <br/>
          </div>

          <div class="go_rotate">
              <h2>Cellular component</h2>
              <c:url var="cellImage" value="/getImage" scope="request">
                  <c:param name="imageName" value="_summary_cellular_component.png"/>
                  <c:param name="imageType" value="PNG"/>
                  <c:param name="dir" value="${model.emgFile.fileID}"/>
              </c:url>
              <img src="<c:out value="${cellImage}"/>"/> <br/>
          </div>
        </div>

        </div>


    <%--<div id="go-terms-Krona">--%>
    <%--<table class="result">--%>
    <%--<tr>--%>
    <%--<td width="10%" style="background-color:white;padding:0;vertical-align: top;"><object class="krona_chart_small" style="height:323px;" data="<c:url value="${baseURL}/sample/${model.sample.sampleId}/krona?function=true&slim=true&depth=1&font=11"/>" type="text/html"></object></td>--%>
    <%--<td style="background-color:white;padding:0;">--%>
    <%--&lt;%&ndash;<a href="http://localhost:8082/metagenomics/Krona_chart_function"  class="icon icon-functional" data-icon="F" title="Open full screen" style="float:right; margin: 10px 4px 0 0; font-size: 283%;"></a>&ndash;%&gt;--%>
    <%--<object class="krona_chart" data="<c:url value="${baseURL}/sample/${model.sample.sampleId}/krona?function=true&font=10"/>" type="text/html"></object></td>--%>
    <%--</tr>--%>

    <%--</table>--%>
    <%--</div>--%>

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
    <%--<div class="output_form" id="large">--%>
        <%--<div class="result_row"><label>Raw sequence reads:</label>--%>
      <%--<span>--%>
         <%--<c:choose>--%>
             <%--<c:when test="${not empty model.archivedSequences}">--%>
                 <%--<c:forEach var="seqId" items="${model.archivedSequences}" varStatus="status">--%>
                     <%--<a class="ext" href="<c:url value="https://www.ebi.ac.uk/ena/data/view/${seqId}"/>">--%>
                         <%--<c:out value="${seqId}"/></a>--%>
                 <%--</c:forEach> (ENA website)--%>
             <%--</c:when>--%>
             <%--<c:otherwise>(not given)</c:otherwise>--%>
         <%--</c:choose></span></div>--%>
    <%--</div>--%>
        <%--END READS SECTION   --%>

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
    $( "#navtabs").tabs({ disabled: [5] });
    $( "#navtabs").tabs({ selected: [0] });
    $( "#interpro-chart" ).tabs({ disabled: [1,2,3,4], selected: 0 });
//    $( "#tabs-chart" ).tabs({ disabled: [0,1,3,5], selected: 2 });
    $( "#tabs-taxchart" ).tabs({ disabled: [5], selected: 0 });
</script>



</c:when>
<c:otherwise>
    <h3>Sample ID Not Recognised</h3>
</c:otherwise>
</c:choose>

</div>  <%--end sample_ana--%>
<div class="but_top"><a href="#top" title="back to the top page">Top</a></div>
