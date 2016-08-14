<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
    <jsp:attribute name="header">
		<h1>Meterstanden</h1>
    </jsp:attribute>
    <jsp:attribute name="footer">
    </jsp:attribute>
    <jsp:body>
    
		<%-- Add the modal window to to add new meterstand --%>
    	<jsp:include page="/WEB-INF/modalwindow/addNewMeterstand.jsp"/>
    	
    	<div class="row">
	    	<div class="col-xs-12">
		        <p>Dit zijn de 20 laatste meterstanden.</p>
		        <button class="btn btn-success selectiebuttonMeterstanden" id="button_0">alles</button>
		        <c:forEach items="${theMetersoorten }" var="soort">
		        	<button class="btn selectiebuttonMeterstanden" id ="button_${soort.id}">${soort.metersoort}</button>
		        </c:forEach>
		        <script>
		        	$('.selectiebuttonMeterstanden').removeClass('btn-success');
		        	$('.selectiebuttonMeterstanden').on("click", function(){
		        		document.location.href="ShowMeterstanden?selection="+this.id.substr(7,this.id.length);
		        	});
		        	$('#button_${selectedMetersoort}').addClass('btn-success');
		        </script>
		        <table class="table table-hover display" id="meterstandenTable">
		        	<caption class="caption">Lijst van meterstanden.</caption>
		        	<thead><tr>
<%-- 		        		<th>id</th> --%>
		        		<th>datum</th>
		        		<th>meter</th>
		        		<th>waarde</th>
		        		<th>opmerking</th>
		        	</tr></thead>
		        	<c:forEach items="${theMeterstanden}" var="ms">
			    		<tr>
<%-- 					        <td><c:out value="${ms.id}"/></td> --%>
					        <td><c:out value="${ms.datum}"/></td>
					        <td><c:out value="${ms.metersoort.metersoort}"/></td>
					        <td><c:out value="${ms.waarde}"/></td>
					        <td><c:out value="${ms.omschrijving}"/></td>  
			    		</tr>
					</c:forEach>
				</table>
				<button class="btn" id="addNewMeterstandButton">ADD</button>
			</div>
		</div>
    </jsp:body>
</t:genericpage>