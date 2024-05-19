package org.xchart.testng;

import org.knowm.xchart.internal.ChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ChartBuilderTest {

    @Test
    public void testWidth() {
        ChartBuilder<?, ?> builder = new MockChartBuilder();
        int width = 1000;
        ChartBuilder<?, ?> result = builder.width(width);
        Assert.assertEquals(result.width, width);
    }

    @Test
    public void testHeight() {
        ChartBuilder<?, ?> builder = new MockChartBuilder();
        int height = 800;
        ChartBuilder<?, ?> result = builder.height(height);
        Assert.assertEquals(result.height, height);
    }

    @Test
    public void testTitle() {
        ChartBuilder<?, ?> builder = new MockChartBuilder();
        String title = "Test Title";
        ChartBuilder<?, ?> result = builder.title(title);
        Assert.assertEquals(result.title, title);
    }

    @Test
    public void testTheme() {
        ChartBuilder<?, ?> builder = new MockChartBuilder();
        ChartTheme theme = ChartTheme.GGPlot2;
        ChartBuilder<?, ?> result = builder.theme(theme);
        Assert.assertEquals(result.chartTheme, theme);
    }

    @Test
    public void testDefaultValues() {
        MockChartBuilder builder = new MockChartBuilder();
        Assert.assertEquals(builder.width, 800);
        Assert.assertEquals(builder.height, 600);
        Assert.assertEquals(builder.title, "");
        Assert.assertEquals(builder.chartTheme, ChartTheme.XChart);
    }

    @Test
    public void testChainedMethods() {
        MockChartBuilder builder = new MockChartBuilder();
        int width = 1000;
        int height = 800;
        String title = "Test Title";
        ChartTheme theme = ChartTheme.Matlab;

        ChartBuilder<?, ?> result = builder.width(width).height(height).title(title).theme(theme);

        Assert.assertEquals(result.width, width);
        Assert.assertEquals(result.height, height);
        Assert.assertEquals(result.title, title);
        Assert.assertEquals(result.chartTheme, theme);
    }

    @Test
    public void testNegativeWidth() {
        ChartBuilder<?, ?> builder = new MockChartBuilder();
        int width = -100;
        ChartBuilder<?, ?> result = builder.width(width);
        Assert.assertEquals(result.width, width);
    }

    @Test
    public void testNegativeHeight() {
        ChartBuilder<?, ?> builder = new MockChartBuilder();
        int height = -50;
        ChartBuilder<?, ?> result = builder.height(height);
        Assert.assertEquals(result.height, height);
    }

    // Mock ChartBuilder class for testing
    private static class MockChartBuilder extends ChartBuilder<MockChartBuilder, Chart> {
        @Override
        public Chart build() {
            return null; // Not needed for testing
        }
    }
}
