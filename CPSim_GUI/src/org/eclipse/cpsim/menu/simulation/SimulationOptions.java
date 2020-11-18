package org.eclipse.cpsim.menu.simulation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.State;
import org.eclipse.cpsim.Diagram.util.MESSAGES;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SimulationOptions extends AbstractHandler {
	private static final String env_string = MESSAGES.CPSIM_PATH;
	//private static final String folder_string = MESSAGES.CPSIM_FOLDER_TOOL;
	//private static final String config_string = MESSAGES.CPSIM_CONFIG;
	private final String engine_string = MESSAGES.FIRST_LINE;
	public static int mult = 0; // 0 for none, 1 for mult
	private final String[] config_line = { MESSAGES.START, MESSAGES.END, MESSAGES.INTERVAL, MESSAGES.RUNTIME };
	private static final String multi_run = MESSAGES.MULTI_RUN;

	public static String getCPSIMenv() {
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

	public static boolean boolmult() {
		return mult == 1;
	}


	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ICommandService service = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
		Command command = service.getCommand("hyundaiConfiguratorGraphiti.commands.AnalyzeDynamicMemory");
		State state = command.getState("org.eclipse.ui.commands.toggleState");
		System.out.println("Dynamic: " + state.getValue());
		command = service.getCommand("hyundaiConfiguratorGraphiti.commands.AnalyzeSystemWideRuntimeBehavior");
		state = command.getState("org.eclipse.ui.commands.toggleState");
		System.out.println("System: " + state.getValue());
		// state.setValue(!(Boolean) state.getValue());
		String cpsim_env = getCPSIMenv();
		if (cpsim_env == null) {
			IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
			MessageDialog.openWarning(window.getShell(), "ERROR",
					"Check the following environment variable: " + env_string);
			return null;
		}

		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		TitleAreaDialog dialog = new SimulationOptionDialog(window.getShell());
		dialog.open();
		return null;
	}
}
