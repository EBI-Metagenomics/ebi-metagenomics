<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- TODO Remove this once the EBI template has been added! -->
<style type="text/css" title="text/css">
    /* Bread Crumbs */
    .ebi_breadcrumbs {
        list-style: none;
        margin: 0;
        padding: 0;
    }

    .ebi_breadcrumbs li {
        display: inline;
        margin: 0;
        padding: 0;
        font-size: 8pt;
        color: #999999;
        background-color: #ffffff;
    }

    .ebi_breadcrumbs a {
        padding: 1px 0 0 12px;
        margin: 0;
        height: 14px;
        text-align: left;
        background: #ffffff
            url(http://www.ebi.ac.uk/inc/images/bread_arrow2.gif) top left
            no-repeat;
    }

    .ebi_breadcrumbs li:first-child a {
        padding: 2px 0 0 0px;
        margin: 0;
        height: 14px;
        text-align: left;
        background: #ffffff;
    }

    .ebi_breadcrumbs a:link,.ebi_breadcrumbs a:visited {
        color: #999999;
        text-decoration: none;
    }

    .ebi_breadcrumbs a:hover,.ebi_breadcrumbs a:active {
        color: #e33e3e;
        text-decoration: underline;
    }

</style>

<div id="breadcrumbs">
    <div class="center_972">
    <ul class="ebi_breadcrumbs">

        <li>
            <a href="<c:url value=""/>" title="EBI">EBI</a>
        </li>
        <li>
            <a href="<c:url value=""/>" title="Databases">Databases</a>
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
