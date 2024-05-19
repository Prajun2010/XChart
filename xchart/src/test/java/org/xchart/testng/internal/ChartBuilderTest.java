package org.xchart.testng.internal;


import org.knowm.xchart.style.Styler.ChartTheme;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


// Dummy Chart class for testing


// Dummy ChartBuilder implementation for testing

public class ChartBuilderTest {

    private DummyChartBuilder chartBuilder;

    @BeforeMethod
    public void setUp() {
        chartBuilder = new DummyChartBuilder();
    }

    @Test
    public void testDefaultWidth() {
        Assert.assertEquals(chartBuilder.width, 800);
    }

    @Test
    public void testDefaultHeight() {
        Assert.assertEquals(chartBuilder.height, 600);
    }

    @Test
    public void testDefaultTitle() {
        Assert.assertEquals(chartBuilder.title, "");
    }

    @Test
    public void testDefaultChartTheme() {
        Assert.assertEquals(chartBuilder.chartTheme, ChartTheme.XChart);
    }

    @Test
    public void testSetWidth() {
        int newWidth = 1024;
        chartBuilder.width(newWidth);
        Assert.assertEquals(chartBuilder.width, newWidth);
    }

    @Test
    public void testSetHeight() {
        int newHeight = 768;
        chartBuilder.height(newHeight);
        Assert.assertEquals(chartBuilder.height, newHeight);
    }

    @Test
    public void testSetTitle() {
        String newTitle = "Test Chart";
        chartBuilder.title(newTitle);
        Assert.assertEquals(chartBuilder.title, newTitle);
    }

    @Test
    public void testSetChartTheme() {
        ChartTheme newTheme = ChartTheme.GGPlot2;
        chartBuilder.theme(newTheme);
        Assert.assertEquals(chartBuilder.chartTheme, newTheme);
    }

    @Test
    public void testBuild() {
        DummyChart chart = chartBuilder.build();
        Assert.assertNotNull(chart);
    }

    @Test
    public void testChainedWidth() {
        int newWidth = 1024;
        chartBuilder.width(newWidth).build();
        Assert.assertEquals(chartBuilder.width, newWidth);
    }

    @Test
    public void testChainedHeight() {
        int newHeight = 768;
        chartBuilder.height(newHeight).build();
        Assert.assertEquals(chartBuilder.height, newHeight);
    }

    @Test
    public void testChainedTitle() {
        String newTitle = "Chained Test Chart";
        chartBuilder.title(newTitle).build();
        Assert.assertEquals(chartBuilder.title, newTitle);
    }

    @Test
    public void testChainedTheme() {
        ChartTheme newTheme = ChartTheme.GGPlot2;
        chartBuilder.theme(newTheme).build();
        Assert.assertEquals(chartBuilder.chartTheme, newTheme);
    }

    @Test
    public void testMultipleChainedSettings() {
        int newWidth = 1024;
        int newHeight = 768;
        String newTitle = "Multiple Chained Test Chart";
        ChartTheme newTheme = ChartTheme.GGPlot2;

        chartBuilder.width(newWidth)
                .height(newHeight)
                .title(newTitle)
                .theme(newTheme)
                .build();

        Assert.assertEquals(chartBuilder.width, newWidth);
        Assert.assertEquals(chartBuilder.height, newHeight);
        Assert.assertEquals(chartBuilder.title, newTitle);
        Assert.assertEquals(chartBuilder.chartTheme, newTheme);
    }

    @Test
    public void testDefaultAfterSettingWidth() {
        int newWidth = 1024;
        chartBuilder.width(newWidth);
        DummyChart chart = chartBuilder.build();
        Assert.assertNotNull(chart);
        Assert.assertEquals(chartBuilder.width, newWidth);
    }

    @Test
    public void testDefaultAfterSettingHeight() {
        int newHeight = 768;
        chartBuilder.height(newHeight);
        DummyChart chart = chartBuilder.build();
        Assert.assertNotNull(chart);
        Assert.assertEquals(chartBuilder.height, newHeight);
    }

    @Test
    public void testDefaultAfterSettingTitle() {
        String newTitle = "Default Title Test";
        chartBuilder.title(newTitle);
        DummyChart chart = chartBuilder.build();
        Assert.assertNotNull(chart);
        Assert.assertEquals(chartBuilder.title, newTitle);
    }

    @Test
    public void testDefaultAfterSettingTheme() {
        ChartTheme newTheme = ChartTheme.Matlab;
        chartBuilder.theme(newTheme);
        DummyChart chart = chartBuilder.build();
        Assert.assertNotNull(chart);
        Assert.assertEquals(chartBuilder.chartTheme, newTheme);
    }

    @Test
    public void testTitleAndThemeTogether() {
        String newTitle = "Title and Theme Test";
        ChartTheme newTheme = ChartTheme.Matlab;
        chartBuilder.title(newTitle).theme(newTheme);
        DummyChart chart = chartBuilder.build();
        Assert.assertNotNull(chart);
        Assert.assertEquals(chartBuilder.title, newTitle);
        Assert.assertEquals(chartBuilder.chartTheme, newTheme);
    }

    @Test
    public void testWidthAndHeightTogether() {
        int newWidth = 1280;
        int newHeight = 720;
        chartBuilder.width(newWidth).height(newHeight);
        DummyChart chart = chartBuilder.build();
        Assert.assertNotNull(chart);
        Assert.assertEquals(chartBuilder.width, newWidth);
        Assert.assertEquals(chartBuilder.height, newHeight);
    }

    @Test
    public void testAllPropertiesTogether() {
        int newWidth = 1280;
        int newHeight = 720;
        String newTitle = "All Properties Test";
        ChartTheme newTheme = ChartTheme.Matlab;

        chartBuilder.width(newWidth)
                .height(newHeight)
                .title(newTitle)
                .theme(newTheme);

        DummyChart chart = chartBuilder.build();
        Assert.assertNotNull(chart);
        Assert.assertEquals(chartBuilder.width, newWidth);
        Assert.assertEquals(chartBuilder.height, newHeight);
        Assert.assertEquals(chartBuilder.title, newTitle);
        Assert.assertEquals(chartBuilder.chartTheme, newTheme);
    }

    @Test
    public void testNegativeWidth() {
        chartBuilder.width(-1);
        Assert.assertEquals(chartBuilder.width, -1);
    }

    @Test
    public void testNegativeHeight() {
        chartBuilder.height(-1);
        Assert.assertEquals(chartBuilder.height, -1);
    }

    @Test
    public void testEmptyTitle() {
        chartBuilder.title("");
        Assert.assertEquals(chartBuilder.title, "");
    }

    @Test
    public void testNullTitle() {
        chartBuilder.title(null);
        Assert.assertNull(chartBuilder.title);
    }

    @Test
    public void testNullTheme() {
        chartBuilder.theme(null);
        Assert.assertNull(chartBuilder.chartTheme);
    }

    @Test
    public void testTitleAfterNull() {
        chartBuilder.title(null).title("Non-null Title");
        Assert.assertEquals(chartBuilder.title, "Non-null Title");
    }

    @Test
    public void testThemeAfterNull() {
        chartBuilder.theme(null).theme(ChartTheme.GGPlot2);
        Assert.assertEquals(chartBuilder.chartTheme, ChartTheme.GGPlot2);
    }

    @Test
    public void testMultipleWidthSettings() {
        chartBuilder.width(800).width(1024).width(640);
        Assert.assertEquals(chartBuilder.width, 640);
    }

    @Test
    public void testMultipleHeightSettings() {
        chartBuilder.height(600).height(768).height(480);
        Assert.assertEquals(chartBuilder.height, 480);
    }

    @Test
    public void testMultipleTitleSettings() {
        chartBuilder.title("First Title").title("Second Title").title("Third Title");
        Assert.assertEquals(chartBuilder.title, "Third Title");
    }

    @Test
    public void testMultipleThemeSettings() {
        chartBuilder.theme(ChartTheme.XChart).theme(ChartTheme.GGPlot2).theme(ChartTheme.Matlab);
        Assert.assertEquals(chartBuilder.chartTheme, ChartTheme.Matlab);
    }

    @Test
    public void testExtremeWidth() {
        chartBuilder.width(Integer.MAX_VALUE);
        Assert.assertEquals(chartBuilder.width, Integer.MAX_VALUE);
    }

    @Test
    public void testExtremeHeight() {
        chartBuilder.height(Integer.MAX_VALUE);
        Assert.assertEquals(chartBuilder.height, Integer.MAX_VALUE);
    }

    @Test
    public void testZeroWidth() {
        chartBuilder.width(0);
        Assert.assertEquals(chartBuilder.width, 0);
    }

    @Test
    public void testZeroHeight() {
        chartBuilder.height(0);
        Assert.assertEquals(chartBuilder.height, 0);
    }

    @Test
    public void testSmallWidth() {
        chartBuilder.width(1);
        Assert.assertEquals(chartBuilder.width, 1);
    }

    @Test
    public void testSmallHeight() {
        chartBuilder.height(1);
        Assert.assertEquals(chartBuilder.height, 1);
    }

    @Test
    public void testMaxStringLengthTitle() {
        String longTitle = new String(new char[1000]).replace('\0', 'T');
        chartBuilder.title(longTitle);
        Assert.assertEquals(chartBuilder.title, longTitle);
    }

    @Test
    public void testSetTitleTwice() {
        chartBuilder.title("First Title").title("Second Title");
        Assert.assertEquals(chartBuilder.title, "Second Title");
    }

    @Test
    public void testSetThemeTwice() {
        chartBuilder.theme(ChartTheme.XChart).theme(ChartTheme.GGPlot2);
        Assert.assertEquals(chartBuilder.chartTheme, ChartTheme.GGPlot2);
    }

    @Test
    public void testTitleAndWidthTogether() {
        String newTitle = "Title and Width Test";
        int newWidth = 1024;
        chartBuilder.title(newTitle).width(newWidth);
        Assert.assertEquals(chartBuilder.title, newTitle);
        Assert.assertEquals(chartBuilder.width, newWidth);
    }

    @Test
    public void testHeightAndThemeTogether() {
        int newHeight = 768;
        ChartTheme newTheme = ChartTheme.GGPlot2;
        chartBuilder.height(newHeight).theme(newTheme);
        Assert.assertEquals(chartBuilder.height, newHeight);
        Assert.assertEquals(chartBuilder.chartTheme, newTheme);
    }

    @Test
    public void testWidthHeightTitleTogether() {
        int newWidth = 1024;
        int newHeight = 768;
        String newTitle = "Width, Height, and Title Test";
        chartBuilder.width(newWidth).height(newHeight).title(newTitle);
        Assert.assertEquals(chartBuilder.width, newWidth);
        Assert.assertEquals(chartBuilder.height, newHeight);
        Assert.assertEquals(chartBuilder.title, newTitle);
    }

    @Test
    public void testWidthHeightThemeTogether() {
        int newWidth = 1024;
        int newHeight = 768;
        ChartTheme newTheme = ChartTheme.GGPlot2;
        chartBuilder.width(newWidth).height(newHeight).theme(newTheme);
        Assert.assertEquals(chartBuilder.width, newWidth);
        Assert.assertEquals(chartBuilder.height, newHeight);
        Assert.assertEquals(chartBuilder.chartTheme, newTheme);
    }

    @Test
    public void testBuildMultipleTimes() {
        DummyChart chart1 = chartBuilder.build();
        DummyChart chart2 = chartBuilder.build();
        Assert.assertNotNull(chart1);
        Assert.assertNotNull(chart2);
        Assert.assertNotSame(chart1, chart2);
    }

    @Test
    public void testTitleWithSpecialCharacters() {
        String specialTitle = "!@#$%^&*()_+{}|:\"<>?";
        chartBuilder.title(specialTitle);
        Assert.assertEquals(chartBuilder.title, specialTitle);
    }

    @Test
    public void testTitleWithSpaces() {
        String spacedTitle = "This is a title with spaces";
        chartBuilder.title(spacedTitle);
        Assert.assertEquals(chartBuilder.title, spacedTitle);
    }


}
