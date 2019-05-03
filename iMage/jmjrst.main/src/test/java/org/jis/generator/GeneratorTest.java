package org.jis.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
		double rad = Math.PI/180*90;
		BufferedImage rotatedImage = generator.rotateImage(image, rad);
		assertEquals(image.getHeight(), rotatedImage.getWidth());
		assertEquals(image.getWidth(), rotatedImage.getHeight());
		//assertEquals(image, rotatedImage);
	}
	
	public void testRotateImageBy270Degree() {
		double rad = Math.PI/180*270;
		BufferedImage rotatedImage = generator.rotateImage(image, rad);
		assertEquals(image.getHeight(), rotatedImage.getWidth());
		assertEquals(image.getWidth(), rotatedImage.getHeight());
		//assertEquals(image, rotatedImage);
	}
	
	
	
	
	
	

}