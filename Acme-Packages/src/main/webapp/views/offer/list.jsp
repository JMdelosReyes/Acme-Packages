<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<display:table name="offers" id="row" requestURI="${requestURI}"
				pagesize="5" class="displaytag">
	
	<display:column titleKey="of.ticker">
		<a href="offer/display.do?id=<jstl:out value="${row.id}"/>"><jstl:out value="${row.ticker}"></jstl:out></a>
	</display:column>
	
	<display:column titleKey="of.maxDateToRequest">
		<fmt:formatDate value="${row.maxDateToRequest}" pattern="dd/MM/yy HH:mm"></fmt:formatDate>
	</display:column>
	
	<jstl:if test="${requestURI eq 'offer/carrier/list.do'}">
	
	<display:column titleKey="of.totalPrice">
		<jstl:out value="${row.totalPrice}"></jstl:out>
	</display:column>
	
	</jstl:if>
		
	<display:column titleKey="of.fares">
		<a href="offer/display.do?id=<jstl:out value="${row.id}"/>"><spring:message code = "of.fares"/></a>
	</display:column>
		
	<display:column titleKey="of.evaluations">
		<a href="evaluations/display.do?id=<jstl:out value="${row.id}"/>"><spring:message code = "of.evaluations"/></a>
	</display:column>
	
	<jstl:if test="${requestURI eq 'offer/carrier/list.do'}">
	
	<display:column titleKey="of.vehicle">
		<a href="vehicle/display.do?id=<jstl:out value="${row.id}"/>"><spring:message code = "of.vehicle"/></a>
	</display:column>
	
	
	<display:column titleKey="of.requests">
		<a href="requests/display.do?id=<jstl:out value="${row.id}"/>"><spring:message code = "of.requests"/></a>
	</display:column>
	
	</jstl:if>
	
	   
</display:table>

<a href="offer/carrier/create.do"><spring:message code="curr.create"/></a>
