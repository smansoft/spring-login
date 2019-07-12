/**
 * 
 */
package com.smansoft.sl.persistence.tests;

import java.util.List;

import org.junit.jupiter.api.Assertions;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.smansoft.sl.bl.services.api.IUserService;
import com.smansoft.sl.bl.services.vo.UserVO;
import com.smansoft.sl.config.SpringLoginPersistenceConfig;
import com.smansoft.tools.print.api.IPrintTool;
import com.smansoft.tools.print.api.types.PrintSfx;
import com.smansoft.tools.print.impl.PrintTool;

/**
 * @author SMan
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "classpath:application.properties")
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
	@Qualifier("passwordEncoder")
	private BCryptPasswordEncoder passwordEncoder;

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

}
