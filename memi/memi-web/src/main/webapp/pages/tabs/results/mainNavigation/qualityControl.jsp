<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="fragment-quality">
    <%--BEGIN READS SECTION   --%>

    <%--<h3>Submitted nucleotide data</h3>--%>
    <p>The chart below shows the number of sequence reads which pass each of the quality control steps we
        have implemented in our pipeline. Note that, for paired-end data, sequence merging may have
        occurred and so the initial number of reads may differ from what is in the ENA. For more details
        about the data processing we employ, please see the <a href="<c:url value="${baseURL}/about#analysis"/>"  title="About Metagenomics">about page</a>.</p>
    <c:choose>
        <c:when test="${empty model.sample.analysisCompleted}"><div class="msg_error">Analysis in progress.</div></c:when>
        <c:when test="${not empty model.sample.analysisCompleted && !model.analysisStatus.qualityControlTabDisabled}">

            <c:url var="statsImage" value="/getImage" scope="request">
                <c:param name="imageName" value="/charts/qc.png"/>
                <c:param name="imageType" value="PNG"/>
                <c:param name="dir" value="${model.analysisJob.resultDirectory}"/>
            </c:url>
            <p><img src="<c:out value="${statsImage}"/>"/></p>

        </c:when>
        <c:otherwise>
            <div class="msg_error">No result files have been associated with this sample.</div>
        </c:otherwise>
    </c:choose>

</div>