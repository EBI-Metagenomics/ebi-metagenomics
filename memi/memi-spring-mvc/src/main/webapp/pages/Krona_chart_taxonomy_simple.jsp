<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <meta charset="utf-8"/>
        <link rel="shortcut icon" href="http://krona.sourceforge.net/img/favicon.ico"/>
        <script id="notfound">window.onload=function(){document.body.innerHTML="Could not get resources from \"http://krona.sourceforge.net\"."}</script>
        <script src="<c:url value="${baseURL}/js/krona-2.0-slim.js"/>"></script>
    </head>
    <body>
        <img id="hiddenImage" src="http://krona.sourceforge.net/img/hidden.png"  style="display:none"/>
        <noscript>Javascript must be enabled to view this page.</noscript>
        <div style="display:none">
        <krona collapse="true" key="true">
           <attributes magnitude="magnitude">
            <attribute display="Total">magnitude</attribute>
           </attributes>
           <datasets>
            <dataset>Arctic seawater collected on 10 March 2008</dataset>
           </datasets>
        <node name="all">
         <magnitude><val>168</val></magnitude>
         <node name="Bacteria">
          <magnitude><val>151</val></magnitude>

          <node name="Actinobacteria">
           <magnitude><val>4</val></magnitude>
           <node name="Acidimicrobiia">
            <magnitude><val>4</val></magnitude>
            <node name="Acidimicrobiales">
             <magnitude><val>4</val></magnitude>
             <node name="wb1_P06">
              <magnitude><val>1</val></magnitude>
             </node>
             <node name="ZA3409c">
              <magnitude><val>2</val></magnitude>
             </node>
             <node name="TK06">
              <magnitude><val>1</val></magnitude>
             </node>
            </node>
           </node>
          </node>

          <node name="Proteobacteria">
           <magnitude><val>95</val></magnitude>
           <node name="Alphaproteobacteria">
            <magnitude><val>35</val></magnitude>
            <node name="Rickettsiales">
             <magnitude><val>25</val></magnitude>
             <node name="Pelagibacteraceae">
              <magnitude><val>24</val></magnitude>
             </node>
             <node name="Unassigned Pelagibacteraceae">
              <magnitude><val>1</val></magnitude>
             </node>
            </node>
            <node name="Rhodospirillales">
             <magnitude><val>1</val></magnitude>
             <node name="Rhodospirillaceae">
              <magnitude><val>1</val></magnitude>
             </node>
            </node>
            <node name="Sphingomonadales">
             <magnitude><val>1</val></magnitude>
             <node name="Erythrobacteraceae">
              <magnitude><val>1</val></magnitude>
             </node>
            </node>
            <node name="Unassigned Alphaproteobacteria">
             <magnitude><val>4</val></magnitude>
            </node>
            <node name="Rhodobacterales">
             <magnitude><val>4</val></magnitude>
             <node name="Rhodobacteraceae">
              <magnitude><val>3</val></magnitude>
             </node>
             <node name="Unassigned Rhodobacterales">
              <magnitude><val>1</val></magnitude>
             </node>
            </node>
           </node>
           <node name="Gammaproteobacteria">
            <magnitude><val>35</val></magnitude>
            <node name="HTCC2188">
             <magnitude><val>4</val></magnitude>
             <node name="HTCC2188">
              <magnitude><val>4</val></magnitude>
             </node>
            </node>
            <node name="Thiohalorhabdales">
             <magnitude><val>1</val></magnitude>
             <node name="f__">
              <magnitude><val>1</val></magnitude>
             </node>
            </node>
            <node name="Alteromonadales">
             <magnitude><val>10</val></magnitude>
             <node name="Alteromonadaceae">
              <magnitude><val>2</val></magnitude>
             </node>
             <node name="OM60">
              <magnitude><val>3</val></magnitude>
             </node>
             <node name="J115">
              <magnitude><val>1</val></magnitude>
             </node>
             <node name="Moritellaceae">
              <magnitude><val>1</val></magnitude>
             </node>
             <node name="Unassigned Alteromonadales">
              <magnitude><val>3</val></magnitude>
             </node>
            </node>
            <node name="Legionellales">
             <magnitude><val>1</val></magnitude>
             <node name="Coxiellaceae">
              <magnitude><val>1</val></magnitude>
             </node>
            </node>
            <node name="Xanthomonadales">
             <magnitude><val>1</val></magnitude>
            </node>
            <node name="Chromatiales">
             <magnitude><val>1</val></magnitude>
            </node>
            <node name="Oceanospirillales">
             <magnitude><val>15</val></magnitude>
             <node name="OM182">
              <magnitude><val>1</val></magnitude>
             </node>
             <node name="SUP05">
              <magnitude><val>1</val></magnitude>
             </node>
             <node name="Oceanospirillaceae">
              <magnitude><val>4</val></magnitude>
             </node>
             <node name="Unassigned Oceanospirillales">
              <magnitude><val>2</val></magnitude>
             </node>
             <node name="Halomonadaceae">
              <magnitude><val>5</val></magnitude>
             </node>
             <node name="Alcanivoracaceae">
              <magnitude><val>2</val></magnitude>
             </node>
            </node>
            <node name="Unassigned Gammaproteobacteria">
             <magnitude><val>1</val></magnitude>
            </node>
            <node name="Pseudomonadales">
             <magnitude><val>1</val></magnitude>
             <node name="Moraxellaceae">
              <magnitude><val>1</val></magnitude>
             </node>
            </node>
           </node>
           <node name="Betaproteobacteria">
            <magnitude><val>5</val></magnitude>
            <node name="Burkholderiales">
             <magnitude><val>5</val></magnitude>
             <node name="Oxalobacteraceae">
              <magnitude><val>5</val></magnitude>
             </node>
            </node>
           </node>
           <node name="Deltaproteobacteria">
            <magnitude><val>17</val></magnitude>
            <node name="Myxococcales">
             <magnitude><val>1</val></magnitude>
             <node name="OM27">
              <magnitude><val>1</val></magnitude>
             </node>
            </node>
            <node name="Desulfobacterales">
             <magnitude><val>7</val></magnitude>
             <node name="Nitrospinaceae">
              <magnitude><val>7</val></magnitude>
             </node>
            </node>
            <node name="Sva0853">
             <magnitude><val>9</val></magnitude>
             <node name="SAR324">
              <magnitude><val>7</val></magnitude>
             </node>
             <node name="Unassigned Deltaproteobacteria">
              <magnitude><val>1</val></magnitude>
             </node>
             <node name="Unassigned Sva0853">
              <magnitude><val>1</val></magnitude>
             </node>
            </node>
           </node>
           <node name="Unassigned Proteobacteria">
            <magnitude><val>3</val></magnitude>
           </node>
          </node>

          <node name="Bacteroidetes">
           <magnitude><val>12</val></magnitude>
           <node name="Sphingobacteriia">
            <magnitude><val>1</val></magnitude>
            <node name="Sphingobacteriales">
             <magnitude><val>1</val></magnitude>
             <node name="Ekhidnaceae">
              <magnitude><val>1</val></magnitude>
             </node>
            </node>
           </node>
           <node name="Flavobacteriia">
            <magnitude><val>10</val></magnitude>
            <node name="Flavobacteriales">
             <magnitude><val>9</val></magnitude>
             <node name="Flavobacteriaceae">
              <magnitude><val>8</val></magnitude>
             </node>
             <node name="Unassigned Flavobacteriales">
              <magnitude><val>1</val></magnitude>
             </node>
            </node>
            <node name="Unassigned Flavobacteriia">
             <magnitude><val>1</val></magnitude>
            </node>
           </node>
           <node name="Unassigned Bacteroidetes">
            <magnitude><val>1</val></magnitude>
           </node>
          </node>

          <node name="Planctomycetes">
           <magnitude><val>1</val></magnitude>
           <node name="OM190">
            <magnitude><val>1</val></magnitude>
           </node>
          </node>

          <node name="Chloroflexi">
           <magnitude><val>2</val></magnitude>
           <node name="SAR202">
            <magnitude><val>1</val></magnitude>
           </node>
           <node name="Unassigned Chloroflexi">
            <magnitude><val>1</val></magnitude>
           </node>
          </node>

          <node name="Cyanobacteria">
           <magnitude><val>1</val></magnitude>
           <node name="Chloroplast">
            <magnitude><val>1</val></magnitude>
            <node name="Stramenopiles">
             <magnitude><val>1</val></magnitude>
            </node>
           </node>
          </node>

          <node name="PAUC34f">
           <magnitude><val>2</val></magnitude>
          </node>

          <node name="SAR406">
           <magnitude><val>6</val></magnitude>
           <node name="AB16">
            <magnitude><val>6</val></magnitude>
            <node name="Arctic96B-7">
             <magnitude><val>3</val></magnitude>
             <node name="A714017">
              <magnitude><val>3</val></magnitude>
             </node>
            </node>
            <node name="ZA3648c">
             <magnitude><val>2</val></magnitude>
             <node name="AEGEAN_185">
              <magnitude><val>2</val></magnitude>
             </node>
            </node>
            <node name="Unassigned AB16">
             <magnitude><val>1</val></magnitude>
            </node>
           </node>
          </node>

          <node name="Unassigned Bacteria">
           <magnitude><val>25</val></magnitude>
          </node>

          <node name="Verrucomicrobia">
           <magnitude><val>3</val></magnitude>
           <node name="Pedosphaerae">
            <magnitude><val>3</val></magnitude>
            <node name="Arctic97B-4">
             <magnitude><val>3</val></magnitude>
            </node>
           </node>
          </node>
         </node>

         <node name="Archaea">
          <magnitude><val>17</val></magnitude>
          <node name="Crenarchaeota">
           <magnitude><val>9</val></magnitude>
           <node name="Thaumarchaeota">
            <magnitude><val>9</val></magnitude>
            <node name="Cenarchaeales">
             <magnitude><val>9</val></magnitude>
             <node name="Cenarchaeaceae">
              <magnitude><val>9</val></magnitude>
             </node>
            </node>
           </node>
          </node>
          <node name="Unassigned Archaea">
           <magnitude><val>2</val></magnitude>
          </node>
          <node name="Euryarchaeota">
           <magnitude><val>6</val></magnitude>
           <node name="Thermoplasmata">
            <magnitude><val>6</val></magnitude>
            <node name="E2">
             <magnitude><val>6</val></magnitude>
             <node name="Marine group III">
              <magnitude><val>2</val></magnitude>
             </node>
             <node name="Marine group II">
              <magnitude><val>4</val></magnitude>
             </node>
            </node>
           </node>
          </node>
         </node>
        </node>
          </krona></div>
    </body>
</html>
