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

<p>Metagenomics is the study of all genomes present in any given environment without the need for prior individual identification or amplification. For example, in its simplest form, a metagenomic study might be the direct sequence results of DNA extracted from a bucket of sea water.</p>
<p>The EBI Metagenomics service is an automated pipeline for the analysis and archiving of metagenomic data that aims to provide insights into the phylogenetic diversity as well as the functional and metabolic potential of a sample. You can freely browse all the public data in the repository.
</p>
<p>Please take your time to explore and tell us what you think about our website. We welcome your feedback on look and feel, functionality or scientific content. If you want to be kept informed about updates to the website, please subscribe to our mailing list. Also note, as we constantly try to improve the site, it may change from time to time.</p>

<h3 id="mail">Staying informed</h3>

<p>Follow us on Twitter using <a href="http://twitter.com/EBImetagenomics">@EBImetagenomics</a></p>

<h3 id="h_cite">Citing us</h3>

<p>To cite EBI Metagenomics, please refer to the following publication:<br/><br/>
    Alex L. Mitchell, Maxim Scheremetjew, Hubert Denise, Simon Potter, Aleksandra Tarkowska, Matloob Qureshi, Gustavo A. Salazar, Sebastien Pesseat, Miguel A. Boland, Fiona M. I. Hunter, Petra ten Hoopen, Blaise Alako, Clara Amid, Darren J. Wilkinson, Thomas P. Curtis, Guy Cochrane, Robert D. Finn (2017).<br/>
<strong>EBI Metagenomics in 2017: enriching the analysis of microbial communities, from sequence reads to assemblies.</strong> Nucleic Acids Research (2017) doi: <a title="EBI Metagenomics in 2017: enriching the analysis of microbial communities, from sequence reads to assemblies" href=https://doi.org/10.1093/nar/gkx967" class="ext">10.1093/nar/gkx967</a>
</p>
<p id="expand_button">View previous citations</p>
<div class="more_citations">
    <p>Alex Mitchell, Francois Bucchini, Guy Cochrane, Hubert Denise, Petra ten Hoopen, Matthew Fraser, Sebastien Pesseat, Simon Potter, Maxim Scheremetjew, Peter Sterk and Robert D. Finn (2015).<br/>
        <strong>EBI metagenomics in 2016 - an expanding and evolving resource for the analysis and archiving of metagenomic data.</strong> Nucleic Acids Research (2015) doi: <a title="EBI metagenomics in 2016 - an expanding and evolving resource for the analysis and archiving of metagenomic data" href="http://nar.oxfordjournals.org/content/44/D1/D595.full" class="ext">10.1093/nar/gkv1195</a>
    </p>
<p>Sarah Hunter, Matthew Corbett, Hubert Denise, Matthew Fraser, Alejandra Gonzalez-Beltran, Christopher Hunter, Philip Jones, Rasko Leinonen, Craig McAnulla, Eamonn Maguire, John Maslen, Alex Mitchell, Gift Nuka, Arnaud Oisel, Sebastien Pesseat, Rajesh Radhakrishnan, Philippe Rocca-Serra, Maxim Scheremetjew, Peter Sterk, Daniel Vaughan, Guy Cochrane, Dawn Field and Susanna-Assunta Sansone (2013).<br/>
<strong>EBI metagenomics - a new resource for the analysis and archiving of metagenomic data.</strong> Nucleic Acids Research (2013) doi: <a title="EBI metagenomics - a new resource for the analysis and archiving of metagenomic data" href="http://dx.doi.org/10.1093/nar/gkt961" class="ext">10.1093/nar/gkt961</a>
</p>
</div>

<h3 id="funding">Funding</h3>

<h4 id="funding_present">Present</h4>

<div class="about_img_r"><img src="${pageContext.request.contextPath}/img/funding/embl_logo.png" alt="EMBL" />
    <img src="${pageContext.request.contextPath}/img/funding/BBSRC.png" alt="BBSRC" /></div>
<p>The EBI metagenomics resource is supported by the Biotechnology and Biological Sciences Research Council (BBSRC) [grant references BB/M011755/1 and BB/N018354/1]],
    the European Commission within the Research Infrastructures programme of Horizon 2020 [grant agreement number 676559]  (ELIXIR-EXCELERATE)
    and InnovateUK (project reference 102513). Funding for open access charge: Research Councils UK (RCUK).
</p>
<div class="about_img_r"><img src="${pageContext.request.contextPath}/img/funding/excelerate_whitebackground.png" alt="Excelerate" />
    <img src="${pageContext.request.contextPath}/img/funding/innovate-uk-logo.png" alt="InnovateUK" /></div>

<h4 id="funding_past">Past</h4>

<%--<div class="about_img_r"><img src="${pageContext.request.contextPath}/img/funding/FP7.jpg" alt="FP7" width="122" height="94"/>--%>
<%--<img src="${pageContext.request.contextPath}/img/funding/BBSRC.png" alt="BBSRC" /></div>--%>
<p>The EBI metagenomics resource was initiated by funding from EMBL. It continues to be developed with support from EMBL and additional
    funding has been gratefully received from the Biotechnology and Biological Sciences Research Council
(BBSRC grant BB/I02612X/1 and BB/M011755/1) and the EU's Seventh Framework Programme for Research (FP7 grant MICROB3).
</p>


<script type="javascript">$(function () { $("[data-toggle='tooltip']").tooltip(); });</script>
<script type="text/javascript">
       $("#expand_button").click(function(){
        $(".more_citations").slideToggle();
        $("#expand_button").toggleClass("min");
    });

</script>
