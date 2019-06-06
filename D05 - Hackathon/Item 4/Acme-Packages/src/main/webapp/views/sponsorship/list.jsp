<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table name="sponsorships" id="row" requestURI="${requestURI}"
				pagesize="5" class="displaytag">
	
	<display:column  titleKey="spon.banner">
   		<jstl:out value="${row.banner}"></jstl:out>  		
    </display:column>
	
    <display:column  titleKey="spon.targetPage">
    	<jstl:out value="${row.target}"></jstl:out>
    </display:column> 
   
    <display:column  titleKey="spon.valid">
    	<jstl:out value="${row.valid}"></jstl:out>
    </display:column>    
      
    <display:column  titleKey="spon.expirationDate">
    	<jstl:if test="${row.expirationDate ne null }">
  		  	<jstl:out value="${row.expirationDate}"></jstl:out>  	
    	</jstl:if>	
    </display:column>   
   
    <display:column  titleKey="spon.count">
    	<jstl:out value="${row.count}"></jstl:out>
    </display:column>  
    
    <display:column  titleKey="spon.totalCount">
    	<jstl:out value="${row.totalCount}"></jstl:out>
    </display:column>    
    
    <display:column>        	
         <security:authorize access="hasRole('ADMIN')">
         	<a href="sponsorship/administrator/edit.do?id=${row.id}">
				<spring:message code="spon.edit"/>
			</a>            
         </security:authorize>
         
         <security:authorize access="hasRole('SPONSOR')">         	
         	<a href="sponsorship/sponsor/edit.do?id=${row.id}">
				<spring:message code="spon.edit"/>
			</a>
		</security:authorize>
	</display:column>
</display:table>

<security:authorize access="hasRole('SPONSOR')">         	
	<a href="sponsorship/sponsor/create.do"><spring:message code="spon.create"/></a>
</security:authorize>
