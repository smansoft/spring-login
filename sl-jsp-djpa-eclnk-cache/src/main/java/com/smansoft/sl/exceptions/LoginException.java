/**
 * 
 */
package com.smansoft.sl.exceptions;

import java.io.IOException;

/**
 * @author SMan
 *
 */
public class LoginException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3228189739206883683L;

	/**
	 * 
	 */
	public LoginException() {
	}

	/**
	 * 
	 * @param message
	 */
	public LoginException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public LoginException(String message, Throwable cause) {
		super(message, cause);
	}

}