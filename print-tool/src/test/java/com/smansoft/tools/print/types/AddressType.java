/**
 * 
 */
package com.smansoft.tools.print.types;


/**
 * @author SMan
 *
 */
public enum AddressType {
	
	/**
	 * 
	 */
	IpV4(1), IpV6(2), DomainName(3), EmailAddress(4), NotClassified(5);

	/**
	 * 
	 */
	private int addressType;

	/**
	 * 
	 * @param addressType
	 */
	AddressType(int addressType) {
		this.addressType = addressType;
	}

	/**
	 * 
	 * @return
	 */
	public int getAddressTypeValue() {
		return this.addressType;
	}

	/**
	 * 
	 * @param addressType
	 * @return
	 * @throws InitException
	 */
	public static AddressType valueOf(int addressType) throws Exception {
		AddressType resAddressType = null; 
		switch(addressType) {
			case 1:
				resAddressType = IpV4;
				break;
			case 2:
				resAddressType = IpV6;
				break;
			case 3:
				resAddressType = DomainName;
				break;
			case 4:
				resAddressType = EmailAddress;
				break;
			case 5:
				resAddressType = NotClassified;
				break;
			default:
				throw new Exception(String.format("Wrong addressType value : %d", addressType));
		}
		return resAddressType;
	}
	
}
