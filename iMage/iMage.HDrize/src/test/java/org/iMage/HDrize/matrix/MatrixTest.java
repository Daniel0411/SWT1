package org.iMage.HDrize.matrix;

import org.junit.Before;
import org.junit.Test;

public class MatrixTest {
	double[][] arMtx = new double[3][3];
	Matrix mtx;
	
	@Before
	public void setUp() {
		int count = 0;
		for(int i = 0; i < 3; i++) {
			for(int j = 0;j < 3; j++) {
				arMtx[i][j] = count;
				count++;
			}
		}
		mtx = new Matrix(arMtx);
	}
	
	@Test
	public void simpleMatrixTest() {
		for(int i = 0; i < 3; i++) {
			for(int j = 0;j < 3; j++) {
				System.out.print(mtx.get(i, j) + " | ");
			}
			System.out.println("");
		}
	}
}
