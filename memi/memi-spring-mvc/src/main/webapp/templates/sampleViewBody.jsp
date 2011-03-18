<%--
  Created by Maxim Scheremetjew, EMBL-EBI, InterPro
  Date: 30-Nov-2010
  Desc: Sample overview body
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--Page variable which is used several time within this page. Used for not specified study attributes.--%>
<c:set var="notGivenId" value="(not given)"/>
<div id="content-full">
<h2>Sample Overview</h2>


<table>
    <tr>
        <td >
            <c:choose>
                <c:when test="${not empty model.sample.sampleTitle}">
                    <c:set var="sampleTitle" value="${model.sample.sampleTitle}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="sampleTitle" value="${notGivenId}"/>
                </c:otherwise>
            </c:choose>
            <h2>${model.sample.sampleTitle}</h2>
            ${model.sample.sampleId}
        </td>
    </tr>
</table>

<a href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}"/>"><h3>Sample Analysis Results and Statistics</h3></a>
<br/>

<h3 id="sample_desc">Sample description</h3>
<c:if test="${isDialogOpen==false}">
    <p><span style="color:red">No export data available for that sample!</span></p>
</c:if>


<div class="export">
    <a href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/doExportDetails"/>" id="csv_plus" title="Export more detailed information about this sample in CSV format">Export detailed info (CSV)</a>
</div>

<table>
    <tr>
        <c:choose>
            <c:when test="${not empty model.sample.sampleDescription}">
                <c:set var="sampleDescription" value="${model.sample.sampleDescription}"/>
            </c:when>
            <c:otherwise>
                <c:set var="sampleDescription" value="${notGivenId}"/>
            </c:otherwise>
        </c:choose>
        <td valign="top" align="right" width="150"><b>Description:</b></td>
        <td><c:out value="${sampleDescription}"/></td>
    </tr>
    <tr>
        <c:choose>
            <c:when test="${not empty model.sample.sampleClassification}">
                <c:set var="sampleClassification" value="${model.sample.sampleClassification}"/>
            </c:when>
            <c:otherwise>
                <c:set var="sampleClassification" value="${notGivenId}"/>
            </c:otherwise>
        </c:choose>
        <td valign="top" align="right" width="150"><b>Experimental factor:</b></td>
        <td><c:out value="${sampleClassification}"/></td>
    </tr>
    <tr>
        <td valign="top" align="right" width="150"><b>Resources:</b></td>
        <td>
            <c:choose>
                <c:when test="${not empty model.pubs}">
                    <c:forEach var="pub" items="${model.pubs}" varStatus="status">
                        <p>
                            <a href="<c:url value="${pub.url}"/>">"<c:out value="${pub.pubTitle}"/>"</a>
                        </p>
                    </c:forEach>
                </c:when>
                <c:otherwise>${notGivenId}</c:otherwise>
            </c:choose>
        </td>
    </tr>
</table>
<br/>
<table>
    <c:choose>
        <c:when test="${model.hostAssociated}">
            <tr>
                <c:choose>
                    <c:when test="${not empty model.sample.phenotype}">
                        <c:set var="phenotype" value="${model.sample.phenotype}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="phenotype" value="${notGivenId}"/>
                    </c:otherwise>
                </c:choose>
                <td valign="top" align="right" width="150"><b>Phenotype:</b></td>
                <td><c:out value="${phenotype}"/></td>
            </tr>
            <tr>
                <td valign="top" align="right" width="150"><b>Host species:</b></td>
                <c:choose>
                    <c:when test="${not empty model.sample.taxonomyId && model.sample.taxonomyId>0}">
                        <td>Tax Id:
                            <a href="<c:url value="http://www.ncbi.nlm.nih.gov/taxonomy?Db=taxonomy&Cmd=DetailsSearch&Term=${model.sample.taxonomyId}[uid]"/>"><c:out
                                    value="${model.sample.taxonomyId}"/></a>
                        </td>
                    </c:when>
                    <c:otherwise>
                        <td><c:out value="${notGivenId}"/></td>
                    </c:otherwise>
                </c:choose>
            </tr>
            <tr>
                <c:choose>
                    <c:when test="${not empty model.sample.hostSex}">
                        <c:set var="hostSex" value="${model.sample.hostSex}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="hostSex" value="${notGivenId}"/>
                    </c:otherwise>
                </c:choose>
                <td valign="top" align="right" width="150"><b>Host sex:</b></td>
                <td><c:out value="${hostSex}"/></td>
            </tr>
        </c:when>
        <c:otherwise>
            <tr>
                <c:choose>
                    <c:when test="${not empty model.sample.latLon}">
                        <c:set var="latLon" value="${model.sample.latLon}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="latLon" value="${notGivenId}"/>
                    </c:otherwise>
                </c:choose>
                <td valign="top" align="right" width="150"><b>Longitude - Latitude:</b></td>
                <td><c:out value="${latLon}"/>
                    <a href="<c:url value="http://maps.google.com/maps?q=${latLon}"/>">(Google Maps)</a>
                </td>
            </tr>
            <tr>
                <c:choose>
                    <c:when test="${not empty model.sample.environmentalBiome}">
                        <c:set var="environmentalBiome" value="${model.sample.environmentalBiome}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="environmentalBiome" value="${notGivenId}"/>
                    </c:otherwise>
                </c:choose>
                <td valign="top" align="right" width="150"><b>Biome:</b></td>
                <td><c:out value="${environmentalBiome}"/></td>
            </tr>
            <tr>
                <c:choose>
                    <c:when test="${not empty model.sample.environmentalFeature}">
                        <c:set var="environmentalFeature" value="${model.sample.environmentalFeature}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="environmentalFeature" value="${notGivenId}"/>
                    </c:otherwise>
                </c:choose>
                <td valign="top" align="right" width="150"><b>Experimental feature:</b></td>
                <td><c:out value="${environmentalFeature}"/></td>
            </tr>
            <tr>
                <c:choose>
                    <c:when test="${not empty model.sample.environmentalMaterial}">
                        <c:set var="environmentalMaterial" value="${model.sample.environmentalMaterial}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="environmentalMaterial" value="${notGivenId}"/>
                    </c:otherwise>
                </c:choose>
                <td valign="top" align="right" width="150"><b>Material:</b></td>
                <td><c:out value="${environmentalMaterial}"/></td>
            </tr>
        </c:otherwise>
    </c:choose>
</table>
<br/>
<table>
    <tr>
        <c:choose>
            <c:when test="${not empty model.sample.geoLocName}">
                <c:set var="geoLocName" value="${model.sample.geoLocName}"/>
            </c:when>
            <c:otherwise>
                <c:set var="geoLocName" value="${notGivenId}"/>
            </c:otherwise>
        </c:choose>
        <td valign="top" align="right" width="150"><b>Geographic location:</b></td>
        <td><c:out value="${geoLocName}"/></td>
    </tr>
    <tr>
        <c:choose>
            <c:when test="${not empty model.sample.collectionDate}">
                <c:set var="collectionDate" value="${model.sample.collectionDate}"/>
            </c:when>
            <c:otherwise>
                <c:set var="collectionDate" value="${notGivenId}"/>
            </c:otherwise>
        </c:choose>
        <td valign="top" align="right" width="150"><b>Collection date:</b></td>
        <td><c:out value="${collectionDate}"/></td>
    </tr>
    <tr>
        <c:choose>
            <c:when test="${not empty model.sample.miscellaneous}">
                <c:set var="miscellaneous" value="${model.sample.miscellaneous}"/>
            </c:when>
            <c:otherwise>
                <c:set var="miscellaneous" value="${notGivenId}"/>
            </c:otherwise>
        </c:choose>
        <td valign="top" align="right" width="150"><b>Miscellaneous:</b></td>
        <td><c:out value="${miscellaneous}"/></td>
    </tr>
</table>
<br/>
<h3>Submitted Raw Sequence in ENA</h3>
<table >
    <tr>
        <td valign="top" align="right" width="150"><b>Raw sequence reads:</b></td>
        <td>
            <c:choose>
                <c:when test="${not empty model.archivedSequences}">
                    <c:forEach var="seqId" items="${model.archivedSequences}" varStatus="status">
                        <a href="<c:url value="http://www.ebi.ac.uk/ena/data/view/${seqId}"/>">
                            <c:out value="${seqId}"/></a><br>
                    </c:forEach>
                </c:when>
                <c:otherwise>(not given)</c:otherwise>
            </c:choose>
        </td>
    </tr>
</table>
</div>
