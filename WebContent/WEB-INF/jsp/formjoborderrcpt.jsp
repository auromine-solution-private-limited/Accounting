<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<body>

<!---------------------------------------->
<div id="col-form" align="left">
	<div class="boxed">
	<div class="title" >Update Job Order Details</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<c:url var="addUrl" value="/formjoborderrcpt.htm?jobOrderId=${joborder.jobOrderId}" />
	<form:form name="form1" id="form1" modelAttribute="joborder" method="POST" action="${addUrl}" class="formStyle">
	<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>
		
	</div>
	<table id="formJobOrder" cellpadding="0" border="0" cellspacing="0" align="center" >
	<tr class="form_cat_head"><td colspan="4"><B>Order Issued Information</B></td></tr>
	<tr>
		<td class="label">Order Number</td><td><form:input path="orderNo" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		<td class="label">Order Date</td><td><form:input path="orderDate" id="date"/></td>
	</tr>
	
		<tr>		
			<td  class="label">Smith Name</td>
			<td>
				<form:select path="smithName" id ="s_name">
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
			<form:select path="bullionType" id="bullion" >
			<form:option value="Gold Bullion" label="Gold Bullion"/>					
			<form:option value="Silver Bullion" label="Silver Bullion"/>
			</form:select>
		</td>	
		<td class="label">Issued Item Code </td>				
		<td>
		    <form:input path="fromItemCode" id="codes" readonly="true"/>		    
		</td>							
		</tr>	
  		
  		<tr>  		  				
		<td class="label">Issued Gross Weight</td>
		<td><form:input path="issuedGrossWeight"  id="gwt" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>	
		
		<td class="label">Issued Net Weight</td>
		<td><form:input path="issuedNetWeight"  id="nwt" onkeypress="return ValidateNumberKeyPress(this, event);" /></td> 
  		</tr>
<!---------- Received item Code ---------->
		
		
		<tr class="form_cat_head"><td colspan="4"><B>Stone Details</B></td></tr>
		<tr>
			<td  class="label">Stone Details</td><td><form:input path="stoneDetails"/></td>
			<td class="label">Stone Weight</td><td><form:input path="stoneWeight" id="s_swt" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
  		</tr>
  		<tr>
			<td class="label">Stone Cost</td><td><form:input path="stoneCost" id="s_cost" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
			<td class="label">Total Expense</td><td><form:input path="totalExpense" id="tot_cost" readonly="true"/></td>
		</tr>	
		
		<tr class="form_cat_head"><td colspan="4"><B>Received Item Details</B></td></tr>		
		
		<tr>  		    		    
		    <td class="label">Item Category </td>
		    <td>		    
		      	<form:input path="categoryName" id="ornament"  readOnly="true"/>
		    </td>		    
 
			<td class="label">Received Item Code </td>				
		    <td>
		    <form:input path="toItemCode" id="code" readonly="true"/>		    
		    </td>				
		</tr>
		<tr>
			<td class="label">Gross Weight</td><td><form:input path="finisheditemGrossWt" id="wt_gw" onkeyup="extractNumber(this,3,true);" onkeypress="return ValidateNumberKeyPress(this, event);"  /></td>
			<td class="label">Touch</td><td><form:input path="touch" id="wt_touch" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>													
		</tr>
		
		<tr>
			<td class="label">Melting</td><td><form:input path="melting" id="wt_melt" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
			<td class="label" >Finished Net Weight</td><td><form:input path="finisheditemNetWt" id="nw" readonly="true"/></td>													
		</tr>
		
		<tr>
			<td class="label" >Number of Pieces</td><td><form:input path="numberOfPieces" id="nop" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
			<td class="label">Size</td><td><form:input path="size" id="siz" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>			
		</tr>		
		
		<tr>			
			<td class="label" >Labour Charge</td><td><form:input path="labourCharge" id="labourCharge" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);"  /></td>
			<td class="label">Wastage</td><td><form:input path="wastage" id="wastage" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>								
		</tr>
		
		<tr>
		<td  class="label">Status</td>
		<td>
				<form:select path="status" id="statusType" >			
				<form:option value="Issued" label="Issued"/>
				<form:option value="Received" label="Received"/>				
				</form:select>
				
		</td>
		<td class="label">Description</td><td><form:input path="Description"/></td>
		
		<input type="hidden" id="orderId" value="${joborder.jobOrderId}"/>
		<input type="hidden" id="errorName" value="${errorType}"/>
		
		</tr>
		<tr>
		    <td bgcolor="#33FFFF" colspan="4" align="center">		    
            	<input type="submit" name="update" id ="update" value="Update" class="button_style"/>          	
          		<input type="submit" name="cancel" value= "Cancel" class="button_style"/>
          	</td>
        </tr>
				
	</table>
	</form:form>
		</div>
	</div>
</div>
<script language="javascript" type="text/javascript"> 

var URIloc = $(location).attr('href');
var uripath = URIloc.split('/')[4];

var errorType=$("#errorName").val();
var sType  = $("#statusType").val();
var order_Id = $("#orderId").val();
var values1 = $("#bullion").val();

$(document).ready(function(){
	$("#date").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
	var orderdate=$("#date").val();
	if(orderdate=="")
		{
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate =myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
		$("#date").val(prettyDate);
		}	
	

	$("#date1").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		
	if(errorType=="updateError"){	
		$('#update').removeAttr("disabled");
	}
	
	 if (values1 == "Silver Bullion") {
			$("#ornament").val("Silver Ornaments");
			$('#code').val('IT100006');
	 }
	 else{
	    	   $("#ornament").val("Gold Ornaments");
	    	   $('#code').val('IT100001');
	 }
	
	$("#bullion").change(metalType);
	//
	var no = null;
			no =  $.getUrlVar('jobOrderId');
		
			if(no != null) {
				var statusType = $("#statusType").val();
				$("#statusType").change(function(){
					
					if($('#statusType option:selected').val() == "Issued"){
						$("#wt_gw").val("0.000");
						$("#wt_touch").val("0.00");
						$("#wt_melt").val("0.00");
						$("#nw").val("0.000");
						$("#nop").val("0");
						$("#siz").val("0.00");
						$("#labourCharge").val("0.00");
						$("#wastage").val("0.000");					
					}
				});
			}
			
});
$.extend({
	  getUrlVars: function(){
	    var vars = [], hash;
	    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
	    for(var i = 0; i < hashes.length; i++)
	    {
	      hash = hashes[i].split('=');
	      vars.push(hash[0]);
	      vars[hash[0]] = hash[1];
	    }
	    return vars;
	  },
	  getUrlVar: function(name){
	    return $.getUrlVars()[name];
	  }
});

function metalType(){
	var values = $("#bullion").val();
    
	   if (values == "Silver Bullion") {
			$("#ornament").val("Silver Ornaments");
			 $('#code').val('IT100006');
			 $('#codes').val('IT100007');
	    }else{
	    	   $("#ornament").val("Gold Ornaments");
	    	   $('#code').val('IT100001');
	    	   $('#codes').val('IT100002');
	    }
	   
	   $('#wt_gw').focus();
}

$('#update').click(init);

function init(){
	
	var ZERO = 0;
	if($('#wt_gw').val().length == 0){
		$('#wt_gw').val(ZERO.toFixed(3));
		$('#wt_touch').val(ZERO.toFixed(3));
	}
	
	
	if($("#gwt").val().length == 0){
		$("#gwt").val(ZERO.toFixed(3));
		$("#nwt").val(ZERO.toFixed(3));
	}
	
	if($("#nop").val().length == 0){
		$("#nop").val(ZERO);		
	}
}

$('[id^=s]').keyup(function() { 
	
    var st_wt = document.getElementById("s_swt").value;
	var st_cost = document.getElementById("s_cost").value;
	var totalCost = 0;
			
	totalCost = st_wt * 5 * st_cost; 
	
	document.getElementById("tot_cost").value = totalCost.toFixed(2);
});


$('#gwt').keyup(function() {
	   $('#nwt').val(this.value);	   
});


//Net weight calculation based on melting and touch
$('[id^=wt]').keyup(function() { 
	
	 	var txt1 = document.getElementById('wt_gw').value;
	    var txt2 = document.getElementById('wt_touch').value;
	    var txt3 = document.getElementById('wt_melt').value;
		var FNTsum=0;
		
		if(txt2 > 0 || txt3 > 0){
		     FNTsum = txt1 * ( (txt2 / 100) + (txt3 / 100) );
		}else{
		   FNTsum = parseFloat(txt1);
		}
		document.getElementById('nw').value =FNTsum.toFixed(3);
});

//For Deleivery date validation
$('#update').click(joborderdatecomparison);

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

</script>

</body>		
</html>