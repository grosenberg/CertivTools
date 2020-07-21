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
package net.certiv.certivtools;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class CertivTools extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "net.certiv.certivtools";

	private static CertivTools plugin;
	private BundleContext context;

	public static CertivTools getDefault() {
		return plugin;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		this.context = context;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	public BundleContext getContext() {
		return context;
	}
}
