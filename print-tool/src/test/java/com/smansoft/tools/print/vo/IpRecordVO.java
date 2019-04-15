/**
 * 
 */
package com.smansoft.tools.print.vo;

import com.smansoft.tools.print.types.IpType;

/**
 * @author SMan
 *
 */
public class IpRecordVO extends BaseVO {

	/**
	 * 
	 */
	protected String ipAddress;
	
	/**
	 * 
	 */
	protected String ipAddressSrv;
	
	/**
	 * 
	 */
	protected IpType ipType;
	
	/**
	 * 
	 * @return
	 */
	public String getIpAddress() {
		return this.ipAddress;
	}

	/**
	 * 
	 * @param ipAddress
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getIpAddressSrv() {
		return this.ipAddressSrv;
	}

	/**
	 * 
	 * @param ipAddressSrv
	 */
	public void setIpAddressSrv(String ipAddressSrv) {
		
		this.ipAddressSrv = ipAddressSrv;
	}
	
	/**
	 * 
	 * @return
	 */
	public IpType getIpType() {
		return this.ipType;
	}

	/**
	 * 
	 * @param ipType
	 */
	public void setIpType(IpType ipType) {
		this.ipType = ipType;
	}
	
	/**
	 * 
	 */
	public boolean equals(Object object) {
		if(this == object) {
			return true;
		}
		if(object == null) {
			return false;
		}
		if(getClass() != object.getClass()) {
			return false;			
		}
		IpRecordVO ipRecord = (IpRecordVO)object;
		if( this.ipType == ipRecord.getIpType() &&
				
				(this.ipAddress == null && ipRecord.getIpAddress() == null) ||
				(this.ipAddress != null && this.ipAddress.equalsIgnoreCase(ipRecord.getIpAddress()) ) &&
				
				(this.ipAddressSrv == null && ipRecord.getIpAddressSrv() == null) ||
				(this.ipAddressSrv != null && this.ipAddressSrv.equalsIgnoreCase(ipRecord.getIpAddressSrv()) ) ) {
			return true;
		} else {
			return false;
		}
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
