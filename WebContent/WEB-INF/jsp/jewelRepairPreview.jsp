<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="script/jquery.min.js"></script>
<script type="text/javascript" src="script/jquery-ui.min.js"></script>
</head>
<body>
Loading...
<script language="javascript"  type="text/javascript"> 
$(document).ready(function() {
	
	var jewelRepairId = $.getUrlVar('jewelrepaiId');
	var orderStatus = $.getUrlVar('orderStatus');
	var test = confirm("Jewel Repair ID:"+jewelRepairId+" Saved Successfully... \n\n  Click OK to view Print preview.");
	var url ="";
	if(orderStatus=="Received"){
		url = "oldjewelfromcustomer.htm?jewelrepaiId="+jewelRepairId;
	}else if(orderStatus=="Delivered"){
		url = "newjeweltocustomer.htm?customerId="+jewelRepairId;
	}
	if(test == true){
		window.location.replace('jewelfix.htm');
		window.open(url,'','');
	}else{
		window.location.replace('jewelfix.htm');
	}
	
});

$.extend({
		  getUrlVars: function(){
		    var vars = [], hash;
		    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
		    for(var i = 0; i < hashes.length; i++)
		    {
		      hash = hashes[i].split('=');
		      vars.push(hash[0]);
		      vars[hash[0]] = hash[1];
		    }
		    return vars;
		  },
		  getUrlVar: function(name){
		    return $.getUrlVars()[name];
		  }
});
</script>
</body>
</html>