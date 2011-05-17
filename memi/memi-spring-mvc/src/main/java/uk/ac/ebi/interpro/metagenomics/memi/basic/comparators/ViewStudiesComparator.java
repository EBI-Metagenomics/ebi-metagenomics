package uk.ac.ebi.interpro.metagenomics.memi.basic.comparators;

import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.Comparator;

/**
 * Used to order studies by name within ViewStudies page {@link uk.ac.ebi.interpro.metagenomics.memi.controller.ViewStudiesController}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class ViewStudiesComparator implements Comparator<Study> {

    /**
     * Compares samples by name. If names are equal then it compares by study identifiers, which should be usually unique.
     */
    @Override
    public int compare(Study study1, Study study2) {
        String studyName1 = study1.getStudyName();
        String studyName2 = study2.getStudyName();
        int studyNameComp = studyName1.compareTo(studyName2);
        long studyIdDiff = study1.getId() - study2.getId();
        int studyIdComp = 0;
        if (studyIdDiff > 0) {
            studyIdComp = 1;
        } else if (studyIdDiff < 0) {
            studyIdComp = -1;
        }
        return ((studyNameComp == 0) ? studyIdComp : studyNameComp);
    }
}
