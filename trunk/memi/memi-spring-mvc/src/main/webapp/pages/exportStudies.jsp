<%--
  Created by Maxim Scheremetjew, EMBL-EBI, InterPro
  Date: 22-Dec-2010
  Desc: Tab Separated file to export a list of studies
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<table cellpadding="0" cellspacing="0" style="font-size:10">
    <tr>
        <c:forEach items="${studyPropertyList}" var="value">
            <td>${value}</td>
        </c:forEach>
    </tr>
    <%-- Study attributes are truncated (the length is currently limited to 35 characters) --%>
    <c:forEach items="${studies}" var="study">
        <tr>
            <c:forEach items="${study.properties}" var="entry">
                <c:set var="truncated" value="${entry.value}"/>
                <c:set var="columnLength" value="${columnLength}"/>
                <c:choose>
                    <c:when test="${not empty entry.value}">
                        <td>${fn:substring(truncated, 0, columnLength)}</td>
                    </c:when>
                    <c:otherwise>
                        <td>N/A</td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tr>
    </c:forEach>
</table>