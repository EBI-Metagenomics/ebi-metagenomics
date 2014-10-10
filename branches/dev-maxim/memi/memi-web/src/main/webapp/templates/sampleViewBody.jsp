<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:choose>
    <c:when test="${not empty model.sample}">

        <div class="title_tab">
            <span class="subtitle">Sample <span>(${model.sample.sampleId})</span></span>

            <h2 class="fl_uppercase_title">${model.sample.sampleName}</h2>
        </div>

        <div id="img_div" style="position: fixed; top: 9px; right: 38px; z-index: 10; "></div>
        <%--container used to convert   google chart into image--%>

        <div class="sample_ana">
        <div id="navtabs">

                <%--Main Tabs--%>
            <ul>
                <li>
                    <a title="Overview"
                       href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/overview"/>"><span>Overview</span></a>
                </li>
                <li>
                    <a title="Quality-Control"
                       href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/qualityControl"/>"><span>Quality control</span></a>
                </li>
                <li><a title="Taxonomy-Analysis"
                       href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/taxonomic"/>"><span>Taxonomy analysis</span></a>
                </li>
                <li>
                    <a title="Functional-Analysis"
                       href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/functional"/>"><span>Functional analysis</span></a>
                </li>
                <li>
                    <a title="Download"
                       href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/download"/>"><span>Download</span></a>
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
            $(function () {
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
            });
            //  Load the Visualization API and the chart package.
            google.load('visualization', '1', {'packages':['corechart', 'table', 'controls'] });
        </script>

    </c:when>
    <c:otherwise>
        <h3>Sample ID Not Recognised</h3>
    </c:otherwise>
</c:choose>

</div>  <%--end sample_ana--%>

<!-- Script needed to convert Google chart images into Canvas/image elements-->
<script type="text/javascript">

    function getImgData(chartContainer,dpiFactor) {
//        extract the svg code for the chart you want to serialize (assuming chartContainer points to the html element containing the chart):
        var chartArea = chartContainer.getElementsByTagName('svg')[0].parentNode;
        var svg = chartArea.innerHTML;
        var document = chartContainer.ownerDocument;
//        create a canvas element and position it offscreen for the user not to see it
        var canvas = document.createElement('canvas');
//        canvas.setAttribute('width', chartArea.offsetWidth);
//        canvas.setAttribute('height', chartArea.offsetHeight);
        // Get chart aspect ratio
        var c_ar = chartArea.offsetHeight / chartArea.offsetWidth;
        var newCanvasWidth = chartArea.offsetWidth*dpiFactor;
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

        canvg(canvas, svg, {
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

    function getSnapshotImgData(chartContainer) {
//        extract the svg code for the chart you want to serialize (assuming chartContainer points to the html element containing the chart):
        var chartArea = chartContainer.getElementsByTagName('svg')[0].parentNode;
        var svg = chartArea.innerHTML;
        var document = chartContainer.ownerDocument;
//        create a canvas element and position it offscreen for the user not to see it
        var canvas = document.createElement('canvas');
        canvas.setAttribute('width', chartArea.offsetWidth);
        canvas.setAttribute('height', chartArea.offsetHeight);
        canvas.setAttribute(
                'style',
                'position: absolute; ' +
                        'top: ' + (-chartArea.offsetHeight * 2) + 'px;' +
                        'left: ' + (-chartArea.offsetWidth * 2) + 'px;');
        document.body.appendChild(canvas);
//        transform the svg instructions into a canvas
        //TODO: Where do I find the transformation function called 'canvg'?
        //Answer: Looks like it is in here: http://canvg.googlecode.com/svn/trunk/canvg.js

        canvg(canvas, svg);
        canvas.parentNode.removeChild(canvas);
        //extract the image data (as a base64 encoded string)
        // Download image - Replacing the mime-type will force the browser to trigger a download rather than displaying the image in the browser window.
        return canvas.toDataURL('image/png');
    }

    //    /**
    //     * Returns SVG element as String representation (extracted from the chart container).
    //     * @param chartContainer
    //     * @return {String}
    //     */
    function getSVGDocumentAsString(chartContainer) {
//        extract the svg code for the chart you want to serialize (assuming chartContainer points to the html element containing the chart):
        var chartArea = chartContainer.getElementsByTagName('svg')[0].parentNode;
        var svgDocumentAsString = chartArea.innerHTML;
        return svgDocumentAsString;
    }

    //    /**
    //     * Save image function for Google charts.
    //     *
    //     * How does it work? Sends a POST request to the server with the dataURL and the filename and the server creates an HTTP response with opens a download dialog.
    //     **/
    function saveAsImg(chartContainer, fileName,dpiFactor) {
        var dataUrl = getImgData(chartContainer,dpiFactor);
        var form = $('<form/>', {
            id:'pngExportForm',
            name:'pngExportForm',
            action:"<c:url value="${baseURL}/sample/${model.sample.sampleId}/export"/>",
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

    //    /**
    //     * Save Google chart as SVG function.
    //     *
    //     * How does it work? Sends a POST request to the server with the dataURL and the filename and the server creates an HTTP response with opens a download dialog.
    //     * @param chartContainer
    //     * @param fileName
    //     */
    function saveAsSVG(chartContainer, fileName) {
        var svgDocumentAsString = getSVGDocumentAsString(chartContainer);
        var form = $('<form/>', {
            id:'svgExportForm',
            name:'svgExportForm',
            action:"<c:url value="${baseURL}/sample/${model.sample.sampleId}/export"/>",
            method:'POST',
            enctype:'text/plain',
            css:{ display:'none' }
        });
        form.append($('<input/>', {name:'fileType', value:'svg'}));
        form.append($('<input/>', {name:'fileName', value:fileName}));
        form.append($('<input/>', {name:'svgDocumentAsString', value:svgDocumentAsString}));
        $('body').append(form);
        form.submit();
    }

    //Creates a snapshot of a Google chart
    function toImg(chartContainer, imgContainer) {
        window.open(getSnapshotImgData(chartContainer), '_blank');


    }

    <%--This method is widely used on the sample page--%>
    <%--Looks like this method only sets CSS styling--%>
    function loadCssStyleForExportSelection(elementIdentifier) {
        $(function () {
            $(elementIdentifier)
                    .next()
                    .button({
                        text:true,
                        icons:{
                            secondary:"ui-icon-triangle-1-s"
                        }
                    })
                    .click(function () {
                        var menu = $(this).parent().next().show().position({
                            my:"left top",
                            at:"left bottom",
                            of:this
                        });
                        $(document).one("click", function () {
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
    }
</script>

<script type="text/javascript" src="https://canvg.googlecode.com/svn/trunk/rgbcolor.js"></script>
<script type="text/javascript" src="https://canvg.googlecode.com/svn/trunk/canvg.js"></script>
