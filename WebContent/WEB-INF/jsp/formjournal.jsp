<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<div id="col-form" align="left">
<div class="boxed">
<div class="title">Journal</div>
<div class="content">
<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>

<form:form method="post" action="formjournal.htm" commandName="Journal" class="formStyle">
<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>
	</div>
	<form:hidden path="journalNO"/>
<table id="formJournal" cellpadding="0" border="0" cellspacing="0" align="center" >
		<tr class="form_cat_head">
			<td colspan=4><B>Journal Entries</B></td>
		</tr>
		<tr style="display:none; " >
			
			<td class="label">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>		
		<tr>
			<td class="label">Journal Type</td>
			<td><form:select path="journalType" id="journalType">
								<form:option value="select" label="Select"></form:option>
								<form:option value="Receipt" label="Receipt" />
								<form:option value="Payment" label="Payment" />
								<form:option value="Journal" label="Journal" />
								<form:option value="Contra" label="Contra" />
							</form:select></td>

			<td class="label">Journal Date*</td>
			<td><form:input path="journalDate" id="date"/></td>
		</tr>
		<tr class="radio_ReceiptShow">
			
			<td class="label">Receipt Type</td>
			<td colspan="3">
			<form:radiobutton path="receiptType" value="Advance Receipt" label="Advance Receipt" class="radio"/>
			<form:radiobutton path="receiptType" value="Jewel Repair" label="Jewel Repair" class="radio"/>
			<form:radiobutton path="receiptType" value="Bill Receipt" label="Bill Receipt" class="radio"/>
			<!-- <form:radiobutton path="receiptType" value="Sales Order Receipt" label="Sales Order Receipt" class="radio"/> -->
			<!--<form:radiobutton path="receiptType" value="Saving Receipt" label="Saving Receipt" class="radio"/>-->
			
			</td>
		</tr>
		
	<tr>
						<td class="label">Debit Account</td>
						<td><form:select path="debitAccountName" id="debtamt">
						<form:option value="" label="select"></form:option>
						<c:forEach var="receipt" items="${RecieptDebit}">
						 <form:option value="${receipt.ledgerName}" class="DebitTypereceipt" label="${receipt.ledgerName}"></form:option>
						</c:forEach>
						<c:forEach var="receipt" items="${RecieptCredit}">
						 <form:option value="${receipt.ledgerName}" class="DebitTypepayment" label="${receipt.ledgerName}"></form:option>
						</c:forEach>
						<c:forEach var="receipt" items="${Journaltype}">
						 <form:option value="${receipt.ledgerName}" class="DebitTypejournal" label="${receipt.ledgerName}"></form:option>
						</c:forEach>
						</form:select></td>
						<td class="label">Amount</td>
						<td><form:input path="debitAmount" id="db_amount"
								onkeyup="extractNumber(this,2,true)"
								onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
					</tr>
		
		<tr>
						<td class="label">Credit Account</td>
						<td><form:select path="creditAccountName" id="credit">
									<form:option value="" label="select"></form:option>
						<c:forEach var="receipt" items="${RecieptCredit}">
						 <form:option value="${receipt.ledgerName}" class="CreditTypereceipt" label="${receipt.ledgerName}"></form:option>
						</c:forEach>
						<c:forEach var="receipt" items="${RecieptDebit}">
						 <form:option value="${receipt.ledgerName}" class="CreditTypepayment" label="${receipt.ledgerName}"></form:option>
						</c:forEach>
						<c:forEach var="receipt" items="${Journaltype}">
						 <form:option value="${receipt.ledgerName}" class="CreditTypejournal" label="${receipt.ledgerName}"></form:option>
						</c:forEach>
									</form:select></td>
						<td class="label">Amount</td>
						<td><form:input path="creditAmount" id="cr_amount"
								onkeyup="extractNumber(this,2,true)"
								onkeypress="return ValidateNumberKeyPress(this, event);"
								readonly="true" /></td>
					</tr>
		
		
		<tr>
			<td class="label">Narration</td>
			<td><form:input path="narration"/></td>	
			
			<td class="label">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<form:input type="hidden"  path="journalId"/>
		<input type="hidden" id="errorName" value="${errorType}"/>	
		<tr>
			<td colspan=4 align="center">
			<input type="submit" name="insert" value="Insert" id="insert" class="button_style"/>
			<input type="submit" name="update" value="Update" id="update" class="button_style"/> 
			<input type="submit" name="cancel" value= "Cancel" class="button_style"></input>	
			</td>
		</tr>
	</table>
	<h3><b><u><a href="journal.htm">Back to Journal List</a></u></b></h3>
</form:form></div>
</div>
</div>
<script type="text/javascript" src="script/journalledgerchange.js"></script>
<script language="javascript" type="text/javascript" > 

	
	var errorType=$("#errorName").val();
	var URIloc = $(location).attr('href');
	
	var uripath = URIloc.split('/')[4];
	
	$(document).ready(function(){
			
		$("#date").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate =myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
		if($("#date").val()=='')
		{		
			$("#date").val(prettyDate);
		}
		

		if(uripath.indexOf("formjournal.htm") >=0 ){	
			
			$('#insert').show();
			$('#update').hide();
		}	
		
		if(errorType == "insertError"){	
			$('#insert').show();	
			$('#update').hide();
		}
		
		if(errorType == "updateError"){	
			$('#insert').hide();	
			$('#update').show();
			if ($('.radio').is(':checked')) {	
				$('#journalType').attr('disabled',true);
			}
			$('.radio').click(function(){
				return false;
			});
		}
		
		if(uripath.indexOf("viewformjournal.htm") >=0 ){	
			
			$('#insert').hide();	
			$('#update').show();
			if ($('.radio').is(':checked')) {	
				$('#journalType').attr('disabled',true);
			}
			
			$('.radio').click(function(){
				return false;
			});
			
			
		}
		
		$("#db_amount").attr("maxlength","15");
		
	});		
	
		
	$('#insert').click(init);
	$('#update').click(function(){
		$('#journalType').attr('disabled',false);
		init();
	});
	
	
	function init(){
		
		var ZERO = 0;
		if($("#db_amount").val().length == 0){
			$("#db_amount").val(ZERO.toFixed(2));
			$("#cr_amount").val(ZERO.toFixed(2));
		}
	}
	
	
	$('#db_amount').keyup(function() {
		   $('#cr_amount').val(this.value);	   
	});
	
	
</script>
</body>
</html>