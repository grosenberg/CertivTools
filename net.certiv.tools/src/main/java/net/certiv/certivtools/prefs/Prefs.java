/*******************************************************************************
 * Copyright 2013, 2019, Gerald B. Rosenberg, Certiv Analytics.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Certiv Analytics
 *******************************************************************************/
package net.certiv.certivtools.prefs;

import java.util.Date;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.FontData;

import net.certiv.certivtools.CertivTools;

public class Prefs {

	private Prefs() {}

	public static IPreferenceStore getStore() {
		return CertivTools.getDefault().getPreferenceStore();
	}

	public static boolean getBoolean(String key) {
		return getStore().getBoolean(key);
	}

	public static int getInt(String key) {
		return getStore().getInt(key);
	}

	public static String getString(String key) {
		return getStore().getString(key);
	}

	public static boolean matches(String key, String value) {
		return getStore().getString(key).equals(value);
	}

	public static FontData[] getFontData(String key) {
		return PreferenceConverter.getFontDataArray(getStore(), key);
	}

	public static Date getDate(String key) {
		Date date = new Date(getStore().getLong(key));
		return date;
	}

	public static void setValue(String key, Object value) {
		if (value instanceof String) getStore().setValue(key, (String) value);
		if (value instanceof Boolean) getStore().setValue(key, ((Boolean) value).booleanValue());
		if (value instanceof Integer) getStore().setValue(key, ((Integer) value).intValue());
		if (value instanceof Double) getStore().setValue(key, ((Double) value).doubleValue());
		if (value instanceof Long) getStore().setValue(key, ((Long) value).longValue());
		if (value instanceof Float) getStore().setValue(key, ((Float) value).floatValue());
		if (value instanceof Date) setValue(key, Long.valueOf(((Date) value).getTime()));
	}
}
