/**
 * 
 */
package com.smansoft.sl.config;

import javax.servlet.annotation.ServletSecurity.TransportGuarantee;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author SMan
 *
 */
@Configuration
public class SpringLoginConnectorConfig {

	@Value("${server.port.http}")
	private int serverPortHttp;

	@Value("${server.port}")
	private int serverPortHttps;

	/**
	 * 
	 * @return
	 */
	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
			/**
			 * 
			 */
			@Override
			protected void postProcessContext(Context context) {
				((StandardJarScanner)context.getJarScanner()).setScanManifest(false);
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint(TransportGuarantee.CONFIDENTIAL.toString());
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint);
			}
		};
		tomcat.addAdditionalTomcatConnectors(redirectConnectorHttp());
		return tomcat;
	}

	/**
	 * 
	 * @return
	 */
	private Connector redirectConnectorHttp() {
		Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
		connector.setScheme("http");
		connector.setPort(serverPortHttp);
		connector.setSecure(false);
		connector.setRedirectPort(serverPortHttps);
		return connector;
	}
	
}
