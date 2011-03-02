<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<div id="content">
    <section id="submit-data">
        <div id="submit-data-display"><h1>
            <c:choose>
                <c:when test="${empty model.submitter}">
                    <a href="<c:url value="${baseURL}/login"/>">Submit your data</a>
                </c:when>
                <c:otherwise>
                    <a href="<c:url value="${baseURL}/submit"/>">Submit your data</a>
                </c:otherwise>
            </c:choose></h1></div>
        <div id="submit-data-description">
            <h2>Data Submission</h2>

            <p>We offers multiple options for submitting your data. We can also provide assistance in formatting and
                processing for easy incorporation into our database. By submitting your data to us, you help to ensure
                its long-term availability as well as place it within a rich, highly cross-referenced context.</p>
            <c:choose>
                <c:when test="${empty model.submitter}">
                    <p>
                        <a href="<c:url value="${baseURL}/login"/>">Submit your data</a>
                    </p>

                </c:when>
                <c:otherwise>
                    <p>
                        <a href="<c:url value="${baseURL}/submit"/>">Submit your data</a>
                    </p>
                </c:otherwise>
            </c:choose>
        </div>
        <%--  <a href="<c:url value="${baseURL}/info"/>">more Info</a></p>   --%>
    </section>


    <section id="list-data">

        <c:choose>
            <%-- Show MyStudies and MySamples tables only if a user is logged in--%>
            <c:when test="${not empty model.submitter}">
                <div id="list-data-study">
                    <h2>My studies</h2>
                    <c:forEach var="study" items="${model.myStudies}" varStatus="status">
                        <%--Just to show that the study is public or private--%>
                        <c:choose>
                            <c:when test="${study.public}">
                                <c:set var="myStudyVisibility" value="public"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="myStudyVisibility" value="private"/>
                            </c:otherwise>
                        </c:choose>
                        <p><span class="list_date">${study.lastMetadataReceived}:</span> <a
                                href="<c:url value="${baseURL}/study/${study.studyId}"/>"
                                class="list_more">${study.studyName} <img alt="<c:out value="${myStudyVisibility}"/>"
                                                                          src="../img/icon_priv_<c:out value="${myStudyVisibility}"/>.gif"></a>
                            <a href="<c:url value="${baseURL}/study/${study.studyId}"/>" class="more_view">view</a></p>

                    </c:forEach>
                    <br/>

                    <p>
                        <a href="<c:url value="${baseURL}/viewStudies/doSearch?searchTerm=&studyVisibility=ALL_PUBLISHED_STUDIES&search=Search"/>"
                           title="View
                    all public studies">View all public studies</a> |
                        <a href="<c:url value="${baseURL}/viewStudies/doSearch?searchTerm=&studyVisibility=MY_STUDIES&search=Search"/>"
                           title="View
                    all my studies">View all my studies</a></p>
                </div>
                <div id="list-data-sample">
                    <h2>My samples</h2>
                    <c:forEach var="sample" items="${model.mySamples}" varStatus="status">
                        <%--Just to show that the sample is public or private--%>
                        <c:choose>
                            <c:when test="${study.public}">
                                <c:set var="mySampleVisibility" value="public"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="mySampleVisibility" value="private"/>
                            </c:otherwise>
                        </c:choose>
                        <p><span class="list_date">${sample.metadataReceived}:</span> <a
                                href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>"
                                class="list_more">${sample.sampleTitle} <img
                                alt="<c:out value="${mySampleVisibility}"/>"
                                src="../img/icon_priv_<c:out value="${mySampleVisibility}"/>.gif"></a> <a
                                href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>" class="more_view">view</a>
                        </p>

                    </c:forEach>
                    <br/>

                    <p>
                        <a href="<c:url value="${baseURL}/viewSamples/doSearch?searchTerm=&sampleVisibility=ALL_PUBLISHED_SAMPLES&search=Search"/>"
                           title="View
                    all public samples">View all public samples</a> |
                        <a href="<c:url value="${baseURL}/viewSamples/doSearch?searchTerm=&sampleVisibility=MY_SAMPLES&search=Search"/>"
                           title="View
                    all My samples">View
                            all my samples</a></p>
                </div>
            </c:when>
            <%-- End of show MyStudies and MySamples tables--%>

            <%-- Show recent PublicStudies and PublicSamples only when the user is not logged in --%>
            <c:otherwise>
                <div id="list-data-study">
                    <h2>Study list</h2>
                    <c:forEach var="study" items="${model.publicStudies}" varStatus="status">
                        <%--Just to show that the study is public or private--%>
                        <c:choose>
                            <c:when test="${study.public}">
                                <c:set var="publicStudyVisibility" value="public"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="publicStudyVisibility" value="private"/>
                            </c:otherwise>
                        </c:choose>
                        <p><span class="list_date">${study.lastMetadataReceived}:</span> <a
                                href="<c:url value="${baseURL}/study/${study.studyId}"/>"
                                class="list_more">${study.studyName} <img
                                alt="<c:out value="${publicStudyVisibility}"/>"
                                src="../img/icon_priv_<c:out value="${publicStudyVisibility}"/>.gif"></a> <a
                                href="<c:url value="${baseURL}/study/${study.studyId}"/>" class="more_view">view</a></p>

                    </c:forEach>
                    <br/>

                    <p>
                        <a href="<c:url value="${baseURL}/viewStudies/doSearch?searchTerm=&studyVisibility=ALL_PUBLISHED_STUDIES&search=Search"/>"
                           title="View
                    all public studies">View all studies</a></p>
                </div>
                <div id="list-data-sample">
                    <h2>Sample list</h2>
                    <c:forEach var="sample" items="${model.publicSamples}" varStatus="status">
                        <%--Just to show that the sample is public or private--%>
                        <c:choose>
                            <c:when test="${sample.public}">
                                <c:set var="publicSampleVisibility" value="public"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="publicSampleVisibility" value="private"/>
                            </c:otherwise>
                        </c:choose>
                        <p><span class="list_date">${sample.metadataReceived}:</span> <a
                                href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>"
                                class="list_more">${sample.sampleTitle} <img
                                alt="<c:out value="${publicStudyVisibility}"/>"
                                src="../img/icon_priv_<c:out value="${publicSampleVisibility}"/>.gif"></a> <a
                                href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>" class="more_view">view</a>
                        </p>

                    </c:forEach>
                    <br/>

                    <p>
                        <a href="<c:url value="${baseURL}/viewSamples/doSearch?searchTerm=&sampleVisibility=ALL_PUBLISHED_SAMPLES&search=Search"/>"
                           title="View
                    all public samples">View all samples</a></p>
                </div>
            </c:otherwise>
        </c:choose>

    </section>

</div>


<div id="sidebar"><tiles:insertAttribute name="loginForm"/></div>
