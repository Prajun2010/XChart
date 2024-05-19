package org.xchart.testng;

import org.knowm.xchart.CSVImporter;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.style.Styler;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CSVImporterTest {

    private String tempDirPath = "./testCSV/";

    private Object getPrivateField(Object object, String fieldName, boolean superAccess) throws Exception {

        Field field = getField(object.getClass(), fieldName, superAccess);
        field.setAccessible(true);
        return field.get(object);
    }

    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        Field field = getField(object.getClass(), fieldName, false);
        field.setAccessible(true);
        field.set(object, value);
    }

    private Field getField(Class<?> clazz, String fieldName, boolean superAccess) throws NoSuchFieldException {

        while (clazz != null) {
            try {
                if(superAccess){
                    return clazz.getSuperclass().getDeclaredField(fieldName);
                }
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }



    private Object invokePrivateMethod(boolean superAccess, Object object, String methodName, Object... args) throws Exception {
        Method method = getMethod(superAccess,  object.getClass(), methodName);
        method.setAccessible(true);
        return method.invoke(object, args);
    }

    private Method getMethod(boolean superAccess, Class<?> clazz, String methodName, Class<?>... parameterTypes ) throws NoSuchMethodException {
        while (clazz != null) {


            try {
                if(superAccess){
                    return clazz.getSuperclass().getDeclaredMethod(methodName, parameterTypes);
                }
                return clazz.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchMethodException(methodName);
    }


    @BeforeClass
    public void setUp() throws IOException {
        // Create test directory
        Files.createDirectories(Paths.get(tempDirPath));

        // Create sample CSV files for testing
        createSampleCSVFile("series1.csv", "1.0,2.0,3.0\n4.0,5.0,6.0\n");
        createSampleCSVFile("series2.csv", "7.0,8.0,9.0\n10.0,11.0,12.0\n");
        createSampleCSVFile("series3.csv", "13.0,14.0,15.0\n16.0,17.0,18.0\n");
    }

    @AfterClass
    public void tearDown() throws IOException {
        // Delete test directory
        Files.walk(Paths.get(tempDirPath))
                .map(Path::toFile)
                .forEach(File::delete);
    }

    private void createSampleCSVFile(String fileName, String content) throws IOException {
        FileWriter writer = new FileWriter(tempDirPath + fileName);
        writer.write(content);
        writer.close();
    }

    @Test(priority = 1)
    public void testGetChartFromCSVDirRows() {
        XYChart chart = CSVImporter.getChartFromCSVDir(tempDirPath, CSVImporter.DataOrientation.Rows, 800, 600, Styler.ChartTheme.GGPlot2);
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getSeriesMap().size(), 3);
    }

    @Test(priority = 2)
    public void testGetChartFromCSVDirColumns() {
        XYChart chart = CSVImporter.getChartFromCSVDir(tempDirPath, CSVImporter.DataOrientation.Columns, 800, 600, Styler.ChartTheme.Matlab);
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getSeriesMap().size(), 3);
    }

    @Test(priority = 3)
    public void testGetChartFromCSVDirDefaultTheme() {
        XYChart chart = CSVImporter.getChartFromCSVDir(tempDirPath, CSVImporter.DataOrientation.Rows, 800, 600);
        Assert.assertNotNull(chart);
        Assert.assertEquals(chart.getSeriesMap().size(), 3);
    }

    @Test(priority = 4)
    public void testGetSeriesDataFromCSVFileRows() {
        CSVImporter.SeriesData seriesData = CSVImporter.getSeriesDataFromCSVFile(tempDirPath + "series1.csv", CSVImporter.DataOrientation.Rows);
        Assert.assertNotNull(seriesData);
        Assert.assertEquals(seriesData.getSeriesName(), "series1");
        Assert.assertEquals(seriesData.getxAxisData().size(), 3);
        Assert.assertEquals(seriesData.getyAxisData().size(), 3);
    }

    @Test(priority = 5)
    public void testGetSeriesDataFromCSVFileColumns() {
        CSVImporter.SeriesData seriesData = CSVImporter.getSeriesDataFromCSVFile(tempDirPath + "series1.csv", CSVImporter.DataOrientation.Columns);
        Assert.assertNotNull(seriesData);
        Assert.assertEquals(seriesData.getSeriesName(), "series1");
        Assert.assertEquals(seriesData.getxAxisData().size(), 2);
        Assert.assertEquals(seriesData.getyAxisData().size(), 2);
    }

    @Test(priority = 6)
    public void testGetSeriesDataWithExtraValuesRows() throws IOException {
        createSampleCSVFile("seriesWithExtra.csv", "1.0,2.0,3.0\n4.0,5.0,6.0\n7.0,8.0,9.0\n");
        CSVImporter.SeriesData seriesData = CSVImporter.getSeriesDataFromCSVFile(tempDirPath + "seriesWithExtra.csv", CSVImporter.DataOrientation.Rows);
        Assert.assertNotNull(seriesData);
        Assert.assertEquals(seriesData.getxAxisData().size(), 3);
        Assert.assertEquals(seriesData.getyAxisData().size(), 3);
    }

    @Test(priority = 7)
    public void testGetSeriesDataWithExtraValuesColumns() throws IOException {
        createSampleCSVFile("seriesWithExtraColumns.csv", "1.0,4.0,7.0\n2.0,5.0,8.0\n3.0,6.0,9.0\n");
        CSVImporter.SeriesData seriesData = CSVImporter.getSeriesDataFromCSVFile(tempDirPath + "seriesWithExtraColumns.csv", CSVImporter.DataOrientation.Columns);
        Assert.assertNotNull(seriesData);
        Assert.assertEquals(seriesData.getxAxisData().size(), 3);
        Assert.assertEquals(seriesData.getyAxisData().size(), 3);
    }




    @Test(priority = 14)
    public void testInvalidDirectoryPath() {
        XYChart chart = CSVImporter.getChartFromCSVDir("./invalidPath/", CSVImporter.DataOrientation.Rows, 800, 600);
        Assert.assertEquals(chart.getSeriesMap().size(), 0);
    }

    @Test(priority = 15)
    public void testEmptyDirectory() throws IOException {
        String emptyDirPath = tempDirPath + "emptyDir/";
        Files.createDirectories(Paths.get(emptyDirPath));
        XYChart chart = CSVImporter.getChartFromCSVDir(emptyDirPath, CSVImporter.DataOrientation.Rows, 800, 600);
        Assert.assertEquals(chart.getSeriesMap().size(), 0);
        Files.delete(Paths.get(emptyDirPath));
    }

    @Test(priority = 16)
    public void testGetChartWithInvalidCSVContent() throws IOException {
        createSampleCSVFile("invalid.csv", "invalid,data\n");
        try {
            XYChart chart = CSVImporter.getChartFromCSVDir(tempDirPath, CSVImporter.DataOrientation.Rows, 800, 600);
            Assert.fail("Expected NumberFormatException");
        } catch (NumberFormatException e) {
            Assert.assertTrue(true);
        }
    }

    @Test(priority = 17)
    public void testGetChartWithMixedValidInvalidCSV() throws IOException {
        createSampleCSVFile("mixed.csv", "1.0,2.0\ninvalid,data\n");
        try {
            XYChart chart = CSVImporter.getChartFromCSVDir(tempDirPath, CSVImporter.DataOrientation.Rows, 800, 600);
            Assert.fail("Expected NumberFormatException");
        } catch (NumberFormatException e) {
            Assert.assertTrue(true);
        }
    }

    @Test(priority = 18)
    public void testGetSeriesDataFromCSVWithHeaders() throws IOException {
        createSampleCSVFile("header.csv", "X,Y\n1.0,2.0\n3.0,4.0\n");
        CSVImporter.SeriesData seriesData = CSVImporter.getSeriesDataFromCSVFile(tempDirPath + "header.csv", CSVImporter.DataOrientation.Rows);
        Assert.assertNotNull(seriesData);
        Assert.assertEquals(seriesData.getxAxisData().size(), 2);
        Assert.assertEquals(seriesData.getyAxisData().size(), 2);
    }

    @Test(priority = 19)
    public void testGetSeriesDataFromCSVWithWhitespace() throws IOException {
        createSampleCSVFile("whitespace.csv", " 1.0 , 2.0 \n 3.0 , 4.0 \n");
        CSVImporter.SeriesData seriesData = CSVImporter.getSeriesDataFromCSVFile(tempDirPath + "whitespace.csv", CSVImporter.DataOrientation.Rows);
        Assert.assertNotNull(seriesData);
        Assert.assertEquals(seriesData.getxAxisData().size(), 2);
        Assert.assertEquals(seriesData.getyAxisData().size(), 2);
    }

    @Test(priority = 20)
    public void testGetSeriesDataFromCSVWithExtraColumns() throws IOException {
        createSampleCSVFile("extraColumns.csv", "1.0,2.0,3.0,4.0\n5.0,6.0,7.0,8.0\n");
        CSVImporter.SeriesData seriesData = CSVImporter.getSeriesDataFromCSVFile(tempDirPath + "extraColumns.csv", CSVImporter.DataOrientation.Rows);
        Assert.assertNotNull(seriesData);
        Assert.assertEquals(seriesData.getxAxisData().size(), 4);
        Assert.assertEquals(seriesData.getyAxisData().size(), 4);
    }

    @Test(priority = 21)
    public void testGetChartWithEmptyFile() throws IOException {
        createSampleCSVFile("empty.csv", "");
        XYChart chart = CSVImporter.getChartFromCSVDir(tempDirPath, CSVImporter.DataOrientation.Rows, 800, 600);
        Assert.assertEquals(chart.getSeriesMap().size(), 0);
    }

    @Test(priority = 22)
    public void testGetChartWithNullFilePath() {
        try {
            CSVImporter.getChartFromCSVDir(null, CSVImporter.DataOrientation.Rows, 800, 600);
            Assert.fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            Assert.assertTrue(true);
        }
    }

    @Test(priority = 23)
    public void testGetSeriesDataFromCSVWithDifferentLineEndings() throws IOException {
        createSampleCSVFile("lineEndings.csv", "1.0,2.0\r\n3.0,4.0\n5.0,6.0\r\n");
        CSVImporter.SeriesData seriesData = CSVImporter.getSeriesDataFromCSVFile(tempDirPath + "lineEndings.csv", CSVImporter.DataOrientation.Rows);
        Assert.assertNotNull(seriesData);
        Assert.assertEquals(seriesData.getxAxisData().size(), 3);
        Assert.assertEquals(seriesData.getyAxisData().size(), 3);
    }

    @Test(priority = 24)
    public void testGetSeriesDataFromCSVWithQuotes() throws IOException {
        createSampleCSVFile("quotes.csv", "\"1.0\",\"2.0\"\n\"3.0\",\"4.0\"\n");
        CSVImporter.SeriesData seriesData = CSVImporter.getSeriesDataFromCSVFile(tempDirPath + "quotes.csv", CSVImporter.DataOrientation.Rows);
        Assert.assertNotNull(seriesData);
        Assert.assertEquals(seriesData.getxAxisData().size(), 2);
        Assert.assertEquals(seriesData.getyAxisData().size(), 2);
    }

    @Test(priority = 25)
    public void testGetSeriesDataFromCSVWithMissingValues() throws IOException {
        createSampleCSVFile("missingValues.csv", "1.0,\n,2.0\n");
        CSVImporter.SeriesData seriesData = CSVImporter.getSeriesDataFromCSVFile(tempDirPath + "missingValues.csv", CSVImporter.DataOrientation.Rows);
        Assert.assertNotNull(seriesData);
        Assert.assertEquals(seriesData.getxAxisData().size(), 2);
        Assert.assertEquals(seriesData.getyAxisData().size(), 2);
    }

    @Test(priority = 26)
    public void testGetSeriesDataFromCSVWithSingleValue() throws IOException {
        createSampleCSVFile("singleValue.csv", "1.0\n");
        CSVImporter.SeriesData seriesData = CSVImporter.getSeriesDataFromCSVFile(tempDirPath + "singleValue.csv", CSVImporter.DataOrientation.Rows);
        Assert.assertNotNull(seriesData);
        Assert.assertEquals(seriesData.getxAxisData().size(), 1);
        Assert.assertEquals(seriesData.getyAxisData().size(), 1);
    }

    @Test(priority = 27)
    public void testGetSeriesDataFromCSVWithMultipleEmptyLines() throws IOException {
        createSampleCSVFile("multipleEmptyLines.csv", "1.0,2.0\n\n\n3.0,4.0\n\n");
        CSVImporter.SeriesData seriesData = CSVImporter.getSeriesDataFromCSVFile(tempDirPath + "multipleEmptyLines.csv", CSVImporter.DataOrientation.Rows);
        Assert.assertNotNull(seriesData);
        Assert.assertEquals(seriesData.getxAxisData().size(), 2);
        Assert.assertEquals(seriesData.getyAxisData().size(), 2);
    }

    @Test(priority = 28)
    public void testGetSeriesDataFromCSVWithDifferentDelimiters() throws IOException {
        createSampleCSVFile("differentDelimiters.csv", "1.0;2.0\n3.0;4.0\n");
        try {
            CSVImporter.SeriesData seriesData = CSVImporter.getSeriesDataFromCSVFile(tempDirPath + "differentDelimiters.csv", CSVImporter.DataOrientation.Rows);
            Assert.fail("Expected ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            Assert.assertTrue(true);
        }
    }

    @Test(priority = 29)
    public void testGetSeriesDataFromCSVWithMixedDelimiters() throws IOException {
        createSampleCSVFile("mixedDelimiters.csv", "1.0,2.0\n3.0;4.0\n");
        try {
            CSVImporter.SeriesData seriesData = CSVImporter.getSeriesDataFromCSVFile(tempDirPath + "mixedDelimiters.csv", CSVImporter.DataOrientation.Rows);
            Assert.fail("Expected ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            Assert.assertTrue(true);
        }
    }

    @Test(priority = 30)
    public void testGetSeriesDataFromCSVWithComments() throws IOException {
        createSampleCSVFile("comments.csv", "# This is a comment\n1.0,2.0\n3.0,4.0\n");
        CSVImporter.SeriesData seriesData = CSVImporter.getSeriesDataFromCSVFile(tempDirPath + "comments.csv", CSVImporter.DataOrientation.Rows);
        Assert.assertNotNull(seriesData);
        Assert.assertEquals(seriesData.getxAxisData().size(), 2);
        Assert.assertEquals(seriesData.getyAxisData().size(), 2);
    }

    @Test(priority = 31)
    public void testGetSeriesDataFromCSVWithSpaces() throws IOException {
        createSampleCSVFile("spaces.csv", " 1.0 , 2.0 \n 3.0 , 4.0 \n");
        CSVImporter.SeriesData seriesData = CSVImporter.getSeriesDataFromCSVFile(tempDirPath + "spaces.csv", CSVImporter.DataOrientation.Rows);
        Assert.assertNotNull(seriesData);
        Assert.assertEquals(seriesData.getxAxisData().size(), 2);
        Assert.assertEquals(seriesData.getyAxisData().size(), 2);
    }

    @Test(priority = 32)
    public void testGetSeriesDataFromCSVWithTabs() throws IOException {
        createSampleCSVFile("tabs.csv", "1.0\t2.0\n3.0\t4.0\n");
        try {
            CSVImporter.SeriesData seriesData = CSVImporter.getSeriesDataFromCSVFile(tempDirPath + "tabs.csv", CSVImporter.DataOrientation.Rows);
            Assert.fail("Expected ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            Assert.assertTrue(true);
        }
    }

    @Test(priority = 33)
    public void testGetSeriesDataFromCSVWithEmptyCells() throws IOException {
        createSampleCSVFile("emptyCells.csv", "1.0,,2.0\n,3.0,4.0\n");
        CSVImporter.SeriesData seriesData = CSVImporter.getSeriesDataFromCSVFile(tempDirPath + "emptyCells.csv", CSVImporter.DataOrientation.Rows);
        Assert.assertNotNull(seriesData);
        Assert.assertEquals(seriesData.getxAxisData().size(), 3);
        Assert.assertEquals(seriesData.getyAxisData().size(), 3);
    }

    @Test(priority = 34)
    public void testGetSeriesDataFromCSVWithExtraCommas() throws IOException {
        createSampleCSVFile("extraCommas.csv", "1.0,2.0,,\n3.0,4.0,,\n");
        CSVImporter.SeriesData seriesData = CSVImporter.getSeriesDataFromCSVFile(tempDirPath + "extraCommas.csv", CSVImporter.DataOrientation.Rows);
        Assert.assertNotNull(seriesData);
        Assert.assertEquals(seriesData.getxAxisData().size(), 4);
        Assert.assertEquals(seriesData.getyAxisData().size(), 4);
    }

    // Continued from previous snippet...

    @Test(priority = 36)
    public void testGetSeriesDataFromCSVWithNegativeValues() throws IOException {
        createSampleCSVFile("negativeValues.csv", "-1.0,-2.0\n-3.0,-4.0\n");
        CSVImporter.SeriesData seriesData = CSVImporter.getSeriesDataFromCSVFile(tempDirPath + "negativeValues.csv", CSVImporter.DataOrientation.Rows);
        Assert.assertNotNull(seriesData);
        Assert.assertEquals(seriesData.getxAxisData().size(), 2);
        Assert.assertEquals(seriesData.getyAxisData().size(), 2);
    }

    @Test(priority = 37)
    public void testGetSeriesDataFromCSVWithLargeValues() throws IOException {
        createSampleCSVFile("largeValues.csv", "1000000.0,2000000.0\n3000000.0,4000000.0\n");
        CSVImporter.SeriesData seriesData = CSVImporter.getSeriesDataFromCSVFile(tempDirPath + "largeValues.csv", CSVImporter.DataOrientation.Rows);
        Assert.assertNotNull(seriesData);
        Assert.assertEquals(seriesData.getxAxisData().size(), 2);
        Assert.assertEquals(seriesData.getyAxisData().size(), 2);
    }





    @Test(priority = 40)
    public void testGetChartFromCSVDirWithDefaultTheme() {
        XYChart chart = CSVImporter.getChartFromCSVDir(tempDirPath, CSVImporter.DataOrientation.Rows, 800, 600);
        Assert.assertNotNull(chart);
    }

    @Test(priority = 41)
    public void testGetChartFromCSVDirWithInvalidTheme() {
        XYChart chart = CSVImporter.getChartFromCSVDir(tempDirPath, CSVImporter.DataOrientation.Rows, 800, 600, null);
        Assert.assertNotNull(chart);
    }

    @Test(priority = 42)
    public void testGetChartFromCSVDirWithEmptyDirectory() throws IOException {
        String emptyDirPath = tempDirPath + "emptyDir/";
        Files.createDirectories(Paths.get(emptyDirPath));
        XYChart chart = CSVImporter.getChartFromCSVDir(emptyDirPath, CSVImporter.DataOrientation.Rows, 800, 600);
        Assert.assertEquals(chart.getSeriesMap().size(), 0);
        Files.delete(Paths.get(emptyDirPath));
    }

    @Test(priority = 43)
    public void testGetChartFromCSVDirWithInvalidCSVFiles() throws IOException {
        createSampleCSVFile("invalid1.csv", "invalid,data\n");
        createSampleCSVFile("invalid2.csv", "1.0,2.0\ninvalid,data\n");
        XYChart chart = CSVImporter.getChartFromCSVDir(tempDirPath, CSVImporter.DataOrientation.Rows, 800, 600);
        Assert.assertEquals(chart.getSeriesMap().size(), 0);
    }

    @Test(priority = 44)
    public void testGetChartFromCSVDirWithMixedValidInvalidCSVFiles() throws IOException {
        createSampleCSVFile("valid1.csv", "1.0,2.0\n3.0,4.0\n");
        createSampleCSVFile("invalid1.csv", "invalid,data\n");
        XYChart chart = CSVImporter.getChartFromCSVDir(tempDirPath, CSVImporter.DataOrientation.Rows, 800, 600);
        Assert.assertEquals(chart.getSeriesMap().size(), 1);
    }

    @Test(priority = 45)
    public void testGetChartFromCSVDirWithFileContainingHeaders() throws IOException {
        createSampleCSVFile("header.csv", "X,Y\n1.0,2.0\n3.0,4.0\n");
        XYChart chart = CSVImporter.getChartFromCSVDir(tempDirPath, CSVImporter.DataOrientation.Rows, 800, 600);
        Assert.assertEquals(chart.getSeriesMap().size(), 1);
    }

    @Test(priority = 46)
    public void testGetChartFromCSVDirWithFileContainingWhitespace() throws IOException {
        createSampleCSVFile("whitespace.csv", " 1.0 , 2.0 \n 3.0 , 4.0 \n");
        XYChart chart = CSVImporter.getChartFromCSVDir(tempDirPath, CSVImporter.DataOrientation.Rows, 800, 600);
        Assert.assertEquals(chart.getSeriesMap().size(), 1);
    }

    @Test(priority = 47)
    public void testGetChartFromCSVDirWithFileContainingExtraColumns() throws IOException {
        createSampleCSVFile("extraColumns.csv", "1.0,2.0,3.0,4.0\n5.0,6.0,7.0,8.0\n");
        XYChart chart = CSVImporter.getChartFromCSVDir(tempDirPath, CSVImporter.DataOrientation.Rows, 800, 600);
        Assert.assertEquals(chart.getSeriesMap().size(), 1);
    }

    @Test(priority = 48)
    public void testGetChartFromCSVDirWithEmptyFile() throws IOException {
        createSampleCSVFile("empty.csv", "");
        XYChart chart = CSVImporter.getChartFromCSVDir(tempDirPath, CSVImporter.DataOrientation.Rows, 800, 600);
        Assert.assertEquals(chart.getSeriesMap().size(), 0);
    }

    @Test(priority = 49)
    public void testGetChartFromCSVDirWithInvalidDirectoryPath() {
        XYChart chart = CSVImporter.getChartFromCSVDir("./invalidPath/", CSVImporter.DataOrientation.Rows, 800, 600);
        Assert.assertEquals(chart.getSeriesMap().size(), 0);
    }

    @Test(priority = 50)
    public void testGetChartFromCSVDirWithNullFilePath() {
        try {
            CSVImporter.getChartFromCSVDir(null, CSVImporter.DataOrientation.Rows, 800, 600);
            Assert.fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            Assert.assertTrue(true);
        }
    }

    @Test(priority = 51)
    public void testGetChartFromCSVDirWithFilesInSubdirectories() throws IOException {
        Files.createDirectories(Paths.get(tempDirPath + "subdir/"));
        createSampleCSVFile("subdir/series4.csv", "1.0,2.0\n3.0,4.0\n");
        XYChart chart = CSVImporter.getChartFromCSVDir(tempDirPath, CSVImporter.DataOrientation.Rows, 800, 600);
        Assert.assertEquals(chart.getSeriesMap().size(), 4);
        Files.delete(Paths.get(tempDirPath + "subdir/series4.csv"));
        Files.delete(Paths.get(tempDirPath + "subdir/"));
    }

    @Test(priority = 52)
    public void testGetChartFromCSVDirWithHiddenFiles() throws IOException {
        createSampleCSVFile(".hidden.csv", "1.0,2.0\n3.0,4.0\n");
        XYChart chart = CSVImporter.getChartFromCSVDir(tempDirPath, CSVImporter.DataOrientation.Rows, 800, 600);
        Assert.assertEquals(chart.getSeriesMap().size(), 0);
        Files.delete(Paths.get(tempDirPath + ".hidden.csv"));
    }

    @Test(priority = 53)
    public void testGetChartFromCSVDirWithDifferentFileExtensions() throws IOException {
        createSampleCSVFile("series5.txt", "1.0,2.0\n3.0,4.0\n");
        XYChart chart = CSVImporter.getChartFromCSVDir(tempDirPath, CSVImporter.DataOrientation.Rows, 800, 600);
        Assert.assertEquals(chart.getSeriesMap().size(), 0);
        Files.delete(Paths.get(tempDirPath + "series5.txt"));
    }

    @Test(priority = 54)
    public void testGetChartFromCSVDirWithLargeFiles() throws IOException {
        StringBuilder data = new StringBuilder();
        for (int i = 1; i <= 1000; i++) {
            data.append(i).append(",").append(i * 2).append("\n");
        }
        createSampleCSVFile("largeFile.csv", data.toString());
        XYChart chart = CSVImporter.getChartFromCSVDir(tempDirPath, CSVImporter.DataOrientation.Rows, 800, 600);
        Assert.assertEquals(chart.getSeriesMap().size(), 1);
        Files.delete(Paths.get(tempDirPath + "largeFile.csv"));
    }

    @Test(priority = 56)
    public void testGetChartFromCSVDirWithSingleSeries() throws IOException {
        createSampleCSVFile("singleSeries.csv", "1.0,2.0\n3.0,4.0\n");
        XYChart chart = CSVImporter.getChartFromCSVDir(tempDirPath, CSVImporter.DataOrientation.Rows, 800, 600);
        Assert.assertEquals(chart.getSeriesMap().size(), 1);
        Files.delete(Paths.get(tempDirPath + "singleSeries.csv"));
    }

    @Test(priority = 57)
    public void testGetChartFromCSVDirWithSinglePointSeries() throws IOException {
        createSampleCSVFile("singlePointSeries.csv", "1.0,2.0\n");
        XYChart chart = CSVImporter.getChartFromCSVDir(tempDirPath, CSVImporter.DataOrientation.Rows, 800, 600);
        Assert.assertEquals(chart.getSeriesMap().size(), 1);
        Files.delete(Paths.get(tempDirPath + "singlePointSeries.csv"));
    }

    @Test(priority = 58)
    public void testGetChartFromCSVDirWithDuplicateSeriesNames() throws IOException {
        createSampleCSVFile("duplicateSeries1.csv", "1.0,2.0\n3.0,4.0\n");
        createSampleCSVFile("duplicateSeries2.csv", "5.0,6.0\n7.0,8.0\n");
        XYChart chart = CSVImporter.getChartFromCSVDir(tempDirPath, CSVImporter.DataOrientation.Rows, 800, 600);
        Assert.assertEquals(chart.getSeriesMap().size(), 2);
        Files.delete(Paths.get(tempDirPath + "duplicateSeries1.csv"));
        Files.delete(Paths.get(tempDirPath + "duplicateSeries2.csv"));
    }

    @Test(priority = 59)
    public void testGetChartFromCSVDirWithDifferentDataOrientations() throws IOException {
        createSampleCSVFile("rowsData.csv", "1.0,2.0\n3.0,4.0\n");
        createSampleCSVFile("columnsData.csv", "1.0,3.0\n2.0,4.0\n");
        XYChart chart1 = CSVImporter.getChartFromCSVDir(tempDirPath, CSVImporter.DataOrientation.Rows, 800, 600);
        XYChart chart2 = CSVImporter.getChartFromCSVDir(tempDirPath, CSVImporter.DataOrientation.Columns, 800, 600);
        Assert.assertEquals(chart1.getSeriesMap().size(), 1);
        Assert.assertEquals(chart2.getSeriesMap().size(), 1);
        Files.delete(Paths.get(tempDirPath + "rowsData.csv"));
        Files.delete(Paths.get(tempDirPath + "columnsData.csv"));
    }


}

