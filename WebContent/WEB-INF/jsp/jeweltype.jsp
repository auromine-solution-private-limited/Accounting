<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	
<div id="col-form">
	<div class="boxed" align="left">
	<div class="title">Category List</div>
	<div class="content" style="height:550px; width:748px; overflow-x:auto ; overflow-y: auto; padding-bottom:10px;">
	<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
%>
		
	<a href="formjeweltype.htm"><b><u>Create New Category</u></b></a>&nbsp;&nbsp;
	<!--<a href="category_sub.htm"><b><u>Sub Category List</u></b></a>-->
	
	<table border="1" width=100%>	
	<tr>	
	<td bgcolor="#BBDDFF" colspan="10" align="center"><b>Category List</b></td></tr>		
	<tr>
		<td bgcolor="#BBDDFF" >Jewel Id</td>
		<td bgcolor="#BBDDFF" >Jewel Name</td>		
		<td bgcolor="#BBDDFF" >Base Metal Used</td>
		<td bgcolor="#BBDDFF" >Opening Gross Wt</td>
	</tr>					
	<c:forEach var="jeweltype" items="${jewelTypeList}">
	
	<c:if test='${jeweltype.jewelTypeId > 10}'>
	<tr bgcolor="#FFFFFF">
	<td><a href="formjeweltype.htm?jewelTypeId=${jeweltype.jewelTypeId}" >${jeweltype.jewelTypeId}</a> </td>	  
	<td><a href="category_sub.htm?baseCategory=${jeweltype.jewelName}">${jeweltype.jewelName}</a></td>
	<td>${jeweltype.metalUsed}</td>
	<td>${jeweltype.totalGrossWeight}</td>
	</tr>
	</c:if>
	</c:forEach>	
	</table>	
	</div>	
	</div>
</div>
</html>