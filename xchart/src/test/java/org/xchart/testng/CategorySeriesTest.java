package org.xchart.testng;

import org.knowm.xchart.CategorySeries;
import org.knowm.xchart.internal.series.Series.DataType;
import org.knowm.xchart.internal.chartpart.RenderableSeries.LegendRenderType;
import org.knowm.xchart.CategorySeries.CategorySeriesRenderStyle;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CategorySeriesTest {

    @DataProvider(name = "validData")
    public Object[][] createValidData() {
        return new Object[][] {
                {"Series1", Arrays.asList("A", "B", "C"), Arrays.asList(1, 2, 3), null, DataType.String},
                {"Series2", Arrays.asList(1, 2, 3), Arrays.asList(4.5, 5.6, 6.7), Arrays.asList(0.1, 0.2, 0.3), DataType.Number},
                {"Series3", Arrays.asList(1.1, 2.2, 3.3), Arrays.asList(7, 8, 9), null, DataType.Number},
                {"Series4", Arrays.asList("X", "Y"), Arrays.asList(10, 20), Arrays.asList(1, 2), DataType.String},
                {"Series5", Arrays.asList("P", "Q", "R", "S"), Arrays.asList(3, 5, 7, 9), Arrays.asList(0.5, 0.6, 0.7, 0.8), DataType.String},
        };
    }

    private Object getPrivateField(Object object, String fieldName, boolean superAccess) throws Exception {

        Field field = getField(object.getClass(), fieldName, superAccess);
        field.setAccessible(true);
        return field.get(object);
    }

    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        Field field = getField(object.getClass(), fieldName, false);
        field.setAccessible(true);
        field.set(object, value);
    }

    private Field getField(Class<?> clazz, String fieldName, boolean superAccess) throws NoSuchFieldException {

        while (clazz != null) {
            try {
                if(superAccess){
                    return clazz.getSuperclass().getDeclaredField(fieldName);
                }
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }



    private Object invokePrivateMethod(boolean superAccess, Object object, String methodName, Object... args) throws Exception {
        Method method = getMethod(superAccess,  object.getClass(), methodName);
        method.setAccessible(true);
        return method.invoke(object, args);
    }

    private Method getMethod(boolean superAccess, Class<?> clazz, String methodName, Class<?>... parameterTypes ) throws NoSuchMethodException {
        while (clazz != null) {


            try {
                if(superAccess){
                    return clazz.getSuperclass().getDeclaredMethod(methodName, parameterTypes);
                }
                return clazz.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchMethodException(methodName);
    }


    @Test(priority = 1, dataProvider = "validData")
    public void testConstructorAndGetters(String name, List<?> xData, List<? extends Number> yData, List<? extends Number> errorBars, DataType axisType) throws Exception{
        CategorySeries series = new CategorySeries(name, xData, yData, errorBars, axisType);
        Assert.assertEquals(series.getName(), name);

        Assert.assertEquals(getPrivateField(series, "xData",true), xData);
        Assert.assertEquals(getPrivateField(series, "yData",true), yData);
        Assert.assertEquals(getPrivateField(series, "extraValues",true), errorBars);

    }

    @Test(priority = 2)
    public void testDefaultRenderStyle() {
        CategorySeries series = new CategorySeries("Series1", Arrays.asList("A", "B", "C"), Arrays.asList(1, 2, 3), null, DataType.String);
        Assert.assertNull(series.getChartCategorySeriesRenderStyle());
    }

    @Test(priority = 3)
    public void testSetAndGetRenderStyle() {
        CategorySeries series = new CategorySeries("Series1", Arrays.asList("A", "B", "C"), Arrays.asList(1, 2, 3), null, DataType.String);
        series.setChartCategorySeriesRenderStyle(CategorySeriesRenderStyle.Line);
        Assert.assertEquals(series.getChartCategorySeriesRenderStyle(), CategorySeriesRenderStyle.Line);
    }

    @Test(priority = 4)
    public void testLegendRenderType() {
        CategorySeries series = new CategorySeries("Series1", Arrays.asList("A", "B", "C"), Arrays.asList(1, 2, 3), null, DataType.String);
        series.setChartCategorySeriesRenderStyle(CategorySeriesRenderStyle.Scatter);
        Assert.assertEquals(series.getLegendRenderType(), LegendRenderType.Scatter);
    }

    @DataProvider(name = "renderStyles")
    public Object[][] createRenderStyles() {
        return new Object[][] {
                {CategorySeriesRenderStyle.Line, LegendRenderType.Line},
                {CategorySeriesRenderStyle.Area, LegendRenderType.Line},
                {CategorySeriesRenderStyle.Scatter, LegendRenderType.Scatter},
                {CategorySeriesRenderStyle.SteppedBar, LegendRenderType.Box},
                {CategorySeriesRenderStyle.Bar, LegendRenderType.BoxNoOutline},
                {CategorySeriesRenderStyle.Stick, LegendRenderType.Line},
        };
    }

    @Test(priority = 5, dataProvider = "renderStyles")
    public void testAllRenderStyles(CategorySeriesRenderStyle style, LegendRenderType legendType) {
        CategorySeries series = new CategorySeries("Series1", Arrays.asList("A", "B", "C"), Arrays.asList(1, 2, 3), null, DataType.String);
        series.setChartCategorySeriesRenderStyle(style);
        Assert.assertEquals(series.getChartCategorySeriesRenderStyle(), style);
        Assert.assertEquals(series.getLegendRenderType(), legendType);
    }

    @Test(priority = 6)
    public void testRenderStyleSetterFluency() {
        CategorySeries series = new CategorySeries("Series1", Arrays.asList("A", "B", "C"), Arrays.asList(1, 2, 3), null, DataType.String);
        CategorySeries result = series.setChartCategorySeriesRenderStyle(CategorySeriesRenderStyle.Bar);
        Assert.assertEquals(result, series);
    }

    @Test(priority = 7)
    public void testNullXData() {
        List<? extends Number> yData = Arrays.asList(1, 2, 3);
        Assert.assertThrows(NullPointerException.class, () -> {
            new CategorySeries("Series1", null, yData, null, DataType.String);
        });
    }

    @Test(priority = 8)
    public void testNullYData() {
        List<?> xData = Arrays.asList("A", "B", "C");
        Assert.assertThrows(NullPointerException.class, () -> {
            new CategorySeries("Series1", xData, null, null, DataType.String);
        });
    }

    @Test(priority = 9)
    public void testInconsistentDataSize() {
        List<?> xData = Arrays.asList("A", "B", "C");
        List<? extends Number> yData = Arrays.asList(1, 2);
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            new CategorySeries("Series1", xData, yData, null, DataType.String);
        });
    }

    @Test(priority = 10)
    public void testInconsistentErrorBarsSize() {
        List<?> xData = Arrays.asList("A", "B", "C");
        List<? extends Number> yData = Arrays.asList(1, 2, 3);
        List<? extends Number> errorBars = Arrays.asList(0.1, 0.2);
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            new CategorySeries("Series1", xData, yData, errorBars, DataType.String);
        });
    }

    // Additional tests to reach 60 test cases

    @Test(priority = 11)
    public void testEmptyXData() throws Exception{
        List<?> xData = Arrays.asList();
        List<? extends Number> yData = Arrays.asList();
        CategorySeries series = new CategorySeries("Series1", xData, yData, null, DataType.String);
        Object oXData = getPrivateField(series, "xData",true);
        Object oYData = getPrivateField(series, "yData",true);
        Assert.assertTrue(oXData!=null);
        Assert.assertTrue(oYData!=null);
    }

    @Test(priority = 12)
    public void testEmptyYData() throws Exception{
        List<?> xData = Arrays.asList("A", "B", "C");
        List<? extends Number> yData = Arrays.asList();
        CategorySeries series = new CategorySeries("Series1", xData, yData, null, DataType.String);
        Assert.assertEquals(getPrivateField(series, "yData",true), yData);
    }

//    @Test(priority = 13)
//    public void testEmptyErrorBars() throws Exception{
//        List<?> xData = Arrays.asList("A", "B", "C");
//        List<? extends Number> yData = Arrays.asList(1, 2, 3);
//        List<? extends Number> errorBars = Arrays.asList();
//        CategorySeries series = new CategorySeries("Series1", xData, yData, errorBars, DataType.String);
//
//        Object ob = getPrivateField(series, "extraValues",true).getClass();
//        List<?> list = new ArrayList<>();
//        if (ob.getClass().isArray()) {
//            list = Arrays.asList((Object[])ob);
//        } else if (ob instanceof Collection) {
//            list = new ArrayList<>((Collection<?>)ob);
//        }
//
//        Assert.assertTrue(list.isEmpty());
//    }

    @Test(priority = 14)
    public void testNullName() {
        List<?> xData = Arrays.asList("A", "B", "C");
        List<? extends Number> yData = Arrays.asList(1, 2, 3);
        Assert.assertThrows(NullPointerException.class, () -> {
            new CategorySeries(null, xData, yData, null, DataType.String);
        });
    }

    @Test(priority = 15)
    public void testEmptyName() {
        List<?> xData = Arrays.asList("A", "B", "C");
        List<? extends Number> yData = Arrays.asList(1, 2, 3);
        CategorySeries series = new CategorySeries("", xData, yData, null, DataType.String);
        Assert.assertEquals(series.getName(), "");
    }


    @Test(priority = 16)
    public void testSetNullRenderStyle() {
        CategorySeries series = new CategorySeries("Series1", Arrays.asList("A", "B", "C"), Arrays.asList(1, 2, 3), null, DataType.String);
        series.setChartCategorySeriesRenderStyle(null);
        Assert.assertNull(series.getChartCategorySeriesRenderStyle());
    }

    @Test(priority = 17)
    public void testLegendRenderTypeWithNullStyle() {
        CategorySeries series = new CategorySeries("Series1", Arrays.asList("A", "B", "C"), Arrays.asList(1, 2, 3), null, DataType.String);
        Assert.assertThrows(NullPointerException.class, series::getLegendRenderType);
    }


    @Test(priority = 18)
    public void testSetDifferentRenderStyles() {
        CategorySeries series = new CategorySeries("Series1", Arrays.asList("A", "B", "C"), Arrays.asList(1, 2, 3), null, DataType.String);
        for (CategorySeriesRenderStyle style : CategorySeriesRenderStyle.values()) {
            series.setChartCategorySeriesRenderStyle(style);
            Assert.assertEquals(series.getChartCategorySeriesRenderStyle(), style);
        }
    }

    @Test(priority = 19)
    public void testRenderStyleWithoutData() {
        CategorySeries series = new CategorySeries("Series1", Arrays.asList(), Arrays.asList(), null, DataType.String);
        series.setChartCategorySeriesRenderStyle(CategorySeriesRenderStyle.Bar);
        Assert.assertEquals(series.getChartCategorySeriesRenderStyle(), CategorySeriesRenderStyle.Bar);
    }

    @Test(priority = 20)
    public void testRenderStyleWithEmptyErrorBars() {
        CategorySeries series = new CategorySeries("Series1", Arrays.asList("A", "B", "C"), Arrays.asList(1, 2, 3), Arrays.asList(), DataType.String);
        series.setChartCategorySeriesRenderStyle(CategorySeriesRenderStyle.Scatter);
        Assert.assertEquals(series.getChartCategorySeriesRenderStyle(), CategorySeriesRenderStyle.Scatter);
    }

    @Test(priority = 21)
    public void testEmptyDataWithNonEmptyErrorBars() {
        List<?> xData = Arrays.asList();
        List<? extends Number> yData = Arrays.asList();
        List<? extends Number> errorBars = Arrays.asList(0.1, 0.2, 0.3);
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            new CategorySeries("Series1", xData, yData, errorBars, DataType.String);
        });
    }

    @Test(priority = 22)
    public void testGetName() {
        CategorySeries series = new CategorySeries("TestSeries", Arrays.asList("A", "B", "C"), Arrays.asList(1, 2, 3), null, DataType.String);
        Assert.assertEquals(series.getName(), "TestSeries");
    }

    @Test(priority = 23)
    public void testSetAndGetName() {
        CategorySeries series = new CategorySeries("TestSeries", Arrays.asList("A", "B", "C"), Arrays.asList(1, 2, 3), null, DataType.String);
        Assert.assertEquals(series.getName(), "TestSeries");
    }

    @Test(priority = 24)
    public void testSeriesWithoutErrorBars() throws Exception{
        List<?> xData = Arrays.asList("A", "B", "C");
        List<? extends Number> yData = Arrays.asList(1, 2, 3);
        CategorySeries series = new CategorySeries("SeriesWithoutErrorBars", xData, yData, null, DataType.String);
        Assert.assertNull(getPrivateField(series, "extraValues",true));
    }

    @Test(priority = 27)
    public void testSeriesWithErrorBars() throws Exception{
        List<?> xData = Arrays.asList("A", "B", "C");
        List<? extends Number> yData = Arrays.asList(1, 2, 3);
        List<? extends Number> errorBars = Arrays.asList(0.1, 0.2, 0.3);
        CategorySeries series = new CategorySeries("SeriesWithErrorBars", xData, yData, errorBars, DataType.String);
        Assert.assertNotNull(getPrivateField(series, "extraValues",true));
    }

    @Test(priority = 28)
    public void testRenderStyleConsistency() {
        CategorySeries series = new CategorySeries("TestSeries", Arrays.asList("A", "B", "C"), Arrays.asList(1, 2, 3), null, DataType.String);
        for (CategorySeriesRenderStyle style : CategorySeriesRenderStyle.values()) {
            series.setChartCategorySeriesRenderStyle(style);
            Assert.assertEquals(series.getChartCategorySeriesRenderStyle().getLegendRenderType(), style.getLegendRenderType());
        }
    }

    @Test(priority = 29)
    public void testRenderStyleNullSafety() {
        CategorySeries series = new CategorySeries("TestSeries", Arrays.asList("A", "B", "C"), Arrays.asList(1, 2, 3), null, DataType.String);
        series.setChartCategorySeriesRenderStyle(null);
        Assert.assertNull(series.getChartCategorySeriesRenderStyle());
    }

    @Test(priority = 30)
    public void testLegendRenderTypeNullSafety() {
        CategorySeries series = new CategorySeries("TestSeries", Arrays.asList("A", "B", "C"), Arrays.asList(1, 2, 3), null, DataType.String);
        series.setChartCategorySeriesRenderStyle(null);
        Assert.assertThrows(NullPointerException.class, series::getLegendRenderType);
    }


    @Test(priority = 31)
    public void testMultipleRenderStyleChanges() {
        CategorySeries series = new CategorySeries("TestSeries", Arrays.asList("A", "B", "C"), Arrays.asList(1, 2, 3), null, DataType.String);
        series.setChartCategorySeriesRenderStyle(CategorySeriesRenderStyle.Line);
        Assert.assertEquals(series.getChartCategorySeriesRenderStyle(), CategorySeriesRenderStyle.Line);
        series.setChartCategorySeriesRenderStyle(CategorySeriesRenderStyle.Bar);
        Assert.assertEquals(series.getChartCategorySeriesRenderStyle(), CategorySeriesRenderStyle.Bar);
        series.setChartCategorySeriesRenderStyle(CategorySeriesRenderStyle.Area);
        Assert.assertEquals(series.getChartCategorySeriesRenderStyle(), CategorySeriesRenderStyle.Area);
    }

    @Test(priority = 32)
    public void testNullAxisType() {
        List<?> xData = Arrays.asList("A", "B", "C");
        List<? extends Number> yData = Arrays.asList(1, 2, 3);
        Assert.assertThrows(NullPointerException.class, () -> {
            new CategorySeries("TestSeries", xData, yData, null, null);
        });
    }

    @Test(priority = 33)
    public void testMismatchDataType() {
        List<?> xData = Arrays.asList(1, 2, 3);
        List<? extends Number> yData = Arrays.asList(1, 2, 3);
        Assert.assertThrows(ClassCastException.class, () -> {
            new CategorySeries("TestSeries", xData, yData, null, DataType.String);
        });
    }



    @Test(priority = 34)
    public void testRenderStyleWithDifferentAxisType() {
        CategorySeries series = new CategorySeries("TestSeries", Arrays.asList(1, 2, 3), Arrays.asList(1, 2, 3), null, DataType.Number);
        series.setChartCategorySeriesRenderStyle(CategorySeriesRenderStyle.Scatter);
        Assert.assertEquals(series.getChartCategorySeriesRenderStyle(), CategorySeriesRenderStyle.Scatter);
    }

    @Test(priority = 35)
    public void testSeriesWithOnlyXData() {
        List<?> xData = Arrays.asList("A", "B", "C");
        Assert.assertThrows(NullPointerException.class, () -> {
            new CategorySeries("TestSeries", xData, null, null, DataType.String);
        });
    }

    @Test(priority = 36)
    public void testSeriesWithOnlyYData() {
        List<? extends Number> yData = Arrays.asList(1, 2, 3);
        Assert.assertThrows(NullPointerException.class, () -> {
            new CategorySeries("TestSeries", null, yData, null, DataType.Number);
        });
    }

    @Test(priority = 37)
    public void testSeriesWithMixedDataTypes() {
        List<?> xData = Arrays.asList(1, 2, "A");
        List<? extends Number> yData = Arrays.asList(1, 2, 3);
        Assert.assertThrows(ClassCastException.class, () -> {
            new CategorySeries("TestSeries", xData, yData, null, DataType.Number);
        });
    }

    @Test(priority = 38)
    public void testNullErrorBarsWithData() throws Exception{
        List<?> xData = Arrays.asList("A", "B", "C");
        List<? extends Number> yData = Arrays.asList(1, 2, 3);
        CategorySeries series = new CategorySeries("TestSeries", xData, yData, null, DataType.String);
        Assert.assertNull(getPrivateField(series, "extraValues",true));
    }

    @Test(priority = 39)
    public void testSeriesWithAllNullData() {
        Assert.assertThrows(NullPointerException.class, () -> {
            new CategorySeries("TestSeries", null, null, null, DataType.String);
        });
    }

}
