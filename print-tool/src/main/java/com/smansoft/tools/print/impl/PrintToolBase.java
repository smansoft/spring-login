/**
 * 
 */
package com.smansoft.tools.print.impl;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.event.Level;

import com.smansoft.tools.print.api.IPrintTool;
import com.smansoft.tools.print.api.IPrintToolBase;
import com.smansoft.tools.print.api.IPrintToolStr;
import com.smansoft.tools.print.api.types.PrintCtx;
import com.smansoft.tools.print.api.types.PrintLevel;

/**
 * @author SMan
 *
 */
public abstract class PrintToolBase implements IPrintToolBase {

	/**
	 * 
	 */
	protected boolean isFileProperties;

	/**
	 * 
	 */
	protected Logger logger = null;

	/**
	 * 
	 */
	protected Properties properties = null;

	/**
	 * 
	 */
	protected boolean usePrintCtx;

	/**
	 * 
	 */
	protected boolean usePrintService;

	/**
	 * 
	 */
	protected Level errorLevel;

	/**
	 * 
	 */
	protected Level warnLevel;

	/**
	 * 
	 */
	protected Level infoLevel;

	/**
	 * 
	 */
	protected Level debugLevel;

	/**
	 * 
	 */
	protected Level traceLevel;

	/**
	 * 
	 * @param logger
	 */
	@Override
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public Logger getLogger() {
		return this.logger;
	}

	/**
	 * 
	 * @param usePrintCtx
	 */
	@Override
	public void setUsePrintCtx(boolean usePrintCtx) {
		this.usePrintCtx = usePrintCtx;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public boolean getUsePrintCtx() {
		return this.usePrintCtx;
	}

	/**
	 * 
	 * @param usePrintableTool
	 */
	public void setUsePrintService(boolean usePrintableTool) {
		this.usePrintService = usePrintableTool;
	}

	/**
	 * 
	 * @return
	 */
	public boolean getUsePrintService() {
		return this.usePrintService;
	}

	/**
	 * 
	 * @param level
	 */
	@Override
	public void setErrorLevel(Level level) {
		this.errorLevel = level;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public Level getErrorLevel() {
		return this.errorLevel;
	}

	/**
	 * 
	 * @param level
	 */
	@Override
	public void setWarnLevel(Level level) {
		this.warnLevel = level;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public Level getWarnLevel() {
		return this.warnLevel;
	}

	/**
	 * 
	 * @param level
	 */
	@Override
	public void setInfoLevel(Level level) {
		this.infoLevel = level;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public Level getInfoLevel() {
		return this.infoLevel;
	}

	/**
	 * 
	 * @param level
	 */
	@Override
	public void setDebugLevel(Level level) {
		this.debugLevel = level;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public Level getDebugLevel() {
		return this.debugLevel;
	}

	/**
	 * 
	 */
	@Override
	public boolean isLevel(PrintLevel printLevel) {
		if (!this.isFileProperties) {
			readProperties();
		}
		switch (printLevel) {
		case PRN_ERROR:
			return isErrorLevel();
		case PRN_WARN:
			return isWarnLevel();
		case PRN_INFO:
			return isInfoLevel();
		case PRN_DEBUG:
			return isDebugLevel();
		case PRN_TRACE:
			return isDebugLevel();
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public boolean isErrorLevel() {
		return this.logger.isErrorEnabled();
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public boolean isWarnLevel() {
		return this.logger.isWarnEnabled();
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public boolean isInfoLevel() {
		return this.logger.isInfoEnabled();
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public boolean isDebugLevel() {
		return this.logger.isDebugEnabled();
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public boolean isTraceLevel() {
		return this.logger.isTraceEnabled();
	}

	/**
	 * 
	 */
	@Override
	public void log(PrintLevel printLevel, IPrintTool printTool) {
	}

	/**
	 * 
	 */
	@Override
	public void log(PrintLevel printLevel, PrintCtx printCtx, IPrintTool printTool) {
	}

	/**
	 * 
	 */
	@Override
	public void log(PrintLevel printLevel, String startMessage, IPrintToolStr printToolStr) {
	}

	/**
	 * 
	 */
	@Override
	public void log(PrintLevel printLevel, PrintCtx printCtx, String startMessage, IPrintToolStr printToolStr) {
	}

	@Override
	public boolean usePrintService() {
		return false;
	}

	/**
	 * 
	 * @param testClass
	 * @param implemenetedInterface
	 * @return
	 */
	protected boolean isImplementsInterface(Class<?> testClass, Class<?> implemenetedInterface) {
		boolean res = false;
		Class<?> testClassInterfaces[] = testClass.getInterfaces();
		for (Class<?> testClassInterface : testClassInterfaces) {
			if (testClassInterface == implemenetedInterface) {
				res = true;
			} else {
				res = isImplementsInterface(testClassInterface, implemenetedInterface);
			}
			if (res) {
				break;
			}
		}
		if (!res) {
			Class<?> superClass = testClass.getSuperclass();
			if (superClass != null) {
				res = isImplementsInterface(superClass, implemenetedInterface);
			}
		}
		return res;
	}

	/**
	 * 
	 * @param testClass
	 * @param extendedClass
	 * @return
	 */
	protected boolean isExtendsClass(Class<?> testClass, Class<?> extendedClass) {
		boolean res = false;
		Class<?> currClass = testClass.getSuperclass();
		while (currClass != null) {
			if (currClass == extendedClass) {
				res = true;
				break;
			}
			currClass = currClass.getSuperclass();
		}
		return res;
	}
}
