<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<div id="content">
    <section id="submit-data">
        <c:choose>
            <%-- Show Private welcome emailMessage only if a user is logged in--%>
            <%--Private area is deactivated for the moment until any decision was made--%>
            <%--<c:when test="${not empty model.submitter}">--%>
            <c:when test="false">
                <div id="submit-data-description">
                    <h2>Welcome to your private area</h2>

                    <p> Your username is : <strong>Meta Genome</strong> (edit your profile)<br/>
                        Your email is : <strong>metagenomic@gmail.com</strong></p>

                    <p> You have submitted: <strong>0</strong> projects and <strong>0</strong> samples.</p>

                    <p> You can view and analyse in this area (under My projects and My samples) any private information
                        that is
                        not yet publicly available. </p>

                    <p> Click here to submit a new project</p>

                    <p><a href="<c:url value="${baseURL}/logout"/>" title="logout">Click here</a> to logout and return
                        to
                        the
                        metagenomics homepage</p>
                </div>
            </c:when>


            <%-- Show Slideshow if not logged in --%>
            <c:otherwise>
                <!--[if IE 6]><div id="IE6" class="IE"><![endif]--> <!--[if IE 7]><div id="IE7" class="IE"><![endif]--> <!--[if IE & ((!IE 6) & (!IE 7))]><div><![endif]--> <!--[if !IE]>--><div><!--<![endif]-->
                <div class="carousel">

                    <ul>
                        <li>
                            <!-- module 1 -->
                            <div class="module" id="mod1">
                                <div class="top_corners"></div>
                                <div class="content">
                                    <h3>Easy submission</h3>

                                    <div class="cent"><img src="${pageContext.request.contextPath}/img/icons_sub.png"
                                                           alt="easy submission"/></div>
                                    <p>Manually supported submission process, with help available for
                                        meta-data provision. Raw SFF format 454 data accepted, other formats considered
                                        on
                                        request.<br/></p>

                                    <div class="find_more"><a href="${pageContext.request.contextPath}/info#features_1"
                                                              title="find out more about easy submission"><span>Find out more</span></a>
                                    </div>
                                </div>
                                <div class="bottom_corners"></div>
                            </div>
                        </li>
                        <li>
                            <!-- module 2 -->
                            <div class="module" id="mod2">
                                <div class="top_corners"></div>
                                <div class="content">
                                    <h3>Powerful analysis</h3>

                                    <div class="cent"><img src="${pageContext.request.contextPath}/img/icons_ana.png"
                                                           alt="powerful analysis"/></div>
                                    <p>Functional analysis of metagenomic sequences using InterPro - a powerful and
                                        sophisticated alternative to BLAST-based analyses.<br/></p>

                                    <div class="find_more"><a href="${pageContext.request.contextPath}/info#features_2"
                                                              title="find out more about analysis"><span>Find out more</span></a>
                                    </div>
                                </div>
                                <div class="bottom_corners"></div>
                            </div>
                        </li>
                        <li>
                            <!-- module 3 -->
                            <div class="module" id="mod3">
                                <div class="top_corners"></div>
                                <div class="content">
                                    <h3>Data archiving</h3>

                                    <div class="cent"><img src="${pageContext.request.contextPath}/img/icons_arc.png"
                                                           alt="data archiving"/></div>
                                    <p>Data automatically archived at the Sequence Read Archive (SRA), ensuring
                                        accession
                                        numbers are supplied - a prerequisite for publication in many journals.</p>

                                    <div class="find_more"><a href="${pageContext.request.contextPath}/info#features_3"
                                                              title="find out more about data archiving"><span>Find out more</span></a>
                                    </div>
                                </div>
                                <div class="bottom_corners"></div>
                            </div>
                        </li>
                        <li>
                            <!-- module 4 -->
                            <div class="module" id="mod4">
                                <div class="top_corners"></div>
                                <div class="content">
                                    <h3>Submit your data</h3>

                                    <div class="cent"><img src="${pageContext.request.contextPath}/img/icons_submit.png"
                                                           alt=""
                                                           width="71" height="71"/></div>
                                    <c:url var="enaRegistrationUrl"
                                           value="${model.propertyContainer.enaSubmissionURL.registrationLink}">
                                        <c:param name="url" value="${enaUrlParam}"/>
                                    </c:url>
                                    <p style="padding-bottom:7px; "> You can click on &quot;submit your data&quot; to
                                        send us your nucleotide sequences for analysis.
                                        <%--(Note: you will need to <a href="javascript:openLoginDialogForm()" title="Login">login</a>--%>
                                        (Note: you will need to <a id="LoginBlockUI" href="" title="Login">login</a>
                                        or <a href="<c:out value="${enaRegistrationUrl}"/>"
                                              title="Registration">register</a>
                                        first). </p>
                                    <c:choose>
                                        <c:when test="${empty model.submitter}">
                                            <div class="find_more"><a
                                                    href="<c:url value="${baseURL}/login"/>"
                                                    title="submit data for analysis"><span>Submit your data</span></a>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="find_more"><a href="<c:url value="${baseURL}/submit"/>"
                                                                      title="submit data for analysis"><span>Submit your data</span></a>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                                <div class="bottom_corners"></div>
                            </div>
                        </li>
                    </ul>
                </div>
                </div>
            </c:otherwise>
        </c:choose>


    </section>


    <section id="list-data">

        <div id="list-data-study" class="grid_9 alpha">
            <h2>Projects</h2>

            <p>We are unable to display the data you request. We are aware of this problem and are trying
                to fix it as fast as possible. Please try again later.</p>
        </div>
        <div id="list-data-sample" class="grid_9">
            <h2>Samples</h2>

            <p>We are unable to display the data you request. We are aware of this problem and are trying
                to fix it as fast as possible. Please try again later.</p>
        </div>
        <%@ include file="../components/listNewsComponent.jsp" %>
    </section>


</div>

<%--<div id="sidebar"><tiles:insertAttribute name="loginForm"/></div>--%>