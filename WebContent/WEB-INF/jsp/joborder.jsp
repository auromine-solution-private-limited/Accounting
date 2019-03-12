<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<!---------------------------------------->	
<div id="col-form" align="left">
	<div class="boxed">
	<div class="title">Job Order List</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<a href="formjoborder.htm"  class="create_or_list">Job Order Issue</a>
	<a href="salesorder.htm"  class="create_or_list">Sales Order List</a>
	<br>
	<div class="summary">Job Order List<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
	<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
	
			
	<thead class="row_head">
		<th>Id</th>		
		<th>Job order Date</th>
		<th>SmithName</th>
		<th>Metal Issue Type</th>
		<th>Issued Gross Weight</th>		
		<th>Status</th>
		<th>Received Item</th>
		<th>Finished Item Gross Weight</th>
		<th>Finished Item Net Weight</th>
		<th>No. Of Pcs</th>
		<th>Delivery Date</th>
		<th>Action</th>
	</thead>	
				
	<c:forEach var="joborder" items="${jobOrderList}">
	<tr bgcolor="#FFFFFF">
		
		<td>${joborder.jobOrderId}</td>
		<td><fmt:formatDate  value="${joborder.orderDate}" pattern="yyyy-MMM-dd"/></td>
		<td>${joborder.smithName}</td>
		<td>${joborder.bullionType}</td>
		<td>${joborder.issuedGrossWeight}</td>		
		<td>${joborder.status}</td>
		<td>${joborder.categoryName}</td>
		<td>${joborder.finisheditemGrossWt}</td>
		<td>${joborder.finisheditemNetWt}</td>
		<td>${joborder.numberOfPieces}</td>
		<td><fmt:formatDate  value="${joborder.deliveryDate}" pattern="yyyy-MMM-dd"/></td>
		<td><a href="formjoborderrcpt.htm?jobOrderId=${joborder.jobOrderId}" class="edit">Edit</a></td>
		
	</tr>
	</c:forEach>	
	
	</table>	
	</div>
	</div>
</div>

</html>