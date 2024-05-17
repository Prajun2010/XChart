package org.xchart.testng;

import org.knowm.xchart.BubbleSeries;
import org.knowm.xchart.internal.chartpart.RenderableSeries;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class BubbleSeriesTest {

    @Test
    public void testConstructor() {
        double[] xData = {1, 2, 3};
        double[] yData = {4, 5, 6};
        double[] bubbleSizes = {10, 20, 30};
        BubbleSeries series = new BubbleSeries("Test Series", xData, yData, bubbleSizes);

        assertNotNull(series);
        assertEquals(series.getName(), "Test Series");
        assertEquals(series.getXData(), xData);
        assertEquals(series.getYData(), yData);
        assertEquals(series.getExtraValues(), bubbleSizes); // Corrected to getExtraValues()
    }

    @Test
    public void testBubbleSeriesRenderStyle() {
        BubbleSeries.BubbleSeriesRenderStyle style = BubbleSeries.BubbleSeriesRenderStyle.Round;
        assertEquals(style.getLegendRenderType(), RenderableSeries.LegendRenderType.Box);
    }

    @Test
    public void testSetAndGetBubbleSeriesRenderStyle() {
        BubbleSeries series = new BubbleSeries("Test Series", new double[]{1, 2}, new double[]{3, 4}, new double[]{5, 6});
        assertNull(series.getBubbleSeriesRenderStyle());

        series.setBubbleSeriesRenderStyle(BubbleSeries.BubbleSeriesRenderStyle.Round);
        assertEquals(series.getBubbleSeriesRenderStyle(), BubbleSeries.BubbleSeriesRenderStyle.Round);
        assertEquals(series.getLegendRenderType(), RenderableSeries.LegendRenderType.Box);
    }

    @Test
    public void testSetAndGetInvalidBubbleSeriesRenderStyle() {
        BubbleSeries series = new BubbleSeries("Test Series", new double[]{1, 2}, new double[]{3, 4}, new double[]{5, 6});
        assertNull(series.getBubbleSeriesRenderStyle());

        series.setBubbleSeriesRenderStyle(null);
        assertNull(series.getBubbleSeriesRenderStyle());
    }

    @Test
    public void testGetLegendRenderTypeWhenStyleIsNull() {
        BubbleSeries series = new BubbleSeries("Test Series", new double[]{1, 2}, new double[]{3, 4}, new double[]{5, 6});
        assertNull(series.getBubbleSeriesRenderStyle());
    }
}
