<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- A bit of CSS to improve page display -->
<style type="text/css">
#selection-wrapper{
width:100%;
align-self: center;

}

#selection-wrapper div{
    top:0;
    width:30%;
    margin: auto;
    padding:10px;
    float:left;

}

#selection-wrapper div select {
    width:calc(95%);
}

</style>

<h2>Sample Comparison Tool</h2>
<p>EBI Metagenomics offers the coolest way ever to compare and visualize your data, with javascript-powered interactive funky stuff. Functional analysis only at the moment.</p>


<p>Choose the project that you wish to work on.</p>
<div id="demo">Content to update</div>

</br>
<div id="selection-wrapper">

<div id="project-choice">
<h2>Select a Project</h2>
    <select size="8" name="Projects" id="projects">
        <c:forEach var="study" items="${studies}">
            <option value=${study.id}>${study.studyName} (${study.studyId})</option>
        </c:forEach>
    </select>
    <%--<button id="choose-project" onclick="DisplayText()">Choose project</button>--%>
    <button id="choose-project">Choose project</button>
</div>

<div id="sample-choice">
    <h2>Select Samples</h2>
    <select multiple size="8" id="samples">
        <c:forEach var="sample" items="${samples}">
            <option value=${sample.sampleId}>${sample.sampleName} (${sample.study.studyId})</option>
        </c:forEach>
    </select>
    <button id="remove" onclick="$('#samples option:selected').remove()">Remove selected</button>
    <button id="clearAll" onclick="$('#samples').empty()">Clear all</button>
</div>

<div id="settings-run">
    <h2>Choose settings & Run</h2>
    <p>Stuff</p>
    <form action="">
    <input type="radio" name="option" value="1">GO slim<br>
    <input type="radio" name="option" value="2">IPR<br>
    <input type="checkbox" name="collapse" value="1">Check if you like Marmite<br>
</form>
    <button id="run" onclick="alert('Nope.')">Run Forrest, run !</button>
    </div>

    </div>

<script type="text/javascript" defer="defer">
    $(document).ready(function () {
        $('#choose-project').click(function()
        {
            var studyId = $('#projects').val();
            $.ajax({
                url:"<c:url value="${baseURL}/compare/samples"/>",
                type:"GET",
                cache:false,
                data:{studyId:studyId},
                success:function (data) {
                    $("#samples").html(data);
                },
                error:function (jqXHR, textStatus, errorThrown) {
                    alert("Request failed: " + textStatus);
                }
            });//end ajax method
//            alert(studyId);
        });
    });
</script>


<script>
    var names = $('#samples option').clone();
    $('#samples').empty();
    $('#projects').change(function() {
        var val = $(this).val();
        $('#samples').empty();
        names.filter(function(idx, el) {
            return $(el).text().indexOf('(' + val + ')') >= 0;
        }).appendTo('#samples');
    });

    function DisplayText() {
var texto = "coucou";

        document.getElementById("demo").innerHTML = texto;

    }
</script>
