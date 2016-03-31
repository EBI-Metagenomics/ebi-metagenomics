<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="container_24" id="mainContainer">

    <h2>Search EBI Metagenomics</h2>
    <div class="grid_24 clearfix" id="content">

        <form:form method="GET" action="${pageContext.request.contextPath}/search/doEbiSearch" commandName="ebiSearchForm">
            <div class="grid_6 alpha" id="facets">
                <c:choose>
                    <c:when test="${not empty model.ebiSampleSearchResults}">
                        <c:forEach var="facet" items="${model.ebiSampleSearchResults.facets}">
                            <c:if test="${fn:length(facet.values) > 0}">
                                <h4>${facet.label}</h4>
                                <form:checkboxes path="facets" items="${facet.values}" itemLabel="labelAndCount" itemValue="facetAndValue" element="div" />
                            </c:if>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </div>
            <div class="grid_18 omega" id="searchInput">
                <fieldset>
                    <label for="searchText">Search:</label>
                    <span>
                        <form:input path="searchText" />
                    </span>
                    <form:errors path="errorText" cssClass="error"/>
                </fieldset>
            </div>

            <c:choose>
                <c:when test="${not empty model.ebiSampleSearchResults}">
                    <h3>${model.ebiSampleSearchResults.numberOfHits} hits</h3>
                    <div class="grid_18 omega">
                        <c:forEach var="result" items="${model.ebiSampleSearchResults.entries}">
                            ${result.identifier}: ${result.description}<br />
                        </c:forEach>
                    </div>
                </c:when>
            </c:choose>
        </form:form>
    </div>
</div>

<script type="javascript">$(function () { $("[data-toggle='tooltip']").tooltip(); });</script>
<script type="text/javascript">
    $("#expand_button").click(function(){
        $(".more_citations").slideToggle();
        $("#expand_button").toggleClass("min");
    });

</script>