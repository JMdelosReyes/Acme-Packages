<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="vehicle/carrier/edit.do" modelAttribute="vehicle">

	<form:hidden path="id"/>
	
	<acme:textbox code="pos.title" path="title"/>
	<acme:textbox code="pos.description" path="description"/>
	
	<br>
	
	<acme:submit name="save" code="pos.save"/>
	<acme:cancel url="position/company/list.do" code="pos.back"/>
	
	<jstl:if test="${position.id!=0}" var="hasId">
		<acme:delete/>
	</jstl:if>
	
</form:form>