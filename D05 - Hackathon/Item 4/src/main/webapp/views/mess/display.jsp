<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<spring:message code="mess.subject"/>: <jstl:out value="${mess.subject}"/><br/>
<spring:message code="mess.sender"/>: <jstl:out value="${mess.sender.name} ${mess.sender.surname}"/><br/>
<spring:message code="mess.priority"/>: <jstl:out value="${mess.priority}"/><br/>
<spring:message code="date.format.column" var="format"/>
<spring:message code="mess.sendDate"/>: <fmt:formatDate value="${mess.sendDate}"  pattern="${format}"/><br/>
<spring:message code="mess.body"/>: <jstl:out value="${mess.body}"/><br/>
<spring:message code="mess.recipients"/>: <br/>
<jstl:forEach var="des" items="${mess.recipients}"> 
	-<jstl:out value="${des.name} ${des.surname}"></jstl:out> 
	<br/>
</jstl:forEach>
<br/>
<spring:message code="mess.tags"/>:
	<br/>
	<jstl:forEach items="${mess.tags}" var="tag">
		#<jstl:out value="${tag}"/>
		<br/>
	</jstl:forEach>

<br/>
<form:form action="mess/display.do">
	<input type="hidden" name="messId" value="${mess.id}">
	<acme:submit name="copy" code="mess.copy"/>
	<acme:submit name="move" code="mess.move"/>
	<acme:submit name="delete" code="mess.delete"/>
</form:form>
<acme:cancel url="messageBox/list.do" code="mess.cancel"/>
