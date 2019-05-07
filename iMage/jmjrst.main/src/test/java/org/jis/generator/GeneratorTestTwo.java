package org.jis.generator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;

import org.jis.Main;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;

/**
 * Test class for Generator
 * 
 * @author Daniel Vollmer
 *
 */
public class GeneratorTestTwo {

	private Generator generator;
	private BufferedImage image;
	private BufferedImage rotatedImage;
	private String testImageName = "image";

	@BeforeClass
	public static void setUpClass() {
		File dir = new File("temp");
		dir.mkdir();
	}

	@Rule
	public MockitoRule mockito = MockitoJUnit.rule();

	@Mock
	Main mainMock;

	@Before
	public void setUp() throws Exception {
		generator = new Generator(mainMock, 0);
		image = ImageIO.read(this.getClass().getResourceAsStream("/" + testImageName + ".jpg"));
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
