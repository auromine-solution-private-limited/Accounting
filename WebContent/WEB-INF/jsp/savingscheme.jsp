<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<div id="col-form" align="left">
	<div class="boxed">
	<div class="title">Saving Scheme List</div>
	<div class="content">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
	 <a href="addnewscheme.htm" class="create_or_list">Add New Scheme</a>
	 <a href="addstartscheme.htm" class="create_or_list">Start Scheme</a>
	 <br>
	 <div class="summary">Saving Scheme List<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
	<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
<thead class="row_head">
		<th>Id</th>
		<th>Scheme Name</th>
		<th>Scheme Type</th>
		<th>Scheme In Amounts</th>
		<th>Scheme In Grams</th>		
		<th>Action</th>
	</thead>					
	<c:forEach var="savingscheme" items="${savingSchemeList}">
	<tr bgcolor="#FFFFFF">
		<td>${savingscheme.saving_schemeId}</td>				   
		<td>${savingscheme.schemeName}</td>
		<td>${savingscheme.schemeType}</td>
		<td>${savingscheme.schemeInAmount}</td>
		<td>${savingscheme.schemeInGrams}</td>		
		<td><a href="viewsavingscheme.htm?saving_schemeId=${savingscheme.saving_schemeId}" class="edit">Edit</a></td>				   
		
	</tr>
	</c:forEach>	
	</table>	
	</div>
</div>
</div>
</html>