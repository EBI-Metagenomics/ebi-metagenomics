<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="tax-Krona"><object class="krona_chart" data="<c:url value="${baseURL}/sample/${model.sample.sampleId}/krona?taxonomy=true&collapse=false"/>" type="text/html"></object>
</div>