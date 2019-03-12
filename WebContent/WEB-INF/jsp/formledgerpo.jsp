<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>

<div id="col-form">
	<div class="boxed" align="left">
	<div class="title">Sales Order Pending</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	<div class="summary">Pending Sales Order List</div>
		
	<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
	<thead class="row_head">
		<th bgcolor="#BBDDFF" >S.O. Id</th>		
		<th bgcolor="#BBDDFF" >Order.No</th>
		<th bgcolor="#BBDDFF" >S.O. Date</th>		
		<th bgcolor="#BBDDFF" >Customer Name</th>
		<th bgcolor="#BBDDFF" >Bullion Type</th>
		<th bgcolor="#BBDDFF" >Finished ItemName</th>
		<th bgcolor="#BBDDFF" >Item Gross Weight</th>
		<th bgcolor="#BBDDFF" >Item Net Weight</th>
		<th bgcolor="#BBDDFF" >Rate</th>
		<th bgcolor="#BBDDFF" >Advance</th>
		<th bgcolor="#BBDDFF" >Delivery Date</th>
	</thead>	
				
	<c:forEach var="Pendingorder" items="${Pendingorder}">
	<tr bgcolor="#FFFFFF">
		<td>${Pendingorder.salesOrderId}</td>   
		<td>${Pendingorder.salesOrderId}</td>
		<td><fmt:formatDate  value="${Pendingorder.orderDate}" pattern="yyyy-MMM-dd"/></td>
		<td>${Pendingorder.customerName}</td>
		<td>${Pendingorder.bullionType1}</td>
		<td>${Pendingorder.itemName1}</td>
		<td>${Pendingorder.grossWeight1}</td>
		<td>${Pendingorder.netWeight1}</td>
		<td>${Pendingorder.bullionRate1}</td>
		<td>${Pendingorder.advance}</td>
		<td><fmt:formatDate  value="${Pendingorder.salesDate}" pattern="yyyy-MMM-dd"/></td>
	</tr>
	</c:forEach>	
	</table>
	<a href="accounts.htm" ><b><u>Back</u></b></a>		
	</div> <!-- content -->
	</div>  <!-- boxed -->
</div> <!-- col-form -->
</html>