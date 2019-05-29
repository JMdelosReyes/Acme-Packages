<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="fare/carrier/edit.do" modelAttribute="fare">

	<form:hidden path="id"/>
	
	<acme:textbox code="fa.maxWeight" path="maxWeight" type="number"/>
	<acme:textbox code="fa.maxVolume" path="maxVolume" type="number"/>
	<acme:textbox code="fa.price" path="price" type="number"/>
	
	<acme:submit name="save" code="fa.save"/>
	
	<jstl:if test="${fa.id!=0}">
		<acme:delete/>
	</jstl:if>
	
	<acme:cancel url="fare/carrier/list.do" code="fa.cancel"/>
	
	
</form:form>