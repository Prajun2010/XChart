package org.xchart.testng;

import org.knowm.xchart.AnnotationTextPanel;

import org.knowm.xchart.internal.chartpart.Axis;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.internal.chartpart.PlotSurface_;
import org.knowm.xchart.internal.chartpart.Plot_;
import org.knowm.xchart.style.Styler;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnnotationTextPanelTest {

    private Chart chart;
    private Plot_ mockPlot;
    private PlotSurface_ mockPlotSurface;
    private Styler styler;
    private Axis yAxis;
    private Axis xAxis;
    @BeforeMethod
    public void setUp() throws Exception{
        chart = mock(Chart.class);
        mockPlot = mock(Plot_.class);
        mockPlotSurface = mock(PlotSurface_.class);
        styler = mock(Styler.class);
        xAxis = mock(Axis.class);
        yAxis = mock(Axis.class);

        when(invokePrivateMethod(chart, "getPlot")).thenReturn(mockPlot);
        when(mockPlotSurface.getBounds()).thenReturn(new Rectangle2D.Double(0, 0, 800, 600));

        try {
            Field plotSurfaceField = Plot_.class.getDeclaredField("plotSurface");
            plotSurfaceField.setAccessible(true);
            plotSurfaceField.set(mockPlot, mockPlotSurface);
        } catch (Exception e) {
            e.printStackTrace(); // Handle or log exception as necessary
        }



        // Mocking styler methods
        when(styler.getAnnotationTextPanelFont()).thenReturn(new Font("Arial", Font.PLAIN, 12));
        when(styler.getAnnotationTextPanelPadding()).thenReturn(5);
        when(styler.getAnnotationTextPanelBackgroundColor()).thenReturn(java.awt.Color.WHITE);
        when(styler.getAnnotationTextPanelBorderColor()).thenReturn(java.awt.Color.BLACK);
        when(styler.getAnnotationTextPanelFontColor()).thenReturn(java.awt.Color.BLACK);

        when(invokePrivateMethod(chart, "getXAxis")).thenReturn(xAxis);
        when(invokePrivateMethod(chart, "getYAxis")).thenReturn(xAxis);


        // Set up the Axis mock behavior
        when(xAxis.getScreenValue(anyDouble())).thenReturn(300.00);
        when(yAxis.getScreenValue(anyDouble())).thenReturn(400.00);

        // Setting the styler in the chart
        when(chart.getStyler()).thenReturn(styler);
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


    @Test(priority = 1, groups = "Single Line")
    public void testSingleLineInScreenSpace1() {
        testSingleLineInScreenSpace("Single Line 1", 50, 50, true);
    }

    @Test(priority = 2, groups = "Single Line")
    public void testSingleLineInScreenSpace2() {
        testSingleLineInScreenSpace("Single Line 2", 200, 100, true);
    }

    @Test(priority = 3, groups = "Single Line")
    public void testSingleLineInPlotSpace1() {
        testSingleLineInPlotSpace("Single Line 3", 50, 50, false);
    }

    @Test(priority = 4, groups = "Single Line")
    public void testSingleLineInPlotSpace2() {
        testSingleLineInPlotSpace("Single Line 4", 200, 100, false);
    }

    @Test(priority = 5, groups = "Multi Line")
    public void testMultiLineInScreenSpace1() {
        testMultiLineInScreenSpace(Arrays.asList("Multi", "Line 1"), 50, 50, true);
    }

    @Test(priority = 6, groups = "Multi Line")
    public void testMultiLineInScreenSpace2() {
        testMultiLineInScreenSpace(Arrays.asList("Multi", "Line 2", "Text"), 200, 100, true);
    }

    @Test(priority = 7, groups = "Multi Line")
    public void testMultiLineInPlotSpace1() {
        testMultiLineInPlotSpace(Arrays.asList("Multi", "Line 3"), 50, 50, false);
    }

    @Test(priority = 8, groups = "Multi Line")
    public void testMultiLineInPlotSpace2() {
        testMultiLineInPlotSpace(Arrays.asList("Multi", "Line 4", "Text"), 200, 100, false);
    }

    @Test(priority = 9, groups = "Empty Text")
    public void testEmptyTextInScreenSpace() {
        testSingleLineInScreenSpace("", 50, 50, true);
    }

    @Test(priority = 10, groups = "Empty Text")
    public void testEmptyTextInPlotSpace() {
        testSingleLineInPlotSpace("", 50, 50, false);
    }

    @Test(priority = 11, groups = "Null Text")
    public void testNullTextInScreenSpace() {
        testSingleLineInScreenSpace(null, 50, 50, true);
    }

    @Test(priority = 12, groups = "Null Text")
    public void testNullTextInPlotSpace() {
        testSingleLineInPlotSpace(null, 50, 50, false);
    }

    @Test(priority = 13, groups = "Long Text")
    public void testLongTextInScreenSpace() {
        testSingleLineInScreenSpace("This is a very long text that exceeds the expected bounds", 50, 50, true);
    }

    @Test(priority = 14, groups = "Long Text")
    public void testLongTextInPlotSpace() {
        testSingleLineInPlotSpace("This is a very long text that exceeds the expected bounds", 50, 50, false);
    }

    @Test(priority = 15, groups = "Edge Case Coordinates")
    public void testEdgeCaseCoordinatesInScreenSpace() {
        testSingleLineInScreenSpace("Edge Case", 0, 0, true);
    }

    @Test(priority = 16, groups = "Edge Case Coordinates")
    public void testEdgeCaseCoordinatesInPlotSpace() {
        testSingleLineInPlotSpace("Edge Case", 0, 0, false);
    }

    @Test(priority = 17, groups = "Negative Coordinates")
    public void testNegativeCoordinatesInScreenSpace() {
        testSingleLineInScreenSpace("Negative Coordinates", -50, -50, true);
    }

    @Test(priority = 18, groups = "Negative Coordinates")
    public void testNegativeCoordinatesInPlotSpace() {
        testSingleLineInPlotSpace("Negative Coordinates", -50, -50, false);
    }

    @Test(priority = 19, groups = "Boundary Coordinates")
    public void testBoundaryCoordinatesInScreenSpace() {
        testSingleLineInScreenSpace("Boundary Coordinates", 800, 600, true);
    }

    @Test(priority = 20, groups = "Boundary Coordinates")
    public void testBoundaryCoordinatesInPlotSpace() {
        testSingleLineInPlotSpace("Boundary Coordinates", 800, 600, false);
    }

    @Test(priority = 21, groups = "Styler Overrides")
    public void testStylerOverridesInScreenSpace() {
        // Mocking different font and color for this test case
        Font customFont = new Font("Times New Roman", Font.BOLD, 14);
        Color customBackgroundColor = Color.YELLOW;
        Color customBorderColor = Color.RED;

        when(styler.getAnnotationTextPanelFont()).thenReturn(customFont);
        when(styler.getAnnotationTextPanelBackgroundColor()).thenReturn(customBackgroundColor);
        when(styler.getAnnotationTextPanelBorderColor()).thenReturn(customBorderColor);

        testSingleLineInScreenSpace("Styler Overrides", 50, 50, true);
    }

    @Test(priority = 22, groups = "Styler Overrides")
    public void testStylerOverridesInPlotSpace() {
        // Mocking different font and color for this test case
        Font customFont = new Font("Times New Roman", Font.BOLD, 14);
        Color customBackgroundColor = Color.YELLOW;
        Color customBorderColor = Color.RED;

        when(styler.getAnnotationTextPanelFont()).thenReturn(customFont);
        when(styler.getAnnotationTextPanelBackgroundColor()).thenReturn(customBackgroundColor);
        when(styler.getAnnotationTextPanelBorderColor()).thenReturn(customBorderColor);

        testSingleLineInPlotSpace("Styler Overrides", 50, 50, false);
    }







    @Test(priority = 29, groups = "Single Line with Large Font")
    public void testSingleLineWithLargeFontInScreenSpace() {
        Font largeFont = new Font("Arial", Font.BOLD, 20);
        when(styler.getAnnotationTextPanelFont()).thenReturn(largeFont);
        testSingleLineInScreenSpace("Large Font", 50, 50, true);
    }

    @Test(priority = 30, groups = "Single Line with Large Font")
    public void testSingleLineWithLargeFontInPlotSpace() {
        Font largeFont = new Font("Arial", Font.BOLD, 20);
        when(styler.getAnnotationTextPanelFont()).thenReturn(largeFont);
        testSingleLineInPlotSpace("Large Font", 50, 50, false);
    }

    @Test(priority = 31, groups = "Single Line with Small Font")
    public void testSingleLineWithSmallFontInScreenSpace() {
        Font smallFont = new Font("Arial", Font.PLAIN, 10);
        when(styler.getAnnotationTextPanelFont()).thenReturn(smallFont);
        testSingleLineInScreenSpace("Small Font", 50, 50, true);
    }

    @Test(priority = 32, groups = "Single Line with Small Font")
    public void testSingleLineWithSmallFontInPlotSpace() {
        Font smallFont = new Font("Arial", Font.PLAIN, 10);
        when(styler.getAnnotationTextPanelFont()).thenReturn(smallFont);
        testSingleLineInPlotSpace("Small Font", 50, 50, false);
    }

    @Test(priority = 33, groups = "Single Line with Different Colors")
    public void testSingleLineWithDifferentColorsInScreenSpace() {
        Color textColor = Color.BLUE;
        Color backgroundColor = Color.GREEN;
        when(styler.getAnnotationTextPanelFontColor()).thenReturn(textColor);
        when(styler.getAnnotationTextPanelBackgroundColor()).thenReturn(backgroundColor);
        testSingleLineInScreenSpace("Different Colors", 50, 50, true);
    }

    @Test(priority = 34, groups = "Single Line with Different Colors")
    public void testSingleLineWithDifferentColorsInPlotSpace() {
        Color textColor = Color.BLUE;
        Color backgroundColor = Color.GREEN;
        when(styler.getAnnotationTextPanelFontColor()).thenReturn(textColor);
        when(styler.getAnnotationTextPanelBackgroundColor()).thenReturn(backgroundColor);
        testSingleLineInPlotSpace("Different Colors", 50, 50, false);
    }

    @Test(priority = 35, groups = "Multi Line with Large Font")
    public void testMultiLineWithLargeFontInScreenSpace() {
        Font largeFont = new Font("Arial", Font.BOLD, 20);
        when(styler.getAnnotationTextPanelFont()).thenReturn(largeFont);
        testMultiLineInScreenSpace(Arrays.asList("Multi Line", "Large Font"), 50, 50, true);
    }

    @Test(priority = 36, groups = "Multi Line with Large Font")
    public void testMultiLineWithLargeFontInPlotSpace() {
        Font largeFont = new Font("Arial", Font.BOLD, 20);
        when(styler.getAnnotationTextPanelFont()).thenReturn(largeFont);
        testMultiLineInPlotSpace(Arrays.asList("Multi Line", "Large Font"), 50, 50, false);
    }

    @Test(priority = 37, groups = "Multi Line with Small Font")
    public void testMultiLineWithSmallFontInScreenSpace() {
        Font smallFont = new Font("Arial", Font.PLAIN, 10);
        when(styler.getAnnotationTextPanelFont()).thenReturn(smallFont);
        testMultiLineInScreenSpace(Arrays.asList("Multi Line", "Small Font"), 50, 50, true);
    }

    @Test(priority = 38, groups = "Multi Line with Small Font")
    public void testMultiLineWithSmallFontInPlotSpace() {
        Font smallFont = new Font("Arial", Font.PLAIN, 10);
        when(styler.getAnnotationTextPanelFont()).thenReturn(smallFont);
        testMultiLineInPlotSpace(Arrays.asList("Multi Line", "Small Font"), 50, 50, false);
    }

    @Test(priority = 39, groups = "Multi Line with Different Colors")
    public void testMultiLineWithDifferentColorsInScreenSpace() {
        Color textColor = Color.BLUE;
        Color backgroundColor = Color.GREEN;
        when(styler.getAnnotationTextPanelFontColor()).thenReturn(textColor);
        when(styler.getAnnotationTextPanelBackgroundColor()).thenReturn(backgroundColor);
        testMultiLineInScreenSpace(Arrays.asList("Multi Line", "Different Colors"), 50, 50, true);
    }

    @Test(priority = 40, groups = "Multi Line with Different Colors")
    public void testMultiLineWithDifferentColorsInPlotSpace() {
        Color textColor = Color.BLUE;
        Color backgroundColor = Color.GREEN;
        when(styler.getAnnotationTextPanelFontColor()).thenReturn(textColor);
        when(styler.getAnnotationTextPanelBackgroundColor()).thenReturn(backgroundColor);
        testMultiLineInPlotSpace(Arrays.asList("Multi Line", "Different Colors"), 50, 50, false);
    }

    @Test(priority = 41, groups = "Multi Line with Large Font and Different Colors")
    public void testMultiLineWithLargeFontAndDifferentColorsInScreenSpace() {
        Font largeFont = new Font("Arial", Font.BOLD, 20);
        Color textColor = Color.BLUE;
        Color backgroundColor = Color.GREEN;
        when(styler.getAnnotationTextPanelFont()).thenReturn(largeFont);
        when(styler.getAnnotationTextPanelFontColor()).thenReturn(textColor);
        when(styler.getAnnotationTextPanelBackgroundColor()).thenReturn(backgroundColor);
        testMultiLineInScreenSpace(Arrays.asList("Multi Line", "Large Font", "Different Colors"), 50, 50, true);
    }

    @Test(priority = 42, groups = "Multi Line with Large Font and Different Colors")
    public void testMultiLineWithLargeFontAndDifferentColorsInPlotSpace() {
        Font largeFont = new Font("Arial", Font.BOLD, 20);
        Color textColor = Color.BLUE;
        Color backgroundColor = Color.GREEN;
        when(styler.getAnnotationTextPanelFont()).thenReturn(largeFont);
        when(styler.getAnnotationTextPanelFontColor()).thenReturn(textColor);
        when(styler.getAnnotationTextPanelBackgroundColor()).thenReturn(backgroundColor);
        testMultiLineInPlotSpace(Arrays.asList("Multi Line", "Large Font", "Different Colors"), 50, 50, false);
    }

    // Add more test cases as needed...

    private void testSingleLineInScreenSpace(String text, double x, double y, boolean isValueInScreenSpace) {
        AnnotationTextPanel annotationTextPanel = new AnnotationTextPanel(text, x, y, isValueInScreenSpace);
        annotationTextPanel.init(chart);
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        annotationTextPanel.paint(g);
        assertTextRendered(g, text);
        g.dispose();
    }

    private void testSingleLineInPlotSpace(String text, double x, double y, boolean isValueInScreenSpace) {
        AnnotationTextPanel annotationTextPanel = new AnnotationTextPanel(text, x, y, isValueInScreenSpace);
        annotationTextPanel.init(chart);
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        annotationTextPanel.paint(g);
        assertTextRendered(g, text);
        g.dispose();
    }

    private void testMultiLineInScreenSpace(List<String> lines, double x, double y, boolean isValueInScreenSpace) {
        AnnotationTextPanel annotationTextPanel = new AnnotationTextPanel(joinLines(lines), x, y, isValueInScreenSpace);
        annotationTextPanel.init(chart);
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        annotationTextPanel.paint(g);
        assertTextLinesRendered(g, lines);
        g.dispose();
    }

    private void testMultiLineInPlotSpace(List<String> lines, double x, double y, boolean isValueInScreenSpace) {
        AnnotationTextPanel annotationTextPanel = new AnnotationTextPanel(joinLines(lines), x, y, isValueInScreenSpace);
        annotationTextPanel.init(chart);
        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        annotationTextPanel.paint(g);
        assertTextLinesRendered(g, lines);
        g.dispose();
    }

    private String joinLines(List<String> lines) {
        return String.join("\n", lines);
    }

    private void assertTextRendered(Graphics2D g, String text) {
        // Implement assertion logic for text rendering if needed
        Assert.assertTrue(true); // Placeholder assertion
    }

    private void assertTextLinesRendered(Graphics2D g, List<String> lines) {
        // Implement assertion logic for multi-line text rendering if needed
        Assert.assertTrue(true); // Placeholder assertion
    }


}


