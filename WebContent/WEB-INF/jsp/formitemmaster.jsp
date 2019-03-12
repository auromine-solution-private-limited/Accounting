<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html xmlns="http://www.w3.org/1999/xhtml">
 
<body>

<!---------------------------------------->
<div id="col-form" align="left">
	<div class="boxed">
	<div class="title" >Create Item Master</div>
	<div class="content">
	<!-- <div id= align="right">Tag as alot stock</div> -->
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<c:url var="saveUrl" value="formitemmaster.htm?categoryId=${categoryId}" />
	<form:form modelAttribute="itemmaster" method="POST" action="${saveUrl}" class="formStyle" id="form1"  onsubmit="return submit_form()">
	<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>
		
	</div>
	<table id="formItemMaster" cellpadding="0" border="0" cellspacing="0" align="center">
	<tr class="form_cat_head"><td colspan="4" id="lotitem">Item Information</td></tr>
	<!--<form:hidden path="itemCode"/>-->
		<td class="label">Stock Type*</td>
			<td><form:select path="stockType" id = "stock_type" class="noEnterSubmit lotitemstock">
		<!--<form:option value="" label="select"></form:option>-->	
			<form:option value="OpeningStock" label="OpeningStock"></form:option>
			<form:option value="PurchasedStock" label="PurchasedStock"></form:option>
			</form:select>
			</td>
			<td class="label">Name*</td><td><form:input path="itemName" id="input1" class="nameCapitalize noEnterSubmit" maxlength="25"/></td>
		</tr>
				

		<tr>
			<td class="label">Item Gross Weight*</td><td><form:input path="grossWeight" id="wt" onkeyup="extractNumber(this,3,true)" onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit cal_dep"/></td>
			<td class="label">Item Net Weight*</td><td><form:input path="netWeight" id="netweight" readonly="true" class="noEnterSubmit cal_dep"/></td>
		</tr>
			
	<!-- ---------------- -->
		<tr class="ppq">
		<td class="label">Pieces per Qty</td><td><form:input path="piecesPerQty" id="ppq"onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit cal_dep"/></td>
		<td class="label tppq">Total Pieces</td><td><form:input path="totalPieces" id="tp"onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit cal_dep" readonly="true"/></td>
		</tr>
		<tr class="itemQty">
		  <td class="label itemQty">Qty</td><td class="itemQty"><form:input path="qty" id="itemquantity" class="cal_dep"/></td>
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
			<td class="label">Sub Category Name*</td>
			<td>
			<form:input path="subCategoryName" id="categoryNames"  readonly="true" class="noEnterSubmit"/>
			</td>
			<td class="label">Category</td>
			<td>
			<form:input path="categoryName" id="subCategoryNames" readonly="true" class="noEnterSubmit"/>
			 	
			</td>	
		</tr>
			<tr class="cmpanyRow">
			<td class="label">VA Percentage</td>
			<td>
			<form:input path="vaPercentage" id="vaPercentages"  class="noEnterSubmit cal_dep"/>
  			</td>
			<td class="label MCGramRupees MCgram">M.C per Gram</td>
			<td class="MCGramRupees MCgram">
			<form:input path="mcPerGram" id="mcPerGrams" readonly="true" class="noEnterSubmit cal_dep"/>
  			</td>
  			
  			  <td class="label MCGramRupees MCrupees">MC In Rupees</td>
			<td class="MCGramRupees MCrupees">
			<form:input path="mcInRupee" id="mcInRupees"  readonly="true" class="noEnterSubmit cal_dep"/>
  			</td>
  		</tr>
				
		<tr class="cmpanyRow">
			<td class="label">Less percentage</td>
			<td>
			<form:input path="lessPercentage" id="lessPercentages" readonly="true"  onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit cal_dep"/>
			</td>
			<td class="label">VAT </td> 
			<td>
			<form:input path="tax"  id="taxs" readonly="true"  onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit cal_dep"/>
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
			<td class="label">Total Gross Weight </td>	<td><form:input path="totalGrossWeight" id="f1" readonly="true" class="noEnterSubmit cal_dep"/></td>
			<td class="label">Stone Description</td><td><form:input path="stone" maxlength="15" class="noEnterSubmit"/></td>
			
		</tr>
		<tr>
			<td class="label">Stone in Pieces</td><td><form:input path="stonePieces" id="spcs"onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit cal_dep"/></td>
			<td class="label">Stone Weight Grams.</td><td><form:input  path="stoneWeight" id="swt" onkeyup="extractNumber(this,3,true)" onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit cal_dep"/></td>
		</tr>
		<tr>
			<td class="label">Stone Rate </td>	<td><form:input path="stoneRatePerCaret" id="srpc" onkeyup="extractNumber(this,2,true)" onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit cal_dep"/></td>
			<td class="label">Stone Cost</td><td><form:input path="stoneCost" id="f2" readonly="true" onkeypress="return ValidateNumberKeyPress(this, event);" class="noEnterSubmit cal_dep"/></td>
		</tr>
		
		<tr class="form_cat_head">
			<td colspan="4">Other Details</td>
		</tr>
		<tr>
			<td class="label">Manufacturer Seal</td> <td><form:input path="manufacturerSeal"  maxlength="15" class="noEnterSubmit"/></td>
			<td class="label">Item Seal</td> 
			<td><form:select path="itemseal" class="noEnterSubmit">
			<form:option value="" label="select"/>
			<form:option value="916 HM" label="916 HM"/>
			<form:option value="916 KDM" label="916 KDM"/>
			<form:option value="NON KDM" label="NON KDM"/>
			</form:select>
			</td>
			</tr>
			<tr style="display:none">
				<td class="label"><label class="barcodelabel">Current Print Format</label></td>
		   		<td><form:input readonly="true" path="ornPrintFormat" value="${ornPrintFormat}" class="isPrint"/></td>
		   		<td class="label"></td>
		   		<td></td>
			</tr>
		<tr style="display:none">
		<td colspan="4">
		<input type="hidden" value="${categoryId}"  disabled="disabled"/>
		<input type="hidden" value="${stockType}" id="hiddenstockType"/>
		<input type="hidden" value="${errorType}" id="errorType"/>
		<form:hidden path="stockDate" id="stockDate" class ="cal_dep" />
		
		<c:forEach var="items" items="${categoryName}">
			<input id="itemCodes" type="hidden" name="itemCodes" value="${itemCode}" class="textfield" />
			<input type="hidden" id="bullionType" name="metalType" value="${items.metalUsed}" disabled="disabled"/>
			<input type="hidden" id="metalGroup" name="metalUsed" value="${items.metalType}" disabled="disabled"/>
			<input type="hidden" id="tax" name="vat" value="${items.vat}" disabled="disabled" class="cal_dep"/>
			<input type="hidden" id="lessPercentage" name="lessPercentage" value="${items.lessPercentage}" disabled="disabled" class="cal_dep"/>
			<input type="hidden" id="mcPerGram" name="mcPerGram" value="${items.mcPerGram}" disabled="disabled" class="cal_dep"/>
			<input type="hidden" id="mcInRupee" name="mcInRupees" value="${items.mcInRupees}" disabled="disabled" class="cal_dep"/>
			<input type="hidden" id="vaPercentage" name="vaPercentage" value="${items.vaPercentage}" disabled="disabled" class="cal_dep"/>
			<input type="hidden" id="categoryName" name="categoryName" value="${items.categoryName}" disabled="disabled" class="cal_dep"/>
			<input type="hidden" id="subCategoryName" name="baseCategory" value="${items.baseCategory}" disabled="disabled" class="cal_dep"/>
			<input type="hidden" id="itemHMChargesHidden" name="itemHMCharegesHidden" value="${items.categoryHMCharges}" disabled="disabled" class="cal_dep"/>
		</c:forEach>
		</td>
		</tr>
		<tr>
		    <td bgcolor="#33FFFF" colspan="4" align="center">
		    <input type="submit" name="insert" value="Insert" id="ItemInsert" class="button_style"/>
           	<input type="submit" name="print" value="SaveWithBarcode" id="saveBarcode" class="button_style">
           	<input type="submit" name="cancel" id="cancel" value="Cancel" class="button_style"/>
           	</td>
        </tr>    
	</table>	
	</form:form>
	</div>
	</div>
</div>


<script language="javascript" type="text/javascript" > 
	$(document).ready(function(){
		$("#stockDate").datepicker({dateFormat:'d/mm/yy'});
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate =myDate.getDate()+ '/'+month+ '/' + myDate.getFullYear();
		$("#stockDate").val(prettyDate);

		/****prevent defaulr submit when enter key is pressed****/
		$('.noEnterSubmit').keypress(function(e){
			if ( e.which == 13 ) e.preventDefault();
			});
		
		//var data = "Thali Necklace Earrings Nosepin Pendant Cufflinks Mangalsutra Broach/pin LadiesRing Drops Bangel Braslet Kada Kapu Mogappu Pendant ChainS Watch BlackBeads Jumki BabyEaring BabyBangel Babytops GentsBracelet GentsRing Ladiesbracelet BabyBracelet Coins PendantSet Neclaceset Chain Chain Bali ChainNecklaceSet Haar Villakku KunJUmChimiz Tahttu KamachiVilakku SandanaKinnam Thanda Schain Pista Khusboo Fancy Allindia Broddpayal MathuraPayal CastingMetti StoneMetti Keychain StKeyChain HipChain HpHipChain ".split(" ");
		//$("#input").autocomplete(data);		
		//var data1 ="Bombay Kerala Rajkot Coimbatore Nellore Antique Gudiyattamstoneitem ChennaiStoneitem Banglore Hydreabad Casting kudan polki ruby Diamond Mesh Fancy kudan-meena Dsigner Handmade Hollow Delhi trichy Madurai Salem-Legchain Agra-Legchain Legchain Hollow-rope Beads Bridal asmi Ddamas Nakshatra Pattram  itlian white-gold watch Mechine".split(" "); 

		//$("#input1").autocomplete(data1);	
		
//Item code		
		var itemcode=$("#itemCodes").val();
		if(itemcode!=null)
			{
			$("#itemCode").val(itemcode);
			}
		
//Metal Type
		var metal=$("#bullionType").val();
		if(metal!=null)
			{
			$("#bullionTypes").val(metal);	
			}
//Bullion Type
		var bullion=$("#metalGroup").val();
		if(bullion!=null)
			{
			$("#metalGroups").val(bullion);	
			}
//category Name
		var category=$("#categoryName").val();
		if(category!=null)
			{
			$("#categoryNames").val(category);	
			$("#input1").val(category);
			}
		if(bullion=="Gold Loose Stock"){
		if(category==$("#input1").val(category)){
			//alert("hi");
		}
		}
//sub category Name
		var subcategory=$("#subCategoryName").val();
		if(subcategory!=null)
			{
			$("#subCategoryNames").val(subcategory);	
			
			}
		
//v.a percentage 
		var va=$("#vaPercentage").val();
		if(va!=null)
			{
			$("#vaPercentages").val(va);	
			}
		
//m.c percentage 
		var mc=$("#mcPerGram").val();
		if(mc!=null)
			{
			$("#mcPerGrams").val(mc);	
			}
		
//less percentage 
		var less=$("#lessPercentage").val();
		if(less!=null)
			{
			$("#lessPercentages").val(less);	
			}
		
//vat percentage 
		var vat=$("#tax").val();
		if(vat!=null)
			{
			$("#taxs").val(vat);	
			}
	//HMC Charges 
		var itemHMChargesHidden=$("#itemHMChargesHidden").val();
		if(itemHMChargesHidden!=null)
			{
			$("#itemHMCharges").val(itemHMChargesHidden);	
			}
		
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
		
		//code end here 10/12/12
		
			//$("#ItemInsert").click(hideInsert);
			$("#stock_type").focus();
			//disbale dot for textbox
			$('#ppq').keyup(function() {
				$(this).val(function(index, oldVal) {
					return oldVal.replace(/[^\d-]/g, '');
				});
			});
		});		
	
	/* function hideInsert(){
		$("#ItemInsert").hide();		
	} */
	
	if(update_record != 'null')
		{
			$('[id^=Itemupdate]').attr('disabled', false);
			$('[id^=ItemInsert]').attr('disabled', true);
					
		}	
		else
		{
			$('[id^=Itemupdate]').attr('disabled', true);
			$('[id^=ItemInsert]').attr('disabled', false);
	
		}
	
</script>
<script type="text/javascript">
var form_submitted = false;

function submit_form ( )
{
  if ( form_submitted )
  {
    //alert ( "Your form has already been submitted. Please wait..." );
    return false;
  }
  else
  {
    form_submitted = true;
    return true;
  }
}


</script>
<script type="text/javascript" src="script/item_master.js"></script>	
</body>		
</html>