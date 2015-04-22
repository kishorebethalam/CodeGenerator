package com.codegen.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShellComandInvoker {

	public static void main(String[] args) {

//		ShellComandInvoker obj = new ShellComandInvoker();
//
//		String domainName = "google.com";
//
//		// in mac oxs
//		String command = "ping -c 3 " + domainName;
//
//		// in windows
//		// String command = "ping -n 3 " + domainName;
//
//		String output = obj.executeCommand(command);
//
//		System.out.println(output);

	}

	public static String executeCommand(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(output.toString());
		return output.toString();

	}
//
//	static public String[] executeMVNCommand(String cmd) throws IOException {
//
//		// The actual procedure for process execution:
//		// runCommand(String cmd);
//		// Create a list for storing output.
//		ArrayList list = new ArrayList();
//		// Execute a command and get its process handle
//		Process proc = Runtime.getRuntime().exec(cmd);
//		Runtime.getRuntime().
//		// Get the handle for the processes InputStream
//		InputStream istr = proc.getInputStream();
//		// Create a BufferedReader and specify it reads
//		// from an input stream.
//
//		BufferedReader br = new BufferedReader(new InputStreamReader(istr));
//		String str; // Temporary String variable
//		// Read to Temp Variable, Check for null then
//		// add to (ArrayList)list
//		while ((str = br.readLine()) != null)
//			list.add(str);
//		// Wait for process to terminate and catch any Exceptions.
//		try {
//			proc.waitFor();
//		} catch (InterruptedException e) {
//			System.err.println("Process was interrupted");
//		}
//		// Note: proc.exitValue() returns the exit value.
//		// (Use if required)
//		br.close(); // Done.
//		// Convert the list to a string and return
//		return (String[]) list.toArray(new String[0]);
//	}
}
