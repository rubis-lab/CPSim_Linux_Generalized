package org.eclipse.cpsim.menu.simulation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.eclipse.cpsim.Diagram.util.MESSAGES;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class ConfigWriter {
	private static final String env_string = MESSAGES.DOXYGEN_PATH;
	private static final String engine_string = MESSAGES.FIRST_LINE;
	private static final String[] config_line = { MESSAGES.START, MESSAGES.END, MESSAGES.INTERVAL, MESSAGES.RUNTIME };
	private static final String doxygen_symbol = MESSAGES.DOXYGEN_SYMBOL_PATH;
	private static final String multi_run = MESSAGES.MULTI_RUN;

	private static String getDOXYGENenv() {
		try {
			String line = System.getenv(env_string);

			// check there are several paths
			if (line.indexOf(";") < 0)
				return line.trim();

			line = line.substring(0, line.indexOf(";"));
			return line.trim();

		} catch (Exception e) {
			return null;
		}

	}

	public static boolean CheckConfigFile(String path) {
		if (getDOXYGENenv() == null) {
			MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "ERROR",
					"Check the following environment variable: " + env_string);
			return false;
		}

		File f = new File(path);

		if (f.isFile()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(path));
				String line = br.readLine();

				if (line.startsWith(engine_string)) {
					br.close();
					return true;
				}
				br.close();

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		WriteConfig(path);
		return true;
	}

	public static void WriteConfig(String path) {
		/*
		 * default config path: getCPSIMenv() + "\\" + folder_string + "\\" +
		 * config_string
		 */
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(path));
			bw.write(engine_string);
			bw.newLine();
			bw.write(multi_run + "0");
			bw.newLine();
			for (int i = 0; i < config_line.length; i++) {
				bw.write(config_line[i]);
				bw.newLine();
			}
			bw.write(doxygen_symbol);
			bw.write(getDOXYGENenv());
			bw.newLine();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
