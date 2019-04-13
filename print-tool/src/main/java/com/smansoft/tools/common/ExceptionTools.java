/**
 * 
 */
package com.smansoft.tools.common;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * @author SMan
 *
 */
public abstract class ExceptionTools {

	/**
	 * 
	 * @param ex
	 * @return
	 */
	public static String stackTraceToString(Throwable ex) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(byteArrayOutputStream);
		printStream.println(ex.toString());
		StackTraceElement[] stacks = ex.getStackTrace();
		for (StackTraceElement stack : stacks) {
			printStream.println(stack.toString());
		}
		printStream.flush();
		return byteArrayOutputStream.toString();
	}

}
