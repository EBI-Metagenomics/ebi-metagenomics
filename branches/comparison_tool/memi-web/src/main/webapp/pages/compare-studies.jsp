<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h4>${study.studyName}</h4>
<div class="output_form" id="description-content" style="overflow-y:scroll; height:150px;">
   <p id="abstract"><strong>Description: </strong>${study.studyAbstract}</p>
    <c:if test="${(not empty study.experimentalFactor) &&  (study.experimentalFactor != 'none')}">
        <p id="exp-factor"><strong>Experimental factor: </strong>${study.experimentalFactor}</p>
    </c:if>

</div>