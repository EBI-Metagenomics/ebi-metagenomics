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
    public static String buildPieChartURL(Properties props, List<Float> chartData, List<String> chartLabels) {
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

    private static void addParameter(StringBuffer result, String parameter, String value) {
        if (result.lastIndexOf("?") != result.length() - 1) {
            result.append(AND_CHAR);
        }
        result.append(parameter + "=" + value);
    }

    private static void addChartData(StringBuffer result, List<Float> chartData) {
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

    public static String buildVerticalBarChartURL(Properties props, List<Float> chartData) {
        StringBuffer result = new StringBuffer(BASE_GOOGLE_CHART_URL);
        addParameter(result, CHART_TYPE, "bvs");
        addGoogleChartParams(result, props);
        //add data
        if (chartData != null && chartData.size() > 0) {
            addChartData(result, chartData);
        }
        return result.toString();
    }

    public static String buildChartURL(Properties props, List<Float> chartData) {
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
