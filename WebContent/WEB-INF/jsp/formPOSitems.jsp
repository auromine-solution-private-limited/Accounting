<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>POS ITEM CREATION</title>
</head>
<body>

<div id="mask">
   </div> 
    <div id="boxes">
<div  class="window dialog loading" style="text-align:center; color:navy; margin: 0 auto;">
<img alt="Loading PLease Wait...." src="images/loading.gif">
<br/><span class="progressBar" id="spaceused4"></span>
 <br/><b>Generating Tag Please Wait....</b>
    </div>
   </div>
 
<div id="col-form" align="left">
<div class="boxed">
<div class="title">POS Items Creation</div>
<div class="content">
<%
if(session.getAttribute("username")==null)
{
	response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
<c:url var="addUrl" value="formPOSitems.htm?Id=${Id}"/>
<form:form name="form1" id="form1" modelAttribute="POSitems" method="POST" action="${addUrl}" class="formStyle"  onsubmit="return submit_form1()">		
	<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>
		
	</div>
	<table id="formJournal" cellpadding="0" border="0" cellspacing="0" align="center" >
		<tr class="form_cat_head">
			<td colspan=4 id="cat_info" align="center"></td>
		</tr>
		<tr>
			<td class="label">Item Name*</td>
			<td><form:input path="itemName" id="itemname" maxlength="25" class="nameCapitalize noEnterSubmit"/></td>
	
			<td class="label">Print Name*</td>
			<td><form:input path="printName" id="prinname" class="nameCapitalize noEnterSubmit"/></td>
		</tr>
		<tr>
		<td class="label">Company</td> 
			<td><form:input path="companyName" class="noEnterSubmit"/></td>
			<td class="label">Date*</td>
			<td><form:input path="stockDate" id="date" class="noEnterSubmit"/></td>		
		
		</tr>
		<tr>

			<td class="label">Quantity / Set</td>
			<td><form:input path="openingStockSet" id="ops_set" class="noEnterSubmit" onkeyup="extractNumber(this,0,true)" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
			<td class="label">Pieces Per Set*</td>
			<td><form:input path="piecesPerQty" id="qty" class="noEnterSubmit"  onkeyup="extractNumber(this,0,true)" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		</tr>
		<tr>
			<td class="label">Total Pieces*</td>
			<td><form:input path="totalPieces" id="totpieces" readonly="true" class="noEnterSubmit" onkeyup="extractNumber(this,3,true)" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
			<td class="label">Vat %</td>
			<td><form:input path="vatPercentage" id="vatAmt" class="noEnterSubmit" onkeyup="extractNumber(this,2,true)" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>			
		</tr>
		<tr>
		
				<td class="label">Category Name</td>
			<td><form:input path="categoryName" id="category" readonly="true" class="noEnterSubmit"/></td>
		
			<td class="label">Discount %</td>
			<td><form:input path="discountPercentage" id="discountAmt" class="noEnterSubmit"  onkeyup="extractNumber(this,2,true)" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>

		</tr>
		<tr class="form_cat_head">
			<td colspan=4><B>Value Addition</B></td>
		</tr>
		<tr>
			<td class="label">Cost Rate*</td>
			<td><form:input path="costRate" id="costRt" class="noEnterSubmit"  onkeyup="extractNumber(this,2,true)" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
			<td class="label">Margin %*</td>
			<td><form:input path="margin" id="margin" class="noEnterSubmit"  onkeyup="extractNumber(this,2,true)" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		</tr>
		<tr>
			<td class="label">Sale Rate</td>
			<td><form:input path="salesRate" id="salesRate" readonly="true" class="noEnterSubmit"  onkeyup="extractNumber(this,3,true)" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
			<td class="label">MRP*</td>
			<td><form:input path="MRP" id="mrp" class="noEnterSubmit"  onkeyup="extractNumber(this,2,true)" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		</tr>
		
		<tr>
			
			<td class="label">Rol</td>
			<td><form:input path="ROL" id="ROL" class="noEnterSubmit"  onkeyup="extractNumber(this,0,true)" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
			<td class="label">Description</td>
			<td><form:input path="description" class="noEnterSubmit"/></td>
			
		</tr>
		
		<tr style="display:none">
		<td colspan="4">
		<input type="hidden" id="errorName" value="${errorType}"/>
		<input type="hidden" value="${Id}"  disabled="disabled"/>
		<form:hidden path="itemId"/>
		<c:forEach var="items" items="${poscategory}">
		<input type="hidden" id="discount" name="discountPercentage" value="${items.discountPercentage}" disabled="disabled"/>
		<input type="hidden" id="catName" name="categoryName" value="${items.categoryName}" disabled="disabled"/>
		<input type="hidden" id="vat" name="vatPercentage" value="${items.vatPercentage}" disabled="disabled"/>
		</c:forEach>
		</td>
		</tr>
		<tr>
			<td align="center">
			<label class="barcodelabel"><b>Want to Print Barcode ?</b></label></td>
			<td><form:checkbox path="isPrint" value="yes" class="noEnterSubmit isPrint" /></td>
		   <td class="label"><label class="barcodelabel">Current Print Format</label></td>
		   <td><form:input readonly="true" path="posPrintFormat" value="${posPrintFormat}" class="isPrint"/></td>
		</tr>
		<tr>
			<td colspan="4" align="center">
			<input type="submit" name="insert" value="Insert" id="insert"  title=".loading" class="button_style"/>
			<input type="submit" name="update" value="Update" id="update" class="button_style"/> 
			<input type="submit" name="cancel" value= "Cancel" id="cancel" class="button_style"></input>	
			</td>
		</tr>
	</table>
</form:form>
</div>
</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	var URIloc = $(location).attr('href');
	var uripath = URIloc.split('/')[4];
	var errorType=$("#errorName").val();
	
	if(uripath.indexOf("formPOSitems.htm") >= 0){
		$('#update').hide();
		$('#insert').show();
		}
	if(errorType == "updateError"){	
		$('#insert').hide();
		$('#update').show();
		$('.barcodelabel').hide();
		$('.isPrint').hide();
		$('#itemname').attr("readonly",true);
	}
	if(uripath.indexOf("viewPOSitems.htm") >= 0){
		$('#update').show();
		$('#insert').hide();
		$('.barcodelabel').hide();
		$('.isPrint').hide();
		
		$('#itemname').attr("readonly",true);
	}
	
	$('#update').click(function()
	{		
		$('#itemname').show();		
	});
	
	if(errorType=="insertError"){
		$('#update').hide();
		$('#insert').show();
	}
	
	/****prevent defaulr submit when enter key is pressed****/
	$('.noEnterSubmit').keypress(function(e){
		if ( e.which == 13 ) e.preventDefault();
	});
	
	$("#date").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var prettyDate =myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
		$("#date").val(prettyDate);
		
		
//categoryName
		var category=$("#catName").val();
		if(category!=null)
			{
			$("#category").val(category);	
			}
//Discount
		var discount=$("#discount").val();
		if(discount!=null)
			{
			$("#discountAmt").val(discount);	
			}
//vatpercentage
		var vatAmt=$("#vat").val();
		if(vatAmt!=null)
			{
			$("#vatAmt").val(vatAmt);	
			}
		/********If Vat field is empty confirmation alert messagebox*******/
		$("#insert").click(function(){
			if ($("#vatAmt").val() == '0.00'||$("#vatAmt").val() == '') {			
				return confirm("THE VAT FIELD IS ZERO!");
			}
		});	
		$("#insert").click(initZero);
		$("#cancel").click(initZero);
		
		$("#itemname").keypress(function(e) {
			var code = (e.keyCode ? e.keyCode : e.which);					

			if (code == 44){						
				return false;
			}
		});
		
		var qty = $("#ops_set").val();   
		if(qty == 0)
		{
		$("#cat_info").append("Item Creation For ").append($("#category").val());
		}
		else
		$("#cat_info").append("Item Updation For ").append($("#category").val());

});

function initZero(){
	var ZERO = 0;
	if($("#ops_set").val().length == 0){
		$("#ops_set").val(ZERO);
	}
	if($("#qty").val().length == 0){
		$("#qty").val(ZERO);
	}
	if($("#discountAmt").val().length == 0){
		$("#discountAmt").val(ZERO);
	}
	if($("#totpieces").val().length == 0){
		$("#totpieces").val(ZERO);
	}
	if($("#vatAmt").val().length == 0){
		$("#vatAmt").val(ZERO);
	}
	if($("#costRt").val().length == 0){
		$("#costRt").val(ZERO);
	}
	if($("#margin").val().length == 0){
		$("#margin").val(ZERO);
	}
	if($("#mrp").val().length == 0){
		$("#mrp").val(ZERO);
	}
	if($("#ROL").val().length == 0){
		$("#ROL").val(ZERO);
	}
}

$('#itemname').keyup(function() {
	   $('#prinname').val(this.value);	   
});
</script>
<script type="text/javascript">
var form_submitted = false;

function submit_form1 ( )
{
  if ( form_submitted )
  {
    //alert ( "Your form has already been submitted. Please wait..." );
    return false;
  }
  else
  {
    form_submitted = true;
    return true;
  }
}


</script>

<script type="text/javascript" src="script/positem.js"></script>
</body>
</html>