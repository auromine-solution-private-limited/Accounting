<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<script type="text/javascript" src="script/jquery-all-manuscript.js"></script>	
<div id="col-form">
	<div class="boxed" align="left">
	<div class="title">Sub Category List</div>
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<div class="content">
	<a href="formjeweltype.htm" class="create_or_list">Create New Sub-Category</a> <!-- ************************ Call to URI  formcustomer.jsp page-->
<br>
<div class="summary">Sub-Category List</div>	
	
	<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
	<thead class="row_head">
			
			<th>CategoryName</th>
			<th>Base Metal Used</th>
			<th>Category Type</th>
			<th>Opening Gross Wt</th>		
			<th>Action</th>
			
	</thead>		
						
	<c:forEach var="category" items="${categoryList}">
	
	<c:if test='${category.categoryType == "Subcategory"}'> 
   	<tr bgcolor="#FFFFFF">
         	  
		<td><a href="categoryitemmaster.htm?categoryId=${category.categoryId}">${category.categoryName}</a> </td>
		<td>${category.metalUsed}</td>
		<td>${category.categoryType}</td>
		<td>${category.totalGrossWeight}</td>
		<td><a href="viewcategory.htm?categoryId=${category.categoryId}" class="edit" >Edit</a> </td>
	 </tr>
 	</c:if>  
 	</c:forEach>	
	</table>	
	</div>	
	</div>
</div>
</html>