 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Form Saving Scheme Payment</title>
</head>
<body>
<div id="container"><!-- Container Div Open -->
<!---------------------------------------->
<div id="col-form" align="left"><!-- Form Div Open -->
	<div class="boxed"><!-- Boxed Div Open -->
	<div class="title">Saving Scheme Payment</div>	
	<div class="content"><!-- Content Div Open -->
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
<a href="ssrCancelList.htm" class="create_or_list">Saving Cancel/Payment List</a>
<br />
<form:form name="ssReceiptForm" method="POST" commandName="ssReceipt" action="formsavingreceipt.htm" class="formStyle">
	  <div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>		
	</div> 
	
	<div class="summary">Form Saving Scheme Cancel/Payment </div>
	<br />
	<table id="formLedger"  cellpadding="0" border="0" cellspacing="0" align="center">
		 <tr class="form_cat_head"><td colspan=4>Saving Scheme Payment &raquo;
		 <span id="headerCustomerSSP"></span></td></tr>
		<tr>
		<td class="label">Customer Name</td>
		<td><form:select path="customerName" id="SSPCustomerName" class="noEnterSubmit" >
				<form:option value="0" >Select</form:option>
				<c:forEach var="custName" items="${CustomerNameList}">
						<form:option value="${custName}">${custName}</form:option>
				</c:forEach>			 
			</form:select>
			<form:input type="hidden" id="ssrFormType" path="formType" value="Saving Scheme Payment"/>	
		</td>
		<td class="label">Scheme Name</td>
		<td><form:select id="SSPSchemeName" path="schemeName" class="noEnterSubmit" >
				<form:option value="0">Select</form:option>		
				<c:forEach var="sspScheme" items="${SchemeNameList}">
						<form:option value="${sspScheme}">${sspScheme}</form:option>
				</c:forEach>						
			</form:select>	
			<form:input type="hidden" id="schemeType" path="schemeType" value=""/>		
		</td>
		</tr>
		<tr>
		<td class="label"> Card No</td>
		<td><form:select id="sspCardNo" path="cardNo" class="noEnterSubmit" >
			<form:option value="0" >Select</form:option>	
			<c:forEach var="card" items="${CardNumbersList}">
				<form:option value="${card}">${card}</form:option>
			</c:forEach>						
			</form:select>
		</td>
		<td class="label SchemeAmt"> Scheme Amount</td>
		<td class="SchemeAmt">
		<form:input type="text" path="schemeInAmount" id="SchemeInAmount" readonly="true" class="noEnterSubmit numField" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
		<td class="label SchemeGms">Scheme Grams</td>
		<td class="SchemeGms"><form:input type="text" path="schemeInGrams" id="SchemeInGrams" readonly="true" class="noEnterSubmit numField" onkeyup="extractNumber(this,3,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		</tr>
		<tr>
		<td class="label">Credit Account</td>
		<td><form:select path="creditAccount" class="noEnterSubmit" >
				
				<c:forEach var="ledger" items="${DebitAccountList}">
					<form:option value="${ledger.ledgerName}"></form:option>
				</c:forEach>			 
			</form:select>
		</td>
		<td class="label">Payment Amount</td>
		<td><form:input type="text" path="paymentAmount" id="ssrReceiptAmount" class="noEnterSubmit numField" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
		</tr>
		<tr>
		<td class="label">Receipt Date</td>
		<td><form:input type="text" path="receiptDate" id="ssrReceiptDate" class="noEnterSubmit" /></td>
		<td class="label">Narration</td>
		<td><form:input type="text" path="narration" class="noEnterSubmit" /></td>
		</tr>
		<tr><td colspan="4" align="center">
	          	<input type="submit" name="insert" value="Insert" id="insert" class="button_style"/></input>
	          	<input type="submit" name="update" value="Update" id="update" class="button_style"/></input>
				<!-- <input type="submit" name="delete" value="Delete" id="delete"/></input> -->
	        	<input type="submit" name="cancel" value= "Cancel" class="button_style"></input>
	        	<input type="hidden" id="errorName" value="${errorType}"/> 
	        	<form:input type="hidden" id="entryId" path="receiptId" />
	        	<form:input type="hidden" path="receiptNO" />
	        	<form:input type="hidden" path="paymentClosingGrams" id="sspClosingGrams" />
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
	var prettyDate = myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
	$("#ssrReceiptDate").val(prettyDate);
});
</script>
<script type="text/javascript" src="script/js_SSReceipt.js"></script>
</body>
</html>