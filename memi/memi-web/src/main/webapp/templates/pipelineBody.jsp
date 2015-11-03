<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<h2 class="pipeline_title">Pipeline version ${releaseVersion}  <span>- ${releaseDate}</span></h2>

<c:choose>

    <c:when test="${releaseVersion == '2.0'}">

        <!-- Pipeline chart for version 2.0-->
        <div class="block_wrapper">

            <div class="block_container">

                <div class="mainbranch">
                    <div class="block-lb" id="item_03">Raw reads</div><div class="arrow_pip "></div>
                                                <div class="block small step0" id="item_000">SeqPrep</div><div class="arrow_pip "></div>
                                                <div class="block-lb" id="item_01">Initial reads</div><div class="arrow_pip"></div>
                    <div class="block step1" id="item_02">QC
                        <div class="qclist"><ul><li>Trim low quality (Trimmomatic)</li>
                                        <li>Length filtering (Biopython)</li></ul></div></div><div class="arrow_pip"></div>
                    <div class="block-lb" id="item_03">Processed reads</div><div class="arrow_pip"></div>
                    <div class="block step2" id="item_04">rRNASelector</div>
                </div>



                <div class="branch">
                    <div class="branch1">
                        <div class="arrow_pip rotate_f"></div><div class="block-lb" id="item_05">Reads with rRNA masked</div><div class="arrow_pip"></div>
                        <div class="block step3 function" id="item_06">FragGeneScan</div><div class="arrow_pip"></div>
                        <div class="block-lb" id="item_07">Predicted CDS</div><div class="arrow_pip"></div>
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
        <!-- Pipeline chart for version 1.0 -->
        <div class="block_wrapper">

                    <div class="block_container">

                        <div class="mainbranch">
                            <div class="block-lb" id="item_03">Raw reads</div><div class="arrow_pip "></div>
                            <div class="block small step0" id="item_000">SeqPrep</div><div class="arrow_pip "></div>
                            <div class="block-lb" id="item_01">Initial reads</div><div class="arrow_pip"></div>
                            <div class="block step1" id="item_02">QC
                                <div class="qclist"><ul><li>Trim low quality (Trimmomatic)</li>
                                                            <li>Length filtering (Biopython)</li>
                                                            <li>Duplicate Removal (UCLUST & Prefix)</li>
                                                            <li>Filtering low complexity region (RepeatMasker)</li></ul></div>
                            </div><div class="arrow_pip"></div>
                            <div class="block-lb" id="item_03">Processed reads</div><div class="arrow_pip"></div>
                            <div class="block step2" id="item_04">rRNASelector</div>
                        </div>



                        <div class="branch">
                            <div class="branch1">
                                <div class="arrow_pip rotate_f"></div><div class="block-lb" id="item_05">Reads without rRNA</div><div class="arrow_pip"></div>
                                <div class="block step3 function" id="item_06">FragGeneScan</div><div class="arrow_pip"></div>
                                <div class="block-lb" id="item_07">Predicted CDS</div><div class="arrow_pip"></div>
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
        <!-- Pipeline chart for version 1.0 - complex version-->
        <%--<div class="block_wrapper">--%>

            <%--<div class="block_container">--%>
                <%--<div class="mainbranch" style="margin-top: 82px;">--%>
                      <%--<div class="block-lb" id="item_03">Raw reads</div>--%>
                 <%--</div>--%>

                <%--<div class="first_branch">--%>
                    <%--<div class="branch1">--%>
                        <%--<div class="arrow_pip rotate_f" style="margin-left:2px;margin-right:2px;"></div>--%>
                        <%--<div class="block-lb small" id="item_00">Paired-end reads</div><div class="arrow_pip "></div>--%>
                        <%--<div class="block small step0" id="item_000">SeqPrep</div><div class="arrow_pip "></div>--%>
                        <%--<div class="block-lb small" id="item_00">Initial reads</div>--%>
                        <%--<div class="arrow_pip rotate_t" style="float: right;margin-top: 34px; margin-left: 1px"></div></div>--%>
                    <%--<div class="branch2"><div class="arrow_pip rotate_t" style="margin-left:2px;margin-right:5px;"></div><div class="block-lb small" id="item_00">Simple reads</div><div class="arrow_notip" ></div><div class="arrow_pip rotate_f" style="margin-top: 10px;float: right;"></div></div>--%>
                <%--</div>--%>

                <%--<div class="mainbranch">--%>

                    <%--&lt;%&ndash;<div class="block-lb" id="item_01">Raw reads</div><div class="arrow_pip"></div>&ndash;%&gt;--%>
                    <%--<div class="block step1" id="item_02">QC</div><div class="arrow_pip"></div>--%>
                    <%--<div class="block-lb" id="item_03">Processed reads</div><div class="arrow_pip"></div>--%>
                    <%--<div class="block step2" id="item_04">rRNASelector</div>--%>
                <%--</div>--%>

                <%--<div class="qclist"><ul><li>Trim low quality (Trimmomatic)</li>--%>
                    <%--<li>Length filtering (Biopython)</li>--%>
                    <%--<li>Duplicate Removal (UCLUST & Prefix)</li>--%>
                    <%--<li>Filtering low complexity region (RepeatMasker)</li></ul></div>--%>

                <%--<div class="branch">--%>
                    <%--<div class="branch1">--%>
                        <%--<div class="arrow_pip rotate_f"></div><div class="block-lb" id="item_05">Reads without rRNA</div><div class="arrow_pip"></div>--%>
                        <%--<div class="block step3 function" id="item_06">FragGeneScan</div><div class="arrow_pip"></div>--%>
                        <%--<div class="block-lb" id="item_07">Predicted CDS</div><div class="arrow_pip"></div>--%>
                        <%--<div class="block step4 function" id="item_08">InterProScan</div>--%>
                        <%--<div class="block-nt">Functional analysis</div>--%>
                    <%--</div> <!-- /branch1 -->--%>

                    <%--<div class="branch2">  <div class="arrow_pip rotate_t"></div>--%>
                        <%--<div class="block-lb" id="item_09">Reads with rRNA</div>--%>
                        <%--<div class="arrow_pip"></div>--%>
                        <%--<div class="block-lb" id="item_11">16s rRNA</div>--%>
                        <%--<div class="arrow_pip"></div>--%>
                        <%--<div class="block step5 taxon" id="item_10">QIIME</div>--%>
                        <%--<div class="block-nt">Taxonomic analysis</div>--%>
                    <%--</div><!-- /branch2 -->--%>

                <%--</div><!-- /branch -->--%>
            <%--</div> <!-- /container -->--%>
        <%--</div>   <!-- /block_wrapper -->--%>
        <!-- /complex pipeline chart version 1.0-->
    </c:otherwise>

</c:choose>

<h3>Pipeline tools & steps</h3>

<c:choose>
    <c:when test="${releaseVersion == '2.0'}">
<!-- Table version 2.0 -->
<table class="pipeline_table">
    <thead>
    <tr>
        <th width="4px"></th>
        <th>Tools</th>
        <th >Version</th>
        <th>Description</th>
        <th>How we use it</th>
    </tr>
    </thead>
    <tbody>
    <tr class="step0 row-cb"><td>1</td><td><a href="https://github.com/jstjohn/SeqPrep" class="ext">SeqPrep</a></td><td>1.1</td><td>A program to merge paired end Illumina reads that are overlapping into a single longer read.</td><td>Paired-end overlapping reads are merged - we do not perform assembly.
    </td></tr>
    <!-- Change table row class for each tool -->
    <c:forEach var="pipelineReleaseTool" items="${pipelineReleaseTools}" varStatus="status">
        <c:set var="pipelineTool" value="${pipelineReleaseTool.pk.pipelineTool}"/>
        <c:set var="tool" value="${pipelineTool.toolName}"/>
        <c:set var="toolGroupMajorId" value="${pipelineReleaseTool.toolGroupMajorId}"/>
        <c:choose>
            <c:when test="${tool == 'InterProScan' || tool == 'FragGeneScan'}">
                <tr class="step${toolGroupMajorId} row-function">
            </c:when>
            <c:when test="${tool == 'Trimmomatic' || tool == 'Biopython' || tool == 'UCLUST' || tool == 'RepeatMasker' || tool == 'rRNASelector'}">
                <tr class="step${toolGroupMajorId} row-cb">
            </c:when>
            <c:when test="${tool == 'QIIME' }">
                <tr class="step${toolGroupMajorId} row-taxon">
            </c:when>

            <c:otherwise>
                <tr class="step row-cb">
                <script>
                    //alert("tool not defined");
                </script>
            </c:otherwise>
        </c:choose>
        <td>
            <!-- Showing a step number, add one as the first step is not a tool in the database -->
            ${pipelineReleaseTool.toolGroupId + 1.0}
        </td>
        <td ><a class="ext" href="${pipelineTool.webLink}">${pipelineTool.toolName}</a></td>
        <td>${pipelineTool.toolVersion}</td>
        <td>${pipelineTool.description}</td>
        <td>${pipelineReleaseTool.howToolUsedDesc}</td>
        </tr>

    </c:forEach>
    </tbody>
</table>

    </c:when>
        <c:otherwise>
            <!-- Table version 1.0 -->
            <table class="pipeline_table">
                <thead>
                <tr>
                    <th width="4px"></th>
                    <th>Tools</th>
                    <th >Version</th>
                    <th>Description</th>
                    <th>How we use it</th>
                </tr>
                </thead>
                <tbody>
                <tr class="step0 row-cb"><td>1</td><td><a href="https://github.com/jstjohn/SeqPrep" class="ext">SeqPrep</a></td><td>1.1</td><td>A program to merge paired end Illumina reads that are overlapping into a single longer read.</td><td>Paired-end overlapping reads are merged - we do not perform assembly.
                </td></tr>
                <!-- Change table row class for each tool -->
                <c:forEach var="pipelineReleaseTool" items="${pipelineReleaseTools}" varStatus="status">
                    <c:set var="pipelineTool" value="${pipelineReleaseTool.pk.pipelineTool}"/>
                    <c:set var="tool" value="${pipelineTool.toolName}"/>
                    <c:set var="toolGroupMajorId" value="${pipelineReleaseTool.toolGroupMajorId}"/>
                    <c:choose>
                        <c:when test="${tool == 'InterProScan' || tool == 'FragGeneScan'}">
                            <tr class="step${toolGroupMajorId} row-function">
                        </c:when>
                        <c:when test="${tool == 'Trimmomatic' || tool == 'Biopython' || tool == 'UCLUST' || tool == 'RepeatMasker' || tool == 'rRNASelector'}">
                            <tr class="step${toolGroupMajorId} row-cb">
                        </c:when>
                        <c:when test="${tool == 'QIIME' }">
                            <tr class="step${toolGroupMajorId} row-taxon">
                        </c:when>

                        <c:otherwise>
                            <tr class="step row-cb">
                            <script>
                                //alert("tool not defined");
                            </script>
                        </c:otherwise>
                    </c:choose>
                    <td>
                        <!-- Showing a step number, add one as the first step is not a tool in the database -->
                        ${pipelineReleaseTool.toolGroupId + 1.0}
                    </td>
                    <td ><a class="ext" href="${pipelineTool.webLink}">${pipelineTool.toolName}</a></td>
                    <td>${pipelineTool.toolVersion}</td>
                    <td>${pipelineTool.description}</td>
                    <td>${pipelineReleaseTool.howToolUsedDesc}</td>
                    </tr>

                </c:forEach>
                </tbody>
            </table>
            <!-- /Table version 1.0 -->
        </c:otherwise>
    </c:choose>



<script type="text/javascript">
    $(document).ready(function() {

        //add class "highlight" when hover over the row
        $('table tbody tr').hover(function() {
            $(this).addClass('highlight');
        }, function() {
            $(this).removeClass('highlight');
        });

          //for step0
        $('.block.step0').hover(function() {
            $('.block.step0, table tbody tr.step0').addClass('highlight');

        }, function() {
            $('.block.step0, table tbody tr.step0').removeClass('highlight');
        });

        $('table tbody tr.step0').hover(function() {
            $('.step0').addClass('highlight');
        }, function() {
            $('.step0').removeClass('highlight');
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