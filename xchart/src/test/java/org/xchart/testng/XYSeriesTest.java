package org.xchart.testng;

import org.knowm.xchart.XYSeries;
import org.knowm.xchart.internal.chartpart.RenderableSeries;
import org.testng.Assert;
import org.testng.annotations.*;

public class XYSeriesTest {



    @Test(groups = {"constructor"})
    public void testConstructorWithNameAndData() {
        String name = "Test Series";
        double[] xData = {1, 2, 3};
        double[] yData = {4, 5, 6};
        double[] errorBars = {0.1, 0.2, 0.3};
        XYSeries.DataType axisType = XYSeries.DataType.Number;

        XYSeries series = new XYSeries(name, xData, yData, errorBars, axisType);
        Assert.assertEquals(series.getName(), name);
        Assert.assertEquals(series.getXData(), xData);
        Assert.assertEquals(series.getYData(), yData);
    }

    @Test(groups = {"constructor"})
    public void testConstructorWithNameAndData_NoErrorBars() {
        String name = "Test Series";
        double[] xData = {1, 2, 3};
        double[] yData = {4, 5, 6};
        XYSeries.DataType axisType = XYSeries.DataType.Number;

        XYSeries series = new XYSeries(name, xData, yData, null, axisType);
        Assert.assertEquals(series.getName(), name);
        Assert.assertEquals(series.getXData(), xData);
        Assert.assertEquals(series.getYData(), yData);
    }

    @Test(groups = {"methods"})
    public void testSetXYSeriesRenderStyle() {
        XYSeries series = new XYSeries("Test Series", new double[]{}, new double[]{}, null, XYSeries.DataType.Number);
        series.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        Assert.assertEquals(series.getXYSeriesRenderStyle(), XYSeries.XYSeriesRenderStyle.Line);
    }

    @Test(groups = {"methods"})
    public void testSetSmooth() {
        XYSeries series = new XYSeries("Test Series", new double[]{}, new double[]{}, null, XYSeries.DataType.Number);
        series.setSmooth(true);
        Assert.assertTrue(series.isSmooth());
    }

    @Test(groups = {"methods"})
    public void testGetLegendRenderType_Line() {
        Assert.assertEquals(XYSeries.XYSeriesRenderStyle.Line.getLegendRenderType(), RenderableSeries.LegendRenderType.Line);
    }

    @Test(groups = {"methods"})
    public void testGetLegendRenderType_Area() {
        Assert.assertEquals(XYSeries.XYSeriesRenderStyle.Area.getLegendRenderType(), RenderableSeries.LegendRenderType.Line);
    }

    @Test(groups = {"methods"})
    public void testGetLegendRenderType_Step() {
        Assert.assertEquals(XYSeries.XYSeriesRenderStyle.Step.getLegendRenderType(), RenderableSeries.LegendRenderType.Line);
    }

    @Test(groups = {"methods"})
    public void testGetLegendRenderType_StepArea() {
        Assert.assertEquals(XYSeries.XYSeriesRenderStyle.StepArea.getLegendRenderType(), RenderableSeries.LegendRenderType.Line);
    }

    @Test(groups = {"methods"})
    public void testGetLegendRenderType_PolygonArea() {
        Assert.assertEquals(XYSeries.XYSeriesRenderStyle.PolygonArea.getLegendRenderType(), RenderableSeries.LegendRenderType.Box);
    }

    @Test(groups = {"methods"})
    public void testGetLegendRenderType_Scatter() {
        Assert.assertEquals(XYSeries.XYSeriesRenderStyle.Scatter.getLegendRenderType(), RenderableSeries.LegendRenderType.Scatter);
    }

    @Test(groups = {"methods"})
    public void testXYSeriesRenderStyleEnumValuesCount() {
        Assert.assertEquals(XYSeries.XYSeriesRenderStyle.values().length, 6);
    }

    @Test(groups = {"methods"})
    public void testXYSeriesRenderStyleEnumValues() {
        XYSeries.XYSeriesRenderStyle[] styles = XYSeries.XYSeriesRenderStyle.values();
        Assert.assertEquals(styles[0], XYSeries.XYSeriesRenderStyle.Line);
        Assert.assertEquals(styles[1], XYSeries.XYSeriesRenderStyle.Area);
        Assert.assertEquals(styles[2], XYSeries.XYSeriesRenderStyle.Step);
        Assert.assertEquals(styles[3], XYSeries.XYSeriesRenderStyle.StepArea);
        Assert.assertEquals(styles[4], XYSeries.XYSeriesRenderStyle.PolygonArea);
        Assert.assertEquals(styles[5], XYSeries.XYSeriesRenderStyle.Scatter);
    }

    @Test(groups = {"methods"})
    public void testXYSeriesRenderStyleArea() {
        XYSeries series = new XYSeries("Test Series", new double[]{}, new double[]{}, null, XYSeries.DataType.Number);
        series.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);
        Assert.assertEquals(series.getXYSeriesRenderStyle(), XYSeries.XYSeriesRenderStyle.Area);
    }

    @Test(groups = {"methods"})
    public void testXYSeriesRenderStyleStep() {
        XYSeries series = new XYSeries("Test Series", new double[]{}, new double[]{}, null, XYSeries.DataType.Number);
        series.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Step);
        Assert.assertEquals(series.getXYSeriesRenderStyle(), XYSeries.XYSeriesRenderStyle.Step);
    }

    @Test(groups = {"methods"})
    public void testXYSeriesRenderStyleStepArea() {
        XYSeries series = new XYSeries("Test Series", new double[]{}, new double[]{}, null, XYSeries.DataType.Number);
        series.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.StepArea);
        Assert.assertEquals(series.getXYSeriesRenderStyle(), XYSeries.XYSeriesRenderStyle.StepArea);
    }

    @Test(groups = {"methods"})
    public void testXYSeriesRenderStylePolygonArea() {
        XYSeries series = new XYSeries("Test Series", new double[]{}, new double[]{}, null, XYSeries.DataType.Number);
        series.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.PolygonArea);
        Assert.assertEquals(series.getXYSeriesRenderStyle(), XYSeries.XYSeriesRenderStyle.PolygonArea);
    }

    @Test(groups = {"methods"})
    public void testXYSeriesRenderStyleScatter() {
        XYSeries series = new XYSeries("Test Series", new double[]{}, new double[]{}, null, XYSeries.DataType.Number);
        series.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        Assert.assertEquals(series.getXYSeriesRenderStyle(), XYSeries.XYSeriesRenderStyle.Scatter);
    }

    @Test(groups = {"methods"})
    public void testGetSmooth() {
        XYSeries series = new XYSeries("Test Series", new double[]{}, new double[]{}, null, XYSeries.DataType.Number);
        series.setSmooth(true);
        Assert.assertTrue(series.isSmooth());
    }

    @Test(groups = {"methods"})
    public void testGetXYSeriesRenderStyle_NoSet() {
        XYSeries series = new XYSeries("Test Series", new double[]{}, new double[]{}, null, XYSeries.DataType.Number);
        Assert.assertNull(series.getXYSeriesRenderStyle());
    }

    @Test(groups = {"methods"})
    public void testSetSmooth_False() {
        XYSeries series = new XYSeries("Test Series", new double[]{}, new double[]{}, null, XYSeries.DataType.Number);
        series.setSmooth(false);
        Assert.assertFalse(series.isSmooth());
    }

    @Test(groups = {"methods"})
    public void testSetXYSeriesRenderStyle_Null() {
        XYSeries series = new XYSeries("Test Series", new double[]{}, new double[]{}, null, XYSeries.DataType.Number);
        series.setXYSeriesRenderStyle(null);
        Assert.assertNull(series.getXYSeriesRenderStyle());
    }

    @Test(groups = {"methods"})
    public void testXYSeriesRenderStyleLine() {
        // Creating XYSeries object and setting render style to Line
        XYSeries series = new XYSeries("Test Series", new double[]{}, new double[]{}, null, XYSeries.DataType.Number);
        series.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Asserting that the render style is Line
        Assert.assertEquals(series.getXYSeriesRenderStyle(), XYSeries.XYSeriesRenderStyle.Line);
    }

    @Test(groups = {"methods"})
    public void testGetLegendRenderTypePolygonArea() {
        // Asserting LegendRenderType for PolygonArea render style
        Assert.assertEquals(XYSeries.XYSeriesRenderStyle.PolygonArea.getLegendRenderType(), RenderableSeries.LegendRenderType.Box);
    }

    @Test(groups = {"methods"})
    public void testGetXDataEmptyConstructor() {
        // Creating XYSeries object with empty data
        XYSeries series = new XYSeries("Test Series", new double[]{}, new double[]{}, null, XYSeries.DataType.Number);

        // Asserting X data is empty
        Assert.assertEquals(series.getXData().length, 0);
    }


}
