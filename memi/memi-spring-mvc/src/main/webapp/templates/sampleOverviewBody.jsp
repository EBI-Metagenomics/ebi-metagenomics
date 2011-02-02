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
    <div align="left">
        <a href="<c:url value="${baseURL}/sampleOverview/exportSample/${sample.sampleId}"/>">Export to CSV</a>
    </div>
    <h3>Sample Description</h3>
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
            <td valign="top" align="right" width="150"><b>Links:</b></td>
            <td></td>
        </tr>
    </table>
    <div style="margin-top:6px"></div>
    <table frame="box" width="95%">
        <tr>
            <td valign="top" align="right" width="150"><b>Submitter name:</b></td>
            <td>(not given)</td>
        </tr>
        <tr>
            <td valign="top" align="right" width="150"><b>Contact name:</b></td>
            <td>(not given)</td>
        </tr>
        <tr>
            <td valign="top" align="right" width="150"><b>Contact email:</b></td>
            <td>(not given)</td>
        </tr>
        <tr>
            <c:choose>
                <c:when test="${not empty study.centreName}">
                    <c:set var="centreName" value="${study.centreName}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="centreName" value="${notGivenId}"/>
                </c:otherwise>
            </c:choose>
            <td valign="top" align="right" width="150"><b>Centre name:</b></td>
            <td><c:out value="${centreName}"/></td>
        </tr>
    </table>
    <div style="margin-top:10px"/>
    <h3>Associated Data:</h3>
    <table frame="box" width="95%">
        <tr>
            <td valign="top" align="right" width="150"><b>Raw sequence reads:</b></td>
            <td>(not given)</td>
        </tr>
        <tr>
            <td valign="top" align="right" width="150"><b>Analysis results:</b></td>
            <td>(not given)</td>
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