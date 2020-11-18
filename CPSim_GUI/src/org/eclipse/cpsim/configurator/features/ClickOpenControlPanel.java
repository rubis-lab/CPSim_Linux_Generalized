package org.eclipse.cpsim.configurator.features;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.cpsim.Diagram.SWC;
import org.eclipse.cpsim.Diagram.util.CmdExecuter;
import org.eclipse.cpsim.Diagram.util.MESSAGES;
import org.eclipse.cpsim.menu.simulation.RunStopSimulation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class ClickOpenControlPanel extends AbstractCustomFeature {
	private SWC swc = null;
	private final String env_string = MESSAGES.CPSIM_PATH;
	protected final String folder_string = MESSAGES.CPSIM_FOLDER_TOOL;
	protected final String cmd_string = MESSAGES.CPSIM_PLOTTER_CMD;

	public ClickOpenControlPanel(IFeatureProvider fp) {
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
	public boolean canExecute(ICustomContext context) {
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
	public void execute(ICustomContext context) {

		PictogramElement[] pes = context.getPictogramElements();
		if (pes == null || pes.length > 1)
			return;

		EObject eobj = (EObject) getBusinessObjectForPictogramElement(pes[0]);
		if (eobj instanceof SWC) {
			swc = (SWC) eobj;
		} else
			return;

		if (!MessageDialog.openConfirm(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				"Open Control Panel", "Open Control Panel of " + swc.getId().split(";")[0] + "?"))
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
