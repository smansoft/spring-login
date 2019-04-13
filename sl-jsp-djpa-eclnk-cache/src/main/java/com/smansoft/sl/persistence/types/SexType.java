/**
 * 
 */
package com.smansoft.sl.persistence.types;

import com.smansoft.sl.exceptions.InitException;

/**
 * @author SMan
 *
 */
public enum SexType {

	NotDefined(0), Male(1), Female(2);

	/**
	 * 
	 */
	private int sexTypeValue;

	/**
	 * 
	 * @param authorityType
	 */
	SexType(int sexTypeValue) {
		this.sexTypeValue = sexTypeValue;
	}

	/**
	 * 
	 * @return
	 */
	public int getsexTypeValue() {
		return this.sexTypeValue;
	}

	/**
	 * 
	 * @param sexTypeValue
	 * @return
	 * @throws InitException
	 */
	public static SexType valueOf(int sexTypeValue) throws InitException {
		SexType sexType = null;
		switch (sexTypeValue) {
		case 0:
			sexType = NotDefined;
			break;
		case 1:
			sexType = Male;
			break;
		case 2:
			sexType = Female;
			break;
		default:
			throw new InitException(String.format("Wrong sexType value : %d", sexTypeValue));
		}
		return sexType;
	}

}
