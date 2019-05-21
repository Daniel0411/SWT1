package org.iMage.HDrize;

import org.iMage.HDrize.base.ICameraCurve;
import org.iMage.HDrize.base.IHDRCombine;
import org.iMage.HDrize.base.images.EnhancedImage;
import org.iMage.HDrize.base.images.HDRImage;

/**
 * Implementation of {@link IHDRCombine}.
 */
public class HDRCombine implements IHDRCombine {
	@Override
	public HDRImage createHDR(ICameraCurve curve, EnhancedImage[] imageList) {
		throw new UnsupportedOperationException("TODO Implement me!");
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
		for (int i = 20; i < 236;i++) {
			weight[i] = 2;
		}
		return weight;
	}
}
