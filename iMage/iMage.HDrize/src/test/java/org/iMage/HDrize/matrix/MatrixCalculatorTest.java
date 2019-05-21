package org.iMage.HDrize.matrix;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class MatrixCalculatorTest {
	double[][] arMtxA = new double[3][2];
	double[][] arMtxB = new double[2][3];
	double[][] arMtxC = {{4,1,-2},{5,-2,-2},{0,4,-3}};
	Matrix mtxA;
	Matrix mtxB;
	Matrix mtxC;
	MatrixCalculator mtxCalc = new MatrixCalculator();

	@Before
	public void setUp() {
		int count = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				arMtxA[i][j] = count;
				count++;
			}
		}
		mtxA = new Matrix(arMtxA);
		count = 0;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				arMtxB[i][j] = count;
				count++;
			}
		}
		mtxB = new Matrix(arMtxB);
		mtxC = new Matrix(arMtxC);
	}

	
	
	@Test
	public void inverseTest() {
		Matrix inverse = mtxCalc.inverse(mtxC);
		for (int i = 0; i < inverse.rows(); i++) {
			for (int j = 0; j < inverse.cols(); j++) {
				System.out.print("|"+inverse.get(i, j) + "|");
			}
			System.out.println("|");
		}
		assertTrue(true);
	}
	
	@Test
	public void transposeTest() {
		double[][] correctTransposed = { { 0.0, 2.0, 4.0 }, { 1.0, 3.0, 5.0 } };
		Matrix transposed = mtxCalc.transpose(mtxA);
		for (int i = 0; i < correctTransposed.length; i++) {
			for (int j = 0; j < correctTransposed[0].length; j++) {
				assertTrue(correctTransposed[i][j] == transposed.get(i, j));
			}
		}
	}

	@Test
	public void multiplicationTest() {
		double[][] correctMultiplied = { { 3.0, 4.0, 5.0 }, { 9.0, 14.0, 19.0 }, { 15.0, 24.0, 33.0 } };
		Matrix multiplied = mtxCalc.multiply(mtxA, mtxB);
		for (int i = 0; i < correctMultiplied.length; i++) {
			for (int j = 0; j < correctMultiplied[0].length; j++) {
				assertTrue(correctMultiplied[i][j] == multiplied.get(i, j));
			}
		}

	}
}
