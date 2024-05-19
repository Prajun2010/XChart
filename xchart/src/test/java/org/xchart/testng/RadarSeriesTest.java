package org.xchart.testng;

import org.knowm.xchart.RadarSeries;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.BasicStroke;
import java.awt.Color;

public class RadarSeriesTest {

    @Test
    public void testConstructor() {
        RadarSeries series = new RadarSeries("Series1", new double[]{1.0, 2.0, 3.0}, null);
        Assert.assertEquals(series.getName(), "Series1");
        Assert.assertEquals(series.getValues(), new double[]{1.0, 2.0, 3.0});
    }

    @Test
    public void testSetValues() {
        RadarSeries series = new RadarSeries("Series1", new double[]{1.0, 2.0, 3.0}, null);
        series.setValues(new double[]{4.0, 5.0, 6.0});
        Assert.assertEquals(series.getValues(), new double[]{4.0, 5.0, 6.0});
    }

    @Test
    public void testSetLineStyle() {
        RadarSeries series = new RadarSeries("Series1", new double[]{1.0, 2.0, 3.0}, null);
        BasicStroke stroke = new BasicStroke(2.0f);
        series.setLineStyle(stroke);
        Assert.assertEquals(series.getLineStyle(), stroke);
    }

    @Test
    public void testSetLineColor() {
        RadarSeries series = new RadarSeries("Series1", new double[]{1.0, 2.0, 3.0}, null);
        series.setLineColor(Color.RED);
        Assert.assertEquals(series.getLineColor(), Color.RED);
    }

    @Test
    public void testSetLineWidth() {
        RadarSeries series = new RadarSeries("Series1", new double[]{1.0, 2.0, 3.0}, null);
        series.setLineWidth(1.5f);
        Assert.assertEquals(series.getLineWidth(), 1.5f);
    }



    @Test
    public void testSetMarkerColor() {
        RadarSeries series = new RadarSeries("Series1", new double[]{1.0, 2.0, 3.0}, null);
        series.setMarkerColor(Color.BLUE);
        Assert.assertEquals(series.getMarkerColor(), Color.BLUE);
    }

    @Test
    public void testGetLegendRenderType() {
        RadarSeries series = new RadarSeries("Series1", new double[]{1.0, 2.0, 3.0}, null);
        Assert.assertEquals(series.getLegendRenderType(), org.knowm.xchart.internal.chartpart.RenderableSeries.LegendRenderType.Line);
    }

    @Test
    public void testSetTooltipOverrides() {
        RadarSeries series = new RadarSeries("Series1", new double[]{1.0, 2.0, 3.0}, null);
        String[] tooltips = {"Tooltip1", "Tooltip2", "Tooltip3"};
        series.setTooltipOverrides(tooltips);
        Assert.assertEquals(series.getTooltipOverrides(), tooltips);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testConstructor_NullValues() {
        new RadarSeries("Series1", null, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testConstructor_NullName() {
        new RadarSeries(null, new double[]{1.0, 2.0, 3.0}, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSetLineWidth_NegativeValue() {
        RadarSeries series = new RadarSeries("Series1", new double[]{1.0, 2.0, 3.0}, null);
        series.setLineWidth(-1.0f);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSetLineStyle_NullStroke() {
        RadarSeries series = new RadarSeries("Series1", new double[]{1.0, 2.0, 3.0}, null);
        series.setLineStyle(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSetMarker_NullMarker() {
        RadarSeries series = new RadarSeries("Series1", new double[]{1.0, 2.0, 3.0}, null);
        series.setMarker(null);
    }
}
