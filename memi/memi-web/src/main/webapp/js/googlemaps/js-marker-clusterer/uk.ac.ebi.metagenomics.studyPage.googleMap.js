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
          fillColor:"#1a5286 ",  /*red #67a1b7*/
          fillOpacity:100,
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

    //transform icon into symbol - from http://stackoverflow.com/questions/33353250/google-maps-api-markerclusterer-plus-set-icon
    var Symbol=function(id,width,height,fill,strokewidth,strokecolor){
              var s={
                vader:  { //star was symbol
                          p:'M 454.5779,419.82295 328.03631,394.69439 282.01503,515.21933 210.30518,407.97233 92.539234,460.65437 117.66778,334.11278 -2.8571457,288.09151 104.38984,216.38165 51.707798,98.615703 178.2494,123.74425 224.27067,3.2193247 295.98052,110.46631 413.74648,57.784277 388.61793,184.32587 509.14286,230.34714 401.89587,302.057 z',
                          v:'0 0 512 512'
                        },
                 luc:  { //map symbol
                        p:' M 100, 100m -75, 0a 75,75 0 1,0 150,0a 75,75 0 1,0 -150,0',
                        v:'0 0 512 512'
                                      }
                }
              return ('data:image/svg+xml;base64,'+window.btoa('<svg xmlns="http://www.w3.org/2000/svg" height="'+height+'" viewBox="0 0 512 512" width="'+width+'" ><g><path stroke-width="'+strokewidth+'" stroke="'+strokecolor+'" fill="'+fill+'" d="'+s[id].p+'" /></g></svg>'));
            }


    //set style options for marker clusters (these are the default styles)
    var mcOptions = {gridSize:40, maxZoom:15, //define how far clustering should go
        styles:[
                {textColor:'white',width:40,height:40,url:Symbol('luc',100,100,'#1a5286',16,'white')},
                {textColor:'white',textSize:'18',width:78,height:78,url:Symbol('luc',200,200,'#1a5286',5,'white')}
             ]
    };
    var markerCluster = new MarkerClusterer(map, markers, mcOptions);
}
function bindInfoWindow(marker, map, infoWindow) {
    google.maps.event.addListener(marker, 'click', function () {
        infoWindow.open(map, marker);
    });
}