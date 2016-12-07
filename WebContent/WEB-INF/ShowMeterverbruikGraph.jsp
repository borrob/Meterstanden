<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
    <jsp:attribute name="header">
		<script src="lib/chart/Chart.min.js"></script>
		<h1>Meterstanden verbruik</h1>
		<button onclick="$('#showGraphModal').modal()" class="btn btn-info">Change graph</button>
    </jsp:attribute>
    <jsp:attribute name="footer">
    </jsp:attribute>
    <jsp:body>
    
    	<jsp:include page="/WEB-INF/modalwindow/showGraph.jsp"/>
    	
    	<div class="row">
	    	<div class="col-xs-12">
		        <p>Dit is het verbruik.</p>      
		        <canvas id="myChart"></canvas>
		    </div>
		</div>
		
		<script>
    	var ctx = $("#myChart");
    	var myData = {
    	        labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
    	        datasets: [{
    	        	label: '<c:out value="${legend}"/>',
    	        	data: <c:out value="${monthoverview}"/>,
    	            borderWidth: 1,
    	            pointRadius: 5,
    	            pointBackgroundColor: 'rgb(10,10,10)',
    	            backgroundColor: 'rgba(10,10,10,0.2)'
    	        }
    	        <c:if test="${monthoverview2 ne null}">
    	        ,{
    	        	label: '<c:out value="${legend2}"/>',
    	        	data: <c:out value="${monthoverview2}"/>,
    	            borderWidth: 1,
    	            pointRadius: 5,
    	            pointBackgroundColor: 'rgb(0,0,200)',
    	            backgroundColor: 'rgba(0,0,200,0.2)'
    	        }
    	        </c:if>
    	        <c:if test="${monthoverview3 ne null}">
    	        ,{
    	        	label: '<c:out value="${legend3}"/>',
    	        	data: <c:out value="${monthoverview3}"/>,
    	            borderWidth: 1,
    	            pointRadius: 5,
    	            pointBackgroundColor: 'rgb(0,200,0)',
    	            backgroundColor: 'rgba(0,200,0,0.2)'
    	        }</c:if>
    	        ]
    	    };
    	var myOptions = {
    	        scales: {
    	            yAxes: [{
    	                ticks: {beginAtZero:true},
    	                scaleLabel: {display: true, labelString: '<c:out value="${ylabel}"/>'}
    	            }]
    	        }
    	};
    	var myChart = new Chart(ctx, {
    	    type: 'line',
    	    data: myData,
    	    options: myOptions
    	    }
    	);
    	</script>
    </jsp:body>
</t:genericpage>