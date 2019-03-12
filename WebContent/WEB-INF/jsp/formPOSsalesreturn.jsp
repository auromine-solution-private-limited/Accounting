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

<div id="col-form1" >
	<div class="boxed" >
	<div class="title_1" style="text-align:left;" >POS Sales Return Form</div>
	<div class="content" style=" height:auto; width:948px; overflow-x:auto ; overflow-y: auto; padding-bottom:10px;">
	
<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
<a class="create_or_list" href="formPOSsales.htm" style="float:left;">New Sales</a> 
<div class="tab_form">

	<c:url var="addUrl" value="formPOSsalesreturn.htm" />
	<form:form name="form1" id="form1" commandName="POSsales" method="POST" action="${addUrl}" class="formStyle">
			<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>
		
	</div>
	<table id="formPOSpurchase" cellpadding="0" cellspacing="0" align="center" class="POSpurchase">
		<tr class="form_cat_head">
				<td colspan=6>Sales	Return Information</td>
		</tr>
		<tr>
				<td class="label">Sales Type</td>
				<td><form:input path="possales.salesType"  value="POS Sales Return" id="possalestype" readonly="true"/></td>
				<td class="label">Invoice No</td>
				<td><form:input path="possales.billNo" type="text"id="posinvoiceno" readonly="true"/></td>
				
				<td class="label">Salesman Name</td>
				<td><input name="possales.salesmanName" id="possalesman" readonly="readonly" value="<%=session.getAttribute("username")%>" size="20"/></td>
				
		</tr>
		<tr>
				<td class="label">Date</td>
				<td><form:input path="possales.salesdate"id="date" /></td>
				<td class="label">Customer Name</td>
				<td><form:input path="possales.customerName" readonly="true"id="poscustomer" style="width:150px;"/>					
				</td>
				<td class="label possupplier">Name*</td>
				<td class="possupplier"><form:input path="possales.walkinCustomer"/></td>
				<td class="label possupplier">Address*</td>
				<td class="possupplier"><form:input path="possales.walkinAddress"/></td>
				
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
		<td>Discount</td>
		<td>Tax %</td>
		<td>Amount</td>
		
	</tr>
	
	<c:set var="countVal" value="0" scope="page"/>
	<c:forEach var="poslistVal" items="${possalesList}" varStatus="popSalesFormList" >
		<c:set var="countVal" value="${popSalesFormList.count}" scope="page"/>
	</c:forEach>
	<c:if test="${countVal eq 0}" >
			<tr class="staticRow">
				<td></td>
		<td><form:input path="listpossalesItem[0].posItemCode" class="icode noEnterSubmit" id="dIcode0" title="0" readonly="true"/> </td>
		<td><form:input path="listpossalesItem[0].categoryName"  class="category noEnterSubmit" id="categoryname0" readonly="true" title="0" /> </td>
		<td><form:input path="listpossalesItem[0].itemName"  id="dIname0" class="category noEnterSubmit" readonly="true" title="0" /> </td>
		<td><form:input path="listpossalesItem[0].quantity" class="QtyPcs noEnterSubmit"  id="qty0" readonly="true" title="0"/> </td>
		<td><form:input path="listpossalesItem[0].totalPieces" class="QtyPcs noEnterSubmit"  id="pcs0" readonly="true" title="0"/></td>
		<td><form:input path="listpossalesItem[0].salesRate" class="posrate noEnterSubmit" id="salesrates0"  title="0" onkeydown="return (event.keyCode!=13);" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/> </td>
		<td><form:input path="listpossalesItem[0].discountAmount" id="discounts0" title="0"class="categorytext noEnterSubmit" onkeydown="return (event.keyCode!=13);" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/> </td>
		<td><form:input path="listpossalesItem[0].salesTax" id="tax0" readonly="true" title="0"class="categorytext noEnterSubmit"/> </td>
		<td><form:input path="listpossalesItem[0].salesAmount" class="posrate noEnterSubmit" readonly="true"id="amt0" title="0"/> </td>
		<form:input path="listpossalesItem[0].POSdeleted" type="hidden" id="posdeleted0"readonly="true" class="isDeleted"/>
		<form:input type="hidden" path="listpossalesItem[0].salesItemID" readonly="true" />		
			</tr>
	</c:if>
	
	<!--  TO show Data on Insert Error and Update Error -->
	<c:set var="index" value="${0}" scope="page"/>
	<c:forEach var="plist3" items="${possalesList}">	
		<tr class="staticRow">
			<td></td>
		<td><form:input path="listpossalesItem[${index}].posItemCode" value="${plist3.posItemCode}" class="icode noEnterSubmit" readonly="true" id="dIcode${index}" title="${index}"/> </td>
		<td><form:input path="listpossalesItem[${index}].categoryName" value="${ plist3.categoryName}"  class="category noEnterSubmit" id="categoryname${index}" readonly="true" title="${index}"/> </td>
		<td><form:input path="listpossalesItem[${index}].itemName" value="${plist3.itemName}" id="dIname${index}" class="category noEnterSubmit" readonly="true" title="${index}"/> </td>
		<td><form:input path="listpossalesItem[${index}].quantity" value="${plist3.quantity }" class="QtyPcs noEnterSubmit"  id="qty${index}" readonly="true" title="${index}"/></td>
		<td><form:input path="listpossalesItem[${index}].totalPieces" value="${plist3.totalPieces}" class="QtyPcs noEnterSubmit"  id="pcs${index}" readonly="true" title="${index}"/> </td>
		<td><form:input path="listpossalesItem[${index}].salesRate" value="${ plist3.salesRate}" class="posrate noEnterSubmit" id="salesrates${index}"  title="${index}" onkeydown="return (event.keyCode!=13);" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/> </td>
		<td><form:input path="listpossalesItem[${index}].discountAmount" value="${plist3.discountAmount}" id="discounts${index}" class="noEnterSubmit"  title="${index}"  onkeydown="return (event.keyCode!=13);" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
		<td><form:input path="listpossalesItem[${index}].salesTax" id="tax${index}"  value="${plist3.salesTax}"readonly="true"class="noEnterSubmit" title="${index}"/> </td>
		<td><form:input path="listpossalesItem[${index}].salesAmount" value="${plist3.salesAmount}" class="posrate noEnterSubmit" id="amt${index}" readonly="true"title="${index}"/> </td>
		<form:input type="hidden" path="listpossalesItem[${index}].salesItemID" value="${plist3.salesItemID}" readonly="true" title="${index}"/>									
		</tr>
		<c:set var="index" value="${index + 1}" scope="page"/>
	</c:forEach>		
	
	<!--  TO show Data on Update Mode -->
	 	
  	<c:set var="indxs" value="${1}" scope="page"/>
  	
	<c:forEach var="plistR" items="${salesItemsList}">
		<tr class="staticRow">
			<td></td>
		<td><form:input path="listpossalesItem[${indxs}].posItemCode" class="icode noEnterSubmit" id="dIcode${indxs}" readonly="true" title="${indxs}"/></td>
		<td><form:input path="listpossalesItem[${indxs}].categoryName"  class="category noEnterSubmit" id="categoryname${indxs}" readonly="true" title="${indxs}"/> </td>
		<td><form:input path="listpossalesItem[${indxs}].itemName"  id="dIname${indxs}" class="category noEnterSubmit" readonly="true" title="${indxs}"/> </td>
		<td><form:input path="listpossalesItem[${indxs}].quantity" class="QtyPcs noEnterSubmit"  id="qty${indxs}" readonly="true" title="${indxs}"/> </td>
		<td><form:input path="listpossalesItem[${indxs}].totalPieces" class="QtyPcs noEnterSubmit"  id="pcs${indxs}" readonly="true" title="${indxs}"/> </td>
		<td><form:input path="listpossalesItem[${indxs}].salesRate" class="posrate noEnterSubmit" id="salesrates${indxs}" title="${indxs}" onkeydown="return (event.keyCode!=13);" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/> </td>
		<td><form:input path="listpossalesItem[${indxs}].discountAmount" id="discounts${indxs}" class="noEnterSubmit" title="${indxs}"  onkeydown="return (event.keyCode!=13);" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /> </td>
		<td><form:input path="listpossalesItem[${indxs}].salesTax" id="tax${indxs}" readonly="true"class="noEnterSubmit" title="${indxs}"/> </td>
		<td><form:input path="listpossalesItem[${indxs}].salesAmount" class="posrate noEnterSubmit" id="amt${indxs}" readonly="true"title="${indxs}"/> </td>
		<form:input path="listpossalesItem[${indxs}].POSdeleted" type="hidden" id="posdeleted${ind}" readonly="true"title="${ind}" class="isDeleted"/>
		<form:input type="hidden" path="listpossalesItem[${indxs}].salesItemID" readonly="true" title="${indxs}"/>	
		</tr>
		<c:set var="indxs" value="${indxs + 1}" scope="page"/>
	</c:forEach>
		
  	<tr class="POSpurchaseTotal" style="display:none;">
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
		<td><form:input path="possales.taxinAmount" readonly="true"id="totalTax"/> </td>
	</tr>
	<tr class="POSsalesTotal">
		<td colspan="8"></td>
		<td>Total Value</td>
		<td><form:input path="possales.totalAmount" readonly="true"id="totalVal" /> </td>
	</tr>
	<tr class="POSsalesTotal">
		<td colspan="4"></td>
		<td>Reason</td>
	<td class="amt">
	<form:select  path="possales.returnreason" id="return_Reason"  cssStyle="width:95px;">
		<form:option label="General" value="General">General</form:option>
		<form:option label="Damaged" value="Damaged">Damaged</form:option>
	</form:select>
	</td>
		<td>Receipt Mode</td>
	<td class="amt">
	<form:select  path="possales.receiptType" id="receipt_Type"  cssStyle="width:95px;">
		<form:option label="Cash" value="Cash">Cash</form:option>
		<form:option label="Credit" value="Credit">Credit</form:option>
	</form:select>
	</td>
		<td>Round Off</td>
		<td><form:input path="possales.roundOff" id="roundOff"readonly="true"/> </td>
	</tr>
	<tr class="POSsalesTotal">
		<td colspan="6"></td>
		
		
		<td class="GrandTotalLabel">Grand Total</td>
		<td colspan="0" class="GrandTotal" ><form:input path="possales.netAmount" readonly="true" id="grandAmt"/> </td>
		
		<form:input type="hidden" path="possales.transactionNo"  />
		<form:input type="hidden" path="possales.posSalesId"  />
	</tr>

	<tr class="subm"><td bgcolor="#33FFFF"  colspan="10" align="center">
          <input type="submit" name="insert" value="Insert" id="insert" class="button_style"/>
          <input type="submit" name="update" value="Update" id="update" class="button_style"/>
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
	<script type="text/javascript">
$(document).ready(function(){
	$("#date").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var prettyDate =myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
	
	
		
		 var URIloc = $(location).attr('href');
			var uripath = URIloc.split('/')[4];
			var errorType = $("#errorType").val();
			
			if(uripath.indexOf("viewPOSSales.htm") >= 0){
				$("#insert").hide();
				$("#update").show();
				
			}

			if(uripath.indexOf("formPOSsalesreturn.htm") >= 0){
				$("#insert").show();
				$("#update").hide();
				$("#date").val(prettyDate);
			}
			if(errorType == "insertError") {
				$("#insert").show();
				$("#update").hide();
				$("#date").val(prettyDate);
			}else if(errorType == "updateError"){
				$("#insert").hide();
				$("#update").show();
				
			}
});
</script>
<script type="text/javascript" src="script/NaNValidate.js"></script>
<script type="text/javascript" src=script/Pos_salesReturn.js></script>
</body>
</html>