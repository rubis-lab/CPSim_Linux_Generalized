package org.eclipse.cpsim.Diagram.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShellCommander {
	public static void shellCmdArray(String[] strings) throws Exception {
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(strings);
		process.waitFor();
	}
	public static StringBuffer shellCmdTopic(String[] strings) throws Exception {

		StringBuffer theRun = null;

}