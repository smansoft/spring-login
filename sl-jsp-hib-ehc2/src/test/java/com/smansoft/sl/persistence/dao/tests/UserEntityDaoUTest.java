/**
 * 
 */
package com.smansoft.sl.persistence.dao.tests;

import org.hibernate.SessionFactory;
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
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.smansoft.sl.BaseTest;
import com.smansoft.sl.bl.services.converters.RegisterVOToUserEntityConverter;
import com.smansoft.sl.bl.services.converters.UserEntityToRegisterVOConverter;
import com.smansoft.sl.bl.services.converters.UserEntityToUserVOConverter;
import com.smansoft.sl.bl.services.vo.RegisterVO;
import com.smansoft.sl.config.SpringLoginPersistenceConfig;
import com.smansoft.sl.exceptions.DaoException;
import com.smansoft.sl.persistence.dao.api.IAuthorityEntityDao;
import com.smansoft.sl.persistence.dao.api.IUserEntityDao;
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
@Import(SpringLoginPersistenceConfig.class)
@Execution(ExecutionMode.SAME_THREAD)
@ComponentScan(basePackages="com.smansoft.sl")
@EnableAutoConfiguration(exclude=HibernateJpaAutoConfiguration.class)
public class UserEntityDaoUTest extends BaseTest {

	private static final IPrintTool printTool = PrintTool
			.getPrintToolInstance(LoggerFactory.getLogger(UserEntityDaoUTest.class));
	
	@Autowired
	@Qualifier(IAuthorityEntityDao.DEF_BEAN_NAME)
	private IAuthorityEntityDao authorityEntityDaoBean;

	@Autowired
	@Qualifier(IUserEntityDao.DEF_BEAN_NAME)
	private IUserEntityDao userEntityDaoBean;

	@Autowired
	@Qualifier(RegisterVOToUserEntityConverter.DEF_BEAN_NAME)
	private RegisterVOToUserEntityConverter registerVOToUserEntityConverterBean;

	@Autowired
	@Qualifier(UserEntityToUserVOConverter.DEF_BEAN_NAME)
	private UserEntityToUserVOConverter userEntityToUserVOConverterBean;

	@Autowired
	@Qualifier(UserEntityToRegisterVOConverter.DEF_BEAN_NAME)
	private UserEntityToRegisterVOConverter userEntityToRegisterVOConverterBean;
	
	/**
	 * 
	 */
	@Autowired
	protected SessionFactory sessionFactory;
	
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
		
		RegisterVO registerVO = new RegisterVO();
		
		registerVO.setLogin("sman");
		registerVO.setEmail("sman@smansoft.com");
		registerVO.setName("SMan");		
		registerVO.setPassword("12345");		
		registerVO.setPasswordConfirm("12345");
		registerVO.setEnabled(true);
		
		UserEntity userEntity = registerVOToUserEntityConverterBean.apply(registerVO);
		
		try {
			userEntityDaoBean.save(userEntity);
		} catch (DaoException e) {
			Assertions.fail(ExceptionTools.stackTraceToString(e));
		}
		
		try {
			Long count = userEntityDaoBean.count();
			if(count != 1) {
				Assertions.fail("Wrong Number of Users");				
			}
		} catch (DaoException e) {
			Assertions.fail(ExceptionTools.stackTraceToString(e));
		}
		
		registerVO.setLogin("msv");
		registerVO.setEmail("msv@smansoft.com");
		registerVO.setName("MSV");		
		registerVO.setPassword("54321");		
		registerVO.setPasswordConfirm("54321");
		registerVO.setEnabled(true);
		
		userEntity = registerVOToUserEntityConverterBean.apply(registerVO);
		
		try {
			userEntityDaoBean.save(userEntity);
		} catch (DaoException e) {
			Assertions.fail(ExceptionTools.stackTraceToString(e));
		}
		
		try {
			Long count = userEntityDaoBean.count();
			if(count != 2) {
				Assertions.fail("Error: Wrong Number of Users");				
			}
		} catch (DaoException e) {
			Assertions.fail(ExceptionTools.stackTraceToString(e));
		}
		
		registerVO.setLogin("sman");
		registerVO.setEmail("sman1@smansoft.com");
		registerVO.setName("SMan");		
		registerVO.setPassword("12345");		
		registerVO.setPasswordConfirm("12345");
		registerVO.setEnabled(true);
		
		userEntity = registerVOToUserEntityConverterBean.apply(registerVO);
		
		try {
			userEntityDaoBean.save(userEntity);
			Assertions.fail("Error: Second User 'sman' can't be created");
		} catch (DaoException e) {
		}
		
		registerVO.setLogin("sman1");
		registerVO.setEmail("sman@smansoft.com");
		registerVO.setName("SMan");		
		registerVO.setPassword("12345");		
		registerVO.setPasswordConfirm("12345");
		registerVO.setEnabled(true);
		
		userEntity = registerVOToUserEntityConverterBean.apply(registerVO);
		
		try {
			userEntityDaoBean.save(userEntity);
			Assertions.fail("Error: Second User 'sman@smansoft.com' can't be created");
		} catch (DaoException e) {
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
	public void test2FindUser() {
		printTool.debug(PrintSfx.SFX_IN);
		
		RegisterVO registerVO = new RegisterVO();
		
		registerVO.setLogin("sman");
		registerVO.setEmail("info@smansoft.com");
		registerVO.setName("SMan");		
		registerVO.setPassword("12345");		
		registerVO.setPasswordConfirm("12345");
		registerVO.setEnabled(true);
		
		UserEntity userEntity = registerVOToUserEntityConverterBean.apply(registerVO);
		
		try {
			userEntityDaoBean.save(userEntity);
		} catch (DaoException e) {
			Assertions.fail(ExceptionTools.stackTraceToString(e));
		}
		
		try {
			Long count = userEntityDaoBean.count();
			if(count != 1) {
				Assertions.fail("Wrong Number of Users");				
			}
		} catch (DaoException e) {
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
	public void test3DoSmth() {
		printTool.debug(PrintSfx.SFX_IN);
		
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
