# spring-login

Spring Login (Stack Templates).

Suite of demo projects (Spring, Spring Boot, Spring-Security, JSP, Apache Tiles, Thymeleaf, Spring-Jpa, Spring-Data-Jpa, JCache, Ehcache, Infinispan, Hibernate, EclipseLink, MySQL, Logback, Maven, Gradle).

This Suite contains follow demo/template projects:

	- print-tool (Print Tool):
		https://github.com/smansoft/print-tool/
		http://blog.smansoft.com/2019/04/09/java-print-tool/
		this tool is used by below demo/template projects;
	
	- scripts 
		scripts for creating/init/drop of databases/users;

	- sl-jsp-hib-ehc2 (Spring Login)
		Spring Boot
		JSP
		Spring MVC + Spring-Security
		DAO (using Hibernate Session Factory)
		Hibernate
		Ehcache2
	
	- sl-jsp-jpa-hib-ehc2 (Spring Login)
		Spring Boot
		JSP
		Spring MVC + Spring-Security
		DAO (using JPA EntityManager)
		Hibernate
		Ehcache2

	- sl-jsp-djpa-jc-hib-ehc3 (Spring Login)
		Spring Boot
		JSP
		Spring MVC + Spring-Security
		DAO (Spring Data JPA)
		JCache
		Hibernate
		Ehcache3

	- sl-jsp-djpa-jc-hib-ehc3-ssl (Spring Login with HTTPS)
		Spring Boot
		JSP
		Spring MVC + Spring-Security
		DAO (Spring Data JPA)
		JCache
		Hibernate
		Ehcache3
	
	- sl-jsp-djpa-jc-hib-infsp (Spring Login)
		Spring Boot
		JSP
		Spring MVC + Spring-Security
		DAO (Spring Data JPA)
		JCache
		Hibernate
		Infinispan

	- sl-jsp-djpa-eclnk-cache (Spring Login)
		Spring Boot
		JSP + Apache Tiles 3
		Spring MVC + Spring-Security
		DAO (Spring Data JPA)
		EclipseLink
		EclipseLink Cache


These projects can be used as templates for creation of new Java Spring Framework projects.

Projects sl-jsp-djpa-eclnk-cache and sl-jsp-djpa-jc-hib-ehc3-ssl
contain more advanced gui, more roles and more advanced implementation 
of auth (please, see screenshots here: doc/screenshots).

Also sl-jsp-djpa-eclnk-cache and sl-jsp-djpa-jc-hib-ehc3-ssl
create default Root Admin (login: 'root' passw: 'root').
Root Admin cann't be removed, but can be updated (for ex. passw value).

Project sl-jsp-djpa-jc-hib-ehc3-ssl supports HTTPS and contains scripts (in /ssl) for generation
SSL keys and certificates.

You can launch built applications as using 
	java -jar sl-xxx.war
as you can deploy them to your Java Servlet Container.

If you build sl-jsp-djpa-jc-hib-ehc3-ssl with current version of src/main/resources/srv.sl.p12,
you will need to import CA certificate (as trusted, for ex. Trusted Root CA) from current version 
ssl/ssl.ecdsa.ca/out/ca.sl.pem. Also you can extract CA certificate from
src/main/resources/srv.sl.p12.

Project sl-jsp-hib-ehc2 contains some Unit and Integration Tests (Spring Boot Test, JUnit 5, Mockito)
(under development yet).

Please, read this post: http://blog.smansoft.com/2019/04/13/spring-login-stack-templates/
for more info and send your notes and questions to info@smansoft.com.
