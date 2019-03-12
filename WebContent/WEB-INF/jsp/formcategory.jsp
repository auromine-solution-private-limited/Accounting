<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<body>
	
<div id="col-form" align="left">
	<div class="boxed">
	<div class="title">Create Sub Category</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<c:url var="saveUrl" value="formcategory.htm?bcat=${param.bcat}" />
	<form:form modelAttribute="category" method="POST" action="${saveUrl}" class="formStyle">
	<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>
		
	</div>
	<table id="formCategory" cellpadding="0" border="0" cellspacing="0" align="center" >
			<tr class="form_cat_head"><td colspan="4" id="cat_info" align="center"></td></tr>
			<tr>
			<td class="label">Sub-Category Name</td>
			<td><form:input path="categoryName" id="jewel_Name" maxlength="25" onfocus=" metalChange();" class="nameCapitalize noEnterSubmit"/></td>
			<td class="label">Metal Type</td>
			<td>
			<form:input path="metalType" id="metal_type" readonly="true" class="noEnterSubmit"/>
				</td>
			</tr>			
			
			<tr><td class="label">Base Metal Used</td><td>			 
			<form:input path="metalUsed" id="base_metal" readonly="true" class="noEnterSubmit"/></td>
			<td class="label">Base Category</td>
			<td>
			<form:input path="baseCategory" id="base_category" readonly="true" class="noEnterSubmit"/>
			</td></tr>	
			<tr>
			<!--<td  bgcolor="#BBDDFF" width="180">Category Type</td>
			<td><form:input path="categoryType" readonly="true"/></td> -->
			
			<td class="label">VAT Percentage</td>
			<td width="280"><form:input path="vat" id="vat"onkeypress="return ValidateNumberKeyPress(this, event);"onkeyup="extractNumber(this,2,true);" class="noEnterSubmit numeric"/></td> 			
			<td class="label">Scheme</td>
			<td width="280"><form:input path="scheme" class="noEnterSubmit"/></td>
			
			</tr>
			<tr>
			<td class="label">MC Type</td>
			<td ><span class="radioClass"><form:radiobutton path="mcTypes" label="MC Per Gram" value="MCInGram"id="MCPerGram" class="radio" /></span><span class="radioClass"><form:radiobutton path="mcTypes" label="MC In Rupees" class="radio" value="MCInRupees" id="MCInRupees"/></span></td>
			<td class="label MCTypeClass MCPerGram">MC Per Gram</td>
			<td width="280"class="MCTypeClass MCPerGram"><form:input path="mcPerGram" id="mcingrm" maxlength="6" onkeypress="return ValidateNumberKeyPress(this, event);"onkeyup="extractNumber(this,3,true);" class="noEnterSubmit numeric"/></td>
			<td class="label MCTypeClass MCInRupees">MC In Rupees</td>
			<td width="280"class="MCTypeClass MCInRupees"><form:input path="mcInRupees" id="mcinrup"maxlength="6" onkeypress="return ValidateNumberKeyPress(this, event);"onkeyup="extractNumber(this,2,true);" class="noEnterSubmit numeric"/></td>
			</tr>
			<tr>
			<td class="label">Less Percentage</td>
			<td width="280"><form:input path="lessPercentage" onkeypress="return ValidateNumberKeyPress(this, event);" onkeyup="extractNumber(this,2,true);" class="noEnterSubmit numeric"/></td>
			<td  class="label">Rol Quantity</td>
			<td width="280"><form:input path="rolquantity" onkeypress="return ValidateNumberKeyPress(this, event);"onkeyup="extractNumber(this,0,true);" class="noEnterSubmit numeric"/></td>
			</tr>
			<!-- Added new feild on 28-11-12 for metal pending  -->
			
			<tr><td  class="label">VA. Wastage Percentage</td>
			<td width="280"><form:input path="vaPercentage" onkeypress="return ValidateNumberKeyPress(this, event);"onkeyup="extractNumber(this,2,true);" class="noEnterSubmit numeric"/></td>
			
			<td class="label">Hall Mark Charges</td>
			<td><form:input path="categoryHMCharges" onkeypress="return ValidateNumberKeyPress(this, event);"onkeyup="extractNumber(this,2,true);" class="noEnterSubmit numeric"/></td>			
				</tr>
			<tr>
			<td class="label">Start Date</td>
			<td width="280"><form:input path="startDate" id="date" class="noEnterSubmit"/></td>
			<td class="label">End Date</td>
			<td width="280"><form:input path="endDate" id="date1" class="noEnterSubmit"/></td>
			
			</tr>
			<tr>
				<td class="label">Description</td>
				<td><form:input path="description" class="noEnterSubmit" maxlength="20"/></td>
				<td class="label"></td>
				<td></td>
			</tr>
						
			
			<form:input type="hidden" path="categoryId"/>
			<input type="hidden" id="errorName" value="${errorType}"/>
			<input type="hidden" id="subCatEdit" value="${baseCategoryName}">
			
			<tr>
		<td bgcolor="#33FFFF" colspan="4" align="center">
			<input type="submit" name="insert" value="Insert" id="insert" class="button_style"/>
			<input type="submit" name="update" value="Update" id="update" class="button_style"/>
          	<!--<input type="submit" name="delete" value="Delete" id="delete"/>-->
 
			<input type="submit" name="cancel" value= "cancel" class="button_style"></input>
			</td></tr>
			<c:forEach var="category" items="${bCat}">
			<input type="hidden" id="Hbullion" name="metalType" value="${category.metalType}" disabled="disabled"/>
			<input type="hidden" id="HjewelName" name="metalType" value="${category.jewelName}" disabled="disabled"/>
			<input type="hidden" id="HmetalUsed" name="metalType" value="${category.metalUsed}" disabled="disabled"/>
			</c:forEach>
			
		</table>
		</form:form>
		</div>
	</div>
</div>
<script language="javascript" type="text/javascript" > 

$(document).ready(function() {	
	
	$(".MCTypeClass").hide();
	
	
	$("#MCPerGram").attr('checked', true );
	$(".MCPerGram").show();
	
	var MCPerGram = $("#MCPerGram");
	var MCInRupees = $("#MCInRupees");
	MCInRupees.click(function (){

		$("#mcingrm").val("0.00");
		$(".MCTypeClass").css("display", "none");
		$(".MCInRupees").show();
	});
	MCPerGram.click(function (){

		$("#mcinrup").val("0.00");
		$(".MCTypeClass").css("display", "none");
		$(".MCPerGram").show();
	});
	
	
	var URIloc = $(location).attr('href');
	var uripath = URIloc.split('/')[4];
			
	var errorType=$("#errorName").val();
	
	
	if(uripath.indexOf("formcategory.htm") >= 0){
		$('#update').hide();
		$('#insert').show(); 
		$('#jewel_Name').show();
		$('#jewel_Name').focus();
		var newMetalType=$("#Hbullion").val();
		var newCatName=$("#HjewelName").val();
		
		if(newMetalType!=null)
			{
			$("#cat_info").append("Create New ").append(newMetalType).append("-->").append(newCatName);	
			}
		else
			{
			var exMetalType=$("#metal_type").val();
			var excategory=$("#base_category").val();
			$("#cat_info").append("Create New ").append(exMetalType).append("-->").append(excategory);
			}
		}
	if(uripath.indexOf("viewcategory.htm") >= 0){
		$('#insert').hide();
		$('#update').show();
		var subCatname=$("#subCatEdit").val();
		if(subCatname=="true")
			{
			$('#jewel_Name').attr("readonly",true);
			}
		else if(subCatname=="false")
			{
			
			$('#jewel_Name').removeAttr('readonly');
			}
		var mcinrupees=$("#mcinrup").val();
		var mcingrm=$("#mcingrm").val();
		if(mcingrm!="0.00"){
			$(".MCInRupees").hide();
			$("#MCPerGram").attr('checked', true );
			$(".MCPerGram").show();
		}
		if(mcinrupees!="0.00"){
			$(".MCPerGram").hide();
			$("#MCInRupees").attr('checked', true );
			$(".MCInRupees").show();
		}
		
			var exMetalType=$("#metal_type").val();
			var excategory=$("#base_category").val();
			$("#cat_info").append("Update").append(exMetalType).append("-->").append(excategory);
			
		}	
	

	if(errorType=="updateError"){
		$('#update').show();
		$('#insert').hide();
		//$('#jewel_Name').hide();
	}
	else if(errorType=="insertError"){
		
		$('#update').hide();
		$('#insert').show();
		$('#jewel_Name').show();
		}
	 
	/* $('#update').click(function()
			{
		var subCatname=$('#jewel_Name').val();
		if(subCatname=="")
			{
			alert("Sub-Category Name should not be empty:");
			return false;
			}
		$('#jewel_Name').show();	
		initNumeric();
	}); */
	//$("#insert").click(initNumeric);

	/****prevent defaulr submit when enter key is pressed****/
	$('.noEnterSubmit').keypress(function(e){
		if ( e.which == 13 ) e.preventDefault();
		});
});
	
	$(document).ready(function(){
		var data = "Thali Necklace Earrings Nosepin Pendant Cufflinks Mangalsutra Broach/pin LadiesRing Drops Bangel Braslet Kada Kapu Mogappu Pendant ChainS Watch BlackBeads Jumki BabyEaring BabyBangel Babytops GentsBracelet GentsRing Ladiesbracelet BabyBracelet Coins PendantSet Neclaceset Chain Chain Bali ChainNecklaceSet Haar Villakku KunJUmChimiz Tahttu KamachiVilakku SandanaKinnam Thanda Schain Pista Khusboo Fancy Allindia Broddpayal MathuraPayal CastingMetti StoneMetti Keychain StKeyChain HipChain HpHipChain ".split(" ");
		$("#jewel_Name").autocomplete(data);
		
		$("#date").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		$("#date1").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});
		
		//Metal used
		var metal=$("#Hbullion").val();
		if(metal!=null)
			{
			$("#metal_type").val(metal);	
			}
		//Metal type
		var metaltype=$("#HmetalUsed").val();
		if(metal!=null)
			{
			$("#base_metal").val(metaltype);	
			}
	//Base category name
	var basecategory=$("#HjewelName").val();
		if(metal!=null)
			{
			$("#base_category").val(basecategory);	
			}
			
});		
	
	$('#insert').click(categorydatecomparison);
	$('#update').click(categorydatecomparison);
	
	
	function categorydatecomparison() {
		var orderDate = $("#date").val();
		var salesDate = $("#date1").val();
		 
		  if (Date.parse(orderDate) > Date.parse(salesDate)) {
		      alert("Scheme End date should not be previous to the Start Date ");
		      return false;
		  }
		  else{
			 
		  }
		  
		  var subCatname=$('#jewel_Name').val();
			if(subCatname=="")
				{
				alert("Sub-Category Name should not be empty:");
				return false;
				}
			$('#jewel_Name').show();
			
		  initNumeric();
	}	
	
	if(update_record != 'null')
	{
		$('#metal_type').focus(function() {		
			if('#metal_type' != null || '#metal_type' == 'Gold Ornaments' || '#metal_type' == 'Silver Ornaments' )
			{
				alert('You Cannot modify the Data');
			}
		});
		
		$('#base_category').focus(function() {		
			if('#base_category' != null || '#base_category' == 'Gold Ornaments' || '#base_category' == 'Silver Ornaments' )
			{
				alert('You Cannot modify the Data');
			}
		});
	}
	
	$('#metal_type').change(function() {
	    var values = $('#metal_type').val();
	    if (values == 'Gold Ornaments') {
	        $('#base_metal').val('Gold');
	    }
		if (values == 'Silver Ornaments') {
	        $('#base_metal').val('Silver');
	    }
	    return false;
	});
	
	function initNumeric(){
		$('input.numeric').each(function(){
			var TempVal = $(this).val();
			if(TempVal.length == 0 || TempVal == '' || TempVal == null){
				$(this).val("0");			
			}
		});
				
	}

</script>
	
</body>		

</html>