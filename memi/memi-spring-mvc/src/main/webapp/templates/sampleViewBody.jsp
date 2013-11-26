<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:choose>
    <c:when test="${not empty model.sample}">

    <div class="title_tab">
    <span class="subtitle">Sample <span>(${model.sample.sampleId})</span></span>
    <h2 class="fl_uppercase_title">${model.sample.sampleName}</h2>
    </div>

        <div id="img_div" style="position: fixed; top: 9px; right: 38px; z-index: 10; "></div><%--container used to convert   google chart into image--%>

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
               setTimeout(function() {
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
                    }
                });
                //Default functionality
                //Set the disable option
                $("#navtabs").tabs({${model.analysisStatus.disabledOption}});
                //Change the Hashtag on Select
                //Described here: http://imdev.in/jquery-ui-tabs-with-hashtags/
                $("#navtabs").tabs({
                    select:function (event, ui) {
                        window.location.hash = ui.tab.hash;
                    }
                });
            });
            //  Load the Visualization API and the chart package.
            google.load('visualization', '1.1', {'packages':['corechart', 'table', 'controls'] });
        </script>

    </c:when>
    <c:otherwise>
        <h3>Sample ID Not Recognised</h3>
    </c:otherwise>
</c:choose>

</div>  <%--end sample_ana--%>
<div class="but_top"><a href="#top" title="back to the top page">Top</a></div>
<!-- Script needed to convert Google chart images into Canvas/image elements-->
<script type="text/javascript">

    function getImgData(chartContainer) {
//        extract the svg code for the chart you want to serialize (assuming chartContainer points to the html element containing the chart):
         var chartArea = chartContainer.getElementsByTagName('svg')[0].parentNode;
         var svg = chartArea.innerHTML;
         var doc = chartContainer.ownerDocument;
//        create a canvas element and position it offscreen for the user not to see it
        var canvas = doc.createElement('canvas');
         canvas.setAttribute('width', chartArea.offsetWidth);
         canvas.setAttribute('height', chartArea.offsetHeight);
         canvas.setAttribute(
             'style',
             'position: absolute; ' +
             'top: ' + (-chartArea.offsetHeight * 2) + 'px;' +
             'left: ' + (-chartArea.offsetWidth * 2) + 'px;');
         doc.body.appendChild(canvas);
//        transform the svg instructions into a canvas
         canvg(canvas, svg);
//        extract the image data (as a base64 encoded string
         var imgData = canvas.toDataURL('image/png');
         canvas.parentNode.removeChild(canvas);
         return imgData;
       }

       function saveAsImg(chartContainer) {
         var imgData = getImgData(chartContainer);

         // Download image - Replacing the mime-type will force the browser to trigger a download rather than displaying the image in the browser window.
         window.location = imgData.replace('image/png', 'image/octet-stream');
       }

       function toImg(chartContainer, imgContainer) {
         var doc = chartContainer.ownerDocument;
         var img = doc.createElement('img');
         img.src = getImgData(chartContainer);

         while (imgContainer.firstChild) {
           imgContainer.removeChild(imgContainer.firstChild);
         }
         imgContainer.appendChild(img);
       }

</script>

<script type="text/javascript" src="http://canvg.googlecode.com/svn/trunk/rgbcolor.js"></script>
<script type="text/javascript" src="http://canvg.googlecode.com/svn/trunk/canvg.js"></script>
