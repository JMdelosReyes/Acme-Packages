<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<fieldset>
	<legend><spring:message code="curr.personalRecord"/></legend>

<spring:message code="curr.fullName"/>: <jstl:out value="${curriculum.fullName}"/><br>
<spring:message code="curr.email"/>: <jstl:out value="${curriculum.email}"/><br>
<spring:message code="curr.phoneNumber"/>: <jstl:out value="${curriculum.phoneNumber}"/><br>
<spring:message code="curr.photo"/>: <img src="${curriculum.photo}"><br>
<jstl:if test="${owner eq true}">
	<a href="curriculum/carrier/edit.do?id=${curriculum.id}"><spring:message code="curr.edit"/></a>
</jstl:if>


</fieldset>

<br>

<fieldset>
	<legend><spring:message code="curr.professionalRecord"/></legend>
	<jstl:if test="${!(empty (curriculum.professionalRecords))}">
		<jstl:forEach items="${curriculum.professionalRecords}" var="pRecord">
			<br>
			<fieldset>
			<spring:message code="pr.company"/>: <jstl:out value="${pRecord.companyName}"/><br>
			<spring:message code="pr.attachment"/>: <jstl:out value="${pRecord.attachment}"/><br>
			<spring:message code="pr.startTime"/>: <jstl:out value="${pRecord.startTime}"/><br>
			<spring:message code="pr.endTime"/>: <jstl:out value="${pRecord.endTime}"/><br>
			<spring:message code="pr.comments"/>:
			<br>
			<jstl:forEach items="${pRecord.comments}" var="att">
				<jstl:out value="${att}"/><br>
			</jstl:forEach>			
			
			<jstl:if test="${owner eq true}">
				<a href="professionalRecord/carrier/edit.do?id=${pRecord.id}"><spring:message code="curr.edit"/></a>
			</jstl:if>			
			</fieldset>
			<br>							
		</jstl:forEach>
	</jstl:if>
	<jstl:if test="${owner eq true}">
		<a href="professionalRecord/carrier/create.do?curId=${curriculum.id}"><spring:message code="curr.add"/></a>
	</jstl:if>
</fieldset>

<br>

<fieldset>
	<legend><spring:message code="curr.miscellaneousRecord"/></legend>
	<jstl:if test="${!(empty (curriculum.miscellaneousRecords))}">
		<jstl:forEach items="${curriculum.miscellaneousRecords}" var="mRecord">
			<br>
			<fieldset>
			<spring:message code="mr.title"/>: <jstl:out value="${mRecord.title}"/><br>
			<spring:message code="mr.attachment"/>: <jstl:out value="${mRecord.attachment}"/><br>
			<spring:message code="mr.comments"/>:
			<br>
			<jstl:forEach items="${mRecord.comments}" var="att1">
				<jstl:out value="${att1}"/><br>
			</jstl:forEach>			
			
			<jstl:if test="${owner eq true}">
				<a href="miscellaneousRecord/carrier/edit.do?id=${mRecord.id}"><spring:message code="curr.edit"/></a>
			</jstl:if>
						
			</fieldset>
			<br>							
		</jstl:forEach>
	</jstl:if>
	<jstl:if test="${owner eq true}">
		<a href="miscellaneousRecord/carrier/create.do?curId=${curriculum.id}"><spring:message code="curr.add"/></a>
	</jstl:if>
	
</fieldset>
