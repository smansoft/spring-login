/**
 * 
 */
package com.smansoft.sl.config;

import javax.servlet.annotation.WebListener;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

/**
 * @author SMan
 *
 */
@Configuration
@WebListener
public class SpringLoginContextListener extends RequestContextListener {

}
