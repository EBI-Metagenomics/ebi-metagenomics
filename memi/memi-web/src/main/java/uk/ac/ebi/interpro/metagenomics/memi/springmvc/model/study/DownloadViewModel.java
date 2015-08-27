package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.study;

import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;

import java.util.List;
import java.util.SortedMap;

/**
 * Download view model.
 *
 * @author Maxim Scheremetjew
 * @since 1.4-SNAPSHOT
 */
public class DownloadViewModel extends ViewModel {

    private SortedMap<String, DownloadSection> downloadSectionMap;

    private Study study;

    public DownloadViewModel(Submitter submitter,
                             String pageTitle,
                             List<Breadcrumb> breadcrumbs,
                             MemiPropertyContainer propertyContainer,
                             SortedMap<String, DownloadSection> downloadSectionMap,
                             Study study) {
        super(submitter, pageTitle, breadcrumbs, propertyContainer);
        this.downloadSectionMap = downloadSectionMap;
        this.study = study;
    }

    public SortedMap<String, DownloadSection> getDownloadSectionMap() {
        return downloadSectionMap;
    }

    public Study getStudy() {
        return study;
    }
}