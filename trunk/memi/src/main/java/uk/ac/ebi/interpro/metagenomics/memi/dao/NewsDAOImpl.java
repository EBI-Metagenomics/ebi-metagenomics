package uk.ac.ebi.interpro.metagenomics.memi.dao;

import uk.ac.ebi.interpro.metagenomics.memi.model.News;
import uk.ac.ebi.interpro.metagenomics.memi.model.Study;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the implementation class of {@link NewsDAO}
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class NewsDAOImpl implements NewsDAO {

    public NewsDAOImpl() {
    }


    @Override
    public List<News> getLatestNews() {
        List<News> result = new ArrayList<News>();
        result.add(new News("Headline 1","message"));
        result.add(new News("Headline 2","message"));
        result.add(new News("Headline 3","message"));
        result.add(new News("Headline 4","message"));
        result.add(new News("Headline 5","message"));
        result.add(new News("Headline 6","message"));
        result.add(new News("Headline 7","message"));
        result.add(new News("Headline 8","message"));
        result.add(new News("Headline 9","message"));
        result.add(new News("Headline 10","message"));
        return result;
    }
}