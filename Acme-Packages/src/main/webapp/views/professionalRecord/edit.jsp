<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="professionalRecord/carrier/edit.do?curId=${param.curId}" modelAttribute="professionalRecord">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<acme:textbox code="pRecord.company" path="companyName"/>
	<acme:textbox code="pRecord.attachment" path="attachment"/>
	<acme:textbox code="pRecord.startTime" path="startTime" placeholder="dd/mm/yyyy"/>
	<acme:textbox code="pRecord.endTime" path="endTime" placeholder="dd/mm/yyyy"/>
	<acme:textbox code="pRecord.comments" path="comments"/>
	
	<acme:submit name="save" code="pRecord.save"/>
	
	<jstl:if test="${professionalRecord.id==0}" var="idZero">
		<acme:cancel url="/curriculum/display.do?id=${param.curId}" code="pRecord.cancel"/>
	</jstl:if>
	
	<jstl:if test="${!idZero}">
		<acme:cancel url="/curriculum/display.do?id=${curId}" code="pRecord.cancel"/>
	</jstl:if>
	
	<jstl:if test="${!idZero}">
		<acme:delete/>	
	</jstl:if>
	
</form:form>
