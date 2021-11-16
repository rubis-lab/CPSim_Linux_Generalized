package org.eclipse.cpsim.Diagram.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShellCommander {
	public static void shellCmd(String command) throws Exception {
		Runtime runtime = Runtime.getRuntime();
	    Process process = runtime.exec(command);
	}
}