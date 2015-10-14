<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div id="fragment-download">

    <c:choose>
        <c:when test="${not empty model.sample.analysisCompleted}">
            <div class="box-export">
                <p>You can download in this section the full set of analysis results files and the original raw sequence
                    reads.</p>
                <section id="download_sq_analysis">
                <h4>Sequence data</h4>
                <ul>
                    <c:forEach var="downloadLink"
                               items="${model.downloadSection.sequencesDownloadSection.otherDownloadLinks}"
                               varStatus="loop">
                        <c:choose>
                            <c:when test="${downloadLink.externalLink}">
                                <li>
                                    <a href="${downloadLink.linkURL}"
                                       title="${downloadLink.linkTitle}">${downloadLink.linkText}</a>
                                </li>
                            </c:when>
                            <c:when test="${!downloadLink.externalLink}">
                                <li>
                                    <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                                       title="${downloadLink.linkTitle}">${downloadLink.linkText}</a>
                                    <span class="list_date"> - ${downloadLink.fileSize}</span>
                                </li>
                            </c:when>
                            <c:otherwise>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:forEach var="downloadLinkList"
                               items="${model.downloadSection.sequencesDownloadSection.listOfChunkedDownloadLinks}"
                               varStatus="loop">
                        <c:choose>
                            <c:when test="${fn:length(downloadLinkList)>1}">
                                <li class="no-dash-last"><c:out value="${downloadLinkList[0].linkPrefix}"/> <span
                                        class="icon icon-generic show_tooltip"
                                        data-icon="i"
                                        title="File compressed using GZIP -GZ, or GNU Zipped Archive file is a compression utility. GZ was adopted by the GNU Project, and is relatively popular on the Internet. GZIP produces files with a GZ extension, which can be decompressed by GZIP/GNUZIP program."></span>:
                                    <c:forEach var="downloadLink"
                                               items="${downloadLinkList}"
                                               varStatus="loop">
                                        <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                                           title="${downloadLink.linkTitle}">${downloadLink.linkText}
                                        </a><span class="list_date_new"> (${downloadLink.fileSize})</span>
                                        <span>-</span>
                                    </c:forEach>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <%--only one link but quite big so it has been zipped --%>
                                <c:forEach var="downloadLink"
                                           items="${downloadLinkList}"
                                           varStatus="loop">
                                    <li>
                                        <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                                           title="${downloadLink.linkTitle}">${downloadLink.linkText}
                                        </a><span class="icon icon-generic show_tooltip" data-icon="i"
                                                  title="File compressed using GZIP - GZ, or GNU Zipped Archive file is a compression utility. GZ was adopted by the GNU Project, and is relatively popular on the Internet. GZIP produces files with a GZ extension, which can be decompressed by GZIP/GNUZIP program."></span>
                                        <span class="list_date"> - ${downloadLink.fileSize}</span>
                                    </li>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </ul>
                </section>
                <section id="download_fun_analysis">
                <c:if test="${(not empty model.downloadSection.functionalDownloadSection.interproscanDownloadLinks || not empty model.downloadSection.functionalDownloadSection.otherDownloadLinks)}">
                    <h4>Functional analysis</h4>
                    <ul>
                        <c:if test="${not empty model.downloadSection.functionalDownloadSection.interproscanDownloadLinks}">
                            <c:choose>
                                <c:when test="${fn:length(model.downloadSection.functionalDownloadSection.interproscanDownloadLinks)>1}">
                                    <li class="no-dash-last"><c:out value="${model.downloadSection.functionalDownloadSection.interproscanDownloadLinks[0].linkPrefix}"/><span
                                            class="icon icon-generic show_tooltip"
                                            data-icon="i"
                                            title="TSV file compressed using GZIP -GZ, or GNU Zipped Archive file is a compression utility. GZ was adopted by the GNU Project, and is relatively popular on the Internet. GZIP produces files with a GZ extension, which can be decompressed by GZIP/GNUZIP program."></span>:
                                        <c:forEach var="downloadLink"
                                                   items="${model.downloadSection.functionalDownloadSection.interproscanDownloadLinks}"
                                                   varStatus="loop">

                                            <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                                               title="${downloadLink.linkTitle}">${downloadLink.linkText}
                                            </a><span class="list_date_new"> (${downloadLink.fileSize})</span>
                                            <span>-</span>
                                        </c:forEach>
                                    </li>

                                </c:when>
                                <c:otherwise>
                                    <%--only one link but quite big so it has been zipped --%>
                                    <c:forEach var="downloadLink"
                                               items="${model.downloadSection.functionalDownloadSection.interproscanDownloadLinks}"
                                               varStatus="loop">
                                        <li>
                                            <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                                               title="${downloadLink.linkTitle}">InterPro matches (TSV)</a> <span
                                                class="icon icon-generic show_tooltip" data-icon="i"
                                                title="TSV file compressed using GZIP - GZ, or GNU Zipped Archive file is a compression utility. GZ was adopted by the GNU Project, and is relatively popular on the Internet. GZIP produces files with a GZ extension, which can be decompressed by GZIP/GNUZIP program."></span>
                                            <span class="list_date_new"> - ${downloadLink.fileSize}</span>
                                        </li>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                        <c:forEach var="downloadLink"
                                   items="${model.downloadSection.functionalDownloadSection.otherDownloadLinks}"
                                   varStatus="loop">
                            <li>
                                <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                                   title="${downloadLink.linkTitle}">${downloadLink.linkText}</a> <span
                                    class="list_date"> - ${downloadLink.fileSize}</span>
                            </li>
                        </c:forEach>
                    </ul>
                </c:if>
                </section>

                <section id="download_tax_analysis">
                <c:if test="${not empty model.downloadSection.taxaAnalysisDownloadLinks}">
                    <h4>Taxonomic analysis</h4>
                    <ul>
                        <c:forEach var="downloadLink" items="${model.downloadSection.taxaAnalysisDownloadLinks}"
                                   varStatus="loop">


                            <li>
                                <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                                   title="${downloadLink.linkTitle}">
                                        ${downloadLink.linkText}</a>

                                <c:choose>
                                    <c:when test="${downloadLink.linkText == 'OTUs and taxonomic assignments (BIOM)' || downloadLink.linkText == 'OTUs, reads and taxonomic assignments (HDF5 biom)' || downloadLink.linkText == 'OTUs, reads and taxonomic assignments (JSON biom)'}">
                                <span class="icon icon-generic show_tooltip" data-icon="i"
                                      title="BIOM is a standard format (http://biom-format.org/) for representing taxonomy distribution (OTUs and their occurence in each sample). BIOM files could be directly imported in diverse analysis packages such as MEGAN (http://ab.inf.uni-tuebingen.de/software/megan5/)."></span></c:when>
                                    <c:when test="${downloadLink.linkText == 'Phylogenetic tree (Newick format)'}"> <span
                                            class="icon icon-generic show_tooltip" data-icon="i"
                                            title="The Newick format is a computer-readable phylogenetic tree representation (http://evolution.genetics.washington.edu/phylip/newicktree.html). The trees can be visualized using graphical viewers such as FigTree (http://tree.bio.ed.ac.uk/software/figtree/) and iTOL (http://itol.embl.de/)."></span></c:when>
                                </c:choose>

                                <span class="list_date"> - ${downloadLink.fileSize}</span>
                            </li>
                        </c:forEach>
                    </ul>
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
