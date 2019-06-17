package org.iMage.iCatcher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Calculator {

	private List inputImages;
	
	public void loadDir(File path) {
		File path = Layout.loadDirDialog();
		for (File file : path.listFiles(f -> f.getName().endsWith(".jpeg") || f.getName().endsWith(".jpg"))) {
			images.add(file);
		}
		if (images.size() % 2 == 0 || images.size() < 3) {
			Layout.showError("You have to choose at least 3 jpg files. The number of files must be odd!");
			return;
		}

		for (File file : images) {
			if (file.getName().length() < 3
					|| !file.getName().substring(0, 3).equals(images.get(0).getName().substring(0, 3))) {
				Layout.showError("All files must have a common prefix of at least 3 characters!");
			}
		}
		
		inputImages = images; 
	}
}
