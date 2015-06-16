<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div id="fragment-download">

    <div class="box-export">
        <p>You can download in this section the full set of analysis results files and the original raw sequence
            reads.</p>
        <h4>Sequence data</h4>
        <ul>
            <c:forEach var="downloadLink" items="${model.downloadSection.seqDataDownloadLinks}" varStatus="loop">
                <c:choose>
                    <c:when test="${downloadLink.externalLink}">
                        <li>
                            <a href="${downloadLink.linkURL}"
                               title="${downloadLink.linkTitle}">${downloadLink.linkText}</a>
                        </li>
                    </c:when>
                    <c:when test="${!downloadLink.externalLink && not empty model.sample.analysisCompleted}">
                        <li>
                            <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                               title="${downloadLink.linkTitle}">${downloadLink.linkText}</a><span
                                class="list_date"> - ${downloadLink.fileSize}</span>
                        </li>
                    </c:when>
                    <c:otherwise>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </ul>
        <c:if test="${(not empty model.downloadSection.functionalDownloadSection.interproscanDownloadLinks || not empty model.downloadSection.functionalDownloadSection.otherDownloadLinks) && not empty model.sample.analysisCompleted}">
            <h4>Functional Analysis</h4>
            <ul>
                <c:if test="${not empty model.downloadSection.functionalDownloadSection.interproscanDownloadLinks}">
                    <c:choose>
                        <c:when test="${fn:length(model.downloadSection.functionalDownloadSection.interproscanDownloadLinks)>1}">
                            <li>InterPro matches (TSV) - compressed</li>
                            <ul>
                                <c:forEach var="downloadLink"
                                           items="${model.downloadSection.functionalDownloadSection.interproscanDownloadLinks}"
                                           varStatus="loop">
                                    <li>
                                        <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                                           title="${downloadLink.linkTitle}">${downloadLink.linkText}
                                        </a><span class="list_date_new"> - ${downloadLink.fileSize}
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="downloadLink"
                                       items="${model.downloadSection.functionalDownloadSection.interproscanDownloadLinks}"
                                       varStatus="loop">
                                <li>
                                    <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                                       title="${downloadLink.linkTitle}">${downloadLink.linkText}
                                    </a><span class="list_date_new"> - ${downloadLink.fileSize}
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
                           title="${downloadLink.linkTitle}">
                                ${downloadLink.linkText}
                        </a>
                        <span class="list_date"> - ${downloadLink.fileSize}
                    </li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${not empty model.downloadSection.taxaAnalysisDownloadLinks && not empty model.sample.analysisCompleted}">
            <h4>Taxonomic Analysis</h4>
            <ul>
                <c:forEach var="downloadLink" items="${model.downloadSection.taxaAnalysisDownloadLinks}"
                           varStatus="loop">


                    <li>
                        <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                           title="${downloadLink.linkTitle}">
                                ${downloadLink.linkText}</a>

                        <c:choose>
                            <c:when test="${downloadLink.linkText == 'OTUs and taxonomic assignments (BIOM)' || downloadLink.linkText == 'OTUs, reads and taxonomic assignments (HDF5 biom)' || downloadLink.linkText == 'OTUs, reads and taxonomic assignments (JSON biom)'}">
                                <span class="icon icon-generic" data-icon="i"
                                      title="BIOM is a standard format (http://biom-format.org/) for representing taxonomy distribution (OTUs and their occurence in each sample). BIOM files could be directly imported in diverse analysis packages such as MEGAN (http://ab.inf.uni-tuebingen.de/software/megan5/)."></span></c:when>
                            <c:when test="${downloadLink.linkText == 'Phylogenetic tree (Newick format)'}"> <span
                                    class="icon icon-generic" data-icon="i"
                                    title="The Newick format is a computer-readable phylogenetic tree representation (http://evolution.genetics.washington.edu/phylip/newicktree.html). The trees can be visualized using graphical viewers such as FigTree (http://tree.bio.ed.ac.uk/software/figtree/) and iTOL (http://itol.embl.de/)."></span></c:when>
                        </c:choose>

                        <span class="list_date"> - ${downloadLink.fileSize}</span>
                    </li>
                </c:forEach>
            </ul>
        </c:if>

    </div>
</div>