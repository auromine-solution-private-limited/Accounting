<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html xmlns="http://www.w3.org/1999/xhtml">

<div id="col-form" align="left">
	<div class="boxed">
	<div class="title">Scheme Started List</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	 <a href="addstartscheme.htm" class="create_or_list">Start New Scheme</a>
	 <br>
	 <div class="summary">Start Scheme List<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
	<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
<thead class="row_head">
		<th>Id</th>
		<th>Scheme Name</th>
		<th>Scheme Type</th>
		<th>Scheme In Amounts</th>
		<th>Scheme In Grams</th>
		<th>Duration</th>
		<th>Date</th>		
		<!-- th>Status</th-->				
		<th style="width:70px; text-align: center;">Action</th>
	</thead>					
	<c:forEach var="startscheme" items="${startSchemeList}">
	<tr bgcolor="#FFFFFF">
		<td>${startscheme.start_schemeId}</td>				   
		<td>${startscheme.schemeName}</td>
		<td>${startscheme.schemeType}</td>
		<td>${startscheme.schemeInAmount}</td>
		<td>${startscheme.schemeInGrams}</td>	
		<td>${startscheme.schemeDuration}</td>
		<td><fmt:formatDate  value="${startscheme.schemeStartDate}" pattern="yyyy-MMM-dd"/></td>
		<!-- td>${startscheme.schemeStatus}</td-->		
		<td><a href="viewstartscheme.htm?start_schemeId=${startscheme.start_schemeId}" class="edit">Edit</a> <!-- |<a href="closestartscheme.htm?start_schemeId=${startscheme.start_schemeId}" class="closing">Close Scheme</a--></td>				   
		
	</tr>
	</c:forEach>	
	</table>	
	</div>
</div>
</div>
</html>