package org.jis.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * Test class for Generator
 * 
 * @author Daniel Vollmer
 *
 */
public class GeneratorTest {

	private Generator generator;
	private BufferedImage image;
	private BufferedImage rotatedImage;
	private String testImageName = "image";

	@BeforeClass
	public static void setUpClass() {
		File dir = new File("target/test");
		dir.mkdir();
	}

	@Before
	public void setUp() throws Exception {
		generator = new Generator(null, 0);
		image = ImageIO.read(this.getClass().getResourceAsStream("/" + testImageName + ".jpg"));
	}

	@After
	public void tearDown() throws Exception {
		java.util.Date time = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd_HH.mm.ss.SSS");
		String date = formatter.format(time);
		if (rotatedImage != null) {
			String imageFullName = testImageName + "_rotated_" + date;
			File outputfile = new File("target/test/" + imageFullName + ".jpg");
			ImageIO.write(rotatedImage, "jpg", outputfile);
		}
	}

	@Test
	public void noRotationTest() {
		rotatedImage = generator.rotateImage(image, 0.0);
		assertEquals(rotatedImage, image);
	}

	@Test
	public void nullImageTest() {
		assertNull(generator.rotateImage(null, 0.0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void rotateImageNotMultipleOf90Test() {
		generator.rotateImage(image, 0.42);
	}

	@Test
	public void rotateImageBy90DegreeTest() {
		rotatedImage = generator.rotateImage(image, Math.toRadians(90));

		assertEquals(image.getHeight(), rotatedImage.getWidth());
		assertEquals(image.getWidth(), rotatedImage.getHeight());

		boolean sameImage = true;

		int width = image.getWidth();
		int height = image.getHeight();

		try {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (image.getRGB(x, y) != rotatedImage.getRGB(height - 1 - y, x)) {
						sameImage = false;
						break;
					}
				}
			}
		} catch (IndexOutOfBoundsException e) {
			fail("Error, height or width of rotated image is not correct!");
		}
		assertTrue(sameImage);
	}

	@Test
	public void rotateImageBy270DegreeTest() {
		rotatedImage = generator.rotateImage(image, Math.toRadians(270));
		assertEquals(image.getHeight(), rotatedImage.getWidth());
		assertEquals(image.getWidth(), rotatedImage.getHeight());

		boolean sameImage = true;

		int width = image.getWidth();
		int height = image.getHeight();
		try {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (image.getRGB(x, y) != rotatedImage.getRGB(y, width - 1 - x)) {
						sameImage = false;
						break;
					}
				}
			}
		} catch (IndexOutOfBoundsException e) {
			fail("Error, height or width of rotated image is not correct!");
		}
		assertTrue(sameImage);
	}

	@Test
	public void rotateImageByMinus90DegreeTest() {
		rotatedImage = generator.rotateImage(image, Math.toRadians(-90));
		assertEquals(image.getHeight(), rotatedImage.getWidth());
		assertEquals(image.getWidth(), rotatedImage.getHeight());
		assertTrue(compareImages(rotatedImage, generator.rotateImage(image, Math.toRadians(270))));
	}

	@Test
	public void rotateImageByMinus270DegreeTest() {
		rotatedImage = generator.rotateImage(image, Math.toRadians(-270));
		assertEquals(image.getHeight(), rotatedImage.getWidth());
		assertEquals(image.getWidth(), rotatedImage.getHeight());
		assertTrue(compareImages(rotatedImage, generator.rotateImage(image, Math.toRadians(90))));
	}

	// Compares each pixel of two images. Returns true if the color of all pixel are
	// the same else false.
	public boolean compareImages(BufferedImage imageA, BufferedImage imageB) {
		if (imageA.getWidth() != imageB.getWidth() || imageA.getHeight() != imageB.getHeight()) {
			return false;
		}

		int width = imageA.getWidth();
		int height = imageA.getHeight();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (imageA.getRGB(x, y) != imageB.getRGB(x, y)) {
					return false;
				}
			}
		}
		return true;
	}
}