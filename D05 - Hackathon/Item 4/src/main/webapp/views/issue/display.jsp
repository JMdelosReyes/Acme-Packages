<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<fieldset>
	<spring:message code="iss.ticker" />: <jstl:out value="${issue.ticker}" /><br>
	<spring:message code="iss.moment" />: <jstl:out value="${issue.moment}" /><br>
	<spring:message code="iss.description" />: <jstl:out value="${issue.description}" /><br>
	
	<jstl:choose>
		<jstl:when test="${issue.closed eq true}">
			<spring:message code="iss.closed"></spring:message>
		</jstl:when>
		<jstl:otherwise>
			<spring:message code="iss.open"></spring:message>
		</jstl:otherwise>
	</jstl:choose>
	
	<br>
	
	<a href="offer/display.do?id=${issue.offer.id}">
		<spring:message code="iss.offer"></spring:message>
	</a>
	
	<br>
	<br>
	
	<security:authorize access="hasRole('CUSTOMER')">
		<jstl:if test="${assigned eq false}">				
			<form action="issue/customer/delete.do" method="POST">
				<input type="hidden" value="${issue.id}" name="id">
	
				<acme:delete/>
			</form>
		</jstl:if>
	</security:authorize>
	
	<security:authorize access="hasRole('AUDITOR')">
		<jstl:if test="${involved eq true}">
			<jstl:if test="${issue.closed eq false}">				
				<form action="issue/auditor/close.do" method="POST">
					<input type="hidden" value="${issue.id}" name="id">
		
					<input type="submit" name="close"
						value="<spring:message code="iss.close"/>" >
				</form>
			</jstl:if>
		</jstl:if>
	</security:authorize>
	
</fieldset>

<jstl:if test="${assigned eq true}">
	<jstl:if test="${involved eq true}">
		
		
			<fieldset>
				<legend><spring:message code="iss.comments"/></legend>
			
		<jstl:if test="${issue.closed eq false}">	
				<form:form action="issue/carrier,customer,auditor/add-comment.do?issId=${issue.id}" modelAttribute="comment">
			
					<form:hidden path="id"/>
					
					<acme:textarea code="comm.comment" path="userComment"/>
					
					<acme:submit name="save" code="iss.save"/>
					
				</form:form>
		</jstl:if>
				<br>
				
				<jstl:forEach items="${issue.comments}" var="item">
					<fieldset >
						<jstl:out value="${item.moment}"></jstl:out>
						<jstl:out value="${item.username}"></jstl:out>
						<jstl:out value="${item.userComment}"></jstl:out>
					</fieldset>
					<br>
				</jstl:forEach>
				
			</fieldset>
			
	</jstl:if>
</jstl:if>





