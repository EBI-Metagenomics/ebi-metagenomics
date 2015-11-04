<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <p id="breadcrumb">
    <a href="<c:url value="${baseURL}/"/>" title="EBI Metagenomics">EBI Metagenomics</a>
        <c:forEach var="breadcrumb" items="${model.breadcrumbs}" varStatus="loop">
            &gt;<a href="<c:url value="${baseURL}/${breadcrumb.url}"/>" title="${breadcrumb.linkDescription}">
            ${breadcrumb.linkName}</a>
        </c:forEach>
    </p>
