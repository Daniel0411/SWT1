package org.iMage.HDrize;

import org.iMage.HDrize.base.ICameraCurve;
import org.iMage.HDrize.base.IHDRCombine;
import org.iMage.HDrize.base.images.EnhancedImage;
import org.iMage.HDrize.base.images.HDRImage;

/**
 * Implementation of {@link IHDRCombine}.
 */
public class HDRCombine implements IHDRCombine {
	private float[] weight = calculateWeights();

	@Override
	public HDRImage createHDR(ICameraCurve curve, EnhancedImage[] imageList) {
		int height = imageList[0].getHeight();
		int width = imageList[0].getWidth();
		float[][] red = new float[width][height];
		float[][] green = new float[width][height];
		float[][] blue = new float[width][height];
		
		for(int channel = 0; channel < 3; channel++) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					float hdrPixel = value(imageList, x, y, curve, channel)/norm(imageList, x, y, channel);
					if(channel == 0) {
						red[x][y] = hdrPixel;
					} else if(channel == 1) {
						green[x][y] = hdrPixel;
					} else {
						blue[x][y] = hdrPixel;
					}
				}
			}
		}
		HDRImage hdrImage = new HDRImage(red, green, blue);
		return hdrImage;
		
	}

	private int value(EnhancedImage[] imageList, int x, int y, ICameraCurve curve, int channel) {
		int value = 0;
		for(int image = 0; image < 3; image++) {
			int[] rgb = imageList[image].getRGB(x, y);
			value += (weight[rgb[channel]]*curve.getResponse(rgb)[channel])/imageList[image].getExposureTime();
		}
		return value;
	}

	private int norm(EnhancedImage[] imageList, int x, int y, int channel) {
		int norm = 0;
		for (int image = 0; image < 3; image++) {
			int[] rgb = imageList[image].getRGB(x, y);
			norm += weight[rgb[channel]];
		}

		return norm;
	}

	@Override
	public float[] calculateWeights() {
		float[] weight = new float[256];
		weight[0] = 1;
		weight[255] = 1;
		for (int i = 1; i < 20; i++) {
			weight[i] = (float) (weight[(i - 1)] + 1.0 / 20.0);
		}
		for (int i = 254; i > 235; i--) {
			weight[i] = (float) (weight[i + 1] + 1.0 / 20.0);
		}
		for (int i = 20; i < 236; i++) {
			weight[i] = 2;
		}
		return weight;
	}
}
