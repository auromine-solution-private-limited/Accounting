<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<div id="col-form" align="left">
	<div class="boxed">
	<div class="title" align="left">POS Low  Balance</div>
	<div class="content" style="height:518px; width:748px; overflow-x:auto ; overflow-y: auto; padding-bottom:10px;">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	<div class="summary">POS Item Low Stock List</div>
	<!----------------------------------------------------->
    <table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
<thead class="row_head">
			<th bgcolor="#BBDDFF" >Item Name</th>
			<th bgcolor="#BBDDFF" >Category Name</th>
			<th bgcolor="#BBDDFF" >Company Name</th>
			<th bgcolor="#BBDDFF" >Rol Quantity</th>
			<th bgcolor="#BBDDFF" >Available Qty</th>	
	</thead>	
				
	<c:forEach var="lowstock" items="${posStocklist}">
		<tr bgcolor="#FFFFFF">
			<td>${lowstock.itemName}</td>
			<td>${lowstock.categoryName}</td>
			<td>${lowstock.companyName}</td>
			<td>${lowstock.ROL}</td>
			<td>${lowstock.sumqty}</td>
		</tr>
	</c:forEach>
	</table>
	<a href="homepage.htm" ><b><u>Back</u></b></a>	
	</div> <!-- content -->
	</div>  <!-- boxed -->
</div> <!-- col-form -->

</html>