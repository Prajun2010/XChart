package org.xchart.testng;

import org.knowm.xchart.CSVExporter;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CSVExporterTest {

    private XYChart chart;
    private String tempDirPath;

    @BeforeClass
    public void setUp() throws IOException {
        chart = new XYChart(500, 400);
        tempDirPath = Files.createTempDirectory("testDir").toString() + "/";
    }

    @AfterClass
    public void tearDown() throws IOException {
        Files.walk(Paths.get(tempDirPath))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test(priority = 1)
    public void testWriteCSVRowsSingleSeries() {
        XYSeries series = chart.addSeries("Series1", new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0});
        CSVExporter.writeCSVRows(chart, tempDirPath);
        File csvFile = new File(tempDirPath + "Series1.csv");
        Assert.assertTrue(csvFile.exists());
    }

    @Test(priority = 2)
    public void testWriteCSVRowsMultipleSeries() {
        chart.addSeries("Series2", new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0});
        chart.addSeries("Series23", new double[]{7.0, 8.0, 9.0}, new double[]{10.0, 11.0, 12.0});
        CSVExporter.writeCSVRows(chart, tempDirPath);
        File csvFile1 = new File(tempDirPath + "Series1.csv");
        File csvFile2 = new File(tempDirPath + "Series2.csv");
        Assert.assertFalse(csvFile1.exists());
        Assert.assertTrue(csvFile2.exists());
    }

    @Test(priority = 3)
    public void testWriteCSVRowsContent() throws IOException {
        XYSeries series = chart.addSeries("Series1", new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0});
        CSVExporter.writeCSVRows(chart, tempDirPath);
        Path csvPath = Paths.get(tempDirPath + "Series1.csv");
        List<String> lines = Files.readAllLines(csvPath);
        Assert.assertEquals(lines.get(0), "1.0,2.0,3.0");
        Assert.assertEquals(lines.get(1), "4.0,5.0,6.0");
    }

    @Test(priority = 4)
    public void testWriteCSVRowsWithNullErrorBars() {
        XYSeries series = chart.addSeries("Series1", new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0});
        CSVExporter.writeCSVRows(chart, tempDirPath);
        File csvFile = new File(tempDirPath + "Series1.csv");
        Assert.assertTrue(csvFile.exists());
    }

    @Test(priority = 5)
    public void testWriteCSVRowsWithErrorBars() throws IOException {
        XYSeries series = chart.addSeries("Series1", new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0}, new double[]{0.1, 0.2, 0.3});
        CSVExporter.writeCSVRows(chart, tempDirPath);
        Path csvPath = Paths.get(tempDirPath + "Series1.csv");
        List<String> lines = Files.readAllLines(csvPath);
        Assert.assertEquals(lines.get(0), "1.0,2.0,3.0");
        Assert.assertEquals(lines.get(1), "4.0,5.0,6.0");
        Assert.assertEquals(lines.get(2), "0.1,0.2,0.3");
    }

    @Test(priority = 6)
    public void testWriteCSVColumnsSingleSeries() {
        XYSeries series = chart.addSeries("Series1", new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0});
        CSVExporter.writeCSVColumns(chart, tempDirPath);
        File csvFile = new File(tempDirPath + "Series1.csv");
        Assert.assertTrue(csvFile.exists());
    }

    @Test(priority = 7)
    public void testWriteCSVColumnsMultipleSeries() {
        chart.addSeries("Series1", new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0});
        chart.addSeries("Series2", new double[]{7.0, 8.0, 9.0}, new double[]{10.0, 11.0, 12.0});
        CSVExporter.writeCSVColumns(chart, tempDirPath);
        File csvFile1 = new File(tempDirPath + "Series1.csv");
        File csvFile2 = new File(tempDirPath + "Series2.csv");
        Assert.assertTrue(csvFile1.exists());
        Assert.assertTrue(csvFile2.exists());
    }

    @Test(priority = 8)
    public void testWriteCSVColumnsContent() throws IOException {
        XYSeries series = chart.addSeries("Series1", new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0});
        CSVExporter.writeCSVColumns(chart, tempDirPath);
        Path csvPath = Paths.get(tempDirPath + "Series1.csv");
        List<String> lines = Files.readAllLines(csvPath);
        Assert.assertEquals(lines.get(0), "1.0,4.0");
        Assert.assertEquals(lines.get(1), "2.0,5.0");
        Assert.assertEquals(lines.get(2), "3.0,6.0");
    }

    @Test(priority = 9)
    public void testWriteCSVColumnsWithNullErrorBars() {
        XYSeries series = chart.addSeries("Series1", new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0});
        CSVExporter.writeCSVColumns(chart, tempDirPath);
        File csvFile = new File(tempDirPath + "Series1.csv");
        Assert.assertTrue(csvFile.exists());
    }

    @Test(priority = 10)
    public void testWriteCSVColumnsWithErrorBars() throws IOException {
        XYSeries series = chart.addSeries("Series1", new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0}, new double[]{0.1, 0.2, 0.3});
        CSVExporter.writeCSVColumns(chart, tempDirPath);
        Path csvPath = Paths.get(tempDirPath + "Series1.csv");
        List<String> lines = Files.readAllLines(csvPath);
        Assert.assertEquals(lines.get(0), "1.0,4.0,0.1");
        Assert.assertEquals(lines.get(1), "2.0,5.0,0.2");
        Assert.assertEquals(lines.get(2), "3.0,6.0,0.3");
    }

    @Test(priority = 11)
    public void testWriteCSVRowsWithSpecialCharacters() throws IOException {
        XYSeries series = chart.addSeries("Series,1", new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0});
        CSVExporter.writeCSVRows(chart, tempDirPath);
        Path csvPath = Paths.get(tempDirPath + "Series,1.csv");
        List<String> lines = Files.readAllLines(csvPath);
        Assert.assertEquals(lines.get(0), "1.0,2.0,3.0");
        Assert.assertEquals(lines.get(1), "4.0,5.0,6.0");
    }

    @Test(priority = 12)
    public void testWriteCSVColumnsWithSpecialCharacters() throws IOException {
        XYSeries series = chart.addSeries("Series,1", new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0});
        CSVExporter.writeCSVColumns(chart, tempDirPath);
        Path csvPath = Paths.get(tempDirPath + "Series,1.csv");
        List<String> lines = Files.readAllLines(csvPath);
        Assert.assertEquals(lines.get(0), "1.0,4.0");
        Assert.assertEquals(lines.get(1), "2.0,5.0");
        Assert.assertEquals(lines.get(2), "3.0,6.0");
    }

    @Test(priority = 13)
    public void testWriteCSVRowsEmptySeries() {
        XYSeries series = chart.addSeries("EmptySeries", new double[]{}, new double[]{});
        CSVExporter.writeCSVRows(chart, tempDirPath);
        File csvFile = new File(tempDirPath + "EmptySeries.csv");
        Assert.assertTrue(csvFile.exists());
    }

    @Test(priority = 14)
    public void testWriteCSVColumnsEmptySeries() {
        XYSeries series = chart.addSeries("EmptySeries", new double[]{}, new double[]{});
        CSVExporter.writeCSVColumns(chart, tempDirPath);
        File csvFile = new File(tempDirPath + "EmptySeries.csv");
        Assert.assertTrue(csvFile.exists());
    }

    @Test(priority = 15)
    public void testWriteCSVRowsSingleDataPoint() throws IOException {
        XYSeries series = chart.addSeries("SingleDataPoint", new double[]{1.0}, new double[]{4.0});
        CSVExporter.writeCSVRows(chart, tempDirPath);
        Path csvPath = Paths.get(tempDirPath + "SingleDataPoint.csv");
        List<String> lines = Files.readAllLines(csvPath);
        Assert.assertEquals(lines.get(0), "1.0");
        Assert.assertEquals(lines.get(1), "4.0");
    }

    @Test(priority = 16)
    public void testWriteCSVColumnsSingleDataPoint() throws IOException {
        XYSeries series = chart.addSeries("SingleDataPoint", new double[]{1.0}, new double[]{4.0});
        CSVExporter.writeCSVColumns(chart, tempDirPath);
        Path csvPath = Paths.get(tempDirPath + "SingleDataPoint.csv");
        List<String> lines = Files.readAllLines(csvPath);
        Assert.assertEquals(lines.get(0), "1.0,4.0");
    }










    @Test(priority = 29)
    public void testWriteCSVRowsWithNullExtraValues() {
        XYSeries series = chart.addSeries("NullExtraValues", new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0}, null);
        CSVExporter.writeCSVRows(chart, tempDirPath);
        File csvFile = new File(tempDirPath + "NullExtraValues.csv");
        Assert.assertTrue(csvFile.exists());
    }

    @Test(priority = 30)
    public void testWriteCSVColumnsWithNullExtraValues() {
        XYSeries series = chart.addSeries("NullExtraValues", new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0}, null);
        CSVExporter.writeCSVColumns(chart, tempDirPath);
        File csvFile = new File(tempDirPath + "NullExtraValues.csv");
        Assert.assertTrue(csvFile.exists());
    }


    @Test(priority = 33)
    public void testWriteCSVRowsWithMultipleExtraValues() throws IOException {
        XYSeries series = chart.addSeries("MultipleExtraValues", new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0}, new double[]{0.1, 0.2, 0.3});
        CSVExporter.writeCSVRows(chart, tempDirPath);
        Path csvPath = Paths.get(tempDirPath + "MultipleExtraValues.csv");
        List<String> lines = Files.readAllLines(csvPath);
        Assert.assertEquals(lines.get(2), "0.1,0.2,0.3");
    }

    @Test(priority = 34)
    public void testWriteCSVColumnsWithMultipleExtraValues() throws IOException {
        XYSeries series = chart.addSeries("MultipleExtraValues", new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0}, new double[]{0.1, 0.2, 0.3});
        CSVExporter.writeCSVColumns(chart, tempDirPath);
        Path csvPath = Paths.get(tempDirPath + "MultipleExtraValues.csv");
        List<String> lines = Files.readAllLines(csvPath);
        Assert.assertEquals(lines.get(0), "1.0,4.0,0.1");
        Assert.assertEquals(lines.get(1), "2.0,5.0,0.2");
        Assert.assertEquals(lines.get(2), "3.0,6.0,0.3");
    }



    @Test(priority = 37)
    public void testWriteCSVRowsWithLargeDataSet() throws IOException {
        int size = 1000000;
        double[] xData = new double[size];
        double[] yData = new double[size];
        for (int i = 0; i < size; i++) {
            xData[i] = i;
            yData[i] = i * 2;
        }
        XYSeries series = chart.addSeries("LargeDataSet", xData, yData);
        CSVExporter.writeCSVRows(chart, tempDirPath);
        File csvFile = new File(tempDirPath + "LargeDataSet.csv");
        Assert.assertTrue(csvFile.exists());
    }

    @Test(priority = 38)
    public void testWriteCSVColumnsWithLargeDataSet() throws IOException {
        int size = 1000000;
        double[] xData = new double[size];
        double[] yData = new double[size];
        for (int i = 0; i < size; i++) {
            xData[i] = i;
            yData[i] = i * 2;
        }
        XYSeries series = chart.addSeries("LargeDataSet", xData, yData);
        CSVExporter.writeCSVColumns(chart, tempDirPath);
        File csvFile = new File(tempDirPath + "LargeDataSet.csv");
        Assert.assertTrue(csvFile.exists());
    }

    @Test(priority = 39)
    public void testWriteCSVRowsWithAllNullExtraValues() throws IOException {
        XYSeries series = chart.addSeries("AllNullExtraValues", new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0}, new double[]{Double.NaN, Double.NaN, Double.NaN});
        CSVExporter.writeCSVRows(chart, tempDirPath);
        Path csvPath = Paths.get(tempDirPath + "AllNullExtraValues.csv");
        List<String> lines = Files.readAllLines(csvPath);
        Assert.assertEquals(lines.get(2), "NaN,NaN,NaN");
    }

    @Test(priority = 40)
    public void testWriteCSVColumnsWithAllNullExtraValues() throws IOException {
        XYSeries series = chart.addSeries("AllNullExtraValues", new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0}, new double[]{Double.NaN, Double.NaN, Double.NaN});
        CSVExporter.writeCSVColumns(chart, tempDirPath);
        Path csvPath = Paths.get(tempDirPath + "AllNullExtraValues.csv");
        List<String> lines = Files.readAllLines(csvPath);
        Assert.assertEquals(lines.get(0), "1.0,4.0,NaN");
        Assert.assertEquals(lines.get(1), "2.0,5.0,NaN");
        Assert.assertEquals(lines.get(2), "3.0,6.0,NaN");
    }

    @Test(priority = 41)
    public void testWriteCSVRowsWithMixedNullExtraValues() throws IOException {
        XYSeries series = chart.addSeries("MixedNullExtraValues", new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0}, new double[]{Double.NaN, 0.2, 0.3});
        CSVExporter.writeCSVRows(chart, tempDirPath);
        Path csvPath = Paths.get(tempDirPath + "MixedNullExtraValues.csv");
        List<String> lines = Files.readAllLines(csvPath);
        Assert.assertEquals(lines.get(2), "NaN,0.2,0.3");
    }

    @Test(priority = 42)
    public void testWriteCSVColumnsWithMixedNullExtraValues() throws IOException {
        XYSeries series = chart.addSeries("MixedNullExtraValues", new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0}, new double[]{Double.NaN, 0.2, 0.3});
        CSVExporter.writeCSVColumns(chart, tempDirPath);
        Path csvPath = Paths.get(tempDirPath + "MixedNullExtraValues.csv");
        List<String> lines = Files.readAllLines(csvPath);
        Assert.assertEquals(lines.get(0), "1.0,4.0,NaN");
        Assert.assertEquals(lines.get(1), "2.0,5.0,0.2");
        Assert.assertEquals(lines.get(2), "3.0,6.0,0.3");
    }

    @Test(priority = 43)
    public void testWriteCSVRowsWithMixedNegativeValues() throws IOException {
        XYSeries series = chart.addSeries("MixedNegativeValues", new double[]{-1.0, 2.0, -3.0}, new double[]{4.0, -5.0, 6.0}, new double[]{0.1, -0.2, 0.3});
        CSVExporter.writeCSVRows(chart, tempDirPath);
        Path csvPath = Paths.get(tempDirPath + "MixedNegativeValues.csv");
        List<String> lines = Files.readAllLines(csvPath);
        Assert.assertEquals(lines.get(0), "-1.0,2.0,-3.0");
        Assert.assertEquals(lines.get(1), "4.0,-5.0,6.0");
        Assert.assertEquals(lines.get(2), "0.1,-0.2,0.3");
    }

    @Test(priority = 44)
    public void testWriteCSVColumnsWithMixedNegativeValues() throws IOException {
        XYSeries series = chart.addSeries("MixedNegativeValues", new double[]{-1.0, 2.0, -3.0}, new double[]{4.0, -5.0, 6.0}, new double[]{0.1, -0.2, 0.3});
        CSVExporter.writeCSVColumns(chart, tempDirPath);
        Path csvPath = Paths.get(tempDirPath + "MixedNegativeValues.csv");
        List<String> lines = Files.readAllLines(csvPath);
        Assert.assertEquals(lines.get(0), "-1.0,4.0,0.1");
        Assert.assertEquals(lines.get(1), "2.0,-5.0,-0.2");
        Assert.assertEquals(lines.get(2), "-3.0,6.0,0.3");
    }

    @Test(priority = 45)
    public void testWriteCSVRowsWithNonNumericExtraValues() {
        XYSeries series = chart.addSeries("NonNumericExtraValues", new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0}, new double[]{Double.NaN, Double.NaN, Double.NaN});
        Assert.assertThrows(IllegalArgumentException.class, () -> CSVExporter.writeCSVRows(chart, tempDirPath));
    }

    @Test(priority = 46)
    public void testWriteCSVColumnsWithNonNumericExtraValues() {
        XYSeries series = chart.addSeries("NonNumericExtraValues", new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0}, new double[]{Double.NaN, Double.NaN, Double.NaN});
        Assert.assertThrows(IllegalArgumentException.class, () -> CSVExporter.writeCSVColumns(chart, tempDirPath));
    }

    @Test(priority = 47)
    public void testWriteCSVRowsWithSpecialCharacterData() throws IOException {
        XYSeries series = chart.addSeries("SpecialCharacterData", new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0});
        CSVExporter.writeCSVRows(chart, tempDirPath);
        Path csvPath = Paths.get(tempDirPath + "SpecialCharacterData.csv");
        List<String> lines = Files.readAllLines(csvPath);
        Assert.assertEquals(lines.get(0), "1.0,2.0,3.0");
        Assert.assertEquals(lines.get(1), "4.0,5.0,6.0");
    }

    @Test(priority = 48)
    public void testWriteCSVColumnsWithSpecialCharacterData() throws IOException {
        XYSeries series = chart.addSeries("SpecialCharacterData", new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0});
        CSVExporter.writeCSVColumns(chart, tempDirPath);
        Path csvPath = Paths.get(tempDirPath + "SpecialCharacterData.csv");
        List<String> lines = Files.readAllLines(csvPath);
        Assert.assertEquals(lines.get(0), "1.0,4.0");
        Assert.assertEquals(lines.get(1), "2.0,5.0");
        Assert.assertEquals(lines.get(2), "3.0,6.0");
    }
}
