<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%--Page variable which is used several time within this page. Used for not specified study attributes.--%>
<c:set var="notGivenId" value="(not given)"/>
<c:set var="study" value="${model.study}"/>

<div class="title_tab_p extra_margin">
    <div class="biome_project"><span class="biome_icon icon_sm show_tooltip ${study.biomeIconCSSClass}"
                                     title="${study.biomeIconTitle} biome"></span></div>

    <span class="subtitle">Project  <span>(${study.studyId})</span></span>

    <h2 class="fl_uppercase_title">${study.studyName}</h2>
</div>

<div class="sample_ana">
    <div id="navtabs">

        <%--Main Tabs--%>
        <ul>
            <li>
                <a title="Overview"
                   href="<c:url value="${baseURL}/projects/${study.studyId}/overview"/>"><span>Overview</span></a>
            </li>
            <li>
                <a title="Analysis summary" href="<c:url value="${baseURL}/projects/${study.studyId}/download"/>"><span>Analysis summary</span></a>
            </li>
        </ul>

    </div>
    <%--end navtabs--%>
</div>
<%--end sample_ana--%>

<script type="text/javascript">
    $(function () {
        //   IMPORTANT TO keep at bottom as it should be rendered last - anchor “jump” when loading a page from http://stackoverflow.com/questions/3659072/jquery-disable-anchor-jump-when-loading-a-page
        setTimeout(function () {
            if (location.hash) {
//                   $('#local-masthead').fadeOut();
                window.scrollTo(0, 0);
            }
        }, 1000);
    });
</script>

<%--test Seb--%>
<%--Only add the scripts for the Google map if the JSON data file is available--%>
<c:if test="${model.googleMapDataAvailable}">

    <%--Javascript for Marker Clusterer – A Google Maps JavaScript API utility library--%>
    <%--https://github.com/googlemaps/js-marker-clusterer/blob/gh-pages/README.md--%>
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/projects/${study.studyId}/map-data"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/googlemaps/js-marker-clusterer/markerclusterer_compiled-1.0.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/googlemaps/js-marker-clusterer/uk.ac.ebi.metagenomics.studyPage.googleMap.js"></script>

    <script type="text/javascript">
        function toggleDiv(divId, buttonId) {
            var divElement = document.getElementById(divId);
            if (divElement.style.display == 'block')
                divElement.style.display = 'none';
            else
                divElement.style.display = 'block';
        }
    </script>
</c:if>

<%--script for tabs--%>
<script type="text/javascript">
    //Main navigation tabs - overview, quality control, taxonomic analysis, functional analysis, downloads
    //Ajax load approach as described here: http://jqueryui.com/tabs/#ajax

    $(document).ready(function () {
        $("#navtabs").tabs({
            cache:true,
            ajaxOptions:{
                error:function (xhr, status, index, anchor) {
                    $(anchor.hash).html("<div class='msg_error'>Couldn't load this tab. We'll try to fix this as soon as possible.</div>");
                }
            },
            //Change the Hashtag on Select
            //Described here: http://imdev.in/jquery-ui-tabs-with-hashtags/
            select:function (event, ui) {
                window.location.hash = ui.tab.hash;
            },
            //Set the tab disable option
            ${model.disabledOption}
        }).bind('tabsload', function (event, ui) {
                    <c:if test="${model.googleMapDataAvailable}">
                    google.maps.event.addDomListener(window, 'load', initialize());
                    </c:if>
                });
    });
</script>
