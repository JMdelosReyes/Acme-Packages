<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
	
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
	
	<jstl:if test="${assignedView eq false}">
		<display:column titleKey="sol.assign">
			<jstl:if test="${row.status eq 'PENDING'}">
				<a href="solicitation/auditor/assign.do?id=${row.id}">
					<spring:message code="sol.assign"></spring:message>
				</a>
			</jstl:if>
		</display:column>
	</jstl:if>
	
	<jstl:if test="${assignedView eq true}">
		<display:column titleKey="sol.vehicle">
			<a href="vehicle/carrier,auditor/display.do?id=${row.id}&sol=yes">
				<spring:message code="sol.vehicle"></spring:message>
			</a>
		</display:column>
	</jstl:if>
	
	<jstl:if test="${assignedView eq true}">
		<display:column titleKey="sol.edit">
			<jstl:if test="${row.status eq 'PENDING'}">
				<a href="solicitation/auditor/edit.do?id=${row.id}">
					<spring:message code="sol.edit"></spring:message>
				</a>
			</jstl:if>
		</display:column>
	</jstl:if>

</display:table>

