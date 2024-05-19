package org.xchart.testng;
import org.knowm.xchart.PieSeries;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.colors.ChartColor;
import org.knowm.xchart.style.theme.Theme;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class PieChartTest {

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
    public void testConstructor_DefaultWidth() {
        PieChart chart = new PieChart(800, 600);
        Assert.assertEquals(chart.getWidth(), 800);
    }

    @Test
    public void testConstructor_DefaultHeight() {
        PieChart chart = new PieChart(800, 600);
        Assert.assertEquals(chart.getHeight(), 600);
    }

    @Test
    public void testConstructor_DefaultThemeNotNull() {
        PieChart chart = new PieChart(800, 600);
        Assert.assertNotNull(chart.getStyler().getTheme());
    }

    @Test
    public void testConstructor_CustomThemeNotNull() {
        Theme customTheme = Styler.ChartTheme.GGPlot2.newInstance(Styler.ChartTheme.GGPlot2);
        PieChart chart = new PieChart(800, 600, customTheme);
        Assert.assertNotNull(chart.getStyler().getTheme());
    }

    @Test
    public void testConstructor_CustomThemeEquals() {
        Theme customTheme = Styler.ChartTheme.GGPlot2.newInstance(Styler.ChartTheme.GGPlot2);
        PieChart chart = new PieChart(800, 600, customTheme);
        Assert.assertEquals(chart.getStyler().getTheme(), customTheme);
    }

    @Test
    public void testConstructor_ChartThemeNotNull() {
        PieChart chart = new PieChart(800, 600, Styler.ChartTheme.Matlab);
        Assert.assertNotNull(chart.getStyler().getTheme());
    }

    @Test
    public void testConstructor_ChartThemeEquals() {
        PieChart chart = new PieChart(800, 600, Styler.ChartTheme.Matlab);
        Assert.assertEquals(chart.getStyler().getTheme().getClass(), Styler.ChartTheme.Matlab.newInstance(Styler.ChartTheme.Matlab).getClass());
    }

    @Test
    public void testConstructor_PieChartBuilderNotNull() {
        PieChartBuilder builder = new PieChartBuilder().width(800).height(600).theme(Styler.ChartTheme.Matlab);
        PieChart chart = new PieChart(builder);
        Assert.assertNotNull(chart);
    }

    @Test
    public void testConstructor_PieChartBuilderTitle() {
        PieChartBuilder builder = new PieChartBuilder().title("Test Chart");
        PieChart chart = new PieChart(builder);
        Assert.assertEquals(chart.getTitle(), "Test Chart");
    }

    @Test
    public void testAddSeries_NewSeries() {
        PieChart chart = new PieChart(800, 600);
        PieSeries series = chart.addSeries("New Series", 15);
        Assert.assertNotNull(series);
    }

    @Test
    public void testAddSeries_SeriesCount() {
        PieChart chart = new PieChart(800, 600);
        chart.addSeries("Series1", 10);
        chart.addSeries("Series2", 20);
        Assert.assertEquals(chart.getSeriesMap().size(), 2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeries_NullName() {
        PieChart chart = new PieChart(800, 600);
        chart.addSeries(null, 10); // Should throw an exception
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddSeries_NegativeValue() {
        PieChart chart = new PieChart(800, 600);
        chart.addSeries("Negative Series", -5); // Should throw an exception
    }

    @Test
    public void testUpdatePieSeries_UpdatedValue() {
        PieChart chart = new PieChart(800, 600);
        chart.addSeries("Series1", 10);
        PieSeries updatedSeries = chart.updatePieSeries("Series1", 25);
        Assert.assertEquals(updatedSeries.getValue().intValue(), 25);
    }

    @Test
    public void testUpdatePieSeries_SeriesNameEquals() {
        PieChart chart = new PieChart(800, 600);
        chart.addSeries("Series1", 10);
        PieSeries updatedSeries = chart.updatePieSeries("Series1", 25);
        Assert.assertEquals(updatedSeries.getName(), "Series1");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdatePieSeries_ZeroValue() {
        PieChart chart = new PieChart(800, 600);
        chart.addSeries("Series1", 10);
        chart.updatePieSeries("Series1", 0); // Should throw an exception
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdatePieSeries_NegativeValue() {
        PieChart chart = new PieChart(800, 600);
        chart.addSeries("Series1", 10);
        chart.updatePieSeries("Series1", -5); // Should throw an exception
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdatePieSeries_NullName() {
        PieChart chart = new PieChart(800, 600);
        chart.updatePieSeries(null, 20); // Should throw an exception
    }

    @Test
    public void testPaint_NotNull() {
        PieChart chart = new PieChart(800, 600);
        chart.addSeries("Series1", 10);
        chart.paint(null, 800, 600);
        Assert.assertNotNull(chart);
    }

    @Test
    public void testSetSeriesStyles_NotNull() throws Exception{
        PieChart chart = new PieChart(800, 600);
        chart.addSeries("Series1", 10);
        invokePrivateMethod(chart,"setSeriesStyles");

        Assert.assertNotNull(chart.getSeriesMap().get("Series1").getFillColor());
    }

    @Test
    public void testSetSeriesStyles_DefaultFillColor() throws Exception{
        PieChart chart = new PieChart(800, 600);
        chart.addSeries("Series1", 10);
        invokePrivateMethod(chart,"setSeriesStyles");
        Assert.assertEquals(chart.getSeriesMap().get("Series1").getFillColor(), ChartColor.BLUE);
    }

    @Test
    public void testSetSeriesStyles_CustomFillColor() throws Exception{
        PieChart chart = new PieChart(800, 600);
        chart.addSeries("Series1", 10);
        chart.getStyler().setDefaultSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);
        invokePrivateMethod(chart,"setSeriesStyles");
        Assert.assertEquals(chart.getSeriesMap().get("Series1").getFillColor(), ChartColor.GREEN);
    }

    @Test
    public void testSetSeriesStyles_DefaultRenderStyle() throws Exception{
        PieChart chart = new PieChart(800, 600);
        chart.addSeries("Series1", 10);
        invokePrivateMethod(chart,"setSeriesStyles");

        Assert.assertEquals(chart.getStyler().getDefaultSeriesRenderStyle(), PieSeries.PieSeriesRenderStyle.Pie);
    }

    @Test
    public void testSetSeriesStyles_CustomRenderStyle() throws Exception{
        PieChart chart = new PieChart(800, 600);
        chart.addSeries("Series1", 10);
        chart.getStyler().setDefaultSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);
        invokePrivateMethod(chart,"setSeriesStyles");

        Assert.assertEquals(chart.getStyler().getDefaultSeriesRenderStyle(), PieSeries.PieSeriesRenderStyle.Donut);
    }

    @Test
    public void testSetSeriesStyles_DefaultLegendPosition() throws Exception{
        PieChart chart = new PieChart(800, 600);
        chart.addSeries("Series1", 10);
        invokePrivateMethod(chart,"setSeriesStyles");

        Assert.assertEquals(chart.getStyler().getLegendPosition(), Styler.LegendPosition.InsideNW);
    }

    @Test
    public void testSetSeriesStyles_CustomLegendPosition() throws Exception{
        PieChart chart = new PieChart(800, 600);
        chart.addSeries("Series1", 10);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
        invokePrivateMethod(chart,"setSeriesStyles");

        Assert.assertEquals(chart.getStyler().getLegendPosition(), Styler.LegendPosition.OutsideS);
    }
}
