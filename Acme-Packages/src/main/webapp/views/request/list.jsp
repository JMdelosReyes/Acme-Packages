<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<security:authorize access="hasRole('CUSTOMER')">
 <a href="request/customer/create.do"><spring:message code="req.create"/></a>
</security:authorize>
<br/>
<display:table name="requests" id="row" requestURI="${requestURI}"
				pagesize="5" class="displaytag">
	
	<display:column titleKey="req.ticker">
		<a href="request/carrier,customer,auditor/display.do?id=${row.id}"><jstl:out value="${row.ticker}"/></a>
	</display:column>
	<display:column titleKey="req.moment">
		<jstl:out value="${row.moment}"/>
	</display:column>
	<display:column titleKey="req.description">
		<jstl:out value="${row.description}"/>
	</display:column>
	<display:column titleKey="req.maxPrice">
		<jstl:out value="${row.maxPrice}"/>
	</display:column>
	<display:column titleKey="req.volume">
		<jstl:out value="${row.volume}"/>
	</display:column>
	<display:column titleKey="req.weight">
		<jstl:out value="${row.weight}"/>
	</display:column>
	<display:column titleKey="req.status">
		<jstl:out value="${row.status}"/>
	</display:column>
	<display:column titleKey="req.issue">
		<security:authorize access="hasRole('CUSTOMER')">
			<jstl:choose>
				<jstl:when test="${row.issue.ticker eq null}">
					<jstl:if test="${row.offer ne null}">
						<a href="issue/customer/create.do?reqId=${row.id}"><spring:message code="req.issue.create"/></a>
					</jstl:if>
				</jstl:when>
				<jstl:otherwise>
					<a href="issue/carrier,customer,auditor/display.do?id=${row.id}"><jstl:out value="${row.issue.ticker}"/></a>
				</jstl:otherwise>
			</jstl:choose>
		</security:authorize>
		<security:authorize access="hasRole('CARRIER')">
			<jstl:if test="${row.issue ne null}">
				<a href="issue/carrier,customer,auditor/display.do?id=${row.id}"><jstl:out value="${row.issue.ticker}"/></a>
			</jstl:if>
		</security:authorize>
	</display:column>
	<security:authorize  access="hasRole('CUSTOMER')">
		<display:column title="req.edit">
			<jstl:if test="${!row.finalMode}">
				<a href="request/customer/edit.do?id=${row.id}"><spring:message code="req.edit"/></a>
			</jstl:if>
		</display:column>
	</security:authorize>
</display:table>

