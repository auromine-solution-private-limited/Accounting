<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>

<body>  
<div id="col-one" align="left">
	<div class="boxed">
			<div class="title">Account Search</div>
			<div class="content">
			<form:form id="form4" method="get" commandName="Ledger" onsubmit="return validate_account();" action="searchledger.htm">
					<fieldset>
					<legend>Search</legend>
				 	Account Name
					<input id="ledgerName"  name="ledgerName" value="" class="textfield" />
					<input id="search" type="submit" name="search" value="Search" class="button" />
					<label id="lblledgerName"></label>					
					</fieldset>					
			</form:form>				
			</div>
	</div>
	
	<div class="boxed">
			<div class="title">Sales Estimate</div>
			<div class="content">
			<form:form id="form15" method="post" action="salesest.htm" target="new" onsubmit=" return  validate_SalesEstimate();">
			<fieldset>
			Sales Estimate		
			<input id="seic" type="text" name="seic" class="textfield" onkeydown = "return (event.keyCode!=13);"/>
			<input type="submit" class="button" value="Sales Bill" onclick=window.open"('','','scrollbars=no,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no')" />
			<label id="lbltagName"></label>
			</fieldset>
			</form:form>				
			</div>
	</div>	
	
	
	<div class="boxed">
			<div class="title">Sales Order Search</div>
			<div class="content">
				<form:form id="form2" method="get" action="searchorder.htm" modelAttribute="Orderno" onsubmit="return validate_order();">
				<fieldset>				
				Order No
				<input id="orderNo" type="text" name="salesOrderId" value="" class="textfield" onkeypress="return ValidateOrderNumberKeyPress(this, event);"/>
				<input id="inputsubmit2" type="submit" name="search2" value="Search" class="button" />							
				<label id="lblorderNo"></label>
				</fieldset>					
				</form:form>	
			</div>
	</div>
		
	<div class="boxed">
		<div class="title">Job Order Search</div>
		<div class="content">
			<form:form id="form5" method="get" action="searchsmith.htm" modelAttribute="jos" onsubmit="return validate_smith();">
				<fieldset>
				<legend>Search</legend>				
				Smith Name(JobOrderNo)
				<input id="smithName" type="text" name="smithName" value="" class="textfield" />
				<input id="inputsubmit2" type="submit" name="search3" value="Search" class="button" />							
				<label id="lblsmithName"> </label>				
				</fieldset>
				</form:form>
		</div>
	</div>
	
	<div class="boxed">
		<div class="title">Item Search</div>
		<div class="content">
		<form:form id="form7" method="get" action="searchitem.htm" modelAttribute="ItSearch" onsubmit="return validate_item();">
		<fieldset>
		<legend>Search</legend>
		Item Name
		<input id="itemName" type="text" name="itemName" value="" class="textfield" />
		Item Weight
		<input id="netWeight" type="text" name="netWeight"  class="textfield" onkeypress="return ValidateNumberKeyPress(this, event);"/>
		<input id="inputsubmit3" type="submit" name="search4" value="Search" class="button" />
		<label id="lblitemName"> </label>		
		</fieldset>
		</form:form>
		</div>
	</div>
</div>
<div id="col-form" align="left">
	<div class="boxed">
	<div class="title">Bullion Sales</div> 
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<form:form commandName="sales" action="formbullionsales.htm" method="post" class="formStyle">
	<a href="newSales.htm" class="create_or_list">Ornament Sales</a>
	<a href="newSalesReturn.htm" class="create_or_list">Sales Return</a>
	<br>
	<div class="summary">Bullion Sales</div>
	<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>
		
	</div>
	<table id="formSales" cellpadding="0" cellspacing="0" align="left">
			<tr class="form_cat_head"><td colspan="4"><B>Bullion Sales</B></td>
			</tr>
			<tr>
			<td class="label" >Customer Name</td> 
			<td>							
			<form:select path="customerName" id="customerName01">
			<option value="walk_in">Walk In</option>
			<c:forEach var="ledger" items="${name}">
			<form:option  value="${ledger.ledgerName}">
			</form:option>
			</c:forEach>
			</form:select>
			</td>
			
			<!-- <td class="label">Sales Type</td>
			<td>
				<form:input path="salesType" value="Sales" readonly="true"/>  				
			</td>-->
			<td  id="lpanNumber" class="label">Tin Number</td>
				<td><form:input id="fpan_Number" path="panNumber" value="" size="9" /></td>			
			</tr>
			<tr id="NameCitybullion1">									
				<td class="label">Name</td>
				<td><form:input id="w_Name" path="walkIn_Name" size="9"/></td>
				<td class="label">City</td>
				<td><form:input id="w_City" path="walkIn_City" size="9" /></td>						
			</tr>
			

			<tr>
			<td  class="label" >Sales Date</td>
			<td>			
					<form:input path="salesDate" id="date" />  			
			</td>
			
			<td class="label" >Salesman Name</td>
			<td>
			<form:select path="salesmanName">
			<form:option value="" label="Select"></form:option>
			<c:forEach var="salesmanname" items="${s_manname}">
			<form:option value="${salesmanname.fullName}"></form:option>
			</c:forEach>
			</form:select>
			</td>
			</tr>	
	</table>
	
	<table align="left" cellpadding="0" cellspacing="0" class="table_two1">
			<tr>
				<td class="label label1">Item Code</td>
				<td class="label label1">Item Name</td>
				<td class="label label1">Metal</td>
		   		<td class="label label1">Gross Wt.</td>
		   		<td class="label label1">Rate</td>
		   		<td class="label label1">Amount</td>
			</tr>
			
			
			<tr>			
  				<td><form:select path="itemCode" id="item_code">
				<form:option value="" label="select"></form:option>
				<form:option value="IT100002" label="Gold Bullion"></form:option>
				<form:option value="IT100007" label="Silver Bullion"></form:option>
				</form:select>
				</td>
  				<td><form:input path="itemName" id="iname"  size="10" readonly="true"/></td>  				
  				<td><form:input path="bullionType" id="btype" size="5" readonly="true"/></td>  				
  				<td><form:input path="grossWeight" id="grwt" onkeypress="return ValidateNumberKeyPress(this, event);" onkeydown="ValidateNumberKeyUp(this);" onkeyup="extractNumber(this,3,true),bullion_sale();" size="5"/></td>
  				<td><form:input path="bullionRate" id="brate" onkeypress="return ValidateNumberKeyPress(this, event);" onkeydown="ValidateNumberKeyUp(this);" onkeyup="extractNumber(this,2,true),bullion_sale();" size="5"/></td>
  				<td class="amt"><form:input path="amountAfterLess" id="amountAfterLess" size="7" readonly="true"/>
  				
  				<form:input type="hidden" path="categoryName" id="cname" size="7" readonly="true"/></td> 
  				<form:input type="hidden" path="salesId" id="cname" size="7" readonly="true"/></td> 
  				<form:input type="hidden" path="salesTypeId" />	
  				<input type="hidden" id="errorName" value="${errorType}"/>			
			</tr>
		
		
					

<!--NetAmount-->		
<tr><td colspan="4">&nbsp;</td><td>Net Amount </td>
	<td colspan="1" class="amt">
	<form:input path="netAmount" id="netamount" size="15" readonly="true"/>
	</td>
</tr>


<!--Vat-->        
<tr><td colspan="2">&nbsp;</td>
<td  bgcolor="#BBDDFF">VAT %</td>
<td><form:input type="text" path="vtax" size="7" id="vat" readonly="true"/></td>
<td>TAX Amount </td>
	<td colspan="1" class="amt">
	<form:input path="tax" id="tax" size="15" readonly="true"/>
	</td>
</tr>
		

<!--BillAmount  -->
<tr>
	<td colspan="2"></td>
	<td  bgcolor="#BBDDFF">Round Off</td>
	<td>	
		<form:input path="roundOff" size="7" id="roundOffValue"  onkeydown = "return (event.keyCode!=13);" readonly="true"/>
	
	</td>
<td>Bill Amount </td>
<td class="amt">
<form:input path="billAmount" id="billamount"  onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);"  size="15"/>

</td>
</tr>
<tr> 
<td colspan="2">&nbsp;</td>
<td ><span>Receipt Id</span></td>
<td>
		<form:select id="sid" path="receiptID" >
		<c:forEach var="receiptId" items="${journalListReceiptID}">			
		   <form:option label="${receiptId}" value="${receiptId}" >${receiptId}</form:option>
		</c:forEach>
		</form:select>
</td>

<td><span>Receipt Amount</span></td>
<td class="amt"><form:input path="receiptAmount" size="8" id="receipt_Amount" readonly="true" cssStyle="text-align:right"/></td>
			
<tr>
	<td colspan="4">&nbsp;</td>
	<td  bgcolor="#BBDDFF">Received Amount</td>
	<td class="amt">
<form:input  path="amtRecd" id="amtReceived" size="8" cssStyle="text-align:right"/>
	</td>
	</tr>
	<tr>
	<td colspan="2">&nbsp;</td>
	<td>Greetings </td>
	<td>	
		<form:textarea path="greetings"style="max-width:150px; max-height:50px;" ></form:textarea>	
	</td>
	<td><span>Balance To Pay</span></td> 
<td class="total balAmt amt"><form:input path="balToPay" id="balAmt" readonly="true" size="8" cssStyle="text-align:right"/></td>
</tr>

<tr>
	<td colspan="9" bgcolor="#33FFFF" align="center">	
		<input type="submit" name="insert" id="insert" value="Insert" class="button_style"/>	
		<input type="submit" name="update" id="update" value="Update" class="button_style"/>	
		<input type="submit" name="cancel" value="Cancel" class="button_style" ></input>			
		<form:input type="hidden" path="formType" value="Bullion Sales" />
		<form:input type="hidden" path="billNo" />
	</td>	
</tr>	
</table> 
<table>
 </table>
</form:form>
</div>
</div>
</div>
<script language="javascript" type="text/javascript"> 

var URIloc = $(location).attr('href');
var uripath = URIloc.split('/')[4];

$(document).ready(function(){
	$("#date").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var prettyDate =myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
	$("#date").val(prettyDate); 	
});


$("#billamount").keyup(function() {
	   $("#balAmt").val(this.value);	   
});

$(document).ready(function(){
	
	
	$("#insert").click(initZero);
	$("#update").click(initZero);
	// billno();
	 $("#billamount").keypress(billno).trigger('keypress');
	 $("#grwt").keypress(billno).trigger('keypress');
	 $("#brate").keypress(billno).trigger('keypress');
	 $("#customerName01").change(billno).trigger('change');
	 $("#inputsubmit2").click(Decimal_Validation); // Order no Integer field decimal Validation.
	 //$('#journalType').change(radio_ReceiptShow).trigger('change');

	 var URIloc = $(location).attr('href');
	 var uripath = URIloc.split('/')[4];
	 
	 
	 var errorType=$("#errorName").val();
	 
	 if(uripath.indexOf("viewSales.htm") >= 0){
			$("#insert").hide();
			$("#update").show();
			$('#item_code').attr('disabled',true);	
			//$("#sid").append("<option>Select</option>");
		//	var sid=$("#sid").val();
			//if(sid!=0){
			///	$("#sid option").find("]").remove();
			//}
			
			
		}

		if(uripath.indexOf("formbullionsales.htm") >= 0){
			$("#insert").show();
			$("#update").hide();
			 $('#customerName01').click(getAjaxReceipt);
		}
		
		
		if(errorType == "insertError") {
			$("#insert").show();
			$("#update").hide();
		}else if(errorType == "updateError") {
			$("#insert").hide();
			$("#update").show();
		}
		
	 
});

function initZero(){
	
	var ZERO = 0;
	
	if($("#roundOffValue").val().length == 0){
		$("#roundOffValue").val(ZERO.toFixed(2));
	}
	
	if($("#billamount").val().length == 0){
		$("#billamount").val(ZERO.toFixed(2));
	}
	
	if($("#brate").val().length == 0 || $("#brate").val() === "NaN" || $("#brate").val() === "Infinity"){
		$("#brate").val(ZERO.toFixed(2));
	}
	
	if($("#grwt").val().length == 0){
		$("#grwt").val(ZERO.toFixed(3));		
	}
	
	$('#item_code').attr('disabled',false);	
}

$('#item_code').change(function() {
	
	var item_code = $('#item_code').val();	
	
	if(item_code =='IT100002')
	{
		 $('#iname').val('Gold Bullion');
		 $('#cname').val('Gold Bullion');
		 $('#btype').val('Gold');	
	}	
	
	 if(item_code =='IT100007')
	{
		$('#iname').val('Silver Bullion');
	 	$('#cname').val('Silver Bullion');
		$('#btype').val('Silver');	
	}
	 
	 return false;	 
	
});

function Decimal_Validation(){
	var orderNo = $("#orderNo").val();
	
	if(!orderNo.match(/[0-9]+/)){
		$("#lblorderNo").html("<span class='error'>Please Enter Only Numbers</span>");
		$("#orderNo").val("");
		return false;
	}
	
	if(orderNo == null || orderNo.length=="0" || orderNo == '' || orderNo ==  'NaN'){		
	}else{
		var temp = parseFloat(orderNo);
		$("#orderNo").val(temp.toFixed(0));
	}
}


 	
</script>
<script type="text/javascript" src="script/bullion_sale.js"></script>
<script type="text/javascript" src="script/jquery-all-manuscript.js"></script>
</body>
</html>