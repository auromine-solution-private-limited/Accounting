<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
 
<body>

<!---------------------------------------->
<div id="col-form" align="left">
	<div class="boxed">
	<div class="title" >Create Item Master</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<c:url var="saveUrl" value="/viewitemmaster.htm?Id=${itemModel.itemId}&cId=${categoryId}" />
<form:form modelAttribute="itemModel" method="POST" action="${saveUrl}" class="formStyle" id="form1">
	<!--<form:errors path="*" />-->
	<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>
		
	</div>
	<table id="formItemMaster" cellpadding="0" border="0" cellspacing="0" align="center">
	<tr class="form_cat_head"><td colspan="4">Item Information</td></tr>
	
		<tr>
		
		<td class="label">Stock Type*</td>
			<td><form:input path="stockType" readonly="true" class="noEnterSubmit"/></td>
			<td class="label">Name*</td><td><form:input path="itemName" id="input1" class="nameCapitalize noEnterSubmit" maxlength="25"/></td>
		</tr>	
		
       <tr class="opGrossWeight">
			<td class="label">Item Gross Weight*</td><td><form:input path="op_GrossWeight" id="opGrossweight" onkeyup="extractNumber(this,3,true)" onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit cal_dep"/></td>
			<td class="label">Item Net Weight*</td><td><form:input path="op_NetWeight" id="op_NetWeight" readonly="true" class="noEnterSubmit cal_dep"/></td>
		</tr>
		
		<tr class="clGrossWeight">
			<td class="label">Item Gross Weight*</td><td><form:input path="grossWeight" id="wt" onkeyup="extractNumber(this,3,true)" onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit"/></td>
			<td class="label">Item Net Weight*</td><td><form:input path="netWeight" id="netweight"readonly="true" class="noEnterSubmit"/></td>
		</tr>
	
		
		
	<!-- ---------------- -->
		<tr class="ppq">			
			<td class="label">Pieces per Qty</td><td><form:input path="piecesPerQty" id="ppq" onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit"/></td>	
			<td class="label">Total Pieces</td><td><form:input path="totalPieces" id="tp"onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit" readonly="true"/></td>
		</tr>
		
		<tr class="opQty">
		  <td class="label itemQty">Qty</td><td class="opQty"><form:input path="op_Quantity" id="op_Quantity" class="cal_dep"/></td>
		  <td>
		</td>
		<td>
		</td>
		</tr>
		
		<tr class="clQty itemQty">
			<td class="label itemQty">Qty</td><td><form:input path="qty" id="itemquantity"  onkeyup="sum();" onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit itemQty"/></td>
			<td>
			</td>
			<td>
			</td>
		</tr>
		<tr class="form_cat_head" id="cmpanyHide">
			<td colspan="4">Category and Sub Category Information<span class="formexpand" style="float: right;"></span></td>
		</tr>
		
		<tr class="cmpanyRow">
			<td class="label">Metal Type*</td>
			<td>
			<form:input path="metalType" id="bullionTypes" readonly="true" class="noEnterSubmit"/>
	        
  			</td>
			<td class="label">Metal Group</td>
			<td>
			<form:input path="metalUsed"  id="metalGroups" readonly="true" class="noEnterSubmit"/>
				
		</tr>  
			
			<tr class="cmpanyRow">
			<td class="label">Category Name*</td>
			<td>
			<form:input path="categoryName" id="categoryNames"  readonly="true" class="noEnterSubmit"/>
			</td>
			<td class="label">Sub Category Name</td>
			<td>
			<form:input path="subCategoryName" id="subCategoryNames" readonly="true" class="noEnterSubmit"/>
			 	
			</td>	
		</tr>
		<tr class="cmpanyRow">
			<td class="label">V.A Percentage</td>
			<td>
			<form:input path="vaPercentage" id="vaPercentages"  class="noEnterSubmit"/>
  			</td>
			<td class="label MCGramRupees MCgram">M.C per Gram</td>
			<td class="MCGramRupees MCgram">
			<form:input path="mcPerGram" id="mcPerGrams" readonly="true" class="noEnterSubmit"/>
  			</td>
  			 <td class="label MCGramRupees MCrupees">M.C In Rupees</td>
			<td class="MCGramRupees MCrupees">
			<form:input path="mcInRupee" id="mcInRupees"  readonly="true" class="noEnterSubmit"/>
  			</td>
  		</tr>
				
		<tr class="cmpanyRow">
			<td class="label">Less percentage</td>
			<td>
			<form:input path="lessPercentage" id="lessPercentages" readonly="true"  onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit"/>
			</td>
			<td class="label">VAT </td> 
			<td>
			<form:input path="tax"  id="taxs" readonly="true"  onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit"/>
			</td>	
  		</tr>
  		
  		<tr class="cmpanyRow">
			<td class="label">Hall Mark Charges</td>
			<td>
				<form:input path="itemHMCharges" id="itemHMCharges" readonly="true"  onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit cal_dep"/>
			</td>
			<td class="label"></td> 
			<td></td>	
  		</tr>
		
		<tr class="form_cat_head">
			<td colspan="4">Stone Information</td>
		</tr>
		<tr>
			<td class="label">Total Gross Weight</td>	<td><form:input path="totalGrossWeight" id="f1" readonly="true" class="noEnterSubmit"/></td>
			<td class="label">Stone Description</td><td><form:input path="stone" maxlength="15" class="noEnterSubmit"/></td>
		</tr>
		<tr>
			<td class="label">Stone in Pieces</td><td><form:input path="stonePieces" id="spcs"onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit"/></td>
			<td class="label">Stone Weight Grams.</td><td><form:input  path="stoneWeight" id="swt" onkeyup="extractNumber(this,3,true)" onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit"/></td>
		</tr>
		<tr>
			<td class="label">Stone Rate </td>	<td><form:input path="stoneRatePerCaret" id="srpc" onkeyup="extractNumber(this,2,true)" onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit"/></td>
			<td class="label">Stone Cost</td><td><form:input path="stoneCost" id="f2" readonly="true" onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit"/></td>
		</tr>
		
		<tr class="form_cat_head">
			<td colspan="4">Other Details</td>
		</tr>
		<tr>
			<td class="label">Manufacturer Seal</td> <td><form:input path="manufacturerSeal"  maxlength="12" class="noEnterSubmit"/></td>
			<td class="label">Item Seal</td> 
			<td><form:select path="itemseal"  cssStyle="width:150px; height:22px" class="noEnterSubmit">
			<form:option value="" label="select"/>
			<form:option value="916 HM" label="916 HM"/>
			<form:option value="916 KDM" label="916 KDM"/>
			<form:option value="NON KDM" label="NON KDM"/>
			</form:select>
			</td>
			</tr>
			<%-- <tr>
			<td class="label">Printer Type</td>
			<td>
			<form:select path="printerType">
			<form:option value="select" label="---select---"></form:option>
			<form:option value="Zebra" label="Zebra"></form:option>
			<form:option value="TSC" label="TSC"></form:option>
			<form:option value="Citizen" label="Citizen"></form:option>
			</form:select>
			</td>
			</tr> --%>
			<tr>
		    <td bgcolor="#33FFFF" colspan="4" align="center">
		    <input type="submit" name="update" value="Update" id="Itemupdate" class="button_style"/>
          	<input type="submit" name="print" value="UpdateWithBarcode" id="UpdateWithBarcode" class="button_style">
          	<input type="submit" name="cancel" id="cancel" value="Cancel" class="button_style"/>
          	<form:input type="hidden"path="itemId"/>
          	<td style="display:none;"><form:hidden path="itemCode" readonly="true"/></td>
          <td style="display:none;">	
           <form:hidden path="stockDate" id="stockDate"/>
        </tr>    
	</table>	
	</form:form>
	</div>
	</div>
</div>


<script language="javascript" type="text/javascript" > 

	$(document).ready(function(){
		var totpieces=$("#tp").val();
		if(totpieces==0)
			{
			$("#wt").attr("readonly","readonly");
			}
		/****prevent defaulr submit when enter key is pressed****/
		$('.noEnterSubmit').keypress(function(e){
			if ( e.which == 13 ) e.preventDefault();
			});
		
		//disbale dot for textbox
		$('#ppq').keyup(function() {
			$(this).val(function(index, oldVal) {
				return oldVal.replace(/[^\d-]/g, '');
			});
		});
		
		//For MC grams and rupees 10/12/12 
		$(".MCGramRupees").hide();
		var MCgrams = $("#mcPerGrams").val();
		var MCrupees = $("#mcInRupees").val();
		
		if(MCgrams != "0.00"){
			$(".MCgram").show();
			
		}
		else if(mcrupee != "0.00"){
		
			$(".MCrupees").show();
		}
		var mcrupee=$("#mcInRupee").val();
		if(mcrupee!=null ){
			$("#mcInRupees").val(mcrupee);
		}
		
		});
		$("#form1").submit(function() {
		    $(this).submit(function() {
		        return false;
		    });
		    
	    return true;
	    
		});
</script>
<script type="text/javascript" src="script/item_master.js"></script>	
</body>		

</html>