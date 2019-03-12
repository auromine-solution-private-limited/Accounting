<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<div id="col-form" align="left">
	<div class="boxed">
		<div class="title">POS Purchase</div>
		<div class="content">
		<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
		<a href="formPOSpurchase.htm" class="create_or_list">POS Purchase Form</a> <!-- ************************ Call to URI  formcustomer.jsp page-->
		<a href="formPOSPurchaseReturn.htm" class="create_or_list">POS Purchase Return Form</a>
		<br>
		<div class="summary">POS Purchase List<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>		
<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
	<thead class="row_head">
		<tr>
			<th>Id</th>
			<th>Invoice No</th>
			<th>Date</th>
			<th>Transaction Type</th>
			<th>Supplier Name</th>
			<th>Total Tax Amount</th>
			<th>Grand Amount</th>
			<th>Action</th>
		</tr>							
	</thead>	
	<tbody>
			 <c:forEach var="po" items="${pospurchaseList}">
				<tr>	
				   <td>${po.purchaseId}</td>
				   <td>${po.invoiceNO}</td>					
				   <td><fmt:formatDate  value="${po.pdate}" pattern="yyyy-MMM-dd"/></td>
				   <td>${po.purchaseType}</td>
				   <td>${po.supplierName}</td>		
				   <td><fmt:formatNumber pattern="0.00" type="number" maxFractionDigits="2" value="${po.totalTax}" /></td>
				   <td><fmt:formatNumber pattern="0.00" type="number" maxFractionDigits="2" value="${po.grandAmount}" /></td>
				   <td><a href="viewPOSPurchase.htm?purchaseId=${po.purchaseId}" class="edit">Edit</a> </td>
				</tr>
			</c:forEach>
	</tbody>					
</table>		
</div>
</div>
</div>
</html>