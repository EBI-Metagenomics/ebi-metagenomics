<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


<section class="jumbo-header">
     <p class="lead">Submit, analyse, visualize and compare your data.</p>
   <div class="button"><a href="<c:url value="${baseURL}/submit"/>"> Submit data</a></div>
</section>




    <%--<c:forEach var="experimentCountEntry"--%>
               <%--items="${model.experimentCountMap}">--%>
        <%--${experimentCountEntry.key}: --%>
        <%--${experimentCountEntry.value}--%>
        <%--</br>--%>
    <%--</c:forEach>--%>

<section class="jumbo-stats">
<div class="grid_24 jumbo-stats-container">
    <div class="grid_6 alpha jumbo-stats-box" id="stat-submissions"><!-- <span class="icon icon-functional" data-icon="D"></span>--><span class="icon icon-generic" data-icon="D"></span><br/><span class="high_nb">${model.numOfDataSets}</span> data sets</div>
    <div class="grid_6  jumbo-stats-box" id="stat-exptype"><div class="grid_24"><div class="grid_4 alpha"><span class="icon icon-conceptual icon-c1" data-icon="d"></span></div> <div class="grid_4">
        <c:forEach var="experimentCountEntry"
                   items="${model.experimentCountMap}">
        <span class="high_nb">${experimentCountEntry.value}</span>
        </c:forEach>
    </div> <div class="grid_16 omega"> metagenomics <br/> metatranscriptomics  <br/> amplicons</div></div> </div>
    <%--<div class="grid_3 jumbo-stats-box" id="stat-member"><span class="icon icon-generic" data-icon="}"></span><br/><span class="high_nb">${model.numOfSubmitters}</span> members </div>--%>
    <div class="grid_6 jumbo-stats-box" id="stat-public"><div class="grid_24"><div class="grid_4 alpha" ><span class="icon icon-functional" data-icon="U"></span><br/><span class="stat-lock-title">Public</span></div> <div class="grid_4"><span class="high_nb">${model.publicRunCount}</span><br/><span class="high_nb">${model.publicSamplesCount}</span><br/><span class="high_nb">${model.publicStudiesCount}</span></div> <div class="grid_16 omega"> runs <br/> samples  <br/> projects</div></div></div>
    <div class="grid_6 jumbo-stats-box omega" id="stat-private"><div class="grid_24"><div class="grid_4 alpha" ><span class="icon icon-functional" data-icon="L"></span><br/><span class="stat-lock-title">Private</span></div> <div class="grid_4"><span class="high_nb">${model.privateRunCount}</span><br/><span class="high_nb">${model.privateSamplesCount}</span><br/><span class="high_nb">${model.privateStudiesCount}</span></div> <div class="grid_16 omega"> runs <br/> samples  <br/> projects</div></div></div>
</div>
</section>
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
        <h1>Browse projects</h1>
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

            <div id="list-biomes" class="grid_12 alpha">
                <div class="home_box alpha">

                <h2>By selected biomes</h2>

                <div class="grid_24 list-biomes-l">

                    <div class=" alpha list-biomes-element"><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&biome=SOIL"/>"
                                                 title="View all <c:out value="${model.biomeMap['SOIL']}"/> soil projects"><span class="biome_icon icon_sm soil_b"></span><span class="biome_text">Soil (<c:out value="${model.biomeMap['SOIL']}"/>)</span></a></div>

                    <div class=" list-biomes-element"><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&biome=MARINE"/>"
                                           title="View all <c:out value="${model.biomeMap['MARINE']}"/> marine projects"><span class="biome_icon icon_sm marine_b"></span><span class="biome_text">Marine (<c:out value="${model.biomeMap['MARINE']}"/>)</span></a></div>


                    <div class=" list-biomes-element"><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&biome=FOREST_SOIL"/>"
                                                 title="View all <c:out value="${model.biomeMap['FOREST_SOIL']}"/> forest projects"><span class="biome_icon icon_sm forest_b"></span><span class="biome_text">Forest (<c:out value="${model.biomeMap['FOREST_SOIL']}"/>)</span></a></div>

                    <div class=" list-biomes-element"><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&biome=NON_HUMAN_HOST"/>"
                                                                  title="View all <c:out value="${model.biomeMap['NON_HUMAN_HOST&']}"/> non-human host-associated projects"><span class="biome_icon icon_sm non_human_host_b"></span><span class="biome_text">Non-human host (<c:out value="${model.biomeMap['NON_HUMAN_HOST']}"/>)</span></a></div>

                    <div class=" omega list-biomes-element"><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&biome=ENGINEERED"/>"
                                                               title="View all <c:out value="${model.biomeMap['ENGINEERED']}"/> engineered projects"><span class="biome_icon icon_sm engineered_b"></span><span class="biome_text">Engineered (<c:out value="${model.biomeMap['ENGINEERED']}"/>)</span></a></div>


                      </div>
                <div class="grid_24 list-biomes-l">

                    <div class="list-biomes-element alpha"><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&biome=FRESHWATER"/>"
                                                                   title="View all <c:out value="${model.biomeMap['FRESHWATER']}"/> freshwater projects"><span class="biome_icon icon_sm freshwater_b"></span><span class="biome_text">Freshwater (<c:out value="${model.biomeMap['FRESHWATER']}"/>)</span></a></div>

                    <div class="list-biomes-element"><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&biome=GRASSLAND"/>"
                                                 title="View all <c:out value="${model.biomeMap['GRASSLAND']}"/> grassland projects"><span class="biome_icon icon_sm grassland_b"></span><span class="biome_text">Grassland (<c:out value="${model.biomeMap['GRASSLAND']}"/>)</span></a></div>

                    <div class="list-biomes-element "><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&biome=HUMAN_GUT"/>"
                                                 title="View all <c:out value="${model.biomeMap['HUMAN_GUT']}"/> human gut projects"><span class="biome_icon icon_sm human_gut_b"></span><span class="biome_text">Human gut (<c:out value="${model.biomeMap['HUMAN_GUT']}"/>)</span></a></div>

                    <div class="list-biomes-element"><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&biome=AIR"/>"
                                                                                  title="View all <c:out value="${model.biomeMap['AIR']}"/> air projects"><span class="biome_icon icon_sm air_b"></span><span class="biome_text">Air (<c:out value="${model.biomeMap['AIR']}"/>)</span></a></div>
                        <%--<div class="grid_4"><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&biome=HUMAN_HOST"/>"--%>
                                                                    <%--title="View all <c:out value="${model.biomeMap['HUMAN_HOST&']}"/> human host-associated projects"><span class="biome_icon icon_sm human_host_b"></span><span class="biome_text">Human host (<c:out value="${model.biomeMap['HUMAN_HOST']}"/>)</span></a></div>--%>

                    <div class="list-biomes-element omega"><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&biome=WASTEWATER"/>"
                                                                                   title="View all <c:out value="${model.biomeMap['WASTEWATER']}"/> wastewater projects"><span class="biome_icon icon_sm wastewater_b"></span><span class="biome_text">Wastewater (<c:out value="${model.biomeMap['WASTEWATER']}"/>)</span></a></div>

                    <%--<div class="grid_4 omega "><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&studyVisibility=ALL_PUBLISHED_PROJECTS"/>"--%>
                                                 <%--title="View all  projects"><span class="biome_icon icon_sm default_b"></span><span class="biome_text">Other biomes</span></a></div>--%>
                </div>

                    <%--<div class="grid_8 alpha"><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&biome=HUMAN-HOST"/>"--%>
                                                           <%--title="View all soil projects"><span class="biome_icon icon_sm human_host_b"></span><span class="biome_text">Human (9)</span></a></div>--%>
                                        <%--<div class="grid_8"><a class="anim" href="<c:url value="${baseURL}/projects/doSearch?search=Search&biome=NON-HUMAN-HOST"/>"--%>
                                                                               <%--title="View all marine projects"><span class="biome_icon icon_sm non_human_host_b"></span><span class="biome_text">Host-associated non-human (24)</span></a></div>--%>
                                        <%--<div class="grid_8 omega"><a class="anim" href="<c:url vaue="${baseURL}/projects/doSearch?search=Search&studyVisibility=ALL_PUBLISHED_PROJECTS"/>"--%>
                                                                               <%--title="View all forest projects"><span class="biome_icon icon_sm default_b"></span><span class="biome_text">All biomes (3)</span></a></div>--%>

               <a href="<c:url value="${baseURL}/projects/doSearch?search=Search&studyVisibility=ALL_PUBLISHED_PROJECTS"/>"
                                                        title="View projects and filter by biome" class="all">View all biomes</a>
            </div> <!-- /home_box -->
            </div>

            <!-- Project list box -->
            <div id="list-data-study" class="grid_12 omega">

                <div class="home_box omega">
                <h2>Latest projects <span class="badge"><a
                                        href="<c:url value="${baseURL}/projects/doSearch?search=Search&studyVisibility=ALL_PUBLISHED_PROJECTS"/>"
                                        title="View all ${model.publicStudiesCount} public projects">${model.publicStudiesCount}</a></span></h2>
                 <div class="list-project-l">
                    <%--The count starts at 0, that is why we subtract 1 from the end value--%>
                <c:forEach var="study" items="${model.studies}" varStatus="status" begin="0"
                           end="${model.maxRowNumberOfLatestItems-1}">

                            <%--TEMP while we implement a better solution--%>
                            <c:choose>
                            <c:when test="${study.biomeIconCSSClass == 'freshwater_b'}">
                                <c:set var="biomeName" value="Freshwater" scope="page"/>
                            </c:when>
                            <c:when test="${study.biomeIconCSSClass == 'soil_b'}">
                                    <c:set var="biomeName" value="Soil" scope="page"/>
                            </c:when>
                            <c:when test="${study.biomeIconCSSClass == 'forest_b'}">
                                    <c:set var="biomeName" value="Forest" scope="page"/>
                            </c:when>
                            <c:when test="${study.biomeIconCSSClass == 'grassland_b'}">
                                    <c:set var="biomeName" value="Grassland" scope="page"/>
                            </c:when>
                            <c:when test="${study.biomeIconCSSClass == 'marine_b'}">
                                <c:set var="biomeName" value="Marine" />
                            </c:when>
                            <c:when test="${study.biomeIconCSSClass == 'human_gut_b'}">
                                    <c:set var="biomeName" value="Human gut" scope="page"/>
                            </c:when>
                            <c:when test="${study.biomeIconCSSClass == 'engineered_b'}">
                                    <c:set var="biomeName" value="Engineered" scope="page"/>
                            </c:when>
                            <c:when test="${study.biomeIconCSSClass == 'air_b'}">
                                    <c:set var="biomeName" value="Air" scope="page"/>
                            </c:when>
                            <c:when test="${study.biomeIconCSSClass == 'wastewater_b'}">
                                    <c:set var="biomeName" value="Wastewater" scope="page"/>
                            </c:when>
                            <c:when test="${study.biomeIconCSSClass == 'non_human_host_b'}">
                                    <c:set var="biomeName" value="Non-human host" scope="page"/>
                            </c:when>
                            <c:when test="${study.biomeIconCSSClass == 'human_host_b'}">
                                    <c:set var="biomeName" value="Human host" scope="page"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="biomeName" value="Undefined" scope="page"/>
                            </c:otherwise>
                            </c:choose>
                    <!-- branch test-->

                    <p><div class="biome_icon icon_xs ${study.biomeIconCSSClass}" title="${biomeName} biome"></div>
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

                </div><!-- /list-project-l -->


                    <a href="<c:url value="${baseURL}/projects/doSearch?search=Search&studyVisibility=ALL_PUBLISHED_PROJECTS"/>"
                       title="View all public projects" class="all">View all projects</a>
                </div> <!-- /home_box -->
                </div> <!-- /Project list box -->


        </c:otherwise>
    </c:choose>

        </div><!-- /jumbo-list-data-container-->
</section>

<section class="hlight_item">
    <div class="grid_24">
        <div id="hlight-box-spotlight" class="grid_12 alpha hlight-spot">
           <div  class="hlight_title_cont"> <div class="hlight_maintitle"><h1>Spotlight</h1></div><div class="hlight_subtitle"><h2>TARA ocean project</h2></div></div>
            <div class="home_box alpha">
                <img alt="Tara map" src="<c:url value="${baseURL}/img/Tara_map_crop_590px.png"/>" />
                <p>Plankton ecosystems contain a phenomenal reservoir of life: more than 10 billion organisms inhabit every litre of oceanic water, including viruses, prokaryotes, unicellular eukaryotes (protists), and metazoans.

                Plankton&#39;s importance for the earth&#39;s climate is at least equivalent to that of the rainforest. Yet only a small fraction of organisms that compose it have been classified and analysed.</p>

                <p>Tara Oceans expedition, led by EMBL senior scientist Eric Karsenti, has been travelling around the world (2009-13) collecting over 35,000 ocean samples containing millions of small organism collected in more than 210 ocean stations. <a href="http://www.embl.de/tara-oceans/start/"> More about Tara</a></p>

                <a href="<c:url value="${baseURL}/projects/ERP001736"/>"
                                                                      title="View Tara project" class="all">Look at the data</a>
            </div>
        </div>
        <div id="hlight-box-tools" class="grid_12 alpha hlight-spot">
                   <div  class="hlight_title_cont"> <div class="hlight_maintitle"><h1>Tools</h1></div><div class="hlight_subtitle"><h2>Functional sample comparison</h2></div></div>
                    <div class="home_box alpha">
                        <img alt="example of Go terms analysis result" src="<c:url value="${baseURL}/img/comparesamples_crop_568px.png"/>"/>
                        <p>Always wondering what your microorganisms do in your samples? Now it is possible to compare mutiple samples in the same project, using our new comparison tool. It allows you to compare samples using functional information based on the Gene Ontology (GO) terms derived from InterPro matches.

                        <p>You can visualise your results, using interactive and responsive charts, with a large choice of options and visualizations to choose from (bar chart, stacked column, PCA, Heatmap).<br/>Have a try, select a project and  <a href="<c:url value="${baseURL}/compare"/>">do a functional sample comparison</a> based on Go terms.</p>

                        <a href="<c:url value="${baseURL}/compare"/>"
                                                                              title="Compare samples" class="all">Compare samples</a>
                    </div>
        </div>
    </div>
</section>

<section class="jumbo-news">
<div class="grid_24 jumbo-news-container">
<div class="jumbo-news-centred">

    <h3 class="icon icon-socialmedia icon-s2" data-icon="T"></h3>
    <%--<div class="flexslider">Learn to run a design the box workshop and how it helped us overhaul our training academy: <a href="#">webcredible.com/blog-reports/</a></div>--%>
    <a class="twitter-timeline"  height="100" data-dnt="true" href="https://twitter.com/EBImetagenomics" data-widget-id="345516657369837568" data-chrome="nofooter noborders noheader noscrollbar transparent"  data-tweet-limit="1">Tweets by @EBImetagenomics</a>

    <p class="jumbo-news-contact"><strong><a href="http://twitter.com/EBImetagenomics" alt="Follow us on Twitter">@EBImetagenomics</a></strong></p>
    <script>
    !function(d,s,id){var
            js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+"://platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>



</div>
</div>
</section>

<!-- script for carousel - only homepage-->
<%--<script src="${pageContext.request.contextPath}/js/jquery.carousel.min.js" type="text/javascript"></script>--%>
<%--<script type="text/javascript">--%>
    <%--$(document).ready(function () {--%>
        <%--$("div.carousel").carousel({pagination:true, autoSlide:true, autoSlideInterval:15000, delayAutoSlide:2000, loop:true });--%>

    <%--});--%>
<%--</script>--%>
<!-- End script for carousel-->
<!-- script to remove empty breadcrumb on the homepage-->
<script type="text/javascript">
    $(document).ready(function () {
        $("#breadcrumb").hide();

    });
</script>
<!-- End script to remove empty breadcrumb on the homepage-->
<%--Client-side twitter news feed - http://tweet.seaofclouds.com/ - only homepage--%>
<script src="${pageContext.request.contextPath}/js/tweet/jquery.tweet.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/tweet/tweet.instance.js" type="text/javascript"></script>
<!-- Style the twitter component-->
<script type="text/javascript">
    $("iframe").ready(function() {
        setTimeout(function() {
            $($("iframe").contents()).find(".avatar, .p-author, .footer").css({ display: "none" });
            $($("iframe").contents()).find(".permalink").css({ float: "none" });
            $($("iframe").contents()).find(".header").css({'text-align': "center" });
            $($("iframe").contents()).find("li").css({'padding': "0 0 0 0" });
            $($("iframe").contents()).find(".e-entry-title").css({'text-align': "center", 'font-size':'157%', 'line-height':'1.4' });
        }, 700);
    });
</script>