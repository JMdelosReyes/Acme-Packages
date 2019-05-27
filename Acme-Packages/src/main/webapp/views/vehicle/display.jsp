<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<spring:message code="veh.plate" />
:
<jstl:out value="${vehicle.plate}" />
<br>
<spring:message code="veh.type" />
:
<jstl:out value="${vehicle.type}" />
<br>
<spring:message code="veh.maxVolume" />
:
<jstl:out value="${vehicle.maxVolume}" />
<br>
<spring:message code="veh.maxWeight" />
:
<jstl:out value="${vehicle.maxWeight}" />
<br>
<spring:message code="veh.comment" />
:
<jstl:out value="${vehicle.comment}" />
<br>

<spring:message code="veh.pictures" />
:
<br>
<jstl:forEach items="${vehicle.pictures}" var="picture">
	<img src="${picture}">
</jstl:forEach>
<br />
<br>

<security:authorize access="hasRole('CARRIER')">
	<jstl:choose>
		<jstl:when test="${canBeEditedOrDeleted eq true}">
			<a href="vehicle/carrier/edit.do?id=${vehicle.id}"> <spring:message
					code="veh.edit"></spring:message>
			</a>
		</jstl:when>

		<jstl:otherwise>
			<spring:message code="veh.cannotBeEdited"></spring:message>
		</jstl:otherwise>
	</jstl:choose>

	<jstl:if test="${!empty solicitations}">

		<display:table name="solicitations" id="row" requestURI="${requestURI}"
			pagesize="5" class="displaytag">
	
			<display:column titleKey="sol.moment">
				<jstl:out value="${row.moment}"></jstl:out>
			</display:column>
	
			<display:column titleKey="sol.status">
				<jstl:out value="${row.status}"></jstl:out>
			</display:column>
	
			<display:column titleKey="sol.startDate">
				<jstl:out value="${row.startDate}"></jstl:out>
			</display:column>
	
			<display:column titleKey="sol.endDate">
				<jstl:out value="${row.endDate}"></jstl:out>
			</display:column>
	
			<display:column titleKey="sol.category">
				<jstl:choose>
					<jstl:when test="${es eq true}">
						<jstl:out value="${row.category.spanishName}"></jstl:out>
					</jstl:when>
	
					<jstl:otherwise>
						<jstl:out value="${row.category.englishName}"></jstl:out>
					</jstl:otherwise>
				</jstl:choose>
			</display:column>
			
			<display:column>
				<jstl:if test="${row.status eq 'PENDING'}">				
					<form action="solicitation/carrier/delete.do" method="POST">
						<input type="hidden" value="${row.id}" name="id">

						<acme:delete/>
					</form>
				</jstl:if>
			</display:column>
	
		</display:table>
	
	</jstl:if>

	<br>

	<jstl:choose>
		<jstl:when test="${moreSol eq true}">
			<a href="solicitation/carrier/create.do?vehId=${vehicle.id}"><spring:message code="veh.newSol"/></a>
		</jstl:when>

		<jstl:otherwise>
			<spring:message code="veh.noMoreSol"></spring:message>
		</jstl:otherwise>
	</jstl:choose>
	
</security:authorize>




