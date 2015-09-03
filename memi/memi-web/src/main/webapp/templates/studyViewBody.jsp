<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%--Page variable which is used several time within this page. Used for not specified study attributes.--%>
<c:set var="notGivenId" value="(not given)"/>
<c:set var="study" value="${model.study}"/>

<div class="title_tab_p extra_margin">
    <%--TEMP while we implement a better solution--%>
    <c:choose>
        <c:when test="${study.biomeIconCSSClass == 'freshwater_b'}">
            <c:set var="biomeName" value="Freshwater" scope="page"/>
        </c:when>
        <c:when test="${study.biomeIconCSSClass == 'soil_b'}">
            <c:set var="biomeName" value="Soil" scope="page"/>
        </c:when>
        <c:when test="${study.biomeIconCSSClass == 'forest_b'}">
            <c:set var="biomeName" value="Forest" scope="page"/>
        </c:when>
        <c:when test="${study.biomeIconCSSClass == 'grassland_b'}">
            <c:set var="biomeName" value="Grassland" scope="page"/>
        </c:when>
        <c:when test="${study.biomeIconCSSClass == 'marine_b'}">
            <c:set var="biomeName" value="Marine"/>
        </c:when>
        <c:when test="${study.biomeIconCSSClass == 'human_gut_b'}">
            <c:set var="biomeName" value="Human gut" scope="page"/>
        </c:when>
        <c:when test="${study.biomeIconCSSClass == 'engineered_b'}">
            <c:set var="biomeName" value="Engineered" scope="page"/>
        </c:when>
        <c:when test="${study.biomeIconCSSClass == 'air_b'}">
            <c:set var="biomeName" value="Air" scope="page"/>
        </c:when>
        <c:when test="${study.biomeIconCSSClass == 'wastewater_b'}">
            <c:set var="biomeName" value="Wastewater" scope="page"/>
        </c:when>
        <c:when test="${study.biomeIconCSSClass == 'non_human_host_b'}">
            <c:set var="biomeName" value="Non-human host" scope="page"/>
        </c:when>
        <c:when test="${study.biomeIconCSSClass == 'human_host_b'}">
            <c:set var="biomeName" value="Human host" scope="page"/>
        </c:when>
        <c:otherwise>
            <c:set var="biomeName" value="Undefined" scope="page"/>
        </c:otherwise>
    </c:choose>

    <div class="biome_project"><span class="biome_icon icon_sm show_tooltip ${study.biomeIconCSSClass}"
                                     title="${biomeName} biome"></span></div>

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
</div>  <%--end sample_ana--%>

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
<%--script for tabs--%>
<script type="text/javascript">
    //Main navigation tabs - overview, quality control, taxonomic analysis, functional analysis, downloads
    //Ajax load approach as described here: http://jqueryui.com/tabs/#ajax

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
    });

</script>
