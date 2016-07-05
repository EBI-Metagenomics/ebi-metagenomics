<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div id="fragment-download">

<c:choose>
<c:when test="${not empty model.sample.analysisCompleted}">
<div class="box-export">
<p>You can download in this section the full set of analysis results files and the original raw sequence
    reads. Multi-part files should be concatenated after unzipping.</p>
<section id="download_sq_analysis">
    <h4>Sequence data</h4>
    <table class="pipeline_table">
        <thead>
        <tr>
            <th>Name</th>
            <th class="xs_hide">Data type</th>
            <th class="xs_hide">Compression <span class="icon icon-generic show_tooltip" data-icon="i"
                                  title="File compressed using GZIP -GZ, or GNU Zipped Archive file is a compression utility. GZ was adopted by the GNU Project, and is relatively popular on the Internet. GZIP produces files with a GZ extension, which can be decompressed by GZIP/GNUZIP program."></span>
            </th>
            <th>Format</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
            <%--only for ENA --%>
        <c:forEach var="downloadLink" items="${model.downloadSection.sequencesDownloadSection.otherDownloadLinks}"
                   varStatus="loop">
            <c:choose>
                <c:when test="${downloadLink.externalLink}"><%-- external link --%>
                    <tr class="row-cb">

                        <td>${downloadLink.linkText}</td>
                        <td class="col_xs xs_hide"><span class="dna_rna icon icon-conceptual icon-c1" data-icon="d"></span></td>
                        <td class="col_sm  xs_hide"></td>
                        <td class="col_sm"></td>
                        <td><a href="${downloadLink.linkURL}" title="${downloadLink.linkTitle}"><span
                                class="link icon icon-generic" data-icon="L"></a> <span class="filesize">
                    (ENA website)</span></td>
                    </tr>
                </c:when>

                <c:when test="${!downloadLink.externalLink}"><%-- CASE 3 - multiple "single link/no partition" NON ZIP - non ENA  links--%>

                    <c:choose>
                        <c:when test="${downloadLink.linkText == 'Processed nucleotide reads'}">
                            <tr class="row-cb">
                        </c:when>
                        <c:otherwise>
                            <tr class="row-function">
                        </c:otherwise>
                    </c:choose>
                    <td>${downloadLink.linkText}</td>
                    <td class="col_xs xs_hide">
                            <%--while implemented in file-definition-context--%>
                        <c:choose>
                            <c:when test="${fn:startsWith(downloadLink.linkPrefix, 'Predicted CDS')}">
                                <span class="show_tooltip protein icon icon-conceptual icon-c3" data-icon="P"
                                      title="Protein (AA) data type"></span>
                            </c:when>
                            <c:otherwise>
                                <span class="show_tooltip dna_rna icon icon-conceptual icon-c1" data-icon="d"
                                      title="DNA or RNA data type"></span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="col_sm  xs_hide">-</td>
                    <td class="col_sm">FASTA</td>
                    <td><a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>" title="${downloadLink.linkTitle}"><span
                            class="icon  icon-functional" data-icon="="></span></a> <span
                            class="filesize"> (${downloadLink.fileSize})</span></td>
                    </tr>
                </c:when>
                <c:otherwise>
                </c:otherwise>
            </c:choose>
        </c:forEach><%--/only for ENA --%>

        <c:forEach var="downloadLinkList"
                   items="${model.downloadSection.sequencesDownloadSection.listOfChunkedDownloadLinks}"
                   varStatus="loop">
            <c:choose>
                <c:when test="${fn:length(downloadLinkList)>1}"><%--for Multiple partitions --%>
                    <c:choose>
                        <c:when test="${downloadLinkList[0].linkPrefix == 'Processed nucleotide reads'}">
                            <tr class="row-cb">
                        </c:when>
                        <c:otherwise>
                            <tr class="row-function">
                        </c:otherwise>
                    </c:choose>
                    <td><c:out value="${downloadLinkList[0].linkPrefix}"/></td>

                    <td class="col_xs xs_hide">
                            <%--while implemented in file-definition-context--%>
                        <c:choose>
                            <c:when test="${downloadLinkList[0].linkPrefix == 'InterPro matches' || downloadLinkList[0].linkPrefix ==  'InterPro matches (TSV)'}">
                                <span class="show_tooltip icon  icon-generic" data-icon="g"
                                      title="Chart data type"></span>
                            </c:when>

                            <c:when test="${fn:startsWith(downloadLinkList[0].linkPrefix, 'Predicted CDS')}">
                                <span class="show_tooltip protein icon icon-conceptual icon-c3" data-icon="P"
                                      title="Protein (AA) data type"></span>
                            </c:when>
                            <c:otherwise>
                                <span class="show_tooltip dna_rna icon icon-conceptual icon-c1" data-icon="d"
                                      title="DNA or RNA data type"></span>
                            </c:otherwise>
                        </c:choose>

                    </td>
                    <td class="col_sm  xs_hide">GZIP</td>
                    <td class="col_sm"><c:out value="${linkPrefix}"/> FASTA, ${downloadLinkList[0].numberOfChunks}</td>
                    <td class="no-dash-last">
                        <c:forEach var="downloadLink" items="${downloadLinkList}" varStatus="loop">
                            <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                               title="${downloadLink.linkTitle}">${downloadLink.linkText}</a> <span
                                class="filesize"> (${downloadLink.fileSize})</span> <span>-</span>
                        </c:forEach>

                    </td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <%--CASE2 multiple "single link/no partition" ZIPPED --%>
                    <c:forEach var="downloadLink" items="${downloadLinkList}" varStatus="loop">
                        <c:choose>
                            <c:when test="${downloadLinkList[0].linkPrefix == 'Processed nucleotide reads'}">
                                <tr class="row-cb">
                            </c:when>
                            <c:otherwise>
                                <tr class="row-function">
                            </c:otherwise>
                        </c:choose>
                        <td><c:out value="${downloadLinkList[0].linkPrefix}"/></td>
                        <td class="col_xs  xs_hide">
                                <%--while implemented in file-definition-context--%>
                            <c:choose>
                                <c:when test="${fn:startsWith(downloadLinkList[0].linkPrefix, 'Predicted CDS')}">
                                    <span class="show_tooltip protein icon icon-conceptual icon-c3" data-icon="P"
                                          title="Protein (AA) data type"></span>
                                </c:when>
                                <c:otherwise>
                                    <span class="show_tooltip dna_rna icon icon-conceptual icon-c1" data-icon="d"
                                          title="DNA or RNA data type"></span>
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td class="col_sm  xs_hide">GZIP</td>
                        <td class="col_sm">FASTA</td>
                        <td>

                            <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                               title="${downloadLink.linkTitle}"><span class="icon  icon-functional"
                                                                       data-icon="="></span></a> <span class="filesize"> (${downloadLink.fileSize})</span>

                        </td>
                        </tr> </c:forEach>
                </c:otherwise>
            </c:choose>
        </c:forEach>


        </tbody>
    </table>
</section>

<section id="download_fun_analysis">

    <c:if test="${(not empty model.downloadSection.functionalDownloadSection.interproscanDownloadLinks || not empty model.downloadSection.functionalDownloadSection.otherDownloadLinks)}">
        <h4>Functional analysis</h4>

        <table class="pipeline_table">
            <thead>
            <tr>
                <th>Name</th>
                <th class="xs_hide">Data type</th>
                <th class="xs_hide">Compression <span class="icon icon-generic show_tooltip" data-icon="i"
                                      title="File compressed using GZIP -GZ, or GNU Zipped Archive file is a compression utility. GZ was adopted by the GNU Project, and is relatively popular on the Internet. GZIP produces files with a GZ extension, which can be decompressed by GZIP/GNUZIP program."></span>
                </th>
                <th>Format</th>
                <th></th>

            </tr>
            </thead>
            <tbody>


                <%-- InterPro matches link--%>
            <c:if test="${not empty model.downloadSection.functionalDownloadSection.interproscanDownloadLinks}">
                <c:choose>

                    <c:when test="${fn:length(model.downloadSection.functionalDownloadSection.interproscanDownloadLinks)>1}">
                        <%--multiple links/partition - zipped  - e.g. //SRP022254/samples/SRS429585/runs/SRR873465/ --%>
                        <tr class="row-function">
                            <td><c:out
                                    value="${model.downloadSection.functionalDownloadSection.interproscanDownloadLinks[0].linkPrefix}"/></td>

                            <td class="col_xs xs_hide">
                                    <%--while implemented in file-definition-context--%>
                                <c:choose>
                                    <c:when test="${model.downloadSection.functionalDownloadSection.interproscanDownloadLinks[0].linkPrefix == 'InterPro matches' || model.downloadSection.functionalDownloadSection.interproscanDownloadLinks[0].linkPrefix ==  'Complete GO annotation' || model.downloadSection.functionalDownloadSection.interproscanDownloadLinks[0].linkPrefix ==  'GO slim annotation' }">
                                        <span class="show_tooltip icon  icon-generic big" data-icon="g"
                                              title="Chart data type"></span>
                                    </c:when>

                                    <c:when test="${model.downloadSection.functionalDownloadSection.interproscanDownloadLinks[0].linkPrefix == 'Predicted CDS' || model.downloadSection.functionalDownloadSection.interproscanDownloadLinks[0].linkPrefix ==  'Predicted CDS without annotation'}">
                                        <span class="show_tooltip protein icon icon-conceptual icon-c3" data-icon="P"
                                              title="Protein (AA) data type"></span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="show_tooltip dna_rna icon icon-conceptual icon-c1" data-icon="d"
                                              title="DNA or RNA data type"></span>
                                    </c:otherwise>
                                </c:choose>

                            </td>
                            <td class="col_sm xs_hide">
                                    <%--while implemented in file-definition-context--%>
                                <c:choose>
                                    <c:when test="${model.downloadSection.functionalDownloadSection.interproscanDownloadLinks[0].linkPrefix == 'InterPro matches'}">
                                        GZIP
                                    </c:when>
                                </c:choose>
                            </td>
                            <td class="col_sm">
                                    <%--while implemented in file-definition-context--%>
                                <c:choose>
                                    <c:when test="${model.downloadSection.functionalDownloadSection.interproscanDownloadLinks[0].linkPrefix == 'InterPro matches'}">
                                        TSV,${model.downloadSection.functionalDownloadSection.interproscanDownloadLinks[0].numberOfChunks }
                                    </c:when>
                                    <c:otherwise>
                                        CSV
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="no-dash-last">
                                <c:forEach var="downloadLink"
                                           items="${model.downloadSection.functionalDownloadSection.interproscanDownloadLinks}"
                                           varStatus="loop">
                                    <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                                       title="${downloadLink.linkTitle}">${downloadLink.linkText}</a> <span
                                        class="filesize"> (${downloadLink.fileSize})</span> <span>-</span>
                                </c:forEach>

                            </td>
                        </tr>

                    </c:when>
                    <c:otherwise>
                        <%--only one link but quite big so it has been zipped  - e.g. /SRP001803/samples/SRS010106/runs/SRR035084/ --%>
                        <c:forEach var="downloadLink"
                                   items="${model.downloadSection.functionalDownloadSection.interproscanDownloadLinks}"
                                   varStatus="loop">

                            <tr class="row-function">
                                <td><c:out
                                        value="${model.downloadSection.functionalDownloadSection.interproscanDownloadLinks[0].linkPrefix}"/></td>

                                <td class="col_xs xs_hide">
                                        <%--while implemented in file-definition-context--%>
                                    <c:choose>
                                        <c:when test="${model.downloadSection.functionalDownloadSection.interproscanDownloadLinks[0].linkPrefix == 'InterPro matches' || model.downloadSection.functionalDownloadSection.interproscanDownloadLinks[0].linkPrefix ==  'Complete GO annotation' || model.downloadSection.functionalDownloadSection.interproscanDownloadLinks[0].linkPrefix ==  'GO slim annotation' }">
                                            <span class="show_tooltip icon  icon-generic big" data-icon="g"
                                                  title="Chart data type"></span>
                                        </c:when>

                                        <c:when test="${model.downloadSection.functionalDownloadSection.interproscanDownloadLinks[0].linkPrefix == 'Predicted CDS' || model.downloadSection.functionalDownloadSection.interproscanDownloadLinks[0].linkPrefix ==  'Predicted CDS without annotation'}">
                                            <span class="show_tooltip protein icon icon-conceptual icon-c3"
                                                  data-icon="P" title="Protein (AA) data type"></span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="show_tooltip dna_rna icon icon-conceptual icon-c1"
                                                  data-icon="d" title="DNA or RNA data type"></span>
                                        </c:otherwise>
                                    </c:choose>

                                </td>
                                <td class="col_sm xs_hide">
                                        <%--while implemented in file-definition-context--%>
                                    GZIP
                                </td>
                                <td class="col_sm">
                                        <%--while implemented in file-definition-context--%>
                                    TSV
                                </td>
                                <td>
                                    <c:forEach var="downloadLink"
                                               items="${model.downloadSection.functionalDownloadSection.interproscanDownloadLinks}"
                                               varStatus="loop">
                                        <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                                           title="${downloadLink.linkTitle}"> <span class="icon icon-functional"
                                                                                    data-icon="="></span></a> <span
                                            class="filesize"> (${downloadLink.fileSize})</span>
                                    </c:forEach>

                                </td>
                            </tr>

                        </c:forEach>

                    </c:otherwise>
                </c:choose>
            </c:if>

                <%--Go related  link--%>

            <c:forEach var="downloadLink" items="${model.downloadSection.functionalDownloadSection.otherDownloadLinks}"
                       varStatus="loop">

                <tr class="row-function">
                    <td>${downloadLink.linkText}</td>

                    <td class="col_xs xs_hide">
                            <%--while implemented in file-definition-context--%>
                        <span class="show_tooltip icon  icon-generic big" data-icon="g" title="Chart data type"></span>
                    </td>
                    <td class="col_sm xs_hide">
                            <%--while implemented in file-definition-context--%>
                        <c:choose>
                            <c:when test="${downloadLink.linkText == 'InterPro matches'}">
                                GZIP
                            </c:when>
                            <c:otherwise>-</c:otherwise>
                        </c:choose>
                    </td>
                    <td class="col_sm">
                            <%--while implemented in file-definition-context--%>
                        <c:choose>
                            <c:when test="${downloadLink.linkText == 'InterPro matches'}">
                                TSV,${model.downloadSection.functionalDownloadSection.interproscanDownloadLinks[0].numberOfChunks}
                            </c:when>
                            <c:otherwise>
                                CSV
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>

                        <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>" title="${downloadLink.linkTitle}"><span
                                class="icon  icon-functional" data-icon="="></span></a> <span
                            class="filesize"> (${downloadLink.fileSize})</span>


                    </td>
                </tr>

            </c:forEach>


            </tbody>
        </table>

    </c:if>
</section>

<section id="download_tax_analysis">
    <c:if test="${not empty model.downloadSection.taxonomyDownloadSection.downloadLinks}">
        <h4>Taxonomic analysis</h4>

        <table class="pipeline_table">
            <thead>
            <tr>
                <th>Name</th>
                <th class="xs_hide">Data type</th>
                <th class="xs_hide">Compression <span class="icon icon-generic show_tooltip" data-icon="i"
                                      title="File compressed using GZIP -GZ, or GNU Zipped Archive file is a compression utility. GZ was adopted by the GNU Project, and is relatively popular on the Internet. GZIP produces files with a GZ extension, which can be decompressed by GZIP/GNUZIP program."></span>
                </th>
                <th>Format</th>
                <th></th>

            </tr>
            </thead>
            <tbody>


            <c:forEach var="downloadLink" items="${model.downloadSection.taxonomyDownloadSection.downloadLinks}" varStatus="loop">

                <tr class="row-taxon">

                    <td> ${downloadLink.linkText}</td>
                    <td class="col_xs xs_hide">
                            <%--while implemented in file-definition-context--%>
                        <c:choose>
                            <c:when test="${downloadLink.linkPrefix == 'InterPro matches' || downloadLink.linkText ==  'Phylogenetic tree' || downloadLink.linkText == 'OTUs, reads and taxonomic assignments'}">
                                <span class="show_tooltip icon  icon-generic big" data-icon="g"
                                      title="Chart data type"></span>
                            </c:when>

                            <c:when test="${downloadLink.linkPrefix == 'Predicted CDS' || downloadLink.linkPrefix ==  'Predicted CDS without annotation'}">
                                <span class="show_tooltip protein icon icon-conceptual icon-c3" data-icon="P"
                                      title="Protein (AA)data type"></span>
                            </c:when>
                            <c:otherwise>
                                <span class="show_tooltip dna_rna icon icon-conceptual icon-c1" data-icon="d"
                                      title="DNA or RNA  data type"></span>
                            </c:otherwise>
                        </c:choose>

                    </td>
                    <td class="col_sm xs_hide">-</td>
                    <td class="col_sm">
                            <%--file format--%>
                        <c:choose>
                            <c:when test="${downloadLink.linkTitle == 'Click to download the OTU table in TSV format'}">
                                TSV
                            </c:when>
                            <c:when test="${downloadLink.linkTitle == 'Click to download the converted OTU table in biom format'}">
                                Biom <span class="icon icon-generic show_tooltip" data-icon="i"
                                           title="BIOM is a standard format (http://biom-format.org/) for representing taxonomy distribution (OTUs and their occurence in each sample). BIOM files could be directly imported in diverse analysis packages such as MEGAN (http://ab.inf.uni-tuebingen.de/software/megan5/)."></span>
                            </c:when>
                            <c:when test="${downloadLink.linkTitle == 'Click to download the converted OTU table in HDF5 biom format'}">
                                HDF5 Biom <span class="icon icon-generic show_tooltip" data-icon="i"
                                                title="BIOM is a standard format (http://biom-format.org/) for representing taxonomy distribution (OTUs and their occurence in each sample). BIOM files could be directly imported in diverse analysis packages such as MEGAN (http://ab.inf.uni-tuebingen.de/software/megan5/)."></span>
                            </c:when>
                            <c:when test="${downloadLink.linkTitle == 'Click to download the converted OTU table in JSON biom format'}">
                                JSON Biom <span class="icon icon-generic show_tooltip" data-icon="i"
                                                title="BIOM is a standard format (http://biom-format.org/) for representing taxonomy distribution (OTUs and their occurence in each sample). BIOM files could be directly imported in diverse analysis packages such as MEGAN (http://ab.inf.uni-tuebingen.de/software/megan5/)."></span>
                            </c:when>
                            <c:when test="${downloadLink.linkText == 'Phylogenetic tree'}">
                                Newick format <span class="icon icon-generic show_tooltip" data-icon="i"
                                                    title="The Newick format is a computer-readable phylogenetic tree representation (http://evolution.genetics.washington.edu/phylip/newicktree.html). The trees can be visualized using graphical viewers such as FigTree (http://tree.bio.ed.ac.uk/software/figtree/) and iTOL (http://itol.embl.de/)."></span>
                            </c:when>
                            <c:otherwise>
                                FASTA
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>

                        <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>" title="${downloadLink.linkTitle}"><span
                                class="icon  icon-functional" data-icon="="></span></a> <span
                            class="filesize"> (${downloadLink.fileSize})</span>
                    </td>
                        <%--<span class="list_date"> - ${downloadLink.fileSize}</span>--%>
                </tr>

            </c:forEach>

            </tbody>
        </table>
    </c:if>
</section>
</div>
</c:when>
<c:otherwise>
    Analysis in progress...
</c:otherwise>
</c:choose>
</div>
<script>
    $(document).ready(function () {
        //temp script to remove the last dash item in the list when partition
        $(".no-dash-last span:last-child").css("display", "none");

        //Force the tooltip to be shown
        $('.show_tooltip').qtip({
            position:{
                my:'top center', // Position my top left...
                at:'bottom center' // at the bottom right of...

            }});

    });
</script>
