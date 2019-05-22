package org.iMage.HDrize;

import org.iMage.HDrize.base.ICameraCurve;
import org.iMage.HDrize.base.IHDRCombine;
import org.iMage.HDrize.base.images.EnhancedImage;
import org.iMage.HDrize.base.images.HDRImage;
import org.iMage.HDrize.matrix.Matrix;
import org.iMage.HDrize.matrix.MatrixCalculator;

/**
 * Implementation of {@link IHDRCombine}.
 */
public class HDRCombine implements IHDRCombine {
	private float[] weight = calculateWeights();

	@Override
	public HDRImage createHDR(ICameraCurve curve, EnhancedImage[] imageList) {
		int height = imageList[0].getHeight();
		int width = imageList[0].getWidth();
		double[][] red = new double[width][height];
		double[][] green = new double[width][height];
		double[][] blue = new double[width][height];

		for (int channel = 0; channel < 3; channel++) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					float hdrPixel = value(imageList, x, y, curve, channel) / norm(imageList, x, y, channel);
					if (channel == 0) {
						red[x][y] = hdrPixel;
					} else if (channel == 1) {
						green[x][y] = hdrPixel;
					} else {
						blue[x][y] = hdrPixel;
					}
				}
			}
		}

		// transpose the rgb matrices
		Matrix mtxR = new Matrix(red);
		Matrix mtxG = new Matrix(green);
		Matrix mtxB = new Matrix(blue);

		MatrixCalculator mtxCalc = new MatrixCalculator();

		mtxR = mtxCalc.transpose(mtxR);
		mtxG = mtxCalc.transpose(mtxG);
		mtxB = mtxCalc.transpose(mtxB);

		HDRImage hdrImage = new HDRImage(mtxToFloat(mtxR), mtxToFloat(mtxG), mtxToFloat(mtxB));
		return hdrImage;
	}

	/*
	 * Transforms a matrix into a 2D array of floats
	 */
	private float[][] mtxToFloat(Matrix mtx) {
		float[][] result = new float[mtx.rows()][mtx.cols()];
		for (int i = 0; i < mtx.rows(); i++) {
			for (int j = 0; j < mtx.cols(); j++) {
				result[i][j] = (float) mtx.get(i, j);
			}
		}
		return result;
	}

	/*
	 * Calculates the value with the given formula
	 */
	private float value(EnhancedImage[] imageList, int x, int y, ICameraCurve curve, int channel) {
		float value = 0;
		for (int image = 0; image < 3; image++) {
			int[] rgb = imageList[image].getRGB(x, y);
			value = value
					+ (weight[rgb[channel]] * curve.getResponse(rgb)[channel]) / imageList[image].getExposureTime();
		}
		return value;
	}

	/*
	 * Calculates the norm with the given formula
	 */
	private float norm(EnhancedImage[] imageList, int x, int y, int channel) {
		float norm = 0;
		for (int image = 0; image < 3; image++) {
			int[] rgb = imageList[image].getRGB(x, y);
			norm = norm + weight[rgb[channel]];
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
