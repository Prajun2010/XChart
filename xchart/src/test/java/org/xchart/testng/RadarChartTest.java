package org.xchart.testng;

import org.knowm.xchart.RadarChart;
import org.knowm.xchart.RadarChartBuilder;
import org.knowm.xchart.RadarSeries;
import org.knowm.xchart.style.RadarStyler;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.theme.Theme;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

public class RadarChartTest {



    private RadarChart radarChart;

    private Object invokePrivateMethod(Object object, String methodName, Object... args) throws Exception {
        Method method = getMethod(object.getClass(), methodName);
        method.setAccessible(true);
        return method.invoke(object, args);
    }

    private Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        while (clazz != null) {
            try {
                return clazz.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchMethodException(methodName);
    }

    @BeforeMethod
    public void setUp() {
        radarChart = new RadarChart(800, 600);
    }

    @Test
    public void testConstructor_DefaultTheme() {
        Assert.assertNotNull(radarChart);
        Assert.assertEquals(radarChart.getWidth(), 800);
        Assert.assertEquals(radarChart.getHeight(), 600);
        Assert.assertNotNull(radarChart.getStyler().getTheme());
    }

    @Test
    public void testConstructor_CustomTheme() {
        Theme customTheme = ChartTheme.GGPlot2.newInstance(ChartTheme.GGPlot2);
        RadarChart chart = new RadarChart(800, 600, customTheme);
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getWidth(), 800);
        Assert.assertEquals(chart.getHeight(), 600);
        Assert.assertEquals(chart.getStyler().getTheme(), customTheme);
    }

    @Test
    public void testConstructor_ChartTheme() {
        RadarChart chart = new RadarChart(800, 600, ChartTheme.Matlab);
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getWidth(), 800);
        Assert.assertEquals(chart.getHeight(), 600);
        Assert.assertEquals(chart.getStyler().getTheme().getClass(), ChartTheme.Matlab.newInstance(ChartTheme.Matlab).getClass());
    }

    @Test
    public void testConstructor_RadarChartBuilder() {
        RadarChartBuilder builder = new RadarChartBuilder().width(800).height(600).title("Test Radar Chart").theme(ChartTheme.Matlab);
        RadarChart chart = new RadarChart(builder);
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getWidth(), 800);
        Assert.assertEquals(chart.getHeight(), 600);
        Assert.assertEquals(chart.getTitle(), "Test Radar Chart");
    }

    @Test
    public void testGetRadiiLabels() {
        radarChart.setRadiiLabels(new String[]{"Label1", "Label2", "Label3"});
        Assert.assertNotNull(radarChart.getRadiiLabels());
        Assert.assertEquals(radarChart.getRadiiLabels().length, 3);
        Assert.assertEquals(radarChart.getRadiiLabels()[0], "Label1");
    }

    @Test
    public void testSetRadiiLabels() {
        String[] labels = {"Label1", "Label2", "Label3"};
        radarChart.setRadiiLabels(labels);
        Assert.assertEquals(radarChart.getRadiiLabels(), labels);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSetRadiiLabels_NullLabels() {
        radarChart.setRadiiLabels(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSetRadiiLabels_TooFewLabels() {
        radarChart.setRadiiLabels(new String[]{"Label1"});
    }

    @Test
    public void testAddSeries_Valid() {
        double[] values = {0.5, 0.7, 0.9};
        RadarSeries series = radarChart.addSeries("Series1", values);
        Assert.assertNotNull(series);
        Assert.assertEquals(series.getName(), "Series1");
        Assert.assertEquals(series.getValues(), values);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeries_NullName() {
        double[] values = {0.5, 0.7, 0.9};
        radarChart.addSeries(null, values);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeries_DuplicateName() {
        double[] values1 = {0.5, 0.7, 0.9};
        radarChart.addSeries("Series1", values1);
        double[] values2 = {0.3, 0.6, 0.8};
        radarChart.addSeries("Series1", values2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeries_NullValues() {
        radarChart.addSeries("Series1", null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeries_TooFewValues() {
        double[] values = {0.5};
        radarChart.addSeries("Series1", values);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeries_ValuesOutOfRange() {
        double[] values = {-0.1, 0.5, 1.1};
        radarChart.addSeries("Series1", values);
    }

    @Test
    public void testPaint() {
        // Not easy to test actual painting without Graphics2D context
        radarChart.setRadiiLabels(new String[]{"Label1", "Label2", "Label3"});
        double[] values = {0.5, 0.7, 0.9};
        radarChart.addSeries("Series1", values);
        radarChart.paint(null, 800, 600); // Just for coverage
        Assert.assertEquals(radarChart.getWidth(), 800);
        Assert.assertEquals(radarChart.getHeight(), 600);
    }

    @DataProvider(name = "invalidSeriesData")
    public Object[][] invalidSeriesData() {
        return new Object[][] {
                {null, new double[]{0.5, 0.7, 0.9}},
                {"Series1", null},
                {"Series1", new double[]{}},
                {"Series1", new double[]{0.5}},
                {"Series1", new double[]{-0.1, 0.5, 1.1}},
                {"Series1", new double[]{0.5, 0.7}},
                {"Series1", new double[]{0.5, 0.7, 0.9, 1.0}},
        };
    }

    @Test(dataProvider = "invalidSeriesData", expectedExceptions = IllegalArgumentException.class)
    public void testAddSeries_InvalidData(String name, double[] values) {
        radarChart.addSeries(name, values);
    }



    @Test
    public void testAddMultipleSeries() {
        radarChart.setRadiiLabels(new String[]{"Label1", "Label2", "Label3"});
        double[] values1 = {0.5, 0.7, 0.9};
        double[] values2 = {0.3, 0.6, 0.8};
        radarChart.addSeries("Series1", values1);
        radarChart.addSeries("Series2", values2);
        Assert.assertEquals(radarChart.getSeriesMap().size(), 2);
    }



    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeries_TooFewTooltips() {
        radarChart.setRadiiLabels(new String[]{"Label1", "Label2", "Label3"});
        double[] values = {0.5, 0.7, 0.9};
        String[] tooltips = {"Tooltip1"};
        radarChart.addSeries("Series1", values, tooltips);
    }


    @Test
    public void testRemoveSeries_Valid() {
        radarChart.setRadiiLabels(new String[]{"Label1", "Label2", "Label3"});
        double[] values = {0.5, 0.7, 0.9};
        radarChart.addSeries("Series1", values);

        radarChart.removeSeries("Series1");

        Assert.assertNull(radarChart.getSeriesMap().get("Series1"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRemoveSeries_NonExistentSeries() {
        radarChart.removeSeries("NonExistentSeries");
    }



    @Test
    public void testPaintWithGraphics2D() {
        // Simulate painting with a Graphics2D context
        radarChart.setRadiiLabels(new String[]{"Label1", "Label2", "Label3"});
        double[] values = {0.5, 0.7, 0.9};
        radarChart.addSeries("Series1", values);

        // Assertions would be more meaningful with a real Graphics2D context
        radarChart.paint(null, 800, 600);

        Assert.assertEquals(radarChart.getWidth(), 800);
        Assert.assertEquals(radarChart.getHeight(), 600);
    }

    @Test
    public void testSetSeriesStyles() throws Exception{
        radarChart.setRadiiLabels(new String[]{"Label1", "Label2", "Label3"});
        double[] values = {0.5, 0.7, 0.9};
        radarChart.addSeries("Series1", values);
        invokePrivateMethod(radarChart,"setSeriesStyles");


        Assert.assertNotNull(radarChart.getSeriesMap().get("Series1").getFillColor());
        Assert.assertNotNull(radarChart.getSeriesMap().get("Series1").getLineColor());
        Assert.assertNotNull(radarChart.getSeriesMap().get("Series1").getLineStyle());
        Assert.assertNotNull(radarChart.getSeriesMap().get("Series1").getMarker());
        Assert.assertNotNull(radarChart.getSeriesMap().get("Series1").getMarkerColor());
    }

    @Test
    public void testSetSeriesStyles_NoSeriesAdded() throws Exception{
        invokePrivateMethod(radarChart,"setSeriesStyles");

        Assert.assertTrue(radarChart.getSeriesMap().isEmpty());
    }

    @Test
    public void testSetSeriesStyles_MultipleSeries() throws Exception{
        radarChart.setRadiiLabels(new String[]{"Label1", "Label2", "Label3"});
        double[] values1 = {0.5, 0.7, 0.9};
        double[] values2 = {0.3, 0.6, 0.8};
        radarChart.addSeries("Series1", values1);
        radarChart.addSeries("Series2", values2);
        invokePrivateMethod(radarChart,"setSeriesStyles");


        Assert.assertNotNull(radarChart.getSeriesMap().get("Series1").getFillColor());
        Assert.assertNotNull(radarChart.getSeriesMap().get("Series1").getLineColor());
        Assert.assertNotNull(radarChart.getSeriesMap().get("Series1").getLineStyle());
        Assert.assertNotNull(radarChart.getSeriesMap().get("Series1").getMarker());
        Assert.assertNotNull(radarChart.getSeriesMap().get("Series1").getMarkerColor());

        Assert.assertNotNull(radarChart.getSeriesMap().get("Series2").getFillColor());
        Assert.assertNotNull(radarChart.getSeriesMap().get("Series2").getLineColor());
        Assert.assertNotNull(radarChart.getSeriesMap().get("Series2").getLineStyle());
        Assert.assertNotNull(radarChart.getSeriesMap().get("Series2").getMarker());
        Assert.assertNotNull(radarChart.getSeriesMap().get("Series2").getMarkerColor());
    }


}

