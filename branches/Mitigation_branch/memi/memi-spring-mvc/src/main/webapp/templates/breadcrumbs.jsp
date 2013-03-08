<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--doesn't work
<nav id="breadcrumb">--%>
      <%--<p>--%>
      <%--<a href="<c:url value="${baseURL}/"/>" title="EBI Metagenomics">EBI Metagenomics</a> &gt;--%>
          <%--<c:forEach var="breadcrumb" items="${model.breadcrumbs}" varStatus="loop">--%>
          <%--<a href="<c:url value="${baseURL}/${breadcrumb.url}"/>" title="${breadcrumb.linkDescription}"--%>
          <%--${breadcrumb.linkName}</a>--%>
           <%--</c:forEach>--%>
     <%--</p>--%>
   <%--</nav>--%>

<div id="breadcrumbs">

    <ul class="ebi_breadcrumbs">

        <li>
            <a href="<c:url value="${baseURL}/"/>" title="EBI Metagenomics">EBI Metagenomics</a>
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
