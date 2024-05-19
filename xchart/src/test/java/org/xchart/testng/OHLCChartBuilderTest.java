package org.xchart.testng;

import org.knowm.xchart.OHLCChart;
import org.knowm.xchart.OHLCChartBuilder;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.testng.Assert.*;

public class OHLCChartBuilderTest {

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



    @Test
    public void testDefaultConstructor() {
        OHLCChartBuilder builder = new OHLCChartBuilder();
        assertNotNull(builder);
    }

    @Test
    public void testXAxisTitle() throws Exception{
        OHLCChartBuilder builder = new OHLCChartBuilder();
        builder.xAxisTitle("X Axis");
        assertEquals(getPrivateField(builder,"xAxisTitle", false), "X Axis");
    }

    @Test
    public void testYAxisTitle() throws Exception{
        OHLCChartBuilder builder = new OHLCChartBuilder();
        builder.yAxisTitle("Y Axis");
        assertEquals(getPrivateField(builder,"yAxisTitle", false), "Y Axis");

    }

    @Test
    public void testBuild() {
        OHLCChartBuilder builder = new OHLCChartBuilder();
        OHLCChart chart = builder.build();
        assertNotNull(chart);
    }

    @Test
    public void testChainedMethods() throws Exception{
        OHLCChartBuilder builder = new OHLCChartBuilder();
        builder.xAxisTitle("X Axis").yAxisTitle("Y Axis");
        assertEquals(getPrivateField(builder,"xAxisTitle", false), "X Axis");
        assertEquals(getPrivateField(builder,"yAxisTitle", false), "Y Axis");
    }

    @Test
    public void testXAxisTitleEmpty() throws Exception{
        OHLCChartBuilder builder = new OHLCChartBuilder();
        builder.xAxisTitle("");
        assertEquals(getPrivateField(builder,"xAxisTitle", false), "");

    }

    @Test
    public void testYAxisTitleEmpty() throws Exception{
        OHLCChartBuilder builder = new OHLCChartBuilder();
        builder.yAxisTitle("");
        assertEquals(getPrivateField(builder,"yAxisTitle", false), "");

    }

    @Test
    public void testXAxisTitleNull() throws Exception{
        OHLCChartBuilder builder = new OHLCChartBuilder();
        builder.xAxisTitle(null);
        assertNull(getPrivateField(builder,"xAxisTitle", false));

    }

    @Test
    public void testYAxisTitleNull() throws Exception{
        OHLCChartBuilder builder = new OHLCChartBuilder();
        builder.yAxisTitle(null);
        assertNull(getPrivateField(builder,"yAxisTitle", false));

    }

    @Test
    public void testXAxisTitleLong() throws Exception{
        OHLCChartBuilder builder = new OHLCChartBuilder();
        String longTitle = "This is a very long title for the X axis";
        builder.xAxisTitle(longTitle);
        assertNull(getPrivateField(builder,"xAxisTitle", false), longTitle);

    }

    @Test
    public void testYAxisTitleLong() throws Exception{
        OHLCChartBuilder builder = new OHLCChartBuilder();
        String longTitle = "This is a very long title for the Y axis";
        builder.yAxisTitle(longTitle);
        assertEquals(getPrivateField(builder,"yAxisTitle", false), longTitle);
    }

    @Test
    public void testBuildWithBothTitles() {
        OHLCChartBuilder builder = new OHLCChartBuilder();
        builder.xAxisTitle("X Axis").yAxisTitle("Y Axis");
        OHLCChart chart = builder.build();
        assertNotNull(chart);
        assertEquals(chart.getXAxisTitle(), "X Axis");
        assertEquals(chart.getYAxisTitle(), "Y Axis");
    }

    @Test
    public void testBuildWithNullXAxisTitle() {
        OHLCChartBuilder builder = new OHLCChartBuilder();
        builder.yAxisTitle("Y Axis");
        OHLCChart chart = builder.build();
        assertNotNull(chart);
        assertEquals(chart.getXAxisTitle(), "");
        assertEquals(chart.getYAxisTitle(), "Y Axis");
    }

    @Test
    public void testBuildWithNullYAxisTitle() {
        OHLCChartBuilder builder = new OHLCChartBuilder();
        builder.xAxisTitle("X Axis");
        OHLCChart chart = builder.build();
        assertNotNull(chart);
        assertEquals(chart.getXAxisTitle(), "X Axis");
        assertEquals(chart.getYAxisTitle(), "");
    }

    @Test
    public void testBuildWithNullTitles() {
        OHLCChartBuilder builder = new OHLCChartBuilder();
        OHLCChart chart = builder.build();
        assertNotNull(chart);
        assertEquals(chart.getXAxisTitle(), "");
        assertEquals(chart.getYAxisTitle(), "");
    }

    @Test
    public void testBuildMultipleCharts() {
        OHLCChartBuilder builder1 = new OHLCChartBuilder();
        OHLCChartBuilder builder2 = new OHLCChartBuilder();

        builder1.xAxisTitle("Chart 1 X").yAxisTitle("Chart 1 Y");
        builder2.xAxisTitle("Chart 2 X").yAxisTitle("Chart 2 Y");

        OHLCChart chart1 = builder1.build();
        OHLCChart chart2 = builder2.build();

        assertNotNull(chart1);
        assertNotNull(chart2);

        assertEquals(chart1.getXAxisTitle(), "Chart 1 X");
        assertEquals(chart1.getYAxisTitle(), "Chart 1 Y");

        assertEquals(chart2.getXAxisTitle(), "Chart 2 X");
        assertEquals(chart2.getYAxisTitle(), "Chart 2 Y");
    }

    @Test
    public void testBuildWithSpecialCharacters() {
        OHLCChartBuilder builder = new OHLCChartBuilder();
        String specialTitle = "!@#$%^&*()";
        builder.xAxisTitle(specialTitle).yAxisTitle(specialTitle);
        OHLCChart chart = builder.build();
        assertNotNull(chart);
        assertEquals(chart.getXAxisTitle(), specialTitle);
        assertEquals(chart.getYAxisTitle(), specialTitle);
    }


}
