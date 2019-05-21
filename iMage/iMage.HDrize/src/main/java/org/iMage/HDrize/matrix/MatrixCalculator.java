package org.iMage.HDrize.matrix;

import org.iMage.HDrize.base.matrix.IMatrixCalculator;

public class MatrixCalculator implements IMatrixCalculator<Matrix> {

	@Override
	public Matrix inverse(Matrix mtx) {
		throw new UnsupportedOperationException("TODO Implement me!");
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
