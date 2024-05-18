package org.xchart.testng;

import org.knowm.xchart.AnnotationImage;
import org.knowm.xchart.internal.chartpart.Axis;
import org.knowm.xchart.internal.chartpart.AxisPair;
import org.knowm.xchart.internal.chartpart.Chart;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

public class AnnotationImageTest {

    private AnnotationImage annotationImage;
    private BufferedImage image;
    private Graphics2D graphics;
    private Chart chart;
    private Axis xAxis;
    private Axis yAxis;

    @BeforeMethod
    public void setUp() throws Exception {
        // Mock dependencies
        image = mock(BufferedImage.class);
        graphics = mock(Graphics2D.class);
        chart = mock(Chart.class);
        xAxis = mock(Axis.class);
        yAxis = mock(Axis.class);

        // Mock AxisPair and its methods
        AxisPair axisPair = mock(AxisPair.class);
        setPrivateField(chart, "axisPair", axisPair);
        setPrivateField(axisPair, "xAxis", xAxis);
        setPrivateField(axisPair, "yAxis", yAxis);

        // Mock chart methods using reflection
        when(invokePrivateMethod(chart, "getXAxis")).thenReturn(xAxis);
        when(invokePrivateMethod(chart, "getYAxis")).thenReturn(yAxis);

        // Create an instance of AnnotationImage with mock data
        annotationImage = new AnnotationImage(image, 100, 200, true);
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

    @Test(priority = 1)
    public void testConstructor() throws Exception {
        // Verify initial state
        Assert.assertEquals(getPrivateField(annotationImage, "image"), image);
        Assert.assertEquals(getPrivateField(annotationImage, "x"), 100.0);
        Assert.assertEquals(getPrivateField(annotationImage, "y"), 200.0);
        Assert.assertTrue((Boolean) getPrivateField(annotationImage, "isValueInScreenSpace"));
    }

    @Test(priority = 2)
    public void testSetImage() throws Exception {
        // Set a new image
        BufferedImage newImage = mock(BufferedImage.class);
        annotationImage.setImage(newImage);

        // Verify the image is updated
        Assert.assertEquals(getPrivateField(annotationImage, "image"), newImage);
    }

    @Test(priority = 3)
    public void testSetX() throws Exception {
        // Set a new x value
        annotationImage.setX(150);

        // Verify the x value is updated
        Assert.assertEquals(getPrivateField(annotationImage, "x"), 150.0);
    }

    @Test(priority = 4)
    public void testSetY() throws Exception {
        // Set a new y value
        annotationImage.setY(250);

        // Verify the y value is updated
        Assert.assertEquals(getPrivateField(annotationImage, "y"), 250.0);
    }

    @Test(priority = 5)
    public void testPaintWhenVisibleAndInScreenSpace() throws Exception {
        when(image.getWidth()).thenReturn(50);
        when(image.getHeight()).thenReturn(50);
        when(chart.getHeight()).thenReturn(400);

        // Initialize the annotation with the chart
        annotationImage.init(chart);

        // Call the paint method
        annotationImage.paint(graphics);

        // Verify that the image is drawn at the correct position
        verify(graphics).drawImage(image, 75, 175, null);
    }

    @Test(priority = 6)
    public void testPaintWhenNotVisible() {
        // Set the annotation to not be visible
        annotationImage.setVisible(false);

        // Call the paint method
        annotationImage.paint(graphics);

        // Verify that the image is not drawn
        verify(graphics, never()).drawImage(any(), anyInt(), anyInt(), any());
    }

    @Test(priority = 7)
    public void testPaintWhenVisibleAndNotInScreenSpace() throws Exception {
        annotationImage = new AnnotationImage(image, 100, 200, false);
        when(image.getWidth()).thenReturn(50);
        when(image.getHeight()).thenReturn(50);

        // Mock the Axis class and its getScreenValue method
        when(xAxis.getScreenValue(100)).thenReturn(150.0);
        when(yAxis.getScreenValue(200)).thenReturn(300.0);

        // Initialize the annotation with the chart
        annotationImage.init(chart);

        // Call the paint method
        annotationImage.paint(graphics);

        // Verify that the image is drawn at the correct position
        verify(graphics).drawImage(image, 125, 275, null);
    }
}
