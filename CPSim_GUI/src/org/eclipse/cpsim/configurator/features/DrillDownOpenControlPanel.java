package org.eclipse.cpsim.configurator.features;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.cpsim.Diagram.SWC;
import org.eclipse.cpsim.Diagram.VirtualCategory;
import org.eclipse.cpsim.Diagram.util.CmdExecuter;
import org.eclipse.cpsim.Diagram.util.DiagramUtil;
import org.eclipse.cpsim.Diagram.util.MESSAGES;
import org.eclipse.cpsim.menu.simulation.RunStopSimulation;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.AbstractDrillDownFeature;

public class DrillDownOpenControlPanel extends AbstractDrillDownFeature {
	private SWC swc = null;
	private final String env_string = MESSAGES.CPSIM_PATH;
	protected final String folder_string = MESSAGES.CPSIM_FOLDER_TOOL;
	protected final String cmd_string = MESSAGES.CPSIM_PLOTTER_CMD;

	public DrillDownOpenControlPanel(IFeatureProvider fp) {
		super(fp);
	}

	public String getCPSIMenv() {
		try {
			String line = System.getenv(env_string);

			// check there are several paths
			if (line.indexOf(";") < 0)
				return line.trim();

			line = line.substring(0, line.indexOf(";"));
			return line.trim();

		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}

	}

	@Override
	public String getName() {
		return "Open Control Panel";
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
		if (RunStopSimulation.state == 0)
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
		return true;
	}

	@Override
	public void execute(ICustomContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		if (pes == null || pes.length > 1)
			return;

		EObject eobj = (EObject) getBusinessObjectForPictogramElement(pes[0]);
		if (eobj instanceof SWC) {
			swc = (SWC) eobj;
			if (swc.getVirtual().equals(VirtualCategory.INVISIBLE))
				return;
		} else
			return;

		try {
			new Thread() {
				public void run() {
					try {
						String dir = getCPSIMenv() + "\\" + folder_string;
						String cmdPath = dir + "\\" + cmd_string;
						cmdPath = cmdPath.replace('/', File.separatorChar);
						List<String> args = new ArrayList<String>();
						args.add(swc.getId().split(";")[0]);
						CmdExecuter.execute(cmdPath, dir, args, false);
					} catch (Exception e) {
					}
				}
			}.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
