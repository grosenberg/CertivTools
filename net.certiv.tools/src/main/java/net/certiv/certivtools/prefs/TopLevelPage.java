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

import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.osgi.framework.Bundle;

import net.certiv.certivtools.CertivTools;

public class TopLevelPage extends PreferencePage implements IWorkbenchPreferencePage {

	private static final String Ident = "net.certiv";
	private static final String Name = "Bundle-Name";
	private static final String Version = "Bundle-Version";

	private static final int Style = SWT.LEFT | SWT.HORIZONTAL;

	public TopLevelPage() {
		super();
		setPreferenceStore(CertivTools.getDefault().getPreferenceStore());
		setDescription("");
	}

	public void init(IWorkbench workbench) {}

	@Override
	protected Control createContents(Composite parent) {

		Composite base = new Composite(parent, SWT.NO_FOCUS);
		GridDataFactory.fillDefaults().grab(true, true).span(2, 1).applyTo(base);
		GridLayoutFactory.fillDefaults().extendedMargins(0, 6, 6, 0).applyTo(base);

		Group toolsGroup = new Group(base, SWT.NONE);
		toolsGroup.setText("Installed Tools");

		Bundle[] bundles = CertivTools.getDefault().getContext().getBundles();
		Map<String, String> kvMap = new HashMap<>();
		for (Bundle b : bundles) {
			if (b.getSymbolicName().startsWith(Ident)) {
				Dictionary<String, String> headers = b.getHeaders();
				kvMap.put(headers.get(Name), b.getSymbolicName() + "_" + headers.get(Version));
			}
		}

		List<String> kList = new LinkedList<>(kvMap.keySet());
		Collections.sort(kList);

		for (String pName : kList) {
			String pVersion = kvMap.get(pName);
			Label lbl = new Label(toolsGroup, Style);
			lbl.setText(pName);
			lbl = new Label(toolsGroup, Style);
			lbl.setText("    " + pVersion);
		}

		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(toolsGroup);
		GridLayoutFactory.fillDefaults().numColumns(2).margins(16, 12).applyTo(toolsGroup);

		return parent;
	}
}
