/**
 * 
 */
package com.smansoft.sl.bl.services.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.smansoft.sl.BaseTest;
import com.smansoft.sl.bl.services.api.IUserService;
import com.smansoft.sl.bl.services.vo.RegisterVO;
import com.smansoft.sl.bl.services.vo.UserVO;
import com.smansoft.sl.exceptions.ServicesException;
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
@TestPropertySource(locations="classpath:application-test.properties")
@Execution(ExecutionMode.SAME_THREAD)
@ComponentScan(basePackages="com.smansoft.sl")
@EnableAutoConfiguration(exclude=HibernateJpaAutoConfiguration.class)
public class UserServiceUnitTest extends BaseTest {
	
	private static final IPrintTool printTool = PrintTool
			.getPrintToolInstance(LoggerFactory.getLogger(UserServiceUnitTest.class));
	
	/**
	 * 
	 */
	@MockBean
	@Qualifier(IUserService.DEF_BEAN_NAME)
	private IUserService userServiceBean;
	
	/**
	 * 
	 */
	@BeforeAll
	public static void beforeAllTests() {
		printTool.debug(PrintSfx.SFX_IN);
		recreateDatabase();		
		printTool.debug(PrintSfx.SFX_OUT);
	}

	/**
	 * 
	 */
	@BeforeEach
	public void beforeEachTest() {
		printTool.debug(PrintSfx.SFX_IN);
		clearDataBase();
		printTool.debug(PrintSfx.SFX_OUT);
	}	
	
	/**
	 * 
	 */
///	@Test
///	@Order(1)
	public void test1CreateUser() {
		printTool.debug(PrintSfx.SFX_IN);
		try {
			RegisterVO registerVO = Mockito.spy(RegisterVO.class);
			
			registerVO.setLogin("sman");
			registerVO.setEmail("sman@smansoft.com");
			registerVO.setName("SMan");		
			registerVO.setPassword("12345");		
			registerVO.setPasswordConfirm("12345");
			registerVO.setEnabled(true);
			registerVO.setIsAdmin(false);
			
			UserVO userVO = Mockito.spy(UserVO.class);
			
			userVO.setLogin("sman");
			userVO.setEmail("sman@smansoft.com");
			userVO.setName("SMan");
			userVO.setPassword("12345");
			userVO.setEnabled(true);
			
			List<String> authorities = new ArrayList<String>();
			authorities.add(AuthorityType.RoleUser.toString());
			
			userVO.setAuthorities(authorities);
			
			Mockito.when(userServiceBean.createUser(registerVO)).thenReturn(userVO);
			
			String login = Mockito.verify(userServiceBean.createUser(registerVO)).getLogin();
			
			if(!login.equals(userVO.getLogin())) {
				Assertions.fail("Error: Error of creating of User");				
			}
		} catch (ServicesException e) {
			Assertions.fail(ExceptionTools.stackTraceToString(e));
		}
		printTool.info("Test is OK");		
		printTool.debug(PrintSfx.SFX_OUT);
		Assertions.assertTrue(true);
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
