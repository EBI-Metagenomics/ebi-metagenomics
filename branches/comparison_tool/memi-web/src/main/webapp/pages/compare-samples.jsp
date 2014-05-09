<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:forEach var="sample" items="${samples}">
        <option> ${sample.sampleId} (${sample.sampleAlias})</option>
    </c:forEach>
