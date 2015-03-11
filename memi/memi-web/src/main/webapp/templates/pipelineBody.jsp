<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2 style="margin-bottom:0;/*for pipeline image higher*/">Pipeline version ${releaseVersion}  <span>- ${releaseDate}</span></h2>

<c:choose>
    <c:when test="${releaseVersion == '2.0'}">

        <!-- Pipeline chart for version 2.0-->
        <div class="block_wrapper">

            <div class="block_container">

                <div class="mainbranch">
                    <div class="block-lb" id="item_01">Raw reads</div><div class="arrow_pip"></div>
                    <div class="block step1" id="item_02">QC</div><div class="arrow_pip"></div>
                    <div class="block-lb" id="item_03">Processed reads</div><div class="arrow_pip"></div>
                    <div class="block step2" id="item_04">rRNASelector</div>
                </div>

                <div class="qclist"><ul><li>Trim low quality (Trimmomatic)</li>
                    <li>Length filtering (Biopython)</li></ul></div>

                <div class="branch">
                    <div class="branch1">
                        <div class="arrow_pip rotate_f"></div><div class="block-lb" id="item_05">Reads without rRNA</div><div class="arrow_pip"></div>
                        <div class="block step3 function" id="item_06">FragGeneScan</div><div class="arrow_pip"></div>
                        <div class="block-lb" id="item_07">Reads with pcds</div><div class="arrow_pip"></div>
                        <div class="block step4 function" id="item_08">InterProScan</div>
                        <div class="block-nt">Functional analysis</div>
                    </div> <!-- /branch1 -->

                    <div class="branch2">  <div class="arrow_pip rotate_t"></div>
                        <div class="block-lb" id="item_09">Reads with rRNA</div>
                        <div class="arrow_pip"></div>
                        <div class="block-lb" id="item_11">16s rRNA</div>
                        <div class="arrow_pip"></div>
                        <div class="block step5 taxon" id="item_10">QIIME</div>
                        <div class="block-nt">Taxonomic analysis</div>
                    </div><!-- /branch2 -->

                </div><!-- /branch -->
            </div> <!-- /container -->
        </div>   <!-- /block_wrapper -->
        <!-- /pipeline chart version 2.0-->
    </c:when>
    <c:otherwise>
        <!-- Pipeline chart for version 1.0-->
        <div class="block_wrapper">

            <div class="block_container">

                <div class="mainbranch">
                    <div class="block-lb" id="item_01">Raw reads</div><div class="arrow_pip"></div>
                    <div class="block step1" id="item_02">QC</div><div class="arrow_pip"></div>
                    <div class="block-lb" id="item_03">Processed reads</div><div class="arrow_pip"></div>
                    <div class="block step2" id="item_04">rRNASelector</div>
                </div>

                <div class="qclist"><ul><li>Trim low quality (Trimmomatic)</li>
                    <li>Length filtering (Biopython)</li>
                    <li>Duplicate Removal (UCLUST & Prefix)</li>
                    <li>Filtering low complexity region (RepeatMasker)</li></ul></div>

                <div class="branch">
                    <div class="branch1">
                        <div class="arrow_pip rotate_f"></div><div class="block-lb" id="item_05">Reads without rRNA</div><div class="arrow_pip"></div>
                        <div class="block step3 function" id="item_06">FragGeneScan</div><div class="arrow_pip"></div>
                        <div class="block-lb" id="item_07">Reads with pcds</div><div class="arrow_pip"></div>
                        <div class="block step4 function" id="item_08">InterProScan</div>
                        <div class="block-nt">Functional analysis</div>
                    </div> <!-- /branch1 -->

                    <div class="branch2">  <div class="arrow_pip rotate_t"></div>
                        <div class="block-lb" id="item_09">Reads with rRNA</div>
                        <div class="arrow_pip"></div>
                        <div class="block-lb" id="item_11">16s rRNA</div>
                        <div class="arrow_pip"></div>
                        <div class="block step5 taxon" id="item_10">QIIME</div>
                        <div class="block-nt">Taxonomic analysis</div>
                    </div><!-- /branch2 -->

                </div><!-- /branch -->
            </div> <!-- /container -->
        </div>   <!-- /block_wrapper -->
        <!-- /pipeline chart version 1.0-->
    </c:otherwise>
</c:choose>

<h3>Pipeline Tools</h3>

<table class="pipeline_table">
    <thead>
    <tr>
        <th>Tools</th>
        <th>Version</th>
        <th>Description</th>
    </tr>
    </thead>
    <tbody>
    <!-- Change table row class for each tool -->
    <c:forEach var="pipelineReleaseTool" items="${pipelineReleaseTools}" varStatus="status">
        <c:set var="pipelineTool" value="${pipelineReleaseTool.pk.pipelineTool}"/>
        <c:set var="tool" value="${pipelineTool.toolName}"/>
        <c:set var="toolGroupId" value="${pipelineReleaseTool.toolGroupId}"/>
        <c:choose>
            <c:when test="${tool == 'InterProScan' || tool == 'FragGeneScan'}">
                <tr class="step${toolGroupId} row-function">
            </c:when>
            <c:when test="${tool == 'Trimmomatic' || tool == 'Biopython' || tool == 'UCLUST' || tool == 'RepeatMasker' || tool == 'rRNASelector'}">
                <tr class="step${toolGroupId} row-cb">
            </c:when>
            <c:when test="${tool == 'QIIME' }">
                <tr class="step${toolGroupId} row-taxon">
            </c:when>

            <c:otherwise>
                <tr class="step row-cb">
                <script>
                    //alert("tool not defined");
                </script>
            </c:otherwise>
        </c:choose>

        <td ><a class="ext" href="${pipelineTool.webLink}">${pipelineTool.toolName}</a></td>
        <td>${pipelineTool.toolVersion}</td>
        <td>${pipelineTool.description}</td>
        </tr>

    </c:forEach>
    </tbody>
</table>



<h3>Data processing steps</h3>
<p>
<c:choose>
    <c:when test="${releaseVersion == '2.0'}">
        <ol class="pipeline_step">
            <li>1. Submitted reads - paired-end reads are merged with SeqPrep v1.1</li>
            <li>2. Reads are processed
                <ol>
                    <li>2.1. Clipped - low quality ends trimmed and adapter sequences removed using Biopython SeqIO
                        package
                    </li>
                    <li>2.2. Quality filtered - sequences with > 10% undetermined nucleotides removed</li>
                    <li>2.3. Read length filtered - sequences < 100 nt in length removed</li>
                </ol>
            <li>3. rRNA recognized by rRNASelector prokaryotic ribosomal RNA hidden Markov models reads are masked</li>
            <li>4. Taxonomic analysis is performed on 16S rRNA reads using QIIME 1.9.0 (default closed-reference OTU picking protocal with Greengenes 13.8 reference with reverse strand matching enabled)</li>
            <li>5. CDS prediction with FragGeneScan v.1.15 </li>
            <li>6. Functional analysis of predicted proteins from step 5 is performed with InterProScan 5.9 using a subset of databases from
                InterPro release 50.0 (databases used for analysis: Pfam, TIGRFAM, PRINTS, PROSITE patterns, Gene3d).
                The Gene Ontology term summary was generated using the following GO slim: goslim_goa
            </li>

        </ol>
        </p>
    </c:when>
    <c:otherwise>
        <p>
        <ol class="pipeline_step">
            <li>1. Reads submitted</li>
            <li>2. Nucleotide sequences processed
                <ol>
                    <li>2.1. Clipped - low quality ends trimmed and adapter sequences removed using Biopython
                        SeqIO
                        package
                    </li>
                    <li>2.2. Quality filtered - sequences with > 10% undetermined nucleotides removed</li>
                    <li>2.3. Read length filtered - sequences < 100 nt in length removed</li>
                    <li>2.4. Duplicate sequences removed - clustered on 99% identity (UCLUST v 1.1.579) for LS454 or on 50nt prefix identity (using pick_otus.py script which is part of Qiime v1.15) for other sequencing platforms and one representative sequence chosen
                    </li>
                    <li>2.5. Repeat masked - RepeatMasker (open-3.2.2), removed reads with 50% or more
                        nucleotides
                        masked
                    </li>
                </ol>
            </li>
            <li>3. rRNA reads are filtered using rRNASelector (rRNASelector v 1.0.0)</li>
            <li>4. Taxonomic analysis is performed upon 16s rRNA using Qiime (Qiime v 1.5).</li>
            <li>5. CDS predicted (FragGeneScan v 1.15)</li>
            <li>6. Matches were generated against predicted CDS with InterProScan 5.0 (beta release) using a subset of
                databases from InterPro release 31.0 (databases used for analysis: Pfam, TIGRFAM, PRINTS, PROSITE
                patterns, Gene3d). The Gene Ontology term summary was generated using the following GO slim: goslim_goa
            </li>
        </ol>
        </p>
    </c:otherwise>
</c:choose>
<script>
    $(document).ready(function() {

        //add class "highlight" when hover over the row
        $('table tbody tr').hover(function() {
            $(this).addClass('highlight');
        }, function() {
            $(this).removeClass('highlight');
        });

        //for step1
        $('.block.step1').hover(function() {
            $('.block.step1, table tbody tr.step1').addClass('highlight');

        }, function() {
            $('.block.step1, table tbody tr.step1').removeClass('highlight');
        });

        $('table tbody tr.step1').hover(function() {
            $('.step1').addClass('highlight');
        }, function() {
            $('.step1').removeClass('highlight');
        });

        //for step2
        $('.block.step2').hover(function() {
            $('.block.step2, table tbody tr.step2').addClass('highlight');
        }, function() {
            $('.block.step2, table tbody tr.step2').removeClass('highlight');
        });

        $('table tbody tr.step2').hover(function() {
            $('.step2').addClass('highlight');
        }, function() {
            $('.step2').removeClass('highlight');
        });

        //for step3
        $('.block.step3').hover(function() {
            $('.block.step3, table tbody tr.step3').addClass('highlight');
        }, function() {
            $('.block.step3, table tbody tr.step3').removeClass('highlight');
        });

        $('table tbody tr.step3').hover(function() {
            $('.step3').addClass('highlight');
        }, function() {
            $('.step3').removeClass('highlight');
        });

        //for step4
        $('.block.step4').hover(function() {
            $('.block.step4, table tbody tr.step4').addClass('highlight');
        }, function() {
            $('.block.step4, table tbody tr.step4').removeClass('highlight');
        });

        $('table tbody tr.step4').hover(function() {
            $('.step4').addClass('highlight');
        }, function() {
            $('.step4').removeClass('highlight');
        });

        //for step5
        $('.block.step5').hover(function() {
            $('.block.step5, table tbody tr.step5').addClass('highlight');
        }, function() {
            $('.block.step5, table tbody tr.step5').removeClass('highlight');
        });

        $('table tbody tr.step5').hover(function() {
            $('.step5').addClass('highlight');
        }, function() {
            $('.step5').removeClass('highlight');
        });
    });
</script>