<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	
<div id="col-form" align="left">
	<div class="boxed">
	<div class="title">Search Sales Order</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<div class="summary">Order Search List<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
	<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
	<thead class="row_head">
		<th bgcolor="#BBDDFF" >Order No</th>
		<th bgcolor="#BBDDFF" >S.O. Date</th>		
		<th bgcolor="#BBDDFF" >Customer Name</th>
		<th bgcolor="#BBDDFF" >SalesMan Name</th>
		<th bgcolor="#BBDDFF" >Bullion Type</th>		
		<th bgcolor="#BBDDFF" >Rate</th>
		<th bgcolor="#BBDDFF" >Advance</th>
		<th bgcolor="#BBDDFF" >Total Amount</th>
		<th bgcolor="#BBDDFF" >Delivery Date</th>
		<th bgcolor="#BBDDFF" >Order Status</th>
		<th bgcolor="#BBDDFF" >Action</th>		
		
	</thead>	
		<c:if test="${empty salesOrderList }">
		<B> <font color="red">*** NO such File Exists</font></B>
		</c:if>		
	<c:forEach var="salesorder" items="${salesOrderList}">
	<tr bgcolor="#FFFFFF">
		<td><a href="salesorderjoborder.htm?orderNo=${salesorder.salesOrderId}" class="a_link">${salesorder.salesOrderId}</a></td>		
		<td>${salesorder.orderDate}</td>
		<td>${salesorder.customerName}</td>
		<td>${salesorder.salesmanName}</td>
		<td>${salesorder.bullionType1}</td>		
		<td>${salesorder.bullionRate1}</td>
		<td>${salesorder.advance}</td>
		<td>${salesorder.totalAmount}</td>
		<td>${salesorder.salesDate}</td>
		<td>${salesorder.orderStatus}</td>
		<td><a href="viewSalesOrder.htm?salesOrderId=${salesorder.salesOrderId}" class="edit">Edit</a></td>   		
	</tr>
	</c:forEach>	
	
	</table>	 
	
</div>
</div>
</div>
</html>