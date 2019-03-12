 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>POS Purchase Return</title>
</head>
<body>
<div id="col-form1" >
	<div class="boxed" >
	<div class="title_1" style="text-align:left;" >POS Purchase Return</div>
	<div class="content" style="height:auto; width:948px; overflow-x:auto ; overflow-y: auto; padding-bottom:10px;">
	
<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>

<div class="tab_form">
	<c:url var="addUrl" value="formPOSPurchaseReturn.htm" />
	<form:form name="form1" id="form1" modelAttribute="pospCommand" method="POST" action="${addUrl}" class="formStyle">		
			<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>
		
	</div>
	<table id="formPOSpurchase" cellpadding="0" cellspacing="0" align="center" class="POSpurchase">
		<tr class="form_cat_head">
				<td colspan=6>Purchase Return Information </td>
		</tr>
		<tr>
				<td class="label">Purchase Type</td>
				<td><form:input path="posp.purchaseType" id="purchaseTypeReturn" style="width:150px;" readonly="true" name="0" value="POS Purchase Return"/>
					
				</td>
				
				<td class="label">Invoice No</td>
				<td><form:input path="posp.invoiceNO" type="text"/></td>
				<td class="label">Date</td>
				<td><form:input path="posp.pdate" id="date" /></td>
				
		</tr>
		
		<tr>
				<td class="label">Supplier Name</td>
				
				<td>
				<form:select path="posp.supplierName" id="supplierName0">
				<form:option value="" label="Select"></form:option>
				<c:forEach var="ledger" items="${suppliername}">
				<form:option  value="${ledger.ledgerName}">
				</form:option>
				</c:forEach>
				</form:select>
									
				</td>
				<td class="label possupplier1"></td>
				<td class="possupplier1"></td>
				<td class="label possupplier1"></td>
				<td class="possupplier1"></td>
		</tr>
		
	</table>
	<table cellpadding="0" cellspacing="0" align="center" class="POSpurchaseItems">
	<tr class="RowHead">
		<td></td>
		<td>Item Code</td>
		<td>Category Name</td>
		<td>Item Name</td>
		<td>Set(Qty)</td>
		<td>Pieces/Set</td>
		<td>Cost Rate</td>
		<td>Tax %</td>
		<td>Amount</td>		
	</tr>
	<c:set var="countVal" value="0" scope="page"/>
	<c:forEach var="plistVal" items="${popPurchaseList}" varStatus="popPurchaseFormList" >
		<c:set var="countVal" value="${popPurchaseFormList.count}" scope="page"/>
	</c:forEach>
	<c:if test="${countVal eq 0}" >
	<tr class="staticRowPR">
		<td><input type="button" name="delete" class="delP" id="rowDelButton" value="" style="width:20px;" title="0"/></td>
		<td><form:input path="listpospurchase[0].barcode" class="icode" type="text" id="pritcode0" title="0" onkeydown="return (event.keyCode!=13);" /></td>
		<td><form:input path="listpospurchase[0].categoryName" readonly="true" class="cname" type="text" id="categoryName0" title="0" onkeydown="return (event.keyCode!=13);" /></td>
		<td><form:input path="listpospurchase[0].itemName" readonly="true" type="text" id="itemName0" title="0" onkeydown="return (event.keyCode!=13);" /></td>
		<td><form:input path="listpospurchase[0].qtySet" readonly="true" type="text" id="qtySet0" class="qty cal_dep qty_pcs_tax" title="0" onkeydown="return (event.keyCode!=13);" onkeypress="return ValidateNumberKeyPress(this, event);"/> </td>
		<td><form:input path="listpospurchase[0].piecesPerSet" readonly="true" type="text" id="piecesPerSet0" class="piecesPerSet cal_dep qty_pcs_tax" title="0" onkeydown="return (event.keyCode!=13);" onkeypress="return ValidateNumberKeyPress(this, event);"/> </td>
		<td><form:input path="listpospurchase[0].costRate" type="text" id="costRate0" title="0" class="costRate posrate cal_dep" onkeydown="return (event.keyCode!=13);" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);"/> </td>
		<td><form:input path="listpospurchase[0].taxPercantage" type="text" id="taxP0" class="taxP cal_dep qty_pcs_tax" title="0" onkeydown="return (event.keyCode!=13);" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);"/> </td>
		<td><form:input path="listpospurchase[0].purchaseAmt" type="text" id="purchaseAmt0" title="0" class="purchaseAmt posrate cal_dep" onkeydown="return (event.keyCode!=13);" onkeypress="return ValidateNumberKeyPress(this, event);" readonly="true"/> </td>
		<td style="display:none">	<form:hidden path="listpospurchase[0].rowStatus" type="text" class="rowStatus" title="0"/>
		<form:input path="listpospurchase[0].deleted" id="deleted0" title="0" class="isDeleted" />
		<form:input type="hidden" path="listpospurchase[0].purchaseItemID" />	
		</td>
	</tr>
	</c:if>
	
<!--  TO show Data on Insert Error and Update Error -->
	<c:set var="index" value="${0}" scope="page"/>
	<c:forEach var="prlist3" items="${popPurchaseList}">	
		<tr class="staticRowPR">
			<td><input type="button" name="delete" class="delP" value="" style="width:20px;" title="${index}"/></td>
			<td><input name="listpospurchase[${index}].barcode" value="${prlist3.barcode}" class="cname noEnterSubmit" maxlength="30" type="text"  id="pritcode${index}" title="${index}"/></td>
			<td><input name="listpospurchase[${index}].categoryName" readonly="readonly" value="${prlist3.categoryName}" class="cname noEnterSubmit" maxlength="30" type="text"  id="categoryName${index}" title="${index}"/></td>
			<td><input name="listpospurchase[${index}].itemName" readonly="readonly" value="${prlist3.itemName}" type="text" class="noEnterSubmit" id="itemName${index}" title="${index}" name="${index}" /></td>
			<td><input name="listpospurchase[${index}].qtySet" readonly="readonly" value="${prlist3.qtySet}" type="text" id="qtySet${index}" title="${index}" class="qty cal_dep noEnterSubmit qty_pcs_tax"onkeyup="extractNumber(this,0,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><input name="listpospurchase[${index}].piecesPerSet" readonly="readonly" value="${prlist3.piecesPerSet}" type="text" id="piecesPerSet${index}" title="${index}" class="piecesPerSet cal_dep noEnterSubmit" onkeyup="extractNumber(this,0,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/> </td>
			<td><input name="listpospurchase[${index}].costRate" value="${prlist3.costRate}" type="text" id="costRate${index}" title="${index}" class="posrate  cal_dep noEnterSubmit" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><input name="listpospurchase[${index}].taxPercantage" value="${prlist3.taxPercantage}" type="text" id="taxP${index}" title="${index}" class="taxP cal_dep noEnterSubmit" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
			<td><input name="listpospurchase[${index}].purchaseAmt" readonly="readonly" value="${prlist3.purchaseAmt}" type="text" id="purchaseAmt${index}" title="${index}" class="purchaseAmt posrate cal_dep noEnterSubmit" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" />				
			<td style="display:none"><input name="listpospurchase[${index}].rowStatus" value="${prlist3.rowStatus}" type="hidden" class="rowStatus" title="${index}"/>
			<input name="listpospurchase[${index}].deleted" id="deleted${index}" value="${prlist3.deleted}" title="${index}" class="isDeleted" type="hidden"/>		
			<input name="listpospurchase[${index}].purchaseItemID" value="${prlist3.purchaseItemID}" type="hidden" id="purchaseItemID${index}" title="${index}" />
			</td>
			</tr>
		<c:set var="index" value="${index + 1}" scope="page"/>
	</c:forEach>		
	
	<!--  TO show Data on Update Mode -->
	<c:set var="ind" value="${1}" scope="page"/>
	<c:forEach var="plist" items="${purchaseItemsList}">	
	<tr class="staticRowPR">
		<td><input type="button" name="delete" class="delP" value="" style="width:20px;"/></td>
		<td><form:input path="listpospurchase[${ind}].barcode" class="icode" type="text" id="pritcode${ind}" title="${ind}" onkeydown="return (event.keyCode!=13);" /></td>
		<td><form:input path="listpospurchase[${ind}].categoryName" readonly="true" class="cname" type="text" id="categoryName${ind}" title="${ind}" onkeydown="return (event.keyCode!=13);" /></td>
		<td><form:input path="listpospurchase[${ind}].itemName" readonly="true" id="itemName${ind}" name="${ind}" onkeydown="return (event.keyCode!=13);"  /></td>
		<td><form:input path="listpospurchase[${ind}].qtySet" readonly="true" type="text" id="qtySet${ind}" class="qty cal_dep qty_pcs_tax" title="${ind}" onkeydown="return (event.keyCode!=13);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
		<td><form:input path="listpospurchase[${ind}].piecesPerSet" readonly="true" type="text" id="piecesPerSet${ind}" class="piecesPerSet cal_dep qty_pcs_tax" title="${ind}" onkeydown="return (event.keyCode!=13);" onkeypress="return ValidateNumberKeyPress(this, event);"/> </td>
		<td><form:input path="listpospurchase[${ind}].costRate" type="text" id="costRate${ind}" title="${ind}" class="posrate  cal_dep" onkeydown="return (event.keyCode!=13);" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);"/> </td>
		<td><form:input path="listpospurchase[${ind}].taxPercantage" type="text" id="taxP${ind}" title="${ind}" class="taxP cal_dep qty_pcs_tax" onkeydown="return (event.keyCode!=13);" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);"/> </td>
		<td><form:input path="listpospurchase[${ind}].purchaseAmt" type="text" id="purchaseAmt${ind}" title="${ind}" class="purchaseAmt posrate cal_dep" onkeydown="return (event.keyCode!=13);" onkeypress="return ValidateNumberKeyPress(this, event);" readonly="true"/> </td>				
		<form:input path="listpospurchase[${ind}].purchaseItemID" type="hidden" title="${ind}"/>				
		<td style="display:none"><form:hidden path="listpospurchase[${ind}].rowStatus"  type="text" class="rowStatus" title="${ind}"/>
		<form:input path="listpospurchase[${ind}].deleted" id="deleted${ind}" title="${ind}" class="isDeleted" />						
	</td>
	</tr>
	<c:set var="ind" value="${ind + 1}" scope="page"/>
	</c:forEach>
	<tr class="POSpurchaseTotal">
		<td colspan="7"><input type="button" value="Add Item" id="addItemPR"></td>
		<td></td>
		<td></td>
	</tr>
	<tr class="POSpurchaseTotal">
		<td colspan="7"></td>
		<td>Sub Total</td>
		<td><form:input path="posp.subTotal" type="text" id="subTotal0" name="0" readonly="true" class="cal_dep" onkeydown="return (event.keyCode!=13);"  /> </td>
	</tr>
	<tr class="POSpurchaseTotal">
		<td colspan="7"></td>
		<td>Total Tax</td>
		<td><form:input path="posp.totalTax" type="text" id="totalTax0" name="0" readonly="true" class="cal_dep" onkeydown="return (event.keyCode!=13);"  /> </td> 
	</tr>
	<tr class="POSpurchaseTotal">
		<td colspan="7"></td>
		<td>Total Value</td>
		<td><form:input path="posp.totalValue" type="text" id="totalValue0" name="0" readonly="true" class="cal_dep" onkeydown="return (event.keyCode!=13);" /> </td>
	</tr>
	<tr class="POSpurchaseTotal">
		<td colspan="5"></td>
		<td>Reason</td>
		<td><form:select path="posp.reason" id="paymentType0" name="0" class="reasontype" style="width:80px;">
				<form:option label="General" value="General"></form:option>
				<form:option label="Date Expiry" value="Date Expiry"></form:option>
				<form:option label="Rate Difference" value="Rate Difference"></form:option>
			</form:select>
		</td>
		<td>Round Off</td>
		<td><form:input path="posp.roundOff" type="text" id="roundOff0" name="0" class="cal_dep" readonly="true" onkeydown="return (event.keyCode!=13);" /> </td>
	</tr>
	<tr class="POSpurchaseTotal">
		<td colspan="5"></td>
		<td>Payment Type</td>
		
		<td><form:select path="posp.paymentType" id="paymentType0" name="0" class="reasontype" style="width:80px;">
				<form:option label="Cash" value="Cash">Cash</form:option>
				<form:option label="Credit" value="Credit">Credit</form:option>
			</form:select>
		</td>
		<td>Grand Total</td>
		<td><form:input path="posp.grandAmount" type="text" readonly="true" id="grandAmt0" name="0" class="cal_dep" onkeydown="return (event.keyCode!=13);"  /> </td>
	</tr>
	<tr class="subm"><td bgcolor="#33FFFF"  colspan="11" align="center">
          <input type="submit" name="insert" value="Insert" id="insert" class="button_style"/></input>
          <input type="submit" name="update" value="Update" id="update" class="button_style"/></input>
          <input type="submit" name="cancel" value= "Cancel" class="button_style"></input>
         </td>
    </tr>
	</table>
	<form:input type="hidden" path="posp.purchaseId" />
	<input type="hidden" id="errorType" value="${errorName}">
	</form:form>
	</div>
	</div>
	</div>
	</div>
<script type="text/javascript" src="script/NaNValidate.js"></script>
<script type="text/javascript" src="script/pospurchasereturn.js"></script>
</body>
</html>