
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<form:form action="socialProfile/edit.do" modelAttribute="socialProfile">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	
	<acme:textbox code="sp.nick" path="nick"/>
	<acme:textbox code="sp.socialNetwork" path="socialNetwork"/>
	<acme:textbox code="sp.link" path="profileLink"/>

	
	<acme:submit name="save" code="sp.save"/>
	<acme:cancel code="sp.cancel" url="socialProfile/list.do"/>

	

	<jstl:if test="${socialProfile.id!=0}">  
		<input type="submit" name="delete" value = "<spring:message code="sp.delete"/>" onclick="return confirm('<spring:message code="app.confirm.delete" />')"/>
    </jstl:if>
	
	
</form:form>
