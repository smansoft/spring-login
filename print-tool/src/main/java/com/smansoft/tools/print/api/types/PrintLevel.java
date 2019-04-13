/**
 * 
 */
package com.smansoft.tools.print.api.types;

import com.smansoft.tools.print.api.exceptions.PrintToolException;

/**
 * @author SMan
 *
 */
public enum PrintLevel {

	/**
	 * 
	 */
	PRN_ERROR(0), PRN_WARN(1), PRN_INFO(2), PRN_DEBUG(3), PRN_TRACE(4);

	/**
	 * 
	 */
	private int printLevel;

	/**
	 * 
	 * @param ipType
	 */
	PrintLevel(int printLevel) {
		this.printLevel = printLevel;
	}

	/**
	 * 
	 * @return
	 */
	int getPrintLevelValue() {
		return this.printLevel;
	}

	/**
	 * 
	 * @param ipType
	 * @return
	 * @throws PrintToolException
	 */
	public static PrintLevel valueOf(int printLevelType) throws PrintToolException {
		PrintLevel resPrintLevelType = null;
		switch (printLevelType) {
		case 0:
			resPrintLevelType = PRN_ERROR;
			break;
		case 1:
			resPrintLevelType = PRN_WARN;
			break;
		case 2:
			resPrintLevelType = PRN_INFO;
			break;
		case 3:
			resPrintLevelType = PRN_DEBUG;
			break;
		case 4:
			resPrintLevelType = PRN_TRACE;
			break;
		default:
			throw new PrintToolException(String.format("Wrong printLevelType value : %d", printLevelType));
		}
		return resPrintLevelType;
	}
}
