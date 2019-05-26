<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="mess/copy.do" modelAttribute="copyForm">
	<form:hidden path="messageId"/>
	
	<jstl:if test="${!(empty destinations)}">
	<acme:select items="${destinations}" itemLabel="name" code="mess.destination" path="destination"/>
	<acme:submit name="save" code="mess.copy"/>
	</jstl:if>	
	<jstl:if test="${empty destinations}">
		<h2><spring:message code="mess.copy.noMessBoxes"/></h2>
	</jstl:if>
	<acme:cancel url="mess/display.do?messageId=${copyForm.messageId}" code="mess.cancel"/>
	
</form:form>