<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="traverseTown/carrier/edit.do?offerId=${offerId}" modelAttribute="traverseTown">

	<form:hidden path="id"/>
	<form:hidden path="number"/>

		
	<acme:textbox code="tt.estimatedDate" path="estimatedDate" placeholder="dd/mm/yyyy"/>
	<acme:select items="${towns}" itemLabel="name" code="tt.town" path="town"/>
	<acme:selectBoolean code="tt.currentTown" path="currentTown" id="currentTown"/>
	
	<acme:submit name="save" code="tt.save"/>
	
	<jstl:if test="${traverseTown.id!=0 and !finalMode}">
		<acme:delete/>
	</jstl:if>
	
	<acme:cancel url="offer/display.do?id=${offerId}" code="tt.cancel"/>
	
	
</form:form>