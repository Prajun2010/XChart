package org.xchart.testng;

import org.knowm.xchart.BoxChart;
import org.knowm.xchart.BoxChartBuilder;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.theme.XChartTheme;
import org.testng.Assert;
import org.testng.annotations.Test;
public class BoxChartBuilderTest {
    
    BoxChartBuilder boxChartBuilder = new BoxChartBuilder();

    @Test
    public void testXAxisTitle(){
        BoxChartBuilder result = boxChartBuilder.xAxisTitle("xAxisTitle");
        Assert.assertEquals(result.title, new BoxChartBuilder());
    }

    @Test
    public void testYAxisTitle(){
        BoxChartBuilder result = boxChartBuilder.yAxisTitle("yAxisTitle");
        Assert.assertEquals(result, new BoxChartBuilder());
    }

    @Test
    public void testBuild(){
        BoxChart result = boxChartBuilder.build();
        Assert.assertEquals(result, result);
    }

    @Test
    public void testWidth(){
        BoxChartBuilder result = boxChartBuilder.width(0);
        Assert.assertEquals(result, new BoxChartBuilder());
    }

    @Test
    public void testHeight(){
        BoxChartBuilder result = boxChartBuilder.height(0);
        Assert.assertEquals(result.height, result.height);
    }

    @Test
    public void testTitle(){
        BoxChartBuilder result = boxChartBuilder.title("title");
        Assert.assertEquals(result.title, result.title);
    }

    @Test
    public void testTheme(){
        BoxChartBuilder result = boxChartBuilder.theme(Styler.ChartTheme.XChart);
        Assert.assertEquals(result, new BoxChartBuilder());
    }
}

