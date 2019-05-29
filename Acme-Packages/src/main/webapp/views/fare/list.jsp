<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<display:table name="fares" id="row" requestURI="${requestURI}"
				pagesize="5" class="displaytag">
	
	<display:column titleKey="fa.maxWeight">
		<jstl:out value="${row.maxWeight}"></jstl:out>
	</display:column>
			
	<display:column titleKey="fa.maxVolume">
		<jstl:out value="${row.maxVolume}"></jstl:out>
	</display:column>
	
	<display:column titleKey="fa.price">
		<jstl:out value="${row.price}"></jstl:out>
	</display:column>
		
	<jstl:if test="${requestURI eq 'fare/carrier/list.do'}">
		
		<display:column>
			<a href="fare/carrier/edit.do?id=<jstl:out value="${row.id}"/>"><spring:message code = "fa.edit"/></a>
		</display:column>
	
	</jstl:if>
	
	   
</display:table>
	<jstl:if test="${requestURI eq 'fare/carrier/list.do'}">

<a href="fare/carrier/create.do"><spring:message code="fa.create"/></a>
	</jstl:if>
