<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="dashboard/administrator/list.do" modelAttribute="actor">
	<acme:submit name="spammers" code="admin.dashboard.calculaSpammers"/>
</form:form>

<form:form action="dashboard/administrator/list.do" modelAttribute="actor">
	<acme:submit name="computeScore" code="admin.dashboard.computeScore"/>
</form:form>

<form:form action="dashboard/administrator/list.do" modelAttribute="actor">
	<acme:submit name="notisponsorship" code="admin.dashboard.sendSponsorshipNotication"/>
</form:form>

<form:form action="dashboard/administrator/list.do" modelAttribute="actor">
	<acme:submit name="invalidsponsorship" code="admin.dashboard.invalidSponsorship"/>
</form:form>




<!--  -->

<h3><spring:message code="admin.dashboard.ratios"/></h3>
<table>
  <tr>
  	<th><spring:message code="admin.dashboard.status"/></th>
    <th><spring:message code="admin.dashboard.value"/></th>
  </tr>
  <tr>
    <td><spring:message code="admin.dashboard.RatioNonEmptyFinders"/></td>
    <td><jstl:out value="${RatioNonEmptyFinders}"/></td>
  </tr>
  <tr>
    <td><spring:message code="admin.dashboard.RatioEmptyFinders"/></td>
    <td><jstl:out value="${RatioEmptyFinders}"/></td>
  </tr>
   <tr>
    <td><spring:message code="admin.dashboard.ratioNonClosedIssue"/></td>
    <td><jstl:out value="${ratioNonClosedIssue}"/></td>
  </tr>
  <tr>
    <td><spring:message code="admin.dashboard.ratioClosedIssue"/></td>
    <td><jstl:out value="${ratioClosedIssue}"/></td>
  </tr>
 
</table>

<h2>
	<spring:message code="admin.dashboard.AuditorsIwth10ClosesIssuesAboveAVG" />
</h2>
<display:table name="AuditorsIwth10ClosesIssuesAboveAVG" requestURI="${requestURI}"
	id="row" class="displaytag">
	<display:column titleKey="auditor">
		<jstl:out value="${row.name} ${row.surname}"></jstl:out>
	</display:column>
</display:table>

<h2>
	<spring:message code="admin.dashboard.top5MostVisitedTowns" />
</h2>
<display:table name="top5MostVisitedTowns" requestURI="${requestURI}"
	id="row" class="displaytag">
	<display:column titleKey="town">
		<jstl:out value="${row.name}"></jstl:out>
	</display:column>
</display:table>

<h2>
	<spring:message code="admin.dashboard.top3CarriersWithHigherScore" />
</h2>
<display:table name="top3CarriersWithHigherScore" requestURI="${requestURI}"
	id="row" class="displaytag">
	<display:column titleKey="carrier">
		<jstl:out value="${row.name} ${row.score}"></jstl:out>
	</display:column>
</display:table>

<h2>
	<spring:message code="admin.dashboard.top3ShownSponsorships" />
</h2>
<display:table name="top3ShownSponsorships" requestURI="${requestURI}"
	id="row" class="displaytag">
	<display:column titleKey="sponsorship">
		<jstl:out value="${row.target} "></jstl:out>
	</display:column>
</display:table>

<!--  -->
<div class="container" style="width: 50%; float:left;">
	<canvas id="myChartnuevo"></canvas>
</div>
	
<script>
	let myChartnuevo = document.getElementById('myChartnuevo').getContext('2d');
	
	var avg = '<jstl:out value="${avgSPShow}"/>';
	var min = '<jstl:out value="${minSPShow}"/>';
	var max = '<jstl:out value="${maxSPShow}"/>';
	var std = '<jstl:out value="${stddevSPShow}"/>';
	
	let barChartnuevo = new Chart(myChartnuevo, {
		type:'bar',
		data:{
			labels:['<spring:message code="admin.dashboard.average"/>',
			        '<spring:message code="admin.dashboard.minimum"/>',
			        '<spring:message code="admin.dashboard.maximum"/>',
			        '<spring:message code="admin.dashboard.std"/>'],
			datasets:[{
				label:'<spring:message code="admin.dashboard.SPShow"/>',
				borderWidth: 1,
				data:[avg,
				      min,
				      max,
				      std],
				backgroundColor:['rgba(54,162,235,0.6)',
				                 'rgba(54,162,235,0.6)',
				                 'rgba(54,162,235,0.6)',
				                 'rgba(54,162,235,0.6)']
			}]
	},
		options:{
			scales: {
		        yAxes: [{
		            ticks: {
		                beginAtZero: true
		            }
		        }]
		    }
		}
	});
</script>

<div class="container" style="width: 50%; float:left;">
	<canvas id="myChartnuevo2"></canvas>
</div>

<script>
	let myChartnuevo2 = document.getElementById('myChartnuevo2').getContext('2d');
	
	var avg = '<jstl:out value="${avgScoreCarriers}"/>';
	var min = '<jstl:out value="${minScoreCarriers}"/>';
	var max = '<jstl:out value="${maxScoreCarriers}"/>';
	var std = '<jstl:out value="${stddevScoreCarriers}"/>';
	
	let barChartnuevo2 = new Chart(myChartnuevo2, {
		type:'bar',
		data:{
			labels:['<spring:message code="admin.dashboard.average"/>',
			        '<spring:message code="admin.dashboard.minimum"/>',
			        '<spring:message code="admin.dashboard.maximum"/>',
			        '<spring:message code="admin.dashboard.std"/>'],
			datasets:[{
				label:'<spring:message code="admin.dashboard.ScoreCarriers"/>',
				borderWidth: 1,
				data:[avg,
				      min,
				      max,
				      std],
				backgroundColor:['rgba(54,162,235,0.6)',
				                 'rgba(54,162,235,0.6)',
				                 'rgba(54,162,235,0.6)',
				                 'rgba(54,162,235,0.6)']
			}]
	},
		options:{
			scales: {
		        yAxes: [{
		            ticks: {
		                beginAtZero: true
		            }
		        }]
		    }
		}
	});
</script>

<div class="container" style="width: 50%; float:left;">
	<canvas id="myChartnuevo3"></canvas>
</div>

<script>
	let myChartnuevo3 = document.getElementById('myChartnuevo3').getContext('2d');
	
	var avg = '<jstl:out value="${avgEvaluationByCustomer}"/>';
	var min = '<jstl:out value="${minEvaluationByCustomer}"/>';
	var max = '<jstl:out value="${maxEvaluationByCustomer}"/>';
	var std = '<jstl:out value="${stddevEvaluationByCustomer}"/>';
	
	let barChartnuevo3 = new Chart(myChartnuevo3, {
		type:'bar',
		data:{
			labels:['<spring:message code="admin.dashboard.average"/>',
			        '<spring:message code="admin.dashboard.minimum"/>',
			        '<spring:message code="admin.dashboard.maximum"/>',
			        '<spring:message code="admin.dashboard.std"/>'],
			datasets:[{
				label:'<spring:message code="admin.dashboard.evaluationByCustomer"/>',
				borderWidth: 1,
				data:[avg,
				      min,
				      max,
				      std],
				backgroundColor:['rgba(54,162,235,0.6)',
				                 'rgba(54,162,235,0.6)',
				                 'rgba(54,162,235,0.6)',
				                 'rgba(54,162,235,0.6)']
			}]
	},
		options:{
			scales: {
		        yAxes: [{
		            ticks: {
		                beginAtZero: true
		            }
		        }]
		    }
		}
	});
</script>

<div class="container" style="width: 50%; float:left;">
	<canvas id="myChartnuevo4"></canvas>
</div>

<script>
	let myChartnuevo4 = document.getElementById('myChartnuevo4').getContext('2d');
	
	var avg = '<jstl:out value="${avgCommentsPerIssue}"/>';
	var min = '<jstl:out value="${minCommentsPerIssue}"/>';
	var max = '<jstl:out value="${maxCommentsPerIssue}"/>';
	var std = '<jstl:out value="${stddevCommentsPerIssue}"/>';
	
	let barChartnuevo4 = new Chart(myChartnuevo4, {
		type:'bar',
		data:{
			labels:['<spring:message code="admin.dashboard.average"/>',
			        '<spring:message code="admin.dashboard.minimum"/>',
			        '<spring:message code="admin.dashboard.maximum"/>',
			        '<spring:message code="admin.dashboard.std"/>'],
			datasets:[{
				label:'<spring:message code="admin.dashboard.commentsPerIssue"/>',
				borderWidth: 1,
				data:[avg,
				      min,
				      max,
				      std],
				backgroundColor:['rgba(54,162,235,0.6)',
				                 'rgba(54,162,235,0.6)',
				                 'rgba(54,162,235,0.6)',
				                 'rgba(54,162,235,0.6)']
			}]
	},
		options:{
			scales: {
		        yAxes: [{
		            ticks: {
		                beginAtZero: true
		            }
		        }]
		    }
		}
	});
</script>


<!--  -->



<!--  -->
<br>










