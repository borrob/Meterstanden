<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
    <jsp:attribute name="header">
		<h1>Metersoorten</h1>
    </jsp:attribute>
    <jsp:attribute name="footer">
    </jsp:attribute>
    <jsp:body>
    	<div class="row">
	    	<div class="col-xs-12">
		        <p>Dit zijn de metersoorten die we kennen.</p>
		        <table class="table table-striped">
		        	<caption>Lijst van metersoorten.</caption>
		        	<thead><tr>
		        		<th>id</th>
		        		<th>soort</th>
		        		<th>unit</th>
		        	</tr></thead>
		        	<c:forEach items="${theMetersoorten}" var="ms">
			    		<tr>
					        <td><c:out value="${ms.id}"/></td>
					        <td><c:out value="${ms.metersoort}"/></td>
					        <td><c:out value="${ms.unit}"/></td>  
			    		</tr>
					</c:forEach>
				</table>
			</div>
		</div>
    </jsp:body>
</t:genericpage>