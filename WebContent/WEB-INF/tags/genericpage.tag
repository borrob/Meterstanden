<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
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