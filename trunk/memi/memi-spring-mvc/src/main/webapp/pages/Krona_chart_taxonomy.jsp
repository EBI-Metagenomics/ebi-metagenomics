<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <meta charset="utf-8"/>
        <link rel="shortcut icon" href="http://krona.sourceforge.net/img/favicon.ico"/>
        <script id="notfound">window.onload=function(){document.body.innerHTML="Could not get resources from \"http://krona.sourceforge.net\"."}</script>
        <script src="<c:url value="${baseURL}/js/krona-2.0-taxonomy.js"/>"></script>
    </head>
    <body>
        <img id="hiddenImage" src="http://krona.sourceforge.net/img/hidden.png"  style="display:none"/>
        <noscript>Javascript must be enabled to view this page.</noscript>
        <div style="display:none">
        <krona collapse="false" key="true">
        <attributes magnitude="match">
          <attribute display="e-conf">econf</attribute>
          <attribute display="match">match</attribute>
        </attributes>

        <color attribute="econf" valueStart="0.1" valueEnd="1" hueStart="120" hueEnd="240"></color>

        <datasets>
        <dataset>Krona_Arctic_seawater_econf</dataset>
        </datasets>

        <node name="all">
         <match><val>229</val></match>
         <econf><val></val></econf>



        <node name="Bacteria">
        <match><val>201</val></match>
        <econf><val>0.712412804</val></econf>


          <node name="Actinobacteria">
           <match><val>10</val></match>
           <econf><val>0.797833333</val></econf>

           <node name="Actinobacteria">
            <match><val>5</val></match>
            <econf><val>0.408</val></econf>
            <node name="Actinomycetales">
             <match><val>5</val></match>
             <econf><val>0.408</val></econf>
             <node name="Cryptosporangiaceae">
              <match><val>5</val></match>
              <econf><val>0.408</val></econf>
             </node>
            </node>
           </node>

           <node name="Acidimicrobiia">
            <match><val>5</val></match>
            <econf><val>0.927777778</val></econf>
            <node name="Acidimicrobiales">
             <match><val>5</val></match>
             <econf><val>0.927777778</val></econf>

             <node name="wb1_P06">
             <match><val>1</val></match>
             <econf><val>0.96</val></econf>
             </node>
             <node name="ZA3409c">
             <match><val>3</val></match>
             <econf><val>0.833333333</val></econf>
             </node>
             <node name="TK06">
             <match><val>1</val></match>
             <econf><val>0.99</val></econf>
             </node>

            </node>
           </node>

          </node>



          <node name="Proteobacteria">
           <match><val>146</val></match>
           <econf><val>0.779097085</val></econf>

           <node name="Alphaproteobacteria">
            <match><val>61</val></match>
            <econf><val>0.770761201</val></econf>
            <node name="Rickettsiales">
             <match><val>52</val></match>
             <econf><val>0.706776136</val></econf>

             <node name="Pelagibacteraceae">
             <match><val>29</val></match>
             <econf><val>0.954137931</val></econf>
             </node>

             <node name="mitochondria">
             <match><val>21</val></match>
             <econf><val>0.626190476</val></econf>
             </node>

             <node name="Unassigned Rickettsiales">
              <match><val>2</val></match>
              <econf><val>0.54</val></econf>
             </node>
            </node>

            <node name="Rhodospirillales">
             <match><val>4</val></match>
             <econf><val>0.695</val></econf>
            <node name="Rhodospirillaceae">
              <match><val>4</val></match>
              <econf><val>0.695</val></econf>
             </node>
            </node>

            <node name="Sphingomonadales">
             <match><val>2</val></match>
             <econf><val>0.885</val></econf>

             <node name="Erythrobacteraceae">
              <match><val>1</val></match>
              <econf><val>0.89</val></econf>
             </node>

             <node name="Unassigned Sphingomonadales">
              <match><val>1</val></match>
              <econf><val>0.88</val></econf>
             </node>

            </node>

            <node name="Rhodobacterales">
             <match><val>3</val></match>
             <econf><val>0.81</val></econf>
             <node name="Rhodobacteraceae">
              <match><val>3</val></match>
              <econf><val>0.81</val></econf>
             </node>
            </node>

           </node>
           <node name="Gammaproteobacteria">
            <match><val>43</val></match>
            <econf><val>0.813157895</val></econf>

            <node name="HTCC2188">
             <match><val>3</val></match>
             <econf><val>0.99</val></econf>
             <node name="HTCC2188">
              <match><val>3</val></match>
              <econf><val>0.99</val></econf>
             </node>
            </node>

            <node name="Thiohalorhabdales">
             <match><val>1</val></match>
             <econf><val>1</val></econf>
            </node>

            <node name="Alteromonadales">
             <match><val>14</val></match>
             <econf><val>0.792666667</val></econf>

             <node name="Alteromonadaceae">
              <match><val>6</val></match>
              <econf><val>0.868333333</val></econf>
             </node>

             <node name="OM60">
              <match><val>3</val></match>
              <econf><val>1</val></econf>
             </node>

             <node name="J115">
              <match><val>2</val></match>
              <econf><val>0.675</val></econf>
             </node>

             <node name="Moritellaceae">
              <match><val>2</val></match>
              <econf><val>0.91</val></econf>
             </node>

             <node name="Unassigned Alteromonadales">
              <match><val>1</val></match>
              <econf><val>0.51</val></econf>
             </node>

            </node>

            <node name="Legionellales">
             <match><val>2</val></match>
             <econf><val>0.45</val></econf>

             <node name="Coxiellaceae">
              <match><val>1</val></match>
              <econf><val>0.77</val></econf>
             </node>
             <node name="Endoecteinascidiaceae">
              <match><val>1</val></match>
              <econf><val>0.13</val></econf>
             </node>

            </node>

            <node name="Xanthomonadales">
             <match><val>1</val></match>
             <econf><val>0.75</val></econf>
            </node>

            <node name="Chromatiales">
             <match><val>1</val></match>
             <econf><val>0.89</val></econf>
            </node>

            <node name="Acidithiobacillales">
             <match><val>5</val></match>
             <econf><val>0.4</val></econf>
            </node>

            <node name="Oceanospirillales">
             <match><val>15</val></match>
             <econf><val>0.926111111</val></econf>

             <node name="OM182">
              <match><val>1</val></match>
              <econf><val>1</val></econf>
             </node>

             <node name="Unassigned Oceanospirillales">
              <match><val>3</val></match>
              <econf><val>0.946666667</val></econf>
             </node>

             <node name="SUP05">
              <match><val>1</val></match>
              <econf><val>0.82</val></econf>
             </node>

             <node name="Oceanospirillaceae">
              <match><val>4</val></match>
              <econf><val>0.9825</val></econf>
             </node>

             <node name="Halomonadaceae">
              <match><val>4</val></match>
              <econf><val>0.8425</val></econf>
             </node>

             <node name="Alcanivoracaceae">
              <match><val>2</val></match>
              <econf><val>0.965</val></econf>
             </node>

            </node>

            <node name="Pseudomonadales">
             <match><val>1</val></match>
             <econf><val>1</val></econf>
             <node name="Moraxellaceae">
              <match><val>1</val></match>
              <econf><val>1</val></econf>
             </node>
            </node>

           </node>

           <node name="Betaproteobacteria">
            <match><val>15</val></match>
            <econf><val>0.673666667</val></econf>

            <node name="Methylophilales">
             <match><val>1</val></match>
             <econf><val>1</val></econf>
             <node name="Methylophilaceae">
              <match><val>1</val></match>
              <econf><val>1</val></econf>
             </node>
            </node>

            <node name="Burkholderiales">
             <match><val>6</val></match>
             <econf><val>0.976666667</val></econf>
             <node name="Oxalobacteraceae">
              <match><val>6</val></match>
              <econf><val>0.976666667</val></econf>
             </node>
            </node>

            <node name="A21b">
             <match><val>5</val></match>
             <econf><val>0.268</val></econf>
            </node>

            <node name="Procabacteriales">
             <match><val>3</val></match>
             <econf><val>0.45</val></econf>
            </node>
           </node>

           <node name="Deltaproteobacteria">
            <match><val>26</val></match>
            <econf><val>0.7655</val></econf>

            <node name="Myxococcales">
             <match><val>1</val></match>
             <econf><val>0.97</val></econf>
             <node name="OM27">
              <match><val>1</val></match>
              <econf><val>0.97</val></econf>
             </node>
            </node>

            <node name="Desulfobacterales">
             <match><val>11</val></match>
             <econf><val>0.559583333</val></econf>

             <node name="Nitrospinaceae">
              <match><val>8</val></match>
              <econf><val>0.9825</val></econf>
             </node>
             <node name="Unassigned Desulfobacterales">
              <match><val>3</val></match>
              <econf><val>0.136666667</val></econf>
             </node>

            </node>

            <node name="Sva0853">
             <match><val>14</val></match>
             <econf><val>0.869166667</val></econf>

             <node name="SAR324">
              <match><val>12</val></match>
              <econf><val>0.903333333</val></econf>
             </node>
             <node name="Unassigned Sva0853">
              <match><val>2</val></match>
              <econf><val>0.835</val></econf>
             </node>

            </node>

           </node>

           <node name="Unassigned Proteobacteria">
            <match><val>1</val></match>
            <econf><val>0.68</val></econf>
           </node>

          </node>



          <node name="Bacteroidetes">
           <match><val>11</val></match>
           <econf><val>0.9946875</val></econf>

           <node name="Sphingobacteriia">
            <match><val>1</val></match>
            <econf><val>1</val></econf>
            <node name="Sphingobacteriales">
             <match><val>1</val></match>
             <econf><val>1</val></econf>
             <node name="Ekhidnaceae">
              <match><val>1</val></match>
              <econf><val>1</val></econf>
             </node>
            </node>
           </node>

           <node name="Flavobacteriia">
            <match><val>10</val></match>
            <econf><val>0.992916667</val></econf>

            <node name="Flavobacteriales">
             <match><val>9</val></match>
             <econf><val>0.989375</val></econf>

             <node name="Flavobacteriaceae">
              <match><val>8</val></match>
              <econf><val>0.97875</val></econf>
             </node>
             <node name="Unassigned Flavobacteriales">
              <match><val>1</val></match>
              <econf><val>1</val></econf>
             </node>
            </node>

            <node name="Unassigned Flavobacteriia">
             <match><val>1</val></match>
             <econf><val>1</val></econf>
            </node>
           </node>
          </node>

          <node name="Planctomycetes">
           <match><val>2</val></match>
           <econf><val>0.65</val></econf>
           <node name="OM190">
            <match><val>1</val></match>
            <econf><val>1</val></econf>
           </node>
           <node name="Planctomycetia">
            <match><val>1</val></match>
            <econf><val>0.3</val></econf>
           </node>
          </node>




          <node name="Firmicutes">
           <match><val>1</val></match>
           <econf><val>0.22</val></econf>
           <node name="Clostridia">
            <match><val>1</val></match>
            <econf><val>0.22</val></econf>
            <node name="Clostridiales">
             <match><val>1</val></match>
             <econf><val>0.22</val></econf>
             <node name="Carboxydocellaceae">
              <match><val>1</val></match>
              <econf><val>0.22</val></econf>
             </node>
            </node>
           </node>
          </node>


          <node name="Chloroflexi">
           <match><val>3</val></match>
           <econf><val>0.416666667</val></econf>

           <node name="SAR202">
            <match><val>1</val></match>
            <econf><val>1</val></econf>
           </node>

           <node name="Anaerolineae">
            <match><val>1</val></match>
            <econf><val>0.15</val></econf>
            <node name="Anaerolineales">
             <match><val>1</val></match>
             <econf><val>0.15</val></econf>
            </node>

           </node>

           <node name="Thermomicrobia">
            <match><val>1</val></match>
            <econf><val>0.1</val></econf>
            <node name="Sphaerobacterales">
             <match><val>1</val></match>
             <econf><val>0.1</val></econf>
            </node>
           </node>

          </node>



          <node name="Cyanobacteria">
           <match><val>1</val></match>
           <econf><val>1</val></econf>
           <node name="Chloroplast">
            <match><val>1</val></match>
            <econf><val>1</val></econf>
            <node name="Stramenopiles">
             <match><val>1</val></match>
             <econf><val>1</val></econf>
            </node>
           </node>
          </node>

          <node name="PAUC34f">
           <match><val>2</val></match>
           <econf><val>0.915</val></econf>
          </node>

          <node name="Unassigned bacteria">
           <match><val>1</val></match>
           <econf><val>0.98</val></econf>
          </node>

          <node name="SAR406">
           <match><val>11</val></match>
           <econf><val>0.643888889</val></econf>
           <node name="AB16">
            <match><val>11</val></match>
            <econf><val>0.643888889</val></econf>

            <node name="Arctic96B-7">
             <match><val>6</val></match>
             <econf><val>0.965</val></econf>
             <node name="A714017">
              <match><val>6</val></match>
              <econf><val>0.965</val></econf>
             </node>
            </node>

            <node name="ZA3648c">
             <match><val>3</val></match>
             <econf><val>0.846666667</val></econf>
             <node name="AEGEAN_185">
              <match><val>3</val></match>
              <econf><val>0.846666667</val></econf>
             </node>
            </node>

            <node name="SSW63Au">
             <match><val>2</val></match>
             <econf><val>0.12</val></econf>
            </node>
           </node>
          </node>

          <node name="OP11">
           <match><val>1</val></match>
           <econf><val>0.18</val></econf>
          </node>

          <node name="Caldiserica">
           <match><val>1</val></match>
           <econf><val>0.1</val></econf>
          </node>

          <node name="Elusimicrobia">
           <match><val>1</val></match>
           <econf><val>0.36</val></econf>
           <node name="Elusimicrobia">
            <match><val>1</val></match>
            <econf><val>0.36</val></econf>
           </node>
          </node>

          <node name="NC10">
           <match><val>3</val></match>
           <econf><val>0.1475</val></econf>

           <node name="12-24">
            <match><val>3</val></match>
            <econf><val>0.1475</val></econf>

            <node name="Unassigned 12-24">
             <match><val>1</val></match>
             <econf><val>0.11</val></econf>
            </node>

            <node name="MIZ17">
             <match><val>2</val></match>
             <econf><val>0.185</val></econf>
            </node>

           </node>
          </node>

         <node name="Verrucomicrobia">
           <match><val>7</val></match>
           <econf><val>0.654</val></econf>

           <node name="Opitutae">
            <match><val>2</val></match>
            <econf><val>0.85</val></econf>

            <node name="Puniceicoccales">
             <match><val>1</val></match>
             <econf><val>0.95</val></econf>
             <node name="Puniceicoccaceae">
              <match><val>1</val></match>
              <econf><val>0.95</val></econf>
             </node>
            </node>

            <node name="Opitutales">
             <match><val>1</val></match>
             <econf><val>0.75</val></econf>
             <node name="Opitutaceae">
              <match><val>1</val></match>
              <econf><val>0.75</val></econf>
             </node>
            </node>

           </node>

           <node name="Verruco-5">
            <match><val>1</val></match>
            <econf><val>0.16</val></econf>
           </node>

           <node name="Methylacidiphilae">
            <match><val>1</val></match>
            <econf><val>0.52</val></econf>
           </node>

           <node name="Pedosphaerae">
            <match><val>3</val></match>
            <econf><val>0.89</val></econf>
            <node name="Arctic97B-4">
             <match><val>3</val></match>
             <econf><val>0.89</val></econf>
            </node>
           </node>
          </node>

         </node>



         <node name="Archaea">
          <match><val>28</val></match>
          <econf><val>0.609468864</val></econf>

          <node name="Crenarchaeota">
           <match><val>17</val></match>
           <econf><val>0.587094017</val></econf>

           <node name="Thaumarchaeota">
            <match><val>16</val></match>
            <econf><val>0.815641026</val></econf>
            <node name="Cenarchaeales">
             <match><val>16</val></match>
             <econf><val>0.815641026</val></econf>

             <node name="Cenarchaeaceae">
              <match><val>13</val></match>
              <econf><val>0.994615385</val></econf>
             </node>
             <node name="Unassigned Cenarchaeales">
              <match><val>3</val></match>
              <econf><val>0.636666667</val></econf>
             </node>

            </node>
           </node>

           <node name="Korarchaeota">
            <match><val>1</val></match>
            <econf><val>0.13</val></econf>
           </node>

          </node>

          <node name="Euryarchaeota">
           <match><val>11</val></match>
           <econf><val>0.62625</val></econf>

           <node name="Methanopyri">
            <match><val>1</val></match>
            <econf><val>0.21</val></econf>
            <node name="Methanopyrales">
             <match><val>1</val></match>
             <econf><val>0.21</val></econf>
             <node name="Methanopyraceae">
              <match><val>1</val></match>
              <econf><val>0.21</val></econf>
             </node>
            </node>
           </node>

           <node name="Methanococci">
            <match><val>4</val></match>
            <econf><val>0.295</val></econf>
           </node>

           <node name="Thermoplasmata">
            <match><val>6</val></match>
            <econf><val>1</val></econf>
            <node name="E2">
             <match><val>6</val></match>
             <econf><val>1</val></econf>

             <node name="Marine group III">
              <match><val>2</val></match>
              <econf><val>1</val></econf>
             </node>
             <node name="Marine group II">
              <match><val>4</val></match>
              <econf><val>1</val></econf>

             </node>
            </node>
           </node>
          </node>
         </node>


        </node>
        </krona></div>
    </body>
</html>
