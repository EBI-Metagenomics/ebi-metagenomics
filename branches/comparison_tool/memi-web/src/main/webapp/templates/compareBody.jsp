<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="sample_comp">
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
                <div id="description-content">
                    <i>Select a project in the menu above.</i>
                </div>
            </div>
        </div>

            <div id="samples-div">
                <h4 id="selected-samples">Sample list</h4>
                <%-- Is the loading icon necessary? quite fast to load samples in the box
                <div id="loading"><img src="${pageContext.request.contextPath}/img/compare_load.gif"></div>--%>
                <form:select path="samples" multiple="true" size="9" id="samples" style="width:100%;">
                </form:select>
                <div id="samples-control"><a id="select-all-button" onclick="SelectAllSamples()">Select all</a> | <a id="unselect-all-button" onclick="UnselectAllSamples()">Unselect all </a></div>
            </div>
    </div>

    <div id="settings-wrapper">
        <div class="settings-div-data">
            <h4>Data</h4>
            <form:select path="usedData" id="data-choice">
                <form:option value="nothing">Select data</form:option>
                <form:option value="GOslim">GO slim annotation</form:option>
                <form:option value="GO">Full GO annotation</form:option>
                <%--<form:option value="IPR" disabled="true">InterPro matches</form:option>--%>
                <%--<form:option value="IPRcol" disabled="true">InterPro slim matches</form:option>--%>
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
        <div class="settings-div-adv">
            <h4>Advanced settings</h4>
            <a id="advanced-settings-link">Show / hide advanced settings</a>
            <!-- Advanced settings div, that should be hidden at the beginning -->
        </div>


    </div>
    <div id="advanced-settings-wrapper" title="Advanced settings">
                <ul>
                    <%--<li><strong>General</strong>--%>
                    <%--<ul>--%>
                        <%--<li><form:checkbox path="keepNames" title="Check to keep sample ID"/><strong>Keep samples ID?</strong></li>--%>
                    <%--</ul></li>--%>
                    <li><strong>Stacked columns</strong>
                        <ul>
                            <li>Stacking threshold (%): <form:input path="stackThreshold" type="number" min="0" max="50" step="0.1" value="1"/> </li>
                        </ul>
                    </li>
                    <li><strong>Heatmap</strong><ul>
                        <li>Show legend <form:checkbox path="hmLegend"/></li>
                        <li>Hierarchical clustering: <form:select path="hmClust" id="heat-clust">
                            <form:option value="none">No clustering</form:option>
                            <form:option value="row">Observations (X axis)</form:option>
                            <form:option value="column">Samples (Y axis)</form:option>
                            <form:option value="both">Both</form:option>
                        </form:select></li>
                        <li>Show dendrograms (if clustering): <form:select path="hmDendrogram" id="heat-dendo">
                            <form:option value="none">No dendrograms</form:option>
                            <form:option value="row">Observations (X axis)</form:option>
                            <form:option value="column">Samples (Y axis)</form:option>
                            <form:option value="both">Both</form:option>
                        </form:select></li>
                    </ul>
                    </li>
                    <li><strong>Full GO annotation</strong>
                        <ul>
                            <li> Show the <form:input path="GOnumber" type="number" min="20" max="250" step="1"/> GO terms which vary the most</li>

                        </ul>
                    </li>

                </ul>
            </div>
    <div id="buttons-wrapper">
        <input class="main_button" type="submit" value="Compare" name="search" id="run-button"/>

        |
        <a id="clear-all-button" type="button" onclick="ClearAll()">Clear all</a>
    </div>
</form:form>

</div>

<%--TODO: Clean up code a bit--%>

<script type="text/javascript" defer="defer">
    $(document).ready(function () {
        $('#projects').change(function()
        {
            UnselectAllSamples();
            var numberSelected = $('#samples :selected').length;
            var studyId = $('#projects').val();
            $.ajax({
                url:"<c:url value="${baseURL}/compare/samples"/>",
                type:"GET",
                cache:false,
                data:{studyId:studyId},
                success:function (data) {
                    $("#samples").html(data);
                    var numberTotal = $('#samples option').length;
                    document.getElementById("selected-samples").innerHTML = "Sample list (" + numberSelected + " selected out of " + numberTotal + ")";
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
        $('#heat-clust').val('nothing').trigger('change');
        $('#heat-dendo').val('nothing').trigger('change');
        // Samples emptying
        $('#samples').empty();
        // Now the project
        $('#projects option').attr('selected', false);

    }

    // Order text of the samples select tag alphabetically.
    function SortSamplesByText() {

        $('#samples').each(function() {
            var selectedValue = $(this).val();
            // Sort all the options by text. It could easily be sorted by value.
            $(this).html($('option', $(this)).sort(function(a, b) {
                return a.text.toUpperCase() == b.text.toUpperCase() ? 0 : a.text.toUpperCase() < b.text.toUpperCase() ? -1 : 1
            }));
            // Select one option
            $(this).val(selectedValue);
        });
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
       // $('#samples').hide();
        //console.log('shown');
    });
    $("#loading").ajaxStop(function(){
        $(this).hide();
       // $('#samples').show();
        //  console.log('hidden');
    });
    // Selected samples number
    $('#samples-control').click(function() {
        var numberSelected = $('#samples :selected').length;
        var numberTotal = $('#samples option').length;
        document.getElementById("selected-samples").innerHTML = "Sample list (" + numberSelected + " selected out of " + numberTotal + ")";
    });
    $('#samples').change(function() {
        var numberSelected = $('#samples :selected').length;
        var numberTotal = $('#samples option').length;
        document.getElementById("selected-samples").innerHTML = "Sample list (" + numberSelected + " selected out of " + numberTotal + ")";
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
        $('#advanced-settings-wrapper').hide();
        $('#advanced-settings-link').click(function() {
            $('#advanced-settings-wrapper').slideToggle(200);
        });
    });
</script>