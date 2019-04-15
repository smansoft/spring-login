/**
 * 
 */
package com.smansoft.tools.print.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SMan
 *
 */
public class IpVO extends BaseVO {
	
	/**
	 * 
	 */
	private List<IpRecordVO> ipRecords = new ArrayList<IpRecordVO>(); 
	
	/**
	 * 
	 */
	public IpVO() {
	}
	
	/**
	 * 
	 * @param ipRecords
	 */
	public IpVO(List<IpRecordVO> ipRecords) {
		this.ipRecords = ipRecords;
	}

	/**
	 * 
	 * @return
	 */
	public List<IpRecordVO> getIpRecords() {
		return this.ipRecords;
	}
	
	/**
	 * 
	 * @param ipRecords
	 */
	public void setIpRecords(List<IpRecordVO> ipRecords) {
		this.ipRecords = ipRecords;
	}
	
	/**
	 * 
	 * @return
	 */
	@Override
	public boolean usePrintService() {
		return true;
	}

}
