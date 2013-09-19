<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<div id="fragment-overview">


    <tags:publications publications="${model.sample.publications}" relatedPublications="${model.relatedPublications}"
                       relatedLinks="${model.relatedLinks}"/>

    <div class="main_tab_content">
        <%--BEGIN DESCRIPTION--%>
        <h3 id="sample_desc">Description</h3>

            <a title="${model.sample.study.studyName}"
               href="<c:url value="${baseURL}/project/${model.sample.study.studyId}"/>"><span>${model.sample.study.studyId}</span></a>

        <div class="output_form">
            <c:choose>
                <c:when test="${not empty model.sample.sampleDescription}">
                    <c:set var="sampleDescription" value="${model.sample.sampleDescription}"/>
                    <p class="fl_uppercase sample_desc">${fn:toLowerCase(sampleDescription)}</p>
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

            <div class="result_row"><label>Classification:</label><span> <c:out value="${sampleClassification}"/></span>
            </div>

            <div class="result_row"><label>Project:</label><span>?</span>
                       </div>
        </div>
        <%--END DESCRIPTION--%>

        <%--BEGIN ENVIRONMENTAL/HOST ASSOCIATED    --%>
        <c:choose>

            <c:when test="${model.hostAssociated}">
                <h3>Host associated</h3>

                <div class="output_form" id="large">

                    <div class="result_row"><label>Species:</label>
                        <c:choose>
                        <c:when test="${not empty model.sample.hostTaxonomyId && model.sample.hostTaxonomyId>0}">
        <span><c:out value="${model.sample.species}"/> <a class="ext"
                                                          href="<c:url value="http://www.uniprot.org/taxonomy/${model.sample.hostTaxonomyId}"/>">Tax
            ID <c:out value="${model.sample.hostTaxonomyId}"/></a> </span></div>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${notGivenId}"/>
                    </c:otherwise>
                    </c:choose>


                    <c:if test="${not empty model.sample.hostSex}">
                        <c:set var="hostSex" value="${model.sample.hostSex}"/>
                        <div class="result_row"><label>Sex:</label> <span
                                style="text-transform: capitalize;">${fn:toLowerCase(hostSex)}</span></div>
                    </c:if>


                    <c:if test="${not empty model.sample.phenotype}">
                        <c:set var="phenotype" value="${model.sample.phenotype}"/>
                        <div class="result_row"><label>Phenotype:</label> <span><c:out value="${phenotype}"/></span>
                        </div>
                    </c:if>


                </div>
                <%--end div output_form--%>

            </c:when>

            <c:otherwise>
                <h3>Environmental conditions</h3>

                <div class="output_form" id="large">
                    <c:choose>
                        <c:when test="${not empty model.sample.environmentalBiome}">
                            <c:set var="environmentalBiome" value="${model.sample.environmentalBiome}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="environmentalBiome" value="${notGivenId}"/>
                        </c:otherwise>
                    </c:choose>
                    <div class="result_row"><label>Biome:</label> <span class="fl_uppercase">${fn:toLowerCase(environmentalBiome)}</span>
                    </div>

                    <c:choose>
                        <c:when test="${not empty model.sample.environmentalFeature}">
                            <c:set var="environmentalFeature" value="${model.sample.environmentalFeature}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="environmentalFeature" value="${notGivenId}"/>
                        </c:otherwise>
                    </c:choose>
                    <div class="result_row"><label>Experimental feature:</label><span class="fl_uppercase">${fn:toLowerCase(environmentalFeature)}</span>
                    </div>

                        <c:choose>
                        <c:when test="${not empty model.sample.environmentalMaterial}">
                            <c:set var="environmentalMaterial" value="${model.sample.environmentalMaterial}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="environmentalMaterial" value="${notGivenId}"/>
                        </c:otherwise>
                    </c:choose>

                    <div class="result_row"><label>Material:</label><span class="fl_uppercase">${fn:toLowerCase(environmentalMaterial)}</span>
                    </div>

                </div>
            </c:otherwise>
        </c:choose>
        <%--END ENVIRONMENTAL/HOST ASSOCIATED--%>

        <%--BEGIN LOCALISATION   --%>
        <h3>Localisation</h3>

        <div class="output_form" id="large" style="overflow:auto;">

            <c:if test="${!model.hostAssociated}">
                <c:choose>
                    <c:when test="${empty model.sample.latitude}">
                        <%--remove the label when emtpy otherwise make alignment problem--%>
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
                        <div class="result_row"><label>Latitude/Longitude:</label> <span><c:out
                                value="${latLon}"/></span></div>
                    </c:otherwise>
                </c:choose>
            </c:if>

            <c:choose>

                <c:when test="${empty model.sample.geoLocName}">
                    <%--remove the label when emtpy otherwise make alignment problem--%>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${not empty model.sample.geoLocName}">
                            <c:set var="geoLocName" value="${model.sample.geoLocName}"/>
                            <div class="result_row"><label>Geographic location:</label> <span><c:out
                                    value="${geoLocName}"/></span></div>
                        </c:when>
                        <c:otherwise>
                            <c:set var="geoLocName" value="${notGivenId}"/>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>


        </div>
        <%--END LOCALISATION   --%>

        <%--BEGIN OTHER INFO   --%>
        <c:if test="${not empty model.sampleAnnotations}">
            <h3 style="">Other information
                    <%--<span id="expandersign">+</span>--%>
            </h3>

            <div class="output_form">
                <table class="simple_table">
                    <tbody>
                    <c:forEach var="annotation" items="${model.sampleAnnotations}" varStatus="status">
                        <tr>
                            <td class="h_left" id="ordered" width="250px"><span class="fl_uppercase">${annotation.annotationName}</span></td>
                            <td class="h_left">${annotation.annotationValue} ${annotation.unit}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
        <%--END OTHER INFO   --%>
    </div>
</div>