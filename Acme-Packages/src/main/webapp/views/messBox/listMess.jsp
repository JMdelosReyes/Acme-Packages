<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<h2><jstl:out value="${messageBox.name}"/></h2>
<br>
<a href="messageBox/create.do"><spring:message code="messBox.create"/></a>
<br>
<display:table name="messageBox.messages" id="row"
		requestURI="${requestURI}"
		pagesize="5" class="displaytag">
	
	<display:column titleKey="mess.subject" sortable="false"><jstl:out value="${row.subject}"></jstl:out></display:column>
	<display:column titleKey="mess.sender" sortable="false"><jstl:out value="${row.sender.name} ${row.sender.surname}"></jstl:out></display:column>
	<spring:message code="date.format.column" var="format"/>
	<display:column titleKey="mess.sendDate" sortable="true"><fmt:formatDate value="${row.sendDate}"  pattern="${format}"/></display:column>
	<display:column titleKey="mess.priority"><jstl:out value="${row.priority}"/></display:column>
	<display:column titleKey="mess.display">
		<a href="mess/display.do?messageId=${row.id}">
			<spring:message code="mess.display"/>
		</a>
	</display:column>
</display:table>
<br>

<jstl:if test="${!(empty children)}">
	<h3><spring:message code="messBox.children"/>:</h3>
	<jstl:forEach var="child" items="${children}">
		·<a href="messageBox/display.do?messageBoxId=${child.id}"><jstl:out value="${child.name}"></jstl:out></a>
		<br>
	</jstl:forEach>
	<br>
</jstl:if>
<acme:cancel url="messageBox/list.do" code="messBox.back"/>
<jstl:if test="${!messageBox.isSystem}">
	<a href="messageBox/edit.do?messageBoxId=${messageBox.id}">
		<spring:message code="messBox.edit"/>
	</a>
</jstl:if>
