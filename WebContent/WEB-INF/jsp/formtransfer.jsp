 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>      

<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<script type="text/javascript" src="script/transfer_wt.js"></script>
<div id="col-form" align="left">
<div class="boxed">
<div class="title">Stock Transfer</div>
<div class="content">
<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
<form:form method="post" action="formtransfer.htm" commandName="Transfers" class="formStyle">
<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>
		
	</div>
	
<table id="formTransfer" width="650" cellpadding="0" border="0" cellspacing="0" class="table_label" align="center" >
	
		<tr class="form_cat_head">
			<td colspan=8><B>Stock Transfer</B></td>
		</tr>
		<tr>
			<td class="label">Transfer No*</td>
			<td bgcolor="#BBDDFF" width="2"></td>
			<td bgcolor="#BBDDFF">
			
			<form:input path="transferNO" size="10"  cssStyle="width:100px;" readonly="true"/>
			<input id="transferNOS" type="hidden" name="transferNOS" value="${transferNO}" class="textfield" />
			</td>
			<td class="label">Transfer Type</td>
			<td bgcolor="#BBDDFF">
			<form:select path="transferType" id="tType_select" cssStyle="width:100px;">
			<form:option value="Transfer_In"></form:option>
			<form:option value="Transfer_Out"></form:option>
			<form:option value="Internal"></form:option>
			</form:select>
		
			
			</td>
			<td class="label">Transfer Date*</td>
			<td colspan=2><form:input size="10"  cssStyle="width:100px;" path="transferDate" id="date"/></td>
		
		</tr>
		<tr>
			<td class="label">From ItemCode</td>
			<td class="label" >SR</td>
			<td class="label">Bullion</td>
			<td class="label">Item Name</td>
			<td class="label">From Gross</td>
			<td class="label">From Qty</td>
			<td class="label Hide_pcs">From Pcs</td>
			<td class="label Hide_totGrossWT">FrmTotalGwt</td>
			<td class="label"></td>
						
		</tr>
	
	<!-- first condition  -->
	
	<!--  -->
		<c:forEach var="transfer" items="${f}">
			<input type="hidden" id="Titemname" value="${transfer.itemName}" />
			<input type="hidden" id="Tbulliontype"value="${transfer.metalType}"/>	
			</c:forEach>
			
		<tr>
			<td width="80"><form:input path="fromItemNo" cssStyle="width:100px;"  size="12" id="textbox1"  /></td>
			<td><input name="f"  id="sb1" value="" style=" width: 18px"  class="search"   size="3" type="submit"   /></td>
			<td><form:input path="fromBullion" value="" size="12" id="frombullion" style="width:80px;"/></td>			
			<td><form:input path="fromItemName" value="" size="12" id="itemname" style="width:100px;"/></td>
			<td><form:input path="fromGrossWeight" value="" size="7"  id="frm_Gross" onkeyup="growss_qty();" style="width:70px;" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
			<td><form:input path="fromQty" value=""  size="3" id="frm_qty" onkeyup="growss_qty();" style="width:50px;" onkeypress="return isNumberKey(event)"/></td>
			<td class="Hide_pcs"><form:input path="fromPieces" value=""  size="3" id="frm_pices" style="width:50px;" onkeypress="return isNumberKey(event)"/></td>
			<td class="Hide_totGrossWT"><form:input path="fromTotalGrossWeight" value="" size="10" id="frm_ToGross"  style="width:80px;"/></td>
			<td></td>
			
		</tr>
		<tr>
			<td class="label">To ItemCode</td>
			<td class="label" width="5">SR</td>
			<td class="label">Bullion</td>
			<td class="label">To Item</td>
			<td class="label">To Gross</td>
			<td class="label">To Qty</td>
			<td class="label Hide_pcs">To Pcs</td>
			<td class="label Hide_pcs">ToTotalGwt</td>
			<td class="label"></td>
			<!-- getting second search input -->
			<c:forEach var="Totransfer" items="${f1}">
			<input type="hidden" id="Toitemname" value="${Totransfer.itemName}" />
			<input type="hidden" id="Tobulliontype"value="${Totransfer.metalType}"/>
			<input type="hidden" id="Togrossweight"value="${Totransfer.grossWeight}"/>			
			</c:forEach>
			</tr>
		<tr>	
		</tr>
		<tr>
			<td bgcolor="#BBDDFF" width="80"><form:input path="toItemNo" cssStyle="width:100px;" size="12" id="textbox2" value="" /></td>
			<td bgcolor="#BBDDFF"><input name="f1"  id="sb2"  class="search" value=""  style=" width: 22px" size="3" type="submit"  /></td>
			<td bgcolor="#BBDDFF"><form:input path="toBullion" size="12" id="tobullion" style="width:80px;"/></td>			
			<td bgcolor="#BBDDFF"><form:input path="toItemName"  size="12" id="toitemname" style="width:100px;"/></td>
			<td bgcolor="#BBDDFF"><form:input path="toGrossWeight" id="To_Gross" onkeyup="togrowss_qty();" cssStyle="width:70px;" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
			<td bgcolor="#BBDDFF"><form:input path="toQty" id="To_qty" onkeyup="togrowss_qty();" cssStyle="width:50px; " onkeypress="return isNumberKey(event)"/></td>
			<td bgcolor="#BBDDFF" class="Hide_pcs"><form:input path="toPieces" id="To_pices"  cssStyle="width:50px;" onkeypress="return isNumberKey(event)"/></td>
			<td bgcolor="#BBDDFF" class="Hide_totGrossWT"><form:input path="toTotalGrossWeight" id="To_totGross"  cssStyle="width:80px;"/></td>
			<td bgcolor="#BBDDFF"></td>
		
		</tr>
		<tr> 
			<td bgcolor="#33FFFF"  width="400" colspan=8 align="center">
		  	<input type="submit" name="insert" value="Insert" id="insert" onclick="return totalgrossweight();" class="button_style"/>
		
			<input type="submit" name="cancel" value="cancel" class="button_style"></input>
				
			</td>
		</tr>
	</table>
	
	<table cellspacing="0" cellpadding="0" class="TransferStockList">
		<tr>
		    <td><b>UNTAGGED ITEMS</b></td>			
		    <td><b>GROSS WEIGHT</b>	    
				<c:forEach var="itemList" items="${itemWeight}">
				<tr><td><b>${itemList.categoryName}</b></td>
				<td><b>${itemList.totalGrossWeight}</b></td>			
				</tr>				
				</c:forEach>
			</td>
		</tr>
	</table>
	<table cellspacing="0" cellpadding="0" class="TransferStockList">
		<tr>
			<td><b>TAGGED ITEMS</b></td>
			<td><b>GROSS WEIGHT</b></td>
		</tr>
		
		<tr>
			<td><b>Tagged Gold Item</b></td>			
			<td><b><fmt:formatNumber pattern="0.000" type="number" maxFractionDigits="3" value="${goldWeight}" /></b></td>
		</tr>
		<tr>
			<td><b>Tagged Silver Item</b></td>		
			<td><b><fmt:formatNumber pattern="0.000" maxFractionDigits="3" value="${silverWeight}" /></b></td>
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
		var prettyDate =myDate.getDate()+ '/' + month + '/' + myDate.getFullYear();
		if(document.getElementById('date').value=='')
		{
		document.getElementById('date').value=prettyDate;
			return true;
		}
		else
		{
		return false;
		}
			
		

	});
	//Default qty setting for qty fields
	$(document).ready(function()
			{
		$("#tType_select").change(mm);
		//$("#textbox1").focus();
		//$("#tType_select").attr('selectedIndex', 0);
		
	});	
	
	function mm(){
		var transfertype=$("#tType_select").val();
		if(transfertype=='Transfer_In')
		{
			var defaultqty=1;
			/* $("#frm_qty").val(defaultqty).attr("readonly",true);
			$("#To_qty").val(defaultqty).attr("readonly",true); */
		}
		else if(transfertype=='Transfer_Out')
		{
			var defaultqty1=1;
			/* $("#frm_qty").val(defaultqty1).attr("readonly",true);
			$("#To_qty").val(defaultqty1).attr("readonly",true);
 */		}
		else if(transfertype=='Internal')
		{		
			var defaultqty12=0;
			if(defaultqty12!=0){
	/* 		$("#frm_qty").val(0).val(defaultqty12).removeAttr("readonly",false);
			$("#To_qty").val(defaultqty12).removeAttr("readonly",false); */
			}
		}
	}
	//first search button	
	var item=$("#Titemname").val();
	if(item!=null){
   $("#itemname").val(item);
     }
	 var bullion=$("#Tbulliontype").val();
   if(bullion!=null){
   $("#frombullion").val(bullion);
	}//end here 
   
   //send search button 
   var tobull=$("#Tobulliontype").val();
   if(tobull!=null){
	$("#tobullion").val(tobull) ;  
   }
   var toitem=$("#Toitemname").val();
   if(toitem!=null){
	   $("#toitemname").val(toitem);
   }
   var togross=$("#Togrossweight").val();
   if(togross!=null){
	   $("#To_Gross").val(togross);
   }
   //send search button ends here
	//loose metal stock updations
	var fromItemcode=$("#textbox1").val();
	var toItemcode=$("#textbox2").val();
	var fromtotgwt=0.0;
	if(toItemcode=='IT100011'|toItemcode=='it100011')
		{
		$("#To_Gross").val(fromtotgwt);
		}
   if(toItemcode=='IT100012'|toItemcode=='it100012')
	   {
	   $("#To_Gross").val(fromtotgwt);
	   }
   //Transfer_in and Transfer_out
   
	if(document.getElementById("transferNO").value == ""){
		var transfer_Code =document.getElementById("transferNOS").value;
		document.getElementById("transferNO").value= transfer_Code;	
	}
   
	$('[id^=frm]').keyup(function() {
		var transfertype=$("#tType_select").val();
		if(transfertype=='Transfer_In'|transfertype=='Transfer_Out')
		{
			var frmgross = $("#frm_Gross").val();
		 	var frmqty = $("#frm_qty").val();
		 	var frmpices = $("#frm_pices").val();
		 	var frmtogross=0;
		  	
			frmtogross=frmgross*frmqty;
			$("#frm_ToGross").val(frmtogross.toFixed(3));	 
	
		}
	 		});
	
$('[id^=To]').keyup(function() { 
	var transfertype=$("#tType_select").val();
	if(transfertype=='Transfer_In'|transfertype=='Transfer_Out')
		{
		var togross = $("#To_Gross").val();
	 	var toqty = $("#To_qty").val();
	 	var topices = $("#To_pices").val();
	 	var totalgross=0;	
		totalgross=togross*toqty;
	 	$("#To_totGross").val(totalgross.toFixed(3));
		}
	 		 
});
</script>
<script type="text/javascript">

$(document).ready(function() {
 	$(".Hide_totGrossWT").hide();
    $('#tType_select').change(changetType_select).trigger('change');
});

function changetType_select() {
	var defaultqty=0;
	var values = $('#tType_select').val();
    if(values == "Transfer_In"){
         $(".Hide_pcs").hide();
        }
    else if(values == "Transfer_Out"){
         $(".Hide_pcs").hide();
    }
    else if(values == "Internal"){
          $(".Hide_pcs").show();
    }
 
    return false;
}

</script>
<script type="text/javascript" src="jquery-latest.js"></script> 
</body>
</html>