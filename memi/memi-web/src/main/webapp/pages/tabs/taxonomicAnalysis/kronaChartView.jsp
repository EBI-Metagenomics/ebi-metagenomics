<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="tax-Krona">
<div class="msg_error" id="ie_krona">Krona chart is only working for latest versions of Internet Explorer (IE 9+). <br/>
Please, update your browser to a more recent version - <a href="http://windows.microsoft.com/ie" onclick="trackExternalDownload('http://windows.microsoft.com/ie')">Download Now</a>.</div>
<object class="krona_chart" data="<c:url value="${baseURL}/sample/${model.sample.sampleId}/krona?runId=${model.sample.id}&taxonomy=true&collapse=false"/>" type="text/html"></object>
</div>