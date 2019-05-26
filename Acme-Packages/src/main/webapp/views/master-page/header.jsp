<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#" onClick="javascript:relativeRedir('')"><img
		src="${ banner }" alt="banner" width="400" height="auto" alt="Acme-PackagesCo., Inc."/></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<!-- COSAS DE ADMIN -->				
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('CARRIER')">
			<li><a href="vehicle/carrier/list.do"><spring:message
						code="master.page.vehicles" /></a></li>
			<li><a href="curriculum/carrier/list.do"><spring:message
						code="master.page.curriculum" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('AUDITOR')">
			<li><a href="solicitation/auditor/list.do"><spring:message
						code="master.page.solicitations" /></a>					
				<ul>
					<li class="arrow"></li>
					<li>
						<a href="solicitation/auditor/list-assigned.do"><spring:message code="master.page.mySolicitations" /></a>
					</li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv" href="messageBox/list.do"><spring:message
				code="master.page.mess" /></a></li>
				
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
						<li><a href="actor/display.do"><spring:message code="master.page.display" /></a></li>
						
					<security:authorize access="hasRole('ADMIN')">
						<li><a href="dashboard/administrator/list.do"><spring:message
									code="master.page.dashboard" /></a></li>
						<li><a href="configuration/administrator/list.do"><spring:message
									code="master.page.configuration" /></a></li>
						<li><a href="actor/create.do"><spring:message
									code="master.page.createActor" /></a></li>
						<li><a href="ban/administrator/list.do"><spring:message
									code="master.page.suspicious" /></a></li>
					</security:authorize>
					
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
		<li><a href="welcome/terms.do"><spring:message
						code="master.page.terms" /></a></li>
	</ul>
</div>

<div>
	
<a href="?language=en"><img src="https://image.flaticon.com/icons/svg/197/197374.svg" alt="en" width="30" height="30"></a> |
	 <a href="?language=es"><img src="https://image.flaticon.com/icons/svg/197/197593.svg" alt="es" width="30" height="30"></a>
	 </div>



