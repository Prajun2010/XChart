package org.xchart.testng;

import org.knowm.xchart.*;
import org.knowm.xchart.style.HeatMapStyler;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.theme.Theme;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HeatMapChartTest {

    @Test
    public void testDefaultConstructor() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        Assert.assertNotNull(chart);
    }


    @Test
    public void testConstructorWithChartTheme() {
        HeatMapChart chart = new HeatMapChart(800, 600, ChartTheme.GGPlot2);
        Assert.assertNotNull(chart);
    }

       @Test
    public void testAddSeriesValidData() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        int[] xData = {0, 1, 2};
        int[] yData = {0, 1, 2};
        int[][] heatData = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        HeatMapSeries series = chart.addSeries("Series1", xData, yData, heatData);
        Assert.assertNotNull(series);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesNullXData() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        int[] yData = {0, 1, 2};
        int[][] heatData = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        chart.addSeries("Series1", null, yData, heatData);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesEmptyXData() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        int[] xData = {};
        int[] yData = {0, 1, 2};
        int[][] heatData = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        chart.addSeries("Series1", xData, yData, heatData);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesNullYData() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        int[] xData = {0, 1, 2};
        int[][] heatData = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        chart.addSeries("Series1", xData, null, heatData);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesEmptyYData() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        int[] xData = {0, 1, 2};
        int[] yData = {};
        int[][] heatData = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        chart.addSeries("Series1", xData, yData, heatData);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesNullHeatData() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        int[] xData = {0, 1, 2};
        int[] yData = {0, 1, 2};
        chart.addSeries("Series1", xData, yData, (int[][]) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesEmptyHeatData() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        int[] xData = {0, 1, 2};
        int[] yData = {0, 1, 2};
        int[][] heatData = {};
        chart.addSeries("Series1", xData, yData, heatData);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesHeatDataColumnLengthNotThree() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        int[] xData = {0, 1, 2};
        int[] yData = {0, 1, 2};
        int[][] heatData = {{1, 2}, {4, 5, 6}, {7, 8, 9}};
        chart.addSeries("Series1", xData, yData, heatData);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesNegativeHeatDataColumns() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        int[] xData = {0, 1, 2};
        int[] yData = {0, 1, 2};
        int[][] heatData = {{1, -2, 3}, {4, 5, 6}, {7, 8, 9}};
        chart.addSeries("Series1", xData, yData, heatData);
    }

    @Test
    public void testUpdateSeriesValidData() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        int[] xData = {0, 1, 2};
        int[] yData = {0, 1, 2};
        int[][] heatData = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        chart.addSeries("Series1", xData, yData, heatData);

        int[] newXData = {0, 1, 2};
        int[] newYData = {0, 1, 2};
        int[][] newHeatData = {{9, 8, 7}, {6, 5, 4}, {3, 2, 1}};
        HeatMapSeries updatedSeries = chart.updateSeries("Series1", newXData, newYData, newHeatData);
        Assert.assertNotNull(updatedSeries);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateSeriesNonExistentSeries() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        int[] xData = {0, 1, 2};
        int[] yData = {0, 1, 2};
        int[][] heatData = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        chart.updateSeries("NonExistentSeries", xData, yData, heatData);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateSeriesNullXData() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        int[] xData = {0, 1, 2};
        int[] yData = {0, 1, 2};
        int[][] heatData = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        chart.addSeries("Series1", xData, yData, heatData);

        int[] newYData = {0, 1, 2};
        int[][] newHeatData = {{9, 8, 7}, {6, 5, 4}, {3, 2, 1}};
        chart.updateSeries("Series1", null, newYData, newHeatData);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateSeriesEmptyXData() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        int[] xData = {0, 1, 2};
        int[] yData = {0, 1, 2};
        int[][] heatData = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        chart.addSeries("Series1", xData, yData, heatData);

        int[] newXData = {};
        int[] newYData = {0, 1, 2};
        int[][] newHeatData = {{9, 8, 7}, {6, 5, 4}, {3, 2, 1}};
        chart.updateSeries("Series1", newXData, newYData, newHeatData);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateSeriesNullYData() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        int[] xData = {0, 1, 2};
        int[] yData = {0, 1, 2};
        int[][] heatData = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        chart.addSeries("Series1", xData, yData, heatData);

        int[] newXData = {0, 1, 2};
        int[][] newHeatData = {{9, 8, 7}, {6, 5, 4}, {3, 2, 1}};
        chart.updateSeries("Series1", newXData, null, newHeatData);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateSeriesEmptyYData() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        int[] xData = {0, 1, 2};
        int[] yData = {0, 1, 2};
        int[][] heatData = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        chart.addSeries("Series1", xData, yData, heatData);

        int[] newXData = {0, 1, 2};
        int[] newYData = {};
        int[][] newHeatData = {{9, 8, 7}, {6, 5, 4}, {3, 2, 1}};
        chart.updateSeries("Series1", newXData, newYData, newHeatData);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateSeriesNullHeatData() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        int[] xData = {0, 1, 2};
        int[] yData = {0, 1, 2};
        int[][] heatData = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        chart.addSeries("Series1", xData, yData, heatData);

        int[] newXData = {0, 1, 2};
        int[] newYData = {0, 1, 2};
        chart.updateSeries("Series1", newXData, newYData, (int[][]) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateSeriesEmptyHeatData() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        int[] xData = {0, 1, 2};
        int[] yData = {0, 1, 2};
        int[][] heatData = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        chart.addSeries("Series1", xData, yData, heatData);

        int[] newXData = {0, 1, 2};
        int[] newYData = {0, 1, 2};
        int[][] newHeatData = {};
        chart.updateSeries("Series1", newXData, newYData, newHeatData);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateSeriesHeatDataColumnLengthNotThree() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        int[] xData = {0, 1, 2};
        int[] yData = {0, 1, 2};
        int[][] heatData = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        chart.addSeries("Series1", xData, yData, heatData);

        int[] newXData = {0, 1, 2};
        int[] newYData = {0, 1, 2};
        int[][] newHeatData = {{9, 8}, {6, 5, 4}, {3, 2, 1}};
        chart.updateSeries("Series1", newXData, newYData, newHeatData);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateSeriesNegativeHeatDataColumns() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        int[] xData = {0, 1, 2};
        int[] yData = {0, 1, 2};
        int[][] heatData = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        chart.addSeries("Series1", xData, yData, heatData);

        int[] newXData = {0, 1, 2};
        int[] newYData = {0, 1, 2};
        int[][] newHeatData = {{-9, 8, 7}, {6, 5, 4}, {3, 2, 1}};
        chart.updateSeries("Series1", newXData, newYData, newHeatData);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testAddSeriesTwice() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        int[] xData = {0, 1, 2};
        int[] yData = {0, 1, 2};
        int[][] heatData = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        chart.addSeries("Series1", xData, yData, heatData);
        chart.addSeries("Series2", xData, yData, heatData);
    }

    @Test
    public void testGetHeatMapSeries() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        int[] xData = {0, 1, 2};
        int[] yData = {0, 1, 2};
        int[][] heatData = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        chart.addSeries("Series1", xData, yData, heatData);
        HeatMapSeries series = chart.getHeatMapSeries();
        Assert.assertNotNull(series);
    }

    @Test
    public void testPaintNoSeriesAdded() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        Graphics2D g2d = (Graphics2D) new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB).getGraphics();
        chart.paint(g2d, 800, 600);
        Assert.assertTrue(true); // If no exception, the test passes
    }

    @Test
    public void testPaintSeriesAdded() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        int[] xData = {0, 1, 2};
        int[] yData = {0, 1, 2};
        int[][] heatData = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        chart.addSeries("Series1", xData, yData, heatData);

        Graphics2D g2d = (Graphics2D) new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB).getGraphics();
        chart.paint(g2d, 800, 600);
        Assert.assertTrue(true); // If no exception, the test passes
    }



    @Test
    public void testSetTitle() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        chart.setTitle("New Title");
        Assert.assertEquals(chart.getTitle(), "New Title");
    }

    @Test
    public void testSetXAxisTitle() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        chart.setXAxisTitle("New X Axis Title");
        Assert.assertEquals(chart.getXAxisTitle(), "New X Axis Title");
    }

    @Test
    public void testSetYAxisTitle() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        chart.setYAxisTitle("New Y Axis Title");
        Assert.assertEquals(chart.getYAxisTitle(), "New Y Axis Title");
    }

    @Test
    public void testSetStyler() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        HeatMapStyler styler = new HeatMapStyler();

        Assert.assertEquals(chart.getStyler(), styler);
    }

    @Test
    public void testGetStyler() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        HeatMapStyler styler = chart.getStyler();
        Assert.assertNotNull(styler);
    }











    @Test
    public void testGetWidth() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        Assert.assertEquals(chart.getWidth(), 800);
    }

    @Test
    public void testGetHeight() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        Assert.assertEquals(chart.getHeight(), 600);
    }

    @Test
    public void testGetTitle() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        chart.setTitle("Test Title");
        Assert.assertEquals(chart.getTitle(), "Test Title");
    }

    @Test
    public void testGetXAxisTitle() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        chart.setXAxisTitle("Test X Axis Title");
        Assert.assertEquals(chart.getXAxisTitle(), "Test X Axis Title");
    }

    @Test
    public void testGetYAxisTitle() {
        HeatMapChart chart = new HeatMapChart(800, 600);
        chart.setYAxisTitle("Test Y Axis Title");
        Assert.assertEquals(chart.getYAxisTitle(), "Test Y Axis Title");
    }
}
