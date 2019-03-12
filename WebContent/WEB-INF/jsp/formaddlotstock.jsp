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
	<div class="title">Add Lot Stock</div>
	<div class="content"> <!-- Content Open -->
	<%
if(session.getAttribute("username")==null)
{
	response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
<c:url var="saveUrl" value="formaddlotstock.htm" />
<form:form method="post" action="${saveUrl}" commandName="lotAddStock" class="formStyle">
<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>		
</div> 
	<table id="formLedger" cellpadding="0" border="0" cellspacing="0" align="center" >
	  <tr class="form_cat_head"><td colspan="4">Lot Item Information</td></tr>
			<tr>
				<td class="label">Item Name</td>
				<td width="280"><form:input path="lotItemName" id="LotItemName" readonly="true"/></td>
				<td class="label" >Lot Type</td>
				<td><form:input path="lotType" value="Add Lot Stock" readonly="true"/></td>
			</tr>
			<tr>
				<td class="label">Gross Weight</td>
				<td width="280"><form:input path="grossWeight" id="gwt" onkeyup="extractNumber(this,3,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
				<td class="label" >Net Weight</td>
				<td><form:input path="netWeight" id="nwt" readonly="true"/></td>
			</tr>
			<tr>
				<td class="label">Date</td>
				<td width="280"><form:input path="lotDate" id="LotStockDate"/></td>
				<td class="label" >Quantity</td>
				<td><form:input path="quantity" id="qty" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
			</tr>
			<tr>
				<td class="label">Description</td>
				<td width="280"><form:input path="description" /></td>
				<td class="label" ></td>
				<td></td>
			</tr>
			
			<tr>
			<td bgcolor="#33FFFF"  colspan="4" align="center">
		          <input type="submit" name="insert" value="Insert" id="insert" class="button_style"/></input>
		          <input type="submit" name="update" value="Update" id="update" class="button_style"/></input>
		          <input type="submit" name="cancel" value= "Cancel" id="cancel" class="button_style"></input>
         		  <input type="hidden" id="errorName" value="${errorType}"/>
         	</td>            	
         	<form:input type="hidden" path="lotId"/>      	
         	</tr>
</table>
</form:form>
</div>
</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	
	var ParamIdVal = window.location.search;
	//alert("5"+ParamIdVal );
	if(ParamIdVal == "?lt_type=gold"){
		$("#LotItemName").val("GoldLotStock");
	}
	if(ParamIdVal == "?lt_type=silver"){
		$("#LotItemName").val("SilverLotStock");
	}
	
	$("#LotStockDate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	 
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var prettyDate =myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
	
	if($('#LotStockDate').val() == ''){
		$("#LotStockDate").val(prettyDate);		
	}	
	
	
	//** Insert/Update Buttons hide Show  **//
	var URIloc = $(location).attr('href');
	var uripath = URIloc.split('/')[4];
	
	var errorType=$("#errorName").val();
	
	if(uripath.indexOf("formaddlotstock.htm") >= 0){
		$("#update").hide();
		$("#insert").show();
	}
	if(uripath.indexOf("viewlotstockadd.htm") >= 0) {
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
	$('#qty').keyup(nondecimalvalidation);
});

$('#gwt').keyup(function() {
	   $('#nwt').val(this.value);	   
});

function init(){
	
	var ZERO = 0;
	if($("#gwt").val().length == 0){
		$("#gwt").val(ZERO.toFixed(3));
		$("#nwt").val(ZERO.toFixed(3));		
	}
	if($("#qty").val().length == 0){
		$("#qty").val(ZERO);				
	}
}


function nondecimalvalidation() {
	$(this).val(function(index, oldVal) {
		return oldVal.replace(/[^\d-]/g, '');
	});
}
</script>
</body>
</html>