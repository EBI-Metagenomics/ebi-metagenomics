<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="fragment-quality">
    <%--BEGIN READS SECTION   --%>

    <%--<h3>Submitted nucleotide data</h3>--%>
    <p>The chart below shows the number of sequence reads which pass each of the quality control steps we
        have implemented in our pipeline. Note that, for paired-end data, sequence merging may have
        occurred and so the initial number of reads may differ from what is in the ENA. For more details
        about the data processing we employ, please see the <a href="<c:url value="${baseURL}/about#analysis"/>"  title="About Metagenomics">about page</a>.</p>
    <c:choose>
        <c:when test="${empty model.sample.analysisCompleted}"><div class="msg_error">Analysis in progress.</div></c:when>
        <c:when test="${not empty model.sample.analysisCompleted && !model.analysisStatus.qualityControlTabDisabled}">

            <c:url var="statsImage" value="/getImage" scope="request">
                <c:param name="imageName" value="/charts/qc.png"/>
                <c:param name="imageType" value="PNG"/>
                <c:param name="dir" value="${model.analysisJob.resultDirectory}"/>
            </c:url>
            <p><img src="<c:out value="${statsImage}"/>"/></p>

            <div id="seq_len"></div>
            <div id="seq_stats"></div>

            <div id="seq_gc"></div>
            <div id="seq_gc_stats">
            </div>


            <div id="nucleotide"></div>
            <script>
                $(function () {
                    $.ajax('/metagenomics//projects/${projectId}/samples/${sampleId}/runs/${runId}/results/versions/${versionId}/qc-stats/length').done(function (rawdata) {
                        var data = rawdata.split('\n').map(function(line){
                            return line.split("\t").map(function(v){ return 1*v; });
                        });

                        $('#seq_len').highcharts({
                            chart: { type: 'areaspline' },
                            title: { text: 'Sequence Length Histogram'},
                            series : [
                                { name : 'Sequences',
                                    data : data,
                                    color: "rgb(63, 114, 191)"
                                }
                            ],
                            credits: false
                        });
                    });
                    $.ajax('/metagenomics//projects/${projectId}/samples/${sampleId}/runs/${runId}/results/versions/${versionId}/qc-stats/stats').done(function (rawdata) {
                        var data = {};
                        rawdata.split('\n').forEach(function(line){
                            var line = line.split("\t");
                            data[line[0]] = line[1]*1
                        });

                        $('#seq_stats').highcharts({
                            chart: {
                                type: 'bar',
                                height: 200
                            },
                            title: { text: 'Sequence Length' },
                            xAxis: {
                                categories: ['Sequence'],
                                title: { enabled: false }
                            },
                            yAxis: {
                                min: 0,
                                max: 100*(Math.floor(data["length_max"]/100)+1),
                                title: { text: 'Sequence length (bp)' }
                            },
                            plotOptions: {
                                series: {
                                    grouping: false,
                                    shadow: false,
                                    borderWidth: 0
                                }
                            },
                            series : [
                                { name : 'Max Length',
                                    pointPadding: 0.25,
                                    color: "rgb(114, 63, 191)",
                                    data : [data["length_max"]]
                                },{
                                    name : 'Avg Length',
                                    pointPadding: 0.25,
                                    color: "rgb(63, 114, 191)",
                                    tooltip: {
                                        pointFormat: '<span style="color:{point.color}">\u25CF</span> {series.name}: <b>'+data["average_length"]+'</b><br/>',
                                    },
                                    data : [data["average_length"]]
                                },{
                                    name : 'Min Length',
                                    color: "rgb(114, 191, 63)",
                                    pointPadding: 0.25,
                                    data : [data["length_min"]]
                                },{
                                    name : 'Standard Deviation',
                                    threshold:data["average_length"]-data["standard_deviation_length"],
                                    data : [data["average_length"]+data["standard_deviation_length"]],
                                    tooltip: {
                                        pointFormat: '<span style="color:{point.color}">\u25CF</span> {series.name}: <b>'+data["standard_deviation_length"]+'</b><br/>',
                                    },
                                    color: "rgba(191, 191, 63, 0.5)"
                                }
                            ],
                            credits: false
                        });
                        $('#seq_gc_stats').highcharts({
                            chart: {
                                type: 'bar',
                                height: 200
                            },
                            title: { text: 'GC Content' },
                            xAxis: {
                                categories: ['Content'],
                                title: { enabled: false }
                            },
                            yAxis: {
                                min: 0,
                                max: 100,
                                title: { text: 'GC Content (%)' }
                            },
                            plotOptions: {
                                series: {
                                    grouping: false,
                                    shadow: false,
                                    borderWidth: 0
                                }
                            },
                            series : [
                                { name : 'GC Content',
                                    pointPadding: 0.25,
                                    color: "rgb(63, 114, 191)",
                                    tooltip: {
                                        pointFormat: '<span style="color:{point.color}">\u25CF</span> {series.name}: <b>'+data["average_gc_content"]+'</b><br/><span style="color:{point.color}">\u25CF</span> GC ratio: <b>'+data["average_gc_ratio"]+'</b><br/>',
                                    },
                                    data : [data["average_gc_content"]]
                                },{
                                    name : 'AT Content',
                                    color: "rgb(114, 63, 191)",
                                    pointPadding: 0.25,
                                    threshold:data["average_gc_content"],
                                    tooltip: {
                                        pointFormat: '<span style="color:{point.color}">\u25CF</span> {series.name}: <b>'+(100-data["average_gc_content"])+'</b><br/>',
                                    },
                                    data : [100]
                                },{
                                    name : 'Standard Deviation GC content',
                                    threshold:data["average_gc_content"]-data["standard_deviation_gc_content"],
                                    data : [data["average_gc_content"]+data["standard_deviation_gc_content"]],
                                    tooltip: {
                                        pointFormat: '<span style="color:{point.color}">\u25CF</span> {series.name}: <b>'+data["standard_deviation_gc_content"]+'</b><br/><span style="color:{point.color}">\u25CF</span> Standard Deviation GC ratio: <b>'+data["standard_deviation_gc_ratio"]+'</b><br/>',
                                    },
                                    color: "rgba(191, 191, 63, 0.5)"
                                }
                            ],
                            credits: false
                        });
                    });

                    $.ajax('/metagenomics//projects/${projectId}/samples/${sampleId}/runs/${runId}/results/versions/${versionId}/qc-stats/gc_bin').done(function (rawdata) {
                        var data = rawdata.split('\n').map(function(line){
                            return line.split("\t").map(function(v){ return 1*v; });
                        });
                        // Create the chart
                        $('#seq_gc').highcharts({
                            chart: { type: 'areaspline' },
                            title: { text: 'Sequence GC distribution' },
                            series : [{
                                name : 'Sequences',
                                data : data,
                                color: "rgb(63, 114, 191)"
                            }],
                            credits: false
                        });
                    });
                    $.ajax('/metagenomics//projects/${projectId}/samples/${sampleId}/runs/${runId}/results/versions/${versionId}/qc-stats/base').done(function (rawdata) {
                        var data = {
                            "pos":[],
                            "A":[],
                            "G":[],
                            "T":[],
                            "C":[],
                            "N":[]
                        }
                        var colors= {
                            "A": "rgb(16, 150, 24)",
                            "G": "rgb(255, 153, 0)",
                            "C": "rgb(51, 102, 204)",
                            "T": "rgb(220, 57, 18)",
                            "N": "rgb(138, 65, 23)"
                        };
                        var headers = null;
                        rawdata.split('\n').forEach(function(line){
                            if (headers == null) {
                                headers = line.split("\t");
                            } else {
                                line.split("\t").forEach(function(v,i){
                                    data[headers[i]].push(v*1)
                                });
                            }
                        });
                        // Create the chart
                        $('#nucleotide').highcharts({
                            chart: {
                                type: 'area'
                            },
                            title: {
                                text: 'Nucleotide Histogram'
                            },
                            xAxis: {
                                categories: data["pos"],
                                tickmarkPlacement: 'on',
                                title: {
                                    enabled: false
                                }
                            },
                            yAxis: {
                                max: 100,
                                title: {
                                    enabled: false
                                }
                            },
                            plotOptions: {
                                area: {
                                    stacking: 'normal',
                                    lineColor: '#666666',
                                    lineWidth: 1,
                                    marker: {
                                        lineWidth: 1,
                                        lineColor: '#666666'
                                    }
                                }
                            },
                            tooltip: {
                                shared: true,
                            },
                            series : ["A","T","C","G","N"].map(function(d){
                                return {
                                    name : d,
                                    data : data[d],
                                    color : colors[d]
                                }
                            }),
                            credits: false
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