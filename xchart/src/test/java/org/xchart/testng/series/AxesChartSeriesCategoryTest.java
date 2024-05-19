package org.xchart.testng.series;

import org.knowm.xchart.internal.chartpart.RenderableSeries;
import org.knowm.xchart.internal.series.AxesChartSeriesCategory;
import org.knowm.xchart.internal.series.Series;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AxesChartSeriesCategoryTest
{

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


    @Test
    public void testConstructor() {
        // Arrange
        String name = "Test Series";
        List<Integer> xData = Arrays.asList(1, 2, 3, 4, 5);
        List<Double> yData = Arrays.asList(10.5, 20.3, 30.1, 40.7, 50.9);
        List<Double> extraValues = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        Series.DataType xAxisDataType = Series.DataType.Number;

        // Act
        AxesChartSeriesCategory series =
                new TestAxesChartSeriesCategory(name, xData, yData, extraValues, xAxisDataType);

        // Assert
        Assert.assertEquals(series.getName(), name);
        Assert.assertEquals(series.getXData(), xData);
        Assert.assertEquals(series.getYData(), yData);
        Assert.assertEquals(series.getExtraValues(), extraValues);
    }

    @Test
    public void testReplaceData() {
        // Arrange
        List<Integer> newXData = Arrays.asList(6, 7, 8, 9, 10);
        List<Double> newYData = Arrays.asList(60.5, 70.3, 80.1, 90.7, 100.9);
        List<Double> newExtraValues = Arrays.asList(6.0, 7.0, 8.0, 9.0, 10.0);

        AxesChartSeriesCategory series =
                new TestAxesChartSeriesCategory("Test Series", Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Series.DataType.Number);

        // Act
        series.replaceData(newXData, newYData, newExtraValues);

        // Assert
        Assert.assertEquals(series.getXData(), newXData);
        Assert.assertEquals(series.getYData(), newYData);
        Assert.assertEquals(series.getExtraValues(), newExtraValues);
    }

    @Test
    public void testCalculateMinMaxMethod() throws Exception{
        // Arrange
        List<Date> xData = Arrays.asList(new Date(1000), new Date(2000), new Date(3000));
        List<Double> yData = Arrays.asList(10.5, 20.3, 30.1);
        List<Double> extraValues = Arrays.asList(1.0, 2.0, 3.0);

        AxesChartSeriesCategory series =
                new TestAxesChartSeriesCategory("Test Series", xData, yData, extraValues, Series.DataType.Date);

        // Act
        invokePrivateMethod(series, "calculateMinMax");


        // Assert
        Assert.assertEquals(series.getXMin(), 1000.0);
        Assert.assertEquals(series.getXMax(), 3000.0);
        Assert.assertEquals(series.getYMin(), 7.5); // 10.5 - 1.0
        Assert.assertEquals(series.getYMax(), 33.1); // 30.1 + 3.0
    }

    // Add similar tests for other data types (Date, String) and additional scenarios

    // TestAxesChartSeriesCategory class used for testing
    private static class TestAxesChartSeriesCategory extends AxesChartSeriesCategory {
        public TestAxesChartSeriesCategory(
                String name,
                List<?> xData,
                List<? extends Number> yData,
                List<? extends Number> extraValues,
                DataType xAxisDataType) {
            super(name, xData, yData, extraValues, xAxisDataType);
        }

        @Override
        public RenderableSeries.LegendRenderType getLegendRenderType() {
            return null;
        }
    }
}
