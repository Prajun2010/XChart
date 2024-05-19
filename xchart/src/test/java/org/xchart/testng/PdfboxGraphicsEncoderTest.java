package org.xchart.testng;


import org.knowm.xchart.PdfboxGraphicsEncoder;
import org.knowm.xchart.internal.chartpart.Chart;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PdfboxGraphicsEncoderTest {

    private Chart mockChart;

    @BeforeClass
    public void setUp() {
        mockChart = Mockito.mock(Chart.class);
        Mockito.when(mockChart.getWidth()).thenReturn(600); // Mocking chart width
        Mockito.when(mockChart.getHeight()).thenReturn(400); // Mocking chart height
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
    public void testSavePdfboxGraphics_SingleChartToFile() throws IOException {
        File file = File.createTempFile("single_chart", ".pdf");
        PdfboxGraphicsEncoder.savePdfboxGraphics(mockChart, file);
        Assert.assertTrue(file.exists());
    }

    @Test
    public void testSavePdfboxGraphics_SingleChartToOutputStream() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfboxGraphicsEncoder.savePdfboxGraphics(mockChart, outputStream);
        Assert.assertTrue(outputStream.size() > 0);
    }

    @Test
    public void testSavePdfboxGraphics_MultipleChartsToFile() throws IOException {
        List<Chart> charts = new ArrayList<>();
        charts.add(mockChart);
        charts.add(mockChart); // Adding same chart twice for simplicity
        File file = File.createTempFile("multiple_charts", ".pdf");
        PdfboxGraphicsEncoder.savePdfboxGraphics(charts, file);
        Assert.assertTrue(file.exists());
    }

    @Test
    public void testSavePdfboxGraphics_MultipleChartsToOutputStream() throws IOException {
        List<Chart> charts = new ArrayList<>();
        charts.add(mockChart);
        charts.add(mockChart); // Adding same chart twice for simplicity
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfboxGraphicsEncoder.savePdfboxGraphics(charts, outputStream);
        Assert.assertTrue(outputStream.size() > 0);
    }



}
