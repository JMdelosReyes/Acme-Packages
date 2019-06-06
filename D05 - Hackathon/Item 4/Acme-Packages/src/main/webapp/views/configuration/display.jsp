<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<table>
	<tr>
		<th><spring:message code="conf.information"/></th>
		<th><spring:message code="conf.value"/></th>
	</tr>
	<tr>
		<th><spring:message code="conf.systemName"/></th>
		<th><jstl:out value="${configuration.systemName}"></jstl:out></th>
	</tr>
	<tr>
		<th><spring:message code="conf.banner"/></th>
		<th><jstl:out value="${configuration.banner}"/></th>
	</tr>
	<tr>
		<th><spring:message code="conf.enWelcomeMessage"/></th>
		<th><jstl:out value="${configuration.englishMessage}"/></th>
	</tr>
	<tr>
		<th><spring:message code="conf.esWelcomeMessage"/></th>
		<th><jstl:out value="${configuration.spanishMessage}"/></th>
	</tr>
	<tr>
		<th><spring:message code="conf.finderResult"/></th>
		<th><jstl:out value="${configuration.finderResults}"/></th>
	</tr>
	<tr>
		<th><spring:message code="conf.finderTime"/></th>
		<th><jstl:out value="${configuration.finderTime}"/></th>
	</tr>
	<tr>
		<th><spring:message code="conf.countryCode"/></th>
		<th><jstl:out value="${configuration.countryCode}"/></th>
	</tr>

	<tr>
		<th><spring:message code="conf.vat"/></th>
		<th><jstl:out value="${configuration.vat}"/></th>
	</tr>
	
	<tr>
		<th><spring:message code="conf.fare"/></th>
		<th><jstl:out value="${configuration.fare}"/></th>
	</tr>

	<tr>
		<th><spring:message code="conf.spamWords"/>
		<th>
			<jstl:forEach var="word" items="${configuration.spamWords}">
				<jstl:out value="${word} "/>
			</jstl:forEach>
		</th>
	</tr>
	
		<tr>
		<th><spring:message code="conf.messPriorities"/></th>
		<th>
			<jstl:forEach var="messpri" items="${configuration.messPriorities}">
				<jstl:out value="${messpri} "/>
			</jstl:forEach>
		</th>
	</tr>
	<tr>
		<th><spring:message code="conf.makes"/>
		<th>
			<jstl:forEach var="make" items="${configuration.makes}">
				<jstl:out value="${make} "/>
			</jstl:forEach>
		</th>
	</tr>	
</table>

<a href="configuration/administrator/edit.do"><spring:message code="conf.edit"/></a>