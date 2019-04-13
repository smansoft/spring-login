/**
 * 
 */
package com.smansoft.tools.print.api.types;

import java.io.Serializable;

/**
 * @author SMan
 *
 */
public class PrintCtx implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3852380353913069918L;

	/**
	 * 
	 */
	public static final String DEF_TABS_LINE = "\t\t\t\t\t\t\t\t\t\t";

	/**
	 * 
	 */
	public static final String DEF_SHORT_TABS_LINE = "";

	/**
	 * 
	 */
	public static final String DEF_DASHES_LINE = "--------------------------------------------------";

	/**
	 * 
	 */
	public static final String DEF_SHORT_DASHES_LINE = "----------";

	/**
	 * 
	 */
	private int ctxIncludeLevel;

	/**
	 * 
	 */
	public PrintCtx() {
		setCtxIncludeLevel(0);
	}

	/**
	 * 
	 */
	public PrintCtx(int ctxIncludeLevel) {
		setCtxIncludeLevel(ctxIncludeLevel);
	}

	/**
	 * 
	 * @param ctxIncludeLevel
	 */
	public void setCtxIncludeLevel(int ctxIncludeLevel) {
		this.ctxIncludeLevel = ctxIncludeLevel;
	}

	/**
	 * 
	 * @return
	 */
	public int incCtxIncludeLevel() {
		ctxIncludeLevel++;
		return ctxIncludeLevel;
	}

	/**
	 * 
	 * @return
	 */
	public int decCtxIncludeLevel() {
		ctxIncludeLevel--;
		if (ctxIncludeLevel < 0) {
			ctxIncludeLevel = 0;
		}
		return ctxIncludeLevel;
	}

	/**
	 * 
	 * @return
	 */
	public int getCtxIncludeLevel() {
		return this.ctxIncludeLevel;
	}

	/**
	 * 
	 * @return
	 */
	public String getCurrentTabs() {
		if (ctxIncludeLevel > 0 && ctxIncludeLevel < 9) {
			return DEF_TABS_LINE.substring(0, ctxIncludeLevel);
		} else if (ctxIncludeLevel >= 9) {
			return DEF_TABS_LINE;
		} else {
			return "";
		}
	}

	/**
	 * 
	 * @return
	 */
	public String getCurrentDashes() {
		int subStrIdx;
		if (ctxIncludeLevel > 0 && ctxIncludeLevel < 9) {
			subStrIdx = (9 - ctxIncludeLevel) * 5 + 5;
			return DEF_DASHES_LINE.substring(0, subStrIdx);
		} else if (ctxIncludeLevel >= 9) {
			subStrIdx = 5;
			return DEF_DASHES_LINE.substring(0, subStrIdx);
		} else {
			return DEF_DASHES_LINE;
		}
	}

}
