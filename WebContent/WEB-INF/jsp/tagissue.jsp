<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<div id="col-form" align="left">
<div class="boxed">
<div class="title">Tag Issue List</div>
<div class="content">
<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>

	<a href="formtransfer.htm" class="create_or_list">New Transfer</a>
	<a href="tagissue.htm" class="create_or_list">New TagIssue</a>
	<br>
	<div class="summary">Tag Issue List<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
	
			
	<thead class="row_head">
		<th>Id</th>
		<th>Issue Code</th>
		<th>Issue Date</th>
		<th>Category Name</th>
		<th>Item Name</th>
		<th>Item Qty/set</th>
		<th>Weight</th>
		<th>Desc</th>
	</thead>
	<c:forEach var="transfer" items="${transferList}">
		<c:if test='${transfer.itemName != null}'>
		<tr bgcolor="#FFFFFF">
			<td>${transfer.transferId}</td>
			<td>${transfer.tagissue}</td>
			<td><fmt:formatDate  value="${transfer.transferDate}" pattern="yyyy-MMM-dd"/></td>
			<td>${transfer.category}</td>
			<td>${transfer.itemName}</td>
			<td>${transfer.itemqtset}</td>
			<td>${transfer.itemweght}</td>
			<td>${transfer.description}</td>
			</tr>
			</c:if>
	</c:forEach>
</table>
</div>
</div>
</div>
</html>