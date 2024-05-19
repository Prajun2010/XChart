package org.xchart.testng.internal;

import org.knowm.xchart.internal.ChartBuilder;

class DummyChartBuilder extends ChartBuilder<DummyChartBuilder, DummyChart> {
    @Override
    public DummyChart build() {
        return new DummyChart();
    }
}
