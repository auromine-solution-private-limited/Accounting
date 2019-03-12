<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<!---------------------------------------->	
<div id="col-form" align="left">
	<div class="boxed">
	<div class="title">Jewel Repair List</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<a href="joborder.htm" class="create_or_list">Job Order List</a>	
	<a href="formjewelfix.htm" class="create_or_list">New Jewel Repair</a>		
	<a href="salesorder.htm" class="create_or_list">Sales Order List</a>
	<br>
	<div class="summary">Jewel Repair List<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
	<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
	
			
	<thead class="row_head">
		<th>Id</th>
		<th>Order No</th>
		<th>Customer Name</th>		
		<th>Received Date</th>
		<th>Smith Name</th>
		<th>Metal Type</th>
		<th>JewelName</th>
		<th>Issued Gross Wt</th>			
		<th>GrossWt After Fixing</th>		
		<th>Total Cost</th>
		<th>Status</th>
		<th>Delivery Date</th>
		<th>Action</th>
		
	</tr>	
				
	<c:forEach var="jewelfix" items="${jewelFixList}">
	<tr bgcolor="#FFFFFF">
		<td>${jewelfix.repairId}</td>
		<td>${jewelfix.orderNO}</td>
		<td>${jewelfix.customerName}</td>		
		<td><fmt:formatDate  value="${jewelfix.issueDate}" pattern="yyyy-MMM-dd"/></td>
		<td>${jewelfix.smithName}</td>
		<td>${jewelfix.metalType}</td>
		<td>${jewelfix.jewelName}</td>
		<td>${jewelfix.issuedGrossWeight}</td>					
		<td>${jewelfix.grossWtAfterFixing}</td>		
		<td>${jewelfix.totalCost}</td>
		<td>${jewelfix.status}</td>
		<td><fmt:formatDate  value="${jewelfix.receivedDate}" pattern="yyyy-MMM-dd"/></td>
		<td><a href="formjewelfixing.htm?repairId=${jewelfix.repairId}" class="edit">Edit</a></td>		
		
	</tr>
	</c:forEach>	
	
	</table>	
	</div>
	</div>
</div>
</html>