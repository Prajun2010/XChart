package org.xchart.testng;

import org.knowm.xchart.AnnotationLine;
import org.knowm.xchart.AnnotationText;
import org.knowm.xchart.internal.chartpart.Axis;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.internal.chartpart.PlotSurface_;
import org.knowm.xchart.internal.chartpart.Plot_;
import org.knowm.xchart.internal.series.Series;
import org.knowm.xchart.style.Styler;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

public class AnnotationLineTest {

    private AnnotationLine annotationLine;
    private Axis yAxis;
    private Styler styler;
    private Axis xAxis;
    private Chart chart;



    private final double value = 100.0;
    private final boolean isVertical = true;
    private final boolean isValueInScreenSpace = true;


    private Plot_<Styler, Series> mockPlot;
    private PlotSurface_<Styler, Series> mockPlotSurface;


    @BeforeMethod
    public void setUp() throws  Exception{



        // Mock the Styler and Chart
        styler = mock(Styler.class);
        chart = mock(Chart.class);
        xAxis = mock(Axis.class);
        yAxis = mock(Axis.class);
        mockPlot= mock(Plot_.class);
        mockPlotSurface = mock(PlotSurface_.class);


        when(invokePrivateMethod(chart, "getPlot")).thenReturn(mockPlot);

        try {
            Field plotSurfaceField = Plot_.class.getDeclaredField("plotSurface");
            plotSurfaceField.setAccessible(true);
            plotSurfaceField.set(mockPlot, mockPlotSurface);
        } catch (Exception e) {
            e.printStackTrace(); // Handle or log exception as necessary
        }
//

        when(styler.getAnnotationLineStroke()).thenReturn(new BasicStroke(1.0f));
        when(styler.getAnnotationLineColor()).thenReturn(Color.BLACK);




        // Set up the Chart mock behavior
        when(chart.getHeight()).thenReturn(400);
        when(chart.getStyler()).thenReturn(styler);

        when(invokePrivateMethod(chart, "getXAxis")).thenReturn(xAxis);
        when(invokePrivateMethod(chart, "getYAxis")).thenReturn(xAxis);


        // Set up the Axis mock behavior
        when(xAxis.getScreenValue(anyDouble())).thenReturn(300.00);
        when(yAxis.getScreenValue(anyDouble())).thenReturn(400.00);

        // Initialize the AnnotationLine instance and call init method
        annotationLine = new AnnotationLine(value, isVertical, isValueInScreenSpace);
//        annotationLine.init(chart);
    }

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


    // Initialization Tests
    @Test(priority = 1)
    public void testConstructor() throws Exception {
        Assert.assertEquals((double)getPrivateField(annotationLine,"value"), value, 0.0001);
    }

    @Test(priority = 2)
    public void testConstructorIsVertical() throws NoSuchFieldException, IllegalAccessException {
        Field field = AnnotationLine.class.getDeclaredField("isVertical");
        field.setAccessible(true);
        Assert.assertTrue((boolean) field.get(annotationLine));
    }

    @Test(priority = 3)
    public void testConstructorIsValueInScreenSpace() throws NoSuchFieldException, IllegalAccessException {
        Field field = AnnotationLine.class.getSuperclass().getDeclaredField("isValueInScreenSpace");
        field.setAccessible(true);
        Assert.assertTrue((boolean) field.get(annotationLine));
    }

    // Paint Tests
    @Test(priority = 4)
    public void testPaintVerticalInScreenSpace() {
        annotationLine = new AnnotationLine(value, true, true);
        // Create a real Graphics2D object
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);

        annotationLine.paint(g);


        // Add assertions or verifications specific to vertical line in screen space coordinates
        Assert.assertNotNull(annotationLine.getBounds());

        g.dispose();
    }

    @Test(priority = 5)
    public void testPaintHorizontalInScreenSpace() {
        annotationLine = new AnnotationLine(value, false, true);
        annotationLine.init(chart);
        // Create a real Graphics2D object
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);

        annotationLine.paint(g);

        // Add assertions or verifications specific to horizontal line in screen space coordinates
        Assert.assertNotNull(annotationLine.getBounds());

        g.dispose();
    }

    @Test(priority = 6)
    public void testPaintVerticalInChartSpace() {
        annotationLine = new AnnotationLine(value, true, false);
        annotationLine.init(chart);
        // Create a real Graphics2D object
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);

        annotationLine.paint(g);

        // Add assertions or verifications specific to vertical line in chart space coordinates
        Assert.assertNotNull(annotationLine.getBounds());

        g.dispose();
    }

    @Test(priority = 7)
    public void testPaintHorizontalInChartSpace() {
        annotationLine = new AnnotationLine(value, false, false);
        annotationLine.init(chart);
        // Create a real Graphics2D object
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);

        annotationLine.paint(g);


        // Add assertions or verifications specific to horizontal line in chart space coordinates
        Assert.assertNotNull(annotationLine.getBounds());

        g.dispose();
    }

    public Object getFieldValue(String fieldName, boolean superSearch) throws  NoSuchFieldException, IllegalAccessException{
        Field field =
        (superSearch)?
              AnnotationLine.class.getSuperclass().getDeclaredField(fieldName): AnnotationLine.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(annotationLine);
    }

//    // Visibility Tests
    @Test(priority = 8)
    public void testSetVisibleTrue() throws Exception {
        annotationLine.setVisible(true);
        Assert.assertTrue((boolean)getFieldValue("isVisible", true));
    }

    @Test(priority = 9)
    public void testSetVisibleFalse() throws Exception{
        annotationLine.setVisible(false);
        Assert.assertFalse((boolean)getFieldValue("isVisible", true));
    }

    @Test(priority = 10)
    public void testPaintNotVisible() {
        annotationLine.setVisible(false);
        // Create a real Graphics2D object
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        annotationLine.paint(g);

        // Add assertions or verifications specific to not painting when not visible
        Assert.assertNull(annotationLine.getBounds());

        g.dispose();
    }

    // Set Value Tests
    @Test(priority = 11)
    public void testSetValue() throws Exception {
        annotationLine.setValue(200.0);
        Assert.assertEquals((double)getPrivateField(annotationLine,"value"), 200.0, 0.0001);
    }

    // Stroke Tests
    @Test(priority = 12)
    public void testPaintWithDifferentStroke() {
        when(styler.getAnnotationLineStroke()).thenReturn(new BasicStroke(5.0f));
        annotationLine.init(chart);
        // Create a real Graphics2D object
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);


        annotationLine.paint(g);

        // Add assertions or verifications specific to line stroke
        Assert.assertNotNull(annotationLine.getBounds());

        g.dispose();
    }

    // Color Tests
    @Test(priority = 13)
    public void testPaintWithDifferentColor() {
        when(styler.getAnnotationLineColor()).thenReturn(Color.RED);
        annotationLine.init(chart);
        // Create a real Graphics2D object
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);


        annotationLine.paint(g);

        // Add assertions or verifications specific to line color
        Assert.assertNotNull(annotationLine.getBounds());

        g.dispose();
    }

    // Bounds Tests
    @Test(priority = 14)
    public void testGetBoundsAfterPaint() {
        // Create a real Graphics2D object
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 0, 0);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);


        annotationLine.paint(g);

        Rectangle2D afterBounds = annotationLine.getBounds();
        Assert.assertNotNull(afterBounds);
        Assert.assertTrue(afterBounds.getWidth() > 0);
        Assert.assertTrue(afterBounds.getHeight() > 0);

        g.dispose();
    }

    // Edge Cases
    @Test(priority = 15)
    public void testPaintWithZeroValue() {
        annotationLine.setValue(0.0);
        // Create a real Graphics2D object
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);


        annotationLine.paint(g);

        Assert.assertNotNull(annotationLine.getBounds());

        g.dispose();
    }

    @Test(priority = 16)
    public void testPaintWithNegativeValue() {
        annotationLine.setValue(-100.0);
        // Create a real Graphics2D object
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);


        annotationLine.paint(g);

        Assert.assertNotNull(annotationLine.getBounds());

        g.dispose();
    }

    @Test(priority = 17)
    public void testPaintWithLargeValue() {
        annotationLine.setValue(10000.0);
        // Create a real Graphics2D object
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);


        annotationLine.paint(g);

        Assert.assertNotNull(annotationLine.getBounds());

        g.dispose();
    }

    @Test(priority = 18)
    public void testPaintWithMinimumValue() {
        annotationLine.setValue(Double.MIN_VALUE);
        // Create a real Graphics2D object
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);


        annotationLine.paint(g);

        Assert.assertNotNull(annotationLine.getBounds());

        g.dispose();
    }

    @Test(priority = 19)
    public void testPaintWithMaximumValue() {
        annotationLine.setValue(Double.MAX_VALUE);
        // Create a real Graphics2D object
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);


        annotationLine.paint(g);

        Assert.assertNotNull(annotationLine.getBounds());

        g.dispose();
    }

    // Testing init method behavior


    @Test(priority = 20)
    public void testInitSetsStyler() throws NoSuchFieldException{
        annotationLine.init(chart);
        Assert.assertNotNull(annotationLine.getClass().getSuperclass().getDeclaredField("styler"));
    }

    @Test(priority = 21)
    public void testInitSetsChart() throws NoSuchFieldException{
        annotationLine.init(chart);
        Assert.assertNotNull(annotationLine.getClass().getSuperclass().getDeclaredField("chart"));
    }

//    @Test(priority = 22)
//    public void testInitWithNullChart()throws NoSuchFieldException {
//
//        annotationLine.init(null);
//
//        Assert.assertNull(annotationLine.getClass().getSuperclass().getDeclaredField("chart"));
//
//    }


    // Testing without initialization
    @Test(priority = 23, expectedExceptions = NullPointerException.class)
    public void testPaintWithoutInit() {
        annotationLine = new AnnotationLine(value, isVertical, isValueInScreenSpace);
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        annotationLine.paint(g);
    }

    // Additional tests for various value scenarios
    @Test(priority = 24)
    public void testSetValueZero() throws Exception {
        annotationLine.setValue(0.0);
        Assert.assertEquals((double)getPrivateField(annotationLine, "value"), 0.0, 0.0001);
    }

    @Test(priority = 25)
    public void testSetValueNegative() throws  Exception{
        annotationLine.setValue(-100.0);
        Assert.assertEquals((double)getPrivateField(annotationLine, "value"), -100.0, 0.0001);
    }

    @Test(priority = 26)
    public void testSetValueLarge() throws  Exception{
        annotationLine.setValue(10000.0);
        Assert.assertEquals((double)getPrivateField(annotationLine, "value"), 10000.0, 0.0001);
    }

    @Test(priority = 27)
    public void testSetValueMinimum() throws  Exception{
        annotationLine.setValue(Double.MIN_VALUE);
        Assert.assertEquals((double)getPrivateField(annotationLine, "value"), Double.MIN_VALUE, 0.0001);
    }

    @Test(priority = 28)
    public void testSetValueMaximum() throws  Exception{
        annotationLine.setValue(Double.MAX_VALUE);
        Assert.assertEquals((double)getPrivateField(annotationLine, "value"), Double.MAX_VALUE, 0.0001);
    }

//     Visibility tests with paint method

    @Test(priority = 30)
    public void testPaintVisibleTrue() {
        annotationLine.setVisible(true);
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);

        annotationLine.paint(g);
        Assert.assertNotNull(annotationLine.getBounds());
        g.dispose();
    }

    @Test(priority = 31)
    public void testPaintVisibleFalse() {
        annotationLine.setVisible(false);
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        annotationLine.paint(g);
        Assert.assertNull(annotationLine.getBounds());
        g.dispose();
    }

    @Test(priority = 32)
    public void testPaintWithDifferentStrokeWidth() {
        when(styler.getAnnotationLineStroke()).thenReturn(new BasicStroke(10.0f));
        annotationLine.init(chart);
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);

        annotationLine.paint(g);
        Assert.assertNotNull(annotationLine.getBounds());
        g.dispose();
    }

    @Test(priority = 33)
    public void testPaintWithTransparentColor() {
        when(styler.getAnnotationLineColor()).thenReturn(new Color(0, 0, 0, 0));
        annotationLine.init(chart);
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);

        annotationLine.paint(g);
        Assert.assertNotNull(annotationLine.getBounds());
        g.dispose();
    }

    @Test(priority = 34)
    public void testPaintWithOpaqueColor() {
        when(styler.getAnnotationLineColor()).thenReturn(new Color(0, 0, 0, 255));
        annotationLine.init(chart);
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);

        annotationLine.paint(g);
        Assert.assertNotNull(annotationLine.getBounds());
        g.dispose();
    }

    @Test(priority = 35)
    public void testPaintWithDashedStroke() {
        when(styler.getAnnotationLineStroke()).thenReturn(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{10.0f}, 0.0f));
        annotationLine.init(chart);
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);

        annotationLine.paint(g);
        Assert.assertNotNull(annotationLine.getBounds());
        g.dispose();
    }

    @Test(priority = 36)
    public void testPaintWithDottedStroke() {
        when(styler.getAnnotationLineStroke()).thenReturn(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{1.0f, 10.0f}, 0.0f));
        annotationLine.init(chart);
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);

        annotationLine.paint(g);
        Assert.assertNotNull(annotationLine.getBounds());
        g.dispose();
    }

    @Test(priority = 37)
    public void testPaintWithDashDotStroke() {
        when(styler.getAnnotationLineStroke()).thenReturn(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{10.0f, 1.0f, 1.0f, 10.0f}, 0.0f));
        annotationLine.init(chart);
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);

        annotationLine.paint(g);
        Assert.assertNotNull(annotationLine.getBounds());
        g.dispose();
    }

    @Test(priority = 38)
    public void testPaintWithSolidStroke() {
        when(styler.getAnnotationLineStroke()).thenReturn(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
        annotationLine.init(chart);
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);

        annotationLine.paint(g);
        Assert.assertNotNull(annotationLine.getBounds());
        g.dispose();
    }

    @Test(priority = 39)
    public void testPaintWithLongDashStroke() {
        when(styler.getAnnotationLineStroke()).thenReturn(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{20.0f, 10.0f}, 0.0f));
        annotationLine.init(chart);
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);

        annotationLine.paint(g);
        Assert.assertNotNull(annotationLine.getBounds());
        g.dispose();
    }

    // Test cases continued from previous set...

    // Chart Space Coordinates Tests
    @Test(priority = 40)
    public void testPaintWithChartSpaceCoordinates() {
        annotationLine = new AnnotationLine(value, true, false);
        annotationLine.init(chart);
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);

        annotationLine.paint(g);
        Assert.assertNotNull(annotationLine.getBounds());
        g.dispose();
    }

    @Test(priority = 41)
    public void testPaintWithChartSpaceCoordinatesHorizontal() {
        annotationLine = new AnnotationLine(value, false, false);
        annotationLine.init(chart);
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);

        annotationLine.paint(g);
        Assert.assertNotNull(annotationLine.getBounds());
        g.dispose();
    }

    // Null Axis Tests
    @Test(priority = 42)
    public void testPaintWithNullXAxis() throws Exception{

        annotationLine.init(chart);
        when(invokePrivateMethod(chart, "getXAxis")).thenReturn(null);

        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 0, 0);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);

        annotationLine.paint(g);
        Assert.assertNull(annotationLine.getBounds());
        g.dispose();
    }

    @Test(priority = 43)
    public void testPaintWithNullYAxis() throws  Exception{
        annotationLine.init(chart);
        when(invokePrivateMethod(chart, "getYAxis")).thenReturn(null);

        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);

        annotationLine.paint(g);
        Assert.assertNull(annotationLine.getBounds());
        g.dispose();
    }

    // Edge Case: No Styler Tests
    @Test(priority = 44)
    public void testPaintWithNullStyler() {
        annotationLine.init(chart);
        when(chart.getStyler()).thenReturn(null);
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);


        annotationLine.paint(g);
        Assert.assertNull(annotationLine.getBounds());
        g.dispose();
    }

    // Edge Case: Negative Axis Screen Value Tests
    @Test(priority = 45)
    public void testPaintWithNegativeXAxisScreenValue() {
        when(xAxis.getScreenValue(anyDouble())).thenReturn(-100.00);
        annotationLine.init(chart);
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);

        annotationLine.paint(g);
        Assert.assertNotNull(annotationLine.getBounds());
        g.dispose();
    }

    @Test(priority = 46)
    public void testPaintWithNegativeYAxisScreenValue() {
        when(yAxis.getScreenValue(anyDouble())).thenReturn(-200.00);
        annotationLine.init(chart);
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);

        annotationLine.paint(g);
        Assert.assertNotNull(annotationLine.getBounds());
        g.dispose();
    }

    // Edge Case: Zero Axis Screen Value Tests
    @Test(priority = 47)
    public void testPaintWithZeroXAxisScreenValue() {
        when(xAxis.getScreenValue(anyDouble())).thenReturn(0.00);
        annotationLine.init(chart);
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);

        annotationLine.paint(g);
        Assert.assertNotNull(annotationLine.getBounds());
        g.dispose();
    }

    @Test(priority = 48)
    public void testPaintWithZeroYAxisScreenValue() {
        when(yAxis.getScreenValue(anyDouble())).thenReturn(0.00);
        annotationLine.init(chart);
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        // Mock behavior for getBounds()
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, 100, 100);
        when(mockPlotSurface.getBounds()).thenReturn(bounds);
        annotationLine.init(chart);

        annotationLine.paint(g);
        Assert.assertNotNull(annotationLine.getBounds());
        g.dispose();
    }
}
