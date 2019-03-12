<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

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
	
	 <a href="formratemaster.htm" class="create_or_list">Add New Rate</a>
	 <br>
	 <div class="summary">Rate Master List</div>
	<table border="0" width=100% cellspacing="0" cellpadding="0" class="list_table">
<thead class="row_head">
		<th>Id</th>
		<th>Gold Bullion</th>
		<th>Gold Ornaments</th>
		<th>Silver Bullion</th>
		<th>Silver Ornaments</th>
		<th>Exchange Gold</th>
		<th>Exchange Silver</th>
		<th>LastUpdate Date</th>
		<th>Action</th>
	</thead>					
	<c:forEach var="ratemaster" items="${rateMasterList}">
	<tr bgcolor="#FFFFFF">
		<td>${ratemaster.ratemasterId}</td>				   
		<td>${ratemaster.goldBullion}</td>
		<td>${ratemaster.goldOrnaments}</td>
		<td>${ratemaster.silverBullion}</td>
		<td>${ratemaster.silverOrnaments}</td>
		<td>${ratemaster.exchangeGold}</td>
		<td>${ratemaster.exchangeSilver}</td>
		<td>${ratemaster.lastUpdateDate}</td>
		<td><a href="viewratemaster.htm?ratemasterId=${ratemaster.ratemasterId}" class="edit">Edit</a></td>				   
		
	</tr>
	</c:forEach>	
	</table>	
	</div>
</div>
</div>
</html>