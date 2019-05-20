package org.iMage.HDrize.matrix;

import org.iMage.HDrize.base.matrix.IMatrix;

public final class Matrix implements IMatrix {

	double mtx[][];

	/**
	 * Create a new matrix.
	 *
	 * @param mtx the original matrix
	 */
	public Matrix(IMatrix mtx) {
		this.mtx = new double[mtx.rows()][mtx.cols()];
		for (int i = 0; i < this.rows(); i++) {
			for (int j = 0; j < this.cols(); j++) {
				this.mtx[i][j] = this.get(i, j);
			}
		}
	}

	/**
	 * Create a new matrix.
	 *
	 * @param mtx the original matrix mtx[Rows][Cols]
	 */
	public Matrix(double[][] mtx) {
		this.mtx = mtx;
	}

	/**
	 * Create a matrix (only zeros).
	 *
	 * @param rows the amount of rows
	 * @param cols the amount of columns
	 */
	public Matrix(int rows, int cols) {
		mtx = new double[rows][cols];
	}

	@Override
	public double[][] copy() {
		double[][] copyMtx = new double[this.rows()][this.cols()];
		for (int i = 0; i < this.rows(); i++) {
			for (int j = 0; j < this.cols(); j++) {
				copyMtx[i][j] = this.get(i, j);
			}
		}
		return copyMtx;
	}

	@Override
	public int rows() {
		return mtx.length;
	}

	@Override
  public int cols() {
    try {
    	return mtx[0].length;
    } catch(IndexOutOfBoundsException e) {
    	throw new IndexOutOfBoundsException("Matrix isnt initialized yet!");
    }
  }

	@Override
	public void set(int r, int c, double v) {
		mtx[r][c] = v;
	}

	@Override
	public double get(int r, int c) {
		if(r < 0 || r > this.rows() || c < 0 || c > this.cols()) {
			throw new IllegalArgumentException("The Matrix doesn't have an inde (" + r + "|" + c + ")");
		}
		return mtx[r][c];
	}

}
