/**
 * 
 */
package com.smansoft.tools.print.types;


/**
 * @author SMan
 *
 */
public enum DnsRecordType {
	
	/**
	 * 
	 */
	ALL(0), ANY(255), A(1), AAAA(28), NS(2), MX(15), PTR(12), TXT(16), SOA(6), SRV(33), CNAME(5), HINFO(13); 
	
	/**
	 * 
	 */
	private int dnsRecordType;
	
	/**
	 * 
	 * @param dnsRecordType
	 */
	DnsRecordType(int dnsRecordType) {
		this.dnsRecordType = dnsRecordType;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getDnsRecordTypeValue() {
		return this.dnsRecordType;
	}
	
	/**
	 * 
	 * @param dnsTypeValue
	 * @return
	 */
	public static DnsRecordType getDnsRecordType(String dnsTypeValue) {
		
		String dnsTypeValueUp = dnsTypeValue.toUpperCase();
		
		switch(dnsTypeValueUp) {
		case "ALL": {
			return ALL;
		}
		case "ANY": {
			return ANY;
		}
		case "A": {
			return A;
		}
		case "AAAA": {
			return AAAA;
		}
		case "NS": {
			return NS;
		}
		case "MX": {
			return MX;
		}
		case "PTR": {
			return PTR;
		}
		case "TXT": {
			return TXT;
		}
		case "SOA": {
			return SOA;
		}
		case "SRV": {
			return SRV;
		}
		case "CNAME": {
			return CNAME;
		}
		case "HINFO": {
			return HINFO;
		}
		}
		return null;
	}	

	/**
	 * 
	 * @param dnsRecordType
	 * @return
	 * @throws Exception 
	 * @throws InitException
	 */
	public static DnsRecordType valueOf(int dnsRecordType) throws Exception {
		DnsRecordType resDnsRecordType = null; 
		switch(dnsRecordType) {
			case 0:
				resDnsRecordType = ALL;
				break;
			case 255:
				resDnsRecordType = ANY;
				break;
			case 1:
				resDnsRecordType = A;
				break;
			case 28:
				resDnsRecordType = AAAA;
				break;
			case 2:
				resDnsRecordType = NS;
				break;
			case 15:
				resDnsRecordType = MX;
				break;
			case 12:
				resDnsRecordType = PTR;
				break;
			case 16:
				resDnsRecordType = TXT;
				break;
			case 6:
				resDnsRecordType = SOA;
				break;
			case 33:
				resDnsRecordType = SRV;
				break;
			case 5:
				resDnsRecordType = CNAME;
				break;
			case 13:
				resDnsRecordType = HINFO;
				break;
			default:
				throw new Exception(String.format("Wrong dnsRecordType value : %d", dnsRecordType));
		}
		return resDnsRecordType;
	}
}
