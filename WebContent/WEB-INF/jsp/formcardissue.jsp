<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Form Card Issue</title>
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
				<td><input type="text" name="customerName" id="popcustomerName" class="nameCapitalize categorytext noEnterSubmit" value="" /></td>
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
	<div id="container">
		<!-- Container Div Open -->
		<!---------------------------------------->
		<div id="col-form" align="left">
			<!-- Form Div Open -->
			<div class="boxed">
				<!-- Boxed Div Open -->
				<div class="title">Card Issue Form</div>
				<div class="content">
					<!-- Content Div Open -->
					<%
						if (session.getAttribute("username") == null) {
							response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
						}
					%>
					<c:url var="saveUrl" value="formcardissue.htm" />
					<form:form modelAttribute="cardissue" action="${saveUrl}"
						method="POST" cssClass="formStyle">
						<div id="form_error">
							<div>
								<a class="close"><img src="images/closebox.png"></a>
							</div>
							<br> <span class="error"><form:errors path="*"
									id="errorDisplay" /></span>
						</div>
						<table id="formLedger" cellpadding="0" border="0" cellspacing="0"
							align="center">
							<tr class="form_cat_head">
								<td colspan=4>Card Issue Form</td>
							</tr>
							<tr>
								<td class="label">Customer Name<a name="modal" id="custSalesPopup" class ="add_new" href="#newCustomer" title="Add New Customer">Add</a></td>
								<td><form:select path="customerName" id="poscustomer" class="custName">
										<form:option value="">Select</form:option>
										<c:forEach var="ledger" items="${customername}">
											<form:option value="${ledger.ledgerName}">
											</form:option>
										</c:forEach>
									</form:select></td>
								<td class="label">Number Of Installment</td>
								<td><form:input path="installment" onkeypress="return ValidateNumberKeyPress(this, event);" id="no_installment" class="noEnterSubmit"/></td>

							</tr>
							<tr>
								<td class="label">Scheme Name</td>
								<td><form:select path="schemeName" id="SchemeNameforissue">
										<form:option value="">Select</form:option>
										<c:forEach var="scheme" items="${schemename}">
											<form:option value="${scheme.schemeName}">
											</form:option>
										</c:forEach>
									</form:select> <form:hidden path="schemeType" id="schemeType" /></td>
								<td class="label SchemeAmt">Scheme Amount</td>
								<td class="SchemeAmt"><form:input path="schemeInAmount"id="SchemeInAmount" readonly="true" /></td>
								<td class="label SchemeGms">Scheme Grams</td>
								<td class="SchemeGms"><form:input path="schemeInGrams"id="SchemeInGrams" readonly="true" /></td>
							</tr>
							<tr class="SchemeAmt">
								<td class="label">Opening Balance in Rs</td>
								<td><form:input path="openingBalanceInRs" id="op_bal_amt_rs" readonly="true" onkeyup="extractNumber(this,2,true)" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
								<td class="label">Closing Balance in Rs</td>
								<td><form:input path="closingBalanceInRs" id="cls_bal_amt_rs" readonly="true" /></td>
							</tr>
							<tr class="SchemeGms">
								<td class="label">Opening Balance in Grams</td>
								<td><form:input path="openingBalanceInGrams" id="op_bal_amt_grms" readonly="true" onkeyup="extractNumber(this,3,true)" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
								<td class="label">Closing Balance in Grams</td>
								<td><form:input path="closingBalanceInGrams" id="cls_bal_amt_grms" readonly="true" /></td>
							</tr>
							<tr>
							<tr>
								<td class="label">Date of Joining</td>
								<td><form:input path="dateOfJoining" id="scs_dateofjoin" class="noEnterSubmit"/></td>
								<td class="label"></td>
								<td>
								
								</td>
							</tr>
							</tr>
							<form:input type="hidden" path="cardId" />
							<form:input type="hidden" path="cardNo" />
							<form:input type="hidden" path="status" />
							<input type="hidden" id="errorName" value="${errorType}" />
							<input type="hidden" id="payment_receipt" value="${payment_receipt_details}" />							
							<tr>
								<td colspan="4" align="center"><input type="submit"
									name="insert" value="Insert" id="insert" class="button_style" /></input>
									<input type="submit" name="update" value="Update" id="update"
									class="button_style" /></input> <!-- <input type="submit" name="delete" value="Delete" id="delete"/></input> -->
									<input type="submit" name="cancel" value="Cancel"
									class="button_style"></input></td>
							</tr>
						</table>
					</form:form>



				</div>
				<!-- Content Div Close -->
			</div>
			<!-- Boxed Div Close -->
		</div>
		<!-- Form Div Close -->
	</div>
	<!-- Container Div Close -->
	<script type="text/javascript">
		$(document).ready(
				function() {
					$("#scs_dateofjoin").datepicker({
						showOn : 'both',
						buttonImageOnly : true,
						buttonImage : 'images/icon_cal.png',
						dateFormat : 'd/mm/yy'
					});
					var myDate = new Date();
					var month = myDate.getMonth() + 1;
					var prettyDate = myDate.getDate() + '/' + month + '/'
							+ myDate.getFullYear();
					$("#scs_dateofjoin").val(prettyDate);
					var URIloc = $(location).attr('href');
					var uripath = URIloc.split('/')[4];
					var errorType = $("#errorName").val();

					if (uripath.indexOf("editcardissue.htm") >= 0) {
						$("#insert").hide();
						if($('#payment_receipt').val()!="empty")
							{
							$("#update").hide();	
							}
						else
							$("#update").show();
						
					}
					if (uripath.indexOf("formcardissue.htm") >= 0) {
						$("#insert").show();
						$("#update").hide();
					}
					if (errorType == "insertError") {
						$("#insert").show();
						$("#update").hide();
					} else if (errorType == "updateError") {
						$("#insert").hide();
						$("#update").show();
					}
					
					$("#insert").click(init);
					$("#update").click(init);
					
					$("#SchemeNameforissue").change(ajax_SchemeDetail_forcardIssue).triggerHandler('change');
					$("#op_bal_amt_rs").keyup(function() {
						var openingBalanceval = $(this).val();
						$("#cls_bal_amt_rs").val(openingBalanceval);
					});
					$("#op_bal_amt_grms").keyup(function() {
						var openingBalanceval = $(this).val();
						$("#cls_bal_amt_grms").val(openingBalanceval);
					});
					$('#no_installment').keyup(function() {
						$(this).val(function(index, oldVal) {
							return oldVal.replace(/[^\d-]/g, '');
						});
					});
					
					$("#no_installment").keyup(calc_OpeningBal);
					$("#SchemeNameforissue").keyup(ajax_SchemeDetail_forcardIssue);
					/****prevent defaulr submit when enter key is pressed****/
					$('.noEnterSubmit').keypress(function(e){
						if ( e.which == 13 ) e.preventDefault();
						});
					
					/*** new script added to allow only _ and - for customer name field on 25-2-13 after bug is posted ****/
					$('#popcustomerName').keyup(function() {
						 $(this).val($(this).val().replace(/[^A-Za-z0-9\s_-]/g,''));
					});
				});
		function init(){
			if(	$("#no_installment").val()=='' || $("#no_installment").val()=='undefined' || $("#no_installment").val()== null){
				$("#no_installment").val(0);
			}
		}		
				
		function ajax_SchemeDetail_forcardIssue() {
			$(".closingBalance").val("0.00");
			var schemeName = $("#SchemeNameforissue").val();

			$.ajax({
				type : 'GET',
				url : 'SchemeDetailCardIssue.htm',
				data : {
					schemeName : schemeName
				},
				success : function(data) {
					data = data.replace('[', '');
					data = data.replace(']', '');
					var arr = new Array();
					arr = data.split(',');
					$.each(arr, function(index, item1) {
						switch (index) {
						case 0: {

							$("#schemeType").val(item1);
							break;
						}
						case 1: {
							$("#SchemeInAmount").val(
									parseFloat(item1).toFixed(2));
							break;
						}
						case 2: {
							$("#SchemeInGrams").val(
									parseFloat(item1).toFixed(3));
							break;
						}
						}
						SchemeAmtGms();
						calc_OpeningBal();
					});
				},
				error : function(xmlHttpRequest, textStatus, errorThrown) {
					if (xmlHttpRequest.readyState == 0
							|| xmlHttpRequest.status == 0)
						return;
				}
			});
		}
		function calc_OpeningBal(){
			var noInstallment = $("#no_installment").val();
			var schemeType = $("#schemeType").val();
			var schemeInAmount = $("#SchemeInAmount").val();
			var schemeInGold = 	$("#SchemeInGrams").val();
			if(schemeType == "Amount"){
				$("#op_bal_amt_grms").val("0.000");
				$("#cls_bal_amt_grms").val("0.000");
				var opBalAMt = noInstallment * schemeInAmount;
				$("#op_bal_amt_rs").val(parseFloat(opBalAMt).toFixed(2));
				$("#cls_bal_amt_rs").val(parseFloat(opBalAMt).toFixed(2));
			}else{
				$("#op_bal_amt_rs").val("0.00");
				$("#cls_bal_amt_rs").val("0.00");
				var opBalGold = noInstallment * schemeInGold;
				$("#op_bal_amt_grms").val(parseFloat(opBalGold).toFixed(3));
				$("#cls_bal_amt_grms").val(parseFloat(opBalGold).toFixed(3));
			}
		}
	</script>
	<script type="text/javascript" src="script/js_CustomerCreation.js"></script>
</body>
</html>