$(document).ready(function() {
    //    TODO: Find a way of out to get study names from database
    //    http://www.infoq.com/articles/First-Cup-Web-2.0-Joel-Confino
    //    http://viralpatel.net/blogs/2009/06/tutorial-create-autocomplete-feature-with-java-jsp-jquery.html
    //    http://jqueryui.com/demos/autocomplete/#remote-jsonp
    var studyNames = [
        "Rainforest Soil",
        "wec.diel.study",
        "A core gut microbiome in obese and lean twins",
        "SRP001743",
        "ERP000118",
        "SRP000319",
        "SRP001709"];
    $("input#autocomplete").autocomplete({
        source: studyNames
    });
});