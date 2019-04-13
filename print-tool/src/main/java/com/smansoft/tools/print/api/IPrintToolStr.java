/**
 * 
 */
package com.smansoft.tools.print.api;

import com.smansoft.tools.print.api.types.PrintLevel;
import com.smansoft.tools.print.api.types.PrintSfx;

/**
 * @author SMan
 *
 */
public interface IPrintToolStr extends IPrintToolBase {
	
	/***********************************************************************************************/	
	/**
	 * 
	 * @param printLevel
	 * @param startMessage
	 * @param object
	 */
	void log(PrintLevel printLevel, String startMessage, Object object);

	/**
	 * 
	 * @param printLevel
	 * @param startMessage
	 * @param message
	 * @param ex
	 */
	void log(PrintLevel printLevel, String startMessage, Object message, Throwable ex);

	/**
	 * 
	 * @param printLevel
	 * @param startMessage
	 * @param printable
	 */
	void log(PrintLevel printLevel, String startMessage, IPrintable printable);

	/**
	 * 
	 * @param printLevel
	 * @param startMessage
	 * @param printSfx
	 */
	void log(PrintLevel printLevel, String startMessage, PrintSfx printSfx);
	/***********************************************************************************************/	
	/**
	 * 
	 * @param startMessage
	 * @param message
	 */
	void error(String startMessage, Object message);

	/**
	 * 
	 * @param startMessage
	 * @param message
	 * @param ex
	 */
	void error(String startMessage, Object message, Throwable ex);

	/**
	 * 
	 * @param startMessage
	 * @param printable
	 */
	void error(String startMessage, IPrintable printable);

	/**
	 * 
	 * @param startMessage
	 * @param printSfx
	 */
	void error(String startMessage, PrintSfx printSfx);
	/***********************************************************************************************/	
	/**
	 * 
	 * @param startMessage
	 * @param message
	 */
	void warn(String startMessage, Object message);

	/**
	 * 
	 * @param startMessage
	 * @param message
	 * @param ex
	 */
	void warn(String startMessage, Object message, Throwable ex);

	/**
	 * 
	 * @param startMessage
	 * @param printable
	 */
	void warn(String startMessage, IPrintable printable);

	/**
	 * 
	 * @param startMessage
	 * @param printSfx
	 */
	void warn(String startMessage, PrintSfx printSfx);
	/***********************************************************************************************/
	/**
	 * 
	 * @param startMessage
	 * @param message
	 */
	void info(String startMessage, Object message);

	/**
	 * 
	 * @param startMessage
	 * @param message
	 * @param ex
	 */
	void info(String startMessage, Object message, Throwable ex);

	/**
	 * 
	 * @param startMessage
	 * @param printable
	 */
	void info(String startMessage, IPrintable printable);

	/**
	 * 
	 * @param startMessage
	 * @param printSfx
	 */
	void info(String startMessage, PrintSfx printSfx);
	/***********************************************************************************************/
	/**
	 * 
	 * @param startMessage
	 * @param message
	 */
	void debug(String startMessage, Object message);

	/**
	 * 
	 * @param startMessage
	 * @param message
	 * @param ex
	 */
	void debug(String startMessage, Object message, Throwable ex);

	/**
	 * 
	 * @param startMessage
	 * @param printable
	 */
	void debug(String startMessage, IPrintable printable);

	/**
	 * 
	 * @param startMessage
	 * @param printSfx
	 */
	void debug(String startMessage, PrintSfx printSfx);
	/***********************************************************************************************/	
	/**
	 * 
	 * @param startMessage
	 * @param message
	 */
	void trace(String startMessage, Object message);

	/**
	 * 
	 * @param startMessage
	 * @param message
	 * @param ex
	 */
	void trace(String startMessage, Object message, Throwable ex);

	/**
	 * 
	 * @param startMessage
	 * @param printable
	 */
	void trace(String startMessage, IPrintable printable);

	/**
	 * 
	 * @param startMessage
	 * @param printSfx
	 */
	void trace(String startMessage, PrintSfx printSfx);
	/***********************************************************************************************/
}
