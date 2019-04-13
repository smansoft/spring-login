/**
 * 
 */
package com.smansoft.sl.tools.common;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.smansoft.sl.exceptions.InitException;
import com.smansoft.sl.exceptions.ServicesException;
import com.smansoft.tools.print.api.IPrintTool;
import com.smansoft.tools.print.api.IPrintToolStr;

/**
 * @author SMan
 *
 */
public class IpTools {

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private IPrintTool printTool = null;

	@SuppressWarnings("unused")
	private IPrintToolStr printToolStr = null;

	/**
	 * 
	 */
	public static final String DEF_IPV4_PATTERN = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";

	/**
	 * 
	 */
	public static final String DEF_IPV6_PATTERN = "([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";

	/**
	 * 
	 */
	public static final String DEF_DOMAIN_NAME_PATTERN = "([a-zA-Z0-9](([a-zA-Z0-9\\-]{0,65})[a-zA-Z0-9])?\\.)+([a-zA-Z]{2,6})(\\.)?";

	/**
	 * 
	 */
	public static final String DEF_DOMAIN_NAME_PATTERN_1 = ".*(\\-){2,}.*";

	/**
	 * 
	 */
	public static final String DEF_DOMAIN_NAME_PATTERN_2DASH = ".*(\\-){2,}.*";

	/**
	 * 
	 */
	public static final String DEF_IPV4_PATTERN_FLINE = String.format("^%s$", DEF_IPV4_PATTERN);

	/**
	 * 
	 */
	public static final String DEF_IPV6_PATTERN_FLINE = String.format("^%s$", DEF_IPV6_PATTERN);

	/**
	 * 
	 */
	public static final String DEF_DOMAIN_NAME_PATTERN_FLINE = String.format("^%s$", DEF_DOMAIN_NAME_PATTERN);

	/**
	 * 
	 */
	public static final String DEF_DOMAIN_NAME_PATTERN_2DASH_FLINE = String.format("^%s$",
			DEF_DOMAIN_NAME_PATTERN_2DASH);

	/**
	 * 
	 */
	public static final String DEF_IPV4_IPV6_DOMAIN_NAME_PATTERN_FLINE = String.format("^((%s)|(%s)|(%s))$",
			DEF_IPV4_PATTERN, DEF_IPV6_PATTERN, DEF_DOMAIN_NAME_PATTERN);

	/**
	 * 
	 */
	public static final String DEF_HTTP_IPV4_PATTERN_FLINE = String.format("^(http|https)://(%s)(/\\S*)?$",
			DEF_IPV4_PATTERN);

	/**
	 * 
	 */
	public static final String DEF_HTTP_IPV6_PATTERN_FLINE = String.format("^(http|https)://(%s)(/\\S*)?$",
			DEF_IPV6_PATTERN);

	/**
	 * 
	 */
	public static final String DEF_HTTP_DOMAIN_NAME_PATTERN_FLINE = String.format("^(http|https)://(%s)(/\\S*)?$",
			DEF_DOMAIN_NAME_PATTERN);

	/**
	 * 
	 */
	public static final String DEF_HTTP_PATTERN_FLINE = String.format(
			"^(http|https)://((%s)|(\\[%s\\])|(%s))(:[0-9]{1,4})?(/\\S*)?$", DEF_IPV4_PATTERN, DEF_IPV6_PATTERN,
			DEF_DOMAIN_NAME_PATTERN);

	/**
	 * 
	 */
	public static final String DEF_EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/**
	 * 
	 */
	private static Pattern validIpV4Pattern = null;

	/**
	 * 
	 */
	private static Pattern validIpV6Pattern = null;

	/**
	 * 
	 */
	private static Pattern validEmailPattern = null;

	/**
	 * 
	 */
	private static Pattern validDomainNamePattern = null;

	/**
	 * 
	 */
	private static Pattern dblDushInDomainNamePattern = null;

	/**
	 * 
	 */
	private static Pattern httpPattern = null;

	/**
	 * 
	 */
	static {
		try {
			validIpV4Pattern = Pattern.compile(DEF_IPV4_PATTERN_FLINE, Pattern.CASE_INSENSITIVE);
			validIpV6Pattern = Pattern.compile(DEF_IPV6_PATTERN_FLINE, Pattern.CASE_INSENSITIVE);
			validDomainNamePattern = Pattern.compile(DEF_DOMAIN_NAME_PATTERN_FLINE);
			dblDushInDomainNamePattern = Pattern.compile(DEF_DOMAIN_NAME_PATTERN_2DASH_FLINE);
			validEmailPattern = Pattern.compile(DEF_EMAIL_PATTERN);
			httpPattern = Pattern.compile(DEF_HTTP_PATTERN_FLINE);
		} catch (PatternSyntaxException e) {
			System.err.println(ExceptionTools.stackTraceToString(e));
		}
	}

	/**
	 * @throws InitException
	 * 
	 */
	public IpTools(IPrintTool printTool) throws InitException {
		this.printTool = printTool;
	}

	/**
	 * @throws InitException
	 * 
	 */
	public IpTools(IPrintToolStr printToolStr) throws InitException {
		this.printToolStr = printToolStr;
	}

	/**
	 * 
	 * @param ipV4Value
	 * @return
	 * @throws ServicesException
	 */
	public Long ipv4_aton(String ipV4Value) throws ServicesException {
		if (ipV4Value == null) {
			throw new ServicesException("ipV4Value == null");
		}
		if (ipV4Value.length() == 0) {
			throw new ServicesException("ipV4Value.length == 0");
		}
		Long lIpv4Value = 0L;
		try {
			String[] ipV4Segments = ipV4Value.split("\\.");
			for (int nIdx = 0; nIdx < ipV4Segments.length; nIdx++) {
				String ipV4Segment = ipV4Segments[nIdx];
				lIpv4Value += (Long.parseLong(ipV4Segment) << 8 * (ipV4Segments.length - nIdx - 1));
			}
		} catch (Exception e) {
			throw new ServicesException(ExceptionTools.stackTraceToString(e), e);
		}
		return lIpv4Value;
	}

	/**
	 * 
	 * @param lIpV4Value
	 * @return
	 * @throws ServicesException
	 */
	public String ipv4_ntoa(Long lIpV4Value) throws ServicesException {
		byte[] ipV4AddressBytes = new byte[] { (byte) (lIpV4Value.longValue() >> 24),
				(byte) (lIpV4Value.longValue() >> 16), (byte) (lIpV4Value.longValue() >> 8),
				(byte) lIpV4Value.longValue() };
		String address = null;
		try {
			address = InetAddress.getByAddress(ipV4AddressBytes).getHostAddress();
		} catch (UnknownHostException e) {
			throw new ServicesException(ExceptionTools.stackTraceToString(e), e);
		}
		return address;
	}

	/**
	 * 
	 * @param ipV6Value
	 * @return
	 * @throws ServicesException
	 */
	public byte[] ipv6_aton(String ipV6Value) throws ServicesException {
		if (ipV6Value == null) {
			throw new ServicesException("ipV6Value == null");
		}
		if (ipV6Value.length() == 0) {
			throw new ServicesException("ipV6Value.length == 0");
		}
		byte[] address = null;
		try {
			address = InetAddress.getByName(ipV6Value).getAddress();
		} catch (UnknownHostException e) {
			throw new ServicesException(ExceptionTools.stackTraceToString(e), e);
		}
		return address;
	}

	/**
	 * 
	 * @param bytesIpV6Value
	 * @return
	 * @throws ServicesException
	 */
	public String ipv6_ntoa(byte[] bytesIpV6Value) throws ServicesException {
		String address = null;
		try {
			address = InetAddress.getByAddress(bytesIpV6Value).getHostAddress();
		} catch (UnknownHostException e) {
			throw new ServicesException(ExceptionTools.stackTraceToString(e), e);
		}
		return address;
	}

	/**
	 * 
	 * @param ipAddress
	 * @return
	 */
	public boolean isIpV4(String ipAddress) {
		boolean bRes = false;
		Matcher matcher = validIpV4Pattern.matcher(ipAddress);
		if (matcher.matches()) {
			bRes = true;
		}
		return bRes;
	}

	/**
	 * 
	 * @param ipAddress
	 * @return
	 */
	public boolean isIpV6(String ipAddress) {
		boolean bRes = false;
		Matcher matcher = validIpV6Pattern.matcher(ipAddress);
		if (matcher.matches()) {
			bRes = true;
		}
		return bRes;
	}

	/**
	 * 
	 * @param email
	 * @return
	 */
	public boolean isEmail(String email) {
		boolean bRes = false;
		Matcher matcher = validEmailPattern.matcher(email);
		if (matcher.matches()) {
			bRes = true;
		}
		return bRes;
	}

	/**
	 * 
	 * @param dnAddress
	 * @return
	 */
	public boolean isDomainName(String dnAddress) {
		boolean bRes = false;
		Matcher dnMatcher = validDomainNamePattern.matcher(dnAddress);
		if (dnMatcher.matches()) {
			Matcher dushInDnMatcher = dblDushInDomainNamePattern.matcher(dnAddress);
			if (!dushInDnMatcher.matches()) {
				bRes = true;
			}
		}
		return bRes;
	}

	/**
	 * 
	 * @param httpAddress
	 * @return
	 */
	public boolean isHttpAddress(String httpAddress) {
		boolean bRes = false;
		Matcher httpMatcher = httpPattern.matcher(httpAddress);
		if (httpMatcher != null && httpMatcher.matches()) {
			bRes = true;
		}
		return bRes;
	}

}
