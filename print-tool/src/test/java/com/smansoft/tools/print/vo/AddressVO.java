/**
 * 
 */
package com.smansoft.tools.print.vo;

/**
 * @author SMan
 *
 */
public class AddressVO extends BaseVO {
	
	/**
	 * 
	 */
	public String address;
	
	/**
	 * 
	 */
	public AddressVO() {
	}
	
	/**
	 * 
	 * @param address
	 */
	public AddressVO(String address) {
		this.address = address;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getAddress() {
		return this.address; 
	}

	/**
	 * 
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address; 
	}
	
	/**
	 * 
	 */
	@Override
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
		AddressVO addressVO = (AddressVO) object;
		if((this.address == null && addressVO.getAddress() == null) ||   
				(this.address.equalsIgnoreCase(addressVO.getAddress()))) {
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
