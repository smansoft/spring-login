/**
 * 
 */
package com.smansoft.sl.bl.services.vo;

/**
 * @author SMan
 *
 */
public class PageRequestVO extends BaseUserVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7951842023692179350L;

	private Integer pageNumber = 0;

	private Integer pageSize = 0;

	/**
	 * 
	 */
	public PageRequestVO() {
	}

	/**
	 * 
	 * @return
	 */
	public Integer getPageSize() {
		return this.pageSize;
	}

	/**
	 * 
	 * @param pageSize
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getPageNumber() {
		return this.pageNumber;
	}

	/**
	 * 
	 * @param pageNumber
	 */
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

}
