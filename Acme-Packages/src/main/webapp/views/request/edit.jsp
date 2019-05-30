<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="request/customer/edit.do" modelAttribute="request">
	<form:hidden path="id"/>
	<acme:textbox code="req.description" path="description" />
	<acme:textbox code="req.maxPrice" path="maxPrice" />
	<acme:textbox code="req.deadline" path="deadline" />
	<spring:message code="req.finalMode" />:
		<form:select id="finalMode" path="finalMode">
			<form:option value="false"> <spring:message code="req.select.notFinal" /> </form:option>
			<form:option value="true"> <spring:message code="req.select.final" /> </form:option>
		</form:select>
	<form:errors path="finalMode" cssClass="error" />
	<acme:textbox code="req.streetAddress" path="streetAddress" />
	<acme:textbox code="req.comment" path="comment" />
	<acme:select items="${towns}" itemLabel="name" code="req.town" path="town"/>
	<acme:submit name="save" code="req.save"/>
	<acme:submit name="delete" code="req.delete"/>
	<acme:cancel url="request/carrier,customer/list.do" code="req.cancel"/>
</form:form>

