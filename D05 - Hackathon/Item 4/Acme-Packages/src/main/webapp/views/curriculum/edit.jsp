<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="curriculum/carrier/edit.do" modelAttribute="curriculum">

	<form:hidden path="id"/>
	
	<acme:textbox code="curr.fullName" path="fullName"/>
	<acme:textbox code="curr.email" path="email"/>
	<acme:textbox code="curr.phoneNumber" path="phoneNumber"/>
	<acme:textbox code="curr.photo" path="photo"/>
	
	<acme:submit name="save" code="curr.save"/>
	
	<jstl:if test="${curriculum.id!=0}" var="hasId">
		<acme:cancel url="curriculum/display.do?id=${curriculum.id}" code="curr.cancel"/>
		<acme:delete/>
		</jstl:if>
	<jstl:if test="${!hasId}">
		<acme:cancel url="curriculum/carrier/list.do" code="curr.cancel"/>
	</jstl:if>
	
</form:form>