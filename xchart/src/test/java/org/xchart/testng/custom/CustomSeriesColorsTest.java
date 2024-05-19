package org.xchart.testng.custom;

import org.knowm.xchart.custom.CustomSeriesColors;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.Color;

import static org.testng.Assert.*;

public class CustomSeriesColorsTest {

    private CustomSeriesColors customSeriesColors;

    @BeforeMethod
    public void setUp() {
        customSeriesColors = new CustomSeriesColors();
    }

    @Test
    public void testCustomSeriesColorsInitialization() {
        // Verify that the custom colors are correctly initialized
        assertEquals(CustomSeriesColors.GREEN, new Color(0, 205, 0, 180), "Green color initialization failed");
        assertEquals(CustomSeriesColors.RED, new Color(205, 0, 0, 180), "Red color initialization failed");
        assertEquals(CustomSeriesColors.BLACK, new Color(0, 0, 0, 180), "Black color initialization failed");
    }

    @Test
    public void testGetSeriesColors() {
        Color[] seriesColors = customSeriesColors.getSeriesColors();

        // Verify that the getSeriesColors method returns the correct array of colors
        assertNotNull(seriesColors, "Series colors array is null");
        assertEquals(seriesColors.length, 3, "Series colors array length is incorrect");
        assertEquals(seriesColors[0], CustomSeriesColors.GREEN, "First series color is incorrect");
        assertEquals(seriesColors[1], CustomSeriesColors.RED, "Second series color is incorrect");
        assertEquals(seriesColors[2], CustomSeriesColors.BLACK, "Third series color is incorrect");
    }
}
