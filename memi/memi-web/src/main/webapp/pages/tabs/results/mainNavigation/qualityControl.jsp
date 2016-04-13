<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="fragment-quality">
    <%--BEGIN READS SECTION   --%>

    <%--<h3>Submitted nucleotide data</h3>--%>
    <c:choose>
        <c:when test="${empty model.sample.analysisCompleted}"><div class="msg_error">Analysis in progress.</div></c:when>
        <c:when test="${not empty model.sample.analysisCompleted && !model.analysisStatus.qualityControlTabDisabled}">

            <p>The chart below shows the number of reads which pass the quality control steps we have implemented in our pipeline. Sequence merging may have occurred with paired-end data, so the initial number of reads may differ from the number given by ENA.</p>
            <div id="qc_overview"></div>

            <p>The histograms below show the distribution of sequence lengths in basepairs (left) and GC percentage (right) for the sequences having passed our quality control steps. For large files, the distributions were derived from a randomly selected subset of 2 millions sequence reads. The standard deviations are shown on each plot. The bar chart underneath each graph indicates the minimum, average and maximum length, and average GC and AT content, respectively.</p>
            <div style="float:left;width:50%">
                <div id="seq_len"></div>
                <div id="seq_stats"></div>
            </div>
            <div style="float:left;width:50%">
                <div id="seq_gc"></div>
                <div id="seq_gc_stats"></div>
            </div>
            <p>The graph below show the relative abundance of nucletotides (A, C, G, T, or ambiguous base "N") at each position starting from the beginning of each read up to the first 500 base pairs.</p>
            <div id="nucleotide"></div>


            <script>
                $(function () {
                    var common_path = "/metagenomics/projects/${projectId}/samples/${sampleId}/runs/${runId}/results/versions/${versionId}/qc-stats/",
                        file_summary = common_path + 'summary',
                        file_length  = common_path + 'length',
                        file_stats   = common_path + 'stats',
                        file_gc      = common_path + 'gc_bin',
                        file_base    = common_path + 'base',
                        numberOfLines = ${(versionId=="2.0"?4:6)};

                    var stats_data =null;
                    $.ajax(file_stats).done(function (rawdata) {
                        var data = {};
                        rawdata.split('\n').forEach(function(line){
                            var line = line.split("\t");
                            data[line[0]] = line[1]*1
                        });
                        drawSequncesLength(data);
                        drawGCContent(data);
                        stats_data = data;

                    }).always(function(){
                        $.ajax(file_summary).done(function(d){
                            drawNumberOfReadsChart(d,numberOfLines,stats_data==null?null:stats_data["sequence_count"]);
                        });
                        $.ajax(file_length)
                                .done(function(data){
                                    drawSequenceLengthHistogram(data,false,stats_data);
                                })
                                .fail(function(){
                                    $.ajax(file_length+".sub-set")
                                            .done(function(data){
                                                drawSequenceLengthHistogram(data,true,stats_data);
                                            });
                                });
                        $.ajax(file_gc)
                                .done(function(data){
                                    drawSequenceGCDistribution(data,false,stats_data);
                                })
                                .fail(function(){
                                    $.ajax(file_gc+".sub-set")
                                            .done(function(data){
                                                drawSequenceGCDistribution(data,true,stats_data);
                                            });
                                });
                    });

                    $.ajax(file_base)
                            .done(function(data){
                                drawNucleotidePositionHistogram(data);
                            })
                            .fail(function(){
                                $.ajax(file_base+".sub-set")
                                        .done(function(data){
                                            drawNucleotidePositionHistogram(data,true);
                                        });
                            });

                });

            </script>

        </c:when>
        <c:otherwise>
            <div class="msg_error">No result files have been associated with this sample.</div>
        </c:otherwise>
    </c:choose>

</div>