package org.eclipse.cpsim.configurator.features;

import java.awt.Desktop;
import java.io.*;
import java.util.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.cpsim.Diagram.SWC;
import org.eclipse.cpsim.Diagram.util.DiagramUtil;
import org.eclipse.cpsim.menu.simulation.RunStopSimulation;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.AbstractDrillDownFeature;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class DrillDownOpenFolder extends AbstractDrillDownFeature {

	public DrillDownOpenFolder(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Open Mapped CODE";
	}

	@Override
	protected Collection<Diagram> getDiagrams() {
		Collection<Diagram> result = Collections.emptyList();
		Resource resource = getDiagram().eResource();
		URI uri = resource.getURI();
		URI uriTrimmed = uri.trimFragment();
		if (uriTrimmed.isPlatformResource()) {
			String platformString = uriTrimmed.toPlatformString(true);
			IResource fileResource = ResourcesPlugin.getWorkspace().getRoot().findMember(platformString);
			if (fileResource != null) {
				IProject project = fileResource.getProject();
				result = DiagramUtil.getDiagrams(project);
			}
		}
		return result;
	}

	@Override
	public boolean isAvailable(IContext context) {
		if (RunStopSimulation.state == 1)
			return false;

		if (!(context instanceof ICustomContext))
			return false;

		ICustomContext icontext = (ICustomContext) context;

		boolean ret = false;
		PictogramElement[] pes = icontext.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof SWC) {
				ret = true;
			}
		}

		return ret;
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof SWC) {
				SWC swc = (SWC) bo;
				String id = swc.getId();

				String[] id_list = id.split(";");
				if (id_list.length == 3 && id_list[1] != null && id_list[2] != null)
					return true;
				else
					return false;

			}
		}

		return false;
	}

	@Override
	public void execute(ICustomContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof SWC) {
				SWC swc = (SWC) bo;
				String id = swc.getId();

				String[] id_list = id.split(";");

				String directory = id_list[2];
				try {
					Runtime obj = Runtime.getRuntime();
					obj.exec("gedit " + directory);
				} catch (Exception e) {
					MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "ERROR",
							"There is no such directory: " + directory);
				}

			}
		}
	}
}
