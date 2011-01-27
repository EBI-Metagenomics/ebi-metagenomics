<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="content">
    <h2>EBI Metagenomics Portal</h2>

    <div style="margin-top:6px"></div>
    <h3>What's metagenomics?</h3>

    <p>The study of all genomes present in any given environment without the need for prior individual identification or
        amplification is termed metagenomics. For example, in its simplest form a metagenomic study might be the direct
        sequence results of DNA extracted from a bucket of sea water.<br><a
                href="<c:url value="${baseURL}/info"/>">more Info</a></p>

    <div style="margin-top:10px"/>
    <table frame="box" width="95%">
        <tr>
            <td width="50%" align="left" valign="top">
                <h3>Data submission</h3>
                Short description of what kind of data you can submit, why and how ...
                <c:choose>
                    <c:when test="${empty mgModel.submitter}">
                        <div id="submitdiv">
                            <a id="submit" href="#">Submit</a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <br>
                        <a href="<c:url value="${baseURL}/submissionForm"/>">Submit</a>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </table>
    <div style="margin-top:10px"/>
    <table frame="box" width="95%">
        <%-- Show MyStudies and MySamples tables only if a user is logged in--%>
        <c:if test="${not empty mgModel.submitter}">
            <tr>
                <td width="50%" align="left" valign="top">
                    <h3>My studies</h3>
                </td>
                <td width="50%" align="left" valign="top">
                    <h3>My samples</h3>
                </td>
            </tr>
            <tr>
                <td width="50%" align="left" valign="top">
                    <table frame="box">
                        <c:forEach var="study" items="${mgModel.myStudies}" varStatus="status">
                        <tr>
                            <td>
                                <a href="<c:url value="${baseURL}/studyOverview/${study.studyId}"/>">${study.lastMetadataReceived}
                                    - ${study.studyName} (${study.public})</a>
                            </td>
                        <tr>
                            </c:forEach>
                    </table>
                </td>
                <td width="50%" align="right" valign="top">
                    <table frame="box">
                        <c:forEach var="sample" items="${mgModel.mySamples}" varStatus="status">
                        <tr>
                            <td>
                                <a href="<c:url value="${baseURL}/sampleOverview/${sample.sampleId}"/>">${sample.metadataReceived}
                                    - ${sample.sampleTitle} (${sample.public})</a>
                            </td>
                        <tr>
                            </c:forEach>
                    </table>
                </td>
            </tr>
            <tr>
                <td width="50%" align="right" valign="top">
                    <a href="<c:url value="${baseURL}/listStudies"/>">View all</a>
                </td>
                <td width="50%" align="right" valign="top">
                    <a href="<c:url value="${baseURL}/listStudies"/>">View all</a>
                </td>
            </tr>
        </c:if>
        <%-- End of show MyStudies and MySamples tables--%>
        <tr>
            <td width="50%" align="left" valign="top">
                <h3>Public studies</h3>
            </td>
            <td width="50%" align="left" valign="top">
                <h3>Public samples</h3>
            </td>
        </tr>
        <tr>
            <td width="50%" align="left" valign="top">
                <table frame="box">
                    <c:forEach var="study" items="${mgModel.publicStudies}" varStatus="status">
                    <tr>
                        <td>
                            <a href="<c:url value="${baseURL}/studyOverview/${study.studyId}"/>">${study.lastMetadataReceived}
                                - ${study.studyName} (${study.public})</a>
                        </td>
                    <tr>
                        </c:forEach>
                </table>
            </td>
            <td width="50%" align="right" valign="top">
                <table frame="box">
                    <c:forEach var="sample" items="${mgModel.publicSamples}" varStatus="status">
                    <tr>
                        <td>
                            <a href="<c:url value="${baseURL}/sampleOverview/${sample.sampleId}"/>">${sample.metadataReceived}
                                - ${sample.sampleTitle} (${sample.public})</a>
                        </td>
                    <tr>
                        </c:forEach>
                </table>
            </td>
        </tr>
        <tr>
            <td width="50%" align="right" valign="top">
                <a href="<c:url value="${baseURL}/listStudies"/>">View all</a>
            </td>
            <td width="50%" align="right" valign="top">
                <a href="<c:url value="${baseURL}/listStudies"/>">View all</a>
            </td>
        </tr>
    </table>
</div>