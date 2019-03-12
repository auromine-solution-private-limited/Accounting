<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html xmlns="http://www.w3.org/1999/xhtml">	
	<div id="col-form" align="left">
	<div class="boxed">
		<div class="title">Creditors Account</div>
		<div class="content">
		<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
		
		<a href="newSupplier.htm?accountId=17" class="create_or_list">Add New Supplier</a> <!-- ************************ Call to URI  formcustomer.jsp page-->
		<br>
		<div class="summary">Creditors Account<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
		
		<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
<thead class="row_head">
	<tr>
			<th>S.no</th>
			<th>Id</th>
			<th>Name</th>
			<th>Account Group</th>
			<th>Primary Phone</th>
			<th>Opening Bal</th>		
			<th>Opening Type</th>
			<th>Closing Bal</th>
			<th>Closing Type</th>
			<th>Action</th>		
	</tr>		
					
	</thead>	
			
		
							
				
			<c:forEach var="ledger" items="${accounts.ledgers}">
				<tr>	
				   <td class="s_no"></td>
				   <td>${ledger.ledgerId}</td>				
		  		   <td>${ledger.ledgerName}</td>
				   <td>${ledger.accountGroup}</td>
				   <td>${ledger.primaryPhone}</td>
				   <td><fmt:formatNumber pattern="0.00" type="number" maxFractionDigits="2" value="${ledger.opTotalBalance}" /></td>
				   <td>${ledger.opTotalType}</td>
				   <td><fmt:formatNumber pattern="0.00" type="number" maxFractionDigits="2" value="${ledger.closingTotalBalance}" /></td>
				   <td>${ledger.closingTotalType}</td>
				   <td><a href="viewSupplier.htm?ledgerId=${ledger.ledgerId}&accountId=${accounts.accountId}" class="edit">Edit</a> </td>
				</tr>
			</c:forEach>					
		</table>		
	</div>
	</div>
</div>
</html>