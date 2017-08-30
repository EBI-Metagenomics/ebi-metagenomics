<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>About EBI Metagenomics</h2>



<h3 id="what_about">EBI Metagenomics service</h3>

<div id="sidebar-nav">
    <ul>
        <%--<li><a href="#intro">Introduction</a></li>
       <li><a href="#feedback">Feedback</a></li>--%>
        <li><a href="#what_about">EBI Metagenomics service</a></li>
        <li><a href="#mail">Staying informed</a></li>
        <li><a href="#h_cite">Citing us</a></li>
        <li><a href="#funding">Funding</a></li>
    </ul>
</div>

<figure class="fr"><img src="${pageContext.request.contextPath}/img/pict_metagenomics_01.png" alt="Metagenomics"/>
<figcaption><p>Metagenomics is the study of all genomes present in any given environment...</p></figcaption></figure>
<p>Metagenomics is the study of all genomes present in any given environment without the need for prior individual identification or amplification. For example, in its simplest form, a metagenomic study might be the direct sequence results of DNA extracted from a bucket of sea water.</p>
<p>The EBI Metagenomics service is an automated pipeline for the analysis and archiving of metagenomic data that aims to provide insights into the phylogenetic diversity as well as the functional and metabolic potential of a sample. You can freely browse all the public data in the repository.
</p>
<p>Please take your time to explore and tell us what you think about our website. We welcome your feedback on look and feel, functionality or scientific content. If you want to be kept informed about updates to the website, please subscribe to our mailing list. Also note, as we constantly try to improve the site, it may change from time to time.</p>

<h3 id="mail">Staying informed</h3>

<p>Follow us on Twitter using <a href="http://twitter.com/EBImetagenomics">@EBImetagenomics</a></p>

<h3 id="h_cite">Citing us</h3>

<p>To cite EBI Metagenomics, please refer to the following publication:<br/><br/>
Alex Mitchell, Francois Bucchini, Guy Cochrane, Hubert Denise, Petra ten Hoopen, Matthew Fraser, Sebastien Pesseat, Simon Potter, Maxim Scheremetjew, Peter Sterk and Robert D. Finn (2015).<br/>
<strong>EBI metagenomics in 2016 - an expanding and evolving resource for the analysis and archiving of metagenomic data.</strong> Nucleic Acids Research (2015) doi: <a title="EBI metagenomics in 2016 - an expanding and evolving resource for the analysis and archiving of metagenomic data" href="http://nar.oxfordjournals.org/content/44/D1/D595.full" class="ext">10.1093/nar/gkv1195</a>
</p>
<p id="expand_button">View previous citations</p>
<div class="more_citations">
<p>Sarah Hunter, Matthew Corbett, Hubert Denise, Matthew Fraser, Alejandra Gonzalez-Beltran, Christopher Hunter, Philip Jones, Rasko Leinonen, Craig McAnulla, Eamonn Maguire, John Maslen, Alex Mitchell, Gift Nuka, Arnaud Oisel, Sebastien Pesseat, Rajesh Radhakrishnan, Philippe Rocca-Serra, Maxim Scheremetjew, Peter Sterk, Daniel Vaughan, Guy Cochrane, Dawn Field and Susanna-Assunta Sansone (2013).<br/>
<strong>EBI metagenomics - a new resource for the analysis and archiving of metagenomic data.</strong> Nucleic Acids Research (2013) doi: <a title="EBI metagenomics - a new resource for the analysis and archiving of metagenomic data" href="http://dx.doi.org/10.1093/nar/gkt961" class="ext">10.1093/nar/gkt961</a>
</p>
</div>

<h3 id="funding">Funding</h3>
<div class="about_img_r"><img src="${pageContext.request.contextPath}/img/funding/FP7.jpg" alt="FP7" width="122" height="94"/>
<img src="${pageContext.request.contextPath}/img/funding/BBSRC.png" alt="BBSRC" /></div>
<p>The EBI metagenomics resource was initiated by funding from EMBL. It continues to be developed with support from EMBL and additional
    funding has been gratefully received from the Biotechnology and Biological Sciences Research Council
(BBSRC grant BB/I02612X/1 and BB/M011755/1) and the EU's Seventh Framework Programme for Research (FP7 grant MICROB3).
</p>

<h4 id="credits_2">Photos</h4>

<p>We would like to thank the following authors for their contribution related to the pictures used on the website:</p>
<ul>
    <li>Image: A Blue Starfish (Linckia laevigata) resting on hard Acropora coral. Lighthouse, Ribbon Reefs, Great
        Barrier Reef, by Richard Ling [<a href="http://www.rling.com" class="ext" rel="nofollow">Copyright (c)
            [2004] Richard Ling</a> - <a href="http://commons.wikimedia.org/wiki/File:Blue_Linckia_Starfish.JPG"
                                         class="ext" rel="nofollow"> Wikipedia</a>]
    </li>
    <li>Image: Coli Bacteria, by renjith krishnan [<a
            href="http://www.freedigitalphotos.net/images/view_photog.php?photogid=721" class="ext">FreeDigitalPhotos.net</a>]
    </li>
    <li>Image: Honeycomb, by Apple's Eyes Studio [<a
            href="http://www.freedigitalphotos.net/images/view_photog.php?photogid=2000" class="ext">FreeDigitalPhotos.net</a>]
    </li>
    <li>Image: Oupeye (Belgium) biological sewage treatment, by User:Xofc [<a
            href="http://www.gnu.org/copyleft/fdl.html" class="ext" rel="nofollow">GFDL</a> - <a
            href="http://commons.wikimedia.org/wiki/File:090913-EpurationOupeye_0020.jpeg" class="ext"
            rel="nofollow"> Wikipedia</a>]
    </li>
</ul>

<script type="javascript">$(function () { $("[data-toggle='tooltip']").tooltip(); });</script>
<script type="text/javascript">
       $("#expand_button").click(function(){
        $(".more_citations").slideToggle();
        $("#expand_button").toggleClass("min");
    });

</script>
