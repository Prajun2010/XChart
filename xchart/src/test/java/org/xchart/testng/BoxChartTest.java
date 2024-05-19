package org.xchart.testng;

import org.knowm.xchart.BoxChart;
import org.knowm.xchart.BoxChartBuilder;
import org.knowm.xchart.BoxSeries;
import org.knowm.xchart.style.BoxStyler;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.theme.Theme;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.swing.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BoxChartTest {

    private BoxChart boxChart;

    @BeforeMethod
    public void setUp() {
        boxChart = new BoxChart(800, 600, ChartTheme.Matlab);
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

    @Test(priority = 1, groups = "constructor")
    public void testBoxChartConstructorWidthHeight() {
        BoxChart chart = new BoxChart(400, 300, ChartTheme.GGPlot2);
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getWidth(), 400);
        Assert.assertEquals(chart.getHeight(), 300);
    }

    @Test(priority = 2, groups = "constructor")
    public void testBoxChartConstructorWithTheme() {
        Theme theme = ChartTheme.GGPlot2.newInstance(ChartTheme.GGPlot2);
        BoxChart chart = new BoxChart(400, 300, theme);
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getWidth(), 400);
        Assert.assertEquals(chart.getHeight(), 300);
    }


    @Test(priority = 3, groups = "series")
    public void testAddSeriesWithIntArray() {
        BoxSeries series = boxChart.addSeries("Series1", new int[]{1, 2, 3});
        Assert.assertNotNull(series);
        int[] intArray = series.getYData().stream().mapToInt(Number::intValue).toArray();

// Convert the integer array to a list
        List<Integer> actualList = Arrays.stream(intArray).boxed().collect(Collectors.toList());

// Compare the actual list with the expected list

        Assert.assertEquals(actualList, Arrays.asList(1, 2, 3));
    }

    @Test(priority = 4, groups = "series")
    public void testAddSeriesWithDoubleArray() {
        BoxSeries series = boxChart.addSeries("Series2", new double[]{1.0, 2.0, 3.0});
        Assert.assertNotNull(series);
        Assert.assertEquals(series.getYData(), Arrays.asList(1.0, 2.0, 3.0));
    }

    @Test(priority = 5, groups = "series")
    public void testAddSeriesWithList() {
        List<Number> yData = Arrays.asList(1, 2, 3);
        BoxSeries series = boxChart.addSeries("Series3", yData);
        Assert.assertNotNull(series);
        Assert.assertEquals(series.getYData(), yData);
    }

    @Test(priority = 6, groups = "series")
    public void testUpdateSeriesWithIntArray() {
        boxChart.addSeries("Series4", new int[]{1, 2, 3});
        BoxSeries updatedSeries = boxChart.updateBoxSeries("Series4", new int[]{4, 5, 6});
        Assert.assertNotNull(updatedSeries);
        int[] intArray = updatedSeries.getYData().stream().mapToInt(Number::intValue).toArray();

// Convert the integer array to a list
        List<Integer> actualList = Arrays.stream(intArray).boxed().collect(Collectors.toList());

        Assert.assertEquals(actualList, Arrays.asList(4, 5, 6));
    }

    @Test(priority = 7, groups = "series")
    public void testUpdateSeriesWithDoubleArray() {
        boxChart.addSeries("Series5", new double[]{1.0, 2.0, 3.0});
        BoxSeries updatedSeries = boxChart.updateBoxSeries("Series5", new double[]{4.0, 5.0, 6.0});
        Assert.assertNotNull(updatedSeries);
        Assert.assertEquals(updatedSeries.getYData(), Arrays.asList(4.0, 5.0, 6.0));
    }

    @Test(priority = 8, groups = "series")
    public void testUpdateSeriesWithList() {
        boxChart.addSeries("Series6", new int[]{1, 2, 3});
        List<Number> newYData = Arrays.asList(4.0, 5.0, 6.0);
        BoxSeries updatedSeries = boxChart.updateBoxSeries("Series6", newYData);
        Assert.assertNotNull(updatedSeries);
        Assert.assertEquals(updatedSeries.getYData(), newYData);
    }

    @DataProvider(name = "seriesDataProvider")
    public Object[][] seriesDataProvider() {
        return new Object[][]{
                {"Series7", new int[]{1, 2, 3}},
                {"Series8", new double[]{1.0, 2.0, 3.0}},
                {"Series9", Arrays.asList(1, 2, 3)}
        };
    }

    @Test(priority = 9, groups = "series", dataProvider = "seriesDataProvider")
    public void testAddSeriesWithDataProvider(String seriesName, Object yData) {
        BoxSeries series = null;
        if (yData instanceof int[]) {
            series = boxChart.addSeries(seriesName, (int[]) yData);
        } else if (yData instanceof double[]) {
            series = boxChart.addSeries(seriesName, (double[]) yData);
        } else if (yData instanceof List) {
            series = boxChart.addSeries(seriesName, (List<? extends Number>) yData);
        }
        Assert.assertNotNull(series);
        Assert.assertEquals(series.getName(), seriesName);
    }

    @Test(priority = 10, groups = "series")
    public void testUpdateSeriesWithDataProvider() {
        boxChart.addSeries("Series20", new int[]{1, 2, 3});
        List<Number> newYData = Arrays.asList(4.0, 5.0, 6.0);
        BoxSeries updatedSeries = boxChart.updateBoxSeries("Series20", newYData);
        Assert.assertNotNull(updatedSeries);
        Assert.assertEquals(updatedSeries.getYData(), newYData);
    }

    @DataProvider(name = "invalidSeriesDataProvider")
    public Object[][] invalidSeriesDataProvider() {
        return new Object[][]{
                {"Series21", null},
                {"Series22", Collections.emptyList()},
                {"Series23", Arrays.asList(1.0, null, 3.0)},
        };
    }

    @Test(priority = 11, groups = "errorHandling", dataProvider = "invalidSeriesDataProvider", expectedExceptions = IllegalArgumentException.class)
    public void testAddSeriesWithInvalidDataProvider(String seriesName, List<Number> yData) {
        boxChart.addSeries(seriesName, yData);
    }

    @Test(priority = 12, groups = "series")
    public void testMultipleSeriesAddition() {
        for (int i = 0; i < 10; i++) {
            boxChart.addSeries("Series" + i, new int[]{i, i + 1, i + 2});
        }
        Assert.assertEquals(boxChart.getSeriesMap().size(), 10);
    }

    @Test(priority = 14, groups = "series")
    public void testMultipleSeriesUpdate() {
        for (int i = 0; i < 10; i++) {
            boxChart.addSeries("Series" + i, new int[]{i, i + 1, i + 2});
        }
        for (int i = 0; i < 10; i++) {
            boxChart.updateBoxSeries("Series" + i, new int[]{i + 3, i + 4, i + 5});
        }
        for (int i = 0; i < 10; i++) {
            BoxSeries series = boxChart.getSeriesMap().get("Series" + i);
            int[] intArray = series.getYData().stream().mapToInt(Number::intValue).toArray();

// Convert the integer array to a list
            List<Integer> actualList = Arrays.stream(intArray).boxed().collect(Collectors.toList());

            Assert.assertEquals(actualList, Arrays.asList(i + 3, i + 4, i + 5));
        }
    }

    @Test(priority = 13, groups = "styler")
    public void testBoxChartStyler() {
        BoxStyler styler = boxChart.getStyler();
        Assert.assertNotNull(styler);
    }

    @Test(priority = 14, groups = "styler")
    public void testBoxChartThemes() {
        for (ChartTheme theme : ChartTheme.values()) {
            BoxChart chart = new BoxChart(800, 600, theme);
            Assert.assertNotNull(chart);
        }
    }


    @Test(priority = 15, groups = "errorHandling")
    public void testSanityCheckWithValidData() {
        List<Number> yData = Arrays.asList(1, 2, 3);
        boxChart.addSeries("Series10", yData);
        // No exception should be thrown
    }

    @Test(priority = 16, groups = "errorHandling", expectedExceptions = IllegalArgumentException.class)
    public void testSanityCheckWithDuplicateSeriesName() {
        boxChart.addSeries("Series11", Arrays.asList(1, 2, 3));
        boxChart.addSeries("Series11", Arrays.asList(4, 5, 6)); // Should throw exception
    }

    @Test(priority = 17, groups = "errorHandling", expectedExceptions = IllegalArgumentException.class)
    public void testSanityCheckWithNullData() {
        boxChart.addSeries("Series12", (List<Number>) null); // Should throw exception
    }

    @Test(priority = 18, groups = "errorHandling", expectedExceptions = IllegalArgumentException.class)
    public void testSanityCheckWithEmptyData() {
        boxChart.addSeries("Series13", Collections.emptyList()); // Should throw exception
    }

    @Test(priority = 19, groups = "errorHandling", expectedExceptions = IllegalArgumentException.class)
    public void testSanityCheckWithNullValuesInData() {
        boxChart.addSeries("Series14", Arrays.asList(1, null, 3)); // Should throw exception
    }

    @Test(priority = 23, groups = "errorHandling", expectedExceptions = IllegalArgumentException.class)
    public void testUpdateNonExistentSeries() {
        boxChart.updateBoxSeries("NonExistentSeries", Arrays.asList(1, 2, 3)); // Should throw exception
    }

    @Test(priority = 20, groups = "styler")
    public void testSetSeriesStyles() throws  Exception{
        boxChart.addSeries("Series15", new int[]{1, 2, 3});
        boxChart.addSeries("Series16", new int[]{4, 5, 6});
        boxChart.addSeries("Series17", new int[]{7, 8, 9});

        invokePrivateMethod(boxChart,"setSeriesStyles");

        for (BoxSeries series : boxChart.getSeriesMap().values()) {
            Assert.assertNotNull(series.getLineStyle());
            Assert.assertNotNull(series.getLineColor());
            Assert.assertNotNull(series.getFillColor());
            Assert.assertNotNull(series.getMarker());
            Assert.assertNotNull(series.getMarkerColor());
        }
    }

    @Test(priority = 21, groups = "styler")
    public void testPaint() {
        boxChart.addSeries("Series18", new int[]{1, 2, 3});
        boxChart.addSeries("Series19", new int[]{4, 5, 6});
        boxChart.paint(null, 800, 600);
        // No exception should be thrown during painting
    }

    @Test(priority = 22, groups = "series")
    public void testAddSeriesWithNegativeValues() {
        BoxSeries series = boxChart.addSeries("Series24", new int[]{-1, -2, -3});
        Assert.assertNotNull(series);
        int[] intArray = series.getYData().stream().mapToInt(Number::intValue).toArray();

// Convert the integer array to a list
        List<Integer> actualList = Arrays.stream(intArray).boxed().collect(Collectors.toList());

        Assert.assertEquals(actualList, Arrays.asList(-1, -2, -3));
    }

    @Test(priority = 23, groups = "series")
    public void testUpdateSeriesWithNegativeValues() {
        boxChart.addSeries("Series25", new int[]{1, 2, 3});
        BoxSeries updatedSeries = boxChart.updateBoxSeries("Series25", new int[]{-4, -5, -6});
        Assert.assertNotNull(updatedSeries);

        int[] intArray = updatedSeries.getYData().stream().mapToInt(Number::intValue).toArray();

// Convert the integer array to a list
        List<Integer> actualList = Arrays.stream(intArray).boxed().collect(Collectors.toList());

        Assert.assertEquals(actualList, Arrays.asList(-4, -5, -6));
    }

    @Test(priority = 24, groups = "styler")
    public void testBoxChartStylerWithCustomSettings() {
        boxChart.getStyler().setLegendVisible(true);
        Assert.assertTrue(boxChart.getStyler().isLegendVisible());
    }


    @Test(priority = 25, groups = "series")
    public void testAddMultipleSeriesWithDifferentDataTypes() {
        BoxSeries series1 = boxChart.addSeries("Series26", new int[]{1, 2, 3});
        BoxSeries series2 = boxChart.addSeries("Series27", new double[]{1.0, 2.0, 3.0});
        BoxSeries series3 = boxChart.addSeries("Series28", Arrays.asList(1, 2, 3));
        Assert.assertNotNull(series1);
        Assert.assertNotNull(series2);
        Assert.assertNotNull(series3);
    }

    @Test(priority = 26, groups = "styler")
    public void testBoxChartWithDifferentThemes() {
        for (ChartTheme theme : ChartTheme.values()) {
            BoxChart chart = new BoxChart(800, 600, theme);
            Assert.assertNotNull(chart);
        }
    }

    @Test(priority = 27, groups = "series")
    public void testSeriesDataWithLargeValues() {
        BoxSeries series = boxChart.addSeries("Series29", new int[]{1000, 2000, 3000});
        Assert.assertNotNull(series);
        int[] intArray = series.getYData().stream().mapToInt(Number::intValue).toArray();

// Convert the integer array to a list
        List<Integer> actualList = Arrays.stream(intArray).boxed().collect(Collectors.toList());

        Assert.assertEquals(actualList, Arrays.asList(1000, 2000, 3000));
    }



    @Test(priority = 28, groups = "styler")
    public void testBoxChartStylerWithDifferentColors() {
        boxChart.addSeries("Series30", new int[]{1, 2, 3});
        BoxSeries series = boxChart.getSeriesMap().get("Series30");
        series.setLineColor(java.awt.Color.RED);
        Assert.assertEquals(series.getLineColor(), java.awt.Color.RED);
    }

    @Test(priority = 29, groups = "constructor")
    public void testBoxChartWithDifferentSizes() {
        BoxChart chart1 = new BoxChart(400, 300, ChartTheme.Matlab);
        BoxChart chart2 = new BoxChart(1200, 800, ChartTheme.GGPlot2);
        Assert.assertEquals(chart1.getWidth(), 400);
        Assert.assertEquals(chart2.getWidth(), 1200);
    }

    @Test(priority = 30, groups = "styler")
    public void testBoxChartStylerWithVisibilitySettings() {
        boxChart.getStyler().setXAxisTicksVisible(false);
        boxChart.getStyler().setYAxisTicksVisible(false);
        Assert.assertFalse(boxChart.getStyler().isXAxisTicksVisible());
        Assert.assertFalse(boxChart.getStyler().isYAxisTicksVisible());
    }







    @Test(priority = 31, groups = "constructor")
    public void testBoxChartWithDifferentTitle() {
        boxChart.setTitle("New Title");
        Assert.assertEquals(boxChart.getTitle(), "New Title");
    }

    @Test(priority = 32, groups = "constructor")
    public void testBoxChartWithDifferentXAxisTitle() {
        boxChart.setXAxisTitle("New X Axis");
        Assert.assertEquals(boxChart.getXAxisTitle(), "New X Axis");
    }

    @Test(priority = 33, groups = "constructor")
    public void testBoxChartWithDifferentYAxisTitle() {
        boxChart.setYAxisTitle("New Y Axis");
        Assert.assertEquals(boxChart.getYAxisTitle(), "New Y Axis");
    }

    @Test(priority = 34, groups = "series")
    public void testSeriesWithMixedPositiveAndNegativeValues() {
        BoxSeries series = boxChart.addSeries("Series31", new int[]{-1, 2, -3, 4});
        Assert.assertNotNull(series);
        int[] intArray = series.getYData().stream().mapToInt(Number::intValue).toArray();

// Convert the integer array to a list
        List<Integer> actualList = Arrays.stream(intArray).boxed().collect(Collectors.toList());

        Assert.assertEquals(actualList, Arrays.asList(-1, 2, -3, 4));
    }



    @Test(priority = 35, groups = "styler")
    public void testBoxChartStylerWithDifferentThemes() {
        for (ChartTheme theme : ChartTheme.values()) {
            BoxChart chart = new BoxChart(800, 600, theme);
            Assert.assertNotNull(chart);
        }
    }

    @Test(priority = 36, groups = "series")
    public void testAddSeriesWithNullName() {
        try {
            boxChart.addSeries(null, new int[]{1, 2, 3});
            Assert.fail("Expected IllegalArgumentException for null series name");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "Series name > null < has already been used. Use unique names for each series!!!");
        }
    }

    @Test(priority = 37, groups = "series")
    public void testAddSeriesWithEmptyName() {
        try {
            boxChart.addSeries("", new int[]{1, 2, 3});
            Assert.fail("Expected IllegalArgumentException for empty series name");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "Series name >  < has already been used. Use unique names for each series!!!");
        }
    }

    @Test(priority = 38, groups = "series")
    public void testUpdateSeriesWithNullName() {
        try {
            boxChart.updateBoxSeries(null, new int[]{1, 2, 3});
            Assert.fail("Expected IllegalArgumentException for null series name");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "Series name > null < not found !!!");
        }
    }

    @Test(priority = 39, groups = "series")
    public void testUpdateSeriesWithEmptyName() {
        try {
            boxChart.updateBoxSeries("", new int[]{1, 2, 3});
            Assert.fail("Expected IllegalArgumentException for empty series name");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "Series name >  < not found !!!");
        }
    }

    @Test(priority = 40, groups = "series")
    public void testSeriesWithSingleValue() {
        BoxSeries series = boxChart.addSeries("Series32", new int[]{1});
        Assert.assertNotNull(series);
        int[] list = series.getYData().stream().mapToInt(Number::intValue).toArray();
        // Convert the integer array to a list
        List<Integer> actualList = Arrays.stream(list).boxed().collect(Collectors.toList());

// Compare the actual list with the expected list
        Assert.assertEquals(actualList, Arrays.asList(1));
    }

    @Test(priority = 41, groups = "series")
    public void testSeriesWithLargeNumberOfValues() {
        int[] largeDataSet = new int[1000];
        for (int i = 0; i < largeDataSet.length; i++) {
            largeDataSet[i] = i;
        }
        BoxSeries series = boxChart.addSeries("Series33", largeDataSet);
        Assert.assertNotNull(series);
        Assert.assertEquals(series.getYData().size(), 1000);
    }

    @Test(priority = 42, groups = "series")
    public void testSeriesWithHighPrecisionValues() {
        BoxSeries series = boxChart.addSeries("Series34", new double[]{1.123456789, 2.987654321, 3.456789123});
        Assert.assertNotNull(series);
        Assert.assertEquals(series.getYData(), Arrays.asList(1.123456789, 2.987654321, 3.456789123));
    }

    @Test(priority = 43, groups = "series")
    public void testUpdateSeriesWithHighPrecisionValues() {
        boxChart.addSeries("Series35", new double[]{1.0, 2.0, 3.0});
        BoxSeries updatedSeries = boxChart.updateBoxSeries("Series35", new double[]{4.123456789, 5.987654321, 6.456789123});
        Assert.assertNotNull(updatedSeries);
        Assert.assertEquals(updatedSeries.getYData(), Arrays.asList(4.123456789, 5.987654321, 6.456789123));
    }

    @Test(priority = 44, groups = "series")
    public void testUpdateSeriesWithEmptyData() {
        boxChart.addSeries("Series36", new int[]{1, 2, 3});
        try {
            boxChart.updateBoxSeries("Series36", Collections.emptyList());
            Assert.fail("Expected IllegalArgumentException for empty Y-Axis data");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "Y-Axis data connot be empyt !!!");
        }
    }
}
