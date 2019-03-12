<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Lot Stock</title>
</head>
<body>
<div id="col-form"> <!-- Col-Form Open Div-->
	<div class="boxed" align="left"> <!-- Boxed Open Div -->
	<div class="title">Return Lot Stock</div>
	<div class="content"> <!-- Content Open -->
	<%
	if(session.getAttribute("username")==null)
	{
	response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
	}
	%>
	<div class="summary" align="center">Lot Stock Return Form</div>
	<br />
	<br /> 
	<form:form method="post" commandName="lotStockReturn" action="formreturnlotstock.htm" class="formStyle">
		<div id="form_error">
			<div><a class="close"><img src="images/closebox.png"></a></div><br>
			<span class="error"><form:errors path="*" id="errorDisplay"/></span>
		</div>
		<table id="formLedger" cellpadding="0" border="0" cellspacing="0" align="center" >
		 		<tr class="form_cat_head"><td colspan="4">Lot Item Information</td></tr>
				<tr>
					<td class="label">Item Name *</td>
					<td width="280"><form:input path="lotItemName" id="LotItemNameReturn" readonly="true"/></td>
					<td class="label" >Lot Type</td>
					<td><form:input path="lotType" value="Return Lot Stock" readonly="true"/></td>
				</tr>
				<tr>
					<td class="label">Gross Weight *</td>
					<td width="280"><form:input path="grossWeight" id="gwtReturn" onkeyup="extractNumber(this,3,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
					<td class="label" >Net Weight *</td>
					<td><form:input path="netWeight" id="nwtReturn" readonly="true"/></td>
				</tr>
				<tr>
					<td class="label">Date *</td>
					<td width="280"><form:input path="lotDate" id="LotStockDateReturn"/></td>
					<td class="label" >Qty (Set) *</td>
					<td><form:input path="quantity" id="qtyReturn" onkeyup="extractNumber(this,0,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
				</tr>
				<tr>
					<td class="label">Description</td>
					<td width="280"><form:input path="description" maxlength="250"/></td>
					<td class="label" ></td>
					<td></td>
				</tr>
				
				<tr>
				<td bgcolor="#33FFFF"  colspan="4" align="center">
			          <input type="submit" name="insert" value="Insert" id="insert" class="button_style"/></input>
			          <input type="submit" name="update" value="Update" id="update" class="button_style"/></input>
			          <input type="submit" name="cancel" value= "Cancel" id="cancel" class="button_style"></input>
			          <input type="hidden" id="errorName" value="${errorType}"/>		
			          <form:input type="hidden" path="lotId"/>  
	         	</td>
	         	</tr>
	</table>
</form:form>
</div>
</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	
	//** List Hide Show  **//
	var ParamIdVal = window.location.search;
	if(ParamIdVal == "?lt_type=gold"){
		$("#LotItemNameReturn").val("GoldLotStock");
	}
	if(ParamIdVal == "?lt_type=silver"){
		$("#LotItemNameReturn").val("SilverLotStock");
	}
	
	//** Date Picker  **//
	$("#LotStockDateReturn").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var prettyDate =myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
	
	if($('#LotStockDateReturn').val() == ''){
		$("#LotStockDateReturn").val(prettyDate);		
	}	
	
	
	//** Insert/Update Buttons hide Show  **//
	var URIloc = $(location).attr('href');
	var uripath = URIloc.split('/')[4];
	
	var errorType=$("#errorName").val();
	
	if(uripath.indexOf("formreturnlotstock.htm") >= 0){
		$("#update").hide();
		$("#insert").show();
	}
	if(uripath.indexOf("viewlotstockreturn.htm") >= 0) {
		$("#update").show();
		$("#insert").hide();
	}
	
	if(errorType=="updateError"){
		$("#update").show();
		$("#insert").hide();
	}
	else if(errorType=="insertError"){	
		$("#update").hide();
		$("#insert").show();
	}
		
	$('#insert').click(init);
	$('#cancel').click(init);
	$('#update').click(init);
	
	$("#gwtReturn").keyup(function() {
		   $("#nwtReturn").val(parseFloat($("#gwtReturn").val()).toFixed(3));	   
	});
			
});
	
function init(){
	var ZERO = 0;
	if($("#gwtReturn").val().length == 0){
		$("#gwtReturn").val(ZERO.toFixed(3));
		$("#nwtReturn").val(ZERO.toFixed(3));		
	}
	if($("#qtyReturn").val().length == 0){
		$("#qtyReturn").val(ZERO);				
	}
}
</script>
</body>
</html>