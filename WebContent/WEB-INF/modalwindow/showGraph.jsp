<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="modal fade" tabindex="-1" role="dialog" id="showGraphModal">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Show graph</h4>
      </div>
      <div class="modal-body">
        <p>Show graph</p>
        <form id="showGraphForm" class="form-horizontal" method="GET" action="ShowMeterverbruikgraph">
        	<div class="form-group">
        		<label for="metersoort" class="col-sm-2 control-label">Meter</label>
        		<div class="col-sm-10">
        			<select name="ms" id="deMeter">
						<c:forEach items="${theMetersoorten }" var="soort">
							<option value="${soort.id }">${soort.metersoort }</option>
	        			</c:forEach>
	        		</select>
        		</div>
        	</div>
        	<div class="form-group">
        		<label for="jaar1" class="col-sm-2 control-label">Jaar1</label>
        		<div class="col-sm-10">
	        		<select name="jaar" id="jaar1">
	        		<!-- TODO: dynamic list -->
							<option value="2016">2016</option>
	        		</select>
        		</div>
        	</div>
        	        	<div class="form-group">
        		<label for="jaar2" class="col-sm-2 control-label">Jaar2</label>
        		<!-- TODO: dynamic list -->
        		<div class="col-sm-10">
	        		<select name="jaar2" id="jaar2">
							<option value="2016">2016</option>
	        		</select>
        		</div>
        	</div>
        	        	<div class="form-group">
        		<label for="jaar3" class="col-sm-2 control-label">Jaar3</label>
        		<!-- TODO: dynamic list -->
        		<div class="col-sm-10">
	        		<select name="jaar3" id="jaar3">
							<option value="2016">2016</option>
	        		</select>
        		</div>
        	</div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Sluit</button>
        <button type="button" class="btn btn-primary" id="goGraph">Show Graph</button>
      </div>
    </div>
  </div>
</div>
<script>
$('#goGraph').on("click", function(){
	$('#showGraphForm').submit();
});
</script>