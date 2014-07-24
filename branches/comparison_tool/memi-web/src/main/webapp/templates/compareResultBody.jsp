<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/comparison-style.css" type="text/css" media="all"/>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script src='http://code.highcharts.com/highcharts.src.js' type='text/javascript'></script>
<script src='http://code.highcharts.com/highcharts-more.js' type='text/javascript'></script>
<script src='http://code.highcharts.com/modules/exporting.src.js' type='text/javascript'></script>

<style>


</style>

<div class="back_ban"><div class="back_button"><a href="<c:url value="${baseURL}/compare"/>"><span>Back to query page</span></a></div>
    <div class="back_title">Sample comparison tool:

        <%--c:if test="${data==GOslim}">
            InterPro matches
        </c:if--%>
        <c:if test="${data=='GO'}">
            Complete GO annotation (functional analysis)
        </c:if>
        <c:if test="${data=='GOslim'}">
            GO term annotation (functional analysis)   <%--hide GO slim notion as we do on the analysis result page --%>
        </c:if>
</div>
</div>
<ul><li>Samples:
<c:forEach var="sample" items="${samples}">
<a class="sample_list" href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>" target="_blank" title="${sample.sampleName} (${sample.sampleId})">${sample.sampleId}</a>
</c:forEach>
<li> Project: <a href="<c:url value="${baseURL}/project/${study.studyId}"/>" target="_blank" title="${study.studyName} (${study.studyId})">${study.studyName}</a> (${study.studyId})</li>
 </li>
</ul>
<%--<p>Use tabs below to switch between available visualizations.</p>--%>
<div class="sample_comp_result">
<div id="tabs">
    <ul>
        <%--li><a href="#overview">Overview</a></li--%>
        <li><a href="#bars" title="view results as bar chart"><span class="ico-barh"></span> <span class="tab-text">Barcharts</span></a></li>
        <li><a href="#stack" title="view results as stacked column"><span class="ico-col"></span> <span class="tab-text">Stacked columns</span></a></li>
        <li><a href="#heatmap" title="view results as heatmap"><span class="ico-heatmap"></span><span class="tab-text">Heatmap</span></a></li>
        <li><a href="#pca" title="view results as principal components analysis"><span class="ico-pca"></span><span class="tab-text">Principal Components Analysis</span></a></li>
        <li><a href="#jstable" title="view results in a table"><span class="ico-table"></span><span class="tab-text">Table</span></a></li>
    </ul>
    <%--div id="overview">
        <p>${graphCode[0]}</p>
    </div--%>
    <div id="bars">
        <div id="export_div_bars" class="style-export">
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
            <br>
            </div>
        <div id="sticky-leg-anchor"></div>
        <div id="barcharts_legend"><strong>Sample list</strong> <span class="barcharts_legend_info">(click to hide)</span><br/></div>
        <div id="bars-wrapper">${graphCode[1]}</div>
    </div>
    <div id="stack">
           <div id="stack-jump-anchor"></div>
           <div id="stack_jump">Jump to: <a href="#stack_bio_title"> Biological process</a> | <a href="#stack_cell_title">Cellular component</a> | <a href="#stack_mol_title">Molecular function</a></div>

            <div id="stack_wrapper">${graphCode[2]}</div>
    </div>
    <div id="heatmap">
   <div id="hm-jump-anchor"></div>
   <div id="hm_jump">Jump to GO category: <a href="#hm_bio_title"> Biological process</a> | <a href="#hm_cell_title">Cellular component</a> | <a href="#hm_mol_title">Molecular function</a></div>

       <%--<h3> Biological process</h3>--%>
        ${graphCode[3]}
    </div>
    <div id="pca">
        <div id="export_pca_div" class="style-export">
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
            </select>
            <br>
        </div>
        <%--Principal Component 1 <input id="pc1" type="number" name="Principal Component 1" min="1" max="3" value="1" disabled>
        Principal Component 2 <input id="pc2" type="number" name="Principal Component 2" min="1" max="3" value="2" disabled> <br>--%>
        <div id="plots-wrapper">${graphCode[4]}</div>
    </div>
    <div id="jstable">
        <p>${graphCode[5]}</p>
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

    // Barcharts export module
        $('#bars_export').change(function() {
            var exportValue = $( "#bars_export" ).val();
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

                var chartId = "#"+goType+"_bars";
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
        });

    // PCA export module
    $('#pca_export').change(function() {
        var exportValue = $( "#pca_export" ).val();
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
        }

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
            var chartId = "#"+goType+"_pca_12"; // Need to find a way to see which PCs are drawn. Could be cleaner
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
    });

    // Sticky div relocation function to let the barchart legend stay on top
    function sticky_bar_relocate() {
        var window_top = $(window).scrollTop();
        var barcharts_leg_top = $('#sticky-leg-anchor').offset().top;
        if (window_top > barcharts_leg_top) {
            $('#barcharts_legend').addClass('stick');
            // Quick fix to make the div-width equal to its tab width
            $("#barcharts_legend").css("width", $("#bars").width());
        } else {
            $('#barcharts_legend').removeClass('stick');
        }
    }

    $(function() {
        $(window).scroll(sticky_bar_relocate);
        sticky_bar_relocate();
    });

    // Sticky div relocation function to let the heatmap 'Jump to' div stay on top
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

    $(function() {
        $(window).scroll(sticky_hm_relocate);
        sticky_hm_relocate();
    });

    // Sticky div relocation function to let the stacked columns 'Jump to' div stay on top
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

    $(function() {
        $(window).scroll(sticky_stack_relocate);
        sticky_stack_relocate();
    });

</script>

<script type="text/javascript">
    $(document).ready(function() {
        if($('#biological_process_bars').length != 0) {
        var sampleNum = "${fn:length(samples)}";
        var sampleString = "${sampleString}";
        var sampleArr = sampleString.split(',');
        var i;
        var bioChart = $('#biological_process_bars').highcharts();
        var molChart = $('#molecular_function_bars').highcharts();
        var cellChart = $('#cellular_component_bars').highcharts();
        var bioChartSeries = bioChart.series;
        var molChartSeries = molChart.series;
        var cellChartSeries = cellChart.series;
        for (i = 0; i < sampleNum; i++)
        {
            var currentColor = cellChartSeries[i].color;
        $('<div/>', {
            'id': 'legend_'+i,
            'class': 'legend-item',
            'html': '<div class="legend-rectangle" style="background:'+ currentColor + ';"></div><span> '+sampleArr[i]+'</span>',
            'click': function () {
                var legInd = this.id.split('_')[1];
                // Set to biological process chart but could be whatever we want
                if(bioChartSeries[legInd].visible){
                    bioChartSeries[legInd].hide();
                    molChartSeries[legInd].hide();
                    cellChartSeries[legInd].hide();
                    // Changing color to grey (for the rectangle too!)
                    $(this).css('color', '#D1D1D1');
                    $(this).children('.legend-rectangle').css('background', '#D1D1D1');
                }
                else {
                    bioChartSeries[legInd].show();
                    molChartSeries[legInd].show();
                    cellChartSeries[legInd].show();
                    // Changing color to normal display (could be cleaner?)
                    $(this).css('color', '#606068');
                    $(this).children('.legend-rectangle').css('background', cellChartSeries[legInd].color);
                }
            },
            'mouseenter': function () {
               // $(this).css('color', 'blue');
            },
            'mouseleave': function () {
               // $(this).css('color', 'black');
            }
        }).appendTo('#barcharts_legend');
        }
        }
        else {
            // No barcharts? Empty the legend and disable export so the user is not confused
            $( "#barcharts_legend" ).empty();

        //    $("#bars_export").disable(); // Could be better to hide the element instead of disabling it.
        }
    });
</script>

<script type="text/javascript" defer="defer">
    // Could I have some tabs please ?
    $( document ).ready(function() {
        $(function () {
           $('#tabs').tabs();
//           $('.rChart').each(function() {
//                if($(this).is(':visible')) {
//                    var chartContainerName = this.id;
                    $(window).trigger('resize');
//                    $('#'+chartContainerName).highcharts().reflow();
//                }
        });
       // $('#molecular_function_bars').hide();
        });
//    });

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

<%--<script type="text/javascript" defer="defer">--%>
    <%--$('#tabs').click(function() {--%>
    <%--$('.rChart').each(function() {--%>
        <%--if($(this).is(':visible')) {--%>
            <%--var chartContainerName = this.id;--%>
            <%--$('#'+chartContainerName).highcharts().reflow();--%>
        <%--}--%>
    <%--});--%>
    <%--});--%>
    <%--$( window ).resize(function() {--%>
    <%--$('.rChart').each(function() {--%>
        <%--if($(this).is(':visible')) {--%>
            <%--var chartContainerName = this.id;--%>
            <%--$('#'+chartContainerName).highcharts().reflow();--%>
        <%--}--%>
    <%--});--%>
    <%--});--%>
<%--</script>--%>
