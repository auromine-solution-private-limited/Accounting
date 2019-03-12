<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<body>

<!---------------------------------------->
<div id="col-form" align="left">
	<div class="boxed">
	<div class="title" >Create Job Order</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<c:url var="saveUrl" value="formjoborder.htm" />
	<form:form modelAttribute="joborder" action="${saveUrl}" method="post" class="formStyle">
	<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>
		
	</div>
	<table id="formJobOrder" cellpadding="0" border="0" cellspacing="0" align="center">
	<tr class="form_cat_head"><td colspan="4">Job Order Information</td></tr>	
	
	<tr>
		<td class="label">Order Number</td><td><form:input path="orderNo" id="order_no" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
		<td class="label">Order Date</td><td><form:input path="orderDate" id="date"/></td>
	</tr>
	
		<tr>		
			<td class="label">Smith Name</td>
			<td>
				<form:select path="smithName">
				<form:option value="" label="Select"></form:option>
				<c:forEach var="ledger" items="${suppliername}">
				<form:option  value="${ledger.ledgerName}">
				</form:option>
				</c:forEach>
				</form:select>
			</td>
			<td class="label">Delivery Date</td>
			<td><form:input path="deliveryDate" id="date1"/></td>									
		
		</tr>	
								
		
		<tr>		
		  		
  		<td class="label">Metal Issued Type</td><td>
		
			<form:select path="bullionType" id="bullion">	
			<form:option value="" label = "Select"></form:option>
			<form:option value="Gold Bullion" label="Gold Bullion"/>	
			<form:option value="Silver Bullion" label="Silver Bullion"/>		
									
			</form:select>
		</td>			
		<td class="label">Issue Item Code </td>	    
	    <td><form:input path="fromItemCode" id="code" readonly="true"/></td>		
  		</tr>	
  							
		<tr>		
		<td class="label">Issued Gross Weight</td>
		<td><form:input path="issuedGrossWeight" id="gwt" onkeyup="extractNumber(this,3,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
		
		
		<td class="label">Issued Net Weight</td>
		<td><form:input path="issuedNetWeight" id="nwt" readonly="true" /></td> 		
		</tr>	
		
		<tr style="display:none;">
		<td>
				<form:select path="status">			
				<form:option value="Issued" label="Issued" />				
				</form:select>					
		</td>
		</tr>		
				
		<tr class="form_cat_head"><td colspan="4">Stone Details</td>
		</tr>		
		<tr>
			<td class="label">Stone Description</td><td><form:input path="stoneDetails"/></td>
			<td class="label">Stone Weight</td><td><form:input path="stoneWeight" id="s_swt" onkeyup="extractNumber(this,3,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
  		</tr>
  		
  		<tr>
			<td class="label">Stone Cost</td><td><form:input path="stoneCost" id="s_cost" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
			<td class="label">Total Cost</td><td><form:input path="totalExpense" id="tot_cost" readonly="true" /></td>
		</tr>
		<td class="label">Description</td><td><form:input path="Description"/></td>
	
		
		
		<tr>
		    <td bgcolor="#33FFFF" colspan="4" align="center">
		    <input type="submit" name="insert" value="Insert" id="insert" class="button_style"/>            
          	<input type="submit" name="cancel" value= "Cancel" class="button_style"/></td>
        </tr>    
		
	</table>
	</form:form>
		</div>
	</div>
</div>

<script language="javascript" type="text/javascript" > 
$(document).ready(function(){
	$("#date").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var prettyDate =myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
		$("#date").val(prettyDate);

	$("#date1").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});

	$('#insert').click(init);
	$('#order_no').focus();
});


function init(){
	
	var ZERO = 0;
	if($("#gwt").val().length == 0){
		$("#gwt").val(ZERO.toFixed(3));
		$("#nwt").val(ZERO.toFixed(3));
		$("#s_swt").val(ZERO.toFixed(3));
		$("#s_cost").val(ZERO.toFixed(2));
		
	}
}


//For Deleivery date validation
$('#insert').click(joborderdatecomparison);

function joborderdatecomparison() {
	var orderDate = $("#date").val();
	  var salesDate = $("#date1").val();
	 // alert("no Date;");

	  if (Date.parse(orderDate) > Date.parse(salesDate)) {
	      //$("#DateFrom").val(DateToValue)
	      alert("Delivery Date is Previous from Order Date ");
	      return false;
	  }
	  else{
		 
	  }
}

$('#bullion').change(function() {
    var values = $('#bullion').val();
    if (values == 'Gold Bullion') {
        $('#code').val('IT100002');
    }
	if (values == 'Silver Bullion') {
        $('#code').val('IT100007');
    }
    return false;
});

$('#gwt').keyup(function() {
	   $('#nwt').val(this.value);	   
});

$('[id^=s]').keyup(function() { 
	
    var st_wt = document.getElementById("s_swt").value;
	var st_cost = document.getElementById("s_cost").value;
	var totalCost = 0;
			
	totalCost = st_wt * 5 * st_cost; 
	
	document.getElementById("tot_cost").value = totalCost.toFixed(2);
});

</script>
</body>		

</html>