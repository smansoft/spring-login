/**
 * 
 */
package com.smansoft.tools.print;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import com.smansoft.tools.print.api.IPrintTool;
import com.smansoft.tools.print.api.types.PrintSfx;
import com.smansoft.tools.print.impl.PrintTool;

/**
 * @author SMan
 *
 */
public class TestPrintTool {

	private static final IPrintTool printTool = PrintTool.getPrintToolInstance(LoggerFactory.getLogger(TestPrintTool.class));

	/**
	 * 
	 */
	@Test
	public void testPrintTool() {
		printTool.debug(PrintSfx.SFX_IN);
		Assert.assertTrue(true);
		printTool.debug(PrintSfx.SFX_OUT);
	}

}
