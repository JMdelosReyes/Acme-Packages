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
	<spring:message code="req.offer"/>: <a href="offer/display.do?=${request.offer.id}"><spring:message code="req.offer"/></a><br/>
</jstl:if>
<jstl:if test="${request.issue ne null}">
	<spring:message code="req.issue"/>: <a href="/issue/carrier,customer,auditor/display.do?id=${request.issue.id}"><spring:message code="req.offer"/></a><br/>
</jstl:if>

<fieldset>
	<jstl:forEach var="pac" items="${packages}">
		<fieldset>
			<legend><strong><spring:message code="req.package"/></strong>:</legend>
			<spring:message code="req.weight"/>: <jstl:out value="${pac.weight}"/> kg<br/>
			<spring:message code="req.height"/>: <jstl:out value="${pac.height}"/> cm<br/>
			<spring:message code="req.width"/>: <jstl:out value="${pac.width}"/> cm<br/>
			<spring:message code="req.length"/>: <jstl:out value="${pac.length}"/> cm<br/>
			<spring:message code="req.description"/>: <jstl:out value="${pac.description}"/><br/>
			<jstl:if test="${request.offer ne null}">
				<spring:message code="req.package.price"/>: <jstl:out value="${pac.price}"/><br/>
			</jstl:if>
			<jstl:if test="${es}">
				<jstl:forEach  var="cat" items="${pac.categories}">
					<fieldset>
					<legend><strong><spring:message code="req.category"/></strong></legend>
					<strong><spring:message code="req.category.spanishName"/>: <jstl:out value="${cat.spanishName}"/></strong><br/>
					<spring:message code="req.category.spanishDescription"/>: <jstl:out value="${cat.spanishDescription}"/><br/>
					</fieldset>
				</jstl:forEach>
			</jstl:if>
			<jstl:if test="${!es}">
				<jstl:forEach  var="cat" items="${pac.categories}">
					<fieldset>
							<legend><strong><spring:message code="req.category"/></strong></legend>
							<strong><spring:message code="req.category.englishName"/>: <jstl:out value="${cat.englishName}"/></strong><br/>
							<spring:message code="req.category.englishDescription"/>: <jstl:out value="${cat.englishDescription}"/><br/>
					</fieldset>
				</jstl:forEach>
			</jstl:if>
		</fieldset>
	</jstl:forEach>
</fieldset>
<br/>
