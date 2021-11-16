package org.eclipse.cpsim.Diagram.util;

import java.io.BufferedReader;

public class ShellCommander {
	public static void shellCmd(String command) throws Exception {
		Runtime runtime = Runtime.getRuntime();
	    Process process = runtime.exec(command);
	}
}