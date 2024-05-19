package org.xchart.testng;

import org.knowm.xchart.BubbleChart;
import org.knowm.xchart.BubbleSeries;
import org.knowm.xchart.style.BubbleStyler;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.theme.Theme;
import org.knowm.xchart.style.theme.XChartTheme;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;
import java.util.Arrays;

import java.util.List;

import static org.testng.Assert.*;

public class BubbleChartTest {

    private BubbleChart chart;

    @BeforeMethod
    public void setUp() {
        chart = new BubbleChart(800, 600);
    }

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




    @Test
    public void testBubbleChartDefaultConstructor() {
        assertNotNull(chart);
        assertTrue(chart.getStyler() instanceof BubbleStyler);
    }

    @Test
    public void testBubbleChartConstructorWithTheme() {
        Theme theme = new XChartTheme();
        BubbleChart chartWithTheme = new BubbleChart(800, 600, theme);
        assertNotNull(chartWithTheme);
        assertEquals(chartWithTheme.getStyler().getTheme(), theme);
    }

    @Test
    public void testAddSeriesWithLists() {
        List<Double> xData = Arrays.asList(1.0, 2.0, 3.0);
        List<Double> yData = Arrays.asList(1.0, 4.0, 9.0);
        List<Double> bubbleData = Arrays.asList(2.0, 4.0, 6.0);
        BubbleSeries series = chart.addSeries("Series1", xData, yData, bubbleData);
        assertNotNull(series);
        assertEquals(series.getXData(), xData.stream().mapToDouble(Double::doubleValue).toArray());
    }

    @Test
    public void testAddSeriesWithArrays() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {1.0, 4.0, 9.0};
        double[] bubbleData = {2.0, 4.0, 6.0};
        BubbleSeries series = chart.addSeries("Series1", xData, yData, bubbleData);
        assertNotNull(series);
        assertEquals(series.getXData(), xData);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesWithMismatchedArrayLengths() {
        double[] xData = {1.0, 2.0};
        double[] yData = {1.0, 4.0, 9.0};
        double[] bubbleData = {2.0, 4.0, 6.0};
        chart.addSeries("Series1", xData, yData, bubbleData);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesWithNullYData() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = null;
        double[] bubbleData = {2.0, 4.0, 6.0};
        chart.addSeries("Series1", xData, yData, bubbleData);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesWithEmptyYData() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {};
        double[] bubbleData = {2.0, 4.0, 6.0};
        chart.addSeries("Series1", xData, yData, bubbleData);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesWithNullBubbleData() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {1.0, 4.0, 9.0};
        double[] bubbleData = null;
        chart.addSeries("Series1", xData, yData, bubbleData);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesWithEmptyBubbleData() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {1.0, 4.0, 9.0};
        double[] bubbleData = {};
        chart.addSeries("Series1", xData, yData, bubbleData);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesWithDuplicateName() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {1.0, 4.0, 9.0};
        double[] bubbleData = {2.0, 4.0, 6.0};
        chart.addSeries("Series1", xData, yData, bubbleData);
        chart.addSeries("Series1", xData, yData, bubbleData);
    }

    @Test
    public void testUpdateSeriesWithLists() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {1.0, 4.0, 9.0};
        double[] bubbleData = {2.0, 4.0, 6.0};
        chart.addSeries("Series1", xData, yData, bubbleData);

        List<Double> newXData = Arrays.asList(2.0, 3.0, 4.0);
        List<Double> newYData = Arrays.asList(2.0, 8.0, 18.0);
        List<Double> newBubbleData = Arrays.asList(3.0, 6.0, 9.0);

        BubbleSeries updatedSeries = chart.updateBubbleSeries("Series1", newXData, newYData, newBubbleData);
        assertNotNull(updatedSeries);
        assertEquals(updatedSeries.getXData(), newXData.stream().mapToDouble(Double::doubleValue).toArray());
    }

    @Test
    public void testUpdateSeriesWithArrays() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {1.0, 4.0, 9.0};
        double[] bubbleData = {2.0, 4.0, 6.0};
        chart.addSeries("Series1", xData, yData, bubbleData);

        double[] newXData = {2.0, 3.0, 4.0};
        double[] newYData = {2.0, 8.0, 18.0};
        double[] newBubbleData = {3.0, 6.0, 9.0};

        BubbleSeries updatedSeries = chart.updateBubbleSeries("Series1", newXData, newYData, newBubbleData);
        assertNotNull(updatedSeries);
        assertEquals(updatedSeries.getXData(), newXData);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateSeriesWithInvalidName() {
        double[] newXData = {2.0, 3.0, 4.0};
        double[] newYData = {2.0, 8.0, 18.0};
        double[] newBubbleData = {3.0, 6.0, 9.0};
        chart.updateBubbleSeries("InvalidSeries", newXData, newYData, newBubbleData);
    }

    @Test
    public void testUpdateSeriesWithNullXData() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {1.0, 4.0, 9.0};
        double[] bubbleData = {2.0, 4.0, 6.0};
        chart.addSeries("Series1", xData, yData, bubbleData);

        double[] newYData = {2.0, 8.0, 18.0};
        double[] newBubbleData = {3.0, 6.0, 9.0};

        BubbleSeries updatedSeries = chart.updateBubbleSeries("Series1", null, newYData, newBubbleData);
        assertNotNull(updatedSeries);
        assertEquals(updatedSeries.getYData(), newYData);
    }


    @Test
    public void testUpdateSeriesWithNullBubbleData() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {1.0, 4.0, 9.0};
        double[] bubbleData = {2.0, 4.0, 6.0};
        chart.addSeries("Series1", xData, yData, bubbleData);

        double[] newXData = {2.0, 3.0, 4.0};
        double[] newYData = {2.0, 8.0, 18.0};

        BubbleSeries updatedSeries = chart.updateBubbleSeries("Series1", newXData, newYData, null);
        assertNotNull(updatedSeries);
        assertEquals(updatedSeries.getYData(), newYData);
    }

    @Test
    public void testUpdateSeriesWithGeneratedXData() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {1.0, 4.0, 9.0};
        double[] bubbleData = {2.0, 4.0, 6.0};
        chart.addSeries("Series1", xData, yData, bubbleData);

        double[] newYData = {2.0, 8.0, 18.0};
        double[] newBubbleData = {3.0, 6.0, 9.0};

        BubbleSeries updatedSeries = chart.updateBubbleSeries("Series1", null, newYData, newBubbleData);
        assertNotNull(updatedSeries);
        assertEquals(updatedSeries.getYData(), newYData);
    }

    @Test
    public void testAddSeriesWithGeneratedXData() {
        double[] yData = {1.0, 4.0, 9.0};
        double[] bubbleData = {2.0, 4.0, 6.0};
        BubbleSeries series = chart.addSeries("Series1", null, yData, bubbleData);
        assertNotNull(series);
        assertEquals(series.getYData(), yData);
    }

    @Test
    public void testSetSeriesStyles() throws  Exception{
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {1.0, 4.0, 9.0};
        double[] bubbleData = {2.0, 4.0, 6.0};
        BubbleSeries series = chart.addSeries("Series1", xData, yData, bubbleData);
        invokePrivateMethod(chart, "setSeriesStyles");

        assertNotNull(series.getLineColor());
        assertNotNull(series.getFillColor());
        assertNotNull(series.getLineStyle());
    }

    @Test
    public void testPaint() {
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        // This test would ideally require a graphical context to test properly
        // For now, we just ensure that the paint method doesn't throw any exceptions
        chart.paint(g, 800, 600);
    }

    @Test
    public void testGetSeriesMap() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {1.0, 4.0, 9.0};
        double[] bubbleData = {2.0, 4.0, 6.0};
        chart.addSeries("Series1", xData, yData, bubbleData);
        assertEquals(chart.getSeriesMap().size(), 1);
    }

    @Test
    public void testRemoveSeries() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {1.0, 4.0, 9.0};
        double[] bubbleData = {2.0, 4.0, 6.0};
        chart.addSeries("Series1", xData, yData, bubbleData);
        chart.getSeriesMap().remove("Series1");
        assertEquals(chart.getSeriesMap().size(), 0);
    }

    @Test
    public void testClearSeries() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {1.0, 4.0, 9.0};
        double[] bubbleData = {2.0, 4.0, 6.0};
        chart.addSeries("Series1", xData, yData, bubbleData);
        chart.addSeries("Series2", xData, yData, bubbleData);
        chart.getSeriesMap().clear();
        assertEquals(chart.getSeriesMap().size(), 0);
    }

    @Test
    public void testGetStyler() {
        assertNotNull(chart.getStyler());
        assertTrue(chart.getStyler() instanceof BubbleStyler);
    }



    @Test
    public void testGetLegend() throws Exception{
        assertNotNull(invokePrivateMethod(chart, "getLegend"));
    }


    @Test
    public void testGetPlot() throws  Exception{
        assertNotNull(invokePrivateMethod(chart,"getPlot"));

    }

    @Test
    public void testGetAxisPair() throws Exception{
        assertNotNull(invokePrivateMethod(chart,"getAxisPair"));
    }


    @Test
    public void testSetTitle() {
        chart.setTitle("New Title");
        assertEquals(chart.getTitle(), "New Title");
    }

    @Test
    public void testSetXAxisTitle() {
        chart.setXAxisTitle("New X Axis Title");
        assertEquals(chart.getXAxisTitle(), "New X Axis Title");
    }

    @Test
    public void testSetYAxisTitle() {
        chart.setYAxisTitle("New Y Axis Title");
        assertEquals(chart.getYAxisTitle(), "New Y Axis Title");
    }

    @Test
    public void testSetTheme() {
        Theme newTheme = new XChartTheme();
        chart.getStyler().setTheme(newTheme);
        assertEquals(chart.getStyler().getTheme(), newTheme);
    }

    @Test
    public void testGetWidth() {
        assertEquals(chart.getWidth(), 800);
    }

    @Test
    public void testGetHeight() {
        assertEquals(chart.getHeight(), 600);
    }

    @Test
    public void testGetTitle() {

        chart.setTitle("Chart Title");
        assertEquals(chart.getTitle(), "Chart Title");
    }

    @Test
    public void testGetXAxisTitle() {

        chart.setXAxisTitle("X Axis Title");
        assertEquals(chart.getXAxisTitle(), "X Axis Title");
    }

    @Test
    public void testGetYAxisTitle() {

        chart.setYAxisTitle("Y Axis Title");
        assertEquals(chart.getYAxisTitle(), "Y Axis Title");
    }

    @Test
    public void testGetChartTitle() throws Exception{
        invokePrivateMethod(chart,"getChartTitle");

    }

    @Test
    public void testGetAxisTitle() {
        assertNotNull(chart.getXAxisTitle());
        assertNotNull(chart.getYAxisTitle());
    }

    @Test
    public void testGetDefaultSeriesRenderStyle() {
        assertEquals(chart.getStyler().getDefaultSeriesRenderStyle(), BubbleSeries.BubbleSeriesRenderStyle.Round);
    }
}
