package org.iMage.HDrize.matrix;

import org.iMage.HDrize.base.matrix.IMatrixCalculator;

public class MatrixCalculator implements IMatrixCalculator<Matrix> {

	@Override
	public Matrix inverse(Matrix mtx) {
		int mtxRows = mtx.rows();
		int mtxCols = mtx.cols();
		Matrix extendedMtx = createExtendedMatrix(mtx);

		// Apply the Gauß-Jordan algorithm to get the inverse of a matrix
		for (int i = 0; i < mtxRows; i++) {
			double divider = extendedMtx.get(i, i);
			if (divider == 0) {
				throw new IllegalArgumentException("Matrix can't be inverted!");
			}
			for (int j = i; j < mtxCols * 2; j++) {
				extendedMtx.set(i, j, extendedMtx.get(i, j) / divider);
			}

			for (int j = i + 1; j < mtxRows; j++) {
				double multiOne = extendedMtx.get(i, i);
				double multiTwo = extendedMtx.get(j, i);
				for (int k = i; k < mtxCols * 2; k++) {
					double minus = extendedMtx.get(j, k) * multiOne - extendedMtx.get(i, k) * multiTwo;
					extendedMtx.set(j, k, minus);
				}
			}
		}

		for (int i = mtxRows - 1; i > 0; i--) {
			for (int j = i - 1; j >= 0; j--) {
				double multiOne = extendedMtx.get(i, i);
				double multiTwo = extendedMtx.get(j, i);
				for (int k = i; k < mtxCols * 2; k++) {
					double minus = extendedMtx.get(j, k) * multiOne - extendedMtx.get(i, k) * multiTwo;
					extendedMtx.set(j, k, minus);
				}
			}
		}

		return cutLeftHalfOff(extendedMtx);
	}

	/*
	 * Creates an extended matrix with the identity matrix on the right half
	 */
	private Matrix createExtendedMatrix(Matrix mtx) {
		int mtxRows = mtx.rows();
		int mtxCols = mtx.cols();
		Matrix extendedMtx = new Matrix(mtxRows, mtxCols * 2);
		for (int i = 0; i < mtxRows; i++) {
			for (int j = 0; j < mtxCols; j++) {
				extendedMtx.set(i, j, mtx.get(i, j));
				if (i == j) {
					extendedMtx.set(i, j + mtxCols, 1);
				}
			}
		}
		return extendedMtx;
	}

	/*
	 * Cuts the left half of a matrix off and returns the right half
	 */
	private Matrix cutLeftHalfOff(Matrix mtx) {
		Matrix resultMtx = new Matrix(mtx.rows(), mtx.cols() / 2);
		for (int i = 0; i < mtx.rows(); i++) {
			for (int j = mtx.cols() / 2; j < mtx.cols(); j++) {
				resultMtx.set(i, j - mtx.rows(), mtx.get(i, j));
			}
		}
		return resultMtx;
	}

	@Override
	public Matrix multiply(Matrix a, Matrix b) {
		if (a.cols() != b.rows()) {
			return null;
		}
		Matrix multipliedMtx = new Matrix(a.rows(), b.cols());
		double value = 0.0;
		for (int i = 0; i < a.rows(); i++) {
			for (int j = 0; j < b.cols(); j++) {
				for (int k = 0; k < a.cols(); k++) {
					value = value + a.get(i, k) * b.get(k, j);
				}
				multipliedMtx.set(i, j, value);
				value = 0.0;
			}
		}
		return multipliedMtx;
	}

	@Override
	public Matrix transpose(Matrix mtx) {
		Matrix transposed = new Matrix(mtx.cols(), mtx.rows());
		for (int i = 0; i < mtx.rows(); i++) {
			for (int j = 0; j < mtx.cols(); j++) {
				transposed.set(j, i, mtx.get(i, j));
			}
		}
		return transposed;
	}

}
