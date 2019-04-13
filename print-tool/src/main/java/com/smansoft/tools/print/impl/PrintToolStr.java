/**
 * 
 */
package com.smansoft.tools.print.impl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.event.Level;

import com.smansoft.tools.common.ExceptionTools;
import com.smansoft.tools.print.api.IPrintToolStr;
import com.smansoft.tools.print.api.IPrintable;
import com.smansoft.tools.print.api.types.PrintCtx;
import com.smansoft.tools.print.api.types.PrintLevel;
import com.smansoft.tools.print.api.types.PrintSfx;

/**
 * @author SMan
 *
 */
public class PrintToolStr extends PrintToolBase implements IPrintToolStr {

	/**
	 * 
	 */
	protected static String configFilePath = "application.properties";

	/**
	 * 
	 */
	public PrintToolStr() {
		this.isFileProperties = false;

		this.errorLevel = Level.ERROR;
		this.warnLevel = Level.WARN;
		this.infoLevel = Level.INFO;
		this.debugLevel = Level.DEBUG;
		this.traceLevel = Level.TRACE;

		this.usePrintCtx = false;
		this.usePrintService = false;

		this.properties = new Properties();
		readProperties();
	}

	/**
	 * 
	 */
	@Override
	public void readProperties() {
		InputStream is = null;
		is = PrintTool.class.getClassLoader().getResourceAsStream(PrintTool.configFilePath);
		if (is == null) {
			System.err.println(String.format("PrintTool hasn't been configured. File: %s not found.", configFilePath));
			return;
		}
		try {
			this.properties.load(is);

			String usePrintCtxProp = properties.getProperty(DEF_USE_PRINT_CTX_PROP);
			String usePrintServiceProp = properties.getProperty(DEF_USE_PRINT_SERVICE_PROP);
			String errorLevelProp = properties.getProperty(DEF_USE_PRINT_ERROR_LEVEL_PROP);
			String warnLevelProp = properties.getProperty(DEF_USE_PRINT_WARN_LEVEL_PROP);
			String infoLevelProp = properties.getProperty(DEF_USE_PRINT_INFO_LEVEL_PROP);
			String debugLevelProp = properties.getProperty(DEF_USE_PRINT_DEBUG_LEVEL_PROP);
			String traceLevelProp = properties.getProperty(DEF_USE_PRINT_TRACE_LEVEL_PROP);

			this.usePrintCtx = false;
			if (usePrintCtxProp != null && usePrintCtxProp.length() > 0) {
				this.usePrintCtx = Boolean.parseBoolean(usePrintCtxProp);
			}

			this.usePrintService = false;
			if (usePrintServiceProp != null && usePrintServiceProp.length() > 0) {
				this.usePrintService = Boolean.parseBoolean(usePrintServiceProp);
			}

			this.errorLevel = Level.ERROR;
			if (errorLevelProp != null && errorLevelProp.length() > 0) {
				this.errorLevel = Level.valueOf(errorLevelProp);
			}

			this.warnLevel = Level.WARN;
			if (warnLevelProp != null && warnLevelProp.length() > 0) {
				this.warnLevel = Level.valueOf(warnLevelProp);
			}

			this.infoLevel = Level.INFO;
			if (infoLevelProp != null && infoLevelProp.length() > 0) {
				this.infoLevel = Level.valueOf(infoLevelProp);
			}

			this.debugLevel = Level.DEBUG;
			if (debugLevelProp != null && debugLevelProp.length() > 0) {
				this.debugLevel = Level.valueOf(debugLevelProp);
			}

			this.traceLevel = Level.TRACE;
			if (traceLevelProp != null && traceLevelProp.length() > 0) {
				this.traceLevel = Level.valueOf(traceLevelProp);
			}

			this.isFileProperties = true;
		} catch (IOException e1) {
			System.err.println(ExceptionTools.stackTraceToString(e1));
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e2) {
					System.err.println(ExceptionTools.stackTraceToString(e2));
				}
			}
		}
	}

	/**
	 * 
	 * @param logger
	 * @return
	 */
	public static PrintToolStr getPrintToolInstance(Logger logger) {
		PrintToolStr resPrintToolImpl = new PrintToolStr();
		resPrintToolImpl.setLogger(logger);
		return resPrintToolImpl;
	}

	/**
	 * 
	 * @param configFilePath
	 * @param logger
	 * @return
	 */
	public static PrintToolStr getPrintToolInstance(String configFilePath, Logger logger) {
		PrintToolStr.setConfigFilePath(configFilePath);
		PrintToolStr resPrintToolImpl = new PrintToolStr();
		resPrintToolImpl.setLogger(logger);
		return resPrintToolImpl;
	}

	/**
	 * 
	 * @param configFilePath
	 */
	public static void setConfigFilePath(String configFilePath) {
		PrintTool.configFilePath = configFilePath;
	}

	/**
	 * 
	 * @return
	 */
	public static String getConfigFilePath() {
		return PrintTool.configFilePath;
	}

	/**
	 * 
	 */
	@Override
	public void log(PrintLevel printLevel, String startMessage, Object object) {
		if (!this.isLevel(printLevel)) {
			return;
		}
		if (object != null) {
			Class<?> objClass = object.getClass();
			if (isImplementsInterface(objClass, IPrintable.class)) {
				IPrintable printableObject = (IPrintable) object;
				if (this.usePrintService) {
					if (printableObject.usePrintService()) {
						log(printLevel, startMessage, (IPrintable) object);
					} else {
						printableObject.log(printLevel, startMessage, this);
					}
				} else {
					printableObject.log(printLevel, startMessage, this);
				}
			} else if (isImplementsInterface(objClass, Iterable.class)) {
				Iterable<?> iterableObject = (Iterable<?>) object;
				for (Object iterableElem : iterableObject) {
					log(printLevel, startMessage, iterableElem);
				}
			} else if (objClass.isArray()) {
				Object arrayObject[] = (Object[]) object;
				for (Object arrayElem : arrayObject) {
					log(printLevel, startMessage, arrayElem);
				}
			} else {
				if (startMessage == null) {
					startMessage = this.logger.getName();
				}
				String resMessage = String.format("%s : %s", startMessage, object.toString());
				switch (printLevel) {
				case PRN_ERROR:
					this.logger.error(resMessage);
					break;
				case PRN_WARN:
					this.logger.warn(resMessage);
					break;
				case PRN_INFO:
					this.logger.info(resMessage);
					break;
				case PRN_DEBUG:
					this.logger.debug(resMessage);
					break;
				case PRN_TRACE:
					this.logger.trace(resMessage);
					break;
				}
			}
		} else {
			if (startMessage == null) {
				startMessage = this.logger.getName();
			}
			String resMessage = String.format("%s : [null]", startMessage);
			switch (printLevel) {
			case PRN_ERROR:
				this.logger.error(resMessage);
				break;
			case PRN_WARN:
				this.logger.warn(resMessage);
				break;
			case PRN_INFO:
				this.logger.info(resMessage);
				break;
			case PRN_DEBUG:
				this.logger.debug(resMessage);
				break;
			case PRN_TRACE:
				this.logger.trace(resMessage);
				break;
			}
		}
	}

	/**
	 * 
	 */
	@Override
	public void log(PrintLevel printLevel, String startMessage, Object message, Throwable ex) {
		if (!this.isLevel(printLevel)) {
			return;
		}
		if (startMessage == null) {
			startMessage = this.logger.getName();
		}
		String resMessage = String.format("%s : %s", startMessage, message);
		switch (printLevel) {
		case PRN_ERROR:
			this.logger.error(resMessage, ex);
			break;
		case PRN_WARN:
			this.logger.warn(resMessage, ex);
			break;
		case PRN_INFO:
			this.logger.info(resMessage, ex);
			break;
		case PRN_DEBUG:
			this.logger.debug(resMessage, ex);
			break;
		case PRN_TRACE:
			this.logger.trace(resMessage, ex);
			break;
		}
	}

	/**
	 * 
	 */
	@Override
	public void log(PrintLevel printLevel, String startMessage, IPrintable printable) {
		if (!this.isLevel(printLevel)) {
			return;
		}
		if (this.usePrintService) {
			if (printable.usePrintService()) {
				if (usePrintCtx) {
					PrintCtx printCtx = new PrintCtx();
					this.log(printLevel, startMessage,
							String.format("%s%s %s %s", printCtx.getCurrentTabs(), printable.getClass().getSimpleName(),
									printCtx.getCurrentDashes(), PrintSfx.SFX_IN.getPrintSfxValue()));
					printCtx.incCtxIncludeLevel();
					Class<?> currClass = printable.getClass();
					while (currClass != null) {
						for (Field field : currClass.getDeclaredFields()) {
							field.setAccessible(true);
							try {
								Object value = null;
								value = field.get(printable);
								if (value != null) {
									if (isImplementsInterface(field.getType(), IPrintable.class)) {
										this.log(printLevel, startMessage,
												String.format("%s%s\t= ", printCtx.getCurrentTabs(), field.getName()));
										this.log(printLevel, startMessage,
												String.format("%s%s %s", printCtx.getCurrentTabs(),
														PrintCtx.DEF_SHORT_DASHES_LINE,
														PrintSfx.SFX_IN.getPrintSfxValue()));
										IPrintable printableObject = (IPrintable) value;
										if (this.usePrintService) {
											if (printableObject.usePrintService()) {
												this.log(printLevel, printCtx, startMessage, printableObject);
											} else {
												printableObject.log(printLevel, printCtx, startMessage, this);
											}
										} else {
											printableObject.log(printLevel, printCtx, startMessage, this);
										}
										this.log(printLevel, startMessage,
												String.format("%s%s %s", printCtx.getCurrentTabs(),
														PrintCtx.DEF_SHORT_DASHES_LINE,
														PrintSfx.SFX_OUT.getPrintSfxValue()));
									} else if (isImplementsInterface(field.getType(), Iterable.class)
											|| field.getType().isArray()) {
										this.log(printLevel, startMessage,
												String.format("%s%s\t= ", printCtx.getCurrentTabs(), field.getName()));
										this.log(printLevel, startMessage,
												String.format("%s%s %s", printCtx.getCurrentTabs(),
														PrintCtx.DEF_SHORT_DASHES_LINE,
														PrintSfx.SFX_IN.getPrintSfxValue()));
										this.log(printLevel, printCtx, startMessage, value);
										this.log(printLevel, startMessage,
												String.format("%s%s %s", printCtx.getCurrentTabs(),
														PrintCtx.DEF_SHORT_DASHES_LINE,
														PrintSfx.SFX_OUT.getPrintSfxValue()));
									} else {
										this.log(printLevel, startMessage, String.format("%s%s\t= %s",
												printCtx.getCurrentTabs(), field.getName(), value));
									}
								} else {
									this.log(printLevel, startMessage, String.format("%s%s\t= %s",
											printCtx.getCurrentTabs(), field.getName(), null));
								}
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
						}
						currClass = currClass.getSuperclass();
					}
					printCtx.decCtxIncludeLevel();
					this.log(printLevel, startMessage,
							String.format("%s%s %s %s", printCtx.getCurrentTabs(), printable.getClass().getSimpleName(),
									printCtx.getCurrentDashes(), PrintSfx.SFX_OUT.getPrintSfxValue()));
				} else {
					this.log(printLevel, startMessage,
							String.format("%s%s %s %s", PrintCtx.DEF_SHORT_TABS_LINE,
									printable.getClass().getSimpleName(), PrintCtx.DEF_DASHES_LINE,
									PrintSfx.SFX_IN.getPrintSfxValue()));
					Class<?> currClass = printable.getClass();
					while (currClass != null) {
						for (Field field : currClass.getDeclaredFields()) {
							field.setAccessible(true);
							try {
								Object value = null;
								value = field.get(printable);
								if (value != null) {
									if (isImplementsInterface(field.getType(), IPrintable.class)) {
										this.log(printLevel, startMessage, String.format("%s\t= ", field.getName()));
										this.log(printLevel, startMessage, String.format("%s %s",
												PrintCtx.DEF_SHORT_DASHES_LINE, PrintSfx.SFX_IN.getPrintSfxValue()));
										IPrintable printableObject = (IPrintable) value;
										if (this.usePrintService) {
											if (printableObject.usePrintService()) {
												this.log(printLevel, startMessage, printableObject);
											} else {
												printableObject.log(printLevel, startMessage, this);
											}
										} else {
											printableObject.log(printLevel, startMessage, this);
										}
										this.log(printLevel, startMessage, String.format("%s %s",
												PrintCtx.DEF_SHORT_DASHES_LINE, PrintSfx.SFX_OUT.getPrintSfxValue()));
									} else if (isImplementsInterface(field.getType(), Iterable.class)
											|| field.getType().isArray()) {
										this.log(printLevel, startMessage, String.format("%s\t= ", field.getName()));
										this.log(printLevel, startMessage, String.format("%s %s",
												PrintCtx.DEF_SHORT_DASHES_LINE, PrintSfx.SFX_IN.getPrintSfxValue()));
										this.log(printLevel, startMessage, value);
										this.log(printLevel, startMessage, String.format("%s %s",
												PrintCtx.DEF_SHORT_DASHES_LINE, PrintSfx.SFX_OUT.getPrintSfxValue()));
									} else {
										this.log(printLevel, startMessage,
												String.format("%s\t= %s", field.getName(), value));
									}
								} else {
									this.log(printLevel, startMessage,
											String.format("%s\t= %s", field.getName(), null));
								}
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
						}
						currClass = currClass.getSuperclass();
					}
					this.log(printLevel, startMessage,
							String.format("%s%s %s %s", PrintCtx.DEF_SHORT_TABS_LINE,
									printable.getClass().getSimpleName(), PrintCtx.DEF_DASHES_LINE,
									PrintSfx.SFX_OUT.getPrintSfxValue()));
				}
			} else {
				if (usePrintCtx) {
					PrintCtx printCtx = new PrintCtx();
					printable.log(printLevel, printCtx, startMessage, this);
				} else {
					printable.log(printLevel, startMessage, this);
				}
			}
		} else {
			if (usePrintCtx) {
				PrintCtx printCtx = new PrintCtx();
				printable.log(printLevel, printCtx, startMessage, this);
			} else {
				printable.log(printLevel, startMessage, this);
			}
		}
	}

	/**
	 * 
	 */
	@Override
	public void log(PrintLevel printLevel, String startMessage, PrintSfx printSfx) {
		if (!this.isLevel(printLevel)) {
			return;
		}
		this.log(printLevel, startMessage,
				String.format("%s %s(): %s %s", PrintCtx.DEF_SHORT_DASHES_LINE,
						Thread.currentThread().getStackTrace()[2].getMethodName(), PrintCtx.DEF_DASHES_LINE,
						printSfx.getPrintSfxValue()));
	}

	/**
	 * 
	 */
	@Override
	public void error(String startMessage, Object message) {
		if (!this.isLevel(PrintLevel.PRN_ERROR)) {
			return;
		}
		this.log(PrintLevel.PRN_ERROR, startMessage, message);
	}

	/**
	 * 
	 */
	@Override
	public void error(String startMessage, Object message, Throwable ex) {
		if (!this.isLevel(PrintLevel.PRN_ERROR)) {
			return;
		}
		this.log(PrintLevel.PRN_ERROR, startMessage, message, ex);
	}

	/**
	 * 
	 */
	@Override
	public void error(String startMessage, IPrintable printable) {
		if (!this.isLevel(PrintLevel.PRN_ERROR)) {
			return;
		}
		this.log(PrintLevel.PRN_ERROR, startMessage, printable);
	}

	/**
	 * 
	 */
	@Override
	public void error(String startMessage, PrintSfx printSfx) {
		if (!this.isLevel(PrintLevel.PRN_ERROR)) {
			return;
		}
		this.log(PrintLevel.PRN_ERROR, startMessage,
				String.format("%s %s(): %s %s", PrintCtx.DEF_SHORT_DASHES_LINE,
						Thread.currentThread().getStackTrace()[2].getMethodName(), PrintCtx.DEF_DASHES_LINE,
						printSfx.getPrintSfxValue()));
	}

	/**
	 * 
	 */
	@Override
	public void warn(String startMessage, Object message) {
		if (!this.isLevel(PrintLevel.PRN_WARN)) {
			return;
		}
		this.log(PrintLevel.PRN_WARN, startMessage, message);
	}

	/**
	 * 
	 */
	@Override
	public void warn(String startMessage, Object message, Throwable ex) {
		if (!this.isLevel(PrintLevel.PRN_WARN)) {
			return;
		}
		this.log(PrintLevel.PRN_WARN, startMessage, message, ex);
	}

	/**
	 * 
	 */
	@Override
	public void warn(String startMessage, IPrintable printable) {
		if (!this.isLevel(PrintLevel.PRN_WARN)) {
			return;
		}
		this.log(PrintLevel.PRN_WARN, startMessage, printable);
	}

	/**
	 * 
	 */
	@Override
	public void warn(String startMessage, PrintSfx printSfx) {
		if (!this.isLevel(PrintLevel.PRN_WARN)) {
			return;
		}
		this.log(PrintLevel.PRN_WARN, startMessage,
				String.format("%s %s(): %s %s", PrintCtx.DEF_SHORT_DASHES_LINE,
						Thread.currentThread().getStackTrace()[2].getMethodName(), PrintCtx.DEF_DASHES_LINE,
						printSfx.getPrintSfxValue()));
	}

	/**
	 * 
	 */
	@Override
	public void info(String startMessage, Object message) {
		if (!this.isLevel(PrintLevel.PRN_INFO)) {
			return;
		}
		this.log(PrintLevel.PRN_INFO, startMessage, message);
	}

	/**
	 * 
	 */
	@Override
	public void info(String startMessage, Object message, Throwable ex) {
		if (!this.isLevel(PrintLevel.PRN_INFO)) {
			return;
		}
		this.log(PrintLevel.PRN_INFO, startMessage, message, ex);
	}

	/**
	 * 
	 */
	@Override
	public void info(String startMessage, IPrintable printable) {
		if (!this.isLevel(PrintLevel.PRN_INFO)) {
			return;
		}
		this.log(PrintLevel.PRN_INFO, startMessage, printable);
	}

	/**
	 * 
	 */
	@Override
	public void info(String startMessage, PrintSfx printSfx) {
		if (!this.isLevel(PrintLevel.PRN_INFO)) {
			return;
		}
		this.log(PrintLevel.PRN_INFO, startMessage,
				String.format("%s %s(): %s %s", PrintCtx.DEF_SHORT_DASHES_LINE,
						Thread.currentThread().getStackTrace()[2].getMethodName(), PrintCtx.DEF_DASHES_LINE,
						printSfx.getPrintSfxValue()));
	}

	/**
	 * 
	 */
	@Override
	public void debug(String startMessage, Object message) {
		if (!this.isLevel(PrintLevel.PRN_DEBUG)) {
			return;
		}
		this.log(PrintLevel.PRN_DEBUG, startMessage, message);
	}

	/**
	 * 
	 */
	@Override
	public void debug(String startMessage, Object message, Throwable ex) {
		if (!this.isLevel(PrintLevel.PRN_DEBUG)) {
			return;
		}
		this.log(PrintLevel.PRN_DEBUG, startMessage, message, ex);
	}

	/**
	 * 
	 */
	@Override
	public void debug(String startMessage, IPrintable printable) {
		if (!this.isLevel(PrintLevel.PRN_DEBUG)) {
			return;
		}
		this.log(PrintLevel.PRN_DEBUG, startMessage, printable);
	}

	/**
	 * 
	 */
	@Override
	public void debug(String startMessage, PrintSfx printSfx) {
		if (!this.isLevel(PrintLevel.PRN_DEBUG)) {
			return;
		}
		this.log(PrintLevel.PRN_DEBUG, startMessage,
				String.format("%s %s(): %s %s", PrintCtx.DEF_SHORT_DASHES_LINE,
						Thread.currentThread().getStackTrace()[2].getMethodName(), PrintCtx.DEF_DASHES_LINE,
						printSfx.getPrintSfxValue()));
	}

	/**
	 * 
	 */
	@Override
	public void trace(String startMessage, Object message) {
		if (!this.isLevel(PrintLevel.PRN_TRACE)) {
			return;
		}
		this.log(PrintLevel.PRN_TRACE, startMessage, message);
	}

	/**
	 * 
	 */
	@Override
	public void trace(String startMessage, Object message, Throwable ex) {
		if (!this.isLevel(PrintLevel.PRN_TRACE)) {
			return;
		}
		this.log(PrintLevel.PRN_TRACE, startMessage, message, ex);
	}

	/**
	 * 
	 */
	@Override
	public void trace(String startMessage, IPrintable printable) {
		if (!this.isLevel(PrintLevel.PRN_TRACE)) {
			return;
		}
		this.log(PrintLevel.PRN_TRACE, startMessage, printable);
	}

	/**
	 * 
	 */
	@Override
	public void trace(String startMessage, PrintSfx printSfx) {
		if (!this.isLevel(PrintLevel.PRN_TRACE)) {
			return;
		}
		this.log(PrintLevel.PRN_TRACE, startMessage,
				String.format("%s %s(): %s %s", PrintCtx.DEF_SHORT_DASHES_LINE,
						Thread.currentThread().getStackTrace()[2].getMethodName(), PrintCtx.DEF_DASHES_LINE,
						printSfx.getPrintSfxValue()));
	}

	/**
	 * 
	 * @param printLevel
	 * @param printCtx
	 * @param startMessage
	 * @param printable
	 */
	private void log(PrintLevel printLevel, PrintCtx printCtx, String startMessage, IPrintable printable) {
		if (!this.isLevel(printLevel)) {
			return;
		}
		printCtx.incCtxIncludeLevel();
		if (this.usePrintService) {
			if (printable.usePrintService()) {
				this.log(printLevel, startMessage,
						String.format("%s%s %s %s", printCtx.getCurrentTabs(), printable.getClass().getSimpleName(),
								printCtx.getCurrentDashes(), PrintSfx.SFX_IN.getPrintSfxValue()));
				printCtx.incCtxIncludeLevel();

				Class<?> currClass = printable.getClass();
				while (currClass != null) {
					for (Field field : currClass.getDeclaredFields()) {
						field.setAccessible(true);
						try {
							Object value = null;
							value = field.get(printable);
							if (value != null) {
								if (isImplementsInterface(field.getType(), IPrintable.class)) {
									this.log(printLevel, startMessage,
											String.format("%s%s\t= ", printCtx.getCurrentTabs(), field.getName()));
									this.log(printLevel, startMessage,
											String.format("%s%s %s ", printCtx.getCurrentTabs(),
													PrintCtx.DEF_SHORT_DASHES_LINE,
													PrintSfx.SFX_IN.getPrintSfxValue()));
									IPrintable printableObject = (IPrintable) value;
									if (this.usePrintService) {
										if (printableObject.usePrintService()) {
											this.log(printLevel, printCtx, startMessage, printableObject);
										} else {
											printableObject.log(printLevel, printCtx, startMessage, this);
										}
									} else {
										printableObject.log(printLevel, printCtx, startMessage, this);
									}
									this.log(printLevel, startMessage,
											String.format("%s%s %s ", printCtx.getCurrentTabs(),
													PrintCtx.DEF_SHORT_DASHES_LINE,
													PrintSfx.SFX_OUT.getPrintSfxValue()));
								} else if (isImplementsInterface(field.getType(), Iterable.class)
										|| field.getType().isArray()) {
									this.log(printLevel, startMessage,
											String.format("%s%s\t= ", printCtx.getCurrentTabs(), field.getName()));
									this.log(printLevel, startMessage,
											String.format("%s%s %s ", printCtx.getCurrentTabs(),
													PrintCtx.DEF_SHORT_DASHES_LINE,
													PrintSfx.SFX_IN.getPrintSfxValue()));
									this.log(printLevel, printCtx, startMessage, value);
									this.log(printLevel, startMessage,
											String.format("%s%s %s ", printCtx.getCurrentTabs(),
													PrintCtx.DEF_SHORT_DASHES_LINE,
													PrintSfx.SFX_OUT.getPrintSfxValue()));
								} else {
									this.log(printLevel, startMessage, String.format("%s%s\t= %s",
											printCtx.getCurrentTabs(), field.getName(), value));
								}
							} else {
								this.log(printLevel, startMessage,
										String.format("%s%s\t= %s", printCtx.getCurrentTabs(), field.getName(), null));
							}
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
					currClass = currClass.getSuperclass();
				}
				printCtx.decCtxIncludeLevel();
				this.log(printLevel, startMessage,
						String.format("%s%s %s %s", printCtx.getCurrentTabs(), printable.getClass().getSimpleName(),
								printCtx.getCurrentDashes(), PrintSfx.SFX_OUT.getPrintSfxValue()));
			} else {
				printable.log(printLevel, printCtx, startMessage, this);
			}
		} else {
			printable.log(printLevel, printCtx, startMessage, this);
		}
		printCtx.decCtxIncludeLevel();
	}

	/**
	 * 
	 * @param printLevel
	 * @param printCtx
	 * @param startMessage
	 * @param object
	 */
	private void log(PrintLevel printLevel, PrintCtx printCtx, String startMessage, Object object) {
		if (!this.isLevel(printLevel)) {
			return;
		}
		printCtx.incCtxIncludeLevel();
		if (object != null) {
			Class<?> objClass = object.getClass();
			if (isImplementsInterface(objClass, IPrintable.class)) {
				IPrintable printableObject = (IPrintable) object;
				if (this.usePrintService) {
					if (printableObject.usePrintService()) {
						log(printLevel, printCtx, startMessage, printableObject);
					} else {
						printableObject.log(printLevel, printCtx, startMessage, this);
					}
				} else {
					printableObject.log(printLevel, printCtx, startMessage, this);
				}
			} else if (isImplementsInterface(objClass, Iterable.class)) {
				Iterable<?> iterableObject = (Iterable<?>) object;
				for (Object iterableElem : iterableObject) {
					log(printLevel, printCtx, startMessage, iterableElem);
				}
			} else if (objClass.isArray()) {
				Object arrayObject[] = (Object[]) object;
				for (Object arrayElem : arrayObject) {
					log(printLevel, printCtx, startMessage, arrayElem);
				}
			} else {
				if (startMessage == null) {
					startMessage = this.logger.getName();
				}
				String resMessage = String.format("%s : %s%s", startMessage, printCtx.getCurrentTabs(), object);
				switch (printLevel) {
				case PRN_ERROR:
					this.logger.error(resMessage);
					break;
				case PRN_WARN:
					this.logger.warn(resMessage);
					break;
				case PRN_INFO:
					this.logger.info(resMessage);
					break;
				case PRN_DEBUG:
					this.logger.debug(resMessage);
					break;
				case PRN_TRACE:
					this.logger.trace(resMessage);
					break;
				}
			}
		} else {
			if (startMessage == null) {
				startMessage = this.logger.getName();
			}
			String resMessage = String.format("%s : %s[null]", startMessage, printCtx.getCurrentTabs());
			switch (printLevel) {
			case PRN_ERROR:
				this.logger.error(resMessage);
				break;
			case PRN_WARN:
				this.logger.warn(resMessage);
				break;
			case PRN_INFO:
				this.logger.info(resMessage);
				break;
			case PRN_DEBUG:
				this.logger.debug(resMessage);
				break;
			case PRN_TRACE:
				this.logger.trace(resMessage);
				break;
			}
		}
		printCtx.decCtxIncludeLevel();
	}

}
