package org.xchart.testng;

import org.knowm.xchart.RadarChart;
import org.knowm.xchart.RadarChartBuilder;
import org.knowm.xchart.style.Styler;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RadarChartBuilderTest {

    @Test
    public void testBuilder_DefaultConstructor() {
        RadarChartBuilder builder = new RadarChartBuilder();
        RadarChart radarChart = builder.build();
        Assert.assertNotNull(radarChart);
    }

    @Test
    public void testBuilder_SetWidthAndHeight() {
        RadarChartBuilder builder = new RadarChartBuilder().width(800).height(600);
        RadarChart radarChart = builder.build();
        Assert.assertEquals(radarChart.getWidth(), 800);
        Assert.assertEquals(radarChart.getHeight(), 600);
    }

    @Test
    public void testBuilder_SetTitle() {
        RadarChartBuilder builder = new RadarChartBuilder().title("Test Radar Chart");
        RadarChart radarChart = builder.build();
        Assert.assertEquals(radarChart.getTitle(), "Test Radar Chart");
    }

    @Test
    public void testBuilder_SetTheme() {
        RadarChartBuilder builder = new RadarChartBuilder().theme(Styler.ChartTheme.XChart);
        RadarChart radarChart = builder.build();
        Assert.assertEquals(radarChart.getStyler().getTheme(), Styler.ChartTheme.XChart);
    }

}
