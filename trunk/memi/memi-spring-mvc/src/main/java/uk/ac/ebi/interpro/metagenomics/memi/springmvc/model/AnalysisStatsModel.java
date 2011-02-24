package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * Model to hold statistics related to the analysis of a sample.
 * <p/>
 * TODO - A bit hacked, with lots of hard-coded stuff that shouldn't be ;-)
 * TODO - revisit before beta release.
 *
 * @author Phil Jones
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class AnalysisStatsModel extends MGModel {

    public static final NumberFormat NUMBER_FORMAT = DecimalFormat.getInstance();

    private Sample sample;

    private boolean hasStats = false;

    private int totalReads;

    private int readsWithOrfs;

    private int totalOrfs;

    private int orfsWithMatches;

    private Set<MatchStatistic> goMatchStatistics = new TreeSet<MatchStatistic>();

    private Set<MatchStatistic> interProMatchStatistics = new TreeSet<MatchStatistic>();

    private final String CLASS_PATH_TO_STATS_FILE;


    AnalysisStatsModel(Submitter submitter, Sample sample, String classPathToStatsFile) {
        super(submitter);
        this.sample = sample;
        this.CLASS_PATH_TO_STATS_FILE = classPathToStatsFile;
        // TODO - Niave - just loads the static files for the JI_soil sample.
        if (sample != null && "JI_soil".equals(sample.getSampleTitle())) {
            // Set all that stats stuff.
            try {
                loadLossStats();
                loadIPRStatistics();
                loadGOStatistics();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                throw new IllegalStateException("Help!  Can't load statistics", e);
            }
        }
    }

    public Sample getSample() {
        return sample;
    }

    /**
     * TODO - Paths to files currently hard-coded.
     *
     * @throws IOException
     */
    private void loadLossStats() throws IOException {
        Resource resource = new ClassPathResource(CLASS_PATH_TO_STATS_FILE);
        // Load properties file containing overall match statistics
        Properties overallStats = new Properties();
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(resource.getInputStream());
            overallStats.load(bis);

            totalReads = Integer.parseInt(overallStats.getProperty("Total_number_of_reads"));
            readsWithOrfs = Integer.parseInt(overallStats.getProperty("Number_of_reads_with_orf"));
            totalOrfs = Integer.parseInt(overallStats.getProperty("Total_number_of_orfs"));
            orfsWithMatches = Integer.parseInt(overallStats.getProperty("Number_of_orfs_with_IPRScan_match"));

            hasStats = true;
        } finally {
            if (bis != null) {
                bis.close();
            }
        }
    }

    private void loadIPRStatistics() throws IOException {
        Resource resource = new ClassPathResource("/stats/WHEAT_RHIZOSPHERE_ME_FASTA_entry-stats");
        loadStats(interProMatchStatistics, resource);
    }

    private void loadGOStatistics() throws IOException {
        Resource resource = new ClassPathResource("/stats/WHEAT_RHIZOSPHERE_ME_FASTA_go-stats");
        loadStats(goMatchStatistics, resource);
    }

    private void loadStats(Set<MatchStatistic> stats, Resource resource) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            String line;
            Integer totalCount = null;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\t");
                if (parts.length == 2 && "#Total proteins matched:".equals(parts[0])) {
                    totalCount = new Integer(parts[1].trim());
                } else if (parts.length == 3) {
                    if (totalCount == null) {
                        throw new IllegalStateException("The statistics file " + resource.getFilename() + " does not appear to start with a 'Total proteins matched' line.");
                    }
                    stats.add(new MatchStatistic(parts, totalCount));
                }
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public boolean isHasStats() {
        return hasStats;
    }

    public int getTotalReads() {
        return totalReads;
    }

    public int getReadsWithOrfs() {
        return readsWithOrfs;
    }

    public int getTotalOrfs() {
        return totalOrfs;
    }

    public int getOrfsWithMatches() {
        return orfsWithMatches;
    }

    /*
   http://chart.apis.google.com/chart?chs=600x280&amp;cht=p3&amp;chco=cde6a8&amp;chd=t:100.0&amp;chl=Eukaryota (eucaryotes)&amp;chma=140,140

   <img alt="" src="http://chart.apis.google.com/chart?chs=600x280&amp;cht=p3&amp;chco=FFCC33|7637A2|fff22a|9dd8f3&amp;chd=t:98.82353,0.7843138,0.26143792,0.13071896&amp;chl=Bacteria (eubacteria)|Archaea|Viruses|unclassified sequences&amp;chma=140,140">
    */
    public String getSubmittedReadsPieChartURL() {
        if (hasStats) {
            float percentReadsWithOrfs = (float) readsWithOrfs / (float) totalReads * 100f;
            StringBuffer buf = new StringBuffer("http://chart.apis.google.com/chart?chs=740x180&amp;cht=p3&amp;chco=FFCC33|7637A2&amp;chd=t:");
            buf.append(percentReadsWithOrfs)
                    .append(',')
                    .append(100f - percentReadsWithOrfs)
                    .append("&amp;chl=Reads WITH predicted ORFs (")
                    .append(NUMBER_FORMAT.format(readsWithOrfs))
                    .append(")|Reads with NO predicted ORFs (")
                    .append(NUMBER_FORMAT.format(totalReads - readsWithOrfs))
                    .append(")&amp;chma=270,270");

            return buf.toString();
        } else {
            return null;
        }
    }

    public String getOrfPieChartURL() {
        if (hasStats) {
            float percentOrfsWithMatches = (float) orfsWithMatches / (float) totalOrfs * 100f;
            StringBuffer buf = new StringBuffer("http://chart.apis.google.com/chart?chs=740x180&amp;cht=p3&amp;chco=FFCC33|7637A2&amp;chd=t:");
            buf.append(percentOrfsWithMatches)
                    .append(',')
                    .append(100f - percentOrfsWithMatches)
                    .append("&amp;chl=ORFs WITH InterPro matches (")
                    .append(NUMBER_FORMAT.format(orfsWithMatches))
                    .append(")|ORFs with NO InterPro matches (")
                    .append(NUMBER_FORMAT.format(totalOrfs - orfsWithMatches))
                    .append(")&amp;chma=270,270");

            return buf.toString();
        } else {
            return null;
        }
    }

    public String getGoPieChartURL() {
        if (hasStats && goMatchStatistics != null) {
            StringBuffer buf = new StringBuffer("http://chart.apis.google.com/chart?chs=740x180&amp;chco=FFCC33|7637A2&amp;cht=p3&amp;chd=t:");
            StringBuffer valueBuf = new StringBuffer();
            StringBuffer labelBuf = new StringBuffer();
            int sliceCount = 0;
            float percentOther = 100.0f;
            for (MatchStatistic statistic : goMatchStatistics) {
                if (sliceCount++ < 35) {
                    percentOther -= statistic.getMatchPercentage();
                    if (valueBuf.length() > 0) {
                        valueBuf.append(',');
                        labelBuf.append('|');
                    }
                    valueBuf.append(statistic.getMatchPercentage());

                    String name = (statistic.getTerm().length() > 25)
                            ? statistic.getTerm().substring(0, 25)
                            : statistic.getTerm();
                    labelBuf.append(statistic.getAccession())
                            .append(' ')
                            .append(name)
                            .append(" (").append(statistic.getCount()).append(')');
                }
            }
            valueBuf.append(',').append(percentOther);
            labelBuf.append("|Other GO Terms");
            buf.append(valueBuf)
                    .append("&amp;chl=")
                    .append(labelBuf)
                    .append("&amp;chma=270,270");
            return buf.toString();
        } else return null;
    }

    public Set<MatchStatistic> getInterProMatchStatistics() {
        return interProMatchStatistics;
    }

    public Set<MatchStatistic> getGoMatchStatistics() {
        return goMatchStatistics;
    }
}
