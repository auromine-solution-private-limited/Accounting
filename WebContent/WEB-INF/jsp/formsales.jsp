<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>

<body onLoad="onload();">
<script type="text/javascript" src="script/formsales.js"></script>
<div id="boxes">
 
   <%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
} 
%>

 <div id="mask">
</div>  
 <!-- #customer POP window code added on 4-12-12 -->
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
				<td><input type="text" name="customerName" id="popcustomerName" maxlength="50" class="categorytext noEnterSubmit" value="" /></td>
				<td class="label">Opening Balance</td>
				<td><input type="text" name="openingBal" id="openingBal" class="categorytext noEnterSubmit" value="0.00" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>				
			</tr>
			<tr>
				
				<td class="label">Opening Type</td>
				<td><select id="openingType" class="noEnterSubmit">
				    <option value="Debit" label="Debit" > Debit </option> 
					<option value="Credit" label="Credit" > Credit </option>
					</select>
				</td>			
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
 <!-- #customize your modal window here : Purchase Exchange POPUP -->
    
    <div id="newpurchaseExchange" class="window dialog">
    <a href="#" class="close">X</a>
        <b class="Popup_heading">Old Gold/Silver Exchange</b>
         <div class="content">
         
         	<!-- For Error Message Display -->
			<div id="ExPurchaseErrorMsg" ></div>
			
			<table id="formPurchase" cellpadding="0" border="0" cellspacing="0" align="center" >
				<tr class="form_cat_head">
					<td colspan=6>Purchase	Information</td>
				</tr>
				<tr>
					<td class="label">Purchase Type</td>
					<td><input type="text" name="purchaseType" id="exPurchaseType" value="Purchase" readonly="readonly" /></td>
					<td class="label">Purchase Date *</td>
					<td><input name="purchaseDate" id="exDate"/></td>
					<td class="label">Purchase BillNo </td>
					<td><input name="purchasebillNO" id="exPurchaseBillNo" onkeyup="extractNumber(this,0,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
				</tr>
				<tr>
					<td class="label">Party Name *</td>
					<td>
						<select name="supplierName" id="exSupplierName">
							<option value="Walk-in" label="Walk-in">Walk-in</option>
							<c:forEach var="ledger" items="${name}">
								<option value="${ledger.ledgerName}">${ledger.ledgerName}</option>
							</c:forEach>
						</select>
					</td>

					<td class="label">Bullion Type</td>
					<td><select name="bullionType" id="exBullionType">
							<option value="" label="Select">Select</option>
							<option value="Gold Exchange" label = "Gold Exchange" >Gold Exchange</option>
							<option value="Silver Exchange" label = "Silver Exchange">Silver Exchange</option>
							<option value="Old Gold Coin" label = "Old Gold Coin">Old Gold Coin</option>
							<option value="Old Silver Coin" label = "Old Silver Coin">Old Silver Coin</option>
							<option value="Old Gold Pure Bullion" label = "Old Gold Pure Bullion">Old Gold Pure Bullion</option>
							<option value="Old Silver Pure Bullion" label = "Old Silver Pure Bullion">Old Silver Pure Bullion</option>
						</select>
					</td>
					<td class="label">Item Code</td>
					<td><input name="itemCode" id="exItemCode" readonly="readonly"/></td>
				</tr>	
				<tr class="always_show">
					<td class="label" style="display:none">Item Name *
					<input name="itemName" id="exItemName" readonly="readonly"/></td>
					<td class="label">Item Quantity(Set) *</td>
					<td><input name="numberOfPieces" class="numField" id="qtyset" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
					<td class="label">Gross Weight *</td>
					<td><input name="grossWeight" id="exGrossWeight" maxlength="10" class="numField" onkeyup="extractNumber(this,3,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
					<td class="label">Net Weight</td>
					<td><input name="netWeight" id="exNetWeight" maxlength="10" class="numField" readonly="readonly"/></td>			
				</tr>
				<tr class="mytext form_cat_head" id="cmpanyHide">
					<td colspan=6><span class="formexpand"></span>Less Stone Information</td>
				</tr>
				<tr  class="mytext cmpanyRow" >
					<!-- <td class="label">Stone Weight(Grams)</td> -->
					<td class="label">Dust (Grams)</td>
					<td><input name="stoneWeight" id="exStoneWeight" class="zeroValidationCur numField" maxlength="10" onkeyup="extractNumber(this,3,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
					<td class="label">Less In:
					<input name="less" id="exLessGrams" type="radio" value="grams" class="radio" /> Grams 
					<input name="less" id="exLessPer" type="radio" value="per" class="radio" />% </td>
					<td><input name="lessValue" id="exLessValue" class="numField" maxlength="10" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
					<td class="label" ><span id="exLessConvert"></span></td>
					<td><input name="lessValue2" id="exLessValue2" style="background:none; border:none;" class="zeroValidationCur numField" readonly="readonly"  /></td>
				</tr>
				
				<tr  class="mytext cmpanyRow" >
					<td class="label">Diamond St Name</td>
					<td><input name="diamondStone" id="exdiamondName" maxlength="50"/></td>
					<td class="label">Diamond St Wt</td>
			        <td><input name="diamondStoneWt" id="exdiamondWt" maxlength="10" class="zeroValidationWT numField" onkeyup="extractNumber(this,3,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
			        <td class="label"></td><td></td>
				</tr>
				<tr class="mytext cmpanyRow">
				 	<td class="label">Diamond St Amt</td>
			        <td><input name="stoneAmount" id="exDiamondAmt" class="zeroValidationCur numField" maxlength="10" onkeyup="extractNumber(this,2,true);"/></td>
					<td class="label">Total Stone Amount</td>
					<td><input name="stoneTotalAmount" id="exTotalStoneAmt" class="zeroValidationCur numField" readonly="readonly"/></td>
					
					<td class="label"></td><td></td>					
				</tr>	
				<tr class="form_cat_head always_show" >
					<td colspan=6><B>Cost Information</B></td>
				</tr>
				<tr class="always_show" >
					<td class="label">Payment Mode *</td>
					<td><select name="paymentMode" id="payment_mode">
							<option value="Credit" label="Credit" >Credit</option>
							<option value="Cash" label="Cash" >Cash</option>
						</select>
					</td>
				 	<td class="label">Rate (GOld/SILVER) *</td>
					<td><input name="rate" id="exRate" class="numField" onkeyup="extractNumber(this,2,true), extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
					<td class="label">Purchase Amount*</td> 
					<td><input name="purchaseAmount" id="exPurchaseAmt" class="zeroValidationCur numField" readonly="readonly"/></td>
				</tr> 
				<tr class="always_show" >
					<td class="label">Vat Percentage</td>
					<td><input name="vatPercentage" id="exVat" maxlength="10" class="zeroValidationCur numField" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
					<td class="label">Vat Amount</td>
					<td><input name="vatAmount" id="exVatAmt" class="zeroValidationCur numField" readonly="readonly"/>
						<input name="roundOff" type="hidden" readonly="readonly" id="exRound_Off" class="zeroValidationCur numField" maxlength="10" onkeyup="extractNumber(this,2,true);" /></td>			
					<td class="label">Round Off</td>
					<td><input name="roundOff" id="exRoundOff" class="zeroValidationCur numField" onkeydown="return (event.keyCode!=13);" onkeyup="extractNumber(this,2,true)"  onkeypress="ValidateNumberKeyUp();"/>
					<input type="hidden" name="purchaseId"/>
					<input type="hidden" name="purchseInvoice"/>
					<input type="hidden" id="errorName" value="${errorType}" /></td>
				</tr>
				
				<tr class="always_show">
					<td class="label">Total Amount *</td>
					<td><input name="totalAmount" id="exTotalAmt" class="zeroValidationCur numField" readonly="readonly"/>
					<td class="label">Description</td>
					<td><input name="purDescription" id="purDescription"/></td>
					<td colspan="2" align="center" ><input type="checkbox" name="addToSales" id="addToSales" value="addToSales" style="width:10px;"/>
							Add this To Sales<input type="hidden" name="exchangePurchase" id="exchange_purchase" />
					</td>
				</tr>
				<tr class="buttons_col" >
					<td colspan="6" align="center">
						<input type="submit" name="insert" value="Insert" id="exchangeinsert" class="button_style"/>			
					    <input type="submit" name="cancel" value="Cancel" id="exchangeCancel" class="button_style" />		  
					<!-- <input type="submit" name="delete"	value="Delete" /> -->		
					</td>
				</tr>
			</table>
     </div> 
</div>   
     
<!-- #customize your modal window here -->
 <form:form>
    <div id="customer" class="window dialog">
    <a href="#" class="close">Close it</a>
        <b>Customer Form</b>
         <div class="content">Customer form here <input type="text"></div> 
    </div>
    
    <div id="purchase" class="window dialog">
    <a href="#" class="close">Close it</a>
        <b>Customer Form</b>
        <div class="content">Purchase form here <input type="text"></div>
     </div>
 </form:form>
      
<!-- Do not remove div#mask, because you'll need it to fill the whole screen --> 
<div id="mask"></div>
</div>

<div id="wrapper">
<div id="col-form1" align="left">
	<div class="boxed">
	<div class="title_1">Sales Details</div>
	<div class="content">
	
	<form:form id="form1" name="form" commandName="sales" method="POST" action="salesItem.htm" class="SalesForm">
	<a class="create_or_list" href="formbullionsales.htm">Bullion Sales Form</a>
	<a class="create_or_list" href="newSalesReturn.htm">Sales Return Form</a>
	<a name="modal" id="exchangePopup" class="create_or_list" href="#newpurchaseExchange" title="New Purchase Exchange">Purchase Exchange</a>
	
		<!-- <div class="summary" align="center">Sales Form</div>  -->
		<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error pos pos2 pos3"><form:errors path="*" id="errorDisplay"/></span>
	</div>
	<input type="hidden" id="errorName" value="${errorType}"/> 
	<div class="smsg summary" style="color:#3EA99F;"></div>
	<table id="formSales" align="left" cellpadding="0" cellspacing="0" class="salesInfoTab" >
			
			<tr class="form_cat_head"><td colspan="8"><a name="modal" id="custSalesPopup" class ="add_new" href="#newCustomer" title="Add New Customer" style="float:right;">Add Customer</a>Ornament Sales</td></tr>
			<tr>
				
				<td class="label">Salesman Name</td>
				<td><input name="salesmanName" readonly="readonly" value="<%=session.getAttribute("username")%>" size="20"  /></td>
	  			<td class="label">Transaction Type</td>
				<td><form:input path="salesType" readonly="true" id="salesType" /></td>
				<td class="label">Sales Date</td>
				<td><form:input path="salesDate" id="date" size="13" style="width:90px;" /></td>
				<td class="label">Customer Name</td>
				<td>
					<form:select path="customerName" id="custName" class="custName">
						<form:option value="walk_in">Walk In</form:option>
						<c:forEach var="ledger" items="${name}">
						 <form:option value="${ledger.ledgerName}">${ledger.ledgerName}</form:option>
						 </c:forEach>
					</form:select>
				</td>
			</tr>
			<tr id="row_NameCity" class="row_NameCity">
				<td  id="lpanNumber" class="label">PanNumber</td>
				<td><form:input id="fpan_Number" path="panNumber" value="" size="9" /></td>
				<td class="label">Name</td>
				<td><form:input class="w_Name" id="w_Name" path="walkIn_Name" size="9"/></td>
				<td class="label">City</td>
				<td><form:input class="w_City" id="w_City" path="walkIn_City" size="9" /></td>
				<td class="label"></td>
				<td></td>		
			</tr>
	</table>
	   	<table align="left" width="100%" cellpadding="0" cellspacing="0" class="salesItemsTab" >
	   	<tr class="SalesRowHead">
				<td>Item Code</td>
				<td></td>
				<td>Item Name</td>
				<td>Item Details</td>
				<td>Stone Details</td>
				<td></td>
				<td>Discounts</td>
				<td> Amount</td>
		</tr>
	<!-- IT 1 -->
	   	<tr class="FirstR">
	   			
				<td><form:input path="itemCode" size="10" id="icode" style="text-transform: uppercase;float:left;text-align:left;" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><input name="s"  id="s" value=""  class="searchBttn" size="3" type="submit"   /></td>
				<td><form:input path="itemName" size="22" id="iname" class="ItemName" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Rate:</span><form:input path="bullionRate" class="numField" size="5" id="brate" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Stone Desc:</span><form:input path="stone" size="9" id="stnDesc" style="" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><span class="td_lable">MC Rs:</span><form:input path="mcByGrams" class="numField" size="5" id="mcgrams" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,3,true);" /></td>
				<td><span class="td_lable">Discount Rs:</span><form:input path="lessAmount" class="numField" size="7" id="lessAmt" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td></td>
		</tr>
		<tr>
				<td></td>
				<td></td>
				<td><div class="td_lable">Category:</div></td>
				<td><form:input path="categoryName" size="10" id="cname" class="Category" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Stone cost:</span><form:input path="stoneCost" class="numField" size="9" id="stn" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><span class="td_lable">Waste gms:</span><form:input path="wastageByGrams" class="numField" size="5" id="wastbygms" readonly="true" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,3,true);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Discount %:</span><form:input path="lessPercentage" class="numField" size="5" id="lessPercent" readonly="true" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td>
				<form:input type="hidden" path="manufacturerSeal" id="script_hman"/>
				<form:input type="hidden" path="salesHMCharges" id="salesHMCharges"/>
				<form:input type="hidden" path="totalPieces" class="numField" id="script_totpieces"/>
				<form:input type="hidden"  path="metalUsed" id="metalUsed"/>
				</td>
				
		</tr>
		<tr class="LastR">
				<td></td>
				<td></td>
				<td><div class="td_lable">Gross Wt:</div></td>
				<form:input path="numberOfPieces" class="numField" size="5" id="nop" style="" value="1" type="hidden"/>
				<!--  <td><span class="td_lable">qty:</span></td>-->
				<td><form:input path="grossWeight" class="numField" size="5" id="grwt" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,3,true);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Qty:</span><form:input path="soldQty" readonly="true" name="soldQty" class="numField" size="5" id="soldQty" style="" />
				<form:input path="bullionType" size="5" id="btype" class="BullionType" readonly="true" onkeydown = "return (event.keyCode!=13);" type="hidden"/></td>
				<td><span class="td_lable">VA %:</span><form:input path="valueAdditionCharges" class="numField" id="valueAdditionCharges" size="5" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">VAT %:</span><form:input path="vtax" class="numField" size="3" id="vat" readonly="true" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><form:input path="amountAfterLess" size="7" class="amt numField" id="amountAfterLess" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
		</tr>
		<!-- <tr class="LastR">
				<td></td>
				<td></td>
				<td><div class="td_lable">Gross Wt:</div></td>
				<form:input path="numberOfPieces" class="numField" size="5" id="nop" style="" value="1" type="hidden"/>   
				<td><form:input path="grossWeight" class="numField" ize="5" id="grwt" style="" readonly="true" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td></td>
				<td></td>
				<td></td>
				<td><form:input path="amountAfterLess" size="7" class="amt numField" readonly="true" id="amountAfterLess" style=" text-align:right"/></td>
		</tr> -->
	<!-- IT 2 -->
	   	<tr class="FirstR SalesRowHide SalesRow2" >
	   			
				<td><form:input path="itemCode1" type="text" size="10" id="icode1" onkeydown = "return (event.keyCode!=13);" style="text-transform: uppercase;float:left;text-align:left;" onclick="return notEmpty(document.getElementById('iname'), 'Please Enter first row of Items');" onkeypress="return notEmpty(document.getElementById('iname'), 'Please Enter first row of Items');"/></td>
				<td><input name="s1"  id="s1" value=""  class="searchBttn" size="3" type="submit"  /></td>
				<td><form:input path="itemName1" type="text" size="22" id="iname1" readonly="true" class="ItemName"  onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Rate:</span><form:input path="bullionRate1" class="numField" type="text" id="brate1" size="5"style=" text-align:right;"onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><span class="td_lable">Stone Desc:</span><form:input path="stone1" class="numField" type="text" id="stnDesc1" size="9" style="" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">MC Rs:</span><form:input path="mcByGrams1" class="numField" type="text" size="5" id="mcgrams1" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,3,true);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Discount Rs:</span><form:input path="lessAmount1" class="numField" size="7" id="lessAmt1" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td></td>
		</tr>
		<tr class="SalesRowHide SalesRow2">
				<td></td>
				<td></td>
				<td><div class="td_lable">Category:</div></td>
				<td><form:input path="categoryName1" type="text" id="cname1" size="9" class="Category" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Stone cost:</span><form:input path="stoneCost1" class="numField" type="text" id="stn1" size="9" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Waste gms:</span><form:input path="wastageByGrams1" class="numField" type="text" id="wastbygms1" readonly="true" size="5" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,3,true);" /></td>
				<td><span class="td_lable">Discount %:</span><form:input path="lessPercentage1" class="numField" type="text" size="5" id="lessPercent1" readonly="true" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
				
				<td>
				<form:input type="hidden" path="manufacturerSeal1" id="script_hman1"/>
				<form:input type="hidden" path="salesHMCharges1" id="salesHMCharges1"/>
				<form:input type="hidden" path="totalPieces1" id="script_totpieces1"/>
				<form:input type="hidden" path="metalUsed1" id="metalUsed1"/>
				</td>
		</tr>
		<tr class="LastR SalesRowHide SalesRow2">
				<td></td>
				<td></td>
				<td><div class="td_lable">Gross Wt:</div></td>
				<form:input path="numberOfPieces1" class="numField" size="5" readonly="readonly" value="1" type="hidden"/>  
				<td><form:input path="grossWeight1" class="numField" type="text" id="grwt1" size="5"  style="text-align:right;" readonly="true" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,3,true);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Qty:</span><form:input path="soldQty1" readonly="true" class="numField" size="5" id="soldQty1" style="" />
				<form:input path="bullionType1" type="hidden" size="5" id="btype1" readonly="true" class="BullionType" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><span class="td_lable">VA %:</span><form:input path="valueAdditionCharges1" class="numField" type="text" id="valueAdditionCharges1" size="5" value="" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">VAT %:</span><form:input path="vtax1" class="numField" type="text" id="vat1" size="3" readonly="true" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><form:input path="amountAfterLess1" type="text numField" class="amt" id="amountAfterLess1" size="7" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
		</tr>
		<!-- <tr class="LastR SalesRowHide SalesRow2">
				<td></td>
				<td></td>
				<td><div class="td_lable">Gross Wt:</div></td>
				<form:input path="numberOfPieces1" class="numField" size="5" readonly="readonly" value="1" type="hidden"/>  
				<td><form:input path="grossWeight1" class="numField" type="text" id="grwt1" size="5"  style="text-align:right;" readonly="true" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td></td>
				<td></td>
				<td></td>
				<td><form:input path="amountAfterLess1" type="text numField" class="amt" id="amountAfterLess1" size="7" readonly="true" style=" text-align:right"/></td>
		</tr> -->
		<!-- IT 3 -->
	   	<tr class="FirstR SalesRowHide SalesRow3">
				<td><form:input path="itemCode2" type="text" size="10" id="icode2" onkeydown = "return (event.keyCode!=13);" style="text-transform: uppercase;float:left;text-align:left;" onclick="return notEmpty(document.getElementById('iname'), 'Please Enter first row of Items');" onkeypress="return notEmpty(document.getElementById('iname'), 'Please Enter first row of Items');"/></td>
				<td><input name="s2"  id="s2" value=""  class="searchBttn" size="2" type="submit"  /></td>
				<td><form:input path="itemName2" type="text" size="22" id="iname2" readonly="true" class="ItemName"  onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Rate:</span><form:input path="bullionRate2" class="numField" type="text" id="brate2" size="5"style=" text-align:right;"onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><span class="td_lable">Stone Desc:</span><form:input path="stone2" class="numField" type="text" id="stnDesc2" size="9" style="" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">MC Rs:</span><form:input path="mcByGrams2" class="numField" type="text" size="5" id="mcgrams2" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,3,true);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Discount Rs:</span><form:input path="lessAmount2" class="numField" size="7" id="lessAmt2" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td></td>
		</tr>
		<tr class="SalesRowHide SalesRow3">
				<td></td>
				<td></td>
				<td><div class="td_lable">Category:</div></td>
				<td><form:input path="categoryName2" type="text" id="cname2" size="9" readonly="true" class="Category" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Stone cost:</span><form:input path="stoneCost2" class="numField" type="text" id="stn2" size="9" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Waste gms:</span><form:input path="wastageByGrams2" class="numField" type="text" id="wastbygms2" readonly="true" size="5" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,3,true);" /></td>
				<td><span class="td_lable">Discount %:</span><form:input path="lessPercentage2" class="numField" type="text" size="5" id="lessPercent2" readonly="true" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
				
				<td>
				<form:input type="hidden" path="manufacturerSeal2" id="script_hman2"/>
				<form:input type="hidden" path="salesHMCharges2" id="salesHMCharges2"/>
				<form:input type="hidden" class="numField" path="totalPieces2" id="script_totpieces2"/>
				<form:input type="hidden" class="numField" path="metalUsed2" id="metalUsed2"/>
				</td>
		</tr>
		<tr class="LastR SalesRowHide SalesRow3">
				<td></td>
				<td></td>
				<td><div class="td_lable">Gross Wt:</div></td>
				<form:input path="numberOfPieces2" class="numField" size="5" readonly="readonly" value="1" type="hidden"/>  
				<td><form:input path="grossWeight2" class="numField" type="text" id="grwt2" size="5"  style="text-align:right;" readonly="true" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,3,true);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Qty:</span><form:input path="soldQty2" readonly="true" class="numField" size="5" id="soldQty2" style="" />
				<form:input path="bullionType2" type="hidden" size="5" id="btype2" readonly="true" class="BullionType" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><span class="td_lable">VA %:</span><form:input path="valueAdditionCharges2" class="numField" type="text" id="valueAdditionCharges2" size="5" value="" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">VAT %:</span><form:input path="vtax2" class="numField" type="text" id="vat2" size="3" readonly="true" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><form:input path="amountAfterLess2" type="text" class="amt numField" id="amountAfterLess2" size="7" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
		</tr>
		<!-- <tr class="LastR SalesRowHide SalesRow3">
				<td></td>
				<td></td>
				<td><div class="td_lable">Gross Wt:</div></td>
				<form:input path="numberOfPieces2" class="numField" size="5" readonly="readonly" value="1" type="hidden"/>  
				<td><form:input path="grossWeight2" class="numField" type="text" id="grwt2" size="5"  style="text-align:right;" readonly="true" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td></td>
				<td></td>
				<td></td>
				<td><form:input path="amountAfterLess2" type="text" class="amt numField" id="amountAfterLess2" size="7" readonly="true" style=" text-align:right"/></td>
		</tr> -->
		<!-- IT 4 -->
	   	<tr class="FirstR SalesRowHide SalesRow4">
				<td><form:input path="itemCode3" type="text" size="10" id="icode3" onkeydown = "return (event.keyCode!=13);" style="text-transform: uppercase;float:left;text-align:left;" onclick="return notEmpty(document.getElementById('iname'), 'Please Enter first row of Items');" onkeypress="return notEmpty(document.getElementById('iname'), 'Please Enter first row of Items');"/></td>
				<td><input name="s3"  id="s3" value=""  class="searchBttn" size="3" type="submit"  /></td>
				<td><form:input path="itemName3" type="text" size="22" id="iname3" class="ItemName"  onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Rate:</span><form:input path="bullionRate3" class="numField" type="text" id="brate3" size="5"style=" text-align:right;"onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><span class="td_lable">Stone Desc:</span><form:input path="stone3" class="numField" type="text" id="stnDesc3" size="9" style="" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">MC Rs:</span><form:input path="mcByGrams3" class="numField" type="text" size="5" id="mcgrams3" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,3,true);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Discount Rs:</span><form:input path="lessAmount3" class="numField" size="7" id="lessAmt3" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td></td>
		</tr>
		<tr class="SalesRowHide SalesRow4">
				<td></td>
				<td></td>
				<td><div class="td_lable">Category:</div></td>
				<td><form:input path="categoryName3" type="text" id="cname3" size="9" class="Category" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Stone cost:</span><form:input path="stoneCost3" class="numField" type="text" id="stn3" size="9" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Waste gms:</span><form:input path="wastageByGrams3" class="numField" type="text" id="wastbygms3" readonly="true" size="5" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,3,true);" /></td>
				<td><span class="td_lable">Discount %:</span><form:input path="lessPercentage3" class="numField" type="text" size="5" id="lessPercent3" readonly="true" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td>
				<form:input type="hidden" path="manufacturerSeal3" id="script_hman3"/>
				<form:input type="hidden" path="salesHMCharges3" id="salesHMCharges3"/>
				<form:input type="hidden" path="totalPieces3" class="numField" id="script_totpieces3"/>
				<form:input type="hidden" path="metalUsed3" class="numField" id="metalUsed3"/>
				</td>
		</tr>
		<tr class="LastR SalesRowHide SalesRow4">
				<td></td>
				<td></td>
				<td><div class="td_lable">Gross Wt:</div></td>
				<form:input path="numberOfPieces3" class="numField" size="5" readonly="readonly" value="1" type="hidden"/>  
				<td><form:input path="grossWeight3" class="numField" type="text" id="grwt3" size="5"  style="text-align:right;" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,3,true);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Qty:</span><form:input path="soldQty3" readonly="true" class="numField" size="5" id="soldQty3" style="" />
				<form:input path="bullionType3" type="hidden" size="5" id="btype3" readonly="true" class="BullionType" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><span class="td_lable">VA %:</span><form:input path="valueAdditionCharges3" class="numField" type="text" id="valueAdditionCharges3" size="5" value="" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">VAT %:</span><form:input path="vtax3" class="numField" type="text" id="vat3" size="3" readonly="true" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><form:input path="amountAfterLess3" type="text" class="amt numField" id="amountAfterLess3" size="7" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
		</tr>
		<!-- <tr class="LastR SalesRowHide SalesRow4">
				<td></td>
				<td></td>
				<td><div class="td_lable">Gross Wt:</div></td>
				<form:input path="numberOfPieces3" class="numField" size="5" readonly="readonly" value="1" type="hidden"/>  
				<td><form:input path="grossWeight3" class="numField" type="text" id="grwt3" size="5"  style="text-align:right;" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td></td>
				<td></td>
				<td></td>
				<td><form:input path="amountAfterLess3" type="text" class="amt numField" id="amountAfterLess3" size="7" readonly="true" style=" text-align:right"/></td>
		</tr> -->
		<!-- IT 5 -->
	   	<tr class="FirstR SalesRowHide SalesRow5">
				<td><form:input path="itemCode4" type="text" size="10" id="icode4" onkeydown = "return (event.keyCode!=13);" style="text-transform: uppercase;float:left;text-align:left;" onclick="return notEmpty(document.getElementById('iname'), 'Please Enter first row of Items');" onkeypress="return notEmpty(document.getElementById('iname'), 'Please Enter first row of Items');"/></td>
				<td><input name="s4"  id="s4" value=""  class="searchBttn" size="3" type="submit"  /></td>
				<td><form:input path="itemName4" type="text" size="22" id="iname4" class="ItemName"  onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Rate:</span><form:input path="bullionRate4" class="numField" type="text" id="brate4" size="5"style=" text-align:right;"onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><span class="td_lable">Stone Desc:</span><form:input path="stone4" class="numField" type="text" id="stnDesc4" size="9" style="" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">MC Rs:</span><form:input path="mcByGrams4" class="numField" type="text" size="5" id="mcgrams4" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,3,true);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Discount Rs:</span><form:input path="lessAmount4" class="numField" size="7" id="lessAmt4" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td></td>
		</tr>
		<tr class="SalesRowHide SalesRow5">
				<td></td>
				<td></td>
				<td><div class="td_lable">Category:</div></td>
				<td><form:input path="categoryName4" type="text" id="cname4" size="9" class="Category" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Stone cost:</span><form:input path="stoneCost4" class="numField" type="text" id="stn4" size="9" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Waste gms:</span><form:input path="wastageByGrams4" class="numField" type="text" id="wastbygms4" readonly="true" size="5" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,3,true);" /></td>
				<td><span class="td_lable">Discount %:</span><form:input path="lessPercentage4" class="numField" type="text" size="5" id="lessPercent4" readonly="true" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
				
				<td>
				<form:input type="hidden" path="manufacturerSeal4" id="script_hman4"/>
				<form:input type="hidden" path="salesHMCharges4" id="salesHMCharges4"/>
				<form:input type="hidden" path="totalPieces4" class="numField" id="script_totpieces4"/>
				<form:input type="hidden" path="metalUsed4" class="numField" id="metalUsed4"/>
				</td>
		</tr>
		<tr class="LastR SalesRowHide SalesRow5">
				<td></td>
				<td></td>
				<td><div class="td_lable">Gross Wt:</div></td>
				<form:input path="numberOfPieces4" class="numField" size="5" readonly="readonly" value="1" type="hidden"/>  
				<td><form:input path="grossWeight4" class="numField" type="text" id="grwt4" size="5"  style="text-align:right;" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,3,true);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">Qty:</span><form:input path="soldQty4" readonly="true" class="numField" size="5" id="soldQty4" style="" />
				<form:input path="bullionType4" type="hidden" size="5" id="btype4" readonly="true" class="BullionType" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><span class="td_lable">VA %:</span><form:input path="valueAdditionCharges4" class="numField" type="text" id="valueAdditionCharges4" size="5" value="" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><span class="td_lable">VAT %:</span><form:input path="vtax4" class="numField" type="text" id="vat4" size="3" readonly="true" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><form:input path="amountAfterLess4" type="text" class="amt numField" id="amountAfterLess4" size="7" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
		</tr>
		<!-- <tr class="LastR SalesRowHide SalesRow5">
				<td></td>
				<td></td>
				<td><div class="td_lable">Gross Wt:</div></td>
				<form:input path="numberOfPieces4" class="numField" size="5" readonly="readonly" value="1" type="hidden"/>  
				<td><form:input path="grossWeight4" class="numField" type="text" id="grwt4" size="5"  style="text-align:right;" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td></td>
				<td></td>
				<td></td>
				<td><form:input path="amountAfterLess4" type="text" class="amt numField" id="amountAfterLess4" size="7" readonly="true" style=" text-align:right"/></td>
		</tr> -->
	   	<!-- Row 2 
	   		<tr class="row_hide" id="row2">
				<td><form:input path="itemCode1" type="text" size="7" id="icode1" onkeydown = "return (event.keyCode!=13);" style="" onclick="return notEmpty(document.getElementById('iname'), 'Please Enter first row of Items');" onkeypress="return notEmpty(document.getElementById('iname'), 'Please Enter first row of Items');"/></td>
				<td><input name="s1"  id="s1" value="S"  style="height: 20px; width: 25px" size="3" type="submit" /></td>
				<td><form:input path="itemName1" type="text" size="7" id="iname1" readonly="true" style=""  onkeydown = "return (event.keyCode!=13);" /></td>
				<td><form:input path="categoryName1" type="text" id="cname1" size="7" readonly="true" style="" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><form:input path="bullionType1" type="text" size="5" id="btype1" readonly="true" style=""/></td>
				<form:input path="numberOfPieces1" size="5" readonly="readonly" value="1" type="hidden"/>  
				<td><form:input path="grossWeight1" type="text" id="grwt1" size="5" style="" readonly="true" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><form:input path="bullionRate1" type="text" id="brate1" size="5" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><form:input path="stone1" type="text" id="stnDesc1" size="5" style="" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><form:input path="stoneCost1" type="text" id="stn1" size="5" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><form:input path="valueAdditionCharges1" type="text" id="valueAdditionCharges1" size="5" value="" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><form:input path="wastageByGrams1" type="text" id="wastbygms1" readonly="true" size="5" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" /></td>
				<td><form:input path="mcByGrams1" type="text" size="5" id="mcgrams1" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><form:input path="vtax1" type="text" id="vat1" size="3" readonly="true" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><form:input path="lessPercentage1" type="text" size="5" id="lessPercent1" readonly="true" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><form:input path="lessAmount1" size="5" id="lessAmt1" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><form:input path="amountAfterLess1" type="text" id="amountAfterLess1" size="7" readonly="true" style=" text-align:right"/></td>
			</tr> 
		-->
			
						<!-- Row 3 
			<tr class="row_hide">
				<td><form:input path="itemCode2" type="text" size="7" id="icode2" onkeydown = "return (event.keyCode!=13);" value="" style="" onclick="return notEmpty(document.getElementById('iname'), 'Please Enter first row of Items');" onkeypress="return notEmpty(document.getElementById('iname'), 'Please Enter first row of Items');"/></td>
				<td><input name="s2"  id="s2" value="S"  style="height: 20px; width: 25px" size="3" type="submit"/></td>
				<td><form:input path="itemName2" type="text" size="7" id="iname2" readonly="true" style=""  onkeydown = "return (event.keyCode!=13);" /></td>
				<td><form:input path="categoryName2" type="text" id="cname2" size="7" readonly="true" style="" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><form:input path="bullionType2" type="text" size="5" id="btype2" readonly="true" style=""/></td>
				<form:input path="numberOfPieces2" size="5" value="1" type="hidden" readonly="readonly"/> 
				<td><form:input path="grossWeight2" type="text" id="grwt2" size="5"  style="" readonly="true" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"  /></td>
				<td><form:input path="bullionRate2" type="text" size="5" id="brate2" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="rate_change_row3(),ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><form:input path="stone2" type="text" id="stnDesc2" size="5" style="" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><form:input path="stoneCost2" type="text" id="stn2" size="5" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><form:input path="valueAdditionCharges2" type="text" id="valueAdditionCharges2" size="5" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><form:input path="wastageByGrams2" type="text" id="wastbygms2" size="5" style="" readonly="true" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"/></td>
				<td><form:input path="mcByGrams2" type="text" size="5" id="mcgrams2" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><form:input path="vtax2" type="text" id="vat2" size="3" readonly="true" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"/></td>
				<td><form:input path="lessPercentage2" size="5" id="lessPercent2" readonly="true" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><form:input path="lessAmount2" size="5" id="lessAmt2" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><form:input path="amountAfterLess2" type="text" id="amountAfterLess2" size="7" readonly="true" style=" text-align:right"/></td>
			</tr>-->
					<!-- Row 4 
			<tr class="row_hide">
				<td><form:input path="itemCode3" type="text" size="7" id="icode3" onkeydown = "return (event.keyCode!=13);" value="" style="" onclick="return notEmpty(document.getElementById('iname'), 'Please Enter first row of Items');" onkeypress="return notEmpty(document.getElementById('iname'), 'Please Enter first row of Items');"/></td>
				<td><input name="s3"  id="s3" value="S"  style="height: 20px; width: 25px" size="3" type="submit" /></td>
				<td><form:input path="itemName3" type="text" size="7" id="iname3" readonly="true" style=""  onkeydown = "return (event.keyCode!=13);"  /></td>
				<td><form:input path="categoryName3" type="text" id="cname3" size="7" readonly="true" style="" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><form:input path="bullionType3" type="text" size="5" id="btype3" readonly="true" style=""/></td>
				<form:input path="numberOfPieces3" size="5" value="1" type="hidden" readonly="readonly"/>  
				<td><form:input path="grossWeight3" type="text" id="grwt3" size="5" style=""  readonly="true" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"  /></td>
				<td><form:input path="bullionRate3" type="text" size="5" id="brate3" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><form:input path="stone3" type="text" id="stnDesc3" size="5" style="" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><form:input path="stoneCost3" type="text" id="stn3" size="5" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><form:input path="valueAdditionCharges3" type="text" id="valueAdditionCharges3" size="5" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><form:input path="wastageByGrams3" type="text" id="wastbygms3" size="5" readonly="true" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"/></td>
				<td><form:input path="mcByGrams3" type="text" size="5" id="mcgrams3" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><form:input path="vtax3" type="text" id="vat3" size="3" readonly="true" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"/></td>
				<td><form:input path="lessPercentage3" size="5" id="lessPercent3" readonly="true" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><form:input path="lessAmount3" size="5" id="lessAmt3" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><form:input path="amountAfterLess3" type="text" id="amountAfterLess3" size="7" readonly="true" style=" text-align:right"/></td>
			</tr>-->
					<!-- Row 5  
			<tr class="row_hide">
				<td><form:input path="itemCode4" type="text" size="7" id="icode4" onkeydown = "return (event.keyCode!=13);" value="" style="" onclick="return notEmpty(document.getElementById('iname'), 'Please Enter first row of Items');" onkeypress="return notEmpty(document.getElementById('iname'), 'Please Enter first row of Items');"/></td>
				<td><input name="s4"  id="s4" value="S"  style="height: 20px; width: 25px" size="4" type="submit" /></td>
				<td><form:input path="itemName4" type="text" size="7" id="iname4" readonly="true" style=""  onkeydown = "return (event.keyCode!=13);" /></td>
				<td><form:input path="categoryName4" type="text" id="cname4" size="7" readonly="true" style="" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><form:input path="bullionType4" type="text" size="5" id="btype4" readonly="true" style=""/></td>
				<form:input path="numberOfPieces4" size="5" value="1" type="hidden" readonly="true"/>  
				<td><form:input path="grossWeight4" type="text" id="grwt4" size="5" style="" readonly="true" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"  /></td>
				<td><form:input path="bullionRate4" type="text" size="5" id="brate4" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><form:input path="stone4" type="text" id="stnDesc4" size="5" style="" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><form:input path="stoneCost4" type="text" id="stn4" size="5" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><form:input path="valueAdditionCharges4" type="text" id="valueAdditionCharges4" size="5" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><form:input path="wastageByGrams4" type="text" id="wastbygms4" size="5"  readonly="true" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><form:input path="mcByGrams4" type="text" size="5" id="mcgrams4" style="" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><form:input path="vtax4" type="text" id="vat4" size="3" readonly="true" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);"/></td>
				<td><form:input path="lessPercentage4" size="5" id="lessPercent4" readonly="true" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);" /></td>
				<td><form:input path="lessAmount4" size="5" id="lessAmt4" style=" text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"/></td>
				<td><form:input path="amountAfterLess4" type="text" id="amountAfterLess4" size="7" readonly="true" style=" text-align:right"/></td>
			</tr>-->
<tr class="CalcRow">
<td valign="top" ><!-- <input type="button" class="addbttn" /> --></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td><form:input path="totalLess" class="numField" size="10"  id="tradediscount" readonly="true" onkeypress="return ValidateNumberKeyPress(this, event);" cssStyle="float:right;" onblur="ValidateAndFormatNumber(this)"/><span>Total Discount</span></td> 
<td></td>
</tr>
<tr class="CalcRow amt">
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td><span>Sub Total</span></td><!--TradeDiscount-->
<td class="total"><form:input path="totalAmount" id="subtotal" size="8" readonly="true" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" style="text-align:right" onblur="ValidateAndFormatNumber(this)"/></td>
</tr>
			

	
<!--NetAmount		
<td colspan="2">Net Amount<td colspan="2">
<form:input path="netAmount" type="hidden" id="netamount" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" STYLE="text-align:right" onblur="ValidateAndFormatNumber(this)"/>
</td></tr>-->

<!--Sum of Vat-->
<tr class="CalcRow">
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>      
<td><span>VAT Amount</span></td>
<td><form:input path="tax" size="8" id="tax" onkeypress="return ValidateNumberKeyPress(this, event);" readonly="true" onkeyup="ValidateNumberKeyUp(this);" STYLE="text-align:right" onblur="ValidateAndFormatNumber(this)"/><span>+</span></td>
</tr>

<!-- Disc Amt -->
<tr class="CalcRow">
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>      
<td><span>Discount Amount</span></td>
<td><form:input path="billDiscAmt" size="15" id="billDiscAmt" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);" STYLE="text-align:right" onblur="ValidateAndFormatNumber(this)"/><span>-</span></td>
</tr>

<!--Amount Received -->
<tr class="CalcRow amt">
<td>&nbsp;</td>
<td></td>
<td><span>Rate Fix Amount</span></td>
<td><form:input path="rateFixAmount" class="numField" size="8" id="rateFixAmount" readonly="true" cssStyle="text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" /></td> 
<!-- Adjustment Amount -->
<td><span>Adjustment Amount</span></td> 
<td><form:input path="adjustmentAmount" class="numField" size="8" id="adjustmentAmt" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" STYLE="text-align:right" onblur="ValidateAndFormatNumber(this)"/></td>				
<!--BillAmount  -->
<td><span>Bill Amount</span> </td>
<td class="total"><form:input path="billAmount"size="8" id="billamount" readonly="true" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" STYLE="text-align:right" onblur="ValidateAndFormatNumber(this)"/></td>
</tr>
<!--RoundOff  -->
<tr class="CalcRow">
<td></td>
<td></td>
<td><span>Rate Fix Gram</span></td>
<td><form:input path="rateFixGrams" class="numField" size="8" id="rateFixGrams" readonly="true" cssStyle="text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>  
<td></td>
<td></td>
<td><span>Round Off</span></td> 
<td><form:input path="roundOff" class="numField" readonly="true" id="roundOff" size="8" cssStyle="text-align:right" onkeydown = "return (event.keyCode!=13);" keypress="ValidateNumberKeyUp();" />
<input type="hidden" id="rondOffReference" /></td>
</tr>
<tr class="CalcRow">
<td>&nbsp;</td>
<td></td>
<td><span>Sales Order Board Rate</span></td>
<td><form:input path="boardRateSO" class="numField" size="8" id="boardRateSO" readonly="true" cssStyle="text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" /></td> 
<td><span>Old Purchase BillNo</span></td>
<td>
		
		<form:select id="billNo" path="exchangeBillNo" onchange="ajax();">
			<form:option value="" label="Select"></form:option>
			<c:forEach var="purbil" items="${billNo}">			
		<form:option value="${purbil}"></form:option>
			</c:forEach>
		</form:select> 
</td>
<!-- Exchange Amount -->
<td><span>Old Purchase Amount</span> </td>
<td><form:input path="exchangeAmount" class="numField" size="8"  id="exchange_Amount"  readonly="true"  cssStyle="text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"/></td>
</tr>

<tr class="CalcRow">
<td>&nbsp;</td>
<td></td>
<td><span>Rate Fix Difference Amount</span></td>
<td><form:input path="rateFixDifferenceAmount" class="numField" size="8" id="rateFixDifferenceAmount" readonly="true" cssStyle="text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" /></td> 			
<td><span>Sales Order Id List</span></td>

<td>
<form:select id="salesOrderIds" path="salesOrderID">
	<form:option value="0" label="Select"></form:option>
	<c:forEach var="salesorderId" items="${salesorder_id}">			
		<form:option value="${salesorderId}" label="${salesorderId }"></form:option>
	</c:forEach>
</form:select>
</td>

<td><span>SalesOrder Advance Amount</span></td>
<td><form:input path="salesOrderAmount" class="numField" size="8" id="order_Amount" readonly="true" cssStyle="text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
</tr>
<tr class="CalcRow">
<td>&nbsp;</td>
<td></td>
<td></td>
<td><form:input type="hidden" path="SSCardNoVal" id="cardnoVal" /></td> 
<td><span>Card Number</span></td>

<td><form:select id="SSCardNo" path="SSCardNo" multiple="true">
		<c:forEach var="SSCard" items="${SSCardNoList}">			
			<option value="${SSCard}" label="${SSCard}">${SSCard}</option>
		</c:forEach>
	</form:select>
</td>
	<td><span>Savings Scheme Amount</span></td>
	<td><form:input path="SSCardAmount" class="numField" size="8" id="SSCardAmount" readonly="true" cssStyle="text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" />
		<form:input path="SSCardGrams" type="hidden" class="numField" size="8" id="SSCardGrams" readonly="true" cssStyle="text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
</tr>

<tr class="CalcRow">
<td>&nbsp;</td>
<td></td>
<td></td>
<td></td> 			
<td><span>Sales Return Id List</span></td>

<td>
<form:select id="salesReturnId" path="salesReturnId">
	<form:option value="0" label="Select"></form:option>
	<c:forEach var="salesReturnId" items="${salesReturn_id}">			
		<form:option value="${salesReturnId}" label="${salesReturnId }"></form:option>
	</c:forEach>
</form:select>
</td>

<td><span>Sales Return Amount</span></td>
<td><form:input path="salesReturnAmount" class="numField" size="8" id="salesReturnAmount" readonly="true" cssStyle="text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
</tr>

<tr class="CalcRow">
<td>&nbsp;</td>
<td></td>
<td></td> 
<td></td>				
<td></td>
<td></td>	
<!-- Scheme Discount -->
<td><span>Scheme Bonus Amount</span></td> 
<td><form:input path="jewelDiscount" class="numField" id="jewelDiscount" size="8" cssStyle="text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);"/></td>	
</tr>

<tr class="CalcRow">
<td>&nbsp;</td>
<td></td>
<td></td> 
<td></td>				
<td></td>
<td></td>	
<!-- Amount Received -->
<td><span>Amount Received</span></td> 
<td><form:input readonly="true" path="amtRecd" class="numField" id="amtReceived" size="8" cssStyle="text-align:right"/><span>-</span></td>	
</tr>

<tr class="CalcRow pay">
<td>&nbsp;</td>
<td></td>
<td></td> 
<td></td>				
<td></td>
<td></td>	
<!--Balance To pay  -->
<td><span>Balance To Pay</span></td> 
<td class="total"><form:input path="balToPay" id="balAmt" readonly="true" size="8" cssStyle="text-align:right"/></td>	
</tr>
	
</table>
<table cellpadding="0" cellspacing="0" align="left" class="SalesPaymentModeTop">
<tr>
<td>
<table cellpadding="0" cellspacing="0" align="left" class="SalesGreetings" >
<tr class="PaymentModeHead">
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr class="greetingsTr">
<td class="padd">Greetings </td>
<td><form:textarea path="greetings" col="60" row="4" ></form:textarea></td>
</tr>


</table>

<table  cellpadding="0" cellspacing="0" align="right" class="SalesPaymentMode">

<tr class="PaymentModeHead">
<td class="pay" colspan="1">Receipt Mode</td> 
<td colspan="1"><form:checkbox path="cashPayment" id="cash" value="Cash" />Cash</td>
<td colspan="1"><form:checkbox path="chequePayment" id="cheque" value="Cheque"/>Cheque</td>
<td colspan="1"><form:checkbox path="cardPayment" id="card" value="Card"/>Card</td>
<td colspan="2"><form:checkbox path="voucherPayment" id="voucher" value="Voucher"/>Voucher</td>
</tr>

<tr>
<tr id="lcash_Amount" class="cash" >
<td>Cash Amount</td>
<td><form:input path="cashAmount" class="numField" id="cash_Amount" size="8"  cssStyle="text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"/></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>

<tr class="cheque" >
<td id="lcheque_Amount">Cheque Amount</td>
<td><form:input path="chequeAmount" class="numField" id="cheque_Amount" size="8" cssStyle="text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"/></td>
<td id="lcheque_Bank">Bank</td>
<td>
	<form:select id="cheque_Bank" path="chequeBank">
		<form:option value="" label="Select"></form:option>
		<c:forEach var="ledger" items="${bank_name}">			
		<form:option value="${ledger.ledgerName}"></form:option>
		</c:forEach>
	</form:select> 
</td>
<td id="lcheque_Details">Cheque Details </td>
<td><form:input id="cheque_Details" path="chequeDetails" size="8"/></td>
</tr>

<tr id="lcard_Amount" class="card" >
<td id="lcard_CommissionAmount" >Card Amount </td>
<td><form:input path="calCardAmount" class="numField" id="calCard_Amount" size="8" cssStyle="text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"/></td>
<td id="lcard_Bankll" >Bank</td>
<td>
	<form:select id="card_Bank" path="cardBank">
	<form:option value="" label="Select"></form:option>
	<c:forEach var="ledger" items="${bank_name}">			
	<form:option value="${ledger.ledgerName}"></form:option>
	</c:forEach>
	</form:select> 
</td>
<td id="lcard_Details" > Card Details </td>
<td>
	<form:input path="cardDetails" id="card_Details" size="8"/>
</td>
</tr>
<tr id="lcard_Bank"  class="card" >
<td id="lcard_CommissionPercent." >Commission Percent </td>
<td><form:input path="commissionPercent" class="numField" id="com_percentage" size="8" cssStyle="text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"/></td>

<td id="lcard_CommissionAmount1" >Commission Amount </td>
<td><form:input path="commissionAmount" class="numField" id="com_Amount" size="8" cssStyle="text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"/></td>


<td>Final Card Amount </td>
<td><form:input path="cardAmount" class="numField" id="card_Amount"  readonly="true" size="8" cssStyle="text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"/></td>
</tr>

<tr class="voucher" >
<td id="lvoucher_Amount">Voucher Amount </td>
<td><form:input path="voucherAmount" class="numField" id="voucher_Amount" size="8" cssStyle="text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="ValidateNumberKeyUp(this);" onkeydown = "return (event.keyCode!=13);"/></td>
<td id="lvouchers">Vouchers</td>
<td>
	<form:select id="vouchers" path="voucherList">
	<form:option value="" label="Select"></form:option>
	<c:forEach var="ledger" items="${assets_name}">			
	<form:option value="${ledger.ledgerName}"></form:option>
	</c:forEach>
	</form:select> 
</td>
<td id="lvoucher_Details">Voucher Details </td>
<td><form:input path="voucherDetails" id="voucher_Details" size="8"/></td>
</tr>	

<!--Subtotal-->			
<!-- <tr><td colspan="9">&nbsp;</td><td colspan="2">&nbsp;</td><td colspan="2">Sub Total </td> -->

<!--Payment Mode 	-->
</table>	
<table cellpadding="0" cellspacing="0" align="left" width="100%" class="salesButton_style">
<tr><td align="center">
		<input type="submit" id="insert" name="insert" value="Insert" class="button_style" />
		<input type="submit" id="update" name="update" value="Update" class="button_style" />
		<input type="submit" id="cancelSales" name="cancelSales" value="Cancel Sales" class="button_style" />
		<input type="submit" name="cancel" value="Cancel" class="button_style" />
		<form:input type="hidden" path="formType" id="formType" />
		<form:input type="hidden" path="salesId" id="salesID"/>
		<form:input type="hidden" path="salesTypeId" />
		<form:input type="hidden" path="billNo" />
		<c:forEach var="gs" items="${gs_rate}">		
		<form:input type="hidden" path="goldOrnBoardRate" value="${gs.goldOrnaments}" />
		<form:input type="hidden" path="goldBullBoardRate"  value="${gs.goldBullion}"/>
		<form:input type="hidden" path="oldGoldOrnBoardRate"  value="${gs.exchangeGold}" />
		<form:input type="hidden" path="silverOrnBoardRate"  value="${gs.silverOrnaments}"/>
		<form:input type="hidden" path="oldSilverOrnBoardRate" value="${gs.exchangeSilver}"/>
		<form:input type="hidden" path="silverBullBoardRate"  value="${gs.silverBullion}"/>
		<form:input type="hidden" path="rateMasterDate" id="date" value="${gs.lastUpdateDate}"/>
</c:forEach>
		<input type="hidden" id="ornamentRate"/>
		<c:forEach var="gs" items="${CurrenTime}">	
		<input type="hidden" id="currentTime"/>
		</c:forEach>
		<form:input type="hidden" id="billType" path="billType" />
	</td>
</tr>	 
</table>
 
</td>
</tr>
</table>
<!-- For Irrelevant Item Details Validation   -->
<input type="hidden" id="R1icode" name="R1icode" />	
<input type="hidden" id="R2icode" name="R2icode" />	
<input type="hidden" id="R3icode" name="R3icode" />	
<input type="hidden" id="R4icode" name="R4icode" />	
<input type="hidden" id="R5icode" name="R5icode" />	

<form:input path="printInvoice" type="hidden" id="printInvoice" value=""/>
<c:forEach var="gs" items="${gs_rate}">		
	<input type="hidden" id="hgrate" value="${gs.goldOrnaments}" />
	<input type="hidden" id="hsrate" value="${gs.silverOrnaments}" />
</c:forEach>
<c:forEach var="code1" items="${itemList1}">
	<input type="hidden" id="hicode" value="${code1.itemCode}"/>
	<input type="hidden" id="hiname" value="${code1.itemName}"/>
  	<input type="hidden" id="hcname" value="${code1.categoryName}"/>
	<input type="hidden" id="hbtype"  value="${code1.metalType}"/>
	<input type="hidden" id="hmetalUsed"  value="${code1.metalUsed}"/>
	<input type="hidden" id="hgrwt" value="${code1.grossWeight}" class="numField" />
	<input type="hidden" id="hstnCost" value="${code1.stoneCost}" class="numField" />
	<input type="hidden" id="hvaPercent" value="${code1.vaPercentage}" class="numField" />
	<input type="hidden" id="hmcAmt" value="${code1.mcPerGram}" class="numField" />
<!-- New added for mc rupees 11/12/12 -->	<input type="hidden" id="hmcRupee" value="${code1.mcInRupee}" class="numField" />
	<input type="hidden" id="hVat" value="${code1.tax}" class="numField" />
	<input type="hidden" id="hlessP" value="${code1.lessPercentage}" class="numField" />
	<input type="hidden" id="hstnDesc" value="${code1.stone}"/>
	<input type="hidden" id="hman" value="${code1.itemseal}" />
	<input type="hidden" id="totpieces" value="${code1.totalPieces}" class="numField"  />
	<input type="hidden" id="hqty" value="${code1.qty}" class="numField" />
	<input type="hidden" id="hitemHMCharges" value="${code1.itemHMCharges}" class="numField"  />
</c:forEach>
	
<c:forEach var="code2" items="${itemList2}">
	<input type="hidden" name="hicode1" id="hicode1" value="${code2.itemCode}"/>
	<input type="hidden" id="hiname1" value="${code2.itemName}"/>
  	<input type="hidden" id="hcname1" value="${code2.categoryName}"/>
	<input type="hidden" id="hbtype1"  value="${code2.metalType}"/>
	<input type="hidden" id="hmetalUsed1"  value="${code2.metalUsed}"/>
	<input type="hidden" id="hgrwt1" value="${code2.grossWeight}" class="numField" />
	<input type="hidden" id="hstnCost1" value="${code2.stoneCost}" class="numField" />
	<input type="hidden" id="hvaPercent1" value="${code2.vaPercentage}" class="numField" />
	<input type="hidden" id="hmcAmt1" value="${code2.mcPerGram}" class="numField" />
	<input type="hidden" id="hmcRupee1" value="${code2.mcInRupee}" class="numField" /><!-- New added for mc rupees 11/12/12 -->
	<input type="hidden" id="hVat1" value="${code2.tax}" class="numField" />
	<input type="hidden" id="hlessP1" value="${code2.lessPercentage}" class="numField" />
	<input type="hidden" id="hstnDesc1" value="${code2.stone}"/>
	<input type="hidden" id="hman1" value="${code2.itemseal}" />
	<input type="hidden" id="totpieces1" value="${code2.totalPieces}" class="numField"  />
	<input type="hidden" id="hqty1" value="${code2.qty}" class="numField" />
	<input type="hidden" id="hitemHMCharges1" value="${code2.itemHMCharges}" class="numField"  />
</c:forEach>
	
	<c:forEach var="code3" items="${itemList3}">
	<input type="hidden" name="hicode2" id="hicode2" value="${code3.itemCode}"/>
	<input type="hidden" id="hiname2" value="${code3.itemName}"/>
  	<input type="hidden" id="hcname2" value="${code3.categoryName}"/>
	<input type="hidden" id="hbtype2"  value="${code3.metalType}"/>
	<input type="hidden" id="hmetalUsed2"  value="${code3.metalUsed}"/>
	<input type="hidden" id="hgrwt2" value="${code3.grossWeight}" class="numField" />
	<input type="hidden" id="hstnCost2" value="${code3.stoneCost}" class="numField" />
	<input type="hidden" id="hvaPercent2" value="${code3.vaPercentage}" class="numField" />
	<input type="hidden" id="hmcAmt2" value="${code3.mcPerGram}" class="numField" />
	<!-- New added for mc rupees 11/12/12 --><input type="hidden" id="hmcRupee2" value="${code3.mcInRupee}" class="numField" />
	<input type="hidden" id="hVat2" value="${code3.tax}" class="numField" />
	<input type="hidden" id="hlessP2" value="${code3.lessPercentage}" class="numField" />
	<input type="hidden" id="hstnDesc2" value="${code3.stone}"/>
	<input type="hidden" id="hman2" value="${code3.itemseal}" />
	<input type="hidden" id="totpieces2" value="${code3.totalPieces}" class="numField" />
	<input type="hidden" id="hqty2" value="${code3.qty}" class="numField" />
	<input type="hidden" id="hitemHMCharges2" value="${code3.itemHMCharges}" class="numField"  />
</c:forEach>
	
	<c:forEach var="code4" items="${itemList4}">
	<input type="hidden" name="hicode3" id="hicode3" value="${code4.itemCode}"/>
	<input type="hidden" id="hiname3" value="${code4.itemName}"/>
  	<input type="hidden" id="hcname3" value="${code4.categoryName}"/>
	<input type="hidden" id="hbtype3"  value="${code4.metalType}"/>
	<input type="hidden" id="hmetalUsed3"  value="${code4.metalUsed}"/>
	<input type="hidden" id="hgrwt3" value="${code4.grossWeight}" class="numField" />
	<input type="hidden" id="hstnCost3" value="${code4.stoneCost}" class="numField" />
	<input type="hidden" id="hvaPercent3" value="${code4.vaPercentage}" class="numField" />
	<input type="hidden" id="hmcAmt3" value="${code4.mcPerGram}" class="numField" />
	<!-- New added for mc rupees 11/12/12 --><input type="hidden" id="hmcRupee3" value="${code4.mcInRupee}" class="numField" />
	<input type="hidden" id="hVat3" value="${code4.tax}" class="numField" />
	<input type="hidden" id="hlessP3" value="${code4.lessPercentage}" class="numField" />
	<input type="hidden" id="hstnDesc3" value="${code4.stone}"/>
	<input type="hidden" id="hman3" value="${code4.itemseal}" />
	<input type="hidden" id="totpieces3" value="${code4.totalPieces}" class="numField" />
	<input type="hidden" id="hqty3" value="${code4.qty}" class="numField" />
	<input type="hidden" id="hitemHMCharges3" value="${code4.itemHMCharges}" class="numField"  />
</c:forEach>

	<c:forEach var="code5" items="${itemList5}">
	<input type="hidden" name="hicode4" id="hicode4" value="${code5.itemCode}"/>
	<input type="hidden" id="hiname4" value="${code5.itemName}"/>
  	<input type="hidden" id="hcname4" value="${code5.categoryName}"/>
	<input type="hidden" id="hbtype4"  value="${code5.metalType}"/>
	<input type="hidden" id="hmetalUsed4"  value="${code5.metalUsed}"/>
	<input type="hidden" id="hgrwt4" value="${code5.grossWeight}" class="numField" />
	<input type="hidden" id="hstnCost4" value="${code5.stoneCost}" class="numField" />
	<input type="hidden" id="hvaPercent4" value="${code5.vaPercentage}" class="numField" />
	<input type="hidden" id="hmcAmt4" value="${code5.mcPerGram}" class="numField" />
	<!-- New added for mc rupees 11/12/12 --><input type="hidden" id="hmcRupee4" value="${code5.mcInRupee}" class="numField" />
	<input type="hidden" id="hVat4" value="${code5.tax}" class="numField" />
	<input type="hidden" id="hlessP4" value="${code5.lessPercentage}" class="numField" />
	<input type="hidden" id="hstnDesc4" value="${code5.stone}"/>
	<input type="hidden" id="hman4" value="${code5.itemseal}" />
	<input type="hidden" id="totpieces4" value="${code5.totalPieces}" class="numField" />
	<input type="hidden" id="hqty4" value="${code5.qty}" class="numField" />
	<input type="hidden" id="hitemHMCharges4" value="${code5.itemHMCharges}" class="numField"  />
</c:forEach>	
</form:form>	
</div>
</div>
</div>
</div>
<script type="text/javascript" src="script/jquery.numeric.js"></script>
<script type="text/javascript" src="script/js_ExchangePurchase.js"></script> 
<script type="text/javascript" src="script/js_CustomerCreation.js"></script>
<script type="text/javascript" src="script/formValidations.js"></script>
</body>
</html>