/*******************************************************************************
 * Copyright 2013, Gerald B. Rosenberg, Certiv Analytics.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Certiv Analytics 
 *******************************************************************************/
package net.certiv.certivtools.prefs;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import net.certiv.certivtools.CertivTools;

/**
 * Class used to initialize default preference values.
 */
public class PrefsInit extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = CertivTools.getDefault().getPreferenceStore();
		store.setDefault(PrefsKey.CG_ANIMATION_LIMIT, 500);
	}
}
