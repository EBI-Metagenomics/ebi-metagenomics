package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

/**
 * Mainly used to define downloable and analysis result files. Result file definitions contain file properties like relative path, identifier, descriptions etc,
 * <p/>
 * All of them, which are used in this app, are configured and instantiated via Spring.
 * <p/>
 * Example configuration:
 * <bean id="kingdomCountsFile" autowire-candidate="false"
 * class="uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.ResultFileDefinitionImpl">
 * <property name="identifier" value="KINGDOM_COUNTS_FILE"/>
 * <property name="relativePath" value="/taxonomy-summary/kingdom-counts.txt"/>
 * <property name="description"
 * value="Input file for the generation of the phylum pie/bar/column chart and table (TSV)"/>
 * </bean>
 *
 * @author Maxim Scheremetjew
 */
public interface IResultFileDefinition {

    String getRelativePath();

    String getFileNameEnding();
}
