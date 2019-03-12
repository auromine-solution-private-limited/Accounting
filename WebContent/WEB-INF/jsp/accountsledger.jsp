 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html >
<body>
<!-- col_one -->
			
<div id="col-form" align="left">
<div class="boxed">
<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>

<div class="title">Ledger Account</div>
<div class="content">
	<!-- <a href="formledger.htm" class="create_or_list">Back</a> -->
	<a href="formledger.htm?ledgerGroupe=${accounts.accountGroup}" class="create_or_list">Add New Ledger</a> <!-- ************************ Call to URI  formcustomer.jsp page-->
<br>	
	<br>	
	<div class="summary">Group Account:		
	<c:set var="id" value="${accounts.accountId}"></c:set>
	<c:choose >
	<c:when test="${ id==1}">Bank Accounts</c:when>
	<c:when test="${ id==2}">Bank Loan Accounts</c:when>
	<c:when test="${ id==3}">Bank OCC Accounts</c:when>
	<c:when test="${ id==4}">Capital Accounts</c:when>
	<c:when test="${ id==5}">Cash Accounts</c:when>
	<c:when test="${ id==6}">Current Assets</c:when>
	<c:when test="${ id==7}">Current Liabilities</c:when>
	<c:when test="${ id==8}">Direct Expenditure</c:when>
	<c:when test="${ id==9}">Direct Income</c:when>
	<c:when test="${ id==10}">Fixed Assets</c:when>
	<c:when test="${ id==11}">Indirect Expenditure</c:when>
	<c:when test="${ id==12}">Indirect Income</c:when>
	<c:when test="${ id==13}">Profit And Loss Account</c:when>
	<c:when test="${ id==14}">Purchae Account</c:when>
	<c:when test="${ id==15}">Sales Account</c:when>
	<c:when test="${ id==16}">Stock Account</c:when>
	</c:choose><br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
	
			
	<thead class="row_head">
	<tr>
			<th>S.no</th>		
			<th>Id</th>		
			<th>Name</th>
			<th>Account Group</th>	
			<th>Closing Balance</th>
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
				   <td>${ledger.closingTotalBalance}</td>
				   <td>${ledger.closingTotalType}</td>
				   <td><a href="viewformledger.htm?ledgerId=${ledger.ledgerId}&accountId=${accounts.accountId}" class="edit">Edit</a></td> 
				   
				</tr>
			</c:forEach>							
		</table>		
		
	</div> <!--content -->
	</div> <!--boxed -->
	
</div><!-- content_container -->

</body>		

</html>