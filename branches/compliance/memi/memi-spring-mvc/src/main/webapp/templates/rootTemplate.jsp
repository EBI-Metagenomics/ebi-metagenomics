<%@ page import="java.net.URL" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.util.Scanner" %>
<%@ page import="java.io.*" %>
<%@ page import="uk.ac.ebi.interpro.metagenomics.memi.services.TemplateService"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="eng">


<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="author" content="EBI Web Team" />
    <meta http-equiv="Content-Language" content="en-GB" />
    <meta http-equiv="Window-target" content="_top" />
    <meta name="no-email-collection" content="http://www.unspam.com/noemailcollection/" />
    <meta name="generator" content="Dreamweaver 8" />

    <title><tiles:insertAttribute name="pageTitle"/>&lt; EBI metagenomics &lt; EMBL-EBI</title>

    <meta name="description" content="EBI Metagenomics is a new web resource targeted at metagenomic researchers"/>
    <meta name="keywords" content="ebi, EBI, InterPro, interpro, metagenomics, metagenomic, metagenome, metagenomes, DNA, microbiology, microbial, ecology, organisms, microorganism, microorganisms, biodiversity, diversity, gene, genes, genome, genomes, genomic, genomics, ecogenomics, community genomics, genetic, sequencing, sequence, environment, environmental, ecosystem, ecosystems, samples, sample, annotation, protein, research, archive, metabolic, pathways, analysis, function, GAIA, shotgun, pyrosequencing, community, communities, metabolism, cultivation, bioinformatics, bioinformatic, database, metadata, dataset, data, repository"/>

    <%-- EBI style and code--%>
    <link rel="stylesheet"  href="http://www.ebi.ac.uk/inc/css/contents.css"     type="text/css" />
    <link rel="stylesheet"  href="http://www.ebi.ac.uk/inc/css/userstyles.css"   type="text/css" />
    <script src="http://www.ebi.ac.uk/inc/js/contents.js" type="text/javascript"></script>
    <link rel="stylesheet"  href="http://www.ebi.ac.uk/inc/css/sidebars.css"   type="text/css" />
    <%--<link rel="SHORTCUT ICON" href="http://www.ebi.ac.uk/bookmark.ico" /> removed as we have created a favicon for metagenomics --%>
          <style type="text/css">
              @media print {
                  body, .contents, .header, .contentsarea, .head {
                      position: relative;
                  }
              }
     </style>
        <link rel="alternate" title="EBI News RSS" href="/Information/News/rss/ebinews.xml" type="application/rss+xml" />
        <link type="text/css" rel="stylesheet" href="//www.ebi.ac.uk/web_guidelines/css/mitigation/develop/ebi-mitigation.css"/>
        <link type="text/css" rel="stylesheet" href="//www.ebi.ac.uk/web_guidelines/css/mitigation/develop/embl-petrol-colours.css"/>
        <script defer="defer" src="//www.ebi.ac.uk/web_guidelines/js/cookiebanner.js"></script>
        <script defer="defer" src="//www.ebi.ac.uk/web_guidelines/js/foot.js"></script>

        <style type="text/css" media="screen, projection">

            /* [FR] Feb 03, 2012 */

            /* The Magnificent Clearfix: Updated to prevent margin-collapsing on child elements.
            j.mp/bestclearfix */
            .clearfix:before, .clearfix:after { content: "\0020"; display: block; height: 0; overflow: hidden; }
            .clearfix:after { clear: both; }
            /* Fix clearfix: blueprintcss.lighthouseapp.com/projects/15318/tickets/5-extra-margin-padding-bottom-of-page */
            .clearfix { zoom: 1; }

            div.cols3 {padding:10px; clear:both;}

            div.cols3 .col1 {padding:0; float:left; width:32%; margin-right:2%;}

            div.cols3 .col2 {padding:0; float:left;width:32%;margin-right:2%;}

            div.cols3 .col3 {padding:0; float:left; width:32%; }

            div.cols3 ul { padding-left: 20px; }

        </style>
    <%-- END EBI style and code--%>

    <%-- remove favicon from img folder to root as was not working anymore--%>
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico"/>




    <%-- JQuery CSS--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui-1.8.11.custom.css" type="text/css" media="all"/>
    <%-- END JQuery CSS--%>

    <%--<link href="https://fonts.googleapis.com/css?family=Droid+Sans:regular,bold" rel="stylesheet" type="text/css"/>--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/default.css" type="text/css" media="all"/>

    <!--[if lt IE 8]>
    <style type="text/css"> table.result tbody tr td {background-color: #F4F4F8;}</style> <![endif]-->
    <!--[if lt IE 7]>
    <style type="text/css"> #feedback_div {display: none;}
    #feedback_no_div {display: block;}</style><![endif]-->

    <!--[if lt IE 9]>
    <style type="text/css"> .krona_chart {display: none;}</style>
    <style type="text/css"> #ie_krona {display: block;}</style>
    <style type="text/css">.but_chart_export {display: none;}</style>
    <![endif]--> <%-- Add a message to the Krona tab for IE users (lower version than IE9)+ remove the export button as Krona and export to img function is not working for IE8, IE7, IE6--%>

    <!--[if IE]>
    <style type="text/css">.chart_exp_snap {display: none;}</style>
    <![endif]-->  <%-- remove the save img as PNG option for all IE--%>


    <!-- JQuery and JQuery UI source-->
    <script src="${pageContext.request.contextPath}/js/jquery-1.4.4.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-ui-1.8.8.custom.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.validate-1.9.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.form-2.85.js" type="text/javascript"></script>
    <!-- The date picker is used within the submission page -->
    <script src="${pageContext.request.contextPath}/js/datepicker.js" type="text/javascript"></script>
    <!-- The auto completion is used with the study search page -->
    <script src="${pageContext.request.contextPath}/js/autocompletion.js" type="text/javascript"></script>

    <script src="${pageContext.request.contextPath}/js/formclearer.js" type="text/javascript"></script>
    <!-- Jquey scroller - work only for the anchors on the same page-->
    <script src="${pageContext.request.contextPath}/js/smoothscrol.js" type="text/javascript"></script>
    <!-- The following line includes the feedback dialog script. -->
    <script src="${pageContext.request.contextPath}/js/feedback_script.js" type="text/javascript"></script>

   <%--Client-side twitter news feed - http://tweet.seaofclouds.com/--%>
    <script src="${pageContext.request.contextPath}/js/tweet/jquery.tweet.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/tweet/tweet.instance.js" type="text/javascript"></script>

    <!--[if lt IE 9]>
<script type="text/javascript">
    document.createElement('header');
    document.createElement('hgroup');
    document.createElement('nav');
    document.createElement('menu');
    document.createElement('section');
    document.createElement('article');
    document.createElement('aside');
    document.createElement('footer');
    document.createElement('figure');
    document.createElement('figcaption');
</script>
    <![endif]-->  <%-- HTML5 tags working in IE8 by including this JavaScript in the head  --%>

    <%-- simple script for alternate row in a table color was #EFF1F1 originally--%>
    <script>
        $(document).ready(function () {
            $("table.result tbody tr:even").css("background-color", "#F4F4F8");
            $("table.result tbody tr:odd").css("background-color", "#e9e9e9");
        });
    </script>

    <script type="text/javascript"
            src="https://maps.google.com/maps/api/js?sensor=false">
    </script>
    <script type="text/javascript">
        function initialize(lat, long) {
            var latlng = new google.maps.LatLng(lat, long);
            google.maps.MarkerOptions
            var myOptions = {
                zoom:4,
                center:latlng,
                mapTypeId:google.maps.MapTypeId.ROADMAP,
                streetViewControl: false
            };
            var map = new google.maps.Map(document.getElementById("map_canvas"),
                    myOptions);
            var marker = new google.maps.Marker({
                position:latlng,
                map:map
            });
        }

    </script>

    <!-- script for carousel -->
    <script src="${pageContext.request.contextPath}/js/jquery.carousel.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
            $("div.carousel").carousel({pagination:true, autoSlide:true, autoSlideInterval:15000, delayAutoSlide:2000, loop:true });

        });
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#datepicker").focus(function () {
                $("#datepicker").datepicker("show");
            });
        });
    </script>

      <%--WARNING this is inserting a character on top of the page--%>
    <%--<!-- Script and noscript versions for feedback and login links --><script type="text/javascript">--%>
    <%--document.write('<style type="text/css">#noscript_feedbackLink{display: none;}#noscript_loginLink{display: none;}</style>');--%>
<%--</script>​--%>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <%--<script type="text/javascript">--%>
        <%--//Load the Visualization API and the chart package.--%>
        <%--google.load("visualization", "1", {packages:["corechart","table","controls"]});--%>
    <%--</script>--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/tablequerywrapper.js"></script>

    <noscript>
        <style type="text/css">
            #tax-Krona, #tabs-chart, #interpro-chart, #tabs-taxchart {display: none;}/*remove the Krona box  and all charts tabs when no javascript*/
            #script_feedbackLink {display: none;}/*remove the feedback button when no javascript*/
            #script_loginLink {display: none;}  /*remove the login button when no javascript*/
            #mod4 {display: none;} /* remove the last item - jumping line - of the carousel when no javascript */
        </style>
    </noscript>
</head>

<%@ include file="scriptlets/_getActivePage.jsp" %>
<c:set var="enaUrlParam" value="<%=activePage%>" scope="session"/>
<%-- The following variable saves and provides the base URL for the whole application --%>
<c:set var="baseURL" value="" scope="session"/>

<jsp:useBean id="mastHead" scope="session" class="uk.ac.ebi.interpro.metagenomics.memi.services.TemplateService" />

<body class="<tiles:getAsString name='bodyClass'/>" id="top"  <%--onload="loaded()" onload="initialize()"--%>>

<%-- EBI-Interpro main header - exactly same as Interpro header--%>
<div class="headerdiv" id="headerdiv">
  <div class="header">
    <div id="global-masthead-container" class="masthead grid_24">
        ${mastHead.globalMastHead}
        <%
//            try{
//                URL oracle = new URL("http://wwwint.ebi.ac.uk/web/template-service/prod/templates/compliance/masthead/global/services");
//                BufferedReader in = new BufferedReader(
//                        new InputStreamReader(oracle.openStream()));
//
//                String inputLine;
//                while ((inputLine = in.readLine()) != null)
//                    System.out.println(inputLine);
//                in.close();
//            }catch(Exception ex){
//                System.out.println("<p>Exception thrown</p>");
//            }
        %>
        <%----%>
        <%--HttpClient client = new DefaultHttpClient();--%>
        <%--HttpGet request = new HttpGet("http://www.yahoo.com");--%>
        <%--HttpResponse response = client.execute(request);--%>

        <%--// Get the response--%>
        <%--BufferedReader rd = new BufferedReader--%>
        <%--(new InputStreamReader(response.getEntity().getContent()));--%>

        <%--String line = "";--%>
        <%--while ((line = rd.readLine()) != null) {--%>
        <%--textView.append(line);--%>
        <%--}--%>
        <%--%>--%>
    </div>

    <div id="local-masthead-container" class="masthead grid_24">
        <%--${mastHead.localMastHead}--%>

        <%
//
        %>
    </div>
  </div>
</div>
<%-- END EBI main header --%>
<%--<script src="${pageContext.request.contextPath}/js/compliance.js" type="text/javascript"></script>--%>

<div class="ebi_contents" id="contents"> <%-- style="margin:0; width:100%;"--%>
    <div id="content-full">
    <div class="grid_24">
    <tiles:insertAttribute name="breadcrumbs"/>

        <%--<header>--%>
            <%--<tiles:insertAttribute name="header"/>--%>
        <%--</header>--%>
        <%--<nav>--%>
            <%--<tiles:insertAttribute name="mainMenu"/>--%>
        <%--</nav>--%>

    <tiles:insertAttribute name="body"/>
    </div>
    </div>


    <footer>
       <%--<tiles:insertAttribute name="footer"/>--%>
        ${mastHead.globalFooter}
    </footer>
</div>
<%-- END div contents --%>

<%--<div id="extra_feedback">--%>
<%--<h1>--%>
<%--<a id="script_feedbackLink" href="javascript:showFeedbackForm()"--%>
<%--title="Give your feedback">Give your feedback</a>--%>
<%--<a id="noscript_feedbackLink" href="<c:url value="${baseURL}/feedback"/>"--%>
<%--title="Give your feedback">Give your feedback</a>--%>
<%--</h1>--%>
<%--</div>--%>

<%--<a href="<c:url value="${baseURL}/info#intro"/>" title="About us">--%>
    <%--<div id="extra_beta" class="anim"></div>--%>
<%--</a>--%>

<!-- The following line includes the feedback DIV and the feedback FORM -->
<%@ include file="feedback/script/feedbackDiv.jsp" %>
</body>
</html>
