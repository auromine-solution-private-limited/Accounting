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
	
	<a href="newSales.htm" class="create_or_list">New Ornament Sales</a>
	<a href="formbullionsales.htm" class="create_or_list">New Bullion Sales</a>
	<a href="newSalesReturn.htm" class="create_or_list">Sales Return</a>
	<br />
	<div class="summary">Sales List<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
	
	<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
	<thead class="row_head">
	<tr>
		<th>S. No</th>
		<th>INV.Id</th>		
		<th>Sales Date</th>
		<th>Sales Type</th>
		<th>CustomerName</th>
		<th>Metal Type</th>
		<th>Bill Amount</th>
		<th>Action</th>
	</tr>		
	</thead>	
				
	<c:forEach var="sales" items="${salesList}">
	<c:set var="form" value="${sales.formType}" />
	<c:if test="${form eq 'Sales' || form eq 'Bullion Sales' || form eq 'Sales Return'}">
		<tr bgcolor="#FFFFFF">
			<td class="s_no"></td> 
			<td>${sales.salesTypeId}</td>		
			<td><fmt:formatDate value="${sales.salesDate}" pattern="yyyy-MMM-dd"/></td>
			<td>${sales.formType}</td>
			<td>${sales.customerName}</td>		
			<td>${sales.bullionType}</td>
			<td>${sales.billAmount}</td>
			<td><a href="viewSales.htm?salesId=${sales.salesId}" class="edit">Edit</a></td>		
		</tr>
	</c:if>
	</c:forEach>
	
	</table>	
	</div>
	</div>
</div> 

</body>		

</html>