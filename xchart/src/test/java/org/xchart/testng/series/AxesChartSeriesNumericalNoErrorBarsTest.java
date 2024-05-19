package org.xchart.testng.series;

import org.knowm.xchart.internal.chartpart.RenderableSeries;
import org.knowm.xchart.internal.series.AxesChartSeriesNumericalNoErrorBars;
import org.knowm.xchart.internal.series.Series;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AxesChartSeriesNumericalNoErrorBarsTest {

    private static final double EPSILON = 1e-9;
    private AxesChartSeriesNumericalNoErrorBars series;

    private Object getPrivateField(Object object, String fieldName) throws Exception {
        Field field = getField(object.getClass(), fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        Field field = getField(object.getClass(), fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    private Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }


    private Object invokePrivateMethod(Object object, String methodName, Object... args) throws Exception {
        Method method = getMethod(object.getClass(), methodName);
        method.setAccessible(true);
        return method.invoke(object, args);
    }

    private Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        while (clazz != null) {
            try {
                return clazz.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchMethodException(methodName);
    }


    @BeforeMethod
    public void setUp() {
        double[] xData = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] yData = {10.0, 20.0, 30.0, 40.0, 50.0};
        double[] extraValues = {0.5, 0.5, 0.5, 0.5, 0.5};
        series = new TestAxesChartSeriesNumericalNoErrorBars("Test Series", xData, yData, extraValues, Series.DataType.Number);
    }

    @Test
    public void testConstructor() {
        // Assert
        Assert.assertEquals(series.getXData(), new double[]{1.0, 2.0, 3.0, 4.0, 5.0});
        Assert.assertEquals(series.getYData(), new double[]{10.0, 20.0, 30.0, 40.0, 50.0});
        Assert.assertEquals(series.getExtraValues(), new double[]{0.5, 0.5, 0.5, 0.5, 0.5});
    }

    @Test
    public void testReplaceData() {
        // Arrange
        double[] newXData = {6.0, 7.0, 8.0, 9.0, 10.0};
        double[] newYData = {60.0, 70.0, 80.0, 90.0, 100.0};
        double[] newExtraValues = {1.0, 1.0, 1.0, 1.0, 1.0};

        // Act
        series.replaceData(newXData, newYData, newExtraValues);

        // Assert
        Assert.assertEquals(series.getXData(), newXData);
        Assert.assertEquals(series.getYData(), newYData);
        Assert.assertEquals(series.getExtraValues(), newExtraValues);
    }

    @Test
    public void testFilterXByIndex() {
        // Act
        series.filterXByIndex(1, 4);

        // Assert
        Assert.assertEquals(series.getXData(), new double[]{2.0, 3.0, 4.0});
        Assert.assertEquals(series.getYData(), new double[]{20.0, 30.0, 40.0});
        Assert.assertEquals(series.getExtraValues(), new double[]{0.5, 0.5, 0.5});
    }

    @Test
    public void testFilterXByValue() {
        // Act
        boolean result = series.filterXByValue(2.0, 4.0);

        // Assert
        Assert.assertTrue(result);
        Assert.assertEquals(series.getXData(), new double[]{2.0, 3.0, 4.0});
        Assert.assertEquals(series.getYData(), new double[]{20.0, 30.0, 40.0});
        Assert.assertEquals(series.getExtraValues(), new double[]{0.5, 0.5, 0.5});
    }

    @Test
    public void testFilterXByValue_NoDataWithinRange() {
        // Act
        boolean result = series.filterXByValue(6.0, 10.0);

        // Assert
        Assert.assertTrue(result);
        Assert.assertEquals(series.getXData().length, 0);
        Assert.assertEquals(series.getYData().length, 0);
        Assert.assertEquals(series.getExtraValues().length, 0);
    }

    @Test
    public void testResetFilter() {
        // Arrange
        series.filterXByIndex(1, 4);

        // Act
        series.resetFilter();

        // Assert
        Assert.assertEquals(series.getXData(), new double[]{1.0, 2.0, 3.0, 4.0, 5.0});
        Assert.assertEquals(series.getYData(), new double[]{10.0, 20.0, 30.0, 40.0, 50.0});
        Assert.assertEquals(series.getExtraValues(), new double[]{0.5, 0.5, 0.5, 0.5, 0.5});
    }

    @Test
    public void testFindMinMax() throws Exception{
        // Arrange
        double[] data = {1.0, 3.0, 5.0, 7.0};

        // Act
        double[] minMax = (double[]) invokePrivateMethod(series, "findMinMax", (Object) data);

        // Assert
        Assert.assertEquals(minMax[0], 1.0, EPSILON);
        Assert.assertEquals(minMax[1], 7.0, EPSILON);
    }

    @Test
    public void testFindMinMaxWithErrorBars() throws Exception{
        // Arrange
        double[] data = {1.0, 3.0, 5.0, 7.0};
        double[] errorBars = {0.5, 0.5, 0.5, 0.5};

        // Act
        double[] minMax = (double[]) invokePrivateMethod(series, "findMinMaxWithErrorBars", (Object) data, errorBars);


        // Assert
        Assert.assertEquals(minMax[0], 0.5, EPSILON);
        Assert.assertEquals(minMax[1], 7.5, EPSILON);
    }

    // TestAxesChartSeriesNumericalNoErrorBars class used for testing
    private static class TestAxesChartSeriesNumericalNoErrorBars extends AxesChartSeriesNumericalNoErrorBars {
        public TestAxesChartSeriesNumericalNoErrorBars(
                String name,
                double[] xData,
                double[] yData,
                double[] extraValues,
                DataType xAxisDataType) {
            super(name, xData, yData, extraValues, xAxisDataType);
        }

        @Override
        public RenderableSeries.LegendRenderType getLegendRenderType() {
            return null;
        }
    }
}
