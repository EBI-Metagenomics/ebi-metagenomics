<div id="content">
    <h2>About Metagenomics</h2>

    <ul>
        <li><a href="#intro">Introduction</a></li>
        <li><a href="#feedback">Feedback</a></li>
        <li><a href="#what_about">What is metagenomics?</a></li>
        <li><a href="#resources">Metagenomic-related resources at EBI</a>
            <ul>
                <li><a href="#resources_1">ENA and Raw Sequence data</a></li>
                <li><a href="#resources_2">InterPro</a></li>
            </ul>
        </li>
        <li><a href="#features">Current features of the metagenomics portal</a>
            <ul>
                <li><a href="#features_1">Submitting data</a></li>
                <li><a href="#features_2">Data Privacy</a></li>
                <li><a href="#features_3">Analysis</a></li>
            </ul>
        </li>
        <li><a href="#p_features">Planned features</a></li>
        <li><a href="#mail">Mailing List</a></li>
    </ul>

    <div style="margin-top:6px"></div>
    <h3 id="intro">Introduction</h3>

    <p>EBI Metagenomics is a new web resource targeted at metagenomic researchers. This is the first release and we
        intend to make rapid and frequent updates to improve the interfaces and services provided. We want provide a
        service that is most useful to our users and do this we need to <a
                href="mailto:chrish@ebi.ac.uk?subject=beta-metagenomics-feedback">hear from you</a>.
        Additional tools and analyses
        will become available during the next few months and we actively encourage users to make requests for the tools
        that they wish to see on the site. If you have any additional feedback about any aspect of the site (good or
        bad), please <a href="mailto:chrish@ebi.ac.uk?subject=beta-metagenomics-feedback">contact
            us</a>.</p>

    <h3 id="feedback">Feedback</h3>

    <p>Please use the <a href="mailto:chrish@ebi.ac.uk?subject=beta-metagenomics-feedback">feedback
        form</a> to provide any comments or feedback about EBI metagenomics.</p>

    <h3 id="what_about">What is metagenomics?</h3>

    <p>The study of all genomes present in any given environment without the need for prior individual identification or
        amplification is termed metagenomics. For example, in its simplest form a metagenomic study might be the direct
        sequence results of DNA extracted from a bucket of sea water.</p>

    <h3 id="resources">Metagenomic-related resources at EBI</h3>

    <p>The EBI resources of the European Nucleotide Archive (in particular Sequence Read Archive and EMBL-Bank),
        UniProt, InterPro, Ensembl Genomes and IntAct are all used for analysis by metagenomic researchers, but not in
        an integrated manner. We want to provide a user friendly interface to these services, promoting their utility in
        the field of metagenomics. The resource will enable protein prediction, function analysis, comparison to
        complete reference genomes and metabolic pathway analysis.</p>

    <h4 id="resources_1">ENA and Raw Sequence data</h4>

    <p>The European Nucleotide Archives (ENA) already accept metagenomic sequences, however the only submission route at
        present is via user-generated XML files. Metagenomics users have a need for an improved metagenomics-focused
        service; therefore, we are working closely with the ENA to develop submission tools specifically designed for
        use with the comprehensive metadata of metagenomic studies. Submitting data to the ENA allows users to obtain
        INSDC accession numbers that are required by many journals for publication of metagenomics research.</p>

    <h4 id="resources_2">InterPro</h4>

    <p>InterPro is an integrated database of protein "signatures" used for the classification and automatic annotation
        of proteins and genomes. InterPro classifies sequences into families and predicts the occurrence of functional
        domains, repeats and important sites. InterPro adds in-depth annotation, including GO terms, to the protein
        signatures. The EBI metagenomics pipeline takes advantage of the analysis tool created by the InterPro team
        (InterProScan) to perform comparative scanning of the pCDSs against the multitude of highly curated protein
        signatures in the various member databases. Certain Interpro member databases are not used in the analysis of
        metagenomic fragments because they yield few or no matches and therefore do not constitute a good use of compute
        time.</p>

    <h3 id="features">Current features of the metagenomics portal</h3>

    <p>In this current version of the resource, we offer users a personal service to assist with data and metadata
        formatting to comply with the SRA (Sequence Read Archive) data schema and the GSC (Genomic Standards Consortium)
        sample metadata guidelines respectively. We provide a minimum analysis of the reads submitted and allow users to
        download the results.</p>

    <h4 id="features_1">Submitting data</h4>

    <p>Presently, analysis is restricted to "long" (average reads lengths over 250nt), unassembled random shotgun
        sequence reads, i.e. Roche 454 sequences, from metagenomic or metatranscriptomic samples. However, we would like
        to hear from users with datasets that do not fit this description to help us better understand your needs so we
        can tailor our future developments appropriately.</p>

    <p>Anyone with an account is free to submit data for analysis.</p>

    <h4 id="features_2">Data Privacy</h4>

    <p>Any data submitted to us can be kept private (by secure user login) for a period of up to 2 years. However it
        should be noted that ALL data must eventually be suitable for public release. Human associated samples (e.g.
        human gut samples) must be filtered by users prior to submission to remove any Human contaminants (Note that we
        intend to offer a filtering step in the near future.)</p>

    <p>Anyone with an account is free to submit data for analysis.</p>

    <h4 id="features_3">Analysis</h4>

    <p>The raw sequence reads are clipped to remove technical parts to the reads and poor quality ends. They are then
        filtered to remove very short reads, duplicate reads and reads containing >10% unknown base calls (N's).</p>

    <p>The remaining reads are subject to analysis by FragGeneScan (FGS) which is a tool to predict coding sequences
        (pCDS) within the reads. For further details on FGS please see <a
                href="http://nar.oxfordjournals.org/content/early/2010/08/29/nar.gkq747.abstract">FragGeneScan:
            predicting genes in short and error-prone reads</a>. The pCDS are then analysed using InterProScan (uses a
        subset of InterPro's member database applications).</p>

    <h3 id="p_features">Planned features</h3>

    <p>The following features are planned for the upcoming releases of the resource:<br>
    <ol>
        <li>Inclusion of an assembly step for short reads</li>
        <li>Better summary information visualisations per sample</li>
        <li>Inclusion of 16S rRNA diversity analysis</li>
    </ol>
    </p>

    <h3 id="mail">Mailing List</h3>

    <p>If you would like to be kept informed of further developments with the EBI metagenomics resources please sign up
        for our mailing list by <a
                href="mailto:chrish@ebi.ac.uk?subject=signup to MG-mailing-list">sending Chris an
            email</a> with the subject line "signup to MG-mailing-list".</p>
</div>