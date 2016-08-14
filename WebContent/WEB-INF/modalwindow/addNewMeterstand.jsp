<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% SimpleDateFormat sdf= new SimpleDateFormat ("dd-MM-yyyy"); %>

<div class="modal fade" tabindex="-1" role="dialog" id="addNewMeterstandenModal">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Nieuwe meterstand</h4>
      </div>
      <div class="modal-body">
        <p>Voege nieuwe meterstand toe</p>
        <form id="nieuwMeterstandForm" class="form-horizontal" method="POST" action="newMeterstand">
    <%-- TODO: meenemen selectie van pagina showMeterstanden (alles, gas, water, ..) --%>
        	<div class="form-group">
        		<label for="deDatum" class="col-sm-2 control-label">Datum</label>
        		<div class="col-sm-10">
        			<input type="date" id="deDatum" name="deDatum" placeholder="<%= sdf.format(new Date()) %>"/>
        		</div>
        	</div>
        	<div class="form-group">
        		<label for="deMeter" class="col-sm-2 control-label">Metertype</label>
        		<div class="col-sm-10">
	        		<select name="deMeter" id="deMeter">
	<%--         		TODO: vullen met waarden van Metersoorten en vullen met huidige selectie--%>
						<c:forEach items="${theMetersoorten }" var="soort">
							<option value="${soort.id }">${soort.metersoort }</option>
<!-- 		        			<option value="1">1</option> -->
<!-- 		        			<option value="2">2</option> -->
<!-- 		        			<option value="3">3</option> -->
	        			</c:forEach>
	        		</select>
        		</div>
        	</div>
        	<div class="form-group">
        		<label for="deStand" class="col-sm-2 control-label">Meterstand</label>
        		<div class="col-sm-10">
        			<input type="number" id="deStand" name="deStand"/>
        		</div>
        	</div>
        	<div class="form-group">
        		<label for="deOpmerking" class="col-sm-2 control-label">Opmerking</label>
        		<div class="col-sm-10">
        			<input type="text" id="deOpmerking" name="deOpmerking" class="form-control"/>
        		</div>
        	</div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Sluit</button>
        <button type="button" class="btn btn-primary" id="bewaarEnSluitKnopNieuweMeterstand">Bewaar en sluit</button>
      </div>
    </div>
  </div>
</div>
<script>
$('#bewaarEnSluitKnopNieuweMeterstand').on("click", function(){
	$.post("newMeterstand",
			{	deDatum: $('#deDatum')[0].value,
				deMeter: $("#deMeter")[0].value,
				deStand: $("#deStand")[0].value,
				deOpmerking: $("#deOpmerking")[0].value
			}
	).done(function(){
		location.reload();
	})
});
</script>