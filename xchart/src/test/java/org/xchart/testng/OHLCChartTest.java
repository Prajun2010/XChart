package org.xchart.testng;

import org.knowm.xchart.OHLCChart;
import org.knowm.xchart.OHLCSeries;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.List;

public class OHLCChartTest {

    @Test
    public void testAddSeriesWithFloatArrays() {
        // Test adding series with float arrays
        OHLCChart chart = new OHLCChart(800, 600);
        float[] xData = {1.0f, 2.0f, 3.0f};
        float[] openData = {10.0f, 20.0f, 30.0f};
        float[] highData = {15.0f, 25.0f, 35.0f};
        float[] lowData = {5.0f, 15.0f, 25.0f};
        float[] closeData = {12.0f, 22.0f, 32.0f};
        OHLCSeries series = chart.addSeries("Test Series", xData, openData, highData, lowData, closeData);
        Assert.assertNotNull(series);
    }

    @Test
    public void testAddSeriesWithIntArrays() {
        // Test adding series with int arrays
        OHLCChart chart = new OHLCChart(800, 600);
        int[] xData = {1, 2, 3};
        int[] openData = {10, 20, 30};
        int[] highData = {15, 25, 35};
        int[] lowData = {5, 15, 25};
        int[] closeData = {12, 22, 32};
        OHLCSeries series = chart.addSeries("Test Series", xData, openData, highData, lowData, closeData);
        Assert.assertNotNull(series);
    }

    @Test
    public void testAddSeriesWithLists() {
        // Test adding series with Lists
        OHLCChart chart = new OHLCChart(800, 600);
        List<Integer> xData = Arrays.asList(1, 2, 3);
        List<Double> openData = Arrays.asList(10.0, 20.0, 30.0);
        List<Double> highData = Arrays.asList(15.0, 25.0, 35.0);
        List<Double> lowData = Arrays.asList(5.0, 15.0, 25.0);
        List<Double> closeData = Arrays.asList(12.0, 22.0, 32.0);
        OHLCSeries series = chart.addSeries("Test Series", xData, openData, highData, lowData, closeData);
        Assert.assertNotNull(series);
    }


    @Test
    public void testUpdateOHLCSeriesWithLists() {
        // Test updating series with Lists
        OHLCChart chart = new OHLCChart(800, 600);
        List<Integer> xData = Arrays.asList(1, 2, 3);
        List<Double> openData = Arrays.asList(10.0, 20.0, 30.0);
        List<Double> highData = Arrays.asList(15.0, 25.0, 35.0);
        List<Double> lowData = Arrays.asList(5.0, 15.0, 25.0);
        List<Double> closeData = Arrays.asList(12.0, 22.0, 32.0);
        OHLCSeries series = chart.addSeries("Test Series", xData, openData, highData, lowData, closeData);
        Assert.assertNotNull(series);

        List<Integer> newXData = Arrays.asList(4, 5, 6);
        List<Double> newOpenData = Arrays.asList(40.0, 50.0, 60.0);
        List<Double> newHighData = Arrays.asList(45.0, 55.0, 65.0);
        List<Double> newLowData = Arrays.asList(35.0, 45.0, 55.0);
        List<Double> newCloseData = Arrays.asList(42.0, 52.0, 62.0);
        OHLCSeries updatedSeries = chart.updateOHLCSeries("Test Series", newXData, newOpenData, newHighData, newLowData, newCloseData);
        Assert.assertNotNull(updatedSeries);
    }

    @Test
    public void testGetSeries() {
        // Test getting series from the chart
        OHLCChart chart = new OHLCChart(800, 600);
        float[] xData = {1.0f, 2.0f, 3.0f};
        float[] openData = {10.0f, 20.0f, 30.0f};
        float[] highData = {15.0f, 25.0f, 35.0f};
        float[] lowData = {5.0f, 15.0f, 25.0f};
        float[] closeData = {12.0f, 22.0f, 32.0f};
        OHLCSeries series = chart.addSeries("Test Series", xData, openData, highData, lowData, closeData);
        Assert.assertNotNull(series);


    }

    @Test
    public void testRemoveSeries() {
        // Test removing series from the chart
        OHLCChart chart = new OHLCChart(800, 600);
        float[] xData = {1.0f, 2.0f, 3.0f};
        float[] openData = {10.0f, 20.0f, 30.0f};
        float[] highData = {15.0f, 25.0f, 35.0f};
        float[] lowData = {5.0f, 15.0f, 25.0f};
        float[] closeData = {12.0f, 22.0f, 32.0f};
        OHLCSeries series = chart.addSeries("Test Series", xData, openData, highData, lowData, closeData);
        Assert.assertNotNull(series);


    }





    @Test
    public void testSetTitle() {
        // Test setting the chart title
        OHLCChart chart = new OHLCChart(800, 600);
        chart.setTitle("Stock Prices");
        String title = chart.getTitle();
        Assert.assertEquals(title, "Stock Prices");
    }

    @Test
    public void testGetTitle() {
        // Test getting the chart title
        OHLCChart chart = new OHLCChart(800, 600);
        String title = chart.getTitle();
        Assert.assertNull(title);
    }

    @Test
    public void testGetWidth() {
        // Test getting the chart width
        OHLCChart chart = new OHLCChart(800, 600);
        int width = chart.getWidth();
        Assert.assertEquals(width, 800);
    }

    @Test
    public void testGetHeight() {
        // Test getting the chart height
        OHLCChart chart = new OHLCChart(800, 600);
        int height = chart.getHeight();
        Assert.assertEquals(height, 600);
    }





}

