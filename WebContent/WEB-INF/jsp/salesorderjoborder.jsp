<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<div id="col-form" align="left">
	<div class="boxed">
	<div class="title">Job Order List</div>
	<div class="content" >
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
<a href="formjoborder.htm" class="create_or_list">New Job Order</a>
<a href="salesorder.htm" class="create_or_list">Sales Order List</a>
<br>
<div class="summary">Job Order List</div>
		<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
<thead class="row_head">
		<th> Id</th>
		<th>Job order No</th>		
		<th>Job order Date</th>
		<th>SmithName</th>
		<th>Bullion Type</th>
		<th>Finished ItemName</th>
		<th>Finished Item Gross Weight</th>
		<th>Finished Item Net Weight</th>
		<th>Delivery Date</th>
		<th>Action</th>
		</thead>					
						
		<c:forEach var="joborder" items="${jobOrderList}">
		<tr bgcolor="#FFFFFF">
			<td>${joborder.jobOrderId}</td>		
			<td>${joborder.orderNo}</td>
			<td><fmt:formatDate  value="${joborder.orderDate}" pattern="yyyy-MMM-dd"/></td>
			<td>${joborder.smithName}</td>
			<td>${joborder.bullionType}</td>
			<td>${joborder.finishedItem}</td>
			<td>${joborder.finisheditemGrossWt}</td>
			<td>${joborder.finisheditemNetWt}</td>
			<td><fmt:formatDate  value="${joborder.deliveryDate}" pattern="yyyy-MMM-dd"/></td>
			<td><a href="formjoborderrcpt.htm?jobOrderId=${joborder.jobOrderId}" class="edit">Edit</a></td>		
			
		</tr>
		</c:forEach>
		</table>
		</div>
	</div>
</div>
</html>