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
	
	var jobOrderNO = $.getUrlVar('jobOrderNO');
	var joborderStatus = $.getUrlVar('joborderStatus');
	var test = confirm("Joborder NO :"+jobOrderNO+" Saved Successfully... \n\n  Click OK to view Print preview.");
	var url ="";
	if(joborderStatus=="Issued"){
		url = "jobOrderIssueVoucher.htm?issueId="+jobOrderNO;
	}else if(joborderStatus=="Received"){
		url = "jobOrderReceiptVoucher.htm?jobordervoucherId="+jobOrderNO;
	}
	if(test == true){
		window.location.replace('joborder.htm');
		window.open(url,'','');
	}else{
		window.location.replace('joborder.htm');
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