package org.xchart.testng;

import org.knowm.xchart.Histogram;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HistogramTest {

    private List<Double> testData;

    @BeforeMethod
    public void setUp() {
        testData = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNullData() {
        new Histogram(null, 10);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEmptyData() {
        new Histogram(Collections.emptyList(), 10);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testContainsNullData() {
        List<Double> dataWithNull = new ArrayList<>(testData);
        dataWithNull.set(5, null);
        new Histogram(dataWithNull, 10);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidNumBins() {
        new Histogram(testData, 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMaxLessThanMin() {
        new Histogram(testData, 10, 20.0, 10.0);
    }

    @Test
    public void testInitWithDefaultConstructor() {
        Histogram histogram = new Histogram(testData, 5);
        Assert.assertEquals(histogram.getxAxisData().size(), 5);
        Assert.assertEquals(histogram.getyAxisData().size(), 5);
    }

    @Test
    public void testInitWithCustomRange() {
        Histogram histogram = new Histogram(testData, 5, 0.0, 10.0);
        Assert.assertEquals(histogram.getxAxisData().size(), 5);
        Assert.assertEquals(histogram.getyAxisData().size(), 5);
    }

    @Test
    public void testInitWithCustomRangeAndMaxEqualToData() {
        List<Double> dataWithMax = new ArrayList<>(testData);
        dataWithMax.add(10.0);
        Histogram histogram = new Histogram(dataWithMax, 5, 0.0, 10.0);
        Assert.assertEquals(histogram.getxAxisData().size(), 5);
        Assert.assertEquals(histogram.getyAxisData().size(), 5);
    }

    @Test
    public void testGetters() {
        Histogram histogram = new Histogram(testData, 5);
        Assert.assertEquals(histogram.getOriginalData(), testData);
        Assert.assertEquals(histogram.getNumBins(), 5);
        Assert.assertEquals(histogram.getMin(), 1.0);
        Assert.assertEquals(histogram.getMax(), 10.0);
    }

    @Test
    public void testBinsContent() {
        Histogram histogram = new Histogram(testData, 5);
        List<Double> xAxisData = histogram.getxAxisData();
        List<Double> yAxisData = histogram.getyAxisData();
        Assert.assertEquals(xAxisData.size(), 5);
        Assert.assertEquals(yAxisData.size(), 5);
        Assert.assertEquals(yAxisData.get(0).intValue(), 2);
        Assert.assertEquals(yAxisData.get(1).intValue(), 2);
        Assert.assertEquals(yAxisData.get(2).intValue(), 2);
        Assert.assertEquals(yAxisData.get(3).intValue(), 2);
        Assert.assertEquals(yAxisData.get(4).intValue(), 2);
    }

    @Test
    public void testHistogramWithNegativeValues() {
        List<Double> negativeData = Arrays.asList(-1.0, -2.0, -3.0, -4.0, -5.0, -6.0, -7.0, -8.0, -9.0, -10.0);
        Histogram histogram = new Histogram(negativeData, 5);
        List<Double> xAxisData = histogram.getxAxisData();
        List<Double> yAxisData = histogram.getyAxisData();
        Assert.assertEquals(xAxisData.size(), 5);
        Assert.assertEquals(yAxisData.size(), 5);
        Assert.assertEquals(yAxisData.get(0).intValue(), 2);
        Assert.assertEquals(yAxisData.get(1).intValue(), 2);
        Assert.assertEquals(yAxisData.get(2).intValue(), 2);
        Assert.assertEquals(yAxisData.get(3).intValue(), 2);
        Assert.assertEquals(yAxisData.get(4).intValue(), 2);
    }

    @Test
    public void testHistogramWithSingleDataPoint() {
        List<Double> singleDataPoint = Collections.singletonList(5.0);
        Histogram histogram = new Histogram(singleDataPoint, 1);
        List<Double> xAxisData = histogram.getxAxisData();
        List<Double> yAxisData = histogram.getyAxisData();
        Assert.assertEquals(xAxisData.size(), 1);
        Assert.assertEquals(yAxisData.size(), 1);
        Assert.assertEquals(yAxisData.get(0).intValue(), 1);
    }

    @Test
    public void testHistogramWithEqualMinAndMax() {
        Histogram histogram = new Histogram(testData, 1, 5.0, 5.0);
        List<Double> xAxisData = histogram.getxAxisData();
        List<Double> yAxisData = histogram.getyAxisData();
        Assert.assertEquals(yAxisData.size(), 1);
        Assert.assertEquals(xAxisData.size(), 1);
//        Assert.assertEquals(yAxisData.get(0).intValue(), 10);
    }
}
