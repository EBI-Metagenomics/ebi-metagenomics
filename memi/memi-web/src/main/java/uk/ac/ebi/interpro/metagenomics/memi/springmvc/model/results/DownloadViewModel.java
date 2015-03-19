package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results;

import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSampleAnnotation;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Publication;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.*;

import java.util.Collections;
import java.util.List;

/**
 * Download view model.
 *
 * @author Maxim Scheremetjew
 * @since 1.4-SNAPSHOT
 */
public class DownloadViewModel extends AbstractResultViewModel {

    private DownloadSection downloadSection;

    private Sample sample;

    public DownloadViewModel(Submitter submitter,
                             String pageTitle,
                             List<Breadcrumb> breadcrumbs,
                             MemiPropertyContainer propertyContainer,
                             DownloadSection downloadSection,
                             Sample sample) {
        super(submitter, pageTitle, breadcrumbs, propertyContainer);
        this.downloadSection = downloadSection;
        this.sample = sample;
    }

    public DownloadSection getDownloadSection() {
        return downloadSection;
    }

    public Sample getSample() {
        return sample;
    }
}