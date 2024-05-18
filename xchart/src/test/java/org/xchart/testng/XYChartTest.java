package org.xchart.testng;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.internal.Utils;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Arrays;
import java.util.List;

public class XYChartTest {

    private XYChart chart;

    @BeforeClass
    public void setUpClass() {
        chart = new XYChart(800, 600);
    }

    @Test(groups = "addSeries")
    public void testAddSeriesWithDoubleArrays() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {4.0, 5.0, 6.0};
        XYSeries series = chart.addSeries("Series1", xData, yData);
        Assert.assertEquals(series.getName(), "Series1");
        Assert.assertEquals(series.getXData(), xData);
        Assert.assertEquals(series.getYData(), yData);
    }

    @Test(groups = "addSeries")
    public void testAddSeriesWithFloatArrays() {
        float[] xData = {1.0f, 2.0f, 3.0f};
        float[] yData = {4.0f, 5.0f, 6.0f};
        XYSeries series = chart.addSeries("Series2", xData, yData);
        Assert.assertEquals(series.getName(), "Series2");
        Assert.assertEquals(series.getXData(), Utils.getDoubleArrayFromFloatArray(xData));
        Assert.assertEquals(series.getYData(), Utils.getDoubleArrayFromFloatArray(yData));
    }

    @Test(groups = "addSeries")
    public void testAddSeriesWithIntArrays() {
        int[] xData = {1, 2, 3};
        int[] yData = {4, 5, 6};
        XYSeries series = chart.addSeries("Series3", xData, yData);
        Assert.assertEquals(series.getName(), "Series3");
        Assert.assertEquals(series.getXData(), Utils.getDoubleArrayFromIntArray(xData));
        Assert.assertEquals(series.getYData(), Utils.getDoubleArrayFromIntArray(yData));
    }

    @Test(groups = "addSeries")
    public void testAddSeriesWithLists() {
        List<Double> xData = Arrays.asList(1.0, 2.0, 3.0);
        List<Double> yData = Arrays.asList(4.0, 5.0, 6.0);
        XYSeries series = chart.addSeries("Series4", xData, yData);
        Assert.assertEquals(series.getName(), "Series4");
        Assert.assertEquals(series.getXData(), Utils.getDoubleArrayFromNumberList(xData));
        Assert.assertEquals(series.getYData(), Utils.getDoubleArrayFromNumberList(yData));
    }

    @Test(groups = "updateSeries")
    public void testUpdateXYSeriesWithDoubleArrays() {
        double[] newXData = {1.0, 2.0, 3.0};
        double[] newYData = {4.0, 5.0, 6.0};
        XYSeries series = chart.addSeries("Series5", newXData, newYData);

        // Update with new data
        double[] updatedXData = {4.0, 5.0, 6.0};
        double[] updatedYData = {7.0, 8.0, 9.0};
        XYSeries updatedSeries = chart.updateXYSeries("Series5", updatedXData, updatedYData, null);

        Assert.assertEquals(updatedSeries.getXData(), updatedXData);
        Assert.assertEquals(updatedSeries.getYData(), updatedYData);
    }

    @Test(groups = "updateSeries")
    public void testUpdateXYSeriesWithLists() {
        List<Double> newXData = Arrays.asList(1.0, 2.0, 3.0);
        List<Double> newYData = Arrays.asList(4.0, 5.0, 6.0);
        XYSeries series = chart.addSeries("Series6", newXData, newYData);

        // Update with new data
        List<Double> updatedXData = Arrays.asList(4.0, 5.0, 6.0);
        List<Double> updatedYData = Arrays.asList(7.0, 8.0, 9.0);
        XYSeries updatedSeries = chart.updateXYSeries("Series6", updatedXData, updatedYData, null);

        Assert.assertEquals(updatedSeries.getXData(), Utils.getDoubleArrayFromNumberList(updatedXData));
        Assert.assertEquals(updatedSeries.getYData(), Utils.getDoubleArrayFromNumberList(updatedYData));
    }

    @Test(groups = "addSeries", expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesWithEmptyXData() {
        double[] xData = {};
        double[] yData = {1.0, 2.0, 3.0};
        chart.addSeries("Series7", xData, yData);
    }

    @Test(groups = "addSeries", expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesWithEmptyYData() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {};
        chart.addSeries("Series8", xData, yData);
    }

    @Test(groups = "addSeries", expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesWithEmptyXAndYData() {
        double[] xData = {};
        double[] yData = {};
        chart.addSeries("Series9", xData, yData);
    }

    @Test(groups = "updateSeries", expectedExceptions = IllegalArgumentException.class)
    public void testUpdateXYSeriesWithEmptyXData() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {4.0, 5.0, 6.0};
        XYSeries series = chart.addSeries("Series10", xData, yData);

        double[] emptyXData = {};
        double[] newYData = {7.0, 8.0, 9.0};
        chart.updateXYSeries("Series10", emptyXData, newYData, null);
    }

    @Test(groups = "updateSeries", expectedExceptions = IllegalArgumentException.class)
    public void testUpdateXYSeriesWithEmptyYData() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {4.0, 5.0, 6.0};
        XYSeries series = chart.addSeries("Series11", xData, yData);

        double[] newXData = {4.0, 5.0, 6.0};
        double[] emptyYData = {};
        chart.updateXYSeries("Series11", newXData, emptyYData, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateXYSeriesWithEmptyXAndYData() {

        // Arrange
        String seriesName = "SeriesName"; // Define seriesName
        double[] xData = {};
        double[] yData = {};

        // Act
        System.out.println("Before calling updateXYSeries: xData length = " + xData.length + ", yData length = " + yData.length);
        chart.updateXYSeries(seriesName, xData, yData, null);
        System.out.println("After calling updateXYSeries");

        // Assert (handled by the @Test annotation)
    }

    @Test(groups = "addSeries")
    public void testAddSeriesWithNullXData() {
        double[] yData = {4.0, 5.0, 6.0};
        XYSeries series = chart.addSeries("Series7", null, yData);
        Assert.assertEquals(series.getXData().length, yData.length);
        Assert.assertEquals(series.getYData(), yData);
    }

    @Test(groups = "addSeries", expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesWithNullYData() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = null;
        chart.addSeries("Series14", xData, yData);
    }

    @AfterClass
    public void tearDownClass() {
        // Clean up resources
        chart = null;
    }
}
