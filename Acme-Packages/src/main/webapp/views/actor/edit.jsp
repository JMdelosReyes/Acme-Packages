<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

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

<form:form action="actor/edit.do" modelAttribute="editActorForm">

<fieldset> 

        <acme:textbox code="act.name" path="name" />
        <acme:textbox code="act.surname" path="surname"/>
        <acme:textbox code="act.middlename" path="middleName" />
        <acme:textbox code="act.photo" path="photo" placeholder="http://www.example.com"/>
        <acme:textbox code="act.email" path="email" placeholder="example@example.com"/>
        <acme:textbox code="act.phoneNumber" path="phoneNumber" placeholder="689513247"/>
        <acme:textbox code="act.address" path="address"/>

        	    
	       <security:authorize access="hasRole('CARRIER')">
       			 <acme:textbox code="act.vat" path="vat" placeholder="DE999999999"/>
	    </security:authorize>
	       <security:authorize access="hasRole('SPONSOR')">

	        		<acme:textbox code="act.nif" path="nif"/>
	    </security:authorize>
	    
        
        
</fieldset>
<fieldset>
					<acme:selectString items="${makes}" code="act.make" path="creditCard.make"/>
	        		<acme:textbox code="act.holderName" path="creditCard.holderName" />
	        		<acme:textbox code="act.expirationMonth" path="creditCard.expirationMonth"/>
	        		<acme:textbox code="act.expirationYear" path="creditCard.expirationYear"/> 
	        		<acme:textbox code="act.cvv" path="creditCard.cvv"/> 
	        		<acme:textbox code="act.number" path="creditCard.number" placeholder="4981304263741333"/> 

</fieldset>
	
	<input type="submit" name="save" onclick="if(validatePhone('phoneNumber')){return confirm('<spring:message code="act.confirmPhone" />')}" value="<spring:message code="act.save"/>"> 
    <acme:delete/>
    <acme:cancel url="/actor/display.do" code="act.cancel"/>
    <security:authorize access="hasAnyRole('SPONSOR')">
    <br>
    	<acme:cancel url="/getActor.do" code="actor.export"/>
    </security:authorize>

	
	</form:form>