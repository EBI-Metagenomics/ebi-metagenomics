package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.googlechart.GoogleChartFactory;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadSection;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Represents a Metagenomics model factory. Use this factory if you want to create a {@link ViewModel}.
 * TODO: Please note, that this factory class is deprecated and needs to be replace by {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.AbstractViewModelBuilder}
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Deprecated
public class MGModelFactory {
    private final static Log log = LogFactory.getLog(MGModelFactory.class);

    private MGModelFactory() {

    }

    public static AnalysisStatsModel getAnalysisStatsModel(SessionManager sessionManager, Sample sample,
                                                           String pageTitle, List<Breadcrumb> breadcrumbs,
                                                           EmgFile emgFile, List<String> archivedSequences,
                                                           MemiPropertyContainer propertyContainer,
                                                           boolean isReturnSizeLimit, AnalysisStatsModel.ExperimentType experimentType,
                                                           final DownloadSection downloadSection) {
        log.info("Building instance of " + AnalysisStatsModel.class + "...");
        if (emgFile != null) {
            Map<Class, List<AbstractGOTerm>> goData = loadGODataFromCSV(propertyContainer.getPathToAnalysisDirectory(),
                    emgFile);
            return new AnalysisStatsModel(getSessionSubmitter(sessionManager), pageTitle, breadcrumbs, sample,
                    getBarChartURL(propertyContainer.getPathToAnalysisDirectory(), emgFile),
                    getHBarChartURL(BiologicalProcessGOTerm.class, goData),
                    getHBarChartURL(CellularComponentGOTerm.class, goData),
                    getHBarChartURL(MolecularFunctionGOTerm.class, goData),
                    null,
                    goData.get(BiologicalProcessGOTerm.class), emgFile, archivedSequences, propertyContainer,
                    getListOfInterProEntries(propertyContainer.getPathToAnalysisDirectory(), emgFile, isReturnSizeLimit), experimentType,
                    downloadSection);
        } else {
            return new AnalysisStatsModel(getSessionSubmitter(sessionManager), pageTitle, breadcrumbs, sample,
                    emgFile, archivedSequences, propertyContainer, downloadSection);
        }
    }


    private static Submitter getSessionSubmitter(SessionManager sessionMgr) {
        if (sessionMgr != null && sessionMgr.getSessionBean() != null) {
            return sessionMgr.getSessionBean().getSubmitter();
        }
        return null;
    }


    private static String getHBarChartURL(Class clazz, Map<Class, List<AbstractGOTerm>> goData) {

        List<Integer> data = getGOData(clazz, goData);
        List<String> labels = getGOLabels(clazz, goData);
        Properties props = new Properties();

        props.put(GoogleChartFactory.CHART_MARGIN, "0,40,0,0");
        props.put(GoogleChartFactory.CHART_SIZE, "300x440");
        props.put(GoogleChartFactory.CHART_COLOUR, "ff0a00,ff4700,ff4700,ffb444,ffb444,ffd088,ffd088," +
                "ffebcc,b8b082,b8b082,b8b082,b8b082,b8b082,b8b082,b8b082,b8b082");
        props.put("chxt", "x");
        props.put("chxs", "0,ffffff,0,0,_");

        props.put("chds", "0,600");
        props.put("chxr", "0,0,600");
        props.put("chxs", "0,ffffff,0,0,_");

        return GoogleChartFactory.buildHorizontalBarChartURL(props, data, labels);
    }


    private static List<Integer> getGOData(Class clazz, Map<Class, List<AbstractGOTerm>> goData) {
        List<Integer> result = new ArrayList<Integer>();
        List<AbstractGOTerm> goTerms = null;
        if (clazz.equals(BiologicalProcessGOTerm.class)) {
            goTerms = goData.get(BiologicalProcessGOTerm.class);
        } else if (clazz.equals(CellularComponentGOTerm.class)) {
            goTerms = goData.get(CellularComponentGOTerm.class);
        } else {
            goTerms = goData.get(MolecularFunctionGOTerm.class);
        }
        if (goTerms != null) {
            //the second iteration is to calculate the pie chart data
            for (AbstractGOTerm term : goTerms) {
                result.add(term.getNumberOfMatches());
            }
        }
        return result;
    }

    private static List<String> getGOLabels(Class clazz, Map<Class, List<AbstractGOTerm>> goData) {
        List<String> result = new ArrayList<String>();
        List<AbstractGOTerm> goTerms = null;
        if (clazz.equals(BiologicalProcessGOTerm.class)) {
            goTerms = goData.get(BiologicalProcessGOTerm.class);
        } else if (clazz.equals(CellularComponentGOTerm.class)) {
            goTerms = goData.get(CellularComponentGOTerm.class);
        } else {
            goTerms = goData.get(MolecularFunctionGOTerm.class);
        }
        if (goTerms != null) {
            //the second iteration is to calculate the pie chart data
            for (AbstractGOTerm term : goTerms) {
                result.add(term.toString());
            }
        }
        return result;
    }

    private static Map<Class, List<AbstractGOTerm>> loadGODataFromCSV(String classPathToStatsFile, EmgFile emgFile) {
        Map<Class, List<AbstractGOTerm>> result = new Hashtable<Class, List<AbstractGOTerm>>();
        result.put(BiologicalProcessGOTerm.class, new ArrayList<AbstractGOTerm>());
        result.put(CellularComponentGOTerm.class, new ArrayList<AbstractGOTerm>());
        result.put(MolecularFunctionGOTerm.class, new ArrayList<AbstractGOTerm>());

        log.info("Processing GO slim file...");
        List<String[]> rows = getRawData(classPathToStatsFile, emgFile, "_summary.go_slim", ',');

        if (rows != null) {
            for (String[] row : rows) {
                if (row.length == 4) {
                    String ontology = row[2];
                    if (ontology != null && ontology.trim().length() > 0) {
                        String accession = row[0];
                        String synonym = row[1];
                        int numberOfMatches = Integer.parseInt(row[3]);
                        if (ontology.equals("biological_process")) {
                            BiologicalProcessGOTerm instance = new BiologicalProcessGOTerm(accession,
                                    synonym, numberOfMatches);
                            result.get(BiologicalProcessGOTerm.class).add(instance);
                        } else if (ontology.equals("cellular_component")) {
                            CellularComponentGOTerm instance = new CellularComponentGOTerm(accession,
                                    synonym, numberOfMatches);
                            result.get(CellularComponentGOTerm.class).add(instance);
                        } else {
                            MolecularFunctionGOTerm instance = new MolecularFunctionGOTerm(accession,
                                    synonym, numberOfMatches);
                            result.get(MolecularFunctionGOTerm.class).add(instance);
                        }
                    }
                } else {
                    log.warn("Row size is not the expected one.");
                }
            }
        } else {
            log.warn("Didn't get any data from GO term file. There might be some fundamental change to this file" +
                    "(maybe in the near past), which affects this parsing process!");
        }
        return result;
    }


    private static String getBarChartURL(String classPathToStatsFile, EmgFile emgFile) {
        List<Integer> chartData = loadStatsFromCSV(classPathToStatsFile, emgFile);
        if (chartData != null && chartData.size() > 0) {
            Properties props = new Properties();
            props.put(GoogleChartFactory.CHART_TYPE, "bhs");
            props.put(GoogleChartFactory.CHART_SIZE, "450x200");
            props.put(GoogleChartFactory.CHART_AXES, "y");
            props.put(GoogleChartFactory.CHART_LEGEND_TEXT, "total number of submitted reads|total number of processed reads|reads with at least 1 pCDS|reads with at least 1 InterPro match");
            props.put(GoogleChartFactory.CHART_COLOUR, "FF0000|00FF00|0000FF|FFFF10");
            props.put(GoogleChartFactory.CHART_MARKER, "N,000000,0,-1,11");
            props.put(GoogleChartFactory.CHART_DATA_SCALE, "0," + chartData.get(0));
            return GoogleChartFactory.buildChartURL(props, chartData);
        }
        return null;
    }

    private static List<Integer> loadStatsFromCSV(String classPathToStatsFile, EmgFile emgFile) {
        List<Integer> result = new ArrayList<Integer>();

        log.info("Processing summary file...");
        List<String[]> rows = getRawData(classPathToStatsFile, emgFile, "_summary", ',');

        if (rows != null && rows.size() >= 6) {
            int numSubmittedSeqs = getValueOfRow(rows, 0);
            int numSeqsOfProcessedSeqs = getValueOfRow(rows, 3);
            int numSeqsWithPredicatedCDS = getValueOfRow(rows, 4);
            int numSeqsWithInterProScanMatch = getValueOfRow(rows, 5);
            result.add(numSubmittedSeqs);
            result.add(numSeqsOfProcessedSeqs);
            result.add(numSeqsWithPredicatedCDS);
            result.add(numSeqsWithInterProScanMatch);
        } else {
            log.warn("Didn't get any data from summary file. There might be some fundamental change to this file" +
                    "(maybe in the near past), which affects this parsing process!");
        }
        return result;
    }

    private static int getValueOfRow(List<String[]> rows, int i) {
        String[] row = rows.get(i);
        if (row.length > 1) {
            return Integer.parseInt(row[1]);
        }
        return -1;
    }

    private static List<InterProEntry> getListOfInterProEntries(String pathToAnalysisDirectory, EmgFile emgFile, boolean isReturnSizeLimit) {
        return loadInterProMatchesFromCSV(pathToAnalysisDirectory, emgFile, isReturnSizeLimit);
    }


    /**
     * Loads InterPro matches from the Python pipeline result file with file extension '_summary.ipr'.
     * Please notice that the size of the returned list could be limited to 5 items.
     * TODO: Size limitation is a temporary solution
     *
     * @param classPathToStatsFile
     * @param emgFile
     * @param isReturnSizeLimit    Specifies if the size of the returned list is limited to 5.
     * @return
     */
    private static List<InterProEntry> loadInterProMatchesFromCSV(String classPathToStatsFile, EmgFile emgFile, boolean isReturnSizeLimit) {
        List<InterProEntry> result = new ArrayList<InterProEntry>();
        log.info("Processing interpro result summary file...");
        List<String[]> rows = getRawData(classPathToStatsFile, emgFile, "_summary.ipr", ',');

        if (rows != null) {
            //return size limitation the
            if (isReturnSizeLimit) {
                rows = rows.subList(0, 5);
            }
            for (String[] row : rows) {
                if (row.length == 3) {
                    String entryID = row[0];
                    String entryDesc = row[1];
                    int numOfEntryHits = Integer.parseInt(row[2]);
                    if (entryID != null && entryID.trim().length() > 0) {
                        result.add(new InterProEntry(entryID, entryDesc, numOfEntryHits));
                    }
                } else {
                    log.warn("Row size is not the expected one.");
                }
            }
        } else {
            log.warn("Didn't get any data from interpro result summary file. There might be some fundamental change to this file" +
                    "(maybe in the near past), which affects this parsing process!");
        }
        return result;
    }

    /**
     * Reads raw data from the specified file by using a CSV reader. Possible to specify different delimiters.
     *
     * @param classPathToStatsFile
     * @param emgFile
     * @return
     */
    private static List<String[]> getRawData(String classPathToStatsFile, EmgFile emgFile, String fileExtension, char delimiter) {
        List<String[]> rows = null;

        String directoryName = emgFile.getFileIDInUpperCase().replace('.', '_');
        File file = new File(classPathToStatsFile + directoryName + '/' + directoryName + fileExtension);
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(file), delimiter);
        } catch (FileNotFoundException e) {
            log.warn("Could not find the following file " + file.getAbsolutePath(), e);
        }
        if (reader != null) {
            try {
                rows = reader.readAll();
            } catch (IOException e) {
                log.warn("Could not get rows from CSV file!", e);
            }
        }
        return rows;
    }
}