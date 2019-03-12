<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html xmlns="http://www.w3.org/1999/xhtml">
 
<body>

<!---------------------------------------->
<div id="col-form" align="left">
	<div class="boxed">
	<div class="title" >Opening stock</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<c:url var="saveUrl" value="/bullionexchangestock.htm?Id=${openingStockModel.itemId}&cId=${categoryId}" />
<form:form modelAttribute="openingStockModel" method="POST" action="${saveUrl}" class="formStyle">
	<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error" id="pos"><form:errors path="*" id="errorDisplay"/></span>
	</div><input type="hidden" id="errorName" value="${errorType}"/>	
	<table id="bullionexchangestock" cellpadding="0" border="0" cellspacing="0" align="center" >
	<tr class="form_cat_head"><td colspan="4" id="cat_info"></td></tr>
	 
			
			<tr>
			<td class="label">Gross Weight*</td>
			<td>
			<form:input path="op_GrossWeight" class="cal_dep" id="grossWeight" onkeyup="extractNumber(this,3,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/>
			 	
			</td>
			<td class="label">Net Weight*</td>
			<td>
			<form:input path="op_NetWeight" class="cal_dep" id="op_netWeight" readonly="true"/>
  			</td>
  		</tr>
				
		<tr>
			<td class="label">Total Gross Weight*</td>
			<td>
			<form:input path="op_TotalGrossWeight" class="cal_dep" id="totalGrossWeight" readonly="true"/>
			</td>
			
			<td class="label">Category Name*</td>
			<td>
			<form:input path="categoryName" id="categoryNames"  readonly="true"/>
			</td>
		</tr>
		<tr class="GS_bullion">	
  		
			<td class="label">VAT %</td><td><form:input path="tax" class="cal_dep" id="vat" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
			<td class="label"></td><td></td>
		</tr>
		<tr class="GS_exchange">	
  		
			<td class="label">Qty/Set*</td>
			<td>
			<form:input path="op_Quantity" class="cal_dep" id="qty" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
  			<td class="label">Total Pieces</td>
  			<td><form:input path="op_TotalPieces" class="cal_dep" id="tot_qty" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
		</tr>
		<tr>
			<td class="label">Date </td>
			<td><form:input path="stockDate" id="stockDate" readonly="true" class ="cal_dep" /></td>
			<td class="label"></td>
			<td></td>
		</tr>		
		<tr>
		    <td bgcolor="#33FFFF" colspan="4" align="center">		  
		    <input type="submit" name="update" value="Update" id="update" class="button_style"/>
         	<input type="submit" name="cancel" value="Cancel" class="button_style"/>         	
          	<form:input type="hidden" path="itemId" />
          	<form:input type="hidden" path="itemCode" /> 
          	<form:input type="hidden" path="itemName" />
          	<form:input type="hidden" path="subCategoryName" />
          	<form:input type="hidden" path="metalUsed" />
          	<form:input type="hidden" path="metalType" />
          	<form:input type="hidden" path="stockType" value="OpeningStock" />
          </tr>    
	</table>	
	</form:form>
	</div>
	</div>
</div>


<script language="javascript" type="text/javascript" > 
$(document).ready(function(){
	var data = "Thali Necklace Earrings Nosepin Pendant Cufflinks Mangalsutra Broach/pin LadiesRing Drops Bangel Braslet Kada Kapu Mogappu Pendant ChainS Watch BlackBeads Jumki BabyEaring BabyBangel Babytops GentsBracelet GentsRing Ladiesbracelet BabyBracelet Coins PendantSet Neclaceset Chain Chain Bali ChainNecklaceSet Haar Villakku KunJUmChimiz Tahttu KamachiVilakku SandanaKinnam Thanda Schain Pista Khusboo Fancy Allindia Broddpayal MathuraPayal CastingMetti StoneMetti Keychain StKeyChain HipChain HpHipChain ".split(" ");
		$("#input").autocomplete(data);
		var data1 ="Bombay Kerala Rajkot Coimbatore Nellore Antique Gudiyattamstoneitem ChennaiStoneitem Banglore Hydreabad Casting kudan polki ruby Diamond Mesh Fancy kudan-meena Dsigner Handmade Hollow Delhi trichy Madurai Salem-Legchain Agra-Legchain Legchain Hollow-rope Beads Bridal asmi Ddamas Nakshatra Pattram  itlian white-gold watch Mechine".split(" "); 
		$("#input1").autocomplete(data1);
				
		var gwt=$('#grossWeight').val();
		if(gwt==0.000)
			{
			$("#cat_info").append("Create New ").append($("#categoryNames").val());
			}
		else
			$("#cat_info").append("Update ").append($("#categoryNames").val());
		});		
	
	$("#stockDate").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});	
	var myDate = new Date();
	var month = myDate.getMonth() + 1;
	var prettyDate =myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
	$("#stockDate").val(prettyDate);
	
	
	$('#grossWeight').keyup(function() {
		 $('#op_netWeight').val(this.value);	
		 $('#totalGrossWeight').val(this.value);		 
	});
	
	/*var gwt=$('#grossWeight').val();
	if(gwt!="")
		{
		$('#grossWeight').attr("readonly",true);
		}
	if(gwt==0.0)
	{
	$('#grossWeight').removeAttr("readonly",false);
	}*/
	
	var categorygrossWeight = $("#grossWeight").val();
	  if(categorygrossWeight == 0.00){
		  $("#update").val("Save");
	  }
	  else{
		  $("#update").val("Update");
	  }
</script>
<script type="text/javascript" src="script/item_master.js"></script>	
<script type="text/javascript" src="script/formValidations.js"></script>
</body>		
</html>