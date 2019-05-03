package org.jis.generator;

import static org.junit.Assert.assertEquals;

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
		assertEquals(null, generator.rotateImage(null, 0.0));
	}
}