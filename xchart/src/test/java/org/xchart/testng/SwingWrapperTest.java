package org.xchart.testng;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class SwingWrapperTest {

    // Helper method to create a dummy CategoryChart
    private CategoryChart createDummyChart() {
        return new CategoryChartBuilder().width(800).height(600).build();
    }

    @Test
    public void testSingleChartConstructor() {
        CategoryChart chart = createDummyChart();
        SwingWrapper<CategoryChart> wrapper = new SwingWrapper<>(chart);
        Assert.assertNotNull(wrapper);
    }

    @Test
    public void testMultipleChartsConstructor() {
        List<CategoryChart> charts = new ArrayList<>();
        charts.add(createDummyChart());
        charts.add(createDummyChart());
        SwingWrapper<CategoryChart> wrapper = new SwingWrapper<>(charts);
        Assert.assertNotNull(wrapper);
    }

    @Test
    public void testMultipleChartsConstructorWithRowsColumns() {
        List<CategoryChart> charts = new ArrayList<>();
        charts.add(createDummyChart());
        charts.add(createDummyChart());
        SwingWrapper<CategoryChart> wrapper = new SwingWrapper<>(charts, 1, 2);
        Assert.assertNotNull(wrapper);
    }

    @Test
    public void testSetWindowTitle() {
        CategoryChart chart = createDummyChart();
        SwingWrapper<CategoryChart> wrapper = new SwingWrapper<>(chart);
        wrapper.setTitle("Test Title");
        Assert.assertEquals(wrapper.setTitle("Test Title"), wrapper);
    }



    @Test
    public void testDisplayChart() throws InvocationTargetException, InterruptedException {
        CategoryChart chart = createDummyChart();
        SwingWrapper<CategoryChart> wrapper = new SwingWrapper<>(chart);
        JFrame frame = wrapper.displayChart();
        Assert.assertNotNull(frame);
        Assert.assertTrue(frame.isVisible());
    }

    @Test
    public void testDisplayChartMatrix() {
        List<CategoryChart> charts = new ArrayList<>();
        charts.add(createDummyChart());
        charts.add(createDummyChart());
        SwingWrapper<CategoryChart> wrapper = new SwingWrapper<>(charts, 1, 2);
        JFrame frame = wrapper.displayChartMatrix();
        Assert.assertNotNull(frame);
        Assert.assertTrue(frame.isVisible());
    }

    @Test
    public void testGetXChartPanel() {
        CategoryChart chart = createDummyChart();
        SwingWrapper<CategoryChart> wrapper = new SwingWrapper<>(chart);
        XChartPanel<CategoryChart> panel = wrapper.getXChartPanel();
        Assert.assertNotNull(panel);
    }

    @Test
    public void testRepaintChart() {
        CategoryChart chart = createDummyChart();
        SwingWrapper<CategoryChart> wrapper = new SwingWrapper<>(chart);
        wrapper.repaintChart();
        Assert.assertNotNull(wrapper.getXChartPanel());
    }

    @Test
    public void testGetXChartPanelWithIndex() {
        List<CategoryChart> charts = new ArrayList<>();
        charts.add(createDummyChart());
        charts.add(createDummyChart());
        SwingWrapper<CategoryChart> wrapper = new SwingWrapper<>(charts);

        // Check that the list is not empty before accessing elements
        if (!charts.isEmpty()) {
            XChartPanel<CategoryChart> panel = wrapper.getXChartPanel(1);
            Assert.assertNotNull(panel);
        } else {
            Assert.fail("The chart list should not be empty.");
        }
    }

    @Test
    public void testRepaintChartWithIndex() {
        List<CategoryChart> charts = new ArrayList<>();
        charts.add(createDummyChart());
        charts.add(createDummyChart());
        SwingWrapper<CategoryChart> wrapper = new SwingWrapper<>(charts);

        // Check that the list is not empty before accessing elements
        if (!charts.isEmpty()) {
            wrapper.repaintChart(1);
            Assert.assertNotNull(wrapper.getXChartPanel(1));
        } else {
            Assert.fail("The chart list should not be empty.");
        }
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testGetXChartPanelWithInvalidIndex() {
        List<CategoryChart> charts = new ArrayList<>();
        charts.add(createDummyChart());
        charts.add(createDummyChart());
        SwingWrapper<CategoryChart> wrapper = new SwingWrapper<>(charts);
        wrapper.getXChartPanel(2); // should throw IndexOutOfBoundsException
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testRepaintChartWithInvalidIndex() {
        List<CategoryChart> charts = new ArrayList<>();
        charts.add(createDummyChart());
        charts.add(createDummyChart());
        SwingWrapper<CategoryChart> wrapper = new SwingWrapper<>(charts);
        wrapper.repaintChart(2); // should throw IndexOutOfBoundsException
    }

    @Test
    public void testSetAndGetWindowTitle() {
        CategoryChart chart = createDummyChart();
        SwingWrapper<CategoryChart> wrapper = new SwingWrapper<>(chart);
        wrapper.setTitle("New Title");
        JFrame frame = wrapper.displayChart();
        Assert.assertEquals(frame.getTitle(), "New Title");
    }


    @Test
    public void testAddNullChartToMatrix() {
        List<CategoryChart> charts = new ArrayList<>();
        charts.add(createDummyChart());
        charts.add(null);
        charts.add(createDummyChart());
        SwingWrapper<CategoryChart> wrapper = new SwingWrapper<>(charts, 1, 3);
        JFrame frame = wrapper.displayChartMatrix();
        Assert.assertNotNull(frame);
        Assert.assertFalse(frame.isVisible());
    }

    @Test
    public void testDisplayEmptyChartMatrix() {
        List<CategoryChart> charts = new ArrayList<>();
        SwingWrapper<CategoryChart> wrapper = new SwingWrapper<>(charts, 1, 1);
        JFrame frame = wrapper.displayChartMatrix();
        Assert.assertNotNull(frame);
        Assert.assertTrue(frame.isVisible());
    }

    @Test
    public void testDisplayChartMatrixWithUnevenGrid() {
        List<CategoryChart> charts = new ArrayList<>();
        charts.add(createDummyChart());
        charts.add(createDummyChart());
        charts.add(createDummyChart());
        SwingWrapper<CategoryChart> wrapper = new SwingWrapper<>(charts, 2, 2);
        JFrame frame = wrapper.displayChartMatrix();
        Assert.assertNotNull(frame);
        Assert.assertTrue(frame.isVisible());
    }

    @Test
    public void testSwingUtilitiesInvocation() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                CategoryChart chart = createDummyChart();
                SwingWrapper<CategoryChart> wrapper = new SwingWrapper<>(chart);
                JFrame frame = wrapper.displayChart();
                Assert.assertNotNull(frame);
                Assert.assertTrue(frame.isVisible());
            }
        });
    }

    @Test
    public void testSwingUtilitiesInvocationMatrix() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                List<CategoryChart> charts = new ArrayList<>();
                charts.add(createDummyChart());
                charts.add(createDummyChart());
                SwingWrapper<CategoryChart> wrapper = new SwingWrapper<>(charts, 1, 2);
                JFrame frame = wrapper.displayChartMatrix();
                Assert.assertNotNull(frame);
                Assert.assertTrue(frame.isVisible());
            }
        });
    }

    @Test
    public void testMultipleChartsGridCalculation() {
        List<CategoryChart> charts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            charts.add(createDummyChart());
        }
        SwingWrapper<CategoryChart> wrapper = new SwingWrapper<>(charts);
        JFrame frame = wrapper.displayChartMatrix();
        Assert.assertNotNull(frame);
        Assert.assertTrue(frame.isVisible());
    }

    @Test
    public void testChartPanelInitialization() {
        List<CategoryChart> charts = new ArrayList<>();
        charts.add(createDummyChart());
        charts.add(createDummyChart());
        SwingWrapper<CategoryChart> wrapper = new SwingWrapper<>(charts);

        // Check that the list is not empty before accessing elements
        if (!charts.isEmpty()) {
            Assert.assertNotNull(wrapper.getXChartPanel(0));
            Assert.assertNotNull(wrapper.getXChartPanel(1));
        } else {
            Assert.fail("The chart list should not be empty.");
        }
    }
}
