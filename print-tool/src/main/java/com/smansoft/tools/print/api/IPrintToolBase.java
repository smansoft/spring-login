/**
 * 
 */
package com.smansoft.tools.print.api;


import org.slf4j.Logger;
import org.slf4j.event.Level;

import com.smansoft.tools.print.api.types.PrintLevel;



/**
 * @author SMan
 *
 */
public interface IPrintToolBase extends IPrintable {
	
	/**
	 * 
	 */
	static final String DEF_USE_PRINT_CTX_PROP = "print.use_print_ctx";

	/**
	 * 
	 */
	static final String DEF_USE_PRINT_SERVICE_PROP = "print.use_print_service";

	/**
	 * 
	 */
	static final String DEF_USE_PRINT_ERROR_LEVEL_PROP = "print.error.level";

	/**
	 * 
	 */
	static final String DEF_USE_PRINT_WARN_LEVEL_PROP = "print.warn.level";

	/**
	 * 
	 */
	static final String DEF_USE_PRINT_INFO_LEVEL_PROP = "print.info.level";

	/**
	 * 
	 */
	static final String DEF_USE_PRINT_DEBUG_LEVEL_PROP = "print.debug.level";

	/**
	 * 
	 */
	static final String DEF_USE_PRINT_TRACE_LEVEL_PROP = "print.trace.level";

	/**
	 * 
	 */
	void readProperties();

	/**
	 * 
	 * @param logger
	 */
	void setLogger(Logger logger);

	/**
	 * 
	 * @return
	 */
	Logger getLogger();
	
	/**
	 * 
	 * @param usePrintCtx
	 */
	void setUsePrintCtx(boolean usePrintCtx);

	/**
	 * 
	 * @return
	 */
	boolean getUsePrintCtx();
	
	/**
	 * 
	 * @param usePrintableTool
	 */
	void setUsePrintService(boolean usePrintableTool);

	/**
	 * 
	 * @return
	 */
	boolean getUsePrintService();
	
	/**
	 * 
	 * @param level
	 */
	void setErrorLevel(Level level);

	/**
	 * 
	 * @return
	 */
	Level getErrorLevel();
	
	/**
	 * 
	 * @param level
	 */
	void setWarnLevel(Level level);

	/**
	 * 
	 * @return
	 */
	Level getWarnLevel();

	/**
	 * 
	 * @param level
	 */
	void setInfoLevel(Level level);

	/**
	 * 
	 * @return
	 */
	Level getInfoLevel();

	/**
	 * 
	 * @param level
	 */
	void setDebugLevel(Level level);

	/**
	 * 
	 * @return
	 */
	Level getDebugLevel();

	/**
	 * 
	 * @param printLevel
	 * @return
	 */
	boolean isLevel(PrintLevel printLevel);
	
	/**
	 * 
	 * @return
	 */
	boolean isErrorLevel();
	
	/**
	 * 
	 * @return
	 */
	boolean isWarnLevel();
	
	/**
	 * 
	 * @return
	 */
	boolean isInfoLevel();

	/**
	 * 
	 * @return
	 */
	boolean isDebugLevel();
	
	/**
	 * 
	 * @return
	 */
	boolean isTraceLevel();
	
}
