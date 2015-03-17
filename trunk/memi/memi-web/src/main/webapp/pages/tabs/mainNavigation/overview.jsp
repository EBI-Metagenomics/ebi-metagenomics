<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="notGivenId" value="(not given)" scope="session"/>

<div id="fragment-overview">


    <tags:publications publications="${model.sample.publications}" relatedPublications="${model.relatedPublications}"
                       relatedLinks="${model.relatedLinks}"/>

    <div class="main_tab_content">
        <!-- Description -->
        <h3 id="sample_desc">Description</h3>


        <div class="output_form">
            <c:choose>
                <c:when test="${not empty model.sample.sampleDescription}">
                    <c:set var="sampleDescription" value="${model.sample.sampleDescription}"/>
                    <p class="fl_capitalize sample_desc"><c:out value="${sampleDescription}"/></p>
                </c:when>
            </c:choose>

            <c:choose>
                <c:when test="${not empty model.sample.sampleClassification}">
                    <c:set var="sampleClassification" value="${model.sample.sampleClassification}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="sampleClassification" value="${notGivenId}"/>
                </c:otherwise>
            </c:choose>

            <div class="result_row">
                <div class="result_row_label">Classification:</div>
                <div class="result_row_data"> <c:out value="${sampleClassification}"/></div>
            </div>

            <div class="result_row">
                <div class="result_row_label">Sample name:</div>
                <div class="result_row_data"><a title="${model.sample.sampleName}" href="<c:url value="${baseURL}/projects/${model.sample.study.studyId}/samples/${model.sample.sampleId}"/>">${model.sample.sampleName} (${model.sample.sampleId})</a></div>
            </div>

            <div class="result_row">
                <div class="result_row_label">Project name:</div>
                <div class="result_row_data"><a title="${model.sample.study.studyName}" href="<c:url value="${baseURL}/projects/${model.sample.study.studyId}"/>">${model.sample.study.studyName} (${model.sample.study.studyId})</a></div>
            </div>
        </div>
        <!--/ Description -->

        <!-- Data analysis -->
        <h3 id="sample_desc">Data analysis</h3>

        <div class="output_form" >
            <div class="result_row">
                <div class="result_row_label">Experiment type:</div>
                <div class="result_row_data"> ${model.analysisJob.experimentType}</div>
            </div>
            <div class="result_row">
                <div class="result_row_label">Pipeline version:</div>
                <div class="result_row_data"><a title="Pipeline release" href="<c:url value="${baseURL}/pipelines/${model.analysisJob.pipelineRelease.releaseVersion}"/>">${model.analysisJob.pipelineRelease.releaseVersion}</a></div>
            </div>
            <div class="result_row">
                <div class="result_row_label">Analysis date:</div>
                <div class="result_row_data">${model.analysisJob.completeTime}</div>
            </div>
        </div>
        <!--/Data analysis -->

        <!-- Host associated/environmental -->
        <c:choose>
            <c:when test="${model.hostAssociated}">

                <h3>Host associated</h3>
                <div class="output_form" >

                    <div class="result_row">
                        <div class="result_row_label">Species:</div>
                        <c:choose>
                        <c:when test="${not empty model.sample.hostTaxonomyId && model.sample.hostTaxonomyId>0}">
                        <div class="result_row_data"><a class="ext" title="UniProt website - Tax ID <c:out value="${model.sample.hostTaxonomyId}"/>" href="<c:url value="http://www.uniprot.org/taxonomy/${model.sample.hostTaxonomyId}"/>"><em><c:out value="${model.sample.species}"/></em></a></div>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${notGivenId}"/>
                    </c:otherwise>
                    </c:choose>
                    </div>

                    <c:if test="${not empty model.sample.hostSex}">
                        <c:set var="hostSex" value="${model.sample.hostSex}"/>
                        <div class="result_row">
                            <div class="result_row_label">Sex:</div>
                            <div class="result_row_data">${fn:toLowerCase(hostSex)}</div>
                        </div>
                    </c:if>

                    <c:if test="${not empty model.sample.phenotype}">
                        <c:set var="phenotype" value="${model.sample.phenotype}"/>
                        <div class="result_row">
                            <div class="result_row_label">Phenotype:</div>
                            <div class="result_row_data"><c:out value="${phenotype}"/></div>
                        </div>
                    </c:if>
                </div>
            </c:when>


            <c:otherwise>

                <h3>Environmental conditions</h3>
                <div class="output_form" >

                    <c:choose>
                        <c:when test="${not empty model.sample.environmentalBiome}">
                            <c:set var="environmentalBiome" value="${model.sample.environmentalBiome}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="environmentalBiome" value="${notGivenId}"/>
                        </c:otherwise>
                    </c:choose>

                    <div class="result_row">
                        <div class="result_row_label">Biome:</div>
                        <div class="result_row_data"><%--${fn:toLowerCase(environmentalBiome)}--%>
                        ${environmentalBiome}
                        </div>
                    </div>

                    <c:choose>
                        <c:when test="${not empty model.sample.environmentalFeature}">
                            <c:set var="environmentalFeature" value="${model.sample.environmentalFeature}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="environmentalFeature" value="${notGivenId}"/>
                        </c:otherwise>
                    </c:choose>

                    <div class="result_row">
                        <div class="result_row_label">Experimental feature:</div>
                        <div class="result_row_data">
                        <%--${fn:toLowerCase(environmentalFeature)}--%>
                        ${environmentalFeature}
                        </div>
                    </div>

                    <c:choose>
                        <c:when test="${not empty model.sample.environmentalMaterial}">
                            <c:set var="environmentalMaterial" value="${model.sample.environmentalMaterial}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="environmentalMaterial" value="${notGivenId}"/>
                        </c:otherwise>
                    </c:choose>

                    <div class="result_row">
                        <div class="result_row_label">Material:</div>
                        <div class="result_row_data">
                        <%--${fn:toLowerCase(environmentalMaterial)}--%>
                        ${environmentalMaterial}
                        </div>
                    </div>

                </div>
            </c:otherwise>
        </c:choose>
        <!--/ Host associated/environmental -->

        <!-- Localisation -->
        <c:choose>
        <c:when test="${empty model.sample.geoLocName && empty model.sample.longitude && empty model.sample.latitude}">
        <%--don't show the localisation box if empty--%>
        </c:when>
        <c:otherwise>
            <h3>Localisation</h3>

                    <div class="output_form"  style="overflow:auto;">

                        <c:if test="${!model.hostAssociated}">
                            <c:choose>
                                <c:when test="${empty model.sample.latitude}">
                                    <%--remove label when emtpy otherwise alignment problem--%>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${not empty model.sample.latitude}">
                                            <c:set var="latLon" value="${model.sample.latitude} , ${model.sample.longitude}"/>
                                            <div id="map_canvas"></div>
                                            <script language="javascript"> initialize(${model.sample.latitude}, ${model.sample.longitude})</script>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="latLon" value="${notGivenId}"/>
                                        </c:otherwise>
                                    </c:choose>

                                    <div class="result_row">
                                        <div class="result_row_label">Latitude/Longitude:</div>
                                        <div class="result_row_data"><c:out value="${latLon}"/></div>
                                    </div>

                                </c:otherwise>
                            </c:choose>
                        </c:if>

                        <c:choose>
                            <c:when test="${empty model.sample.geoLocName}">
                                <%--remove label when emtpy otherwise alignment problem--%>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${not empty model.sample.geoLocName}">
                                        <c:set var="geoLocName" value="${model.sample.geoLocName}"/>
                                        <div class="result_row">
                                            <div class="result_row_label">Geographic location:</div>
                                            <div class="result_row_data"><c:out value="${geoLocName}"/></div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="geoLocName" value="${notGivenId}"/>
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>


                    </div>
        </c:otherwise>
        </c:choose>
        <!--/ Localisation -->

        <!-- Other info box -->
        <c:if test="${not empty model.sampleAnnotations}">
            <h3 style="">Other information</h3>

            <div class="output_form">
                <table class="simple_table">
                    <tbody>
                    <c:forEach var="annotation" items="${model.sampleAnnotations}" varStatus="status">
                        <tr>
                            <td id="ordered" width="250px"><div class="fl_capitalize">${annotation.annotationName}</div></td>
                            <td class="fl_capitalize">${annotation.annotationValue} ${annotation.unit}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
        <!--/ Other info box -->
    </div>
</div>