 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>POS Purchase Form</title>
</head>
<body>
<div id="mask">
</div>   
    <div id="boxes">
     
    <!-- #customize your modal window here : CATEGORY POPUP -->   
    
     <div id="category" class="window dialog">
    <a href="#" class="close">X</a>
        <b class="Popup_heading">New POS Category Form</b>
         <div class="content">
         	
			<table id="formJournal" cellpadding="0" border="0" cellspacing="0" align="center" >
			<tr class="form_cat_head">
				<td colspan=4><B>Create Category</B></td>
			</tr>
			<tr>
				<td class="label" >Category Name*</td>
				<td><input type="text" name="categoryName" id="categoryName" class="categorytext noEnterSubmit" value="" /></td>
				<td class="label">Discount %</td>
				<td><input type="text" name="DiscountPercentage" id="DiscountPercentage" class="categorytext noEnterSubmit" value="0.00" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
			</tr>
			<tr>
				<td class="label">Vat %</td>
				<td><input type="text" name="vatPercentage" id="vatPercentage" class="categorytext noEnterSubmit" value="0.00" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
				<td class="label">Description</td>
				<td><input type="text" name="description" id="description" class="categorytext noEnterSubmit" value="" /></td>
			</tr>
			<tr>
				<td colspan="4" id ="POSPCatErrorMsg"></td>
			</tr>
			<tr>
				<td colspan=4 align="center">
				<input type="button" value="Save" name="saveCategory" id="saveCategory" class="button_style" />
				<input type="button" value="Cancel" class="button_style"></input>	
				</td>
			</tr>
			</table>
        </div> 
    </div> 
    
    <!-- #customize your modal window here : SUPPLIER POPUP -->
    
    <div id="newSupplier" class="window dialog">
    <a href="#" class="close">X</a>
        <b class="Popup_heading">New Supplier Form</b>
         <div class="content">
         	
			<table id="formJournal2" cellpadding="0" border="0" cellspacing="0" align="center" >
			<tr class="form_cat_head">
				<td colspan=4><B>Create Supplier</B></td>
			</tr>
			<tr>
				<td class="label">Supplier Name *</td>
				<td><input type="text" name="supplierName" id="supplierName" maxlength="50" class="categorytext noEnterSubmit" value="" /></td>
				<td class="label">Address</td>
				<td><input type="text" name="Address1" id="Address1" class="categorytext noEnterSubmit" value=""/></td>
			</tr>
			<tr>
				<td class="label">Opening Balance</td>
				<td><input type="text" name="openingBal" id="openingBal" class="categorytext noEnterSubmit" value="0.00" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
				<td class="label">Opening Type</td>
				<td><select id="openingType" class="noEnterSubmit">
					<option value="Credit" label="Credit" > Credit </option>
					<option value="Debit" label="Debit" > Debit </option> 
				</select></td>
			</tr>
			<tr>
				<td class="label">Phone Number</td>
				<td><input type="text" name="phoneNumber" id="phoneNumber" class="categorytext noEnterSubmit" value=""  onkeypress="return ValidateNumberPhone(this, event);"  /></td>
				<td class="label">Tin Number</td>
				<td><input type="text" name="tinNumber" id="tinNumber" class="categorytext noEnterSubmit" /></td>
			</tr>
			<tr>
				<td colspan="4" id ="POSPSuppErrorMsg"></td>
			</tr>
			<tr>
				<td colspan=4 align="center">
				<input type="button" value="Save" name="saveSupplier" id="saveSupplier" class="button_style" />
				<input type="button" value="Cancel" class="button_style"></input>	
				</td>
			</tr>
			</table>
        </div> 
    </div>   
    
    <!-- #customize your modal window here : ITEM POPUP -->
    
    <div id="newItem" class="window dialog">
    <a href="#" class="close">X</a>
        <b class="Popup_heading">New Item Form</b>
         <div class="content">
         	
			<table id="formJournal3" cellpadding="0" border="0" cellspacing="0" align="center" >
			<tr class="form_cat_head">
				<td colspan=4><B>Create Item</B></td>
			</tr>
			<tr>
				<td class="label">Item Name *</td>
				<td><input type="text" name="popIName" id="popIName" class="categorytext noEnterSubmit" value="" /></td>
				<td class="label">Category Name *</td>
				<td><input type="text" name="itemCategoryName" id="itemCategoryName" class="categorytext noEnterSubmit" value="" /></td>
			</tr>
			<tr>
				<td class="label">Discount %</td>
				<td><input type="text" name="itemDiscP" id="itemDiscP" class="categorytext noEnterSubmit" value="0.00" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
				<td class="label">Vat %</td>
				<td><input type="text" name="itemVatP" id="itemVatP" class="categorytext noEnterSubmit" value="0.00" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
			</tr>
			<tr>
				<td class="label">Company Name</td>
				<td><input type="text" name="itemCompanyName" id="itemCompanyName" class="categorytext noEnterSubmit" value="" /></td>	
				<td></td>
				<td></td>			
			</tr>
			<tr>
				<td colspan="4" id="POSPItemErrorMsg"></td>
			</tr>
			<tr>
				<td colspan=4 align="center">
				<input type="button" value="Save" name="saveItem" id="saveItem" class="button_style" />
				<input type="button" value= "Cancel" class="button_style"></input>	
				</td>
			</tr>
			</table>
        </div> 
    </div>       
 </div>	

	 <!-- Do not remove div#mask, because you'll need it to fill the whole screen --> 
  
<div id="col-form1" >
	<div class="boxed" >
	<div class="title_1" style="text-align:left;" >POS Purchase</div>
	<div class="content" style=" height:auto; width:948px; overflow-x:auto ; overflow-y: auto; padding-bottom:10px;">
	
<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
<a class="create_or_list" href="formPOSPurchaseReturn.htm" style="float:left;">Purchase Return</a> 
<div class="tab_form">

	<c:url var="addUrl" value="formPOSpurchase.htm" />
	<form:form name="form1" id="form1" commandName="pospCommand" method="POST" action="${addUrl}" class="formStyle">		
			<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span> 
	</div>
	<div class="summary">POS Purchase Form</div>
	<table id="formPOSpurchase" cellpadding="0" cellspacing="0" align="center" class="POSpurchase">
		<tr class="form_cat_head">
				<td colspan=6>Purchase Information</td>
		</tr>
		<tr>	
			<td class="label">Purchase Type</td>
			<td><form:input path="posp.purchaseType" type=" text" readonly="true" id="purchaseType0" name="0" value="POS Purchase"/></td>
			<td class="label" >Purchase Invoice No *</td>
			<td><form:input path="posp.invoiceNO" type="text" id="invoiceNo0" name="0" class="noEnterSubmit" /></td>
			<td class="label">Date * </td>
			<td><form:input path="posp.pdate" id="POSPdate" name="0"  class="noEnterSubmit" /></td>
		</tr>
		<tr>
			<td class="label">Supplier Name * <a name="modal" id="suppPopup" class ="add_new" href="#newSupplier" title="Add New Supplier">Add</a></td>
			<td><form:select path="posp.supplierName" style="width:150px;" id="supplierName0" class="noEnterSubmit" >
					<form:option value="" label="Select">Select</form:option>
					<c:forEach var="ledger" items="${SupplierNameList}">
						 <form:option value="${ledger.ledgerName}">${ledger.ledgerName}</form:option>
					</c:forEach>
				</form:select>
			</td>
			
			<!-- Walk-in not needed in Purchase by M.D -->
			<!-- <td class="label possupplier">Name*</td>
			<td class="possupplier"><form:input path="posp.walkinName" type="text" id="walkinName0" name="0"  class="noEnterSubmit" /></td>
			<td class="label possupplier">Address*</td>
			<td class="possupplier"><form:input path="posp.walkinAddress" type="text" id="walkinAddress0" name="0"  class="noEnterSubmit" /></td> -->
					
				
			<td class="label possupplier1">Fixed Discount %</td>
			<td class="possupplier1"><form:input path="posp.fixedPer" type="text" id="fixedPer0" class="cal_dep noEnterSubmit" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
			<td class="label possupplier1" colspan="2" id="delTagCheckMSg"></td>
		
			<td class="label possupplier" ></td>
			<td class="possupplier"></td>
			<td class="label possupplier"></td>
			<td class="possupplier"></td>	
			<td style="display:none"><select name="tempCategoryName" id="tempCategoryName">
					<c:forEach var="category" items="${categoryList}">
						 <option value="${category}">${category}</option>
					</c:forEach>
					</select></td>			
		</tr>
		
	</table>
	<table cellpadding="0" cellspacing="0" align="center" class="POSpurchaseItems">
	<tr class="RowHead">
		<td>Id</td>
		<td></td>
		<td>Category * <a name="modal" id="catPopup" class="add_new" href="#category" title="Add new Category">Add</a></td>
		<td>Item Name * <a name="modal" id="itemPopup" class="add_new" href="#newItem" title="Add new Item">Add</a></td>
		<td>Set(Qty)</td>
		<td>Pieces Per Set</td>
		<td>Cost Rate * </td>
		<td>Margin %</td>
		<td>Sales Rate</td>
		<td>Disc %</td>
		<td>Tax %</td>
		<td>Amount</td>		
	</tr>
	
	<c:set var="countVal" value="0" scope="page"/>
	<c:forEach var="plistVal" items="${popPurchaseList}" varStatus="popPurchaseFormList" >
		<c:set var="countVal" value="${popPurchaseFormList.count}" scope="page"/>
	</c:forEach>

	<c:if test="${countVal eq 0}" >
			<tr class="staticRowP">
				<td>0</td>
				<td><input type="button" name="delete" class="delP" id="rowDelButton0" value="" style="width:20px;" title="0"/></td>
				<td><form:select path="listpospurchase[0].categoryName" class="cname noEnterSubmit" maxlength="30" type="text" id="categoryName0" title="0">
					<form:option value="">Select</form:option>
					<c:forEach var="categoryName" items="${categoryList}">
						 <form:option value="${categoryName}">${categoryName}</form:option>
					</c:forEach>
					</form:select>
				</td>
				<td><form:select path="listpospurchase[0].itemName" id="itemName0" title="0" class="itemname noEnterSubmit">
						<c:forEach var="positem" items="${itemNameList}">
							 <form:option value="${positem}">${positem}</form:option>
						</c:forEach>
					</form:select>
				</td>
				<td><form:input path="listpospurchase[0].qtySet" type="text" id="qtySet0" class="qty cal_dep qty_pcs_tax noEnterSubmit" title="0" onkeyup="extractNumber(this,0,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/> </td>
				<td><form:input path="listpospurchase[0].piecesPerSet" type="text" id="piecesPerSet0" class="piecesPerSet cal_dep qty_pcs_tax noEnterSubmit" title="0" onkeyup="extractNumber(this,0,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/> </td>
				<td><form:input path="listpospurchase[0].costRate" type="text" id="costRate0" title="0" class="costRate posrate cal_dep noEnterSubmit" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
				<td><form:input path="listpospurchase[0].marginPercentage" type="text" id="marginP0" class="marginP cal_dep noEnterSubmit" title="0" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
				<td><form:input path="listpospurchase[0].salesRate" type="text" id="salesRate0" title="0" class="salesRate posrate cal_dep noEnterSubmit" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
				<td><form:input path="listpospurchase[0].discPer" type="text" id="discountPer0" class="discPer cal_dep qty_pcs_tax noEnterSubmit" title="0" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
				<td><form:input path="listpospurchase[0].taxPercantage" type="text" id="taxP0" class="taxP cal_dep qty_pcs_tax noEnterSubmit" title="0" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
				<td><form:input path="listpospurchase[0].purchaseAmt" type="text" readonly="true" id="purchaseAmt0" title="0" class="purchaseAmt posrate cal_dep noEnterSubmit" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
				<td style="display:none">
					<form:input path="listpospurchase[0].rowStatus" type="hidden" class="rowStatus" title="0"/> 
					<form:input path="listpospurchase[0].deleted" id="deleted0" title="0" class="isDeleted" />
					<form:input path="listpospurchase[0].purchaseItemID" type="hidden" id="purchaseItemID0" title="0"/>
					<form:input path="listpospurchase[0].itemStatus" type="hidden" id="itemStatus0" title="0" class="itemStatus"/>
					<form:input path="listpospurchase[0].transactionNo" type="hidden" title="0"/></td>
			</tr>
	</c:if>
	
	<!--  TO show Data on Insert Error and Update Error -->
	<c:set var="index" value="${0}" scope="page"/>
	<c:forEach var="plist3" items="${popPurchaseList}">	
		<tr class="staticRowP">
			<td>${index}</td>
			<td><input type="button" name="delete" class="delP" id="rowDelButton${index}" value="" style="width:20px;" title="${index}"/></td>
			<td><form:select path="listpospurchase[${index}].categoryName" class="itemname noEnterSubmit" id="categoryName${index}" title="${index}">
					<form:option value="">Select</form:option>
					<c:forEach var="category" items="${categoryList}">
						 <form:option value="${category}" label="${category}">${category}</form:option>
					</c:forEach>
				</form:select>
			</td>
			<td><form:select path="listpospurchase[${index}].itemName" value="${plist3.itemName}" class="itemname noEnterSubmit" id="itemName${index}" title="${index}" >
					<c:forEach var="positem" items="${itemNameList}">
						<form:option value="${positem}">${positem}</form:option>
					</c:forEach>
				</form:select>
			</td>
			<td><form:input path="listpospurchase[${index}].qtySet" value="${plist3.qtySet}" type="text" id="qtySet${index}" title="${index}" class="qty cal_dep qty_pcs_tax noEnterSubmit" onkeyup="extractNumber(this,0,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="listpospurchase[${index}].piecesPerSet" value="${plist3.piecesPerSet}" type="text" id="piecesPerSet${index}" title="${index}" class="piecesPerSet cal_dep qty_pcs_tax noEnterSubmit" onkeyup="extractNumber(this,0,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/> </td>
			<td><form:input path="listpospurchase[${index}].costRate" value="${plist3.costRate}" type="text" id="costRate${index}" title="${index}" class="posrate  cal_dep noEnterSubmit" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="listpospurchase[${index}].marginPercentage" value="${plist3.marginPercentage}" type="text" id="marginP${index}" title="${index}" class="marginP cal_dep noEnterSubmit" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="listpospurchase[${index}].salesRate" value="${plist3.salesRate}" type="text" id="salesRate${index}" title="${index}" class="salesRate posrate cal_dep noEnterSubmit" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="listpospurchase[${index}].discPer" value="${plist3.discPer}" type="text" id="discountPer${index}" title="${index}" class="discPer cal_dep qty_pcs_tax noEnterSubmit" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
			<td><form:input path="listpospurchase[${index}].taxPercantage" value="${plist3.taxPercantage}" type="text" id="taxP${index}" title="${index}" class="taxP cal_dep qty_pcs_tax noEnterSubmit" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="listpospurchase[${index}].purchaseAmt" value="${plist3.purchaseAmt}" type="text" readonly="true" id="purchaseAmt${index}" title="${index}" class="purchaseAmt posrate cal_dep noEnterSubmit" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
			<td style="display:none" >
				<form:input path="listpospurchase[${index}].rowStatus" value="${plist3.rowStatus}" type="hidden" class="rowStatus" title="${index}"/>			
				<form:input path="listpospurchase[${index}].deleted" id="deleted${index}" value="${plist3.deleted}" title="${index}" class="isDeleted" />
				<form:input path="listpospurchase[${index}].purchaseItemID" value="${plist3.purchaseItemID}" type="hidden" id="purchaseItemID${index}" title="${index}" />
				<form:input path="listpospurchase[${index}].itemStatus" value="${plist3.itemStatus}" type="hidden" id="itemStatus${index}" title="${index}" class="itemStatus"/>
				<form:input path="listpospurchase[${index}].transactionNo" type="hidden" value="${plist3.transactionNo}" title="${index}"/></td>														
		</tr>
		<c:set var="index" value="${index + 1}" scope="page"/>
	</c:forEach>		
		
	<!--  TO show Data on Update Mode -->
	 	
  	<c:set var="ind" value="${1}" scope="page"/>
  	
	<c:forEach var="plist" items="${purchaseItemsList}">
		<tr class="staticRowP">
			<td>${ind}</td>
			<td><input type="button" name="delete" id="rowDelButton${ind}" class="delP" value="" style="width:20px;" title="${ind}"/></td>
			<td><form:select path="listpospurchase[${ind}].categoryName" disabled="true" class="cname noEnterSubmit" id="categoryName${ind}" title="${ind}">
				<form:option value="">Select</form:option>
					<c:forEach var="category" items="${categoryList}">
						<form:option value="${category}">${category}</form:option>
					</c:forEach>
				</form:select>
			</td>
			<td><form:select path="listpospurchase[${ind}].itemName" disabled="true" class="itemname noEnterSubmit"id="itemName${ind}" title="${ind}" >
					<c:forEach var="positem" items="${itemNameList}">
						<form:option value="${positem}">${positem}</form:option>
					</c:forEach>
				</form:select>
			</td>
			<td><form:input path="listpospurchase[${ind}].qtySet" type="text" id="qtySet${ind}" title="${ind}" class="qty cal_dep qty_pcs_tax noEnterSubmit" onkeyup="extractNumber(this,0,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="listpospurchase[${ind}].piecesPerSet" type="text" id="piecesPerSet${ind}" title="${ind}" class="piecesPerSet cal_dep qty_pcs_tax noEnterSubmit" onkeyup="extractNumber(this,0,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/> </td>
			<td><form:input path="listpospurchase[${ind}].costRate" type="text" id="costRate${ind}" title="${ind}" class="posrate  cal_dep noEnterSubmit" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="listpospurchase[${ind}].marginPercentage" type="text" id="marginP${ind}" title="${ind}" class="marginP cal_dep noEnterSubmit" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="listpospurchase[${ind}].salesRate" type="text" id="salesRate${ind}" title="${ind}" class="salesRate posrate cal_dep noEnterSubmit" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="listpospurchase[${ind}].discPer" type="text" id="discountPer${ind}" title="${ind}" class="discPer cal_dep qty_pcs_tax noEnterSubmit" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
			<td><form:input path="listpospurchase[${ind}].taxPercantage" type="text" id="taxP${ind}" title="${ind}" class="taxP cal_dep qty_pcs_tax noEnterSubmit"  onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><form:input path="listpospurchase[${ind}].purchaseAmt" type="text" id="purchaseAmt${ind}" readonly="true" title="${ind}" class="purchaseAmt posrate cal_dep noEnterSubmit" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
			<td style="display:none">
				<form:input path="listpospurchase[${ind}].rowStatus" type="hidden" class="rowStatus" title="${ind}"/> 				
				<form:input path="listpospurchase[${ind}].deleted" id="deleted${ind}" title="${ind}" class="isDeleted" />						
				<form:input path="listpospurchase[${ind}].purchaseItemID" type="hidden" id="purchaseItemID${ind}" title="${ind}" />
				<form:input path="listpospurchase[${ind}].itemStatus" type="hidden" id="itemStatus${ind}" title="${ind}" class="itemStatus"/>
				<form:input path="listpospurchase[${ind}].transactionNo" type="hidden" title="${ind}"/></td>				
		</tr>
		<c:set var="ind" value="${ind + 1}" scope="page"/>
	</c:forEach>
		

	<tr class="POSpurchaseTotal">
		<td colspan="11"><input type="button" value="Add Item" id="addItemP"></td>
		<td>
	</tr>
	<tr class="POSpurchaseTotal">
		<td colspan="11">Sub Total</td>
		<td><form:input path="posp.subTotal" type="text" id="subTotal0" name="0" readonly="true" class="cal_dep noEnterSubmit"   /> </td>
	</tr>
	 <tr class="POSpurchaseTotal">
		<td colspan="11">Total Discount</td>
		<td><form:input path="posp.totalDiscount" type="text" id="totalDiscount0" name="0" readonly="true" class="cal_dep noEnterSubmit"   /> </td>
	</tr>
	<tr class="POSpurchaseTotal">
		<td colspan="11">Total Tax</td>
		<td><form:input path="posp.totalTax" type="text" id="totalTax0" name="0" readonly="true" class="cal_dep noEnterSubmit"   /> </td> 
	</tr>
	<tr class="POSpurchaseTotal">
		<td colspan="11">Total Value</td>
		<td><form:input path="posp.totalValue" type="text" id="totalValue0" name="0" readonly="true" class="cal_dep noEnterSubmit"  /> </td>
	</tr>
	<tr class="POSpurchaseTotal">
	<td colspan="6">Payment Type</td>
		<td colspan="2"><form:select path="posp.paymentType" id="paymentType0" name="0" Style="width:110px;">
				<form:option label="Credit" value="Credit">Credit</form:option>
				<form:option label="Cash" value="Cash">Cash</form:option>
			</form:select>
		</td>
		<td colspan="3">Round Off</td>
		<td><form:input path="posp.roundOff" type="text" id="roundOff0" name="0" class="cal_dep noEnterSubmit" readonly="true"  /> </td>
	</tr>
	<tr class="POSpurchaseTotal">
		
		<td colspan="7" class="GrandTotalLabel">Grand Total</td>
		<td colspan="6" class="GrandTotal"><form:input path="posp.grandAmount" type="text" readonly="true" id="grandAmt0" name="0" class="cal_dep noEnterSubmit"   /> </td>
	</tr>
	<tr class="subm"><td bgcolor="#33FFFF"  colspan="12" align="center">
          <input type="submit" name="insert" value="Insert" id="insert" class="button_style"/></input>
          <input type="submit" name="update" value="Update" id="update" class="button_style"/></input>
          <input type="submit" name="cancel" value= "Cancel" class="button_style"></input>
          
         <span class="SpanHide"></span></td>
    </tr>
	</table>
	<form:input type="hidden" path="posp.purchaseId" />
	<input type="hidden" id="errorType" value="${errorName}">
	</form:form>
	</div>
	</div>
	</div>
	</div>
<script type="text/javascript" src="script/jquery.autocomplete.js"></script>
<script type="text/javascript" src="script/NaNValidate.js"></script>
<script type="text/javascript" src="script/ledger_Name_Auto.js"></script>	
<script type="text/javascript" src="script/js_POSPurchase.js"></script>
<script type="text/javascript" src="script/formValidations.js"></script>
</body>
</html>