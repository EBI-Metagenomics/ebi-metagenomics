function initialize() {
    var center = new google.maps.LatLng(5.0, 0.0);

    var map = new google.maps.Map(document.getElementById('map_canvas_study'), {
        zoom:2,
        center:center,
        mapTypeId:google.maps.MapTypeId.ROADMAP
    });

    var markers = [];
    var samples = data.samples
    for (var i = 0; i < samples.length; i++) {
        var sampleObject = samples[i];
        //Little tweak to deal with multiple markers on exact same spot
        var newSampleLatitude = sampleObject.latitude + (Math.random() - .5) / 750;// * (Math.random() * (max - min) + min);
        //end tweak
        var latLng = new google.maps.LatLng(newSampleLatitude,
            sampleObject.longitude);
        var sampleTitle = sampleObject.title;

        //Define an image for the marker icon
        //Set the image URL
        //var imageUrl = '${pageContext.request.contextPath}/favicon.ico';
        //Define the marker image object
        //var markerImage = new google.maps.MarkerImage(imageUrl,new google.maps.Size(24, 32));

        var marker = new google.maps.Marker({
            position:latLng,
            label:sampleTitle,
            title:sampleTitle
            //Set the marker icon
            //icon:markerImage
        });

        //Set up the info window for the marker click event
        //Define the content string for the info window
        var contentString = "<div style='min-width:200px;max-width:650px;max-height: 350px;'>"
        contentString += "<span class='biome_icon icon_xs show_tooltip ${study.biomeIconCSSClass}' title='${study.biomeIconTitle} biome'></span>"
        contentString += "<h3>Sample Overview - " + sampleObject.sample_id + "</h3>"
        contentString += "<p>" + sampleObject.sample_desc + "</p>"
        contentString += '<button id="googleMapInfoButton" onclick="' + "toggleDiv('myDiv','googleMapInfoButton')" + '">More/Hide</button>';
        contentString += '<div id="myDiv" style="display:none;"><p>Project: ${study.studyId}<br>'
        contentString += "Title: <a href='${pageContext.request.contextPath}/projects/${study.studyId}/samples/" + sampleObject.sample_id + "'>" + sampleObject.title + "</a><br>"
        contentString += "Classification: " + sampleObject.lineage + "<br>"
        contentString += "Collection Date: " + sampleObject.collection_date + "<br>"
        contentString += "Latitude: " + sampleObject.latitude + "<br>"
        contentString += "Longitude: " + sampleObject.longitude
        contentString += "</p></div>";
        contentString += "</div>";

        //Define the into window object
        var infoWindow = new google.maps.InfoWindow({
            content:contentString
        });

        bindInfoWindow(marker, map, infoWindow);
        markers.push(marker);
    }

    //To use a marker clusterer, create a MarkerClusterer object and add the map plus all the markers
    //specify a number of options to fine-tune
    var mcOptions = {gridSize:50, maxZoom:15};
    var markerCluster = new MarkerClusterer(map, markers, mcOptions);
}
function bindInfoWindow(marker, map, infoWindow) {
    google.maps.event.addListener(marker, 'click', function () {
        infoWindow.open(map, marker);
    });
}