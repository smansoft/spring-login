/**
 * 
 */
package com.smansoft.sl.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.smansoft.sl.web.controllers.BaseController;

/**
 * @author SMan
 *
 */
@Component("errorPageRegistrar")
public class SpringLoginErrorPageRegistrar implements ErrorPageRegistrar {

	/**
	 * 
	 */
	@Override
	public void registerErrorPages(ErrorPageRegistry registry) {
		registry.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, 	BaseController.DEF_ERROR_HTM));
		registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, 				BaseController.DEF_ERROR_HTM));
		registry.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, 			BaseController.DEF_ERROR_HTM));
		registry.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, 			BaseController.DEF_ERROR_HTM));
		registry.addErrorPages(new ErrorPage(Throwable.class, 					BaseController.DEF_ERROR_HTM));
		registry.addErrorPages(new ErrorPage(									BaseController.DEF_ERROR_HTM));
	}

}
