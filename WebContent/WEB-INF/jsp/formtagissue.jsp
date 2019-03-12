<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<div id="col-form" align="left">
<div class="boxed">
<div class="title">Stock Transfer</div>
<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>

<div class="content">

	

<form:form method="post" action="formtagissue.htm" commandName="tagissue"  class="formStyle">
<div id="form_error">
		<div><a class="close"><img src="images/closebox.png"></a></div><br>
		<span class="error"><form:errors path="*" id="errorDisplay"/></span>
		
	</div>
	<font color="red" size="3"><c:out value="${errorTag }"></c:out></font>
<table id="formTransfer"  cellpadding="0" border="0" cellspacing="0" align="center" class="TagIssue">
	
		<tr class="form_cat_head">
			<td colspan=12>Tag Issue</td>
		</tr>
		<tr>	
		<td bgcolor="#BBDDFF">Date*</td>
			<td><form:input size="10" path="transferDate" id="date" class="date"/></td>
			<td colspan="8"></td>
		</tr>
	</table>
		<table cellpadding="0" border="0" cellspacing="0" align="center" class="TagIssue">
		<tr>
			<td bgcolor="#BBDDFF">Tag No</td>
			<td></td>
			<td bgcolor="#BBDDFF">Category</td>
			<td bgcolor="#BBDDFF">Sub Category</td>
			<td bgcolor="#BBDDFF">Item Name</td>
			<td bgcolor="#BBDDFF">Metal Type</td>
			<td bgcolor="#BBDDFF">Weight</td>
			<td bgcolor="#BBDDFF">Qty/Set</td>
			<td bgcolor="#BBDDFF">Desc</td>			
		</tr>
	<c:forEach var="tag" items="${tagissuecode}">
	<input type="hidden" id="category1" value="${tag.categoryName}" />
	<input type="hidden" id="subcategory1"value="${tag.subCategoryName}"/>
	<input type="hidden" id="name"value="${tag.itemName}"/>
	<input type="hidden" id="metalType1"value="${tag.metalType}"/>
	<input type="hidden" id="grossWeight1"value="${tag.grossWeight}"/>		
	<input type="hidden" id="qty"value="${tag.qty}"/>
	<input type="hidden" id="hidden_transfertype" value="${tag.stockType}">		
			
</c:forEach>
	
	<tr>
			<td bgcolor="#BBDDFF"><form:input path="tagissue"  size="5" id="tag_code"/></td>
			<td><input name="issue" value=""  class="search"  type="submit" id="searchtagNo"/></td>
			<td bgcolor="#BBDDFF"><form:input path="category" size="14" id="category" readonly="true"/></td>			
			<td bgcolor="#BBDDFF"><form:input path="subcategory" size="12" id="subcategory" readonly="true"/></td>
			<td bgcolor="#BBDDFF"><form:input path="itemName" size="7"  id="item" readonly="true" onkeypress="return ValidateNumberKeyPress(this, event);"/></td>
			<td bgcolor="#BBDDFF"><form:input path="metaltype" size="3" id="metaltype" readonly="true" onkeypress="return isNumberKey(event)"/></td>
			<td bgcolor="#BBDDFF"><form:input path="itemweght" size="3" id="itemweght" readonly="true" onkeypress="return isNumberKey(event)"/></td>
			<td bgcolor="#BBDDFF"><form:input path="itemqtset" size="3" id="itemqtset" readonly="true"/></td>
			<td bgcolor="#BBDDFF"><form:input path="description" size="3" id="desc"/></td>
		</tr>
		<form:hidden path="transferType" id="trasnfertype"/>
			
		<tr>
			<td bgcolor="#33FFFF"  width="400" colspan=10 align="center">
		  	<input type="submit" name="save" value="Save" id="insert" onclick="return totalgrossweight();" class="button_style"/>
		
			<input type="submit" name="cancel" value="Cancel"class="button_style"></input>
				
			</td>
		</tr>
	</table>
	
	<table width="250">
	</table>
	
</form:form>
</div>
</div>
</div>
<script language="javascript" type="text/javascript" > 

	var newrecord="<%=request.getParameter("transfer.htm")%>";
	var update_record="<%=request.getParameter("transferId")%>";
	
	
	
	$(document).ready(function(){
			
		$("#date").datepicker({showOn: 'both', buttonImageOnly: true, buttonImage: 'images/icon_cal.png',dateFormat:'d/mm/yy'});	
		
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var prettyDate =myDate.getDate()+ '/' + month + '/' + myDate.getFullYear();
		if(document.getElementById('date').value=='')
		{
		$('#date').val(prettyDate);
			return true;
		}
		else
		{
		return false;
		}		
	});		
	
	$(document).ready(function(){
		$('#insert').click(init);
		$('#tag_code').focus();
		$('#searchtagNo').click(tagnovalidat);
		
	});
	
	function tagnovalidat()
	{
		var tagno=$('#tag_code').val();
		if(tagno=='IT100001'||tagno=='IT100002'||tagno=='IT100003'||tagno=='IT100004'||tagno=='IT100005'||tagno=='IT100006'||tagno=='IT100007'||tagno=='IT100008'||tagno=='IT100009'||tagno=='IT100010'||tagno=='IT100011'||tagno=='IT100012'||tagno=='it100001'||tagno=='it100002'||tagno=='it100003'||tagno=='it100004'||tagno=='it100005'||tagno=='it100006'||tagno=='it100007'||tagno=='it100008'||tagno=='it100009'||tagno=='it100010'||tagno=='it100011'||tagno=='it100012')
			{
			alert('Invalid Tagno.');
			return false;
			}
	}
	function init(){
		
		var ZERO = 0;
		if($("#itemweght").val().length == 0){
			$("#itemweght").val(ZERO.toFixed(3));
			$("#itemqtset").val(ZERO);
		}
		
		var category = $("#category").val();
		if(category  == "" || category == null){
			alert('Please Search the tag code!');
			return false;
			}
		var issueDate=$("#date").val();
		if(issueDate=="" || issueDate==null)
			{
			alert('Please enter the tag issue date!');
			return false;
			}
	
	}	

</script>
<script type="text/javascript">

var categoryH=$("#category1").val();
if(categoryH!=null){
$("#category").val(categoryH);
 }
var subcategory=$("#subcategory1").val();
if(subcategory!=null){
$("#subcategory").val(subcategory);
}
var weight=$("#grossWeight1").val();
if(weight!=null){
$("#itemweght").val(weight);
}
var metalType1=$("#metalType1").val();
if(metalType1!=null){
$("#metaltype").val(metalType1);
}
var qty=$("#qty").val();
if(qty!=null){
$("#itemqtset").val(qty);
}
var itemn=$("#name").val();
if(itemn!=null){
$("#item").val(itemn);
}
var trasfertype=$("#hidden_transfertype").val();
if(trasfertype!=null)
	{
	$("#trasnfertype").val(trasfertype);
	}
//end here 
</script>
<script type="text/javascript" src="jquery-latest.js"></script> 
</body>
</html>