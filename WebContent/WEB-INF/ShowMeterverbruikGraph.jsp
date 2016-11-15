<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
    <jsp:attribute name="header">
		<script src="lib/chart/Chart.min.js"></script>
		<h1>Meterstanden verbruik</h1>
    </jsp:attribute>
    <jsp:attribute name="footer">
    </jsp:attribute>
    <jsp:body>
    	
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
    	        	label: <c:out value="${legend}"/>,
    	        	data: <c:out value="${monthoverview}"/>,
    	            borderWidth: 1,
    	            pointRadius: 5,
    	            pointBackgroundColor: 'black'
    	        }]
    	    };
    	var myOptions = {
    	        scales: {
    	            yAxes: [{
    	                ticks: {
    	                    beginAtZero:true
    	                }
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