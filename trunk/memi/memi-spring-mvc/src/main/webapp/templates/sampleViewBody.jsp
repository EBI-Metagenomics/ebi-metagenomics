<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--Page variable which is used several time within this page. Used for not specified study attributes.--%>
<c:set var="notGivenId" value="(not given)"/>
<div id="content-full">


  <c:choose>
    <c:when test="${not empty model.pubs}">
  <div id="sidebar-analysis">
  <div id="sidebar-related">
          <h2>Related resources</h2>
           <span class="separator"></span>
           <ul>
        <c:forEach var="pub" items="${model.pubs}" varStatus="status">
            <li><c:out value="${pub.pubTitle}"/> <br/><a  style="font-size:110%;" class="ext" href="<c:url value="${pub.url}"/>">HTML</a></li>
           
        </c:forEach>
               </ul>
  </div>
 </div>
      
    </c:when>
    <c:otherwise>
<%--<div id="sidebar-analysis">
<div id="sidebar-steps">
        <h2>Resources</h2>
         <span class="separator"></span>

<p>There is no resources available for this sample.<br/></p>
</div>
   </div>--%>
 </c:otherwise>
</c:choose>

<span class="subtitle" >Sample overview</span>
<c:choose>
    <c:when test="${not empty model.sample.sampleName}">
        <c:set var="sampleName" value="${model.sample.sampleName}"/>
    </c:when>
    <c:otherwise><c:set var="sampleName" value="${notGivenId}"/></c:otherwise>
</c:choose>

<h2>${model.sample.sampleName}</h2>
<br/>Sample ID: ${model.sample.sampleId}
<br/>
Private data <img alt="private" src="${pageContext.request.contextPath}/img/icon_priv_private.gif">

<p><a class="list_sample" href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}"/>"> <img src="<c:url value="${baseURL}/img/ico_analysis.png"/>" alt="Analysis results and statistics"> View analysis results and statistics</a></p>

<%-- Removed to be consistent with the Project page
<c:if test="${isDialogOpen==false}">
    <p><span style="color:red">No export data available for that sample!</span></p>
</c:if>
<div class="export">
    <a href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/doExportDetails"/>" id="csv_plus" title="Export more detailed information about this sample in CSV format">Export detailed info (CSV)</a>
</div>--%>

<h3 id="sample_desc" style="margin-top:40px;">Description</h3>

<c:choose>
<c:when test="${not empty model.sample.sampleDescription}">
<c:set var="sampleDescription" value="${model.sample.sampleDescription}"/>
</c:when>
<c:otherwise><c:set var="sampleDescription" value="${notGivenId}"/></c:otherwise>
</c:choose>
<div class="output_form">
<p style="font-size:110%;"><c:out value="${sampleDescription}"/></p>

        <c:choose>
            <c:when test="${not empty model.sample.sampleClassification}">
                <c:set var="sampleClassification" value="${model.sample.sampleClassification}"/>
            </c:when>
            <c:otherwise>
                <c:set var="sampleClassification" value="${notGivenId}"/>
            </c:otherwise>
        </c:choose>

Classification: <c:out value="${sampleClassification}"/>

</div>
<c:choose>
    
<c:when test="${model.hostAssociated}">
<h3>Host associated</h3>
<div class="output_form" id="large">
                <c:choose>
                    <c:when test="${not empty model.sample.phenotype}">
                        <c:set var="phenotype" value="${model.sample.phenotype}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="phenotype" value="${notGivenId}"/>
                    </c:otherwise>
                </c:choose>
  <label>Species:</label>
                <c:choose>
                    <c:when test="${not empty model.sample.hostTaxonomyId && model.sample.hostTaxonomyId>0}">
               <%--To make it working--%>
               <span>Homo sapiens, (human) <a class="ext" href="<c:url value="http://www.ncbi.nlm.nih.gov/taxonomy?Db=taxonomy&Cmd=DetailsSearch&Term=${model.sample.hostTaxonomyId}[uid]"/>">Tax ID <c:out value="${model.sample.hostTaxonomyId}"/></a> </span><br/><br/>
                    </c:when>
                    <c:otherwise>
                       <c:out value="${notGivenId}"/>
                    </c:otherwise>
                </c:choose>

    <c:choose>
                    <c:when test="${not empty model.sample.hostSex}">
                        <c:set var="hostSex" value="${model.sample.hostSex}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="hostSex" value="${notGivenId}"/>
                    </c:otherwise>
                </c:choose>
  <label>Sex:</label> <span style="text-transform:lowercase;"><c:out value="${hostSex}"/></span><br/><br/>

  <label>Phenotype:</label> <span ><c:out value="${phenotype}"/></span>
</div>
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
      <label>Biome:</label> <span><c:out value="${environmentalBiome}"/></span><br/><br/>

                       <c:choose>
                        <c:when test="${not empty model.sample.environmentalFeature}">
                            <c:set var="environmentalFeature" value="${model.sample.environmentalFeature}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="environmentalFeature" value="${notGivenId}"/>
                        </c:otherwise>
                    </c:choose>
     <label>Experimental feature:</label> <span><c:out value="${environmentalFeature}"/></span><br/><br/>
                    <c:choose>
                        <c:when test="${not empty model.sample.environmentalMaterial}">
                            <c:set var="environmentalMaterial" value="${model.sample.environmentalMaterial}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="environmentalMaterial" value="${notGivenId}"/>
                        </c:otherwise>
                    </c:choose>
     <label>Material:</label> <span><c:out value="${environmentalMaterial}"/></span>
      </div>
 </c:otherwise>
</c:choose>


 <h3>Localisation</h3>
     <div class="output_form" id="large">

<c:choose>
<c:when test="${model.hostAssociated}"></c:when>
<c:otherwise>

                <c:choose>
                    <c:when test="${not empty model.sample.latLon}">
                        <c:set var="latLon" value="${model.sample.latLon}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="latLon" value="${notGivenId}"/>
                    </c:otherwise>
                </c:choose>
<label>Longitude/Latitude:</label> <span><c:out value="${latLon}"/> <a class="ext" href="<c:url value="http://maps.google.com/maps?q=${latLon}"/>">View map</a> </span><br/><br/>
</c:otherwise>
</c:choose>


        <c:choose>
            <c:when test="${not empty model.sample.geoLocName}">
                <c:set var="geoLocName" value="${model.sample.geoLocName}"/>
            </c:when>
            <c:otherwise>
                <c:set var="geoLocName" value="${notGivenId}"/>
            </c:otherwise>
        </c:choose>
 <label>Geographic location:</label> <span><c:out value="${geoLocName}"/></span>
 </div>


<c:choose>
<c:when test="${not empty model.sample.collectionDate || not empty model.sample.miscellaneous}">

 <h3>Other information</h3>
   <div class="output_form" id="large">
    <c:choose>
            <c:when test="${not empty model.sample.collectionDate}">      
                <c:set var="collectionDate" value="${model.sample.collectionDate}"/>
<label>Collection date:</label> <span><c:out value="${collectionDate}"/> </span><br/><br/>
            </c:when>
            <%--<c:otherwise><c:set var="collectionDate" value="${notGivenId}"/></c:otherwise>--%>
        </c:choose>



        <c:choose>
            <c:when test="${not empty model.sample.miscellaneous}">
                <c:set var="miscellaneous" value="${model.sample.miscellaneous}"/>
 <label>Miscellaneous:</label> <span><c:out value="${miscellaneous}"/></span>
            </c:when>
             <%--<c:otherwise><c:set var="miscellaneous" value="${notGivenId}"/></c:otherwise>--%>
        </c:choose>

</div>
</c:when>
 </c:choose>
  
<h3>Submitted nucleotide data</h3>

<div class="output_form" id="large">
<label>Raw sequence reads:</label>
 <span>
            <c:choose>
                <c:when test="${not empty model.archivedSequences}">
                    <c:forEach var="seqId" items="${model.archivedSequences}" varStatus="status">
                        <a class="ext" href="<c:url value="http://www.ebi.ac.uk/ena/data/view/${seqId}"/>">
                            <c:out value="${seqId}"/></a>
                    </c:forEach> (ENA website)
                </c:when>
                <c:otherwise>(not given)</c:otherwise>
            </c:choose></span>
</div>
</div>
