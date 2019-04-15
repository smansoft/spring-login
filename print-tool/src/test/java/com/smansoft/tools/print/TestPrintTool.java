/**
 * 
 */
package com.smansoft.tools.print;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import com.smansoft.tools.print.api.IPrintTool;
import com.smansoft.tools.print.api.types.PrintSfx;
import com.smansoft.tools.print.impl.PrintTool;
import com.smansoft.tools.print.types.AddressType;
import com.smansoft.tools.print.types.IpType;
import com.smansoft.tools.print.vo.AddressReqVO;
import com.smansoft.tools.print.vo.AddressVO;
import com.smansoft.tools.print.vo.IpRecordVO;
import com.smansoft.tools.print.vo.IpVO;

/**
 * @author SMan
 *
 */
public class TestPrintTool {

	private static final IPrintTool printTool = PrintTool
			.getPrintToolInstance(LoggerFactory.getLogger(TestPrintTool.class));

	/**
	 * 
	 */
	private IpVO	ipVO;

	/**
	 * 
	 */
	private AddressReqVO	addressReqVO;

	/**
	 * 
	 */
	public 	TestPrintTool() {
		ipVO = new IpVO();
		addressReqVO = new AddressReqVO();
	}
	
	/**
	 * 
	 */
	@Before
	public void beforeTest() {
		printTool.debug(PrintSfx.SFX_IN);

		IpRecordVO ipRecordVO = new IpRecordVO();
		
		ipRecordVO.setIpAddress("127.0.0.1");
		ipRecordVO.setIpAddressSrv("127_0_0_1");
		ipRecordVO.setIpType(IpType.IpV4);	
		
		ipVO.getIpRecords().add(ipRecordVO);
		
		ipRecordVO = new IpRecordVO();
		
		ipRecordVO.setIpAddress("216.58.207.78");
		ipRecordVO.setIpAddressSrv("216_58_207_78");
		ipRecordVO.setIpType(IpType.IpV4);
		
		ipVO.getIpRecords().add(ipRecordVO);
		
		ipRecordVO = new IpRecordVO();
		
		ipRecordVO.setIpAddress("93.104.214.157");
		ipRecordVO.setIpAddressSrv("93_104_214_157");
		ipRecordVO.setIpType(IpType.IpV4);	
		
		ipVO.getIpRecords().add(ipRecordVO);
		
		ipRecordVO = new IpRecordVO();
		
		ipRecordVO.setIpAddress("2a00:1450:4001:80b:0:0:0:200e");
		ipRecordVO.setIpAddressSrv("2a00_1450_4001_80b_0_0_0_200e");
		ipRecordVO.setIpType(IpType.IpV6);	

		ipVO.getIpRecords().add(ipRecordVO);
		
		ipRecordVO = new IpRecordVO();
		
		ipRecordVO.setIpAddress("2c0f:fa48:1:0:0:0:0:0");
		ipRecordVO.setIpAddressSrv("2c0f_fa48_1_0_0_0_0_0");
		ipRecordVO.setIpType(IpType.IpV6);
		
		ipVO.getIpRecords().add(ipRecordVO);
		
		AddressVO addressVO = new AddressVO();
		addressVO.setAddress("smansoft.com");
		
		addressReqVO.setAddressType(AddressType.DomainName);
		addressReqVO.setAddressVO(addressVO);
		
		printTool.debug(PrintSfx.SFX_OUT);		
	}

	/**
	 * 
	 */
	@Test
	public void testPrintTool() {
		printTool.debug(PrintSfx.SFX_IN);
		
		printTool.info(ipVO);
		printTool.info(addressReqVO);		
		
		printTool.debug(PrintSfx.SFX_OUT);
		Assert.assertTrue(true);
	}

}
