<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<body>
<script type="text/javascript" src="script/cash.js"></script>
<div id="content_container" align="left" >
<div id="col-form1">
<div id="mask">
</div>  
<div id="boxes">
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
					<td class="label">Party Name*</td>
					<td>
						<select name="supplierName" id="exSupplierName">
							<option value="Walk-in" label="Walk-in">Walk-in</option>
							<c:forEach var="ledger" items="${suppliername}">
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
					<td class="label">Dust (Grams)</td>
					<td><input name="stoneWeight" id="exStoneWeight" class="zeroValidationCur numField" maxlength="10" onkeyup="extractNumber(this,3,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
					<td class="label">Less In:
					<input name="less" id="exLessGrams" type="radio" value="grams" class="radio" /> Grams 
					<input name="less" id="exLessPer" type="radio" value="per" class="radio" />%</td>
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
						<input type="submit" name="insert" value="Insert" id="exchangeinsert2" class="button_style"/>			
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
         <div class="content">Customer form here <input type="text" id="custPop"></div> 
    </div>
    
    <div id="purchase" class="window dialog">
    <a href="#" class="close">Close it</a>
        <b>Customer Form</b>
        <div class="content">Purchase form here <input type="text"></div>
     </div>
 </form:form>
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
<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>

	<div class="boxed">
	<div class="title_1" ><b>Sales Order Details</b></div>
	<div class="content" style="height:720px; width:948px; overflow-x:auto ; overflow-y: auto; padding-bottom:10px;">
	<a class="create_or_list" href="joborder.htm">Job Order List</a>
	<a class="create_or_list" href="jewelfix.htm">Jewel Repair List</a>
	<!-- add new on 20-12-12-->
	<a name="modal" id="exchangePopup" class="create_or_list" href="#newpurchaseExchange" title="New Purchase Exchange">Purchase Exchange</a>
	<br>
	<div class="summary">Sales Order Information</div>
	<form:form commandName="salesorder" action="formsalesorder.htm" method="post" class="formStyle">
	<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>
		
	</div>
	<br>
	<input type="hidden" id="errorName" value="${errorType}"/>		
	<div class="smsg summary" style="color:#3EA99F;"></div>	
	<table id="formSalesOrder" cellpadding="0" border="0" cellspacing="0" align="left" style="width:940px;">
	<tr class="form_cat_head"><td colspan="4">Sales Order Information</td></tr>
	<tr>		
		<td class="label">Customer Name * <a name="modal" id="custSalesPopup" class ="add_new" href="#newCustomer" title="Add New Customer">Add</a></td>
		<td>
			<form:select path="customerName" id="poscustomer" class="custName">
			<form:option value="" label="Select"></form:option>
			<c:forEach var="ledger" items="${suppliername}">
			<form:option  value="${ledger.ledgerName}">
			</form:option>
			</c:forEach>
			</form:select>
		</td>	
		
		<td class="label">Salesman Name</td>
		<td><form:select path="salesmanName">
		<form:option value="0">---select---</form:option>
		<c:forEach var="salesmanname" items="${s_manname}">
		<form:option value="${salesmanname.fullName}"></form:option>
		</c:forEach>
		</form:select>
		</td>	
	</tr>
	<tr>
		<td class="label">Order Date</td>
		<td><form:input path="orderDate" id="date"/></td>
					
		<td class="label">Delivery Date</td>
		<td><form:input path="salesDate" id="date1"/></td>
	</tr>	
	
	<tr>		
  	 	<td  class="label">Order Status</td>
  		<td><form:select path="orderStatus" id="status">			
		<form:option value="Accepted" label="Accepted"/>
		<form:option value="Sold" label="Sold"/>
		<form:option value="Cancelled" label="Cancelled"/> 
		</form:select>			
  		</td>
  		
  		<td  class="label">Rate Fix/UnFix</td>
  		<td><form:select path="rateFix" id="fix">	
  		<form:option value="Select" label="Select"></form:option>		
		<form:option value="Fix" label="Fix"/>
		<form:option value="UnFix" label="UnFix"/>		
		</form:select>			
  		</td>
  		
	</tr>  	
	<!--  add new on 21-12-12 for purchae exchange-->  
		<tr>
		<td  class="label">Exchange Grams</td><td><form:input path="exchangeGrams" id="exchangeGrams" class="numField" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,3,true),sum();"/></td>		
		<td class="label">Exchange Amount</td><td><form:input path="exchangeAmount" id="exchangeAmount" class="numField" onkeyup="extractNumber(this,2,true),sum();" /></td>		
		</tr>	
	<!--  add new on 16-1-13 for rateFix  -->
		<tr class="rateFixAmount">
		<td  class="label">Rate Fix Amount</td><td><form:input path="rateFixAmount" id="rateFixAmount" readonly="true" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true),sum();"/></td>		
		<td class="label">Rate Fix Grams</td><td><form:input path="rateFixGrams" id="rateFixGrams"  readonly="true" onkeyup="extractNumber(this,3,true),sum();"/></td>		
		</tr>	
	<tr style="display:none;">
		<!-- <td><font color="red"><form:errors path="customerName"/></font></td><td></td> -->
		<td></td><td></td>
	</tr>	
	<tr>
		<td  class="label">Total Advance Amount</td><td><form:input path="advance" id="advance_paid" readonly="true" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>		
		<td class="label">Total Amount</td><td><form:input path="totalAmount" id="totalamount"  readonly="true"/></td>		
	</tr>
			
	</table>
	<table cellpadding="0" border="0" cellspacing="0" align="left" class="table_two" >
	<tr class="form_cat_head"><td colspan="13">Ordered Item Details</td>
	</tr>			
	<tr>	
	
		<td class="label">Item Name</td><td class="label">Bullion</td>
		<td class="label">Rate</td><td class="label">Gr.Wt</td>
		<td class="label">V.A</td><td class="label">MC</td>
		<td class="label">Stone Desc</td><td class="label">Stone Wt</td>
		<td class="label">Pcs.</td><td class="label">Stone Rate</td>
		<td class="label">Stone Cost</td>	<td class="label">Item Cost</td><td class="label">Description</td>
	
	</tr>
	
	<tr>
		
		<td><form:input path="itemName1" style="width:150px;" class="nameCapitalize"/></td>
		
		<td><form:select path="bullionType1"  class="1" id="bullionType1">
		<form:option value="Select" label="Select"></form:option>
			<form:option value="Gold" label="Gold"/>
			<form:option value="Silver" label="Silver"/>
			</form:select>
		</td>
		
		 <td><form:input path="bullionRate1" id="brate1" class="numField" onkeyup="extractNumber(this,2,true),sum();" size="4"/></td>
		<td><form:input path="grossWeight1" size="7"  id="grossWeight" onkeyup="extractNumber(this,3,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>	
		<td><form:input path="wastage1" size="5" id="wastage" class="numField" onkeyup="extractNumber(this,2,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		<td><form:input path="mcPerGram1" size="5" id="m_charge" class="numField" onkeyup="extractNumber(this,2,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>		
		<td><form:input path="stoneDetails1" size="5" /></td>
		<td><form:input path="stoneWeight1" size="5" id="stoneweight" class="numField" onkeyup="extractNumber(this,3,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		<td><form:input path="noOfPcs1" size="5" id="noOfPcs" class="numField" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		<td><form:input path="stoneRate1" size="5" id="stoneRate" class="numField" onkeyup="extractNumber(this,2,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		<td><form:input path="stoneCost1" size="7" id="stoneCost"  readonly="true"/></td>
		<td><form:input path="itemCost1" size="7" id="itemCost1"  readonly="true"/></td>
		<td><form:input path="description" size="7" id="description1" /></td>
		
	</tr>
	<tr>
		<td><form:input path="itemName2" style="width:150px;" class="nameCapitalize"/></td>	
		
		<td><form:select path="bullionType2" class="2" id="bullionType2">
		<form:option value="Select" label="Select"></form:option>
			<form:option value="Gold" label="Gold"/>
			<form:option value="Silver" label="Silver"/>
			</form:select>
		</td>
	 <td><form:input path="bullionRate2" id="brate2"  class="numField" onkeyup="extractNumber(this,2,true),sum();" size="4"/></td>
					
		<td><form:input path="grossWeight2" size="7" id="grsWht2"  class="numField" onkeyup="extractNumber(this,3,true),sum();"  onkeypress="return ValidateNumberKeyPress(this, event);"/></td>	
		<td><form:input path="wastage2" size="5" id="wastag2"  class="numField" onkeyup="extractNumber(this,2,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		<td><form:input path="mcPerGram2" size="5" id="m_charg2"  class="numField" onkeyup="extractNumber(this,2,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>		
		<td><form:input path="stoneDetails2" size="5" /></td>
		<td><form:input path="stoneWeight2" size="5" id="stweight2"  class="numField" onkeyup="extractNumber(this,3,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		<td><form:input path="noOfPcs2" size="5" id="noPcs2"  class="numField" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		<td><form:input path="stoneRate2" size="5" id="stRate2"  class="numField" onkeyup="extractNumber(this,2,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		<td><form:input path="stoneCost2" size="7" id="stoneCost2"  readonly="true"/></td>
		<td><form:input path="itemCost2" size="7" id="itemCost2"  readonly="true"/></td>
		<td><form:input path="description2" size="7" id="description2"  /></td>
	</tr>
	<tr>
		
		<td><form:input path="itemName3" style="width:150px;" class="nameCapitalize"/></td>
		
		<td><form:select path="bullionType3" class="3" id="bullionType3">
		<form:option value="Select" label="Select"></form:option>
			<form:option value="Gold" label="Gold"/>
			<form:option value="Silver" label="Silver"/>
			</form:select>
		</td>
		<td><form:input path="bullionRate3" id="brate3"class="numField" onkeyup="extractNumber(this,2,true),sum();" size="4"/></td>
							
		<td><form:input path="grossWeight3" size="7" id="grsWht3"  class="numField" onkeyup="extractNumber(this,3,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>	
		<td><form:input path="wastage3" size="5" id="wastag3"  class="numField" onkeyup="extractNumber(this,2,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		<td><form:input path="mcPerGram3" size="5" id="m_charg3"  class="numField" onkeyup="extractNumber(this,2,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>		
		<td><form:input path="stoneDetails3" size="5"/></td>
		<td><form:input path="stoneWeight3" size="5" id="stweight3"  class="numField" onkeyup="extractNumber(this,3,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		<td><form:input path="noOfPcs3" size="5" id="noPcs3"  class="numField" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		<td><form:input path="stoneRate3" size="5" id="stRate3"  class="numField" onkeyup="extractNumber(this,2,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		<td><form:input path="stoneCost3" size="7" id="stoneCost3"  readonly="true"/></td>
		<td><form:input path="itemCost3" size="7" id="itemCost3" readonly="true"/></td>
		<td><form:input path="description3" size="7" id="description3"  /></td>
		
	</tr>
	<tr>
		<td><form:input path="itemName4" style="width:150px;" class="nameCapitalize"/></td>
		
		<td><form:select path="bullionType4" class="4" id="bullionType4">
		<form:option value="Select" label="Select"></form:option>
			<form:option value="Gold" label="Gold"/>
			<form:option value="Silver" label="Silver"/>
			</form:select>
		</td>
		 <td><form:input path="bullionRate4" id="brate4"  class="numField" onkeyup="extractNumber(this,2,true),sum();" size="4"/></td>
		<td><form:input path="grossWeight4" size="7" id="grsWht4"  class="numField"onkeyup="extractNumber(this,3,true),sum();"  onkeypress="return ValidateNumberKeyPress(this, event);"/></td>	
		<td><form:input path="wastage4" size="5" id="wastag4" onkeyup="extractNumber(this,2,true),sum();" class="numField" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		<td><form:input path="mcPerGram4" size="5" id="m_charg4" onkeyup="extractNumber(this,2,true),sum();" class="numField" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>		
		<td><form:input path="stoneDetails4" size="5" /></td>
		<td><form:input path="stoneWeight4" size="5" id="stweight4"  class="numField" onkeyup="extractNumber(this,3,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		<td><form:input path="noOfPcs4" size="5" id="noPcs4"  class="numField" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		<td><form:input path="stoneRate4" size="5" id="stRate4"  class="numField" onkeyup="extractNumber(this,2,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		<td><form:input path="stoneCost4" size="7" id="stoneCost4" readonly="true"/></td>
		<td><form:input path="itemCost4" size="7" id="itemCost4"  readonly="true"/></td>
		<td><form:input path="description4" size="7" id="description4"  /></td>
	</tr>
	<tr>
		<td><form:input path="itemName5" style="width:150px;" class="nameCapitalize"/></td>	
		
		<td><form:select path="bullionType5" class="5" id="bullionType5">
		<form:option value="Select" label="Select"></form:option>
			<form:option value="Gold" label="Gold"/>
			<form:option value="Silver" label="Silver"/>
			</form:select>
		</td>
		<td><form:input path="bullionRate5" id="brate5"  class="numField" onkeyup="extractNumber(this,2,true),sum();" size="4"/></td>
		<td><form:input path="grossWeight5" size="7" id="grsWht5"   class="numField" onkeyup="extractNumber(this,3,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>	
		<td><form:input path="wastage5" size="5" id="wastag5" onkeyup="extractNumber(this,2,true),sum();" class="numField"  onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		<td><form:input path="mcPerGram5" size="5" id="m_charg5"  class="numField" onkeyup="extractNumber(this,2,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>		
		<td><form:input path="stoneDetails5" size="5" /></td>
		<td><form:input path="stoneWeight5" size="5" id="stweight5" onkeyup="extractNumber(this,3,true),sum();"  class="numField"  onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		<td><form:input path="noOfPcs5" size="5" id="noPcs5"  class="numField" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		<td><form:input path="stoneRate5" size="5" id="stRate5"  class="numField" onkeyup="extractNumber(this,2,true),sum();" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		<td><form:input path="stoneCost5" size="7" id="stoneCost5"  readonly="true" /></td>
		<td><form:input path="itemCost5" size="7" id="itemCost5"  readonly="true" /></td>
		<td><form:input path="description5" size="7" id="description5"  /></td>
		<form:input type="hidden" path="salesOrderId"/>	
		</tr>
		<tr class="SalesOrderPaymentModetrhead"> 
			<td colspan="2"> </td> 
			<td class="pay" colspan="2">Receipt Mode</td> 
			<td colspan="2"><form:checkbox path="cashPaymentSO" id="cash" value="Cash" />Cash</td>
			<td colspan="2"><form:checkbox path="chequePaymentSO" id="cheque" value="Cheque"/>Cheque</td>
			<td colspan="2"><form:checkbox path="cardPaymentSO" id="card" value="Card"/>Card</td>
			<td colspan="3"><form:checkbox path="voucherPaymentSO" id="voucher" value="Voucher"/>Voucher</td>
		</tr>
		<tr class="SalesOrderPaymentModetr SalesOrderPaymentMode_cash">
			<td colspan="2"> </td> 
			<td id="lcash_Amount" colspan="2">Amount in Cash</td>
			<td colspan="2"><form:input path="cashAmountSO" class="numField" id="cash_Amount"  size="8"  cssStyle="text-align:right" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);"  onkeydown = "return (event.keyCode!=13);" /></td>
			<td id="lcash_Bank">Cash</td>
			<td colspan="6">
				<form:select id="cash_Bank" path="cashBankSO">
					
					<c:forEach var="ledger" items="${cash_name}">			
					<form:option value="${ledger.ledgerName}"></form:option>
					</c:forEach>
				</form:select> 
			</td>
		</tr>
		<tr class="SalesOrderPaymentModetr SalesOrderPaymentMode_cheque">
			<td colspan="2"> </td> 
			<td id="lcheque_Amount" colspan="2">Amount in Cheque </td>
			<td colspan="2"><form:input path="chequeAmountSO" class="numField" id="cheque_Amount" size="8" cssStyle="text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
			<td id="lcheque_Bank">Bank</td>
			<td colspan="2">
				<form:select id="cheque_Bank" path="chequeBankSO">
					<form:option value="" label="Select"></form:option>
					<c:forEach var="ledger" items="${bank_name}">			
					<form:option value="${ledger.ledgerName}"></form:option>
					</c:forEach>
				</form:select> 
			</td>
			<td id="lcheque_Details" colspan="2">Cheque Details </td>
			<td colspan="2"><form:input id="cheque_Details" path="chequeDetailsSO" size="8"/></td>
		</tr>
		<tr class="SalesOrderPaymentModetr SalesOrderPaymentMode_card">
			<td colspan="2"> </td> 
			<td id="lcard_Amount" colspan="2" > Amount in Card </td>
			<td colspan="2"><form:input path="cardAmountSO" class="numField" id="card_Amount" size="8" cssStyle="text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
			<td id="lcard_Bank" >Bank</td>
			<td colspan="2">
				<form:select id="card_Bank" path="cardBankSO">
				<form:option value="" label="Select"></form:option>
				<c:forEach var="ledger" items="${bank_name}">			
				<form:option value="${ledger.ledgerName}"></form:option>
				</c:forEach>
				</form:select> 
			</td>
			<td id="lcard_Details" colspan="2" > Card Details </td>
			<td colspan="2">
				<form:input path="cardDetailsSO" id="card_Details" size="8"/>
			</td>
		</tr>
		<tr class="SalesOrderPaymentModetr SalesOrderPaymentMode_voucher">
			<td colspan="2"> </td> 
			<td id="lvoucher_Amount" colspan="2">Amount in Gift Voucher </td>
			<td colspan="2"><form:input path="voucherAmountSO" class="numField" id="voucher_Amount" size="8" cssStyle="text-align:right" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" onkeydown = "return (event.keyCode!=13);"/></td>
			<td id="lvouchers">Vouchers</td>
			<td colspan="2">
				<form:select id="vouchers" path="voucherListSO">
				<form:option value="" label="Select"></form:option>
				<c:forEach var="ledger" items="${assets_name}">			
				<form:option value="${ledger.ledgerName}"></form:option>
				</c:forEach>
				</form:select> 
			</td>
			<td id="lvoucher_Details" colspan="2">Voucher Details </td>
			<td colspan="2"><form:input path="voucherDetailsSO" id="voucher_Details" size="8"/></td>
		</tr>
						
	<tr><td align="center" colspan="14">
		    <input name="printInvoice" type="hidden" id="printInvoice" value="${printInvoice}"/>
		    <input name="printPreviewId" type="hidden" id="printId" value="${salesid}"/>
		    <input type="submit" name="insert" value="Insert" id="insert" style="width:150px;" class="button_style"/>
            <input type="submit" name="update" value="Update" id="update" style="width:150px;" class="button_style"/>
          	<!-- input type="submit" name="delete" value="Delete" id="update"/ -->
            <input type="submit" name="cancel" value= "Cancel" style="width:150px;" class="button_style"/></td>
    </tr>
    <c:forEach var="gs" items="${gs_rate}">
    <input type="hidden" id="gold1" value="${gs.goldOrnaments}" />
    <input type="hidden" id="silver1" value="${gs.silverOrnaments}" />
	</c:forEach>
</table> 
	<input type="hidden" name="errorName" id="errorInfo" value="${orderErrorType}" />		
	</form:form>
</div>
</div>
</div>
</div>

<script language="javascript" type="text/javascript" > 
	
/*** new script added to allow only _ and - for customer name field on 25-2-13 after bug is posted ****/
$('#popcustomerName').keyup(function() {
	 $(this).val($(this).val().replace(/[^A-Za-z0-9\s_-]/g,''));
});
	
	var update_record= $("#salesOrderId");	
		
	$(document).ready(function(){		
		$("#date").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		$("#date1").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate =myDate.getDate()+ '/' + month + '/' + myDate.getFullYear();
		if(document.getElementById('date').value=='')
		{
			document.getElementById('date').value=prettyDate;
			return true;
		}
		else
		{
			return false;
		}	
		
	});	
	
	$(document).ready(function(){
		bullionTypeRate();
		var URIloc = $(location).attr('href');
		var uripath = URIloc.split('/')[4];	
		if(uripath.indexOf("viewSalesOrder.htm") >=0 )
		{	
			$("#insert").hide();
			$("#update").show();
			var soType = $('#status').val();
				
		    if(soType == "Sold"){
		    	$('#status').attr("disabled",true);
		    }
		    $("#advance_paid").attr("disabled",true);
		   exchangeSalesOrderDisable();
			$('#status').change(ordersatatustype).trigger("change");
			$('#status').keyup(ordersatatustype).trigger("change");	
			//ratefixcall();
		}else if(uripath.indexOf("newSalesOrder.htm") >=0 )
		{
			$("#update").hide();
			$("#insert").show();
		}
		
		var errorType = $("#errorInfo").val();
		if(errorType == "insertError"){
			 $("#insert").show();
			 $("#update").hide();
		 }else if(errorType == "updateError"){
			 $("#insert").hide();
			 $("#update").show();
		 }		
		$('#insert').click(initZero);
		//$('#insert').click(setorderstatus);
		$('#update').click(initZero);
		$("#update").click(function()
		{		
			$("#advance_paid").removeAttr("disabled");		 
		});
		$("#fix").change(ratefixcall);//add on 16-1-13
		var fix=$("#fix").val();
		if(fix=="Fix"){
			$(".rateFixAmount").show();//add on 16-1-13
		}else{
			$(".rateFixAmount").hide();//add on 16-1-13
		}
	});
	
	/*For Deleivery date validation
	$('#insert').click(salesorderdatecomparison);
	$('#update').click(salesorderdatecomparison);
	
	function salesorderdatecomparison() {
		var orderDate = $("#date").val();
		  var salesDate = $("#date1").val();
		

		  if (Date.parse(orderDate) > Date.parse(salesDate)) {
		      //$("#DateFrom").val(DateToValue)
		      alert("Invalid Date ");
		      return false;
		  }
		  
	}*/
	function initZero() {
		$('input.numField').each(function() {
			var TempVal = $(this).val();
			if (TempVal.length === 0 || TempVal === '' || TempVal === null) {
				$(this).val("0");
			}
		});
	}
	/*//function //init(){
		
		  var ZERO = 0;		
			
		 if($("#advance_paid").val().length == 0){
			$("#advance_paid").val(ZERO.toFixed(2));			
		 }		 		 
		 
		 if($("#noOfPcs1").val().length == 0){
			$("#noOfPcs1").val(ZERO);		
		 }
		 
		 if($("#noOfPcs2").val().length == 0){
			$("#noOfPcs2").val(ZERO);		
		 }
		 
		 if($("#noOfPcs3").val().length == 0){
			$("#noOfPcs3").val(ZERO);		
		 }
		 if($("#noOfPcs4").val().length == 0){
			$("#noOfPcs4").val(ZERO);		
		 }
		 
		 if($("#noOfPcs5").val().length == 0){
			$("#noOfPcs5").val(ZERO);		
		 }		 		 
	}	*****/
	
	/* function setorderstatus(){
		
		var sType  = $("#status").val();
		if(sType == "Sold" || sType == "Cancelled"){
			 alert('Select the status as Accepted');
			 return false;
		 }
	} */
	
	//add on 16-1-13
	function ratefixcall(){
		var fix=$("#fix").val();
		if(fix=="Fix"){
			$(".rateFixAmount").show();
			ratefixamtsum();
		}else{
			$(".rateFixAmount").hide();
			$("#rateFixAmount").val("0");
			$("#rateFixGrams").val("0.00");
		}
	}
	
	
	function ordersatatustype() {
	    var sType = $('#status').val();
	    if(sType == "Sold" || sType == "Cancelled"){
	    	$('#advance_paid').attr("readonly",true);			 
		}
	    else{
	    	$('#advance_paid').removeAttr("readonly",true);
	    }
	    return false;
	}
	
	if(update_record != 'null')
	{		
		if(sType == 'Cancelled')
		{
			$('#update').hide();
		}		
	}
	
	

	//first row gold and silver dynamic rate oncahnge
function bullionTypeRate(){
	$('[id^=bullionType]').change(function() {
		//ratefixgrmsum();
	    var values = $(this).val();
	    var BlnTypenum = $(this).attr("class");
	    var goldH=$("#gold1").val();
	    var silverS=$("#silver1").val();
	 
	    if (values == 'Gold') {
	        $('#brate'+BlnTypenum).val(goldH);
	    }
		if (values == 'Silver') {
	        $('#brate'+BlnTypenum).val(silverS);
	    }
		if (values == 'Select') {
	        $('#brate'+BlnTypenum).val("0.00");
	   }
		
	    return false;
	});
}
	
function exchangeSalesOrderDisable(){
	if($('#status').val()=='Sold'  )
	{
		$('#update').hide();		
	}	
	
}

</script>	
<script language="javascript" src="script/salesorder.js"></script>
<script type="text/javascript" src="script/js_CustomerCreation.js"></script>
<script type="text/javascript" src="script/js_ExchangePurchase.js"></script> 
</body>		

</html>