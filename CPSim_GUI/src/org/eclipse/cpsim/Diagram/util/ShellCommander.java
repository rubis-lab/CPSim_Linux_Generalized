package org.eclipse.cpsim.Diagram.util;

public class ShellCommander {
	public static void shellCmd(String command) throws Exception {
		Runtime runtime = Runtime.getRuntime();
	    Process process = runtime.exec(command);
	}
}