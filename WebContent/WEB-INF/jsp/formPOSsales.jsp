<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>POS Sales Form</title>
</head>
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
<div id="col-form1" >
	<div class="boxed" >
	<div class="title_1" style="text-align:left;" >POS Sales Form</div>
	<div class="content" style=" height:auto; width:948px; overflow-x:auto ; overflow-y: auto; padding-bottom:10px;">
	
 <%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
<a class="create_or_list" href="possalesreturnList.htm" style="float:left;">Sales Return</a> 
<div class="summary">POS Sales Form</div>
<div class="tab_form">

	<form:form name="form1" id="form1" commandName="POSsales" method="POST" action="formPOSsales.htm" class="formStyle">
	<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span> 
	</div>
	
	<div class="smsg summary"  style="color:#3EA99F;"></div>
	<table id="formPOSpurchase" cellpadding="0" cellspacing="0" align="center" class="POSpurchase">
		<tr class="form_cat_head">
				<td colspan=6>Sales	Information</td>
		</tr>
		<tr>
				<td class="label">Sales Type</td>
				<td><form:input path="possales.salesType"  id="salesType" readonly="true"/></td>
				
				
				<td class="label">Salesman Name</td>
				
				
			<td><input name="possales.salesmanName" readonly="readonly" value="<%=session.getAttribute("username")%>" size="20"  /></td>
				<td class="label">Date</td>
				<td><form:input path="possales.salesdate" id="date" /></td>
		</tr>
		<tr>
				<td class="label">Customer Name <a name="modal" id="custSalesPopup" class ="add_new" href="#newCustomer" title="Add New Customer">Add</a></td>
				<!-- <td><form:input path="possales.customerName" id="poscustomer" style="width:150px;" class="categorytext noEnterSubmit"/> -->
					<td>
				<form:select path="possales.customerName" id="poscustomer" style="width:150px;" class="custName noEnterSubmit">
				<form:option value="" label="Select"></form:option>
				<form:option value="Walk-in" label="Walk-in"></form:option>
				<c:forEach var="ledger" items="${customername}">
				<form:option  value="${ledger.ledgerName}">
				</form:option>
				</c:forEach>
				</form:select>
		</td>
					
				
				<td class="label possupplier row_NameCity">Name*</td>
				<td class="possupplier row_NameCity"><form:input path="possales.walkinCustomer" id="walkincust" class="categorytext noEnterSubmit w_Name"/></td>
				<td class="label possupplier row_NameCity"></td>
				<td class="possupplier row_NameCity"></td>
				<!--  <td class="label possupplier">Address*</td>
				<td class="possupplier"><form:input path="possales.walkinAddress"/></td>-->
				
				<td class="label possupplier1"></td>
				<td class="possupplier1"></td>
				<td class="label possupplier1"></td>
				<td class="possupplier1"></td>
		</tr>
		
	</table>
	<table cellpadding="0" cellspacing="0" align="center" class="POSsales">
	<tr class="RowHead">
		<td></td>
		<td>Item Code</td>
		<td>Category Name</td>
		<td>Item Name</td>
		<td>Qty</td>
		<td>Pcs</td>
		<td>Rate</td>
		<td>Discount in(Rs)</td>
		<td>Tax %</td>
		<td>Amount</td>
		
	</tr>
	
	<c:set var="countVal" value="0" scope="page"/>
	<c:forEach var="poslistVal" items="${possalesList}" varStatus="popSalesFormList" >
		<c:set var="countVal" value="${popSalesFormList.count}" scope="page"/>
	</c:forEach>
	<c:if test="${countVal eq 0}" >
			<tr class="staticRow">
				<td><input type="button" name="delete" class="del" value="" id="PosrowDelButton0" style="width:20px;" title="0"/></td>
		<td><form:input path="listpossalesItem[0].posItemCode" class="icode noEnterSubmit" id="dIcode0" title="0" /> </td>
		<td><form:input path="listpossalesItem[0].categoryName"  class="category noEnterSubmit" id="categoryname0" readonly="true" title="0" /> </td>
		<td><form:input path="listpossalesItem[0].itemName"  id="dIname0" class="category noEnterSubmit" readonly="true" title="0" /> </td>
		<td><form:input path="listpossalesItem[0].quantity" class="QtyPcs noEnterSubmit"  id="qty0" readonly="true" title="0"/> </td>
		<td><form:input path="listpossalesItem[0].totalPieces" class="QtyPcs noEnterSubmit"  id="pcs0" readonly="true" title="0"/> </td>
		<td><form:input path="listpossalesItem[0].salesRate" class="posrate noEnterSubmit" id="salesrates0"  title="0" onkeydown="return (event.keyCode!=13);" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/> </td>
		<td><form:input path="listpossalesItem[0].discountAmount" id="discounts0" title="0"class="categorytext noEnterSubmit" onkeydown="return (event.keyCode!=13);" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/> </td>
		<td><form:input path="listpossalesItem[0].salesTax" id="tax0" readonly="true" title="0"class="categorytext noEnterSubmit"/> </td>
		<td><form:input path="listpossalesItem[0].salesAmount" class="posrate noEnterSubmit" readonly="true"id="amt0" title="0"/> 
		<form:input path="listpossalesItem[0].POSdeleted" type="hidden" id="posdeleted0" class="isDeleted"/>
		<form:input path="listpossalesItem[0].itemSalesType" type="hidden"  readonly="true"/>
		<form:input type="hidden" path="listpossalesItem[0].salesItemID"  />	
		<form:input type="hidden" path="listpossalesItem[0].itemStatus" id="positemStatus0" title="0" class="itemStatus" />	</td>	
			</tr>
	</c:if>
	
	<!--  TO show Data on Insert Error and Update Error -->
	<c:set var="index" value="${0}" scope="page"/>
	<c:forEach var="plist3" items="${possalesList}">	
		<tr class="staticRow">
			<td><input type="button" name="delete" class="del" value="" id="PosrowDelButton${index}"style="width:20px;" title="${index}"/></td>
		<td><form:input path="listpossalesItem[${index}].posItemCode" value="${plist3.posItemCode}" class="icode noEnterSubmit" id="dIcode${index}" title="${index}"/> </td>
		<td><form:input path="listpossalesItem[${index}].categoryName" value="${ plist3.categoryName}"  class="category noEnterSubmit" id="categoryname${index}" readonly="true" title="${index}"/> </td>
		<td><form:input path="listpossalesItem[${index}].itemName" value="${plist3.itemName}" id="dIname${index}" class="category noEnterSubmit" readonly="true" title="${index}"/> </td>
		<td><form:input path="listpossalesItem[${index}].quantity" value="${plist3.quantity }" class="QtyPcs noEnterSubmit"  id="qty${index}" readonly="true" title="${index}"/> </td>
		<td><form:input path="listpossalesItem[${index}].totalPieces" value="${plist3.totalPieces}" class="QtyPcs noEnterSubmit"  id="pcs${index}" readonly="true" title="${index}"/> </td>
		<td><form:input path="listpossalesItem[${index}].salesRate" value="${ plist3.salesRate}" class="posrate noEnterSubmit" id="salesrates${index}"  title="${index}" onkeydown="return (event.keyCode!=13);" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/> </td>
		<td><form:input path="listpossalesItem[${index}].discountAmount" value="${plist3.discountAmount}" id="discounts${index}" class="noEnterSubmit" title="${index}"  onkeydown="return (event.keyCode!=13);" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
		<td><form:input path="listpossalesItem[${index}].salesTax" id="tax${index}"  value="${plist3.salesTax}"readonly="true"class="noEnterSubmit" title="${index}"/> </td>
		<td><form:input path="listpossalesItem[${index}].salesAmount" value="${plist3.salesAmount}" class="posrate noEnterSubmit" id="amt${index}" readonly="true"title="${index}"/> </td>
		<td><form:input path="listpossalesItem[${index}].POSdeleted" value="${plist3.POSdeleted}" type="hidden" id="posdeleted${index}" title="${index}" class="isDeleted"/>
		<form:input type="hidden" path="listpossalesItem[${index}].salesItemID" value="${plist3.salesItemID}"  title="${index}"/>	
		<form:input path="listpossalesItem[${index}].itemSalesType" type="hidden"  readonly="true"/>	
        <form:input type="hidden" path="listpossalesItem[${index}].itemStatus" id="positemStatus${index}" title="${index}" class="itemStatus" />	</td>						
		</tr>
		<c:set var="index" value="${index + 1}" scope="page"/>
	</c:forEach>		
	
	<!--  TO show Data on Update Mode -->
	 	
  	<c:set var="indx" value="${1}" scope="page"/>
  	
	<c:forEach var="plist" items="${salesItemsList}">
		<tr class="staticRow">
			<td><input type="button" name="delete" class="del" value="" id="PosrowDelButton${indx}" style="width:20px;" title="${indx}"/></td>
		<td><form:input path="listpossalesItem[${indx}].posItemCode" class="icode noEnterSubmit" id="dIcode${indx}" title="${indx}"/> </td>
		<td><form:input path="listpossalesItem[${indx}].categoryName"  class="category noEnterSubmit" id="categoryname${indx}" readonly="true" title="${indx}"/> </td>
		<td><form:input path="listpossalesItem[${indx}].itemName"  id="dIname${indx}" class="category noEnterSubmit" readonly="true" title="${indx}"/> </td>
		<td><form:input path="listpossalesItem[${indx}].quantity" class="QtyPcs noEnterSubmit"  id="qty${indx}" readonly="true" title="${indx}"/> </td>
		<td><form:input path="listpossalesItem[${indx}].totalPieces" class="QtyPcs noEnterSubmit"  id="pcs${indx}" readonly="true" title="${indx}"/> </td>
		<td><form:input path="listpossalesItem[${indx}].salesRate" class="posrate noEnterSubmit" id="salesrates${indx}"  title="${indx}" onkeydown="return (event.keyCode!=13);" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/> </td>
		<td><form:input path="listpossalesItem[${indx}].discountAmount" id="discounts${indx}" class="noEnterSubmit" title="${indx}"  onkeydown="return (event.keyCode!=13);" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
		<td><form:input path="listpossalesItem[${indx}].salesTax" id="tax${indx}" readonly="true"class="noEnterSubmit" title="${indx}"/> </td>
		<td><form:input path="listpossalesItem[${indx}].salesAmount" class="posrate noEnterSubmit" id="amt${indx}" readonly="true"title="${indx}"/> 
		<form:input path="listpossalesItem[${indx}].POSdeleted" type="hidden" id="posdeleted${indx}" title="${indx}" class="isDeleted"/>
		<form:input type="hidden" path="listpossalesItem[${indx}].salesItemID"  title="${indx}"/>	
			<form:input path="listpossalesItem[${indx}].itemSalesType" type="hidden"  readonly="true"/>
			<form:input path="listpossalesItem[${indx}].itemStatus" type="hidden" id="positemStatus${indx}" title="${indx}" class="itemStatus"/></td>
		</tr>
		<c:set var="indx" value="${indx + 1}" scope="page"/>
	</c:forEach>
	 
	
	
		
	<tr class="POSpurchaseTotal">
		<td colspan="2"><input type="button" value="Add Item" id="addItem"></td>
		<td colspan="5"></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr class="POSsalesTotal">
		<td colspan="8"></td>
		<td>Sub Total</td>
		<td><form:input path="possales.subTotal" readonly="true" id="subTotal"/> </td>
	</tr>
	<tr class="POSsalesTotal">
		<td colspan="8"></td>
		<td>Total Tax</td>
		<td><form:input path="possales.taxinAmount" readonly="true" id="totalTax"/> </td>
	</tr>
	<tr class="POSsalesTotal">
		<td colspan="8"></td>
		<td>Total Value</td>
		<td><form:input path="possales.totalAmount" readonly="true" id="totalVal"/> </td>
	</tr>
	<tr class="POSsalesTotal">
		<td colspan="8"></td>
		<td>Round Off</td>
		<td><form:input path="possales.roundOff" id="roundOff"readonly="true" /> </td>
	</tr>
	
	
		<form:input path="possales.printInvoice" type="hidden" id="printInvoice" value=""/>
	
  <tr class="POSsalesTotal">
		<td colspan="8"></td>
		  <td>Total Amount Received:</td>
		<td><form:input path="possales.totalAmountReceived" readonly="true" id="receivedAmts" value=""/></td>
</tr>

	<tr class="POSsalesTotal">
		<td colspan="8"></td>
		  <td>Balance To Customer:</td>
		<td><form:input path="possales.balanceToCustomer"  readonly="true" id="balToCust" value=""/></td>
	
	<tr>
	<tr class="POSsalesTotal" style="display:none;">
		<td colspan="8"></td>
		  <td>Balance To Receive:</td>
		<td><!--  <td>Balance To Receive:</td>-->
	<input type="hidden" id="balReceiv" name="balanceToReceive" readonly="true" value=""/></td>

	</tr>
	<tr class="POSsalesTotal" style="display:none;">
		<td colspan="8"></td>
		  <td>Amount Receive:</td>
		<td><!--  <td>Balance To Receive:</td>-->
	<input type="hidden" id="amountreceiv" name="amot_rev" readonly="true" value=""/></td>
	
	</tr>
	<tr class="POSsalesTotal">
		<td colspan="6"></td>
		<!--<td>Receipt Type</td>
		<td>
			<!-- <select id="" style="width:100px;">
					<option value=""label="Walk_in">Cash</option>
					<option value=""label="">Cheque</option>
					<option value=""label="">Card</option>
					<option value=""label="">Gift Coupon</option>
			</select> 
		</td>-->
		<td class="GrandTotalLabel">Grand Total</td>
		<td class="GrandTotal" colspan="4"><form:input path="possales.netAmount" readonly="true" id="grandAmt"/> </td>
		<form:input type="hidden" path="possales.posSalesId"  />
		
		<form:input type="hidden" path="possales.transactionNo"  />
		<form:input type="hidden" path="possales.billNo"  />
		<form:input type="hidden"  path="possales.billposType" id="billposType"/>
	</tr>
	
	<tr>
		<td colspan="11">
		
			<table cellpadding="0" cellspacing="0" width="100%" class="ReceiptMode">
				<tr>
					<td>Receipt Mode
					</td>
					<td>
						<table cellpadding="0" cellspacing="0" class="table">

						<tr class="head">
						
						<td colspan="6">
						<div><form:checkbox path="possales.card" id="card" value="Card"/>Card</div>
						<div><form:checkbox path="possales.cheque" id="cheque" value="Cheque"/>Cheque</div>
						<div><form:checkbox path="possales.giftVoucher" id="voucher" value="Voucher"/>Voucher</div>
						<div><form:checkbox path="possales.cash" id="cash" value="Cash" />Cash</div>
						</td>
						</tr>
						
						
						<tr class="ReceiptModeRow card">
						<td>Card Amount</td>
						<td><form:input path="possales.cardAmount" id="card_Amount" value=""/></td>
						
						<td id="lvouchers">Bank</td>
                        <td>
						<form:select id="card_Bank" path="possales.cardBank" style="width:110px;">
	                    <form:option value="" label="Select"></form:option>
	                       <c:forEach var="ledger" items="${bank_name}">			
	                     <form:option value="${ledger.ledgerName}"></form:option>
                       	</c:forEach>
	                     </form:select>
	                    </td>
	                    
						<td>Card Details</td>
						<td><form:input path="possales.cardDetails" id="" value=""/></td>
						</tr>
						<tr class="ReceiptModeRow cheque">
						<td>Cheque Amount</td>
						<td><form:input path="possales.chequeAmount" id="cheque_Amount" value=""/></td>
						<td id="lvouchers">Bank</td>
                        <td>
						<form:select id="cheque_Bank" path="possales.chequeBank" style="width:110px;">
	                    <form:option value="" label="Select"></form:option>
	                       <c:forEach var="ledger" items="${bank_name}">			
	                     <form:option value="${ledger.ledgerName}"></form:option>
                       	</c:forEach>
	                     </form:select>
	                    </td>
						<td>Cheque Details</td>
						<td><form:input path="possales.chequeDetails" id="" value=""/></td>
						</tr>
						<tr class="ReceiptModeRow voucher">
						<td>Voucher Amount</td>
						<td><form:input path="possales.voucherAmount" id="voucher_Amount" value=""/></td>
						<td id="lvouchers">Bank</td>
                        <td>
						<form:select id="vouchers" path="possales.voucherList" style="width:110px;">
	                    <form:option value="" label="Select"></form:option>
	                       <c:forEach var="ledger" items="${assets_name}">			
	                     <form:option value="${ledger.ledgerName}"></form:option>
                       	</c:forEach>
	                     </form:select>
	                    </td>
						<td>Voucher Details</td>
						<td><form:input path="possales.voucherDetails" id="" value=""/></td>
						</tr>
						<!-- add for new requiremtn -->
						<tr class="ReceiptModeRow cash" >
						<td>Counter Cash </td>
						<td><form:input path="possales.counterCash" id="counter_cash" value=""/></td>
						<td>Cash </td>
						<td><form:input path="possales.cashAmount" id="cash_Amount" readonly="true" value=""/></td>
						</tr>
						
						
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr class="subm"><td bgcolor="#33FFFF"  colspan="10" align="center">
          <input type="submit" name="insert" value="Insert" id="insert" class="button_style"/>
          <input type="hidden"   value="print" id="printInvoice" class="button_style"/>
          <input type="submit" name="update" value="Update" id="update" class="button_style"/>
          <input type="submit" id="cancelSales" name="cancelSales" value="Cancel Sales" class="button_style" />
          <input type="submit" name="cancel" value= "Cancel" class="button_style">
         </td>
    </tr>
	</table>
	<input type="hidden" id="errorType" value="${errorName}">
	</form:form>
	</div>
	</div>
	</div>
</div>
<script type="text/javascript" src="script/js_CustomerCreation.js"></script>
<script type="text/javascript" src="script/jquery.autocomplete.js"></script>
<script type="text/javascript" src="script/NaNValidate.js"></script>
<script type="text/javascript" src="script/formposSales.js"></script>
</body>
</html>