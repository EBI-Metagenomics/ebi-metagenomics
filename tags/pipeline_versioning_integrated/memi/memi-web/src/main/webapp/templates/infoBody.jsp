<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>About EBI Metagenomics</h2>



<h3 id="what_about">EBI Metagenomics service</h3>

<div id="sidebar-nav">
    <ul>
        <%--<li><a href="#intro">Introduction</a></li>
       <li><a href="#feedback">Feedback</a></li>--%>
        <li><a href="#what_about">EBI Metagenomics service</a></li>
        <li><a href="#features">Why choose EBI Metagenomics</a>
            <ul>
                <li><a href="#features_1">Easy submission</a></li>
                <li><a href="#features_2">Powerful analysis</a></li>
                <li><a href="#features_3">Data archiving</a></li>
            </ul>
        </li>
        <li><a href="#submit">How to use EBI Metagenomics for data analysis</a>
            <ul>
                <li><a href="#registration">Register with us</a></li>
                <li><a href="#format">Check your data format</a></li>
                <li><a href="#prepublication">Filter any human-associated samples</a></li>
            </ul>
        </li>
        <li><a href="#analysis">How we analyse the data</a></li>
        <li><a href="#h_cite">How to cite</a></li>
        <li><a href="#p_features">Planned features</a></li>
        <li><a href="#funding">Funding</a></li>
        <li><a href="#mail">Follow us</a></li>
        <li><a href="#credits">Credits</a> </li>

    </ul>
</div>

<figure class="fr"><img src="${pageContext.request.contextPath}/img/pict_metagenomics_01.png" alt="Metagenomics"/>
<figcaption><p>Metagenomics is the study of all genomes present in any given environment...</p></figcaption></figure>
<p>Metagenomics is the study of all genomes present in any given environment without the need for prior individual identification or amplification. For example, in its simplest form, a metagenomic study might be the direct sequence results of DNA extracted from a bucket of sea water.</p>
<p>The EBI Metagenomics service is an automated pipeline for the analysis and archiving of metagenomic data that aims to provide insights into the phylogenetic diversity as well as the functional and metabolic potential of a sample. You can freely browse all the public data in the repository.
</p>
<p>Please take your time to explore and tell us what you think about our website. We welcome your feedback on look and feel, functionality or scientific content. If you want to be kept informed about updates to the website, please subscribe to our mailing list. Also note, as we constantly try to improve the site, it may change from time to time.</p>


<h3 id="features">Why choose EBI Metagenomics?</h3>

<h4 id="features_1">Easy submission</h4>
<div class="about_img_l"><img src="${pageContext.request.contextPath}/img/icons_sub.png" alt="easy submission" /></div>
<p>The EBI Metagenomics service offers a manually-assisted submission route, with help available to ensure data and metadata formatting comply with the European Nucleotide Archive (ENA) data schema and the Genomic Standards Consortium (GSC) sample metadata guidelines respectively, allowing harmonisation of analysis efforts across the wider genomics community.
</p>

<h4 id="features_2">Powerful analysis</h4>
<div class="about_img_l"><img src="${pageContext.request.contextPath}/img/icons_ana.png" alt="powerful analysis" /></div>
<p>The service identifies rRNA sequences, using rRNASelector, and performs taxonomic analysis upon 16S rRNAs using Qiime. The remaining reads are submitted for functional analysis of predicted protein coding sequences using the InterPro sequence analysis resource. InterPro uses diagnostic models to classify sequences into families and to predict the presence of functionally important domains and sites. By utilising this resource, the service offers a powerful and sophisticated alternative to BLAST-based functional metagenomic analyses.
<br/><br/></p>

<h4 id="features_3">Data archiving</h4>
<div class="about_img_l"><img src="${pageContext.request.contextPath}/img/icons_arc.png" alt="data archiving"/></div>
<p>Data submitted to the EBI Metagenomics service is automatically archived in the European Nucleotide Archive (ENA). Accession numbers are supplied for sequence data as part of the archiving process, which is a prerequisite for publication in many journals.
The ENA only accepts data that is intended for public release. However, any data submitted to us can be kept confidential (by secure user login) for a period of up to 2 years to allow time for the data producer to publish their findings. It should be noted that ALL data must eventually be suitable for public release. </p>


<h3 id="submit">How to use EBI Metagenomics for data analysis</h3>

<h4 id="registration">Register with us</h4>
<p>Registration is required to submit data for analysis. Please note that the registration system is shared with ENA, so if you have previously submitted sequences to EMBL-Bank you will already have a valid account.
</p>

<h4 id="format">Check your data format</h4>
<p>The service accepts all NGS shotgun sequence reads, including Roche 454, Illumina and IonTorrent sequences, from metagenomic or metatranscriptomic samples. Amplicon marker gene studies may be included, particularly if they are associated with other meta-omic data for a sample.</p>
 <p>If your dataset does not fit these descriptions, please <a title="EBI's support & feedback form" href="http://www.ebi.ac.uk/support/metagenomics" class="ext">contact us</a> to help us better understand your needs.</p>

<h4 id="prepublication">Filter any human-associated samples</h4>
<p>Human associated samples (e.g., human gut samples) must be filtered prior to submission to remove any human contaminants.</p>


<h3 id="analysis">How we analyse the data</h3>

<p>Please explore here: <a title="EBI metagenomics - pipeline releases" href="<c:url value="${baseURL}/pipelines"/>">Pipeline Release Archive</a></p>

<h3 id="h_cite">How to cite</h3>

<p>To cite EBI Metagenomics, please refer to the following publication:<br/><br/>
Sarah Hunter, Matthew Corbett, Hubert Denise, Matthew Fraser, Alejandra Gonzalez-Beltran, Christopher Hunter, Philip Jones, Rasko Leinonen, Craig McAnulla, Eamonn Maguire, John Maslen, Alex Mitchell, Gift Nuka, Arnaud Oisel, Sebastien Pesseat, Rajesh Radhakrishnan, Philippe Rocca-Serra, Maxim Scheremetjew, Peter Sterk, Daniel Vaughan, Guy Cochrane, Dawn Field and Susanna-Assunta Sansone (2013).<br/>
<strong>EBI metagenomics - a new resource for the analysis and archiving of metagenomic data.</strong> Nucleic Acids Research (2013) doi: <a title="EBI metagenomics - a new resource for the analysis and archiving of metagenomic data" href="http://dx.doi.org/10.1093/nar/gkt961" class="ext">10.1093/nar/gkt961</a>
</p>


<h3 id="p_features">Planned features</h3>

<p>We intend to make frequent updates to the interfaces and services provided. The following features are planned for future releases of the resource:<br/>
<ul>
    <li>Including comparative analysis of results</li>
    <li>Human contaminant filtering</li>
</ul>
</p>

<%--<p>We actively encourage users to make requests for additional tools and analyses that they wish to see as part of the service. We also welcome general feedback. Please <a title="EBI's support & feedback form" href="http://www.ebi.ac.uk/support/metagenomics" class="ext">contact us</a> if you have comments about any aspect of the site</p>--%>

<h3 id="funding">Funding</h3>
<div class="about_img_r"><img src="${pageContext.request.contextPath}/img/funding/FP7.jpg" alt="FP7" width="122" height="94"/>
<img src="${pageContext.request.contextPath}/img/funding/BBSRC.png" alt="BBSRC" /></div>
<p>The EBI metagenomics resource was initiated by funding from EMBL. It continues to be developed with support from EMBL and additional
    funding has been gratefully received from the Biotechnology and Biological Sciences Research Council
(BBSRC grant BB/I02612X/1) and the EU's Seventh Framework Programme for Research (FP7 grant MICROB3).
</p>

<h3 id="mail">Follow us</h3>

<p>If you would like to be kept informed of further developments with the EBI metagenomics resources please follow us on Twitter using
<a href="http://twitter.com/EBImetagenomics" class="ext">@EBImetagenomics</a>.</p>


<h3 id="credits">Credits</h3>

<p>We would like to thanks our beta testers for providing valuable feedback.</p>

<h4 id="credits_1">The team</h4>

<p>This new resource for metagenomics is supported by the <a
        href="https://www.ebi.ac.uk/services/teams/metagenomics"
        title="The EBI Metagenomics team" class="ext">EBI Metagenomics Team</a>, in collaboration with the <a href="http://www.ebi.ac.uk/ena/" class="ext"> ENA</a> and
    <a href="http://www.uniprot.org/" class="ext"> UniProt</a> groups.</p>

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
