<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>	
<script language="javascript" type="text/javascript" > 
	$(document).ready(function(){
		/*******Update category onclick function calling*********/
		$('.popupCategory').click(viewCategory);
		$(".list_table").advancedtable({searchField: "#listsearch", loadElement: "#loader", searchCaseSensitive: false, ascImage: "images/up.png", descImage: "images/down.png"});
		$("#search").show();
		$('.noEnterSubmit').keypress(function(e){
		if ( e.which == 13 ) e.preventDefault();
		});
		$("#popupCategoryForm").click(initForm);
		$("#insert").click(submitCategory);
		$("#update").click(updateSubmit);
		var ssssd = $("#CatMetalTyp").val();
		if(ssssd == "Silver"){
			$('.catTabclass').hide();
			$("#goldsiltab ul li a").css('background','#fff');
			$('#goldsiltab ul li a[href="#silvert"]').css('background','#eee');
			$("#silvert").show();
		}
		$('#metal_type').change(onChangeMetalType);
	});
	function initForm(){
		$('#metal_type').change(onChangeMetalType);
		$("#jewel_Name").val("");
		$("#metal_type").val(0);
		$("#base_metal").val("");
		$("#description").val("");
		$("#CatErrorMsg").html("");
		$("#jewel_Name").focus();
		$("#insert").show();
		$("#update").hide();
		
	}
	
	function onChangeMetalType()
	{
		var values = $('#metal_type').val();
	    if (values == 'Gold Ornaments') {
	        $('#base_metal').val('Gold');
	        $(".Popup_heading").text("New Gold Category Form");
	        $(".Popup_metaltype").text("New Gold Ornaments Creation");
	    }
		if (values == 'Silver Ornaments') {
	        $('#base_metal').val('Silver');
	        $(".Popup_heading").text("New Silver Category Form");
	        $(".Popup_metaltype").text("New Silver Ornaments Creation");
	    }
		if (values == 'Gold Loose Stock') {
	        $('#base_metal').val('Gold');
	        $(".Popup_heading").text("New Gold Loose Stock Category Form");
	        $(".Popup_metaltype").text("New Gold Loose Stock Creation");
	    }
		if (values == 'Silver Loose Stock') {
	        $('#base_metal').val('Silver');
	        $(".Popup_heading").text("New Silver Loose Stock Category Form");
	        $(".Popup_metaltype").text("New Silver Loose Stock Creation");
	    }
	}
	
	function updateSubmit()
	{
		var oldCategoryName=$("#oldCategoryName").val();
		var newCategoryName = $("#jewel_Name").val();
		var metal_type = $("#metal_type").val();
		var base_metal = $("#base_metal").val();
		var description = $("#description").val();
		
		
		 if(newCategoryName == null || newCategoryName.length == 0 || newCategoryName == "undefined"){
			newCategoryName = "";
			$("#CatErrorMsg").html('<span class="error"> * Base Category Name cannot empty </span>');
			return false;
			
		}else 
		{
			// Catogory existing check server side validation through ajax in itemmaster
			
			$.ajax({
				type:'GET',
				url:'ValidateCategoryName.htm',
				data : {newCategoryName : newCategoryName, oldCategoryName : oldCategoryName},
				success : function(data) {
						if(data != ""){
						$("#CatErrorMsg").html('<span class="error">'+data+'</span>');
						return false;
					} else{
						// update POS Category pop up
						$.ajax({
							type:'POST',
							url:'updateExistingCategory.htm',
							data : {metal_type : metal_type, base_metal : base_metal, description: description, newCategoryName : newCategoryName, oldCategoryName : oldCategoryName},
							success : function(data){
								$('input.categorytext').val('');
								alert("'"+oldCategoryName+"' "+"updated as "+"'"+newCategoryName+"'");
								$("#CatMetalTyp").val(base_metal);
								$('#mask, .window').hide();
									if(metal_type == "Gold Ornaments" || metal_type == "Gold Loose Stock" ){
										location.reload();
									}
									if(metal_type == "Silver Ornaments" || metal_type == "Silver Loose Stock"){
										window.location.href = 'listCategorySilver.htm';
									}
								},
							error : function(xmlHttpRequest, textStatus, errorThrown){
									if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
										return;
							},	
							
						});// update pop up ends here
					} 
					
				},
				error : function(xmlHttpRequest, textStatus, errorThrown){
						if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
							return;
				},	
				
			});	
		}
		
	}
/********view the existing category details***********/
	function viewCategory()
	{
		var Cat = $(this).parents("tr").find(".a_link").text();
		var metalType = $(this).parents("tr").find(".MtlType").text();
		var Typemetal= $(this).parents("tr").find(".Typemetal").val();
		var size = $(".edit").size();
		$("#oldCategoryName").val(Cat);
		$("#jewel_Name").val(Cat);
		
		 if(Typemetal == "Gold Ornaments"){
			$("#metal_type").val("Gold Ornaments");
			$(".Popup_heading").text("Update Gold Category Form");
	        $(".Popup_metaltype").text("Update Gold Ornaments Creation");
		}
		else if(Typemetal == "Silver Ornaments"){
			$("#metal_type").val("Silver Ornaments");
			$(".Popup_heading").text("Update Silver Category Form");
	        $(".Popup_metaltype").text("Update Silver Ornaments Creation");
		}
		else if(Typemetal == "Gold Loose Stock"){
			$("#metal_type").val("Gold Loose Stock");
			$(".Popup_heading").text("Update Gold Loose Stock Category Form");
	        $(".Popup_metaltype").text("Update Gold Loose Stock Creation");
		}
		else if(Typemetal == "Silver Loose Stock"){
			$("#metal_type").val("Silver Loose Stock");
			$(".Popup_heading").text("Update Silver Loose Stock Category Form");
	        $(".Popup_metaltype").text("Update Silver Loose Stock Creation");
		}
		$("#base_metal").val(metalType);
		$("#insert").hide();
		$("#update").show();
		$("#CatErrorMsg").html("");
		
		}
		
//*********** Category Creation POP Window *****************//
	function submitCategory(){
		var jewel_Name = $("#jewel_Name").val();
		var metal_type = $("#metal_type").val();
		var base_metal = $("#base_metal").val();
		var description = $("#description").val();
		
		if(base_metal == null || base_metal.length == 0 || base_metal == "undefined"){
			base_metal = "";		
		}
		if(description == null || description.length == 0 || description == "undefined"){
			description = "";		
		}
		
		if(jewel_Name == null || jewel_Name.length == 0 || jewel_Name == "undefined"){
			jewel_Name = "";
			$("#CatErrorMsg").html('<span class="error"> * Base Category Name cannot empty </span>');
			return false;
		}
		if(metal_type == null || metal_type.length == 0 || metal_type == "undefined" || metal_type ==  0){
			$("#CatErrorMsg").html('<span class="error"> * Please Select Metal Type </span>');
			return false;
		}else 
		{
			// Catogory existing check server side validation through ajax
			$.ajax({
				type:'GET',
				url:'CatValidate.htm',
				data : {jewel_Name : jewel_Name},
				success : function(data) {
					if(data == "true"){
						$("#CatErrorMsg").html('<span class="error"> * Base Catogory Name already exist.</span>');
						return false;
					}else{
						// Save POS Category pop up
						$.ajax({
							type:'POST',
							url:'submitJewelType.htm',
							data : {jewel_Name : jewel_Name, metal_type : metal_type, base_metal : base_metal, description: description},
							success : function(data){
								$('input.categorytext').val('');
								alert("'"+jewel_Name+"' "+"Added To Base Category");
								$("#CatMetalTyp").val(base_metal);
								$('#mask, .window').hide();

								if(metal_type == "Gold Ornaments" || metal_type =="Gold Loose Stock"){
									location.reload();
								}
								if(metal_type == "Silver Ornaments" || metal_type =="Silver Loose Stock"){
									window.location.href = 'listCategorySilver.htm';
								}
								
							},
							error : function(xmlHttpRequest, textStatus, errorThrown){
									if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
										return;
							},	
							
						});// SaveCategory pop up ends here
					}
					
				},
				error : function(xmlHttpRequest, textStatus, errorThrown){
						if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
							return;
				},	
				
			});	
		}
		
	}
</script>
<div id="mask">
</div>   
    
    <!-- #customize your modal window here : CATEGORY POPUP -->
    
    <div id="boxes">   
     <div id="category" class="window dialog">
    <a href="#" class="close">X</a>
        <b class="Popup_heading">New Category Form</b>
         <div class="content">
         	<table id="formJeweltype" cellpadding="0" border="0" cellspacing="0" align="center" >
			<tr class="form_cat_head">
				<td colspan="4" class="Popup_metaltype">Create Base Category</td>
			</tr>
			<tr>
				<td bgcolor="#BBDDFF" width="180">Ornament type  *</td>
				<td>
				<select name="metalType" id="metal_type" class="noEnterSubmit type">
						<option value="0" label="select">Select</option>
						<option value="Gold Ornaments" label="Gold Ornaments">Gold Ornaments</option>
						<option value="Silver Ornaments" label="Silver Ornaments">Silver Ornaments</option>
						<option value="Gold Loose Stock" label="Gold Loose Stock">Gold Loose Stock</option>
						<option value="Silver Loose Stock" label="Silver Loose Stock">Silver Loose Stock</option>
					</select>
				
				</td>
				<td  bgcolor="#BBDDFF" width="180">Base Category Name *</td>
				<td>
					<input name="jewelName" id="jewel_Name" maxlength="20" class="nameCapitalize noEnterSubmit"/>
				</td>
			</tr>
			<tr>
			<td  bgcolor="#BBDDFF" width="180">Base Metal Used</td>
			<td>			
			<input name="metalUsed" id="base_metal" readonly="readonly" class="noEnterSubmit"/>
			</td>
			<!--<tr><td bgcolor="#BBDDFF" width="180">Total Gross Weight</td><td width="280"><input name="totalGrossWeight" onkeypress="return ValidateNumberKeyPress(this, event);" /></td></tr>-->
			<td  bgcolor="#BBDDFF" width="180">Description</td>
			<td><input name="description" id="description" class="noEnterSubmit"/></td>
			</tr>	
			<tr>
				<td colspan="4" id="CatErrorMsg"></td>
			</tr>		
			<tr style="display:none;"><td colspan=2>&nbsp;
				<input type="hidden" name="jewelTypeId"/>
				<input type="hidden" id="errorName" value="${errorType}" />
				<input type="hidden" id="oldCategoryName">	
			</td>
			</tr>
			<tr>
				<td bgcolor="#33FFFF" colspan=4 align="center">
				<input type="submit" name="insert" value="Insert" id="insert" class="button_style"/>
				<input type="submit" name="update" value="Update" id="update" class="button_style"/>
				<input type="reset" name="cancel" value= "Cancel" class="button_style" />
				</td>
			</tr>
		</table>
        </div> 
    </div>        
</div>


<div id="col-form" align="left">
	<div class="boxed">
	<div class="title">Create Base Category</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	<input type="hidden" id="CatMetalTyp" />
	<!-- #CatMetalTyp is for category the redirection to the list has to be to the corresponding tab of Gold or Silver Ornaments -->
	<a name="modal" href="#category"  id="popupCategoryForm" class="create_or_list">Add New Category</a>
	<a style="padding-right:8px; float:right;" href="category.htm" class="create_or_list">Metal Summary List</a>
	<br>
		<div class="summary">Category List<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
		<div id="goldsiltab">
			<ul>
				<li><a class="TabClass activetabs" href="formjeweltype.htm">Gold</a></li>
				<li><a class="TabClass" href="listCategorySilver.htm">Silver</a></li>
			</ul>
			<div id="goldt" class="catTabclass" style="width:763px;  padding-bottom:10px;">
				<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
						
				<thead class="row_head">
				<tr>
					<th>S.No</th>
					<th>Jewel Name</th>
					<th>Gold</th>
					<th>Opening Gross Wt</th>
					<th></th>
					<th></th>
					</tr>
				</thead>				
			<c:forEach var="jeweltype" items="${jewelTypeListgold}">
			
			<c:if test='${jeweltype.jewelTypeId > 10}'>
			<tr>
			<!-- <td>${jeweltype.jewelTypeId}</td> -->
			<td class="s_no"></td>
			<td><a href="categoryList.htm?bcat=${jeweltype.jewelName}" class="a_link" title="Click to view '${jeweltype.jewelName}' ">${jeweltype.jewelName}</a></td>	  
			<td class="MtlType">${jeweltype.metalUsed}</td>
			<td style="display: none;"><input type="text" class="Typemetal" value="${jeweltype.metalType}" /></td>
			<td>${jeweltype.totalGrossWeight}</td>
			<td><a href="formcategory.htm?bcat=${jeweltype.jewelName}" class="add_new" title="${jeweltype.metalType}">Add New Sub Category</a><a name="modal" href="#category" class="popupCategory edit">Edit Category</a></td>
			</tr>
			</c:if>
			</c:forEach>	
			</table>
		</div>
		</div>	
	</div>
	</div>
</div> 
</body>
		
</html>