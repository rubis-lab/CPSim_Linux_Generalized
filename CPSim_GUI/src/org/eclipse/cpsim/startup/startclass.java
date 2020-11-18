package org.eclipse.cpsim.startup;

import org.eclipse.cpsim.Diagram.util.MESSAGES;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;

public class startclass implements IStartup {
	private final String env_string = MESSAGES.CPSIM_PATH;
	private String cpsim_path = null;


	public String getCPSIMenv() {
		if (cpsim_path != null)
			return cpsim_path;

		try {
			String line = System.getenv(env_string);

			// check there are several paths
			if (line.indexOf(";") < 0)
				return line.trim();

			line = line.substring(0, line.indexOf(";"));
			cpsim_path = line.trim();
			return cpsim_path;

		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}

	}


	@Override
	public void earlyStartup() {
		IHandlerService hs = (IHandlerService) PlatformUI.getWorkbench().getService(IHandlerService.class);

		try {
			//hs.executeCommand("hyundaiConfiguratorGraphiti.commands.RunStopSimulation", null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Display.getDefault().syncExec(new Runnable() {
			public void run() {
	
				return;

			}
		});

	}

}
