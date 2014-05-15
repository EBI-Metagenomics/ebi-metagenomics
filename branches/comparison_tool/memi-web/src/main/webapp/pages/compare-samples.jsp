<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:forEach var="sample" items="${samples}">
        <option value="${sample.sampleId}" title="Sample ${sample.sampleId} | ${sample.sampleName} | ${sample.sampleAlias}">${sample.sampleName}</option>
    </c:forEach>
