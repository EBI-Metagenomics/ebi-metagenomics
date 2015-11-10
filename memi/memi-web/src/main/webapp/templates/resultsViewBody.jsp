<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:choose>
    <c:when test="${not empty model.sample}">
       <a href="<c:url value="${baseURL}/pipelines/${model.analysisJob.pipelineRelease.releaseVersion}"/>"> <div class="icon_pipeline_v anim show_tooltip" title="Data analysed with pipeline v.${model.analysisJob.pipelineRelease.releaseVersion}">Pipeline v.${model.analysisJob.pipelineRelease.releaseVersion}</div></a>

        <h2 class="fl_uppercase_title run_title extra_margin">Run <span>(${model.run.externalRunId})</span></h2>

        <div class="sample_ana">
        <div id="navtabs">

            <%--Main Tabs--%>
            <ul>
                <li>
                    <a title="Overview"
                       href="<c:url value="${baseURL}/projects/${model.run.externalProjectId}/samples/${model.run.externalSampleId}/runs/${model.run.externalRunId}/results/overview/versions/${model.analysisJob.pipelineRelease.releaseVersion}"/>"><span>Overview</span></a>
                </li>
                <li>
                    <a title="Quality control"
                       href="<c:url value="${baseURL}/projects/${model.run.externalProjectId}/samples/${model.run.externalSampleId}/runs/${model.run.externalRunId}/results/qualityControl/versions/${model.analysisJob.pipelineRelease.releaseVersion}"/>"><span>Quality control</span></a>
                </li>
                <li><a title="Taxonomy analysis"
                       href="<c:url value="${baseURL}/projects/${model.run.externalProjectId}/samples/${model.run.externalSampleId}/runs/${model.run.externalRunId}/results/taxonomic/versions/${model.analysisJob.pipelineRelease.releaseVersion}"/>"><span>Taxonomic analysis</span></a>
                </li>
                <li>
                    <a title="Functional analysis"
                       href="<c:url value="${baseURL}/projects/${model.run.externalProjectId}/samples/${model.run.externalSampleId}/runs/${model.run.externalRunId}/results/functional/versions/${model.analysisJob.pipelineRelease.releaseVersion}"/>"><span>Functional analysis</span></a>
                </li>
                <li>
                    <a title="Download"
                       href="<c:url value="${baseURL}/projects/${model.run.externalProjectId}/samples/${model.run.externalSampleId}/runs/${model.run.externalRunId}/results/download/versions/${model.analysisJob.pipelineRelease.releaseVersion}"/>"><span>Download</span></a>
                </li>
                    <%--<li><a href="#fragment-experimental"><span>Experimental factor</span></a></li>--%>
            </ul>

        </div>
        <%--end navtabs--%>

        <script type="text/javascript">
            $(function () {
                //   IMPORTANT TO keep at bottom as it should be rendered last - anchor “jump” when loading a page from http://stackoverflow.com/questions/3659072/jquery-disable-anchor-jump-when-loading-a-page
                setTimeout(function () {
                    if (location.hash) {
//                   $('#local-masthead').fadeOut();
                        window.scrollTo(0, 0);
                    }
                }, 1000);
            });
        </script>
        <%--script for tabs--%>
        <script type="text/javascript">
            //Main navigation tabs - overview, quality control, taxonomic analysis, functional analysis, downloads
            //Ajax load approach as described here: http://jqueryui.com/tabs/#ajax

            $("#navtabs").tabs({
                cache:true,
                ajaxOptions:{
                    error:function (xhr, status, index, anchor) {
                        $(anchor.hash).html("<div class='msg_error'>Couldn't load this tab. We'll try to fix this as soon as possible.</div>");
                    }
                },
                //Change the Hashtag on Select
                //Described here: http://imdev.in/jquery-ui-tabs-with-hashtags/
                select:function (event, ui) {
                    window.location.hash = ui.tab.hash;
                },
                //Set the disable option
                ${model.analysisStatus.disabledOption}
            });

            //  Load the Visualization API and the chart package.
            google.load('visualization', '1.0', {'packages':['corechart', 'table', 'controls'] });
        </script>

    </c:when>
    <c:otherwise>
        <h3>Run ID Not Recognised</h3>
    </c:otherwise>
</c:choose>

</div>  <%--end sample_ana--%>

<!-- Script needed to convert Google chart images into Canvas/image elements-->
<script type="text/javascript">

    function getImgData(chartContainer, dpiFactor) {
//        extract the svg code for the chart you want to serialize (assuming chartContainer points to the html element containing the chart):
        var chartArea = chartContainer.getElementsByTagName('svg')[0].parentNode;
        var svg = chartArea.innerHTML;
        var jHtmlObject = jQuery(svg);
        var editor = jQuery("<p>").append(jHtmlObject);
        editor.find("div").remove();
        var newHtml = editor.html();
//       alert('' + newHtml + '');

        var document = chartContainer.ownerDocument;
//        create a canvas element and position it offscreen for the user not to see it
        var canvas = document.createElement('canvas');
//        canvas.setAttribute('width', chartArea.offsetWidth);
//        canvas.setAttribute('height', chartArea.offsetHeight);
        // Get chart aspect ratio
        var c_ar = chartArea.offsetHeight / chartArea.offsetWidth;
        var newCanvasWidth = chartArea.offsetWidth * dpiFactor;
        // Set canvas size
        canvas.setAttribute('width', newCanvasWidth);
        canvas.setAttribute('height', newCanvasWidth * c_ar);
        canvas.setAttribute(
                'style',
                'position: absolute; ' +
                        'top: ' + (-chartArea.offsetHeight * 2) + 'px;' +
                        'left: ' + (-chartArea.offsetWidth * 2) + 'px;');
        document.body.appendChild(canvas);
//        transform the svg instructions into a canvas
        //TODO: Where do I find the transformation function called 'canvg'?
        //Answer: Looks like it is in here: http://canvg.googlecode.com/svn/trunk/canvg.js

        canvg(canvas, newHtml, {
//            http://stackoverflow.com/questions/15642145/highcharts-and-canvg-scaling-issue
            ignoreDimensions:true,
            scaleWidth:canvas.getAttribute('width'),
            scaleHeight:canvas.getAttribute('height')
        });
        canvas.parentNode.removeChild(canvas);
        //extract the image data (as a base64 encoded string)
        // Download image - Replacing the mime-type will force the browser to trigger a download rather than displaying the image in the browser window.
        return canvas.toDataURL('image/png');
    }
    //    /**
    //     * Save image function for Google charts.
    //     *
    //     * How does it work? Sends a POST request to the server with the dataURL and the filename and the server creates an HTTP response with opens a download dialog.
    //     **/
    function saveAsImg(chartContainer, fileName, dpiFactor) {
        var dataUrl = getImgData(chartContainer, dpiFactor);
        var form = $('<form/>', {
            id:'pngExportForm',
            name:'pngExportForm',
            action:"<c:url value="${baseURL}/projects/${model.run.externalProjectId}/samples/${model.run.externalSampleId}/runs/${model.run.externalRunId}/results/versions/${model.analysisJob.pipelineRelease.releaseVersion}/export"/>",
            method:'POST',
            enctype:'text/plain',
            css:{ display:'none' }
        });
        form.append($('<input/>', {name:'fileType', value:'png'}));
        form.append($('<input/>', {name:'fileName', value:fileName}));
        form.append($('<input/>', {name:'dataUrl', value:dataUrl}));
        $('body').append(form);
        form.submit();
    }

    //    function getSnapshotImgData(chartContainer) {
    ////        extract the svg code for the chart you want to serialize (assuming chartContainer points to the html element containing the chart):
    //        var chartArea = chartContainer.getElementsByTagName('svg')[0].parentNode;
    //        var svg = chartArea.innerHTML;
    //        var document = chartContainer.ownerDocument;
    ////        create a canvas element and position it offscreen for the user not to see it
    //        var canvas = document.createElement('canvas');
    //        canvas.setAttribute('width', chartArea.offsetWidth);
    //        canvas.setAttribute('height', chartArea.offsetHeight);
    //        canvas.setAttribute(
    //                'style',
    //                'position: absolute; ' +
    //                        'top: ' + (-chartArea.offsetHeight * 2) + 'px;' +
    //                        'left: ' + (-chartArea.offsetWidth * 2) + 'px;');
    //        document.body.appendChild(canvas);
    ////        transform the svg instructions into a canvas
    //        //TODO: Where do I find the transformation function called 'canvg'?
    //        //Answer: Looks like it is in here: http://canvg.googlecode.com/svn/trunk/canvg.js
    //
    //        canvg(canvas, svg);
    //        canvas.parentNode.removeChild(canvas);
    //        //extract the image data (as a base64 encoded string)
    //        // Download image - Replacing the mime-type will force the browser to trigger a download rather than displaying the image in the browser window.
    //        return canvas.toDataURL('image/png');
    //    }

    //    /**
    //     * Returns SVG element as String representation (extracted from the chart container).
    //     * @param chartContainer
    //     * @return {String}
    //     */
    function getSVGDocumentAsString(chartContainer) {
//   extract the svg code for the chart (assuming chartContainer points to the html element containing the chart) - svg elements don't have inner/outerHTML properties -  IN FAct, this is not true anymore-, so use the parent
        var chartArea = chartContainer.getElementsByTagName('svg')[0].parentNode;
        var svgDocumentAsString = chartArea.innerHTML;
        return svgDocumentAsString;
    }

    //    /**
    //     * Save Google chart as SVG function.
    //     *
    //     * How does it work? Sends a POST request to the server with the dataURL and the filename and the server creates an HTTP response with opens a download dialog.
    //     * @param chartContainer
    //     * @param fileName
    //     */
    function saveAsSVG(chartContainer, fileName) {
        var svgDocumentAsString = getSVGDocumentAsString(chartContainer);
        //remove the div from the string to make the SVG export work
        var jHtmlObject = jQuery(svgDocumentAsString);
        var editor = jQuery("<p>").append(jHtmlObject);
        editor.find("div").remove();
        var newHtml = editor.html();
       // alert('' + newHtml + '');
        mystringNew = newHtml.split('\u2026').join('...'); //replace 3dots in string that cause of SVG bug export
       //  alert(mystringNew);
        var form = $('<form/>', {
            id:'svgExportForm',
            name:'svgExportForm',
            action:"<c:url value="${baseURL}/projects/${model.run.externalProjectId}/samples/${model.run.externalSampleId}/runs/${model.run.externalRunId}/results/versions/${model.analysisJob.pipelineRelease.releaseVersion}/export"/>",
            method:'POST',
            enctype:'text/plain',
            css:{ display:'none' }
        });
        form.append($('<input/>', {name:'fileType', value:'svg'}));
        form.append($('<input/>', {name:'fileName', value:fileName}));
        form.append($('<input/>', {name:'svgDocumentAsString', value:mystringNew}));
        $('body').append(form);
        form.submit();
    }

    //    //Creates a snapshot of a Google chart
    //    function toImg(chartContainer, imgContainer) {
    //        window.open(getSnapshotImgData(chartContainer), '_blank');
    //    }
</script>

<script type="text/javascript" src="https://canvg.googlecode.com/svn/trunk/rgbcolor.js"></script>
<script type="text/javascript" src="https://canvg.googlecode.com/svn/trunk/canvg.js"></script>
