/**
 * 
 */
package com.smansoft.sl.config;

import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * @author SMan
 *
 * Path-based Bread Crumb
 *
 */
@Service(value = SpringLoginBreadCrumb.DEF_BEAN_NAME)
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SpringLoginBreadCrumb {

	public static final String DEF_BEAN_NAME = "springLoginBreadCrumbBean";

	private List<String> pathBreadCrumb = new LinkedList<>();

	private int popCount = 1;

	/**
	 * 
	 * @param path
	 */
	public void pushPath(String path) {
		pathBreadCrumb.add(path);
	}

	/**
	 * 
	 * @return
	 */
	public String popPath() {
		String popResult = null;
		int breadCrumbSize = pathBreadCrumb.size();
		if(breadCrumbSize > 0 && breadCrumbSize >= popCount) {
			for(int i = 0; i < popCount; i++) {
				popResult = popPathOne();
			}
		}
		return popResult;
	}

	/**
	 * 
	 * @return
	 */
	private String popPathOne() {
		String popResult = null;
		int breadCrumbSize = pathBreadCrumb.size();
		if(breadCrumbSize > 0) {
			popResult = pathBreadCrumb.get(breadCrumbSize-1);
			pathBreadCrumb.remove(breadCrumbSize-1);
		}
		return popResult;
	}

	/**
	 * 
	 * @param popCount
	 */
	public void setPopCount(int popCount) {
		this.popCount = popCount;
	}

	/**
	 * 
	 * @return
	 */
	public int getPopCount() {
		return this.popCount;
	}

}
