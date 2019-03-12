<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<script type="text/javascript" src="script/jquery-all-manuscript.js"></script>	
<div id="col-form" align="left">
	<div class="boxed">
	<div class="title lotstock"></div>
	<div class="content">
	<% 
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
<div class="lotType" id="LTGold">
<a href="formaddlotstock.htm?lt_type=gold" class="create_or_list">Add New Stock</a> <!-- ************************ Call to URI  formaddlotstock.jsp page-->
<a style="padding-right:8px; float:right;" href="lotstock.htm" class="create_or_list">Stock Summary List</a>
<br>
<div class="summary">Lot Stock<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
<thead class="row_head">
			<th>S.No</th>
			<th>Date</th>
			<th>Item Name</th>			
			<th>Lot Type</th>
			<th>Gross Weight</th>		
			<th>Quantity</th>
			<th>Description</th>		
			<th>Action</th>	
	</thead>			
		 				
	<c:forEach var="lotstock" items="${goldStockList}">
	<tr bgcolor="#FFFFFF">
		<td class="s_no"></td>	
		<!-- td>${lotstock.lotId}</td -->		
		<td><fmt:formatDate  value="${lotstock.lotDate}" pattern="yyyy-MMM-dd"/></td>		
		<td>${lotstock.lotItemName}</td>
		<td>${lotstock.lotType}</td>
		<td>${lotstock.grossWeight}</td>
		<td>${lotstock.quantity}</td>
		<td>${lotstock.description}</td>
		<td><a href="viewlotstockadd.htm?lotId=${lotstock.lotId}" class="edit">Edit</a></td>
		
	</tr>			
	</c:forEach>
		
		</table>
</div>
<div class="lotType" id="LTSilver">
<a href="formaddlotstock.htm?lt_type=silver" class="create_or_list">Add New Stock</a> <!-- ************************ Call to URI  formaddlotstock.jsp page-->
<a style="padding-right:8px; float:right;" href="lotstock.htm" class="create_or_list">Stock Summary List</a>
<br>
<div class="summary">Lot Stock<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
<thead class="row_head">
			
			<th>S.No</th>
			<th>Date</th>
			<th>Lot Type</th>
			<th>Item Name</th>			
			<th>Gross Weight</th>		
			<th>Quantity</th>	
			<th>Description</th>		
			<th>Action</th>	
	</thead>			
		 				
	<c:forEach var="lotstock" items="${silverStockList}">
	<tr bgcolor="#FFFFFF">
		
		<td class="s_no"></td>		
		<td><fmt:formatDate  value="${lotstock.lotDate}" pattern="yyyy-MMM-dd"/></td>		
		<td>${lotstock.lotType}</td>
		<td>${lotstock.lotItemName}</td>
		<td>${lotstock.grossWeight}</td>
		<td>${lotstock.quantity}</td>
		<td>${lotstock.description}</td>
		<td><a href="viewlotstockadd.htm?lotId=${lotstock.lotId}" class="edit">Edit</a></td>
		
	</tr>			
	</c:forEach>
		
		</table>
</div>		
	</div>
	</div>
	</div>
<script type="text/javascript">
$(document).ready(function(){
	$(".lotType").hide();
	var ParamIdVal = window.location.search;
	//alert("5"+ParamIdVal );
	if(ParamIdVal == "?lt_type=gold"){
		$("#LTGold").show();
	}
	if(ParamIdVal == "?lt_type=silver"){
		$("#LTSilver").show();
	}
});
</script>
</body>
</html>