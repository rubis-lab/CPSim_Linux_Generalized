package org.eclipse.cpsim.menu.simulation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.cpsim.Diagram.util.MESSAGES;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ResultAnalysisOptions extends AbstractHandler {
	private final String env_string = MESSAGES.CPSIM_PATH;
	private final String folder_string = MESSAGES.CPSIM_FOLDER_TOOL;
	private final String config_string = MESSAGES.CPSIM_CONFIG;
	private final String engine_string = MESSAGES.FIRST_LINE;

	private final String[] config_line = { MESSAGES.START, MESSAGES.END, MESSAGES.INTERVAL, MESSAGES.RUNTIME };

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
	public Object execute(ExecutionEvent event) throws ExecutionException {

		String cpsim_env = getCPSIMenv();
		if (cpsim_env == null) {
			IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
			MessageDialog.openWarning(window.getShell(), "ERROR",
					"Check the following environment variable: " + env_string);
			return null;
		}

		File f = new File(cpsim_env);
		if (!f.isDirectory()) {
			IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
			MessageDialog.openWarning(window.getShell(), "ERROR",
					"Check the following directory is exists: " + cpsim_env + "\\" + folder_string);
			return null;
		}

		String configPath = cpsim_env + "\\" + folder_string + "\\" + config_string;
		if (!ConfigWriter.CheckConfigFile(configPath))
			return null;

		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		TitleAreaDialog dialog = new ResultAnalysisDialog(window.getShell());
		dialog.open();
		return null;
	}

	public void WriteConfig(String path) {
		/*
		 * default config path: getCPSIMenv() + "\\" + folder_string + "\\" +
		 * config_string
		 */
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(path));
			bw.write(engine_string);
			bw.newLine();
			for (int i = 0; i < config_line.length; i++) {
				bw.write(config_line[i]);
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
