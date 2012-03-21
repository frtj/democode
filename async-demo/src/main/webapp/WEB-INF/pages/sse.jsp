<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
<title>Server Side Events example</title>
</head>
<body>

<a href="/async-demo/">Tilbake</a>
<h3>SSE: </h3>

<div id="my_div"></div>

<script type="text/javascript">
function myLog( str )
{	
	$("#my_div").html(str + '<br>' + $("#my_div").html());
}

var source;
if (!!window.EventSource) {         
	source = new EventSource('/async-demo/AsyncSSEServlet?cmd=subscribeSSE');         
	source.addEventListener('message', function(e) {
		myLog(e.data);
		}, false);        
	source.addEventListener('open', function(e) {
		myLog('connection opened');
		}, false);        
	source.addEventListener('error', function(e) {
		myLog('error');
		myLog('type: ' + e.type );
		myLog('readyState: ' + e.readyState );
		if (e.readyState == EventSource.CLOSED) {
			myLog('Connection was closed');
		    // Connection was closed.
		  }
		}, false);
	source.addEventListener('closeStream', function(e) {
		myLog('closeStream');
		source.close();
		}, false);
	}  
else {         
	alert("Browser doesn't support Server-Sent Events");  
} 
</script>

</body>
</html>