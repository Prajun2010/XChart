package org.xchart.testng;

import org.knowm.xchart.DialChart;
import org.knowm.xchart.DialChartBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DialChartBuilderTest {

    @Test(priority = 1)
    public void testBuildDialChartBuilder() {
        DialChartBuilder builder = new DialChartBuilder();
        DialChart chart = builder.width(800).height(600).build();
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getWidth(), 800);
        Assert.assertEquals(chart.getHeight(), 600);
    }

    @Test(priority = 2)
    public void testBuildDialChartBuilderWithCustomWidthAndHeight() {
        DialChartBuilder builder = new DialChartBuilder();
        DialChart chart = builder.width(1200).height(900).build();
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getWidth(), 1200);
        Assert.assertEquals(chart.getHeight(), 900);
    }



    @Test(priority = 4)
    public void testBuildDialChartBuilderWithNullTitle() {
        DialChartBuilder builder = new DialChartBuilder();
        DialChart chart = builder.width(800).height(600).title(null).build();
        Assert.assertNotNull(chart);
        Assert.assertNull(chart.getTitle());
    }

    @Test(priority = 5)
    public void testBuildDialChartBuilderWithTitle() {
        DialChartBuilder builder = new DialChartBuilder();
        DialChart chart = builder.width(800).height(600).title("Dial Chart Title").build();
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getTitle(), "Dial Chart Title");
    }

    @Test(priority = 6)
    public void testBuildDialChartBuilderWithNegativeWidth() {
        DialChartBuilder builder = new DialChartBuilder();
        DialChart chart = builder.width(-800).height(600).build();
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getWidth(), -800);
        Assert.assertEquals(chart.getHeight(), 600);
    }

    @Test(priority = 7)
    public void testBuildDialChartBuilderWithNegativeHeight() {
        DialChartBuilder builder = new DialChartBuilder();
        DialChart chart = builder.width(800).height(-600).build();
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getWidth(), 800);
        Assert.assertEquals(chart.getHeight(), -600);
    }

    @Test(priority = 8)
    public void testBuildDialChartBuilderWithZeroWidthHeight() {
        DialChartBuilder builder = new DialChartBuilder();
        DialChart chart = builder.width(0).height(0).build();
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getWidth(), 0);
        Assert.assertEquals(chart.getHeight(), 0);
    }

    @Test(priority = 9)
    public void testBuildDialChartBuilderWithMaxIntegerWidthAndHeight() {
        DialChartBuilder builder = new DialChartBuilder();
        DialChart chart = builder.width(Integer.MAX_VALUE).height(Integer.MAX_VALUE).build();
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getWidth(), Integer.MAX_VALUE);
        Assert.assertEquals(chart.getHeight(), Integer.MAX_VALUE);
    }

    @Test(priority = 10)
    public void testBuildDialChartBuilderWithMinIntegerWidthAndHeight() {
        DialChartBuilder builder = new DialChartBuilder();
        DialChart chart = builder.width(Integer.MIN_VALUE).height(Integer.MIN_VALUE).build();
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getWidth(), Integer.MIN_VALUE);
        Assert.assertEquals(chart.getHeight(), Integer.MIN_VALUE);
    }



}
