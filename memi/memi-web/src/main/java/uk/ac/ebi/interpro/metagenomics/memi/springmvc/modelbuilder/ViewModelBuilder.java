package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

/**
 * This interface allows an object of type model builder to be used in generic ways.
 * Example of how to use:<br>
 * final ViewModelBuilder<ContactViewModel> builder = new DefaultViewModelBuilder(userManager, "Metagenomics Contact", getBreadcrumbs(null), propertyContainer);
 * final ContactViewModel contactPageViewModel = builder.getModel();
 * ModelMap model = new ModelMap();
 * model.addAttribute(ViewModel.MODEL_ATTR_NAME, contactPageViewModel);
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public interface ViewModelBuilder<T> {
    T getModel();
}
