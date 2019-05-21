package org.iMage.HDrize.matrix;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class MatrixTest {
	double[][] arMtx = new double[3][2];
	double[][] correctMatrix = { { 0.0, 1.0 }, { 2.0, 3.0 }, { 4.0, 5.0 } };
	Matrix mtx;

	@Before
	public void setUp() {
		int count = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				arMtx[i][j] = count;
				count++;
			}
		}
		mtx = new Matrix(arMtx);
	}

	@Test
	public void contructorWithRowAndColumnTest() {
		int rows = 3;
		int cols = 4;
		Matrix mtxTest = new Matrix(rows,cols);
		for (int i = 0; i< rows;i++) {
			for (int j = 0; j < cols; j++) {
				assertEquals(0.0, mtxTest.get(i, j), 0.0000001);
			}
		}
	}
	
	@Test
	public void constructorWithDArrayTest() {
		Matrix mtxTest = new Matrix(correctMatrix);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				assertEquals(correctMatrix[i][j], mtxTest.get(i, j), 0.00000001);
			}
			
		}
	}
	
	@Test
	public void constructorWithMatrixTest() {
		Matrix mtxTest = new Matrix(mtx);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				assertEquals(correctMatrix[i][j], mtxTest.get(i, j), 0.00000001);
			}
		}
	}
	
	@Test
	public void copyTest() {
		double[][] copyMtx = mtx.copy();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				assertEquals(correctMatrix[i][j], copyMtx[i][j], 0.00000001);
			}
		}
		
	}

	@Test
	public void setTest() {
		mtx = new Matrix(3, 2);
		int count = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				mtx.set(i, j, count);
				count++;
				assertEquals(correctMatrix[i][j], mtx.get(i, j), 0.00000001);
			}
		}
	}

	@Test
	public void getTest() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				assertEquals(correctMatrix[i][j], mtx.get(i, j), 0.00000001);
			}
		}
	}

	@Test
	public void rowTest() {
		assertEquals(mtx.rows(), 3);
	}

	@Test
	public void columnTest() {
		assertEquals(mtx.cols(), 2);
	}
}
