<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<script type="text/javascript" src="script/jquery-all-manuscript.js"></script>	
<div id="col-form">
	<div class="boxed" align="left">
	<div class="title">Sub Category List</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	
<br>
<b><a href="formcategory.htm?bcat=${param.bcat}" class="create_or_list">Create new Sub Category under "${param.bcat}"</a></b>
<a style="float:right;" href="formjeweltype.htm" class="create_or_list">Base Category List</a>
<div class="summary">Sub-Category List<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>	
	
	<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
	<thead class="row_head">
			<th>S.No</th>
			<th>CategoryName</th>
			<th>Base Metal Used</th>
			<th>Category Type</th>
			<th>Opening Gross Wt</th>		
			<th>Action</th>
			
	</thead>		
						
	<c:forEach var="category" items="${categoryList}">
	
	<c:if test='${category.categoryType == "Subcategory"}'> 
   	<tr bgcolor="#FFFFFF">
         <td class="s_no"></td>  
       	<td><a href="categoryitemmaster.htm?categoryId=${category.categoryId}" class="a_link" title='Click to View "${category.categoryName}" Items'>${category.categoryName}</a> </td>
		<td>${category.metalUsed}</td>
		<td>${category.categoryType}</td>
		<td>${category.totalGrossWeight}</td>
		<td style="display: none;"><input type="text" class="goldLosestock" value="${category.metalType}"></td>
		<td style="display: none;"><input type="text" class="grosswgt" value="${category.totalGrossWeight}"></td>
		<td><a href="formitemmaster.htm?categoryId=${category.categoryId}" class="add_new" title="${category.totalGrossWeight}">Add</a> | <a href="viewcategory.htm?categoryId=${category.categoryId}" class="edit" >Edit</a> </td>
	 </tr>
 	</c:if>  
 	</c:forEach>
	</table>	
	</div>	
	</div>
</div>
<script language="javascript" type="text/javascript" > 
	$(document).ready(function(){
		tohide();
	});
	function tohide(){
		var metaltype=$(".goldLosestock").val();
		var size = $(".add_new").size();
		if(metaltype == "Gold Loose Stock" || metaltype == "Silver Loose Stock"){
			$(".add_new").each(function(){
				var thsTitle = $(this).attr("title");
				if(thsTitle == "0.000"){
					$(this).addClass("add_new");
				}
				else{
					$(this).removeClass("add_new");
					$(this).addClass("add_disabled");
					$(this).removeAttr("href");
				}
			});
			
			/**if(grosswgt == 0.000){
		var ss = $(".s_no").text();
			for(var j=1;j<size;j++){
     			alert(ss + j);
         		} */
			
     			
     		
			//$(".add_new").not($(this)).parents("tr").children("td.s_no").children("a.add_new").hide();
			//var sNoText = 
			//alert(sNoText);
			//}
		}
	}
	</script>	
</html>