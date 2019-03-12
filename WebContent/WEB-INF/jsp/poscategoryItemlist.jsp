<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<div id="col-form" align="left">
	<div class="boxed">
	<div class="title">POS ITEM LIST</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<a href="formPOSitems.htm?Id=${poscategory.categoryId}" class="create_or_list">New Item</a> <!-- ************************ Call to URI  formcustomer.jsp page-->
	<a style="float:right;" href="formPOScategory.htm" class="create_or_list">Return To Category List</a>
<br>
<div class="summary">Item List Of:< c:out value="${poscategory.categoryName}" /><br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
<thead class="row_head">
			
			
			<th>Item Name</th>
			<th>Date</th>
			<th>V.A %</th>
			<th>Discount</th>
			<th>Total Pieces</th>
			<th>Cost</th>		
			<th>Action</th>
			
					
	</thead>
	<c:forEach var="itemmaster" items="${poscategory.posItems}">
				<tr>				  
<td>${itemmaster.itemName}</td>
<td><fmt:formatDate  value="${itemmaster.stockDate}" pattern="yyyy-MMM-dd"/></td>
<td>${itemmaster.vatPercentage}</td>
<td>${itemmaster.discountPercentage}</td>
<td>${itemmaster.totalPieces}</td>
<td>${itemmaster.costRate}</td>
<td><a href="viewPOSitems.htm?posId=${itemmaster.itemId}&Id=${poscategory.categoryId}" class="edit">Edit</a></td>	
</tr>
</c:forEach>
</table>
</div>
</div>
</div>		 

</body>		

</html>