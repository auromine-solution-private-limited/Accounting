<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<div id="col-form" align="left">
<div class="boxed">
<div class="title">Stock Transfer list</div>
<div class="content" style="height:550px; width:748px; overflow-x:auto ; overflow-y: auto; padding-bottom:11px;">
<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>

	<a href="tagissuelist.htm" class="create_or_list">TagIssue List</a>
	<a href="formtransfer.htm" class="create_or_list">New Transfer</a>				
	<a href="journal.htm" class="create_or_list">Journal List</a>
	<br>
	<div class="summary">Stock Transfer list<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
	
			
	<thead class="row_head">
		<th>Id</th>
		<th>TransferNo</th>
		<th>Transfer Type</th>
		<th>Transfer Date</th>
		<th>From ItemCode</th>
		<th>To ItemCode</th>
		<th>From ItemName</th>
		<th>To ItemName</th>
		<th>Gross Weight</th>		
		<th>Quantity</th>
	</thead>
	<c:forEach var="transfer" items="${transferList}">
		<c:if test='${transfer.transferNO != null}'>
		<tr>
			<td>${transfer.transferId}</td>
			<td>${transfer.transferNO}</td>
			<td>${transfer.transferType}</td>
			<td><fmt:formatDate  value="${transfer.transferDate}" pattern="yyyy-MMM-dd"/></td>
			<td>${transfer.fromItemNo}</td>
			<td>${transfer.toItemNo}</td>
			<td>${transfer.fromItemName}</td>
			<td>${transfer.toItemName}</td>
			<td>${transfer.fromGrossWeight}</td>			
			<td>${transfer.fromQty}</td>
		</tr>
		</c:if>
	</c:forEach>
</table>
</div>
</div>
</div>
</html>