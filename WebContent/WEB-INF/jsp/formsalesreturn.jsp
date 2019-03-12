<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<div id="col-one" align="left">
	<div class="boxed">
			<div class="title">Account Search</div>
			<div class="content">
			<form:form id="form4" method="get" commandName="Ledger" onsubmit="return validate_account();" action="searchledger.htm">
					<fieldset>
					<legend>Search</legend>
				 	Account Name (or) Id
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
				Smith Name (or) JobOrder No
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
	<div class="title">Sales Return</div>
	<div class="content" style="height:600px;">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	<a href="newSales.htm" class="create_or_list"><b>Ornament Sales</b></a>
	<a href="formbullionsales.htm" class="create_or_list"><b>Bullion Sales</b></a>
	<br>
			
	<form:form method="POST" action="SalesReturn.htm" commandName="sales" class="formStyle">	
	<form:hidden path="salesId"/>
	<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>
		
	</div>
	<table id="formSalesReturn"  cellpadding="0" border="0" cellspacing="0" align="left" >
	
			<tr class="form_cat_head"><td  colspan="4"><B>Sales Return</B></td>
			</tr>
		<tr ><td  class="label">Sales Bill No</td><td><form:input path="billNo" id="bill_no"/></td>
		
			
			<td class="label">Sales Date</td>
			<td><form:input path="salesDate" id="date"/></td>
			</tr>
			
			<tr>
			<td class="label">Customer Name</td>
			<td>
			<form:select path="customerName" id="customerName">
			<form:option value="" label="----select----"></form:option>
			<c:forEach var="ledger" items="${name}">
			<form:option  value="${ledger.ledgerName}">
			</form:option>
			</c:forEach>
			</form:select>
			</td>
			<td  class="label">Salesman Name</td>
			<td>
			<form:select path="salesmanName" id="salesmanName">
			<form:option value="" label="----select----"></form:option>
			<c:forEach var="salesnamname" items="${s_manname}">
			<form:option  value="${salesnamname.fullName}">
			</form:option>
			</c:forEach>
			</form:select>
			</td>
			</tr>
			<!--  <tr><td class="label">Transaction Type</td>-->
		
	</table>
	
	<table align="left"  cellpadding="0" border="0" cellspacing="0" align="center" class="table_two" >
			<tr>
			<td  class="label label1">Item Code</td>	
	   		<!-- <td  bgcolor="#BBDDFF" width="5">Karat</td -->
	   		<td class="label label1">Pcs/Set</td>
	   		<td class="label label1">Gross Wt.</td>
	   		<td  class="label label1">Net Wt.</td>
	   		<td  class="label label1">VA%</td>
	   		<td  class="label label1">MC</td>
	   		<td  class="label label1">HM</td>
	   		<td class="label label1">Stone Cost.</td>
	   		<td class="label label1">Rate</td>
			<td  class="label label1">Amount</td>
			</tr>
			<tr class="DeSelectClass">
			
			<td>
			<!--<form:input path="itemCode" size="9" id="icode"/>-->
			 
			<form:select path="itemCode" id="icode1" class="itemcode_class" cssStyle="width:115px;">
			<form:option value="" label="select"></form:option>
			<form:option value="IT100001" label="Gold Ornament"></form:option>
			<form:option value="IT100002" label="Gold Bullion"></form:option>
			<form:option value="IT100006" label="Silver Ornament"></form:option>
			<form:option value="IT100007" label="Silver Bullion"></form:option>
			</form:select>
			</td>
			<form:input type ="hidden" path="itemName" size="12" id="itemname1" class="char_class" readonly="true" cssStyle="width:80px;"/>
			<form:input type ="hidden" path="categoryName" size="7" class="char_class" id="catname1" readonly="true"/> 
			<form:input type ="hidden" path="bullionType" size="5" id="metal1" class="char_class" readonly="true"/> 
			<td><form:input path="numberOfPieces" class="cal_dep pcs_class" size="5" id="nop1" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,0,true)"/> </td> 
			<td><form:input path="grossWeight" class="cal_dep wt_class" id="gwt1" onkeyup="extractNumber(this,3,true);" onkeypress="return ValidateNumberKeyPress(this, event);"  size="6"/> </td>
			<td><form:input path="netWeight" class="cal_dep wt_class" size="6" id="nwt1" onkeyup="salesReturnFIrstRow1(), extractNumber(this,3,true), ValidateNumberKeyUp(this);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="salesReturnVA1" class="cal_dep wt_class" size="6" id="salesReturnVA1" onkeyup="extractNumber(this,3,true), salesReturnFIrstRow1(),ValidateNumberKeyUp(this);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="salesReturnMC1" class="cal_dep wt_class" size="6" id="salesReturnMC1" onkeyup="extractNumber(this,3,true), salesReturnFIrstRow1(),ValidateNumberKeyUp(this);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="salesReturnHC1" class="cal_dep wt_class" size="6" id="salesReturnHC1" onkeyup="extractNumber(this,3,true), salesReturnFIrstRow1(),ValidateNumberKeyUp(this);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="stoneCost" size="7" class="cal_dep currency_class" id="stcost1" onkeyup="extractNumber(this,2,true), salesReturnFIrstRow1();" onkeypress="return ValidateNumberKeyPress(this, event);" />  </td>
			<td><form:input path="bullionRate" class="cal_dep currency_class" size="6" id="rate1" onkeyup="extractNumber(this,2,true),salesReturnFIrstRow1(),ValidateNumberKeyUp(this);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<form:input type ="hidden" path="stone"  size="7" id="stoneDesc1"/>  
			<td><form:input path="amountAfterLess" size="7" id="amount1" class="amt cal_dep currency_class" readonly="true"/>  </td>
			</tr>
		
			<tr class="DeSelectClass">
			<td>
			<!--<form:input path="itemCode1" size="9" id="icode1" />--> 
			<form:select path="itemCode1" id="icode2" class="itemcode_class" onclick="return notEmpty(document.getElementById('icode1'), 'Please Select Enter First row of Items');" cssStyle="width:115px;">
			<form:option value="" label="select"></form:option>
			<form:option value="IT100001" label="Gold Ornament"></form:option>
			<form:option value="IT100002" label="Gold Bullion"></form:option>
			<form:option value="IT100006" label="Silver Ornament"></form:option>
			<form:option value="IT100007" label="Silver Bullion"></form:option>
			</form:select>
			</td>
			<form:input type="hidden" path="itemName1" size="12" id="itemname2" class="char_class" readonly="true" cssStyle="width:80px;" /> 
			<form:input type="hidden" path="categoryName1" size="7" id="catname2" class="char_class" readonly="true" />
			<form:input type="hidden" path="bullionType1" size="5" id="metal2" readonly="true" class="char_class"/>
			<td><form:input path="numberOfPieces1" class="cal_dep pcs_class" size="5" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,0,true)"/> </td> 
			<td><form:input path="grossWeight1" class="cal_dep wt_class" size="6" onkeyup="extractNumber(this,3,true);" id="gwt2" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="netWeight1" class="cal_dep wt_class" size="6" id="nwt2" onkeyup="extractNumber(this,3,true), salesReturnFIrstRow2();" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="salesReturnVA2" class="cal_dep wt_class" size="6" id="salesReturnVA2" onkeyup="extractNumber(this,3,true), salesReturnFIrstRow2(),ValidateNumberKeyUp(this);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="salesReturnMC2" class="cal_dep wt_class" size="6" id="salesReturnMC2" onkeyup="extractNumber(this,3,true), salesReturnFIrstRow2(),ValidateNumberKeyUp(this);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="salesReturnHC2" class="cal_dep wt_class" size="6" id="salesReturnHC2" onkeyup="extractNumber(this,3,true), salesReturnFIrstRow2(),ValidateNumberKeyUp(this);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="stoneCost1" class="cal_dep currency_class" size="7" id="stcost2" onkeyup="extractNumber(this,2,true),salesReturnFIrstRow2();" onkeypress="return ValidateNumberKeyPress(this, event);" />  </td>
			<td><form:input path="bullionRate1" class="cal_dep currency_class"  size="6" id="rate2" onkeyup="extractNumber(this,2,true),salesReturnFIrstRow2();" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<form:input type="hidden" path="stone1" size="7"/>  
			<td><form:input path="amountAfterLess1" size="7" id="amount2" class="amt cal_dep currency_class" readonly="true"/>  </td>
			</tr>
			
			<tr class="DeSelectClass">
			<td>
			<!-- <form:input path="itemCode2" size="9" id="icode2" />-->
			 <form:select path="itemCode2" id="icode3" class="itemcode_class" onclick="return notEmpty(document.getElementById('icode2'), 'Please Select Enter Second row of Items');" cssStyle="width:115px;">
			<form:option value="" label="select"></form:option>
			<form:option value="IT100001" label="Gold Ornament"></form:option>
			<form:option value="IT100002" label="Gold Bullion"></form:option>
			<form:option value="IT100006" label="Silver Ornament"></form:option>
			<form:option value="IT100007" label="Silver Bullion"></form:option>
			</form:select>
			 </td>
			<form:input type="hidden" path="itemName2" size="12" id="itemname3" class="char_class" readonly="true"  cssStyle="width:80px;"/> 
			<form:input type="hidden" path="categoryName2" size="7" id="catname3" class="char_class" readonly="true" />
			<form:input type="hidden" path="bullionType2" size="5" id="metal3" class="char_class" readonly="true" /> 
			<td><form:input path="numberOfPieces2" class="cal_dep pcs_class" size="5" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,0,true)"/> </td> 
			<td><form:input path="grossWeight2" class="cal_dep wt_class" size="6" onkeyup="extractNumber(this,3,true);" id="gwt3" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="netWeight2" class="cal_dep wt_class" size="6" id="nwt3" onkeyup="extractNumber(this,3,true), salesReturnFIrstRow3();" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="salesReturnVA3" class="cal_dep wt_class" size="6" id="salesReturnVA3" onkeyup="extractNumber(this,3,true), salesReturnFIrstRow3(),ValidateNumberKeyUp(this);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="salesReturnMC3" class="cal_dep wt_class" size="6" id="salesReturnMC3" onkeyup="extractNumber(this,3,true), salesReturnFIrstRow3(),ValidateNumberKeyUp(this);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="salesReturnHC3" class="cal_dep wt_class" size="6" id="salesReturnHC3" onkeyup="extractNumber(this,3,true), salesReturnFIrstRow3(),ValidateNumberKeyUp(this);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="stoneCost2" class="cal_dep currency_class" size="7" id="stcost3" onkeyup="extractNumber(this,2,true),salesReturnFIrstRow3();" onkeypress="return ValidateNumberKeyPress(this, event);" />  </td>
			<td><form:input path="bullionRate2" class="cal_dep currency_class" size="6" id="rate3" onkeyup="extractNumber(this,2,true),salesReturnFIrstRow3();" onkeypress="return ValidateNumberKeyPress(this, event);"  /> </td>
			<form:input type="hidden" path="stone2" size="7" /> 
			<td><form:input path="amountAfterLess2" size="7" id="amount3" class="amt cal_dep currency_class" readonly="true"/>  </td>
			</tr>
			
			<tr class="DeSelectClass">
			<!-- <form:input path="itemCode3" size="9" id="icode3" />-->
			<td>
			<form:select path="itemCode3" id="icode4" class="itemcode_class" onclick="return notEmpty(document.getElementById('icode3'), 'Please Select Enter Third row of Items');" cssStyle="width:115px;">
			<form:option value="" label="select"></form:option>
			<form:option value="IT100001" label="Gold Ornament"></form:option>
			<form:option value="IT100002" label="Gold Bullion"></form:option>
			<form:option value="IT100006" label="Silver Ornament"></form:option>
			<form:option value="IT100007" label="Silver Bullion"></form:option>
			</form:select>
			 </td>
			<form:input type="hidden"path="itemName3" size="12" id="itemname4" class="char_class" readonly="true" cssStyle="width:80px;" /> 
			<form:input type="hidden" path="categoryName3" size="7" id="catname4" class="char_class" readonly="true" />
			<form:input type="hidden" path="bullionType3" size="5" id="metal4" class="char_class" readonly="true" /> 
			<td><form:input path="numberOfPieces3" class="cal_dep pcs_class	" size="5" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,0,true)"/> </td> 
			<td><form:input path="grossWeight3" class="cal_dep wt_class" size="6" onkeyup="extractNumber(this,3,true);" id="gwt4" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="netWeight3" class="cal_dep wt_class" size="6" id="nwt4" onkeyup="extractNumber(this,3,true), salesReturnFIrstRow4();" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="salesReturnVA4" class="cal_dep wt_class" size="6" id="salesReturnVA4" onkeyup="extractNumber(this,3,true), salesReturnFIrstRow4(),ValidateNumberKeyUp(this);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="salesReturnMC4" class="cal_dep wt_class" size="6" id="salesReturnMC4" onkeyup="extractNumber(this,3,true), salesReturnFIrstRow4(),ValidateNumberKeyUp(this);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="salesReturnHC4" class="cal_dep wt_class" size="6" id="salesReturnHC4" onkeyup="extractNumber(this,3,true), salesReturnFIrstRow4(),ValidateNumberKeyUp(this);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
				<td><form:input path="stoneCost3" class="cal_dep currency_class" size="7" id="stcost4" onkeyup="extractNumber(this,2,true), salesReturnFIrstRow4();" onkeypress="return ValidateNumberKeyPress(this, event);" />  </td>
			<td><form:input path="bullionRate3" class="cal_dep currency_class" size="6" id="rate4" onkeyup="extractNumber(this,2,true), salesReturnFIrstRow4();" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<form:input type="hidden" path="stone3" size="7" /> 
			<td><form:input path="amountAfterLess3" size="7" id="amount4" class="amt cal_dep currency_class" readonly="true"/> </td>
			</tr>
			
							<tr class=" always_show" >
		<td class="label">Payment Mode*</td>
		<td><form:select path="paymentMode" id="paymentmode">
				<form:option value="Cash" label="Cash"/>
				<form:option value="Credit" label="Credit"/>
			</form:select>
		</td>
		<td class="label"></td>
		<td class="label"></td>
		<td class="label" colspan="2"><input type="checkbox" name="addToSales" id="addToSales" value="addToSales" style="width:10px;"/>
		Add this To Sales<form:input type="hidden" path="salesReturnStatus"  name="salesReturnStatus" id="salesReturnStatus" />
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		</tr>
		
			<tr><td colspan="6">&nbsp;</td><td colspan="2">Sub Total </td><td colspan="2">
				<form:input path="totalAmount" id="subtotal" class="amt_total cal_dep tcurrency_class" readonly="true"/> </td></tr>
            <tr><td colspan="6">&nbsp;</td><td colspan="2">VAT </td><td colspan="2">
				<form:input path="tax" id="tax" class="amt_total cal_dep tcurrency_class" onkeyup="extractNumber(this,2,true)" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td></tr>
			<tr><td colspan="6">&nbsp;</td><td colspan="2">Net Amount </td><td colspan="2">
				<form:input path="netAmount" class="amt_total cal_dep tcurrency_class" id="netamount" readonly="true"/> </td></tr>
			<tr><td colspan="6">&nbsp;</td><td colspan="2">Trade Discount</td><td colspan="2">
				<form:input path="totalLess" class="amt_total cal_dep tcurrency_class" id="tradediscount" onkeyup="extractNumber(this,2,true)" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td></tr>
			<tr><td colspan="6">&nbsp;</td><td colspan="2">Bill Amount </td><td colspan="2">
				<form:input path="billAmount" class="amt_total cal_dep tcurrency_class"  id="billamount" readonly="true"/> </td>
			</tr>
	
  			<tr><td colspan="14" align="center">
			<input type="submit" name="insert" id="insert" value="Insert" class="button_style"/>
			<input type="submit" name="update" id="update" value="Update" class="button_style"/>
       		<input type="submit" name="cancel" id="cancel" value="Cancel" class="button_style"></input>
       		<input name="salesType" type="hidden"  value="Sales Return" readonly="readonly" />
			</td>
			</tr>			
	</table>
	<form:input type="hidden" path="formType" value="Sales Return" />
	<form:input type="hidden" path="salesTypeId"/>
	<input type="hidden" id="errorName" value="${errorType}"/>
	</form:form>		
	</div>
	</div>
</div>
<script type="text/javascript">

$(document).ready(function(){
	
	var URIloc = $(location).attr('href');
	var uripath = URIloc.split('/')[4];
	$("#inputsubmit2").click(Decimal_Validation); // Order no Integer field decimal Validation.
	addToSalesCheckBox();
	$("#insert").click(toenableReadonly);
	$("#update").click(toenableReadonly);
	//addToSalesCheckBox();
	checkPaymentReadonly();
	
	if(uripath.indexOf("viewSales.htm") >=0 ){
		if($("#salesReturnStatus").val() == 'Sold'){
			$('#update').hide();
		}else{
			$('#update').show();
		}
	}	
	$("#tax").keyup(netAmtCalc);
	$("#tradediscount").keyup(billAmtCalc);
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

function addToSalesCheckBox(){
	// Manually Check the Checkbox				
	if($("#salesReturnStatus").val() == 'Yes' || $("#salesReturnStatus").val() == 'Sold'){
		$("#addToSales").attr("checked",true);			
	}
	// On click of Checkbox
	$("#addToSales").click(function(){
		/** if Status sold making checkbox disabled **/
		if($("#salesReturnStatus").val() == 'Sold'){
			$("#addToSales").attr("disabled",true);
			return false;
		}
	if ($("#addToSales").is(":checked")) {
		$('#paymentmode').val('Credit');
		$("#salesReturnStatus").val('Yes');
		$("#paymentmode").attr("disabled",true);
		alert("'Added to Sales' and Payment Mode set to 'Credit'");	
	}else{
		$('#paymentmode').val('Cash');			
		$("#salesReturnStatus").val('No');
		$("#paymentmode").attr("disabled",false);
		alert("'Not Added to Sales' and Payment Mode set to 'Cash'");
	}
	
});
}

if(errorType == "insertError"){	
	if ($("#addToSales").is(":checked")) {
		$("#paymentmode").attr('disabled', true);
	}
}
function toenableReadonly(){
	$("#paymentmode").removeAttr("disabled");
}

function checkPaymentReadonly(){
	if ($("#addToSales").is(":checked")) {
		$('#paymentmode').val('Credit');
		$("#paymentmode").attr("disabled",true);
	} 
}
</script>

<script type="text/javascript" src="script/salesreturn.js"></script>
</body>
</html>