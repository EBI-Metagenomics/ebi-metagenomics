package uk.ac.ebi.interpro.metagenomics.memi.core.comparators;

import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Publication;

import java.util.Comparator;

/**
 * Compares 2 publications by title.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class PublicationComparator implements Comparator<Publication> {
    @Override
    public int compare(Publication o1, Publication o2) {
        return o1.getPubTitle().compareTo(o2.getPubTitle());
    }
}
