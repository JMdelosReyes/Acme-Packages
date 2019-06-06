<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
	
<display:table name="issues" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag">

	<display:column titleKey="iss.ticker">
		<a href="issue/carrier,customer,auditor/display.do?id=${row.id}">
			${row.ticker}
		</a>
	</display:column>

	<display:column titleKey="iss.moment">
		<jstl:out value="${row.moment}"></jstl:out>
	</display:column>

	<display:column titleKey="iss.status">
		<jstl:choose>
			<jstl:when test="${row.closed eq true}">
				<spring:message code="iss.closed"></spring:message>
			</jstl:when>
			<jstl:otherwise>
				<spring:message code="iss.open"></spring:message>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	<display:column titleKey="iss.description">
		<jstl:out value="${row.description}"></jstl:out>
	</display:column>

	<display:column titleKey="iss.offer">
		<a href="offer/display.do?id=${row.offer.id}">
			<spring:message code="iss.offer"></spring:message>
		</a>
	</display:column>
	
	<display:column titleKey="iss.request">
		<a href="request/carrier,customer,auditor/display.do?id=${row.id}">
			<spring:message code="iss.request"></spring:message>
		</a>
	</display:column>
	
	<security:authorize access="hasRole('AUDITOR')">
		<jstl:if test="${assignable eq true}">
			<display:column titleKey="iss.assign">
				<a href="issue/auditor/assign.do?id=${row.id}">
					<spring:message code="iss.assign"></spring:message>
				</a>
			</display:column>
		</jstl:if>
	</security:authorize>

</display:table>

