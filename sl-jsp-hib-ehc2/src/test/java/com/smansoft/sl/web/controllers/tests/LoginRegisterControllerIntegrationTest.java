/**
 * 
 */
package com.smansoft.sl.web.controllers.tests;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.smansoft.sl.web.controllers.LoginRegisterController;
import com.smansoft.tools.print.api.IPrintTool;
import com.smansoft.tools.print.api.types.PrintSfx;
import com.smansoft.tools.print.impl.PrintTool;

/**
 * @author SMan
 *
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = { LoginRegisterController.class })
@TestPropertySource(locations="classpath:application-test.properties")
@Execution(ExecutionMode.SAME_THREAD)
@ComponentScan(basePackages="com.smansoft.sl")
@EnableAutoConfiguration(exclude=HibernateJpaAutoConfiguration.class)
@TestMethodOrder(OrderAnnotation.class)
public class LoginRegisterControllerIntegrationTest {
	
	/**
	 * 
	 */
	private static final IPrintTool printTool = PrintTool
			.getPrintToolInstance(LoggerFactory.getLogger(LoginRegisterControllerIntegrationTest.class));
	
	/**
	 * 
	 */
    @Autowired
    private MockMvc mockMvc;

    /**
     * 
     * @throws Exception
     */
    @Test
	@Order(1)
    public void whenContextRootUrlIsAccessed_thenStatusIsOk() throws Exception {
		printTool.debug(PrintSfx.SFX_IN);
    	ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.get("/"));
    	ra.andExpect(MockMvcResultMatchers.status().is(200));
		printTool.debug(PrintSfx.SFX_OUT);
		Assertions.assertTrue(true);			
    }

}
