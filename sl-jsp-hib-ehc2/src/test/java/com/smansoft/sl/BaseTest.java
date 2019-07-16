/**
 * 
 */
package com.smansoft.sl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.smansoft.sl.bl.services.vo.UserVO;
import com.smansoft.tools.common.ExceptionTools;
import com.smansoft.tools.print.api.IPrintTool;
import com.smansoft.tools.print.api.types.PrintSfx;
import com.smansoft.tools.print.impl.PrintTool;

/**
 * @author SMan
 *
 */
public class BaseTest {
	
	private static final IPrintTool printTool = PrintTool
			.getPrintToolInstance(LoggerFactory.getLogger(BaseTest.class));	
	
	/**
	 * 
	 */
	protected static void recreateDatabase() {
        try {
            Properties props = new Properties();
            
            props.load(BaseTest.class.getClassLoader().getResourceAsStream("jdbc.properties"));
            
            Class.forName(props.getProperty("jdbc.driver"));
            
            Connection con = DriverManager.getConnection(
            		props.getProperty("jdbc.url"),
            		props.getProperty("jdbc.user"),
            		props.getProperty("jdbc.password"));
            
            Statement stmt = con.createStatement();
            
            String sql = "drop database if exists spring_login_test";
            stmt.executeUpdate(sql); 
            
            sql = "create database spring_login_test character set 'utf8mb4' collate 'utf8mb4_unicode_ci'";
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
	protected void clearDataBase() {
		printTool.debug(PrintSfx.SFX_IN);
		try {
            Properties props = new Properties();
            
            props.load(BaseTest.class.getClassLoader().getResourceAsStream("application-test.properties"));
            
            Class.forName(props.getProperty("spring.datasource.driver-class-name"));
            
            Connection con = DriverManager.getConnection(
            		props.getProperty("spring.datasource.url"),
            		props.getProperty("spring.datasource.username"),
            		props.getProperty("spring.datasource.password"));

            Statement stmt = con.createStatement();
            
            String sql = "delete from users";
            stmt.executeUpdate(sql); 
            
            sql = "delete from authorities";
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
	 * @param firstUserVO
	 * @param secondUserVO
	 * @param passw
	 * @return
	 */
	protected static boolean compareTwoUserVOs(UserVO firstUserVO, UserVO secondUserVO, String passw) {
		if(firstUserVO == secondUserVO) {
			return true;
		}
		if((firstUserVO.getLogin() != null || secondUserVO.getLogin() != null) &&   
				(!firstUserVO.getLogin().equals(secondUserVO.getLogin()))) {
			return false;
		}
		if((firstUserVO.getEmail() != null || secondUserVO.getEmail() != null) &&   
				(!firstUserVO.getEmail().equalsIgnoreCase(secondUserVO.getEmail()))) {
			return false;
		}
		if((firstUserVO.getName() != null || secondUserVO.getName() != null) &&   
				(!firstUserVO.getName().equalsIgnoreCase(secondUserVO.getName()))) {
			return false;
		}
		BCryptPasswordEncoder passwEncoder = new BCryptPasswordEncoder();		
		if((firstUserVO.getPassword() != null || secondUserVO.getPassword() != null) &&
				(!passwEncoder.matches(passw, firstUserVO.getPassword()) ||  
				!passwEncoder.matches(passw, secondUserVO.getPassword()))) {
			return false;
		}
		if(firstUserVO.getEnabled() != secondUserVO.getEnabled()) {
			return false;
		}
		if(firstUserVO.getAuthorities().size() != secondUserVO.getAuthorities().size()) {
			return false;
		}
		int nFirstSize = firstUserVO.getAuthorities().size();
		int nSecondSize = secondUserVO.getAuthorities().size();
		if(nFirstSize != nSecondSize) {
			return false;
		}
		boolean isEquals = true;
		for(int i = 0; isEquals && i < nFirstSize; i++) {
			String authority = firstUserVO.getAuthorities().get(i);
			if(authority == null) {
				continue;
			}
			isEquals = false;
			for(int j = 0; j < nSecondSize; j++) {
				String authorityVO = secondUserVO.getAuthorities().get(j);
				if(authority == null && authorityVO == null || authority.equals(authorityVO)) {
					isEquals = true;
					break;
				}
			}
		}
		return isEquals;
	}

}
