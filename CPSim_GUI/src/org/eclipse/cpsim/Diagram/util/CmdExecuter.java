package org.eclipse.cpsim.Diagram.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.cpsim.menu.simulation.SimulationOptions;

public class CmdExecuter {
	public static void addConfigFromFile(String configPath, List<String> configs) {
		try {
			FileInputStream fis = new FileInputStream(configPath);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			String line;
			while ((line = br.readLine()) != null) {
				int idx = line.lastIndexOf('=') + 1;
				String val = "";
				if (idx != -1)
					val = line.substring(idx);

				if (SimulationOptions.boolmult() && line.startsWith(MESSAGES.START)) {
					configs.add("-gs");
					if (val.equals(""))
						val = "0";
					configs.add(val);
				} else if (SimulationOptions.boolmult() && line.startsWith(MESSAGES.END)) {
					configs.add("-ge");
					if (val.equals(""))
						val = "5";
					configs.add(val);
				} else if (SimulationOptions.boolmult() && line.startsWith(MESSAGES.INTERVAL)) {
					configs.add("-gi");
					if (val.equals(""))
						val = "1";
					configs.add(val);
				} else if (SimulationOptions.boolmult() && line.startsWith(MESSAGES.RUNTIME)) {
					configs.add("-t");
					if (val.equals(""))
						val = "1000";
					configs.add(val);
				}
				/*
				 * else if(line.startsWith(MESSAGES.THRESHOLD)) { configs.add("-ths");
				 * configs.add(val); } else if(line.startsWith(MESSAGES.ROI)) {
				 * configs.add("-roi"); configs.add(val.replaceAll(" ", "")); }
				 */
				else if (line.startsWith(MESSAGES.WAIT_SIGNAL) && val.equalsIgnoreCase("on")) {
					configs.add("-w");
				} else if (line.startsWith(MESSAGES.DOXYGEN_SYMBOL_PATH)) {
					configs.add("-s");
					configs.add(val);
				}
			}

			br.close();
		} catch (Exception ex) {

		}
	}

	public static void execute(String cmdPath, String dir, List<String> exeArgs, boolean logging) {
		try {
			FileInputStream fis = new FileInputStream(cmdPath);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			String line = "";
			System.out.println("dir : " + dir);
			List<String> cmd = new ArrayList<String>();
			cmd.clear();
			cmd.add("cmd");
			cmd.add("/c");
			cmd.add("start");

			while ((line = br.readLine()) != null) {
				if (line.length() <= 1)
					break;

				String[] args = line.split(" ");
				for (String s : args) {
					if (s.equals("cd"))
						dir = args[1];

					if (!args[0].equals("cd"))
						cmd.add(s);
				}
				if (!args[0].equals("cd"))
					cmd.add("&&");

			}
			if (cmd.size() > 2)
				cmd.remove(cmd.size() - 1);
			if (exeArgs != null) {
				for (String s : exeArgs)
					cmd.add(s);
			}
			System.out.println(cmd);
			Process p = new ProcessBuilder(cmd).directory(new File(dir)).start();
			br.close();

			if (!logging)
				return;

			FileOutputStream log = new FileOutputStream("log.txt");

			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			String result = "";
			String errorLine = "";
			String linefeed = "\r\n";

			while (result != null) {
				log.write(result.getBytes());
				log.write(linefeed.getBytes());
				result = reader.readLine();
			}

			String errormsg = "--error--";
			log.write(errormsg.getBytes());
			log.write(linefeed.getBytes());

			while (errorLine != null) {
				log.write(errorLine.getBytes());
				log.write(linefeed.getBytes());
				errorLine = errorReader.readLine();
			}

			log.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
