package org.xchart.testng.custom;

import org.knowm.xchart.custom.CustomSeriesColors;
import org.knowm.xchart.custom.CustomTheme;
import org.knowm.xchart.style.colors.ChartColor;
import org.knowm.xchart.style.lines.XChartSeriesLines;
import org.knowm.xchart.style.markers.Marker;
import org.knowm.xchart.style.markers.XChartSeriesMarkers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;

import static org.testng.Assert.*;

public class CustomThemeTest {

    private CustomTheme customTheme;

    @BeforeMethod
    public void setUp() {
        customTheme = new CustomTheme();
    }

    @Test
    public void testGetBaseFont() {
        Font baseFont = customTheme.getBaseFont();
        assertNotNull(baseFont, "Base font should not be null");
        assertEquals(baseFont.getFamily(), Font.SERIF, "Base font family should be SERIF");
        assertEquals(baseFont.getStyle(), Font.PLAIN, "Base font style should be PLAIN");
        assertEquals(baseFont.getSize(), 10, "Base font size should be 10");
    }

    @Test
    public void testGetChartBackgroundColor() {
        Color backgroundColor = customTheme.getChartBackgroundColor();
        assertNotNull(backgroundColor, "Chart background color should not be null");
        assertEquals(backgroundColor, ChartColor.DARK_GREY.getColor(), "Chart background color should be DARK_GREY");
    }

    @Test
    public void testGetChartFontColor() {
        Color fontColor = customTheme.getChartFontColor();
        assertNotNull(fontColor, "Chart font color should not be null");
        assertEquals(fontColor, ChartColor.DARK_GREY.getColor(), "Chart font color should be DARK_GREY");
    }

    @Test
    public void testGetChartPadding() {
        int padding = customTheme.getChartPadding();
        assertEquals(padding, 12, "Chart padding should be 12");
    }

    @Test
    public void testGetSeriesColors() {
        Color[] seriesColors = customTheme.getSeriesColors();
        assertNotNull(seriesColors, "Series colors should not be null");
        assertEquals(seriesColors.length, 3, "Series colors array length should be 3");
        assertEquals(seriesColors[0], CustomSeriesColors.GREEN, "First series color should be GREEN");
        assertEquals(seriesColors[1], CustomSeriesColors.RED, "Second series color should be RED");
        assertEquals(seriesColors[2], CustomSeriesColors.BLACK, "Third series color should be BLACK");
    }

    @Test
    public void testGetSeriesMarkers() {
        Marker[] seriesMarkers = customTheme.getSeriesMarkers();
        assertNotNull(seriesMarkers, "Series markers should not be null");
        assertEquals(seriesMarkers, new XChartSeriesMarkers().getSeriesMarkers(), "Series markers should match XChartSeriesMarkers");
    }

    @Test
    public void testGetSeriesLines() {
        BasicStroke[] seriesLines = customTheme.getSeriesLines();
        assertNotNull(seriesLines, "Series lines should not be null");
        assertEquals(seriesLines, new XChartSeriesLines().getSeriesLines(), "Series lines should match XChartSeriesLines");
    }

    @Test
    public void testGetChartTitleFont() {
        Font chartTitleFont = customTheme.getChartTitleFont();
        assertNotNull(chartTitleFont, "Chart title font should not be null");
        assertEquals(chartTitleFont.getStyle(), Font.BOLD, "Chart title font style should be BOLD");
        assertEquals(chartTitleFont.getSize(), 18, "Chart title font size should be 18");
    }

    @Test
    public void testIsChartTitleBoxVisible() {
        boolean isVisible = customTheme.isChartTitleBoxVisible();
        assertFalse(isVisible, "Chart title box should not be visible");
    }

    @Test
    public void testGetChartTitleBoxBackgroundColor() {
        Color titleBoxBackgroundColor = customTheme.getChartTitleBoxBackgroundColor();
        assertNotNull(titleBoxBackgroundColor, "Chart title box background color should not be null");
        assertEquals(titleBoxBackgroundColor, ChartColor.GREY.getColor(), "Chart title box background color should be GREY");
    }

    @Test
    public void testGetChartTitleBoxBorderColor() {
        Color titleBoxBorderColor = customTheme.getChartTitleBoxBorderColor();
        assertNotNull(titleBoxBorderColor, "Chart title box border color should not be null");
        assertEquals(titleBoxBorderColor, ChartColor.GREY.getColor(), "Chart title box border color should be GREY");
    }

    @Test
    public void testGetAxisTickMarksStroke() {
        BasicStroke axisTickMarksStroke = customTheme.getAxisTickMarksStroke();
        assertNotNull(axisTickMarksStroke, "Axis tick marks stroke should not be null");
        assertEquals(axisTickMarksStroke.getLineWidth(), 1.0f, "Axis tick marks stroke line width should be 1.0f");
        assertEquals(axisTickMarksStroke.getDashArray(), new float[] {3.0f, 0.0f}, "Axis tick marks stroke dash array should be [3.0f, 0.0f]");
    }

    @Test
    public void testIsPlotTicksMarksVisible() {
        boolean areVisible = customTheme.isPlotTicksMarksVisible();
        assertFalse(areVisible, "Plot ticks marks should not be visible");
    }

    @Test
    public void testGetPlotGridLinesStroke() {
        BasicStroke plotGridLinesStroke = customTheme.getPlotGridLinesStroke();
        assertNotNull(plotGridLinesStroke, "Plot grid lines stroke should not be null");
        assertEquals(plotGridLinesStroke.getLineWidth(), 0.25f, "Plot grid lines stroke line width should be 0.25f");
        assertEquals(plotGridLinesStroke.getDashArray(), new float[] {3.0f, 3.0f}, "Plot grid lines stroke dash array should be [3.0f, 3.0f]");
    }

    @Test
    public void testGetMarkerSize() {
        int markerSize = customTheme.getMarkerSize();
        assertEquals(markerSize, 16, "Marker size should be 16");
    }
}
