<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="content">
    <h2>EBI Metagenomics Portal</h2>

    <div style="margin-top:6px"></div>
    <h3>What's metagenomics?</h3>

    <p>The study of all genomes present in any given environment without the need for prior individual identification or
        amplification is termed metagenomics. For example, in its simplest form a metagenomic study might be the direct
        sequence results of DNA extracted from a bucket of sea water.<br><a
                href="<c:url value="./info"/>">more</a></p>

    <table border="0" width="95%" style="border-width: 1px;border-color: #000000;border-style: solid;">
        <tr>
            <td width="50%" align="center" valign="middle">
                <h3>Release news</h3>
                <table border="0" style="border-width: 1px;border-color: #000000;border-style: solid;">
                    <c:forEach var="news" items="${mgModel.newsList}">
                        <tr>
                            <td><c:out value="${news.formattedAnnouncedDate} - ${news.newsHeadline}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
            <td width="50%" align="left" valign="top">
                <h3>Submission form</h3>
                <c:choose>
                    <c:when test="${empty mgModel.submitter}">
                        <a href="<c:url value="./index"/>">Submit data</a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value="./submissionForm"/>">Submit data</a>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </table>
</div>