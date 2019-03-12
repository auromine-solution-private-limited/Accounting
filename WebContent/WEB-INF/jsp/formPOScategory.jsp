<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div id="mask">
</div>   
     <div id="boxes">
     
    <!-- #customize your modal window here : CATEGORY POPUP 
    
      <div id="category" class="window dialog">
  <a href="#" class="close">X</a>
        <b class="Popup_heading">New Category Form</b>
         <div class="content">
         	<form:form name="form1" id="form1" modelAttribute="posCategory" method="POST" action="${addUrl}" class="formStyle">		
	<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>
		
	</div>
	<table id="formJournal" cellpadding="0" border="0" cellspacing="0" align="center" >
		<tr class="form_cat_head">
			<td colspan=4><B>Create Category</B></td>
		</tr>
		<tr>
			<td class="label">Category Name</td>
			<td><form:input path="categoryName" id="poscategoryName"/></td>
			<td class="label">Discount %</td>
			<td><form:input path="DiscountPercentage" onkeyup="extractNumber(this,2,true);" onkeypress = "return ValidateNumberKeyPress(this, event);"/></td>
		</tr>
		<tr>
			<td class="label">Vat %</td>
			<td><form:input path="vatPercentage" onkeyup="extractNumber(this,2,true);" onkeypress = "return ValidateNumberKeyPress(this, event);"/></td>
			<td class="label">Description</td>
			<td><form:input path="description" id="posdescription"/></td>
		</tr>
		
		<tr>
			<td colspan=4 align="center">
			<input type="submit" name="insert" value="Insert" id="insert" class="button_style"/>
			<input type="Reset" name="cancel" value= "Reset" class="button_style"></input>	
			</td>
		</tr>
	</table>
</form:form>
			
        </div> 
    </div>  --> 
 	
 	  <!-- #customize your modal window here : CATEGORY POPUP -->   
    
     <div id="category" class="window dialog">
    <a href="#" class="close">X</a>
        <b class="Popup_heading">POS Category Creation Form</b>
         <div class="content">
         	
			<table id="formJournal" cellpadding="0" border="0" cellspacing="0" align="center" >
			<tr class="form_cat_head">
				<td colspan=4><B>Create POS Category</B></td>
			</tr>
			<tr>
				<td class="label" >Category Name*</td>
				<td><input type="text" name="categoryName" id="categoryName" maxlength="30" class="categorytext noEnterSubmit nameCapitalize" value="" /></td>
				<td class="label">Discount %</td>
				<td><input type="text" name="DiscountPercentage" id="DiscountPercentage" class="categorytext noEnterSubmit" value="0.00" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
			</tr>
			<tr>
				<td class="label">Vat %</td>
				<td><input type="text" name="vatPercentage" id="vatPercentage" class="categorytext noEnterSubmit" value="0.00" onkeyup="extractNumber(this,2,true);" onkeypress="return ValidateNumberKeyPress(this, event);" /></td>
				<td class="label">Description</td>
				<td><input type="text" name="description" id="description" maxlength="250" class="categorytext noEnterSubmit" value="" /></td>
			</tr>
			<tr>
				<td colspan="4" id="POSPCatErrorMsg"></td>
			</tr>
			<tr>
				<td colspan=4 align="center">
				<input type="button" value="Save" name="saveCategory" id="saveCategory" class="button_style" />
				<input type="button" value="Cancel" class="button_style"></input>	
				</td>
			</tr>
			</table>
        </div> 
    </div> 
    </div>

<div id="col-form" align="left">
<div class="boxed">
<div class="title">POS Category</div>
<div class="content">
<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
<c:url var="addUrl" value="formPOScategory.htm" />
<a href="#category" id="catPopup" class="create_or_list" name="modal">Add New Category</a>
<div class="summary">Category List<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
		
	<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
		<thead class="row_head">
		<tr>
			<th>Id</th>
			<th>Category Name</th>
			<th>Vat Percentage</th>
			<th>Discount Percentage</th>
			<th>Total Quantity</th>				
			<th>Action</th>
		</tr>		
		</thead>
		
		<c:forEach var="posCategory" items="${categoryList}">	
				<tr bgcolor="#FFFFFF">
				<td>${posCategory.categoryId}</td>
				<td><a href="positemList.htm?Id=${posCategory.categoryId}" class="a_link" title='Click to View "${posCategory.categoryName}" Items'>${posCategory.categoryName}</a></td>	  
				<td>${posCategory.vatPercentage}</td>
				<td>${posCategory.discountPercentage}</td> 
				<td>${posCategory.totalQuantity}</td>						
				<td><a href="formPOSitems.htm?Id=${posCategory.categoryId}" class="edit add">Add Item</a></td>
		</tr>			
		</c:forEach>	
	</table>	
</div>
</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$("#categoryName").keypress(function(e) {
		var code = (e.keyCode ? e.keyCode : e.which);
		
		if (code == 44) {
			//alert('Comma cannot be accepted please use other character.');
			//$(this).val($find(code == 188).clear());
			return false;
		}
	});
	$("#saveCategory").click(submitCategory);
	$("#catPopup").click(clearCatPopup);
});

//clear Category pop up window 
function clearCatPopup(){
	 $("#categoryName").val("");
	 $("#DiscountPercentage").val("0.00");
	 $("#vatPercentage").val("0.00");
	 $("#description").val("");
	 $("#POSPCatErrorMsg").html("");
}

//*********** Category Creation POP Window ****************************//
function submitCategory() {
		
	var catName = $("#categoryName").val();
	var discper = $("#DiscountPercentage").val();
	var vatper = $("#vatPercentage").val();
	var desc = $("#description").val();
	
	if(discper == null || discper.length == 0){
		discper = 0.00;		
	}
	if(vatper == null || vatper.length == 0){
		vatper = "";
	}
	if(desc == null || desc.length == 0){
		desc = 0.00;
	}	
	
	if(catName == null || catName.length == 0){
		catName = "";
		$("#POSPCatErrorMsg").html('<span class="error"> * Catogory Name cannot empty </span>');
		return false;
	}else 
	{
		// Catogory existing check server side validation through ajax
		$.ajax({
			type:'GET',
			url:'POSPCatValidate.htm',
			data : {catName : catName},
			success : function(data) {
				if(data == "true"){
					$("#POSPCatErrorMsg").html('<span class="error"> * Catogory Name already exist.</span>');
					return false;
				}else{
					// Save POS Category pop up
					$.ajax({
						type:'POST',
						url:'POScategoryPOPUP.htm',
						data : {catName : catName, discper : discper, vatper : vatper, desc: desc},
						success : function(data){
							$('input.categorytext').val('');
							
							  $('#mask, .window').hide();
							  location.reload();
						},
						error : function(xmlHttpRequest, textStatus, errorThrown){
								if(xmlHttpRequest.readyState == 0 || xmlHttpRequest.status == 0)
									return;
						},	
						
					});// Save POS Category pop up ends here
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
</body>
</html>