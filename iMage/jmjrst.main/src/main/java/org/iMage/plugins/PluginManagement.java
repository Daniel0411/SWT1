package org.iMage.plugins;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ServiceLoader;

/**
 * Knows all available plug-ins and is responsible for using the service loader
 * API to detect them.
 *
 * @author Dominik Fuchss
 */
public final class PluginManagement {

	/**
	 * No constructor for utility class.
	 */
	private PluginManagement() {
		throw new IllegalAccessError();
	}

	/**
	 * Return an {@link Iterable} Object with all available {@link PluginForJmjrst
	 * PluginForJmjrsts} sorted according to the length of their class names
	 * (shortest first).
	 *
	 * @return an {@link Iterable} Object containing all available plug-ins
	 */
	public static Iterable<PluginForJmjrst> getPlugins() {
		ArrayList<PluginForJmjrst> al = new ArrayList<PluginForJmjrst>();
		ServiceLoader.load(PluginForJmjrst.class).forEach(plugin -> {
			al.add(plugin);
		});
		Collections.sort(al);
		return al;
	}
}
