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
                        <p>
                            <a href="<c:url value="${baseURL}/login"/>">Submit</a>
                        </p>
                        <%-- JQuery example--%>
                        <%--<div id="submitdiv">--%>
                        <%--<a id="submit" href="#">Submit</a>--%>
                        <%--</div>--%>
                    </c:when>
                    <c:otherwise>
                        <p>
                            <a href="<c:url value="${baseURL}/submissionForm"/>">Submit</a>
                        </p>
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
                                    <%--Just to show that the study is public or private--%>
                                <c:choose>
                                    <c:when test="${study.public}">
                                        <c:set var="myStudyVisibility" value="public"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="myStudyVisibility" value="private"/>
                                    </c:otherwise>
                                </c:choose>
                                <a href="<c:url value="${baseURL}/studyOverview/${study.studyId}"/>">${study.lastMetadataReceived}
                                    - ${study.studyName} (<c:out value="${myStudyVisibility}"/>)</a>
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
                                    <%--Just to show that the sample is public or private--%>
                                <c:choose>
                                    <c:when test="${study.public}">
                                        <c:set var="mySampleVisibility" value="public"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="mySampleVisibility" value="private"/>
                                    </c:otherwise>
                                </c:choose>
                                <a href="<c:url value="${baseURL}/sampleOverview/${sample.sampleId}"/>">${sample.metadataReceived}
                                    - ${sample.sampleTitle} (<c:out value="${mySampleVisibility}"/>)</a>
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
        <%-- Shows PublicStudies and PublicSamples tables at any time--%>
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
                                <%--Just to show that the study is public or private--%>
                            <c:choose>
                                <c:when test="${study.public}">
                                    <c:set var="publicStudyVisibility" value="public"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="publicStudyVisibility" value="private"/>
                                </c:otherwise>
                            </c:choose>
                            <a href="<c:url value="${baseURL}/studyOverview/${study.studyId}"/>">${study.lastMetadataReceived}
                                - ${study.studyName} (<c:out value="${publicStudyVisibility}"/>)</a>
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
                                <%--Just to show that the sample is public or private--%>
                            <c:choose>
                                <c:when test="${sample.public}">
                                    <c:set var="publicSampleVisibility" value="public"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="publicSampleVisibility" value="private"/>
                                </c:otherwise>
                            </c:choose>
                            <a href="<c:url value="${baseURL}/sampleOverview/${sample.sampleId}"/>">${sample.metadataReceived}
                                - ${sample.sampleTitle} (<c:out value="${publicSampleVisibility}"/>)</a>
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