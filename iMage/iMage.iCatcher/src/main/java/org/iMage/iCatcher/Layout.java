package org.iMage.iCatcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import org.iMage.HDrize.base.images.HDRImageIO.ToneMapping;

public class Layout {

	private Controller controller = new Controller();
	
	private JLabel previewImage = new JLabel();
	private JLabel cameraCurveLabel = new JLabel("CameraCurve");
	private JLabel toneMappingLabel = new JLabel("Tone Mapping");
	private JLabel lambdaLabel = new JLabel("Lambda");
	private JLabel samplesLabel = new JLabel("Samples");

	private JButton resultImage = new JButton();
	private JButton loadDIR = new JButton("Load DIR");
	private JButton loadCurve = new JButton("Load Curve");
	private JButton runHDrize = new JButton("Run HDrize");
	private JButton saveHDR = new JButton("Save HDR");
	private JButton saveCurve = new JButton("Save Curve");
	private JButton showCurve = new JButton("Show Curve");

	private JSlider samples = new JSlider(1, 1000, 142);

	private JTextField lambda = new JTextField("30", 9);

	private JComboBox<ToneMapping> toneMappingBox;
	private JComboBox<String> cameraCurveBox;

	public Layout() {
		resultImage.addActionListener(controller::enlargeResultImage);
		loadDIR.addActionListener(controller::loadDIR);
		loadCurve.addActionListener(controller::loadCurve);
		runHDrize.addActionListener(controller::runHDrize);
		saveHDR.addActionListener(controller::saveHDR);
		saveCurve.addActionListener(controller::saveCurve);
		showCurve.addActionListener(controller::showCurve);
		lambda.addActionListener(controller::lambda);
		samples.addChangeListener(controller::samples);
	}
	
	public JPanel buildGUI() {
		JPanel rootPanel = new JPanel();
		JPanel topPart = buildTopPart();
		JPanel bottomPart = buildBottomPart();
		rootPanel.setLayout(new BorderLayout(10, 10));

		rootPanel.add(topPart, BorderLayout.CENTER);
		rootPanel.add(bottomPart, BorderLayout.PAGE_END);
		rootPanel.setBorder(new EmptyBorder(15, 10, 15, 10));

		return rootPanel;
	}

	private JPanel buildTopPart() {
		JPanel top = new JPanel(new GridLayout(1, 2, 10, 0));
		top.setBorder(new EmptyBorder(0, 0, 10, 0));
		JPanel rightPanel = buildTopRightQuadrant();
		JPanel leftPanel = buildTopLeftQuadrant();

		top.add(leftPanel);
		top.add(rightPanel);

		return top;
	}

	private JPanel buildTopLeftQuadrant() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc;

		gbc = createGridBagConstraints(0, 0, 1, 1, 0, 0);
		gbc.insets = new Insets(0, 0, 10, 0);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(350, 250));
		panel.add(scrollPane, gbc);

		gbc = createGridBagConstraints(0, 1, 1, 1, 0, 1);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(new JLabel(), gbc);

		return panel;
	}
	
	private JPanel buildTopRightQuadrant() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc;

		gbc = createGridBagConstraints(0, 0, 3, 1, 0, 0);
		gbc.insets = new Insets(0, 0, 5, 0);
		resultImage.setPreferredSize(new Dimension(350, 250));
		panel.add(resultImage, gbc);

		gbc = createGridBagConstraints(0, 1, 1, 1, 1, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 0, 0, 5);
		panel.add(showCurve, gbc);

		gbc = createGridBagConstraints(1, 1, 1, 1, 1, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 0, 5);
		panel.add(saveCurve, gbc);

		gbc = createGridBagConstraints(2, 1, 1, 1, 1, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 0, 0);
		panel.add(saveHDR, gbc);

		return panel;
	}

	private JPanel buildBottomPart() {
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridLayout(1, 2, 10, 0));

		JPanel leftPanel = buildBottomLeftQuadrant();
		JPanel rightPanel = buildBottomRightQuadrant();

		bottom.add(leftPanel);
		bottom.add(rightPanel);

		return bottom;
	}

	private JPanel buildBottomLeftQuadrant() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc;

		gbc = createGridBagConstraints(0, 0, 1, 1, 1, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(cameraCurveLabel, gbc);

		String[] cameraCurveBoxString = { "Standard Curve", "Calculated Curve", "Loaded Curve" };
		cameraCurveBox = new JComboBox<String>(cameraCurveBoxString);
		gbc = createGridBagConstraints(0, 1, 1, 1, 1, 0);
		gbc.insets = new Insets(0, 0, 10, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(cameraCurveBox, gbc);

		gbc = createGridBagConstraints(0, 2, 1, 1, 1, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(toneMappingLabel, gbc);

		toneMappingBox = new JComboBox<ToneMapping>(ToneMapping.values());
		toneMappingBox.setSelectedItem(ToneMapping.StandardGamma);
		gbc = createGridBagConstraints(0, 3, 1, 1, 1, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(toneMappingBox, gbc);

		return panel;
	}

	private JPanel buildBottomRightQuadrant() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc;

		gbc = createGridBagConstraints(0, 0, 2, 1, 1, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		samplesLabel.setText("Samples (" + samples.getValue() + ")");
		panel.add(samplesLabel, gbc);

		gbc = createGridBagConstraints(0, 1, 2, 1, 0, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 10, 5);
		samples.setLabelTable(samples.createStandardLabels(999, 1));
		samples.setPaintLabels(true);
		showSliederValue();
		panel.add(samples, gbc);

		gbc = createGridBagConstraints(2, 0, 2, 1, 1, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 5, 0, 0);
		panel.add(lambdaLabel, gbc);

		gbc = createGridBagConstraints(2, 1, 1, 1, 0, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 5, 10, 0);
		lambda.setHorizontalAlignment(JTextField.RIGHT);
		checkLambda();
		panel.add(lambda, gbc);

		gbc = createGridBagConstraints(0, 2, 1, 1, 0, 1);
		panel.add(new JLabel(), gbc);

		gbc = createGridBagConstraints(0, 3, 1, 1, 1, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 5);
		panel.add(loadDIR, gbc);

		gbc = createGridBagConstraints(1, 3, 1, 1, 1, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 5, 0, 5);
		panel.add(loadCurve, gbc);

		gbc = createGridBagConstraints(2, 3, 1, 1, 1, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 5, 0, 0);
		panel.add(runHDrize, gbc);

		return panel;
	}

	private void showSliederValue() {
		samples.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				samplesLabel.setText("Samples (" + ((JSlider) e.getSource()).getValue() + ")");
			}
		});
	}

	private void checkLambda() {
		Document doc = lambda.getDocument();
		doc.addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				checkValue();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				checkValue();
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				checkValue();
			}

			private void checkValue() {
				try {
					double value = Double.parseDouble(lambda.getText());
					if (value <= 0 || value > 100) {
						lambda.setForeground(Color.RED);
					} else {
						lambda.setForeground(Color.BLACK);
					}
				} catch (NumberFormatException e) {
					lambda.setForeground(Color.RED);
				}
			}

		});

	}

	private GridBagConstraints createGridBagConstraints(int x, int y, int width, int height, double weightx,
			double weighty) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.fill = GridBagConstraints.NONE;

		return gbc;
	}
	
	public static void showError(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
	
	public static File loadDirDialog() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Load DIR");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int returnValue = fileChooser.showOpenDialog(null);
		File path = null;
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			path = new File(fileChooser.getSelectedFile().getAbsolutePath());
		}
		
		return path;
	}
}



















