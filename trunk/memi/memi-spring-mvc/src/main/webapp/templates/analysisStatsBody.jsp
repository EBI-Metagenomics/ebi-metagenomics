<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div id="content-full">
    <a name="top"></a>
    <c:choose>
        <c:when test="${not empty model.sample}">

            <h2>Study ${model.sample.study.studyName}</h2>
            <h4>Analysis of Sample <a
                    href="<c:url value="${baseURL}/sample/${model.sample.sampleId}"/>">${model.sample.sampleTitle}</a>
            </h4>


            <h2>Analysis results summary view</h2>

            <div style="margin-top:40px"></div>
            <h4>Statistics</h4>
            <c:set var="noDisplayID" value="No data to display."/>

            <p>Percentage of sequence reads with Predicted CDS features and InterPro hits</p>
            <table border="1" width="95%">
                <tr>
                    <td width="40px">
                        <c:choose>
                            <c:when test="${not empty model.barChartURL}">
                                <a href="#">
                                    <img src="<c:out value="${model.barChartURL}"/>"/>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <b><c:out value="${noDisplayID}"/></b>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </table>

            <h4>Annotation profile - GO Terms</h4>

            <table border="1" width="95%">
                <tr>
                    <td>Biological process</td>
                    <td align="left">
                        <c:if test="${not empty model.pieChartBiologicalProcessURL}">
                            <a title="Export as a table (TSV)"
                               href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportResultTable/${model.emgFile.fileName}"/>">
                                Export as a table (TSV)
                            </a>
                            <br>
                            <a href="#" title="Export a mored detailed table (TSV)">Export a more detailed table
                                (TSV)
                            </a>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${not empty model.pieChartBiologicalProcessURL}">
                                <a href="#">
                                    <img src="<c:out value="${model.pieChartBiologicalProcessURL}"/>"/>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <b><c:out value="${noDisplayID}"/></b>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <td>Cellular component</td>
                </tr>
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${not empty model.pieChartCellularComponentURL}">
                                <a href="#">
                                    <img src="<c:out value="${model.pieChartCellularComponentURL}"/>"/>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <b><c:out value="${noDisplayID}"/></b>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                <tr>
                    <td>Molecular function</td>
                </tr>
                </tr>
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${not empty model.pieChartMolecularFunctionURL}">
                                <a href="#">
                                    <img src="<c:out value="${model.pieChartMolecularFunctionURL}"/>"/>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <b><c:out value="${noDisplayID}"/></b>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </table>
        </c:when>
        <c:otherwise>
            <h2>Analysis Statistics</h2>

            <h3>Sample ID Not Recognised</h3>
        </c:otherwise>
    </c:choose>
</div>