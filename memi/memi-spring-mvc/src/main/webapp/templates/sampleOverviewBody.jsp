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
<div id="content">
<h2>Sample Overview</h2>

<div style="margin-top:6px"></div>
<table frame="box" width="95%">
    <tr>
        <td width="50%" align="left" valign="top">
            <h2>Sample ${sample.sampleId}</h2>
            ${sample.sampleTitle}
        </td>
    </tr>
</table>
<div style="margin-top:10px"></div>
<h3>Sample Description</h3>
<div align="left">
    <a href="<c:url value="${baseURL}/sampleOverview/exportSample/${sample.sampleId}"/>">Export to CSV</a>
</div>
<table frame="box" width="95%">
    <tr>
        <c:choose>
            <c:when test="${not empty sample.sampleTitle}">
                <c:set var="sampleTitle" value="${sample.sampleTitle}"/>
            </c:when>
            <c:otherwise>
                <c:set var="sampleTitle" value="${notGivenId}"/>
            </c:otherwise>
        </c:choose>
        <td valign="top" align="right" width="150"><b>Sample name:</b></td>
        <td><c:out value="${sampleTitle}"/></td>
    </tr>
    <tr>
        <c:choose>
            <c:when test="${not empty sample.sampleDescription}">
                <c:set var="sampleDescription" value="${sample.sampleDescription}"/>
            </c:when>
            <c:otherwise>
                <c:set var="sampleDescription" value="${notGivenId}"/>
            </c:otherwise>
        </c:choose>
        <td valign="top" align="right" width="150"><b>Sample description:</b></td>
        <td><c:out value="${sampleDescription}"/></td>
    </tr>
    <tr>
        <c:choose>
            <c:when test="${not empty sample.sampleClassification}">
                <c:set var="sampleClassification" value="${sample.sampleClassification}"/>
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
                <c:when test="${not empty publications}">
                    <c:forEach var="pub" items="${publications}" varStatus="status">
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
<div style="margin-top:6px"></div>
<table frame="box" width="95%">
    <c:choose>
        <c:when test="${isHostInstance}">
            <tr>
                <c:choose>
                    <c:when test="${not empty sample.phenotype}">
                        <c:set var="phenotype" value="${sample.phenotype}"/>
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
                    <c:when test="${not empty sample.taxonomyId && sample.taxonomyId>0}">
                        <td>Tax Id:
                            <a href="<c:url value="http://www.ncbi.nlm.nih.gov/taxonomy?Db=taxonomy&Cmd=DetailsSearch&Term=${sample.taxonomyId}[uid]"/>"><c:out
                                    value="${sample.taxonomyId}"/></a>
                        </td>
                    </c:when>
                    <c:otherwise>
                        <td><c:out value="${notGivenId}"/></td>
                    </c:otherwise>
                </c:choose>
            </tr>
            <tr>
                <c:choose>
                    <c:when test="${not empty sample.hostSex}">
                        <c:set var="hostSex" value="${sample.hostSex}"/>
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
                    <c:when test="${not empty sample.latLon}">
                        <c:set var="latLon" value="${sample.latLon}"/>
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
                    <c:when test="${not empty sample.environmentalBiome}">
                        <c:set var="environmentalBiome" value="${sample.environmentalBiome}"/>
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
                    <c:when test="${not empty sample.environmentalFeature}">
                        <c:set var="environmentalFeature" value="${sample.environmentalFeature}"/>
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
                    <c:when test="${not empty sample.environmentalMaterial}">
                        <c:set var="environmentalMaterial" value="${sample.environmentalMaterial}"/>
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
<div style="margin-top:6px"/>
<table frame="box" width="95%">
    <tr>
        <c:choose>
            <c:when test="${not empty sample.geoLocName}">
                <c:set var="geoLocName" value="${sample.geoLocName}"/>
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
            <c:when test="${not empty sample.collectionDate}">
                <c:set var="collectionDate" value="${sample.collectionDate}"/>
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
            <c:when test="${not empty sample.miscellaneous}">
                <c:set var="miscellaneous" value="${sample.miscellaneous}"/>
            </c:when>
            <c:otherwise>
                <c:set var="miscellaneous" value="${notGivenId}"/>
            </c:otherwise>
        </c:choose>
        <td valign="top" align="right" width="150"><b>Miscellaneous:</b></td>
        <td><c:out value="${miscellaneous}"/></td>
    </tr>
</table>
<div style="margin-top:10px"/>
<h3>Associated Data:</h3>
<table frame="box" width="95%">
    <tr>
        <td valign="top" align="right" width="150"><b>Analysis results:</b></td>
        <td>(not given)</td>
    </tr>
    <tr>
        <td valign="top" align="right" width="150"><b>Raw sequence reads:</b></td>
        <td><c:forEach var="seqId" items="${archivedSequences}" varStatus="status">
            <a href="<c:url value="http://www.ebi.ac.uk/ena/data/view/${seqId}"/>"><c:out value="${seqId}"/></a>
            <br>
        </c:forEach>
        </td>
    </tr>
</table>
<%--<div style="margin-top:6px"></div>--%>
<%--&lt;%&ndash; Print out all sample properties into a table &ndash;%&gt;--%>
<%--&lt;%&ndash; Properties with NULL values are filtered&ndash;%&gt;--%>
<%--<%--%>
<%--int i = 1;--%>
<%--String oddEven;--%>
<%--%>--%>
<%--<table border="0" style="border-width: 1px;border-color: #000000;border-style: solid;">--%>
<%--<c:forEach items="${sample.propertyMap}" var="entry">--%>
<%--<c:if test="${entry.value!=null}">--%>
<%--<%--%>
<%--if (i % 2 == 0) {--%>
<%--oddEven = "#FFF0F5";--%>
<%--} else {--%>
<%--oddEven = "#E6E6FA";--%>
<%--}--%>
<%--i++;--%>
<%--%>--%>
<%--<tr bgcolor="<%=oddEven%>">--%>
<%--<td valign="top"><c:out value="${entry.key}:"/></td>--%>
<%--<td><c:out value="${entry.value}"/></td>--%>
<%--</tr>--%>
<%--</c:if>--%>
<%--</c:forEach>--%>
<%--</table>--%>
</div>