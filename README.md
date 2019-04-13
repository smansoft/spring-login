# spring-login

Spring Login (Stack Templates).

Suite of demo projects (Spring, Spring Boot, Spring-Security, JSP, Thymeleaf, Spring-Jpa, Spring-Data-Jpa, JCache, Ehcache, Infinispan, Hibernate, EclipseLink, MySQL, Logback, Maven, Gradle).

This Suite contains follow demo/template projects:

	- print-tool (Print Tool):
		https://github.com/smansoft/print-tool
		http://blog.smansoft.com/2019/04/09/java-print-tool/
		This tool is used by bellow examples/templates;
	
	- scripts 
		scripts for creating/init/drop of database

	- sl-jsp-hib-ehc2 (Spring Login)
		JSP
		Spring MVC + Spring-Security
		DAO (using Hibernate Session Factory)
		Hibernate
		Ehcache2
	
	- sl-jsp-jpa-hib-ehc2 (Spring Login)
		JSP
		Spring MVC + Spring-Security
		DAO (using JPA EntityManager)
		Hibernate
		Ehcache2

	- sl-jsp-djpa-jc-hib-ehc3 (Spring Login)
		JSP
		Spring MVC + Spring-Security
		DAO (Spring Data JPA)
		JCache
		Hibernate
		Ehcache3
	
	- sl-jsp-djpa-jc-hib-infsp (Spring Login)
		JSP
		Spring MVC + Spring-Security
		DAO (Spring Data JPA)
		JCache
		Hibernate
		Infinispan

	- sl-jsp-djpa-eclnk-cache (Spring Login)
		JSP
		Spring MVC + Spring-Security
		DAO (Spring Data JPA)
		EclipseLink
		EclipseLink Cache


These projects can be used as templates for creation of new Web Spring projects.

Project sl-jsp-djpa-eclnk-cache contains more advanced gui and more roles 
(please, see screenshots here: doc/screenshots).
Also sl-jsp-djpa-eclnk-cache creates default Root Admin (login: 'root' passw: 'root').

Please, read this post: http://blog.smansoft.com/2019/04/13/spring-login-stack-templates/
for more info and send your notes and questions to info@smansoft.com.
