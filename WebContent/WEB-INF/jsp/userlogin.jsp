<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	
<div id="col-form">
	<div class="boxed" align="left">
	<div class="title">User Login</div>
	<div class="content" style="height:550px; width:748px; overflow-x:auto ; overflow-y: auto; padding-bottom:10px;">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
	
		<a href="addnewuser.htm" class="create_or_list">Add New User</a>
		<a style="float:right" href="ratemaster.htm" class="create_or_list">Rate Master</a>
		<a href="CompanyDetail.htm" style="float:right" class="create_or_list">Company info Settings</a>
		<c:if test="${roll eq 'Admin'}">
			<a href="Config.htm" style="float:right" class="create_or_list">Application Settings</a>
		</c:if>
		<br><div class="summary">User Login List<br><input name="search" type="text" id="listsearch" style="float: left;"  /></div>
	<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
	<thead class="row_head">
	<tr>
		<th>Id</th>
		<th>User Name</th>		
		<th>Full Name</th>
		<th>EmailId</th>
		<th>User Roll</th>
		<th>Active</th>
		<th>Edit</th>
	</tr>
	</thead>					
	<c:forEach var="userlogin" items="${userlogin}">
	<c:url var="editUrl" value="/edituser.htm?id=${userlogin.id}" />
	<tr bgcolor="#FFFFFF">	
		<td>${userlogin.id}</td>
		<td>${userlogin.userName}</td>			
		<td>${userlogin.fullName}</td>
		<td>${userlogin.emailId}</td>
		<td>${userlogin.rollName}</td>		
		<td>${userlogin.active}</td>
		<td><a href="${editUrl}" class="edit">Edit</a></td>
			
	</tr>
	</c:forEach>	
	</table>	
	</div>
	</div>
</div>
</html>