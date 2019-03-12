 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Form Saving Scheme Receipt</title>
</head>
<body>
<div id="container"><!-- Container Div Open -->
<!---------------------------------------->
<div id="col-form" align="left"><!-- Form Div Open -->
	<div class="boxed"><!-- Boxed Div Open -->
	<div class="title">Form Saving Scheme Receipt</div>	
	<div class="content"><!-- Content Div Open -->
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	<form:form name="ssReceiptForm" method="POST" commandName="ssReceipt" action="formsavingreceipt.htm" class="formStyle">
	  <div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>		
	</div> 
	
	<div class="summary">Saving Scheme Receipt Form</div>
	<br />
	<div class="smsg summary" style="color:#3EA99F;"></div>	
	<br />
	<table id="formLedger"  cellpadding="0" border="0" cellspacing="0" align="center">
		 <tr class="form_cat_head"><td colspan=4>Saving Scheme Receipt &raquo;
		<span id="headerCustomer"></span></td></tr>
		<tr>
		<td class="label"> Card No</td>
		<td><form:input id="ssrCardNo" path="cardNo" class="noEnterSubmit" /></td>
		
		<td class="label">Customer Name</td>
		<td><form:input path="customerName" id="ssrCustomerName" class="noEnterSubmit" readonly="true" />
			<form:input type="hidden" id="ssrFormType" path="formType" value="Saving Scheme Receipt"/>	
		</td>
		</tr>
		<tr>
		<td class="label">Scheme Name</td>
		<td><form:input id="ssrSchemeName" path="schemeName" class="noEnterSubmit" readonly="true"/>
			<form:input type="hidden" id="schemeType" path="schemeType" value=""/>
		</td>
		<td class="label SchemeAmt"> Scheme Amount</td>
		<td class="SchemeAmt">
		<form:input type="text" path="schemeInAmount" id="SchemeInAmount"  readonly="true" class="noEnterSubmit numField" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
		<td class="label SchemeGms">Scheme Grams</td>
		<td class="SchemeGms"><form:input type="text" path="schemeInGrams" id="SchemeInGrams" readonly="true" class="noEnterSubmit numField" onkeyup="extractNumber(this,3,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		</tr>
		<tr>
		<td class="label">Debit Account</td>
		<td><form:select path="debitAccount" id="debitAccountName" class="noEnterSubmit">
				
				<c:forEach var="ledger" items="${DebitAccountList}">
					<form:option value="${ledger.ledgerName}"></form:option>
				</c:forEach>			 
			</form:select>
		</td>
		<td class="label">Receipt Amount</td>
		<td><form:input type="text" path="receiptAmount" id="ssrReceiptAmount" class="noEnterSubmit numField" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		</tr>
		<tr>
		<td class="label">Receipt Date</td>
		<td><form:input type="text" path="receiptDate" id="ssrReceiptDate" class="noEnterSubmit" /></td>
		<td class="label">Narration</td>
		<td><form:input type="text" path="narration" class="noEnterSubmit" /></td>
		</tr>
		
		<tr><td colspan="4" align="center">
		<input type="hidden" id="errorName" value="${errorType}"/>
        	<input type="hidden" id="lastNo" value="${lastReceiptNumber}" /> 
        	<form:input type="hidden" id="entryId" path="receiptId" />
        	<form:input type="hidden" path="receiptNO" />
        	<form:input type="hidden" path="receiptInstallmentNO" />
        	<input name="printInvoice" type="hidden" id="printInvoice" value="${printInvoice}"/>
		    <input name="printPreviewId" type="hidden" id="printId" value="${receiptNO}"/>
          	<input type="submit" name="insert" value="Insert" id="insert" class="button_style"/></input>
          	<input type="submit" name="update" value="Update" id="update" class="button_style"/></input>
			<!-- <input type="submit" name="delete" value="Delete" id="delete"/></input> -->
        	<input type="submit" name="cancel" value= "Cancel" class="button_style"></input>
           	</td>
        </tr>
	</table>
	
	</form:form>
	
			</div> <!-- Content Div Close -->
		</div><!-- Boxed Div Close -->
	</div><!-- Form Div Close -->
</div><!-- Container Div Close -->
<script type="text/javascript">
$(document).ready(function(){
	$("#ssrReceiptDate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var prettyDate =myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
	$("#ssrReceiptDate").val(prettyDate);
});
</script>
<script type="text/javascript" src="script/js_SSReceipt.js"></script>
</body>
</html>