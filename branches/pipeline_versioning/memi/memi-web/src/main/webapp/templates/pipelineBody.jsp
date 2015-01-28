<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Pipeline Tools (version 1.0)</h2>

<table border="1" class="result">
    <thead>
    <tr>
        <th>Tool name</th>
        <th>Version</th>
        <th>Execution command</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="pipelineTool" items="${pipelineTools}" varStatus="status">
        <tr>
            <td class="h_left" id="ordered">${pipelineTool.toolName}</td>
            <td>${pipelineTool.toolVersion}</td>
            <td class="h_left">${pipelineTool.executionCmd}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>