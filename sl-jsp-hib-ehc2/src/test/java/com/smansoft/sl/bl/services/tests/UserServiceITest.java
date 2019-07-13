/**
 * 
 */
package com.smansoft.sl.bl.services.tests;

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
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.smansoft.sl.BaseTest;
import com.smansoft.sl.bl.services.api.IUserService;
import com.smansoft.sl.bl.services.vo.RegisterVO;
import com.smansoft.sl.bl.services.vo.UserVO;
import com.smansoft.sl.exceptions.DaoException;
import com.smansoft.sl.exceptions.ServicesException;
import com.smansoft.sl.persistence.entities.UserEntity;
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
///@Import(SpringLoginPersistenceConfig.class)
@Execution(ExecutionMode.SAME_THREAD)
@ComponentScan(basePackages="com.smansoft.sl")
@EnableAutoConfiguration(exclude=HibernateJpaAutoConfiguration.class)
public class UserServiceITest extends BaseTest {
	
	private static final IPrintTool printTool = PrintTool
			.getPrintToolInstance(LoggerFactory.getLogger(UserServiceITest.class));
	
	@Autowired
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
	@Test
	@Order(1)
	public void test1CreateUser() {
		printTool.debug(PrintSfx.SFX_IN);
		try {
			RegisterVO registerVO = new RegisterVO();
			
			registerVO.setLogin("sman");
			registerVO.setEmail("sman@smansoft.com");
			registerVO.setName("SMan");		
			registerVO.setPassword("12345");		
			registerVO.setPasswordConfirm("12345");
			registerVO.setEnabled(true);
			
			userServiceBean.createUser(registerVO);
			
			List<UserVO> allUsers = userServiceBean.getAllUsers();
			if(allUsers.size() != 1) {
				Assertions.fail("Error: Wrong Number of Users");
			}
			
			UserVO userVO = allUsers.get(0);
			
			if(!"sman".equals(userVO.getLogin()) ||
					!"sman@smansoft.com".equals(registerVO.getEmail()) ||
					!"SMan".equals(registerVO.getName())) {
				Assertions.fail("Error: Wrong User");
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
	@Test
	@Order(2)	
	public void test2DeleteUserByEmail() {
		printTool.debug(PrintSfx.SFX_IN);
		try {
			RegisterVO registerVO = new RegisterVO();
			
			registerVO.setLogin("sman");
			registerVO.setEmail("sman@smansoft.com");
			registerVO.setName("SMan");		
			registerVO.setPassword("12345");		
			registerVO.setPasswordConfirm("12345");
			registerVO.setEnabled(true);
			
			userServiceBean.createUser(registerVO);
			
			List<UserVO> allUsers = userServiceBean.getAllUsers();
			if(allUsers.size() != 1) {
				Assertions.fail("Error: Wrong Number of Users: User wasn't created.");
			}
			
			userServiceBean.deleteUserByEmail("sman@smansoft.com");
			
			allUsers = userServiceBean.getAllUsers();
			if(allUsers.size() != 0) {
				Assertions.fail("Error: Wrong Number of Users: User wasn't deleted.");
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
	@Test
	@Order(3)	
	public void test3DeleteUserByLogin() {
		printTool.debug(PrintSfx.SFX_IN);
		try {
			RegisterVO registerVO = new RegisterVO();
			
			registerVO.setLogin("sman");
			registerVO.setEmail("sman@smansoft.com");
			registerVO.setName("SMan");		
			registerVO.setPassword("12345");		
			registerVO.setPasswordConfirm("12345");
			registerVO.setEnabled(true);
			
			userServiceBean.createUser(registerVO);
			
			List<UserVO> allUsers = userServiceBean.getAllUsers();
			if(allUsers.size() != 1) {
				Assertions.fail("Error: Wrong Number of Users: User wasn't created.");
			}
			
			userServiceBean.deleteUserByUserLogin("sman");
			
			allUsers = userServiceBean.getAllUsers();
			if(allUsers.size() != 0) {
				Assertions.fail("Error: Wrong Number of Users: User wasn't deleted.");
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
	@Test
	@Order(4)	
	public void test4UpdateUser() {
		printTool.debug(PrintSfx.SFX_IN);
		try {
			RegisterVO registerVO = new RegisterVO();
			
			registerVO.setLogin("sman");
			registerVO.setEmail("sman@smansoft.com");
			registerVO.setName("SMan");		
			registerVO.setPassword("12345");		
			registerVO.setPasswordConfirm("12345");
			registerVO.setEnabled(true);
			
			userServiceBean.createUser(registerVO);
			
			List<UserVO> allUsers = userServiceBean.getAllUsers();
			if(allUsers.size() != 1) {
				Assertions.fail("Error: Wrong Number of Users: User wasn't created.");
			}
			
			registerVO.setLogin("sman");
			registerVO.setEmail("sman@smansoft.com");
			registerVO.setName("SMan.Updated");		
			registerVO.setPassword("12345");		
			registerVO.setPasswordConfirm("12345");
			registerVO.setEnabled(true);
			
			userServiceBean.updateUser(registerVO);
			
			allUsers = userServiceBean.getAllUsers();
			if(allUsers.size() != 1) {
				Assertions.fail("Error: Wrong Number of Users: User wasn't updated.");
			}
			
			List<UserVO> users = userServiceBean.getUsersByUserName("SMan.Updated");
			if(users.size() != 1) {
				Assertions.fail("Error: Wrong Number of Users: User wasn't found by updated name.");
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
	@Test
	@Order(5)	
	public void test4AllUsersRegisters() {
		printTool.debug(PrintSfx.SFX_IN);	
		
		try {
			RegisterVO registerVO = new RegisterVO();
			
			registerVO.setLogin("sman");
			registerVO.setEmail("sman@smansoft.com");
			registerVO.setName("SMan");		
			registerVO.setPassword("12345");		
			registerVO.setPasswordConfirm("12345");
			registerVO.setEnabled(true);
			registerVO.setIsAdmin(true);
			
			userServiceBean.createUser(registerVO);
			
			List<UserVO> allUsers = userServiceBean.getAllUsers();
			List<RegisterVO> allRegisters = userServiceBean.getAllRegisters();
			if(allUsers.size() != 1 || allRegisters.size() != 1) {
				Assertions.fail("Error: Wrong Number of Users: User wasn't created.");
			}
			
			registerVO.setLogin("msv");
			registerVO.setEmail("msv@smansoft.com");
			registerVO.setName("MSV");		
			registerVO.setPassword("54321");		
			registerVO.setPasswordConfirm("54321");
			registerVO.setEnabled(true);
			
			userServiceBean.createUser(registerVO);
			
			allUsers = userServiceBean.getAllUsers();
			allRegisters = userServiceBean.getAllRegisters();			
			if(allUsers.size() != 2 || allRegisters.size() != 2) {
				Assertions.fail("Error: Wrong Number of Users: User wasn't created.");
			}
			
			userServiceBean.deleteUserByEmail("msv@smansoft.com");
			
			allUsers = userServiceBean.getAllUsers();
			allRegisters = userServiceBean.getAllRegisters();			
			if(allUsers.size() != 1 || allRegisters.size() != 1) {
				Assertions.fail("Error: Wrong Number of Users: User wasn't deleted.");
			}

			UserVO userVO = allUsers.get(0);
			if(!"sman".equals(userVO.getLogin()) || !"sman@smansoft.com".equals(userVO.getEmail()) 
					|| !"SMan".equals(userVO.getName())) {
				Assertions.fail("Error: Wrong UserVO.");
			}
			registerVO = allRegisters.get(0);
			if(!"sman".equals(registerVO.getLogin()) || !"sman@smansoft.com".equals(registerVO.getEmail()) 
					|| !"SMan".equals(registerVO.getName()) || !registerVO.getIsAdmin()) {
				Assertions.fail("Error: Wrong RegisterVO.");
			}

			userServiceBean.deleteUserByUserLogin("sman");
			
			allUsers = userServiceBean.getAllUsers();
			allRegisters = userServiceBean.getAllRegisters();			
			if(allUsers.size() != 0 || allRegisters.size() != 0) {
				Assertions.fail("Error: Wrong Number of Users: User wasn't deleted.");
			}			
			
		} catch (ServicesException e) {
			Assertions.fail(ExceptionTools.stackTraceToString(e));
		}		
		printTool.debug(PrintSfx.SFX_OUT);
		Assertions.assertTrue(true);			
	}
	
	/**
	 * 
	 */
	@AfterEach
	public void afterEachTest() {
		printTool.debug(PrintSfx.SFX_IN);
		clearDataBase();
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
