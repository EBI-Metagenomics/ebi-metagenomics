<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script src='${pageContext.request.contextPath}/js/highcharts-4.0.3/highcharts.src.js' type='text/javascript'></script>
<script src='${pageContext.request.contextPath}/js/highcharts-4.0.3/highcharts-more.js' type='text/javascript'></script>
<script src='${pageContext.request.contextPath}/js/highcharts-4.0.3/exporting.src.js' type='text/javascript'></script>
<%--DataTables 1.10.0--%>
<script src="${pageContext.request.contextPath}/js/jquery.dataTables.js"></script>

<div class="back_ban"><div class="back_button"><a href="<c:url value="${baseURL}/compare"/>"><span>Back to query page</span></a></div>
    <div class="back_title">Sample comparison tool:

        <c:if test="${data=='GO'}">
            Complete GO annotation (functional analysis)
        </c:if>
        <c:if test="${data=='GOslim'}">
            GO term annotation (functional analysis)   <%--hide "GO slim" notion as we do on the analysis result page --%>
        </c:if>
    </div>
</div>
<ul><li>Runs:
<c:forEach var="analysisJob" items="${analysisJobs}">
<a class="sample_list" href="<c:url value="${baseURL}/projects/${study.studyId}/samples/${analysisJob.sample.sampleId}/runs/${analysisJob.externalRunIDs}/results/versions/${analysisJob.pipelineRelease.releaseVersion}"/>" title="${analysisJob.sample.sampleName} (${analysisJob.externalRunIDs})">${analysisJob.externalRunIDs}</a>
</c:forEach>
    <%-- Uncomment these lines if you plan to handle the 'file is empty' error by showing missing samples on the result page
    <%--<c:if test="${not empty missingSamples}">--%>
    <%--<br>No data was available for sample(s): <c:forEach var="sample" items="${missingSamples}">--%>
        <%--<a class="sample_list" href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>" target="_blank" title="${sample.sampleName} (${sample.sampleId})">${sample.sampleId}</a>--%>
    <%--</c:forEach>--%>
    <%--</c:if>--%>
    </li>
    <li> Project: <a href="<c:url value="${baseURL}/projects/${study.studyId}"/>" title="${study.studyName} (${study.studyId})">${study.studyName}</a> (${study.studyId})</li>
</ul>
<%--<p>Use tabs below to switch between available visualizations.</p>--%>

<%-- jQuery UI tabs  --%>
<div class="sample_comp_result">
<div id="tabs">
    <ul>
        <%--li><a href="#overview">Overview</a></li--%>
        <li><a href="#bars" title="view results as bar chart"><span class="ico-barh"></span> <span class="tab-text">Barcharts</span></a></li>
        <li><a href="#stack" title="view results as stacked column"><span class="ico-col"></span> <span class="tab-text">Stacked columns</span></a></li>
        <li><a href="#heatmap" title="view results as heatmap"><span class="ico-heatmap"></span><span class="tab-text">Heatmap</span></a></li>
        <li><a href="#pca" title="view results as principal components analysis"><span class="ico-pca"></span><span class="tab-text">Principal Component Analysis</span></a></li>
        <li><a href="#jstable" title="view results in a table"><span class="ico-table"></span><span class="tab-text">Table</span></a></li>
    </ul>
    <%-- Tabs content --%>
    <%--div id="overview">
        <p>${graphCode[0]}</p>
    </div--%>
    <div id="bars">
        <div id="sticky-leg-anchor"></div>
       <div id="barcharts_legend">
           <div id="export_div_bars" class="chart-but-export">
             <select id="bars_export" title="Export" class="export-select">
                 <option selected>Export</option>
                 <optgroup label="Biological process">
                     <option value="bp_png">PNG</option>
                     <option value="bp_pdf">PDF</option>
                     <option value="bp_svg">SVG</option>
                 </optgroup>
                 <optgroup label="Molecular function">
                     <option value="mf_png">PNG</option>
                     <option value="mf_pdf">PDF</option>
                     <option value="mf_svg">SVG</option>
                 </optgroup>
                 <optgroup label="Cellular component">
                     <option value="cc_png">PNG</option>
                     <option value="cc_pdf">PDF</option>
                     <option value="cc_svg">SVG</option>
                 </optgroup>
             </select>
             </div>

       <div id="barcharts_legend_title"><strong>Run list</strong> <span class="barcharts_legend_info">(click to hide)</span></div>
       <div id="barcharts_legend_list_items"></div>
<%--${sampleString}--%>
        </div>

        <%-- THIS FEATURE IS STILL EXPERIMENTAL.
             Allows user to redraw the stacked columns visualization with changed settings.
             See Confluence page of the tool for more details on how to enable it --%>
        <%--<div class="redraw_settings">Change threshold: <input id="stackThreshold" type="number" min="0" max="90" step="0.1" value="2" style="width:60px;"/> <button id="redrawStackButton">Redraw chart</button></div>--%>
        <div id="bars-wrapper">${graphCode[1]}</div>
    </div>
    <div id="stack">

           <div id="stack-jump-anchor"></div>
           <div id="stack_jump">
           <div id="export_stack_div" class="chart-but-export">
                      <select id="stack_export" title="Export" class="export-select">
                          <option selected>Export</option>
                          <optgroup label="Biological process">
                              <option value="bp_png">PNG</option>
                              <option value="bp_pdf">PDF</option>
                              <option value="bp_svg">SVG</option>
                          </optgroup>
                          <optgroup label="Molecular function">
                              <option value="mf_png">PNG</option>
                              <option value="mf_pdf">PDF</option>
                              <option value="mf_svg">SVG</option>
                          </optgroup>
                          <optgroup label="Cellular component">
                              <option value="cc_png">PNG</option>
                              <option value="cc_pdf">PDF</option>
                              <option value="cc_svg">SVG</option>
                          </optgroup>
                      </select>
                  </div>

               <div class="jump_anchor_text">Jump to: <a href="#stack_bio_title"> Biological process</a> | <a href="#stack_mol_title">Molecular function</a> | <a href="#stack_cell_title">Cellular component</a></div>



           </div>

            <div id="stack_wrapper">${graphCode[2]}</div>
    </div>
    <div id="heatmap">
   <div id="hm-jump-anchor"></div>
   <div id="hm_jump">

       <%--no export working for heatmap - we could use absolute link--%>
       <%--<div id="export_heatmap_div" class="chart-but-export">--%>
             <%--<select id="heatmap_export"  title="Export">--%>
             <%--<option selected="">Export</option>--%>
             <%--<optgroup label="Biological process">--%>
             <%--<option value="/metagenomics/tmp/comparison/bio_GOslim_1406639941340.svg">SVG</option>--%>
             <%--</optgroup>--%>
             <%--<optgroup label="Molecular function">--%>
             <%--<option value="/metagenomics/tmp/comparison/mol_GOslim_1406639941340.svg">SVG</option>--%>
             <%--</optgroup>--%>
             <%--<optgroup label="Cellular component">--%>
             <%--<option value="/metagenomics/tmp/comparison/cell_GOslim_1406639941340.svg">SVG</option>--%>
             <%--</optgroup>--%>
             <%--</select>--%>
             <%--</div>--%>

       <div class="jump_anchor_text">Jump to GO category: <a href="#hm_bio_title"> Biological process</a> | <a href="#hm_mol_title">Molecular function</a> | <a href="#hm_cell_title">Cellular component</a></div>

       </div>

       <%--<h3> Biological process</h3>--%>
        ${graphCode[3]}
    </div>
    <div id="pca">

        <div id="pca-leg-anchor"></div>
        <div id="pca_legend">
            <div id="export_pca_div" class="chart-but-export">

                    <select id="pca_export" title="Export" class="export-select">
                        <option selected>Export</option>
                        <optgroup label="Biological process">
                            <option value="bp_png">PNG</option>
                            <option value="bp_pdf">PDF</option>
                            <option value="bp_svg">SVG</option>
                        </optgroup>
                        <optgroup label="Molecular function">
                            <option value="mf_png">PNG</option>
                            <option value="mf_pdf">PDF</option>
                            <option value="mf_svg">SVG</option>
                        </optgroup>
                        <optgroup label="Cellular component">
                            <option value="cc_png">PNG</option>
                            <option value="cc_pdf">PDF</option>
                            <option value="cc_svg">SVG</option>
                        </optgroup>
                    </select>
            </div>
        <div id="pca_legend_title"><strong>Run list</strong> <span class="pca_legend_info">(mouse over to highlight and click to hide)</span></div>
        <div id="pca_legend_list_items"></div>

        </div>
        <%--Principal Component 1 <input id="pc1" type="number" name="Principal Component 1" min="1" max="3" value="1" disabled>
        Principal Component 2 <input id="pc2" type="number" name="Principal Component 2" min="1" max="3" value="2" disabled> <br>--%>
        <div id="plots-wrapper">${graphCode[4]}</div>
    </div>
    <div id="jstable">
        ${graphCode[5]}
    </div>
</div>
</div>

<script type="text/javascript">
     // to remove the last comma in the sample list
    $(document).ready(function() {
    $(' a.sample_list:not(:last-child)').each(function () {
                        $(this).append(',');
                    });
    });
</script>

<script type="text/javascript">
    // Enable selection of rows in the table visualization
    $(document).ready(function() {
        $('#table tbody').on( 'click', 'tr', function () {
            if ( $(this).hasClass('selected') ) {
                $(this).removeClass('selected');
            }
            else {
                $('#table tr.selected').removeClass('selected');
                $(this).addClass('selected');
            }});
        });

    $( "#bars_export" ).selectmenu({
      change: function( event, ui ) { {
                  var exportValue = $( "#bars_export" ).val();
            // exportArray: array containing information on GO type and wanted output format based on the value of the selected option
                  var exportArray = exportValue.split('_');
                  var goType = "";
                  var format = "";
                  switch(exportArray[0]) {
                      case "bp":
                          goType = "biological_process";
                          break;
                      case "mf":
                          goType = "molecular_function";
                          break;
                      case "cc":
                          goType = "cellular_component";
                          break;
                  }
            // if no valid export option is selected exportArray[1] should be null and export won't be performed
                  if(exportArray[1]!=null) {
                      switch(exportArray[1]) {
                          case "png":
                              format = "image/png";
                              break;
                          case "pdf":
                              format = "application/pdf";
                              break;
                          case "svg":
                              format = "image/svg+xml";
                              break;
                      }

                var chartId = "#" + goType + "_bars"; // Rebuilds chart div id from retrieved GO type

                // Creation of a unique file name
                      var date = new Date();
                      var mmddyyyyDate = (date.getMonth()+1).toString() +'-'+ date.getDate() +'-'+ date.getFullYear();
                      var fileName = "${study.studyId}"+"_"+mmddyyyyDate+"_comp_func_go"+"_barcharts_"+exportArray[0];
                      var chart = $(chartId).highcharts();
                      chart.redraw();
                      chart.exportChart({
                          type: format,
                          filename: fileName
                      });
                  }
                  $('#bars_export').val('Export');
              }

      }
    });

    // Stacked columns export module
    $('#stack_export').selectmenu({
        change:function( event, ui ) { {
        var exportValue = $( "#stack_export" ).val();
        // exportArray: array containing information on GO type and wanted output format based on the value of the selected option
        var exportArray = exportValue.split('_');
        var goType = "";
        var format = "";

        switch(exportArray[0]) {
            case "bp":
                goType = "biological_process";
                break;
            case "mf":
                goType = "molecular_function";
                break;
            case "cc":
                goType = "cellular_component";
                break;
        }
        // if no valid export option is selected exportArray[1] should be null
        if(exportArray[1]!=null) {
            switch(exportArray[1]) {
                case "png":
                    format = "image/png";
                    break;
                case "pdf":
                    format = "application/pdf";
                    break;
                case "svg":
                    format = "image/svg+xml";
                    break;
            }
            var chartId = "#" + "stack_" + goType; // Rebuilds chart div id from retrieved GO type

            // Creation of a unique file name
            var date = new Date();
            var mmddyyyyDate = (date.getMonth()+1).toString() +'-'+ date.getDate() +'-'+ date.getFullYear();
            var fileName = "${study.studyId}"+"_"+mmddyyyyDate+"_comp_func_go_col_"+exportArray[0];
            var chart = $(chartId).highcharts();
            chart.redraw();
            chart.exportChart({
                type: format,
                filename: fileName
            });
        }
        $('#stack_export').val('Export');
        }

          }
        });

    // PCA export module
    $('#pca_export').selectmenu({
            change:function( event, ui ) { {
        var exportValue = $( "#pca_export" ).val();
        // exportArray: array containing information on GO type and wanted output format based on the value of the selected option
        var exportArray = exportValue.split('_');
        var goType = "";
        var format = "";
        switch(exportArray[0]) {
            case "bp":
                goType = "biological_process";
                break;
            case "mf":
                goType = "molecular_function";
                break;
            case "cc":
                goType = "cellular_component";
                break;
        }
        // if no valid export option is selected exportArray[1] should be null
        if(exportArray[1]!=null) {
            switch(exportArray[1]) {
                case "png":
                    format = "image/png";
                    break;
                case "pdf":
                    format = "application/pdf";
                    break;
                case "svg":
                    format = "image/svg+xml";
                    break;
            }
            var chartId = "#" + goType + "_pca_12"; // Rebuilds chart div id from retrieved GO type. It may be good to find a way to see which PCs are drawn (not hard coded)

            // Creation of a unique file name
            var date = new Date();
            var mmddyyyyDate = (date.getMonth()+1).toString() +'-'+ date.getDate() +'-'+ date.getFullYear();
            var fileName = "${study.studyId}"+"_"+mmddyyyyDate+"_comp_func_go"+"_PCA_"+exportArray[0];
            var chart = $(chartId).highcharts();
            chart.redraw();
            chart.exportChart({
                type: format,
                filename: fileName
            });
        }
        $('#pca_export').val('Export');
            }

                 }
               });

    /*
    Sticky divs functions ('Jump to...' and single legends for barcharts / PCA visualizations.
    All these functions are working in a similar way (only div ids change)
     */

    // Sticky div relocation function to let the barcharts legend stay on top
    function sticky_bar_relocate() {
        var window_top = $(window).scrollTop(); // Position of 'top of window'
        var barcharts_leg_top = $('#sticky-leg-anchor').offset().top; // Position of top of div
        // If the div top position is lower than the window top position (which means that the div is visually 'higher'), stick it!
        if (window_top > barcharts_leg_top) {
            $('#barcharts_legend').addClass('stick');
            // Quick fix to make the div-width equal to its tab width
            $("#barcharts_legend").css("width", $("#bars").width());
        } else {
            $('#barcharts_legend').removeClass('stick');
        }
    }

    // Triggers barcharts legend div relocation function on scroll event
    $(function() {
        $(window).scroll(sticky_bar_relocate);
        sticky_bar_relocate();
    });

    // Sticky div relocation function to let the pca legend stay on top
    function sticky_pca_relocate() {
        var window_top = $(window).scrollTop();
        var pca_leg_top = $('#pca-leg-anchor').offset().top;
        if (window_top > pca_leg_top) {
            $('#pca_legend').addClass('stick');
            // Quick fix to make the div-width equal to its tab width
            $("#pca_legend").css("width", $("#pca").width());
        } else {
            $('#pca_legend').removeClass('stick');
        }
    }

    // Triggers pca legend div relocation function on scroll event
    $(function() {
        $(window).scroll(sticky_pca_relocate);
        sticky_pca_relocate();
    });

    // Sticky div relocation function to let the heatmaps 'Jump to...' div stay on top
    function sticky_hm_relocate() {
        var window_top = $(window).scrollTop();
        var hm_jump_top = $('#hm-jump-anchor').offset().top;
        if (window_top > hm_jump_top) {
            $('#hm_jump').addClass('stick');
            // Quick fix to make the div-width equal to its tab width
            $("#hm_jump").css("width", $("#heatmap").width());
        } else {
            $('#hm_jump').removeClass('stick');
        }
    }

    // Triggers heatmaps 'Jump to...' div relocation function on scroll event
    $(function() {
        $(window).scroll(sticky_hm_relocate);
        sticky_hm_relocate();
    });

    // Sticky div relocation function to let the stacked columns 'Jump to...' div stay on top
    function sticky_stack_relocate() {
        var window_top = $(window).scrollTop();
        var stack_jump_top = $('#stack-jump-anchor').offset().top;
        if (window_top > stack_jump_top) {
            $('#stack_jump').addClass('stick');
            // Quick fix to make the div-width equal to its tab width
            $("#stack_jump").css("width", $("#stack").width());
        } else {
            $('#stack_jump').removeClass('stick');
        }
    }

    // Triggers stacked columns 'Jump to...' div relocation function on scroll event
    $(function() {
        $(window).scroll(sticky_stack_relocate);
        sticky_stack_relocate();
    });
</script>

<script type="text/javascript">
    // Creation of legend items for barcharts visualization
    $(document).ready(function() {
        // Creation of legend items only if the charts exist (if the barchart for biological process exists others should exist as well)
        if($('#biological_process_bars').length != 0) {
            var sampleNum = "${fn:length(analysisJobs)}"; // Retrieving number of samples
            // Retrieving charts and charts series as JS vars
            var bioChart = $('#biological_process_bars').highcharts();
            var molChart = $('#molecular_function_bars').highcharts();
            var cellChart = $('#cellular_component_bars').highcharts();
            var bioChartSeries = bioChart.series;
            var molChartSeries = molChart.series;
            var cellChartSeries = cellChart.series;
            // Creation of the legend items for each sample
            for (var i = 0; i < sampleNum; i++)
            {
//                var currentColor = cellChartSeries[i].color;
                var currentColor = bioChartSeries[i].color;
                var currentName =  bioChartSeries[i].name;
                $('<div/>', {
                    'id': 'legend_' + i,
                    'class': 'legend-item',
                    'html': '<div class="legend-rectangle" style="background:'+ currentColor + ';"></div><span> '+currentName+'</span>',
                    'click': function () {
                        var legInd = this.id.split('_')[1]; // Gives the series index to hide / show when clicking on the legend item
                        // Check if element is visible on biological process chart but could be whatever we want
                        if(bioChartSeries[legInd].visible){
                            bioChartSeries[legInd].hide();
                            molChartSeries[legInd].hide();
                            cellChartSeries[legInd].hide();
                            // Changing color to disabled grey for text and icon
                            $(this).toggleClass('legend-item-off');
                            $(this).children('.legend-rectangle').css('background', '#D1D1D1');//have to keep that to force the color
                        }
                        else {
                            bioChartSeries[legInd].show();
                            molChartSeries[legInd].show();
                            cellChartSeries[legInd].show();
                            // Changing color back to normal display
                            $(this).toggleClass('legend-item-off');
                            $(this).children('.legend-rectangle').css('background', cellChartSeries[legInd].color);
                        }
                    } // Adding legend item to legend div
                }).appendTo('#barcharts_legend_list_items'); //where the color puce are
            }
        }
        else {
            // No barcharts? Empty the legend and disable export so the user is not confused
            $( "#barcharts_legend" ).empty();
            // $("#bars_export").disable(); // Trying to hide the export button when nothing is displayed but 'disable()' does not seem to work.
        }
    });

    // Creation of legend items for PCA visualization
    // This function is similar to the previous one, the only difference being the 'mouseenter' and 'mouseleave' functions.
    $(document).ready(function() {
        if($('#biological_process_pca_12').length != 0) {
            var sampleNum = "${fn:length(analysisJobs)}";
            var bioChart = $('#biological_process_pca_12').highcharts();
            var molChart = $('#molecular_function_pca_12').highcharts();
            var cellChart = $('#cellular_component_pca_12').highcharts();
            var bioChartSeries = bioChart.series;
            var molChartSeries = molChart.series;
            var cellChartSeries = cellChart.series;
            // These two lines are really ugly but allow to correct the 'moving axis' display bug
            bioChartSeries[0].hide();
            bioChartSeries[0].show();
            for (var i = 0; i < sampleNum; i++)
            {
                var currentColor = bioChartSeries[i].color;
                var currentName =  bioChartSeries[i].name;
                $('<div/>', {
                    'id': 'pca_legend_'+i,
                    'class': 'legend-item',
                    'html': '<div class="legend-rectangle" style="background:'+ currentColor + ';"></div><span> '+currentName+'</span>',
                    'click': function () {
                        var legInd = this.id.split('_')[2];
                        // Set to biological process chart but could be whatever we want
                        if(bioChartSeries[legInd].visible){ // Gives the indice of the series to hide / show when clicking on the legend item
                            bioChartSeries[legInd].hide();
                            molChartSeries[legInd].hide();
                            cellChartSeries[legInd].hide();
                            // Changing color to grey (for the rectangle too!)
                            $(this).toggleClass('legend-item-off');
                            $(this).children('.legend-rectangle').css('background', '#D1D1D1');
                        }
                        else {
                            bioChartSeries[legInd].show();
                            molChartSeries[legInd].show();
                            cellChartSeries[legInd].show();
                            // Changing color to normal display (could be cleaner?)
                            $(this).toggleClass('legend-item-off');
                            $(this).children('.legend-rectangle').css('background', cellChartSeries[legInd].color);
                        }
                    },
                    'mouseenter': function () {
                        var legInd = this.id.split('_')[2];
                        // Highlights PCA chart points when mousing over
                        // if(bioChartSeries[legInd].visible){
                        bioChartSeries[legInd].data[0].setState('hover');
                        molChartSeries[legInd].data[0].setState('hover');
                        cellChartSeries[legInd].data[0].setState('hover');
                        // }
                    },
                    'mouseleave': function () {
                        var legInd = this.id.split('_')[2];
                        // PCA points back to normal state when mousing over
                        bioChartSeries[legInd].data[0].setState();
                        molChartSeries[legInd].data[0].setState();
                        cellChartSeries[legInd].data[0].setState();
                    }
                }).appendTo('#pca_legend_list_items');
            }
        }
        else {
            // No PCA? Empty the legend and disable export so the user is not confused
            $( "#pca_legend" ).empty();
            // $("#bars_export").disable(); // Trying to hide the export button when nothing is displayed but 'disable()' does not seem to work.
        }
    });
</script>

<script type="text/javascript" defer="defer">
    // Enable jQuery tabs
   $('#tabs').tabs();
   // Triggering the resize event after tab loading to debug display of charts (blurry charts on firefox)
   $(window).trigger('resize');

    // Correcting display error of highcharts charts when working with jQuery tabs
    // Because of the 'display:none' style applied on hidden tabs, highcharts cannot render the charts properly.
    $('#tabs').on('tabsactivate', function (event, ui) {
        $('.rChart').each(function() {
        if($(this).is(':visible')) {
        var chartContainerName = this.id;
        $('#'+chartContainerName).highcharts().reflow();
        }
    });
    $(window).trigger('resize');
     });
</script>

<%-- THIS FEATURE IS STILL EXPERIMENTAL.
     Sends reauest when user redraw the stacked columns.
     See Confluence page of the tool for more details on how to enable it --%>
<%--<script type="text/javascript" defer="defer">--%>
    <%--$(document).ready(function () {--%>
        <%--$('#redrawStackButton').click(function() {--%>

            <%--var outputName = "${outputName}";--%>
            <%--var newThreshold = $('#stackThreshold').val();--%>
            <%--$.ajax({--%>
                <%--url:"<c:url value="${baseURL}/compare/stack"/>",--%>
                <%--type:"GET",--%>
                <%--cache:false,--%>
                <%--data:{outputName:outputName,--%>
                <%--newThreshold:newThreshold},--%>
                <%--success:function (data) {--%>
                    <%--$("#stack_wrapper").html(data);--%>
                <%--},--%>
                <%--error:function (jqXHR, textStatus, errorThrown) {--%>
                    <%--alert("Request failed: " + textStatus);--%>
                <%--}--%>
            <%--});//end ajax method--%>
        <%--});--%>
    <%--});--%>
<%--</script>--%>
<%--<script type="text/javascript">--%>
    <%--$( "#tabss, #tabst, #tabs" ).tabs();--%>

<%--</script>--%>