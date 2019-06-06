<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="messBox" action="messageBox/edit.do?messageBoxId=${messBox.id}">
	
	<form:hidden path="id"/>
	<acme:textbox code="messBox.name" path="name"/>
	<jstl:if test="${messBox.id==0}">
		<acme:select items="${parents}" itemLabel="name" code="messBox.parent" path="parent"/>
	</jstl:if>
	<acme:submit name="save" code="messBox.save"/>
	<acme:cancel url="messageBox/list.do" code="messBox.cancel"/>
	<jstl:if test="${messBox.isSystem!=true}">
		<jstl:if test="${messBox.id!=0}"> 
			<jstl:if test="${!(empty messBox.messages)}">
			<acme:submit name="deleteAll" code="messBox.deleteAll"/>
			</jstl:if>
			<jstl:if test="${(empty messBox.messages)}">  
					<jstl:if test="${(empty children)}">   
	    				<input type="submit" name="delete" value="<spring:message code="mess.delete"/>" onclick="return confirm('<spring:message code="mess.confirm.delete" />')"/>	    			</jstl:if> 	
	    			<jstl:if test="${!(empty children)}">
	    				<br>
	    				<spring:message code="messBox.hasChildren"/>:
	    				<jstl:forEach items="${children}" var="child">
	    					<br>
	    					<jstl:out value="${child.name}"></jstl:out>
							<br>
	    				</jstl:forEach>
	    			</jstl:if>
	    	</jstl:if>
		</jstl:if>
	</jstl:if>
	
</form:form>