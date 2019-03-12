<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<body>
<div id="mask">
</div>  
<div id="boxes">
<div id="newCustomer" class="window dialog">
    <a href="#" class="close">X</a>
        <b class="Popup_heading">New Customer Form</b>
         <div class="content">
         	
			<table id="formJournal2" cellpadding="0" border="0" cellspacing="0" align="center" >
			<tr class="form_cat_head">
				<td colspan=4><B>Create Customer</B></td>
			</tr>
			<tr>
				<td class="label">Customer Name</td>
				<td><input type="text" name="customerName" id="popcustomerName"  maxlength="50" class="nameCapitalize categorytext noEnterSubmit" value="" /></td>
				<td class="label">Opening Balance</td>
				<td><input type="text" name="openingBal" id="openingBal" class="categorytext noEnterSubmit" value="0.00" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
			</tr>
			<tr>
				<td class="label">Opening Type</td>
				<td><select id="openingType" class="noEnterSubmit">
				    <option value="Debit" label="Debit" > Debit </option> 
					<option value="Credit" label="Credit" > Credit </option>
				</select></td>
				
				<td class="label">Address</td>
				<td><input type="text" name="Address1" id="Address1" class="categorytext noEnterSubmit" value=""/></td>
			</tr>
			<tr>
				<td colspan="4" id ="POSCustErrorMsg"></td>
			</tr>
			<tr>
				<td colspan=4 align="center">
				<input type="button" value="Save" id="saveCustomer" class="button_style" />
				<input type="button" value= "Cancel" class="button_style"></input>	
				</td>
			</tr>
			</table>
        </div> 
    </div>
 </div> 
<!---------------------------------------->
<div id="col-form" align="left">
	<div class="boxed">
	<div class="title" >Create Jewel Repair</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<c:url var="addUrl" value="formjewelfix.htm" />
	<form:form modelAttribute="jewelfix" action="${addUrl}" method="post" class="formStyle">
	<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>
		
	</div>
	<br />
	<div class="smsg summary" style="color:#3EA99F;"></div>	
	<table id="formJewelfix" cellpadding="0" border="0" cellspacing="0" align="center">
	<tr class="form_cat_head"><td colspan="4">Jewel Repair Information</td></tr>	
	<tr>
			<td class="label">Order No*</td>
			<td><form:input path="orderNO" readonly="true"/></td>
			<input id="orderNOS" type="hidden" name="orderNOS" value="${orderNo}" class="textfield" />
			<td class="label"></td>
			<td></td>
	</tr>
	
	<tr>
		<td class="label">Customer Name<a name="modal" id="custSalesPopup" class ="add_new" href="#newCustomer" title="Add New Customer">Add</a></td>
		<td>
				<form:select path="customerName" id="poscustomer" class="custName">
				<form:option value="" label="Select"></form:option>
				<c:forEach var="ledger" items="${customername}">
				<form:option  value="${ledger.ledgerName}">
				</form:option>
				</c:forEach>
				</form:select>
		</td>
		
		<td class="label">*Received From Customer</td><td><form:input path="issueDate" id="date"/></td>		
				
	</tr>
	
	<tr>
		<!-- <td><font color="red"><form:errors path="customerName"/></font></td>
	    <td><font color="red"><form:errors path="issueDate"/></font></td> -->
	</tr>	
	<tr>		
		<td class="label">Smith Name</td>
		<td>
				<form:select path="smithName">
				<form:option value="" label="Select"></form:option>
				<c:forEach var="ledger" items="${suppliername}">
				<form:option  value="${ledger.ledgerName}">
				</form:option>
				</c:forEach>
				</form:select>
		</td>
		
		<td class="label">Metal Type*</td><td>
		
			<form:select path="metalType" id="bullion">	
			<form:option value="">select</form:option>		
			<form:option value="Gold Ornaments" label="Gold Ornaments"/>			
			<form:option value="Silver Ornaments" label="Silver Ornaments"/>			
			</form:select>
		</td>			
	</tr>	
		
		<tr>		
		<!--  <td><font color="red"><form:errors path="smithName"/></font></td>
		<td><font color="red"><form:errors path="metalType"/></font></td> --> 
		</tr>						
		
		<tr>  					 
			<td class="label">Item Name</td>
			<td><form:input path="jewelName" class="nameCapitalize"/></td>
			
			<td class="label">Issued Gross Weight</td>
			<td><form:input path="issuedGrossWeight" id="gwt" onkeyup="extractNumber(this,3,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
			<!-- <td><form:errors path="issuedGrossWeight" cssStyle="color:red" /></td> -->								
  		</tr>	
  					
		<tr>		
		<td  class="label">Number of Pieces</td><td><form:input path="numberOfPieces" id="nop" onkeypress="return ValidateNumberKeyPress(this, event);" onfocus="receivedtype();" /></td>
		<td class="label">No.Of Stone/Diamond</td><td><form:input path="noOfstone" id="nod" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td> 		
		</tr>
		
		<tr class="form_cat_head"><td colspan="4">Repair Details  </td></tr>
						
		<tr>
			<td class="label">New Item Name</td><td><form:input path="newitemName" class="nameCapitalize"/></td>		
			<td class="label">New Stone Applied</td><td><form:input path="newStoneApplied" id="nst" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
		</tr>
		<tr>
			<td class="label">Gross Weight After Fixing</td><td><form:input path="grossWtAfterFixing" id="wt" onkeyup="extractNumber(this,3,true),sum_cost();" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>			
  			<td  class="label">Balance Repair Weight</td><td><form:input path="balanceWt"  id="f_bwt" readonly="true"/></td>
  		</tr>
		
		<tr>
			<td class="label">Gross Weight Added</td><td><form:input path="grossWtAdded" id="f_gwt" readonly="true"  onkeyup="sum_cost();"  /></td>																
			<td class="label">Wastage Grams</td><td><form:input path="wastage" id="wst" onkeyup="extractNumber(this,3,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
		</tr>
			
			<!--<form:errors path="rate" cssStyle="color:red" />-->	
		<tr>
			<td class="label">Rate (GOld/SILVER)</td><td><form:input path="rate" id="r" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
			<td class="label">Service Charge</td><td><form:input path="serviceCharge" id="ser_cost" onkeyup="extractNumber(this,2,true), sum_cost();" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
		</tr>
		 
		<tr>
			<td class="label">Polishing Charge</td><td><form:input path="polishCharge" id="pol_cost" onkeyup="extractNumber(this,2,true), sum_cost();" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>			
			<td class="label">Stone Cost</td><td><form:input path="stoneCost" id="stone_cost" onkeyup="extractNumber(this,2,true), sum_cost();" onkeypress="return ValidateNumberKeyPress(this, event);"  /></td>
			</tr>
		
		<tr>
			<td class="label">Metal Cost</td><td><form:input path="metalCost" id="metal_cost" onkeyup="extractNumber(this,2,true), sum_cost();" readonly="true"/></td>			
			<td class="label">Total Cost</td>
			<td><form:input path="totalCost" onkeypress="return ValidateNumberKeyPress(this, event);" id="tot_cost" readonly="true"/></td>		
						
			</tr>
		
		<tr>
		<td class="label">Smith Cost</td>
		<td><form:input path="smithCost" onkeypress="return ValidateNumberKeyPress(this, event);" id="smith_cost" onkeyup="sum_cost(),extractNumber(this,2,true);"/></td>		
		
		<td class="label">Delivered Date</td>		
		<td><form:input path="receivedDate" id="date1" /></td>		
		
		
		</tr>	
		
		<!-- <td><font color="red"><form:errors path="receivedDate"/></font></td> -->
		
		<tr>	
			<td class="label">Description</td>
			<td><form:input path="description"/></td>			
			<td class="label" >Status</td>
			<td>
				<form:select path="status" id="statusType">			
				<form:option value="Received" label="Received"/>
				<form:option value="Delivered" label="Delivered"/>				
				</form:select>
			</td>
			<form:input type="hidden" path="repairId"/>	
			<form:input type="hidden" id="mode" path="mode" />
			<input type="hidden" id="errorName" value="${errorType}"/>					
		</tr>	
		
		<tr>
		    <td bgcolor="#33FFFF" colspan="4" align="center">	
		    <input name="printInvoice" type="hidden" id="printInvoice" value="${printInvoice}"/>
		    <input name="printPreviewId" type="hidden" id="printId" value="${receiptNO}"/>
		    <input type="submit" id="insert" name="insert" value="Insert" class="button_style"/>	    
            <input type="submit" id="update" name="update" value="Update" onclick="return updateStatus();" class="button_style"/>          	
          	<input type="submit" id="cancel" name="cancel" value= "Cancel" class="button_style"/>
          	  </td>       	
        </tr>
				
	</table>
	</form:form>
	</div>
	</div>
</div>
<script language="javascript" type="text/javascript" > 

/*** new script added to allow only _ and - for customer name field on 25-2-13 after bug is posted ****/
$('#popcustomerName').keyup(function() {
	 $(this).val($(this).val().replace(/[^A-Za-z0-9\s_-]/g,''));
});

var URIloc = $(location).attr('href');
var uripath = URIloc.split('/')[4];
var errorType=$("#errorName").val();
var sType  = $('#statusType').val();
var Update_Id = $('#repairId').val();

$(document).ready(function(){
	$("#date").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	
	var recdate=$("#date").val();
	if(recdate=="")
	{
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate =myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
		$("#date").val(prettyDate);
	}
	
	$("#date1").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		
	
	if(errorType=="updateError"){	
	
		$("#insert").hide();
		$("#update").show();
		$('[id^=nst]').attr('disabled', false);
		$('[id^=wt]').attr('disabled', false);
		$('[id^=wst]').attr('disabled', false);
		$('[id^=stone_cost]').attr('disabled', false);
		$('[id^=ser_cost]').attr('disabled', false);
		$('[id^=pol_cost]').attr('disabled', false);
		$('[id^=smith_cost]').attr('disabled', false);
		$('[id^=date1]').attr('disabled', false);
		$('[id^=r]').attr('disabled', false);	
	} 
	
	if(errorType=="insertError"){
		$("#insert").show();
		$("#update").hide();
	}
	

	if(uripath.indexOf("formjewelfixing.htm") >= 0) {		
		$('#update').show();
		$("#insert").hide();
	}
	
	if(Update_Id != 'null')
	{		
		if(sType == 'Delivered' && errorType != "updateError")
		{
			//$('#update').hide();
			$("#mode").val("Edit");
			$("#update").show();
		}
		else if(errorType == "updateError")
		{
			$("#update").show();
		}
	}
	
	$('#insert').click(init);
	$('#update').click(initUpdate);
	$('#cancel').click(initUpdate);
	$('#nst').focus();
	$("#r").keyup(sum_cost);
	$("#gwt").keyup(sum_cost);
	$("#nst").keyup(sum_cost);
	$("#nop").keyup(sum_cost);
	$("#nod").keyup(sum_cost);
	$("#wst").keyup(sum_cost);
});


function init(){	
	var ZERO = 0;
	if($("#gwt").val().length == 0){
		$("#gwt").val(ZERO.toFixed(3));		
		$("#wt").val(ZERO.toFixed(3));
		$("#nod").val(ZERO.toFixed(2));
	}
	checkNOP();
	initUpdate();
}

function checkNOP(){
	if($("#nop").val().length == 0){
		$("#nop").val(0);
	}
}

function initUpdate(){
	
	var ZERO = 0;
	if($("#wt").val().length == 0){		
		$("#wt").val(ZERO.toFixed(3));
		$('[id^=f]').val(ZERO.toFixed(3));		
	}	
		
	if($("#wst").val().length == 0){
		$("#wst").val(ZERO.toFixed(2));
	}	
	
	if($("#pol_cost").val().length == 0){
		$("#pol_cost").val(ZERO.toFixed(2));
	}
				
	if($("#r").val().length == 0){
		$("#r").val(ZERO.toFixed(2));		
	}
	
	if($("#stone_cost").val().length == 0){ 
		$("#stone_cost").val(ZERO.toFixed(2));		
	}
	
	if($("#metal_cost").val().length == 0){
		$("#metal_cost").val(ZERO.toFixed(2));		
	}
	if($("#smith_cost").val().length == 0){
		$("#smith_cost").val(ZERO.toFixed(2));		
	}
	if($("#r").val().length == 0){
		$("#r").val(ZERO.toFixed(2)); 
		$("#tot_cost").val(ZERO.toFixed(2));
	}
			
	checkNOP();
}

if(uripath.indexOf("formjewelfix.htm") >=0 )
{
	$('#insert').show();
	$("#update").hide();
	$('[id^=nst]').attr('disabled', true);
	$('[id^=wt]').attr('disabled', true);
	$('[id^=wst]').attr('disabled', true);
	$('[id^=stone_cost]').attr('disabled', true);
	$('[id^=ser_cost]').attr('disabled', true);
	$('[id^=pol_cost]').attr('disabled', true);
	$('[id^=smith_cost]').attr('disabled', true);
	$('[id^=date1]').attr('disabled', true);
	$('[id^=r]').attr('disabled', true);
}

if(uripath.indexOf("formjewelfixing.htm") >=0 )
{	
	$('[id^=insert]').hide();
	$('[id^=update]').show();
	$('[id^=nst]').attr('disabled', false);
	$('[id^=wt]').attr('disabled', false);
	$('[id^=wst]').attr('disabled', false);
	$('[id^=stone_cost]').attr('disabled', false);
	$('[id^=ser_cost]').attr('disabled', false);
	$('[id^=pol_cost]').attr('disabled', false);
	$('[id^=smith_cost]').attr('disabled', false);
	$('[id^=date1]').attr('disabled', false);
	$('[id^=r]').attr('disabled', false);		
}			
	
if(document.getElementById("orderNO").value == ""){
	var order_Code =document.getElementById("orderNOS").value;
	document.getElementById("orderNO").value= order_Code;		
}
		

</script>
<script type="text/javascript" src="script/repair_sum.js"></script>
<script type="text/javascript" src="script/formValidations.js"></script>
<script type="text/javascript" src="script/js_CustomerCreation.js"></script>
</body>		
</html>