<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="mess/delete.do" modelAttribute="deleteForm">
	<form:hidden path="idMessage"/>
	<acme:select items="${containers}" itemLabel="name" code="mess.container" path="container"/>
	<input type="submit" name="save" 
	value = "<spring:message code="mess.delete"/>" 
	onclick="return confirm('<spring:message code="mess.confirm.delete" />')"/>
	<acme:cancel url="mess/display.do?messageId=${deleteForm.idMessage}" code="mess.cancel"/>
	
</form:form>
