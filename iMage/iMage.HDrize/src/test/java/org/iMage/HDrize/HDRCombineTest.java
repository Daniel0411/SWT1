package org.iMage.HDrize;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HDRCombineTest {
	HDRCombine hdrCom = new HDRCombine();

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
