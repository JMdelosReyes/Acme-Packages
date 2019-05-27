<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<spring:message code="act.name" />
:
<jstl:out value="${da.name}" />
<br>
<spring:message code="act.surname" />
:
<jstl:out value="${da.surname}" />
<br>

<jstl:if test="${! (da.middleName eq null || da.middleName eq '')}">
<spring:message code="act.middlename" />
:
<jstl:out value="${da.middleName}" />
<br>
</jstl:if>


<jstl:if test="${! (da.photo eq null || da.photo eq '')}">
	<spring:message code="act.photo" />: <img src="${da.photo}"
		width="150" height="150">
	<br>
</jstl:if>
<spring:message code="act.email" />
:
<jstl:out value="${da.email}" />
<br>
<jstl:if test="${! (da.phoneNumber eq null || da.phoneNumber eq '')}">
	<spring:message code="act.phoneNumber" />: <jstl:out
		value="${da.phoneNumber}" />
	<br>
</jstl:if>
<jstl:if test="${! (da.address eq null || da.address eq '')}">
	<spring:message code="act.address" />: <jstl:out value="${da.address}" />
	<br>
</jstl:if>

<jstl:if
	test="${! (da.score eq null || da.score eq '')}">
	<spring:message code="act.score" />: <jstl:out
		value="${da.score}" />
	<br>
	
</jstl:if>
<jstl:if test="${!(da.creditCard eq null)}">
	<spring:message code="act.make" />: <jstl:out
		value="${da.creditCard.make}" />
	<br>
	<spring:message code="act.holderName" />: <jstl:out
		value="${da.creditCard.holderName}" />
	<br>
	<spring:message code="act.expirationMonth" />: <jstl:out
		value="${da.creditCard.expirationMonth}" />
	<br>
	<spring:message code="act.expirationYear" />: <jstl:out
		value="${da.creditCard.expirationYear}" />
	<br>
	<spring:message code="act.cvv" />: <jstl:out
		value="${da.creditCard.cvv}" />
	<br>
	<spring:message code="act.number" />: <jstl:out
		value="${da.creditCard.number}" />
	<br>
</jstl:if>

<jstl:if
	test="${! (da.vat eq null || da.vat eq '')}">
	<spring:message code="act.vat" />: <jstl:out
		value="${da.vat}" />
	<br>
	
</jstl:if>

<jstl:if
	test="${! (da.nif eq null || da.nif eq '')}">
	<spring:message code="act.nif" />: <jstl:out
		value="${da.nif }" />
	<br>
	
</jstl:if>


<security:authorize access="hasRole('ADMIN')">
	<spring:message code="act.spammer" />:
	<jstl:if test="${!(da.spammer eq null)}">
	<jstl:out
		value="${da.spammer}" /></jstl:if>
	<jstl:if test="${(da.spammer eq null)}"> <jstl:out
		value="N/A" /></jstl:if>
	
	<br>
</security:authorize>

<jstl:if test="${id eq '0'}">
	<acme:cancel url="actor/edit.do" code="act.edit" />
</jstl:if>
