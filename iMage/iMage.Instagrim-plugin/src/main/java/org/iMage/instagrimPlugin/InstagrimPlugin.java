package org.iMage.instagrimPlugin;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.iMage.plugins.PluginForJmjrst;
import org.jis.Main;
import org.kohsuke.MetaInfServices;

@MetaInfServices
public class InstagrimPlugin extends PluginForJmjrst {

	private final String NAME = "Instagrim-Plugin";
	private final boolean IS_CONFIGURABLE = true;

	private Main main;
	private String[] comments = { "Wow, scharfes Pic. Mit iMage erstellt?",
			"Wusste gar nicht, dass sowas mit iMage möglich ist. Voll krasse Farben.",
			"Alter das Bild ist ja mal mega knorke!! Wünschte ich könnte sowas auch!",
			"Oha, hast du das pic etwa mit dieser voll trendigen neuen App namens iMage erstellt?",
			"Krasses HDR Bild. Da bekomme ich direkt Lust das voll günstige Monats-Abo für gerade mal 6,99 Euro abzuschließen!",
			"Diggi so ein hippes Bild hab ich ja noch nie gesehen. iMage ist echt voll nicenstein!" };

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void init(Main main) {
		String userName = System.getProperty("user.name");
		System.out.println(
				"iMage: Der Bildverschönerer, dem Influencer vertrauen! Jetzt bist auch Du Teil unseres Teams, "
						+ userName);
		this.main = main;
	}

	@Override
	public void run() {
		Random rand = new Random();
		int randomNumber = rand.nextInt(comments.length);
		System.out.println(comments[randomNumber]);
	}

	@Override
	public boolean isConfigurable() {
		return IS_CONFIGURABLE;
	}

	@Override
	public void configure() {
		JDialog dialog = new JDialog(main, NAME);
		JList<String> list = new JList<String>(comments);
		JScrollPane scrollPane = new JScrollPane(list);

		Container contentPane = dialog.getContentPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		dialog.pack();
		dialog.setLocationRelativeTo(main);
		dialog.setVisible(true);

	}

}
