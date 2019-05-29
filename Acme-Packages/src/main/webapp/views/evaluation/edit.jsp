<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

	
<form:form action="evaluation/customer/edit.do?offId=${offId}" modelAttribute="evaluation">

	<form:hidden path="id"/>
	
	<acme:textarea code="eva.comment" path="comment"/>
	
	<acme:textarea code="eva.mark" path="mark" />
	
	<acme:submit name="save" code="eva.save"/>
	<acme:cancel url="evaluation/customer/list.do" code="eva.back"/>
	
</form:form>
	
	
