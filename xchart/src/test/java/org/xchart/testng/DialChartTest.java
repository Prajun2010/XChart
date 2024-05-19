package org.xchart.testng;

import org.knowm.xchart.custom.CustomTheme;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.knowm.xchart.DialChart;
import org.knowm.xchart.DialSeries;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.theme.Theme;

public class DialChartTest {

    @Test(priority = 1)
    public void testConstructorWithDefaultTheme() {
        DialChart chart = new DialChart(800, 600);
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getWidth(), 800);
        Assert.assertEquals(chart.getHeight(), 600);
    }

    @Test(priority = 2)
    public void testConstructorWithCustomTheme() {
        Theme customTheme = new CustomTheme(); // Define your custom theme class
        DialChart chart = new DialChart(800, 600, customTheme);
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getWidth(), 800);
        Assert.assertEquals(chart.getHeight(), 600);

    }

    @Test(priority = 3)
    public void testConstructorWithChartTheme() {
        DialChart chart = new DialChart(800, 600, ChartTheme.Matlab);
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getWidth(), 800);
        Assert.assertEquals(chart.getHeight(), 600);

    }

    @Test(priority = 4, expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesWithDuplicateName() {
        DialChart chart = new DialChart(800, 600);
        chart.addSeries("Series1", 0.5);
        chart.addSeries("Series1", 0.6);
    }

    @Test(priority = 5, expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesWithValueOutOfRange() {
        DialChart chart = new DialChart(800, 600);
        chart.addSeries("Series1", -0.1);
    }

    @Test(priority = 6)
    public void testAddSeriesWithValidData() {
        DialChart chart = new DialChart(800, 600);
        DialSeries series = chart.addSeries("Series1", 0.7, "Label1");
        Assert.assertNotNull(series);
        Assert.assertEquals(series.getName(), "Series1");
        Assert.assertEquals(series.getValue(), 0.7);
        Assert.assertEquals(series.getLabel(), "Label1");
    }

    @Test(priority = 7, expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesWithValueTooHigh() {
        DialChart chart = new DialChart(800, 600);
        chart.addSeries("Series1", 1.1);
    }

    @Test(priority = 8, expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesWithNullName() {
        DialChart chart = new DialChart(800, 600);
        chart.addSeries(null, 0.5);
    }

    @Test(priority = 9)
    public void testConstructorWithNullTheme() {
        DialChart chart = new DialChart(800, 600, (Theme)null);
        Assert.assertNotNull(chart);

    }

    @Test(priority = 10, expectedExceptions = IllegalArgumentException.class)
    public void testConstructorWithNegativeWidth() {
        DialChart chart = new DialChart(-800, 600);
    }

    @Test(priority = 11, expectedExceptions = IllegalArgumentException.class)
    public void testConstructorWithNegativeHeight() {
        DialChart chart = new DialChart(800, -600);
    }

    @Test(priority = 12)
    public void testAddMultipleSeries() {
        DialChart chart = new DialChart(800, 600);
        DialSeries series1 = chart.addSeries("Series1", 0.4);
        DialSeries series2 = chart.addSeries("Series2", 0.6, "Label2");
        Assert.assertEquals(chart.getSeriesMap().size(), 2);
        Assert.assertEquals(chart.getSeriesMap().get("Series1"), series1);
        Assert.assertEquals(chart.getSeriesMap().get("Series2"), series2);
    }

    @Test(priority = 13)
    public void testAddSeriesWithMaxValue() {
        DialChart chart = new DialChart(800, 600);
        DialSeries series = chart.addSeries("SeriesMax", 1.0);
        Assert.assertNotNull(series);
        Assert.assertEquals(series.getValue(), 1.0);
    }

    @Test(priority = 14)
    public void testAddSeriesWithMinValue() {
        DialChart chart = new DialChart(800, 600);
        DialSeries series = chart.addSeries("SeriesMin", 0.0);
        Assert.assertNotNull(series);
        Assert.assertEquals(series.getValue(), 0.0);
    }

    @Test(priority = 15)
    public void testAddSeriesWithDefaultLabel() {
        DialChart chart = new DialChart(800, 600);
        DialSeries series = chart.addSeries("SeriesLabel", 0.8);
        Assert.assertNotNull(series);
        Assert.assertNull(series.getLabel());
    }

    @Test(priority = 16)
    public void testSetAndGetTitle() {
        DialChart chart = new DialChart(800, 600);
        chart.setTitle("Test Dial Chart");
        Assert.assertEquals(chart.getTitle(), "Test Dial Chart");
    }



    @Test(priority = 18)
    public void testSetAndGetHeight() {
        DialChart chart = new DialChart(800, 600);

        Assert.assertEquals(chart.getHeight(), 700);
    }

    @Test(priority = 20)
    public void testAddSeriesWithEmptyLabel() {
        DialChart chart = new DialChart(800, 600);
        DialSeries series = chart.addSeries("SeriesEmptyLabel", 0.3, "");
        Assert.assertNotNull(series);
        Assert.assertEquals(series.getLabel(), "");
    }

    @Test(priority = 21, expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesWithInvalidValue() {
        DialChart chart = new DialChart(800, 600);
        chart.addSeries("SeriesInvalidValue", -0.1);
    }

    @Test(priority = 22)
    public void testAddSeriesWithMaxAllowedValue() {
        DialChart chart = new DialChart(800, 600);
        DialSeries series = chart.addSeries("SeriesMaxAllowedValue", 1.0);
        Assert.assertNotNull(series);
        Assert.assertEquals(series.getValue(), 1.0);
    }

    @Test(priority = 23)
    public void testAddSeriesWithMinAllowedValue() {
        DialChart chart = new DialChart(800, 600);
        DialSeries series = chart.addSeries("SeriesMinAllowedValue", 0.0);
        Assert.assertNotNull(series);
        Assert.assertEquals(series.getValue(), 0.0);
    }

    @Test(priority = 24)
    public void testAddMultipleSeriesWithSameValue() {
        DialChart chart = new DialChart(800, 600);
        DialSeries series1 = chart.addSeries("Series1", 0.5);
        DialSeries series2 = chart.addSeries("Series2", 0.5, "Label2");
        Assert.assertNotNull(series1);
        Assert.assertNotNull(series2);
        Assert.assertEquals(chart.getSeriesMap().size(), 2);
        Assert.assertEquals(chart.getSeriesMap().get("Series1").getValue(), 0.5);
        Assert.assertEquals(chart.getSeriesMap().get("Series2").getValue(), 0.5);
    }

    @Test(priority = 25)
    public void testAddSeriesWithMaximumWidthAndHeight() {
        DialChart chart = new DialChart(Integer.MAX_VALUE, Integer.MAX_VALUE);
        DialSeries series = chart.addSeries("SeriesMaxWidthHeight", 0.8);
        Assert.assertNotNull(series);
        Assert.assertEquals(chart.getWidth(), Integer.MAX_VALUE);
        Assert.assertEquals(chart.getHeight(), Integer.MAX_VALUE);
    }

    @Test(priority = 26)
    public void testAddSeriesWithMinimumWidthAndHeight() {
        DialChart chart = new DialChart(1, 1);
        DialSeries series = chart.addSeries("SeriesMinWidthHeight", 0.8);
        Assert.assertNotNull(series);
        Assert.assertEquals(chart.getWidth(), 1);
        Assert.assertEquals(chart.getHeight(), 1);
    }

    @Test(priority = 27)
    public void testAddSeriesWithNegativeWidth() {
        DialChart chart = new DialChart(-800, 600);
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getWidth(), -800);
        Assert.assertEquals(chart.getHeight(), 600);
    }

    @Test(priority = 28)
    public void testAddSeriesWithNegativeHeight() {
        DialChart chart = new DialChart(800, -600);
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getWidth(), 800);
        Assert.assertEquals(chart.getHeight(), -600);
    }

    @Test(priority = 29)
    public void testAddSeriesWithLongSeriesName() {
        DialChart chart = new DialChart(800, 600);
        String longName = "ThisIsALongSeriesNameThatExceedsNormalLength";
        DialSeries series = chart.addSeries(longName, 0.6);
        Assert.assertNotNull(series);
        Assert.assertEquals(series.getName(), longName);
    }



    @Test(priority = 31)
    public void testSetAndGetTitleWithSpecialCharacters() {
        DialChart chart = new DialChart(800, 600);
        chart.setTitle("Dial Chart with Special Characters: !@#$%^&*()_+-={}[]:\";'<>,.?/~");
        Assert.assertEquals(chart.getTitle(), "Dial Chart with Special Characters: !@#$%^&*()_+-={}[]:\";'<>,.?/~");
    }

    @Test(priority = 32)
    public void testSetAndGetTitleWithUnicodeCharacters() {
        DialChart chart = new DialChart(800, 600);
        chart.setTitle("Dial Chart with Unicode Characters: 你好，世界！");
        Assert.assertEquals(chart.getTitle(), "Dial Chart with Unicode Characters: 你好，世界！");
    }

    @Test(priority = 33)
    public void testSetAndGetTitleWithEmptyString() {
        DialChart chart = new DialChart(800, 600);
        chart.setTitle("");
        Assert.assertEquals(chart.getTitle(), "");
    }

    @Test(priority = 34)
    public void testSetAndGetTitleWithNullString() {
        DialChart chart = new DialChart(800, 600);
        chart.setTitle(null);
        Assert.assertNull(chart.getTitle());
    }

    @Test(priority = 35)
    public void testSetAndGetTitleWithLeadingTrailingSpaces() {
        DialChart chart = new DialChart(800, 600);
        chart.setTitle("   Dial Chart with Spaces   ");
        Assert.assertEquals(chart.getTitle(), "   Dial Chart with Spaces   ");
    }

}
