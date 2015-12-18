function initialize(contextPath, biomeIconCSSClass, biomeIconTitle) {
    var center = new google.maps.LatLng(5.0, 0.0);

    var map = new google.maps.Map(document.getElementById('map_project'), {
        zoom:2,
        center:center,
        mapTypeId:google.maps.MapTypeId.ROADMAP,
        streetViewControl: false
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

        // for multiple icons - see https://developers.google.com/maps/tutorials/customizing/custom-markers

        var marker = new google.maps.Marker({
          position:latLng,
          //Set the marker icon
          icon: {
          path: google.maps.SymbolPath.CIRCLE,
          strokeWeight:3,
          strokeColor:"#fff",
          fillColor:"#d42929",
          fillOpacity:50,
          scale: 8
        }
        });

        //Set up the info window for the marker click event
        //Define the content string for the info window
        var contentString = "<div class='map_info_window'>"
        contentString += "<span class='biome_icon icon_xs show_tooltip "+biomeIconCSSClass+"' title='"+biomeIconTitle+" biome'></span>"
        contentString += "<h3>Sample Overview - <a href='"+contextPath+"/projects/" + sampleObject.study_id + "/samples/" + sampleObject.sample_id + "'>" + sampleObject.sample_id + "</a></h3>"
        contentString +=  sampleObject.title + " / " + sampleObject.sample_desc + "<br/><br/>"
//        contentString += '<button id="googleMapInfoButton" onclick="' + "toggleDiv('sampleDetailsDiv','googleMapInfoButton')" + '">More/Hide</button>';
//        contentString += '<div id="sampleDetailsDiv" style="display:none;"><p>Project: ' + sampleObject.study_id + '<br>'
        contentString += "<strong>Project name:</strong> " + sampleObject.study_desc + " ("+ sampleObject.study_id +")<br/>"
        contentString += "<strong>Classification:</strong> " + sampleObject.lineage + "<br/>"
        contentString += "<strong>Collection Date:</strong> " + sampleObject.collection_date + "<br/>"
        contentString += "<strong>Lat/Long:</strong> " + sampleObject.latitude + ", "+ sampleObject.longitude   + "<br/>"
        contentString += "<a class='anim map_info_more' href='"+contextPath+"/projects/" + sampleObject.study_id + "/samples/" + sampleObject.sample_id + "'>View more</a>"
        contentString += "</div>";

        //Define the info window object
        var infoWindow = new google.maps.InfoWindow({
            content:contentString
        });

        bindInfoWindow(marker, map, infoWindow);
        markers.push(marker);
    }

    //set style options for marker clusters (these are the default styles)
    var mcOptions = {gridSize:40, maxZoom:15, //define how far clustering should go
        styles: [
            {//medium
            textColor:'white',
            textSize:12,
            height: 44,
            width: 44,
            url: contextPath+"/img/ico_map_clust_44.png"
            },
            {//big
            textColor:'white',
            textSize:18,
            height: 58,
            width: 58,
            url: contextPath+"/img/ico_map_clust_58.png"
            }]
    };
    var markerCluster = new MarkerClusterer(map, markers, mcOptions);
}
function bindInfoWindow(marker, map, infoWindow) {
    google.maps.event.addListener(marker, 'click', function () {
        infoWindow.open(map, marker);
    });
}
