/**
 * 
 */
package com.smansoft.sl.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.smansoft.sl.bl.services.api.IUserService;
import com.smansoft.sl.bl.services.vo.RegisterVO;
import com.smansoft.sl.bl.services.vo.UserVO;
import com.smansoft.tools.print.api.IPrintTool;
import com.smansoft.tools.print.api.types.PrintSfx;
import com.smansoft.tools.print.impl.PrintTool;

/**
 * @author SMan
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "classpath:application.properties")
public class TestInstallData {

	private static final IPrintTool printTool = PrintTool.getPrintToolInstance(LoggerFactory.getLogger(TestInstallData.class));

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

/*		

		List<String> resUserRoles = new ArrayList<>();
		resUserRoles.add("ROLE_USER");
		resUserRoles.add("ROLE_USER_EXT_1");
		resUserRoles.add("ROLE_USER_EXT_2");
		resUserRoles.add("ROLE_USER_EXT_3");

		for(int i = 0; i < 350; i++) {
			RegisterVO registerVO = new RegisterVO();
			registerVO.setLogin(String.format("demo_login_%d", i));
			registerVO.setEmail(String.format("demo_login_%d@some.empty.email.com", i));
			registerVO.setName(String.format("SMan_%d", i));
			registerVO.setAddress(String.format("Some Address SMan %d", i));
			if((i % 2) == 0) {
				registerVO.setSex("Male");
			}
			else {
				registerVO.setSex("Female");
			}
			String encodedPassword = passwordEncoder.encode("12345");
			registerVO.setPassword(encodedPassword);
			registerVO.setPasswordConfirm(encodedPassword);
			registerVO.setEnabled(true);
			registerVO.setUserRoles(resUserRoles);
			userServiceBean.createUser(registerVO);
		}

		userVOs = userServiceBean.getAllUsers();
		for(UserVO userVO : userVOs) {
			printTool.debug(userVO);
		}
*/

		Assert.assertTrue(true);
		printTool.debug(PrintSfx.SFX_OUT);
	}
	
	/**
	 * 
	 */
	@Test
	public void testData() {
		printTool.debug(PrintSfx.SFX_IN);
		Assert.assertTrue(true);
		printTool.debug(PrintSfx.SFX_OUT);
	}

}
