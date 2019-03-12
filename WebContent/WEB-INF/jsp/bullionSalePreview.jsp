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
<script language="javascript"  type="text/javascript"> 
$(document).ready(function() {
	var invoiceNo = $.getUrlVar('invc');
	var test = confirm("'"+invoiceNo+"' Invoice Saved Successfully \n\n Click OK to view Print preview, Cancel for Sales list...");
	if(test){
		window.location.replace('sales.htm');
		window.open('bullioninc.htm?bullion_billno='+invoiceNo,'','');	
	}else{
		window.location.replace('sales.htm');
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