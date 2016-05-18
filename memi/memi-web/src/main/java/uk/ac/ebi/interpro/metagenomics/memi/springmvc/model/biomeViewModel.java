package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.BiomeDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch.EBISampleSearchResults;

import java.util.List;

/**
 * Created by maq on 17/03/2016.
 */
public class BiomeViewModel extends ViewModel {
    private final static Log log = LogFactory.getLog(BiomeViewModel.class);

    BiomeDAO biomeDAO;

    /**
     * Please notice to use this name for all the different model types. Otherwise the main menu would not work
     * fine.
     */
    public final static String MODEL_ATTR_NAME = "model";
    public static final String MODEL_COUNTER = "biome_counter";

    public BiomeViewModel(Submitter submitter,
                          EBISearchForm ebiSearchForm,
                          String pageTitle,
                          List<Breadcrumb> breadcrumbs,
                          MemiPropertyContainer propertyContainer,
                          BiomeDAO biomeDAO) {
        super(submitter, ebiSearchForm, pageTitle, breadcrumbs, propertyContainer);
        this.biomeDAO = biomeDAO;
//        List tmp = biomeDAO.countProjects();
//        log.info("BiomeDAO: " + tmp + "...");
    }

    public BiomeDAO getBiomeDAO() {
        return biomeDAO;
    }

    public void setBiomeDAO(BiomeDAO biomeDAO) {
        this.biomeDAO = biomeDAO;
    }

}
