/**
 * 
 */
package com.smansoft.sl.bl.services.vo.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.smansoft.sl.bl.services.vo.UserVO;
import com.smansoft.sl.persistence.types.AuthorityType;
import com.smansoft.tools.common.ExceptionTools;
import com.smansoft.tools.print.api.IPrintTool;
import com.smansoft.tools.print.api.types.PrintSfx;
import com.smansoft.tools.print.impl.PrintTool;

/**
 * @author SMan
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Execution(ExecutionMode.SAME_THREAD)
@TestMethodOrder(OrderAnnotation.class)
public class UserVOUnitTest {
	
	private static final IPrintTool printTool = PrintTool
			.getPrintToolInstance(LoggerFactory.getLogger(UserVOUnitTest.class));
	
	private UserVO vo; 
	
	private UserVO vo_equal;	
	
	private UserVO vo_different;	
	
	/**
	 * 
	 */
	private void init() {
		printTool.debug(PrintSfx.SFX_IN);	
		
		vo = new UserVO();
		
		vo.setLogin("sman");
		vo.setEmail("sman@smansoft.com");
		vo.setName("SMan");		
		vo.setPassword("12345");		
		vo.setEnabled(true);
		
		List<String> authorities = new ArrayList<String>();
		authorities.add(AuthorityType.RoleUser.toString());
		authorities.add(AuthorityType.RoleAdmin.toString());		
		
		vo.setAuthorities(authorities);
		
		try {
			vo_equal = vo.clone();
		}
		catch (CloneNotSupportedException e) {
			Assertions.fail(ExceptionTools.stackTraceToString(e));			
		}	
		
		vo_different = new UserVO();		

		vo_different.setLogin("msv");
		vo_different.setEmail("msv@smansoft.com");
		vo_different.setName("MSV");		
		vo_different.setPassword("12345");		
		vo_different.setEnabled(true);
		
		authorities = new ArrayList<String>();
		authorities.add(AuthorityType.RoleUser.toString());
		
		vo_different.setAuthorities(authorities);

		printTool.debug(PrintSfx.SFX_OUT);		
		return;
	}
	
	
	/**
	 * 
	 */
	@BeforeAll
	public static void beforeAllTests() {
		printTool.debug(PrintSfx.SFX_IN);
		printTool.debug(PrintSfx.SFX_OUT);
	}

	/**
	 * 
	 */
	@BeforeEach
	public void beforeEachTest() {
		printTool.debug(PrintSfx.SFX_IN);
		init();		
		printTool.debug(PrintSfx.SFX_OUT);
	}
	
	/**
	 *	test: compare two equals UserVO objects;
	 *	result:
	 *		true, if objects are equal
	 *		fail, if objects are different   
	 */
	@Test
	@Order(1)
	public void testCompareTwoEqual() {
		printTool.debug(PrintSfx.SFX_IN);
		if(vo.equals(vo_equal)) {
			Assertions.assertTrue(true);			
		}
		else {
			Assertions.fail("Error: VO objects are different, but should be equal.");
		}
		printTool.debug(PrintSfx.SFX_OUT);		
	}
	
	/**
	 *	test: compare two different UserVO objects;
	 *	result:
	 *		true, if objects are different
	 *		fail, if objects are equal   
	 */
	@Test
	@Order(2)
	public void testCompareTwoDifferent() {
		printTool.debug(PrintSfx.SFX_IN);
		if(!vo.equals(vo_different)) {
			Assertions.assertTrue(true);			
		}
		else {
			Assertions.fail("Error: VO objects are equal, but should be different.");
		}
		printTool.debug(PrintSfx.SFX_OUT);		
	}
	
	/**
	 *	test: compare two different UserVO objects (by every field);
	 *	result:
 	 *		true, if objects are different
	 *		fail, if objects are equal
	 */
	@Test
	@Order(3)
	public void testCompareTwoDifferentByEveryField() {
		printTool.debug(PrintSfx.SFX_IN);
		try {
			UserVO vo_equal_tmp = vo.clone();
			if(vo.equals(vo_equal_tmp)) {
				Assertions.assertTrue(true);			
			}
			else {
				Assertions.fail("Error: VO objects are different, but should be equal.");
			}
			
			vo_equal_tmp = vo.clone();
			vo_equal_tmp.setLogin("fake_login");
			if(!vo.equals(vo_equal_tmp)) {
				Assertions.assertTrue(true);			
			}
			else {
				Assertions.fail("Error: VO objects are equal, but should be different.");			
			}

			vo_equal_tmp = vo.clone();
			vo_equal_tmp.setEmail("fake_email@fake.com");
			if(!vo.equals(vo_equal_tmp)) {
				Assertions.assertTrue(true);			
			}
			else {
				Assertions.fail("Error: VO objects are equal, but should be different.");			
			}
			
			vo_equal_tmp = vo.clone();
			vo_equal_tmp.setName("Fake_Name");
			if(!vo.equals(vo_equal_tmp)) {
				Assertions.assertTrue(true);			
			}
			else {
				Assertions.fail("Error: VO objects are equal, but should be different.");			
			}
			
			vo_equal_tmp = vo.clone();
			vo_equal_tmp.setPassword("Fake_Password");
			if(!vo.equals(vo_equal_tmp)) {
				Assertions.assertTrue(true);			
			}
			else {
				Assertions.fail("Error: VO objects are equal, but should be different.");			
			}
			
			vo_equal_tmp = vo.clone();
			vo_equal_tmp.setEnabled(false);
			if(!vo.equals(vo_equal_tmp)) {
				Assertions.assertTrue(true);			
			}
			else {
				Assertions.fail("Error: VO objects are equal, but should be different.");			
			}
			
			vo_equal_tmp = vo.clone();
			List<String> authorities = new ArrayList<String>();
			authorities.add(AuthorityType.RoleUser.toString());
			vo_equal_tmp.setAuthorities(authorities);
			if(!vo.equals(vo_equal_tmp)) {
				Assertions.assertTrue(true);			
			}
			else {
				Assertions.fail("Error: VO objects are equal, but should be different.");			
			}
		} 
		catch (CloneNotSupportedException e) {
			Assertions.fail(ExceptionTools.stackTraceToString(e));			
		}
		printTool.debug(PrintSfx.SFX_OUT);		
	}
	
	/**
	 *	test: compare two different objects (same object, null, other class);
	 *	result:
 	 *		true, if true, false, false
	 *		fail, if !(true, false, false)
	 */
	@Test
	@Order(4)
	public void testCompareThis_CompareNull_CompareOtherClass() {
		printTool.debug(PrintSfx.SFX_IN);
		if(vo.equals(vo)) {
			Assertions.assertTrue(true);			
		}
		else {
			Assertions.fail("Error: VO objects are different, but should be equal.");				
		}
		
		if(!vo.equals(null)) {
			Assertions.assertTrue(true);			
		}
		else {
			Assertions.fail("Error: VO objects are equal, but should be different.");				
		}
		
		if(!vo.equals(new Object())) {
			Assertions.assertTrue(true);			
		}
		else {
			Assertions.fail("Error: VO objects are equal, but should be different.");				
		}
		printTool.debug(PrintSfx.SFX_OUT);		
	}	

	/**
	 * 
	 */
	@AfterEach
	public void afterEachTest() {
		printTool.debug(PrintSfx.SFX_IN);
		printTool.debug(PrintSfx.SFX_OUT);
	}
	
	/**
	 * 
	 */
	@AfterAll
	public static void afterAllTests() {
		printTool.debug(PrintSfx.SFX_IN);
		printTool.debug(PrintSfx.SFX_OUT);
	}	

}
