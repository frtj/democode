<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
<title>Iframe example</title>
</head>
<body>

<a href="/async-demo/">Tilbake</a>
<h3>Iframe:</h3>

<div id="my_div"></div>

<iframe id="comet-frame" style="display: none;"></iframe>

<script type="text/javascript">
function myLog( str )
{	
	$("#my_div").html(str + '<br>' + $("#my_div").html());
}

$(document).ready(function() {	
	$("#comet-frame").attr("src", "/async-demo/AsyncIframeServlet?cmd=subscribeIframe");
	//myLog($("#comet-frame").attr('src'));
});

var app = {
	update : function(data) {
		myLog(data.message);
	}
};
</script>
</body>
</html>