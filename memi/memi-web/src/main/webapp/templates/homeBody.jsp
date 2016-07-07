<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<c:choose>
    <c:when test="${not empty model.submitter}">
        <section class="jumbo-header private-area">
            <div class="close_section anim"><span class="show_tooltip" title="Close this section" id="this_close">&#10006;</span>
            </div>
                <%--<div class="close_section anim"><span class="icon icon-functional" data-icon="x" title="Close this section" id="this_close"></span></div>--%>
            <div class="mega-lock"><span class="icon icon-functional" data-icon="L"></span></div>
            <p class="lead">Welcome to your private area.<br/>You can track here data you submitted to us <br/>and
                visualize the corresponding results </p>
            <div class="button"><a href="<c:url value="${baseURL}/submission"/>"> Submit data</a></div>
        </section>

        <section class="jumbo-user">
            <div class="jumbo-user-box">

                <div class="output_form">

                    <div class="result_row">
                        <div class="result_row_label">Name:</div>
                        <div class="result_row_data">${model.submitter.firstName} ${model.submitter.surname}</div>
                    </div>

                    <div class="result_row">
                        <div class="result_row_label">Webin account:</div>
                        <div class="result_row_data lowercase">${model.submitter.loginName}</div>
                    </div>

                    <div class="result_row">
                        <div class="result_row_label">Email:</div>
                        <div class="result_row_data lowercase">${model.submitter.emailAddress}</div>
                    </div>

                    <c:if test="${not empty model.submitter.centreName}">
                        <div class="result_row">
                            <div class="result_row_label">Institute:</div>
                            <div class="result_row_data">${model.submitter.centreName}</div>
                        </div>
                    </c:if>

                    <div class="result_row">
                        <div class="result_row_label">Registered with us:</div>
                        <div class="result_row_data lowercase"><c:choose><c:when
                                test="${model.submitter.registered}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose></div>
                    </div>
                    <div class="result_row">
                        <div class="result_row_label">Consent given:</div>
                        <div class="result_row_data lowercase"><c:choose><c:when
                                test="${model.submitter.consentGiven}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose></div>
                        &nbsp;

                    </div>
                    <div class="result_row">
                        <div class="result_row_label"><a href="https://www.ebi.ac.uk/ena/submit/sra/#home"
                                                         title="Edit my user profile" class="icon icon-functional"
                                                         data-icon="e"></a></div>
                    </div>
                </div>


            </div>
        </section>
    </c:when>
    <c:otherwise>
        <section class="jumbo-header">
            <div class="close_section anim"><span class="show_tooltip" title="Close this section" id="this_close">&#10006;</span>
            </div>
            <p class="lead">Submit, analyse, visualize and compare your data.</p>
            <div class="button"><a href="<c:url value="${baseURL}/submission"/>"> Submit data</a></div>
        </section>
    </c:otherwise>
</c:choose>

<%--<c:forEach var="experimentCountEntry"--%>
<%--items="${model.experimentCountMap}">--%>
<%--${experimentCountEntry.key}: --%>
<%--${experimentCountEntry.value}--%>
<%--</br>--%>
<%--</c:forEach>--%>
<c:choose>
    <c:when test="${not empty model.submitter}">
        <!-- no stats for private area unless someone makes it work-->
    </c:when>
    <c:otherwise>
        <section class="jumbo-stats">
            <div class="grid_24 jumbo-stats-container">
                <div class="grid_6 alpha jumbo-stats-box" id="stat-submissions">
                    <!-- <span class="icon icon-functional" data-icon="D"></span>--><span class="icon icon-generic"
                                                                                          data-icon="D"></span><br/><span
                        class="high_nb">${model.numOfDataSets}</span> data sets
                </div>
                <div class="grid_6  jumbo-stats-box" id="stat-exptype">
                    <div class="grid_24">
                        <div class="grid_6 alpha"><span class="icon icon-conceptual icon-c1" data-icon="d"></span></div>
                        <div class="grid_6">
                            <c:forEach var="experimentCountEntry"
                                       items="${model.experimentCountMap}">
                                <span class="high_nb">${experimentCountEntry.value}</span><br/>
                            </c:forEach>
                        </div>
                        <div class="grid_12 omega">
                            <c:forEach var="experimentCountEntry" items="${model.experimentCountMap}">
                                ${experimentCountEntry.key}
                                <c:choose>
                                    <c:when test="${experimentCountEntry.key == 'metatranscriptomes'}">
                                    </c:when>
                                    <c:otherwise>
                                        <br/>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach></div>
                    </div>
                </div>
                    <%--<div class="grid_3 jumbo-stats-box" id="stat-member"><span class="icon icon-generic" data-icon="}"></span><br/><span class="high_nb">${model.numOfSubmitters}</span> members </div>--%>
                <div class="grid_6 jumbo-stats-box" id="stat-public">
                    <div class="grid_24">
                        <div class="grid_6 alpha"><span class="icon icon-functional" data-icon="U"></span><br/><span
                                class="stat-lock-title">Public</span></div>
                        <div class="grid_6"><span class="high_nb">${model.dataStatistics.numOfPublicRuns}</span><br/><span
                                class="high_nb">${model.dataStatistics.numOfPublicSamples}</span><br/><span
                                class="high_nb">${model.dataStatistics.numOfPublicStudies}</span></div>
                        <div class="grid_12 omega"> runs <br/> samples <br/> projects</div>
                    </div>
                </div>
                <div class="grid_6 jumbo-stats-box omega" id="stat-private">
                    <div class="grid_24">
                        <div class="grid_6 alpha"><span class="icon icon-functional" data-icon="L"></span><br/><span
                                class="stat-lock-title">Private</span></div>
                        <div class="grid_6"><span class="high_nb">${model.dataStatistics.numOfPrivateRuns}</span><br/><span
                                class="high_nb">${model.dataStatistics.numOfPrivateSamples}</span><br/><span
                                class="high_nb">${model.dataStatistics.numOfPrivateStudies}</span></div>
                        <div class="grid_12 omega"> runs <br/> samples <br/> projects</div>
                    </div>
                </div>
            </div>
        </section>
    </c:otherwise>
</c:choose>
<%--Caroussel--%>
<%--<!--[if IE 6]><div id="IE6" class="IE"><![endif]--> <!--[if IE 7]><div id="IE7" class="IE"><![endif]--> <!--[if IE & ((!IE 6) & (!IE 7))]><div><![endif]--> <!--[if !IE]>--><div><!--<![endif]-->--%>
<%--<div class="carousel">--%>

<%--<ul>--%>
<%--<li>--%>
<%--<!-- module 1 -->--%>
<%--<div class="module" id="mod1">--%>
<%--<div class="top_corners"></div>--%>
<%--<div class="content">--%>
<%--<h3>Easy submission</h3>--%>

<%--<div class="cent"><img src="${pageContext.request.contextPath}/img/icons_sub.png"--%>
<%--alt="easy submission"/></div>--%>
<%--<p>Manually supported submission process, with help available for--%>
<%--meta-data provision. Accepted data formats include SFF (454) and FASTQ (Illumina and--%>
<%--IonTorrent).<br/></p>--%>

<%--<div class="find_more"><a href="${pageContext.request.contextPath}/about#features_1"--%>
<%--title="find out more about easy submission"><span>Find out more</span></a>--%>
<%--</div>--%>
<%--</div>--%>
<%--<div class="bottom_corners"></div>--%>
<%--</div>--%>
<%--</li>--%>
<%--<li>--%>
<%--<!-- module 2 -->--%>
<%--<div class="module" id="mod2">--%>
<%--<div class="top_corners"></div>--%>
<%--<div class="content">--%>
<%--<h3>Powerful analysis</h3>--%>

<%--<div class="cent"><img src="${pageContext.request.contextPath}/img/icons_ana.png"--%>
<%--alt="powerful analysis"/></div>--%>
<%--<p>Functional analysis of metagenomic sequences using InterPro - a powerful and--%>
<%--sophisticated alternative to BLAST-based analyses. Taxonomy diversity analysis is--%>
<%--performed using Qiime. <br/></p>--%>

<%--<div class="find_more"><a href="${pageContext.request.contextPath}/about#features_2"--%>
<%--title="find out more about analysis"><span>Find out more</span></a>--%>
<%--</div>--%>
<%--</div>--%>
<%--<div class="bottom_corners"></div>--%>
<%--</div>--%>
<%--</li>--%>
<%--<li>--%>
<%--<!-- module 3 -->--%>
<%--<div class="module" id="mod3">--%>
<%--<div class="top_corners"></div>--%>
<%--<div class="content">--%>
<%--<h3>Data archiving</h3>--%>

<%--<div class="cent"><img src="${pageContext.request.contextPath}/img/icons_arc.png"--%>
<%--alt="data archiving"/></div>--%>
<%--<p>Data automatically archived at the European Nucleotide Archive (ENA), ensuring accession--%>
<%--numbers are supplied - a prerequisite for publication in many journals.</p>--%>

<%--<div class="find_more"><a href="${pageContext.request.contextPath}/about#features_3"--%>
<%--title="find out more about data archiving"><span>Find out more</span></a>--%>
<%--</div>--%>
<%--</div>--%>
<%--<div class="bottom_corners"></div>--%>
<%--</div>--%>
<%--</li>--%>
<%--<li>--%>
<%--<!-- module 4 -->--%>
<%--<div class="module" id="mod4">--%>
<%--<div class="top_corners"></div>--%>
<%--<div class="content">--%>
<%--<h3>Submit your data</h3>--%>

<%--<div class="cent"><img src="${pageContext.request.contextPath}/img/icons_submit.png"--%>
<%--alt=""--%>
<%--width="71" height="71"/></div>--%>
<%--&lt;%&ndash;</c:url>&ndash;%&gt;--%>
<%--<p style="padding-bottom:7px; "> You can click on <a href="<c:url value="${baseURL}/submit"/>"--%>
<%--title="Submit data">Submit data</a>--%>
<%--to send us your nucleotide sequences for analysis.--%>

<%--(Note: you will need to  <a  href="https://www.ebi.ac.uk/ena/submit/sra/#metagenome_registration"--%>
<%--title="Click here to register for a new ENA submitter account">register</a>--%>
<%--first). </p>--%>
<%--<c:choose>--%>
<%--<c:when test="${empty model.submitter}">--%>
<%--<div class="find_more"><a href="<c:url value="${baseURL}/submitData"/>"--%>
<%--title="submit data for analysis"><span>Submit your data</span></a>--%>
<%--</div>--%>
<%--</c:when>--%>
<%--<c:otherwise>--%>
<%--<div class="find_more"><a href="<c:url value="${baseURL}/submit"/>"--%>
<%--title="submit data for analysis"><span>Submit your data</span></a>--%>
<%--</div>--%>
<%--</c:otherwise>--%>
<%--</c:choose>--%>

<%--</div>--%>
<%--<div class="bottom_corners"></div>--%>
<%--</div>--%>
<%--</li>--%>
<%--</ul>--%>
<%--</div>--%>
<%--</div>--%>


<section class="list-data">
    <div class="grid_24 jumbo-list-data-container">

        <c:choose>
        <c:when test="${not empty model.submitter && empty model.myStudiesMap && empty model.mySamples}">
            <!-- No project or sample have been analysed -->
            <p class="msg_error">
                No projects or samples processed - If you have already submitted your data to ENA, then there is a small
                time lag (a few days) before they appear in this section as they must first be added to the ENA
                database. If you have not yet submitted your data, you can do this directly using <a class="ext"
                                                                                                     title="Click here to submit data to ENA"
                                                                                                     href="https://www.ebi.ac.uk/ena/submit/sra/#home">the
                ENA Webin tool</a>.
            </p>
        </c:when>
        <c:otherwise>
        <!-- Some project or sample have been analysed -->
        <h1>Browse projects</h1>
        <c:choose>
            <%-- Show MyStudies and MySamples tables only if a user is logged in--%>
            <c:when test="${not empty model.submitter}">
                <div class="list-contain-box grid_12 alpha">
                    <div class="home_box alpha">
                        <h2>My latest projects <span class="badge"><a
                                href="<c:url value="${baseURL}/projects/doSearch?searchTerm=&studyVisibility=MY_PROJECTS&search=Search"/>"
                                title="View all ${model.myStudiesCount} my projects">${model.myStudiesCount}</a></span>
                        </h2>

                        <div class="list-project-l">
                            <c:forEach var="study" items="${model.studies}" varStatus="status">
                                <div class="list-item">
                                    <div class="list-title">
                                        <div class="biome_icon icon_xs ${study.biomeIconCSSClass}"
                                             title="${study.biomeIconTitle} biome"></div>
                                        <a href="<c:url value="${baseURL}/projects/${study.studyId}"/>"
                                           class="list_more fl_uppercase_title">${study.studyName}</a>
                                    </div>
                                    <div class="list-body">
                                        <p class="list-desc"><c:out value="${study.shortStudyAbstract} ..."/></p>
                                        <p class="list-more">
                                            <a href="<c:url value="${baseURL}/projects/${study.studyId}"/>"
                                               class="more_view">View more</a> - <a
                                                href="<c:url value="${baseURL}/projects/${study.studyId}"/>#samples_id"
                                                class="list_sample"><c:out
                                                value="${model.studyToSampleCountMap[study.studyId]} sample"/><c:if
                                                test='${model.studyToSampleCountMap[study.studyId] > 1}'>s</c:if></a>
                                            <c:choose>
                                                <c:when test="${study.public}">
                                                    <span class="show_tooltip icon icon-functional" data-icon="U"
                                                          title="Public data"></span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="show_tooltip icon icon-functional" data-icon="L"
                                                          title="Private data"></span>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:if test='${model.studyToRunCountMap[study.studyId] > 1}'>- <a
                                                    href="<c:url value="${baseURL}/compare&#35${study.studyId}"/>"
                                                    title="Compare samples in this project" class="list_sample"> <span
                                                    class="icon icon-functional" data-icon="O"></span>
                                                compare</a></c:if>
                                        </p>
                                    </div>
                                </div>
                            </c:forEach>
                        </div><!-- /list-project-l -->

                        <!-- <a href="<c:url value="${baseURL}/projects/doSearch?search=Search&studyVisibility=ALL_PUBLISHED_PROJECTS"/>"
                            title="View all public projects" class="all">All public projects</a>  -->
                        <c:if test="${not empty model.myStudiesMap}">
                            <p>
                                <a href="<c:url value="${baseURL}/projects/doSearch?search=Search&studyVisibility=MY_PROJECTS"/>"
                                   title="View all my studies" class="all">All my projects</a></p>
                        </c:if>

                    </div>
                </div>

                <div class="list-contain-box grid_12 omega">
                    <div class="home_box omega">
                        <h2>My latest Samples <span class="badge"><a
                                href="<c:url value="${baseURL}/samples/doSearch?searchTerm=&sampleType=&sampleVisibility=MY_SAMPLES&search=Search&startPosition=0"/>"
                                title="View all ${model.mySamplesCount} my samples">${model.mySamplesCount}</a></span>
                        </h2>

                        <div class="list-sample-l">
                                <%--<h3>My latest samples (Total: <a--%>
                                <%--href="<c:url value="${baseURL}/samples/doSearch?searchTerm=&sampleType=&sampleVisibility=MY_SAMPLES&search=Search&startPosition=0"/>"--%>
                                <%--title="View all ${model.mySamplesCount} my samples">${model.mySamplesCount}</a>)</h3>--%>
                                <%--The count starts at 0, that is why we subtract 1 from the end value--%>

                            <c:forEach var="sample" items="${model.mySamples}" varStatus="status" begin="0"
                                       end="${model.maxRowNumberOfLatestItems-1}">

                                <div class="list-item">
                                    <div class="list-title">
                                            <%--<span class="list_date">${sample.metadataReceived}:</span>--%>
                                        <a href="<c:url value="${baseURL}/projects/${sample.study.studyId}/samples/${sample.sampleId}"/>"
                                           class="list_more fl_uppercase_title">${sample.sampleName}
                                            <c:if test="${!sample.public}"><span
                                                    class="show_tooltip icon icon-functional" data-icon="L"
                                                    title="Private data"></span></c:if>
                                            <c:if test="${sample.public}"><span
                                                    class="show_tooltip icon icon-functional" data-icon="U"
                                                    title="Public data"></span></c:if>
                                        </a>
                                    </div>
                                    <div class="list-body">
                                        <p class="list-desc"><c:out value="${sample.shortSampleDescription} ..."/></p>
                                        <p class="list-more"><a
                                                href="<c:url value="${baseURL}/projects/${sample.study.studyId}/samples/${sample.sampleId}"/>"
                                                class="more_view">View more</a></p>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                            <%--<a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=&sampleVisibility=ALL_PUBLISHED_SAMPLES&search=Search&startPosition=0"/>"--%>
                            <%--title="View all public samples" class="all">All public samples</a>--%>
                        <c:if test="${not empty model.mySamples}">
                            <p>
                                <a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=&sampleVisibility=MY_SAMPLES&search=Search&startPosition=0"/>"
                                   title="View all my samples" class="all">All my samples</a></p>
                        </c:if>

                    </div>
                </div>
            </c:when>
            <%-- End of show MyStudies and MySamples tables--%>

            <%-- Show recent PublicStudies and PublicSamples only when the user is not logged in --%>
            <c:otherwise>

                <!-- biomes box - strechy to resize sprite - need to keep the image in the page + spacer (no background)-->
                <div id="list-biomes" class="grid_12 alpha">
                    <div class="home_box alpha">
                        <h2>By selected biomes</h2>

                        <div class="grid_24">
                            <c:forEach var="biome" items="${model.biomeMap}" varStatus="status">
                                <c:set var="cssClass" value="anim"/>
                                <c:if test="${status.last}">
                                    <c:set var="cssClass" value="anim xs_hide"/>
                                </c:if>
                                <a class="${cssClass}"
                                   href="<c:url value="${baseURL}/projects/doSearch?search=Search&includingChildren=true&biomeLineage=${biome.lineage}"/>"
                                   title="View all <c:out value="${biome.numberOfProjects}"/> ${biome.label} projects">
                                    <div class="list-biomes-element">
                                        <div class="stretchy">
                                            <img class="spacer" alt="icon"
                                                 src="<c:url value="${baseURL}/img/backgrounds/bgd_transparent_1x1.png"/>">
                                            <img class="sprite ${biome.css}"
                                                 src="<c:url value="${baseURL}/img/ico_biome_sprite.png"/>">
                                        </div>
                                        <div class="biome_text">${biome.label} (<c:out value="${biome.numberOfProjects}"/>)</div>
                                    </div>
                                </a>
                            </c:forEach>
                        </div><!-- /list-biomes-l -->
                        <a href="<c:url value="${baseURL}/biomes"/>"
                           title="View projects and filter by biome" class="all">Browse all biomes</a>
                    </div> <!-- /home_box -->
                </div>
                <!-- /list-biomes -->

                <!-- Project list box -->
                <div class="list-contain-box grid_12 omega">

                    <div class="home_box omega">
                        <h2>Latest projects <span class="badge"><a
                                href="<c:url value="${baseURL}/projects/doSearch?search=Search&studyVisibility=ALL_PUBLISHED_PROJECTS"/>"
                                title="View all ${model.dataStatistics.numOfPublicStudies} public projects">${model.dataStatistics.numOfPublicStudies}</a></span>
                        </h2>
                        <div class="list-project-l">
                                <%--The count starts at 0, that is why we subtract 1 from the end value--%>
                            <c:forEach var="study" items="${model.studies}" varStatus="status">

                                <div class="list-item">
                                    <div class="list-title">
                                        <div class="biome_icon icon_xs ${study.biomeIconCSSClass}"
                                             title="${study.biomeIconTitle} biome"></div>
                                        <a href="<c:url value="${baseURL}/projects/${study.studyId}"/>"
                                           class="list_more fl_uppercase_title">${study.studyName}</a>
                                    </div>
                                    <div class="list-body">
                                        <p class="list-desc"><c:out value="${study.shortStudyAbstract} ..."/></p>
                                        <p class="list-more">
                                            <a href="<c:url value="${baseURL}/projects/${study.studyId}"/>"
                                               class="more_view">View more</a> - <a
                                                href="<c:url value="${baseURL}/projects/${study.studyId}"/>#samples_id"
                                                class="list_sample"><c:out
                                                value="${model.studyToSampleCountMap[study.studyId]} sample"/><c:if
                                                test='${model.studyToSampleCountMap[study.studyId] > 1}'>s</c:if></a>
                                            <c:if test='${model.studyToRunCountMap[study.studyId] > 1}'>- <a
                                                    href="<c:url value="${baseURL}/compare&#35${study.studyId}"/>"
                                                    title="Compare samples in this project" class="list_sample"> <span
                                                    class="icon icon-functional" data-icon="O"></span>
                                                compare</a></c:if>
                                        </p>
                                    </div>
                                </div>
                            </c:forEach>
                        </div><!-- /list-project-l -->


                        <a href="<c:url value="${baseURL}/projects/doSearch?search=Search&studyVisibility=ALL_PUBLISHED_PROJECTS"/>"
                           title="View all public projects" class="all">View all projects</a>
                    </div> <!-- /home_box -->
                </div>
                <!-- /Project list box -->


            </c:otherwise>
        </c:choose>

    </div><!-- /jumbo-list-data-container-->
    </c:otherwise>
    </c:choose>

</section>

<c:choose>
    <c:when test="${not empty model.submitter}"> <!-- private-->
        <section class="hlight_item">
            <div class="grid_24">
                <div id="hlight-box-spotlight" class="grid_12 alpha hlight-info">
                    <div class="hlight_title_cont">
                        <div class="hlight_maintitle"><h1>Tracking data submission</h1></div>
                        <div class="hlight_subtitle" style="margin-top:0;"><h2 class="icon icon-generic"
                                                                               data-icon="i"></h2></div>
                    </div>
                    <div class="home_box alpha"><p><img src="${pageContext.request.contextPath}/img/ico_sub_big.png"
                                                        alt="Tracking submission"/> EBI Metagenomics service offers a
                        manually-assisted submission route, with help available to ensure data and metadata formatting
                        comply with the European Nucleotide Archive (ENA) data schema and the Genomic Standards
                        Consortium (GSC) sample metadata guidelines.</p>
                        <p>You may find delays between the moment you submit your data in ENA and the moment you will
                            find them available in this private section of the EBI metagenomics. It doesn't necessarily
                            means there is something wrong with your data. We just need some time to get your data
                            analysed (usually 1-2 weeks). It needs to go through the analysis pipeline before it will
                            appear in this private area.
                        </p>
                        <p>If you have been waiting for more than a month, you are welcome to <a
                                href="mailto:metagenomics-help@ebi.ac.uk?subject=EBI Metagenomics private area: data submission status">contact
                            us to get some update</a>.</p>
                    </div>
                </div>
                <div id="hlight-box-spotlight" class="grid_12 alpha hlight-info">
                    <div class="hlight_title_cont">
                        <div class="hlight_maintitle"><h1>Data confidentiality</h1></div>
                        <div class="hlight_subtitle" style="margin-top:0;"><h2 class="icon icon-generic"
                                                                               data-icon="i"></h2></div>
                    </div>
                    <div class="home_box alpha"><p><img src="${pageContext.request.contextPath}/img/ico_conf_big.png"
                                                        alt="Data archiving"/> Data submitted to us can be kept
                        confidential (and only accessible after secure user login) to allow the data producer to publish
                        their findings. It should be noted that ALL data must eventually be suitable for public release.
                    </p>
                        <p>The default is two years but you can set it up for any length suitable to you in the ENA
                            Webin submission tools. Upon analysis, result data will be posted on our website however, if
                            private, only you will be able to access them as login will be necessary for access.
                        </p>
                        <p>Once you published the results, you can <a href="https://www.ebi.ac.uk/ena/submit/sra/#home"
                                                                      title="Reset release date"> reset the date for
                            release </a> or we can do it for you if you <a
                                href="mailto:metagenomics-help@ebi.ac.uk?subject=EBI Metagenomics private area: reset release date">sent
                            us an email</a>.
                        </p></div>
                </div>
            </div>
        </section>
    </c:when>
    <c:otherwise>


        <section class="hlight_item">
            <div class="grid_24">
                <div id="hlight-box-spotlight" class="grid_12 alpha hlight-spot">
                    <div class="hlight_title_cont">
                        <div class="hlight_maintitle"><h1>Spotlight</h1></div>
                        <div class="hlight_subtitle"><h2>American Gut project</h2></div>
                    </div>
                    <div class="home_box alpha">
                        <div class="hlight-text-scroll">
                        <img alt="American Gut Study Logo"
                             src="<c:url value="${baseURL}/img/pict_home_american_gut_sm.jpg"/>" style="max-width:50%;float:left;margin:18px;"/>
                        <p/>The microbial population (or microbiome) of the human gut is involved in a wide range of important processes, such as digestion, production of vitamins and other nutrients, detoxification, protection from pathogens, and helping to shape the host immune system. Gut microbial communities represent substantial reservoirs of genetic and metabolic diversity: different people have different types of microorganisms in their gut, and community composition can change over time or with diet.

                        <p/>The importance of the gut microbiota in health and disease is becoming increasingly recognised. Microbiome composition seems to be vital in maintaining a healthy gut, and community perturbance has been associated with a wide range of diseases, including obesity, diabetes, IBS and cancer. In order to better understand such associations, and to work out what constitutes normal or unhealthy gut populations, it is important to analyse samples from a broad range of people, with a variety of ages, diets and lifestyles.

                        <p/>American Gut (<a href="http://americangut.org">americangut.org</a>) is an extensive crowd-sourced citizen science project, aiming to shed light on the connections between human microbiome and health. For a small monetary contribution (covering sample processing, sequencing and analysis costs), the organisers mail volunteers a dietary and lifestyle questionnaire and sample collection kit. Samples and questionnaires are then sent back for analysis. The project also contains samples and data from a related project in the UK, "The British Gut" (<a href="http://britishgut.org/">britishgut.org</a>).

                        <p/>Once received by the laboratory, samples are anonymised, DNA is extracted and microbial 16S rRNA genes are amplified, sequenced and analysed to help reveal gut community composition. The analysis results can be accessed via the American Gut website, and the anonymised sequence sets are also submitted to public sequence data archives, such as the European Nucleotide Archive (ENA), for reuse and re-analysis by the scientific community.

                        <p/>EBI Metagenomics has analysed the 16S rRNA gene amplicon sequence data for over 8,000 samples from the American Gut project (ERP012803) that have been deposited with the ENA to date. The taxonomic analysis for each sequencing run can be visualised on (or downloaded from) the EBI Metagenomics web site (for example, <a href="${baseURL}/metagenomics/projects/ERP012803/samples/ERS915520/runs/ERR1073439/results/versions/2.0">ERR1073439</a>). Users can also download the whole data set, summarised at both the phylum and full taxonomic classification level, as results matrix files (<a href="${baseURL}/metagenomics/projects/ERP012803">ERP012803</a>).

                        </div>
                        <a href="<c:url value="${baseURL}/projects/ERP012803"/>"
                                                   title="View American Gut project" class="all">Look at the data</a>
                    </div>
                    <!--
                    <div class="hlight_title_cont">
                        <div class="hlight_maintitle"><h1>Spotlight</h1></div>
                        <div class="hlight_subtitle"><h2>TARA ocean project</h2></div>
                    </div>
                    <div class="home_box alpha">
                        <img alt="Tara Oceans expedition map"
                             src="<c:url value="${baseURL}/img/pict_home_taramap_590.png"/>" style="max-width:590px;"/>
                        <p>Plankton ecosystems contain a phenomenal reservoir of life: more than 10 billion organisms
                            inhabit every litre of oceanic water, including viruses, prokaryotes, unicellular eukaryotes
                            (protists), and metazoans.

                            Plankton&#39;s importance for the earth&#39;s climate is at least equivalent to that of the
                            rainforest. Yet only a small fraction of organisms that compose it have been classified and
                            analysed.</p>

                        <p>Tara Oceans expedition, led by EMBL senior scientist Eric Karsenti, has been travelling
                            around the world (2009-13) collecting over 35,000 ocean samples containing millions of small
                            organism collected in more than 210 ocean stations. <a
                                    href="http://www.embl.de/tara-oceans/start/" class="ext"> More about Tara</a></p>

                        <a href="<c:url value="${baseURL}/projects/ERP001736"/>"
                           title="View Tara project" class="all">Look at the data</a>
                    </div>
                    -->
                </div>
                <div id="hlight-box-tools" class="grid_12 omega hlight-spot">
                    <div class="hlight_title_cont">
                        <div class="hlight_maintitle"><h1>Tools</h1></div>
                        <div class="hlight_subtitle"><h2>Functional sample comparison</h2></div>
                    </div>
                    <div class="home_box omega">
                        <div class="hlight-text-scroll">
                        <img alt="Example of Go terms analysis result page"
                             src="<c:url value="${baseURL}/img/pict_home_comparison_566.png"/>"
                             style="max-width: 566px;"/>
                        <p>Interested in comparing the functional profile of sequencing runs within a project? Now it is
                            possible, using our comparison tool, which provides analysis based on a slimmed-down subset
                            of Gene Ontology (GO) terms, specially developed to describe metagenomic data. </p>
                        <p>You can visualise the results using a range of interactive charts (bar charts, stacked
                            columns, Principal Component Analysis and heatmaps).
                            The different charts can be exported in PNG, PDF and SVG formats, so that they can easily be
                            included in presentations and publications.
                        </p>
                        </div>
                        <a href="<c:url value="${baseURL}/compare"/>" title="Compare samples" class="all">Compare
                                                  samples</a>
                    </div>
                </div>
            </div>
            <div class="grid_24">
                <div class="alpha omega hlight-spot">
                    <div class="hlight_title_cont">
                        <div class="hlight_subtitle"><h2>How to cite us</h2></div>
                    </div>
                    <div class="home_box alpha omega" style="min-height: auto;">
                        <div>
                            <img
                                alt="Cover of the Nucleic Acids Research journal"
                                src="<c:url value="${baseURL}/img/nucleic_acids_research_D1_cover.gif"/>"
                                style="width: auto; float: left; margin-right: 18px;"
                            />
                            <p>
                                To cite EBI Metagenomics, please refer to the following publication:
                            </p>
                            <p>
                              Alex Mitchell, Francois Bucchini, Guy Cochrane, Hubert Denise, Petra ten Hoopen, Matthew Fraser, Sebastien Pesseat, Simon Potter, Maxim Scheremetjew, Peter Sterk and Robert D. Finn (2015).
                              <br />
                              <strong>
                                  EBI metagenomics in 2016 - an expanding and evolving resource for the analysis and archiving of metagenomic data.
                              </strong>
                              Nucleic Acids Research (2015) doi:
                              <a
                                  title="EBI metagenomics in 2016 - an expanding and evolving resource for the analysis and archiving of metagenomic data"
                                  href="http://nar.oxfordjournals.org/content/44/D1/D595.full"
                                  class="ext"
                              >
                                  10.1093/nar/gkv1195
                              </a>
                            </p>
                        </div>
                        <a href="<c:url value="${baseURL}/about#h_cite"/>" title="About page - How to cite" class="all">
                            More citations
                        </a>
                    </div>
                </div>
            </div>
        </section>

        <section class="jumbo-news">
            <div class="grid_24 jumbo-news-container">
                <div class="jumbo-news-centred">

                    <h3 class="icon icon-socialmedia icon-s2" data-icon="T"></h3>
                        <%--<div class="flexslider">Learn to run a design the box workshop and how it helped us overhaul our training academy: <a href="#">webcredible.com/blog-reports/</a></div>--%>
                    <a class="twitter-timeline" height="100" data-dnt="true" href="https://twitter.com/EBImetagenomics"
                       data-widget-id="345516657369837568"
                       data-chrome="nofooter noborders noheader noscrollbar transparent" data-tweet-limit="1">Tweets by
                        @EBImetagenomics</a>

                    <p class="jumbo-news-contact"><strong><a href="http://twitter.com/EBImetagenomics"
                                                             alt="Follow us on Twitter">@EBImetagenomics</a></strong>
                    </p>
                    <script>
                        !function (d, s, id) {
                            var
                                    js, fjs = d.getElementsByTagName(s)[0], p = /^http:/.test(d.location) ? 'http' : 'https';
                            if (!d.getElementById(id)) {
                                js = d.createElement(s);
                                js.id = id;
                                js.src = p + "://platform.twitter.com/widgets.js";
                                fjs.parentNode.insertBefore(js, fjs);
                            }
                        }(document, "script", "twitter-wjs");</script>


                </div>
            </div>
        </section>

    </c:otherwise>
</c:choose>

<%--Client-side twitter news feed - http://tweet.seaofclouds.com/ - only homepage--%>
<script src="${pageContext.request.contextPath}/js/tweet/jquery.tweet.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/tweet/tweet.instance.js" type="text/javascript"></script>
<script type="text/javascript">
    //  re-style the twitter component
    $("iframe").ready(function () {
        var timer = setInterval(function () {
            if ($($("iframe").contents()).find(".avatar").length > 0) {
                $($("iframe").contents()).find(".avatar, .p-author, .footer, .retweet-credit").css({display: "none"});
                $($("iframe").contents()).find(".permalink").css({float: "none"});
                $($("iframe").contents()).find(".header, .inline-media").css({'text-align': "center"});
                $($("iframe").contents()).find("li").css({'padding': "0 0 0 0"});
                $($("iframe").contents()).find(".e-entry-title").css({
                    'text-align': "center",
                    'font-size': '157%',
                    'line-height': '1.4'
                });
                $($("iframe").contents()).find("img.autosized-media").css({'max-height': '175px'});
                clearInterval(timer);
            }
        }, 100);
    });
</script>
<script type="text/javascript">
    //close section for the private area
    $('#this_close').click(function (event) {
        $('.jumbo-header').slideToggle();
    });
</script>
