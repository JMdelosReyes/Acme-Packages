<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="miscellaneousRecord/carrier/edit.do?curId=${param.curId}" modelAttribute="miscellaneousRecord">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<acme:textbox code="mRecord.title" path="title"/>
	<acme:textbox code="mRecord.attachment" path="attachment"/>
	<acme:textbox code="mRecord.comments" path="comments"/>
	
	<acme:submit name="save" code="mRecord.save"/>
	
	<jstl:if test="${miscellaneousRecord.id==0}" var="idZero">
		<acme:cancel url="/curriculum/display.do?id=${param.curId}" code="mRecord.cancel"/>
	</jstl:if>
	
	<jstl:if test="${!idZero}">
		<acme:cancel url="/curriculum/display.do?id=${curId}" code="mRecord.cancel"/>
	</jstl:if>

	<jstl:if test="${!idZero}">
		<acme:delete/>
	</jstl:if>
	
</form:form>
