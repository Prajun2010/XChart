package org.xchart.testng;

import org.knowm.xchart.AnnotationText;
import org.knowm.xchart.internal.chartpart.Axis;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.Styler;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

public class AnnotationTextTest {

    private AnnotationText annotationText;
    private final String text = "Test Text";
    private final double x = 100.0;
    private final double y = 200.0;
    private final boolean isValueInScreenSpace = true;
    private Styler styler;
    private Chart chart;
    private Axis xAxis;
    private Axis yAxis;

    @BeforeMethod
    public void setUp() throws Exception{
        chart = mock(Chart.class);
        styler = mock(Styler.class);
        xAxis = mock(Axis.class);
        yAxis = mock(Axis.class);

        when(styler.getAnnotationTextFontColor()).thenReturn(Color.BLACK);
        when(styler.getAnnotationTextFont()).thenReturn(new Font("Arial", Font.PLAIN, 12));

        // Set up the Chart mock behavior
        when(chart.getHeight()).thenReturn(400);
        when(chart.getStyler()).thenReturn(styler);
        when(invokePrivateMethod(chart, "getXAxis")).thenReturn(xAxis);
        when(invokePrivateMethod(chart, "getYAxis")).thenReturn(xAxis);
//        when(chart.getYAxis()).thenReturn(yAxis);

        // Set up the Axis mock behavior
        when(xAxis.getScreenValue(anyDouble())).thenReturn(300.00);
        when(yAxis.getScreenValue(anyDouble())).thenReturn(400.00);

        annotationText = new AnnotationText(text, x, y, isValueInScreenSpace);

    }

    private double getYField(AnnotationText annotationText) throws NoSuchFieldException, IllegalAccessException {
        Field field = AnnotationText.class.getDeclaredField("y");
        field.setAccessible(true);
        return (double) field.get(annotationText);
    }

    private String getTextField(AnnotationText annotationText) throws NoSuchFieldException, IllegalAccessException {
        Field field = AnnotationText.class.getDeclaredField("text");
        field.setAccessible(true);
        return (String) field.get(annotationText);
    }

    private double getXField(AnnotationText annotationText) throws NoSuchFieldException, IllegalAccessException {
        Field field = AnnotationText.class.getDeclaredField("x");
        field.setAccessible(true);
        return (double) field.get(annotationText);
    }



    private boolean getIsValueInScreenSpace(AnnotationText annotationText) throws NoSuchFieldException, IllegalAccessException {
        Field field = AnnotationText.class.getSuperclass().getDeclaredField("isValueInScreenSpace");
        field.setAccessible(true);
        return (boolean) field.get(annotationText);
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


    @Test(priority = 1)
    public void testConstructor() throws NoSuchFieldException, IllegalAccessException {
        Assert.assertEquals(getTextField(annotationText), text);
        Assert.assertEquals(getXField(annotationText), x, 0.0001);
        Assert.assertEquals(getYField(annotationText), y, 0.0001);
        Assert.assertTrue(getIsValueInScreenSpace(annotationText));
    }

    @Test(priority = 2)
    public void testSetText() throws NoSuchFieldException, IllegalAccessException {
        String newText = "New Text";
        annotationText.setText(newText);
        Assert.assertEquals(getTextField(annotationText), newText);
    }

    @Test(priority = 3)
    public void testSetX() throws NoSuchFieldException, IllegalAccessException {
        double newX = 150.0;
        annotationText.setX(newX);
        Assert.assertEquals(getXField(annotationText), newX, 0.0001);
    }

    @Test(priority = 4)
    public void testSetY() throws NoSuchFieldException, IllegalAccessException {
        double newY = 250.0;
        annotationText.setY(newY);
        Assert.assertEquals(getYField(annotationText), newY, 0.0001);
    }

    @Test(priority = 5)
    public void testPaint() throws Exception{
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();


        annotationText.init(chart);

        annotationText.paint(g);
        Assert.assertNotNull(annotationText.getBounds());

        g.dispose();
    }

    @Test(priority = 6)
    public void testPaintInvisible() {
        Graphics2D g = mock(Graphics2D.class);
        annotationText.setVisible(false);

        annotationText.paint(g);

        verify(g, never()).fill(any(Shape.class));
    }

    @Test(priority = 7)
    public void testPaintWithScreenSpaceCoordinates() {
        // Create a real Graphics2D object
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        annotationText.init(chart);
        annotationText.paint(g);

        // Add assertions or verifications specific to screen space coordinates
        Assert.assertNotNull(annotationText.getBounds());

        g.dispose();
    }

    @Test(priority = 8)
    public void testPaintWithChartSpaceCoordinates() {
        annotationText = new AnnotationText(text, x, y, false);
        annotationText.init(chart);
        // Create a real Graphics2D object
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        annotationText.paint(g);

        // Add assertions or verifications specific to chart space coordinates
        Assert.assertNotNull(annotationText.getBounds());

        g.dispose();
    }

    @Test(priority = 9)
    public void testBoundsCalculation() {
        // Create a real Graphics2D object
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        annotationText.init(chart);
        annotationText.paint(g);

        Rectangle2D bounds = annotationText.getBounds();
        Assert.assertNotNull(bounds);
        Assert.assertTrue(bounds.getWidth() > 0);
        Assert.assertTrue(bounds.getHeight() > 0);

        g.dispose();
    }

    @Test(priority = 10)
    public void testDifferentTexts() {
        // Create a real Graphics2D object
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        annotationText.init(chart);
        // Test with empty text --> this should not be pass
//        annotationText.setText("");
        annotationText.paint(g);

        // Test with a long text
        annotationText.setText("This is a very long text to test the rendering of the AnnotationText class.");
        annotationText.paint(g);

        // Test with special characters
        annotationText.setText("Special characters: !@#$%^&*()_+");
        annotationText.paint(g);

        g.dispose();
    }
}
