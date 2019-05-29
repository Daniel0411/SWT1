package org.iMage.HDrize;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.imaging.ImageReadException;
import org.iMage.HDrize.base.ICameraCurve;
import org.iMage.HDrize.base.images.EnhancedImage;
import org.iMage.HDrize.base.images.HDRImage;
import org.junit.Test;

public class HDRCombineTest {
	HDRCombine hdrCom = new HDRCombine();

	@Test
	public void createHDRSizeTest() throws ImageReadException, IOException {
		File input = new File("src/test/resources/TestImages");
		File[] listOfFiles = input.listFiles();
		EnhancedImage[] imageList = new EnhancedImage[3];

		for (int i = 0; i < listOfFiles.length; i++) {
			InputStream inputStreamImage = new FileInputStream(listOfFiles[i]);
			imageList[i] = new EnhancedImage(inputStreamImage);
			inputStreamImage.close();
		}
		ICameraCurve curve = new CameraCurve();
		HDRImage hdrIm = hdrCom.createHDR(curve, imageList);
		
		assertEquals(hdrIm.getHeight(), imageList[0].getHeight());
		assertEquals(hdrIm.getWidth(), imageList[0].getWidth());
	}

	@Test
	public void weightTest() {
		float[] weight = hdrCom.calculateWeights();

		for (int i = 0; i < 20; i++) {
			assertEquals(weight[i], weight[255 - i], 0.000000001);
		}
		for (int i = 20; i < 236; i++) {
			assertEquals(2, weight[i], 0.0000000001);
		}
	}
}
