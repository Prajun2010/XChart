package org.xchart.testng;

import org.knowm.xchart.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BitmapEncoderTest {

    @Test
    public void testAddFileExtension() {
        String fileName1 = "image";
        String fileName2 = "image.png";
        String fileName3 = "image.PNG";

        Assert.assertEquals(BitmapEncoder.addFileExtension(fileName1, BitmapEncoder.BitmapFormat.PNG), "image.png");
        Assert.assertEquals(BitmapEncoder.addFileExtension(fileName2, BitmapEncoder.BitmapFormat.PNG), "image.png");
        // This part throws error when providing Uppercase format of file extension
        Assert.assertEquals(BitmapEncoder.addFileExtension(fileName3, BitmapEncoder.BitmapFormat.PNG), "image.png");
    }

    @Test
    public void testSaveBitmap() throws IOException {
        BubbleChart chart = createSampleBubbleChart();
        String fileName = "bubble_chart_test.png";

        BitmapEncoder.saveBitmap(chart, fileName, BitmapEncoder.BitmapFormat.PNG);

        File outputFile = new File(fileName);
        Assert.assertTrue(outputFile.exists());
        Assert.assertTrue(outputFile.length() > 0);

        // Clean up
        outputFile.delete();
    }

    @Test
    public void testSaveBitmapWithDPI() throws IOException {
        BubbleChart chart = createSampleBubbleChart();
        String fileName = "bubble_chart_dpi_test.png";
        int dpi = 300;

        BitmapEncoder.saveBitmapWithDPI(chart, fileName, BitmapEncoder.BitmapFormat.PNG, dpi);

        File outputFile = new File(fileName);
        Assert.assertTrue(outputFile.exists());
        Assert.assertTrue(outputFile.length() > 0);

        // Clean up
        outputFile.delete();
    }

    @Test
    public void testSaveJPGWithQuality() throws IOException {
        BubbleChart chart = createSampleBubbleChart();
        String fileName = "bubble_chart_quality_test.jpg";
        float quality = 0.8f;

        BitmapEncoder.saveJPGWithQuality(chart, fileName, quality);

        File outputFile = new File(fileName);
        Assert.assertTrue(outputFile.exists());
        Assert.assertTrue(outputFile.length() > 0);

        // Clean up
        outputFile.delete();
    }

    @Test
    public void testGetBitmapBytes() throws IOException {
        BubbleChart chart = createSampleBubbleChart();

        byte[] bytes = BitmapEncoder.getBitmapBytes(chart, BitmapEncoder.BitmapFormat.PNG);

        Assert.assertNotNull(bytes);
        Assert.assertTrue(bytes.length > 0);
    }

    private BubbleChart createSampleBubbleChart() {
        BubbleChart chart = new BubbleChart(800, 600);

        List<Double> xData = new ArrayList<>();
        List<Double> yData = new ArrayList<>();
        List<Double> bubbleData = new ArrayList<>();
        xData.add(1.0);
        xData.add(2.0);
        xData.add(3.0);
        yData.add(10.0);
        yData.add(20.0);
        yData.add(30.0);
        bubbleData.add(5.0);
        bubbleData.add(10.0);
        bubbleData.add(15.0);

        chart.addSeries("Series1", xData, yData, bubbleData);

        return chart;
    }
}
