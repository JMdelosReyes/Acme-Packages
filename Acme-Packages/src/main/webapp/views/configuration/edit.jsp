<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="configuration/administrator/edit.do" modelAttribute="configuration">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<fieldset>
	<acme:textbox code="conf.systemName" path="systemName"/>
	<acme:textbox type="number" code="conf.finderResult" path="finderResults" placeholder="Must be between 10 and 100 results"/>
	<acme:textbox type="number" code="conf.finderTime" path="finderTime" placeholder="Must be between 1 and 24 hours"/>
	<acme:textbox code="conf.countryCode" path="countryCode"/>
	<acme:textarea code="conf.banner" path="banner"/>
	<acme:textarea code="conf.enWelcomeMessage" path="englishMessage"/>
	<acme:textarea code="conf.esWelcomeMessage" path="spanishMessage"/>
	<acme:textbox code="conf.vat" path="vat"/>
	<acme:textbox code="conf.fare" path="fare"/>
	
	
	
	<acme:textarea code="conf.spamWords" path="spamWords"/>
	<acme:textarea code="conf.messPriorities" path="messPriorities"/>
	<acme:textarea code="conf.makes" path="makes" placeholder="Enter the words separated by commas.."/>
	
	</fieldset>
	<acme:submit name="save" code="conf.save"/>
	<acme:cancel url="configuration/administrator/list.do" code="conf.cancel"/>
</form:form>