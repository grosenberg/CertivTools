package net.certiv.certivtools.prefs.blocks;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

import net.certiv.certivtools.CertivTools;

public class InstalledBlock {

	private static final String PkgPrefix = "net.certiv";
	private static final String BundleName = "Bundle-Name";

	private static final int STYLE = SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE | SWT.BORDER
			| SWT.FULL_SELECTION;

	private TableViewer viewer;

	public Control createTable(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).grab(true, false).applyTo(comp);

		TableColumnLayout layout = new TableColumnLayout();
		comp.setLayout(layout);

		Table table = new Table(comp, STYLE);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setFont(parent.getFont());

		TableColumn nameCol = new TableColumn(table, SWT.NONE, 0);
		nameCol.setText("Bundle Name");
		layout.setColumnData(nameCol, new ColumnWeightData(30));

		TableColumn symbCol = new TableColumn(table, SWT.NONE, 1);
		symbCol.setText("Symbolic Name");
		layout.setColumnData(symbCol, new ColumnWeightData(20));

		TableColumn verCol = new TableColumn(table, SWT.NONE, 2);
		verCol.setText("Version");
		layout.setColumnData(verCol, new ColumnWeightData(1, chToPx(comp, 15)));

		viewer = new TableViewer(table);
		viewer.setUseHashlookup(true);
		viewer.setLabelProvider(new ElemLabelProvider());
		viewer.setContentProvider(new ElemContentProvider());

		GridDataFactory.fillDefaults().grab(true, false).applyTo(viewer.getControl());
		return comp;
	}

	public void initialize() {
		Assert.isNotNull(viewer);
		viewer.setInput(createModel());
		viewer.getTable().setEnabled(true);
	}

	private Elem[] createModel() {
		Bundle[] bundles = CertivTools.getDefault().getContext().getBundles();

		List<Elem> elems = new LinkedList<>();
		for (Bundle bundle : bundles) {
			if (bundle.getSymbolicName().startsWith(PkgPrefix)) {
				String name = bundle.getHeaders().get(BundleName);
				String symb = bundle.getSymbolicName();
				Version ver = bundle.getVersion();
				String version = String.format("%s.%s.%s", ver.getMajor(), ver.getMinor(), ver.getMicro());
				elems.add(new Elem(name, symb, version));
			}
		}

		Collections.sort(elems);
		return elems.toArray(new Elem[elems.size()]);
	}

	private int chToPx(Control control, int chars) {
		PixelConverter pc = new PixelConverter(control);
		return pc.convertWidthInCharsToPixels(chars);
	}

	private class Elem implements Comparable<Elem> {
		final String name;
		final String symb;
		final String version;

		Elem(String name, String symb, String version) {
			this.name = name;
			this.symb = symb;
			this.version = version;
		}

		@Override
		public int compareTo(Elem e) {
			int v = name.compareTo(e.name);
			if (v != 0) return v;
			v = symb.compareTo(e.symb);
			if (v != 0) return v;
			return version.compareTo(e.version);
		}
	}

	private class ElemContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object input) {
			return (Elem[]) input;
		}

		@Override
		public void dispose() {}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
	}

	private class ElemLabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object row, int idxCol) {
			switch (idxCol) {
				case 0:
					return ((Elem) row).name;
				case 1:
					return ((Elem) row).symb;
				case 2:
					return ((Elem) row).version;
				default:
					Assert.isLegal(false);
			}
			return null;
		}

		@Override
		public void addListener(ILabelProviderListener listener) {}

		@Override
		public void dispose() {}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {}
	}
}
