package org.iMage.iCatcher;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame("HDrize");
		View l = new View();
		frame.add(l.buildGUI());
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}
}
