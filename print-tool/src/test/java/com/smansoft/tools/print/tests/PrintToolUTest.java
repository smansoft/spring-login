/**
 * 
 */
package com.smansoft.tools.print.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class PrintToolUTest {

	private static final IPrintTool printTool = PrintTool
			.getPrintToolInstance(LoggerFactory.getLogger(PrintToolUTest.class));

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
	public 	PrintToolUTest() {
		printTool.debug(PrintSfx.SFX_IN);
		
		ipVO = new IpVO();
		addressReqVO = new AddressReqVO();
		
		printTool.debug(PrintSfx.SFX_OUT);		
	}
	
	/**
	 * 
	 */
	@BeforeEach
	public void beforeEachTest() {
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
		Assertions.assertTrue(true);
	}

	/**
	 * 
	 */
	@AfterEach
	public void afterEachTest() {
		printTool.debug(PrintSfx.SFX_IN);
		ipVO.getIpRecords().clear();
		printTool.debug(PrintSfx.SFX_OUT);		
	}

}
