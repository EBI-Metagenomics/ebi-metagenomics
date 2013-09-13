<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>--%>
<%--<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>--%>
<%--<%@ page language="java" contentType="text/html" %>--%>
<%--<%@ page import="java.util.*" %>--%>

<%--<%--%>
<%--//Use in functionalAnalysisTab.jsp to set the color code of the first 10 entries in the InterPro matches table--%>
<%--ArrayList colorCodeList = new ArrayList();--%>
<%--colorCodeList.add("#058dc7");--%>
<%--colorCodeList.add("#50b432");--%>
<%--colorCodeList.add("#ed561b");--%>
<%--colorCodeList.add("#edef00");--%>
<%--colorCodeList.add("#24cbe5");--%>
<%--colorCodeList.add("#64e572");--%>
<%--colorCodeList.add("#ff9655");--%>
<%--colorCodeList.add("#fff263");--%>
<%--colorCodeList.add("#6af9c4");--%>
<%--colorCodeList.add("#dabe88");--%>
<%--pageContext.setAttribute("colorCodeList", colorCodeList);--%>
<%--%>--%>
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

<%--<script type="text/javascript">--%>
<%--// Load the Visualization API and the piechart package.--%>
<%--google.load('visualization', '1.1', {'packages':['corechart', 'table', 'controls'] });--%>
<%--</script>--%>

<%--<%@ include file="googleCharts/functionalAnalysisTab.jsp" %>--%>
<%--<%@ include file="googleCharts/taxonomicAnalysisTab.jsp" %>--%>

<script type='text/javascript'>
    //BEGIN code used to showroom the row number selection - TODO apply on the new table

    //    google.setOnLoadCallback(init);
    //
    //    var dataSourceUrl = 'https://docs.google.com/spreadsheet/ccc?key=0AgWotcbTSSjYdGF6NjE0WGxGRmV5djJDWEZ6RzZhT2c&usp=sharing';
    //    var query, options, container;
    //
    //       function init() {
    //         query = new google.visualization.Query(dataSourceUrl);
    //         container = document.getElementById("func_table_div1");
    //         options = {width:600, allowHtml:true, showRowNumber:true, page:'enable', pageSize:10, pagingSymbols:{prev:'prev', next:'next'}, sortColumn:2, sortAscending:false};
    //         sendAndDraw();
    //       }
    //
    //       function sendAndDraw() {
    //         query.abort();
    //         var tableQueryWrapper = new TableQueryWrapper(query, container, options);
    //         tableQueryWrapper.sendAndDraw();
    //       }
    //
    //
    //       function setOption(prop, value) {
    //         options[prop] = value;
    //         sendAndDraw();
    //       }


    //    google.setOnLoadCallback(drawTable);// Set a callback to run when the Google Visualization API is loaded.

    //END code used to showroom the row number selection - TODO apply on the new table

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
                <li>
                    <a href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/overviewTab"/>"><span>Overview</span></a>
                </li>
                <li>
                    <a href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/qualityControlTab"/>"><span>Quality control</span></a>
                </li>
                <li><a href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/taxonomicTab"/>"><span>Taxonomy analysis</span></a>
                </li>
                <li>
                    <a href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/functionalTab"/>"><span>Functional analysis</span></a>
                </li>
                <li>
                    <a href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/downloadTab"/>"><span>Download</span></a>
                </li>
                    <%--<li><a href="#fragment-experimental"><span>Experimental factor</span></a></li>--%>
            </ul>

        </div>
        <%--end navtabs--%>


        <%--script for tabs--%>
        <script type="text/javascript">
            //Main navigation tabs - overview, quality control, taxonomic analysis, functional analysis, downloads
            //Ajax load approach as described here: http://jqueryui.com/tabs/#ajax
            $(document).ready(function () {
                $("#navtabs").tabs({
                    ajaxOptions:{
                        error:function (xhr, status, index, anchor) {
                            $(anchor.hash).html("Couldn't load this tab. We'll try to fix this as soon as possible.");
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
//            google.load("visualization", "1", {packages:["corechart"]});
            });
            //  Load the Visualization API and the chart package.
            google.load('visualization', '1.1', {'packages':['corechart', 'table', 'controls'] });

            //Fixes the auto-scrolling issue when linking from homepage using hash tag
            setTimeout(function () {
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
