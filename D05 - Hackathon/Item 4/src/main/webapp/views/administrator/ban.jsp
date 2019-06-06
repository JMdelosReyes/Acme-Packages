<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="suspicious" requestURI="${requestURI}" id="rowSus"
	class="display" pagesize="5">
	<display:column titleKey="admin.actor.name">
		<jstl:out value="${rowSus.name} ${rowSus.surname} (${rowSus.userAccount.username})"></jstl:out>
	</display:column>
	<display:column titleKey="admin.dashboard.suspicious">
		<jstl:out value="${rowSus.spammer}"></jstl:out>
	</display:column>
	<display:column titleKey="admin.ban">
		<jstl:out value="${rowSus.banned}"></jstl:out>
	</display:column>
	<jstl:if test="${!rowSus.banned}">
		<spring:message code="admin.dashboard.ban" var="status"></spring:message>
	</jstl:if>
	<jstl:if test="${rowSus.banned}">
		<spring:message code="admin.dashboard.unban"/>
	</jstl:if>

	<display:column titleKey="admin.dashboard.ban">
		<form:form action="ban/administrator/save.do" modelAttribute="banForm">
		<input type="hidden" name="idActor" value="${rowSus.id}">
		<jstl:if test="${rowSus.banned}">
			<acme:submit name="ban" code="admin.unban"/>
		</jstl:if>
		<jstl:if test="${!(rowSus.banned)}">
			<acme:submit name="ban" code="admin.ban"/>
		</jstl:if>
		</form:form>
	</display:column>
</display:table>
