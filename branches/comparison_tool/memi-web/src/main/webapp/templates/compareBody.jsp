<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- A bit of CSS to improve page display. Yay. Dubugging css is cool when you apply different colors on background -->
<style type="text/css">

a:hover {
    cursor:pointer;
}

#row-wrapper{
    width:100%;
    height:250px;
    background: transparent;
}

#final-buttons-wrapper{
    width:11%;
    margin: 0 auto;
    background: transparent;
}

#settings-wrapper{
    background: transparent;
    height:150px;
}

#settings-wrapper .settings-div {
    float:left;
    background: transparent;
    padding: 10px;
}

#project-div {
    float:left;
    width: 45%;
    height: 100%;
    padding-left: 20px;
}

#samples-div {
    float:left;
    width: 45%;
    height: 100%;
    padding-left: 20px;
}

</style>

<h2>Sample comparison tool</h2>

<div id="demo"></div>

<form:form id="comparison-tool-form-id" method="post" commandName="comparisonForm">
    <div id="row-wrapper">

        <div id="project-div">
            <h4>Project list</h4>
                <%--<form:option value="-" label="--Select project"/>--%>
                <form:select path="study" size="9" id="projects" style="width:100%;">
                    <c:forEach var="study" items="${studies}">
                        <c:if test="${fn:length(study.samples) gt 2}">
                        <form:option id="${study.studyId}" value="${study.id}" title="Project ${study.studyId} | ${study.studyName}">${study.studyName}</form:option>
                        </c:if>
                    </c:forEach>
                </form:select>
            <form:errors path="study" cssClass="error"/>
            <div id="project-description" title="Project description">
                <div id="description-content" style="height:30px;">
                    <i>Select a project in the menu above.</i>
                </div>
            </div>
        </div>

            <div id="samples-div">
                <h4 id="selected-samples">Sample list (0 selected)</h4>
                <form:select path="samples" multiple="true" size="9" id="samples" style="width:100%;">
                </form:select>
                <div id="samples-control"><a id="select-all-button" onclick="SelectAllSamples()">Select all</a> | <a id="unselect-all-button" onclick="UnselectAllSamples()">Unselect all </a></div>
            </div>
    </div>

    <div id="settings-wrapper">
        <div class="settings-div">
            <h4>Data</h4>
            <form:select path="usedData" id="data-choice">
                <form:option value="nothing">Select data</form:option>
                <form:option value="GO">Full GO annotation</form:option>
                <form:option value="GOslim">GO slim annotation</form:option>
                <form:option value="IPR" disabled="true">InterPro matches</form:option>
                <form:option value="IPRcol" disabled="true">Collapsed InterPro matches</form:option>
            </form:select>
        </div>
        <%--div class="settings-div">
            <h4>Visualization</h4>
            <form:select path="usedVis" id="vis-choice">
                <form:option value="nothing">Select a visualization</form:option>
                <form:option value="all">All available visualizations</form:option>
                <form:option value="pie" disabled="true">Overview</form:option>
                <form:option value="bar" disabled="true">Barcharts</form:option>
                <form:option value="stackbar" disabled="true">Stacked bars</form:option>
                <form:option value="heatmap" disabled="true">Heatmap</form:option>
                <form:option value="int-heatmap" disabled="true">Javascript Heatmap</form:option>
                <form:option value="table" disabled="true">Table</form:option>
                <form:option value="pca" disabled="true">Principal Components analysis</form:option>
            </form:select>
        </div--%>
        <div class="settings-div" style="position:relative; bottom: 0;">
            <h4>Advanced settings</h4>
            <p><a id="advanced-settings-link">Show / hide advanced settings</a></p>
            <!-- Advanced settings div, that should be hidden at the beginning -->
        </div>
        <div id="advanced-settings-div" class="settings-div" title="Advanced settings">
            <ul>
                <li><strong>General</strong>
                <ul>
                    <li><form:checkbox path="keepNames" title="Check to keep sample ID"/><strong>Keep samples ID?</strong></li>
                </ul></li>
                <li><strong>Stacked columns</strong>
                    <ul>
                        <li><form:input path="stackThreshold" type="number" min="0" max="50" step="0.1" value="1"/> Stacking threshold (%)</li>
                    </ul>
                </li>
                <li><strong>Heatmap</strong><ul>
                    <li><form:checkbox path="hmLegend"/>Show legend<br></li>
                    <li><form:select path="hmClust">
                        <form:option value="none">No clustering</form:option>
                        <form:option value="row">Observations (X axis)</form:option>
                        <form:option value="column">Samples (Y axis)</form:option>
                        <form:option value="both">Both</form:option>
                    </form:select>Hierarchical clustering</li>
                    <li><form:select path="hmDendrogram">
                        <form:option value="none">No dendrograms</form:option>
                        <form:option value="row">Observations (X axis)</form:option>
                        <form:option value="column">Samples (Y axis)</form:option>
                        <form:option value="both">Both</form:option>
                    </form:select>Show dendrograms (if clustering)</li>
                </ul>
                </li>
                <li><strong>Full GO annotation</strong>
                    <ul>
                        <li> Show the <form:input path="GOnumber" type="number" min="20" max="250" step="1"/> GO terms which vary the most</li>

                    </ul>
                </li>

            </ul>
        </div>
    </div>
    <div id="final-buttons-wrapper">
        <input class="main_button" type="submit" value="Compare" name="search" id="run-button"/>
        <div id="loading"><img src="${pageContext.request.contextPath}/img/compare_load.gif"></div>
        |
        <a id="clear-all-button" type="button" onclick="ClearAll()">Clear all</a>
    </div>
</form:form>
<br>
<%--TODO: Clean up code a bit--%>

<script type="text/javascript" defer="defer">
    $(document).ready(function () {
        $('#projects').change(function()
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
        });
    });
</script>

<%--script type="text/javascript" defer="defer">//Try of return of post request with AJAX...
/*    $(document).ready(function () {
        $('#comparison-tool-form-id').submit(function(event)
        {
            var requestText = $("#comparison-tool-form-id").serialize();
            alert(requestText);
            $.ajax({
                url:jQuery(this).attr("action"),
                type:"POST",
                data:requestText,
                success:function (response) {
                    alert("Hi");
                    $("#demo").html("Hey coucou toi.");
                },
                error:function (jqXHR, textStatus, errorThrown) {
                    alert("Request failed: " + textStatus);
                }
            });//end ajax method
//            alert(studyId);
        });
    });*/

</script--%>

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
    // Visual details and various utility javascript
    $('#sample-choice').hide();
    // Select all samples in the sample selection box
    function SelectAllSamples() {
        $('#samples option').each(function () {
            $(this).attr('selected', true);
        });
    }

    // Unselect all samples in the sample selection box
    function UnselectAllSamples() {
        $('#samples option').each(function () {
            $(this).attr('selected', false);
        });
    }

    // Clear all : deselect project, clear sample selection box, clear all settings.
    function ClearAll() {
        // Settings first
        $('#vis-choice').val('nothing').trigger('change');
        $('#data-choice').val('nothing').trigger('change');
        // Samples emptying
        $('#samples').empty();
        // Now the project
        $('#projects option').attr('selected', false);

    }
    // Replace too long text with a shorter one + '...'
    var maxLength = 90;

    // Replace long text by substring + dots in both project and sample select
    $("#projects option").each(function()
            {
                if ($(this).text().length > maxLength) {
                    $(this).text($(this).text().substring(0, (maxLength - 3)) + "...");
                }
                else {
                    $(this).text($(this).text());
                }
            });

    $("#samples option").each(function()
    {
        if ($(this).text().length > maxLength) {
            $(this).text($(this).text().substring(0, (maxLength - 3)) + "...");
        }
        else {
            $(this).text($(this).text());
        }
    });

    // Loading div when AJAX request is requested (useless at the moment because not using any ajax request).
    $('#loading').hide(); //initially hide the loading icon

    $('#loading').ajaxStart(function(){
        $(this).show();
        //console.log('shown');
    });
    $("#loading").ajaxStop(function(){
        $(this).hide();
        //  console.log('hidden');
    });
    // Selected samples number
    $('#samples-control').click(function() {
        var numberSelected = $('#samples :selected').length;
        document.getElementById("selected-samples").innerHTML = "Sample list (" + numberSelected + " selected)";
    });
    $('#samples').change(function() {
        var numberSelected = $('#samples :selected').length;
        document.getElementById("selected-samples").innerHTML = "Sample list (" + numberSelected + " selected)";
    });

    // Filter options of visualization according to the chosen data STILL TO DO
    $('#data-choice').change(function() {
        var selectedData = $('#data-choice').children(":selected").attr("value");
        if (selectedData === "nothing") {
           // $("#vis-choice option[value='table']").prop("disabled", true);
        }
        else {

        }
    });

    // Show / hide advanced settings
    $(function(){
        $('#advanced-settings-div').hide();
        $('#advanced-settings-link').click(function() {
            $('#advanced-settings-div').toggle(200);
        });
    });
</script>