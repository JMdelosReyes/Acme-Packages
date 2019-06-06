<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

	
<form:form action="town/administrator/edit.do" modelAttribute="town">

	<form:hidden path="id"/>
	
	<form:hidden path="version"/>
	
	<acme:textbox code="town.name" path="name"/>
	
	<acme:textbox code="town.zipCode" path="zipCode"/>
	
	<acme:textbox code="town.county" path="county"/>
		
	<acme:submit name="save" code="town.save"/>
	
	<jstl:if test="${town.id!=0}">
		<jstl:if test="${canBeDeleted eq true}">
			<acme:delete/>
		</jstl:if>
	</jstl:if>
	
	<acme:cancel url="town/administrator/list.do" code="town.back"/>
	
</form:form>
	
	
