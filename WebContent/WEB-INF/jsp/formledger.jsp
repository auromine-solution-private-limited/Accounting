<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<body>

<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
<!--
<div id="content_container">

<div id="col-one" align="left">


<div class="boxed">
		<div class="title">Board Rate</div>
		<div class="content">
	<c:forEach var="ratemaster" items="${boardrate}">
      <div class="boardrate" cellpadding="0" cellspacing="2">
			<tr>  
				<td> <img src="images/gold.png" align="middle">&nbsp;</td><td><b>Gold:</b> Rs.${ratemaster.goldOrnaments}/gm</td>
			</tr>
			<br><br>
			<tr>	
				<td> <img src="images/silver.png" align="middle">&nbsp;</td><td><b>Silver:</b> Rs.${ratemaster.silverOrnaments}/gm</td>
           
			</tr>
        </div>
		</c:forEach>
		</div>
	</div>
	
 div class="boxed">
		<div id="rotator">
			<img src="catalog/slider-img6.jpg">
			<img src="catalog/slider-img1.jpg">
			<img src="catalog/slider-img2.jpg">
			<img src="catalog/slider-img3.jpg">
			<img src="catalog/slider-img4.jpg">
        	<img src="catalog/slider-img5.jpg">
      	</div>
</div 
<div class="boxed">
			<div class="title">Dash Board</div>
			<div class="content">
			<form id="form2" method="get" action="#">
			<fieldset>	
			<legend>Search</legend>
					<b>Sales Order Pending<br><br>
					Pending SO:</b>${message}<br>					
					
					<p class="tiny"><a href="formledgerpo.htm">More Details...</a></p>
					<br>
					
					<b>Low Metal Balance<br><br>
					Low Metal:</b>${SLCount}<br>
					<p class="tiny"><a href="formledgerlist.htm">More Details...</a></p>
					<br>
			</fieldset>	
			</form>
			</div>		
	</div>		
	<div class="boxed">
			<div class="title"align="left">Account Search</div>
			<div class="content">
				<form:form id="form4" method="get" commandName="Ledger" onsubmit="return validate_account();" action="searchledger.htm">
					<fieldset>
					<legend>Search</legend>
				 	Account Name (or) Id
					<input id="ledgerName"  name="ledgerName" value="" class="textfield" />
					<input id="search" type="submit" name="search" value="Search" class="button" />
					<label id="lblledgerName"></label>					
					</fieldset>					
			</form:form>			
			</div> 
	</div>	

	<div class="boxed">
			<div class="title">Admin</div>
			<div class="content">
				<form id="form3" method="get" action="#">
					<fieldset>					
				
					<tr>
					<td><p class="tiny"><a href = "ratemaster.htm" ><b>Rate Master</b></a></p></td>
					</tr><br>
					<tr>
					<td><p class="tiny"><a href = "alluser.htm" ><b>List Of User</b></a></p></td>
					</tr>					
					</fieldset>
				</form>
			</div>
	</div>
</div>	

-->
<div id="col-form">
	<div class="boxed">
	<div class="title" align="left">Create Ledger Account</div>
	<div class="content">
	<!----------------------------------------------------->
   <!--  <a href="accounts.htm" class="create_or_list" style="float:left;">List of Accounts</a> -->


	<!-- <align="left"><h2>Bank Account</h2> -->
	
	<form:form method="post" action="formledger.htm" commandName="ledgerId" class="formStyle">
	<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>
		
	</div>
	<!-- <div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error" id="pos"><form:errors path="*"/></span>
	</div> -->
	
			<table id="formLedger" cellpadding="0" border="0" cellspacing="0" align="center">
			  <tr class="form_cat_head"><td colspan=2><div class="ledgerlabel"></div></td></tr>
			  <tr><td class="label">Ledger Name</td>
			  <td width="280"><form:input path="ledgerName" id="ledger_name" maxlength="50" class="nameCapitalize noEnterSubmit"/></td>
			  </tr>
			  
			  <tr>
			  <td class="label">Date*</td>
			  <td><form:input path="ledgerDate" id="date" class="noEnterSubmit"/></td>
			  </tr>
			  
			  <tr><td class="label">Group Under</td>
					<td>
					<form:select path="accountGroup" id="acc_group">
					<form:option value="" label = "Select"></form:option>	
					<form:option value="Bank Account" label="Bank Account"/>
					<form:option value="Bank Loan" label="Bank Loan"/>
					<form:option value="Bank OCC Account" label="Bank OCC Account" />
					<form:option value="Capital Account" label="Capital Account"/>
					<form:option value="Cash Account" label="Cash Account"/>
					<form:option value="Current Assets" label="Current Assets"/>
					<form:option value="Current Liabilities" label="Current Liabilities"/>
					<form:option value="Direct Expenditure" label="Direct Expenditure"/>
					<form:option value="Direct Income" label="Direct Income"/>
					<form:option value="Fixed Assets" label="Fixed Assets"/>
					<form:option value="Indirect Expenditure" label="Indirect Expenditure"/>
					<form:option value="Indirect Income" label="Indirect Income"/>
					<form:option value="Profit And Loss Account" label="Profit And Loss Account"/>
					<form:option value="Purchase" label="Purchase"/>
					<form:option value="Sales" label="Sales"/>
					<form:option value="Stock Account" label="Stock Account"/>
					</form:select>
					</td></tr>
				<tr><td class="label">Opening Balance</td><td width="280">
				<form:input path="openingBalance" maxlength="15" id="opBal" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit"/></td></tr>
				
				<tr  style="display:none;"><td  class="label">Closing Balance</td><td width="280">
				<form:input path="closingTotalBalance" id="clBal" readonly="true"/></td></tr>
				<tr><td class="label">Opening Type</td>
					<td><form:select path="openingType" id="opType_acc">		
					<form:option value="Credit" label="Credit"></form:option>
					<form:option value="Debit" label="Debit"></form:option></form:select>
					</td>
				</tr>
					
					
				<tr style="display:none;"><td class="label">Closing Type</td><td>
				<form:input path="closingTotalType" id="clType_acc" readonly="true"/></td></tr>
				<tr><td bgcolor="#33FFFF" colspan="3" align="center">
				
				         <input type="submit" name="insert" value="Insert" id="insert" class="button_style validateDate"/>
				         <input type="submit" name="update" value="Update" id="update" class="button_style validateDate"/> 				        
				         <input type="submit" name="cancel" value= "Cancel" class="button_style"></input>
						 <form:input type="hidden"path="ledgerId"/>
						 <input type="hidden" id="errorName" value="${errorType}" />
						 <input type="hidden" value="${accountGroup}" id="accountGroup">
						 <input type="hidden" name="accountId" value="${accountId}" />
				    	 <input type="hidden" id="errorLedger" value="${errorType}"/>
				         </td>
				</tr>    
				        
				</table>	
		</form:form>
	 
			</div>
		</div>
 	</div>
</div>
<script language="javascript"  type="text/javascript">


	$(document).ready(function() {
		
		var URIloc = $(location).attr('href');
		var uripath = URIloc.split('/')[4];		
			
		var accountGroup=$("#acc_group").val();			
		var errorType=$("#errorName").val();
		
		$("#date").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate =myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
		if($("#date").val()=='')
		{		
			$("#date").val(prettyDate);
		}
			
		
		if(errorType=="insertError"){		
			$('#insert').show();
			$('#update').hide();
		}
		
		if(errorType=="updateError"){	
			$('#insert').hide;
			$('#update').show();
		}		
		var mode="";
				   
		if(uripath.indexOf("formledger.htm") >= 0) {						
			$("#insert").show();
			$('#update').hide();
			mode="insertMode";
		}		
		
		if(uripath.indexOf("viewformledger.htm") >= 0){			
			$("#opBal").focus();
			$('#insert').hide();
			$('#update').show();
			$('div.ledgerlabel').text('Update').append(':').append(accountGroup).append(' ').append('Ledger');
			$('#ledger_name').attr("disabled",true);
			$('#acc_group').attr('disabled',true);
			$('#opBal').keyup(function() {				 
			  $('#clBal').val(not($(this)).value);				
			});
			mode="viewMode";
		}
		
		if(mode=="insertMode"){
			group_change();
		}	
		
			$('#acc_group').change(group_change);
			$('#acc_group').keyup(group_change);			
			$('#ledger_name').focus();	
			
			
			/****prevent default submit when enter key is pressed****/
			$('.noEnterSubmit').keypress(function(e){
				if ( e.which == 13 ) e.preventDefault();
			});
	});	
	
		$('#insert').click(init);
		$('#update').click(init);
		
		$(".validateDate").bind('click',function(){
			var result = checkDate();
			if(result == false){
				alert("*Enter Valid Date");
				return false;
			}			
		});
	
		function init(){
			
			var ZERO = 0;
			if($("#opBal").val().length == 0){
				$("#opBal").val(ZERO.toFixed(2));
				$("#clBal").val(ZERO.toFixed(2));
			}			
		}	
		
		//Invalid date validation
		function checkDate(){
			var currVal = $("#date").val();
			var regexDatePattern = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
			var dtArray = currVal.match(regexDatePattern);
			if(dtArray == null){
				return false;
			}
			var dtDay = dtArray[1];
			var dtMonth = dtArray[3];
			var dtYear = dtArray[5];
			if(dtMonth < 1 || dtMonth > 12){
				return false;
			}else if(dtDay < 1 || dtDay > 31){
				return false;
			}else if((dtMonth == 4 || dtMonth ==6 || dtMonth == 9 || dtMonth == 11) && dtDay == 31){
				return false;
			}else if(dtMonth == 2){
				var isLeap = ( dtYear %4 ==0 && (dtYear % 100 != 0 || dtYear % 400 == 0) );
				if(dtDay > 29 || (dtDay == 29 && !isLeap)){
					return false;
				}
			}
			return true;
		}
		
		function group_change() {
		    
			var values = $('#acc_group').val();
		    
		    if (values == 'Bank Account') {
		        $('#opType_acc').val('Debit'); 
		        $('#clType_acc').val('Debit');
		    }
		    
		    if(values == 'Bank Loan') {
		        $('#opType_acc').val('Credit');
		        $('#clType_acc').val('Credit');
		    }
		    
		    if(values == 'Bank OCC Account') {
		        $('#opType_acc').val('Credit');
		        $('#clType_acc').val('Credit');
		    }
			
		    if(values == 'Capital Account') {
		        $('#opType_acc').val('Credit'); 
		        $('#clType_acc').val('Credit');
		    }
		   if(values == 'Cash Account') {
		        $('#opType_acc').val('Debit');
		        $('#clType_acc').val('Debit'); 
		    }
		    
		   if(values == 'Current Assets') {
		        $('#opType_acc').val('Debit');
		        $('#clType_acc').val('Debit'); 
		    }
		    
		   if(values == 'Current Liabilities') {
		        $('#opType_acc').val('Credit');
		        $('#clType_acc').val('Credit'); 
		    }
		    
		   if(values == 'Direct Expenditure') {
		        $('#opType_acc').val('Debit');
		        $('#clType_acc').val('Debit');
		    }
		   
		   if(values == 'Indirect Expenditure') {
		        $('#opType_acc').val('Debit');
		        $('#clType_acc').val('Debit');
		    }
		    
		   if(values == 'Direct Income') {
		        $('#opType_acc').val('Credit');
		        $('#clType_acc').val('Credit');
		    }
		   
		   if(values == 'Indirect Income') {
		        $('#opType_acc').val('Credit');
		        $('#clType_acc').val('Credit');
		    }
		    
		   if(values == 'Fixed Assets') {
		        $('#opType_acc').val('Debit');
		        $('#clType_acc').val('Debit');
		    }
		    
		   if(values == 'Profit And Loss Account') {
		        $('#opType_acc').val('Credit');
		        $('#clType_acc').val('Credit');
		    }
			
		   if(values == 'Purchase') {
		        $('#opType_acc').val('Debit');
		        $('#clType_acc').val('Debit');
		    }
		    
		   if(values == 'Sales') {
		        $('#opType_acc').val('Credit');
		        $('#clType_acc').val('Credit');
		    }
		    
		   if (values == 'Stock Account') {
		        $('#opType_acc').val('Debit');
		        $('#clType_acc').val('Debit');
		    }
		    
		    return false;
		}
		
	$.urlParam = function(name){
	var results = new RegExp('[\\?&]' + name + '=([^&#]*)').exec(top.window.location.href); 
	return (results !== null) ? results[1] : 0;
			}
	var ledgerName=(decodeURIComponent($.urlParam('ledgerGroupe')));
	if(ledgerName!=0)
	{
		$("#acc_group").val(ledgerName);	
		$('div.ledgerlabel').text('Create New').append(':').append(ledgerName).append(' ').append('Ledger');
	}
	if(ledgerName==0)
	{
		var hiddenVal=$('#accountGroup').val();
		if(hiddenVal!='')
		{
			$("#acc_group").val(hiddenVal);	
			$('div.ledgerlabel').text('Create New').append(':').append(hiddenVal).append(' ').append('Ledger');;
		}
	}
 </script>
</body>
</html>

