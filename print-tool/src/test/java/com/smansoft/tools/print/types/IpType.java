/**
 * 
 */
package com.smansoft.tools.print.types;


/**
 * @author SMan
 *
 */
public enum IpType {

	/**
	 * 
	 */
	IpV4(0), IpV6(1);

	/**
	 * 
	 */
	private int ipType;

	/**
	 * 
	 * @param ipType
	 */
	IpType(int ipType) {
		this.ipType = ipType;
	}

	/**
	 * 
	 * @return
	 */
	public int getIpTypeValue() {
		
		return this.ipType;
	}

	/**
	 * 
	 * @param ipType
	 * @return
	 * @throws InitException
	 */
	public static IpType valueOf(int ipType) throws Exception {
		IpType resIpType = null; 
		switch(ipType) {
			case 0:
				resIpType = IpV4;
				break;
			case 1:
				resIpType = IpV6;
				break;
			default:
				throw new Exception(String.format("Wrong ipType value : %d", ipType));
		}
		return resIpType;
	}
}
