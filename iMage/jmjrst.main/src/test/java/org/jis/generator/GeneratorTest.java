package org.jis.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for generator
 * 
 * @author Daniel Vollmer
 *
 */
public class GeneratorTest {

	private Generator generator;
	private BufferedImage image;

	/**
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		generator = new Generator(null, 0);
		BufferedImage input = ImageIO.read(this.getClass().getResourceAsStream("/image.jpg"));
		image = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
	}

	/**
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRotateImageNoRotation() {
		BufferedImage newImage = generator.rotateImage(image, 0.0);
		assertEquals(newImage, image);
	}

	@Test
	public void testRotateImageNullImage() {
		assertNull(generator.rotateImage(null, 0.0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRotateImageNotMultipleOf90() {
		generator.rotateImage(image, 0.42);
	}

	@Test
	public void testRotateImageBy90Degree() {
		BufferedImage rotatedImage = generator.rotateImage(image, Math.toRadians(90));
		assertEquals(image.getHeight(), rotatedImage.getWidth());
		assertEquals(image.getWidth(), rotatedImage.getHeight());
		// assertEquals(image, rotatedImage);
	}

	@Test
	public void testRotateImageBy270Degree() {
		BufferedImage rotatedImage = generator.rotateImage(image, Math.toRadians(270));
		assertEquals(image.getHeight(), rotatedImage.getWidth());
		assertEquals(image.getWidth(), rotatedImage.getHeight());
		// assertEquals(image, rotatedImage);
	}

	@Test
	public void testRotateImageByMinus90Degree() {
		BufferedImage rotatedImage = generator.rotateImage(image, Math.toRadians(-90));
		assertEquals(image.getHeight(), rotatedImage.getWidth());
		assertEquals(image.getWidth(), rotatedImage.getHeight());
		assertTrue(compareImages(rotatedImage, generator.rotateImage(image, Math.toRadians(270))));
	}

	@Test
	public void testRotateImageByMinus270Degree() {
		BufferedImage rotatedImage = generator.rotateImage(image, Math.toRadians(-270));
		assertEquals(image.getHeight(), rotatedImage.getWidth());
		assertEquals(image.getWidth(), rotatedImage.getHeight());
		assertTrue(compareImages(rotatedImage, generator.rotateImage(image, Math.toRadians(90))));
	}

	public static boolean compareImages(BufferedImage imgA, BufferedImage imgB) {
		// The images must be the same size.
		if (imgA.getWidth() != imgB.getWidth() || imgA.getHeight() != imgB.getHeight()) {
			return false;
		}

		int width = imgA.getWidth();
		int height = imgA.getHeight();

		// Loop over every pixel.
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// Compare the pixels for equality.
				if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
					return false;
				}
			}
		}
		return true;
	}
}