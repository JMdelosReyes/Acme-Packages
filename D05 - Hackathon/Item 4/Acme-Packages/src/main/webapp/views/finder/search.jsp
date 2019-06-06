<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form:form action="finder/customer/search.do" modelAttribute="finder">

	<form:hidden path="id" />
	
	<fieldset>
		<legend><spring:message code="finder.filters"></spring:message></legend>
		
		<acme:textbox code="finder.town" path="town"/>
		<acme:textbox code="finder.minDate" path="minDate" placeholder="dd/MM/yyyy" />
		<acme:textbox code="finder.maxDate" path="maxDate" placeholder="dd/MM/yyyy" />
		<acme:textbox code="finder.maxPrice" type="number" path="maxPrice"/>
		<acme:textbox code="finder.weight" type="number" path="weight"/>				
		<acme:textbox code="finder.volume" type="number" path="volume"/>	
		
		<jstl:if test="${es }"><acme:selectString items="${ esCategories}" code="finder.category" path="category"/>	</jstl:if>
		<jstl:if test="${not es }"><acme:selectString items="${ enCategories}" code="finder.category" path="category"/>	</jstl:if>
	
		<acme:submit name="search" code="finder.search"/>
    	<acme:submit name="clear" code="finder.clear"/>
    	<acme:cancel url="" code="finder.cancel"/>	
	</fieldset>
	
</form:form>

<display:table name="offers" id="row" requestURI="${requestURI}"
				pagesize="5" class="displaytag">
	
	<display:column titleKey="of.ticker">
		<a href="offer/display.do?id=<jstl:out value="${row.id}"/>"><jstl:out value="${row.ticker}"></jstl:out></a>
	</display:column>
	
	<display:column>
		<a href="actor/displayCarrier.do?offerId=<jstl:out value="${row.id}"/>"><spring:message code = "of.carrier"/></a>	</display:column>
	
	<display:column titleKey="of.maxDateToRequest" sortable="true">
		<fmt:formatDate value="${row.maxDateToRequest}" pattern="dd/MM/yy"></fmt:formatDate>
	</display:column>
	
	<display:column titleKey="of.fares">
		<a href="fare/list.do?id=<jstl:out value="${row.id}"/>"><spring:message code = "of.fares"/></a>
	</display:column>
	
		<display:column titleKey="of.evaluations">
		<a href="evaluation/list.do?id=<jstl:out value="${row.id}"/>"><spring:message code = "of.evaluations"/></a>
	</display:column>
	
	   
</display:table>
