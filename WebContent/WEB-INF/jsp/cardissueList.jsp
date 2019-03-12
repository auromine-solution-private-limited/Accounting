<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<div id="col-form" align="left">
	<div class="boxed">
	<div class="title">Rate Master</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	 <a href="formcardissue.htm" class="create_or_list">Issue New Card</a>
	 <br>
	 <div class="summary">Card Issue List<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
	<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
<thead class="row_head">
		<th>Card No</th>
		<th>Customer Name</th>
		<th>Scheme Name</th>
		<th>Scheme Type</th>
		<th>Scheme In Amounts</th>
		<th>Scheme In Grams</th>
		<th>Date Of Joning</th>	
		<th>Scheme Status</th>
		<th>No Of Installment</th>			
		<th>Action</th>
	</thead>					
	<c:forEach var="issuelist" items="${cardissueList}">
	<tr bgcolor="#FFFFFF">
		<td>${issuelist.cardNo}</td>	
		<td>${issuelist.customerName}</td>			   
		<td>${issuelist.schemeName}</td>
		<td>${issuelist.schemeType}</td>
		<td>${issuelist.schemeInAmount}</td>
		<td>${issuelist.schemeInGrams}</td>	
		<td><fmt:formatDate  value="${issuelist.dateOfJoining}" pattern="yyyy-MMM-dd"/></td>
		<td>${issuelist.status}</td>	
		<td>${issuelist.installment}</td>
		<td><a href="editcardissue.htm?cardId=${issuelist.cardId}" class="edit">Edit</a></td>				   
		
	</tr>
	</c:forEach>	
	</table>	
	</div>
</div>
</div>
</html>