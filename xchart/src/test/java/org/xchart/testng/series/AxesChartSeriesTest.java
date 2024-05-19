package org.xchart.testng.series;

import org.knowm.xchart.internal.chartpart.RenderableSeries;
import org.knowm.xchart.internal.series.AxesChartSeries;
import org.knowm.xchart.internal.series.Series;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.awt.*;
import java.util.Arrays;

public class AxesChartSeriesTest {

  @Test
  public void testConstructor_WithXAxisDataTypeOnly() {
    // Arrange
    String name = "Series1";
    Series.DataType xAxisDataType = Series.DataType.Number;

    // Act
    AxesChartSeries series = new TestAxesChartSeries(name, xAxisDataType);

    // Assert
    Assert.assertEquals(series.getName(), name);
    Assert.assertEquals(series.getxAxisDataType(), xAxisDataType);
    Assert.assertEquals(series.getyAxisDataType(), Series.DataType.Number);
  }

  @Test
  public void testConstructor_WithXYAxisDataType() {
    // Arrange
    String name = "Series2";
    Series.DataType xAxisDataType = Series.DataType.Number;
    Series.DataType yAxisDataType = Series.DataType.Date;

    // Act
    AxesChartSeries series = new TestAxesChartSeries(name, xAxisDataType, yAxisDataType);

    // Assert
    Assert.assertEquals(series.getName(), name);
    Assert.assertEquals(series.getxAxisDataType(), xAxisDataType);
    Assert.assertEquals(series.getyAxisDataType(), yAxisDataType);
  }

  @Test
  public void testCalculateMinMaxMethod() {
    // Arrange
    TestAxesChartSeries series = new TestAxesChartSeries("Test Series", Series.DataType.Number);
    series.setXData(new double[] {1.0, 2.0, 3.0, 4.0});
    series.setYData(new double[] {-1.0, -2.0, -3.0, -4.0});

    // Act
    series.calculateMinMax();

    // Assert
    Assert.assertEquals(series.getXMin(), 1.0);
    Assert.assertEquals(series.getXMax(), 4.0);
    Assert.assertEquals(series.getYMin(), -4.0);
    Assert.assertEquals(series.getYMax(), -1.0);
  }


  @Test
  public void testLineStyleAndColor() {
    // Arrange
    AxesChartSeries series = new TestAxesChartSeries("Test Series", Series.DataType.Number);
    BasicStroke stroke = new BasicStroke(2.0f);
    Color color = Color.RED;

    // Act
    series.setLineStyle(stroke);
    series.setLineColor(color);

    // Assert
    Assert.assertEquals(series.getLineStyle(), stroke);
    Assert.assertEquals(series.getLineColor(), color);
  }

  // TestAxesChartSeries class used for testing
  private static class TestAxesChartSeries extends AxesChartSeries {
    private double[] xData;
    private double[] yData;

    public TestAxesChartSeries(String name, DataType xAxisDataType) {
      super(name, xAxisDataType);
    }

    public TestAxesChartSeries(String name, DataType xAxisDataType, DataType yAxisDataType) {
      super(name, xAxisDataType, yAxisDataType);
    }

    public void setXData(double[] xData) {
      this.xData = xData;
    }

    public void setYData(double[] yData) {
      this.yData = yData;
    }

    @Override
    protected void calculateMinMax() {
      xMin = Arrays.stream(xData).min().orElse(Double.NaN);
      xMax = Arrays.stream(xData).max().orElse(Double.NaN);
      yMin = Arrays.stream(yData).min().orElse(Double.NaN);
      yMax = Arrays.stream(yData).max().orElse(Double.NaN);
    }

    @Override
    public RenderableSeries.LegendRenderType getLegendRenderType() {
      return null;
    }
  }
}
