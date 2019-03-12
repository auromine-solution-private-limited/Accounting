<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Manual Sales List</title>
</head>
<body>
	<div id="col-form" align="left">
		<div class="boxed" >
		<div class="title">Manual Sales List</div>
		<div class="content">
			<%
			if(session.getAttribute("username")==null)
			{
			response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
			}
			%>
			
			<a href="newManualSales.htm" class="create_or_list">New Vat Manual Sales</a> <a href="newManualSalesVF.htm" class="create_or_list">New Vat Free Manual Sales</a> 
			<br>
			<div class="summary">Manual Sales List <br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
			<div id="Manualtab">
			<ul>
				<li><a href="#VatTax">Vat Tax</a></li>
				<li><a href="#VatTaxFree">Vat Tax Free</a></li>
			</ul>
			<div id="VatTax" class="ManulTabclass"  style="height:500px;width:763px; overflow-x:auto ; overflow-y: auto; padding-bottom:10px;">
			
				<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table2">
					<thead class="row_head">
					 <tr>
					    <th>Invoice Number</th> 
						<th>Bill Number</th>
						<th>Sales Date</th>
						<th>Customer Name</th>
						<th>Bill Amount</th>
						<th>Action</th>
					 </tr>					
					</thead>
							
					<c:forEach var="manualsales" items="${manualVatSalesList}">	
						<tr bgcolor="#FFFFFF">	
							<td>${manualsales.mBillAutoNO}</td>
							<td>${manualsales.mBillNo}</td>
							<td><fmt:formatDate  value="${manualsales.mBDate}" pattern="yyyy-MMM-dd"/></td>
							<td>${manualsales.customerName}</td>		
							<td>${manualsales.totalAmount}</td>
							<td><a href="viewManualSales.htm?mBillId=${manualsales.mBillId}" class="edit">Edit</a></td>		
						</tr>
					</c:forEach>
				</table>
				</div>
				
				<div id="VatTaxFree" class="ManulTabclass"  style="height:500px;width:763px; overflow-x:auto ; overflow-y: auto; padding-bottom:10px;">
				
				<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table2">
				<thead class="row_head">
				 <tr>
				    <th>Invoice Number</th> 
					<th>Bill Number</th>
					<th>Sales Date</th>
					<th>Customer Name</th>
					<th>Bill Amount</th>
					<th>Action</th>
				 </tr>					
				</thead>
						
				<c:forEach var="manualsales" items="${manualVatFreeSalesList}">	
					<tr bgcolor="#FFFFFF">	
						<td>${manualsales.mBillAutoNO}</td>
						<td>${manualsales.mBillNo}</td>
						<td><fmt:formatDate  value="${manualsales.mBDate}" pattern="yyyy-MMM-dd"/></td>
						<td>${manualsales.customerName}</td>		
						<td>${manualsales.totalAmount}</td>
						<td><a href="viewManualSales.htm?mBillId=${manualsales.mBillId}" class="edit">Edit</a></td>		
					</tr>
				</c:forEach>
			</table>
			</div>
			</div>
			</div>
		</div>
	</div>
<script type="text/javascript">
//$(".list_table2").advancedtable({searchField: "#listsearch", loadElement: "#loader", searchCaseSensitive: false, ascImage: "images/up.png", descImage: "images/down.png"});
</script>
</body>
</html>