package org.knowm.xchart;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuickChartTest {

    @Test
    public void testGetChart_SingleSeries() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {10.0, 20.0, 30.0};
        XYChart chart = QuickChart.getChart("Test Chart", "X", "Y", "Series1", xData, yData);
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getSeriesMap().size(), 1);
        Assert.assertTrue(chart.getStyler().isLegendVisible());
    }

    @Test
    public void testGetChart_SingleSeries_NoLegend() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {10.0, 20.0, 30.0};
        XYChart chart = QuickChart.getChart("Test Chart", "X", "Y", null, xData, yData);
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getSeriesMap().size(), 1);
        Assert.assertFalse(chart.getStyler().isLegendVisible());
    }

    @Test
    public void testGetChart_MultipleSeries() {
        double[] xData = {1.0, 2.0, 3.0};
        double[][] yData = {{10.0, 20.0, 30.0}, {15.0, 25.0, 35.0}};
        String[] seriesNames = {"Series1", "Series2"};
        XYChart chart = QuickChart.getChart("Test Chart", "X", "Y", seriesNames, xData, yData);
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getSeriesMap().size(), 2);
        Assert.assertTrue(chart.getStyler().isLegendVisible());
    }

    @Test
    public void testGetChart_MultipleSeries_NoLegend() {
        double[] xData = {1.0, 2.0, 3.0};
        double[][] yData = {{10.0, 20.0, 30.0}, {15.0, 25.0, 35.0}};
        String[] seriesNames = {"Series1", "Series2"};
        XYChart chart = QuickChart.getChart("Test Chart", "X", "Y", null, xData, yData);
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getSeriesMap().size(), 2);
        Assert.assertFalse(chart.getStyler().isLegendVisible());
    }

    @Test
    public void testGetChart_EmptySeriesName() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {10.0, 20.0, 30.0};
        XYChart chart = QuickChart.getChart("Test Chart", "X", "Y", "", xData, yData);
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getSeriesMap().size(), 1);
        Assert.assertTrue(chart.getStyler().isLegendVisible());
    }

    @Test
    public void testGetChart_NullSeriesName() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {10.0, 20.0, 30.0};
        XYChart chart = QuickChart.getChart("Test Chart", "X", "Y", null, xData, yData);
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getSeriesMap().size(), 1);
        Assert.assertFalse(chart.getStyler().isLegendVisible());
    }

    @Test
    public void testGetChart_CollectionData() {
        List<Number> xData = Arrays.asList(1.0, 2.0, 3.0);
        List<Number> yData = Arrays.asList(10.0, 20.0, 30.0);
        XYChart chart = QuickChart.getChart("Test Chart", "X", "Y", "Series1", xData, yData);
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getSeriesMap().size(), 1);
        Assert.assertTrue(chart.getStyler().isLegendVisible());
    }

    @Test
    public void testGetChart_EmptyCollectionData() {
        List<Number> xData = Collections.emptyList();
        List<Number> yData = Collections.emptyList();
        XYChart chart = QuickChart.getChart("Test Chart", "X", "Y", "Series1", xData, yData);
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getSeriesMap().size(), 1);
        Assert.assertTrue(chart.getStyler().isLegendVisible());
    }

    @Test
    public void testGetChart_NullCollectionData() {
        List<Number> xData = null;
        List<Number> yData = null;
        XYChart chart = QuickChart.getChart("Test Chart", "X", "Y", "Series1", xData, yData);
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getSeriesMap().size(), 0);
    }

    @Test
    public void testGetChart_MarkerSetting() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {10.0, 20.0, 30.0};
        XYChart chart = QuickChart.getChart("Test Chart", "X", "Y", "Series1", xData, yData);
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getSeriesMap().size(), 1);
        Assert.assertEquals(chart.getSeriesMap().get("Series1").getMarker(), SeriesMarkers.NONE);
    }

    @Test
    public void testGetChart_NullMarkerSetting() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {10.0, 20.0, 30.0};
        XYChart chart = QuickChart.getChart("Test Chart", "X", "Y", "Series1", xData, yData);
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getSeriesMap().size(), 1);
        Assert.assertEquals(chart.getSeriesMap().get("Series1").getMarker(), SeriesMarkers.NONE);
    }
}
