<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
	
<display:table name="evaluations" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag">

	<display:column titleKey="eva.comment">
		<jstl:out value="${row.comment}"></jstl:out>
	</display:column>

	<display:column titleKey="eva.mark">
		<jstl:out value="${row.mark}"></jstl:out>
	</display:column>
	
	<display:column titleKey="eva.moment">
		<jstl:out value="${row.moment}"></jstl:out>
	</display:column>
	
	<display:column titleKey="eva.offer">
		<a href="offer/display.do?id=<jstl:out value="${row.offer.id}"/>"><spring:message code="eva.offer"/></a>
	</display:column>
	
	<display:column titleKey="eva.customer">
		<a href="actor/display.do?id=<jstl:out value="${row.customer.id}"/>"><spring:message code="eva.customer"/></a>
	</display:column>
	
	<jstl:if test="${owner eq true}">
		<display:column>
			<form action="evaluation/customer/delete.do" method="POST">
				<input type="hidden" value="${row.id}" name="id">

				<acme:delete/>
			</form>
		</display:column>
	</jstl:if>


</display:table>

