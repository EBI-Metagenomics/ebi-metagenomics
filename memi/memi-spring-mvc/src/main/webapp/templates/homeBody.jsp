<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<div id="content">
<section id="submit-data">
    <div id="submit-data-display"></div>
    <div id="submit-data-description">
        <h2>Data Submission</h2>
        <p>Short description of what kind of data you can submit, why and how ... <br/>
        We offers multiple options for submitting your data. We can also provide assistance in formatting and processing for easy incorporation into our database. By submitting your data to us, you help to ensure its long-term availability as well as place it within a rich, highly cross-referenced context.</p>
         <c:choose>
                    <c:when test="${empty mgModel.submitter}">
                        <p>
                            <a href="<c:url value="${baseURL}/login"/>" class="more_desc">Submit your data</a>
                        </p>

                    </c:when>
                    <c:otherwise>
                        <p>
                            <a href="<c:url value="${baseURL}/submissionForm"/>" class="more_desc">Submit your data</a>
                        </p>
                    </c:otherwise>
                </c:choose>
    </div>
    <%--  <a href="<c:url value="${baseURL}/info"/>">more Info</a></p>   --%>
</section>

<section id="list-data">
<div id="list-data-study">

        <h2>Study list</h2>
        <p><span class="list_date">:</span><a href="" class="list_more">erm availability as well as place it within a rich, highly cross-referenced context.</a> <a href="#" class="more_view">view</a></p>



</div>

<div id="list-data-sample">

</div>
</section>
</div>
<div id="sidebar"><tiles:insertAttribute name="loginForm"/></div>

    <div style="margin-top:10px"/>
    <table frame="box" width="95%">
        <c:choose>
        <%-- Show MyStudies and MySamples tables only if a user is logged in--%>
        <c:when test="${not empty mgModel.submitter}">
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
                                <a href="<c:url value="${baseURL}/studyView/${study.id}"/>">${study.lastMetadataReceived}
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
                                <a href="<c:url value="${baseURL}/sampleView/${sample.sampleId}"/>">${sample.metadataReceived}
                                    - ${sample.sampleTitle} (<c:out value="${mySampleVisibility}"/>)</a>
                            </td>
                        <tr>
                            </c:forEach>
                    </table>
                </td>
            </tr>
            <tr>
                <td width="50%" align="right" valign="top">
                    <a href="<c:url value="${baseURL}/viewStudies/doSearch?searchTerm=&studyVisibility=MY_STUDIES&search=Search"/>">View
                        all my studies</a>
                </td>
                <td width="50%" align="right" valign="top">
                    <a href="<c:url value="${baseURL}/viewSamples/doSearch?searchTerm=&sampleVisibility=MY_SAMPLES&search=Search"/>">View
                        all my samples</a>
                </td>
            </tr>
        </c:when>
        <%-- End of show MyStudies and MySamples tables--%>

        <%-- Show recent PublicStudies and PublicSamples only when the user is not logged in --%>
        <c:otherwise>
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
                            <a href="<c:url value="${baseURL}/studyView/${study.id}"/>">${study.lastMetadataReceived}
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
                            <a href="<c:url value="${baseURL}/sampleView/${sample.sampleId}"/>">${sample.metadataReceived}
                                - ${sample.sampleTitle} (<c:out value="${publicSampleVisibility}"/>)</a>
                        </td>
                    <tr>
                        </c:forEach>
                </table>
            </td>
        </tr>
        </c:otherwise>
        </c:choose>
        <%-- End of show recent PublicStudies and PublicSamples--%>

        <%-- Always show links to all PublicStudies and PublicSamples (whether the user is logged in or not) --%>
        <tr>
            <td width="50%" align="right" valign="top">
                <a href="<c:url value="${baseURL}/viewStudies/doSearch?searchTerm=&studyVisibility=ALL_PUBLISHED_STUDIES&search=Search"/>">View
                    all public studies</a>
            </td>
            <td width="50%" align="right" valign="top">
                <a href="<c:url value="${baseURL}/viewSamples/doSearch?searchTerm=&sampleVisibility=ALL_PUBLISHED_SAMPLES&search=Search"/>">View
                    all public samples</a>
            </td>
        </tr>
        <%-- End of links to all PublicStudies and PublicSamples --%>

    </table>
