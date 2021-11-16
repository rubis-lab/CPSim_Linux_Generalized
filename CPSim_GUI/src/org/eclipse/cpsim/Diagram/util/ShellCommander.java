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
		Runtime runtime = Runtime.getRuntime();
	 	Process process = runtime.exec(strings);
		BufferedReader reader = new BufferedReader(
			new InputStreamReader(process.getInputStream()));
		int read;
		char[] buffer = new char[4096];
		StringBuffer output = new StringBuffer();
		while ((read = reader.read(buffer)) > 0) {
       			theRun = output.append(buffer, 0, read);
		}
		System.out.println(theRun);
		reader.close();
		process.waitFor();
		return theRun;
	}
	public static void shellCmd(String string) throws Exception {
	}



}