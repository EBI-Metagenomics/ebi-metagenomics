<div id="list-news" class="grid_9 omega">

    <h2>Data content</h2>

    <p class="data-stat" style="margin-bottom:0;"><span class="icon icon-functional" data-icon="U"></span>Public: <strong>${model.publicRunCount}</strong> runs / <strong>${model.publicSamplesCount}</strong> samples  (<strong>${model.publicStudiesCount}</strong> projects)</p>
    <p class="data-stat" style="margin-top:0;"><span class="icon icon-functional" data-icon="L"></span>Private: <strong> ${model.privateRunCount}</strong> runs / <strong>${model.privateSamplesCount}</strong> samples (<strong>${model.privateStudiesCount}</strong> projects)</p>



        <%--The number of registered user is deactivated at the moment as there is no easy way to get that info automatically.--%>
    <%--<span class="icon icon-generic" data-icon="M" title=""></span> <strong>40</strong> registered users<br/>--%>
    <%--<img src="/metagenomics/img/icons_sub.png" alt="easy submission" width="20px" style="vertical-align: top;"> <strong>X</strong> total submitted nucleotide reads--%>

    <%--<span class="separator"></span>--%>
    <h2>
        <a href="http://twitter.com/EBImetagenomics" alt="Follow us on Twitter"></a>News & events
        <%--<a href="https://api.twitter.com/1/statuses/user_timeline/EBImetagenomics.rss" rel="alternate" type="application/rss+xml" title="Metagenomics RSS feeds"><img src="${pageContext.request.contextPath}/img/icon_rss.gif" alt="Metagenomics RSS feeds" style="float:right;"/></a>--%>
    </h2>

    <%--<div class="tweet"></div>--%>
    <a class="twitter-timeline"  height="400" data-dnt="true" href="https://twitter.com/EBImetagenomics" data-widget-id="345516657369837568">Tweets by @EBImetagenomics</a>
    <script>
    !function(d,s,id){var
            js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+"://platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>




</div>