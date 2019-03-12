<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<div id="col-form" align="left">
	<div class="boxed">
	<div class="title">Create Sales</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	<a href="formPOSsales.htm" class="create_or_list">New POS Sales</a> <!-- ************************ Call to URI  formcustomer.jsp page-->
<a href="possalesreturnList.htm" class="create_or_list">New POS Sales Return</a>  <!-- ************************ Call to URI  formcustomer.jsp page-->
<br>
<div class="summary">Estimate POS Sales List<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
	<thead class="row_head">
	<tr>
		<th>S.NO</th>
		<th>Invoice Number</th>		
		<th>Sales Date</th>
		<th>SalesMan Name</th>
		<th>CustomerName</th>
		<th>Bill Amount</th>
		<th>Received Amount</th>
		<th>Action</th>
	</tr>		
	</thead>	
				
	<c:forEach var="sales" items="${possalesList}">
	<c:set var="form" value="${sales.salesType}" />
	<c:if test="${form eq 'Estimate POS Sales'}">
		<tr bgcolor="#FFFFFF">
			<td class="s_no"></td> 
			<td>${sales.billNo}</td>
			<td><fmt:formatDate value="${sales.salesdate}" pattern="yyyy-MMM-dd"/></td>
			<td>${sales.salesmanName}</td>
			<td>${sales.customerName}</td>		
			<td>${sales.netAmount}</td>
			<td>${sales.cashAmount}</td>
			<td><a href="viewPOSSales.htm?posSalesId=${sales.posSalesId}" class="edit">Edit</a></td>		
		</tr>
	</c:if>
	</c:forEach>
	
	</table>	
	</div>
	</div>
</div> 

</body>	