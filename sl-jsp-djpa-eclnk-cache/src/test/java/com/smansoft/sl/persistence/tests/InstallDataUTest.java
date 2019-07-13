/**
 * 
 */
package com.smansoft.sl.persistence.tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.smansoft.sl.bl.services.api.IUserService;
import com.smansoft.sl.bl.services.vo.UserVO;
import com.smansoft.sl.config.SpringLoginPersistenceConfig;
import com.smansoft.sl.persistence.dao.IUserRepository;
import com.smansoft.sl.persistence.dao.IUserRoleRepository;
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
@ComponentScan (basePackages="com.smansoft.sl")
@EnableAutoConfiguration(exclude=HibernateJpaAutoConfiguration.class)
public class InstallDataUTest {

	private static final IPrintTool printTool = PrintTool.getPrintToolInstance(LoggerFactory.getLogger(InstallDataUTest.class));

	@Autowired
	@Qualifier(IUserService.DEF_BEAN_NAME)
	private IUserService userServiceBean;
	
	@Autowired
	@Qualifier(IUserRepository.DEF_BEAN_NAME)
	private IUserRepository userRepository;

	@Autowired
	@Qualifier(IUserRoleRepository.DEF_BEAN_NAME)
	private IUserRoleRepository userRoleRepository;	

	@Autowired
	@Qualifier("passwordEncoder")
	private BCryptPasswordEncoder passwordEncoder;
	
	/**
	 * 
	 */
    public static void recreateDatabase() {
        try {
            Properties props = new Properties();
            
            props.load(InstallDataUTest.class.getClassLoader().getResourceAsStream("jdbc.properties"));
            
            Class.forName(props.getProperty("jdbc.driver"));
            
            Connection con = DriverManager.getConnection(
            		props.getProperty("jdbc.url"),
            		props.getProperty("jdbc.user"),
            		props.getProperty("jdbc.password"));
            
            Statement stmt = null;            
            stmt = con.createStatement();
            
            String sql = "drop database if exists spring_login_test;";
            stmt.executeUpdate(sql); 
            
            sql = "create database spring_login_test character set 'utf8mb4' collate 'utf8mb4_unicode_ci';";
            stmt.executeUpdate(sql); 
            
            con.close();
            
            return;
        }
        catch (FileNotFoundException e) {
        	printTool.error(ExceptionTools.stackTraceToString(e), e);
        	
        } catch (IOException e) {
        	printTool.error(ExceptionTools.stackTraceToString(e), e);
        } catch (ClassNotFoundException e) {
        	printTool.error(ExceptionTools.stackTraceToString(e), e);        	
        } catch (SQLException e) {
        	printTool.error(ExceptionTools.stackTraceToString(e), e);        	
        }
        return;
    }
	
	/**
	 * 
	 */
	private void clearDataBase() {
		printTool.debug(PrintSfx.SFX_IN);
		
		try {
            Properties props = new Properties();

            props.load(InstallDataUTest.class.getClassLoader().getResourceAsStream("application-test.properties"));

            Class.forName(props.getProperty("spring.datasource.driver-class-name"));
            
            Connection con = DriverManager.getConnection(
            		props.getProperty("spring.datasource.url"),
            		props.getProperty("spring.datasource.username"),
            		props.getProperty("spring.datasource.password"));
            
            Statement stmt = con.createStatement();
            
            String sql = "delete from users";
            stmt.executeUpdate(sql); 
            
            sql = "delete from user_roles";
            stmt.executeUpdate(sql); 
            
            con.close();
            
            return;
        }
        catch (FileNotFoundException e) {
        	printTool.error(ExceptionTools.stackTraceToString(e), e);
        	
        } catch (IOException e) {
        	printTool.error(ExceptionTools.stackTraceToString(e), e);
        } catch (ClassNotFoundException e) {
        	printTool.error(ExceptionTools.stackTraceToString(e), e);        	
        } catch (SQLException e) {
        	printTool.error(ExceptionTools.stackTraceToString(e), e);        	
        }		
		printTool.debug(PrintSfx.SFX_OUT);		
	}
	
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
	public void installData() {
		printTool.debug(PrintSfx.SFX_IN);

		List<UserVO> userVOs = userServiceBean.getAllUsers();
		for(UserVO userVO : userVOs) {
			printTool.debug(userVO);
		}
		
		Assertions.assertTrue(true);

		printTool.debug(PrintSfx.SFX_OUT);
	}
	
	/**
	 * 
	 */
	@Test
	public void testData() {
		printTool.debug(PrintSfx.SFX_IN);
		Assertions.assertTrue(true);		
		printTool.debug(PrintSfx.SFX_OUT);
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
