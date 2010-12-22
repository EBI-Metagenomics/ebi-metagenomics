<%--
  Created by Maxim Scheremetjew, EMBL-EBI, InterPro
  Date: 21-Dec-2010
  Desc: Tab Separated file
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table cellpadding="0" cellspacing="0" style="font-size:10">
    <c:forEach items="${sample.propertyMap}" var="entry">
        <c:if test="${entry.value!=null}">
            <tr>
                <td>${entry.key}</td>
                <td>${entry.value}</td>
            </tr>
        </c:if>
    </c:forEach>
</table>