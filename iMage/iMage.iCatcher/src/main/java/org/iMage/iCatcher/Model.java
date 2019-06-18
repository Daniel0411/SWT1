package org.iMage.iCatcher;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.imaging.ImageReadException;
import org.iMage.HDrize.CameraCurve;
import org.iMage.HDrize.HDrize;
import org.iMage.HDrize.base.images.EnhancedImage;
import org.iMage.HDrize.base.images.HDRImageIO.ToneMapping;
import org.iMage.HDrize.matrix.MatrixCalculator;

/**
 * 
 * @author s_vollmer
 *
 */
public class Model {

	public static final double STANDARD_LAMBDA = 30;
	public static final int STANDARD_SAMPLES = 142;

	private List<File> inputImagesPaths = null;
	private Image previewImage = null;
	private CameraCurveEnum cameraCurveType;
	private CameraCurve cameraCurve;
	private ToneMapping toneMapping;
	private double lambda = 0;
	private int samples = 0;
	private BufferedImage hdrImage = null;

	public void setLambda(String lambda) {
		double lambdaNumber;
		try {
			lambdaNumber = Double.parseDouble(lambda);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Lambda must be a number in (0, 100]!");
		}
		if (lambdaNumber <= 0 || lambdaNumber > 100) {
			throw new IllegalArgumentException("Lambda must be a number in (0, 100]!");
		}
		this.lambda = lambdaNumber;
	}

	public void setSamples(int samples) {
		if (samples < 1 || samples > 1000) {
			throw new IllegalArgumentException("Samples must be an integer in [1, 1000]");
		}
		this.samples = samples;
	}

	public void setCameraCurve(CameraCurveEnum cameraCurve) {
		this.cameraCurveType = cameraCurve;
	}

	public void setToneMapping(ToneMapping toneMapping) {
		this.toneMapping = toneMapping;
	}

	/**
	 * 
	 * @return previewImage
	 */
	public Image getPreviewImage() {
		return previewImage;
	}

	public BufferedImage getHDRImage() {
		return hdrImage;
	}

	public String getLongestCommonPrefix() {
		if (inputImagesPaths == null) {
			throw new IllegalArgumentException("You have to select a directory first!");
		}

		String[] fileNames = new String[inputImagesPaths.size()];
		for (int i = 0; i < inputImagesPaths.size(); i++) {
			fileNames[i] = inputImagesPaths.get(i).getName();
		}

		try {
			for(String name : fileNames) {
				
			}
		} catch(NullPointerException e) {
			
		}
		
		return "prefix";

	}

	public void saveHDRImage(File path) throws IOException {
		ImageIO.write(hdrImage, "png", path);
	}

	public void calculateHDRImage() throws ImageReadException, FileNotFoundException, IOException {
		if (previewImage == null) {
			throw new IllegalArgumentException("You have to load a directory before you can run HDrize!");
		}
		if (cameraCurveType == CameraCurveEnum.CALCULATED_CURVE && (lambda == 0 || samples == 0)) {
			throw new IllegalArgumentException(
					"You have to enter valid lambda and sample values before you can run HDrize with a calculated curve!");
		}
		switch (cameraCurveType) {
		case CALCULATED_CURVE: {
			calculateCurve();
			createHDRImage();
			break;
		}
		case STANDARD_CURVE: {
			calculateStandardCurve();
			createHDRImage();
			break;
		}
		case LOADED_CURVE: {
			break;
		}
		}
	}

	private void calculateCurve() throws ImageReadException, FileNotFoundException, IOException {
		EnhancedImage[] images = new EnhancedImage[inputImagesPaths.size()];
		for (int i = 0; i < images.length; i++) {
			images[i] = new EnhancedImage(new FileInputStream(inputImagesPaths.get(i)));
		}
		this.cameraCurve = new CameraCurve(images, samples, lambda, new MatrixCalculator());
		this.cameraCurve.calculate();
	}

	private void createHDRImage() throws ImageReadException, FileNotFoundException, IOException {
		EnhancedImage[] images = new EnhancedImage[inputImagesPaths.size()];
		for (int i = 0; i < images.length; i++) {
			images[i] = new EnhancedImage(new FileInputStream(inputImagesPaths.get(i)));
		}
		HDrize hdrize = new HDrize();
		hdrImage = hdrize.createRGB(images, cameraCurve, toneMapping);
	}

	private void calculateStandardCurve() throws ImageReadException, FileNotFoundException, IOException {
		EnhancedImage[] images = new EnhancedImage[inputImagesPaths.size()];
		for (int i = 0; i < images.length; i++) {
			images[i] = new EnhancedImage(new FileInputStream(inputImagesPaths.get(i)));
		}
		this.cameraCurve = new CameraCurve(images, STANDARD_SAMPLES, STANDARD_LAMBDA, new MatrixCalculator());
		this.cameraCurve.calculate();
	}

	/**
	 * 
	 * @param path
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public void calculatePreviewImage(File path) throws IOException {
		List<File> inputImagesPaths = new ArrayList<File>();

		for (File file : path.listFiles(f -> f.getName().endsWith(".jpeg") || f.getName().endsWith(".jpg"))) {
			inputImagesPaths.add(file);
		}

		if (inputImagesPaths.size() % 2 == 0 || inputImagesPaths.size() < 3) {
			throw new IOException("You have to choose at least 3 jpg files. The number of files must be odd!");
		}

		for (File file : inputImagesPaths) {
			if (file.getName().length() < 3
					|| !file.getName().substring(0, 3).equals(inputImagesPaths.get(0).getName().substring(0, 3))) {
				throw new IOException("All files must have a common prefix of at least 3 characters!");
			}
		}

		this.inputImagesPaths = inputImagesPaths;
		System.out.println(inputImagesPaths);
		this.previewImage = concatenatePreviewImages();
	}

	private Image concatenatePreviewImages() throws IllegalArgumentException, IOException {
		List<BufferedImage> inputImages = pathsToImages();
		for (BufferedImage image : inputImages) {
			if (image.getWidth() != inputImages.get(0).getWidth()
					|| image.getHeight() != inputImages.get(0).getHeight()) {
				throw new IOException("All images must have the same height and width!");
			}
		}

		int width = inputImages.get(0).getWidth() * inputImages.size();
		int height = inputImages.get(0).getHeight();
		BufferedImage joinedImage = new BufferedImage(width, height, inputImages.get(0).getType());

		Graphics2D g2 = joinedImage.createGraphics();
		for (int i = 0; i < inputImages.size(); i++) {
			g2.drawImage(inputImages.get(i), inputImages.get(0).getWidth() * i, 0, null);
		}
		g2.dispose();

		return joinedImage;
	}

	private List<BufferedImage> pathsToImages() throws IOException {
		List<BufferedImage> inputImages = new ArrayList<BufferedImage>();
		for (File path : inputImagesPaths) {
			inputImages.add(ImageIO.read(path));
			// InputStream in = getClass().getResourceAsStream(path.getName());
			// inputImages.add(ImageIO.read(in));
		}
		return inputImages;
	}
}
