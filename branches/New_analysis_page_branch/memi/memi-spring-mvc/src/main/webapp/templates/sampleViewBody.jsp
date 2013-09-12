<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html" %>
<%@ page import="java.util.*" %>

<%
    //Use in functionalAnalysisTab.jsp to set the color code of the first 10 entries in the InterPro matches table
    ArrayList colorCodeList = new ArrayList();
    colorCodeList.add("#058dc7");
    colorCodeList.add("#50b432");
    colorCodeList.add("#ed561b");
    colorCodeList.add("#edef00");
    colorCodeList.add("#24cbe5");
    colorCodeList.add("#64e572");
    colorCodeList.add("#ff9655");
    colorCodeList.add("#fff263");
    colorCodeList.add("#6af9c4");
    colorCodeList.add("#dabe88");
    pageContext.setAttribute("colorCodeList", colorCodeList);
%>
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
    google.load('visualization', '1.1', {'packages':['corechart', 'table', 'controls'] });
</script>

<%@ include file="googleCharts/functionalAnalysisTab.jsp" %>
<%@ include file="googleCharts/taxonomicAnalysisTab.jsp" %>

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
    <li><a href="#fragment-overview"><span>Overview</span></a></li>
    <li><a href="#fragment-quality"><span>Quality control</span></a></li>
    <li><a href="#fragment-taxonomy"><span>Taxonomy analysis</span></a></li>
    <li><a href="#fragment-functional"><span>Functional analysis</span></a></li>
    <li><a href="#fragment-download"><span>Download</span></a></li>
        <%--<li><a href="#fragment-experimental"><span>Experimental factor</span></a></li>--%>
</ul>


<div id="fragment-overview">


    <tags:publications publications="${model.sample.publications}" relatedPublications="${model.relatedPublications}"
                       relatedLinks="${model.relatedLinks}"/>

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
                        <div class="result_row"><label>Phenotype:</label> <span><c:out value="${phenotype}"/></span>
                        </div>
                    </c:if>


                </div>
                <%--end div output_form--%>

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
                    </c:choose>
                    <div class="result_row"><label>Biome:</label> <span><c:out value="${environmentalBiome}"/></span>
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
            <div class="result_row"><label>Geographic location:</label> <span><c:out value="${geoLocName}"/></span>
            </div>
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
        <p>These are the results from the taxonomic analysis steps of our pipeline. You can switch between
            different views of the data using the menu of icons below (pie, bar, stacked and interactive krona
            charts).  If you wish to download the full set of results, all files are listed under the
            "Download" tab.</p>
            <%--<h3>Taxonomy analysis</h3>--%>


        <c:choose>
        <%--<c:when test="${empty model.sample.analysisCompleted}">--%>
            <%--<div class="msg_error">Analysis in progress</div>--%>
        <%--</c:when>--%>
        <c:when test="${not empty model.sample.analysisCompleted && !model.analysisStatus.taxonomicAnalysisTabDisabled}">
        <h3>Top taxonomy Hits</h3>
        <div id="tabs-taxchart">
            <ul>
                <li class="selector_tab">Switch view:</li>
                    <%--<li><a href="#tax-table" title="Table view"><span class="ico-table"></span></a></li>--%>
                <li><a href="#tax-pie" title="Pie chart view"><span class="ico-pie"></span></a></li>
                <li><a href="#tax-bar" title="Bar chart view"><span class="ico-barh"></span></a></li>
                <li><a href="#tax-col" title="Stacked column chart view"><span class="ico-col"></span></a></li>
                <li class="but_krona"><a href="#tax-Krona" title="Krona interactive chart view"><span class="ico-krona"></span></a></li>
                    <%--<li class="ico-downl"><a class="icon icon-functional" data-icon="=" href="#download" title="Download image/table"></a></li>--%>
            </ul>


                <%--Taxonomy google chart--%>
            <div id="tax-pie">
                <div class="chart_container">
                    <div class="chart_container">
                        <div id="tax_chart_pie_dom"></div>
                        <div id="tax_chart_pie_phy"></div>

                        <div id="tax_dashboard">
                            <div id="tax_table_filter"></div>
                            <div id="tax_table_pie"></div>
                                <%--<div id="table_div"></div>--%>
                        </div>

                    </div>
                </div>
            </div>

            <div id="tax-bar">
                <div class="chart_container">
                    <div id="tax_chart_bar_dom"></div>
                    <div id="tax_chart_bar_phy"></div>

                    <div id="tax_dashboard_bar">
                                         <div id="tax_table_bar_filter"></div>
                                          <div id="tax_table_bar"></div>
                    </div>

                </div>
            </div>

            <div id="tax-col">
                <div class="chart_container">
                    <div id="tax_chart_col"></div>

                    <div id="tax_dashboard_col">
                           <div id="tax_table_col_filter"></div>
                            <div id="tax_table_col"></div>
                     </div>

                </div>

            </div>
            <div id="tax-Krona"><object class="krona_chart" data="<c:url value="${baseURL}/sample/${model.sample.sampleId}/krona?taxonomy=true&collapse=false"/>" type="text/html"></object>
            </div>
        </div>
            </c:when>
            <c:otherwise>
                <div class="msg_error">No taxonomy result files have been associated with this sample.</div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<div id="fragment-functional">

    <div class="main_tab_full_content">
        <p>Functional analysis has 3 main outputs: a sequence features summary, showing the number of reads with predicted coding sequences (pCDS), the number of pCDS with InterPro matches, and so on; the matches of pCDS to the <a href="http://www.ebi.ac.uk/interpro"  title="InterPro website" class="ext">InterPro database</a>
            and a chart of the GO terms that summarise the functional content of the sample's sequences. If you wish to download the full set of results, all files are listed under the "Download" tab.</p>

        <c:choose>
        <c:when test="${not empty model.sample.analysisCompleted && !model.analysisStatus.functionalAnalysisTab.interProMatchSectionDisabled && !model.analysisStatus.functionalAnalysisTab.goSectionDisabled}">

           <c:choose>
               <%--Do we still need those ??? --%>
               <%--<c:when test="${empty model.sample.analysisCompleted}">--%>
                   <%--<b>Analysis in progress</b>--%>
               <%--</c:when>--%>
               <c:when test="${not empty model.sample.analysisCompleted && !model.analysisStatus.functionalAnalysisTab.sequenceFeatureSectionDisabled}">

                   <h3>Sequence feature summary</h3>

                   <div style="display:block; overflow: auto;">
                       <c:url var="sequenceFeatureImage" value="/getImage" scope="request">
                           <c:param name="imageName" value="/charts/seq-feat.png"/>
                           <c:param name="imageType" value="PNG"/>
                           <c:param name="dir" value="${model.emgFile.fileID}"/>
                       </c:url>
                       <div style="float:left; margin-left: 9px;"><img src="<c:out value="${sequenceFeatureImage}"/>"/></div>
                   </div>
                       </c:when>
               <c:otherwise>
                   <%-- remove the section if empty
                   <div class="msg_error">No sequence feature result files have been associated with this sample.</div>--%>
               </c:otherwise>
           </c:choose>


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
               <%--<c:when test="${empty model.sample.analysisCompleted}">--%>
                   <%--<b>Analysis in progress</b>--%>
               <%--</c:when>--%>
               <c:when test="${not empty model.sample.analysisCompleted && !model.analysisStatus.functionalAnalysisTab.interProMatchSectionDisabled}">
                   <div id="interpro-chart">

                           <%--Tabs--%>
                       <ul>
                               <%--<li><a href="#interpro-match-table" title="Table view"><span class="ico-table"></span></a></li>--%>
                           <li class="selector_tab"></li>
                           <li><a href="#interpro-match-pie" title="Pie chart view"><span class="ico-pie"></span></a></li>
                               <%--<li><a href="#interpro-match-bar" title="Bar chart view"><span class="ico-barh"></span></a></li>--%>
                               <%--<li><a href="#interpro-match-col" title="Stacked column chart view"><span class="ico-col"></span></a></li>--%>
                               <%--<li><a href="#interpro-match-Krona" title="Krona interactive chart view"><span class="ico-krona"></span></a></li>--%>
                           <div class="ico-download" id="toolbar_div" style="display:none;"><a class="icon icon-functional"
                                                                                               data-icon="=" id="csv"
                                                                                               href="#" title=""></a></div>
                       </ul>


                       <div id="interpro-match-pie">

                           <div class="chart_container">

                                   <%--<div id="func_chart_div1"></div>--%>

                               <div id="func_chart_pie_ipro"></div>

                               <div id="func_dashboard">
                                   <div id="func_table_filter"></div>
                                   <div id="func_table_pie_ipro"></div>
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
                   <div class="msg_error">No InterPro match result files have been associated with this sample.</div>
               </c:otherwise>
           </c:choose>

           <h3>GO Terms annotation</h3>

           <p>A summary of Gene Ontology (GO) terms derived from InterPro matches to your sample is provided in the charts
               below.</p>

           <c:choose>
               <%--<c:when test="${empty model.sample.analysisCompleted}">--%>
                   <%--<b>Analysis in progress</b>--%>
               <%--</c:when>--%>
               <c:when test="${not empty model.sample.analysisCompleted && !model.analysisStatus.functionalAnalysisTab.goSectionDisabled}">
                   <div id="tabs-chart">

                           <%--Tabs--%>
                       <ul>
                           <li class="selector_tab">Switch view:</li>
                               <%--<li><a href="#go-terms-table" title="Table view"><span class="ico-table"></span></a></li>--%>
                           <li><a href="#go-terms-bar" title="Bar chart view"><span class="ico-barh"></span></a></li>
                           <li><a href="#go-terms-pie" title="Pie chart view"><span class="ico-pie"></span></a></li>

                       <%--<li><a href="#go-terms-col" title="Stacked column chart view"><span class="ico-col"></span></a></li>--%>
                               <%--<li><a href="#go-terms-Krona" title="Krona interactive chart view"><span class="ico-krona"></span></a></li>--%>
                               <%--<li class="ico-downl"><a class="icon icon-functional" data-icon="=" href="#download" title="Download image/table"></a></li>--%>
                       </ul>


                       <div id="go-terms-bar">
                           <div class="go-chart">
                               <div id="func_chart_bar_go_bp"></div>
                               <div id="func_chart_bar_go_mf"></div>
                               <div id="func_chart_bar_go_cc"></div>
                           </div>
                       </div>

                       <div id="go-terms-pie">
                           <div class="go-chart">
                               <div id="func_chart_pie_go_bp"></div>
                               <div id="func_chart_pie_go_mf"></div>
                               <div id="func_chart_pie_go_cc"></div>
                           </div>
                       </div>

                   </div>
               </c:when>
               <c:otherwise>
                   <div class="msg_error">No GO Terms annotation have been associated with this sample.</div>
               </c:otherwise>
           </c:choose>

        </c:when>
        <c:otherwise>

            <div class="msg_error">No functional result files have been associated with this sample.</div>
        </c:otherwise>

        </c:choose>

    </div>

</div>
    <%--end div fragment functional--%>

<div id="fragment-quality">
        <%--BEGIN READS SECTION   --%>

        <%--<h3>Submitted nucleotide data</h3>--%>
    <p>The chart below shows the number of sequence reads which pass each of the quality control steps we
        have implemented in our pipeline. Note that, for paired-end data, sequence merging may have
        occurred and so the initial number of reads may differ from what is in the ENA. For more details
        about the data processing we employ, please see the <a href="<c:url value="${baseURL}/info#analysis"/>"  title="About Metagenomics">about page</a>.</p>
    <c:choose>
        <%--<c:when test="${empty model.sample.analysisCompleted}">--%>
            <%--<b>Analysis in progress</b>--%>
        <%--</c:when>--%>
        <c:when test="${not empty model.sample.analysisCompleted && !model.analysisStatus.qualityControlTabDisabled}">
            <div style="display:block; overflow: auto;">
                <c:url var="statsImage" value="/getImage" scope="request">
                    <c:param name="imageName" value="/charts/qc.png"/>
                    <c:param name="imageType" value="PNG"/>
                    <c:param name="dir" value="${model.emgFile.fileID}"/>
                </c:url>
                <div style="float:left; margin-left: 9px;"><img src="<c:out value="${statsImage}"/>"/></div>
            </div>
        </c:when>
        <c:otherwise>
            <p>No result files have been associated with this sample.</p>
        </c:otherwise>
    </c:choose>

</div>

<div id="fragment-download">

    <div class="box-export">
        <p>You can download in this section the full set of analysis results files and the original raw sequence reads.</p>
        <h4>Sequence data</h4>
        <ul>
            <c:forEach var="downloadLink" items="${model.downloadSection.seqDataDownloadLinks}" varStatus="loop">
                    <c:choose>
                        <c:when test="${downloadLink.externalLink}">
                            <li>
                                <a href="${downloadLink.linkURL}"
                                   title="${downloadLink.linkTitle}">${downloadLink.linkText}</a>
                            </li>
                        </c:when>
                        <c:when test="${!downloadLink.externalLink && not empty model.sample.analysisCompleted}">
                            <li>
                                <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                                   title="${downloadLink.linkTitle}">${downloadLink.linkText}</a><span
                                    class="list_date"> - ${downloadLink.fileSize}</span>
                            </li>
                        </c:when>
                        <c:otherwise>
                        </c:otherwise>
                    </c:choose>
            </c:forEach>
        </ul>
        <c:if test="${not empty model.downloadSection.funcAnalysisDownloadLinks && not empty model.sample.analysisCompleted}">
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
        <c:if test="${not empty model.downloadSection.taxaAnalysisDownloadLinks && not empty model.sample.analysisCompleted}">
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
    $("#navtabs").tabs({${model.analysisStatus.disabledOption}});
//    $("#navtabs").tabs({ selected:2 });
    $("#interpro-chart").tabs();
    $("#tabs-chart").tabs({ selected:0  });
    $("#tabs-taxchart").tabs({${model.analysisStatus.taxonomicAnalysisTab.tabsOptions}});
//    $("#tabs-taxchart").tabs({ selected:3  });



    // fix the auto-scrolling issue when linking from homepage using htag
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
