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
                    <h2>My Projects</h2>
                    <c:forEach var="entry" items="${model.myStudiesMap}" varStatus="status">
                        <p>
                            <a href="<c:url value="${baseURL}/study/${entry.key.studyId}"/>"
                               class="list_more">${entry.key.studyName}
                                <c:if test="${!entry.key.public}">
                                    <img alt="public" src="../img/icon_priv_private.gif">
                                </c:if>
                            </a>
                            </br>
                            <c:out value="${entry.key.shortStudyAbstract} ..."/>
                            <a href="<c:url value="${baseURL}/study/${entry.key.studyId}"/>#study_desc"
                               class="more_view">view</a>
                            </br>
                            <a href="<c:url value="${baseURL}/study/${entry.key.studyId}"/>#samples_id"
                               class="list_more">
                                <c:out value="(${entry.value} samples)"/>
                            </a>
                        </p>
                    </c:forEach>
                    <br/>

                    <p>
                        <a href="<c:url value="${baseURL}/studies/doSearch?search=Search&studyVisibility=ALL_PUBLISHED_PROJECTS"/>"
                           title="View
                    all public studies">View all public projects</a> |
                        <a href="<c:url value="${baseURL}/studies/doSearch?search=Search&studyVisibility=MY_PROJECTS"/>"
                           title="View
                    all my studies">View all my projects</a></p>
                </div>
                <div id="list-data-sample">
                    <h2>My Samples</h2>
                    <c:forEach var="sample" items="${model.mySamples}" varStatus="status">
                        <%--Just to show that the sample is public or private--%>
                        <p><%--<span class="list_date">${sample.metadataReceived}:</span>--%>
                            <a href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>"
                               class="list_more">${sample.sampleTitle}
                                <c:if test="${!sample.public}">
                                    <img alt="private" src="../img/icon_priv_private.gif">
                                </c:if>
                            </a>
                            </br>
                            <c:out value="${sample.shortSampleDescription} ..."/>
                            <a href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>#sample_desc"
                               class="more_view">view
                            </a>
                            </br>
                            <c:choose>
                                <c:when test="${empty sample.analysisCompleted}">Analysis in Progress</c:when>
                                <c:otherwise>
                                    <a href="<c:url value="${baseURL}/analysisStatsView/${sample.sampleId}"/>">Analysis
                                        Results</a>
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </c:forEach>
                    <br/>

                    <p>
                        <a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=&sampleVisibility=ALL_PUBLISHED_SAMPLES&search=Search"/>"
                           title="View
                    all public samples">View all public samples</a> |
                        <a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=&sampleVisibility=MY_SAMPLES&search=Search"/>"
                           title="View
                    all My samples">View
                            all my samples</a></p>
                </div>
            </c:when>
            <%-- End of show MyStudies and MySamples tables--%>

            <%-- Show recent PublicStudies and PublicSamples only when the user is not logged in --%>
            <c:otherwise>
                <div id="list-data-study">
                    <h2>Projects</h2>
                    <c:forEach var="entry" items="${model.publicStudiesMap}" varStatus="status">
                        <p>
                                <%--<span class="list_date">${entry.key.lastMetadataReceived}:</span> --%>
                            <a href="<c:url value="${baseURL}/study/${entry.key.studyId}"/>"
                               class="list_more">${entry.key.studyName}
                                <c:if test="${!entry.key.public}">
                                    <img alt="public" src="../img/icon_priv_private.gif">
                                </c:if>
                            </a>
                            </br>
                            <c:out value="${entry.key.shortStudyAbstract} ..."/>
                            <a href="<c:url value="${baseURL}/study/${entry.key.studyId}"/>#study_desc"
                               class="more_view">view</a>
                            </br>
                            <a href="<c:url value="${baseURL}/study/${entry.key.studyId}"/>#samples_id"
                               class="list_more">
                                <c:out value="(${entry.value} samples)"/>
                            </a>
                                <%--<a href="<c:url value="${baseURL}/study/${entry.key.studyId}"/>" class="more_view">view</a>--%>
                        </p>

                    </c:forEach>
                    <br/>

                    <p>
                        <a href="<c:url value="${baseURL}/studies/doSearch?search=Search&studyVisibility=ALL_PUBLISHED_PROJECTS"/>"
                           title="View
                    all public studies">View all projects</a></p>
                </div>
                <div id="list-data-sample">
                    <h2>Samples</h2>
                    <c:forEach var="sample" items="${model.publicSamples}" varStatus="status">
                        <p><%--<span class="list_date">${sample.metadataReceived}:</span>--%>
                            <a href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>"
                               class="list_more">${sample.sampleTitle}
                                <c:if test="${!sample.public}">
                                    <img alt="private" src="../img/icon_priv_private.gif">
                                </c:if>
                            </a>
                            </br>
                            <c:out value="${sample.shortSampleDescription} ..."/>
                            <a href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>#sample_desc"
                               class="more_view">view
                            </a>
                            </br>
                            <c:choose>
                                <c:when test="${empty sample.analysisCompleted}">Analysis in Progress</c:when>
                                <c:otherwise>
                                    <a href="<c:url value="${baseURL}/analysisStatsView/${sample.sampleId}"/>">Analysis
                                        Results</a>
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </c:forEach>
                    <br/>

                    <p>
                        <a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=&sampleVisibility=ALL_PUBLISHED_SAMPLES&search=Search"/>"
                           title="View
                    all public samples">View all samples</a></p>
                </div>
            </c:otherwise>
        </c:choose>

    </section>
</div>

<div id="sidebar"><tiles:insertAttribute name="loginForm"/></div>