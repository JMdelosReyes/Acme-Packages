<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
	
<display:table name="towns" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag">

	<display:column titleKey="town.name">
		<jstl:out value="${row.name}"></jstl:out>
	</display:column>

	<display:column titleKey="town.zipCode">
		<jstl:out value="${row.zipCode}"></jstl:out>
	</display:column>
	
	<display:column titleKey="town.county">
		<jstl:out value="${row.county}"></jstl:out>
	</display:column>
	
	<display:column>
		<a href="town/administrator/edit.do?id=<jstl:out value="${row.id}"/>"><spring:message code = "town.edit"/></a>
	</display:column>
	
</display:table>

<a href="town/administrator/create.do"><spring:message code = "town.create"/></a>
