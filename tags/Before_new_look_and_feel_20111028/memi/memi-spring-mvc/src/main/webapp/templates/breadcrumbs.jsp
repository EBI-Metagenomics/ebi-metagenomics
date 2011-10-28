<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="breadcrumbs">
    <div class="center_972">
    <ul class="ebi_breadcrumbs">

        <li>
            <a href="<c:url value="http://www.ebi.ac.uk/"/>" title="EBI">EBI</a>
        </li>
        <li>
            <a href="<c:url value="http://www.ebi.ac.uk/Databases/"/>" title="Databases">Databases</a>
        </li>
        <li>
            <a href="<c:url value="${baseURL}/"/>" title="Metagenomics">Metagenomics</a>
        </li>

        <c:forEach var="breadcrumb" items="${model.breadcrumbs}" varStatus="loop">
            <li>
               <a href="<c:url value="${baseURL}/${breadcrumb.url}"/>" title="${breadcrumb.linkDescription}">
                   ${breadcrumb.linkName}
               </a>
            </li>
        </c:forEach>

    </ul>
     </div>
</div>
