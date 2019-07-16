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
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.smansoft.sl.BaseTest;
import com.smansoft.sl.bl.services.api.IUserService;
import com.smansoft.sl.bl.services.vo.RegisterVO;
import com.smansoft.sl.bl.services.vo.UserVO;
import com.smansoft.sl.exceptions.DaoException;
import com.smansoft.sl.exceptions.ServicesException;
import com.smansoft.sl.persistence.dao.api.IAuthorityEntityDao;
import com.smansoft.sl.persistence.dao.api.IUserEntityDao;
import com.smansoft.sl.persistence.entities.AuthorityEntity;
import com.smansoft.sl.persistence.entities.UserEntity;
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
@TestMethodOrder(OrderAnnotation.class)
public class UserServiceUnitTest extends BaseTest {
	
	private static final IPrintTool printTool = PrintTool
			.getPrintToolInstance(LoggerFactory.getLogger(UserServiceUnitTest.class));
    
	/**
	 * 
	 */
    @Autowired
	@Qualifier(IUserService.DEF_BEAN_NAME)
	private IUserService userServiceBean;
	
	/**
	 * 
	 */
    @MockBean
	@Qualifier(IAuthorityEntityDao.DEF_BEAN_NAME)
	private IAuthorityEntityDao authorityEntityDaoBean;

	/**
	 * 
	 */
    @MockBean
	@Qualifier(IUserEntityDao.DEF_BEAN_NAME)    
	private IUserEntityDao userEntityDaoBean;

    /**
     * 
     */
	@Autowired
	@Qualifier("passwordEncoder")
	private BCryptPasswordEncoder passwordEncoder;

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
		printTool.debug(PrintSfx.SFX_OUT);
	}	
	
	/**
	 * 
	 */
	@Test
	@Order(1)
	public void test1CreateUser() {
		printTool.debug(PrintSfx.SFX_IN);
		////----------------------------------
		String passw="12345";
		String encodedPassw = passwordEncoder.encode(passw);
		////----------------------------------		
///		RegisterVO registerVO = new RegisterVO();  /// Mockito.spy(RegisterVO.class);
		RegisterVO registerVO = Mockito.spy(RegisterVO.class);		
		
		registerVO.setLogin("sman");
		registerVO.setEmail("sman@smansoft.com");
		registerVO.setName("SMan");		
		registerVO.setPassword("12345");		
		registerVO.setPasswordConfirm(passw);
		registerVO.setEnabled(true);
		registerVO.setIsAdmin(true);
		////----------------------------------
///		UserVO userVO = new UserVO();  // Mockito.spy(UserVO.class);
		UserVO userVO = Mockito.spy(UserVO.class);		
		
		userVO.setLogin("sman");
		userVO.setEmail("sman@smansoft.com");
		userVO.setName("SMan");
		userVO.setPassword(encodedPassw);
		userVO.setEnabled(true);
		List<String> authorities = new ArrayList<String>();
		authorities.add(AuthorityType.RoleUser.toString());
		authorities.add(AuthorityType.RoleAdmin.toString());		
		
		userVO.setAuthorities(authorities);
		////----------------------------------		
///		UserEntity userEntity = new UserEntity(); //  Mockito.spy(UserEntity.class);
		UserEntity userEntity = Mockito.spy(UserEntity.class);		
		
		userEntity.setUserLogin("sman");
		userEntity.setEmail("sman@smansoft.com");
		userEntity.setUserName("SMan");
		userEntity.setPassword(encodedPassw);
		userEntity.setEnabled(true);
		////----------------------------------		
		List<AuthorityEntity> authorityEntities = new ArrayList<>();

		AuthorityEntity authorityEntity = new AuthorityEntity();
		authorityEntity.setAuthorityType(AuthorityType.RoleUser);
		authorityEntity.setUser(userEntity);
		authorityEntities.add(authorityEntity);
		
		authorityEntity = new AuthorityEntity();
		authorityEntity.setAuthorityType(AuthorityType.RoleAdmin);
		authorityEntity.setUser(userEntity);
		authorityEntities.add(authorityEntity);
			
		userEntity.setAuthorities(authorityEntities);
		////----------------------------------
		UserEntity userEntityRes = Mockito.spy(UserEntity.class);
		
		userEntityRes.setId(1L);
		userEntityRes.setUserLogin("sman");
		userEntityRes.setEmail("sman@smansoft.com");
		userEntityRes.setUserName("SMan");
		userEntityRes.setPassword(encodedPassw);
		userEntityRes.setEnabled(true);
		////----------------------------------		
		List<AuthorityEntity> authorityEntitiesRes = new ArrayList<>();

		AuthorityEntity authorityEntityRes = new AuthorityEntity();
		authorityEntityRes.setId(1L);
		authorityEntityRes.setAuthorityType(AuthorityType.RoleUser);
		authorityEntityRes.setUser(userEntityRes);
		authorityEntitiesRes.add(authorityEntityRes);
		
		authorityEntityRes = new AuthorityEntity();
		authorityEntityRes.setId(2L);		
		authorityEntityRes.setAuthorityType(AuthorityType.RoleAdmin);
		authorityEntityRes.setUser(userEntity);
		authorityEntitiesRes.add(authorityEntityRes);
		////----------------------------------		
		userEntityRes.setAuthorities(authorityEntitiesRes);
		////----------------------------------		
		try {
			Mockito.when(userEntityDaoBean.save(userEntity)).thenReturn(userEntityRes);
			UserVO mockUserVO = userServiceBean.createUser(registerVO);
			if(!compareTwoUserVOs(userVO, mockUserVO, passw)) { 
				Assertions.fail("Error: Error of creating of User");				
			}
			if(compareTwoUserVOs(userVO, mockUserVO, "54321")) { 
				Assertions.fail("Error: Error of creating of User");				
			}
			mockUserVO.setLogin("sman_1");
			mockUserVO.setEmail("sman_1@smansoft.com");
			if(compareTwoUserVOs(userVO, mockUserVO, passw)) { 
				Assertions.fail("Error: Error of creating of User");				
			}
		} catch (ServicesException e) {
			Assertions.fail(ExceptionTools.stackTraceToString(e));
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
