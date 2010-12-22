<%--
  Created by Maxim Scheremetjew, EMBL-EBI, InterPro
  Date: 21-Dec-2010
  Desc: Tab Separated file to export a list of samples
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table cellpadding="0" cellspacing="0" style="font-size:10">
    <tr>
        <c:forEach items="${samplePropertyList}" var="value">
            <c:if test='${value != "SampleDescription"}'>
                <td>${value}</td>
            </c:if>
        </c:forEach>
    </tr>
    <c:forEach items="${samples}" var="sample">
        <tr>
            <c:forEach items="${sample.propertyMap}" var="entry">
                <c:if test='${entry.key != "SampleDescription"}'>
                    <c:choose>
                        <c:when test="${entry.value!=null}">
                            <td>${entry.value}</td>
                        </c:when>
                        <c:otherwise>
                            <td>N/A</td>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </c:forEach>
        </tr>
    </c:forEach>
</table>