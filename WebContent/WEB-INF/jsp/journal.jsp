<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<div id="col-form" align="left">
<div class="boxed">

<div class="title">Journal List</div>
<div class="content">
<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>

	<a href="tagissuelist.htm" class="create_or_list">Tag Issue List</a>
	<a href="formjournal.htm" class="create_or_list">New Journal</a>			
	<a href="transfer.htm" class="create_or_list">Transfer List</a>
	<br>
	<div class="summary">Journal List<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
	
			
	<thead class="row_head">
		<th>Id</th>
		<th>JournalNo</th>
		<th>Journal Type</th>
		<th>Journal Date</th>
		<th>Debit Account</th>
		<th>Credit Account</th>
		<th>Debit Amount</th>
		<th>Credit Amount</th>
		<th>Narration</th>
		<th>Action</th>
	</thead>
	<c:forEach var="journal" items="${journalList}">
		<tr>
			<td>${journal.journalId}</td>
			<td>${journal.journalNO}</td>
			<td>${journal.journalType}</td>
			<td><fmt:formatDate  value="${journal.journalDate}" pattern="yyyy-MMM-dd"/></td>
			<td>${journal.debitAccountName}</td>
			<td>${journal.creditAccountName}</td>
			<td>${journal.debitAmount}</td>
			<td>${journal.creditAmount}</td>
			<td>${journal.narration}</td>
			<td><a href="viewformjournal.htm?journalId=${journal.journalId}" class="edit">Edit</a></td>		
		</tr>
	</c:forEach>	
</table>
</div>
</div>
</div>
</body>
</html>