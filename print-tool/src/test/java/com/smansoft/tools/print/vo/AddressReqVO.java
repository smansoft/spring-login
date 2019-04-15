/**
 * 
 */
package com.smansoft.tools.print.vo;

import com.smansoft.tools.print.types.AddressType;

/**
 * @author SMan
 *
 */
public class AddressReqVO extends BaseVO {

	/**
	 * 
	 */
	private AddressVO addressVO;
	
	/**
	 * 
	 */
	private AddressType addressType;
	

	/**
	 * 
	 */
	public AddressReqVO() {
	}
	
	/**
	 * 
	 * @param addressVO
	 * @param addressType
	 */
	public AddressReqVO(AddressVO addressVO, AddressType addressType) {
		this.addressVO = addressVO;
		this.addressType = addressType;
	}
	
	/**
	 * 
	 * @param addressVO
	 */
	public void setAddressVO (AddressVO addressVO) {
		this.addressVO = addressVO;
	}

	/**
	 * 
	 * @return
	 */
	public AddressVO getAddressVO () {
		return this.addressVO;
	}

	/**
	 * 
	 * @param addressType
	 */
	public void setAddressType(AddressType addressType) {
		this.addressType = addressType; 		
	}

	/**
	 * 
	 * @return
	 */
	public AddressType getAddressType() {
		return this.addressType; 		
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
		AddressReqVO addressReqVO = (AddressReqVO) object;
		if(this.addressType == addressReqVO.getAddressType() && 
				((this.addressVO == null && addressReqVO.getAddressVO() == null) ||
					(this.addressVO.equals(addressReqVO.getAddressVO())) )) {
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
