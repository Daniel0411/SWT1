package org.iMage.iCatcher;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Layout {
	public Layout() {
		buildFrame();
	}

	private void buildFrame() {
		JFrame frame = new JFrame("HDrize");
		frame.setSize(800, 700);
		frame.setVisible(true);
		frame.setResizable(false);

		GridBagLayout layout = new GridBagLayout();
		JPanel panel = new JPanel(layout);
		frame.add(panel);

		GridBagConstraints pImageConstraints = new GridBagConstraints();
		pImageConstraints.
		JPanel previewImage = new JPanel();
		
		
		
		JPanel resultImage = new JPanel();
		Color c = new Color(22);
		previewImage.setBackground(c);
		resultImage.setBackground(c);

		frame.add(previewImage);
		//frame.add(resultImage);
	}
}
