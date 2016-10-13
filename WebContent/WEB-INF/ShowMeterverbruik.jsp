<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
    <jsp:attribute name="header">
		<h1>Meterstanden verbruik</h1>
    </jsp:attribute>
    <jsp:attribute name="footer">
    </jsp:attribute>
    <jsp:body>
    	
    	<div class="row">
	    	<div class="col-xs-12">
		        <p>Dit zijn de 20 laatste meterstanden.</p>
		      
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
					        	<td><c:out value="${mlomv.waarde}"/></td>
					        </c:forEach>
			    		</tr>
					</c:forEach>
				</table>
			</div>
		</div>
    </jsp:body>
</t:genericpage>