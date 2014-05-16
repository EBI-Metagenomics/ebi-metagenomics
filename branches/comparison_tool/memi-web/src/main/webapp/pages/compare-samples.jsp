<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:forEach var="sample" items="${samples}">
        <option value="${sample.id}" title="Sample ${sample.sampleId} | ${sample.sampleName} | ${sample.sampleAlias}">${sample.sampleName}</option>
    </c:forEach>
