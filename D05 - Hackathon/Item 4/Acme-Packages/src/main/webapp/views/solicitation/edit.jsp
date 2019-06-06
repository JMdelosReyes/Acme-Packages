<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:choose>
	<jstl:when test="${solicitation.id==0}">
	
		<form:form action="solicitation/carrier/edit.do?vehId=${vehId}" modelAttribute="solicitation">

			<form:hidden path="id"/>
			
			<jstl:choose>
				<jstl:when test="${es eq true}">
					<acme:select items="${categories}" itemLabel="spanishName" code="sol.category" path="category"/>
				</jstl:when>
		
				<jstl:otherwise>
					<acme:select items="${categories}" itemLabel="englishName" code="sol.category" path="category"/>
				</jstl:otherwise>
			</jstl:choose>
			
			<acme:textarea code="sol.comments" path="comments"/>
			
			<acme:submit name="save" code="sol.save"/>
			<acme:cancel url="vehicle/carrier,auditor/display.do?id=${vehId}" code="sol.back"/>
			
		</form:form>
	
	</jstl:when>
	
	<jstl:otherwise>
	
		<form:form action="solicitation/auditor/edit.do?vehId=${vehId}" modelAttribute="solicitation">

			<form:hidden path="id"/>
			
			<acme:selectString items="${decision}" code="sol.status" path="status"/>
			
			<acme:textbox code="sol.startDate" path="startDate" placeholder="dd/mm/yyyy"/>
			
			<acme:textbox code="sol.endDate" path="endDate" placeholder="dd/mm/yyyy"/>
			
			<acme:submit name="save" code="sol.save"/>
			<acme:cancel url="vehicle/carrier,auditor/display.do?id=${vehId}" code="sol.back"/>
			
		</form:form>
	
	
	</jstl:otherwise>



</jstl:choose>