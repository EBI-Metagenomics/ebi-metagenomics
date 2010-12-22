<%--
  Created by Maxim Scheremetjew, EMBL-EBI, InterPro
  Date: 30-Nov-2010
  Desc: Sample overview page
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sample overview</title>
    <link href="/css/memi.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
<div id="right_side_navigation">
    <p><a href="<c:url value="../homePage"/>">Home</a></p>
</div>
<div id="content">
    <div style="margin-top:60px"></div>
    <h2>Sample overview - ${sample.sampleTitle} (ID ${sample.sampleId}) </h2>

    <spring:url var="exportUrl" value="exportSample/${sample.sampleId}"/>

    <div align="right"><a href="${exportUrl}">Export to CSV</a></div>
    <div style="margin-top:6px"></div>
    <%-- Print out all sample properties into a table --%>
    <%-- Properties with NULL values are filtered--%>

    <%
        int i = 1;
        String oddEven;
    %>
    <table border="0" style="border-width: 1px;border-color: #000000;border-style: solid;">
        <c:forEach items="${sample.propertyMap}" var="entry">
            <c:if test="${entry.value!=null}">
                <%
                    if (i % 2 == 0) {
                        oddEven = "#FFF0F5";
                    } else {
                        oddEven = "#E6E6FA";
                    }
                    i++;
                %>
                <tr bgcolor="<%=oddEven%>">
                    <td valign="top"><c:out value="${entry.key}:"/></td>
                    <td><c:out value="${entry.value}"/></td>
                </tr>
            </c:if>
        </c:forEach>
    </table>
</div>
</body>
</html>