package org.xchart.testng.internal;

import org.knowm.xchart.internal.Utils;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class UtilsTest {

    @Test
    public void testGetTickStartOffset() {
        double workingSpace = 100.0;
        double tickSpace = 80.0;
        double expectedOffset = 10.0;
        double actualOffset = Utils.getTickStartOffset(workingSpace, tickSpace);
        Assert.assertEquals(actualOffset, expectedOffset);
    }

    @Test
    public void testPowPositiveExponent() {
        double base = 2.0;
        int exponent = 3;
        double expected = 8.0;
        double actual = Utils.pow(base, exponent);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testPowNegativeExponent() {
        double base = 2.0;
        int exponent = -3;
        double expected = 0.125;
        double actual = Utils.pow(base, exponent);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetNumberListFromDoubleArray() {
        double[] data = {1.0, 2.0, 3.0};
        List<Double> expected = Arrays.asList(1.0, 2.0, 3.0);
        List<Double> actual = Utils.getNumberListFromDoubleArray(data);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetNumberListFromIntArray() {
        int[] data = {1, 2, 3};
        List<Double> expected = Arrays.asList(1.0, 2.0, 3.0);
        List<Double> actual = Utils.getNumberListFromIntArray(data);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetGeneratedDataAsList() {
        int length = 3;
        List<Double> expected = Arrays.asList(1.0, 2.0, 3.0);
        List<Double> actual = Utils.getGeneratedDataAsList(length);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetDoubleArrayFromFloatArray() {
        float[] data = {1.0f, 2.0f, 3.0f};
        double[] expected = {1.0, 2.0, 3.0};
        double[] actual = Utils.getDoubleArrayFromFloatArray(data);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetDoubleArrayFromIntArray() {
        int[] data = {1, 2, 3};
        double[] expected = {1.0, 2.0, 3.0};
        double[] actual = Utils.getDoubleArrayFromIntArray(data);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetDoubleArrayFromNumberList() {
        List<Number> data = Arrays.asList(1.0, 2.0, 3.0);
        double[] expected = {1.0, 2.0, 3.0};
        double[] actual = Utils.getDoubleArrayFromNumberList(data);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetDoubleArrayFromDateList() {
        Date now = new Date();
        List<Date> data = Arrays.asList(now);
        double[] expected = {now.getTime()};
        double[] actual = Utils.getDoubleArrayFromDateList(data);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetGeneratedDataAsArray() {
        int length = 3;
        double[] expected = {1.0, 2.0, 3.0};
        double[] actual = Utils.getGeneratedDataAsArray(length);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetLongArrayFromIntArray() {
        int[] data = {1, 2, 3};
        long[] expected = {1L, 2L, 3L};
        long[] actual = Utils.getLongArrayFromIntArray(data);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetLongArrayFromFloatArray() {
        float[] data = {1.0f, 2.0f, 3.0f};
        long[] expected = {1L, 2L, 3L};
        long[] actual = Utils.getLongArrayFromFloatArray(data);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetLongArrayFromNumberList() {
        List<Number> data = Arrays.asList(1.0, 2.0, 3.0);
        long[] expected = {1L, 2L, 3L};
        long[] actual = Utils.getLongArrayFromNumberList(data);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testAddFileExtensionWhenNotPresent() {
        String fileName = "test";
        String fileExtension = ".png";
        String expected = "test.png";
        String actual = Utils.addFileExtension(fileName, fileExtension);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testAddFileExtensionWhenPresent() {
        String fileName = "test.png";
        String fileExtension = ".png";
        String expected = "test.png";
        String actual = Utils.addFileExtension(fileName, fileExtension);
        Assert.assertEquals(actual, expected);
    }

    // Add additional test cases to cover edge cases and null values
    @Test
    public void testGetNumberListFromDoubleArrayWithNull() {
        double[] data = null;
        List<Double> actual = Utils.getNumberListFromDoubleArray(data);
        Assert.assertNull(actual);
    }

    @Test
    public void testGetNumberListFromIntArrayWithNull() {
        int[] data = null;
        List<Double> actual = Utils.getNumberListFromIntArray(data);
        Assert.assertNull(actual);
    }

    @Test
    public void testGetDoubleArrayFromFloatArrayWithNull() {
        float[] data = null;
        double[] actual = Utils.getDoubleArrayFromFloatArray(data);
        Assert.assertNull(actual);
    }

    @Test
    public void testGetDoubleArrayFromIntArrayWithNull() {
        int[] data = null;
        double[] actual = Utils.getDoubleArrayFromIntArray(data);
        Assert.assertNull(actual);
    }

    @Test
    public void testGetDoubleArrayFromNumberListWithNull() {
        List<?> data = null;
        double[] actual = Utils.getDoubleArrayFromNumberList(data);
        Assert.assertNull(actual);
    }

    @Test
    public void testGetDoubleArrayFromDateListWithNull() {
        List<?> data = null;
        double[] actual = Utils.getDoubleArrayFromDateList(data);
        Assert.assertNull(actual);
    }

    @Test
    public void testGetLongArrayFromIntArrayWithNull() {
        int[] data = null;
        long[] actual = Utils.getLongArrayFromIntArray(data);
        Assert.assertNull(actual);
    }

    @Test
    public void testGetLongArrayFromFloatArrayWithNull() {
        float[] data = null;
        long[] actual = Utils.getLongArrayFromFloatArray(data);
        Assert.assertNull(actual);
    }

    @Test
    public void testGetLongArrayFromNumberListWithNull() {
        List<?> data = null;
        long[] actual = Utils.getLongArrayFromNumberList(data);
        Assert.assertNull(actual);
    }
}
