package org.xchart.testng;

import org.knowm.xchart.VectorGraphicsEncoder;
import org.knowm.xchart.internal.chartpart.Chart;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class VectorGraphicsEncoderTest {

    private Chart chart;

    @BeforeMethod
    public void setUp() {
        chart = mock(Chart.class);
        when(chart.getWidth()).thenReturn(800);
        when(chart.getHeight()).thenReturn(600);
    }

    @AfterMethod
    public void tearDown() {
        chart = null;
    }

    @DataProvider(name = "vectorGraphicsFormats")
    public Object[][] vectorGraphicsFormats() {
        return new Object[][] {
                { VectorGraphicsEncoder.VectorGraphicsFormat.EPS },
                { VectorGraphicsEncoder.VectorGraphicsFormat.PDF },
                { VectorGraphicsEncoder.VectorGraphicsFormat.SVG }
        };
    }

    @Test(dataProvider = "vectorGraphicsFormats")
    public void testSaveVectorGraphicToFile(VectorGraphicsEncoder.VectorGraphicsFormat format) throws IOException {
        String fileName = "testChart";
        VectorGraphicsEncoder.saveVectorGraphic(chart, fileName, format);

        File file = new File(VectorGraphicsEncoder.addFileExtension(fileName, format));
        assert file.exists() : "File should exist after saving vector graphic";
        assert file.length() > 0 : "File should not be empty";
        file.delete();
    }

    @Test(dataProvider = "vectorGraphicsFormats")
    public void testSaveVectorGraphicToOutputStream(VectorGraphicsEncoder.VectorGraphicsFormat format) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        VectorGraphicsEncoder.saveVectorGraphic(chart, os, format);

        assert os.size() > 0 : "Output stream should contain data after saving vector graphic";
    }

    @Test(dataProvider = "vectorGraphicsFormats")
    public void testAddFileExtension(VectorGraphicsEncoder.VectorGraphicsFormat format) {
        String fileName = "testChart";
        String expectedFileName = "testChart." + format.toString().toLowerCase();
        String actualFileName = VectorGraphicsEncoder.addFileExtension(fileName, format);

        assert expectedFileName.equals(actualFileName) : "File name should have correct extension";
    }

    @Test(dataProvider = "vectorGraphicsFormats")
    public void testAddFileExtensionWithExistingExtension(VectorGraphicsEncoder.VectorGraphicsFormat format) {
        String fileName = "testChart." + format.toString().toLowerCase();
        String actualFileName = VectorGraphicsEncoder.addFileExtension(fileName, format);

        assert fileName.equals(actualFileName) : "File name should not change if extension already exists";
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testSaveVectorGraphicUnsupportedFormat() throws IOException {
        VectorGraphicsEncoder.VectorGraphicsFormat unsupportedFormat = mock(VectorGraphicsEncoder.VectorGraphicsFormat.class);
        when(unsupportedFormat.toString()).thenReturn("UNSUPPORTED");

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        VectorGraphicsEncoder.saveVectorGraphic(chart, os, unsupportedFormat);
    }

    @Test
    public void testSaveVectorGraphicIOException() throws IOException {
        String fileName = "testChart";
        String invalidFileName = "/invalid/path/testChart";

        try {
            VectorGraphicsEncoder.saveVectorGraphic(chart, invalidFileName, VectorGraphicsEncoder.VectorGraphicsFormat.SVG);
            assert false : "Expected IOException not thrown";
        } catch (IOException e) {
            assert true : "IOException thrown as expected";
        }
    }

    @Test
    public void testSaveVectorGraphicNullChart() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            VectorGraphicsEncoder.saveVectorGraphic(null, os, VectorGraphicsEncoder.VectorGraphicsFormat.SVG);
            assert false : "Expected NullPointerException not thrown";
        } catch (NullPointerException e) {
            assert true : "NullPointerException thrown as expected";
        }
    }

    @Test
    public void testSaveVectorGraphicZeroDimensions() throws IOException {
        when(chart.getWidth()).thenReturn(0);
        when(chart.getHeight()).thenReturn(0);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        VectorGraphicsEncoder.saveVectorGraphic(chart, os, VectorGraphicsEncoder.VectorGraphicsFormat.SVG);

        assert os.size() > 0 : "Output stream should contain data even with zero dimensions";
    }
}
