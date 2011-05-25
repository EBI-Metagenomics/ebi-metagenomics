<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                        <li><a class="list_more" href="<c:url value="${pub.url}"/>"><c:out value="${pub.pubTitle}"/></a></li>

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

<span class="subtitle">Sample overview <a href="<c:url value="${baseURL}/sample/${model.sample.sampleId}"/>" style="font-size:90%;"> (${model.sample.sampleId})</a></span>

<c:choose>
    <c:when test="${not empty model.sample.sampleName}">
        <c:set var="sampleName" value="${model.sample.sampleName}"/>
    </c:when>
    <c:otherwise><c:set var="sampleName" value="${notGivenId}"/></c:otherwise>
</c:choose>

<h2>${model.sample.sampleName}</h2>

<c:if test="${!model.sample.public}">
    Private data <img alt="private" src="${pageContext.request.contextPath}/img/icon_priv_private.gif">
</c:if>

<p><a class="analysis" href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}"/>">
   <img src="<c:url value="${baseURL}/img/ico_analysis_chart.png"/>" alt="view analysis results and statistics"> View analysis
    results </a></p>

<%-- Removed to be consistent with the Project page
<c:if test="${isDialogOpen==false}">
    <p><span style="color:red">No export data available for that sample!</span></p>
</c:if>
<div class="export">
    <a href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/doExportDetails"/>" id="csv_plus" title="Export more detailed information about this sample in CSV format">Export detailed info (CSV)</a>
</div>--%>

<h3 id="sample_desc" style="margin-top:30px;">Description</h3>

<div class="output_form" >
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
            <div class="result_row"><label>Species:</label>
                <c:choose>
                <c:when test="${not empty model.sample.hostTaxonomyId && model.sample.hostTaxonomyId>0}">
                    <%--To make it working--%>
                <span>Homo sapiens, (human) <a class="ext"
                                               href="<c:url value="http://www.ncbi.nlm.nih.gov/taxonomy?Db=taxonomy&Cmd=DetailsSearch&Term=${model.sample.hostTaxonomyId}[uid]"/>">Tax
                    ID <c:out value="${model.sample.hostTaxonomyId}"/></a> </span></div>
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
            <div class="result_row"><label>Sex:</label> <span style="text-transform:lowercase;"><c:out
                    value="${hostSex}"/></span></div>

            <div class="result_row"><label>Phenotype:</label> <span><c:out value="${phenotype}"/></span></div>
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
            <div class="result_row"><label>Biome:</label> <span><c:out value="${environmentalBiome}"/></span></div>

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
            <div class="result_row"><label>Material:</label> <span><c:out value="${environmentalMaterial}"/></span>
            </div>
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
            <div class="result_row"><label>Longitude/Latitude:</label>
    <span><c:out value="${latLon}"/>
        <%-- Use of Google API, where parameter q is the query string, parameter z specifies the zoom factor
        and t the map type (value k stands for satellite)--%>
    <a class="ext" href="<c:url value="http://maps.google.com/maps?q=${latLon}(Info%20Window)&t=k&z=5"/>">View map</a><br>
    <a  href="<c:url value="http://maps.google.com/maps?q=${latLon}(Info%20Window)&t=k&z=5"/>"><img src="http://maps.google.com/maps/api/staticmap?center=${latLon}&zoom=1&size=70x70&maptype=roadmap
&markers=color:blue%7C${latLon}&sensor=false" alt="" style="border:1px solid grey;margin-left:16px;"></a>
    </span>
            </div>
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
    <div class="result_row"><label>Geographic location:</label> <span><c:out value="${geoLocName}"/></span></div>
</div>


<c:choose>
    <c:when test="${not empty model.sample.collectionDate || not empty model.sample.miscellaneous}">

        <h3>Other information</h3>

        <div class="output_form" id="large">
            <c:choose>
                <c:when test="${not empty model.sample.collectionDate}">
                    <c:set var="collectionDate" value="${model.sample.collectionDate}"/>
                    <div class="result_row"><label>Collection date:</label>

                     <span><fmt:formatDate value="${collectionDate}" pattern="dd-MMM-yyyy"/></span></div>
                </c:when>
                <%--<c:otherwise><c:set var="collectionDate" value="${notGivenId}"/></c:otherwise>--%>
            </c:choose>


        <c:choose>
            <c:when test="${not empty model.sample.miscellaneous }">
                <c:set var="miscellaneous" value="${model.sample.miscellaneous}"/>
 <div class="result_row"><label>Miscellaneous:</label> <span><c:out value="${miscellaneous}"/></span></div>
            </c:when>
             <%--<c:otherwise><c:set var="miscellaneous" value="${notGivenId}"/></c:otherwise>--%>
        </c:choose>

        </div>
    </c:when>
</c:choose>

<h3>Submitted nucleotide data</h3>

<div class="output_form" id="large">
    <div class="result_row"><label>Raw sequence reads:</label>
 <span>
            <c:choose>
                <c:when test="${not empty model.archivedSequences}">
                    <c:forEach var="seqId" items="${model.archivedSequences}" varStatus="status">
                        <a class="ext" href="<c:url value="http://www.ebi.ac.uk/ena/data/view/${seqId}"/>">
                            <c:out value="${seqId}"/></a>
                    </c:forEach> (ENA website)
                </c:when>
                <c:otherwise>(not given)</c:otherwise>
            </c:choose></span></div>
</div>

 <div class="but_top"><a href="#top" title="back to the top page">Top</a></div>
</div>
