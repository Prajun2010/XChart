package org.xchart.testng;

import org.knowm.xchart.PieSeries;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PieSeriesTest {

    @Test
    public void testConstructor_NameAndValue() {
        PieSeries series = new PieSeries("Series1", 10);
        Assert.assertEquals(series.getName(), "Series1");
        Assert.assertEquals(series.getValue(), 10);
    }

    @Test
    public void testConstructor_NullName() {
        PieSeries series = new PieSeries(null, 10);
        Assert.assertNull(series.getName());
        Assert.assertEquals(series.getValue(), 10);
    }

    @Test
    public void testConstructor_ZeroValue() {
        PieSeries series = new PieSeries("Series1", 0);
        Assert.assertEquals(series.getName(), "Series1");
        Assert.assertEquals(series.getValue(), 0);
    }

    @Test
    public void testConstructor_NegativeValue() {
        PieSeries series = new PieSeries("Series1", -5);
        Assert.assertEquals(series.getName(), "Series1");
        Assert.assertEquals(series.getValue(), -5);
    }

    @Test
    public void testReplaceData() {
        PieSeries series = new PieSeries("Series1", 10);
        series.replaceData(20);
        Assert.assertEquals(series.getValue(), 20);
    }

    @Test
    public void testReplaceData_ZeroValue() {
        PieSeries series = new PieSeries("Series1", 10);
        series.replaceData(0);
        Assert.assertEquals(series.getValue(), 0);
    }

    @Test
    public void testReplaceData_NegativeValue() {
        PieSeries series = new PieSeries("Series1", 10);
        series.replaceData(-5);
        Assert.assertEquals(series.getValue(), -5);
    }

    @Test
    public void testGetChartPieSeriesRenderStyle_Default() {
        PieSeries series = new PieSeries("Series1", 10);
        Assert.assertNull(series.getChartPieSeriesRenderStyle());
    }

    @Test
    public void testSetChartPieSeriesRenderStyle() {
        PieSeries series = new PieSeries("Series1", 10);
        series.setChartPieSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);
        Assert.assertEquals(series.getChartPieSeriesRenderStyle(), PieSeries.PieSeriesRenderStyle.Donut);
    }

    @Test
    public void testSetValue() {
        PieSeries series = new PieSeries("Series1", 10);
        series.setValue(15);
        Assert.assertEquals(series.getValue(), 15);
    }



    @Test
    public void testSetValue_Negative() {
        PieSeries series = new PieSeries("Series1", 10);
        series.setValue(-5);
        Assert.assertEquals(series.getValue(), -5);
    }

    @Test
    public void testGetLegendRenderType() {
        PieSeries series = new PieSeries("Series1", 10);
        Assert.assertNull(series.getLegendRenderType());
    }



    @Test
    public void testGetLegendRenderType_NullRenderStyle() {
        PieSeries series = new PieSeries("Series1", 10);
        series.setChartPieSeriesRenderStyle(null);
        Assert.assertNull(series.getLegendRenderType());
    }



    @Test
    public void testGetLegendRenderType_PieStyleAndNameNull() {
        PieSeries series = new PieSeries(null, 10);
        series.setChartPieSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Pie);
        Assert.assertNull(series.getLegendRenderType());
    }

    @Test
    public void testGetLegendRenderType_DonutStyleAndNameNull() {
        PieSeries series = new PieSeries(null, 10);
        series.setChartPieSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);
        Assert.assertNull(series.getLegendRenderType());
    }

    @Test
    public void testGetLegendRenderType_NameNull() {
        PieSeries series = new PieSeries(null, 10);
        Assert.assertNull(series.getLegendRenderType());
    }





    @Test
    public void testGetLegendRenderType_DonutStyleAndNegativeValue() {
        PieSeries series = new PieSeries("Series1", -5);
        series.setChartPieSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);
        Assert.assertNull(series.getLegendRenderType());
    }

    @Test
    public void testGetLegendRenderType_PieStyleAndNegativeValue() {
        PieSeries series = new PieSeries("Series1", -5);
        series.setChartPieSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Pie);
        Assert.assertNull(series.getLegendRenderType());
    }



    @Test
    public void testGetLegendRenderType_DonutStyleAndZeroValue() {
        PieSeries series = new PieSeries("Series1", 0);
        series.setChartPieSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);
        Assert.assertNull(series.getLegendRenderType());
    }

    @Test
    public void testGetLegendRenderType_PieStyleAndZeroValue() {
        PieSeries series = new PieSeries("Series1", 0);
        series.setChartPieSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Pie);
        Assert.assertNull(series.getLegendRenderType());
    }


    @Test
    public void testGetChartPieSeriesRenderStyle_DonutStyle() {
        PieSeries series = new PieSeries("Series1", 10);
        series.setChartPieSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);
        Assert.assertEquals(series.getChartPieSeriesRenderStyle(), PieSeries.PieSeriesRenderStyle.Donut);
    }

    @Test
    public void testGetChartPieSeriesRenderStyle_NullStyle() {
        PieSeries series = new PieSeries("Series1", 10);
        Assert.assertNull(series.getChartPieSeriesRenderStyle());
    }

    @Test
    public void testGetChartPieSeriesRenderStyle_PieStyle() {
        PieSeries series = new PieSeries("Series1", 10);
        series.setChartPieSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Pie);
        Assert.assertEquals(series.getChartPieSeriesRenderStyle(), PieSeries.PieSeriesRenderStyle.Pie);
    }

    @Test
    public void testGetValue_PositiveInteger() {
        PieSeries series = new PieSeries("Series1", 10);
        Assert.assertEquals(series.getValue(), 10);
    }

    @Test
    public void testGetValue_NegativeInteger() {
        PieSeries series = new PieSeries("Series1", -5);
        Assert.assertEquals(series.getValue(), -5);
    }

    @Test
    public void testGetValue_Zero() {
        PieSeries series = new PieSeries("Series1", 0);
        Assert.assertEquals(series.getValue(), 0);
    }

    @Test
    public void testSetValue_PositiveInteger() {
        PieSeries series = new PieSeries("Series1", 10);
        series.setValue(15);
        Assert.assertEquals(series.getValue(), 15);
    }

    @Test
    public void testSetValue_NegativeInteger() {
        PieSeries series = new PieSeries("Series1", 10);
        series.setValue(-5);
        Assert.assertEquals(series.getValue(), -5);
    }

    @Test
    public void testSetValue_Zero() {
        PieSeries series = new PieSeries("Series1", 10);
        series.setValue(0);
        Assert.assertEquals(series.getValue(), 0);
    }

    @Test
    public void testSetChartPieSeriesRenderStyle_Null() {
        PieSeries series = new PieSeries("Series1", 10);
        series.setChartPieSeriesRenderStyle(null);
        Assert.assertNull(series.getChartPieSeriesRenderStyle());
    }

    @Test
    public void testSetChartPieSeriesRenderStyle_Pie() {
        PieSeries series = new PieSeries("Series1", 10);
        series.setChartPieSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Pie);
        Assert.assertEquals(series.getChartPieSeriesRenderStyle(), PieSeries.PieSeriesRenderStyle.Pie);
    }

    @Test
    public void testSetChartPieSeriesRenderStyle_Donut() {
        PieSeries series = new PieSeries("Series1", 10);
        series.setChartPieSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);
        Assert.assertEquals(series.getChartPieSeriesRenderStyle(), PieSeries.PieSeriesRenderStyle.Donut);
    }

    @Test
    public void testReplaceData_PositiveInteger() {
        PieSeries series = new PieSeries("Series1", 10);
        series.replaceData(15);
        Assert.assertEquals(series.getValue(), 15);
    }

    @Test
    public void testReplaceData_NegativeInteger() {
        PieSeries series = new PieSeries("Series1", 10);
        series.replaceData(-5);
        Assert.assertEquals(series.getValue(), -5);
    }

    @Test
    public void testReplaceData_Zero() {
        PieSeries series = new PieSeries("Series1", 10);
        series.replaceData(0);
        Assert.assertEquals(series.getValue(), 0);
    }

    @Test
    public void testReplaceData_Null() {
        PieSeries series = new PieSeries("Series1", 10);
        series.replaceData(null);
        Assert.assertNull(series.getValue());
    }

    @Test
    public void testGetLegendRenderType_Null() {
        PieSeries series = new PieSeries("Series1", 10);
        Assert.assertNull(series.getLegendRenderType());
    }

    @Test
    public void testGetLegendRenderType_PieStyle() {
        PieSeries series = new PieSeries("Series1", 10);
        series.setChartPieSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Pie);
        Assert.assertNull(series.getLegendRenderType());
    }

    @Test
    public void testGetLegendRenderType_DonutStyle() {
        PieSeries series = new PieSeries("Series1", 10);
        series.setChartPieSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);
        Assert.assertNull(series.getLegendRenderType());
    }

    @Test
    public void testGetLegendRenderType_EmptyName() {
        PieSeries series = new PieSeries("", 10);
        Assert.assertNull(series.getLegendRenderType());
    }

    @Test
    public void testGetLegendRenderType_NullName() {
        PieSeries series = new PieSeries(null, 10);
        Assert.assertNull(series.getLegendRenderType());
    }

    @Test
    public void testGetLegendRenderType_ZeroValue() {
        PieSeries series = new PieSeries("Series1", 0);
        Assert.assertNull(series.getLegendRenderType());
    }

    @Test
    public void testGetLegendRenderType_NegativeValue() {
        PieSeries series = new PieSeries("Series1", -5);
        Assert.assertNull(series.getLegendRenderType());
    }

    @Test
    public void testGetLegendRenderType_NullNameAndValue() {
        PieSeries series = new PieSeries(null, null);
        Assert.assertNull(series.getLegendRenderType());
    }

    @Test
    public void testGetLegendRenderType_EmptyNameAndValue() {
        PieSeries series = new PieSeries("", null);
        Assert.assertNull(series.getLegendRenderType());
    }

    @Test
    public void testGetLegendRenderType_NullNameAndValueAndStyle() {
        PieSeries series = new PieSeries(null, null);
        series.setChartPieSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);
        Assert.assertNull(series.getLegendRenderType());
    }

    @Test
    public void testGetLegendRenderType_EmptyNameAndValueAndStyle() {
        PieSeries series = new PieSeries("", null);
        series.setChartPieSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);
        Assert.assertNull(series.getLegendRenderType());
    }

    @Test
    public void testGetLegendRenderType_ZeroValueAndStyle() {
        PieSeries series = new PieSeries("Series1", 0);
        series.setChartPieSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);
        Assert.assertNull(series.getLegendRenderType());
    }

    @Test
    public void testGetLegendRenderType_NegativeValueAndStyle() {
        PieSeries series = new PieSeries("Series1", -5);
        series.setChartPieSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);
        Assert.assertNull(series.getLegendRenderType());
    }

    @Test
    public void testGetLegendRenderType_DonutStyleAndEmptyName() {
        PieSeries series = new PieSeries("", 10);
        series.setChartPieSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);
        Assert.assertNull(series.getLegendRenderType());
    }

    @Test
    public void testGetLegendRenderType_PieStyleAndEmptyName() {
        PieSeries series = new PieSeries("", 10);
        series.setChartPieSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Pie);
        Assert.assertNull(series.getLegendRenderType());
    }

    @Test
    public void testGetLegendRenderType_DonutStyleAndNullName() {
        PieSeries series = new PieSeries(null, 10);
        series.setChartPieSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);
        Assert.assertNull(series.getLegendRenderType());
    }

    @Test
    public void testGetLegendRenderType_PieStyleAndNullName() {
        PieSeries series = new PieSeries(null, 10);
        series.setChartPieSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Pie);
        Assert.assertNull(series.getLegendRenderType());
    }
}

