<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<display:table name="socialProfiles" id="row" requestURI="${requestURI}"
				pagesize="5" class="displaytag">
				
	<display:column titleKey="sp.nick">
		<jstl:out value="${row.nick}"></jstl:out>
	</display:column>
	
	<display:column titleKey="sp.socialNetwork">
		<jstl:out value="${row.socialNetwork}"></jstl:out>
	</display:column>
	
	<display:column titleKey="sp.link">
		<jstl:out value="${row.profileLink}"></jstl:out>
	</display:column>
	
	<display:column>
		<a href="socialProfile/edit.do?id=<jstl:out value="${row.id}"/>"><spring:message code="sp.edit"/></a>
	</display:column>
	
</display:table>

<input type="button" name="new" value="<spring:message code="sp.new" />" onclick="javascript: relativeRedir('socialProfile/create.do');" />
	
