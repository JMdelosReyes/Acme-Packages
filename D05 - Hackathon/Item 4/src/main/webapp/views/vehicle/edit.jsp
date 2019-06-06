<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="vehicle/carrier/edit.do" modelAttribute="vehicle">

	<form:hidden path="id"/>
	
	<acme:textbox code="veh.plate" path="plate"/>
	<acme:selectString items="${types}" code="veh.type" path="type"/>
	<acme:textbox type="number" code="veh.maxVolume" path="maxVolume"/>
	<acme:textbox type="number" code="veh.maxWeight" path="maxWeight"/>
	<acme:textbox code="veh.pictures" path="pictures" placeholder="https://www.asd.com, https://www.asd2.com"/>
	<acme:textbox code="veh.comment" path="comment"/>
	
	<br>
	
	<acme:submit name="save" code="veh.save"/>
	<acme:cancel url="vehicle/carrier/list.do" code="veh.back"/>
	
	<jstl:if test="${vehicle.id!=0}">
		<acme:delete/>
	</jstl:if>
	
</form:form>