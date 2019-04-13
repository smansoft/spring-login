/**
 * 
 */
package com.smansoft.sl.exceptions;

/**
 * 
 * @author SMan
 *
 */
public class InitException extends Exception {

	/**
     * 
     */
	private static final long serialVersionUID = -7760577546599648392L;

	/**
	 * 
	 */
	public InitException() {
	}

	/**
	 * 
	 * @param message
	 */
	public InitException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public InitException(String message, Throwable cause) {
		super(message, cause);
	}
}
