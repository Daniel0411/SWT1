package org.jis.generator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Vector;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;

import org.jis.Main;
import org.jis.Messages;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;

/**
 * Test class for Generator
 * 
 * @author Daniel Vollmer
 *
 */
public class SecondGeneratorTest {

	private Generator generator;
	private BufferedImage image;
	private String testImageName = "image";

	@Rule
	public MockitoRule mockito = MockitoJUnit.rule();

	@Mock
	Main mainMock;

	@Before
	public void setUp() throws Exception {
		File temp = new File("temp");
		temp.mkdir();
		MockitoAnnotations.initMocks(this);
		Messages mes = new Messages(new Locale("en"));
		mainMock.mes = mes;
		generator = new Generator(mainMock, 0);
		image = ImageIO.read(this.getClass().getResourceAsStream("/" + testImageName + ".jpg"));
	}

	@After
	public void tearDown() {
		File temp = new File("temp");
		removeDir(temp);
	}

	/**
	 * Deletes a directory even if it's not empty.
	 * 
	 * @param dir
	 */
	public static void removeDir(File dir) {
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			if (files != null && files.length > 0) {
				for (File aFile : files) {
					removeDir(aFile);
				}
			}
			dir.delete();
		} else {
			dir.delete();
		}
	}

	/**
	 * Tests if the method reads 2 input files from the correct path and writes two
	 * files with the correct name to the correct path.
	 */
	@Test
	public void generateTextTest() throws IOException {
		File inputDir = new File("temp/input");
		File resizeDir = new File("temp/resized");
		inputDir.mkdir();
		resizeDir.mkdir();

		File tempImage = new File("temp/input/" + testImageName + ".jpg");
		ImageIO.write(image, "jpg", tempImage);
		File tempImageTwo = new File("temp/input/" + testImageName + "Two.jpg");
		ImageIO.write(image, "jpg", tempImageTwo);

		generator.generateText(inputDir, resizeDir, 100, 100);

		assertTrue(new File("temp/resized/t_" + testImageName + ".jpg").exists());
		assertTrue(new File("temp/resized/t_" + testImageName + "Two.jpg").exists());
	}

	/**
	 * Tests if the input file still exists after the rotate method modified the
	 * file. (Not really obvious in this Generator class)
	 * 
	 * @throws IOException
	 */

	@Test
	public void rotateFileExistsTest() throws IOException {
		File tempImage = new File("temp/" + testImageName + ".jpg");
		ImageIO.write(image, "jpg", tempImage);
		generator.rotate(tempImage);

		assertTrue(tempImage.exists());
	}

	/**
	 * Tests if an image has the same scale as before if width and height parameter
	 * are the values of the actual image.
	 * 
	 * @throws IOException
	 */
	@Test
	public void generateImageNoScaleCorrectScaleTest() throws IOException {
		File imageFile = new File("src/test/resources/" + testImageName + ".jpg");
		File iout = new File("temp/scaledImage.jpg");
		int height = image.getHeight();
		int width = image.getWidth();
		generator.generateImage(imageFile, iout, false, width, height, "scaled");

		BufferedImage scaledImage = ImageIO.read(new File("temp/scaledImage.jpg"));
		assertEquals(scaledImage.getHeight(), height);
		assertEquals(scaledImage.getWidth(), width);
	}

	/**
	 * Tests if the number of zipped files is correct.
	 * 
	 * @throws ZipException
	 * @throws IOException
	 */
	@Test
	public void createZipNumberOfFilesTest() throws ZipException, IOException {
		File testZip = new File("temp/testZip.zip");
		Vector<File> filesToZip = new Vector<File>();
		File textFileOne = new File("temp/textFile1.txt");
		File textFileTwo = new File("temp/textFile2.txt");

		// Generate 2 text files.
		PrintWriter writer = new PrintWriter(textFileOne, "UTF-8");
		writer.close();
		writer = new PrintWriter(textFileTwo, "UTF-8");
		writer.close();
		filesToZip.add(textFileOne);
		filesToZip.add(textFileTwo);

		generator.createZip(testZip, filesToZip);

		// Assert that the zip file has 2 entries.
		ZipFile zip = new ZipFile(testZip);
		assertEquals(2, zip.size());
		zip.close();
	}

	/**
	 * Tests if a zip file with the correct name and path gets created.
	 * 
	 * @throws IOException
	 */
	@Test
	public void createZipNameTest() {
		File testZip = new File("temp/testZip.zip");
		Vector<File> filesToZip = new Vector<File>();

		generator.createZip(testZip, filesToZip);

		assertTrue(testZip.exists());
	}
}
