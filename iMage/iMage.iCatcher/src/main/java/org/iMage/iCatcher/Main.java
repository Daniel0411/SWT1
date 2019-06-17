package org.iMage.iCatcher;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame("HDrize");
		Layout l = new Layout();
		frame.add(l.buildGUI());
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}
}
