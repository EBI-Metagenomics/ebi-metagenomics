<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<div id="content">
    <%--<spring:message code="label.email.submission.subject" />--%>
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

            <p>Click "submit data" to send us your nucleotide sequences to analyse. It's as simple as 1,2,3:<br/>
                1 . Login (or register)<br/>
                2 . Provide details of your project and your nucleotide sequence data<br/>
                3 . Track and view your analysis results.</p>
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
                    <c:choose>
                        <c:when test="${empty model.myStudiesMap}">
                            <p>
                                No projects submitted
                            </p>
                        </c:when>
                        <c:otherwise>
                            <h3>My latest projects</h3>
                            <c:forEach var="entry" items="${model.myStudiesMap}" varStatus="status">
                                <p>
                                    <c:if test="${!entry.key.public}">
                                        <img alt="private" src="${pageContext.request.contextPath}/img/icon_priv_private.gif">
                                    </c:if>&nbsp;&nbsp;<a href="<c:url value="${baseURL}/study/${entry.key.studyId}"/>" class="list_more">${entry.key.studyName}</a>
                                    <br />
                                    <span class="list_desc"><c:out value="${entry.key.shortStudyAbstract} ..."/></span>
                                    <a href="<c:url value="${baseURL}/study/${entry.key.studyId}"/>#study_desc" class="more_view">view more</a> -  <a href="<c:url value="${baseURL}/study/${entry.key.studyId}"/>#samples_id"  class="list_sample"><c:out value="${entry.value} sample"/><c:if test='${entry.value > 1}'>s</c:if></a>
                                </p>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    <p>
                        <a href="<c:url value="${baseURL}/studies/doSearch?search=Search&studyVisibility=ALL_PUBLISHED_PROJECTS"/>" title="View all public studies" class="all">All public projects</a>
                        <c:if test="${not empty model.myStudiesMap}">
                            <a href="<c:url value="${baseURL}/studies/doSearch?search=Search&studyVisibility=MY_PROJECTS"/>" title="View all my studies" class="all">All my projects</a>
                        </c:if>
                    </p>
                </div>

                <div id="list-data-sample">
                    <h2>My Samples</h2>
                    <c:choose>
                        <c:when test="${empty model.mySamples}">
                            <p>
                                No samples submitted
                            </p>
                        </c:when>
                        <c:otherwise>
                            <h3>My latest samples</h3>
                            <c:forEach var="sample" items="${model.mySamples}" varStatus="status">
                                <p><%--<span class="list_date">${sample.metadataReceived}:</span>--%>
                                    <c:if test="${!sample.public}"><img alt="private" src="${pageContext.request.contextPath}/img/icon_priv_private.gif"></c:if>&nbsp;&nbsp;
                                    <a href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>" class="list_more">${sample.sampleName}</a>
                                    <br />
                                    <span class="list_desc"><c:out value="${sample.shortSampleDescription} ..."/></span>
                                    <a href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>#sample_desc" class="more_view">view more</a> -
                                    <c:choose>
                                        <c:when test="${empty sample.analysisCompleted}">Analysis in progress</c:when>
                                        <c:otherwise>
                                            <a href="<c:url value="${baseURL}/analysisStatsView/${sample.sampleId}"/>"  class="list_sample">Analysis results</a>
                                        </c:otherwise>
                                    </c:choose>
                                </p>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    <p>
                        <a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=&sampleVisibility=ALL_PUBLISHED_SAMPLES&search=Search"/>" title="View all public samples" class="all">All public samples</a>
                        <c:if test="${not empty model.mySamples}">
                            <a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=&sampleVisibility=MY_SAMPLES&search=Search"/>" title="View all my samples" class="all">All my samples</a>
                        </c:if>
                    </p>
                </div>
            </c:when>
            <%-- End of show MyStudies and MySamples tables--%>

            <%-- Show recent PublicStudies and PublicSamples only when the user is not logged in --%>
            <c:otherwise>
                <div id="list-data-study">
                    <h2>Projects</h2>
                    <h3>Latest public projects</h3>
                    <c:forEach var="entry" items="${model.publicStudiesMap}" varStatus="status">
                        <p><%--<span class="list_date">${entry.key.lastMetadataReceived}:</span> --%>
                            <a href="<c:url value="${baseURL}/study/${entry.key.studyId}"/>" class="list_more">${entry.key.studyName}</a>
                            <br />
                            <span class="list_desc"><c:out value="${entry.key.shortStudyAbstract} ..."/></span>
                            <a href="<c:url value="${baseURL}/study/${entry.key.studyId}"/>#study_desc" class="more_view">view more</a> - <a href="<c:url value="${baseURL}/study/${entry.key.studyId}"/>#samples_id" class="list_sample"><c:out value="${entry.value} sample"/><c:if test='${entry.value > 1}'>s</c:if></a>
                        </p>
                    </c:forEach>
                    <p><a href="<c:url value="${baseURL}/studies/doSearch?search=Search&studyVisibility=ALL_PUBLISHED_PROJECTS"/>" title="View all public studies" class="all">View all projects</a></p>
                </div>

                <div id="list-data-sample">
                    <h2>Samples</h2>
                    <h3>Latest public samples</h3>
                    <c:forEach var="sample" items="${model.publicSamples}" varStatus="status">
                        <p><%--<span class="list_date">${sample.metadataReceived}:</span>--%>
                            <a href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>" class="list_more">${sample.sampleName}</a>
                            <br />
                            <span class="list_desc"><c:out value="${sample.shortSampleDescription} ..."/></span>
                            <a href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>#sample_desc" class="more_view">view more</a> -
                            <c:choose>
                                <c:when test="${empty sample.analysisCompleted}">Analysis in progress</c:when>
                                <c:otherwise>
                                    <a href="<c:url value="${baseURL}/analysisStatsView/${sample.sampleId}"/>" class="list_sample">Analysis results</a>
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </c:forEach>
                    <p><a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=&sampleVisibility=ALL_PUBLISHED_SAMPLES&search=Search"/>" title="View all public samples" class="all">View all samples</a></p>
                </div>
            </c:otherwise>
        </c:choose>
    </section>
</div>

<div id="sidebar"><tiles:insertAttribute name="loginForm"/></div>
