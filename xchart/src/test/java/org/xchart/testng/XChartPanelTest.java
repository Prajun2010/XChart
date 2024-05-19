package org.xchart.testng;

import org.knowm.xchart.*;
import org.knowm.xchart.internal.chartpart.*;
import org.knowm.xchart.style.XYStyler;
import org.mockito.Mockito;
import org.testng.annotations.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class XChartPanelTest {

    private XYChart chart;
    private XYStyler styler;
    private XChartPanel<XYChart> xChartPanel;

    @BeforeMethod
    public void setUp() {
        chart = mock(XYChart.class);
        styler = mock(XYStyler.class);

        when(chart.getStyler()).thenReturn(styler);
        when(chart.getWidth()).thenReturn(800);
        when(chart.getHeight()).thenReturn(600);

        xChartPanel = new XChartPanel<>(chart);
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

    @Test
    public void testPreferredSize() {
        Dimension preferredSize = xChartPanel.getPreferredSize();
        assertEquals(preferredSize.width, 800);
        assertEquals(preferredSize.height, 600);
    }


    @Test
    public void testPaintComponent() throws Exception {
        // Create a mock Graphics2D object
        Graphics2D g2d = mock(Graphics2D.class);

        // Mock methods on Graphics2D as necessary
        when(g2d.create()).thenReturn(g2d);

        // Using reflection to access the protected paintComponent method
        Method paintComponentMethod = XChartPanel.class.getDeclaredMethod("paintComponent", Graphics.class);
        paintComponentMethod.setAccessible(true);

        // Invoke the paintComponent method with the mock Graphics2D
        paintComponentMethod.invoke(xChartPanel, g2d);

        // Verify the paint method on the chart was called
        verify(chart, times(1)).paint(any(Graphics2D.class), eq(xChartPanel.getWidth()), eq(xChartPanel.getHeight()));
    }


    @Test
    public void testSetSaveAsString() throws Exception{
        xChartPanel.setSaveAsString("Guardar como...");
        assertEquals(getPrivateField(xChartPanel,"saveAsString"), "Guardar como...");

    }

    @Test
    public void testSetExportAsString() throws Exception{
        xChartPanel.setExportAsString("Exportar a...");

        assertEquals(getPrivateField(xChartPanel,"exportAsString"),"Exportar a...");
    }

    @Test
    public void testSetPrintString() throws Exception{
        xChartPanel.setPrintString("Imprimir...");
        assertEquals(getPrivateField(xChartPanel,"printAsString"),"Imprimir...");
    }

    @Test
    public void testSetResetString() throws Exception{
        xChartPanel.setResetString("Restablecer Zoom");
        assertEquals(getPrivateField(xChartPanel,"resetAsString"),"Restablecer Zoom");
    }

    @Test
    public void testToolTipsEnabled() throws Exception{
        when(styler.isToolTipsEnabled()).thenReturn(true);
        xChartPanel = new XChartPanel<>(chart);
        assertNotNull(getPrivateField(xChartPanel,"toolTips"));
    }

    @Test
    public void testToolTipsDisabled() throws Exception{
        when(styler.isToolTipsEnabled()).thenReturn(false);
        xChartPanel = new XChartPanel<>(chart);
        assertNotNull(getPrivateField(xChartPanel,"toolTips"));
    }

    @Test
    public void testMouseZoomListenerEnabled() {
        when(styler.isZoomEnabled()).thenReturn(true);
        xChartPanel = new XChartPanel<>(chart);
        assertEquals(xChartPanel.getMouseListeners().length, 2);
    }

    @Test
    public void testMouseZoomListenerDisabled() {
        when(styler.isZoomEnabled()).thenReturn(false);
        xChartPanel = new XChartPanel<>(chart);
        assertEquals(xChartPanel.getMouseListeners().length, 1);
    }

    @Test
    public void testMouseCursorListenerEnabled() {
        when(styler.isCursorEnabled()).thenReturn(true);
        xChartPanel = new XChartPanel<>(chart);
        assertEquals(xChartPanel.getMouseMotionListeners().length, 1);
    }

    @Test
    public void testMouseCursorListenerDisabled() {
        when(styler.isCursorEnabled()).thenReturn(false);
        xChartPanel = new XChartPanel<>(chart);
        assertEquals(xChartPanel.getMouseMotionListeners().length, 0);
    }

    @Test
    public void testPrinterJob() {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        assertNotNull(printJob);
    }

    @Test
    public void testDisableTextField() throws Exception{
        JPanel panel = new JPanel();
        JTextField textField = new JTextField();
        panel.add(textField);
        invokePrivateMethod(xChartPanel, "disableTextField", panel.getComponents());

        assertFalse(textField.isVisible());
    }



    @Test
    public void testChartWithNullStyler() {
        when(chart.getStyler()).thenReturn(null);
        xChartPanel = new XChartPanel<>(chart);
        assertNull(chart.getStyler());
    }

    @Test
    public void testNullChart() {
        xChartPanel = new XChartPanel<>(null);
        assertNull(xChartPanel.getChart());
    }


}
