<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- EBeye search box-->
<div class="grid_12 omega">
    <form id="local-search" name="local-search" action="<c:url value="/search" />" method="get">
        <fieldset>

            <div class="left">
                <label>
                    <input type="text" name="q" id="local-searchbox" onblur="displaySearchInterPro(this);"
                           onfocus="hideSearchInterPro(this);" value="${userSessionData.currentQuery}"/>
                </label>

                <!-- Include some example searchterms -->
            <span class="examples">Examples: <a href="<c:url value="/search?q=SRS259549"/>"
                                                title="Sample accession (e.g. SRS259549)">SRS259549</a>
                , <a href="<c:url value="/search?q=SRP000319"/>" title="Study accession (e.g. SRP000319)">SRP000319</a>, <a
                        href="<c:url value="/search?q=gut"/>" title="Free text search (e.g. gut)">gut</a>, <a
                        href="<c:url value="/search?q=TAXONOMY:(885331+OR+1325974+OR+256318+OR+410656+OR+410661+OR+410662+OR+410657+OR+410658+OR+410659+OR+408169+OR+408170+OR+408172+OR+412754+OR+412755+OR+412756+OR+412757+OR+433724+OR+433733+OR+433727+OR+447426+OR+444079+OR+449393+OR+452919+OR+408171+OR+471232+OR+483425+OR+496922+OR+496923+OR+496924+OR+496920+OR+496921+OR+506600+OR+506599+OR+527639+OR+527640+OR+539654+OR+539655+OR+540485+OR+556182+OR+530537+OR+1263966+OR+662107+OR+646099+OR+652676+OR+655179+OR+703336+OR+702656+OR+797283+OR+718289+OR+718308+OR+717931+OR+1327954+OR+749906+OR+749907+OR+870726+OR+1311626+OR+904678+OR+903395+OR+910258+OR+942017+OR+938273+OR+939928+OR+1218275+OR+1326787+OR+1006967+OR+1009879+OR+1041057+OR+1082480+OR+1070537+OR+1070528+OR+1077529+OR+1077528+OR+1077527+OR+1076179+OR+1076140+OR+1163772+OR+1115523+OR+1118232+OR+1131769+OR+1154581+OR+1130098+OR+1204294+OR+1202446+OR+1198048+OR+1169740+OR+1338477+OR+1176744+OR+1214127+OR+1213622+OR+1235509+OR+1331678+OR+1227552+OR+1243988+OR+1236744+OR+1234904+OR+1263854+OR+1256227+OR+1260732+OR+1269027+OR+1249455+OR+1284368+OR+1284369+OR+1306859+OR+1297885+OR+1297858+OR+1300146+OR+1292099+OR+1319833+OR+1303196+OR+1346744+OR+1342751+OR+1335591)"/>"
                        title="All metagenomes">All metagenomes</a></span>
            </div>
            <div class="right">
                <input class="submit" id="searchsubmit" title="Search InterPro" type="submit" value="Search"/>

            </div>
        </fieldset>
    </form>
</div>
<!-- /EBeye search box -->