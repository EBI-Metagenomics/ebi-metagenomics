<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:choose>
    <c:when test="${not empty model.submitter}">
        <section class="jumbo-header private-area" style="position: relative;">
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

        <section class="jumbo-header beta-header" style="position: relative;">
            <p class="lead">We are working on a new website!</p>
            <div class="button"><a href="<c:url value="${baseURL}/beta/"/>">Click here to try it out!</a></div>
        </section>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${not empty model.submitter}">
        <!-- no stats for private area unless someone makes it work-->
    </c:when>
    <c:otherwise>
        <section class="jumbo-stats" style="visibility:hidden">
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
                                <span class="high_nb"
                                      id="${experimentCountEntry.key}-statistics">${experimentCountEntry.value}</span><br/>
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
                        <div class="grid_6">
                            <span id="Runs-statistics"
                                  class="high_nb">${model.dataStatistics.runStatistics.numOfPublicRuns}</span><br/>
                            <span id="Samples-statistics"
                                  class="high_nb">${model.dataStatistics.sampleStatistics.numOfPublicSamples}</span><br/>
                            <span id="Projects-statistics"
                                  class="high_nb">${model.dataStatistics.studyStatistics.numOfPublicStudies}</span>
                        </div>
                        <div class="grid_12 omega"> runs <br/> samples <br/> projects</div>
                    </div>
                </div>
                <div class="grid_6 jumbo-stats-box omega" id="stat-private">
                    <div class="grid_24">
                        <div class="grid_6 alpha"><span class="icon icon-functional" data-icon="L"></span><br/><span
                                class="stat-lock-title">Private</span></div>
                        <div class="grid_6"><span
                                class="high_nb">${model.dataStatistics.runStatistics.numOfPrivateRuns}</span><br/><span
                                class="high_nb">${model.dataStatistics.sampleStatistics.numOfPrivateSamples}</span><br/><span
                                class="high_nb">${model.dataStatistics.studyStatistics.numOfPrivateStudies}</span></div>
                        <div class="grid_12 omega"> runs <br/> samples <br/> projects</div>
                    </div>
                </div>
            </div>
        </section>
    </c:otherwise>
</c:choose>

<section class="list-data">
    <div class="grid_24 jumbo-list-data-container">

        <c:choose>
        <c:when test="${not empty model.submitter && empty model.studies && empty model.mySamples}">
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
                                                value="${model.studyToSampleCountMap[study.id]} sample"/><c:if
                                                test='${model.studyToSampleCountMap[study.id] > 1}'>s</c:if></a>
                                            <c:choose>
                                                <c:when test="${study['public']}">
                                                    <span class="show_tooltip icon icon-functional" data-icon="U"
                                                          title="Public data"></span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="show_tooltip icon icon-functional" data-icon="L"
                                                          title="Private data"></span>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:if test="${(fn:contains(model.nonAmpliconStudies, study.studyId)) and (model.studyToRunCountMap[study.studyId] > 1)}">-
                                                <a
                                                        href="<c:url value="${baseURL}/compare&#35${study.studyId}"/>"
                                                        title="Compare samples in this project"
                                                        class="list_sample"> <span
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
                        <h2>My latest samples <span class="badge"><a
                                href="<c:url value="${baseURL}/samples/doSearch?searchTerm=&sampleType=&sampleVisibility=MY_SAMPLES&search=Search&startPosition=0"/>"
                                title="View all ${model.mySamplesCount} my samples">${model.mySamplesCount}</a></span>
                        </h2>

                        <div class="list-sample-l">
                            <c:forEach var="sample" items="${model.mySamples}" varStatus="status" begin="0"
                                       end="${model.maxRowNumberOfLatestItems-1}">

                                <div class="list-item">
                                    <div class="list-title">
                                        <c:forEach var="study" items="${sample.studies}">
                                            <c:if test="${fn:contains(model.studies, study)}">
                                                <a href="<c:url value="${baseURL}/projects/${study.studyId}/samples/${sample.sampleId}"/>"
                                                   class="list_more fl_uppercase_title">${sample.sampleName}
                                                    <c:if test="${!sample['public']}"><span
                                                            class="show_tooltip icon icon-functional" data-icon="L"
                                                            title="Private data"></span></c:if>
                                                    <c:if test="${sample['public']}"><span
                                                            class="show_tooltip icon icon-functional" data-icon="U"
                                                            title="Public data"></span></c:if>
                                                </a>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                    <div class="list-body">
                                        <p class="list-desc"><c:out value="${sample.shortSampleDescription} ..."/></p>
                                        <p class="list-more">
                                            <c:forEach var="study" items="${sample.studies}">
                                                <c:if test="${fn:contains(model.studies, study)}">
                                                    <a href="<c:url value="${baseURL}/projects/${study.studyId}/samples/${sample.sampleId}"/>"
                                                       class="more_view">View more</a>
                                                </c:if>
                                            </c:forEach>
                                        </p>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

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
                <div class="grid_12 alpha list-biomes">
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
                                        <div class="biome_text">${biome.label} (<c:out
                                                value="${biome.numberOfProjects}"/>)
                                        </div>
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
                                title="View all ${model.dataStatistics.studyStatistics.numOfPublicStudies} public projects">${model.dataStatistics.studyStatistics.numOfPublicStudies}</a></span>
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
                                                value="${model.studyToSampleCountMap[study.id]} sample"/><c:if
                                                test='${model.studyToSampleCountMap[study.id] > 1}'>s</c:if></a>
                                            <c:if test="${(fn:contains(model.nonAmpliconStudies, study.studyId)) and (model.studyToRunCountMap[study.studyId] > 1)}">-
                                                <a
                                                        href="<c:url value="${baseURL}/compare&#35${study.studyId}"/>"
                                                        title="Compare samples in this project"
                                                        class="list_sample"> <span
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
                <div class="grid_12 alpha hlight-info hlight-spot">
                    <div class="hlight_title_cont">
                        <div class="hlight_maintitle"><h1>Tracking data submission</h1></div>
                        <div class="hlight_subtitle" style="margin-top:0;"><h2 class="icon icon-generic"
                                                                               data-icon="i"></h2></div>
                    </div>
                    <div class="home_box alpha">
                        <div class="hlight-text-scroll">
                            <p><img src="${pageContext.request.contextPath}/img/ico_sub_big.png"
                                    alt="Tracking submission"/>
                                EBI Metagenomics offers a manually-assisted submission route, with help available to
                                ensure data
                                and metadata formatting comply with the European Nucleotide Archive (ENA) data schema
                                and the
                                Genomic Standards Consortium (GSC) sample metadata guidelines. If you have problems with
                                your submission,
                                please contact <a
                                        href="mailto:datasubs@ebi.ac.uk?subject=EBI Metagenomics private area: data submission status">
                                    datasubs@ebi.ac.uk</a> (for submission related queries) or <a
                                        href="mailto:metagenomics-help@ebi.ac.uk?subject=EBI Metagenomics private area: general query">
                                    metagenomics-help@ebi.ac.uk</a> (for more general queries).</p>
                            <p>Once your data is submitted to ENA, it will be archived and then analysed by the EBI
                                Metagenomics pipeline.
                                This may take some time (typically 1-2 weeks, depending on the data size). You will
                                receive an email when
                                the analysis is complete, and the results will appear in this section of the site.</p>
                            <p>If you have been waiting for more than a month for your results, please <a
                                    href="mailto:metagenomics-help@ebi.ac.uk?subject=EBI Metagenomics private area: submission status">
                                contact us</a> to check the status of the submission/analysis.</p>
                        </div>
                    </div>
                </div>
                <div class="grid_12 omega hlight-info hlight-spot">
                    <div class="hlight_title_cont">
                        <div class="hlight_maintitle"><h1>Data confidentiality</h1></div>
                        <div class="hlight_subtitle" style="margin-top:0;"><h2 class="icon icon-generic"
                                                                               data-icon="i"></h2></div>
                    </div>
                    <div class="home_box omega">
                        <div class="hlight-text-scroll">
                            <p><img src="${pageContext.request.contextPath}/img/ico_conf_big.png" alt="Data archiving"/>
                                Data submitted to us can be kept confidential (accessible only via secure user login) to
                                allow data
                                producers time to publish their findings. It should be noted that all submitted data
                                must eventually be
                                suitable for public release.</p>
                            <p>The default confidential hold period is two years, but this can be changed using the ENA
                                Webin submission tool.
                                Should you wish to make your confidential data publicly available before the
                                confidential hold period expires,
                                you can reset the release date using the Webin tool.
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </c:when>
</c:choose>


<section class="hlight_item" id="blog">
    <div class="grid_24">
        <div class="hlight-spot grid_12 alpha" id="blog-spotlight">
            <div class="hlight_title_cont">
                <div class="hlight_maintitle"><h1>Spotlight</h1></div>
                <div class="hlight_subtitle"><h2></h2></div>
            </div>
            <div class="home_box alpha">
                <div>
                    <c:forEach var="i" begin="1" end="<%= (int) (Math.random() * 5 + 5) %>">
                        <p class="placeholder" style="width: <%= (int) (Math.random() * 50 + 50) %>%;"></p>
                    </c:forEach>
                </div>
                <a href="https://ebi-metagenomics.github.io/ebi-metagenomics-blog/" title="Read more on the blog"
                   class="all left">Read more</a>
                <a href="" title="" class="all">Look at the data</a>
            </div>
        </div>
        <div class="hlight-spot grid_12 omega" id="blog-tools">
            <div class="hlight_title_cont">
                <div class="hlight_maintitle"><h1>Tools</h1></div>
                <div class="hlight_subtitle"><h2></h2></div>
            </div>
            <div class="home_box omega">
                <div>
                    <c:forEach var="i" begin="1" end="<%= (int) (Math.random() * 5 + 5) %>">
                        <p class="placeholder" style="width: <%= (int) (Math.random() * 50 + 50) %>%;"></p>
                    </c:forEach>
                </div>
                <a href="https://ebi-metagenomics.github.io/ebi-metagenomics-blog/" title="Read more on the blog"
                   class="all left">Read more</a>
                <a href="" class="all">Compare samples</a>
            </div>
        </div>
    </div>
    <p>
        <a href="https://ebi-metagenomics.github.io/ebi-metagenomics-blog/" class="all">
            See all articles
        </a>
    </p>
</section>

<section class="jumbo-citation">
    <div class="grid_24">
        <div class="hlight-spot alpha">
            <div class="hlight_title_cont">
                <div class="hlight_maintitle"><h1>How to cite</h1></div>
            </div>
            <div class="home_box alpha omega" style="min-height: auto;">

                <img
                        alt="Cover of the Nucleic Acids Research journal"
                        src="<c:url value="${baseURL}/img/nucleic_acids_research_D1_cover.gif"/>"
                        style="width: auto; float: left; margin-right: 18px;"
                />
                <p>
                    To cite EBI Metagenomics, please refer to the following publication:
                </p>
                <p>
                    Alex L. Mitchell, Maxim Scheremetjew, Hubert Denise, Simon Potter, Aleksandra Tarkowska, Matloob Qureshi, Gustavo A. Salazar, Sebastien Pesseat, Miguel A. Boland, Fiona M. I. Hunter, Petra ten Hoopen, Blaise Alako, Clara Amid, Darren J. Wilkinson, Thomas P. Curtis, Guy Cochrane, Robert D. Finn (2017).
                    <br/>
                    <strong>
                        EBI Metagenomics in 2017: enriching the analysis of microbial communities, from sequence
                        reads to assemblies.
                    </strong>
                    Nucleic Acids Research (2017) doi:
                    <a
                            title="EBI Metagenomics in 2017: enriching the analysis of microbial communities, from sequence reads to assemblies"
                            href="https://doi.org/10.1093/nar/gkx967"
                            class="ext"
                    >
                        10.1093/nar/gkx967
                    </a>
                </p>

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

<%--Client-side twitter news feed - http://tweet.seaofclouds.com/ - only homepage--%>
<script src="${pageContext.request.contextPath}/js/blog.js" type="text/javascript" defer></script>
<script src="${pageContext.request.contextPath}/js/tweet/jquery.tweet.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/tweet/tweet.instance.js" type="text/javascript"></script>
<script type="text/javascript">
    //  re-style the twitter component
    $("iframe").ready(function () {
        var timer = setInterval(function () {
            if ($($("iframe").contents()).find(".avatar").length > 0) {
                $($("iframe").contents()).find(".avatar, .timeline-Tweet-author, .timeline-Tweet-media").css({display: "none"});
//                $($("iframe").contents()).find(".permalink").css({float: "none"});
                $($("iframe").contents()).find(".timeline-Tweet-retweetCredit").css({'text-align': "center"});
                /*style retweet info text*/
//                $($("iframe").contents()).find("li").css({'padding': "0 0 0 0"});
                $($("iframe").contents()).find(".timeline-Tweet-text").css({
                    'text-align': "center",
                    'font-size': '157%',
                    'line-height': '1.4'
                });
                /*style tweet main text*/
                $($("iframe").contents()).find("img.autosized-media").css({'max-height': '175px'});
                /*don't know if this is relevant anymore*/
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
