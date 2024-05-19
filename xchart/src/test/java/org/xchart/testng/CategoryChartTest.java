package org.xchart.testng;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategorySeries;
import org.knowm.xchart.internal.chartpart.AxisPair;
import org.knowm.xchart.internal.chartpart.Legend_Marker;
import org.knowm.xchart.internal.chartpart.Plot_Category;
import org.knowm.xchart.internal.series.Series;
import org.knowm.xchart.style.CategoryStyler;
import org.knowm.xchart.style.theme.Theme;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryChartTest {

    private CategoryChart chart;

    @BeforeMethod
    public void setUp() {
        chart = new CategoryChart(800, 600);
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


    @Test
    public void testConstructorWithDimensions() {
        CategoryChart chart = new CategoryChart(800, 600);
        assertNotNull(chart);
        assertEquals(chart.getWidth(), 800);
        assertEquals(chart.getHeight(), 600);
    }

    @Test
    public void testConstructorWithTheme() {
        Theme theme = new CategoryStyler().getTheme();
        CategoryChart chart = new CategoryChart(800, 600, theme);
        assertNotNull(chart);
        assertEquals(chart.getWidth(), 800);
        assertEquals(chart.getHeight(), 600);
        assertEquals(chart.getStyler().getTheme(), theme);
    }

    @Test
    public void testConstructorWithChartTheme() {
        Theme chartTheme = new CategoryStyler().getTheme();
        CategoryChart chart = new CategoryChart(800, 600, chartTheme);
        assertNotNull(chart);
        assertEquals(chart.getWidth(), 800);
        assertEquals(chart.getHeight(), 600);
    }


    @Test
    public void testAddSeriesWithDoubleArrays() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {1.0, 4.0, 9.0};
        CategorySeries series = chart.addSeries("Series1", xData, yData);
        assertNotNull(series);
        assertEquals(series.getName(), "Series1");
        assertEquals(series.getXData(), Arrays.asList(1.0, 2.0, 3.0));
        assertEquals(series.getYData(), Arrays.asList(1.0, 4.0, 9.0));
    }

    @Test
    public void testAddSeriesWithDoubleArraysAndErrorBars() throws Exception {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {1.0, 4.0, 9.0};
        double[] errorBars = {0.1, 0.2, 0.3};
        CategorySeries series = chart.addSeries("Series1", xData, yData, errorBars);
        assertNotNull(series);
        assertEquals(series.getName(), "Series1");
        assertEquals(series.getXData(), Arrays.asList(1.0, 2.0, 3.0));
        assertEquals(series.getYData(), Arrays.asList(1.0, 4.0, 9.0));
        assertEquals( getPrivateField(series, "extraValues", true), Arrays.asList(0.1, 0.2, 0.3));
    }

    @Test
    public void testAddSeriesWithIntArrays() {
        int[] xData = {1, 2, 3};
        int[] yData = {1, 4, 9};
        CategorySeries series = chart.addSeries("Series1", xData, yData);
        assertNotNull(series);
        assertEquals(series.getName(), "Series1");

        Object[] value = series.getXData().toArray();
        int[] xlist = Arrays.stream(value)
                .mapToDouble(o -> (Double) o) // Convert Object to Double
                .mapToInt(d -> (int) d)       // Cast Double to int
                .toArray();

        List<Integer> actualXList = Arrays.stream(xlist).boxed().collect(Collectors.toList());
        // Convert the integer array to a list


        int[] ylist = series.getYData().stream().mapToInt(Number::intValue).toArray();
        List<Integer> actualYList = Arrays.stream(ylist).boxed().collect(Collectors.toList());

        assertEquals(actualXList, Arrays.asList(1, 2, 3));
        assertEquals(actualYList, Arrays.asList(1, 4, 9));
    }

    @Test
    public void testAddSeriesWithIntArraysAndErrorBars() throws Exception{
        int[] xData = {1, 2, 3};
        int[] yData = {1, 4, 9};
        int[] errorBars = {1, 2, 3};
        CategorySeries series = chart.addSeries("Series1", xData, yData, errorBars);
        assertNotNull(series);

        Object[] value = series.getXData().toArray();
        int[] xlist = Arrays.stream(value)
                .mapToDouble(o -> (Double) o) // Convert Object to Double
                .mapToInt(d -> (int) d)       // Cast Double to int
                .toArray();

        List<Integer> actualXList = Arrays.stream(xlist).boxed().collect(Collectors.toList());
        // Convert the integer array to a list

        int[] ylist = series.getYData().stream().mapToInt(Number::intValue).toArray();
        List<Integer> actualYList = Arrays.stream(ylist).boxed().collect(Collectors.toList());


        assertEquals(series.getName(), "Series1");
        assertEquals(actualXList, Arrays.asList(1, 2, 3));
        assertEquals(actualYList, Arrays.asList(1, 4, 9));


        assertEquals(getPrivateField(series, "extraValues",true), Arrays.asList(1.0, 2.0, 3.0));

    }

    @Test
    public void testAddSeriesWithLists() {
        List<String> xData = Arrays.asList("A", "B", "C");
        List<Double> yData = Arrays.asList(1.0, 4.0, 9.0);
        CategorySeries series = chart.addSeries("Series1", xData, yData);
        assertNotNull(series);
        assertEquals(series.getName(), "Series1");
        assertEquals(series.getXData(), xData);
        assertEquals(series.getYData(), yData);
    }

    @Test
    public void testAddSeriesWithListsAndErrorBars() throws Exception{
        List<String> xData = Arrays.asList("A", "B", "C");
        List<Double> yData = Arrays.asList(1.0, 4.0, 9.0);
        List<Double> errorBars = Arrays.asList(0.1, 0.2, 0.3);
        CategorySeries series = chart.addSeries("Series1", xData, yData, errorBars);
        assertNotNull(series);
        assertEquals(series.getName(), "Series1");
        assertEquals(series.getXData(), xData);
        assertEquals(series.getYData(), yData);
        assertEquals( getPrivateField(series, "extraValues", true), errorBars);


    }

    @Test
    public void testUpdateCategorySeriesWithLists() throws Exception{
        List<String> xData = Arrays.asList("A", "B", "C");
        List<Double> yData = Arrays.asList(1.0, 4.0, 9.0);
        chart.addSeries("Series1", xData, yData);

        List<String> newXData = Arrays.asList("A", "B", "C");
        List<Double> newYData = Arrays.asList(2.0, 8.0, 18.0);
        List<Double> newErrorBars = Arrays.asList(0.2, 0.4, 0.6);
        CategorySeries updatedSeries = chart.updateCategorySeries("Series1", newXData, newYData, newErrorBars);

        assertNotNull(updatedSeries);
        assertEquals(updatedSeries.getXData(), newXData);
        assertEquals(updatedSeries.getYData(), newYData);
        assertEquals( getPrivateField(updatedSeries, "extraValues", true), newErrorBars);

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateCategorySeriesWithNonExistingSeries() {
        List<String> newXData = Arrays.asList("A", "B", "C");
        List<Double> newYData = Arrays.asList(2.0, 8.0, 18.0);
        List<Double> newErrorBars = Arrays.asList(0.2, 0.4, 0.6);
        chart.updateCategorySeries("NonExistingSeries", newXData, newYData, newErrorBars);
    }

    @Test
    public void testUpdateCategorySeriesWithGeneratedXData() throws Exception{
        List<String> xData = Arrays.asList("A", "B", "C");
        List<Double> yData = Arrays.asList(1.0, 4.0, 9.0);
        chart.addSeries("Series1", xData, yData);

        List<Double> newYData = Arrays.asList(2.0, 8.0, 18.0);
        List<Double> newErrorBars = Arrays.asList(0.2, 0.4, 0.6);
        CategorySeries updatedSeries = chart.updateCategorySeries("Series1", null, newYData, newErrorBars);

        assertNotNull(updatedSeries);
        assertEquals(updatedSeries.getXData(), Arrays.asList(1, 2, 3));
        assertEquals(updatedSeries.getYData(), newYData);

        assertEquals( getPrivateField(updatedSeries, "extraValues", true), newErrorBars);


    }

    @Test
    public void testUpdateCategorySeriesWithDoubleArrays() throws Exception{
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {1.0, 4.0, 9.0};
        chart.addSeries("Series1", xData, yData);

        double[] newXData = {2.0, 3.0, 4.0};
        double[] newYData = {2.0, 8.0, 18.0};
        double[] newErrorBars = {0.2, 0.4, 0.6};
        CategorySeries updatedSeries = chart.updateCategorySeries("Series1", newXData, newYData, newErrorBars);

        assertNotNull(updatedSeries);
        assertEquals(updatedSeries.getXData(), Arrays.asList(2.0, 3.0, 4.0));
        assertEquals(updatedSeries.getYData(), Arrays.asList(2.0, 8.0, 18.0));
        assertEquals( getPrivateField(updatedSeries, "extraValues", true), Arrays.asList(0.2, 0.4, 0.6));

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesWithMismatchedSizes() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {1.0, 4.0};
        chart.addSeries("Series1", xData, yData);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesWithNullYData() {
        double[] xData = {1.0, 2.0, 3.0};
        chart.addSeries("Series1", xData, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesWithEmptyYData() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {};
        chart.addSeries("Series1", xData, yData);
    }

    @Test
    public void testGetSeriesMap() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {1.0, 4.0, 9.0};
        chart.addSeries("Series1", xData, yData);
        assertEquals(chart.getSeriesMap().size(), 1);
        assertTrue(chart.getSeriesMap().containsKey("Series1"));
    }



    @Test
    public void testRemoveSeries() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {1.0, 4.0, 9.0};
        chart.addSeries("Series1", xData, yData);
        assertEquals(chart.getSeriesMap().size(), 1);
        chart.removeSeries("Series1");
        assertEquals(chart.getSeriesMap().size(), 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesWithDuplicateName() {
        double[] xData = {1.0, 2.0, 3.0};
        double[] yData = {1.0, 4.0, 9.0};
        chart.addSeries("Series1", xData, yData);
        chart.addSeries("Series1", xData, yData);
    }

    @Test
    public void testSetTitle() {
        chart.setTitle("Test Chart");
        assertEquals(chart.getTitle(), "Test Chart");
    }

    @Test
    public void testSetXAxisTitle() {
        chart.setXAxisTitle("X Axis");
        assertEquals(chart.getXAxisTitle(), "X Axis");
    }

    @Test
    public void testSetYAxisTitle() {
        chart.setYAxisTitle("Y Axis");
        assertEquals(chart.getYAxisTitle(), "Y Axis");
    }

//    @Test
//    public void testSetSeriesStyles() throws Exception{
//        double[] xData = {1.0, 2.0, 3.0};
//        double[] yData = {1.0, 4.0, 9.0};
//        chart.addSeries("Series1", xData, yData);
//        invokePrivateMethod(chart,"setSeriesStyles");
//
//        CategorySeries series = chart.getSeries("Series1");
//        assertNotNull(series.getLineStyle());
//        assertNotNull(series.getLineColor());
//        assertNotNull(series.getFillColor());
//        assertNotNull(series.getMarker());
//        assertNotNull(series.getMarkerColor());
//    }


    @Test
    public void testGetStyler() {
        CategoryStyler styler = chart.getStyler();
        assertNotNull(styler);
    }

    @Test
    public void testSetTheme() {
        Theme theme = new CategoryStyler().getTheme();
        chart.getStyler().setTheme(theme);
        assertEquals(chart.getStyler().getTheme(), theme);
    }

    @Test
    public void testGetAxisPair() throws Exception{
        AxisPair axisPair =  (AxisPair) invokePrivateMethod(false, chart, "getAxisPair");
        assertNotNull(axisPair);
    }

    @Test
    public void testGetPlot() throws Exception {
        Plot_Category plot = (Plot_Category) invokePrivateMethod( false, chart, "getPlot");
        assertNotNull(plot);
    }

    @Test
    public void testGetLegend() throws Exception {
        Legend_Marker legend =  (Legend_Marker) invokePrivateMethod( false, chart, "getLegend");
        assertNotNull(legend);
    }











}
