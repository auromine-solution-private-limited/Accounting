<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<body>
<div id="col-form" align="left">
	<div class="boxed">
	<div class="title">Metal Classification</div>
	<div class="content">	
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<a href="formjeweltype.htm" class="create_or_list">Category List</a>
	<br>
<div class="summary">Metal Group Summary</div>
	<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table2" id="category_link">
	<thead class="row_head">
			
			<th>Metal Id</th>
			<th>Metal Group Name</th>
			<th></th>
			<th>Base Metal Used</th>
		<!--<th>Category Type</th>-->	
			<th>Opening Gross Wt.</th>		
	</thead>					
	<c:forEach var="category" items="${categoryList}">
	<c:if test='${category.categoryType == "Master"}'>
  	<tr bgcolor="#FFFFFF" class="metalIdnum">
        <td title="${category.categoryId}">${category.categoryId}</td>	  
		<td>${category.categoryName}</td>
		<td></td>
		<td>${category.metalUsed}</td>
		<!--<td>${category.categoryType}</td>-->
		<td>${category.totalGrossWeight}</td>

	</tr>
    </c:if>
    </c:forEach>
    		</table>	
    		
	</div>	
	</div>
</div>

</html>
