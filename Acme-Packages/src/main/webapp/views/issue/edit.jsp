<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

	
<form:form action="issue/customer/edit.do?reqId=${reqId}" modelAttribute="issue">

	<form:hidden path="id"/>
	
	<acme:textarea code="iss.description" path="description"/>
	
	<acme:submit name="save" code="iss.save"/>
	<acme:cancel url="issue/customer/list.do" code="iss.back"/>
	
</form:form>
	
	
