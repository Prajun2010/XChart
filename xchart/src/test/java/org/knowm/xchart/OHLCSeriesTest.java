package org.knowm.xchart;

import org.knowm.xchart.internal.chartpart.RenderableSeries;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.awt.Color;

public class OHLCSeriesTest {

    @Test
    public void testConstructorWithOHLCData() {
        OHLCSeries series = new OHLCSeries("Test Series", new double[]{1, 2, 3}, new double[]{10, 20, 30},
                new double[]{15, 25, 35}, new double[]{5, 15, 25}, new double[]{12, 22, 32}, OHLCSeries.DataType.Number);
        Assert.assertEquals(series.getName(), "Test Series");
        Assert.assertEquals(series.getXData(), new double[]{1, 2, 3});
        Assert.assertEquals(series.getOpenData(), new double[]{10, 20, 30});
        Assert.assertEquals(series.getHighData(), new double[]{15, 25, 35});
        Assert.assertEquals(series.getLowData(), new double[]{5, 15, 25});
        Assert.assertEquals(series.getCloseData(), new double[]{12, 22, 32});
    }

    @Test
    public void testConstructorWithOHLCAndVolumeData() {
        OHLCSeries series = new OHLCSeries("Test Series", new double[]{1, 2, 3}, new double[]{10, 20, 30},
                new double[]{15, 25, 35}, new double[]{5, 15, 25}, new double[]{12, 22, 32}, new long[]{100, 200, 300}, OHLCSeries.DataType.Number);
        Assert.assertEquals(series.getName(), "Test Series");
        Assert.assertEquals(series.getXData(), new double[]{1, 2, 3});
        Assert.assertEquals(series.getOpenData(), new double[]{10, 20, 30});
        Assert.assertEquals(series.getHighData(), new double[]{15, 25, 35});
        Assert.assertEquals(series.getLowData(), new double[]{5, 15, 25});
        Assert.assertEquals(series.getCloseData(), new double[]{12, 22, 32});
        Assert.assertEquals(series.getVolumeData(), new long[]{100, 200, 300});
    }

    @Test
    public void testConstructorWithYData() {
        OHLCSeries series = new OHLCSeries("Test Series", new double[]{1, 2, 3}, new double[]{10, 20, 30}, OHLCSeries.DataType.Number);
        Assert.assertEquals(series.getName(), "Test Series");
        Assert.assertEquals(series.getXData(), new double[]{1, 2, 3});
        Assert.assertEquals(series.getYData(), new double[]{10, 20, 30});
    }



    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidOhlcSeriesRenderStyle() {
        OHLCSeries series = new OHLCSeries("Test Series", new double[]{1, 2, 3}, new double[]{10, 20, 30}, OHLCSeries.DataType.Number);
        series.setOhlcSeriesRenderStyle(OHLCSeries.OHLCSeriesRenderStyle.Line);
    }

    @Test
    public void testSetUpColor() {
        OHLCSeries series = new OHLCSeries("Test Series", new double[]{1, 2, 3}, new double[]{10, 20, 30}, OHLCSeries.DataType.Number);
        series.setUpColor(Color.RED);
        Assert.assertEquals(series.getUpColor(), Color.RED);
    }

    @Test
    public void testSetDownColor() {
        OHLCSeries series = new OHLCSeries("Test Series", new double[]{1, 2, 3}, new double[]{10, 20, 30}, OHLCSeries.DataType.Number);
        series.setDownColor(Color.GREEN);
        Assert.assertEquals(series.getDownColor(), Color.GREEN);
    }

    // Additional test cases

    @Test
    public void testReplaceDataWithOHLC() {
        OHLCSeries series = new OHLCSeries("Test Series", new double[]{1, 2, 3}, new double[]{10, 20, 30},
                new double[]{15, 25, 35}, new double[]{5, 15, 25}, new double[]{12, 22, 32}, OHLCSeries.DataType.Number);
        series.replaceData(new double[]{4, 5, 6}, new double[]{25, 35, 45}, new double[]{30, 40, 50},
                new double[]{20, 30, 40}, new double[]{22, 32, 42});
        Assert.assertEquals(series.getXData(), new double[]{4, 5, 6});
        Assert.assertEquals(series.getOpenData(), new double[]{25, 35, 45});
        Assert.assertEquals(series.getHighData(), new double[]{30, 40, 50});
        Assert.assertEquals(series.getLowData(), new double[]{20, 30, 40});
        Assert.assertEquals(series.getCloseData(), new double[]{22, 32, 42});
    }

    @Test
    public void testReplaceDataWithOHLCAndVolume() {
        OHLCSeries series = new OHLCSeries("Test Series", new double[]{1, 2, 3}, new double[]{10, 20, 30},
                new double[]{15, 25, 35}, new double[]{5, 15, 25}, new double[]{12, 22, 32}, OHLCSeries.DataType.Number);
        series.replaceData(new double[]{4, 5, 6}, new double[]{25, 35, 45}, new double[]{30, 40, 50},
                new double[]{20, 30, 40}, new double[]{22, 32, 42}, new long[]{400, 500, 600});
        Assert.assertEquals(series.getXData(), new double[]{4, 5, 6});
        Assert.assertEquals(series.getOpenData(), new double[]{25, 35, 45});
        Assert.assertEquals(series.getHighData(), new double[]{30, 40, 50});
        Assert.assertEquals(series.getLowData(), new double[]{20, 30, 40});
        Assert.assertEquals(series.getCloseData(), new double[]{22, 32, 42});
        Assert.assertEquals(series.getVolumeData(), new long[]{400, 500, 600});
    }

    @Test
    public void testReplaceDataWithY() {
        OHLCSeries series = new OHLCSeries("Test Series", new double[]{1, 2, 3}, new double[]{10, 20, 30}, OHLCSeries.DataType.Number);
        series.replaceData(new double[]{4, 5, 6}, new double[]{25, 35, 45});
        Assert.assertEquals(series.getXData(), new double[]{4, 5, 6});
        Assert.assertEquals(series.getYData(), new double[]{25, 35, 45});
    }




    @Test
    public void testCalculateMinMax() {
        OHLCSeries series = new OHLCSeries("Test Series", new double[]{1, 2, 3}, new double[]{10, 20, 30},
                new double[]{15, 25, 35}, new double[]{5, 15, 25}, new double[]{12, 22, 32}, OHLCSeries.DataType.Number);
        series.calculateMinMax();
        Assert.assertEquals(series.getXMin(), 1.0);
        Assert.assertEquals(series.getXMax(), 3.0);
        Assert.assertEquals(series.getYMin(), 5.0);
        Assert.assertEquals(series.getYMax(), 35.0);
    }

    @Test
    public void testGetLegendRenderType() {
        OHLCSeries.OHLCSeriesRenderStyle renderStyle = OHLCSeries.OHLCSeriesRenderStyle.Candle;
        Assert.assertEquals(renderStyle.getLegendRenderType(), RenderableSeries.LegendRenderType.Line);
    }


}

