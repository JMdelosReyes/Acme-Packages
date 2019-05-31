<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<h3><spring:message code="req.request"/></h3>
<spring:message code="req.ticker"/>: <jstl:out value="${request.ticker}"/><br/>
<spring:message code="req.moment"/>: <jstl:out value="${request.moment}"/><br/>
<spring:message code="req.description"/>: <jstl:out value="${request.description}"/><br/>
<spring:message code="req.maxPrice"/>: <jstl:out value="${request.maxPrice}"/><br/>
<spring:message code="req.deadline"/>: <jstl:out value="${request.deadline}"/><br/>
<spring:message code="req.finalMode"/>: <jstl:out value="${request.finalMode}"/><br/>
<spring:message code="req.volume"/>: <jstl:out value="${request.volume}"/> cm3<br/>
<spring:message code="req.weight"/>: <jstl:out value="${request.weight}"/> kg<br/>
<spring:message code="req.status"/>: <jstl:out value="${request.status}"/><br/>
<spring:message code="req.town"/>: <jstl:out value="${request.town.name} (${request.town.county}) ${request.town.zipCode}"/><br/>
<spring:message code="req.streetAddress"/>: <jstl:out value="${request.streetAddress}"/><br/>
<jstl:if test="${request.offer ne null}">
	<spring:message code="req.offer"/>: <a href="offer/display.do?id=${request.offer.id}"><spring:message code="req.offer"/></a><br/>
</jstl:if>
<jstl:if test="${request.issue ne null}">
	<spring:message code="req.issue"/>: <a href="/issue/carrier,customer,auditor/display.do?id=${request.issue.id}"><spring:message code="req.issue"/></a><br/>
</jstl:if>
	<security:authorize access="hasRole('CUSTOMER')">
	<jstl:if test="${request.finalMode && request.status eq null && owner}">
		<jstl:if test="${!(empty offers)}">
		<fieldset>
		<h3><spring:message code="req.apply"/></h3>
		<form:form action="request/carrier,customer,auditor/display.do" modelAttribute="chooseOfferForm">
		<form:hidden path="id"/>
		<acme:select items="${offers}" itemLabel="ticker" code="req.offersAvailables" path="idOffer"/>
		<acme:submit name="apply" code="req.apply"/>
		</form:form>
		</fieldset>
		</jstl:if>
		<jstl:if test="${empty offers}">
		<h3><spring:message code="req.noOffers"/></h3>
		</jstl:if>
	</jstl:if>
	
</security:authorize>
<fieldset>
<legend><strong><spring:message code="req.package"/></strong>:</legend>
<display:table name="packages" id="row" requestURI="${requestURI}"
				pagesize="5" class="displaytag">
	<display:column titleKey="pac.weight">
		<jstl:out value="${row.weight}"/> kg
	</display:column>
	<display:column titleKey="req.height">
		<jstl:out value="${row.height}"/> cm
	</display:column>
	<display:column titleKey="req.width">
		<jstl:out value="${row.width}"/> cm
	</display:column>
	<display:column titleKey="req.length">
		<jstl:out value="${row.length}"/> cm
	</display:column>
	<display:column titleKey="req.details">
		<jstl:out value="${row.details}"/>
	</display:column>
	<display:column titleKey="req.package.price">
		<jstl:if test="${request.offer ne null}">
			<jstl:out value="${row.price}"/> euro
		</jstl:if>
	</display:column>
	<display:column titleKey="req.category">
	<jstl:if test="${es}">
		<jstl:forEach  var="cat" items="${row.categories}">
			<jstl:out value="${cat.spanishName}"/>
		</jstl:forEach>
	</jstl:if>
	<jstl:if test="${!es}">
		<jstl:forEach  var="cat" items="${row.categories}">
				<jstl:out value="${cat.englishName}"/>
			</jstl:forEach>
		</jstl:if>
	</display:column>
	<display:column titleKey="req.edit">
		<jstl:if test="${owner && !request.finalMode}">
			<a href="package/customer/edit.do?id=${row.id}"><spring:message code="req.edit"/></a>
		</jstl:if>
	</display:column>
</display:table>
	<jstl:if test="${owner && !request.finalMode}">
		<form:form action="package/customer/create.do">
			<input type="hidden" name="reqId" value="${request.id}">
			<acme:submit name="add" code="req.addPackage"/>
		</form:form>
	</jstl:if>
</fieldset>


<acme:cancel url="request/carrier,customer/list.do" code="req.cancel"/>

