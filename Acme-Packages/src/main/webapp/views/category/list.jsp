<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>




<display:table name="categories" id="row" requestURI="category/administrator/list.do"
				pagesize="5" class="displaytag">
	
	
	<jstl:if test="${es}">
		<display:column property="spanishName" titleKey="cat.name"/>
   	 	<display:column property="spanishDescription" titleKey="cat.spanishDescription"/>
   </jstl:if>
   <jstl:if test="${!es}">
    	<display:column property="englishName" titleKey="cat.name"/>
   		<display:column property="englishDescription" titleKey="cat.englishDescription"/>
   </jstl:if>
         <display:column>
         	       
         
         	<a href="category/administrator/edit.do?category=${row.id}">
				<spring:message code="cat.categoryEdit"/>
			</a>
			
			
		</display:column>


</display:table>

<a href="category/administrator/create.do"><spring:message code="cat.categoryCreate"/></a>