<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="request/customer/edit.do" modelAttribute="request">
	<fieldset>
	<form:hidden path="id"/>
	<acme:textbox code="req.description" path="description" />
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
	<acme:submit name="save" code="req.save"/>
	<acme:cancel url="request/customer/list.do" code="req.cancel"/>
</form:form>

<fieldset>
<jstl:forEach var="pac" items="${packages}">
		<fieldset>
			<legend><strong><spring:message code="req.package"/></strong>:</legend>
			<spring:message code="req.weight"/>: <jstl:out value="${pac.weight}"/> kg<br/>
			<spring:message code="req.height"/>: <jstl:out value="${pac.height}"/> cm<br/>
			<spring:message code="req.width"/>: <jstl:out value="${pac.width}"/> cm<br/>
			<spring:message code="req.length"/>: <jstl:out value="${pac.length}"/> cm<br/>
			<spring:message code="req.description"/>: <jstl:out value="${pac.description}"/><br/>
			<jstl:if test="${request.offer ne null}">
				<spring:message code="req.package.price"/>: <jstl:out value="${pac.price}"/><br/>
			</jstl:if>
			<jstl:if test="${es}">
				<jstl:forEach  var="cat" items="${pac.categories}">
					<fieldset>
					<legend><strong><spring:message code="req.category"/></strong></legend>
					<strong><spring:message code="req.category.spanishName"/>: <jstl:out value="${cat.spanishName}"/></strong><br/>
					<spring:message code="req.category.spanishDescription"/>: <jstl:out value="${cat.spanishDescription}"/><br/>
					</fieldset>
				</jstl:forEach>
			</jstl:if>
			<jstl:if test="${!es}">
				<jstl:forEach  var="cat" items="${pac.categories}">
					<fieldset>
							<legend><strong><spring:message code="req.category"/></strong></legend>
							<strong><spring:message code="req.category.englishName"/>: <jstl:out value="${cat.englishName}"/></strong><br/>
							<spring:message code="req.category.englishDescription"/>: <jstl:out value="${cat.englishDescription}"/><br/>
					</fieldset>
				</jstl:forEach>
			</jstl:if>
		</fieldset>
	</jstl:forEach>
<legend><spring:message code="req.package"/></legend>
<form:form action="request/customer/edit.do" modelAttribute="package">	
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
</form:form>	
</fieldset>

