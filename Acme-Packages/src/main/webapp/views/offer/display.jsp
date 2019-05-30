<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<fieldset>
	<legend><spring:message code="of.offer"/></legend>

<spring:message code="of.ticker"/>: <jstl:out value="${offer.ticker}"/><br>
<spring:message code="of.maxDateToRequest"/>: <jstl:out value="${offer.maxDateToRequest}"/><br>
<spring:message code="of.canceled"/>: <jstl:out value="${offer.canceled}"/><br>
<jstl:if test="${offer.finalMode eq true}">
	<a href="fare/list.do?id=<jstl:out value="${offer.id}"/>"><spring:message code="of.fares"/></a><br>
	<security:authorize  access="isAuthenticated()">
		<a href="evaluation/list.do?id=<jstl:out value="${offer.id}"/>"><spring:message code="of.evaluations"/></a>
		<br>
	</security:authorize>
</jstl:if>
<jstl:if test="${owner eq true}">
	<a href="vehicle/carrier,auditor/display.do?id=<jstl:out value="${offer.vehicle.id}"/>"><spring:message code="of.vehicle"/></a><br>
	<a href="request/carrier,customer/list.do?id=<jstl:out value="${offer.id}"/>"><spring:message code="of.requests"/></a><br>
	<spring:message code="of.finalMode"/>: <jstl:out value="${offer.finalMode}"/><br>
	<spring:message code="of.score"/>: <jstl:out value="${offer.score}"/><br>
</jstl:if>

<jstl:if test="${canEvaluate eq true}">
	<a href="evaluation/customer/create.do?offId=<jstl:out value="${offer.id}"/>"><spring:message code="of.newEva"/></a><br>
</jstl:if>

</fieldset>

<br>

<fieldset>
	<legend><spring:message code="of.traverseTowns"/></legend>
	<display:table name="traverseTowns" id="row" requestURI="${requestURI}"
				pagesize="5" class="displaytag">
	
	<display:column titleKey="of.number" sortable="true">
		<jstl:out value="${row.number}"></jstl:out>
	</display:column>
			
	<display:column titleKey="of.estimatedDate" sortable="true">
		<fmt:formatDate value="${row.estimatedDate}" pattern="dd/MM/yy"></fmt:formatDate>
	</display:column>
	
	<display:column titleKey="of.currentTown">
		<jstl:out value="${row.currentTown}"></jstl:out>
	</display:column>
	
	<display:column>
			<a href="town/display.do?id=<jstl:out value="${row.id}"/>"><jstl:out value="${row.town.name}"></jstl:out></a>
	</display:column>
	
		
	<jstl:if test="${owner eq true}">
		
		<display:column>
			<a href="traverseTown/carrier/edit.do?offerId=<jstl:out value="${offer.id}"/>&id=<jstl:out value="${row.id}"/>"><spring:message code = "of.edit"/></a>
		</display:column>
	
	</jstl:if>
	
	   
</display:table>

	<jstl:if test="${owner eq true}">
		<jstl:if test="${offer.finalMode eq false}">
			<a href="traverseTown/carrier/create.do?offerId=<jstl:out value="${offer.id}"/>"><spring:message code = "of.create"/></a>
		</jstl:if>
	</jstl:if>


</fieldset>
	<jstl:if test="${owner eq true}">
			<a href="offer/carrier/edit.do?id=${offer.id}"><spring:message code="of.edit"/></a>
	</jstl:if>
	

