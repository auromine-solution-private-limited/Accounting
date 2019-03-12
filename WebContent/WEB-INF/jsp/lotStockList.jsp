<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<div id="col-form" align="left">
<div class="boxed">
<div class="title">Lot Stock List</div>
<div class="content">
<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	<br>
	<div class="summary">Lot Stock List<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
	<a href="reports.htm?lotDetailedStock" class="create_or_list" style="float: right;">Lot Stock Reports</a>
	<a href="estimateSalesList.htm" class="create_or_list" style="float: right;">Estimate Sales List</a>
	<a href="estimatePOSSalesList.htm" class="create_or_list" style="float: right;">Estimate POS Sales List</a>
<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
	
			
	<thead class="row_head">
		<th>Id</th>
		<!-- <th>Date</th>
		<th>Lot Type</th> -->
		<th>Stock Name</th>
		<th>Total GrossWeight</th>
		<th>Total Qty/set</th>		
		<th>Action</th>
	</thead>
		
		<tr bgcolor="#FFFFFF">
			<td>1</td>			
		
			<td>Gold Lot Stock</td>
			<td><fmt:formatNumber pattern="0.000" type="number" maxFractionDigits="3" value="${lotgoltgwt}" /></td>
			<td>${lotgoltqty}</td>
			<td><a href="lotaddstock.htm?lt_type=gold" class="add_new">Add lot Stock</a> | <a href="lotreturnstock.htm?lt_type=gold" class="edit" >Return lot Stock</a> </td>
		</tr>	
		<tr bgcolor="#FFFFFF">
			<td>2</td>			
		
			<td>Silver Lot Stock</td>
			
			<td><fmt:formatNumber pattern="0.000" type="number" maxFractionDigits="3" value="${lotsilvergwt}" /></td>
			<td>${lotsilverqty}</td>
			<td><a href="lotaddstock.htm?lt_type=silver" class="add_new">Add lot Stock</a> | <a href="lotreturnstock.htm?lt_type=silver" class="edit" >Return lot Stock</a> </td>
		</tr>			
	
</table>
</div>
</div>
</div>
</html>