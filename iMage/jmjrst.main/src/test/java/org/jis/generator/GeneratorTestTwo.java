package org.jis.generator;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.jis.Main;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

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
}
