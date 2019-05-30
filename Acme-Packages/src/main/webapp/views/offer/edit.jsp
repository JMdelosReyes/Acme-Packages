<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="offer/carrier/edit.do" modelAttribute="offerForm">

	<form:hidden path="id"/>
	
	<acme:textbox code="of.maxDateToRequest" path="maxDateToRequest" placeholder="dd/MM/yyyy" />
	<acme:select items="${vehicles}" itemLabel="plate" code="of.vehicle" path="vehicle"/>
	<acme:selectMultiple items="${fares}" itemLabel="price" code="of.fares" path="fares"/>
	<jstl:if test="${offerForm.id!=0 }">
		<acme:selectBoolean code="of.finalMode" path="finalMode" id="finalMode"/>
		<acme:selectBoolean code="of.canceled" path="canceled" id="canceled"/>
	</jstl:if>
	
	
	<acme:submit name="save" code="of.save"/>
	
	<jstl:if test="${offerForm.id!=0}" var="hasId">
		<acme:cancel url="offer/display.do?id=${offerForm.id}" code="of.cancel"/>
		<acme:delete/>
		</jstl:if>
	<jstl:if test="${!hasId}">
		<acme:cancel url="offer/carrier/list.do" code="of.cancel"/>
	</jstl:if>
	
</form:form>