<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="mess/edit.do" modelAttribute="mess">
	 
	<form:hidden path="id"/>
	<form:hidden path="version"/>	
	<acme:textbox code="mess.subject" path="subject"/>
	<acme:selectMultiple items="${recipients}" itemLabel="userAccount.username" code="mess.recipients" path="recipients"/>
	<acme:textarea code="mess.tags" path="tags" placeholder="Enter the tags separated by commas.."/>
	<form:label path="priority"><spring:message code="mess.priority"/></form:label>
	<form:select path="priority">
		<jstl:forEach items="${priorities}" var="priority">
			<form:option label="${priority}" value="${priority}"></form:option>
		</jstl:forEach>
	</form:select>
	<form:errors cssClass="error" path="priority"/>
	<br/>
	<acme:textarea code="mess.body" path="body"/>	
	<acme:cancel url="messageBox/list.do" code="mess.cancel"/>
	<acme:submit name="send" code="mess.send"/>
	<security:authorize access="hasRole('ADMIN')">
		<acme:submit name="broadcast" code="mess.broadcast"/>
	</security:authorize>
		
	
</form:form>