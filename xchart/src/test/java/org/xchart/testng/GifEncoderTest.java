package org.xchart.testng;

import org.knowm.xchart.GifEncoder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GifEncoderTest {

    private static final String TEST_OUTPUT_DIR = "test-output";
    private static final String TEST_FILE_PREFIX = "test_";

    @BeforeMethod
    public void setUp() throws IOException {
        // Create test output directory if it doesn't exist
        Files.createDirectories(Paths.get(TEST_OUTPUT_DIR));
    }

    @AfterMethod
    public void tearDown() throws IOException {
        // Clean up test output directory after each test method
        Path testOutputPath = Paths.get(TEST_OUTPUT_DIR);
        Files.list(testOutputPath)
                .filter(file -> file.getFileName().toString().startsWith(TEST_FILE_PREFIX))
                .forEach(file -> {
                    try {
                        Files.deleteIfExists(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    public void testSaveGifWithSingleImage() {
        BufferedImage image = createTestImage();
        List<BufferedImage> images = new ArrayList<>();
        images.add(image);

        GifEncoder.saveGif(getTestFilePath("single_image.gif"), images);
        Assert.assertTrue(checkFileExists("single_image.gif"));
    }

    @Test
    public void testSaveGifWithMultipleImages() {
        BufferedImage image1 = createTestImage();
        BufferedImage image2 = createTestImage();
        List<BufferedImage> images = new ArrayList<>();
        images.add(image1);
        images.add(image2);

        GifEncoder.saveGif(getTestFilePath("multiple_images.gif"), images);
        Assert.assertTrue(checkFileExists("multiple_images.gif"));
    }

    @Test
    public void testSaveGifWithEmptyImagesList() {
        List<BufferedImage> images = new ArrayList<>();

        GifEncoder.saveGif(getTestFilePath("empty_images.gif"), images);
        Assert.assertTrue(checkFileExists("empty_images.gif"));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testSaveGifNullFilePath() {
        BufferedImage image = createTestImage();
        List<BufferedImage> images = new ArrayList<>();
        images.add(image);

        GifEncoder.saveGif(null, images);
    }
//
//    @Test(expectedExceptions = IllegalArgumentException.class)
//    public void testSaveGifEmptyFilePath() {
//        BufferedImage image = createTestImage();
//        List<BufferedImage> images = new ArrayList<>();
//        images.add(image);
//
//        GifEncoder.saveGif("", images);
//    }
//
//    @Test(expectedExceptions = NullPointerException.class)
//    public void testSaveGifNullImagesList() {
//        GifEncoder.saveGif(getTestFilePath("null_images.gif"), null);
//    }

    @Test
    public void testSaveGifWithNegativeRepeat() {
        BufferedImage image = createTestImage();
        List<BufferedImage> images = new ArrayList<>();
        images.add(image);

        GifEncoder.saveGif(getTestFilePath("negative_repeat.gif"), images, -1, 100);
        Assert.assertTrue(checkFileExists("negative_repeat.gif"));
    }

    @Test
    public void testSaveGifWithZeroRepeat() {
        BufferedImage image = createTestImage();
        List<BufferedImage> images = new ArrayList<>();
        images.add(image);

        GifEncoder.saveGif(getTestFilePath("zero_repeat.gif"), images, 0, 100);
        Assert.assertTrue(checkFileExists("zero_repeat.gif"));
    }

    @Test
    public void testSaveGifWithPositiveRepeat() {
        BufferedImage image = createTestImage();
        List<BufferedImage> images = new ArrayList<>();
        images.add(image);

        GifEncoder.saveGif(getTestFilePath("positive_repeat.gif"), images, 3, 100);
        Assert.assertTrue(checkFileExists("positive_repeat.gif"));
    }

//    @Test(expectedExceptions = IllegalArgumentException.class)
//    public void testSaveGifWithNegativeDelay() {
//        BufferedImage image = createTestImage();
//        List<BufferedImage> images = new ArrayList<>();
//        images.add(image);
//
//        GifEncoder.saveGif(getTestFilePath("negative_delay.gif"), images, 0, -100);
//    }
//
//    @Test(expectedExceptions = IllegalArgumentException.class)
//    public void testSaveGifWithZeroDelay() {
//        BufferedImage image = createTestImage();
//        List<BufferedImage> images = new ArrayList<>();
//        images.add(image);
//
//        GifEncoder.saveGif(getTestFilePath("zero_delay.gif"), images, 0, 0);
//    }

    @Test
    public void testSaveGifWithLargeImagesList() {
        List<BufferedImage> images = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            BufferedImage image = createTestImage();
            images.add(image);
        }

        GifEncoder.saveGif(getTestFilePath("large_images.gif"), images);
        Assert.assertTrue(checkFileExists("large_images.gif"));
    }

    @Test(dependsOnMethods = {"testSaveGifWithSingleImage", "testSaveGifWithMultipleImages"})
    public void testDependentMethods() {
        // This test method depends on testSaveGifWithSingleImage and testSaveGifWithMultipleImages
        // Ensure they pass successfully before running this test
        Assert.assertTrue(true);
    }

    @Test(threadPoolSize = 5, invocationCount = 10, timeOut = 1000)
    public void testConcurrentSaveGif() {
        BufferedImage image = createTestImage();
        List<BufferedImage> images = new ArrayList<>();
        images.add(image);

        GifEncoder.saveGif(getTestFilePath("concurrent.gif"), images);
        Assert.assertTrue(checkFileExists("concurrent.gif"));
    }

    @Test(enabled = false)
    public void testDisabledMethod() {
        // This test method is disabled (not executed)
    }

    @Test(description = "Test saving a GIF with a description")
    public void testMethodWithDescription() {
        BufferedImage image = createTestImage();
        List<BufferedImage> images = new ArrayList<>();
        images.add(image);

        GifEncoder.saveGif(getTestFilePath("description.gif"), images);
        Assert.assertTrue(checkFileExists("description.gif"));
    }

    // Helper methods

    private BufferedImage createTestImage() {
        return new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    }

    private String getTestFilePath(String filename) {
        return Paths.get(TEST_OUTPUT_DIR, filename).toString();
    }

    private boolean checkFileExists(String filename) {
        File file = new File(getTestFilePath(filename));
        return file.exists();
    }
}
