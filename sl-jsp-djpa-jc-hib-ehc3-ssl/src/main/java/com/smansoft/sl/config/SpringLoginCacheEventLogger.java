/**
 * 
 */
package com.smansoft.sl.config;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

import org.slf4j.LoggerFactory;

import com.smansoft.tools.print.api.IPrintTool;
import com.smansoft.tools.print.api.types.PrintSfx;
import com.smansoft.tools.print.impl.PrintTool;


/**
 * @author SMan
 *
 */
public class SpringLoginCacheEventLogger implements CacheEventListener<Object, Object> {

	/**
	 * 
	 */
	private static final IPrintTool printTool = PrintTool.getPrintToolInstance(LoggerFactory.getLogger(SpringLoginCacheEventLogger.class));

	/**
	 * 
	 */
	@Override
	public void onEvent(CacheEvent<? extends Object, ? extends Object> event) {
		printTool.debug(PrintSfx.SFX_IN);
		printTool.debug("event.getType()		= " + event.getType());
		printTool.debug("event.getKey()			= " + event.getKey());
		printTool.debug("event.getOldValue()	= " + event.getOldValue());
		printTool.debug("event.getNewValue()	= " + event.getNewValue());
		printTool.debug(PrintSfx.SFX_OUT);
	}

}
