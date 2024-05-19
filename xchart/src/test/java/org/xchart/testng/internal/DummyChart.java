package org.xchart.testng.internal;

import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.internal.series.Series;

import java.awt.*;

public class DummyChart extends Chart<DummyStyler, Series> {

    public DummyChart() {
        super(800, 600, new DummyStyler());
    }

    @Override
    public void paint(Graphics2D g, int width, int height) {
        // Implement the painting logic for DummyChart
    }
}
