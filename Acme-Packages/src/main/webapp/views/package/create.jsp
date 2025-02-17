<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="package/customer/create.do" modelAttribute="addPackageForm">
	<form:hidden path="requestId"/>
	<acme:textbox code="req.weight" path="weight" placeholder="In kg"/>
	<acme:textbox code="req.height" path="height" placeholder="In cm"/>
	<acme:textbox code="req.width" path="width" placeholder="In cm"/>
	<acme:textbox code="req.length" path="length" placeholder="In cm"/>
	<acme:textarea code="req.details" path="details" />
	<jstl:choose>
		<jstl:when test="${es eq true}">
			<acme:selectLuis items="${categories}" itemLabel="spanishName" code="req.category" path="categories"/>
		</jstl:when>

		<jstl:otherwise>
			<acme:selectLuis items="${categories}" itemLabel="englishName" code="req.category" path="categories"/>
		</jstl:otherwise>
	</jstl:choose>
	<acme:submit name="save" code="req.save"/>
	<acme:cancel url="request/carrier,customer,auditor/display.do?id=${reqId}" code="req.cancel"/>
</form:form>