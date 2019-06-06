<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>




<form:form action="category/administrator/edit.do" modelAttribute="category">

    <form:hidden path="id" />
    <form:hidden path="version" />


    <fieldset>
    <jstl:if test="${category.id==0}">
        <form:label path="englishName">
			<spring:message code="cat.name" />:
		</form:label>
		<form:input path="englishName" />
		<form:errors cssClass="error" path="englishName" />
        <br />

       <form:label path="spanishName">
			<spring:message code="cat.spanishName" />:
		</form:label>
		<form:input path="spanishName" />
		<form:errors cssClass="error" path="spanishName" />
        <br />
        
          </jstl:if>
        <form:label path="englishDescription">
			<spring:message code="cat.englishDescription" />:
		</form:label>
		<form:input path="englishDescription" />
		<form:errors cssClass="error" path="englishDescription" />
        <br />

       <form:label path="spanishDescription">
			<spring:message code="cat.spanishDescription" />:
		</form:label>
		<form:input path="spanishDescription" />
		<form:errors cssClass="error" path="spanishDescription" />
        <br />
      
        
		
		
		
        <br />

    </fieldset>

	     
    <input type="submit" name="save"
			value="<spring:message code="cat.save"/>">
	

			
	<input type="button" name="cancel"
		value="<spring:message code="cat.cancel"/>" onclick="javascript:relativeRedir('category/administrator/list.do');"/>	
			
    <jstl:if test="${category.id!=0}">        
    <input type="submit" name="delete"
			value="<spring:message code="cat.delete"/>" onclick="return confirm('<spring:message code="mess.confirm.delete" />')">
	</jstl:if>

</form:form>
