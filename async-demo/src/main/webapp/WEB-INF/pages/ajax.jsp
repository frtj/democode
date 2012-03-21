<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
<title>Ajax example</title>
</head>
<body>

<a href="/async-demo/">Tilbake</a>
<h3>Ajax:</h3>

<div id="my_div"></div>

<script type="text/javascript">
function myLog( str )
{	
	$("#my_div").html(str + '<br>' + $("#my_div").html());
}

function makeRequest() 
{    
	var httpRequest;  
	if (window.XMLHttpRequest)     
	{        
		httpRequest = new XMLHttpRequest();    
	} else if (window.ActiveXObject) {         
		alert("shait");  
	}    
	httpRequest.onreadystatechange = function(){handleResponse(httpRequest);};    
	httpRequest.open('GET','/async-demo/AsyncAjaxServlet?cmd=subscribeAjax',true);    
	httpRequest.send(''); 
}   

function handleResponse(request) {     
	if (request.readyState == 3){
		if (request.status == 200){
			myLog("3/200/" + request.responseText.substring(request.responseText.lastIndexOf("|")));
		}else{
			myLog("3/" + request.status + "/-");
		}
	}else if (request.readyState == 4){
		if (request.status == 200){
			
			//the connection is now closed.
			myLog("4/200/ - Done");
		}else{
			myLog("4/" + request.status + "/-");
		}
	}
}

makeRequest();
</script>
</body>
</html>