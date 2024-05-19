package org.xchart.testng;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CategoryChartBuilderTest {

    @Test
    public void testDefaultBuilder() {
        CategoryChartBuilder builder = new CategoryChartBuilder();
        CategoryChart chart = builder.build();
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getXAxisTitle(), "");
        Assert.assertEquals(chart.getYAxisTitle(), "");
    }

    @Test
    public void testCustomXAxisTitle() {
        CategoryChartBuilder builder = new CategoryChartBuilder().xAxisTitle("X Axis");
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), "X Axis");
    }

    @Test
    public void testCustomYAxisTitle() {
        CategoryChartBuilder builder = new CategoryChartBuilder().yAxisTitle("Y Axis");
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getYAxisTitle(), "Y Axis");
    }

    @Test
    public void testCustomBothAxisTitles() {
        CategoryChartBuilder builder = new CategoryChartBuilder().xAxisTitle("X Axis").yAxisTitle("Y Axis");
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), "X Axis");
        Assert.assertEquals(chart.getYAxisTitle(), "Y Axis");
    }

    @Test
    public void testEmptyXAxisTitle() {
        CategoryChartBuilder builder = new CategoryChartBuilder().xAxisTitle("");
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), "");
    }

    @Test
    public void testEmptyYAxisTitle() {
        CategoryChartBuilder builder = new CategoryChartBuilder().yAxisTitle("");
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getYAxisTitle(), "");
    }

    @Test
    public void testNullXAxisTitle() {
        CategoryChartBuilder builder = new CategoryChartBuilder().xAxisTitle(null);
        CategoryChart chart = builder.build();
        Assert.assertNull(chart.getXAxisTitle());
    }

    @Test
    public void testNullYAxisTitle() {
        CategoryChartBuilder builder = new CategoryChartBuilder().yAxisTitle(null);
        CategoryChart chart = builder.build();
        Assert.assertNull(chart.getYAxisTitle());
    }

    @DataProvider(name = "titlesProvider")
    public Object[][] titlesProvider() {
        return new Object[][]{
                {"X1", "Y1"},
                {"X2", "Y2"},
                {"X3", "Y3"},
                {"X4", "Y4"},
                {"X5", "Y5"},
                {"X6", "Y6"},
                {"X7", "Y7"},
                {"X8", "Y8"},
                {"X9", "Y9"},
                {"X10", "Y10"},
        };
    }

    @Test(dataProvider = "titlesProvider")
    public void testMultipleTitles(String xAxisTitle, String yAxisTitle) {
        CategoryChartBuilder builder = new CategoryChartBuilder().xAxisTitle(xAxisTitle).yAxisTitle(yAxisTitle);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), xAxisTitle);
        Assert.assertEquals(chart.getYAxisTitle(), yAxisTitle);
    }

    @Test
    public void testLongXAxisTitle() {
        String longTitle = "This is a very long title for the X axis that should be handled correctly by the chart";
        CategoryChartBuilder builder = new CategoryChartBuilder().xAxisTitle(longTitle);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), longTitle);
    }

    @Test
    public void testLongYAxisTitle() {
        String longTitle = "This is a very long title for the Y axis that should be handled correctly by the chart";
        CategoryChartBuilder builder = new CategoryChartBuilder().yAxisTitle(longTitle);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getYAxisTitle(), longTitle);
    }

    @Test
    public void testSpecialCharactersXAxisTitle() {
        String specialTitle = "X Axis !@#$%^&*()";
        CategoryChartBuilder builder = new CategoryChartBuilder().xAxisTitle(specialTitle);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), specialTitle);
    }

    @Test
    public void testSpecialCharactersYAxisTitle() {
        String specialTitle = "Y Axis !@#$%^&*()";
        CategoryChartBuilder builder = new CategoryChartBuilder().yAxisTitle(specialTitle);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getYAxisTitle(), specialTitle);
    }

    @Test
    public void testNumericXAxisTitle() {
        String numericTitle = "1234567890";
        CategoryChartBuilder builder = new CategoryChartBuilder().xAxisTitle(numericTitle);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), numericTitle);
    }

    @Test
    public void testNumericYAxisTitle() {
        String numericTitle = "0987654321";
        CategoryChartBuilder builder = new CategoryChartBuilder().yAxisTitle(numericTitle);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getYAxisTitle(), numericTitle);
    }

    @DataProvider(name = "emptyTitlesProvider")
    public Object[][] emptyTitlesProvider() {
        return new Object[][]{
                {""},
                {null}
        };
    }

    @Test(dataProvider = "emptyTitlesProvider")
    public void testEmptyOrNullXAxisTitles(String xAxisTitle) {
        CategoryChartBuilder builder = new CategoryChartBuilder().xAxisTitle(xAxisTitle);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), xAxisTitle);
    }

    @Test(dataProvider = "emptyTitlesProvider")
    public void testEmptyOrNullYAxisTitles(String yAxisTitle) {
        CategoryChartBuilder builder = new CategoryChartBuilder().yAxisTitle(yAxisTitle);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getYAxisTitle(), yAxisTitle);
    }

    @Test
    public void testVeryLongXAxisTitle() {
        String veryLongTitle = "X".repeat(1000);
        CategoryChartBuilder builder = new CategoryChartBuilder().xAxisTitle(veryLongTitle);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), veryLongTitle);
    }

    @Test
    public void testVeryLongYAxisTitle() {
        String veryLongTitle = "Y".repeat(1000);
        CategoryChartBuilder builder = new CategoryChartBuilder().yAxisTitle(veryLongTitle);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getYAxisTitle(), veryLongTitle);
    }

    @Test
    public void testChangeXAxisTitleAfterBuild() {
        CategoryChartBuilder builder = new CategoryChartBuilder().xAxisTitle("Initial X");
        CategoryChart chart = builder.build();
        chart.setXAxisTitle("Updated X");
        Assert.assertEquals(chart.getXAxisTitle(), "Updated X");
    }

    @Test
    public void testChangeYAxisTitleAfterBuild() {
        CategoryChartBuilder builder = new CategoryChartBuilder().yAxisTitle("Initial Y");
        CategoryChart chart = builder.build();
        chart.setYAxisTitle("Updated Y");
        Assert.assertEquals(chart.getYAxisTitle(), "Updated Y");
    }

    @Test
    public void testXAxisTitleWithLeadingSpaces() {
        String title = "   Leading spaces";
        CategoryChartBuilder builder = new CategoryChartBuilder().xAxisTitle(title);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), title);
    }

    @Test
    public void testYAxisTitleWithLeadingSpaces() {
        String title = "   Leading spaces";
        CategoryChartBuilder builder = new CategoryChartBuilder().yAxisTitle(title);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getYAxisTitle(), title);
    }

    @Test
    public void testXAxisTitleWithTrailingSpaces() {
        String title = "Trailing spaces   ";
        CategoryChartBuilder builder = new CategoryChartBuilder().xAxisTitle(title);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), title);
    }

    @Test
    public void testYAxisTitleWithTrailingSpaces() {
        String title = "Trailing spaces   ";
        CategoryChartBuilder builder = new CategoryChartBuilder().yAxisTitle(title);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getYAxisTitle(), title);
    }

    @Test
    public void testXAxisTitleWithSpaces() {
        String title = "   Spaces   ";
        CategoryChartBuilder builder = new CategoryChartBuilder().xAxisTitle(title);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), title);
    }

    @Test
    public void testYAxisTitleWithSpaces() {
        String title = "   Spaces   ";
        CategoryChartBuilder builder = new CategoryChartBuilder().yAxisTitle(title);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getYAxisTitle(), title);
    }

    @Test
    public void testXAxisTitleWithUnicodeCharacters() {
        String title = "X Axis with Unicode: \u2603";
        CategoryChartBuilder builder = new CategoryChartBuilder().xAxisTitle(title);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), title);
    }

    @Test
    public void testYAxisTitleWithUnicodeCharacters() {
        String title = "Y Axis with Unicode: \u2603";
        CategoryChartBuilder builder = new CategoryChartBuilder().yAxisTitle(title);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getYAxisTitle(), title);
    }

    @Test
    public void testXAxisTitleWithNewLine() {
        String title = "Line1\nLine2";
        CategoryChartBuilder builder = new CategoryChartBuilder().xAxisTitle(title);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), title);
    }

    @Test
    public void testYAxisTitleWithNewLine() {
        String title = "Line1\nLine2";
        CategoryChartBuilder builder = new CategoryChartBuilder().yAxisTitle(title);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getYAxisTitle(), title);
    }

    @Test
    public void testXAxisTitleWithTab() {
        String title = "X Axis\tTitle";
        CategoryChartBuilder builder = new CategoryChartBuilder().xAxisTitle(title);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), title);
    }

    @Test
    public void testYAxisTitleWithTab() {
        String title = "Y Axis\tTitle";
        CategoryChartBuilder builder = new CategoryChartBuilder().yAxisTitle(title);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getYAxisTitle(), title);
    }

    @Test
    public void testEmptyBuilder() {
        CategoryChartBuilder builder = new CategoryChartBuilder();
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), "");
        Assert.assertEquals(chart.getYAxisTitle(), "");
    }

    @Test
    public void testBuilderWithNoXAxisTitle() {
        CategoryChartBuilder builder = new CategoryChartBuilder().yAxisTitle("Only Y Axis");
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), "");
        Assert.assertEquals(chart.getYAxisTitle(), "Only Y Axis");
    }

    @Test
    public void testBuilderWithNoYAxisTitle() {
        CategoryChartBuilder builder = new CategoryChartBuilder().xAxisTitle("Only X Axis");
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), "Only X Axis");
        Assert.assertEquals(chart.getYAxisTitle(), "");
    }

    @Test
    public void testBuilderWithSpecialCharactersInTitles() {
        CategoryChartBuilder builder = new CategoryChartBuilder().xAxisTitle("X!@#$%^&*()").yAxisTitle("Y!@#$%^&*()");
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), "X!@#$%^&*()");
        Assert.assertEquals(chart.getYAxisTitle(), "Y!@#$%^&*()");
    }

    @Test
    public void testXAxisTitleWithQuotes() {
        String title = "X Axis \"Title\"";
        CategoryChartBuilder builder = new CategoryChartBuilder().xAxisTitle(title);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), title);
    }

    @Test
    public void testYAxisTitleWithQuotes() {
        String title = "Y Axis \"Title\"";
        CategoryChartBuilder builder = new CategoryChartBuilder().yAxisTitle(title);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getYAxisTitle(), title);
    }

    @Test
    public void testXAxisTitleWithApostrophe() {
        String title = "X Axis's Title";
        CategoryChartBuilder builder = new CategoryChartBuilder().xAxisTitle(title);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), title);
    }

    @Test
    public void testYAxisTitleWithApostrophe() {
        String title = "Y Axis's Title";
        CategoryChartBuilder builder = new CategoryChartBuilder().yAxisTitle(title);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getYAxisTitle(), title);
    }

    @Test
    public void testXAxisTitleWithMixedCharacters() {
        String title = "X Axis 123 !@# ABC";
        CategoryChartBuilder builder = new CategoryChartBuilder().xAxisTitle(title);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getXAxisTitle(), title);
    }

    @Test
    public void testYAxisTitleWithMixedCharacters() {
        String title = "Y Axis 123 !@# ABC";
        CategoryChartBuilder builder = new CategoryChartBuilder().yAxisTitle(title);
        CategoryChart chart = builder.build();
        Assert.assertEquals(chart.getYAxisTitle(), title);
    }
}
