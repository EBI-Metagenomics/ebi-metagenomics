<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

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

                <p><a href="<c:url value="${baseURL}/logout"/>" title="logout">Click here</a> to logout and return to
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
                                    meta-data provision. Accepted data formats include SFF (454) and FASTQ (Illumina and
                                    IonTorrent).<br/></p>

                                <div class="find_more"><a href="${pageContext.request.contextPath}/about#features_1"
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
                                    sophisticated alternative to BLAST-based analyses. Taxonomy diversity analysis is
                                    performed using Qiime. <br/></p>

                                <div class="find_more"><a href="${pageContext.request.contextPath}/about#features_2"
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
                                <p>Data automatically archived at the European Nucleotide Archive (ENA), ensuring accession
                                    numbers are supplied - a prerequisite for publication in many journals.</p>

                                <div class="find_more"><a href="${pageContext.request.contextPath}/about#features_3"
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
                                <%--</c:url>--%>
                                <p style="padding-bottom:7px; "> You can click on <a href="<c:url value="${baseURL}/submit"/>"
                                                                                     title="Submit data">Submit data</a>
                                    to send us your nucleotide sequences for analysis.

                                    (Note: you will need to  <a  href="https://www.ebi.ac.uk/ena/submit/sra/#metagenome_registration"
                                                                 title="Click here to register for a new ENA submitter account">register</a>
                                    first). </p>
                                <c:choose>
                                    <c:when test="${empty model.submitter}">
                                        <div class="find_more"><a href="<c:url value="${baseURL}/submitData"/>"
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

    <c:choose>
        <%-- Show MyStudies and MySamples tables only if a user is logged in--%>
        <c:when test="${not empty model.submitter}">
            <div id="list-data-study" class="grid_9 alpha">
                <h2>My Projects</h2>
                <c:choose>
                    <c:when test="${empty model.myStudiesMap}">
                        <p>
                            No projects submitted
                        </p>
                    </c:when>
                    <c:otherwise>
                        <h3>My latest projects (Total: <a
                                href="<c:url value="${baseURL}/projects/doSearch?searchTerm=&studyVisibility=MY_PROJECTS&search=Search"/>"
                                title="View all ${model.myStudiesCount} my projects">${model.myStudiesCount}</a>)</h3>
                        <%--The count starts at 0, that is why we subtract 1 from the end value--%>
                        <c:forEach var="entry" items="${model.myStudiesMap}" varStatus="status" begin="0"
                                   end="${model.maxRowNumberOfLatestItems-1}">
                            <p>
                                <c:if test="${!entry.key.public}">
                                    <img alt="private"
                                         src="${pageContext.request.contextPath}/img/icon_priv_private.gif">
                                </c:if>&nbsp;&nbsp;<a
                                    href="<c:url value="${baseURL}/projects/${entry.key.studyId}"/>"
                                    class="list_more">${entry.key.studyName}</a>
                                <br/>
                                <span class="list_desc"><c:out value="${entry.key.shortStudyAbstract} ..."/></span>
                                <br/><a href="<c:url value="${baseURL}/projects/${entry.key.studyId}"/>"
                                        class="more_view">View more</a> - <a
                                    href="<c:url value="${baseURL}/projects/${entry.key.studyId}"/>#samples_id"
                                    class="list_sample"><c:out value="${entry.value} sample"/><c:if
                                    test='${entry.value > 1}'>s</c:if></a>
                            </p>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                <p>
                    <a href="<c:url value="${baseURL}/projects/doSearch?search=Search&studyVisibility=ALL_PUBLISHED_PROJECTS"/>"
                       title="View all public projects" class="all">All public projects</a>
                    <c:if test="${not empty model.myStudiesMap}">
                        <a href="<c:url value="${baseURL}/projects/doSearch?search=Search&studyVisibility=MY_PROJECTS"/>"
                           title="View all my studies" class="all">All my projects</a>
                    </c:if>
                </p>
            </div>

            <div id="list-data-sample" class="grid_9">
                <h2>My Samples</h2>
                <c:choose>
                    <c:when test="${empty model.mySamples}">
                        <p>
                            No samples submitted
                        </p>
                    </c:when>
                    <c:otherwise>
                        <h3>My latest samples (Total: <a
                                href="<c:url value="${baseURL}/samples/doSearch?searchTerm=&sampleType=&sampleVisibility=MY_SAMPLES&search=Search&startPosition=0"/>"
                                title="View all ${model.mySamplesCount} my samples">${model.mySamplesCount}</a>)</h3>
                        <%--The count starts at 0, that is why we subtract 1 from the end value--%>
                        <c:forEach var="sample" items="${model.mySamples}" varStatus="status" begin="0"
                                   end="${model.maxRowNumberOfLatestItems-1}">
                            <p><%--<span class="list_date">${sample.metadataReceived}:</span>--%>
                                <c:if test="${!sample.public}"><img alt="private"
                                                                    src="${pageContext.request.contextPath}/img/icon_priv_private.gif"></c:if>&nbsp;&nbsp;
                                <a href="<c:url value="${baseURL}/projects/${sample.study.studyId}/samples/${sample.sampleId}"/>"
                                   class="list_more fl_uppercase_title">${sample.sampleName}</a>
                                <span class="list_desc"><c:out value="${sample.shortSampleDescription} ..."/></span>
                                <br/>
                                <a href="<c:url value="${baseURL}/projects/${sample.study.studyId}/samples/${sample.sampleId}"/>" class="more_view">View more</a>
                            </p>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                <p>
                    <a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=&sampleVisibility=ALL_PUBLISHED_SAMPLES&search=Search&startPosition=0"/>"
                       title="View all public samples" class="all">All public samples</a>
                    <c:if test="${not empty model.mySamples}">
                        <a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=&sampleVisibility=MY_SAMPLES&search=Search&startPosition=0"/>"
                           title="View all my samples" class="all">All my samples</a>
                    </c:if>
                </p>
            </div>
        </c:when>
        <%-- End of show MyStudies and MySamples tables--%>

        <%-- Show recent PublicStudies and PublicSamples only when the user is not logged in --%>
        <c:otherwise>
            <div id="list-data-study" class="grid_9 alpha">
                <h2>Projects</h2>

                <h3>Latest public projects (Total: <a
                        href="<c:url value="${baseURL}/projects/doSearch?search=Search&studyVisibility=ALL_PUBLISHED_PROJECTS"/>"
                        title="View all ${model.publicStudiesCount} public projects">${model.publicStudiesCount}</a>)
                </h3>
                    <%--The count starts at 0, that is why we subtract 1 from the end value--%>
                <c:forEach var="study" items="${model.studies}" varStatus="status" begin="0"
                           end="${model.maxRowNumberOfLatestItems-1}">
                    <p><div class="biome_icon icon_xs default_b" title="Biome icon"></div>
                        <a href="<c:url value="${baseURL}/projects/${study.studyId}"/>"
                           class="list_more fl_uppercase_title">${study.studyName}</a>

                        <span class="list_desc"><c:out value="${study.shortStudyAbstract} ..."/></span>
                        <br/>
                        <a href="<c:url value="${baseURL}/projects/${study.studyId}"/>"
                           class="more_view">View more</a> - <a
                                href="<c:url value="${baseURL}/projects/${study.studyId}"/>#samples_id"
                                class="list_sample"><c:out value="${study.sampleSize} sample"/><c:if
                                test='${study.sampleSize > 1}'>s</c:if></a>
                    </p>
                </c:forEach>
                <p>
                    <a href="<c:url value="${baseURL}/projects/doSearch?search=Search&studyVisibility=ALL_PUBLISHED_PROJECTS"/>"
                       title="View all public projects" class="all">View all projects</a></p>
            </div>

            <div id="list-biomes" class="grid_9"> <h2>Browse by selected biomes</h2>

                <div class="grid_24">
                    <div class="grid_8 alpha"><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&biome=SOIL"/>"
                                                 title="View all soil projects"><span class="biome_icon icon_sm soil_b"></span><span class="biome_text">Soil (<c:out value="${model.biomeMap['Soil']}"/>)</span></a></div>

                    <div class="grid_8"><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&biome=MARINE"/>"
                                           title="View all marine projects"><span class="biome_icon icon_sm marine_b"></span><span class="biome_text">Marine (<c:out value="${model.biomeMap['Marine']}"/>)</span></a></div>

                    <div class="grid_8 omega"><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&biome=FOREST_SOIL"/>"
                                                 title="View all forest projects"><span class="biome_icon icon_sm forest_b"></span><span class="biome_text">Forest (<c:out value="${model.biomeMap['Forest Soil']}"/>)</span></a></div>

                    <div class="grid_8 alpha"><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&biome=FRESHWATER"/>"
                                                 title="View all freshwater projects"><span class="biome_icon icon_sm freshwater_b"></span><span class="biome_text">Freshwater (<c:out value="${model.biomeMap['Freshwater']}"/>)</span></a></div>

                    <div class="grid_8"><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&biome=GRASSLAND"/>"
                                           title="View all grassland projects"><span class="biome_icon icon_sm grassland_b"></span><span class="biome_text">Grassland (<c:out value="${model.biomeMap['Grassland Soil']}"/>)</span></a></div>

                    <div class="grid_8 omega"><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&biome=HUMAN_GUT"/>"
                                                 title="View all  human gut projects"><span class="biome_icon icon_sm human_gut_b"></span><span class="biome_text">Human gut (<c:out value="${model.biomeMap['Human gut']}"/>)</span></a></div>

                    <div class="grid_8 alpha"><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&biome=ENGINEERED"/>"
                                                 title="View all engineered projects"><span class="biome_icon icon_sm engineered_b"></span><span class="biome_text">Engineered (<c:out value="${model.biomeMap['Engineered']}"/>)</span></a></div>

                    <div class="grid_8"><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&biome=AIR"/>"
                                           title="View all air projects"><span class="biome_icon icon_sm air_b"></span><span class="biome_text">Air (<c:out value="${model.biomeMap['Air']}"/>)</span></a></div>

                    <div class="grid_8 omega"><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&biome=WASTEWATER"/>"
                                                 title="View all  wastewater projects"><span class="biome_icon icon_sm wastewater_b"></span><span class="biome_text">Wastewater (<c:out value="${model.biomeMap['Wastewater']}"/>)</span></a></div>

                    <%--<div class="grid_8 alpha"><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&biome=HUMAN-HOST"/>"--%>
                                                           <%--title="View all soil projects"><span class="biome_icon icon_sm human_host_b"></span><span class="biome_text">Human (9)</span></a></div>--%>
                                        <%--<div class="grid_8"><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&biome=NON-HUMAN-HOST"/>"--%>
                                                                               <%--title="View all marine projects"><span class="biome_icon icon_sm non_human_host_b"></span><span class="biome_text">Host-associated non-human (24)</span></a></div>--%>
                                        <%--<div class="grid_8 omega"><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&studyVisibility=ALL_PUBLISHED_PROJECTS"/>"--%>
                                                                               <%--title="View all forest projects"><span class="biome_icon icon_sm default_b"></span><span class="biome_text">All biomes (3)</span></a></div>--%>

                </div>
                <p><a href="<c:url value="${baseURL}/projects/doSearch?search=Search&studyVisibility=ALL_PUBLISHED_PROJECTS"/>"
                                                        title="View projects and filter by biome" class="all">View all biomes</a></p>
            </div>

        </c:otherwise>
    </c:choose>
    <%@ include file="components/listNewsComponent.jsp" %>
</section>

<!-- script for carousel - only homepage-->
<script src="${pageContext.request.contextPath}/js/jquery.carousel.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function () {
    $("div.carousel").carousel({pagination:true, autoSlide:true, autoSlideInterval:15000, delayAutoSlide:2000, loop:true });

});
</script>
<!-- End script for carousel-->

<%--Client-side twitter news feed - http://tweet.seaofclouds.com/ - only homepage--%>
<script src="${pageContext.request.contextPath}/js/tweet/jquery.tweet.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/tweet/tweet.instance.js" type="text/javascript"></script>