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

<%--Only add the scripts for the Google map if the JSON data file is available--%>
<c:if test="${model.googleMapDataAvailable}">
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/projects/${study.studyId}/map-data"></script>
    <script type="text/javascript">
        var script = '<script type="text/javascript" src="${pageContext.request.contextPath}/js/google-cluster-map/markerclusterer-1.0.1.js"><' + '/script>';
        document.write(script);
    </script>

    <script type="text/javascript">
        function initialize() {
            var center = new google.maps.LatLng(5.0, 0.0);

            var map = new google.maps.Map(document.getElementById('map'), {
                zoom:2,
                center:center,
                mapTypeId:google.maps.MapTypeId.ROADMAP
            });

            var markers = [];
            var samples = data.samples
            for (var i = 0; i < samples.length; i++) {
                var sampleObject = samples[i];
                //Little tweak to deal with multiple markers on exact same spot
                sampleLatitude = sampleObject.latitude + (Math.random() * (0.0001 - 0.001) + 0.001);
                //end tweak
                var latLng = new google.maps.LatLng(sampleLatitude,
                        sampleObject.longitude);
                var sampleTitle = sampleObject.title;
                var marker = new google.maps.Marker({
                    position:latLng, label:sampleTitle, title:sampleTitle
                });
                var infoWindow = new google.maps.InfoWindow;
                var html = "<div style='min-width:200px;max-width:400px;max-height: 200px;'><b>Sample Overview - " + sampleObject.sample_id + "</b><br>"
                html += "Project: ${study.studyId}<br>"
                html += "Title: <a href='${pageContext.request.contextPath}/projects/${study.studyId}/samples/" + sampleObject.sample_id + "'>" + sampleObject.title + "</a><br>"
                html += "Classification: " + sampleObject.lineage + "<br>"
                html += "Collection Date: " + sampleObject.collection_date + "<br>"
                html += "Latitude: " + sampleObject.latitude + "<br>"
                html += "Longitude: " + sampleObject.longitude
                html += "</div>";
                bindInfoWindow(marker, map, infoWindow, html);
                markers.push(marker);
            }
            var markerCluster = new MarkerClusterer(map, markers);
        }
        function bindInfoWindow(marker, map, infoWindow, html) {
            google.maps.event.addListener(marker, 'click', function () {
                infoWindow.setContent(html);
                infoWindow.open(map, marker);
            });
        }
        google.maps.event.addDomListener(window, 'load', initialize);
    </script>
</c:if>
