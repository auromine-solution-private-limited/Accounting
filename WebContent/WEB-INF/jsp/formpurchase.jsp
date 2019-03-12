       <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<body>
<div id="col-form" >
	<div class="boxed" >
	<div class="title" >Purchase</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<div class="tab_form">
	<c:url var="addUrl" value="/formpurchase.htm" />
	<form:form name="form1" id="form1" modelAttribute="purchase" method="POST" action="${addUrl}" class="formStyle">		
			<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>
	</div>
			<table id="formPurchase" cellpadding="0" border="0" cellspacing="0" align="center" >
			
			<tr class="form_cat_head">
				<td colspan=4>Purchase	Information</td>
			</tr>
			<tr>
				<td class="label">Purchase Type</td>
				<td><form:select path="purchaseType" id="purchasetype">
					<form:option value="Purchase"label="Purchase"/>
					<form:option value="Purchase Return"label="Purchase Return"/>
					</form:select></td>
				
				<td class="label">SupplierName*</td>
				<td>
					<form:select id="supplierName" path="supplierName">
					<form:option value="Walk-in" label="Walk-in"></form:option>
					<c:forEach var="ledger" items="${suppliername}">
					<form:option  value="${ledger.ledgerName}">
					</form:option>
					</c:forEach>
					</form:select></td>
			</tr>
			<tr >
				<td class="label">Purchase BillNo*</td>
				<td><form:input path="purchasebillNO" id="purchasebillNO" onkeyup="extractNumber(this,0,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
				<td class="label">Purchase Date* (dd/mm/yyyy)</td>
				<td><form:input path="purchaseDate" id="date"/></td>
			</tr>
			<tr >
			
			<td class="label">Bullion Type</td>
			<td><form:select path="bullionType" id="bullion1" onchange="bulliontype1(),sum();">
			<form:option value="" label="Select"></form:option>
			<form:option value="Gold Ornaments" label="Gold Ornaments"/>
			<form:option value="Silver Ornaments" label="Silver Ornaments"/>
			<form:option value="Gold Bullion" label="Gold Bullion"/>
			<form:option value="Silver Bullion" label="Silver Bullion"/>
			<form:option value="Gold Exchange" label="Gold Exchange"/>
			<form:option value="Silver Exchange" label="Silver Exchange"/>
			
			<!-- Added for SGH Client -->
			
			<form:option value="Old Gold Coin" label="Old Gold Coin"/>
			<form:option value="Old Silver Coin" label="Old Silver Coin"/>
			<form:option value="Old Gold Pure Bullion" label="Old Gold Pure Bullion"/>
			<form:option value="Old Silver Pure Bullion" label="Old Silver Pure Bullion"/>
			</form:select>
			</td>
			<td class="label">Item Code</td>
			<td><form:input path="itemCode" id="code1" readonly="true"/></td>
								
		</tr>	
		<tr class="form_disable always_show">
			<td class="label">Item Name*</td>
			<td>
			<form:input path="itemName" id="itemname1" readonly="true"/>
			</td>
			
			<td class="label">Item Qty(Set)</td>
			<td><form:input path="numberOfPieces" id="qtyset" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
		</tr>
			
		<tr class="form_disable always_show" >
			<td class="label">Gross Weight</td>
			<td><form:input path="grossWeight" id="wt" maxlength="10" onkeyup="extractNumber(this,3,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
			<td class="label">Net Weight</td>
			<td><form:input path="netWeight" id="f" maxlength="10"  readonly="true"/></td>			
		</tr>
		
		<tr  class="form_disable mytext2">	
			<td class="label">Melting</td>
			<td><form:input path="melting" id="m" maxlength="10" class="zeroValidationCur" onkeyup="extractNumber(this,2,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
			
			<td class="label">Touch</td>
			<td><form:input path="touch" id="t" maxlength="10" class="zeroValidationCur" onkeyup="extractNumber(this,2,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
		</tr>
		<tr   class="form_disable mytext form_cat_head">
			<td colspan=4>Less Stone Information</td>
		</tr>
		
		<tr  class="form_disable mytext" >
			<td class="label">Less In :
				<form:radiobutton path="less" id="lessGrams" value="grams" label="Grams" class="radio" />
				<form:radiobutton path="less" id="lessPer" value="per" label="%" class="radio" /></td>
			<td><form:input path="lessValue" id="lessValue" class="zeroValidationCur" maxlength="10" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
			<td class="label" ><span id="lessConvert"></span></td>
			<td><form:input path="lessValue2" style="background:none; border:none;" readonly="true" id="lessValue2" /></td>			 
		</tr>
		
		<tr class="form_disable mytext">
			<td class="label">Dust (Grams)</td>
			<td><form:input path="stoneWeight" id="t2" class="zeroValidationCur" maxlength="10" onkeyup="extractNumber(this,3,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
			<td class="label">Diamond St Name</td>
	        <td><form:input path="diamondStone" id="dSt" maxlength="50"/></td>		
	    </tr>
	    
        <tr  class="form_disable mytext">
        	<td class="label">Diamond/Stone Wt</td>
	        <td><form:input path="diamondStoneWt" id="dWt" maxlength="10" class="zeroValidationWT" onkeyup="extractNumber(this,3,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
	        <td class="label">Diamond St Amt</td>
	        <td><form:input path="stoneAmount" id="s" class="zeroValidationCur" maxlength="10" onkeyup="extractNumber(this,2,true),sum();"/></td>
		</tr>	
		
		<tr  class="form_disable mytext1 form_cat_head">
			<td colspan=4>Stone Information <span id="headPrimaryStone"></span> <span id="headSecondaryStone"></span></td>
		</tr>
		
		<tr class="form_disable mytext1" >	
        	<td  class="label">PrimaryStone</td>		    
			<td><form:select path="primaryStone" id="pst">
			<form:option value="0" label="Select"/>
			<form:option value="Precious" label="Precious"/>
			<form:option value="SemiPrecious" label="SemiPrecious"/>
			<form:option value="Diamond" label="Diamond"/>
			<form:option value="Emerald" label="Emerald"/>
			<form:option value="Pearls" label="Pearls"/>
			<form:option value="Ruby" label="Ruby"/>
			<form:option value="Sapphire" label="Sapphire"/>
			<form:option value="Stones" label="Stones"/>
			<form:option value="Topaz" label="Topaz"/>
			</form:select>
			</td>			
			<td class="label">Stone Wt(gm)</td>
			<td><form:input path="primaryStoneWt"  id="p_swt" maxlength="10" class="zeroValidationWT" onkeyup="sum(), extractNumber(this,3,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		</tr>
        <tr class="form_disable mytext1">              
			<td class="label">Stone Pcs</td>
			<td><form:input path="primaryStonePcs" id="p_spcs" maxlength="10" class="zeroValidationPcs" onkeyup="sum();" onkeypress="return ValidateIntegerNumberKeyPress(this, event);"/></td>
			
			<td class="label">Stone Rate Per Carat</td>
			<td><form:input path="primaryStoneRate" class="zeroValidationCur" id="p_srate" maxlength="10" onkeyup="sum(), extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		</tr>
		
		<tr  class="form_disable mytext1">
			<td class="label">SecondaryStone</td>
			<td><form:select path="secondaryStone" id="sst" >
			<form:option value="0" label="Select"/>
			<form:option value="Precious" label="Precious"/>
			<form:option value="SemiPrecious" label="SemiPrecious"/>
			<form:option value="JD" label="JD"/>
			<form:option value="KashmireStones" label="KashmireStones"/>
			<form:option value="Kundan" label="Kundan"></form:option>
			</form:select>
			</td>		
			<td class="label">Stone Wt(gm)</td>
			<td><form:input path="secondaryStoneWt"  id="s_swt" class="zeroValidationWT" maxlength="10" onkeyup="extractNumber(this,3,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		</tr>
		<tr  class="form_disable mytext1">	
			<td class="label">Stone Pcs</td>
			<td><form:input path="secondaryStonePcs" id="s_spcs" class="zeroValidationPcs" maxlength="10" onkeyup="extractNumber(this,0,true),sum();" onkeypress="return ValidateIntegerNumberKeyPress(this, event);"/></td>
			
			<td class="label">Stone Rate Per Carat</td>
			<td><form:input path="secondaryStoneRate" id="s_srate" class="zeroValidationCur" maxlength="10" onkeyup="extractNumber(this,2,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		</tr>
		<tr class="form_disable mytext1 mytext" >
			<td class="label">Total Stone Amount</td>
			<td><form:input path="stoneTotalAmount" id="stTotAmt" class="zeroValidationCur" readonly="true"/></td>
			<td class="label"></td>
			<td></td>	
		</tr>
		
		<tr class="form_disable form_cat_head always_show" >
				<td colspan=4><B>Cost Information</B></td>
		</tr>
		<tr class="form_disable always_show" >
		<td class="label">Payment Mode*</td>
		<td><form:select path="paymentMode" id="payment_mode">
				<form:option value="Credit" label="Credit"/>
				<form:option value="Cash" label="Cash"/>
			</form:select>
		</td>
		<td class="label purTosal"></td>
		<td class="purTosal"></td>
		<td class="label purMc">MC Amount</td>
		<td class="purMc"><form:input type="text" path="mcAmt" maxlength="15" onkeyup="extractNumber(this,2,true),sum();" id="pMcAmt" /></td>
		<td class="purTosal1" colspan="2"><input type="checkbox" name="addToSales" id="addToSales" value="addToSales" style="width:10px;"/>
		Add the Purchase To Sales<form:input type="hidden" path="exchangePurchase" name="exchangePurchase" id="exchange_purchase" /></td>
	
		</tr>
		<tr class="form_disable always_show" >
		 	<td class="label">Rate (GOld/SILVER)</td>
			<td><form:input path="rate" id="r" onkeyup="extractNumber(this,2,true),sum(), extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
		 
			<td class="label">Purchase Amount*</td>
			<td><form:input path="purchaseAmount" id="amount" class="zeroValidationCur" readonly="true"/></td>
		</tr> 
		
		<tr class="form_disable always_show" >
			<td class="label">Vat Percentage</td>
			<td><form:input path="vatPercentage" id="vt" maxlength="10" class="zeroValidationCur" onkeyup="extractNumber(this,2,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
			
			<td class="label">Vat Amount</td>
			<td><form:input path="vatAmount" id="vatamount" class="zeroValidationCur" readonly="true"/></td>
		</tr>
		
		<tr class="form_disable always_show" >			
			<td class="label">Round Off</td>
			<td><form:input path="roundOff" id="round_Off" class="zeroValidationCur numField" maxlength="10" onkeyup="extractNumber(this,2,true),sum();"  onkeypress="ValidateNumberKeyUp();" onkeydown="return (event.keyCode!=13);"/></td>			
			<td class="label">Total Amount*</td>
			<td><form:input path="totalAmount" id="total" class="zeroValidationCur" readonly="true"/>
			<form:input type="hidden" path="purchaseId"/>
			<form:input type="hidden" path="purchseInvoice"/>
			<input type="hidden" id="errorName" value="${errorType}" />	</td>
		</tr>
		<tr class="form_disable always_show" >
		 	<td class="label">Description</td>
			<td><form:input path="purDescription" id="purDescription" class="noEnterSubmit"/></td>
			<td class="label"></td>
			<td></td>	
		 </tr> 
	
	<tr class="form_disable buttons_col" >
		<td colspan="4" align="center">
 			<input type="submit" name="insert" value="Insert" id="insert" class="button_style"/>			
		    <input type="submit" name="update" value="Update" id="update" class="button_style"/>
		    <input type="submit" name="cancel" value="Cancel" id="cancel" class="button_style"></input>		  
		<!-- <input type="submit" name="delete"	value="Delete" /> -->		
		</td>
	</tr>
	</table>
	</form:form>
	</div> <!-- tab-form -->
	</div>  <!-- content -->
	</div>  <!-- boxed -->
</div>  <!-- col-form -->

<script language="javascript" type="text/javascript" > 

$(document).ready(function(){	
	
	
	var URIloc = $(location).attr('href');
	var uripath = URIloc.split('/')[4];
	
	var errorType=$("#errorName").val();
	
	$("#date").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var prettyDate =myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
	
	
	if(uripath.indexOf("formpurchase.htm") >=0 ){
		$("#date").val(prettyDate);
		$('#update').hide();
		$('#insert').show();	
	}	
	
	if(errorType == "insertError"){	
		if ($("#addToSales").is(":checked")) {
			$("#payment_mode").attr("disabled",true);
		}
	}
	
	if(errorType == "updateError"){	
		$('#insert').hide();
		$('#update').show();
		exchangeBullionDisable();
	}
	
	if(uripath.indexOf("viewpurchase.htm") >=0 ){	
		$('#insert').hide();
		if($("#exchange_purchase").val() == "Sold"){
			$('#update').hide();
		}else{
			$('#update').show();
		}
		exchangeBullionDisable();
	}	
	
	$("#insert").click(commonValid);
	$("#update").click(commonValid);
	$("#cancel").click(commonValid);
		
	$("#p_swt").keyup(displayHeader);
	$("#s_swt").keyup(displayHeader);
	
	addToSalesCheckBox();
	checkPaymentReadonly();
	displayHeader();
	
});

function commonValid(){
	$('#bullion1').attr('disabled',false);
	$('#payment_mode').attr('disabled',false);
	init();
	var result = checkDate();
	if(result == false){
		alert("*Enter Valid Date");
		return false;
	}
	return checkValid();
}

function checkValid(){
	
	if($("#date").val().length ==0){
		alert("Purchase Date Can't be Empty !");
		return false;
	}
	
	if(($("#bullion1").val() == "Gold Ornaments" || $("#bullion1").val() == "Silver Ornaments") && $("#supplierName").val() == "Walk-in" ){
		alert("This transaction not allowed for Walk-In Customer !");
		return false;
	} 
	
	return true;
}

function displayHeader(){
	
	var primaryStone = $("#p_swt").val();
	var primaryKarat = parseFloat(primaryStone) * 5;
	if(primaryStone > 0){
		$("#headPrimaryStone").html("-- Primary Stone : "+parseFloat(primaryKarat).toFixed(2)+" Carat");
	}else{
		$("#headPrimaryStone").html("");
	}
	
	var secondaryStone = $("#s_swt").val();
	var secondaryKarat = parseFloat(secondaryStone) * 5;
	if(secondaryStone > 0){
		$("#headSecondaryStone").html(", Secondary Stone : "+parseFloat(secondaryKarat).toFixed(2)+" Carat");
	}else{
		$("#headSecondaryStone").html("");
	}
}

function checkPaymentReadonly(){
	if ($("#addToSales").is(":checked")) {
		$('#payment_mode').val('Credit');
		$("#payment_mode").attr("disabled",true);
	} 
}

function exchangeBullionDisable(){
	
	if($('#bullion1').val()=='Gold Exchange' ||$('#bullion1').val()=='Silver Exchange' )
	{
		$('#bullion1').attr('disabled',true);		
	}	
}

	
	function addToSalesCheckBox(){
		
		// Manually Check the Checkbox				
		if($("#exchange_purchase").val() == 'Yes' || $("#exchange_purchase").val() == 'Sold'){
			$("#addToSales").attr("checked",true);			
		}
		
		// On click of Checkbox
		$("#addToSales").click(function(){
			
			/** if Status sold making checkbox disabled **/
			if($("#exchange_purchase").val() == 'Sold'){
				$("#addToSales").attr("disabled",true);
				return false;
			}
			
		if ($("#addToSales").is(":checked")) {
			$('#payment_mode').val('Credit');
			$("#exchange_purchase").val('Yes');
			$("#payment_mode").attr("disabled",true);
			alert("'Added to Sales' and Payment Mode set to 'Credit'");	
		}else{
			$('#payment_mode').val('Cash');			
			$("#exchange_purchase").val('No');
			$("#payment_mode").attr("disabled",false);
			alert("'Not Added to Sales' and Payment Mode set to 'Cash'");
		}
		
	});
	}
	function init() 
	{
		var ZERO = 0;
		// Item Qty(Set)
		if($("#qtyset").val().length == 0){
			$("#qtyset").val(ZERO);		
		}
		
		// Gross Weight
		if($("#wt").val().length == 0){
			$("#wt").val(ZERO.toFixed(3));		
		}
		
		// Net Weight
		if($("#f").val().length == 0){
			$("#f").val(ZERO.toFixed(3));		
		}
		
		// Melting
		if($("#m").val().length == 0){
			$("#m").val(ZERO.toFixed(2));		
		}
		
		// Touch
		if($("#t").val().length == 0){
			$("#t").val(ZERO.toFixed(2));		
		}
		
		// Stone Weight(%)
		if($("#t2").val().length == 0){
			$("#t2").val(ZERO.toFixed(3));		
		}
		
		// Less %
		if($("#lessPer").val().length == 0){
			$("#lessPer").val(ZERO.toFixed(2));		
		}

		// Less Grams
		if($("#m2").val().length == 0){
			$("#m2").val(ZERO.toFixed(2));		
		}
		
		// Diamond St Wt
		if($("#dWt").val().length == 0){
			$("#dWt").val(ZERO.toFixed(3));		
		}
		
		// P Stone Wt(gm)
		if($("#p_swt").val().length == 0){
			$("#p_swt").val(ZERO.toFixed(3));		
		}
		
		// p Stone Pcs
		if($("#p_spcs").val().length == 0){
			$("#p_spcs").val(ZERO);		
		}
		
		// p Stone Rate
		if($("#p_srate").val().length == 0){
			$("#p_srate").val(ZERO.toFixed(2));		
		}
		
		// Stone Wt(gm)
		if($("#s_swt").val().length == 0){
			$("#s_swt").val(ZERO.toFixed(3));		
		}
		
		// Stone Pcs
		if($("#s_spcs").val().length == 0){
			$("#s_spcs").val(ZERO);		
		}
		
		// Stone Rate
		if($("#s_srate").val().length == 0){
			$("#s_srate").val(ZERO.toFixed(2));		
		}
		
		// Total Stone Amount
		if($("#stTotAmt").val().length == 0 || $("#stTotAmt").val() == 'NaN'){
			$("#stTotAmt").val(ZERO.toFixed(2));		
		}
		
		// Rate (GOld/SILVER)
		if($("#r").val().length == 0){
			$("#r").val(ZERO.toFixed(2));		
		}
		
		// Purchase Amount*
		if($("#amount").val().length == 0 || $("#amount").val() == 'NaN'){
			$("#amount").val(ZERO.toFixed(2));		
		}
		
		// Vat Percentage
		if($("#vt").val().length == 0){
			$("#vt").val(ZERO.toFixed(2));		
		}
		// MC Amount
		if($("#pMcAmt").val().length == 0){
			$("#pMcAmt").val(ZERO.toFixed(2));
		}
		
		// Vat Amount
		if($("#vatamount").val().length == 0 || $("#vatamount").val() == 'NaN'){
			$("#vatamount").val(ZERO.toFixed(2));		
		}
		
		// Round Off
		if($("#round_Off").val().length == 0){
			$("#round_Off").val(ZERO.toFixed(2));		
		}
		
		// Total Amount*
		if($("#total").val().length == 0 || $("#total").val() == 'NaN'){
			$("#total").val(ZERO.toFixed(2));		
		}
		// Purchase BIll No*
		if($("#purchasebillNO").val().length == 0 || $("#purchasebillNO").val() == 'NaN'){
			$("#purchasebillNO").val(ZERO);		
		}
	}

$("#qtyset").keyup(function() {
    if($(this).val().indexOf('.') !== -1) {
        var newVal = $(this).val().replace('.', '');
        $(this).val(newVal);
    }
});

function checkDate(){
	var currVal = $("#date").val();
	var regexDatePattern = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
	var dtArray = currVal.match(regexDatePattern);
	if(dtArray == null){
		return false;
	}
	var dtDay = dtArray[1];
	var dtMonth = dtArray[3];
	var dtYear = dtArray[5];
	if(dtMonth < 1 || dtMonth > 12){
		return false;
	}else if(dtDay < 1 || dtDay > 31){
		return false;
	}else if((dtMonth == 4 || dtMonth ==6 || dtMonth == 9 || dtMonth == 11) && dtDay == 31){
		return false;
	}else if(dtMonth == 2){
		var isLeap = ( dtYear %4 ==0 && (dtYear % 100 != 0 || dtYear % 400 == 0) );
		if(dtDay > 29 || (dtDay == 29 && !isLeap)){
			return false;
		}
	}
	return true;
}

/*$('#wt').keyup(function() {				 
	  $('#f').val(this.value);				
});*/

$("#form1").submit(function() {
    $(this).submit(function() {
        return false;
    });
    return true;
});
</script>
<script type="text/javascript" src="jquery-latest.js"></script> 
<script type="text/javascript" src="script/netweightsum.js"></script> 
</body>
</html>