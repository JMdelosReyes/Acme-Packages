<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="scripts/signUp.js"></script>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="request/customer/create.do" modelAttribute="createRequestForm">
	<fieldset>
	<h3><spring:message code="req.request"/></h3>
	<acme:textbox code="req.description" path="request.description" />
	<acme:textbox code="req.maxPrice" path="request.maxPrice" />
	<acme:textbox code="req.deadline" path="request.deadline" />
	<spring:message code="req.finalMode" />:
		<form:select id="finalMode" path="request.finalMode">
			<form:option value="false"> <spring:message code="req.select.notFinal" /> </form:option>
			<form:option value="true"> <spring:message code="req.select.final" /> </form:option>
		</form:select>
	<form:errors path="request.finalMode" cssClass="error" />
	<acme:textbox code="req.streetAddress" path="request.streetAddress" />
	<acme:textbox code="req.comment" path="request.comment" />
	<acme:select items="${towns}" itemLabel="name" code="req.town" path="request.town"/>
	</fieldset>
	
	<fieldset>
	<h3><spring:message code="req.package"/></h3>
	<acme:textbox code="req.weight" path="pac.weight" />
	<acme:textbox code="req.height" path="pac.height" />
	<acme:textbox code="req.width" path="pac.width" />
	<acme:textbox code="req.length" path="pac.length" />
	<acme:textbox code="req.description" path="pac.description" />
	<jstl:choose>
		<jstl:when test="${es eq true}">
			<acme:select items="${categories}" itemLabel="spanishName" code="req.category" path="pac.categories"/>
		</jstl:when>

		<jstl:otherwise>
			<acme:select items="${categories}" itemLabel="englishName" code="req.category" path="pac.categories"/>
		</jstl:otherwise>
	</jstl:choose>
	</fieldset>		
	<acme:submit name="save" code="req.save"/>
	<acme:cancel url="request/customer/list.do" code="req.cancel"/>


</form:form>