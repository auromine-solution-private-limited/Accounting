<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<div id="col-form" align="left">
<div class="boxed">
<div class="title">Create Purchase</div>
<div class="content">
<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>

<a href="formpurchase.htm" class="create_or_list">New purchase</a>
<!-- <a href="formPOSpurchase.htm" class="create_or_list">New POS purchase</a> -->
<br>

<div class="summary">Purchase List<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
	
	<thead class="row_head">
			<th>Id</th>
			<th>BillNo</th>
			<th>Pur. Date</th>
			<th>Purchase Type</th>
			<th>Supplier Name</th>
			<th>Bullion Type</th>		
			<th>Gross Weight</th>
			<th>No. Of.Pcs</th>
			<th>Rate</th>
			<th>Amount</th>
			<th>Total Amount</th>		
			<th>Action</th>
			
					
	</thead>
	<c:forEach var="purchase" items="${purchaseList}">
		<tr bgcolor="#FFFFFF">
			 <td>${purchase.purchaseId}</td>
			<td>${purchase.purchseInvoice}</td>
			<td><fmt:formatDate  value="${purchase.purchaseDate}" pattern="yyyy-MMM-dd"/></td>
			<td>${purchase.purchaseType}</td>
			<td>${purchase.supplierName}</td>			
			<td>${purchase.bullionType}</td>
			<td>${purchase.grossWeight}</td>			
			<td>${purchase.numberOfPieces}</td>
			<td>${purchase.rate}</td>
			<td>${purchase.purchaseAmount}</td>
			<td>${purchase.totalAmount}</td>
			<td><a href="viewpurchase.htm?purchaseId=${purchase.purchaseId}" class="edit">Edit</a></td>
			
		</tr>
	</c:forEach>	
	
</table>
</div>
</div>
</div>
</html>