<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- A bit of CSS to improve page display -->
<style type="text/css">

#selection-row{
width:100%;
align-self: center;

}

#selection-row .container{
    vertical-align:top;
    width:48%;
    margin: auto;
    padding:10px;
    display:inline-block;

}

#samples-settings-row{
    width:100%;
    align-self: center;

}

#samples-settings-row .container{
    vertical-align: top;
    width:48%;
    margin: auto;
    padding:10px;
    display:inline-block;

}

</style>

<h2>Sample Comparison Tool</h2>

<p>EBI Metagenomics offers a web tool to compare and visualize metagenomics data from same projects. Start by selecting a project. To do : documentation for users.</p>

<div id="demo"></div>

<form:form id="comparison-tool-form-id" method="post" action="compare" commandName="comparisonForm">
    <div id="selection-row">

        <h3>1. Select a Project</h3>
        <div id="project-choice" class="container">
            <h4>List of projects</h4>
                <%--<form:option value="-" label="--Select project"/>--%>
                <form:select path="study" size="9" id="projects">

                    <c:forEach var="study" items="${studies}">
                        <c:if test="${fn:length(study.samples) gt 2}">
                        <form:option id="${study.studyId}" value="${study.id}" title="Project ${study.studyId} | ${study.studyName}">${study.studyName}</form:option>
                        </c:if>
                    </c:forEach>
                </form:select>
            <form:errors path="study" cssClass="error"/>


        </div>
        <div id="project-description" class="container">
            <h4>Selected Project Information</h4>
            <div class="output_form" id="description-content" style="overflow-y:scroll; height:150px;">
                <i>Select a project in the menu on the left to display info about it.</i>
            </div>

        </div>
    </div>
    <div>
        <button id="choose-project" type="button">Choose project (next step)</button>
    </div>


    <div id="samples-settings-row">
    <div id="settings" class="container">
        <h3>2. Select Data & Visualization</h3>
        <div>
            <h4>Data</h4>
            <form:select path="usedData" id="data-choice">
                <form:option value="nothing">Choose your data</form:option>
                <form:option value="GO">Full GO annotation</form:option>
                <form:option value="GOslim">GO slim annotation</form:option>
                <form:option value="IPR">InterPro matches</form:option>
                <form:option value="IPRcol">Collapsed InterPro matches</form:option>
            </form:select>
        </div>
        <div>
            <h4>Visualization</h4>
            <form:select path="usedVis" id="vis-choice">
                <form:option value="nothing">Choose your visualization</form:option>
                <form:option value="bar">Barcharts (2-10 samples only)</form:option>
                <form:option value="heatmap">Heatmap</form:option>
                <form:option value="pca">Principal Components analysis</form:option>
                <form:option value="table">Abundance table only</form:option>
            </form:select>
        </div>
        <div>
            <h4>Extra settings</h4>
            <form:checkbox path="keepNames" title="Check to keep sample ID" value="keeping-names"/><strong>Keep samples ID during pre-processing?</strong>
        </div>
    </div>
    <div id="sample-choice" class="container">
    <h3>3. Select Samples & Run</h3>
    <form:select path="samples" multiple="true" size="8" id="samples">
    </form:select>
    <p id="selected-samples">No selected samples</p>
        <div id="samples-buttons">
            <button id="remove" type="button" onclick="$('#samples option:selected').remove()">Remove selected</button>
            <button id="clearAll" type="button" onclick="$('#samples').empty()">Clear all</button>
            <input class="main_button" type="submit" value="Submit" name="search" id="run-button"/>
        </div>
    </div>


</form:form>
</div>

<br><br>

<%--TODO: Clean up code a bit--%>
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

<script type="text/javascript" defer="defer">
    $(document).ready(function () {
        $('#projects').change(function() {
            var textId = $('#projects').children(":selected").attr("id");
            $.ajax({
                url:"<c:url value="${baseURL}/compare/studies"/>",
                type:"GET",
                cache:false,
                data:{studyId:textId},
                success:function (data) {
                    $("#project-description").html(data);
                },
                error:function (jqXHR, textStatus, errorThrown) {
                    alert("Request failed: " + textStatus);
                }
            });//end ajax method
        });
    });
</script>


<script defer="defer">
    // Replace long text by substring + dots
    $("#projects option").each(function()
{
    if($(this).text().length>80)
    {
        $(this).text($(this).text().substring(0, 77)+"...");
    }
    else
    {
        $(this).text($(this).text());
    }
});</script>

<script>
    $(document).ready(function () {
        // Elements are disabled and hidden at start and enabled and displayed if certain conditions are matched
        document.getElementById('data-choice').disabled = true;
        document.getElementById('vis-choice').disabled = true;
        document.getElementById('samples').disabled = true;
        document.getElementById('clearAll').disabled = true;
        document.getElementById('remove').disabled = true;
        document.getElementById('run-button').disabled = true;
        $('#settings').hide();
        $('#sample-choice').hide();

        $('#choose-project').click(function () {
            document.getElementById('data-choice').disabled = false;
            $('#settings').fadeIn('slow');

        });

        // Data choice
        $('#data-choice').change(function() {       if($('#data-choice').val() !== "nothing") {
            document.getElementById('vis-choice').disabled = false;
        }
            else {
            document.getElementById('vis-choice').disabled = true;
            }
        });

        // Visualization choice
        $('#vis-choice').change(function() {       if($('#vis-choice').val() !== "nothing") {
            document.getElementById('samples').disabled = false;
            document.getElementById('clearAll').disabled = false;
            document.getElementById('remove').disabled = false;
            $('#sample-choice').fadeIn('slow');
        }
        else {
            document.getElementById('samples').disabled = true;
            document.getElementById('clearAll').disabled = true;
            document.getElementById('remove').disabled = true;
        }
        });

        // Samples
        $('#samples').change(function() {
            var numberSelected = $("#samples option:selected").length;
            document.getElementById("selected-samples").innerHTML = numberSelected+" selected samples.";
            if(numberSelected < 2) {
            document.getElementById('run-button').disabled = true;
        }
            else {
                document.getElementById('run-button').disabled = false;
            }
        });
        // Run
        $('#run-button').click(function() {
            alert("I have been enabled and clicked.");
        });


    });
       // document.getElementById('clearAll').disabled;
       // document.getElementById('remove').disabled;
</script>

<script>


</script>
<!-- script>
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
</script-->