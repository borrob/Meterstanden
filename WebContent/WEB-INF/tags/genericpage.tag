<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
	<head>
	    <meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
	    <title>Meterstanden</title>
	
	    <link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	    
	    <script src="lib/jquery/jquery-3.1.0.min.js"></script>
	    <script src="lib/bootstrap/js/bootstrap.min.js"></script>
	    
	    
  	</head>
  	<body>
  		<div class="container">
	    	<div id="pageheader" class="page-header">
	    		<c:if test="${! empty message}">
		    		<div class="row">
	    				<div class="col-xs-12">
	    					<div class="alert alert-info alert-dismissible" role="alert">
	    						<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	    						${message}
	    					</div>
		      			</div>
		      		</div>
	    		</c:if>
   	    		<div class="row">
    				<div class="col-xs-12">
	      				<jsp:invoke fragment="header"/>
	      			</div>
	      		</div>
	    	</div>
	    	<div id="body">
	      		<jsp:doBody/>
	    	</div>
	    	<div id="pagefooter">
	      		<jsp:invoke fragment="footer"/>
	    	</div>
    	</div>
 	</body>
</html>