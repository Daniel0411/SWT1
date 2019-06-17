package org.iMage.iCatcher;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;

public class Controller {

	private Calculator calc = new Calculator();

	/**
	 * 
	 * @param ae ActionEvent
	 */
	public void enlargeResultImage(ActionEvent ae) {

	}

	/**
	 * 
	 * @param ae ActionEvent
	 */
	public void loadDIR(ActionEvent ae) {
		File path = Layout.loadDirDialog();
		calc.loadDir(path);
	}

	/**
	 * 
	 * @param ae ActionEvent
	 */
	public void loadCurve(ActionEvent ae) {

	}

	/**
	 * 
	 * @param ae ActionEvent
	 */
	public void runHDrize(ActionEvent ae) {

	}

	/**
	 * 
	 * @param ae ActionEvent
	 */
	public void saveHDR(ActionEvent ae) {

	}

	/**
	 * 
	 * @param ae ActionEvent
	 */
	public void saveCurve(ActionEvent ae) {

	}

	/**
	 * 
	 * @param ae ActionEvent
	 */
	public void showCurve(ActionEvent ae) {

	}

	/**
	 * 
	 * @param ae ActionEvent
	 */
	public void lambda(ActionEvent ae) {

	}

	/**
	 * 
	 * @param ae ActionEvent
	 */
	public void samples(ChangeEvent ae) {

	}

}
