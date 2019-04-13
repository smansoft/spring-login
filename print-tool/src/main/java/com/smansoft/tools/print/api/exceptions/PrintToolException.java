/**
 * 
 */
package com.smansoft.tools.print.api.exceptions;

/**
 * 
 * @author SMan
 *
 */
public class PrintToolException extends Exception {

	/**
     * 
     */
	private static final long serialVersionUID = -7760577546599648392L;

	/**
	 * 
	 */
	public PrintToolException() {
	}

	/**
	 * 
	 * @param message
	 */
	public PrintToolException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public PrintToolException(String message, Throwable cause) {
		super(message, cause);
	}
}
