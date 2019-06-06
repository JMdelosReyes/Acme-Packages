<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="vehicles" id="row" requestURI="${requestURI}"
				pagesize="5" class="displaytag">			
	
	<display:column titleKey="veh.plate">
		<a href="vehicle/carrier,auditor/display.do?id=<jstl:out value="${row.id}"/>"><jstl:out value="${row.plate}"></jstl:out></a>
	</display:column>
	
	<display:column titleKey="veh.type">
		<jstl:out value="${row.type}"></jstl:out>
	</display:column>
	
	<display:column titleKey="veh.maxVolume">
		<jstl:out value="${row.maxVolume}"></jstl:out>
	</display:column>
	
	<display:column titleKey="veh.maxWeight">
		<jstl:out value="${row.maxWeight}"></jstl:out>
	</display:column>
	
</display:table>

<security:authorize access="hasRole('CARRIER')">
	<a href="vehicle/carrier/create.do"><spring:message code="veh.create"/></a>
</security:authorize>
