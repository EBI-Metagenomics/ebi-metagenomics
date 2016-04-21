package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.BiomeDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.StudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Biome;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.UserManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: maxim
 * Date: 7/14/15
 * Time: 12:36 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractBiomeViewModelBuilder<E extends ViewModel> extends AbstractViewModelBuilder<E> {
    protected AbstractBiomeViewModelBuilder(UserManager sessionMgr, EBISearchForm ebiSearchForm) {
        super(sessionMgr, ebiSearchForm);
    }

    protected List<Integer> getBiomeIdsByLineage(final BiomeDAO biomeDAO, final String... lineages) {
        List<Integer> biomeIdList = new ArrayList<Integer>();
        for (String lineage : lineages) {
            if (lineage != null && !lineage.isEmpty()) {
                final Biome biome = biomeDAO.readByLineage(lineage);
                if (biome != null) {
                    biomeIdList.addAll(biomeDAO.getListOfBiomeIdsBetween(biome.getLeft(), biome.getRight()));
                }
            }
        }
        return biomeIdList;
    }

    protected long countStudiesFilteredByBiomes(final StudyDAO studyDAO, final BiomeDAO biomeDAO, final String... lineages) {
        List<Integer> biomeIds = getBiomeIdsByLineage(biomeDAO, lineages);
        Long rowCount = studyDAO.countPublicStudiesFilteredByBiomes(biomeIds);
        return (rowCount == null ? 0L : rowCount);
    }
}