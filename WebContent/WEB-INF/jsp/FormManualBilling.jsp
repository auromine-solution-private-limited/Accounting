<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manual Billing Form</title>
</head>
<body>
<div id="col-form" >
	<div class="boxed" >
	<div class="title" style="text-align:left;" >Manual Billing Form</div>
	<div class="content">
	
<%
if(session.getAttribute("username")==null)
{
	response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
<form:form name="manualBIll" id="manBillForm" commandName="ManBillWrapper" method="POST" action="formManualSales.htm" class="formStyle">		
	<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>
		
	</div>
	<table id="formLedger" cellpadding="0" border="0" cellspacing="0" align="center" >
	  <tr class="form_cat_head"><td colspan="4"><div id="mBillFormHeading" ></div></td></tr>
	  <tr>
	  		<td class="label">Sales Date * </td>
	  		<td width="280"><form:input path="manBill.mBDate" id="mnulBllng" class="noEnterSubmit" /></td>
			<td class="label" >Bill No * </td>
			<td><form:input path="manBill.mBillNo" id="mBillNo" class="noEnterSubmit" /></td>
	   </tr>
	   <tr>
	  		<td class="label">Board Rate * </td>
	  		<td width="280"><form:input path="manBill.boardRate" id="boardRate" class="noEnterSubmit cal_dep"/></td>
			<td class="label" >Customer Name * </td>
			<td><form:input path="manBill.customerName" id="customerName" class="noEnterSubmit"/></td>
	   </tr>
	   <tr>
	  		<td class="label">Customer's Tin No</td>
	  		<td width="280"><form:input path="manBill.customerNameTinNo" id="customerNameTinNo" class="noEnterSubmit"/></td>
			<td class="label" >Address</td>
			<td><form:input path="manBill.address" id="address" class="noEnterSubmit"/></td>
	   </tr>
	 </table>
	 <table cellpadding="0" cellspacing="0" align="center" class="ManualBilling">
	<tr class="RowHead">
		<td>Id</td>
		<td></td>
		<td>Description</td>
		<td>Qty</td>
		<td>Gross weight</td>
		<td>Net weight</td>
		<td>Amount</td>	
	</tr>
	
	<!--  Static Row : TO show Data on Insert Mode -->
	
	<c:set var="countVal" value="0" scope="page"/>
	<c:forEach var="mbList1" items="${ManRowList}" varStatus="ManRowListFormList" >
		<c:set var="countVal" value="${ManRowListFormList.count}" scope="page"/>
	</c:forEach>
	
	<c:if test="${countVal eq 0}" >
		<tr class="staticRowMB">
			<td>0</td>
			<td><input type="button" name="delete" id="rowDelButton0" class="delMB" title="0" style="width:20px;"/></td>
			<td><form:input path="manBillRowList[0].description" id="description0" class="MbDescription noEnterSubmit" title="0" /></td>
			<td><form:input path="manBillRowList[0].qty" id="qty0" maxlength="10" class="MbQtyWght cal_dep noEnterSubmit" title="0"  onkeyup="extractNumber(this,0,true);"  /></td>
			<td><form:input path="manBillRowList[0].grossWeight" id="grossWeight0" class="MbQtyWght cal_dep noEnterSubmit" title="0" onkeyup="extractNumber(this,3,true);" /></td>
			<td><form:input path="manBillRowList[0].netWeight" readonly="true" id="netWeight0" class="MbQtyWght cal_dep noEnterSubmit" title="0" onkeyup="extractNumber(this,3,true);"  /></td>
			<td><form:input path="manBillRowList[0].amount" id="mBillamount0" class="MbAmt cal_dep noEnterSubmit" title="0" onkeyup="extractNumber(this,2,true);"  /></td>
			<td style="display:none">
				<form:input path="manBillRowList[0].deleted" id="deleted0" class="isDeleted" title="0" />
				<form:input path="manBillRowList[0].manBillRowListId" id="manBillRowListId0" title="0"/></td>
		</tr>
	</c:if>
	
	<!--  TO show Data on Insert Error and Update Error -->
	
	<c:set var="index" value="${0}" scope="page"/>
	<c:forEach var="mbList2" items="${ManRowList}">	
		<tr class="staticRowMB">
			<td>${index}</td>
			<td><input type="button" name="delete" id="rowDelButton${index}" class="delMB" title="${index}" style="width:20px;"/></td>
			<td><form:input path="manBillRowList[${index}].description" id="description${index}" value="${mbList2.description}" class="MbDescription noEnterSubmit" title="${index}" /></td>
			<td><form:input path="manBillRowList[${index}].qty" id="qty${index}" maxlength="10" value="${mbList2.qty}" class="MbQtyWght cal_dep noEnterSubmit" title="${index}" onkeyup="extractNumber(this,0,true);" /></td>
			<td><form:input path="manBillRowList[${index}].grossWeight" id="grossWeight${index}" value="${mbList2.grossWeight}" class="MbQtyWght cal_dep noEnterSubmit" title="${index}" onkeyup="extractNumber(this,3,true);"/></td>
			<td><form:input path="manBillRowList[${index}].netWeight" readonly="true" id="netWeight${index}" value="${mbList2.netWeight}" class="MbQtyWght cal_dep noEnterSubmit" title="${index}" onkeyup="extractNumber(this,3,true);"/></td>
			<td><form:input path="manBillRowList[${index}].amount" id="mBillamount${index}" value="${mbList2.amount}" class="MbAmt cal_dep noEnterSubmit" title="${index}" onkeyup="extractNumber(this,2,true);"/></td>
			<td style="display:none">
				<form:input path="manBillRowList[${index}].deleted" id="deleted${index}" value="${mbList2.deleted}" class="isDeleted" title="${index}" />
				<form:input path="manBillRowList[${index}].manBillRowListId" id="manBillRowListId${index}" value="${mbList2.manBillRowListId}" title="${index}"/></td>
		</tr>
		<c:set var="index" value="${index + 1}" scope="page"/>
	</c:forEach>
	
	<!--  TO show Data on Update Mode -->
	 	
  	<c:set var="ind" value="${1}" scope="page"/>
	<c:forEach var="mbList3" items="${ManBillRowList}">
		<tr class="staticRowMB">
			<td>${ind}</td>
			<td><input type="button" name="delete" id="rowDelButton${ind}" class="delMB" title="${ind}" style="width:20px;"/></td>
			<td><form:input path="manBillRowList[${ind}].description" id="description${ind}" class="MbDescription noEnterSubmit" title="${ind}" /></td>
			<td><form:input path="manBillRowList[${ind}].qty" id="qty${ind}" maxlength="10" class="MbQtyWght cal_dep noEnterSubmit" title="${ind}" onkeyup="extractNumber(this,0,true);" /></td>
			<td><form:input path="manBillRowList[${ind}].grossWeight" id="grossWeight${ind}" class="MbQtyWght cal_dep noEnterSubmit" title="${ind}" onkeyup="extractNumber(this,3,true);"/></td>
			<td><form:input path="manBillRowList[${ind}].netWeight" readonly="true" id="netWeight${ind}" class="MbQtyWght cal_dep noEnterSubmit" title="${ind}" onkeyup="extractNumber(this,3,true);"/></td>
			<td><form:input path="manBillRowList[${ind}].amount" id="mBillamount${ind}" class="MbAmt cal_dep noEnterSubmit" title="${ind}" onkeyup="extractNumber(this,2,true);"/></td>
			<td style="display:none">
				<form:input path="manBillRowList[${ind}].deleted" id="deleted${ind}" class="isDeleted" title="${ind}" />
				<form:input path="manBillRowList[${ind}].manBillRowListId" id="manBillRowListId${ind}" title="${ind}"/></td>
		</tr>
		<c:set var="ind" value="${ind + 1}" scope="page"/>
	</c:forEach>
	
	<tr class="ManualBillingTotal">
		<td colspan="6"><input type="button" value="Add Item" id="addItemMb"></td>
		<td></td>
	</tr>
	<tr class="ManualBillingTotal">
		<td colspan="6">Sub Total</td>
		<td><form:input path="manBill.subTotal" id="subTotal" class="noEnterSubmit TotalAmt cal_dep" onkeyup="extractNumber(this,2,true);" /></td>
	</tr>
	<tr class="ManualBillingTotal" id="trVatAmount" >
		<td colspan="6" >Vat Amount</td>
		<td><form:input path="manBill.vatAmount" id="vatAmount" class="noEnterSubmit TotalAmt cal_dep" onkeyup="extractNumber(this,2,true);" /></td>
	</tr>
	<tr class="ManualBillingTotal">
		<td colspan="6">Total Amount</td>
		<td><form:input path="manBill.totalAmount" id="totalAmount" class="noEnterSubmit TotalAmt cal_dep" onkeyup="extractNumber(this,2,true);" /></td>
	</tr>
	<tr class="ManualBillingTotal">
		<td colspan="3">Sales order No</td>
		<td><form:input path="manBill.salesOrderNo" id="salesOrderNo" class="noEnterSubmit TotalAmt"/></td>
		<td colspan="2">Advance Amount</td>
		<td><form:input path="manBill.advanceAmount" id="advanceAmount" class="noEnterSubmit TotalAmt cal_dep" onkeyup="extractNumber(this,2,true);" /></td>
	</tr>
	<tr class="ManualBillingTotal">
		<td colspan="3">Savings Scheme No</td>
		<td><form:input path="manBill.savingsSchemeNO" id="savingsSchemeNO" class="noEnterSubmit TotalAmt"/></td>
		<td colspan="2">Savings Amount</td>
		<td><form:input path="manBill.savingAmount" id="savingAmount" class="noEnterSubmit TotalAmt cal_dep" onkeyup="extractNumber(this,2,true);" /></td>
	</tr>
	<tr class="ManualBillingTotal">
		<td colspan="6">Bonus</td>
		<td><form:input path="manBill.bonus" id="bonus" class="noEnterSubmit TotalAmt" onkeyup="extractNumber(this,2,true);" /></td>
	</tr>
	<tr class="ManualBillingTotal">
		<td colspan="6">Total Amount Received</td>
		<td><form:input path="manBill.totalAmtReceived" readonly="true" id="totalAmtReceived" class="noEnterSubmit TotalAmt cal_dep" onkeyup="extractNumber(this,2,true);" /> </td>
	</tr>
	<tr class="ManualBillingTotal">
		<td colspan="3">Cash Amount</td>
		<td><form:input path="manBill.cashAmount" id="cashAmount" class="noEnterSubmit TotalAmt cal_dep" onkeyup="extractNumber(this,2,true);" /></td>
		<td colspan="2">Card Amount</td>
		<td><form:input path="manBill.cardAmount" id="cardAmount" class="noEnterSubmit TotalAmt cal_dep" onkeyup="extractNumber(this,2,true);" /></td>
	</tr>
	<tr class="ManualBillingTotal">
		<td colspan="3">Cheque Amount</td>
		<td><form:input path="manBill.chequeAmount" id="chequeAmount" class="noEnterSubmit TotalAmt cal_dep" onkeyup="extractNumber(this,2,true);" /></td>
		<td colspan="2">Voucher amount</td>
		<td><form:input path="manBill.voucherAmount" id="voucherAmount" class="noEnterSubmit TotalAmt cal_dep" onkeyup="extractNumber(this,2,true);" /></td>
	</tr>
	<tr class="ManualBillingTotal">
		<td colspan="3">Notes</td>
		<td colspan="5"><form:textarea path="manBill.Notes" id="Notes" class="MBnotes noEnterSubmit" /></td>
	</tr>
	<tr>
		<td colspan="8" align="center">
          <input type="submit" name="insert" value="Insert" id="insert" class="button_style"/></input>
          <input type="submit" name="update" value="Update" id="update" class="button_style"/></input>
          <input type="submit" name="cancel" value= "Cancel" class="button_style"></input>
          <input type="hidden" id="errorName" value="${errorType}"/>
          <form:input type="hidden" path="manBill.mBillId" />
          <form:input type="hidden" path="manBill.mBillAutoNO" />
          <form:input type="hidden" path="manBill.mBillFormType" id="mBillFormType" />
          
         </td>
    </tr>	
	</table> 
	 </form:form>
	</div>
	</div>
	</div>

<script type="text/javascript" src="script/formValidations.js"></script>
<script type="text/javascript" src="script/NaNValidate.js"></script>
<script type="text/javascript" src="script/js_ManualBilling.js"></script>
<script type="text/javascript">

$(document).ready(function() {
	
	$("#mnulBllng").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var prettyDate =myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
	
	var URIloc = $(location).attr('href');
	var uripath = URIloc.split('/')[4];
			
	var errorType=$("#errorName").val();
	
	if(errorType=="updateError"){
		$("#update").show();
		$("#insert").hide();
	}
	else if(errorType=="insertError"){			
		$("#update").hide();
		$("#insert").show();
		$("#mnulBllng").val(prettyDate);
	}		
	if(uripath.indexOf("newManualSales.htm") >= 0){
		$("#update").hide();
		$("#insert").show();
		$("#mnulBllng").val(prettyDate);
		$("#mBillFormType").val("vatForm");
		$("#trVatAmount").show();
	}
	if(uripath.indexOf("viewManualSales.htm") >= 0) {
		$("#update").show();
		$("#insert").hide();
	}		
	if(uripath.indexOf("newManualSalesVF.htm") >= 0){
		$("#update").hide();
		$("#insert").show();
		$("#mnulBllng").val(prettyDate);
		$("#mBillFormType").val("vatFreeForm");
		$("#trVatAmount").hide();
	}
	checkformType();
});
function checkformType(){
	if($("#mBillFormType").val() == "vatFreeForm"){
		$("#trVatAmount").hide();
		$("#mBillFormHeading").html("New VAT Free Manual Sales Form");
	}else{
		$("#trVatAmount").show();
		$("#mBillFormHeading").html("New VAT Manual Sales Form");
	}
}
</script>
</body>
</html>