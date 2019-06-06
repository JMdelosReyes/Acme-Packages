<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<a href="mess/create.do"><spring:message code="messBox.createMessage"/></a>
<br/>
<display:table name="messBox" id="row" requestURI="${requestURI}"
				pagesize="5" class="displaytag">
	
	<display:column titleKey="messBox.name">
		<jstl:out value="${row.name}"></jstl:out>
	</display:column>
	
	<display:column titleKey="messBox.messages">
		<a href="messageBox/display.do?messageBoxId=${row.id}"> 
			<spring:message code="messBox.messages"/>
		</a>
	</display:column>
	
	<jstl:if test="${row.isSystem}" var="isSys">
		<display:column titleKey="messBox.isSystem">
			<spring:message code="messBox.notEdit"/>
		</display:column>
	</jstl:if>
	<jstl:if test="${!row.isSystem}" var="isSys">
		<display:column titleKey="messBox.isSystem">
			<spring:message code="messBox.edit"/>
		</display:column>
	</jstl:if>
	
	<display:column titleKey="messBox.edit">
	<jstl:if test="${!row.isSystem}">
		<a href="messageBox/edit.do?messageBoxId=${row.id}">
			<spring:message code="messBox.edit"/>
		</a>
	</jstl:if>
	</display:column>
</display:table>
<a href="messageBox/create.do"><spring:message code="messBox.create"/></a>

