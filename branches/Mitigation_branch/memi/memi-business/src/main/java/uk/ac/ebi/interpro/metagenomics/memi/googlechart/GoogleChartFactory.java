package uk.ac.ebi.interpro.metagenomics.memi.googlechart;

import java.util.List;
import java.util.Properties;

/**
 * TODO: Description
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class GoogleChartFactory {

    private static final String BASE_GOOGLE_CHART_URL = "http://chart.apis.google.com/chart?";

    public static final String CHART_SIZE = "chs";

    public static final String CHART_COLOUR = "chco";

    public static final String CHART_TYPE = "cht";

    public static final String CHART_DATA = "chd";

    public static final String CHART_LABEL = "chl";

    public static final String CHART_LEGEND_TEXT = "chdl";

    public static final String CHART_MARGIN = "chma";

    public static final String CHART_AXES = "chxt";

    public static final String CHART_AXES_LABELS = "chxl";

    public static final String CHART_MARKER = "chm";

    public static final String CHART_DATA_SCALE = "chds";

    private static final char AND_CHAR = '&';


    private GoogleChartFactory() {

    }

    /**
     * @param chartData   - List of chart data
     * @param chartLabels - List of chart label
     * @return A Google chart Tool URL.
     */
    public static String buildPieChartURL(Properties props, List<Integer> chartData, List<String> chartLabels) {
        if (chartData != null && chartData.size() > 0 && chartLabels != null && chartLabels.size() > 0) {
            StringBuffer result = new StringBuffer(BASE_GOOGLE_CHART_URL);
            addParameter(result, CHART_TYPE, "p3");
            addGoogleChartParams(result, props);
            addChartData(result, chartData);
            addChartLabels(result, chartLabels);
            return result.toString();
        } else {
            return null;
        }
    }

    public static String buildHorizontalBarChartURL(Properties props, List<Integer> chartData, List<String> chartLabels) {
        if (chartData != null && chartData.size() > 0 && chartLabels != null && chartLabels.size() > 0) {
            StringBuffer result = new StringBuffer(BASE_GOOGLE_CHART_URL);
            addParameter(result, CHART_TYPE, "bhs");
            addGoogleChartParams(result, props);
            addChartData(result, chartData);
            addChartMarker(result, chartLabels);
            return result.toString();
        } else {
            return null;
        }
    }

    private static void addParameter(StringBuffer result, String parameter, String value) {
        if (result.lastIndexOf("?") != result.length() - 1) {
            result.append(AND_CHAR);
        }
        result.append(parameter + "=" + value);
    }

    private static void addChartData(StringBuffer result, List<Integer> chartData) {
        if (result.lastIndexOf("?") != result.length() - 1) {
            result.append(AND_CHAR);
        }
        result.append(CHART_DATA + "=" + "t:");
        int size = chartData.size();
        for (float dataValue : chartData) {
            size--;
            result.append(dataValue);
            if (size > 0) {
                result.append(',');
            }
        }
    }

    private static void addChartLabels(StringBuffer result, List<String> chartLabels) {
        if (result.lastIndexOf("?") != result.length() - 1) {
            result.append(AND_CHAR);
        }
        result.append(CHART_LABEL + "=");
        int size = chartLabels.size();
        for (String label : chartLabels) {
            size--;
            result.append(label);
            if (size > 0) {
                result.append('|');
            }
        }
    }

    private static void addChartMarker(StringBuffer result, List<String> chartMarker) {
        if (result.lastIndexOf("?") != result.length() - 1) {
            result.append(AND_CHAR);
        }
        result.append(CHART_MARKER + "=");
        result.append("tmacromol" +
                "ecule+metabolic+process+(508),727272,0,0,10,,:4|tcellular+process+(489),7272" +
                "72,0,1,10,,l:4:0|tbiosynthetic+process+(477),727272,0,2,10,,:4|tmetabolic+pr" +
                "ocess+(271),727272,0,3,10,,:4|tnucleobase+nucleoside+nucleotide+and+nucleic+" +
                "acid+metabolic+process+(227),727272,0,4,10,,:4|ttransport+(131),727272,0,5,1" +
                "0,,:8|tregulation+of+biological+process+(120),727272,0,6,10,,:4|tresponse+to" +
                "+stimulus+(74),727272,0,7,10,,:4|tcatabolic+process+(26),727272,0,8,10,,:4|t" +
                "behavior+%2811%29,727272,0,9,10,,:4|tbiological_process+(4),727272,0,10,10,," +
                ":4|tcellular+component+movement+(4),727272,0,11,10,,:4|tsecretion+(3),727272" +
                ",0,12,10,,:4|tcell+communication+(2),727272,0,13,10,,:4|tpathogenesis+(1),72" +
                "7272,0,14,10,,:4|tmulti-organism+process+(1),727272,0,15,10,,:4");
//        int size = chartMarker.size();
//        for (String marker : chartMarker) {
//            size--;
//            result.append(marker);
//            if (size > 0) {
//                result.append('|');
//            }
//        }
    }

    public static String buildVerticalBarChartURL(Properties props, List<Integer> chartData) {
        StringBuffer result = new StringBuffer(BASE_GOOGLE_CHART_URL);
        addParameter(result, CHART_TYPE, "bvs");
        addGoogleChartParams(result, props);
        //add data
        if (chartData != null && chartData.size() > 0) {
            addChartData(result, chartData);
        }
        return result.toString();
    }

    public static String buildChartURL(Properties props, List<Integer> chartData) {
        StringBuffer result = new StringBuffer(BASE_GOOGLE_CHART_URL);
        addGoogleChartParams(result, props);
        //add data
        if (chartData != null && chartData.size() > 0) {
            addChartData(result, chartData);
        }
        return result.toString();
    }

    private static void addGoogleChartParams(StringBuffer url, Properties props) {
        for (Object key : props.keySet()) {
            addParameter(url, (String) key, (String) props.get(key));
        }
    }
}
