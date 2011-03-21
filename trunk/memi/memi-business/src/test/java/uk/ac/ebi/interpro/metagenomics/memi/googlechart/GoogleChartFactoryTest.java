package uk.ac.ebi.interpro.metagenomics.memi.googlechart;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class GoogleChartFactoryTest {


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testBuildPieChartURL() {
        List<Float> chartData = new ArrayList<Float>();
        chartData.add(4.8235593f);
        chartData.add(4.747398f);
        chartData.add(4.468139f);
        chartData.add(4.391978f);

        List<String> chartLabels = new ArrayList<String>();
        chartLabels.add("GO:0003824 catalytic activity (190)");
        chartLabels.add("GO:0006412 translation (187)");
        chartLabels.add("GO:0008152 metabolic process (176)");
        chartLabels.add("GO:0003735 structural constituent of (173)");

        Properties props = new Properties();
        props.put(GoogleChartFactory.CHART_COLOUR, "FFCC33|7637A2");
        props.put(GoogleChartFactory.CHART_MARGIN, "270,270");
        props.put(GoogleChartFactory.CHART_SIZE, "740x180");

        String actual = GoogleChartFactory.buildPieChartURL(props, chartData, chartLabels);
        String expected = "http://chart.apis.google.com/chart?cht=p3&chco=FFCC33|7637A2&chma=270,270&chs=740x180&chd=t:4.8235593,4.747398,4.468139,4.391978&chl=GO:0003824 catalytic activity (190)|GO:0006412 translation (187)|GO:0008152 metabolic process (176)|GO:0003735 structural constituent of (173)";
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void testBuildBarChartURL() {
        List<Float> chartData = new ArrayList<Float>();
        chartData.add(90f);
        chartData.add(80f);
        chartData.add(30f);

        Properties props = new Properties();
        props.put(GoogleChartFactory.CHART_SIZE, "250x100");
        props.put(GoogleChartFactory.CHART_AXES, "x,y");
        props.put(GoogleChartFactory.CHART_AXES_LABELS, "1:|0%|25%|50%|75%|100%");
        props.put(GoogleChartFactory.CHART_LEGEND_TEXT, "total reads|reads with >1 pCDS|reads with >1 hit");
        props.put(GoogleChartFactory.CHART_COLOUR, "FF0000|00FF00|0000FF");

        String actual = GoogleChartFactory.buildVerticalBarChartURL(props, chartData);
        String expected = "http://chart.apis.google.com/chart?cht=bvs&chco=FF0000|00FF00|0000FF&chxl=1:|0%|25%|50%|75%|100%&chxt=x,y&chs=250x100&chdl=total reads|reads with >1 pCDS|reads with >1 hit&chd=t:90.0,80.0,30.0";
        Assert.assertEquals(expected, actual);

    }
}
