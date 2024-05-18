package org.xchart.testng;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.theme.MatlabTheme;
import org.testng.Assert;
import org.testng.annotations.*;

public class XYChartBuilderTest {

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Before Suite");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("Before Class");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("Before Test");
    }

    @Test(groups = "title")
    public void testXAxisTitle() {
        XYChartBuilder builder = new XYChartBuilder();
        builder.xAxisTitle("X Axis");
        Assert.assertEquals(builder.xAxisTitle, "X Axis");
    }

    @Test(groups = "title")
    public void testYAxisTitle() {
        XYChartBuilder builder = new XYChartBuilder();
        builder.yAxisTitle("Y Axis");
        Assert.assertEquals(builder.yAxisTitle, "Y Axis");
    }

    @Test(groups = "build")
    public void testBuild() {
        XYChartBuilder builder = new XYChartBuilder();
        XYChart chart = builder.build();
        Assert.assertNotNull(chart);
    }

    @Test(groups = "title")
    public void testEmptyTitle() {
        XYChartBuilder builder = new XYChartBuilder();
        XYChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), "");
        Assert.assertEquals(chart.getYAxisTitle(), "");
    }

    @Test(groups = "title")
    public void testXAxisTitleNull() {
        XYChartBuilder builder = new XYChartBuilder();
        builder.xAxisTitle(null);
        XYChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), null);
    }

    @Test(groups = "title")
    public void testYAxisTitleNull() {
        XYChartBuilder builder = new XYChartBuilder();
        builder.yAxisTitle(null);
        XYChart chart = builder.build();
        Assert.assertEquals(chart.getYAxisTitle(), null);
    }

    @Test(groups = "title")
    public void testBothTitlesNull() {
        XYChartBuilder builder = new XYChartBuilder();
        builder.xAxisTitle(null).yAxisTitle(null);
        XYChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), null);
        Assert.assertEquals(chart.getYAxisTitle(), null);
    }

    @Test(groups = "title")
    public void testLongXAxisTitle() {
        XYChartBuilder builder = new XYChartBuilder();
        builder.xAxisTitle("This is a very long X Axis title");
        XYChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), "This is a very long X Axis title");
    }

    @Test(groups = "title")
    public void testLongYAxisTitle() {
        XYChartBuilder builder = new XYChartBuilder();
        builder.yAxisTitle("This is a very long Y Axis title");
        XYChart chart = builder.build();
        Assert.assertEquals(chart.getYAxisTitle(), "This is a very long Y Axis title");
    }

    @Test(groups = "title")
    public void testEmptyChartTitle() {
        XYChartBuilder builder = new XYChartBuilder();
        XYChart chart = builder.build();
        Assert.assertEquals(chart.getTitle(), "");
    }

    @Test(groups = "title")
    public void testSetChartTitle() {
        XYChartBuilder builder = new XYChartBuilder();
        builder.title("My Chart Title");
        XYChart chart = builder.build();
        Assert.assertEquals(chart.getTitle(), "My Chart Title");
    }

    @Test(groups = "title")
    public void testLongChartTitle() {
        XYChartBuilder builder = new XYChartBuilder();
        builder.title("This is a very long chart title");
        XYChart chart = builder.build();
        Assert.assertEquals(chart.getTitle(), "This is a very long chart title");
    }

    @Test(groups = "size")
    public void testSetWidth() {
        XYChartBuilder builder = new XYChartBuilder();
        builder.width(800);
        XYChart chart = builder.build();
        Assert.assertEquals(chart.getWidth(), 800);
    }

    @Test(groups = "size")
    public void testSetHeight() {
        XYChartBuilder builder = new XYChartBuilder();
        builder.height(600);
        XYChart chart = builder.build();
        Assert.assertEquals(chart.getHeight(), 600);
    }

    @Test(groups = "style")
    public void testSetTheme() {
        XYChartBuilder builder = new XYChartBuilder();
        builder.theme(Styler.ChartTheme.Matlab);
        XYChart chart = builder.build();
        Assert.assertTrue(chart.getStyler().getTheme() instanceof MatlabTheme);;
    }

    // Add more tests as needed...

    @AfterSuite
    public void afterSuite() {
        System.out.println("After Suite");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("After Class");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("After Test");
    }
}
