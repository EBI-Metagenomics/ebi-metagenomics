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
            <%--<h3>Taxonomy analysis</h3>--%>
        <h3>Top taxonomy Hits</h3>

        <div id="tabs-taxchart">

                <%--Tabs--%>
            <ul>
                <li class="selector_tab">Switch view:</li>
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
                        <div id="tax_chart_pie_dom"></div>
                        <div id="tax_chart_pie_phy"></div>

                        <div id="tax_dashboard">
                            <div id="tax_table_filter"></div>
                            <div id="tax_table_pie"></div>
                        </div>

                    </div>
                </div>
            </div>

            <div id="tax-bar">
                <div class="chart_container">
                    <div id="tax_chart_bar_dom"></div>
                    <div id="tax_chart_bar_phy"></div>
                    <div id="tax_table_bar"></div>
                </div>
            </div>

            <div id="tax-col">
                <div class="chart_container">
                    <div id="tax_chart_col"></div>
                    <div id="tax_table_col"></div>
                </div>
                    <%--<div id="tax-col">--%>
                    <%--<div class="chart_container">--%>
                    <%--<div class="chart_container">--%>
                    <%--<div id="tax_chart_col"></div>--%>
                    <%--<div id="tax_table_col">--%>
                    <%--&lt;%&ndash;<div class="tax_table_filter"></div>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<div class="tax_table_pie"></div>&ndash;%&gt;--%>
                    <%--</div>--%>

                    <%--</div>--%>
                    <%--</div>--%>
                    <%--</div>--%>

            </div>
            <div id="tax-Krona">
                <object class="krona_chart"
                        data="<c:url value="${baseURL}/sample/${model.sample.sampleId}/krona?taxonomy=true&collapse=false"/>"
                        type="text/html"></object>
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
                <b>No data available</b>
            </c:otherwise>
        </c:choose>

        <h3>GO Terms annotation</h3>

        <p>A summary of Gene Ontology (GO) terms derived from InterPro matches to your sample is provided in the charts
            below.</p>

        <div id="tabs-chart">

                <%--Tabs--%>
            <ul>
                <li class="selector_tab">Switch view:</li>
                    <%--<li><a href="#go-terms-table" title="Table view"><span class="ico-table"></span></a></li>--%>
                <li><a href="#go-terms-pie" title="Pie chart view"><span class="ico-pie"></span></a></li>
                <li><a href="#go-terms-bar" title="Bar chart view"><span class="ico-barh"></span></a></li>
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

    </div>

</div>
    <%--end div fragment functional--%>

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
            <h3>Data processed</h3>

            <p>Analysis in progress!</p>
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
    <%--$( "#navtabs").tabs({ ${model.analysisStatus.disabledAttribute} });--%>
    $("#navtabs").tabs({});
    $("#interpro-chart").tabs();
    //    $( "#interpro-chart" ).tabs({ disabled: [1,2,3,4], selected: 0 });
    $("#tabs-chart").tabs({ selected:1  });
    $("#tabs-taxchart").tabs({ disabled:[5], selected:0 });

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
