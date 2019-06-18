package org.iMage.iCatcher;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.imaging.ImageReadException;

/**
 * 
 * @author s_vollmer
 *
 */
public class Controller {

	private Model model;
	private View view;

	public Controller(View view) {
		this.view = view;
		this.model = new Model();
	}

	/**
	 * 
	 * @param ae ActionEvent
	 */
	public void enlargeResultImage(ActionEvent ae) {
		if (model.getHDRImage() == null) {
			view.showError("You have to create a HDR Image before you can enlarge it!");
			return;
		}
		view.showImageLarge(model.getHDRImage());
		
	}

	/**
	 * 
	 * @param ae ActionEvent
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public void loadDIR(ActionEvent ae) {
		File path = view.selectDirPath();
		try {
			if (path != null) {
				model.calculatePreviewImage(path);
				view.setPreviewImage(model.getPreviewImage());
			}
		} catch (IOException e) {
			view.showError(e.getMessage());
		}
	}

	/*
	 * public void changeCameraCurve(ActionEvent ae) {
	 * model.setCameraCurve(view.getCameraCurve()); }
	 * 
	 * public void changeToneMappong() {
	 * model.setToneMapping(view.getToneMapping()); }
	 */

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
		model.setCameraCurve(view.getCameraCurve());
		model.setToneMapping(view.getToneMapping());
		if (view.getCameraCurve() == CameraCurveEnum.CALCULATED_CURVE) {
			model.setLambda(view.getLambda());
			model.setSamples(view.getSamples());
		}
		try {
			model.calculateHDRImage();
		} catch (IOException e) {
			view.showError("An error occured while createing the HDR image: " + e.getMessage());
			return;
		} catch (ImageReadException e) {
			view.showError("An error occured while createing the HDR image: " + e.getMessage());
			return;
		} catch (IllegalArgumentException e) {
			view.showError("An error occured while createing the HDR image: " + e.getMessage());
			return;
		}
		view.setResultImage(model.getHDRImage());
	}

	/**
	 * 
	 * @param ae ActionEvent
	 */
	public void saveHDR(ActionEvent ae) {
		if (model.getHDRImage() == null) {
			view.showError("You must create a HDR image before you can save it!");
			return;
		}
		File path = view.savePNG(model.getHDRImage());
		
		try {
			String fileEnding = path.getName().substring(path.getName().length() -3, path.getName().length());
			if(!fileEnding.equals("png")) {
				view.showError("The HDR image can only be saved as a png file!");
				return;
			}
			model.saveHDRImage(path);
		} catch (IOException e) {
			view.showError("An error occured while saving the picture: " + e.getMessage());
		} catch (IndexOutOfBoundsException e) {
			view.showError("The HDR image can only be saved as a png file!");
		}
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

}
