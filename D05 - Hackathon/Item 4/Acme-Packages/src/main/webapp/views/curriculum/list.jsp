<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="curricula" id="row" requestURI="${requestURI}"
				pagesize="5" class="displaytag">			
	
	<display:column titleKey="curr.fullName">
		<a href="curriculum/display.do?id=<jstl:out value="${row.id}"/>"><jstl:out value="${row.fullName}"></jstl:out></a>
	</display:column>
	
	<display:column titleKey="curr.email">
		<jstl:out value="${row.email}"></jstl:out>
	</display:column>
	
	<display:column titleKey="curr.phoneNumber">
		<jstl:out value="${row.phoneNumber}"></jstl:out>
	</display:column>
	
	<display:column titleKey="curr.photo">
		<jstl:out value="${row.photo}"></jstl:out>
	</display:column>
	
	
</display:table>

<a href="curriculum/carrier/create.do"><spring:message code="curr.create"/></a>
