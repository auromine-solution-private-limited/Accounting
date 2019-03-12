<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	
<div id="col-form" align="left">
	<div class="boxed">
	<div class="title">Item Search List</div>	
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
		<div class="summary">Item Search List<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
		<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
<thead class="row_head">
			
			<th bgcolor="#BBDDFF" >Id</th>
			<th bgcolor="#BBDDFF" >Item Code</th>
			<th bgcolor="#BBDDFF" >Item Name</th>
			<th bgcolor="#BBDDFF" >Category Name</th>
			<th bgcolor="#BBDDFF" >Sub Category Name</th>
			<th bgcolor="#BBDDFF" >Bullion Type</th>
			<th bgcolor="#BBDDFF" >Gross Weight</th>
			<th bgcolor="#BBDDFF" >Net Weight</th> 
			<th bgcolor="#BBDDFF" >VA Percentage</th>
			<th bgcolor="#BBDDFF" >M.C. per Gram</th>
			<th bgcolor="#BBDDFF" >Number Of Sets</th>
			<th bgcolor="#BBDDFF" >Total Gross Wt</th>
			<th bgcolor="#BBDDFF" >Action</th>
				
		</thead>		
		<c:if test="${empty itemMasterList }">
		<b><font color="red">***No Such Item Exits</font></b></c:if>			
			<c:forEach var="itemmaster" items="${itemMasterList}">
			
				<tr bgcolor="#FFFFFF">				  
					
				   <td>${itemmaster.itemId}</td>	
				   <td>${itemmaster.itemCode}</td>
				   <td>${itemmaster.itemName}</td>	
				   <td>${itemmaster.categoryName}</td>
				   <td>${itemmaster.subCategoryName}</td>
				   <td>${itemmaster.metalUsed}</td>
				   <td>${itemmaster.grossWeight}</td>
				   <td>${itemmaster.netWeight}</td>
				   <td>${itemmaster.vaPercentage}</td>
				   <td>${itemmaster.mcPerGram}</td>
				   <td>${itemmaster.qty}</td>
				   <td>${itemmaster.totalGrossWeight}</td>
				   <c:forEach var="cat" items="${category}">
 			       <td><a href="viewitemmaster.htm?Id=${itemmaster.itemId}&cId=${cat.categoryId}" class="edit">Edit</a></td> 
 			       </c:forEach>
				</tr>
			</c:forEach>	
		
		</table>
		</div>
	</div>
</div>		
</html>