<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:choose>
<c:when test="${not empty model.sample}">
<div class="title_tab">
<span class="subtitle">Sample <a href="<c:url value="${baseURL}/sample/${model.sample.sampleId}"/>"> (${model.sample.sampleId})</a></span>

<h2>${model.sample.sampleName}</h2>
</div>

<div class="sample_ana">
<div id="tabs">

  <ul>
    <li><a href="#fragment-overview"><span>Overview</span></a></li>
    <li><a href="#fragment-taxonomy"><span>Taxonomy analysis</span></a></li>
    <li><a href="#fragment-functional"><span>Functional analysis</span></a></li>
    <li><a href="#fragment-functional"><span>Comparison tool</span></a></li>
    <li><a href="#fragment-download"><span>Downloads</span></a></li>
  </ul>




  <div id="fragment-overview">

      <%--<c:choose>--%>
          <%--<c:when test="${not empty model.publications}">--%>

              <div id="sidebar-allrel">
                  <c:choose>
                    <c:when test="${not empty model.sample.analysisCompleted}">

                           <h3>Data processed</h3>


                            <c:url var="statsImage" value="/getImage" scope="request">
                                <c:param name="imageName" value="_summary.png"/>
                                <c:param name="imageType" value="PNG"/>
                                <c:param name="dir" value="${model.emgFile.fileID}"/>
                            </c:url>
                            <p><img  style="height:94px;" src="<c:out value="${statsImage}"/>"/></p>




                    </c:when>
                    <c:otherwise>
                        <h3>Analysis in progress!</h3>
                    </c:otherwise>
                </c:choose>
              <h3>Download</h3>

                    <div id="box-export">
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
                        </div>




                  <div id="sidebar-related">
                      <h2>Related resources</h2>
                      <span class="separator"></span>
                      <%--<ul>--%>
                          <%--<c:forEach var="pub" items="${model.publications}" varStatus="status">--%>
                              <%--<li>--%>
                                  <%--<c:if test="${pub.pubType == 'PUBLICATION'}">--%>
                                      <%--<a class="list_more" href="<c:url value="http://dx.doi.org/${pub.doi}"/>"><c:out--%>
                                              <%--value="${pub.pubTitle}"/></a><br/>--%>
                                      <%--<i><c:out value="${pub.shortAuthors}"/></i><br/>--%>
                                      <%--<c:out value="${pub.year}"/> <c:out value="${pub.volume}"/><br/>--%>
                                  <%--</c:if>--%>
                                  <%--<c:if test="${pub.pubType == 'WEBSITE_LINK'}">--%>
                                      <%--<a class="list_more" href="<c:url value="${pub.url}"/>"><c:out--%>
                                              <%--value="${pub.pubTitle}"/></a>--%>
                                  <%--</c:if>--%>
                              <%--</li>--%>
                          <%--</c:forEach>--%>
                      <%--</ul>--%>
                  </div>
              </div>
          <%--</c:when>--%>
          <%--<c:otherwise>--%>

      <%----%>
           <%--&lt;%&ndash;<p>There is no resources available for this sample.<br/></p>&ndash;%&gt;--%>

          <%--</c:otherwise>--%>
      <%--</c:choose>--%>



   <div class="main_tab_content" style="border:0px red solid; width: 72%;">
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

    <div class="result_row"><label>Classification:</label><span> <c:out value="${sampleClassification}"/></span></div>

    </div>
    <%--END DESCRIPTION--%>

 <%--BEGIN ENVIRONMENTAL/HOST ASSOCIATED    --%>
<%--<c:choose>--%>

         <%--<c:when test="${model.hostAssociated}">--%>
             <%--<h3>Host associated</h3>--%>

             <%--<div class="output_form" id="large">--%>
                 <%--<c:choose>--%>
                     <%--<c:when test="${not empty model.sample.phenotype}">--%>
                         <%--<c:set var="phenotype" value="${model.sample.phenotype}"/>--%>
                     <%--</c:when>--%>
                     <%--<c:otherwise>--%>
                         <%--<c:set var="phenotype" value="${notGivenId}"/>--%>
                     <%--</c:otherwise>--%>
                 <%--</c:choose>--%>
                 <%--<div class="result_row"><label>Species:</label>--%>
                     <%--<c:choose>--%>
                     <%--<c:when test="${not empty model.sample.hostTaxonomyId && model.sample.hostTaxonomyId>0}">--%>
                     <%--<span><c:out value="${model.sample.species}"/> <a class="ext"--%>
                                                                       <%--href="<c:url value="http://www.uniprot.org/taxonomy/${model.sample.hostTaxonomyId}"/>">Tax--%>
                         <%--ID <c:out value="${model.sample.hostTaxonomyId}"/></a> </span></div>--%>
                 <%--</c:when>--%>
                 <%--<c:otherwise>--%>
                     <%--<c:out value="${notGivenId}"/>--%>
                 <%--</c:otherwise>--%>
                 <%--</c:choose>--%>

                 <%--<c:choose>--%>
                     <%--<c:when test="${not empty model.sample.hostSex}">--%>
                         <%--<c:set var="hostSex" value="${model.sample.hostSex}"/>--%>
                     <%--</c:when>--%>
                     <%--<c:otherwise>--%>
                         <%--<c:set var="hostSex" value="${notGivenId}"/>--%>
                     <%--</c:otherwise>--%>
                 <%--</c:choose>--%>
                 <%--<div class="result_row"><label>Sex:</label> <span style="text-transform:lowercase;"><c:out--%>
                         <%--value="${hostSex}"/></span></div>--%>

                 <%--<div class="result_row"><label>Phenotype:</label> <span><c:out value="${phenotype}"/></span></div>--%>
             <%--</div>--%>
         <%--</c:when>--%>

         <%--<c:otherwise>--%>
             <%--<h3>Environmental conditions</h3>--%>

             <%--<div class="output_form" id="large">--%>
                 <%--<c:choose>--%>
                     <%--<c:when test="${not empty model.sample.environmentalBiome}">--%>
                         <%--<c:set var="environmentalBiome" value="${model.sample.environmentalBiome}"/>--%>
                     <%--</c:when>--%>
                     <%--<c:otherwise>--%>
                         <%--<c:set var="environmentalBiome" value="${notGivenId}"/>--%>
                     <%--</c:otherwise>--%>
                 <%--</c:choose>--%>
                 <%--<div class="result_row"><label>Biome:</label> <span><c:out value="${environmentalBiome}"/></span></div>--%>

                 <%--<c:choose>--%>
                     <%--<c:when test="${not empty model.sample.environmentalFeature}">--%>
                         <%--<c:set var="environmentalFeature" value="${model.sample.environmentalFeature}"/>--%>
                     <%--</c:when>--%>
                     <%--<c:otherwise>--%>
                         <%--<c:set var="environmentalFeature" value="${notGivenId}"/>--%>
                     <%--</c:otherwise>--%>
                 <%--</c:choose>--%>
                 <%--<div class="result_row"><label>Experimental feature:</label> <span><c:out--%>
                         <%--value="${environmentalFeature}"/></span></div>--%>
                 <%--<c:choose>--%>
                     <%--<c:when test="${not empty model.sample.environmentalMaterial}">--%>
                         <%--<c:set var="environmentalMaterial" value="${model.sample.environmentalMaterial}"/>--%>
                     <%--</c:when>--%>
                     <%--<c:otherwise>--%>
                         <%--<c:set var="environmentalMaterial" value="${notGivenId}"/>--%>
                     <%--</c:otherwise>--%>
                 <%--</c:choose>--%>
                 <%--<div class="result_row"><label>Material:</label> <span><c:out value="${environmentalMaterial}"/></span>--%>
                 <%--</div>--%>
             <%--</div>--%>
         <%--</c:otherwise>--%>
     <%--</c:choose>--%>
     <%--END ENVIRONMENTAL/HOST ASSOCIATED   --%>

     <%--BEGIN LOCALISATION   --%>
      <h3>Localisation</h3>

      <div class="output_form" id="large" style="overflow:auto;">

          <%--<c:if test="${!model.hostAssociated}">--%>
              <%--<c:choose>--%>
                  <%--<c:when test="${not empty model.sample.latitude}">--%>
                      <%--<c:set var="latLon" value="${model.sample.latitude} , ${model.sample.longitude}"/>--%>
                      <%--<div id="map_canvas"></div>--%>
                      <%--<script language="javascript"> initialize(${model.sample.latitude}, ${model.sample.longitude})</script>--%>
                  <%--</c:when>--%>
                  <%--<c:otherwise>--%>
                      <%--<c:set var="latLon" value="${notGivenId}"/>--%>
                  <%--</c:otherwise>--%>
              <%--</c:choose>--%>
              <%--<div class="result_row"><label>Latitude/Longitude:</label> <span><c:out value="${latLon}"/></span></div>--%>
          <%--</c:if>--%>

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

    <%--BEGIN READS SECTION   --%>
    <h3>Submitted nucleotide data</h3>

     <div class="output_form" id="large">
         <div class="result_row"><label>Raw sequence reads:</label>
      <span>
         <c:choose>
             <c:when test="${not empty model.archivedSequences}">
                 <c:forEach var="seqId" items="${model.archivedSequences}" varStatus="status">
                     <a class="ext" href="<c:url value="https://www.ebi.ac.uk/ena/data/view/${seqId}"/>">
                         <c:out value="${seqId}"/></a>
                 </c:forEach> (ENA website)
             </c:when>
             <c:otherwise>(not given)</c:otherwise>
         </c:choose></span></div>
     </div>
     <%--END READS SECTION   --%>

     <%--BEGIN OTHER INFO   --%>
      <h3>Other information</h3>

      <%--<c:if test="${not empty model.sampleAnnotations}">--%>
          <%--<table border="1" class="result">--%>
              <%--<thead>--%>
              <%--<tr>--%>
                  <%--<c:set var="headerWidth" value="" scope="page"/>--%>
                  <%--<c:set var="headerId" value="" scope="page"/>--%>
                  <%--<th id="${headerId}" abbr="${headerName}" width="${headerWidth}" scope="col">Annotation</th>--%>
                  <%--<th id="${headerId}" abbr="${headerName}" width="${headerWidth}" scope="col">Value</th>--%>
              <%--</tr>--%>
              <%--</thead>--%>
              <%--<tbody>--%>
              <%--<c:forEach var="annotation" items="${model.sampleAnnotations}" varStatus="status">--%>
                  <%--<tr>--%>
                      <%--<td style="text-align:left;" id="ordered">${annotation.annotationName}</td>--%>
                      <%--<td style="text-align:left;">${annotation.annotationValue} ${annotation.unit}</td>--%>
                  <%--</tr>--%>
              <%--</c:forEach>--%>
              <%--</tbody>--%>
          <%--</table>--%>
      <%--</c:if>--%>

     <%--END OTHER INFO   --%>


  </div>
</div>









  <div id="fragment-taxonomy">
      <aside>
      <div id="sidebar-download">

      <h3>Download results</h3>

      <div id="box-export">
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

      </aside>
      <h3>Taxonomy analysis</h3>
      <table class="result">
        <tr>
          <td width="10%" style="background-color:white;padding:0;"><object class="krona_chart_small" data="<c:url value="${baseURL}/Krona_chart_taxonomy_simple?depth=1&font=11"/>" type="text/html"></object></td>
          <td rowspan="2" style="background-color:white;padding:0;"><object class="krona_chart" data="<c:url value="${baseURL}/Krona_chart_taxonomy?font=10"/>" type="text/html"></object></td>
        </tr>
          <tr>
          <td style="background-color:white;padding:0;"><object class="krona_chart_small" data="<c:url value="${baseURL}/Krona_chart_taxonomy_simple?node=1&depth=2&font=11"/>" type="text/html"></object></td>
        </tr>
      </table>
  </div>










  <div id="fragment-functional">
      <aside>
      <div id="sidebar-download">

      <h3>Download results</h3>

      <div id="box-export">

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

      </div>
      </div>

      </aside>

      <h3>Functional analysis</h3>

              <p>The entire InterProScan results file (<a title="Click to download full InterPro matches table (TSV)"
                                                          href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportI5TSVFile"/>">download
                  here</a>) has been used to produce the following summaries.</p>

              <h4>InterPro match summary</h4>

              <p>Top five most frequently found InterPro matches to this sample:</p>
              <c:choose>
                  <c:when test="${not empty model.interProEntries}">
                      <div id="small"> <div class="export">
                          <a id="csv"
                             href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportIPRFile"/>"
                             title="<spring:message code="analysisStatsView.label.download.i5.table.view"/>">
                              <spring:message code="analysisStatsView.label.download.i5.table.view"/><%-- <c:out
                          value="${model.emgFile.fileSizeMap['_summary.ipr']}"/>--%>
                          </a>
                      </div>
                      </div>
                      <table border="1" class="result" id="small">
                          <thead>
                          <tr>
                              <th scope="col" abbr="IEid" width="90px">InterPro ID</th>
                              <th scope="col" abbr="IEname" id="h_left">Entry name</th>
                              <th scope="col" abbr="IEnum" width="130px">Proteins matched</th>
                          </tr>
                          </thead>
                          <tbody>
                          <c:forEach var="entry" items="${model.interProEntries}" varStatus="status">
                              <tr>
                                  <td>
                                      <c:url var="linkToInterProSearch" value="http://www.ebi.ac.uk/interpro/search">
                                          <c:param name="q" value="${entry.entryID}"/>
                                      </c:url>
                                      <a href="<c:out value="${linkToInterProSearch}"/>"
                                         title="<c:out value="Link to ${entry.entryID}"/>" class="ext">
                                          <c:out value="${entry.entryID}"/>
                                      </a>
                                  </td>
                                  <td style="text-align:left;">${entry.entryDescription}</td>
                                  <td id="ordered">${entry.numOfEntryHits}</td>
                              </tr>
                          </c:forEach>
                          </tbody>
                      </table>
                      <c:set var="showFullTableID" value="View full table"/>
                      <p><a title="<c:out value="${showFullTableID}"/>"
                            href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/showProteinMatches"/>">
                          <c:out value="${showFullTableID}"/></a></p>

                  </c:when>
                  <c:otherwise>
                      <b><c:out value="${noDisplayID}"/></b>
                  </c:otherwise>
              </c:choose>


              <h4>Annotation by GO Terms</h4>

              <p>A summary of Gene Ontology (GO) terms derived from InterPro matches to your sample is provided in the chart
                  below. You can view the complete list of GO terms by clicking on the download icon.</p>


              <div class="export" style="float:none; margin-left:9px;">
                  <c:if test="${not empty model.pieChartBiologicalProcessURL}">

                      <%-- remove detailed TSV export : available on top
                <a title="<spring:emailMessage code="analysisStatsView.label.download.goterms.full.csv"/>" href="<c:url
                         value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportGOFile/${model.emgFile.fileName}"/>">
                     <spring:emailMessage code="analysisStatsView.label.download.goterms.full.csv"/></a>--%>


                      <a id="csv"
                         title="<spring:message code="analysisStatsView.label.download.go.slim.anchor.title"/>"
                         href="<c:url
                          value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportGOSlimFile"/>">
                          <spring:message
                                  code="analysisStatsView.label.download.go.slim.anchor.href.message"/></a>
                  </c:if>
              </div>
              <table class="result">
                        <tr>
                          <td width="10%" style="background-color:white;padding:0;vertical-align: top;"><object class="krona_chart_small" style="height:323px;" data="<c:url value="${baseURL}/Krona_chart_function_simple?depth=1&font=11"/>" type="text/html"></object></td>
                          <td style="background-color:white;padding:0;">
               <%--<a href="http://localhost:8082/metagenomics/Krona_chart_function"  class="icon icon-functional" data-icon="F" title="Open full screen" style="float:right; margin: 10px 4px 0 0; font-size: 283%;"></a>--%>
                              <object class="krona_chart" data="<c:url value="${baseURL}/Krona_chart_function?font=10"/>" type="text/html"></object></td>
                        </tr>

                      </table>


                  <div id="go-chart">
                      <div id="go-chart-process">
                          <h2>Biological process</h2>
                          <c:url var="bioImage" value="/getImage" scope="request">
                              <c:param name="imageName" value="_summary_biological_process.png"/>
                              <c:param name="imageType" value="PNG"/>
                              <c:param name="dir" value="${model.emgFile.fileID}"/>
                          </c:url>
                          <p><img src="<c:out value="${bioImage}"/>"/></p>
                              <%--<b><c:out value="${noDisplayID}"/></b>--%>
                      </div>

                      <div id="go-chart-molecular">
                          <h2>Molecular function</h2>
                          <c:url var="molecularImage" value="/getImage" scope="request">
                              <c:param name="imageName" value="_summary_molecular_function.png"/>
                              <c:param name="imageType" value="PNG"/>
                              <c:param name="dir" value="${model.emgFile.fileID}"/>
                          </c:url>
                          <p><img src="<c:out value="${molecularImage}"/>"/></p>
                      </div>
                      <div id="go-chart-cellular">
                          <h2>Cellular component</h2>
                          <c:url var="cellImage" value="/getImage" scope="request">
                              <c:param name="imageName" value="_summary_cellular_component.png"/>
                              <c:param name="imageType" value="PNG"/>
                              <c:param name="dir" value="${model.emgFile.fileID}"/>
                          </c:url>
                          <p><img src="<c:out value="${cellImage}"/>"/></p>
                      </div>
                  </div>


  </div>









  <div id="fragment-download">


      <h3>Download results</h3>

      <div id="box-export">
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
</div>

<script>

$( "#tabs" ).tabs({ disabled: [3] });

</script>












</c:when>
<c:otherwise>
    <h3>Sample ID Not Recognised</h3>
</c:otherwise>
</c:choose>


<div class="but_top"><a href="#top" title="back to the top page">Top</a></div>