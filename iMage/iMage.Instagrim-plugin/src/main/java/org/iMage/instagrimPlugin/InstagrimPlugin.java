package org.iMage.instagrimPlugin;

import java.util.Random;

import org.iMage.plugins.PluginForJmjrst;
import org.jis.Main;
import org.kohsuke.MetaInfServices;

@MetaInfServices
public class InstagrimPlugin extends PluginForJmjrst {

	private final String NAME = "Instagrim-Plugin";
	private final boolean IS_CONFIGURABLE = true;
	private String[] comments = { "Wow, scharfes Pic. Mit iMage erstellt?",
			"Wusste gar nicht, dass sowas mit iMage möglich ist. Voll krasse Farben." };

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
		// TODO Auto-generated method stub

	}

}
