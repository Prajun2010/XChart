package org.xchart.testng;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.PieSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.theme.Theme;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;
import java.text.DecimalFormat;

public class PieChartBuilderTest {

    @Test
    public void testConstructor_Default() {
        PieChartBuilder builder = new PieChartBuilder();
        Assert.assertNotNull(builder);
    }

    @Test
    public void testBuild_NotNull() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertNotNull(chart);
    }

    @Test
    public void testBuild_DefaultWidth() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertEquals(chart.getWidth(), 800);
    }

    @Test
    public void testBuild_DefaultHeight() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertEquals(chart.getHeight(), 600);
    }

    @Test
    public void testBuild_CustomWidth() {
        PieChartBuilder builder = new PieChartBuilder().width(1000);
        PieChart chart = builder.build();
        Assert.assertEquals(chart.getWidth(), 1000);
    }

    @Test
    public void testBuild_CustomHeight() {
        PieChartBuilder builder = new PieChartBuilder().height(800);
        PieChart chart = builder.build();
        Assert.assertEquals(chart.getHeight(), 800);
    }

    @Test
    public void testBuild_DefaultTitle() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertTrue(chart.getTitle().isEmpty());
    }

    @Test
    public void testBuild_CustomTitle() {
        PieChartBuilder builder = new PieChartBuilder().title("Test Chart");
        PieChart chart = builder.build();
        Assert.assertEquals(chart.getTitle(), "Test Chart");
    }

    @Test
    public void testBuild_DefaultThemeNotNull() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertNotNull(chart.getStyler().getTheme());
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBuild_InvalidWidth() {
        PieChartBuilder builder = new PieChartBuilder().width(-100); // Should throw an exception
        builder.build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBuild_InvalidHeight() {
        PieChartBuilder builder = new PieChartBuilder().height(-100); // Should throw an exception
        builder.build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBuild_NullTitle() {
        PieChartBuilder builder = new PieChartBuilder().title(null); // Should throw an exception
        builder.build();
    }



    @Test
    public void testBuild_DefaultSeriesRenderStyle() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertEquals(chart.getStyler().getDefaultSeriesRenderStyle(), PieSeries.PieSeriesRenderStyle.Pie);
    }




    @Test
    public void testBuild_DefaultToolTipsAlwaysVisible() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertFalse(chart.getStyler().isToolTipsAlwaysVisible());
    }


    @Test
    public void testBuild_DefaultDonutThickness() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertEquals(chart.getStyler().getDonutThickness(), 0.2);
    }


    @Test
    public void testBuild_DefaultToolTipsFormat() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertNull(chart.getStyler().getToolTipType());
    }


    @Test
    public void testBuild_DefaultCircular() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertTrue(chart.getStyler().isCircular());
    }


    @Test
    public void testBuild_DefaultSumVisible() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertFalse(chart.getStyler().isSumVisible());
    }



    @Test
    public void testBuild_DefaultToolTipsBorderColor() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertEquals(chart.getStyler().getToolTipBorderColor(), Color.BLACK);
    }


    @Test
    public void testBuild_DefaultToolTipsBackgroundColor() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertEquals(chart.getStyler().getToolTipBackgroundColor(), Color.WHITE);
    }



    @Test
    public void testBuild_DefaultToolTipsFont() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertEquals(chart.getStyler().getToolTipFont(), new Font("Arial", Font.PLAIN, 12));
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBuild_NullTheme() {
        PieChartBuilder builder = new PieChartBuilder().theme(null); // Should throw an exception
        builder.build();
    }


    @Test
    public void testBuild_DefaultLabelsFont() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertEquals(chart.getStyler().getLabelsFont(), new Font("Arial", Font.PLAIN, 10));
    }


    @Test
    public void testBuild_DefaultLabelsVisible() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertTrue(chart.getStyler().isLabelsVisible());
    }

    @Test
    public void testBuild_DefaultLegendBorderColor() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertEquals(chart.getStyler().getLegendBorderColor(), Color.GRAY);
    }


    @Test
    public void testBuild_DefaultLegendBackgroundColor() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertEquals(chart.getStyler().getLegendBackgroundColor(), new Color(230, 230, 230));
    }


    @Test
    public void testBuild_DefaultLegendFont() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertEquals(chart.getStyler().getLegendFont(), new Font("Arial", Font.PLAIN, 10));
    }



    @Test
    public void testBuild_DefaultLegendPosition() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertEquals(chart.getStyler().getLegendPosition(), Styler.LegendPosition.InsideNW);
    }



    @Test
    public void testBuild_DefaultLegendVisible() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertTrue(chart.getStyler().isLegendVisible());
    }



    @Test
    public void testBuild_DefaultToolTipsEnabled() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertTrue(chart.getStyler().isToolTipsEnabled());
    }


    @Test
    public void testBuild_DefaultPlotContentSize() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertEquals(chart.getStyler().getPlotContentSize(), 1.0);
    }



    @Test
    public void testBuild_DefaultPlotBorderColor() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertEquals(chart.getStyler().getPlotBorderColor(), Color.BLACK);
    }


    @Test
    public void testBuild_DefaultPlotBackgroundColor() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertEquals(chart.getStyler().getPlotBackgroundColor(), Color.WHITE);
    }


    @Test
    public void testBuild_DefaultPlotBorderVisible() {
        PieChartBuilder builder = new PieChartBuilder();
        PieChart chart = builder.build();
        Assert.assertTrue(chart.getStyler().isPlotBorderVisible());
    }
}





