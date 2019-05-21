package org.iMage.HDrize;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.cli.*;
import org.apache.commons.imaging.ImageReadException;
import org.iMage.HDrize.base.images.EnhancedImage;
import org.iMage.HDrize.base.images.HDRImageIO.ToneMapping;
import org.iMage.HDrize.matrix.MatrixCalculator;

/**
 * This class parses all command line parameters and creates an HDRImage.
 *
 */
public final class App {
	private App() {
		throw new IllegalAccessError();
	}

	private static final String CMD_OPTION_INPUT_IMAGES = "i";
	private static final String CMD_OPTION_OUTPUT_IMAGE = "o";
	private static final String CMD_OPTION_LAMBDA = "l";
	private static final String CMD_OPTION_SAMPLES = "s";
	private static final double LAMBDA_UPPER_LIMIT = 100;
	private static final double LAMBDA_LOWER_LIMIT = 0;
	private static final double SAMPLES_UPPER_LIMIT = 1000;
	private static final double SAMPLES_LOWER_LIMIT = 1;
	private static final int PREFIX_LENGTH = 3;

	public static void main(String[] args) {
		// Don't touch...
		CommandLine cmd = null;
		try {
			cmd = App.doCommandLineParsing(args);
		} catch (ParseException e) {
			System.err.println("Wrong command line arguments given: " + e.getMessage());
			System.exit(1);
		}
		// ...this!

		String input = cmd.getOptionValue("input-images");
		String output = cmd.getOptionValue("image-output");
		double lambda = 30;
		int samples = 142;

		if (cmd.hasOption("lambda")) {
			try {
				lambda = Double.parseDouble(cmd.getOptionValue("lamdba"));
				if (lambda <= LAMBDA_LOWER_LIMIT || lambda > LAMBDA_UPPER_LIMIT) {
					throw new IllegalArgumentException("lambda must be in (0,100]!");
				}
			} catch (NumberFormatException e) {
				printErrorAndExit(e);
			} catch (IllegalArgumentException e) {
				printErrorAndExit(e);
			}
		}

		if (cmd.hasOption("samples")) {
			try {
				samples = Integer.parseInt(cmd.getOptionValue("samples"));
				if (samples < SAMPLES_LOWER_LIMIT || samples > SAMPLES_UPPER_LIMIT) {
					throw new IllegalArgumentException("samples must be in [1,1000]!");
				}
			} catch (NumberFormatException e) {
				printErrorAndExit(e);
			} catch (IllegalArgumentException e) {
				printErrorAndExit(e);
			}
		}

		EnhancedImage[] imageList = imageReader(new File(input));
		HDrize hdrize = new HDrize();
		MatrixCalculator mtxCalc = new MatrixCalculator();
		BufferedImage resultImage = hdrize.createRGB(imageList, samples, lambda, mtxCalc, ToneMapping.SRGBGamma);

		try {
			ImageIO.write(resultImage, "png", new File(output + "/result.png"));
		} catch (IOException e) {
			printErrorAndExit(e);
		}
	}

	private static EnhancedImage[] imageReader(File input) {
		File[] listOfFiles = input.listFiles();
		EnhancedImage[] imageList = null;
		try {
			imageList = new EnhancedImage[listOfFiles.length];
			if (listOfFiles.length % 2 == 0) {
				throw new IllegalArgumentException("Number of images must be odd!");
			}
			if (listOfFiles[0].getName().length() < 3) {
				throw new IllegalArgumentException("All image files must have a same prefix of at least 3 characters!");
			}
			String prefix = listOfFiles[0].getName().substring(0, PREFIX_LENGTH - 1);

			for (int i = 0; i < listOfFiles.length; i++) {
				InputStream inputStreamImage = new FileInputStream(listOfFiles[i]);
				imageList[i] = new EnhancedImage(inputStreamImage);
				if (!listOfFiles[i].getName().substring(0, PREFIX_LENGTH - 1).equals(prefix)) {
					throw new IllegalArgumentException(
							"All image files must have a same prefix of at least 3 characters!");
				}
			}
		} catch (FileNotFoundException e) {
			printErrorAndExit(e);
		} catch (ImageReadException e) {
			printErrorAndExit(e);
		} catch (IOException e) {
			printErrorAndExit(e);
		} catch (IllegalArgumentException e) {
			printErrorAndExit(e);
		} catch (NullPointerException e) {
			printErrorAndExit(e);
		}
		return imageList;
	}

	private static void printErrorAndExit(Exception e) {
		System.err.println(e.getMessage());
		System.exit(1);
	}

	/**
	 * Parse and check command line arguments
	 *
	 * @param args command line arguments given by the user
	 * @return CommandLine object encapsulating all options
	 * @throws ParseException if wrong command line parameters or arguments are
	 *                        given
	 */
	private static CommandLine doCommandLineParsing(String[] args) throws ParseException {
		Options options = new Options();
		Option opt;

		/*
		 * Define command line options and arguments
		 */
		opt = new Option( //
				App.CMD_OPTION_INPUT_IMAGES, "input-images", true, "path to folder with input images");
		opt.setRequired(true);
		opt.setType(String.class);
		options.addOption(opt);

		opt = new Option(App.CMD_OPTION_OUTPUT_IMAGE, "image-output", true, "path to output image");
		opt.setRequired(true);
		opt.setType(String.class);
		options.addOption(opt);

		opt = new Option(App.CMD_OPTION_LAMBDA, "lambda", true, "the lambda value of algorithm");
		opt.setRequired(false);
		opt.setType(Double.class);
		options.addOption(opt);

		opt = new Option(App.CMD_OPTION_SAMPLES, "samples", true, "the number of samples");
		opt.setRequired(false);
		opt.setType(Integer.class);
		options.addOption(opt);

		CommandLineParser parser = new DefaultParser();
		return parser.parse(options, args);
	}
}