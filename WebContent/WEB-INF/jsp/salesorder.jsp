<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	
<div id="col-form" align="left">
	<div class="boxed">
	<div class="title">Sales Order List</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<a href="joborder.htm"  class="create_or_list">Job Order List</a> 
	<a href="newSalesOrder.htm" class="create_or_list">New Sales Order</a>
	<a href="jewelfix.htm" class="create_or_list">Jewel Repair List</a>
	<br>
<div class="summary">Sales Order List<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
	
	<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
	
			
	<thead class="row_head">
		<th>Id</th>
		<th>Order No</th>
		<th>S.O. Date</th>		
		<th>Customer Name</th>
		<th>SalesMan Name</th>
		<th>Bullion Type</th>		
		<th>Rate</th>
		<th>Advance</th>
		<th>Total Amount</th>
		<th>Delivery Date</th>
		<th>Order Status</th>
		<th>Action</th>		
		
	</thead>	
				
	<c:forEach var="salesorder" items="${salesOrderList}">
	<tr bgcolor="#FFFFFF">
		<td>${salesorder.salesOrderId}</td>
		<td><a href="salesorderjoborder.htm?orderNo=${salesorder.salesOrderId}" >${salesorder.salesOrderId}</a></td>
		<td><fmt:formatDate  value="${salesorder.orderDate}" pattern="yyyy-MMM-dd"/></td>
		<td>${salesorder.customerName}</td>
		<td>${salesorder.salesmanName}</td>
		<td>${salesorder.bullionType1}</td>		
		<td>${salesorder.bullionRate1}</td>
		<td>${salesorder.advance}</td>
		<td>${salesorder.totalAmount}</td>
		<td><fmt:formatDate  value="${salesorder.salesDate}" pattern="yyyy-MMM-dd"/></td>
		<td>${salesorder.orderStatus}</td>
		<td><a href="viewSalesOrder.htm?salesOrderId=${salesorder.salesOrderId}" class="edit">Edit</a></td>   		
			
	</tr>
	</c:forEach>
	</table>	
	</div>
	</div>
</div>

</body>		

</html>