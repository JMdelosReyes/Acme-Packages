<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('SPONSOR')">         	
	<form:form action="sponsorship/sponsor/edit.do" modelAttribute="sponsorship">
	
		<form:hidden path="id" />
		
		<jstl:if test="${sponsorship.id==0}">
			<fieldset>
				<acme:textbox code="spon.banner" path="banner" />
				<acme:textbox code="spon.targetPage" path="target" />		
			</fieldset>
		</jstl:if>
		<jstl:if test="${sponsorship.id!=0}">

			
			<fieldset>	
				<spring:message code="spon.banner" />: <jstl:out value="${sponsorship.banner}"></jstl:out><br>	
				<spring:message code="spon.targetPage"/>: <jstl:out value="${sponsorship.banner}"></jstl:out><br>		
			</fieldset>
		</jstl:if>
		<jstl:if test="${sponsorship.id==0}">
			<acme:submit name="save" code="spon.save" />
		</jstl:if>	
		<jstl:if test="${sponsorship.id!=0}">
			<acme:delete/>
		</jstl:if>
		<acme:cancel url="sponsorship/sponsor/list.do" code="spon.cancel" />
	
	
	</form:form>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">         	
	<form:form action="sponsorship/administrator/edit.do" modelAttribute="sponsorship">
	
		<form:hidden path="id" />
		<form:hidden path="version"/>
		
		<fieldset>
		
			<spring:message code="spon.banner" />: <jstl:out value="${sponsorship.banner}"></jstl:out><br>	
			<spring:message code="spon.targetPage"/>: <jstl:out value="${sponsorship.banner}"></jstl:out><br>
			
		</fieldset>
	
		<acme:submit name="save" code="spon.accept" />
		<jstl:if test="${sponsorship.id!=0}">
			<acme:delete/>
		</jstl:if>
		<acme:cancel url="sponsorship/administrator/list.do" code="spon.cancel" />
	
	
	</form:form>
</security:authorize>
