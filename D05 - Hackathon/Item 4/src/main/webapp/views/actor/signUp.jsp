<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="scripts/signUp.js"></script>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form name="signUp" action="actor/sign-up.do" modelAttribute="signUpForm">

	<fieldset>

		<acme:textbox code="act.name" path="name" />
		<acme:textbox code="act.surname" path="surname" />
		<acme:textbox code="act.middlename" path="middleName" />
		<acme:textbox code="act.photo" path="photo" placeholder="http://www.example.com"/>
		<acme:textbox code="act.email" path="email" placeholder="example@example.com"/>
		<acme:textbox code="act.phoneNumber" path="phoneNumber" placeholder="689513247"/>
		<acme:textbox code="act.address" path="address" />


		<security:authorize access="isAnonymous()">

			<div id='hiddenDiv'>
				<acme:textbox code="act.vat" path="vat" placeholder="DE999999999" />
			</div>
			
			<div id='hiddenDiv2'>
				<acme:textbox code="act.nif" path="nif" />
			</div>

		</security:authorize>


	</fieldset>

	<fieldset>
		<acme:selectString items="${makes}" code="act.make" path="creditCard.make"/>
		<acme:textbox code="act.holderName" path="creditCard.holderName" />
		<acme:textbox code="act.expirationMonth"
			path="creditCard.expirationMonth" />
		<acme:textbox code="act.expirationYear"
			path="creditCard.expirationYear" />
		<acme:textbox code="act.cvv" path="creditCard.cvv" placeholder="718"/>
		<acme:textbox code="act.number" path="creditCard.number" placeholder="4981304263741333"/>

	</fieldset>
	<fieldset>
		<acme:textbox code="act.username" path="userAccount.username" />
		<acme:password code="act.password" path="userAccount.password" />
		<acme:password code="act.passConfirmation" path="passConfirmation" />
	</fieldset>

	<acme:selectAcceptReject id="termsAccepted" code="act.termsAccepted"
		path="termsAccepted" />


	<security:authorize access="isAnonymous()">
		<form:radiobutton name="actorType" path="actorType" value="carrier"
			onclick="java_script_:show(this.value)"/>
		<spring:message code="act.carrier" />
		<br />
		<form:radiobutton name="actorType" path="actorType" value="customer"
			onclick="java_script_:show(this.value)"/>
		<spring:message code="act.customer" />
		<br />
		<form:radiobutton name="actorType" path="actorType" value="sponsor"
			onclick="java_script_:show(this.value)"/>		
			<spring:message code="act.sponsor" />
		<br />
	</security:authorize>
	<security:authorize access="hasRole('ADMIN')">
	<form:radiobutton name="actorType" path="actorType" value="administrator"
			onclick="java_script_:show(this.value)"/>
		<spring:message code="act.administrator" />
		<br />
		<form:radiobutton name="actorType" path="actorType" value="auditor"
			onclick="java_script_:show(this.value)"/>		
			<spring:message code="act.auditor" />
		<br />
		
	</security:authorize>
	

	<input type="submit" name="save"
		onclick="if(validatePhone('phoneNumber')){return confirm('<spring:message code="act.confirmPhone" />')}"
		value="<spring:message code="act.save"/>">
	<acme:cancel url="/" code="act.cancel" />

</form:form>