package uk.ac.ebi.interpro.metagenomics.memi.core.comparators;

import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.Comparator;
import java.util.Date;

/**
 * Used to order studies by last meta data received date within Home page {@link uk.ac.ebi.interpro.metagenomics.memi.controller.HomePageController}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class HomePageStudiesComparator implements Comparator<Study> {

    /**
     * Compares studies by last meta data received date. Starts with the latest one!
     */
    @Override
    public int compare(Study study1, Study study2) {
        Date studyDate1 = study1.getLastMetadataReceived();
        Date studyDate2 = study2.getLastMetadataReceived();
        int studyDateComp = studyDate2.compareTo(studyDate1);
        if (studyDateComp == 0) {
            if (study1.getStudyName() != null && study2.getStudyName() != null) {
                return study1.getStudyName().compareTo(study2.getStudyName());
            }

        } else {
            return studyDateComp;
        }
        return 0;
    }
}
