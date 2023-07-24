/*******************************************************************************
 * Copyright 2013, 2020, Gerald B. Rosenberg, Certiv Analytics.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Certiv Analytics
 *******************************************************************************/
package net.certiv.certivtools.prefs.pages;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import net.certiv.certivtools.CertivTools;
import net.certiv.certivtools.prefs.blocks.InstalledBlock;

public class TopLevelPage extends PreferencePage implements IWorkbenchPreferencePage {

	private static final String CERTIV_TOOLS = "Certiv Tools";
	private static final String INSTALLED = "Installed";

	public TopLevelPage() {
		super();
		setPreferenceStore(CertivTools.getDefault().getPreferenceStore());
		setDescription(CERTIV_TOOLS);
	}

	@Override
	public void init(IWorkbench workbench) {}

	@Override
	protected Control createContents(Composite parent) {
		Composite base = new Composite(parent, SWT.NO_FOCUS);
		GridDataFactory.fillDefaults().grab(true, true).span(2, 1).applyTo(base);
		GridLayoutFactory.fillDefaults().extendedMargins(0, 6, 6, 0).applyTo(base);

		Group group = new Group(base, SWT.NONE);
		group.setText(INSTALLED);

		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(group);
		GridLayoutFactory.fillDefaults().margins(5, 5).applyTo(group);

		InstalledBlock block = new InstalledBlock();
		block.createTable(group);
		block.initialize();

		return parent;
	}
}
