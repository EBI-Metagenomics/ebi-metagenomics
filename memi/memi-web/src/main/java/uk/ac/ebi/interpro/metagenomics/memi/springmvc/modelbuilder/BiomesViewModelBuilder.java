package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.BiomeDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Biome;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.BiomeViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.UserManager;

import java.util.List;

/**
 * Model builder class for ViewModel (default). See {@link ViewModelBuilder} for more information of how to use.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class BiomesViewModelBuilder extends AbstractViewModelBuilder<BiomeViewModel> {

    private final static Log log = LogFactory.getLog(DefaultViewModelBuilder.class);

    private String pageTitle;

    private BiomeDAO biomeDAO;

    private List<Breadcrumb> breadcrumbs;

    private MemiPropertyContainer propertyContainer;

    public BiomesViewModelBuilder(UserManager sessionMgr, EBISearchForm ebiSearchForm, String pageTitle,
                                  List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer, BiomeDAO biomeDAO) {
        super(sessionMgr, ebiSearchForm);
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.propertyContainer = propertyContainer;
        this.biomeDAO = biomeDAO;
    }

    @Override
    public BiomeViewModel getModel() {
        log.info("Building instance of " + BiomeViewModel.class + "...");
        return new BiomeViewModel(
                getSessionSubmitter(sessionMgr),
                getEbiSearchForm(),
                pageTitle,
                breadcrumbs,
                propertyContainer,
                biomeDAO);
    }
}