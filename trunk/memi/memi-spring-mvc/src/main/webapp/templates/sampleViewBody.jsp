<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:choose>
    <c:when test="${not empty model.sample}">

    <div class="title_tab">
    <span class="subtitle">Sample <span>(${model.sample.sampleId})</span></span>
    <h2 class="fl_uppercase_title">${model.sample.sampleName}</h2>
    </div>

        <div class="sample_ana">
        <div id="navtabs">

                <%--Main Tabs--%>
            <ul>
                <li>
                    <a title="Overview"
                       href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/overview"/>"><span>Overview</span></a>
                </li>
                <li>
                    <a title="Quality-Control"
                       href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/qualityControl"/>"><span>Quality control</span></a>
                </li>
                <li><a title="Taxonomy-Analysis"
                       href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/taxonomic"/>"><span>Taxonomy analysis</span></a>
                </li>
                <li>
                    <a title="Functional-Analysis"
                       href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/functional"/>"><span>Functional analysis</span></a>
                </li>
                <li>
                    <a title="Download"
                       href="<c:url value="${baseURL}/sample/${model.sample.sampleId}/download"/>"><span>Download</span></a>
                </li>
                    <%--<li><a href="#fragment-experimental"><span>Experimental factor</span></a></li>--%>
            </ul>

        </div>
        <%--end navtabs--%>

        <script type="text/javascript">
            $(function () {
           //   IMPORTANT TO keep at bottom as it should be rendered last - anchor “jump” when loading a page from http://stackoverflow.com/questions/3659072/jquery-disable-anchor-jump-when-loading-a-page
               setTimeout(function() {
                 if (location.hash) {
//                   $('#local-masthead').fadeOut();
                   window.scrollTo(0, 0);
                 }
               }, 1000);
            });
        </script>
        <%--script for tabs--%>
        <script type="text/javascript">
            //Main navigation tabs - overview, quality control, taxonomic analysis, functional analysis, downloads
            //Ajax load approach as described here: http://jqueryui.com/tabs/#ajax
            $(function () {
                $("#navtabs").tabs({
                    cache:true,
                    ajaxOptions:{
                        error:function (xhr, status, index, anchor) {
                            $(anchor.hash).html("<div class='msg_error'>Couldn't load this tab. We'll try to fix this as soon as possible.</div>");
                        }
                    }
                });
                //Default functionality
                //Set the disable option
                $("#navtabs").tabs({${model.analysisStatus.disabledOption}});
                //Change the Hashtag on Select
                //Described here: http://imdev.in/jquery-ui-tabs-with-hashtags/
                $("#navtabs").tabs({
                    select:function (event, ui) {
                        window.location.hash = ui.tab.hash;
                    }
                });
            });
            //  Load the Visualization API and the chart package.
            google.load('visualization', '1.1', {'packages':['corechart', 'table', 'controls'] });
        </script>

    </c:when>
    <c:otherwise>
        <h3>Sample ID Not Recognised</h3>
    </c:otherwise>
</c:choose>

</div>  <%--end sample_ana--%>
<div class="but_top"><a href="#top" title="back to the top page">Top</a></div>
