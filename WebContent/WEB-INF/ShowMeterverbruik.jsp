<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:genericpage>
    <jsp:attribute name="header">
		<h1>Meterstanden verbruik</h1>
    </jsp:attribute>
    <jsp:attribute name="footer">
    </jsp:attribute>
    <jsp:body>
    	
    	<div class="row">
	    	<div class="col-xs-12">
		        <p>Dit is het verbruik.</p>
		      
		        <table class="table table-hover display" id="meterverbruikTable">
		        	<caption class="caption">Lijst van verbruik.</caption>
		        	<thead><tr>
		        	
		        		<th>jaar</th>
		        		<th>maand</th>
		        		<c:forEach items="${theMetersoorten}"  var="ms">
		        			<th><c:out value="${ms.metersoort}"/></th>
		        		</c:forEach>
		        	</tr></thead>
		        	<c:forEach items="${theMeterverbruik}" var="mlo">
			    		<tr>
					        <td><c:out value="${mlo.jaar}"/></td>
					        <td><c:out value="${mlo.maand}"/></td>
					        <c:forEach items="${mlo.mv}" var="mlomv">
					        	<td><fmt:formatNumber value="${mlomv.verbruik}" type="number" maxFractionDigits="0"/> <c:out value="${mlomv.metersoort.unit}"/></td>
					        </c:forEach>
			    		</tr>
					</c:forEach>
				</table>
			</div>
		</div>
    </jsp:body>
</t:genericpage>