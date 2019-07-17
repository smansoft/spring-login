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
@SpringBootTest
@ExtendWith(SpringExtension.class)
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
	@Autowired    
	@Qualifier(IUserService.DEF_BEAN_NAME)
	private IUserService userServiceBean;

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
		String login="sman";
		String email="sman@smansoft.com";
		String name="SMan";		
		String passw="12345";
		String encodedPassw = passwordEncoder.encode(passw);
		boolean enabled = true;
		boolean isAdmin = true;		
		////----------------------------------		
		RegisterVO registerVO = Mockito.spy(RegisterVO.class);		
		
		Mockito.when(registerVO.getLogin()).thenReturn(login);
		Mockito.when(registerVO.getEmail()).thenReturn(email);		
		Mockito.when(registerVO.getName()).thenReturn(name);		
		Mockito.when(registerVO.getPassword()).thenReturn(passw);
		Mockito.when(registerVO.getPasswordConfirm()).thenReturn(passw);		
		Mockito.when(registerVO.getEnabled()).thenReturn(enabled);		
		Mockito.when(registerVO.getIsAdmin()).thenReturn(isAdmin);	
		////----------------------------------
		UserVO userVO = Mockito.spy(UserVO.class);
		
		Mockito.when(userVO.getLogin()).thenReturn(login);
		Mockito.when(userVO.getEmail()).thenReturn(email);		
		Mockito.when(userVO.getName()).thenReturn(name);		
		Mockito.when(userVO.getPassword()).thenReturn(encodedPassw);
		Mockito.when(userVO.getEnabled()).thenReturn(enabled);
		
		List<String> authorities = new ArrayList<String>();
		authorities.add(AuthorityType.RoleUser.toString());
		authorities.add(AuthorityType.RoleAdmin.toString());		
		
		Mockito.when(userVO.getAuthorities()).thenReturn(authorities);		
		////----------------------------------		
		UserEntity userEntity = Mockito.spy(UserEntity.class);
		
		Mockito.when(userEntity.getUserLogin()).thenReturn(login);		
		Mockito.when(userEntity.getEmail()).thenReturn(email);		
		Mockito.when(userEntity.getUserName()).thenReturn(name);		
		Mockito.when(userEntity.getPassword()).thenReturn(encodedPassw);		
		Mockito.when(userEntity.getEnabled()).thenReturn(enabled);		
		////----------------------------------		
		List<AuthorityEntity> authorityEntities = new ArrayList<>();

		AuthorityEntity authorityEntity = Mockito.spy(AuthorityEntity.class);
		Mockito.when(authorityEntity.getAuthorityType()).thenReturn(AuthorityType.RoleUser);				
		Mockito.when(authorityEntity.getUser()).thenReturn(userEntity);
		authorityEntities.add(authorityEntity);
		
		authorityEntity = Mockito.spy(AuthorityEntity.class);
		Mockito.when(authorityEntity.getAuthorityType()).thenReturn(AuthorityType.RoleAdmin);				
		Mockito.when(authorityEntity.getUser()).thenReturn(userEntity);
		authorityEntities.add(authorityEntity);		
			
		Mockito.when(userEntity.getAuthorities()).thenReturn(authorityEntities);
		////----------------------------------
		UserEntity userEntityRes = Mockito.spy(UserEntity.class);
		
		Mockito.when(userEntityRes.getId()).thenReturn(1L);		
		Mockito.when(userEntityRes.getUserLogin()).thenReturn(login);		
		Mockito.when(userEntityRes.getEmail()).thenReturn(email);		
		Mockito.when(userEntityRes.getUserName()).thenReturn(name);		
		Mockito.when(userEntityRes.getPassword()).thenReturn(encodedPassw);		
		Mockito.when(userEntityRes.getEnabled()).thenReturn(enabled);		
		////----------------------------------		
		List<AuthorityEntity> authorityEntitiesRes = new ArrayList<>();
		
		AuthorityEntity authorityEntityRes = Mockito.spy(AuthorityEntity.class);
		Mockito.when(authorityEntityRes.getAuthorityType()).thenReturn(AuthorityType.RoleUser);				
		Mockito.when(authorityEntityRes.getUser()).thenReturn(userEntity);
		authorityEntitiesRes.add(authorityEntityRes);
		
		authorityEntityRes = Mockito.spy(AuthorityEntity.class);
		Mockito.when(authorityEntityRes.getAuthorityType()).thenReturn(AuthorityType.RoleAdmin);				
		Mockito.when(authorityEntityRes.getUser()).thenReturn(userEntity);
		authorityEntitiesRes.add(authorityEntityRes);
			
		Mockito.when(userEntityRes.getAuthorities()).thenReturn(authorityEntitiesRes);
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
		////----------------------------------		
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
