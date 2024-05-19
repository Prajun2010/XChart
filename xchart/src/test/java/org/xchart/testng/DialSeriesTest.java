package org.xchart.testng;

import org.knowm.xchart.DialSeries;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DialSeriesTest {

    @Test(priority = 1)
    public void testDialSeriesConstructorWithNameAndValue() {
        DialSeries series = new DialSeries("Series1", 0.75, "Label1");
        Assert.assertEquals(series.getName(), "Series1");
        Assert.assertEquals(series.getValue(), 0.75);
        Assert.assertEquals(series.getLabel(), "Label1");
    }

    @Test(priority = 2)
    public void testDialSeriesConstructorWithNullLabel() {
        DialSeries series = new DialSeries("Series2", 0.5, null);
        Assert.assertEquals(series.getName(), "Series2");
        Assert.assertEquals(series.getValue(), 0.5);
        Assert.assertNull(series.getLabel());
    }

    @Test(priority = 3)
    public void testDialSeriesSetValue() {
        DialSeries series = new DialSeries("Series3", 0.25, "Label3");
        series.setValue(0.8);
        Assert.assertEquals(series.getValue(), 0.8);
    }

    @Test(priority = 4)
    public void testDialSeriesSetNullLabel() {
        DialSeries series = new DialSeries("Series4", 0.1, "Label4");
        series.setValue(0.3);
        series.setLabel(null);
        Assert.assertNull(series.getLabel());
    }

    @Test(priority = 5)
    public void testDialSeriesGetLegendRenderType() {
        DialSeries series = new DialSeries("Series5", 0.6, "Label5");
        Assert.assertNull(series.getLegendRenderType());
    }

    @Test(priority = 6)
    public void testDialSeriesEqualsAndHashCode() {
        DialSeries series1 = new DialSeries("Series6", 0.4, "Label6");
        DialSeries series2 = new DialSeries("Series6", 0.4, "Label6");
        DialSeries series3 = new DialSeries("Series7", 0.4, "Label6");

        Assert.assertTrue(series1.equals(series2) && series2.equals(series1));
        Assert.assertEquals(series1.hashCode(), series2.hashCode());

        Assert.assertFalse(series1.equals(series3));
        Assert.assertNotEquals(series1.hashCode(), series3.hashCode());
    }

    @Test(priority = 7, expectedExceptions = IllegalArgumentException.class)
    public void testDialSeriesNegativeValue() {
        new DialSeries("Series8", -0.1, "Label8");
    }

    @Test(priority = 8, expectedExceptions = IllegalArgumentException.class)
    public void testDialSeriesValueGreaterThanOne() {
        new DialSeries("Series9", 1.1, "Label9");
    }

    @Test(priority = 9)
    public void testDialSeriesValueUpperLimit() {
        DialSeries series = new DialSeries("Series10", 1.0, "Label10");
        Assert.assertEquals(series.getValue(), 1.0);
    }

    @Test(priority = 10)
    public void testDialSeriesValueLowerLimit() {
        DialSeries series = new DialSeries("Series11", 0.0, "Label11");
        Assert.assertEquals(series.getValue(), 0.0);
    }

}
