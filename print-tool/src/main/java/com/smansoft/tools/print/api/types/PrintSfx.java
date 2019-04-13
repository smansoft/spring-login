/**
 * 
 */
package com.smansoft.tools.print.api.types;

/**
 * @author SMan
 *
 */
public enum PrintSfx {

	/**
	 * 
	 */
	SFX_EMPTY(""), SFX_IN(">>"), SFX_OUT("<<");

	/**
	 * 
	 */
	private String printSfx;

	/**
	 * 
	 * @param printSfx
	 */
	PrintSfx(String printSfx) {
		this.printSfx = printSfx;
	}

	/**
	 * 
	 * @return
	 */
	public String getPrintSfxValue() {
		return this.printSfx;
	}

}
