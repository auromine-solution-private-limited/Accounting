<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<body>

<div id="container"><!-- Container Div Open -->
<!---------------------------------------->
<div id="col-form" align="left"><!-- Form Div Open -->
	<div class="boxed"><!-- Boxed Div Open -->
	<div class="title">CREATE SUPPLIER ACCOUNT</div>	
	<div class="content"><!-- Content Div Open -->
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<form:form method="post" commandName="ledger" action="Supplier.htm" class="formStyle">
	<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>		
	</div>
	
	<table id="formLedger"  cellpadding="0" border="0" cellspacing="0" align="center">
	  <tr class="form_cat_head"><td colspan=4>Supplier Information</td></tr>
			<tr><td class="label">Supplier Name</td><td><form:input path="ledgerName" id="supplier_name"  class="nameCapitalize" maxlength="50" onkeydown = "return (event.keyCode!=13);" /></td>
			
			<td class="label">Group Under</td>
				<td><form:input path="accountGroup" value="Sundry Creditors" readonly="true" onkeydown = "return (event.keyCode!=13);" />
			</td>
			</tr>
			<!--<tr><td colspan=3><font color="red"><form:errors path="ledgerName"></form:errors></font></td></tr>-->
			
			
			<tr><td class="label">Opening Balance</td><td><form:input path="openingBalance" maxlength="15" id="op_bal" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td class="label">Opening Type</td>
				<td>
				<form:select path="openingType" id="op_type">
					<form:option value="Credit" label="Credit"></form:option>
					<form:option value="Debit" label="Debit"></form:option>
				</form:select></td>
				
			</tr>
			
			<!-- <tr><td>Op. Gold Balance</td><td><form:input path="opGoldBalance" maxlength="15" id="op_gold" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
				<td>Op. Gold Type</td><td><form:select path="opGoldType" id="op_GoldType">
				<form:option value="Credit"></form:option>
				<form:option value="Debit"></form:option>
				</form:select>
				</td>
			</tr>
			<tr><td>Op. Silver Balance</td><td><form:input path="opSilverBalance" maxlength="15" id="op_silver" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);"  /></td>
				<td>Op. Silver Type</td><td><form:select path="opSilverType" id="op_SilverType">
				<form:option value="Credit"></form:option>
				<form:option value="Debit"></form:option>
				</form:select>
				</td>				
			</tr> -->
			
			<tr style="display:none;"><td class="label">Op. Total Balance</td><td><form:input path="opTotalBalance" id="op_TotBal" readonly="true" onkeydown = "return (event.keyCode!=13);" /></td>
				<td class="label">Op. Total Type</td><td><form:select path="opTotalType" id="op_TotalType" >
				<form:option value="Credit" label="Credit"></form:option>
				<form:option value="Debit" label="Debit"></form:option>
				</form:select>
				</td>
			</tr>
			<tr class="form_cat_head"><td colspan=4><b>Address Information</b></td></tr>
			<tr><td class="label">Primary Phone</td><td><form:input path="primaryPhone" maxlength="20" id="primary_no"  onkeydown = "return (event.keyCode!=13);" onkeypress="return ValidateNumberPhone(this, event);" /></td>
				<td class="label">Alternate Phone</td><td><form:input path="alternatePhone" maxlength="20" id="alternate_no" onkeydown = "return (event.keyCode!=13);" onkeypress="return ValidateNumberPhone (this, event);" /></td>
			</tr>
		<!--<tr><td colspan=3><font color="red"><form:errors path="primaryPhone"></form:errors></font></td></tr>  -->
			
			<tr><td class="label">Email</td><td><form:input path="email" id="emailid" onkeydown = "return (event.keyCode!=13);" />
			<span id="email_help"></span>
			</td>
				<td class="label">Website:</td><td><form:input path="website" onkeydown = "return (event.keyCode!=13);" /></td>
			</tr>
			<tr><td class="label">Address1</td><td><form:input path="address1" id="ad3" onkeydown = "return (event.keyCode!=13);" /></td>
				<td class="label">Address2</td><td><form:input path="address2"  id="ad4" onkeydown = "return (event.keyCode!=13);" /></td>
			</tr>
			<tr><td class="label">City</td><td><form:input path="city"  id="example" onkeydown = "return (event.keyCode!=13);" onkeypress="return textonly(event);"/></td>
				<td class="label">State</td><td><form:input path="state" id="state"  onkeydown = "return (event.keyCode!=13);" onkeypress="return textonly(event);"/></td>
			</tr>
			<tr><td class="label">Country</td><td><form:input path="country" id="country" maxlength="25" onkeypress="return textonly(event);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td class="label">Pincode</td><td><form:input path="pincode" maxlength="15" onkeypress="return ValidateNumberKeyPress(this, event);" onkeydown = "return (event.keyCode!=13);" /></td>
			</tr>
			
			<!--tr><td class="label">Reference/Remarks</td><td><form:input path="source" onkeydown = "return (event.keyCode!=13);" /></td>
				<td class="label">Type of Reference</td>
				<td>
				<form:select path="typeOfSource">
					<form:option value="Friend" label="Friend"></form:option>
					<form:option value="Customer" label="Customer"></form:option>
					<form:option value="Relative" label="Relative"></form:option>
					<form:option value="Supplier" label="Supplier"></form:option>
					<form:option value="Business Partner" label="Business Partner"></form:option>
					<form:option value="Others" label="Others"></form:option>					
				</form:select>
				
			    
				</td>
				
			</tr>
			<tr>
			<td class="label">Cast</td>
			<td>
			<form:select path="cast" >
			<form:option value="Hindu" label="Hindu"></form:option>	
			<form:option value="Islam" label="Islam"></form:option>
			<form:option value="Christian" label="Christian"></form:option>
			<form:option value="Jain" label="Jain"></form:option>
			<form:option value="Sikh" label="Sikh"></form:option>		
			<form:option value="Unknown" label="Unknown"></form:option>		
			</form:select>
			</td>
			<td class="label"></td>
			<td></td>
			</tr-->
			<tr class="form_cat_head" id="cmpanyHide"><td colspan="4"><span class="formexpand"></span>Company Information</td></tr>	
			<tr class="cmpanyRow"><td class="label">Company Name</td><td><form:input path="companyName" maxlength="50" onkeydown = "return (event.keyCode!=13);" /></td>
			<td class="label"></td>
			<td></td>
			</tr>	
			<tr class="cmpanyRow"><td class="label">CST Number</td><td><form:input path="cstNumber" maxlength="20" onkeydown = "return (event.keyCode!=13);" /></td>
				<td class="label">Service Tax Number</td><td><form:input path="serviceTaxNumber" maxlength="20" onkeydown = "return (event.keyCode!=13);" /></td>
			</tr>
			<tr class="cmpanyRow"><td class="label">PAN / Tin Number</td><td><form:input path="panNumber" maxlength="15" onkeydown = "return (event.keyCode!=13);"/></td>
				<td class="label">VAT Number</td><td><form:input path="vatNumber" maxlength="15" onkeydown = "return (event.keyCode!=13);"/></td>
			</tr>
			
		<!--<tr><td colspan=4>&nbsp;</td></tr>-->		

		<tr><td colspan="4" align="center">
          	<input type="submit" name="insert" value="Insert" id="insert" class="button_style"/></input>
          	<input type="submit" name="update" value="Update" id="update" class="button_style"/></input>
			<!-- <input type="submit" name="delete" value="Delete" id="delete"/></input> -->
        	<input type="submit" name="cancel" value= "Cancel" class="button_style"></input>
         	</td>
        </tr>    
           <tr style="display:none;">
			<td>Cls. Total Balance</td><td><form:input path="closingTotalBalance" id="f3" readonly="true" /></td>
			<td>Cls. Total Type</td><td><form:select path="closingTotalType" id="C">
				
				<form:option value="Credit" label="Credit" ></form:option>
				<form:option value="Debit" label="Debit"></form:option>
				</form:select></td>
				
				<td class="label">Date*</td>
				<td><form:input path="ledgerDate" id="date"/></td>
				
				<form:input type="hidden" path="ledgerId" onkeydown = "return (event.keyCode!=13);" />
				<input type="hidden" id="errorName" value="${errorType}" />		
				<form:hidden path="accountGroupCode"/>	
			</tr>
			</table>	
			</form:form>
			</div> <!-- Content Div Close -->
		</div><!-- Boxed Div Close -->
	</div><!-- Form Div Close -->
</div><!-- Container Div Close -->

<script language="javascript" type="text/javascript" > 

$(document).ready(function() {	
		
	$("#date").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var prettyDate =myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
	if($("#date").val()=='')
	{		
		$("#date").val(prettyDate);
	}
	
	var URIloc = $(location).attr('href');
	var uripath = URIloc.split('/')[4];
	var errorType=$("#errorName").val();
	
	if(errorType=="updateError"){
		$("#insert").hide();
		$("#update").show();
		$('#op_bal').attr("disabled","disabled");		
		$("#supplier_name").attr("readonly","readonly");
		$("#op_type").attr("disabled","disabled");
	}
	else if(errorType=="insertError"){		
		$("#update").hide();
		$("#insert").show();
		$('#op_bal').removeAttr("disabled");
		$('#supplier_name').removeAttr("disabled");
		$("#op_type").removeAttr("disabled");
	}
	
	if(uripath.indexOf("newSupplier.htm") >= 0){
		$('#supplier_name').focus();
		$("#update").hide();
		$("#insert").show();
		$('#op_bal').removeAttr("disabled");
		$('#supplier_name').removeAttr("disabled");
		$("#op_type").removeAttr("disabled");
	}
	if(uripath.indexOf("viewSupplier.htm") >=0 ){
		$("#insert").hide();
		$("#update").show();
		$("#supplier_name").attr("readonly","readonly");
		//$('#supplier_name').attr("disabled","disabled");
	}	
	
	/*$('#update').click(function()
	{	
		$('#op_bal').removeAttr('disabled');
		$('#supplier_name').removeAttr('disabled');
		$("#op_type").removeAttr("disabled");		
	});*/
	
	$('#insert').click(validateEmail);
	$('#update').click(validateEmail);		
	 $("#op_bal").attr("maxlength","15");
	
});

	function validateEmail(){
		var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
		var emailaddressVal = $("#emailid").val();
		var ZERO = 0;
		
		if (emailaddressVal != '') {
			$("#form_error").find('#ss').remove();
			if (!emailReg.test(emailaddressVal)) {
				$("#form_error").slideDown("fast").append('<span class="error" id="ss">Please Enter valid Email id</span>');
				return false;
			}
		}
		if($("#op_bal").val().length == 0){
			$("#op_bal").val(ZERO.toFixed(2));
			$("#op_TotBal").val(ZERO.toFixed(2));
		}		
		return true; 
	}

	$('#op_bal').keyup(function() {
	   $('#op_TotBal').val(this.value);
	});
		
	$('#primary_no').keyup(function() {
	   $('#alternate_no').val(this.value);
	});		
		
	$('#op_type').change(function() {
	   $('#op_TotalType').val(this.value);
	});	
	
	/*** new script added to allow only '_' and '-' for supplier_name field on 4-2-13****/
	$('#supplier_name').keyup(function() {
		 $(this).val($(this).val().replace(/[^A-Za-z0-9\s_-]/g,''));
    });
</script>
</body>
</html>