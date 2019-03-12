<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page errorPage="ExceptionHandler.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<div id="col-form" align="left">
<div class="boxed">
<div class="title">Ledger Account</div>
<div class="content">
<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
<div class="summary">Account Search List<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
<thead class="row_head">
			
			<th >Id</th>
			<th >Name</th>
			<th >Account Group</th>
			<th >Opening Balance</th>
			<th >Opening Type</th>
			<th >Closing Balance</th>
			<th >Closing Type</th>
			<th>Action</th>
	</thead>
	
		<!--<c:if test="${ledger.ledgerName == list.ledgerName }">
		<c:out value="Account doesn't exist..."/>-->
			 <c:if test="${empty ledgerList }">
			 <b><font color="red">***No Such File Exists</font></b>
			 </c:if>			
		<c:forEach var="ledger" items="${ledgerList}">
			<tr>	   		  
				   
				   <td>${ledger.ledgerId}</td>
				   <td>${ledger.ledgerName}</td>
				   <td>${ledger.accountGroup}</td>
				   <td>${ledger.openingBalance}</td>
				   <td>${ledger.openingType}</td>
				   <td>${ledger.closingTotalBalance}</td>
				   <td>${ledger.closingTotalType}</td>
				   <td><a href="viewformledger.htm?ledgerId=${ledger.ledgerId}&accountId=${accId}" class="edit">Edit</a></td>				
			</tr>	
		</c:forEach>	
		<!--</c:if>-->
	
		</table>
	</div>
</div>
</div>
</html>