<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="content-full">
<h2>About EBI Metagenomics Beta</h2>

<div id="sidebar-nav">
    <ul>
        <%--<li><a href="#intro">Introduction</a></li>
       <li><a href="#feedback">Feedback</a></li>--%>
        <li><a href="#what_about">What is metagenomics?</a></li>
        <li><a href="#resources">Metagenomic-related resources at EBI</a>
            <ul>
                <li><a href="#resources_1">ENA and Raw Sequence data</a></li>
                <li><a href="#resources_2">InterPro</a></li>
            </ul>
        </li>
        <li><a href="#features">Current features</a>
            <ul>
                <li><a href="#features_1">Submitting data</a></li>
                <li><a href="#features_2">Data Privacy</a></li>
                <li><a href="#features_3">Analysis</a></li>
            </ul>
        </li>
        <li><a href="#p_features">Planned features</a></li>
        <li><a href="#mail">Mailing List</a></li>
        <li><a href="#credits">Credits</a>
            <ul>
                <li><a href="#credits_1">The team</a></li>
                <li><a href="#credits_2">Photos</a></li>
            </ul>
        </li>

    </ul>
</div>

<p class="intro">
    EBI Metagenomics Beta is a new web resource targeted at metagenomic researchers. This is the first
    release and we intend to make rapid and frequent updates to improve the interfaces and services provided.
    Additional tools and analyses will become available during the next few months and we actively encourage users to
    make requests for the tools that they wish to see on the site.
    We want to provide a service that is most useful for our users and to do this we need to hear from you.
    If you have any additional feedback about any aspect of the site (good or bad), please
    <a href="<c:url value="${baseURL}/contact"/>" title="Contact us">contact us</a>.
</p>

<h3 id="what_about">What is metagenomics?</h3>

<p>
    The study of all genomes present in any given environment without the need for prior individual identification or
    amplification is termed metagenomics. For example, in its simplest form a metagenomic study might be the direct
    sequence results of DNA extracted from a bucket of sea water.
</p>

<p>

<div class="but_top"><a href="#top" title="back to the top page">Top</a></div>
</p>

<h3 id="resources">Metagenomic-related resources at EBI</h3>

<p>
    The EBI resources of the European Nucleotide Archive (in particular Sequence Read Archive and EMBL-Bank),
    UniProt, InterPro, Ensembl Genomes and IntAct are all used for analysis by metagenomic researchers, but not in
    an integrated manner. We want to provide a user friendly interface to these services, promoting their utility in
    the field of metagenomics. The resource will enable protein prediction, function analysis, comparison to
    complete reference genomes and metabolic pathway analysis.
</p>

<h4 id="resources_1">ENA and Raw Sequence data</h4>

<p>
    The European Nucleotide Archives (ENA) already accept metagenomic sequences, however the only submission route at
    present is via user-generated XML files. Metagenomics users have a need for an improved metagenomics-focused
    service; therefore, we are working closely with the ENA to develop submission tools specifically designed for
    use with the comprehensive metadata of metagenomic studies. Submitting data to the ENA allows users to obtain
    INSDC accession numbers that are required by many journals for publication of metagenomics research.
</p>

<h4 id="resources_2">InterPro</h4>

<p>
    InterPro is an integrated database of protein "signatures" used for the classification and automatic annotation
    of proteins and genomes. InterPro classifies sequences into families and predicts the occurrence of functional
    domains, repeats and important sites. InterPro adds in-depth annotation, including GO terms, to the protein
    signatures. The EBI metagenomics pipeline takes advantage of the analysis tool created by the InterPro team
    (InterProScan) to perform comparative scanning of the pCDSs against the multitude of highly curated protein
    signatures in the various member databases. Certain Interpro member databases are not used in the analysis of
    metagenomic fragments because they yield few or no matches and therefore do not constitute a good use of compute
    time.
</p>

<p>

<div class="but_top"><a href="#top" title="back to the top page">Top</a></div>
</p>

<h3 id="features">Current features</h3>

<p>
    In this current version (Beta) of the resource, we offer users a manually assisted submission route to ensure data
    and metadata formatting to comply with the SRA (Sequence Read Archive) data schema and the GSC (Genomic Standards
    Consortium) sample metadata guidelines respectively. We provide a minimum analysis of the reads submitted and allow
    users to download the results.
</p>

<h4 id="features_1">Submitting data</h4>

<p>
    Presently, analysis is restricted to "long" (average reads lengths over 250nt), unassembled random shotgun
    sequence reads, i.e. Roche 454 sequences, from metagenomic or metatranscriptomic samples. However, we would like
    to hear from users with datasets that do not fit this description to help us better understand your needs so we
    can tailor our future developments appropriately.
</p>

<p>
    Registration is required to submit data for analysis. Please note that the registration system is shared with ENA,
    so if you have ever submitted sequences to EMBL-Bank you will already have a valid account.
</p>

<h4 id="features_2">Data Privacy</h4>

<p>
    Any data submitted to us can be kept private (by secure user login) for a period of up to 2 years. However it
    should be noted that ALL data must eventually be suitable for public release. Human associated samples (e.g.
    human gut samples) must be filtered by users prior to submission to remove any Human contaminants (Note that we
    intend to offer a filtering step in the near future.)
</p>

<h4 id="features_3">Analysis</h4>

<figure style="margin-right:14px;">
    <a href="${pageContext.request.contextPath}/img/chart_pipeline_400.gif"><img src="${pageContext.request.contextPath}/img/chart_pipeline.gif" alt="Pipeline chart"/> </a>
    <figcaption>
        <h3>Metagenomics pipeline chart</h3>

        <p>The basic pipeline is outlined this chart.</p>
    </figcaption>
</figure>

<p>The basic pipeline is outlined this chart.</p>

<p> The raw sequence reads are clipped to remove technical parts to the reads and poor quality ends. They are then
    filtered to remove very short reads, duplicate reads and reads containing >10% unknown base calls (N's).</p>

<p>The remaining reads are subject to analysis by FragGeneScan (FGS) which is a tool to predict coding sequences
    (pCDS) within the reads. For further details on FGS please see <a
            href="http://nar.oxfordjournals.org/content/early/2010/08/29/nar.gkq747.abstract" class="ext">FragGeneScan:
        predicting genes in short and error-prone reads</a>. The pCDS are then analysed using InterProScan (uses a
    subset of InterPro's member database applications). The InterProScan results table is downloadable for further
    interrogation and contains the interpro IDs, member database IDs and associated GO terms.
</p>
<p>
    The interpro results table is dowloadable in TSV format which can be opened in any spreadsheet application
    (e.g. Microsoft Excel). The columns in the table are:
</p>
<ul>
    <li>Sequence ID</li>
    <li>MD5 checksum of sequence</li>
    <li>Length of pCDS (in amino acids)</li>
    <li>Member database of match</li>
    <li>Member database ID of match</li>
    <li>Member database description for that ID</li>
    <li>Start position of match</li>
    <li>End position of match</li>
    <li>E-Value of match</li>
    <li>T (Match status)</li>
    <li>Date analysis ran</li>
    <li>InterPro ID</li>
    <li>InterPro name</li>
    <li>Comma separated list of associated GO terms in the format: &lt;ontology&gt;:&lt;description&gt;&lt;(GO:ID)&gt;,"</li>
</ul>


<p>
    <div class="but_top"><a href="#top" title="back to the top page">Top</a></div>
</p>


<h3 id="p_features">Planned features</h3>

<p>The following features are planned for future releases of the resource:<br>
<ol>
    <li>Inclusion of an assembly step for short reads</li>
    <li>Better summary information visualisations per sample</li>
    <li>Inclusion of 16S rRNA diversity analysis</li>
    <li>Interactive visualisation of InterPro matches with links to InterPro entries</li>
</ol>
</p>
<p>

<div class="but_top"><a href="#top" title="back to the top page">Top</a></div>
</p>

<h3 id="mail">Mailing List</h3>

<p>If you would like to be kept informed of further developments with the EBI metagenomics resources please sign up
    for the
    <a href="http://listserver.ebi.ac.uk/mailman/listinfo/metagenomics" class="ext"> EBI metagenomics mailing
        list</a>.
</p>

<p>

<div class="but_top"><a href="#top" title="back to the top page">Top</a></div>
</p>

<h3 id="credits">Credits</h3>

<h4 id="credits_1">The team</h4>

<p>This new resource for metagenomics is supported by the <a
        href="http://www.ebi.ac.uk/Information/Staff/searchx.php?s_keyword=&s_group=28"
        title="The InterPro team at EBI" class="ext">InterPro Team</a> at EBI, in collaboration with the ENA and
    UniProt groups.</p>

<h4 id="credits_2">Photos</h4>

<p>We would like to thanks for their contribution related to the top banner pictures, the following authors:</p>
<ul>
    <li>Image: A Blue Starfish (Linckia laevigata) resting on hard Acropora coral. Lighthouse, Ribbon Reefs, Great
        Barrier Reef, by Richard Ling [<a href="http://www.rling.com" class="ext" rel="nofollow">Copyright (c)
            [2004] Richard Ling</a> - <a href="http://commons.wikimedia.org/wiki/File:Blue_Linckia_Starfish.JPG"
                                         class="ext" rel="nofollow"> Wikipedia</a>]
    </li>
    <li>Image: Coli Bacteria, by renjith krishnan [<a
            href="http://www.freedigitalphotos.net/images/view_photog.php?photogid=721" class="ext">FreeDigitalPhotos.net</a>]
    </li>
    <li>Image: Honeycomb, by Apple\'s Eyes Studio [<a
            href="http://www.freedigitalphotos.net/images/view_photog.php?photogid=2000" class="ext">FreeDigitalPhotos.net</a>]
    </li>
    <li>Image: Oupeye (Belgium) biological sewage treatment, by User:Xofc [<a
            href="http://www.gnu.org/copyleft/fdl.html" class="ext" rel="nofollow">GFDL</a> - <a
            href="http://commons.wikimedia.org/wiki/File:090913-EpurationOupeye_0020.jpeg" class="ext"
            rel="nofollow"> Wikipedia</a>]
    </li>
</ul>
<p>

<div class="but_top"><a href="#top" title="back to the top page">Top</a></div>
</p>
</div>
